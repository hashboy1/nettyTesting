package org.MyNetty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;  
import io.netty.channel.ChannelFutureListener;  
import io.netty.channel.ChannelInitializer;  
import io.netty.channel.EventLoopGroup;  
import io.netty.channel.nio.NioEventLoopGroup;  
import io.netty.channel.socket.SocketChannel;  
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;  
  

  
public class MyNettyClient {  
    private final String host;  
    private final int port;  
  
    public MyNettyClient(String host, int port) {  
        this.host = host;  
        this.port = port;  
    }  
  
    public void start() throws Exception {  
        EventLoopGroup group = new NioEventLoopGroup();  
        try {  
            Bootstrap b = new Bootstrap();  
            b.group(group);  
            b.channel(NioSocketChannel.class);  
            b.remoteAddress(new InetSocketAddress(host, port));  
            b.handler(new ChannelInitializer<SocketChannel>() {  
  
                public void initChannel(SocketChannel ch) throws Exception {  
                    ch.pipeline().addLast(new MyNettyClientHandler());  
                }  
            });  
            ChannelFuture f = b.connect().sync();  
            f.addListener(new ChannelFutureListener() {            
                public void operationComplete(ChannelFuture future) throws Exception {  
                    if(future.isSuccess()){  
                        System.out.println("client connected");  
                    }else{  
                        System.out.println("server attemp failed");  
                        future.cause().printStackTrace();  
                    }  
                      
                }  
            }); 
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
            String currentTime= df.format(System.currentTimeMillis()); 
            Channel fc=f.channel();
            fc.writeAndFlush(Unpooled.copiedBuffer(currentTime, CharsetUtil.UTF_8));
            fc.writeAndFlush(Unpooled.copiedBuffer(currentTime, CharsetUtil.UTF_8));
            fc.writeAndFlush(Unpooled.copiedBuffer("stop", CharsetUtil.UTF_8));
            System.out.println("send time,"+currentTime);
            f.channel().closeFuture().sync(); 
            f.channel().close();
        } finally {  
            group.shutdownGracefully().sync();  
        }  
    }  
  
    public static void main(String[] args) throws Exception {  
      
    	MyNettyClient mc=new MyNettyClient("127.0.0.1", 8099);  
        mc.start();
      
    }  
}  