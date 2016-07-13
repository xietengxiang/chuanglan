package com.phei.netty.nio143;

import com.sun.xml.internal.ws.util.Pool.Marshaller;

import io.netty.buffer.ByteBuf;

public class MarshallingEncoder {

	private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
	Marshaller marshaller;
	
	public MarshallingEncoder(){
//		marshaller = MarshallingCodecFactory.buildMarshalling();
	}
	
	protected void encode(Object msg, ByteBuf out){
		
	}
}
