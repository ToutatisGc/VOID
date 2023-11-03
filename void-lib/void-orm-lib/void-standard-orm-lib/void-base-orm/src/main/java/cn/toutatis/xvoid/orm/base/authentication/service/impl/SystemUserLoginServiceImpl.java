package cn.toutatis.xvoid.orm.base.authentication.service.impl;

import cn.toutatis.xvoid.orm.base.authentication.entity.SystemUserLogin;
import cn.toutatis.xvoid.orm.base.authentication.enums.RegistryType;
import cn.toutatis.xvoid.orm.base.authentication.persistence.SystemUserLoginMapper;
import cn.toutatis.xvoid.orm.base.authentication.service.SystemUserLoginService;
import cn.toutatis.xvoid.orm.support.mybatisplus.VoidMybatisServiceImpl;
import cn.toutatis.xvoid.toolkit.clazz.LambdaToolkit;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户类 服务实现类
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-07-13
*/
@Service
public class SystemUserLoginServiceImpl extends VoidMybatisServiceImpl<SystemUserLoginMapper, SystemUserLogin> implements SystemUserLoginService {

    /**
     * 此服务用来判断用户是否已在系统存在
     * @param account 账户/手机号/邮箱
     * @return 账户是否存在
     */
    @Override
    public Boolean preCheckAccountExist(String account) {
        return mapper.selectAccountExist(account) != null;
    }

    @Override
    public Boolean userInputRegistryExist(String account, RegistryType registryType) {
        LambdaQueryWrapper<SystemUserLogin> userWrapper = Wrappers.lambdaQuery();
        switch (registryType){
            case ACCOUNT -> userWrapper.eq(SystemUserLogin::getAccount,account);
            case EMAIL -> userWrapper.eq(SystemUserLogin::getEmail,account);
            case PHONE -> userWrapper.eq(SystemUserLogin::getPhoneCode,account);
        }
        return mapper.selectCount(userWrapper) > 0;
    }

    private static final int NONCE_LENGTH = 2;

    @Override
    public String userUidGenerate(SystemUserLogin userLogin){
        QueryWrapper<SystemUserLogin> userQuery = Wrappers.query();
        String uidFieldName = LambdaToolkit.getFieldName(SystemUserLogin::getUid);
        String channelToInt = convertRegistryChannelToInt(userLogin.getRegistryType());
        userQuery.select(uidFieldName);
        String nonce = RandomStringUtils.randomNumeric(NONCE_LENGTH);
        userQuery.likeRight(uidFieldName,channelToInt+nonce);
        userQuery.orderByDesc(uidFieldName+"+0");
        userQuery.last("LIMIT 1");
        SystemUserLogin findUser = mapper.selectOne(userQuery);
        StringBuilder newUid = new StringBuilder(channelToInt);
        newUid.append(nonce);
        newUid.append("100");
        if (findUser !=  null){
            String uid = findUser.getUid();
            if (uid != null){
                String idx = uid.substring(channelToInt.length()+NONCE_LENGTH);
                long longIdx = Long.parseLong(idx)+1;
                newUid = new StringBuilder(channelToInt + nonce +longIdx);
            }
        }
        return newUid.toString();
    }

    private String convertRegistryChannelToInt(RegistryType registryType){
        int ix = 0;
        for (int i = 0; i < registryType.name().length(); i++) {
            char c = registryType.name().charAt(i);
            ix+=c;
        }
        return (ix/ 10 + ix % 10)+"";
    }
}
