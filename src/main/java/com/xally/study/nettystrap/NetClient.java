package com.xally.study.nettystrap;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NetClient {

	private static final int MAX_RETRY = 5;

	public static void main(String[] args) {
		Bootstrap bootstrap = new Bootstrap();
		NioEventLoopGroup group = new NioEventLoopGroup();
		bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<Channel>() {

			@Override protected void initChannel(Channel channel) throws Exception {
				channel.pipeline().addLast(new FirstClientHandler());
			}
		});
		reconnect(bootstrap, "127.0.0.1", 8000, 5);
	}

	private static class FirstClientHandler extends ChannelInboundHandlerAdapter {

		@Override public void channelActive(ChannelHandlerContext ctx) throws Exception {
			super.channelActive(ctx);
			System.out.println(new Date() + "客户端写出数据");
			//获取数据
			ByteBuf buffer = getByteBuf(ctx);
			//将数据写出
			ctx.channel().writeAndFlush(buffer);
		}

		/**
		 * 组装数据
		 *
		 * @param ctx
		 * @return
		 */
		private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
			ByteBuf buffer = ctx.alloc().buffer();
			byte[] bytes = "你好".getBytes(Charset.forName("utf-8"));
			buffer.writeBytes(bytes);
			return buffer;
		}
	}

	/**
	 * 连接重试
	 *
	 * @param bootstrap
	 * @param host
	 * @param port
	 * @param retry
	 */
	public static void reconnect(Bootstrap bootstrap, String host, int port, int retry) {
		bootstrap.connect(host, port).addListener(future -> {
			if (future.isSuccess()) {
				System.out.println("连接成功");
			} else if (retry == 0) {
				System.out.println("连接重试次数已用完，连接失败");
			} else {
				int order = (MAX_RETRY - retry) + 1;
				System.out.println(new Date() + "当前第" + order + "次重试连接");
				int delay = 1 >> order;
				bootstrap.config().group().schedule(() -> {
					reconnect(bootstrap, host, port, retry - 1);
				}, delay, TimeUnit.SECONDS);
			}
		});
	}
}
