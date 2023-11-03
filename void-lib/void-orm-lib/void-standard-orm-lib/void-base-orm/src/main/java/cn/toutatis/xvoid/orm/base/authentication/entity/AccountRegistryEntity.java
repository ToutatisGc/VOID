package cn.toutatis.xvoid.orm.base.authentication.entity;


import cn.toutatis.xvoid.toolkit.validator.Validator;
import lombok.Data;

/**
 * 账户注册方式额外字段
 * @author Toutatis_Gc
 */
@Data
public class AccountRegistryEntity {

    private String secret;

    private String confirmSecret;

    public Boolean secretFilled(){
        return Validator.stringsNotNull(secret,confirmSecret);
    }

    public Boolean secretIsMatch(){
        return secret.equals(confirmSecret);
    }

}
