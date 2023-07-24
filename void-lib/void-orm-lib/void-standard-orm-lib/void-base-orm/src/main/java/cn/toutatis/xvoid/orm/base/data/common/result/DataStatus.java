package cn.toutatis.xvoid.orm.base.data.common.result;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 数据状态枚举
 * 相关引用 Spring-data-jpa/hibernate/mybatis-plus
 * @author Toutatis_Gc
 * @date 2022/6/13 20:49
 */
public enum DataStatus {

    /**
     * SYS_OPEN_0000和SYS_DELETED_0000不可操作
     * 两种状态为顶级数据标志,影响整个系统使用,并且受命名/index字段/顺序等影响
     * 如果一定需要改变,请仔细查看调用和业务数据库为空或者系统未使用
     */
    SYS_OPEN_0000(0,"SYS_OPEN_0000","数据正常","数据一切正常"),
    SYS_DELETED_0000(1,"SYS_DELETED_0000","逻辑删除","数据删除[用户不可见]"),
    // WARNING END WARNING
    /**
     * 系统状态
     */
    SYS_VISIBILITY_0000(2,"SYS_VISIBILITY_0000","数据可浏览","数据对所有人可见"),
    SYS_VISIBILITY_0001(3,"SYS_VISIBILITY_0001","数据可浏览","数据对部分人可见"),
    SYS_VISIBILITY_0002(4,"SYS_VISIBILITY_0002","数据可浏览","数据仅个人可见"),
    /**
     * 编辑状态
     */
    SYS_OPEN_0001(5,"SYS_OPEN_0001","草稿状态","非正式数据[草稿状态]"),
    SYS_ARCHIVED_0000(6, "SYS_ARCHIVED_0000", "数据正在归档","数据正在归档中,不可操作"),
    SYS_ARCHIVED_0001(7, "SYS_ARCHIVED_0001", "数据已归档","数据已归档,不可修改"),
    /**
     * 活跃状态
     */
    SYS_ACTIVE_0000(8, "SYS_ACTIVE_0000", "数据激活","数据处于活跃状态"),
    SYS_INACTIVE_0000(9, "SYS_INACTIVE_0000", "数据静置","数据处于静置状态"),
    /**
     * 处理状态
     */
    SYS_PENDING_0000(10, "SYS_PENDING_0000", "数据待处理","数据处于待处理状态"),
    SYS_IN_PROGRESS_0000(11, "SYS_IN_PROGRESS_0000", "数据正在处理","数据正在业务处理中"),
    SYS_REJECTED_0000(12, "SYS_REJECTED_0000", "数据已拒绝","数据已被拒绝接收"),
    SYS_ON_HOLD_0000(13, "SYS_ON_HOLD_0000", "数据处理暂停中","数据暂停处理"),
    SYS_ON_HOLD_0001(14, "SYS_ON_HOLD_0001", "数据已挂起", "数据处在挂起状态"),
    SYS_FAILED_0000(15, "SYS_FAILED_0000", "数据处理失败", "数据处理失败"),
    SYS_COMPLETED_0000(16, "SYS_COMPLETED_0000", "数据已完成","数据已处理完成"),
    /**
     * 时间状态
     */
    SYS_EXPIRED_0000(17, "SYS_EXPIRED_0000", "数据过期","数据已经超过使用期限"),
    SYS_EXPIRING_0000(18, "SYS_EXPIRING_0000", "即将过期", "数据即将过期"),
    /**
     * 合法状态
     */
    SYS_VALIDATED_0000(19, "SYS_VALIDATED_0000", "已验证", "数据已验证状态"),
    SYS_UNCHECK_0000(20,"SYS_UNCHECK_0000","数据未验证","数据未验证,需要验证正确性"),
    SYS_LOCKED_0000(21, "SYS_LOCKED_0000", "数据已锁定","数据由于过期/归档等原因合法锁定"),
    SYS_LOCKED_0001(22, "SYS_LOCKED_0001", "数据已锁定","数据由于非法访问等原因,为数据安全已锁定"),
    /**
     * 流程状态
     */
    SYS_REVIEW_PENDING_0000(23, "SYS_REVIEW_PENDING_0000", "数据待评审", "数据等待评审"),
    SYS_REVIEW_PENDING_0001(24, "SYS_REVIEW_PENDING_0001", "数据评审中", "数据正在评审状态中"),
    SYS_UNDER_REVIEW_0000(25, "SYS_UNDER_REVIEW_0000", "数据待审核", "数据等待审核"),
    SYS_UNDER_REVIEW_0001(26, "SYS_UNDER_REVIEW_0001", "数据审核中", "数据正在审核状态中"),
    SYS_UNDER_REVISION_0000(27, "SYS_UNDER_REVISION_0000", "修订中", "数据正在修订"),
    SYS_REVIEW_FAILED_0000(28, "SYS_REVIEW_FAILED_0000", "审核未通过", "数据未通过审核"),
    SYS_REVIEWED_0000(29, "SYS_REVIEWED_0000", "已审核", "数据已通过审核"),
    SYS_UNAPPROVED_0000(30, "SYS_UNAPPROVED_0000", "未批准", "数据未被批准"),
    SYS_APPROVED_0000(31, "SYS_APPROVED_0000", "已批准", "数据已被批准"),
    /**
     * 转换状态
     */
    SYS_CONVERTED_0000(32, "SYS_CONVERTED_0000", "已转换", "数据已转换状态"),
    SYS_INVALIDATED_0000(33, "SYS_INVALIDATED_0000", "已作废", "数据已作废状态"),
    SYS_UNDER_MAINTENANCE_0000(34, "SYS_UNDER_MAINTENANCE_0000", "维护中", "数据正在维护状态"),
    SYS_UNDER_CONSTRUCTION_0000(35, "SYS_UNDER_CONSTRUCTION_0000", "建设中", "数据正在建设状态"),
    /**
     * 支付状态
     */
    SYS_PENDING_PAYMENT_0000(36, "SYS_PENDING_PAYMENT_0000", "待付款", "数据处于待付款状态"),
    SYS_PAYMENT_SUBMITTED_0000(37, "SYS_PAYMENT_SUBMITTED_0000", "支付已提交", "支付已提交支付渠道,待回调"),
    SYS_PAYMENT_FAILURE_0000(38, "SYS_PAYMENT_FAILURE_0000", "支付失败", "付款失败"),
    SYS_PAID_0000(39, "SYS_PAID_0000", "已付款", "数据已付款"),
    ;

    @EnumValue
    private final Integer index;

    private final String code;

    private final String content;

    private final String detail;

    DataStatus(Integer index, String code, String content, String detail) {
        this.index = index;
        this.code = code;
        this.content = content;
        this.detail = detail;
    }

    public String getCode() {
        return code;
    }

    public String getContent() {
        return content;
    }

    public Integer getIndex() {
        return index;
    }

    public String getDetail() {
        return detail;
    }
}
