package com.phei.netty.nio0511;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class EchoServerHandler extends ChannelHandlerAdapter {

	private int counter;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = (String) msg;
		System.out.println("receive:"+body+",the counter is:"+ ++counter);
		body += "$_";
		ByteBuf buf = Unpooled.copiedBuffer(body.getBytes());
		ctx.writeAndFlush(buf);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
