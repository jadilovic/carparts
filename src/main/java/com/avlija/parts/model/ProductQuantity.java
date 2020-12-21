package com.avlija.parts.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
/**
 * PRODUCT QUANTITY  MODEL WITH ID composed of USER and PRODUCT
 * IT DEFINES Quantity for each USER of each PRODUCT
 */
@Entity
@Table(name = "product_quantity")
public class ProductQuantity {

	@EmbeddedId
	private UserProduct userProduct;
	
	@NotNull
	private int quantity;
	
	public ProductQuantity() {
	}

	public ProductQuantity(UserProduct userProduct, @NotNull int quantity) {
		this.userProduct = userProduct;
		this.quantity = quantity;
	}

	/**
	 * @return the userProduct
	 */
	public UserProduct getUserProduct() {
		return userProduct;
	}

	/**
	 * @param userProduct the userProduct to set
	 */
	public void setUserProduct(UserProduct userProduct) {
		this.userProduct = userProduct;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}

