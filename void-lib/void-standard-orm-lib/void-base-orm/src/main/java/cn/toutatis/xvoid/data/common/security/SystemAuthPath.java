package cn.toutatis.xvoid.data.common.security;

import cn.toutatis.xvoid.data.common.EntityBasicAttribute;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Toutatis_Gc
 */
@Getter @Setter @Entity @ToString(callSuper = true)
@JsonIgnoreProperties({"reservedString","reservedInt"})
@ApiModel(value = "SystemAuth 系统权限类", description = "系统权限类", parent = EntityBasicAttribute.class)
@Table(name = "vb_system_auth_path") @org.hibernate.annotations.Table(appliesTo = "vb_system_auth_path", comment = "系统权限类")
public class SystemAuthPath extends EntityBasicAttribute<SystemAuthPath> {

    @Id @TableId
    @ApiModelProperty(value="主键ID",required = true, example = "0b01f8466bcf11eda9c1b827eb90cfbc")
    @Column(name="id",columnDefinition = "VARCHAR(32) COMMENT '主键ID'")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    private String id;

    @TableField("path")
    @ApiModelProperty(value="路径",required = false, example = "/auth/**")
    @Column(name = "path", columnDefinition = "VARCHAR(64) COMMENT '路径[支持ant通配符]'")
    private String path;

    @TableField("export")
    @ApiModelProperty(value="是否暴露",required = false, example = "true")
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
