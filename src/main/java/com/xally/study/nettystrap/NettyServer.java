package com.xally.study.nettystrap;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.Charset;
import java.util.Date;

public class NettyServer {

	public static void main(String[] args) {
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		NioEventLoopGroup boos = new NioEventLoopGroup();
		NioEventLoopGroup worker = new NioEventLoopGroup();
		serverBootstrap.group(boos,worker).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<NioSocketChannel>() {

			@Override protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
				nioSocketChannel.pipeline().addLast(new FistServiceHandler());
			}
		});
		serverBootstrap.bind("127.0.0.1",8000);
	}

	private static class FistServiceHandler extends ChannelInboundHandlerAdapter {

		@Override public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			ByteBuf buff=(ByteBuf)msg;
			System.out.println(new Date() + "服务端读取到得数据" + buff.toString(Charset.forName("utf-8")));
		}
	}
}
