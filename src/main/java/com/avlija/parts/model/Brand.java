package com.avlija.parts.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "brands")
public class Brand implements Serializable {

    /**
	 * CAR BRAND MODEL WITH ID, NAME and list of CAR MODELS
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    @OneToMany(mappedBy="brand", cascade = CascadeType.ALL)
    Set<CarModel> carModels = new HashSet<CarModel>();

    
    public Brand() {
    }

    public Brand(String name) {
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
	 * @return the carModels
	 */
	public Set<CarModel> getCarModels() {
		return carModels;
	}

	/**
	 * @param carModels the carModels to set
	 */
	public void setCarModels(Set<CarModel> carModels) {
		this.carModels = carModels;
	}

	
}