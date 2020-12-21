package com.avlija.parts.model;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "carmodels")
public class CarModel implements Serializable {
    /**
	 * CAR MODEL - MODEL WITH ID, NAME and BRAND it belongs to
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    @ManyToOne
    @JoinColumn(name ="FK_BrandId")
    private Brand brand;
    
    public CarModel() {
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
	 * @return the brand
	 */
	public Brand getBrand() {
		return brand;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	
}