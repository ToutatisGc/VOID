package cn.toutatis.xvoid.sql.base;

import cn.toutatis.xvoid.common.standard.StringPool;
import cn.toutatis.xvoid.toolkit.constant.Regex;

import java.util.List;
import java.util.regex.Pattern;

public class SQLHelper {

    public static StringBuilder initSql(SQLType sqlType){
        StringBuilder sql = new StringBuilder(sqlType.name());
        sql.append(StringPool.SPACE);
        switch (sqlType){
            case SELECT -> {
                sql.append(SQLPlaceHolder.SELECT_COLUMNS.value());
            }
            case DELETE -> {
                sql.append("FROM");
            }
            case INSERT -> {
                sql.append("INTO");
            }
        }
        sql.append(StringPool.SPACE);
        return sql;
    }

//    protected static Pattern pattern = Pattern.compile(Regex.NUMBER_REGEX);

    public static String processingConditions(List<SQLCondition> conditions){
        StringBuilder conditionsSQL = new StringBuilder();
        if (!conditions.isEmpty()){
            conditionsSQL.append(StringPool.SPACE);
            conditionsSQL.append("WHERE");
            conditionsSQL.append(StringPool.SPACE);
            int conditionsSize = conditions.size();
            for (int i = 0; i < conditionsSize; i++) {
                SQLCondition condition = conditions.get(i);
                String field = condition.getField();
                conditionsSQL.append(field);
                if (condition.isNotEq() && condition.getOperator().equals(StringPool.EQUALS)){
                    conditionsSQL.append(StringPool.EXCLAMATION_MARK);
                }
                conditionsSQL.append(condition.getOperator());
                Object value = condition.getValue();
                if (value instanceof String){
                    conditionsSQL.append(StringPool.SINGLE_QUOTE);
                    conditionsSQL.append(value);
                    conditionsSQL.append(StringPool.SINGLE_QUOTE);
                }else {
                    conditionsSQL.append(value);
                }
                if (i != conditionsSize - 1) {
                    conditionsSQL.append(StringPool.SPACE);
                    if (condition.getLogicalSymbol() != SQLLogicalSymbol.END){
                        conditionsSQL.append(condition.getLogicalSymbol().name());
                    }
                    conditionsSQL.append(StringPool.SPACE);
                }else {
                    condition.setLogicalSymbol(SQLLogicalSymbol.END);
                }
            }
        }
        return conditionsSQL.toString();
    }

}
