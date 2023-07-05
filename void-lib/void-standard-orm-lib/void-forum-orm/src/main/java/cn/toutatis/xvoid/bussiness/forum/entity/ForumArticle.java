package cn.toutatis.xvoid.bussiness.forum.entity;

import cn.toutatis.xvoid.bussiness.forum.enums.ArticleSource;
import cn.toutatis.xvoid.data.common.EntityBasicAttribute;
import cn.toutatis.xvoid.data.common.result.DataStatus;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serial;

/**
 * 文章实体类
 * 可浏览的文章正文
 * @author Toutatis_Gc
 */
@Getter @Setter @ToString(callSuper = true)
@JsonIgnoreProperties({"reservedString","reservedInt"})
@ApiModel(value="论坛文章实体类",description="可浏览的文章正文",parent = EntityBasicAttribute.class)
@TableName("vb_forum_resource")
@Entity @Table(name = "vb_forum_resource") @org.hibernate.annotations.Table(appliesTo = "vb_forum_resource", comment = "论坛文章类")
public class ForumArticle extends EntityBasicAttribute<ForumArticle> {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID",required = true)
    @Id @TableId(value = "id",type = IdType.AUTO)
    @Column(name="id",columnDefinition = "INT AUTO_INCREMENT COMMENT '主键ID'")
    private Integer id;

    @ApiModelProperty(value = "文章标题",required = true)
    @TableField("title")
    @Column(name="title",columnDefinition = "VARCHAR(128) COMMENT '文章标题'")
    private String title;

    @ApiModelProperty(value = "封面图链接")
    @TableField("coverImageUrl")
    @Column(name="coverImageUrl",columnDefinition = "VARCHAR(256) COMMENT '封面图链接'")
    private String coverImageUrl;

    @ApiModelProperty(value = "文章内容富文本",required = true)
    @TableField("content")
    @Column(name="content",columnDefinition = "TEXT COMMENT '文章内容富文本'")
    private String content;

    @ApiModelProperty(value = "文章点赞数",notes = "点赞数为显性的,最直接的提高排名和赞同程度")
    @TableField("likeNum")
    @Column(name="likeNum",columnDefinition = "INT DEFAULT 0 COMMENT '文章点赞数'")
    private Integer likeNum;

    @ApiModelProperty(value = "文章喜欢数",notes = "喜欢数偏向感性,内部发布和不想明示喜欢则点喜欢")
    @TableField("enjoyNum")
    @Column(name="enjoyNum",columnDefinition = "INT DEFAULT 0 COMMENT '文章喜欢数'")
    private Integer enjoyNum;

    @ApiModelProperty(value = "文章收藏数")
    @TableField("favorites")
    @Column(name="favorites",columnDefinition = "INT DEFAULT 0 COMMENT '文章收藏数'")
    private Integer favorites;

    @ApiModelProperty(value = "文章分享数",notes = "分享强调的是站内分享")
    @TableField("shareNum")
    @Column(name="shareNum",columnDefinition = "INT DEFAULT 0 COMMENT '文章分享数'")
    private Integer shareNum;

    @ApiModelProperty(value = "文章转发数",notes = "转发强调的是分享到外部社交媒体")
    @TableField("retweets")
    @Column(name="retweets",columnDefinition = "INT DEFAULT 0 COMMENT '文章转发数'")
    private Integer retweets;

    @ApiModelProperty(value = "浏览量")
    @TableField("views")
    @Column(name="views",columnDefinition = "INT DEFAULT 0 COMMENT '文章浏览量'")
    private Integer views;

    @ApiModelProperty(value = "文章语言",notes = "文章语言可以是语言(中文/英文),也可以是编程语言(Java/C++)仅作为标记")
    @TableField("language")
    @Column(name="language",columnDefinition = "VARCHAR(32) COMMENT '文章语言'")
    private String language;

    @Enumerated(EnumType.ORDINAL)
    @ApiModelProperty(value = "文章来源")
    @TableField("source")
    @Column(name="source",columnDefinition = "INT COMMENT '文章来源'")
    private ArticleSource source;

    @ApiModelProperty(value = "来源地址")
    @TableField("reprintSource")
    @Column(name="reprintSource",columnDefinition = "VARCHAR(255) COMMENT '来源地址'")
    private String reprintSource;

    @ApiModelProperty(value = "是否置顶")
    @TableField("top")
    @Column(name="top",columnDefinition = "BOOLEAN DEFAULT 0 COMMENT '是否置顶'")
    private Boolean top;

    @ApiModelProperty(value = "推荐指数")
    @TableField("recommendScore")
    @Column(name="recommendScore",columnDefinition = "INT COMMENT '推荐指数'")
    private Integer recommendScore;

    @ApiModelProperty(value = "版权声明")
    @TableField("copyright")
    @Column(name="copyright",columnDefinition = "VARCHAR(255) COMMENT '版权声明'")
    private String copyright;

    @Enumerated(EnumType.ORDINAL)
    @ApiModelProperty(value = "文章状态[编辑状态]")
    @TableField("articleState")
    @Column(name="articleState",columnDefinition = "INT COMMENT '文章状态[编辑状态]'")
    private DataStatus articleState;

    @ApiModelProperty(value = "阅读时长")
    @TableField("readTime")
    @Column(name="readTime",columnDefinition = "INT COMMENT '阅读时长(单位:分钟)'")
    private Integer readTime;

    @Enumerated(EnumType.ORDINAL)
    @ApiModelProperty(value = "文章状态[可见状态]")
    @TableField("visibility")
    @Column(name="visibility",columnDefinition = "INT COMMENT '文章状态[可见状态]'")
    private DataStatus visibility;

    @ApiModelProperty(value = "允许评论")
    @TableField("allowComments")
    @Column(name="allowComments",columnDefinition = "BOOLEAN DEFAULT 1 COMMENT '允许评论'")
    private Boolean allowComments;

    @Transient()
    @TableField(exist = false)
    private Integer popularityScore;

    private Integer reviewStatus;

}
