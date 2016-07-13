package com.phei.netty.nio032;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeClient {

	public static void main(String[] args) {
		int port = 8080;
		new TimeClient().connect(port, "127.0.0.1");
	}

	public void connect(int port, String string) {
		// ���ÿͻ���NIO�߳���
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel arg0) throws Exception {
							arg0.pipeline().addLast(new LineBasedFrameDecoder(1024));
							arg0.pipeline().addLast(new StringDecoder());
							arg0.pipeline().addLast(new TimeClientHandler());
						}

					});
			//�����첽���Ӳ���
			ChannelFuture cf = b.connect(string,port).sync();
			//�ȴ��ͻ�����·�ر�
			cf.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//�ͷ�NIO�߳���
			group.shutdownGracefully();
		}
	}
}
