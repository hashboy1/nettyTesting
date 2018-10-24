package org.MyNetty;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;  
import io.netty.channel.ChannelFutureListener;  
import io.netty.channel.ChannelHandlerContext;  
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/** 
 * Sharable表示此对象在channel间共享 
 * handler类是我们的具体业务类 
 * */  

public class MyNettyServerHandler extends SimpleChannelInboundHandler<Object>{  
	



	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {	
		
		if (msg instanceof ByteBuf)
		{
		ByteBuf byt=(ByteBuf) msg;
		byte[] bytes = new byte[byt.readableBytes()];
		byt.readBytes(bytes);
		System.out.println("server received data :" + new String(bytes));   
		ctx.writeAndFlush(Unpooled.copiedBuffer(new String(bytes), CharsetUtil.UTF_8));//写回数据
		}
		else
		{
			System.out.println("server the other data :"+msg.getClass());
		}
		
	}     
}  