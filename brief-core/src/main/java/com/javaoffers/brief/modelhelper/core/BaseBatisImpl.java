package com.javaoffers.brief.modelhelper.core;

import com.javaoffers.brief.modelhelper.convert.Serializable2IdConvert;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.parse.ModelParseUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.InterruptibleBatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterDisposer;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Description: core implementation class
 * @Auther: create by cmj on 2022/05/22 02:56
 */
public class BaseBatisImpl<T, ID> implements BaseBatis<T, ID> {

    private static final Object[] EMPTY = new Object[0];

    private JdbcTemplate jdbcTemplate;

    public static <T, ID> BaseBatis getInstance(HeadCondition headCondition) {
        return getInstance(headCondition.getTemplate(), headCondition.getModelClass());
    }

    private static <T, ID> BaseBatis getInstance(JdbcTemplate jdbcTemplate, Class mClass) {
        BaseBatisImpl batis = new BaseBatisImpl<>(jdbcTemplate);
        return new BaseBatisImplProxy(batis, mClass);
    }

    private BaseBatisImpl(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.setFetchSize(10000);
        this.jdbcTemplate = jdbcTemplate;
    }

    /****************************crud****************************/
    public int saveData(String sql) {
        return saveData(sql, Collections.EMPTY_MAP);
    }

    @Override
    public int saveData(String sql, Map<String, Object> map) {
        SQL sql_ = SQLParse.getSQL(sql, map);
        return this.jdbcTemplate.update(sql_.getSql(), new ArgumentPreparedStatementSetter(sql_.getArgsParam().get(0)));
    }

    public int deleteData(String sql) {
        return deleteData(sql, Collections.EMPTY_MAP);
    }

    @Override
    public int deleteData(String sql, Map<String, Object> map) {
        SQL sql_ = SQLParse.getSQL(sql, map);
        return this.jdbcTemplate.update(sql_.getSql(), new ArgumentPreparedStatementSetter(sql_.getArgsParam().get(0)));
    }

    public int updateData(String sql) {
        return updateData(sql, Collections.EMPTY_MAP);
    }

    @Override
    public int updateData(String sql, Map<String, Object> map) {
        SQL sql_ = SQLParse.getSQL(sql, map);
        return this.jdbcTemplate.update(sql_.getSql(), new ArgumentPreparedStatementSetter(sql_.getArgsParam().get(0)));
    }

    public List<Map<String, Object>> queryData(String sql) {
        List<Map<String, Object>> queryForList = queryData(sql, EMPTY);
        return queryForList;
    }

    @Override
    public List<Map<String, Object>> queryData(String sql, Map<String, Object> map) {
        SQL batchSQL = SQLParse.getSQL(sql, map);
        long start = System.currentTimeMillis();
        List<Map<String, Object>> result = queryData(batchSQL.getSql(), batchSQL.getArgsParam().get(0));
        long end = System.currentTimeMillis();
        System.out.println("C耗时 : "+(end-start));
        return result;
    }

    /*********************************Support Model*********************************/
    public <E> List<E> queryDataForT(String sql, Class<E> clazz) {
        List<Map<String, Object>> list_map = queryData(sql, EMPTY);
        List<E> list = ModelParseUtils.converterMap2Model(clazz, list_map);
        return list;
    }

    @Override
    public <E> List<E> queryDataForT4(String sql, Map<String, Object> paramMap, Class<E> clazz) {
        long start1 = System.nanoTime();
        List<Map<String, Object>> maps = queryData(sql, paramMap);
        long start = System.nanoTime();
        System.out.println("A耗时："+ TimeUnit.NANOSECONDS.toMillis(start - start1));
        List<Map<String, Object>> tmp =  new ArrayList<>();
        tmp.add(maps.get(0));
        List<E> es = ModelParseUtils.converterMap2Model(clazz, tmp);
        long end = System.nanoTime();
        System.out.println("B耗时："+ TimeUnit.NANOSECONDS.toMillis(end - start));
        return es;
    }

    /*********************************batch processing*********************************/
    public Integer batchUpdate(String sql, List<Map<String, Object>> paramMap) {
        SQL batchSQL = SQLParse.parseSqlParams(sql, paramMap);
        int[] is = batchUpdate(batchSQL.getSql(), batchSQL);

        Assert.isTrue(is != null, " batch update is null ");
        AtomicInteger countSuccess = new AtomicInteger();
        Arrays.stream(is).forEach(countSuccess::addAndGet);
        return Integer.valueOf(countSuccess.get());
    }

    @Override
    public List<Serializable> batchInsert(String sql, List<Map<String, Object>> paramMap) {

        SQL pss = SQLParse.parseSqlParams(sql, paramMap);
        List<Serializable> ids = new ArrayList<>();
        jdbcTemplate.execute(new InsertPreparedStatementCreator(pss.getSql()), (PreparedStatementCallback<List<Serializable>>) ps -> {
            try {
                int batchSize = pss.getBatchSize();
                InterruptibleBatchPreparedStatementSetter ipss =
                        (pss instanceof InterruptibleBatchPreparedStatementSetter ?
                                (InterruptibleBatchPreparedStatementSetter) pss : null);
                Connection connection = ps.getConnection();
                if (JdbcUtils.supportsBatchUpdates(connection) && batchSize > 0) {
                    boolean oldAutoCommit = connection.getAutoCommit();
                    connection.setAutoCommit(false);
                    try {
                        for (int i = 0; i < batchSize; i++) {
                            pss.setValues(ps, i);
                            if (ipss != null && ipss.isBatchExhausted(i)) {
                                break;
                            }
                            ps.addBatch();
                        }

                        ps.executeBatch();
                        //Avoid transactional inconsistencies
                        if(oldAutoCommit){
                            connection.commit();
                        }
                    }catch (Exception e){
                        throw e;
                    }finally {
                        connection.setAutoCommit(oldAutoCommit);
                    }

                } else {
                    List<Integer> rowsAffected = new ArrayList<>();
                    for (int i = 0; i < batchSize; i++) {
                        pss.setValues(ps, i);
                        if (ipss != null && ipss.isBatchExhausted(i)) {
                            break;
                        }
                        rowsAffected.add(ps.executeUpdate());
                    }
                    int[] rowsAffectedArray = new int[rowsAffected.size()];
                    for (int i = 0; i < rowsAffectedArray.length; i++) {
                        rowsAffectedArray[i] = rowsAffected.get(i);
                    }

                }

                int i = 0;
                ResultSet rs = ps.getGeneratedKeys();
                while (rs.next() && i < batchSize) {
                    Object object = rs.getObject(1);
                    ids.add(Serializable2IdConvert.newId((Serializable) object));
                    i++;
                }
                return ids;
            } finally {
                if (pss instanceof ParameterDisposer) {
                    ((ParameterDisposer) pss).cleanupParameters();
                }
            }
        });

        return ids;
    }

    /**
     * Basic query implementation
     *
     * @param nativeSql sql
     * @param param     parameter
     * @return
     */
    private List<Map<String, Object>> queryData(String nativeSql, Object[] param) {
        RowMapperResultSetExtractorPlus<Map<String, Object>> rowMapperResultSetExtractorPlus =
                new RowMapperResultSetExtractorPlus(new ColumnMapRowMapper() {
                    String[] names = null;
                    int columnCount = 0;
                    @Override
                    public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Map<String, Object> mapOfColumnValues = new HashMap<>(columnCount);
                        if(rowNum == 0){
                            ResultSetMetaData rsmd = rs.getMetaData();
                            columnCount = rsmd.getColumnCount();
                            names = new String[columnCount];
                            for (int i = 1, j = 0; i <= columnCount; i++, j++) {
                                String name = rsmd.getColumnLabel(i);
                                names[j] = name;
                                mapOfColumnValues.put( name, getColumnValue(rs, i));
                            }
                            return mapOfColumnValues;
                        }else{
//                            if(rowNum > 2){
//                                return new HashMap<>();
//                            }
                            for (int i = 1, j = 0 ; i <= columnCount; i++,j++) {
                                String name = names[j];
                                Object columnValue = rs.getObject(i);
                                //mapOfColumnValues.put( names[j], rs.getObject(i));
                            }
                        }



                        return mapOfColumnValues;
                    }
                });
        return this.jdbcTemplate.query(nativeSql, param, rowMapperResultSetExtractorPlus);
    }

   private int[] batchUpdate(String sql, final BatchPreparedStatementSetter pss) throws DataAccessException {

        int[] result = this.jdbcTemplate.execute(sql, (PreparedStatementCallback<int[]>) ps -> {
            try {
                int batchSize = pss.getBatchSize();
                InterruptibleBatchPreparedStatementSetter ipss =
                        (pss instanceof InterruptibleBatchPreparedStatementSetter ?
                                (InterruptibleBatchPreparedStatementSetter) pss : null);
                Connection connection = ps.getConnection();
                if (JdbcUtils.supportsBatchUpdates(connection)) {
                    boolean oldAutoCommit = connection.getAutoCommit();
                    connection.setAutoCommit(false);
                    try {
                        for (int i = 0; i < batchSize; i++) {
                            pss.setValues(ps, i);
                            if (ipss != null && ipss.isBatchExhausted(i)) {
                                break;
                            }
                            ps.addBatch();
                        }
                        int[] ints = ps.executeBatch();
                        //Avoid transactional inconsistencies
                        if(oldAutoCommit){
                            connection.commit();
                        }
                        return ints;
                    }catch (Exception e){
                        throw e;
                    }finally {
                        connection.setAutoCommit(oldAutoCommit);
                    }
                }
                else {
                    List<Integer> rowsAffected = new ArrayList<>();
                    for (int i = 0; i < batchSize; i++) {
                        pss.setValues(ps, i);
                        if (ipss != null && ipss.isBatchExhausted(i)) {
                            break;
                        }
                        rowsAffected.add(ps.executeUpdate());
                    }
                    int[] rowsAffectedArray = new int[rowsAffected.size()];
                    for (int i = 0; i < rowsAffectedArray.length; i++) {
                        rowsAffectedArray[i] = rowsAffected.get(i);
                    }
                    return rowsAffectedArray;
                }
            }
            finally {
                if (pss instanceof ParameterDisposer) {
                    ((ParameterDisposer) pss).cleanupParameters();
                }
            }
        });

        Assert.state(result != null, "No result array");
        return result;
    }

}
