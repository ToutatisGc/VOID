package cn.toutatis.xvoid.orm.base.authentication.entity;


import cn.toutatis.xvoid.toolkit.validator.Validator;
import lombok.Data;

/**
 * 账户注册方式额外字段
 * @author Toutatis_Gc
 */
@Data
public class AccountRegistryEntity {

    /**
     * 密码
     */
    private String secret;

    /**
     * 二次确认密码
     */
    private String confirmSecret;

    /**
     * 校验密码和确认密码是否填充
     * @return 是否不为空
     */
    public Boolean secretFilled(){
        return Validator.stringsNotNull(secret,confirmSecret);
    }

    /**
     * 校验两个密码是否一致
     * @return 密码是否一致
     */
    public Boolean secretIsEquals(){
        return secret.equals(confirmSecret);
    }

    /**
     * 校验密码是否符合正则
     * @return 密码是否符合正则要求
     */
    public Boolean secretMatchFormat(){
        return Validator.checkUsefulPasswordFormat(secret);
    }

}
