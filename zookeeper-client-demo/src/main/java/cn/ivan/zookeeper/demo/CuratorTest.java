package cn.ivan.zookeeper.demo;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

/**
 * zookeeper curator 客户端api
 * @author yanqi
 * @version 1.0
 */
public class CuratorTest {

    public static void main(String[] args) throws Exception {




        // 创建会话
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("localhost:2181", retryPolicy);
        curatorFramework.start();

        // buidler方式创建
//        CuratorFramework build = CuratorFrameworkFactory.builder()
//                .connectString("localhost:2181")
//                .connectionTimeoutMs(1000)
//                .sessionTimeoutMs(30000)
//                .retryPolicy(retryPolicy).build();


        // 删除节点
        //curatorFramework.delete().forPath("/zk-curator-test");
        //如果此版本已经不存在，则删除异常，异常信息如下
        //curatorFramework.delete().withVersion(-1).forPath("/zk-curator-test");
        //删除节点并递归删除其子节点
        //curatorFramework.delete().deletingChildrenIfNeeded().forPath("/zk-curator-test1");
        //强制保证删除一个节点
        //只要客户端会话有效，那么Curator会在后台持续进行删除操作，直到节点删除成功。
        // 比如遇到一些网络异常的情况，此guaranteed的强制删除就会很有效果
        //curatorFramework.delete().guaranteed().forPath("//zk-curator-test");

        //创建节点,默认创建的是持久节点,存在会抛出异常NodeExistsException

        String path = curatorFramework.create()
                .forPath("/zk-curator-test", "通过curator客户端创建节点".getBytes());
        System.out.println("创建节点成功:" + path);

        // 创建临时节点并递归创建父节点
        //此处Curator和ZkClient一样封装了递归创建父节点的方法。在递归创建父节点时，父节点为持久节点。
        String forPath = curatorFramework.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath("/zk-curator-test1/aaaa", "创建临时节点并递归创建父节点".getBytes());
        System.out.println("创建临时节点并递归创建父节点成功:" + forPath);

        // 获取节点数据
        byte[] bytes = curatorFramework.getData().forPath("/zk-curator-test");
        System.out.println("节点数据:" + new String(bytes));
        //查询stat
        Stat stat = new Stat();
        byte[] bytes1 = curatorFramework.getData().storingStatIn(stat).forPath("/zk-curator-test1/aaaa");
        System.out.println("节点数据:" + new String(bytes1) + " 节点状态 :" + stat);

        //更新数据
        //更新数据，如果未传入version参数，那么更新当前最新版本，如果传入version则更新指定version，如果version已经变更，则抛出异常
        Stat stat1 = curatorFramework.setData().withVersion(-1)
                .forPath("/zk-curator-test", "第一次修改节点数据".getBytes());
        System.out.println("更新数据成功,节点状态:" + stat1);

        byte[] bytes2 = curatorFramework.getData().forPath("/zk-curator-test");
        System.out.println("节点更新后的新数据:" + new String(bytes2));

        //异步创建一个临时节点
        //BackgroundCallback
        curatorFramework.create().withMode(CreateMode.EPHEMERAL)
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                        System.out.println(curatorEvent);
                        if(curatorEvent.getResultCode() == 0){
                            System.out.println("创建成功");
                            byte[] bytes3 = curatorFramework.getData().forPath(curatorEvent.getPath());
                            System.out.println("新创建节点的内容:" + new String(bytes3));
                        }
                    }
                }).forPath("/zk-curator-back","异步创建的节点".getBytes());

        // 等待回掉函数执行
        Thread.sleep(10000);
    }

}
