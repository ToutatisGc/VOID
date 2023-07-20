package cn.toutatis.xvoid.orm.support.mybatisplus;

import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import cn.toutatis.xvoid.orm.support.VoidService;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 * @author Toutatis_Gc
 */
public interface VoidMybatisService<T extends EntityBasicAttribute<T>> extends VoidService<T>,IService<T> {

}
