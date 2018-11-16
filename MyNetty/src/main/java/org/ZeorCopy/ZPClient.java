package org.ZeorCopy;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;  
import io.netty.channel.ChannelFutureListener;  
import io.netty.channel.ChannelInitializer;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.EventLoopGroup;  
import io.netty.channel.nio.NioEventLoopGroup;  
import io.netty.channel.socket.SocketChannel;  
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;  
  

  
public class ZPClient implements Runnable{  
    private final String host;  
    private final int port;  
    private  long ThreadName;
    private Logger logger = Logger.getLogger(getClass());
  
    public ZPClient(String host, int port) {  
        this.host = host;  
        this.port = port;  
        ThreadName = Thread.currentThread().getId();
    }  
  
	@Override
	public void run() {  
        EventLoopGroup group = new NioEventLoopGroup();  
        try {  
            Bootstrap b = new Bootstrap();  
            b.group(group);  
            b.channel(NioSocketChannel.class);  
            b.remoteAddress(new InetSocketAddress(host, port));  
            b.handler(new ChannelInitializer<SocketChannel>() {  
  
                public void initChannel(SocketChannel ch) throws Exception {  
                	ch.pipeline().addLast(new LineBasedFrameDecoder(1024));   //message seperated by the \r\n
                	ch.pipeline().addLast(new StringDecoder());               //decoder to write the string type
                	ch.pipeline().addLast(new StringEncoder());               //encoder to write the string type
                    ch.pipeline().addLast(new ZPClientHandler(ThreadName));  
                }  
            });  
            ChannelFuture f;
			
				f = b.connect().sync();
			 
            f.addListener(new ChannelFutureListener() {            
                public void operationComplete(ChannelFuture future) throws Exception {  
                    if(future.isSuccess()){  
                    	logger.info("client connected");  
                    }else{  
                    	logger.info("server attemp failed");  
                        future.cause().printStackTrace();  
                    }  
                      
                }  
            }); 
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
            String currentTime= df.format(System.currentTimeMillis()); 
            Channel fc=f.channel();
            fc.writeAndFlush(ThreadName+":"+currentTime+"\r\n");
            fc.writeAndFlush(ThreadName+":"+currentTime+"\r\n");
            fc.writeAndFlush(ThreadName+":"+"stop\r\n");
            
            //DefaultFileRegion dfr=new DefaultFileRegion(); 
            
            
            //logger.info("send time,"+currentTime);
            f.channel().closeFuture().sync(); 
            f.channel().close();
        } 
		catch(Exception ex)
		{
				logger.info(ex.toString());
		}
		finally {  
            try {
				group.shutdownGracefully().sync();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
        }  
    }  
  
	
	
    public static void main(String[] args) throws Exception {  
      
    	//STClient mc=new STClient("127.0.0.1", 8099);  
        //mc.start();
      
    }


}  