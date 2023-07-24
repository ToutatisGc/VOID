package cn.toutatis.xvoid.orm.base.authentication.entity;

import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author Toutatis_Gc
 * @date 2022/11/24 16:04
 * 系统权限角色
 *
 */
@Getter @Setter @Entity @ToString(callSuper = true)
@JsonIgnoreProperties({"reservedString","reservedInt"})
@ApiModel(value = "SystemAuthRole 系统角色类", description = "系统角色类", parent = EntityBasicAttribute.class)
@Table(name = "vb_system_auth_role") @org.hibernate.annotations.Table(appliesTo = "vb_system_auth_role", comment = "系统角色类")
public class SystemAuthRole extends EntityBasicAttribute<SystemAuthRole>{

    @Id
    @TableId
    @ApiModelProperty(value="主键ID",required = true, example = "0b01f8466bcf11eda9c1b827eb90cfbc")
    @Column(name="id",columnDefinition = "VARCHAR(32) COMMENT '主键ID'")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    private String id;

    @TableField("roleName")
    @ApiModelProperty(value="角色名",required = false, example = "administrator")
    @Column(name = "roleName", columnDefinition = "VARCHAR(32) COMMENT '角色名'")
    private String roleName;

}
