package plus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import commons.DataSourceFactory;
import flex.entity.FlexAccount;
import flex.mapper.FlexAccountMapper;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import plus.entity.PlusAccount;
import plus.mapper.PlusAccountMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PlusInitializer {

    private static SqlSessionFactory sqlSessionFactory;

    public static void init() {
        DataSource dataSource = DataSourceFactory.getDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("Production", transactionFactory, dataSource);
        MybatisConfiguration configuration = new MybatisConfiguration(environment);
        configuration.addMapper(PlusAccountMapper.class);

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.H2);
        paginationInnerInterceptor.setMaxLimit(20000L);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                return new LongValue(1);
            }

            // 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
            @Override
            public boolean ignoreTable(String tableName) {
                return !"sys_user".equalsIgnoreCase(tableName);
            }
        }));

        configuration.addInterceptor(interceptor);

        sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(configuration);
    }

    public static PlusAccount selectOneWithLambda() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            PlusAccountMapper mapper = sqlSession.getMapper(PlusAccountMapper.class);
            LambdaQueryWrapper<PlusAccount> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.ge(PlusAccount::getId, 100);
            queryWrapper.or();
            queryWrapper.eq(PlusAccount::getUserName, "admin" + ThreadLocalRandom.current().nextInt(10000));
            queryWrapper.last("limit 1");
            return mapper.selectOne(queryWrapper);
        }
    }

    public static PlusAccount selectOne() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            PlusAccountMapper mapper = sqlSession.getMapper(PlusAccountMapper.class);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.ge("id", 100);
            queryWrapper.or();
            queryWrapper.eq("user_name", "admin" + ThreadLocalRandom.current().nextInt(10000));
            queryWrapper.last("limit 1");
            return mapper.selectOne(queryWrapper);
        }
    }


    public static List<PlusAccount> selectTop10WithLambda() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            PlusAccountMapper mapper = sqlSession.getMapper(PlusAccountMapper.class);
            LambdaQueryWrapper<PlusAccount> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.ge(PlusAccount::getId, 100);
            queryWrapper.or();
            queryWrapper.eq(PlusAccount::getUserName, "admin" + ThreadLocalRandom.current().nextInt(10000));
            queryWrapper.last("limit 10");
            return mapper.selectList(queryWrapper);
        }
    }

    public static List<PlusAccount> selectTop10() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            PlusAccountMapper mapper = sqlSession.getMapper(PlusAccountMapper.class);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.ge("id", 100);
            queryWrapper.or();
            queryWrapper.eq("user_name", "admin" + ThreadLocalRandom.current().nextInt(10000));
            queryWrapper.last("limit 10");
            return mapper.selectList(queryWrapper);
        }
    }
    public static List<PlusAccount> selectTop10000WithLambda() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            PlusAccountMapper mapper = sqlSession.getMapper(PlusAccountMapper.class);
            LambdaQueryWrapper<PlusAccount> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.ge(PlusAccount::getId, 100);
            queryWrapper.or();
            queryWrapper.eq(PlusAccount::getUserName, "admin" + ThreadLocalRandom.current().nextInt(10000));
            queryWrapper.last("limit 10000");
            return mapper.selectList(queryWrapper);
        }
    }

    public static List<PlusAccount> selectTop10000() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            PlusAccountMapper mapper = sqlSession.getMapper(PlusAccountMapper.class);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.ge("id", 100);
            queryWrapper.or();
            queryWrapper.eq("user_name", "admin" + ThreadLocalRandom.current().nextInt(10000));
            queryWrapper.last("limit 10000");
            return mapper.selectList(queryWrapper);
        }
    }


    public static Page<PlusAccount> paginate(int page, int pageSize, LambdaQueryWrapper<PlusAccount> queryWrapper) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            PlusAccountMapper mapper = sqlSession.getMapper(PlusAccountMapper.class);

            Page<PlusAccount> p = Page.of(page, pageSize, 20000, false);
            return mapper.selectPage(p, queryWrapper);
        }
    }


    public static void update(PlusAccount plusAccount, LambdaUpdateWrapper<PlusAccount> lambdaUpdateWrapper) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            PlusAccountMapper mapper = sqlSession.getMapper(PlusAccountMapper.class);
            mapper.update(plusAccount, lambdaUpdateWrapper);
        }
    }


}
