package su.grinev;


class Test {
    private int param1;
    private int param2;
    private int param3;

    public Test(int param1, int param2, int param3) {
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
    }

    public void setAll(int param1, int param2, int param3) {
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
    }

    public int getParam1() {
        return param1;
    }

    public void setParam1(int param1) {
        this.param1 = param1;
    }

    public int getParam2() {
        return param2;
    }

    public void setParam2(int param2) {
        this.param2 = param2;
    }

    public int getParam3() {
        return param3;
    }

    public void setParam3(int param3) {
        this.param3 = param3;
    }

    @Override
    public String toString() {
        return "test{" +
                "param1=" + param1 +
                ", param2=" + param2 +
                ", param3=" + param3 +
                '}';
    }
}

public class Main {


    public static void main(String[] args) throws InterruptedException {
        ObjectPool objectPool = new ObjectPool(100000);
        for (int i = 0; i != 200000; i++) {
            Test test = new Test(1, 2, 3);
            objectPool.putObject(test);
        }

        Long time = System.currentTimeMillis();
        for (int i = 0; i != 10000; i++) {
            Test test = new Test(1, 2, 3);
            test.toString();
        }
        System.out.println("Create object execution time: " + (System.currentTimeMillis() - time) + " ms");


        time = System.currentTimeMillis();
        for (int i = 0; i != 10000; i++) {
            Test t = (Test) objectPool.acquireObject(Test.class);
            t.setParam1(1);
            t.setParam2(2);
            t.setParam3(3);
            t.toString();
            objectPool.releaseObject(t);
        }
        System.out.println("Reuse object execution time: " + (System.currentTimeMillis() - time) + " ms");
       /*
        CustomThreadPool customThreadPool=new CustomThreadPool(4);


        for (int i=0;i!=10;i++){
            int finalI = i;
            customThreadPool.enqueueTask(() -> {
                int a =finalI*finalI*finalI;
                int b=0;
                for (int tT = 0; tT != a; tT++) {
                    b = b + tT;
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Task " + a + " " + finalI);
            });
        }
        customThreadPool.startExecutor();
        customThreadPool.waitForComplete();
       // customThreadPool.stopExecutor();

*/
    }
}
