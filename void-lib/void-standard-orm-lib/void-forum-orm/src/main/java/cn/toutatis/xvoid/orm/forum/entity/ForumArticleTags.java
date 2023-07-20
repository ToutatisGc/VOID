package cn.toutatis.xvoid.orm.forum.entity;

import cn.toutatis.xvoid.BusinessType;
import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
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

import static cn.toutatis.xvoid.orm.forum.entity.ForumArticleTags.TABLE;

@Getter @Setter @ToString(callSuper = true)
@JsonIgnoreProperties({"reservedString","reservedInt"})
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
    @Id
    @TableId(value = "id",type = IdType.AUTO)
    @Column(name="id",columnDefinition = "INT AUTO_INCREMENT COMMENT '主键ID'")
    private Integer id;


}
