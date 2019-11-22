package cn.ivan.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Date;

/**
 * @author yanqi69
 * @date 2019/11/4
 */
public class NettyDemo {

    public static class NettyServer{

        public static void main(String[] args) {

            ServerBootstrap serverBootstrap = new ServerBootstrap();

            //接受线程
            NioEventLoopGroup boss = new NioEventLoopGroup();
            //读取线程
            NioEventLoopGroup worker = new NioEventLoopGroup();

            ChannelFuture bind = serverBootstrap.group(boss, worker)  // 设置线程模型
                    .channel(NioServerSocketChannel.class)  //设置io 模型
                    .childHandler(new ChannelInitializer<NioSocketChannel>() { // 连接读写处理回调
                        @Override
                        protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                            nioSocketChannel.pipeline().addLast(new StringDecoder());
                            nioSocketChannel.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext channelHandlerContext, String message) throws Exception {
                                    System.out.println(message);
                                }
                            });
                        }
                    })
                    .bind(8000);
            // bind 方法是异步的， 返回ChannelFuture ,可以添加监听器判断绑定是否成功
            bind.addListener(future -> {
               if(future.isSuccess()){
                    System.out.println("端口绑定成功");
               } else {
                   System.out.println("端口绑定失败");
               }
            });
        }
    }

    /**
     * NioServerSocketChannel和NioSocketChannel的概念可以和 BIO 编程模型中的ServerSocket以及Socket两个概念对应上
     */
    public static class NettyClient{
        public static void main(String[] args) throws InterruptedException {
            Bootstrap bootstrap = new Bootstrap();
            NioEventLoopGroup clientGroup = new NioEventLoopGroup();
            bootstrap.group(clientGroup)   // 1.指定线程模型
                    .channel(NioSocketChannel.class) // 2.指定 IO 类型为 NIO
                    .handler(new ChannelInitializer<Channel>() {  // 3.IO 处理逻辑
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline().addLast(new StringEncoder());
                        }
                    });
            Channel localhost = bootstrap.connect("localhost", 8000).channel();
            //connect 连接也是异步的
            while (true){
                localhost.writeAndFlush(new Date() + ":hello world");
                Thread.sleep(2000);
            }
        }
    }
}
