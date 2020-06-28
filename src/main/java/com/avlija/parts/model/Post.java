package com.avlija.parts.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "post")
public class Post {
 
 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private int id;
 
 @Column(name = "user_id")
 private int userId;
 
 @Column(name = "product_id")
 private Long productId;
 
 @Column(name = "product_sifra")
 private String productSifra;
 
 @Column(name = "product_name")
 private String productName;
 
 @Column(name = "user_name")
 private String userName;
 
 @Column(name = "contact_option")
 private String contactOption; 
 
 @Column(name = "contact_info")
 private String contactInfo;
 
 @Column(name = "post_quantity")
 private int postQuantity;
 
 @Column(name = "active")
 private int active;
 
 @Column(name = "price")
 private double price;
 
 @Column(name="created")
 private Date created;
 
 public Post() {
	 
 }

public Post(int userId, Long productId, String productSifra, String contactOption, String contactInfo, int postQuantity,
		int active, double price, Date created) {
	this.userId = userId;
	this.productId = productId;
	this.productSifra = productSifra;
	this.contactOption = contactOption;
	this.contactInfo = contactInfo;
	this.postQuantity = postQuantity;
	this.active = active;
	this.price = price;
	this.created = created;
}

/**
 * @return the id
 */
public int getId() {
	return id;
}

/**
 * @param id the id to set
 */
public void setId(int id) {
	this.id = id;
}

/**
 * @return the userId
 */
public int getUserId() {
	return userId;
}

/**
 * @param userId the userId to set
 */
public void setUserId(int userId) {
	this.userId = userId;
}

/**
 * @return the productId
 */
public Long getProductId() {
	return productId;
}

/**
 * @param productId the productId to set
 */
public void setProductId(Long productId) {
	this.productId = productId;
}

/**
 * @return the productSifra
 */
public String getProductSifra() {
	return productSifra;
}

/**
 * @param productSifra the productSifra to set
 */
public void setProductSifra(String productSifra) {
	this.productSifra = productSifra;
}

/**
 * @return the contactOption
 */
public String getContactOption() {
	return contactOption;
}

/**
 * @param contactOption the contactOption to set
 */
public void setContactOption(String contactOption) {
	this.contactOption = contactOption;
}

/**
 * @return the contactInfo
 */
public String getContactInfo() {
	return contactInfo;
}

/**
 * @param contactInfo the contactInfo to set
 */
public void setContactInfo(String contactInfo) {
	this.contactInfo = contactInfo;
}

/**
 * @return the postQuantity
 */
public int getPostQuantity() {
	return postQuantity;
}

/**
 * @param postQuantity the postQuantity to set
 */
public void setPostQuantity(int postQuantity) {
	this.postQuantity = postQuantity;
}

/**
 * @return the active
 */
public int getActive() {
	return active;
}

/**
 * @param active the active to set
 */
public void setActive(int active) {
	this.active = active;
}

/**
 * @return the price
 */
public double getPrice() {
	return price;
}

/**
 * @param price the price to set
 */
public void setPrice(double price) {
	this.price = price;
}

/**
 * @return the created
 */
public Date getCreated() {
	return created;
}

/**
 * @param created the created to set
 */
public void setCreated(Date created) {
	this.created = created;
}

/**
 * @return the productName
 */
public String getProductName() {
	return productName;
}

/**
 * @param productName the productName to set
 */
public void setProductName(String productName) {
	this.productName = productName;
}

/**
 * @return the userName
 */
public String getUserName() {
	return userName;
}

/**
 * @param userName the userName to set
 */
public void setUserName(String userName) {
	this.userName = userName;
}

 

}