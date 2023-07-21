package cn.toutatis.xvoid.orm.forum.entity.intermediate;

import cn.toutatis.xvoid.BusinessType;
import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import cn.toutatis.xvoid.orm.forum.entity.ForumArticle;
import cn.toutatis.xvoid.orm.forum.entity.ForumArticleCategory;
import cn.toutatis.xvoid.orm.forum.entity.ForumArticleTags;
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

import javax.persistence.*;
import java.io.Serial;

import static cn.toutatis.xvoid.orm.forum.entity.intermediate.ForumArticleTagsIntermediate.TABLE;

/**
 * 文章和文章标签的中间表
 * 对应 1:N 的关系
 * 一个文章所附带的标签
 * @see ForumArticle 文章合集
 * @see ForumArticleTags 文章标签
 * @author Toutatis_Gc
 */
@Getter @Setter @ToString(callSuper = true)
@JsonIgnoreProperties({"reservedString","reservedInt"})
@ApiModel(value="[论坛业务]文章&标签中间表",description="一篇文章有多个标签",parent = EntityBasicAttribute.class)
@TableName(TABLE) @Entity @Table(name = TABLE)
@org.hibernate.annotations.Table(appliesTo = TABLE, comment = "论坛文章&文章标签中间表")
public class ForumArticleTagsIntermediate extends EntityBasicAttribute<ForumArticleTagsIntermediate>{

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 数据库表名以及业务类型
     */
    public static final String TABLE = "vb_forum_article_tags_intermediate";
    {this.setBusinessType(BusinessType.XVOID_FORUM);}

    @ApiModelProperty(value = "主键ID",required = true)
    @Id @TableId(value = "id",type = IdType.AUTO)
    @Column(name="id",columnDefinition = "INT AUTO_INCREMENT COMMENT '主键ID'")
    private Integer id;

    @ApiModelProperty(value = "文章ID",notes = "关联的文章ID")
    @TableField("articleId")
    @Column(name="articleId",nullable = false,columnDefinition = "INT COMMENT '文章ID'")
    private Integer articleId;

    @ApiModelProperty(value = "文章ID",notes = "关联的文章ID")
    @TableField("tagId")
    @Column(name="tagId",nullable = false,columnDefinition = "INT COMMENT '文章标签ID'")
    private Integer tagId;

    @ManyToOne
    @JoinColumn(name = "articleId",insertable = false,updatable = false)
    private ForumArticle article;

    @ManyToOne
    @JoinColumn(name = "tagId",insertable = false,updatable = false)
    private ForumArticleTags tag;

}
