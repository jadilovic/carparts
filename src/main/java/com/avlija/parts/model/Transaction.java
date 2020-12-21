package com.avlija.parts.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;
/**
 * TRANSACTION MODEL TO RECORD CHANGES IN QUANTITY OF PRODUCTS FOR CERTAIN USERS
 */
@Entity
@Table(name="transactions")
public class Transaction implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
    private Date created;
    
    private int transQuant;

    private double totalValue;
    
    private String transType;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id")
    private Product product;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    public Transaction() {
    }

	public Transaction(Date created, int transQuant, double totalValue, String transType, Product product, User user) {
		this.created = created;
		this.totalValue = totalValue;
		this.transQuant = transQuant;
		this.transType = transType;
		this.product = product;
		this.user = user;
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
	 * @return the totalCost
	 */
	public double getTotalValue() {
		return totalValue;
	}

	/**
	 * @param totalCost the totalCost to set
	 */
	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}

	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the transQuant
	 */
	public int getTransQuant() {
		return transQuant;
	}

	/**
	 * @param transQuant the transQuant to set
	 */
	public void setTransQuant(int transQuant) {
		this.transQuant = transQuant;
	}

	/**
	 * @return the transType
	 */
	public String getTransType() {
		return transType;
	}

	/**
	 * @param transType the transType to set
	 */
	public void setTransType(String transType) {
		this.transType = transType;
	}
    
    
}