import brief.BriefInitializer;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.javaoffers.brief.modelhelper.fun.crud.update.SmartUpdateFun;
import com.mybatisflex.core.datasource.FlexDataSource;
import com.mybatisflex.core.query.QueryWrapper;
import easyquery.EasyQueryInitializer;
import flex.FlexInitializer;
import flex.entity.FlexAccount;
import plus.PlusInitializer;
import plus.entity.PlusAccount;

import static flex.entity.table.Tables.FLEX_ACCOUNT;

public class TestStarter {

    private static final int queryCount = 1000;

    public static void main(String[] args) {

        FlexInitializer.init();
        PlusInitializer.init();
        EasyQueryInitializer.init();
        BriefInitializer.init();


        //预热
        testBriefSelectOne();
        testFlexSelectOne();
        testEasySelectOne();
        testPlusSelectOneWithLambda();
        testPlusSelectOne();

        testBriefPaginate();
        testFlexPaginate();
        testEasyPaginate();
        testPlusPaginate();


        testBriefUpdate();
        testFlexUpdate();
        testEasyUpdate();
        testPlusUpdate();

        long timeMillis;


        System.out.println();
        System.out.println();
        System.out.println("---------------------selectOne:");

        for (int i = 1; i < 10; i++) {
            System.out.println("---------------");
//
//            timeMillis = System.currentTimeMillis();
//            testFlexSelectOne();
//            System.out.println(">>>>>>>testFlexSelectOne:" + (System.currentTimeMillis() - timeMillis));
            timeMillis = System.currentTimeMillis();
            testEasySelectOne();
            System.out.println(">>>>>>>testEasySelectOne:" + (System.currentTimeMillis() - timeMillis));

//            timeMillis = System.currentTimeMillis();
//            testPlusSelectOneWithLambda();
//            System.out.println(">>>>>>>testPlusSelectOneWithLambda:" + (System.currentTimeMillis() - timeMillis));
//
//            timeMillis = System.currentTimeMillis();
//            testPlusSelectOne();
//            System.out.println(">>>>>>>testPlusSelectOne:" + (System.currentTimeMillis() - timeMillis));

            timeMillis = System.currentTimeMillis();
            testBriefSelectOne();
            System.out.println(">>>>>>>testBriefSelectOne:" + (System.currentTimeMillis() - timeMillis));
        }

        System.out.println();
        System.out.println();
        System.out.println("---------------------selectList:");

        for (int i = 10; i < 10; i++) {
            System.out.println("---------------");
//
//            timeMillis = System.currentTimeMillis();
//            testFlexSelectTop10();
//            System.out.println(">>>>>>>testFlexSelectTop10:" + (System.currentTimeMillis() - timeMillis));
            timeMillis = System.currentTimeMillis();
            testEasySelectTop10();
            System.out.println(">>>>>>>testEasySelectTop10:" + (System.currentTimeMillis() - timeMillis));
//
//            timeMillis = System.currentTimeMillis();
//            testPlusSelectTop10WithLambda();
//            System.out.println(">>>>>>>testPlusSelectTop10WithLambda:" + (System.currentTimeMillis() - timeMillis));
//
//            timeMillis = System.currentTimeMillis();
//            testPlusSelectTop10();
//            System.out.println(">>>>>>>testPlusSelectTop10:" + (System.currentTimeMillis() - timeMillis));

            timeMillis = System.currentTimeMillis();
            testBriefSelectTop10();
            System.out.println(">>>>>>>testBriefSelectTop10:" + (System.currentTimeMillis() - timeMillis));
        }


        System.out.println();
        System.out.println();
        System.out.println("---------------------selectList1W:");

        for (int i = 1; i < 10; i++) {
            System.out.println("---------------");

            timeMillis = System.currentTimeMillis();
            testFlexSelectTop10000();
            System.out.println(">>>>>>>testFlexSelectTop10000:" + (System.currentTimeMillis() - timeMillis));
            timeMillis = System.currentTimeMillis();
            testEasySelectTop10000();
            System.out.println(">>>>>>>testEasySelectTop10000:" + (System.currentTimeMillis() - timeMillis));

            timeMillis = System.currentTimeMillis();
            testPlusSelectTop10000WithLambda();
            System.out.println(">>>>>>>testPlusSelectTop10000WithLambda:" + (System.currentTimeMillis() - timeMillis));

            timeMillis = System.currentTimeMillis();
            testPlusSelectTop10000();
            System.out.println(">>>>>>>testPlusSelectTop10000:" + (System.currentTimeMillis() - timeMillis));

            timeMillis = System.currentTimeMillis();
            testBriefSelectTop10000();
            System.out.println(">>>>>>>testBriefSelectTop10000:" + (System.currentTimeMillis() - timeMillis));
        }


        System.out.println();
        System.out.println();
        System.out.println("---------------------paginate:");

        for (int i = 0; i < 10; i++) {
            System.out.println("---------------");

            timeMillis = System.currentTimeMillis();
            testFlexPaginate();
            System.out.println(">>>>>>>testFlexPaginate:" + (System.currentTimeMillis() - timeMillis));
            timeMillis = System.currentTimeMillis();
            testEasyPaginate();
            System.out.println(">>>>>>>testEasyPaginate:" + (System.currentTimeMillis() - timeMillis));

            timeMillis = System.currentTimeMillis();
            testPlusPaginate();
            System.out.println(">>>>>>>testPlusPaginate:" + (System.currentTimeMillis() - timeMillis));

            timeMillis = System.currentTimeMillis();
            testBriefPaginate();
            System.out.println(">>>>>>>testBriefPaginate:" + (System.currentTimeMillis() - timeMillis));
        }


        System.out.println();
        System.out.println();
        System.out.println("---------------------update:");

        for (int i = 0; i < 10; i++) {
            System.out.println("---------------");

            timeMillis = System.currentTimeMillis();
            testFlexUpdate();
            System.out.println(">>>>>>>testFlexUpdate:" + (System.currentTimeMillis() - timeMillis));
            timeMillis = System.currentTimeMillis();
            testEasyUpdate();
            System.out.println(">>>>>>>testEasyUpdate:" + (System.currentTimeMillis() - timeMillis));

            timeMillis = System.currentTimeMillis();
            testPlusUpdate();
            System.out.println(">>>>>>>testPlusUpdate:" + (System.currentTimeMillis() - timeMillis));

            timeMillis = System.currentTimeMillis();
            testBriefUpdate();
            System.out.println(">>>>>>>testBriefUpdate:" + (System.currentTimeMillis() - timeMillis));
        }
    }


    private static void testFlexSelectOne() {
        for (int i = 0; i < queryCount; i++) {
            FlexInitializer.selectOne();
        }
    }
    private static void testBriefSelectOne() {
        for (int i = 0; i < queryCount; i++) {
            BriefInitializer.selectOne();
        }
    }
    private static void testEasySelectOne() {
        for (int i = 0; i < queryCount; i++) {
            EasyQueryInitializer.selectOne();
        }
    }

    private static void testPlusSelectOneWithLambda() {
        for (int i = 0; i < queryCount; i++) {
            PlusInitializer.selectOneWithLambda();
        }
    }

    private static void testPlusSelectOne() {
        for (int i = 0; i < queryCount; i++) {
            PlusInitializer.selectOne();
        }
    }


    private static void testFlexSelectTop10() {
        for (int i = 0; i < queryCount; i++) {
            FlexInitializer.selectTop10();
        }
    }
    private static void testEasySelectTop10() {
        for (int i = 0; i < queryCount; i++) {
            EasyQueryInitializer.selectTop10();
        }
    }

    private static void testPlusSelectTop10WithLambda() {
        for (int i = 0; i < queryCount; i++) {
            PlusInitializer.selectTop10WithLambda();
        }
    }

    private static void testPlusSelectTop10() {
        for (int i = 0; i < queryCount; i++) {
            PlusInitializer.selectTop10();
        }
    }

    private static void testBriefSelectTop10() {
        for (int i = 0; i < queryCount; i++) {
            BriefInitializer.selectTop10();
        }
    }

    private static void testFlexSelectTop10000() {
        for (int i = 0; i < queryCount; i++) {
            FlexInitializer.selectTop10000();
        }
    }
    private static void testEasySelectTop10000() {
        for (int i = 0; i < queryCount; i++) {
            EasyQueryInitializer.selectTop10000();
        }
    }

    private static void testPlusSelectTop10000WithLambda() {
        for (int i = 0; i < queryCount; i++) {
            PlusInitializer.selectTop10000WithLambda();
        }
    }

    private static void testPlusSelectTop10000() {
        for (int i = 0; i < queryCount; i++) {
            PlusInitializer.selectTop10000();
        }
    }

    private static void testBriefSelectTop10000() {
        for (int i = 0; i < queryCount; i++) {
            BriefInitializer.selectTop10000();
        }
    }


    private static void testFlexPaginate() {
        for (int i = 1; i <= queryCount; i++) {
            QueryWrapper queryWrapper = new QueryWrapper()
                    .where(FLEX_ACCOUNT.ID.ge(100));
            FlexInitializer.paginate(1, 10, queryWrapper);
        }
    }

    private static void testBriefPaginate() {
        for (int i = 1; i <= queryCount; i++) {
            BriefInitializer.paginate(1, 10);
        }
    }


    private static void testEasyPaginate() {
        for (int i = 1; i <= queryCount; i++) {
            EasyQueryInitializer.paginate(1,10);
        }
    }

    private static void testPlusPaginate() {
        for (int i = 1; i <= queryCount; i++) {
            LambdaQueryWrapper<PlusAccount> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.ge(PlusAccount::getId, 100);
            PlusInitializer.paginate(1, 10, queryWrapper);
        }
    }


    private static void testFlexUpdate() {
        for (long i = 0; i < queryCount; i++) {
            FlexAccount flexAccount = new FlexAccount();
            flexAccount.setUserName("testInsert" + i);
            flexAccount.setNickname("testInsert" + i);

            QueryWrapper queryWrapper = QueryWrapper.create()
                    .where(FLEX_ACCOUNT.ID.ge(9200))
                    .and(FLEX_ACCOUNT.ID.le(9300))
                    .and(FLEX_ACCOUNT.USER_NAME.like("admin"))
                    .and(FLEX_ACCOUNT.NICKNAME.like("admin"));

            FlexInitializer.update(flexAccount, queryWrapper);
        }
    }

    private static void testEasyUpdate() {
        for (long i = 0; i < queryCount; i++) {
            FlexAccount flexAccount = new FlexAccount();
            flexAccount.setUserName("testInsert" + i);
            flexAccount.setNickname("testInsert" + i);

            EasyQueryInitializer.update("testInsert" + i,"testInsert" + i);
        }
    }

    private static void testBriefUpdate() {
        SmartUpdateFun update = null;
        for (long i = 0; i < queryCount; i++) {
            FlexAccount flexAccount = new FlexAccount();
            flexAccount.setUserName("testInsert" + i);
            flexAccount.setNickname("testInsert" + i);
            update = BriefInitializer.update(update, "testInsert" + i, "testInsert" + i, i== (queryCount-1));
        }

    }


    private static void testPlusUpdate() {
        for (int i = 0; i < queryCount; i++) {
            PlusAccount plusAccount = new PlusAccount();
            plusAccount.setUserName("testInsert" + i);
            plusAccount.setNickname("testInsert" + i);

            LambdaUpdateWrapper<PlusAccount> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.ge(PlusAccount::getId, 9000);
            updateWrapper.le(PlusAccount::getId, 9100);
            updateWrapper.like(PlusAccount::getUserName, "admin");
            updateWrapper.like(PlusAccount::getNickname, "admin");
            PlusInitializer.update(plusAccount, updateWrapper);
        }
    }


}
