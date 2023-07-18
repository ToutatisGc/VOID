package cn.toutatis.xvoid.support.spring.config.orm.mybatisplus.support;

/**
 * @author Toutatis_Gc
 * mybatis plus 分页查询对象
 */
public class PagingQuery {

    /**
     * 默认查询第一页
     */
    private Integer currentPage = 1;

    /**
     * 默认分页大小为15个记录
     */
    private Integer pageSize = 15;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "PagingQuery{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                '}';
    }
}
