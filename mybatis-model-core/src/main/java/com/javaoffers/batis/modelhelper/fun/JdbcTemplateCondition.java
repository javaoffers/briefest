package com.javaoffers.batis.modelhelper.fun;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

/**
 * @author mingJie
 */
public class JdbcTemplateCondition implements Condition {
    private JdbcTemplate template;

    public JdbcTemplateCondition(JdbcTemplate template) {
        this.template = template;
    }

    public JdbcTemplate getTemplate() {
        return template;
    }

    @Override
    public ConditionTag getConditionTag() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getSql() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, Object> getParams() {
        throw new UnsupportedOperationException();
    }
}
