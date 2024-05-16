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

import static cn.toutatis.xvoid.orm.base.authentication.entity.intermediate.SystemRolePathIntermediate.TABLE;

/**
 * @author Toutatis_Gc
 * @date 2022/11/24 16:04
 * 系统权限角色
 *
 */
@Getter @Setter @Entity @ToString(callSuper = true)
@JsonIgnoreProperties({"reservedString","reservedInt"})
@ApiModel(value = "SystemRolePathIntermediate 系统角色&权限关系类", description = "系统角色&权限关系类", parent = EntityBasicAttribute.class)
@Table(
        name = TABLE,
        indexes = {
                @Index(name = "ROLE_INDEX",columnList = "roleId"),
                @Index(name = "AUTH_INDEX",columnList = "authId")
        }
)
@org.hibernate.annotations.Table(appliesTo = TABLE, comment = "系统角色&权限关系类")
public class SystemRolePathIntermediate extends EntityBasicAttribute<SystemRolePathIntermediate>{

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 数据库表名以及业务类型
     */
    public static final String TABLE = "vb_system_role_path_intermediate";
    {this.setBusinessType(BusinessType.XVOID_SYSTEM);}

    @Id
    @TableId
    @Schema(name="主键ID",required = true, example = "0")
    @Column(name="id",columnDefinition = "INT COMMENT '主键ID'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @TableField("roleId")
    @Schema(name="角色ID",required = true, example = "administrator")
    @Column(name = "roleId",nullable = false, columnDefinition = "VARCHAR(32) COMMENT '角色ID'")
    private String roleId;

    @TableField("authId")
    @Schema(name="权限ID",required = true)
    @Column(name = "authId",nullable = false, columnDefinition = "VARCHAR(32) COMMENT '权限ID'")
    private String authId;


}
