package cn.toutatis.xvoid.orm.base.infrastructure.entity;

import cn.toutatis.xvoid.BusinessType;
import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import static cn.toutatis.xvoid.orm.base.infrastructure.entity.SystemLog.TABLE;

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-12-04
 */
@Getter @Setter @ToString(callSuper = true)
@TableName(TABLE)
@JsonIgnoreProperties({"reservedString","reservedInt","lastUpdateTimeMs","createTimeMs"})
@ApiModel(value="SystemLog对象",description="系统日志",parent = EntityBasicAttribute.class)
@Entity @Table(name = TABLE) @org.hibernate.annotations.Table(appliesTo = TABLE, comment = "系统日志")
public class SystemLog extends EntityBasicAttribute<SystemLog> {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 数据库表名以及业务类型
     */
    public static final String TABLE = "vb_system_log";
    {this.setBusinessType(BusinessType.XVOID_SYSTEM);}

    @Id @TableId
    @Schema(name="主键ID",required = true, example = "0b01f8466bcf11eda9c1b827eb90cfbc")
    @Column(name="id",columnDefinition = "VARCHAR(32) COMMENT '主键ID'")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    private String id;

    @ApiModelProperty(value = "日志类型")
    @TableField("`type`")
    @Column(name="type",columnDefinition = "VARCHAR(32) COMMENT '日志类型'")
    private String type;

    @ApiModelProperty(value = "子业务类型")
    @TableField("subType")
    @Column(name="subType",columnDefinition = "VARCHAR(32) COMMENT '子业务类型'")
    private String subType;

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
