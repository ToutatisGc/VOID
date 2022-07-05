package cn.toutatis.data.common.security;

import cn.toutatis.data.common.EntityBasicAttribute;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author Toutatis_Gc
 */
@Data
@ToString(callSuper = true)
@Entity
@ApiModel(
        value = "SystemUserLogin 系统用户类",
        description = "系统用户实体类",
        parent = EntityBasicAttribute.class)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"reservedString","reservedInt"})
@Table(name="vb_system_user_login")
@org.hibernate.annotations.Table(appliesTo = "vb_system_user_login", comment = "系统用户类")
public class SystemUserLogin extends EntityBasicAttribute<SystemUserLogin> {

    @Id @TableId
    @ApiModelProperty(
            value="主键ID",required = true,
            example = "1111222233334444")
    @Column(name="id",columnDefinition = "VARCHAR(32) COMMENT '主键ID'")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    private String id;

    @TableField("`account`")
    @ApiModelProperty(
            value="账号",required = true,
            example = "admin")
    @Column(name="account",columnDefinition = "VARCHAR(32) COMMENT '账号'")
    private String account;

    @TableField("username")
    @ApiModelProperty(
            value="用户名",required = true,
            example = "Toutatis_Gc")
    @Column(name="username",columnDefinition = "VARCHAR(32) COMMENT '用户名'")
    private String username;

    @ApiModelProperty(
            value="电话号码",required = false,
            example = "19902351234")
    @Column(name="phoneCode",columnDefinition = "VARCHAR(11) COMMENT '电话号码'")
    private String phoneCode;

    @TableField("openId")
    @ApiModelProperty(
            value="微信公众平台ID",required = false,
            example = "微信公众平台ID")
    @Column(name="openId",columnDefinition = "VARCHAR(64) COMMENT '微信公众平台openID'")
    private String openId;

    @TableField("email")
    @ApiModelProperty(
            value="邮箱",required = false,
            example = "gc@toutatis.cn")
    @Column(name="email",columnDefinition = "VARCHAR(64) COMMENT '邮箱'")
    private String email;

    @TableField("secret")
    @ApiModelProperty(
            value="登录密文",required = true,
            example = "******")
    @Column(name="secret",columnDefinition = "VARCHAR(255) COMMENT '登录密文'")
    private String secret;

}
