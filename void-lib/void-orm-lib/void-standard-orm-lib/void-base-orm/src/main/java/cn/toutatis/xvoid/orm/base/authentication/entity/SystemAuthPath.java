package cn.toutatis.xvoid.orm.base.authentication.entity;

import cn.toutatis.xvoid.BusinessType;
import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
import java.util.Objects;

/**
 * @author Toutatis_Gc
 */
@Getter @Setter @Entity @ToString(callSuper = true)
@JsonIgnoreProperties({"reservedString","reservedInt"})
@ApiModel(value = "SystemAuth 系统权限类", description = "系统权限类", parent = EntityBasicAttribute.class)
@Table(name = "vb_system_auth_path") @org.hibernate.annotations.Table(appliesTo = "vb_system_auth_path", comment = "系统权限类")
@Where(clause = EntityBasicAttribute.DEFAULT_WHERE_CLAUSE)
public class SystemAuthPath extends EntityBasicAttribute<SystemAuthPath> {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 数据库表名以及业务类型
     */
    public static final String TABLE = "";
    {this.setBusinessType(BusinessType.XVOID_SYSTEM);}

    @Id @TableId
    @Schema(name="主键ID",required = true, example = "0b01f8466bcf11eda9c1b827eb90cfbc")
    @Column(name="id",columnDefinition = "VARCHAR(32) COMMENT '主键ID'")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    private String id;

    @TableField("name")
    @Schema(name="权限名称",required = false, example = "首页")
    @Column(name = "name", columnDefinition = "VARCHAR(64) COMMENT '权限名称'")
    private String name;

    @TableField("path")
    @Schema(name="路径",required = false, example = "/auth/**")
    @Column(name = "path", columnDefinition = "VARCHAR(64) COMMENT '路径[支持ant通配符]'")
    private String path;

    @TableField("export")
    @Schema(name="是否暴露",required = false, example = "true")
    @Column(name = "export", columnDefinition = "BOOLEAN DEFAULT TRUE COMMENT '是否暴露'")
    private Boolean export;

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) { return false; }
        SystemAuthPath that = (SystemAuthPath) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
