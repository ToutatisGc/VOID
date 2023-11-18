package cn.toutatis.xvoid.orm.base.data.common;

import cn.toutatis.xvoid.BusinessType;
import cn.toutatis.xvoid.common.result.DataStatus;
import cn.toutatis.xvoid.common.standard.StandardFields;
import cn.toutatis.xvoid.toolkit.constant.Time;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>每个实体类都要继承该类，该类包含了一些公共的属性并且</p>
 * <p>每张建立表必须字段,除中间表可缺省，其余表不得缺省注解</p>
 * <p>@MappedSuperclass hibernate的注解，用于指定继承的父类，并且该类会被映射到数据库表中</p>
 * <p>@TableField 和 @Column 可以同时使用，</p>
 * <p>@TableField 是指定MybatisPlus数据库表的字段名，</p>
 * <p>@Column 是hibernate指定数据库表的字段类型</p>
 * <p>@ApiModelProperty 是swagger的注解，用于描述实体类的属性</p>
 * <p>@JsonIgnore 是jackson的注解，用于描述实体类的属性不被序列化</p>
 * <p>@Version 是mybatisPlus的注解，用于描述实体类的版本号</p>
 * <p>@TableLogic 是mybatisPlus的注解，用于描述实体类的逻辑删除字段</p>
 * @author Toutatis_Gc
 */
@Getter @Setter @ToString
@MappedSuperclass
@ApiModel(value = "EntityBasicAttribute", description = "基础实体类")
public abstract class EntityBasicAttribute<O extends Model<?>> extends Model<O> {

    /**
     * 实体状态字段名称
     */
    public static final String STATUS_COLUMN_NAME = "status";

    /**
     * 创建人字段名称
     */
    public static final String CREATE_BY_COLUMN_NAME = "createBy";

    /**
     * 创建日期字段名称
     */
    public static final String CREATE_TIME_COLUMN_NAME = "createTime";

    /**
     * 逻辑删除字段名称
     */
    public static final String LOGIC_DELETED_COLUMN_NAME = "logicDeleted";

    public static final String DEFAULT_WHERE_CLAUSE = LOGIC_DELETED_COLUMN_NAME + " = 0";

    protected EntityBasicAttribute(){}

    /**
     * 业务类型
     */
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @TableField("businessType")
    @Schema(name="业务类型")
    @Column(name="businessType",nullable = false,columnDefinition = "VARCHAR(64) COMMENT '业务类型'")
    protected BusinessType businessType = BusinessType.XVOID_SYSTEM;

    /**
     * 预留整型值
     */
    @TableField("rInt")
    @Schema(name="预留整形值")
    @Column(name="rInt",columnDefinition = "INT COMMENT '预留整形值'")
    protected Integer reservedInt;

    /**
     * 预留字符串值
     */
    @TableField("rStr")
    @Schema(name="预留字符串")
    @Column(name="rStr",columnDefinition = "VARCHAR(255) COMMENT '预留字符串'")
    protected String reservedString;

    /**
     * 创建日期
     */
    @JsonIgnore
    @CreatedDate
    @TableField(value = CREATE_TIME_COLUMN_NAME,fill = FieldFill.INSERT)
    @Schema(name="创建日期", required=true)
    @Column(columnDefinition = "DATETIME NOT NULL DEFAULT current_timestamp() COMMENT '创建日期'")
    protected LocalDateTime createTime;

    /**
     * 创建操作人
     */
    @JsonIgnore
    @CreatedBy
    @TableField(value = CREATE_BY_COLUMN_NAME)
    @Schema(name="创建操作人")
    @Column(columnDefinition = "VARCHAR(32) NOT NULL DEFAULT '"+ StandardFields.VOID_BUSINESS_DEFAULT_CREATOR +"' COMMENT '创建操作人'")
    protected String createBy;

    /**
     * 最后更新日期
     */
    @JsonIgnore
    @LastModifiedDate
    @TableField(value = "lastUpdateTime",fill = FieldFill.INSERT_UPDATE)
    @Schema(name="最后更新日期", required=true)
    @Column(columnDefinition = "DATETIME NOT NULL DEFAULT current_timestamp() COMMENT '最后更新日期'")
    protected LocalDateTime lastUpdateTime;

    @JsonIgnore
    @LastModifiedBy
    @TableField(value = "updateBy")
    @Schema(name="更新操作人")
    @Column(columnDefinition = "VARCHAR(32) NOT NULL DEFAULT '"+ StandardFields.VOID_BUSINESS_DEFAULT_CREATOR +"' COMMENT '更新操作人'")
    protected String updateBy;

    /**
     * 版本号
     */
    @JsonIgnore
    @Version
    @TableField(value = "version",fill = FieldFill.INSERT)
    @Schema(name="数据版本号", required=true)
    @Column(columnDefinition = "INT NOT NULL DEFAULT 0 COMMENT '版本号'")
    private Integer version;

    /**
     * 数据状态
     */
    @Enumerated(EnumType.ORDINAL)
    @TableField(value = "`"+STATUS_COLUMN_NAME+"`",fill = FieldFill.INSERT)
    @Schema(name="数据状态标志", required=true)
    @Column(nullable = false,columnDefinition = "TINYINT NOT NULL DEFAULT 0 COMMENT '数据状态'")
    protected DataStatus status = DataStatus.SYS_OPEN_0000;

    /**
     * 逻辑删除状态
     */
    @JsonIgnore
    @TableLogic
    @Enumerated(EnumType.ORDINAL)
    @TableField(value = LOGIC_DELETED_COLUMN_NAME,fill = FieldFill.INSERT)
    @Schema(name="逻辑删除标志", required=true)
    @Column(nullable = false,columnDefinition = "TINYINT NOT NULL DEFAULT 0 COMMENT '0正常:1删除'")
    protected DataStatus logicDeleted = DataStatus.SYS_OPEN_0000;

    /**
     * 备注
     */
    @TableField("remark")
    @Schema(name="备注")
    @Column(columnDefinition = "VARCHAR(255) COMMENT '备注'")
    protected String remark;

    /**
     * 归属
     * 任何非中间表都应当有一个归属，举例如归属于上下级，归属于某渠道。
     * 例如应用作为平台模式，平台下有多渠道商，各渠道商的数据需要靠归属标志区分各自数据。
     */
    @JsonIgnore
    @TableField("belongTo")
    @Schema(name="归属")
    @Column(columnDefinition = "VARCHAR(32) NOT NULL DEFAULT '"+ StandardFields.VOID_BUSINESS_DEFAULT_CREATOR +"' COMMENT '归属'")
    protected String belongTo;

    public Integer getReservedInt() {
        return reservedInt;
    }

    public void setReservedInt(Integer reservedInt) {
        this.reservedInt = reservedInt;
    }

    public String getReservedString() {
        return reservedString;
    }

    public void setReservedString(String reservedString) {
        this.reservedString = reservedString;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime == null ? LocalDateTime.now() : createTime;
    }

    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }

    public DataStatus getLogicDeleted() {
        return logicDeleted;
    }

    public void setLogicDeleted(DataStatus logicDeleted) {
        this.logicDeleted = logicDeleted;
    }

    @Transient
    public String getCreateTimeStr() {
        return createTime == null ? "" : createTime.format(DateTimeFormatter.ofPattern(Time.SIMPLE_DATE_FORMAT_REGEX));
    }

    @Transient
    public long getCreateTimeMs() {
        return createTime == null ? 0L :  Timestamp.valueOf(createTime).getTime()/1000;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime == null ? LocalDateTime.now() : lastUpdateTime;
    }

    @Transient
    public String getLastUpdateTimeStr() {
        return lastUpdateTime == null ? "" : lastUpdateTime.format(DateTimeFormatter.ofPattern(Time.SIMPLE_DATE_FORMAT_REGEX));
    }

    @Transient
    public long getLastUpdateTimeMs() {
        return lastUpdateTime == null ? 0L : Timestamp.valueOf(lastUpdateTime).getTime()/1000;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark  = remark;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

}
