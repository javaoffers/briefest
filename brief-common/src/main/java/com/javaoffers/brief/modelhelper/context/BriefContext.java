package com.javaoffers.brief.modelhelper.context;

import com.javaoffers.brief.modelhelper.filter.JqlExecutorFilter;
import com.javaoffers.brief.modelhelper.jdbc.JdbcExecutorFactory;
import com.javaoffers.brief.modelhelper.mapper.BaseMapper;
import com.javaoffers.brief.modelhelper.parser.StatementParser;
import com.javaoffers.brief.modelhelper.parser.TableInfoParser;
import com.javaoffers.brief.modelhelper.utils.DBType;
import javax.sql.DataSource;
import java.util.List;

/**
 * brief context . 用于初始化化brief启动前的必要信息. 是brief的上下文，代表brief的应用.
 * NOTE: 全局只有一个BriefContext.
 *
 * @author mingJie
 */
public interface BriefContext {

    /**
     * brief的配置信息,存在默认配置+用户配置(用户可自定义brief提供的配置功能).
     * @return
     */
    public BriefProperties getBriefProperties();

    /**
     * 获取执行工厂
     * @return
     */
    public JdbcExecutorFactory getJdbcExecutorFactory();

    /**
     * 缓存BriefMapper
     * @param briefMapper
     * @param <T>
     * @return
     */
    public <T extends BaseMapper> T getBriefMapper(Class<T> briefMapper);

    /**
     * 获取jql拦截器.
     */
    public List<JqlInterceptor> getJqlInterceptors();

    /**
     * 获取jqlExecutorFilters
     * @return
     */
    public List<JqlExecutorFilter> getJqlExecutorFilters();

    /**
     * 获取statement parse
     */
    public StatementParser getStatementParser(DBType dbType);

    /**
     * 获取Table解析器
     * @return
     */
    public TableInfoParser getTableInfoParser();

    /**
     * 获取
     * @return
     */
    public List<DeriveProcess> getDeriveProcess();

    /**
     * 发布 BriefContext.
     * 执行fresh方法后开始生效。实现类要保证每一次fresh都是安全可靠的.
     */
    public void fresh();
}
