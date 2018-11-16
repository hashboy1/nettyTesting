package org.ZeorCopy;

import org.apache.log4j.Logger;

import io.netty.buffer.ByteBuf;  
import io.netty.buffer.ByteBufUtil;  
import io.netty.buffer.Unpooled;  
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;  
import io.netty.channel.ChannelHandler.Sharable;  
import io.netty.util.CharsetUtil;  
  
@Sharable  
public class ZPClientHandler extends SimpleChannelInboundHandler<Object> {  
	
	
    private Logger logger = Logger.getLogger(getClass());
    private  long ThreadName;
    
    ZPClientHandler(long ThreadName)
    {
    	this.ThreadName=ThreadName;
    }
    
    
    /** 
     *此方法会在连接到服务器后被调用  
     * */  
	@Override
    public void channelActive(ChannelHandlerContext ctx) {  
		logger.info(ThreadName+":"+"channel init.");
        //ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));  
		ctx.writeAndFlush(ThreadName+":"+"Netty rocks!\r\n");
    }  
   

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object  content) throws Exception {		
        String msg=(String) content;
		logger.info("Client received data :" + msg);   
		//ctx.writeAndFlush((String) msg);//写回数据
		if (msg!=null && msg.indexOf("stop") > -1 )  ctx.close();
		
		
	
			

		
	}   
	
  
}  