package com.avlija.parts.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "makers")
public class ProductMaker implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "maker", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<ProductGroup> groups;

    public ProductMaker() {
    }

    public ProductMaker(String name) {
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
	 * @return the groups
	 */
	public Set<ProductGroup> getGroups() {
		return groups;
	}

	/**
	 * @param groups the groups to set
	 */
	public void setGroups(Set<ProductGroup> groups) {
		this.groups = groups;
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

    
}