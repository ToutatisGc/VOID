package cn.toutatis.xvoid.sql.dql;

import cn.toutatis.xvoid.common.standard.StringPool;
import cn.toutatis.xvoid.sql.base.*;
import cn.toutatis.xvoid.toolkit.validator.Validator;

import java.util.List;

/**
 * @author Toutatis_Gc
 */
public class DQLBuilder<T> {

    private final DQLMetaBuilder<T> metaInfo;

    public DQLBuilder(Class<T> entityClass) {
        metaInfo = new DQLMetaBuilder<>(SQLType.SELECT, entityClass);
    }

    public DQLBuilder<T> select(String column,String alias){
        metaInfo.putColumn(new SQLColumn(column,alias));
        return this;
    }

    public DQLBuilder<T> eq(String column, Object value){
        // TODO 看用哪种形式存储数据
        metaInfo.putColumn(new SQLColumn(column,value, SQLPart.PartType.CONDITION));
        return this;
    }

    public DQLBuilder<T> eq(XFunc<T,?> column, Object value) throws Exception {
        this.eq(LambdaHandler.getFieldName(LambdaHandler.serialize(column)), value);
        return this;
    }

    public String build(){
        // 初始化SQL
        StringBuilder sql = new StringBuilder(metaInfo.getSqlType().toString());
        appendSpacing(sql);
        // 处理查询字段
        this.processingColumns(sql);
        sql.append("FROM");
        appendSpacing(sql);
        sql.append(metaInfo.getTable());
        appendSpacing(sql);
        this.processingConditions(sql);
        return sql.toString();
    }

    private void appendSpacing(StringBuilder sql){
        sql.append(StringPool.SPACE);
    }

    private void processingColumns(StringBuilder sql){
        List<SQLColumn> columns = metaInfo.getColumns();
        int columnsSize = columns.size();
        if (columnsSize > 0) {
            for (int i = 0; i < columnsSize; i++) {
                SQLColumn sqlColumn = columns.get(i);
                sql.append(sqlColumn.getColumnName());
                appendSpacing(sql);
                String alias = sqlColumn.getAlias();
                if (Validator.strNotBlank(alias)){
                    sql.append("AS");
                    appendSpacing(sql);
                    // TODO 关键字添加反引号
                    sql.append(alias);
                }
                if (i == columnsSize - 1){
                    appendSpacing(sql);
                }else {
                    sql.append(StringPool.COMMA);
                }
            }
        }else {
            sql.append(StringPool.ASTERISK);
            appendSpacing(sql);
        }
    }

    private void processingConditions(StringBuilder sql){
        List<SQLColumn> conditions = metaInfo.getConditions();
        if (!conditions.isEmpty()){
            sql.append("WHERE");
            appendSpacing(sql);
        }
    }

}