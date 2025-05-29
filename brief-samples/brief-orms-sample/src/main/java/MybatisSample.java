import plus.PlusInitializer;

public class MybatisSample {

    public static void main(String[] args) {
        PlusInitializer.init();
        //预热
        System.out.println(">>>>");
        TestStarter.testPlusSelectTop10000();
        TestStarter.testPlusSelectMapTop10000();

        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            TestStarter.testPlusSelectTop10000();
            System.out.println(">>>>> testPlusSelectTop10000 cost " + (System.currentTimeMillis() - start)+" ms");
            start = System.currentTimeMillis();
            TestStarter.testPlusSelectMapTop10000();
            System.out.println(">>>>> testPlusSelectMapTop10000 cost " + (System.currentTimeMillis() - start)+" ms");
        }

        /**
         * >>>>> testPlusSelectTop10000 cost 9273 ms
         * >>>>> testPlusSelectMapTop10000 cost 3057 ms
         * >>>>> testPlusSelectTop10000 cost 9027 ms
         * >>>>> testPlusSelectMapTop10000 cost 3124 ms
         * >>>>> testPlusSelectTop10000 cost 9118 ms
         * >>>>> testPlusSelectMapTop10000 cost 2961 ms
         * >>>>> testPlusSelectTop10000 cost 9105 ms
         * >>>>> testPlusSelectMapTop10000 cost 2961 ms
         * >>>>> testPlusSelectTop10000 cost 8790 ms
         * >>>>> testPlusSelectMapTop10000 cost 2977 ms
         * >>>>> testPlusSelectTop10000 cost 8960 ms
         * >>>>> testPlusSelectMapTop10000 cost 2971 ms
         * >>>>> testPlusSelectTop10000 cost 9097 ms
         * >>>>> testPlusSelectMapTop10000 cost 3013 ms
         * >>>>> testPlusSelectTop10000 cost 9396 ms
         * >>>>> testPlusSelectMapTop10000 cost 3015 ms
         * >>>>> testPlusSelectTop10000 cost 9380 ms
         * >>>>> testPlusSelectMapTop10000 cost 3000 ms
         * >>>>> testPlusSelectTop10000 cost 9144 ms
         * >>>>> testPlusSelectMapTop10000 cost 3015 ms
         */

    }


}
