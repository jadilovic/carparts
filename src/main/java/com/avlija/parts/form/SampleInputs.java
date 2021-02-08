package com.avlija.parts.form;

import org.springframework.format.annotation.DateTimeFormat;

import com.avlija.parts.model.Product;

import java.util.Date;
import java.util.List;

public class SampleInputs {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateField;
    private Long id;
    private int userId;
    private int postId;
    private Double doubleField;
    private Integer quantity;
    private String sifra;
    private String colorField;
    private String brandName;
    private String modelName;
    private String groupName;
    private String email;
    private String password;
    private String token;
    private String confirmPassword;
    private String name;
    private String productKeyword;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date dateTimeField;
    
    private List<Product> replaceProducts;
    
    
    public SampleInputs() {
    	
    }

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the sifra
	 */
	public String getSifra() {
		return sifra;
	}

	/**
	 * @param sifra the sifra to set
	 */
	public void setSifra(String sifra) {
		this.sifra = sifra;
	}



	/**
	 * @return the dateField
	 */
	public Date getDateField() {
		return dateField;
	}

	/**
	 * @param dateField the dateField to set
	 */
	public void setDateField(Date dateField) {
		this.dateField = dateField;
	}

	/**
	 * @return the doubleField
	 */
	public Double getDoubleField() {
		return doubleField;
	}

	/**
	 * @param doubleField the doubleField to set
	 */
	public void setDoubleField(Double doubleField) {
		this.doubleField = doubleField;
	}


	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the colorField
	 */
	public String getColorField() {
		return colorField;
	}

	/**
	 * @param colorField the colorField to set
	 */
	public void setColorField(String colorField) {
		this.colorField = colorField;
	}

	/**
	 * @return the dateTimeField
	 */
	public Date getDateTimeField() {
		return dateTimeField;
	}

	/**
	 * @param dateTimeField the dateTimeField to set
	 */
	public void setDateTimeField(Date dateTimeField) {
		this.dateTimeField = dateTimeField;
	}

	/**
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}

	/**
	 * @param brandName the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * @return the modelName
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * @param modelName the modelName to set
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<Product> getReplaceProducts() {
		return replaceProducts;
	}

	public void setReplaceProducts(List<Product> replaceProducts) {
		this.replaceProducts = replaceProducts;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductKeyword() {
		return productKeyword;
	}

	public void setProductKeyword(String productKeyword) {
		this.productKeyword = productKeyword;
	}
	
	
}