package dev.gabrields.desafiojavaspringboot.domain.dto;

import java.math.BigDecimal;

import dev.gabrields.desafiojavaspringboot.domain.Product;

public class ProductDTO {
	private String name;
	private String description;
	private BigDecimal price;
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
		this.price = price;
	}
	
	public Product toProduct() {
		return new Product(this.name,this.description,this.price);
	}
	
}
