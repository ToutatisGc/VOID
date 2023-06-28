package cn.toutatis.xvoid.support.spring.core.file.service.impl;


import cn.toutatis.xvoid.common.enums.FileFields;
import cn.toutatis.xvoid.common.exception.ContinueTransactionException;
import cn.toutatis.xvoid.data.common.base.SystemResource;
import cn.toutatis.xvoid.data.common.result.ProxyResult;
import cn.toutatis.xvoid.data.common.result.Result;
import cn.toutatis.xvoid.data.common.result.ResultCode;
import cn.toutatis.xvoid.data.common.result.SimpleResultMessage;
import cn.toutatis.xvoid.support.VoidModuleInfo;
import cn.toutatis.xvoid.support.spring.config.ObjectStorageMode;
import cn.toutatis.xvoid.support.spring.config.VoidConfiguration;
import cn.toutatis.xvoid.support.spring.core.file.MinIOHelper;
import cn.toutatis.xvoid.support.spring.core.file.persistence.SystemResourceMapper;
import cn.toutatis.xvoid.support.spring.core.file.service.SystemResourceService;
import cn.toutatis.xvoid.support.spring.config.orm.mybatisplus.support.VoidMybatisServiceImpl;
import cn.toutatis.xvoid.toolkit.file.FileToolkit;
import cn.toutatis.xvoid.toolkit.file.image.CompressConfig;
import cn.toutatis.xvoid.toolkit.file.image.ImageCompressToolKit;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import cn.toutatis.xvoid.toolkit.file.image.PictureQualityDistributionStrategy;
import cn.toutatis.xvoid.toolkit.validator.Validator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.val;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.*;

/**
 *
 * <p>系统资源类 服务实现类
 * <p>Web服务的文件上传下载皆在于此
 * <p>如果没有特殊需求,请使用此类
 * <p><i>如有特殊需求,在此服务类编写,以防命名和行为的不一致导致的追踪困难</i>
 *
 *
 * @author Toutatis_Gc
 * @since 2023-06-03
*/
@Service("systemResourceService")
public class SystemResourceServiceImpl extends VoidMybatisServiceImpl<SystemResourceMapper, SystemResource> implements SystemResourceService {

    private Logger logger = LoggerToolkit.getLogger(SystemResourceServiceImpl.class);
    /**
     * 配置注入类
     */
    private final VoidConfiguration voidConfiguration;

    /**
     * MinIO文件系统
     */
    private final MinIOHelper minIOHelper;

    /**
     * 压缩图片配置文件
     */
    private CompressConfig.CompressContent compressConfig;

    public SystemResourceServiceImpl(VoidConfiguration voidConfiguration, MinIOHelper minIOHelper) {
        this.voidConfiguration = voidConfiguration;
        this.minIOHelper = minIOHelper;
    }

    @PostConstruct
    public void init(){
        // 使用局内配置文件
        compressConfig = new CompressConfig(null).getConfig();
        // 设置为源命名方式
        compressConfig.setSaveFileRenameType("SOURCE");
        compressConfig.setPictureQualityDistributionStrategy(PictureQualityDistributionStrategy.EXTREME);
        compressConfig.setZipTimes(3);
    }

    @Override
        // 文件合规校验
    public Result receiveFile(MultipartFile multipartFile, List<FileFields> fields) throws IOException {
        // 边界判断
        if (multipartFile == null || multipartFile.getSize() == 0L){
            return new ProxyResult(ResultCode.UPLOAD_FAILED, SimpleResultMessage.RESPONSE_UPLOAD_MISS_FILE);
        }
        if (Validator.strIsBlank(multipartFile.getOriginalFilename())){
            return new ProxyResult(ResultCode.UPLOAD_FAILED, SimpleResultMessage.RESPONSE_UPLOAD_MISS_NAME);
        }
        //查询数据库是否存在相同文件
        String md5Hex = DigestUtils.md5Hex(multipartFile.getBytes());
        QueryWrapper<SystemResource> md5QueryWrapper = new QueryWrapper<>();
        md5QueryWrapper.eq("hash",md5Hex);
        val hashFile = this.getOne(md5QueryWrapper);
        //md5不冲突则秒传,else合并文件
        //初始化返回信息
        Map<String,Object> returnInfo = null;
        if (fields != null && fields.size() > 1){
            returnInfo = fields.contains(FileFields.FEATURE_ORDER) ?
                    new LinkedHashMap<String, Object>(fields.size()) : new HashMap<String, Object>(fields.size());
        }
        //md5不冲突则秒传,合并文件
        SystemResource systemResource = new SystemResource();
        if (hashFile == null) {
            // hashFile为空,初始化存储对象,填入基础信息
            String originalFilename = multipartFile.getOriginalFilename();
            assert originalFilename != null;
            String fileSuffix = FileToolkit.getFileSuffix(originalFilename);
            String randomId = RandomStringUtils.random(32, true, true).toLowerCase(Locale.getDefault());
            systemResource.setOriginName(originalFilename);
            systemResource.setSuffix(fileSuffix);
            systemResource.setFileName(randomId);
            systemResource.setHash(md5Hex);
            systemResource.setSize((int) multipartFile.getSize());
            String contentType = multipartFile.getContentType();
            systemResource.setContentType(contentType);
            contentType = (contentType != null) ? contentType.split("/")[0] : "unknown";
            File localFile;
            String threadPath = FileToolkit.getThreadPath();
            VoidConfiguration.GlobalServiceConfig globalServiceConfig = voidConfiguration.getGlobalServiceConfig();
            // 存储开始,判断本地存储还是MINIO服务存储
            if (globalServiceConfig.getObjectStorageMode() == ObjectStorageMode.LOCAL) {
                String resourceDir = threadPath + FileToolkit.RESOURCE_FILE_DIR;
                boolean resourceDirExist = FileToolkit.createDirectoryOrExist(resourceDir);
                if (resourceDirExist) {
                    if(globalServiceConfig.getFileObjectClassify()) {
                        contentType = (contentType != null) ? contentType.split("/")[0] : "unknown";
                        localFile = new File("%s/%s/%s.%s".formatted(resourceDir, contentType, randomId, fileSuffix));
                        FileToolkit.createDirectoryOrExist(resourceDir + "/" + contentType);
                        systemResource.setPath("%s/%s/%s.%s".formatted(FileToolkit.RESOURCE_FILE_DIR, contentType, randomId, fileSuffix));
                    } else {
                        localFile = new File(resourceDir + "/" + randomId + "." + fileSuffix);
                        systemResource.setPath(FileToolkit.RESOURCE_FILE_DIR + "/" + randomId + "." + fileSuffix);
                    }
                    //转储文件到本地
                    multipartFile.transferTo(localFile);
                    Boolean compressFile = globalServiceConfig.getCompressFile();
                    if (compressFile){
                        // TODO 文本文件压缩和压缩文件到7z或者其他,目前只有图片
                        if (FileToolkit.isImg(fileSuffix)){
                            List<String> compressFiles = this.compressFile(localFile);
                        }
                    }
                    System.err.println(localFile.getPath());
                    // TODO 分解子目录压缩文件
                    //是否压缩
//                    if (globalServiceConfig.getCompressFile()){
//                        ArrayList<File> files = new ArrayList<>(1);
//                        files.add(localFile);
//                        String parentDir = localFile.getParent();
//                        compressConfig.setLastSaveDir(parentDir);
//                        ImageCompressToolKit.differentStandardThumbnail(files,compressConfig);
//                    }
                }
            } else {
                // MINIO存储
                String tmpDir = threadPath + FileToolkit.TEMP_FILE_DIR;
                boolean tmpDirExist = FileToolkit.createDirectoryOrExist(tmpDir);
                if (tmpDirExist) {
                    //直接将数据转储至本地文件，随后上传至文件服务器
                    localFile = new File(tmpDir + "/" + randomId + "." + fileSuffix);
                    multipartFile.transferTo(localFile);
                    MinioClient client = minIOHelper.getClient();
                    UploadObjectArgs.Builder uploadObjectBuilder = UploadObjectArgs.builder();
                    uploadObjectBuilder.bucket(minIOHelper.bucket(MinIOHelper.XVOID_USER_RESOURCE_BUCKET)).filename(localFile.getPath());
                    if(globalServiceConfig.getFileObjectClassify()) {
                        contentType = (contentType != null) ? contentType.split("/")[0] : "unknown";
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
                        String message = "[%s]上传文件[%s]转储MINIO服务异常,请查看日志解决问题.".formatted(VoidModuleInfo.MODULE_NAME, originalFilename);
                        logger.error(message);
                        throw new ContinueTransactionException(message);
                    }
                    systemResource.setPath(MinIOHelper.XVOID_USER_RESOURCE_BUCKET+"/"+contentType+"/"+localFile.getName());
                    localFile.delete();
//                    GetPresignedObjectUrlArgs.Builder getUrlArgsBuilder = GetPresignedObjectUrlArgs.builder();
//                    getUrlArgsBuilder.bucket(minIOHelper.bucket(MinIOHelper.XVOID_USER_RESOURCE_BUCKET)).object(originalFilename);
//                    getUrlArgsBuilder.method(Method.GET);
//                    GetPresignedObjectUrlArgs presignedObjectUrlArgs = getUrlArgsBuilder.build();
//                    String presignedObjectUrl;
//                    try {
//                        presignedObjectUrl = client.getPresignedObjectUrl(presignedObjectUrlArgs);
//                        systemResource.setPath(presignedObjectUrl);
//                        localFile.delete();
//                    } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
//                            InvalidResponseException | NoSuchAlgorithmException | XmlParserException | ServerException e) {
//                        e.printStackTrace();
//                        throw new ContinueTransactionException("[%s]上传文件获取MINIO回调链接异常,请查看日志解决问题.".formatted(PkgInfo.MODULE_NAME));
//                    }
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
            }else {
                return new ProxyResult(ResultCode.UPLOAD_FAILED);
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
                        throw new ContinueTransactionException("[%s]上传文件获取MINIO回调链接异常,请查看日志解决问题.".formatted(VoidModuleInfo.MODULE_NAME));
                    }
                }
            }
        }else {
            ProxyResult proxyResult = new ProxyResult(ResultCode.UPLOAD_SUCCESS);
            proxyResult.putData("filename",hashFile.getFileName()+"."+hashFile.getSuffix());
            proxyResult.putData("url",hashFile.getPath());
            return proxyResult;
        }
    }

    /**
     *
     * @param file
     * @return
     * @throws IOException
     */
    private List<String> compressFile(File file) throws IOException {
        ArrayList<File> files = new ArrayList<>(1);
        files.add(file);
        String parentDir = file.getParent();
        compressConfig.setLastSaveDir(parentDir);
        return ImageCompressToolKit.differentStandardThumbnail(files,compressConfig);
    }

    @Override
    public Object downloadFile(String path, ObjectStorageMode objectStorageMode) throws IOException {
        return null;
    }

    @Override
    public Map<String, Object> metaData() {
        return null;
    }

    /**
     * 放置文件信息
     * @param infoMap 信息映射
     * @param key map的Key
     * @param value 文件的值
     * @see cn.toutatis.xvoid.common.enums.FileFields
     */
    private void putInfoField(Map<String,Object> infoMap,FileFields key,Object value){
        if (infoMap !=null){
            infoMap.put(key.name(),value);
        }
    }
}
