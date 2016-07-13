package com.phei.netty.nio071;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class SubReqClientHandler extends ChannelHandlerAdapter {

	public SubscribeReq subReq(int i){
		SubscribeReq req = new SubscribeReq();
		req.setSubReqID(i);
		req.setUserName("LiLinFeng");
		req.setProductName("Netty开发指南");
		req.setPhoneNum("13817694188");
		req.setAddress("上海市徐汇区虹梅路1535号");
		return req;
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (int i = 0; i < 10; i++) {
			ctx.write(subReq(i));
		}
		ctx.flush();
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("Received server response: "+msg);
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
