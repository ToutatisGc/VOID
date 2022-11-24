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

import javax.persistence.*;

/**
 * @author Toutatis_Gc
 * @date 2022/11/24 16:04
 * 系统权限角色
 *
 */
@Getter @Setter @Entity @ToString(callSuper = true)
@JsonIgnoreProperties({"reservedString","reservedInt"})
@ApiModel(value = "SystemRolePathRelation 系统角色&权限关系类", description = "系统角色&权限关系类", parent = EntityBasicAttribute.class)
@Table(
        name = "vb_system_role_path_relation"
        ,indexes = {
                @Index(name = "ROLE_INDEX",columnList = "roleId"),
                @Index(name = "AUTH_INDEX",columnList = "authId")
        }
)
@org.hibernate.annotations.Table(appliesTo = "vb_system_role_path_relation", comment = "系统角色&权限关系类")
public class SystemRolePathRelation extends EntityBasicAttribute<SystemRolePathRelation>{
    
    @Id
    @TableId
    @ApiModelProperty(value="主键ID",required = true, example = "0")
    @Column(name="id",columnDefinition = "INT COMMENT '主键ID'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @TableField("roleId")
    @ApiModelProperty(value="角色ID",required = true, example = "administrator")
    @Column(name = "roleId",nullable = false, columnDefinition = "VARCHAR(32) COMMENT '角色ID'")
    private String roleId;

    @TableField("authId")
    @ApiModelProperty(value="权限ID",required = true)
    @Column(name = "authId",nullable = false, columnDefinition = "VARCHAR(32) COMMENT '权限ID'")
    private String authId;


}
