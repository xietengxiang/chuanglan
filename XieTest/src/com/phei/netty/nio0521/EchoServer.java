package com.phei.netty.nio0521;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoServer {

	public void bind(int port) throws Exception{
		//配置服务端的NIO线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 100)
				.handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(new ChannelInitializer<SocketChannel>(){

					@Override
					protected void initChannel(SocketChannel arg0) throws Exception {
						arg0.pipeline().addLast(new FixedLengthFrameDecoder(20));
						arg0.pipeline().addLast(new StringDecoder());
						arg0.pipeline().addLast(new EchoServerHandler());
					}
				});
			//绑定端口，同步等待成功
			ChannelFuture f = b.bind(port).sync();
			//等待服务端监听端口关闭
			f.channel().closeFuture().sync();
		}finally{
			//退出
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) {
		int port = 8080;
		try {
			new EchoServer().bind(port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
