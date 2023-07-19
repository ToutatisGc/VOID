package cn.toutatis.xvoid.orm.forum.entity.intermediate;

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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serial;

/**
 * 文章集合和文章的中间表
 * 对应 N:M 的关系
 * 一个文章合集下有多个文章,多个文章合计下也可以有多个相同文章
 * @see cn.toutatis.xvoid.orm.forum.entity.ForumArticleCategory 文章合集
 * @see cn.toutatis.xvoid.orm.forum.entity.ForumArticle 文章
 * @author Toutatis_Gc
 */
@Getter @Setter
@ToString(callSuper = true)
@JsonIgnoreProperties({"reservedString","reservedInt"})
@ApiModel(value="论坛文章实体类",description="可浏览的文章正文",parent = EntityBasicAttribute.class)
@TableName("vb_forum_category_article_intermediate")
@Entity @Table(name = "vb_forum_category_article_intermediate")
@org.hibernate.annotations.Table(appliesTo = "vb_forum_category_article_intermediate", comment = "论坛文章合集&文章中间表")
public class ForumCategoryArticleIntermediate extends EntityBasicAttribute<ForumCategoryArticleIntermediate>{

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID",required = true)
    @Id @TableId(value = "id",type = IdType.AUTO)
    @Column(name="id",columnDefinition = "INT AUTO_INCREMENT COMMENT '主键ID'")
    private Integer id;

    @ApiModelProperty(value = "文章集合ID",notes = "关联的文章集合ID")
    @TableField("categoryId")
    @Column(name="categoryId",nullable = false,columnDefinition = "INT COMMENT '文章集合ID'")
    private Integer categoryId;

    @ApiModelProperty(value = "文章ID",notes = "关联的文章ID")
    @TableField("articleId")
    @Column(name="articleId",nullable = false,columnDefinition = "INT COMMENT '文章ID'")
    private Integer articleId;



}
