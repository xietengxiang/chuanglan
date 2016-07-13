package com.chuanglan.myTest.xml;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

public class TestXml2Java {
	@Test
	public void beanToXML() {
		List<ClassRoom> rooms = new ArrayList<ClassRoom>();
		ClassRoom classroom1 = new ClassRoom(1, "软件工程", 2);
		ClassRoom classroom2 = new ClassRoom(2, "XX工程", 3);
		ClassRoom classroom3 = new ClassRoom(3, "AA工程", 4);
		rooms.add(classroom1);
		rooms.add(classroom2);
		rooms.add(classroom3);
		Student student = new Student(101, "张三", 22, rooms);

		try {
			JAXBContext context = JAXBContext.newInstance(Student.class);
			Marshaller marshaller = context.createMarshaller();
//			marshaller.marshal(student, System.out);
			
			StringWriter writer = new StringWriter();
			marshaller.marshal(student, writer);
			System.out.println(writer);
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void XMLStringToBean() {
		String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><student><age>22</age><rooms><grade>4</grade><id>1</id><name>软件工程</name></rooms><id>101</id><name>张三</name></student>";
		try {
			JAXBContext context = JAXBContext.newInstance(Student.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Student student = (Student) unmarshaller.unmarshal(new StringReader(xmlStr));
			System.out.println(student.getAge());
			System.out.println(student.getRooms().get(0).getName());
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}
}
