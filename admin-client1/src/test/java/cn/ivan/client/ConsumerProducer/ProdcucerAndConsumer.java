package cn.ivan.client.ConsumerProducer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ProdcucerAndConsumer {

    private BlockingQueue<Task<String>> queue = new ArrayBlockingQueue<>(10);

    public void producer(Task<String> task){
        try {
            queue.put(task);
            System.out.println("生产任务:任务ID:" + task.getTaskId() + " 任务内容 : " + task.getT());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void consumer(){
        try {
            Task<String> take = queue.take();
            System.out.println("消费任务:任务ID:" + take.getTaskId() + " 任务内容 : " + take.getT());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ProdcucerAndConsumer prodcucerAndConsumer = new ProdcucerAndConsumer();
        AtomicInteger atomicInteger = new AtomicInteger();
        //生成者线程
        new Thread(() -> {
            for(int i = 0; i< 100 ; i++){
                Task<String> task = new Task<>();
                int taskId = atomicInteger.incrementAndGet();
                task.setTaskId(taskId);
                task.setT("生成的第 " + taskId + "条任务");
                prodcucerAndConsumer.producer(task);
            }
        }).start();
        //消费者线程
        new Thread(()->{
            while (true){
                prodcucerAndConsumer.consumer();
            }
        }).start();
    }



}

class Task<T>{
    private T t;
    private Integer taskId;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
