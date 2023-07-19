package cn.toutatis.xvoid.spring.support.enhance.orm.mybatisplus.support;

import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import cn.toutatis.xvoid.orm.base.data.common.result.DataStatus;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author Toutatis_Gc
 * 公用的一些查询Wrapper
 */
public class CommonWrapper<T extends EntityBasicAttribute<T>> {

    /**
     * @return 获取开放状态的Wrapper
     */
    public QueryWrapper<T> selectOpenWrapper(){
        return selectByStatusWrapper(DataStatus.SYS_OPEN_0000.getCode());
    }

    /**
     * @param status 列的状态
     * @return 根据status的Wrapper
     */
    public QueryWrapper<T> selectByStatusWrapper(String status){
        return singletonEqWrapper("status", status);
    }

    /**
     * @param column 匹配列
     * @param eqValue 匹配值
     * @return column == value的wrapper
     */
    public QueryWrapper<T> singletonEqWrapper(String column,String eqValue){
        QueryWrapper<T> selectWrapper = new QueryWrapper<>();
        selectWrapper.eq(column,eqValue);
        return selectWrapper;
    }
}
