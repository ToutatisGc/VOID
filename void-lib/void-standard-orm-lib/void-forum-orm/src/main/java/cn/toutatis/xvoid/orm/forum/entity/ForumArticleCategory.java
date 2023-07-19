package cn.toutatis.xvoid.orm.forum.entity;


import cn.toutatis.xvoid.BusinessType;
import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import cn.toutatis.xvoid.orm.base.data.common.result.DataStatus;
import cn.toutatis.xvoid.orm.structure.Tree;
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
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serial;
import java.util.List;

/**
 * 文章归属分类
 * @author Toutatis_Gc
 */
@Getter @Setter @ToString(callSuper = true)
@JsonIgnoreProperties({"reservedString","reservedInt"})
@ApiModel(value="文章归属集合类",description="例如文章在:Python学习&机器学习集合",parent = EntityBasicAttribute.class)
@TableName("vb_forum_article_category")
@DynamicInsert @DynamicUpdate
@Entity @Table(name = "vb_forum_article_category",
        indexes = {@Index(name = "NAME_INDEX", columnList = "name")}
) @org.hibernate.annotations.Table(appliesTo = "vb_forum_article_category", comment = "文章归属集合类")
public class ForumArticleCategory extends EntityBasicAttribute<ForumArticleCategory> implements Tree<ForumArticleCategory,ForumArticle> {
    @Serial
    private static final long serialVersionUID = 1L;

    {this.setBusinessType(BusinessType.XVOID_FORUM);}

    @ApiModelProperty(value = "主键ID",required = true)
    @Id @TableId(value = "id",type = IdType.AUTO)
    @Column(name="id",columnDefinition = "INT AUTO_INCREMENT COMMENT '主键ID'")
    private int id;

    @ApiModelProperty(value = "父ID")
    @TableField("parentId")
    @Column(name="parentId",nullable = false,columnDefinition = "INT DEFAULT 0 COMMENT '父ID'")
    private Integer parentId = 0;

    @ApiModelProperty(value = "冗余ID",notes = "为父ID和当前ID的拼接")
    @TableField("redundantId")
    @Column(name="redundantId",columnDefinition = "VARCHAR(256) COMMENT '冗余ID'")
    private String redundantId;

    @ApiModelProperty(value = "集合名称")
    @TableField("name")
    @Column(name="name",nullable = false,columnDefinition = "VARCHAR(32) COMMENT '集合名称'")
    private String name;

    @ApiModelProperty(value = "集合描述")
    @TableField("description")
    @Column(name="description",columnDefinition = "VARCHAR(256) COMMENT '集合描述'")
    private String description;

    @ApiModelProperty(value = "图标链接")
    @TableField("iconUrl")
    @Column(name="iconUrl",columnDefinition = "VARCHAR(256) COMMENT '图标链接'")
    private String iconUrl;

    @ApiModelProperty(value = "封面图链接")
    @TableField("coverImageUrl")
    @Column(name="coverImageUrl",columnDefinition = "VARCHAR(256) COMMENT '封面图链接'")
    private String coverImageUrl;

    @ApiModelProperty(value = "优先级(优先级越高越靠前)")
    @TableField("categoryOrder")
    @Column(name="categoryOrder",nullable = false,columnDefinition = "INT DEFAULT 0 COMMENT '优先级(优先级越高越靠前)'")
    private Integer categoryOrder = 0;

    @Enumerated(EnumType.ORDINAL)
    @ApiModelProperty(value = "合集状态[可见状态]")
    @TableField("visibility")
    @Column(name="visibility",nullable = false,columnDefinition = "INT COMMENT '合集状态[可见状态]'")
    private DataStatus visibility = DataStatus.SYS_VISIBILITY_0000;

    @ApiModelProperty(value = "订阅数")
    @TableField("subscriptionNum")
    @Column(name="subscriptionNum",nullable = false,columnDefinition = "INT DEFAULT 0 COMMENT '订阅数'")
    private Integer subscriptionNum = 0;

    @ApiModelProperty(value = "关联文章数量")
    @Transient @TableField(exist = false)
    private Integer associatedCount;

    @Enumerated(EnumType.STRING)
    @ApiModelProperty(value = "业务类型")
    @TableField("businessType")
    @Column(name="businessType",nullable = false,columnDefinition = "VARCHAR(32) COMMENT '业务类型'")
    private BusinessType businessType = BusinessType.XVOID_FORUM;

    @Transient @TableField(exist = false)
    private Tree<ForumArticleCategory, ForumArticle> parent;

    @Transient @TableField(exist = false)
    private List<Tree<ForumArticleCategory, ForumArticle>> branches;

    @Transient @TableField(exist = false)
    private List<ForumArticle> nodes;

    @NotNull
    @Override
    public Tree<ForumArticleCategory, ForumArticle> getParent() {
        return parent;
    }

    @NotNull
    @Override
    public List<Tree<ForumArticleCategory, ForumArticle>> getBranch() {
        return branches;
    }

    @NotNull
    @Override
    public List<ForumArticle> getNodes() {
        return nodes;
    }

    public void setParent(Tree<ForumArticleCategory, ForumArticle> parent) {
        this.parent = parent;
    }

    public void setBranches(List<Tree<ForumArticleCategory, ForumArticle>> branches) {
        this.branches = branches;
    }

    public void setNodes(List<ForumArticle> nodes) {
        this.nodes = nodes;
    }
}
