package com.avlija.parts.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "products")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sifra;
    private String name;
    
    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private ProductGroup productGroup;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private ProductMaker productMaker;
    
    private String description;
    private double price;
    private int quantity;

    @ManyToMany
    @JoinTable(name="tbl_replace",
     joinColumns=@JoinColumn(name="partId"),
     inverseJoinColumns=@JoinColumn(name="replaceId")
    )
    private List<Product> products;

    @ManyToMany
    @JoinTable(name="tbl_replace",
     joinColumns=@JoinColumn(name="replaceId"),
     inverseJoinColumns=@JoinColumn(name="partId")
    )
    private List<Product> productOf;
    
    
    public Product() {
    }


	public Product(Long id, String sifra, String name, ProductGroup productGroup, ProductMaker productMaker,
			String description, double price, int quantity, List<Product> products, List<Product> productOf) {
		super();
		this.id = id;
		this.sifra = sifra;
		this.name = name;
		this.productGroup = productGroup;
		this.productMaker = productMaker;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.products = products;
		this.productOf = productOf;
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
	 * @return the productGroup
	 */
	public ProductGroup getProductGroup() {
		return productGroup;
	}


	/**
	 * @param productGroup the productGroup to set
	 */
	public void setProductGroup(ProductGroup productGroup) {
		this.productGroup = productGroup;
	}


	/**
	 * @return the productMaker
	 */
	public ProductMaker getProductMaker() {
		return productMaker;
	}


	/**
	 * @param productMaker the productMaker to set
	 */
	public void setProductMaker(ProductMaker productMaker) {
		this.productMaker = productMaker;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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


	/**
	 * @return the products
	 */
	public List<Product> getProducts() {
		return products;
	}


	/**
	 * @param products the products to set
	 */
	public void setProducts(List<Product> products) {
		this.products = products;
	}


	/**
	 * @return the productOf
	 */
	public List<Product> getProductOf() {
		return productOf;
	}


	/**
	 * @param productOf the productOf to set
	 */
	public void setProductOf(List<Product> productOf) {
		this.productOf = productOf;
	}


}