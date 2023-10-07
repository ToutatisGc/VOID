package cn.toutatis.xvoid.sql.base;

import cn.toutatis.xvoid.common.standard.StringPool;

/**
 * 条件部分
 * @author Toutatis_Gc
 */
public class SQLCondition extends SQLPart {

    private SQLLogicalSymbol logicalSymbol = SQLLogicalSymbol.AND;

    private String operator = StringPool.EQUALS;

    private boolean notEq = false;

    public SQLCondition(String field,Object value) {
        this.setField(field);
        this.setValue(value);
    }

    public SQLLogicalSymbol getLogicalSymbol() {
        return logicalSymbol;
    }

    public void setLogicalSymbol(SQLLogicalSymbol logicalSymbol) {
        this.logicalSymbol = logicalSymbol;
    }


    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public boolean isNotEq() {
        return notEq;
    }

    public void setNotEq(boolean notEq) {
        this.notEq = notEq;
    }
}
