package com.avlija.parts.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "groups")
public class ProductGroup implements Serializable {

    /**
	 *  
	 * PRODUCT GROUP MODEL with ID, NAME and a list of products that it includes
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    @OneToMany(mappedBy="productGroup", cascade = CascadeType.ALL)
    Set<Product> products = new HashSet<Product>();

    
    public ProductGroup() {
    }

    public ProductGroup(String name) {
        this.name = name;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the products
	 */
	public Set<Product> getProducts() {
		return products;
	}

	/**
	 * @param products the products to set
	 */
	public void setProducts(Set<Product> products) {
		this.products = products;
	}

}