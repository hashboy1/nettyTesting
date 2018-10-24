package org.StringTransfer;


import org.apache.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;  
import io.netty.channel.ChannelFutureListener;  
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/** 
 * Sharable表示此对象在channel间共享 
 * handler类是我们的具体业务类 
 * */  

public class STServerHandler extends SimpleChannelInboundHandler<Object>{  
	
	private Logger logger = Logger.getLogger(getClass());


	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object  msg) throws Exception {	
		
        
		logger.info("server received data :" + msg);   
		ctx.writeAndFlush((String) msg+"\r\n");//写回数据
	
		
	}


	
}  