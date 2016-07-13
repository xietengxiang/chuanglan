package com.phei.netty.multiHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class EchoClient {

	public void connect(int port, String addr){
		//配置客户端NIO的线程组
		EventLoopGroup group = new NioEventLoopGroup();
		try{
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>(){
				@Override
				protected void initChannel(SocketChannel arg0) throws Exception {
					ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
					arg0.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
					arg0.pipeline().addLast(new StringDecoder());
					arg0.pipeline().addLast(new EchoClientHandler());
				}
				
			});
			//发起异步链接
			ChannelFuture cf = b.connect(addr, port).sync();
			//等待客户端链路关闭
			cf.channel().closeFuture().sync();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			group.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) {
		int port = 8080;
		new EchoClient().connect(port, "127.0.0.1");
	}
}
