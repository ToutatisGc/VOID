package cn.toutatis.data.common;

import cn.toutatis.constant.Time;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Toutatis_Gc
 * 每张建立表必须字段,除中间表可缺省，其余表不得缺省
 */
public class EntityBasicAttribute<O extends Model<?>> extends Model<O> {

    /**
     * 预留整型值
     */
    @TableField("rInt")
    public Integer reservedInt;

    /**
     * 预留字符串值
     */
    @TableField("rStr")
    public String reservedString;

    /**
     * 创建日期
     */
    @JsonIgnore
    @TableField("createTime")
    public LocalDateTime createTime;

    @JsonIgnore
    @TableField("lastUpdateTime")
    public LocalDateTime lastUpdateTime;

    @Version
    @TableField("version")
    private Integer version;

    /**
     * 数据状态
     */
    @TableLogic
    @TableField("`status`")
    public String status;

    /**
     * 备注
     */
    @TableField("remark")
    public String remark;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void bindBaseProperties(){
        LocalDateTime nowTime = LocalDateTime.now();
        this.setCreateTime(nowTime);
        this.setLastUpdateTime(nowTime);
        this.setStatus(DataStatus.LOGIC_NORMAL);
    }

    public String getCreateTimeStr() {
        return createTime == null ? "" : createTime.format(DateTimeFormatter.ofPattern(Time.DATE_FORMAT_REGEX));
    }

    public long getCreateTimeMs() {
        return createTime == null ? 0L : createTime.getNano();
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
        return lastUpdateTime == null ? 0L : lastUpdateTime.getNano();
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
