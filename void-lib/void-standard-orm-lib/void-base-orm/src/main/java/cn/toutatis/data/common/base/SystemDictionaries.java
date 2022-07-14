package cn.toutatis.data.common.base;

import cn.toutatis.data.common.EntityBasicAttribute;
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
 */
@Getter
@Setter
@ToString(callSuper = true)
@ApiModel(
        value = "SystemDictionaries 系统字典实体类",
        description = "系统字典实体类",
        parent = EntityBasicAttribute.class)
@Table(name="vb_system_dictionaries")
@Entity
@JsonIgnoreProperties({"reservedString","reservedInt"})
@org.hibernate.annotations.Table(appliesTo = "vb_system_dictionaries", comment = "系统用户类")
public class SystemDictionaries extends EntityBasicAttribute<SystemDictionaries> {

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
