package cn.toutatis.data.common;

import cn.toutatis.constant.Time;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Toutatis_Gc
 * 每张建立表必须字段,除中间表可缺省，其余表不得缺省
 */
@MappedSuperclass
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
     * 最后更新日期
     */
    @JsonIgnore
    @TableField(value = "lastUpdateTime",fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value="最后更新日期", required=true)
    @Column(nullable = false,columnDefinition = "DATETIME COMMENT '最后更新日期'")
    public LocalDateTime lastUpdateTime;

    /**
     * 版本号
     */
    @JsonIgnore
    @Version
    @TableField(value = "version",fill = FieldFill.INSERT)
    @ApiModelProperty(value="数据版本号", required=true)
    @Column(nullable = false,columnDefinition = "INT COMMENT '版本号'")
    private Integer version;

    /**
     * 数据状态
     */
    @TableField(value = "`status`",fill = FieldFill.INSERT)
    @ApiModelProperty(value="数据状态标志", required=true)
    @Column(nullable = false,columnDefinition = "TINYINT COMMENT '数据状态'")
    public DataStatus status;

    /**
     * 逻辑删除状态
     */
    @JsonIgnore
    @TableLogic
    @TableField(value = "logicDeleted",fill = FieldFill.INSERT)
    @ApiModelProperty(value="逻辑删除标志", required=true)
    @Column(nullable = false,columnDefinition = "TINYINT COMMENT '0正常:1删除'")
    public DataStatus logicDeleted;

    /**
     * 备注
     */
    @TableField("remark")
    @ApiModelProperty(value="备注")
    @Column(columnDefinition = "VARCHAR(255) COMMENT '备注'")
    public String remark;

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

    public String getCreateTimeStr() {
        return createTime == null ? "" : createTime.format(DateTimeFormatter.ofPattern(Time.DATE_FORMAT_REGEX));
    }

    public long getCreateTimeMs() {
        return createTime == null ? 0L :  Timestamp.valueOf(createTime).getTime()/1000;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime == null ? LocalDateTime.now() : lastUpdateTime;
    }

    public String getLastUpdateTimeStr() {
        return lastUpdateTime == null ? "" : lastUpdateTime.format(DateTimeFormatter.ofPattern(Time.DATE_FORMAT_REGEX));
    }

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

}
