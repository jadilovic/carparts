package com.avlija.parts.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sifra;
    private String name;
    
    @ManyToOne
    @JoinColumn(name ="FK_ProductGroupId")
    private ProductGroup productGroup;
    
    @ManyToOne
    @JoinColumn(name ="FK_ProductMakerId")
    private ProductMaker productMaker;
    
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    	private Set<Transaction> transactions;

    
    private String description;
    private double price;
    
    // private int quantity;

   // @ManyToMany(cascade = CascadeType.MERGE)
   // @JoinTable(name = "part_hierarchy", 
   //             joinColumns = { @JoinColumn(name = "part_id")}, 
   //             inverseJoinColumns={@JoinColumn(name="child_part_id")})  
   // private List<Product> products;

    //@ManyToMany(cascade = CascadeType.MERGE, mappedBy = "products")
    //private List<Product> children;  
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "part_hierarchy",
            joinColumns = {
                    @JoinColumn(name = "part_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "child_part_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private List <Product> products = new ArrayList<>();
    
    public Product() {
    }

    
    
	/**
	 * @return the transactions
	 */
	public Set<Transaction> getTransactions() {
		return transactions;
	}



	/**
	 * @param transactions the transactions to set
	 */
	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
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
	 
	public int getQuantity() {
		return quantity;
	}


	/**
	 * @param quantity the quantity to set
	 
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
*/

	/**
	 * @return the products
	 */
	public List <Product> getProducts() {
		return products;
	}


	/**
	 * @param products the products to set
	 */
	public void setProducts(List  <Product> products) {
		this.products = products;
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

}