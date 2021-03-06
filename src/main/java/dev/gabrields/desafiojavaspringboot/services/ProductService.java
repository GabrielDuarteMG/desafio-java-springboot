package dev.gabrields.desafiojavaspringboot.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.gabrields.desafiojavaspringboot.domain.Product;
import dev.gabrields.desafiojavaspringboot.repositories.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository repo;

	public Product findById(String id) {
		Optional<Product> obj = repo.findById(id);
		return obj.orElse(null);
	}

	public List<Product> searchProduct(String nameDescription, BigDecimal minPrice, BigDecimal maxPrice) {
		List<Product> products = null;
		List<Product> filterResult = new ArrayList<>();
		if (nameDescription != null && !nameDescription.isEmpty())
			products = repo.findTitleByNameDescription(nameDescription);
		else
			products = repo.findAll();
		
		products.forEach(product -> {
			if (minPrice != null && product.getPrice().compareTo(minPrice) > 0) {
				filterResult.add(product);
			}
			if (maxPrice != null && product.getPrice().compareTo(maxPrice) == 1) {
				filterResult.add(product);
			}
		});

		if (!filterResult.isEmpty())
			return filterResult;
		else
			return products;
	}

	public List<Product> findAll() {
		return repo.findAll();
	}

	public Boolean deleteProduct(String id) {
		if (repo.existsById(id)) {
			repo.deleteById(id);
			return true;
		} else {
			return false;
		}
	}

	public Product updateProduct(String id, Product prod) {
		if (repo.existsById(id)) {
			prod.setId(id);
			repo.save(prod);
			return prod;
		} else {
			return null;
		}
	}

	public Product insertProduct(Product prod) {
		return repo.save(prod);
	}

}
