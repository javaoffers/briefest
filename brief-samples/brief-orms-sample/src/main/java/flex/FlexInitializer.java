package flex;

import com.mybatisflex.core.MybatisFlexBootstrap;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.audit.ConsoleMessageCollector;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import commons.DataSourceFactory;
import flex.entity.FlexAccount;
import flex.mapper.FlexAccountMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.sql.DataSource;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static flex.entity.table.Tables.FLEX_ACCOUNT;

public class FlexInitializer {

    private static SqlSessionFactory sqlSessionFactory;

    public static void init() {
        DataSource dataSource = DataSourceFactory.getDataSource();
        MybatisFlexBootstrap.getInstance()
                .setDataSource(dataSource)
                .addMapper(FlexAccountMapper.class)
                .start();

//        AuditManager.setAuditEnable(true);
//        AuditManager.setMessageCollector(new ConsoleMessageCollector());

        sqlSessionFactory = MybatisFlexBootstrap.getInstance().getSqlSessionFactory();
    }


    public static FlexAccount selectOne() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            FlexAccountMapper mapper = sqlSession.getMapper(FlexAccountMapper.class);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.where(FLEX_ACCOUNT.ID.ge(100)
                    .or(FLEX_ACCOUNT.USER_NAME.eq("admin" + ThreadLocalRandom.current().nextInt(10000))));
            return mapper.selectOneByQuery(queryWrapper);
        }
    }


    public static List<FlexAccount> selectTop10() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            FlexAccountMapper mapper = sqlSession.getMapper(FlexAccountMapper.class);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.where(FLEX_ACCOUNT.ID.ge(100).or(FLEX_ACCOUNT.USER_NAME
                            .eq("admin" + ThreadLocalRandom.current().nextInt(10000))))
                    .limit(10);
            return mapper.selectListByQuery(queryWrapper);
        }
    }
    public static List<FlexAccount> selectTop10000() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            FlexAccountMapper mapper = sqlSession.getMapper(FlexAccountMapper.class);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.where(FLEX_ACCOUNT.ID.ge(100).or(FLEX_ACCOUNT.USER_NAME
                            .eq("admin" + ThreadLocalRandom.current().nextInt(10000))))
                    .limit(10000);
            return mapper.selectListByQuery(queryWrapper);
        }
    }

    public static Page<FlexAccount> paginate(int page, int pageSize, QueryWrapper queryWrapper) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            FlexAccountMapper mapper = sqlSession.getMapper(FlexAccountMapper.class);
            return mapper.paginate(page, pageSize, 20000, queryWrapper);
        }
    }


    public static void update(FlexAccount flexAccount, QueryWrapper queryWrapper) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            FlexAccountMapper mapper = sqlSession.getMapper(FlexAccountMapper.class);
            mapper.updateByQuery(flexAccount, queryWrapper);
        }
    }


}
