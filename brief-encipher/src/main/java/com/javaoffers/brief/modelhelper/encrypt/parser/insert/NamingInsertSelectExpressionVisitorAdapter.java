package com.javaoffers.brief.modelhelper.encrypt.parser.insert;

import com.javaoffers.brief.modelhelper.encrypt.parser.ConditionName;
import com.javaoffers.brief.modelhelper.encrypt.parser.NamingSelectContent;
import com.javaoffers.brief.modelhelper.encrypt.parser.NamingSelectExpressionVisitorAdapter;
import com.javaoffers.thrid.jsqlparser.schema.Column;
import com.javaoffers.thrid.jsqlparser.statement.select.SelectBody;
import com.javaoffers.thrid.jsqlparser.statement.select.SubSelect;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 针对于 insert xx select xx , (select xx from ) xx.  在插入的子查询中又包含子查询. 所有的子查询数据保持加密. where 部分解密
 * @author mingJie
 */
public class NamingInsertSelectExpressionVisitorAdapter extends NamingSelectExpressionVisitorAdapter {

    private NamingSelectContent namingContent;

    private ConditionName conditionName;

    public NamingInsertSelectExpressionVisitorAdapter(ConditionName name, NamingSelectContent namingContent) {
        super(name, namingContent);
        this.namingContent = namingContent;
        this.conditionName = name;
    }

    /**
     * select columns, where columns
     *  这个方法代表真实的表字段.
     */
    public void visit(Column column) {
        //insert 中的子查询 column 不允许解密. 因此跳过
        if(ConditionName.SELECT_COL_NAME == conditionName){
            return;
        }
        super.visit(column);
    }

    /**
     * 子查询 处理
     */
    @Override
    public void visit(SubSelect subSelect) {
        SelectBody selectBody = subSelect.getSelectBody();
        NamingSelectContent namingContent = new NamingSelectContent();
        namingContent.setSubSelct(true);
        Map<String, String> tableNameMapper = this.namingContent.getTableNameMapper();
        tableNameMapper.forEach((k, v) -> {
            if (StringUtils.isNotBlank(k)) {
                namingContent.getTableNameMapper().put(k, v);
            }
        });
        namingContent.setProcessor(this.namingContent.getProcessor());
        //保持select 部分加密(即不做解密处理), where 解密
        selectBody.accept(new NamingInsertVisitorAdapter(namingContent));
    }
}
