package com.javaoffers.brief.modelhelper.speedier.transaction;

import com.javaoffers.brief.modelhelper.exception.BriefTransactionException;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * affairs management
 * @author mingJie
 */
public class SpeedierTransactionManagement implements BriefTransaction {
    /**
     * The data source.
     */
    private TransactionDataSource dataSource;

    public SpeedierTransactionManagement(DataSource dataSource) {
        this.dataSource = new TransactionDataSource(dataSource);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public void openTransaction() {
        dataSource.openTransaction();
    }

    @Override
    public void commitTransaction() {
        dataSource.commitTransaction();
    }

    @Override
    public void rollbackTransaction() {
        dataSource.rollbackTransaction();
    }


    class TransactionDataSource implements DataSource, BriefTransaction{

        /**
         * The data source.
         */
        private DataSource dataSource;

        private InheritableThreadLocal<Connection> tranConnections = new InheritableThreadLocal<>();

        private Map<Connection, Connection> tranMap = new ConcurrentHashMap<>();

        public TransactionDataSource(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        public void openTransaction(){
            Connection connection = null;
            try {
                connection = getConnection();

                tranConnections.set(connection);
                connection.setAutoCommit(false);
                tranMap.put(connection, connection);
            }catch (Exception e){
                e.printStackTrace();
                closeConnection(connection);
                throw new BriefTransactionException(e.getMessage());
            }
        }

        @Override
        public void commitTransaction() {
            Connection connection = null;
            try {
                connection = tranConnections.get();
                if(connection == null){
                    throw new BriefTransactionException("you do not have a transaction open");
                }
                connection.commit();
                connection.setAutoCommit(true);
            }catch (Exception e){
                e.printStackTrace();
                throw new BriefTransactionException(e.getMessage());
            }finally {
                closeConnection(connection);
            }
        }

        @Override
        public void rollbackTransaction() {
            Connection connection = null;
            try {
                connection = tranConnections.get();
                if(connection == null){
                    throw new BriefTransactionException("you do not have a transaction open");
                }
                connection.rollback();
                connection.setAutoCommit(true);
            }catch (Exception e){
                e.printStackTrace();
                throw new BriefTransactionException(e.getMessage());
            }finally {
                closeConnection(connection);
            }
        }

        private void closeConnection(Connection connection){
            try{
                tranConnections.remove();
                if(connection != null){
                    tranMap.remove(connection);
                    if(!connection.isClosed()){
                        connection.close();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                throw new BriefTransactionException(e.getMessage());
            }
        }

        @Override
        public Connection getConnection() throws SQLException {
            Connection connection = tranConnections.get();
            //not null mean the transaction is open
            if(connection != null){
                return connection;
            }
            return this.dataSource.getConnection();
        }

        @Override
        public Connection getConnection(String username, String password) throws SQLException {
            return this.dataSource.getConnection(username, password);
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return this.dataSource.unwrap(iface);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return this.dataSource.isWrapperFor(iface);
        }

        @Override
        public PrintWriter getLogWriter() throws SQLException {
            return this.dataSource.getLogWriter();
        }

        @Override
        public void setLogWriter(PrintWriter out) throws SQLException {
             this.dataSource.setLogWriter(out);
        }

        @Override
        public void setLoginTimeout(int seconds) throws SQLException {
            this.dataSource.setLoginTimeout(seconds);
        }

        @Override
        public int getLoginTimeout() throws SQLException {
            return this.dataSource.getLoginTimeout();
        }

        @Override
        public Logger getParentLogger() throws SQLFeatureNotSupportedException {
            return this.dataSource.getParentLogger();
        }
    }
}
