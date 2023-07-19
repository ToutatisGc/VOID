package cn.toutatis.xvoid.spring.support.core.file.service;

import cn.toutatis.xvoid.common.enums.FileFields;
import cn.toutatis.xvoid.orm.base.data.common.base.SystemResource;
import cn.toutatis.xvoid.orm.base.data.common.result.Result;
import cn.toutatis.xvoid.spring.configure.system.enums.storage.ObjectStorageMode;
import cn.toutatis.xvoid.orm.support.mybatisplus.VoidMybatisService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统资源类 服务类
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2023-06-03
*/
public interface SystemResourceService extends VoidMybatisService<SystemResource> {

    /**
     * TODO 注释
     * 用户上传文件
     * @param multipartFile 用户上传文件
     * @param fields 需要Object返回的字段
     * @return 上传结果
     * 将用户上传的文件保存至本地或文件系统
     * 并根据配置文件判断是否进行分类和压缩图片文件
     */
    Result receiveFile(MultipartFile multipartFile, List<FileFields> fields) throws IOException;

    /**
     * 下载资源文件
     * @param path 文件路径
     * @param objectStorageMode 文件存储位置
     * @throws IOException 文件流异常
     * @return 文件输出流
     */
    Object downloadFile(String path, ObjectStorageMode objectStorageMode) throws IOException;

    /**
     * 获取文件信息
     * @return 文件信息
     */
    Map<String,Object> metaData();


}
