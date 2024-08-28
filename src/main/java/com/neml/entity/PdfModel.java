package com.neml.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;

import lombok.Data;


public class PdfModel {
	private String auctionId; 
	private Date auctionDate;
	private String material;
    private String location;
    private String bidderName;
    private double lowestRate;
    private int quantity;
    private String productName;
    private String productDescription;
    private String auctionDescription;
    private String orderStatus;
	
    
    public String getAuctionId() {
		return auctionId;
	}
	public void setAuctionId(String auctionId) {
		this.auctionId = auctionId;
	}

	public Date getAuctionDate() {
		return auctionDate;
	}
	public void setAuctionDate(Date auctionDate) {
		this.auctionDate = auctionDate;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getBidderName() {
		return bidderName;
	}
	public void setBidderName(String bidderName) {
		this.bidderName = bidderName;
	}
	public double getLowestRate() {
		return lowestRate;
	}
	public void setLowestRate(double lowestRate) {
		this.lowestRate = lowestRate;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getAuctionDescription() {
		return auctionDescription;
	}
	public void setAuctionDescription(String auctionDescription) {
		this.auctionDescription = auctionDescription;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
    
}