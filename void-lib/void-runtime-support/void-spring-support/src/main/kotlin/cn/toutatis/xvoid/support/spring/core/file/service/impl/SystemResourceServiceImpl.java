package cn.toutatis.xvoid.support.spring.core.file.service.impl;


import cn.toutatis.xvoid.common.exception.ContinueTransactionException;
import cn.toutatis.xvoid.data.common.base.SystemResource;
import cn.toutatis.xvoid.data.common.result.ProxyResult;
import cn.toutatis.xvoid.data.common.result.ResultCode;
import cn.toutatis.xvoid.data.common.result.SimpleResultMessage;
import cn.toutatis.xvoid.support.PkgInfo;
import cn.toutatis.xvoid.support.spring.config.ObjectStorageMode;
import cn.toutatis.xvoid.support.spring.config.VoidConfiguration;
import cn.toutatis.xvoid.support.spring.core.file.MinIOHelper;
import cn.toutatis.xvoid.support.spring.core.file.persistence.SystemResourceMapper;
import cn.toutatis.xvoid.support.spring.core.file.service.SystemResourceService;
import cn.toutatis.xvoid.support.spring.config.orm.mybatisplus.support.VoidMybatisServiceImpl;
import cn.toutatis.xvoid.toolkit.file.FileToolkit;
import cn.toutatis.xvoid.toolkit.file.image.CompressConfig;
import cn.toutatis.xvoid.toolkit.file.image.ImageCompressToolKit;
import cn.toutatis.xvoid.toolkit.file.image.PictureQualityDistributionStrategy;
import cn.toutatis.xvoid.toolkit.validator.Validator;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.val;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * <p>
 * 系统资源类 服务实现类
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2023-06-03
*/
@Service
public class SystemResourceServiceImpl extends VoidMybatisServiceImpl<SystemResourceMapper, SystemResource> implements SystemResourceService {


    @Autowired
    private VoidConfiguration voidConfiguration;

    @Autowired
    private MinIOHelper minIOHelper;

    private CompressConfig.CompressContent compressConfig;

    @PostConstruct
    public void init(){
        compressConfig = new CompressConfig(null).getConfig();
        compressConfig.setSaveFileRenameType("SOURCE");
        compressConfig.setPictureQualityDistributionStrategy(PictureQualityDistributionStrategy.EXTREME);
        compressConfig.setZipTimes(3);
    }

    @Override
    public Object receiveFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.getSize() == 0L){
            return new ProxyResult(ResultCode.UPLOAD_FAILED, SimpleResultMessage.RESPONSE_UPLOAD_MISS_FILE);
        }
        if (Validator.strIsBlank(multipartFile.getOriginalFilename())){
            return new ProxyResult(ResultCode.UPLOAD_FAILED, SimpleResultMessage.RESPONSE_UPLOAD_MISS_NAME);
        }
        String threadPath = FileToolkit.getThreadPath();
        String md5Hex = DigestUtils.md5Hex(multipartFile.getBytes());
        QueryWrapper<SystemResource> md5QueryWrapper = new QueryWrapper<>();
        md5QueryWrapper.eq("hash",md5Hex);
        val hashFile = this.getOne(md5QueryWrapper);
        //md5不冲突则秒传,合并文件
        if (hashFile == null) {
            String originalFilename = multipartFile.getOriginalFilename();
            assert originalFilename != null;
            String fileSuffix = FileToolkit.getFileSuffix(originalFilename);
            String randomId = RandomStringUtils.random(32, true, true).toLowerCase(Locale.getDefault());
            VoidConfiguration.GlobalServiceConfig globalServiceConfig = voidConfiguration.getGlobalServiceConfig();
            SystemResource systemResource = new SystemResource();
            systemResource.setOriginName(originalFilename);
            systemResource.setSuffix(fileSuffix);
            systemResource.setFileName(randomId);
            systemResource.setHash(md5Hex);
            systemResource.setSize((int) multipartFile.getSize());
            String contentType = multipartFile.getContentType();
            systemResource.setContentType(contentType);
            contentType = (contentType != null) ? contentType.split("/")[0] : "unknown";
            File localFile;
            if (globalServiceConfig.getObjectStorageMode() == ObjectStorageMode.LOCAL) {
                String resourceDir = threadPath + FileToolkit.RESOURCE_FILE_DIR;
                boolean resourceDirExist = FileToolkit.createDirectoryOrExist(resourceDir);
                if (resourceDirExist) {
                    if(globalServiceConfig.getFileObjectClassify()) {
                        localFile = new File("%s/%s/%s.%s".formatted(resourceDir, contentType, randomId, fileSuffix));
                        FileToolkit.createDirectoryOrExist(resourceDir + "/" + contentType);
                        systemResource.setPath("%s/%s/%s.%s".formatted(FileToolkit.RESOURCE_FILE_DIR, contentType, randomId, fileSuffix));
                    } else {
                        localFile = new File(resourceDir + "/" + randomId + "." + fileSuffix);
                        systemResource.setPath(FileToolkit.RESOURCE_FILE_DIR + "/" + randomId + "." + fileSuffix);
                    }
                    multipartFile.transferTo(localFile);
                    Boolean compressFile = globalServiceConfig.getCompressFile();
                    if (compressFile){
                        ArrayList<File> files = new ArrayList<>(1);
                        files.add(localFile);
                        String parentDir = localFile.getParent();
                        compressConfig.setLastSaveDir(parentDir);
                        ImageCompressToolKit.differentStandardThumbnail(files,compressConfig);
                    }
                }
            } else {
                String tmpDir = threadPath + FileToolkit.TEMP_FILE_DIR;
                boolean tmpDirExist = FileToolkit.createDirectoryOrExist(tmpDir);
                if (tmpDirExist) {
                    localFile = new File(tmpDir + "/" + randomId + "." + fileSuffix);
                    multipartFile.transferTo(localFile);
                    MinioClient client = minIOHelper.getClient();
                    UploadObjectArgs.Builder uploadObjectBuilder = UploadObjectArgs.builder();
                    uploadObjectBuilder.bucket(minIOHelper.bucket(MinIOHelper.XVOID_USER_RESOURCE_BUCKET)).filename(localFile.getPath());
                    if(globalServiceConfig.getFileObjectClassify()) {
                        uploadObjectBuilder.object(contentType+"/"+localFile.getName());
                    }else {
                        uploadObjectBuilder.object(localFile.getName());
                    }
                    UploadObjectArgs uploadObject = uploadObjectBuilder.build();
                    try {
                        client.uploadObject(uploadObject);
                    } catch (ErrorResponseException | InsufficientDataException | InternalException |
                            InvalidKeyException | InvalidResponseException | NoSuchAlgorithmException
                            | ServerException | XmlParserException e) {
                        e.printStackTrace();
                        throw new ContinueTransactionException("[%s]上传文件转储MINIO服务异常,请查看日志解决问题.".formatted(PkgInfo.MODULE_NAME));
                    }
                    GetPresignedObjectUrlArgs.Builder getUrlArgsBuilder = GetPresignedObjectUrlArgs.builder();
                    getUrlArgsBuilder.bucket(minIOHelper.bucket(MinIOHelper.XVOID_USER_RESOURCE_BUCKET)).object(originalFilename);
                    getUrlArgsBuilder.method(Method.GET);
                    GetPresignedObjectUrlArgs presignedObjectUrlArgs = getUrlArgsBuilder.build();
                    String presignedObjectUrl;
                    try {
                        presignedObjectUrl = client.getPresignedObjectUrl(presignedObjectUrlArgs);
                        systemResource.setPath(presignedObjectUrl);
                        localFile.delete();
                    } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                            InvalidResponseException | NoSuchAlgorithmException | XmlParserException | ServerException e) {
                        e.printStackTrace();
                        throw new ContinueTransactionException("[%s]上传文件获取MINIO回调链接异常,请查看日志解决问题.".formatted(PkgInfo.MODULE_NAME));
                    }
                }
            }
            boolean save = this.save(systemResource);
            if (save) {
                ProxyResult proxyResult = new ProxyResult(ResultCode.UPLOAD_SUCCESS);
                JSONObject resourceInfo = new JSONObject(2);
                resourceInfo.put("filename",systemResource.getFileName()+"."+systemResource.getSuffix());
                resourceInfo.put("url",systemResource.getPath());
                proxyResult.setData(resourceInfo);
                return proxyResult;
            }
        }else {

        }
        return null;
    }
}
