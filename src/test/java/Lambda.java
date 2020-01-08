/**
 * @author EricWei on 2019/12/26
 */
public class Lambda {
    public static void main(String[] args){
        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                System.out.println("多线程启动"+Thread.currentThread().getName());
            }
        }).start();
        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                System.out.println("多线程启动"+Thread.currentThread().getName());
            }
        }).start();
    }

}
