package cn.ivan.client.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.UUID;

/**
 * 用redis 的 zset 实现延时队列
 *
 * @param <T>
 */
public class RedisDealyQuene<T> {

    static class TaskItem<T> {
        String msgId;
        T msg;
    }

    private Jedis jedis;

    // fastjson 序列化对象中存在 generic 类型时，需要使用 TypeReference
    private Type taskType = new TypeReference<TaskItem<T>>() {
    }.getType();

    private String queneKey;

    public RedisDealyQuene(Jedis jedis, String queneKey) {
        this.jedis = jedis;
        this.queneKey = queneKey;
    }

    public void delay(T msg) {
        TaskItem<T> taskItem = new TaskItem<>();
        // 分配唯一的id,保证value值唯一
        taskItem.msgId = UUID.randomUUID().toString();
        taskItem.msg = msg;
        String value = JSON.toJSONString(taskItem);
        // 塞入延时队列 ,5s 执行
        jedis.zadd(queneKey, System.currentTimeMillis() + 5000, value);
    }

    public void loop() {
        while (!Thread.interrupted()) {
            //取一条
            Set<String> values = jedis.zrangeByScore(queneKey, 0, System.currentTimeMillis(), 0, 1);
            if (values.isEmpty()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
                continue;
            }
            //取一条
            String value = values.iterator().next();
            //删除
            Long zrem = jedis.zrem(queneKey, value);
            // 某个线程抢到了这个任务
            if (zrem > 0) {
                TaskItem<T> taskItem = JSON.parseObject(value, taskType);
                handleMsg(taskItem.msg);
            }
        }
    }

    public void handleMsg(T msg) {
        System.out.println(msg);
    }
}
