package com.phei.netty.nio032;

import io.netty.channel.socket.SocketChannel;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeServer {

	public void bind(int port){
		//���÷������˵�NIO�߳���
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).
				option(ChannelOption.SO_BACKLOG, 1024).childHandler(new ChannelInitializer<SocketChannel>() {
	                @Override
	                public void initChannel(SocketChannel ch) throws Exception {
	                	ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
	                	ch.pipeline().addLast(new StringDecoder());
	                	ch.pipeline().addLast(new TimeServerHandler());
	                }
				});
			//�󶨶˿ڣ�ͬ���ȴ��ɹ�
			ChannelFuture f = b.bind(port).sync();
			
			//�ȴ�����˼����˿ڹر�
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			//�ͷ��߳���Դ
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) {
		int port = 8080;
		new TimeServer().bind(port);
	}
}
