package com.phei.netty.nio103;

import java.util.List;

public class Customer {
	private Long customerNumbers;
	private String firstName;
	private String lastName;
	private List<String> middleNames;

	public Long getCustomerNumbers() {
		return customerNumbers;
	}

	public void setCustomerNumbers(Long customerNumbers) {
		this.customerNumbers = customerNumbers;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<String> getMiddleNames() {
		return middleNames;
	}

	public void setMiddleNames(List<String> middleNames) {
		this.middleNames = middleNames;
	}
}
