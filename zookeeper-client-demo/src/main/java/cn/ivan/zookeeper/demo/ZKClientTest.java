package cn.ivan.zookeeper.demo;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

import java.util.List;

/**
 *  zookeeper 开源客户端测试
 * @author yanqi
 * @version 1.0
 */

public class ZKClientTest {

    public static void main(String[] args) throws InterruptedException {

        /**
         *   ZkClient将ZK原生API中的异步处理进行了同步化
         */
        ZkClient zkClient = new ZkClient("localhost:2181", 5000);
        System.out.println("连接上zookeeper...");
        // 创建节点: 节点存在抛出异常
        String path = zkClient.create("/zk-java-zkclient", "zkcliet创建持久节点", CreateMode.PERSISTENT);
        System.out.println("创建节点成功" + path);
        //获取节点内容,通过方法返回参数的定义，就可以得知，返回的结果（节点的内容）已经被反序列化成对象了。
        //本接口实现监听的接口为IZkDataListener，分别提供了处理数据变化和删除操作的监听：

        Object o = zkClient.readData("/zk-java-zkclient");
        System.out.println("节点的数据:" + o);

        //注册节点变化的监听 ，不是一次
        zkClient.subscribeDataChanges("/zk-java-zkclient", new IZkDataListener() {
            @Override
            public void handleDataChange(String path, Object data) throws Exception {
                System.out.println("监听节点 " + path + " 数据变化 :" + data );
            }

            @Override
            public void handleDataDeleted(String path) throws Exception {
                System.out.println("监听节点" + path + "删除");
            }
        });

        //更新节点内容
        zkClient.writeData("/zk-java-zkclient","第一次修改节点内容");

        // 连续更新，导致监听回掉的数据节点内容被覆盖
        Thread.sleep(1000);
        zkClient.writeData("/zk-java-zkclient","第二次修改节点内容");

        //检测节点是否存在:
        boolean exists = zkClient.exists("/zk-java-zkclient");
        System.out.println("节点是否存在 : " + exists);

        // // 注册子节点变更监听（此时path节点并不存在，但可以进行监听注册）
        zkClient.subscribeChildChanges("/zk-java-zkclient", new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println("路径" + parentPath +"下面的子节点变更。子节点为：" + currentChilds );
            }
        });

        // 可以递归创建子节点
        zkClient.createEphemeral("/zk-java-zkclient/aaa");
        zkClient.createEphemeral("/zk-java-zkclient/bbb");

        List<String> children = zkClient.getChildren("/zk-java-zkclient");
        children.forEach(System.out::print);

        Thread.sleep(20000);

    }
}
