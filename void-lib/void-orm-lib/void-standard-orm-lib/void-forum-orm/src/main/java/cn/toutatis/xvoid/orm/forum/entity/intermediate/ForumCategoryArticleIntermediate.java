package cn.toutatis.xvoid.orm.forum.entity.intermediate;

import cn.toutatis.xvoid.BusinessType;
import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import cn.toutatis.xvoid.orm.forum.entity.ForumArticle;
import cn.toutatis.xvoid.orm.forum.entity.ForumArticleCategory;
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

import static cn.toutatis.xvoid.orm.forum.entity.intermediate.ForumCategoryArticleIntermediate.TABLE;


/**
 * 文章集合和文章的中间表
 * 对应 N:M 的关系
 * 一个文章合集下有多个文章,多个文章合集下也可以有多个相同文章
 * @see cn.toutatis.xvoid.orm.forum.entity.ForumArticleCategory 文章合集
 * @see cn.toutatis.xvoid.orm.forum.entity.ForumArticle 文章
 * @author Toutatis_Gc
 */
@Getter @Setter @ToString(callSuper = true)
@JsonIgnoreProperties({"reservedString","reservedInt"})
@ApiModel(value="[论坛业务]文章集合和文章实体中间关系",description="文章集合和文章实体中间表",parent = EntityBasicAttribute.class)
@TableName(TABLE)
@Entity @Table(name = TABLE)
@org.hibernate.annotations.Table(appliesTo = TABLE, comment = "论坛文章合集&文章中间表")
public class ForumCategoryArticleIntermediate extends EntityBasicAttribute<ForumCategoryArticleIntermediate>{

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 数据库表名以及业务类型
     */
    public static final String TABLE = "vb_forum_category_article_intermediate";
    {this.setBusinessType(BusinessType.XVOID_FORUM);}

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

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private ForumArticleCategory category;

    @ManyToOne
    @JoinColumn(name = "articleId")
    private ForumArticle article;

}
