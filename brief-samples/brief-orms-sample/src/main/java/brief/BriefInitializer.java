package brief;

import brief.entity.BriefAccount;
import com.javaoffers.brief.modelhelper.mapper.BriefMapper;
import com.javaoffers.brief.modelhelper.speedier.BriefSpeedier;
import commons.DataSourceFactory;
import easyquery.entity.EasyQueryAccount;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BriefInitializer {

    private static BriefMapper<BriefAccount> briefMapper;

    public static void init() {
        DataSource dataSource = DataSourceFactory.getDataSource();
        BriefSpeedier speedier = BriefSpeedier.getInstance(dataSource);
        briefMapper = speedier.newDefaultCrudMapper(BriefAccount.class);
    }


    public static BriefAccount selectOne() {
        return briefMapper.general().query(1,1).get(0);
    }


    public static List<BriefAccount> selectTop10() {
        return briefMapper.general().query(1,10);
    }
    public static List<BriefAccount> selectTop10000() {
        return briefMapper.select().colAll().where().gtEq(BriefAccount::getId, 100).or().eq(BriefAccount::getUserName,
                "admin" + ThreadLocalRandom.current().nextInt(10000)).limitPage(1, 10000).exs();
    }

    public static List<BriefAccount> paginate(int page, int pageSize) {
        return briefMapper.select().colAll().where().gtEq(BriefAccount::getId, 100).limitPage(page, pageSize).exs();
    }


    public static void update(String userName,String nickname) {
        briefMapper.update().npdateNull()
                .col(BriefAccount::getUserName, userName)
                .col(BriefAccount::getNickname, nickname)
                .where()
                .gtEq(BriefAccount::getId, 9000).ltEq(BriefAccount::getId, 9100)
                .like(BriefAccount::getUserName, "admin")
                .like(BriefAccount::getNickname,"admin")
                .ex();
    }


}
