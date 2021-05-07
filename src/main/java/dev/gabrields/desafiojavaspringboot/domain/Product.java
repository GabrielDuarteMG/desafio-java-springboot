package dev.gabrields.desafiojavaspringboot.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 788138279398216954L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	private String name;
	private String description;
	private BigDecimal price;

	public Product() {

	}

	public Product(String id, String name, String description, BigDecimal price) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public Product(String name, String description, BigDecimal price) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		// Prevent negative number
		if (price.compareTo(BigDecimal.ZERO) < 0)
			this.price = BigDecimal.ZERO;
		else
			this.price = price;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
