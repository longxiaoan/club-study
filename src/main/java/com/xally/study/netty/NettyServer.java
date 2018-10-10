package com.xally.study.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * Created by xiaoanlong on 2018/10/8.
 */
public class NettyServer {

	public static void main(String[] args) {
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		NioEventLoopGroup boss = new NioEventLoopGroup();
		NioEventLoopGroup worker = new NioEventLoopGroup();
		serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<NioSocketChannel>() {

			protected void initChannel(NioSocketChannel ch) {
				ch.pipeline().addLast(new StringDecoder());
				ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {

					@Override protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
						System.out.println(s);
					}
				});
			}
		});
		bindPort(serverBootstrap,8000);
	}

	public static void bindPort(ServerBootstrap serverBootstrap,int port) {
		ChannelFuture bind = serverBootstrap.bind(port);
		bind.addListener(future -> {
			if(future.isSuccess()){
				System.out.println("绑定成功");
			}else{
				System.out.println("绑定失败");
				bindPort(serverBootstrap,port+1);
			}
		});
	}
}
