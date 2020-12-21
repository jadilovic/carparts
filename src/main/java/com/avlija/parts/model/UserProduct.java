


package com.avlija.parts.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
/**
 * USER PRODUCT PRIMARY KEY MODEL TO KEEP TRACK OF PRODUCT QUANTITY FOR EACH USER
 */
@Embeddable
public class UserProduct implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotNull
	private int userId;
	
	@NotNull
	private Long productId;
	
	public UserProduct() {
	}

	public UserProduct(@NotNull int userId, @NotNull Long productId) {
		this.userId = userId;
		this.productId = productId;
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
	
}
