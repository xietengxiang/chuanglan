package com.phei.netty.multiHandler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class EchoServerHandler extends ChannelHandlerAdapter {

	private int counter;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = (String) msg;
		System.out.println("1111111111"+"receive:"+body+",the counter is:"+ ++counter);
		body += "$_";
		ByteBuf buf = Unpooled.copiedBuffer(body.getBytes());
		ctx.writeAndFlush(buf);
		
		
		//缓存到本地
        ctx.fireChannelRead(msg);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
