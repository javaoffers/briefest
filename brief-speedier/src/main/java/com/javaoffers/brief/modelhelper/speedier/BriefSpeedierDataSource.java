package com.javaoffers.brief.modelhelper.speedier;

import com.javaoffers.brief.modelhelper.exception.ParseDataSourceException;
import com.mchange.v2.c3p0.AbstractComboPooledDataSource;

import javax.naming.Referenceable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class BriefSpeedierDataSource extends AbstractComboPooledDataSource implements Serializable, Referenceable
{
    public BriefSpeedierDataSource()
    { super(); }

    public BriefSpeedierDataSource(boolean autoregister )
    { super( autoregister ); }

    public BriefSpeedierDataSource(String configName)
    { super( configName );  }


    // serialization stuff -- set up bound/constrained property event handlers on deserialization
    private static final long serialVersionUID = 1;
    private static final short VERSION = 0x0002;

    private void writeObject( ObjectOutputStream oos ) throws IOException
    {
        oos.writeShort( VERSION );
    }

    private void readObject( ObjectInputStream ois ) throws IOException, ClassNotFoundException
    {
        short version = ois.readShort();
        switch (version)
        {
            case VERSION:
                //ok
                break;
            default:
                throw new IOException("Unsupported Serialized Version: " + version);
        }
    }

    public static BriefSpeedierDataSource getInstance(String driver, String jdbcUrl, String user, String password, int maxPoolSize, int minPoolSize){
        BriefSpeedierDataSource briefSpeedierDataSource = new BriefSpeedierDataSource();
        try {
            briefSpeedierDataSource.setDriverClass( driver); //loads the jdbc driver
            briefSpeedierDataSource.setJdbcUrl( jdbcUrl );
            briefSpeedierDataSource.setUser(user);
            briefSpeedierDataSource.setPassword(password);
            briefSpeedierDataSource.setMaxPoolSize(maxPoolSize);
            briefSpeedierDataSource.setMinPoolSize(minPoolSize);
        }catch (Exception e){
            throw new ParseDataSourceException("Data creation failed", e);
        }

        return briefSpeedierDataSource;
    }

    public static BriefSpeedierDataSource getInstance(String driver, String jdbcUrl, String user, String password){
       return getInstance(driver, jdbcUrl, user, password, 5, 1);
    }
}
