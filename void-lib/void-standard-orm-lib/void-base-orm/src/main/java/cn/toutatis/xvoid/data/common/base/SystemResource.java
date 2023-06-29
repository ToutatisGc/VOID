package cn.toutatis.xvoid.data.common.base;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import cn.toutatis.xvoid.data.common.EntityBasicAttribute;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import java.text.SimpleDateFormat;

/**
 * <p>
 * 系统用户类
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2023-06-03
 */
@Getter @Setter @ToString(callSuper = true)
@TableName("vb_system_resource")
@JsonIgnoreProperties({"reservedString","reservedInt"})
@ApiModel(value="SystemResource对象",description="系统用户类",parent = EntityBasicAttribute.class)
@Entity @Table(name = "vb_system_resource") @org.hibernate.annotations.Table(appliesTo = "vb_system_resource", comment = "系统资源类")
public class SystemResource extends EntityBasicAttribute<SystemResource> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @Id @TableId(value = "id",type = IdType.AUTO)
    @Column(name="id",columnDefinition = "INT COMMENT '主键ID'")
    private Integer id;

    @ApiModelProperty(value = "原文件名")
    @TableField("originName")
    @Column(name="originName",columnDefinition = "VARCHAR(64) COMMENT '原文件名'")
    private String originName;

    @ApiModelProperty(value = "文件名")
    @TableField("fileName")
    @Column(name="fileName",columnDefinition = "VARCHAR(64) COMMENT '文件名'")
    private String fileName;

    @ApiModelProperty(value = "哈希信息[默认MD5摘要算法]")
    @TableField("`hash`")
    @Column(name="hash",columnDefinition = "VARCHAR(256) COMMENT '哈希信息[默认MD5摘要算法]'")
    private String hash;

    @ApiModelProperty(value = "文件后缀")
    @Column(name="suffix",columnDefinition = "VARCHAR(32) COMMENT '文件后缀'")
    private String suffix;

    @ApiModelProperty(value = "文件大小")
    @Column(name="size",columnDefinition = "INT COMMENT '文件大小'")
    private Integer size;

    @ApiModelProperty(value = "文件MIME-TYPE")
    @TableField("contentType")
    @Column(name="contentType",columnDefinition = "VARCHAR(64) COMMENT '文件MIME-TYPE'")
    private String contentType;

    @ApiModelProperty(value = "文件路径")
    @Column(name="path",columnDefinition = "VARCHAR(512) COMMENT '文件路径'")
    private String path;

    @ApiModelProperty(value = "文件标签")
    @Column(name="tags",columnDefinition = "VARCHAR(256) COMMENT '文件标签'")
    private String tags;

    @ApiModelProperty(value = "关联文件")
    @TableField("relatedFile")
    @Column(name="relatedFile",columnDefinition = "INT COMMENT '关联文件'")
    private Integer relatedFile;

    @ApiModelProperty(value = "关联类型")
    @TableField("relatedType")
    @Column(name="relatedType",columnDefinition = "INT COMMENT '关联类型'")
    private Integer relatedType;

    @ApiModelProperty(value = "文件状态[草稿,可归档,不可见等]")
    @TableField("fileStatus")
    @Column(name="fileStatus",columnDefinition = "INT COMMENT '文件状态[草稿,可归档,不可见等]'")
    private Integer fileStatus;

    @ApiModelProperty(value = "共享短链接")
    @TableField("shareableLink")
    @Column(name="shareableLink",columnDefinition = "VARCHAR(128) COMMENT '共享短链接'")
    private String shareableLink;

    @ApiModelProperty(value = "访问次数")
    @TableField("downloadCount")
    @Column(name="downloadCount",columnDefinition = "INT COMMENT '访问次数'")
    private Integer downloadCount;


    @Override
    public Serializable pkVal() {
        return this.id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) { return false; }
        SystemResource that = (SystemResource) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
