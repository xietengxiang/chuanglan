package com.phei.netty.nio0511;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoServer {

	public void bind(int port) {
		// 配置服务端的NIO线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 100).handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ChannelInitializer<SocketChannel>(){

						@Override
						protected void initChannel(SocketChannel arg0) throws Exception {
							ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
							arg0.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
							arg0.pipeline().addLast(new StringDecoder());
							arg0.pipeline().addLast(new EchoServerHandler());
						}
					});
			
			//绑定端口，同步等待成功
			ChannelFuture cf = b.bind(port).sync();
			//等待服务器监听端口关闭
			cf.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//释放资源
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) {
		int port = 8080;
		new EchoServer().bind(port);
	}
}
