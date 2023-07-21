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

import javax.persistence.*;

/**
 * @author Toutatis_Gc
 * @date 2022/11/24 16:04
 * 系统权限角色
 *
 */
@Getter @Setter @Entity @ToString(callSuper = true)
@ApiModel(value = "SystemUserRoleRelation 系统用户&角色关系类", description = "系统用户&角色关系类", parent = EntityBasicAttribute.class)
@JsonIgnoreProperties({"reservedString","reservedInt"})
@Table(
        name = "vb_system_user_role_relation"
        ,indexes = {
                @Index(name = "USER_INDEX",columnList = "userId"),
                @Index(name = "ROLE_INDEX",columnList = "roleId"),
        }
)
@org.hibernate.annotations.Table(appliesTo = "vb_system_user_role_relation", comment = "系统用户&角色关系类")
public class SystemUserRoleRelation extends EntityBasicAttribute<SystemUserRoleRelation> {

    @Id
    @TableId
    @ApiModelProperty(value="主键ID",required = true, example = "0")
    @Column(name = "id", nullable = false, columnDefinition = "INT COMMENT '主键ID'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @TableField("userId")
    @ApiModelProperty(value="用户ID",required = true, example = "administrator")
    @Column(name = "userId",nullable = false, columnDefinition = "VARCHAR(32) COMMENT '用户ID'")
    private String userId;

    @TableField("roleId")
    @ApiModelProperty(value="角色ID",required = true, example = "administrator")
    @Column(name = "roleId",nullable = false, columnDefinition = "VARCHAR(32) COMMENT '角色ID'")
    private String roleId;

}
