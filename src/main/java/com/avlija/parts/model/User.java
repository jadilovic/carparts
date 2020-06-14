package com.avlija.parts.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user")
public class User {
 
 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private int id;
 
 @Column(name = "email")
 private String email;
 
 @Column(name = "firstname")
 private String firstname; 
 
 @Column(name = "lastname")
 private String lastname;
 
 @Column(name = "password")
 private String password;
 
 @Column(name = "active")
 private int active;
 
 @Column(name = "country")
 private String country;
 
 @Transient
 private String role;
 
 @ManyToMany(cascade=CascadeType.ALL)
 @JoinTable(name="user_role",
 joinColumns=@JoinColumn(name="user_id"),
 inverseJoinColumns=@JoinColumn(name="role_id"))
 private Set<Role> roles;

 @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
         cascade = CascadeType.ALL)
 private Set<Transaction> transactions;
 
 public User() {
	 
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
 * @return the role
 */
public String getRole() {
	return role;
}

/**
 * @param role the role to set
 */
public void setRole(String role) {
	this.role = role;
}

public int getId() {
  return id;
 }

 public void setId(int id) {
  this.id = id;
 }

 public String getEmail() {
  return email;
 }

 public void setEmail(String email) {
  this.email = email;
 }

 public String getFirstname() {
  return firstname;
 }

 public void setFirstname(String firstname) {
  this.firstname = firstname;
 }

 public String getLastname() {
  return lastname;
 }

 public void setLastname(String lastname) {
  this.lastname = lastname;
 }

 public String getPassword() {
  return password;
 }

 public void setPassword(String password) {
  this.password = password;
 }

 public int getActive() {
  return active;
 }

 public void setActive(int active) {
  this.active = active;
 }

 public Set<Role> getRoles() {
  return roles;
 }

 public void setRoles(Set<Role> roles) {
  this.roles = roles;
 }

/**
 * @return the country
 */
public String getCountry() {
	return country;
}

/**
 * @param country the country to set
 */
public void setCountry(String country) {
	this.country = country;
}
 
}