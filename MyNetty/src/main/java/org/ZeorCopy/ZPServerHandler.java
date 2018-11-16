package org.ZeorCopy;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

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

public class ZPServerHandler extends SimpleChannelInboundHandler<Object>{  
	
	private Logger logger = Logger.getLogger(getClass());
	FileChannel fout = null;
	
	ZPServerHandler() throws Exception
	{
		fout = new FileOutputStream("f:\\3.obj").getChannel();
	
	}
	

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object  msg) throws Exception {	
		
        
		logger.info("server received data!");   
		//ctx.writeAndFlush((String) msg+"\r\n");//写回数据
		
	
	   
		//fout.transferFrom( ctx.channel(),0,0);
	}


	
}  