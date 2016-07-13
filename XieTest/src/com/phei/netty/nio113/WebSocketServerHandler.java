package com.phei.netty.nio113;


import java.util.Date;
import java.util.logging.Level;

import com.sun.istack.internal.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;

public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

	private static final Logger log = Logger.getLogger(WebSocketServerHandler.class.getName(), WebSocketServerHandler.class);
	private WebSocketServerHandshaker handshaker;
	
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 传统http接入
		if(msg instanceof FullHttpRequest){
			handleHttpRequest(ctx, (FullHttpRequest)msg);
		}
		// WebSocket接入
		else if(msg instanceof WebSocketFrame){
			handleWebSocketFrame(ctx, (WebSocketFrame)msg);
		}
	}

	/**
	 * http接入
	 * @param ctx
	 * @param msg
	 */
	private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
		if(!req.getDecoderResult().isSuccess() || !"websocket".equals(req.headers().get("Upgrade"))){
			// http解码失败
			setHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
			return;
		}
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:8080/websocket", null, false);
		handshaker = wsFactory.newHandshaker(req);
		if(handshaker == null){
			WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
		} else {
			handshaker.handshake(ctx.channel(), req);
		}
	}

	/**
	 * WebSocket接入
	 * @param ctx
	 * @param msg
	 */
	private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
		// 判断是否是关闭链路命令
		if(frame instanceof CloseWebSocketFrame){
			handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
			return;
		}
		//判断是否是ping消息
		if(frame instanceof PingWebSocketFrame){
			ctx.channel().write(new PongWebSocketFrame().content().retain());
			return;
		}
		//仅支持文本消息
		if(frame instanceof TextWebSocketFrame){
			String request = ((TextWebSocketFrame) frame).text();
			if(log.isLoggable(Level.FINE)){
				log.fine(String.format("%s received %s", ctx.channel(), request));
			}
			ctx.channel().write(new TextWebSocketFrame(request + ", 欢迎使用netty WebSocket服务. 现在时间是:" + new Date()));
		}else{
			throw new UnsupportedOperationException(String.format("%s frame type not supported", frame.getClass().getName()));
		}
	}

	/**
	 * 回复响应消息
	 * @param ctx
	 * @param req
	 * @param defaultHttpResponse
	 */
	public static void setHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req,
			FullHttpResponse res) {
		// 返回响应到客户端
		if(res.getStatus().code() != 200){
			ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
			res.content().writeBytes(buf);
			buf.release();
			HttpHeaders.setContentLength(res, res.content().readableBytes());
		}
		// 如果是非Keep-Alive模式，关闭连接
		ChannelFuture f = ctx.channel().writeAndFlush(res);
		if(HttpHeaders.isKeepAlive(req) || res.getStatus().code() != 200){
			f.addListener(ChannelFutureListener.CLOSE);
		}
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
