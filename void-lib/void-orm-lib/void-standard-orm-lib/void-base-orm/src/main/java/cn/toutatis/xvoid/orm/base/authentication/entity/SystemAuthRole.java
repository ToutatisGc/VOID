package cn.toutatis.xvoid.orm.base.authentication.entity;

import cn.toutatis.xvoid.BusinessType;
import cn.toutatis.xvoid.orm.base.authentication.entity.intermediate.SystemRolePathIntermediate;
import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serial;
import java.util.List;

import static cn.toutatis.xvoid.orm.base.authentication.entity.SystemAuthRole.TABLE;

/**
 * @author Toutatis_Gc
 * @date 2022/11/24 16:04
 * 系统权限角色
 *
 */
@Getter @Setter @Entity @ToString(callSuper = true)
@JsonIgnoreProperties({"reservedString","reservedInt"})
@ApiModel(value = "SystemAuthRole 系统角色类", description = "系统角色类", parent = EntityBasicAttribute.class)
@Table(name = TABLE) @org.hibernate.annotations.Table(appliesTo = TABLE, comment = "系统角色类")
@Where(clause = EntityBasicAttribute.DEFAULT_WHERE_CLAUSE)
public class SystemAuthRole extends EntityBasicAttribute<SystemAuthRole>{

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 数据库表名以及业务类型
     */
    public static final String TABLE = "vb_system_auth_role";
    {this.setBusinessType(BusinessType.XVOID_SYSTEM);}

    @Id
    @TableId
    @Schema(name="主键ID",requiredMode = Schema.RequiredMode.REQUIRED, example = "0b01f8466bcf11eda9c1b827eb90cfbc")
    @Column(name="id",columnDefinition = "VARCHAR(32) COMMENT '主键ID'")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    private String id;

    @TableField("displayName")
    @Schema(name="显示名",required = true, example = "管理员")
    @Column(name = "displayName", columnDefinition = "VARCHAR(32) COMMENT '显示名'")
    private String displayName;

    @TableField("roleName")
    @Schema(name="角色名",required = true, example = "XVOID_ADMIN")
    @Column(name = "roleName", columnDefinition = "VARCHAR(32) COMMENT '角色名'")
    private String roleName;

    @ToString.Exclude
    @TableField(exist = false)
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinTable(
            name = SystemRolePathIntermediate.TABLE,
            joinColumns = @JoinColumn(name = "roleId"),
            inverseJoinColumns = @JoinColumn(name = "authId")
    )
    private List<SystemAuthPath> authPaths;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SystemAuthRole that = (SystemAuthRole) o;

        return new EqualsBuilder().append(id, that.id).append(displayName, that.displayName).append(roleName, that.roleName).append(authPaths, that.authPaths).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(displayName).append(roleName).append(authPaths).toHashCode();
    }
}
