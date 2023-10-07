package cn.toutatis.xvoid.sql.dql;

import cn.toutatis.xvoid.common.standard.StringPool;
import cn.toutatis.xvoid.sql.base.*;
import cn.toutatis.xvoid.toolkit.clazz.LambdaToolkit;
import cn.toutatis.xvoid.toolkit.clazz.XFunc;
import cn.toutatis.xvoid.toolkit.validator.Validator;

import java.util.Map;

/**
 * @author Toutatis_Gc
 */
public class DQLBuilder<T> {

    private final DQLMetaBuilder<T> metaInfo;

    public DQLBuilder(Class<T> entityClass) {
        metaInfo = new DQLMetaBuilder<>(SQLType.SELECT, entityClass);
    }

    public DQLBuilder<T> select(String field){
        metaInfo.putColumn(new SQLColumn(field));
        return this;
    }

    public DQLBuilder<T> select(String field,String alias){
        metaInfo.putColumn(new SQLColumn(field,alias));
        return this;
    }

    public DQLBuilder<T> select(XFunc<T,?> field) throws Exception {
        this.select(LambdaToolkit.getFieldName(field));
        return this;
    }

    public DQLBuilder<T> select(XFunc<T,?> field, String alias) throws Exception {
        this.select(LambdaToolkit.getFieldName(field),alias);
        return this;
    }

    public DQLBuilder<T> eq(String field, Object value){
        metaInfo.putCondition(new SQLCondition(field, value));
        return this;
    }

    public DQLBuilder<T> eq(XFunc<T,?> column, Object value) throws Exception {
        this.eq(LambdaToolkit.getFieldName(column), value);
        return this;
    }

    public String build(){
        // 初始化SQL
        StringBuilder sql = SQLHelper.initSql(metaInfo.getSqlType());
        // 处理查询字段
        String columnsPlaceholder = SQLPlaceHolder.SELECT_COLUMNS.value();
        int start = metaInfo.getSqlType().name().length() + 1;
        sql.replace(start,start+columnsPlaceholder.length(),this.processingColumns());
        sql.append("FROM");
        appendSpacing(sql);
        sql.append(metaInfo.getTable());
        // TODO 处理条件
        String conditions = SQLHelper.processingConditions(metaInfo.getConditions());
        sql.append(conditions);
        return sql.toString();
    }

    private void appendSpacing(StringBuilder sql){
        sql.append(StringPool.SPACE);
    }

    /**
     * 处理查询字段
     */
    private String processingColumns(){
        StringBuilder selectFields = new StringBuilder();
        Map<String, SQLColumn> columns = metaInfo.getColumns();
        int columnsSize = columns.size();
        if (!columns.isEmpty()) {
            int idx = 0;
            for (Map.Entry<String, SQLColumn> sqlColumnEntry : columns.entrySet()) {
                SQLColumn sqlColumn = sqlColumnEntry.getValue();
                String alias = sqlColumn.getAlias();
                if (Validator.strNotBlank(alias)){
                    selectFields.append(sqlColumn.getField());
                    appendSpacing(selectFields);
                    selectFields.append("AS");
                    appendSpacing(selectFields);
                    boolean match = SQLKeyword.match(alias);
                    if (match){
                        selectFields.append(StringPool.BACKTICK);
                        selectFields.append(alias);
                        selectFields.append(StringPool.BACKTICK);
                    }else {
                        selectFields.append(alias);
                    }
                }else {
                    selectFields.append(sqlColumn.getField());
                }
                if (idx != columnsSize - 1) {
                    selectFields.append(StringPool.COMMA);
                }
                idx++;
            }
        }else {
            selectFields.append(StringPool.ASTERISK);
        }
        return selectFields.toString();
    }

}