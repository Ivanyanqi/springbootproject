package cn.ivan.zookeeper.demo;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 由于Zookeeper客户端和服务器创建会话是异步过程，因此使用CountDownLatch来阻塞线程，等待服务器创建完成，并发送事件通知
 * @author yanqi
 * @version 1.0
 */

public class DirectApiTest implements Watcher {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        long startTime = System.currentTimeMillis();
        /**
         *   参数说明:
         *   connectString:服务器地址列表 ip:port 支持多个地址，用逗号拼接
         *   sessionTimeout:会话超时时间，单位毫秒。通过心跳来检测会话的有效性,只要连接没断开，会话一直有效
         *   watcher:监听节点的状态变化,如果发生变化则通知watcher,做出相应处理.如果不需要监听，则设置为null
         */
        zooKeeper = new ZooKeeper("localhost:2181", 40000, new DirectApiTest());
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("创建连接花费时间：" + (System.currentTimeMillis() - startTime) + "ms");
        System.out.println("连接状态：" + zooKeeper.getState());
        //同步创建临时节点:
        // 临时节点不能创建子节点
        String elemeentPath = zooKeeper.create("/zk-java-direct", "javaApi临时同步创建节点".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("同步创建临时节点 : " + elemeentPath);

        //异步创建临时节点 (回调函数为StringCallback)
        zooKeeper.create("/zk-test-driect-async", "异步创建临时节点".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new AsyncCallback.StringCallback() {
                    @Override
                    public void processResult(int rc, String path, Object ctx, String name) {
                        System.out.println("异步创建回调结果：状态：" + rc +"；创建路径：" +
                                path + "；传递信息：" + ctx + "；实际节点名称：" + name);
                    }
                },"传递的上下文");

        // 创建子节点
        zooKeeper.create("/zk-java-direct/aaa", "javaApi临时同步创建节点子节点aaa".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zooKeeper.create("/zk-java-direct/bbb", "javaApi临时同步创建节点子节点bbbb".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        // 同步获取自节点列表
        List<String> children = zooKeeper.getChildren("/zk-java-direct", true);
        children.forEach(System.out::println);

        //获取状态
        Stat stat = new Stat();
        List<String> children1 = zooKeeper.getChildren("/zk-java-direct", false, stat);
        children1.forEach(System.out::println);
        System.out.println(stat);

        //异步获取子节点列表，回掉的类是 ChildrenCallback
        zooKeeper.getChildren("/zk-java-direct", true, (rc, path, ctx, children2) ->
                System.out.println("异步获得getChildren结果，rc=" + rc
                        + "；path=" + path + "；ctx=" + ctx + "；children=" + children2),"传递数据");
        //异步获取子节点带状态 , 回调接口是 Clildren2CallBack
        zooKeeper.getChildren("/zk-java-direct", false, new AsyncCallback.Children2Callback() {
            @Override
            public void processResult(int rc, String path, Object ctx, List<String> child, Stat stat) {
                System.out.println("异步获得getChildren结果，rc=" + rc
                        + "；path=" + path + "；ctx=" + ctx + "；children=" + child +"；stat=" + stat);
            }
        },"传递的数据");

        //查看监听是否好用
        zooKeeper.create("/zk-java-direct/ccc", "javaApi临时同步创建节点子节点ccc".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        byte[] data = zooKeeper.getData("/zk-java-direct", true, stat);
        System.out.println(new String(data));

        // 设置数据节点内容
        zooKeeper.setData("/zk-java-direct","改变了数据节点的内容".getBytes(),-1);

        // 异步获取数据节点的内容
        zooKeeper.getData("/zk-java-direct", true, new AsyncCallback.DataCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, byte[] bytes, Stat stat) {
                System.out.println("执行状态:" + rc  + "数据节点路径 : " + path + " " +
                        "传递过来的数据:"+ ctx + " 数据节点内容 , " + new String(bytes)+ "" +
                        "节点的状态信息:" + stat);
            }
        },"异步调用获取数据节点内容");

        //参数方法
        zooKeeper.delete("/zk-java-direct/aaa",-1);

        // 判断节点收否存在，并注册监听 ,节点不存在,返回null
        Stat exists = zooKeeper.exists("/zk-java-direct/aaa", true);
        System.out.println("节点是否存在:" + exists);

        // 创建节点:
        String s = zooKeeper.create("/zk-java-direct/aaa", "第二次创建".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        // 判断节点收否存在，并注册监听
        Stat exists1 = zooKeeper.exists("/zk-java-direct/aaa", true);
        System.out.println("节点是否存在:" + exists1);

        Thread.sleep(30000); // 异步等待执行结果
        //while (true){}
    }

    /**
     *  通知回掉方法
     * @param watchedEvent
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("接到事件通知:" + watchedEvent);
        // 通知countDownLatch连接完成，主线程才能继续往下走
        if(Event.KeeperState.SyncConnected == watchedEvent.getState() && Event.EventType.None == watchedEvent.getType()){
            countDownLatch.countDown();
        }else if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged){
            try {
                System.out.println("重新获取child:并监听 " + zooKeeper.getChildren("/zk-java-direct",true));
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else if(watchedEvent.getType() == Event.EventType.NodeDataChanged){
            // 节点内容变化
            try {
                byte[] data = zooKeeper.getData(watchedEvent.getPath(), true, null);
                System.out.println("数据节点内容变化" + new String(data));
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }else if(watchedEvent.getType() == Event.EventType.NodeCreated){
            System.out.println("节点创建" + watchedEvent.getPath());
        }else if(watchedEvent.getType() == Event.EventType.NodeDeleted){
            System.out.println("节点删除" + watchedEvent.getPath());
        }
    }
}
