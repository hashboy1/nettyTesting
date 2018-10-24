package org.Websocket;


import org.apache.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;  
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.ImmediateEventExecutor;

/** 
 * Sharable表示此对象在channel间共享 
 * handler类是我们的具体业务类 
 * */  

public class WebsocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{  
	
	private Logger logger = Logger.getLogger(getClass());
	public static ChannelGroup channels = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        Channel incoming = ctx.channel();
        logger.info("Client:"+incoming.remoteAddress()+"在线");
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel incoming = ctx.channel();
        logger.info("Client:"+incoming.remoteAddress()+"掉线");
	}
	
	
	

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
       
    	logger.info("event:"+evt.toString());
    	
    	if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
        
    		logger.info("shake completed");
            channels.add(ctx.channel());
        } 
    	
    	
    	
    	else {
            super.userEventTriggered(ctx, evt);
        }
    }
	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)	// (7)
			throws Exception {
    	Channel incoming = ctx.channel();
    	logger.info("Client:"+incoming.remoteAddress()+"异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame  msg) throws Exception {	

		logger.info("server received data :" + msg.text());   
		ctx.writeAndFlush(msg.retain());//写回数据
	
	}



	
}  