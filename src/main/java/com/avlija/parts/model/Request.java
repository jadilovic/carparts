package com.avlija.parts.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="requests")
public class Request implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
    private Date created;
    
    private String sifra1;
    private String sifra2;
    private String sifra3;
    private String sifra4;
    private String sifra5;
    
    private int completed;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    public Request() {
    }

	public Request(Date created, String sifra1, String sifra2, String sifra3, String sifra4, String sifra5, User user) {
		this.created = created;
		this.sifra1 = sifra1;
		this.sifra2 = sifra2;
		this.sifra3 = sifra3;
		this.sifra4 = sifra4;
		this.sifra5 = sifra5;
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
	 * @return the sifra1
	 */
	public String getSifra1() {
		return sifra1;
	}

	/**
	 * @param sifra1 the sifra1 to set
	 */
	public void setSifra1(String sifra1) {
		this.sifra1 = sifra1;
	}

	/**
	 * @return the sifra2
	 */
	public String getSifra2() {
		return sifra2;
	}

	/**
	 * @param sifra2 the sifra2 to set
	 */
	public void setSifra2(String sifra2) {
		this.sifra2 = sifra2;
	}

	/**
	 * @return the sifra3
	 */
	public String getSifra3() {
		return sifra3;
	}

	/**
	 * @param sifra3 the sifra3 to set
	 */
	public void setSifra3(String sifra3) {
		this.sifra3 = sifra3;
	}

	/**
	 * @return the sifra4
	 */
	public String getSifra4() {
		return sifra4;
	}

	/**
	 * @param sifra4 the sifra4 to set
	 */
	public void setSifra4(String sifra4) {
		this.sifra4 = sifra4;
	}

	/**
	 * @return the sifra5
	 */
	public String getSifra5() {
		return sifra5;
	}

	/**
	 * @param sifra5 the sifra5 to set
	 */
	public void setSifra5(String sifra5) {
		this.sifra5 = sifra5;
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

	public int getCompleted() {
		return completed;
	}

	public void setCompleted(int completed) {
		this.completed = completed;
	}

}