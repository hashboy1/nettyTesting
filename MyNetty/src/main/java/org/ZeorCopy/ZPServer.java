package org.ZeorCopy;


import io.netty.bootstrap.ServerBootstrap; 
import io.netty.channel.ChannelFuture;  
import io.netty.channel.ChannelInitializer;  
import io.netty.channel.EventLoopGroup;  
import io.netty.channel.nio.NioEventLoopGroup;  
import io.netty.channel.socket.SocketChannel;  
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.apache.log4j.Logger;  



public class ZPServer {  
    private static final int port = 8099;  
    private Logger logger = Logger.getLogger(getClass());
    
    public void start() throws InterruptedException {  
        ServerBootstrap b = new ServerBootstrap();// 引导辅助程序  
        EventLoopGroup bossgroup = new NioEventLoopGroup();
        EventLoopGroup workgroup = new NioEventLoopGroup();// 通过nio方式来接收连接和处理连接  
        try {  
            b.group(bossgroup,workgroup);  
            b.channel(NioServerSocketChannel.class);// 设置nio类型的channel  
            b.localAddress(new InetSocketAddress(port));// 设置监听端口  
            b.childHandler(new ChannelInitializer<SocketChannel>() {//有连接到达时会创建一个channel  
                        protected void initChannel(SocketChannel ch) throws Exception {  
                        	ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                        	ch.pipeline().addLast(new StringDecoder());
                        	ch.pipeline().addLast(new StringEncoder());
                            ch.pipeline().addLast("myHandler", new ZPServerHandler());  
                        }  
                    });  
            ChannelFuture f = b.bind().sync();// 配置完成，开始绑定server，通过调用sync同步方法阻塞直到绑定成功  
            logger.info(ZPServer.class.getName() + " started and listen on " + InetAddress.getLocalHost().getHostAddress());  
            f.channel().closeFuture().sync();// 应用程序会一直等待，直到channel关闭  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
        	bossgroup.shutdownGracefully();
        	workgroup.shutdownGracefully();//关闭EventLoopGroup，释放掉所有资源包括创建的线程  
        }  
    }  
    public static void main(String[] args) {  
        try {  
            new ZPServer().start();  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
    }  
}  