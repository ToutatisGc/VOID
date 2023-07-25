package cn.toutatis.xvoid.orm.base.infrastructure.entity;

import cn.toutatis.xvoid.BusinessType;
import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serial;

import static cn.toutatis.xvoid.orm.base.infrastructure.entity.SystemDictionaries.*;


/**
 * [系统业务] 字典类
 * 可浏览的文章正文
 * @author Toutatis_Gc
 */
@Getter
@Setter
@ToString(callSuper = true)
@JsonIgnoreProperties({"reservedString","reservedInt","createTimeMs","lastUpdateTimeMs"})
@ApiModel(value="[系统业务] 字典类",description="键值字典",parent = EntityBasicAttribute.class)
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
    @ApiModelProperty(value="主键ID",required = true, example = "0")
    @Column(name="id",columnDefinition = "INT COMMENT '主键ID'")
    private Integer id;

    @TableField("parentId")
    @ApiModelProperty(value="父ID",required = true, example = "0")
    @Column(name="parentId",columnDefinition = "INT COMMENT '父ID'")
    private Integer parentId;

    @TableField("key")
    @ApiModelProperty(value="键(英文)",required = true, example = "SYS_STATUS_0000")
    @Column(name="`key`",unique = true,columnDefinition = "VARCHAR(32) COMMENT '键(英文)'")
    private String key;

    @TableField("`value`")
    @ApiModelProperty(value="值",required = true, example = "数据正常")
    @Column(name="`value`",columnDefinition = "VARCHAR(32) COMMENT '值'")
    private String value;

    @TableField("weight")
    @ApiModelProperty(value="权重(影响排序)",required = false, example = "50")
    @Column(name="weight",columnDefinition = "INT DEFAULT 50 COMMENT '权重(影响排序)'")
    private Integer weight;

    @TableField("classify")
    @ApiModelProperty(value="数据分类",required = true, example = "系统数据")
    @Column(name="classify",columnDefinition = "VARCHAR(32) COMMENT '数据分类'")
    private String classify;

}
