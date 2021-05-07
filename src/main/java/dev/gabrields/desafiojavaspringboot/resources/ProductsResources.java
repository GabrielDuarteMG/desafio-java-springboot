package dev.gabrields.desafiojavaspringboot.resources;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.gabrields.desafiojavaspringboot.domain.Product;
import dev.gabrields.desafiojavaspringboot.domain.ResponseError;
import dev.gabrields.desafiojavaspringboot.domain.dto.ProductDTO;
import dev.gabrields.desafiojavaspringboot.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductsResources {
	@Autowired
	private ProductService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> getProducts(@PathVariable(required = true) String id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping(value = "/search")
	public ResponseEntity<List<Product>> searchProducts(@RequestParam(name = "q",required = false) String nameDescription,
			@RequestParam(name = "min_price", required = false) BigDecimal minPrice,
			@RequestParam(name = "max_price", required = false) BigDecimal maxPrice) {
		return ResponseEntity.ok(service.searchProduct(nameDescription, minPrice,maxPrice));
	}

	@GetMapping(value = "")
	public ResponseEntity<List<Product>> getAllProducts() {
		return ResponseEntity.ok(service.findAll());
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<List<Product>> deleteProduct(@PathVariable(required = true) String id) {
		return ResponseEntity.status(service.deleteProduct(id)).body(null);
	}
	
	@PostMapping()
	public ResponseEntity<?> insertProduct(@RequestBody ProductDTO prodDto) {
		try {
			var prod = prodDto.toProduct();
			if (prod.getPrice().compareTo(BigDecimal.ZERO) < 0) {
				return ResponseEntity.status(400)
						.body(new ResponseError(400, "O Valor do produto deve ser maior que zero!"));
			}
			var prodCreated = service.insertOrUpdateProduct(prod);
			var location = URI.create(String.format("/products/%s", prodCreated.getId()));
			return ResponseEntity.created(location).build();
		} catch (Exception e) {
			return ResponseEntity.status(400).body(new ResponseError(400, e.toString()));
		}

	}

	@PutMapping()
	public ResponseEntity<?> updateProduct(@RequestBody ProductDTO prodDto) {
		try {
			var prod = prodDto.toProduct();
			if (prod.getPrice().compareTo(BigDecimal.ZERO) < 0) {
				return ResponseEntity.status(400)
						.body(new ResponseError(400, "O Valor do produto deve ser maior que zero!"));
			}
			var prodCreated = service.insertOrUpdateProduct(prod);
			var location = URI.create(String.format("/products/%s", prodCreated.getId()));
			return ResponseEntity.created(location).build();
		} catch (Exception e) {
			return ResponseEntity.status(400).body(new ResponseError(400, e.toString()));
		}

	}

}
