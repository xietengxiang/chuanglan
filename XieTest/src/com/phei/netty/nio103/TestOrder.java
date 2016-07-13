package com.phei.netty.nio103;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

public class TestOrder {

	private IBindingFactory factory = null;
	private StringWriter writer = null;
	private StringReader reader = null;
	private static final String CharserName = "UTF-8";
	
	private String encode2Xml(Order order) throws JiBXException, IOException{
		factory = BindingDirectory.getFactory(Order.class);
		writer = new StringWriter();
		IMarshallingContext mctx = factory.createMarshallingContext();
		mctx.setIndent(2);
		mctx.marshalDocument(order, CharserName, null, writer);
		String xmlStr = writer.toString();
		writer.close();
		System.out.println(xmlStr);
		return xmlStr;
	}
	
	private Order decode2Order(String xmlStr) throws JiBXException{
		reader = new StringReader(xmlStr);
		IUnmarshallingContext uctx = factory.createUnmarshallingContext();
		Order order = (Order) uctx.unmarshalDocument(reader);
		return order;
	}
	
	public static void main(String[] args) throws JiBXException, IOException {
		TestOrder test = new TestOrder();
		Order order = new Order();
		String xmlStr = test.encode2Xml(order);
		test.decode2Order(xmlStr);
	}
}
