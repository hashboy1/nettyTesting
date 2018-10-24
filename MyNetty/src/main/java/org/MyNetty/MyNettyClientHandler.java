package org.MyNetty;

import io.netty.buffer.ByteBuf;  
import io.netty.buffer.ByteBufUtil;  
import io.netty.buffer.Unpooled;  
import io.netty.channel.ChannelHandlerContext;  
import io.netty.channel.SimpleChannelInboundHandler;  
import io.netty.channel.ChannelHandler.Sharable;  
import io.netty.util.CharsetUtil;  
  
@Sharable  
public class MyNettyClientHandler extends SimpleChannelInboundHandler<Object> {  
    /** 
     *此方法会在连接到服务器后被调用  
     * */  
	@Override
    public void channelActive(ChannelHandlerContext ctx) {  
    	System.out.println("channel init.");
        //ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));  
    }  
    /** 
     *此方法会在接收到服务器数据后调用  
     * */  
	@Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) {  
		if (msg instanceof ByteBuf)
		{
		ByteBuf byt=(ByteBuf) msg;
		byte[] bytes = new byte[byt.readableBytes()];
		byt.readBytes(bytes);
		String content=new String(bytes);
		System.out.println("Client received data :" + content);   
     	if (content!=null && content.indexOf("stop") > 0 )  ctx.disconnect();
		}
		else
		{
			System.out.println("Client the other data :"+msg.getClass());
		}
      
    }  
   
	
  
}  