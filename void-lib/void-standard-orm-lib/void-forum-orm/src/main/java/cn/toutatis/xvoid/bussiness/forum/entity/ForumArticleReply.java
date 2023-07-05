package cn.toutatis.xvoid.bussiness.forum.entity;

import cn.toutatis.xvoid.data.common.EntityBasicAttribute;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * @author Toutatis_Gc
 */
@Getter @Setter @ToString(callSuper = true)
@JsonIgnoreProperties({"reservedString","reservedInt"})
@ApiModel(value="ForumArticleReply对象",description="文章回复类",parent = EntityBasicAttribute.class)
@TableName("vb_forum_article_reply")
@Entity @Table(name = "vb_forum_article_reply") @org.hibernate.annotations.Table(appliesTo = "vb_forum_article_reply", comment = "文章回复类")
public class ForumArticleReply extends EntityBasicAttribute<ForumArticleReply> {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @Id @TableId(value = "id",type = IdType.AUTO)
    @Column(name="id",columnDefinition = "INT AUTO_INCREMENT COMMENT '主键ID'")
    private Integer id;

}
