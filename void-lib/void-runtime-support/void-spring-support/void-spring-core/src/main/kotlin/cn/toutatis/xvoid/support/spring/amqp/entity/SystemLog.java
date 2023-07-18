package cn.toutatis.xvoid.support.spring.amqp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import cn.toutatis.xvoid.data.common.EntityBasicAttribute;
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
 * 系统日志
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-12-04
 */
@Getter @Setter @ToString(callSuper = true)
@TableName("vb_system_log")
@JsonIgnoreProperties({"reservedString","reservedInt"})
@ApiModel(value="SystemLog对象",description="系统日志",parent = EntityBasicAttribute.class)
@Entity @Table(name = "vb_system_log") @org.hibernate.annotations.Table(appliesTo = "vb_system_log", comment = "系统日志")
public class SystemLog extends EntityBasicAttribute<SystemLog> {

    private static final long serialVersionUID = 1L;

    @Id @TableId
    @ApiModelProperty(value="主键ID",required = true, example = "0b01f8466bcf11eda9c1b827eb90cfbc")
    @Column(name="id",columnDefinition = "VARCHAR(32) COMMENT '主键ID'")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    private String id;

    @ApiModelProperty(value = "日志类型")
    @TableField("`type`")
    @Column(name="type",columnDefinition = "VARCHAR(32) COMMENT '日志类型'")
    private String type;

    @ApiModelProperty(value = "摘要")
    @Column(name="intro",columnDefinition = "VARCHAR(255) COMMENT '摘要'")
    private String intro;

    @ApiModelProperty(value = "详细内容（JSON格式）")
    @Column(name="details",columnDefinition = "VARCHAR(6000) COMMENT '详细内容（JSON格式）'")
    private String details;

    @ApiModelProperty(value = "请求用户")
    @TableField("`user`")
    @Column(name="user",columnDefinition = "VARCHAR(32) COMMENT '请求用户'")
    private String user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


    @Override
    public Serializable pkVal() {
        return this.id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) { return false; }
        SystemLog that = (SystemLog) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
