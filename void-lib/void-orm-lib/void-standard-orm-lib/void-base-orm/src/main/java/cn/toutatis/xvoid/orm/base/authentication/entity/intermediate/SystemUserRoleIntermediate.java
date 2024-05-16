package cn.toutatis.xvoid.orm.base.authentication.entity.intermediate;

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

import jakarta.persistence.*;
import java.io.Serial;

import static cn.toutatis.xvoid.orm.base.authentication.entity.intermediate.SystemUserRoleIntermediate.TABLE;

/**
 * @author Toutatis_Gc
 * @date 2022/11/24 16:04
 * 系统权限角色
 *
 */
@Getter @Setter @Entity @ToString(callSuper = true)
@ApiModel(value = "SystemUserRoleIntermediate 系统用户&角色关系类", description = "系统用户&角色关系类", parent = EntityBasicAttribute.class)
@JsonIgnoreProperties({"reservedString","reservedInt"})
@Table(
        name = TABLE,
        indexes = {
                @Index(name = "USER_INDEX",columnList = "userId"),
                @Index(name = "ROLE_INDEX",columnList = "roleId")
        }
)
@org.hibernate.annotations.Table(appliesTo = TABLE, comment = "系统用户&角色关系类")
public class SystemUserRoleIntermediate extends EntityBasicAttribute<SystemUserRoleIntermediate> {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 数据库表名以及业务类型
     */
    public static final String TABLE = "vb_system_user_role_intermediate";
    {this.setBusinessType(BusinessType.XVOID_SYSTEM);}

    @Id
    @TableId
    @Schema(name="主键ID",required = true, example = "0")
    @Column(name = "id", nullable = false, columnDefinition = "INT COMMENT '主键ID'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @TableField("userId")
    @Schema(name="用户ID",required = true, example = "administrator")
    @Column(name = "userId",nullable = false, columnDefinition = "VARCHAR(32) COMMENT '用户ID'")
    private String userId;

    @TableField("roleId")
    @Schema(name="角色ID",required = true, example = "administrator")
    @Column(name = "roleId",nullable = false, columnDefinition = "VARCHAR(32) COMMENT '角色ID'")
    private String roleId;

}
