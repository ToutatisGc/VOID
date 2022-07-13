package cn.toutatis.data.common;

import cn.toutatis.xvoid.toolkit.constant.Time;
import cn.toutatis.data.common.result.DataStatus;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * @author Toutatis_Gc
 * 每个实体类都要继承该类，该类包含了一些公共的属性并且
 * 每张建立表必须字段,除中间表可缺省，其余表不得缺省
 * 注解解释
 * MappedSuperclass hibernate的注解，用于指定继承的父类，并且该类会被映射到数据库表中
 *
 * TableField 和 Column 可以同时使用，
 *      TableField 是指定MybatisPlus数据库表的字段名，
 *      Column 是hibernate指定数据库表的字段类型
 * ApiModelProperty 是swagger的注解，用于描述实体类的属性
 * JsonIgnore 是jackson的注解，用于描述实体类的属性不被序列化
 * Version 是mybatisPlus的注解，用于描述实体类的版本号
 * TableLogic 是mybatisPlus的注解，用于描述实体类的逻辑删除字段
 */
@Data
@MappedSuperclass
@ApiModel(value = "EntityBasicAttribute", description = "基础实体类")
public class EntityBasicAttribute<O extends Model<?>> extends Model<O> {

    /**
     * 预留整型值
     */
    @TableField("rInt")
    @ApiModelProperty(value="预留整形值")
    @Column(name="rInt",columnDefinition = "INT COMMENT '预留整形值'")
    public Integer reservedInt;

    /**
     * 预留字符串值
     */
    @TableField("rStr")
    @ApiModelProperty(value="预留字符串")
    @Column(name="rStr",columnDefinition = "VARCHAR(255) COMMENT '预留字符串'")
    public String reservedString;

    /**
     * 创建日期
     */
    @JsonIgnore
    @TableField(value = "createTime",fill = FieldFill.INSERT)
    @ApiModelProperty(value="创建日期", required=true)
    @Column(nullable = false,columnDefinition = "DATETIME COMMENT '创建日期'")
    public LocalDateTime createTime;

    /**
     * 创建操作人
     */
    @JsonIgnore
    @TableField(value = "createBy")
    @ApiModelProperty(value="创建操作人")
    @Column(columnDefinition = "VARCHAR(32) DEFAULT 'SYSTEM' COMMENT '创建操作人'")
    public String createBy;

    /**
     * 最后更新日期
     */
    @JsonIgnore
    @TableField(value = "lastUpdateTime",fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value="最后更新日期", required=true)
    @Column(nullable = false,columnDefinition = "DATETIME COMMENT '最后更新日期'")
    public LocalDateTime lastUpdateTime;

    @JsonIgnore
    @TableField(value = "updateBy")
    @ApiModelProperty(value="更新操作人")
    @Column(columnDefinition = "VARCHAR(32) DEFAULT 'SYSTEM' COMMENT '更新操作人'")
    public String updateBy;

    /**
     * 版本号
     */
    @JsonIgnore
    @Version
    @TableField(value = "version",fill = FieldFill.INSERT)
    @ApiModelProperty(value="数据版本号", required=true)
    @Column(nullable = false,columnDefinition = "INT DEFAULT 0 COMMENT '版本号'")
    private Integer version;

    /**
     * 数据状态
     */
    @Enumerated(EnumType.ORDINAL)
    @TableField(value = "`status`",fill = FieldFill.INSERT)
    @ApiModelProperty(value="数据状态标志", required=true)
    @Column(nullable = false,columnDefinition = "TINYINT COMMENT '数据状态'")
    public DataStatus status;

    /**
     * 逻辑删除状态
     */
    @JsonIgnore
    @TableLogic
    @Enumerated(EnumType.ORDINAL)
    @TableField(value = "logicDeleted",fill = FieldFill.INSERT)
    @ApiModelProperty(value="逻辑删除标志", required=true)
    @Column(nullable = false,columnDefinition = "TINYINT DEFAULT 0 COMMENT '0正常:1删除'")
    public DataStatus logicDeleted;

    /**
     * 备注
     */
    @TableField("remark")
    @ApiModelProperty(value="备注")
    @Column(columnDefinition = "VARCHAR(255) COMMENT '备注'")
    public String remark;

    /**
     * 归属
     * 任何非中间表都应当有一个归属，举例如归属于上下级，归属于某渠道。
     * 例如应用作为平台模式，平台下有多渠道商，各渠道商的数据需要靠归属标志区分各自数据。
     */
    @TableField("belongTo")
    @ApiModelProperty(value="归属")
    @Column(columnDefinition = "VARCHAR(32) DEFAULT 'SYSTEM' COMMENT '归属'")
    public String belongTo;

    /**
     * 绑定初始数据
     */
    public void bindBaseProperties(){
        LocalDateTime nowTime = LocalDateTime.now();
        this.setCreateTime(nowTime);
        this.setLastUpdateTime(nowTime);
        this.setStatus(DataStatus.SYS_OPEN_0000);
        this.setLogicDeleted(DataStatus.SYS_OPEN_0000);
        this.setVersion(0);
    }

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
        return createTime == null ? "" : createTime.format(DateTimeFormatter.ofPattern(Time.DATE_FORMAT_REGEX));
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
        return lastUpdateTime == null ? "" : lastUpdateTime.format(DateTimeFormatter.ofPattern(Time.DATE_FORMAT_REGEX));
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
