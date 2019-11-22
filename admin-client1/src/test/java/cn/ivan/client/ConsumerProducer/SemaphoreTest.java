package cn.ivan.client.ConsumerProducer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 *  信号量测试,计数信号量用来控制同时访问某个特定资源的操作数量，
 *  或者同时执行某个指定操作的数量。信号量还可以用来实现某种资源池，或者对容器施加边界
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        CountDownLatch countDownLatch = new CountDownLatch(10);
        ExecutorService service = Executors.newFixedThreadPool(10);
        long start = System.currentTimeMillis();
        for(int i= 0 ;i<10;i++){
            service.submit(()->{
                try {
                    semaphore.acquire();
                    System.out.println("=========" + Thread.currentThread().getName() + " 执行 。。。。。。");
                    Thread.sleep(1000);
                    semaphore.release();
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时: " + (end - start));
        service.shutdown();
    }

}
