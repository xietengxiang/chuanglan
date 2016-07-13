package com.phei.netty.nio122;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

public class ChineseProverbClient {

	public static void main(String[] args) {
		int port = 8080;
		new ChineseProverbClient().run(port);
	}

	private void run(int port) {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true)
					.handler(new ChineseProverbClientHandler());
			Channel ch = b.bind(0).sync().channel();
			// 向网段内所有机器广播UDP消息
			ch.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("中国谚语", CharsetUtil.UTF_8),
					new InetSocketAddress("255.255.255.255", port))).sync();
			if (!ch.closeFuture().await(15000L)) {
				System.out.println("查询超时！");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}
}
