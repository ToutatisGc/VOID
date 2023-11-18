package cn.toutatis.xvoid.orm.base.authentication.entity;

import cn.toutatis.xvoid.BusinessType;
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
import java.io.Serial;

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
    @ApiModelProperty(value="主键ID",required = true, example = "0b01f8466bcf11eda9c1b827eb90cfbc")
    @Column(name="id",columnDefinition = "VARCHAR(32) COMMENT '主键ID'")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    private String id;

    @TableField("displayName")
    @ApiModelProperty(value="显示名",required = true, example = "管理员")
    @Column(name = "displayName", columnDefinition = "VARCHAR(32) COMMENT '显示名'")
    private String displayName;

    @TableField("roleName")
    @ApiModelProperty(value="角色名",required = true, example = "XVOID_ADMIN")
    @Column(name = "roleName", columnDefinition = "VARCHAR(32) COMMENT '角色名'")
    private String roleName;



}
