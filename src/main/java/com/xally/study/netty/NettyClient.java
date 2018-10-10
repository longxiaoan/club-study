package com.xally.study.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by xiaoanlong on 2018/10/8.
 */
public class NettyClient {

	private static Integer MAX_RETRY = 5;

	public static void main(String[] args) throws InterruptedException {
		//建立引导类bootstrap
		Bootstrap bootstrap = new Bootstrap();
		NioEventLoopGroup group = new NioEventLoopGroup();
		//指定IO模型为NioSocketChannel
		bootstrap.group(group).channel(NioSocketChannel.class)
				//定义连接的业务处理逻辑
				.handler(new ChannelInitializer<Channel>() {

					@Override protected void initChannel(Channel channel) throws Exception {
						channel.pipeline().addLast(new StringEncoder());
					}
				});
		//Channel connect = bootstrap.connect("127.0.0.1", 8000).channel();
		Channel connect = reConnect(bootstrap, "127.0.0.1", 8000, MAX_RETRY).channel();
		while (true) {
			connect.writeAndFlush(new Date() + ": hello world!");
			Thread.sleep(2000);
		}
	}

	public static ChannelFuture reConnect(Bootstrap bootstrap, String ip, int port, int retry) {
		ChannelFuture connect = bootstrap.connect(ip, port);
		connect.addListener(future -> {
			if (future.isSuccess()) {
				System.out.println("ip[" + ip + "],port[" + port + "],连接成功");
			} else if (0 == retry) {
				System.out.println("重试次数已用完，放弃连接");
			} else {
				int order = (MAX_RETRY - retry) + 1;
				int delay = 1 << order;
				System.out.println(new Date()+":第" + order+"次重试");
				bootstrap.config().group().schedule(() -> reConnect(bootstrap, ip, port, retry - 1), delay, TimeUnit.SECONDS);
			}
		});
		return connect;
	}
}
