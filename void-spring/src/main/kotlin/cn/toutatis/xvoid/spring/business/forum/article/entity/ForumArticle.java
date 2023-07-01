package cn.toutatis.xvoid.spring.business.forum.article.entity;

import cn.toutatis.xvoid.data.common.EntityBasicAttribute;
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

@Getter @Setter @ToString(callSuper = true)
@JsonIgnoreProperties({"reservedString","reservedInt"})
@ApiModel(value="ForumArticle对象",description="论坛文章类",parent = EntityBasicAttribute.class)
@TableName("vb_forum_resource")
@Entity @Table(name = "vb_forum_resource") @org.hibernate.annotations.Table(appliesTo = "vb_forum_resource", comment = "论坛文章类")
public class ForumArticle extends EntityBasicAttribute<ForumArticle> {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @Id @TableId(value = "id",type = IdType.AUTO)
    @Column(name="id",columnDefinition = "INT AUTO_INCREMENT COMMENT '主键ID'")
    private Integer id;

    @ApiModelProperty(value = "文章标题")
    @TableField("title")
    @Column(name="title",columnDefinition = "VARCHAR(64) COMMENT '文章标题'")
    private String title;

    @ApiModelProperty(value = "文章内容富文本")
    @TableField("content")
    @Column(name="content",columnDefinition = "VARCHAR(64) COMMENT '文章内容富文本'")
    private String content;

    // TODO 字段

}
