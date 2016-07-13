package com.chuanglan.myTest.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Student {
	private int id;
	private String name;
	private int age;
	private List<ClassRoom> rooms;
//	private ClassRoom classroom;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

//	public ClassRoom getClassroom() {
//		return classroom;
//	}
//
//	public void setClassroom(ClassRoom classroom) {
//		this.classroom = classroom;
//	}

	public Student(int id, String name, int age, List<ClassRoom> rooms) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.rooms = rooms;
	}

	public List<ClassRoom> getRooms() {
		return rooms;
	}

	public void setRooms(List<ClassRoom> rooms) {
		this.rooms = rooms;
	}

	// 无参够着函数一定需要，否则JXBContext无法正常解析。
	public Student() {
		super();
	}
}