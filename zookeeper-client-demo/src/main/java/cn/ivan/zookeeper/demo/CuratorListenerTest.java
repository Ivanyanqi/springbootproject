package cn.ivan.zookeeper.demo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *  测试 curator 客户端事件监听 api
 * @author yanqi
 * @version 1.0
 */
public class CuratorListenerTest {


    private CuratorFramework client;

    /**
     *  初始化curator 客户端
     */
    @Before
    public void before(){
        client = CuratorFrameworkFactory.builder()
                .connectString("localhost:2181")
                .retryPolicy(new ExponentialBackoffRetry(1000,3))
                .connectionTimeoutMs(5000)
                .sessionTimeoutMs(30000)
                .build();
        client.start();
    }

    @After
    public void after(){
        client.close();
    }

    /**
     *  利用Watcher来对节点进行监听操作，但此监听操作只能监听一次
     */
    @Test
    public void testListener1() throws Exception {
        client.getData().usingWatcher(new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("监听回掉" + watchedEvent);
            }
        }).forPath("/zk-curator-test");
        //设置
        client.setData().forPath("/zk-curator-test","第一次修改".getBytes());
        client.setData().forPath("/zk-curator-test","第二次修改".getBytes());
        //等待监听回掉
        Thread.sleep(10000);
    }

    /**
     * CuratorListener监听，此监听主要针对background通知和错误通知。使用此监听器之后，
     * 调用inBackground方法会异步获得监听，新增，修改节点内容也能得到通知
     */
    @Test
    public void testListener2() throws Exception {
        client.getCuratorListenable().addListener(new CuratorListener() {
            @Override
            public void eventReceived(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                System.out.println("事件监听回掉:" + curatorEvent);
            }
        });
        //修改节点内容
        client.setData().inBackground().forPath("/zk-curator-test","修改节点内容通知".getBytes());

        //获取节点内容,调用inBackground 返回值为null
       client.getData().inBackground().forPath("/zk-curator-test");
        //System.out.println("节点内容:" + new String(bytes));

        // 新增节点,节点路径可以为中文
        client.create().withMode(CreateMode.EPHEMERAL).inBackground().forPath("/zk-curator-test/回电话好多","创建临时节点".getBytes());
        //等待回掉通知
        Thread.sleep(10000);
    }

    /**
     *  cache 监听  经过试验，发现注册监听之后，如果先后多次修改监听节点的内容，部分监听事件会发生丢失现象
     */
    @Test
    public void testListener3()throws Exception{
        String path = "/zk-curator-test";
        // 1.nodeCache
        NodeCache nodeCache = new NodeCache(client,path);
        // 不调用start() 方法，监听不会生效
        nodeCache.start();
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("========节点变化");
                System.out.println("========重新获取节点内容:" + new String(nodeCache.getCurrentData().getData()));
            }
        });
        client.setData().forPath(path,"456".getBytes());
        client.setData().forPath(path,"789".getBytes());
        client.setData().forPath(path,"123".getBytes());
        client.setData().forPath(path,"222".getBytes());
        client.setData().forPath(path,"333".getBytes());
        client.setData().forPath(path,"444".getBytes());
        Thread.sleep(10000);
    }
}
