package com.javaoffers.brief.modelhelper.core;

public interface Limit {
    public int pageNum();

    public int pageSize();

    public int startIndex();

    public String cleanLimit(String sql);
}
