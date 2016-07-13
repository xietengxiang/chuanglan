package com.phei.netty.nio103;

public class Order {

	private Long orderNumber;
	private Customer customer;
	private Address billTo;
	private Shipping shippimg;
	private Address shipTo;
	private Float total;

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Address getBillTo() {
		return billTo;
	}

	public void setBillTo(Address billTo) {
		this.billTo = billTo;
	}

	public Shipping getShippimg() {
		return shippimg;
	}

	public void setShippimg(Shipping shippimg) {
		this.shippimg = shippimg;
	}

	public Address getShipTo() {
		return shipTo;
	}

	public void setShipTo(Address shipTo) {
		this.shipTo = shipTo;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}
}
