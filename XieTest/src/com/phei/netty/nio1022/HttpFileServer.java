package com.phei.netty.nio1022;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpFileServer {

	private static final String Default_URL = "/test";

	public void run(final int port, final String url) {
		// 配置客户端NIO线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel arg0) throws Exception {
							arg0.pipeline().addLast("http-decoder", new HttpRequestDecoder());
							arg0.pipeline().addLast("http-aggerator", new HttpObjectAggregator(65536));
							arg0.pipeline().addLast("http-encoder", new HttpResponseEncoder());
							arg0.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
							arg0.pipeline().addLast("fileServerHandler", new HttpFileServerHandler(url));
						}

					});
			ChannelFuture f = b.bind("127.0.0.1", port).sync();
			System.out.println("HTTP目录服务器现在启动，网址是：http://127.0.0.1:" + port + url);
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) {
		int port = 8080;
		String url = Default_URL;
		new HttpFileServer().run(port, url);
	}
}
