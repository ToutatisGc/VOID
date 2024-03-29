package cn.toutatis.xvoid.orm.base.authentication.entity;

import cn.toutatis.xvoid.BusinessType;
import cn.toutatis.xvoid.common.enums.RegistryType;
import cn.toutatis.xvoid.orm.base.authentication.entity.intermediate.SystemUserDirectAuthIntermediate;
import cn.toutatis.xvoid.orm.base.authentication.entity.intermediate.SystemUserRoleIntermediate;
import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import cn.toutatis.xvoid.toolkit.constant.Time;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serial;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static cn.toutatis.xvoid.orm.base.authentication.entity.SystemUserLogin.TABLE;

/**
 * @author Toutatis_Gc
 */
@Getter @Setter @Entity @ToString(callSuper = true)
@JsonIgnoreProperties({"reservedString","reservedInt"})
@ApiModel(value = "SystemUserLogin 系统用户类", description = "系统用户实体类", parent = EntityBasicAttribute.class)
@Table(name=TABLE,
        indexes = {
                @Index(name = "ACCOUNT_INDEX",columnList = "account",unique = true),
                @Index(name = "USERNAME_INDEX",columnList = "username",unique = true),
                @Index(name = "PHONE_INDEX",columnList = "phoneCode",unique = true),
                @Index(name = "EMAIL_INDEX",columnList = "email",unique = true)
        }
)
@org.hibernate.annotations.Table(appliesTo = TABLE, comment = "系统用户类")
@Where(clause = EntityBasicAttribute.DEFAULT_WHERE_CLAUSE)
public class SystemUserLogin extends EntityBasicAttribute<SystemUserLogin> {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 数据库表名以及业务类型
     */
    public static final String TABLE = "vb_system_user_login";
    {this.setBusinessType(BusinessType.XVOID_SYSTEM);}

    @Id @TableId
    @Schema(name="主键ID",required = true, example = "0b01f8466bcf11eda9c1b827eb90cfbc")
    @Column(name="id",columnDefinition = "VARCHAR(32) COMMENT '主键ID'")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    private String id;

    @TableField("`account`")
    @Schema(name="账号",required = true, example = "admin")
    @Column(name="account",unique = true,columnDefinition = "VARCHAR(32) COMMENT '账号'")
    private String account;

    @TableField("uid")
    @Schema(name="用户短标识[由规则生成]",required = true, example = "V10000")
    @Column(name="uid",unique = true,columnDefinition = "VARCHAR(32) COMMENT '用户短标识'")
    private String uid;

    @Enumerated(EnumType.STRING)
    @TableField("registryType")
    @Schema(name="注册类型",required = true, example = "ACCOUNT")
    @Column(name="registryType",columnDefinition = "VARCHAR(16) COMMENT '用户类型'")
    private RegistryType registryType;

    @TableField("username")
    @Schema(name="用户名",required = true, example = "Toutatis_Gc")
    @Column(name="username",unique = true,columnDefinition = "VARCHAR(32) COMMENT '用户名'")
    private String username;

    @TableField("phoneCode")
    @Schema(name="电话号码",required = false, example = "19902351234")
    @Column(name="phoneCode",unique = true,columnDefinition = "VARCHAR(11) COMMENT '电话号码'")
    private String phoneCode;

    @TableField("email")
    @Schema(name="邮箱",required = false, example = "gc@toutatis.cn")
    @Column(name="email",unique = true,columnDefinition = "VARCHAR(64) COMMENT '邮箱'")
    private String email;

    @TableField("secret")
    @Schema(name="登录密文",required = true, example = "******")
    @Column(name="secret",columnDefinition = "VARCHAR(255) COMMENT '登录密文'")
    private String secret;

    @JsonIgnore
    @TableField(value = "expiredTime")
    @Schema(name="过期时间", required=false)
    @Column(columnDefinition = "DATETIME COMMENT '过期时间'")
    private LocalDateTime expiredTime;

    @JsonIgnore
    @TableField(value = "allowLoginDevicesCount")
    @Schema(name="允许登录设备数量", required=false)
    @Column(nullable = false,columnDefinition = "INT DEFAULT 3 COMMENT '允许登录设备数量'")
    private Integer allowLoginDevicesCount;

    @Transient
    public String getExpiredTimeStr() {
        return expiredTime == null ? "" : expiredTime.format(DateTimeFormatter.ofPattern(Time.SIMPLE_DATE_FORMAT_REGEX));
    }

    @Transient
    public long getExpiredTimeMs() {
        return expiredTime == null ? 0L :  Timestamp.valueOf(expiredTime).getTime()/1000;
    }


    @ToString.Exclude
    @TableField(exist = false)
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(
            name = SystemUserRoleIntermediate.TABLE,
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    private List<SystemAuthRole> userRoles;


    @ToString.Exclude
    @TableField(exist = false)
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(
            name = SystemUserDirectAuthIntermediate.TABLE,
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "authId")
    )
    private List<SystemAuthPath> directAuthPaths;

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) { return false; }
        SystemUserLogin that = (SystemUserLogin) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, uid, registryType, username, phoneCode, email, secret, expiredTime, allowLoginDevicesCount);
    }
}
