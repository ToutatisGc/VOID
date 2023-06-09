package cn.toutatis.xvoid.support.spring.core.file.service;

import cn.toutatis.xvoid.data.common.base.SystemResource;
import cn.toutatis.xvoid.data.common.result.Result;
import cn.toutatis.xvoid.support.spring.config.orm.mybatisplus.support.VoidMybatisService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
     * @param multipartFile 用户上传文件
     * @return 上传结果
     * 将用户上传的文件保存至本地
     */
    Object receiveFile(MultipartFile multipartFile) throws IOException;

}
