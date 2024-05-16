package cn.toutatis.xvoid.orm.forum.entity;

import cn.toutatis.xvoid.BusinessType;
import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serial;

import static cn.toutatis.xvoid.orm.forum.entity.ForumArticleTags.TABLE;

@Getter @Setter @ToString(callSuper = true)
@JsonIgnoreProperties({"reservedString","reservedInt","createTimeMs","lastUpdateTimeMs"})
@ApiModel(value="论坛文章标签类",description="文章所拥有的标签",parent = EntityBasicAttribute.class)
@TableName(TABLE) @Entity
@Table(name = TABLE) @org.hibernate.annotations.Table(appliesTo = TABLE, comment = "论坛文章标签类")
public class ForumArticleTags extends EntityBasicAttribute<ForumArticleTags> {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 数据库表名以及业务类型
     */
    public static final String TABLE = "vb_forum_article_tags";
    {this.setBusinessType(BusinessType.XVOID_FORUM);}

    @ApiModelProperty(value = "主键ID",required = true)
    @Id @TableId(value = "id",type = IdType.AUTO)
    @Column(name="id",columnDefinition = "INT AUTO_INCREMENT COMMENT '主键ID'")
    private Integer id;

    @ApiModelProperty(value = "父ID")
    @TableField("parentId")
    @Column(name="parentId",nullable = false,columnDefinition = "INT DEFAULT 0 COMMENT '父ID'")
    private Integer parentId = 0;

    @ApiModelProperty(value = "层级")
    @TableField("level")
    @Column(name="level",nullable = false,columnDefinition = "INT DEFAULT 0 COMMENT '层级'")
    private Integer level = 0;

    @ApiModelProperty(value = "标签名称")
    @TableField("name")
    @Column(name="name",nullable = false,columnDefinition = "VARCHAR(32) COMMENT '标签名称'")
    private String name;

    @ApiModelProperty(value = "标签描述")
    @TableField("description")
    @Column(name="description",columnDefinition = "VARCHAR(128) COMMENT '标签描述'")
    private String description;

    @ApiModelProperty(value = "颜色[推荐使用16进制]")
    @TableField("color")
    @Column(name="color",columnDefinition = "VARCHAR(32) COMMENT '颜色[推荐使用16进制]'")
    private String color;

    @ApiModelProperty(value = "标签优先级")
    @TableField("`order`")
    @Column(name="`order`",nullable = false,columnDefinition = "INT DEFAULT 100 COMMENT '标签优先级'")
    private Integer order = 100;

}
