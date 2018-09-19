package com.nuls.io.model.common;

/**
 * 查询操作符枚举
 *
 * @author hhu 【huan.hu@cnambition.com】
 * @version com.ambition.uis.model.common, v 0.1 2017/11/17 16:04 hhu Exp $$
 */
public enum OperatorEnum {

    /**
     *
     */
    EQ("eq", "$col1$ = $ex1$"),
    NE("ne", "$col1$ <> $ex1$"),
    LT("lt", "$col1$ < $ex1$"),
    LE("le", "$col1$ <= $ex1$"),
    GT("gt", "$col1$ > $ex1$"),
    GE("ge", "$col1$ >= $ex1$"),
    IN("in", "$col1$ in ($ex1$)"),
    EXACT_LIKE("like", "$col1$ like \'$ex1$\'"),
    END_LIKE("like", "$col1$ like \'%$ex1$\'"),
    START_LIKE("like", "$col1$ like \'$ex1$%\'"),
    ANYWHERE_LIKE("like", "$col1$ like \'%$ex1$%\'"),
    EXISTS("exists", "$col1$ exists ($sub_query$)"),
    BETWEEN("between", "$col1$ between $ex1$ and $ex2$"),
    NULL("isNull", "$col1$ is null"),
    NOT_NULL("isNotNull", "$col1$ is not null");


    private String value;
    private String operator;

    OperatorEnum(String value, String operator) {
        this.value = value;
        this.operator = operator;
    }

    public String value() {
        return value;
    }

    public String operator() {
        return operator;
    }

}
