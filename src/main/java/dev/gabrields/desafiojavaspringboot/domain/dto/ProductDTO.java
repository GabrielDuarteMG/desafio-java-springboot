package dev.gabrields.desafiojavaspringboot.domain.dto;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import dev.gabrields.desafiojavaspringboot.domain.Product;

public class ProductDTO {
	
	private String id;
	
    @NotBlank(message = "Campo name Obrigratório")
    @NotEmpty(message = "Campo name Obrigratório")
    @NotNull
	private String name;

    @NotBlank(message = "Campo description Obrigratório")
    @NotEmpty(message = "Campo description Obrigratório")
    @NotNull
	private String description;

	@DecimalMin(value = "0.0", message = "Por favor insira um valor maior que zero.")
	@NotNull
	private BigDecimal price;
	
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
		this.price = price;
	}

	public Product toProduct() {
		return new Product(this.name, this.description, this.price);
	}

	

}
