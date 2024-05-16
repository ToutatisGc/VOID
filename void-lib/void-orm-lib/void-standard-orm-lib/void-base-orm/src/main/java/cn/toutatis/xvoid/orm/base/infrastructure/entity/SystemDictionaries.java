package cn.toutatis.xvoid.orm.base.infrastructure.entity;

import cn.toutatis.xvoid.BusinessType;
import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.io.Serial;

import static cn.toutatis.xvoid.orm.base.infrastructure.entity.SystemDictionaries.*;


/**
 * [系统业务] 字典类
 * 可浏览的文章正文
 * @author Toutatis_Gc
 */
@Getter @Setter @ToString(callSuper = true)
@JsonIgnoreProperties({"reservedString","reservedInt","createTimeMs","lastUpdateTimeMs"})
@Schema(name="[系统业务] 字典类",description="键值字典")
@TableName(TABLE)
@Entity @Table(name = TABLE) @org.hibernate.annotations.Table(appliesTo = TABLE, comment = "[系统业务] 字典类")
public class SystemDictionaries extends EntityBasicAttribute<SystemDictionaries> {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 数据库表名以及业务类型
     */
    public static final String TABLE = "vb_system_dictionaries";

    {this.setBusinessType(BusinessType.XVOID_SYSTEM);}

    @Id @TableId
    @Schema(name="主键ID",requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @Column(name="id",columnDefinition = "INT COMMENT '主键ID'")
    private Integer id;

    @TableField("parentId")
    @Schema(name="父ID",requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @Column(name="parentId",columnDefinition = "INT COMMENT '父ID'")
    private Integer parentId;

    @TableField("key")
    @Schema(name="键(英文)",requiredMode = Schema.RequiredMode.REQUIRED, example = "SYS_STATUS_0000")
    @Column(name="`key`",unique = true,nullable = false,columnDefinition = "VARCHAR(32) COMMENT '键(英文)'")
    private String key;

    @TableField("`value`")
    @Schema(name="值",requiredMode = Schema.RequiredMode.REQUIRED, example = "数据正常")
    @Column(name="`value`",nullable = false,columnDefinition = "VARCHAR(32) COMMENT '值'")
    private String value;

    @TableField("weight")
    @Schema(name="权重(影响排序)",requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "50")
    @Column(name="weight",columnDefinition = "INT DEFAULT 50 COMMENT '权重(影响排序)'")
    private Integer weight;

    @TableField("classify")
    @Schema(name="数据分类",requiredMode = Schema.RequiredMode.REQUIRED, example = "系统数据")
    @Column(name="classify",nullable = false,columnDefinition = "VARCHAR(32) COMMENT '数据分类'")
    private String classify;

}
