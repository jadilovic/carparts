package com.avlija.parts.form;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class SampleInputs {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateField;

    private Double doubleField;
    private Integer numberField;
    private String sifra;
    private String colorField;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date dateTimeField;
    
    
    public SampleInputs() {
    	
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
	 * @return the dateField
	 */
	public Date getDateField() {
		return dateField;
	}

	/**
	 * @param dateField the dateField to set
	 */
	public void setDateField(Date dateField) {
		this.dateField = dateField;
	}

	/**
	 * @return the doubleField
	 */
	public Double getDoubleField() {
		return doubleField;
	}

	/**
	 * @param doubleField the doubleField to set
	 */
	public void setDoubleField(Double doubleField) {
		this.doubleField = doubleField;
	}

	/**
	 * @return the numberField
	 */
	public Integer getNumberField() {
		return numberField;
	}

	/**
	 * @param numberField the numberField to set
	 */
	public void setNumberField(Integer numberField) {
		this.numberField = numberField;
	}

	/**
	 * @return the colorField
	 */
	public String getColorField() {
		return colorField;
	}

	/**
	 * @param colorField the colorField to set
	 */
	public void setColorField(String colorField) {
		this.colorField = colorField;
	}

	/**
	 * @return the dateTimeField
	 */
	public Date getDateTimeField() {
		return dateTimeField;
	}

	/**
	 * @param dateTimeField the dateTimeField to set
	 */
	public void setDateTimeField(Date dateTimeField) {
		this.dateTimeField = dateTimeField;
	}

    
}