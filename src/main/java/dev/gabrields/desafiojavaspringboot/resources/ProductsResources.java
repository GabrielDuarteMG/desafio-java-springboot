package dev.gabrields.desafiojavaspringboot.resources;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	public ResponseEntity<List<Product>> searchProducts(
			@RequestParam(name = "q", required = false) String nameDescription,
			@RequestParam(name = "min_price", required = false) BigDecimal minPrice,
			@RequestParam(name = "max_price", required = false) BigDecimal maxPrice) {
		return ResponseEntity.ok(service.searchProduct(nameDescription, minPrice, maxPrice));
	}

	@GetMapping(value = "")
	public ResponseEntity<List<Product>> getAllProducts() {
		return ResponseEntity.ok(service.findAll());
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<List<Product>> deleteProduct(@PathVariable(required = true) String id) {
		var product = service.deleteProduct(id);
		if (product) {
			return ResponseEntity.ok(null);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PostMapping()
	public ResponseEntity<?> insertProduct(@Valid @RequestBody ProductDTO prodDto) {
		try {
			var prod = prodDto.toProduct();
			var prodCreated = service.insertProduct(prod);
			return ResponseEntity.status(HttpStatus.CREATED).body(prodCreated);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseError(HttpStatus.BAD_REQUEST, e.toString()));
		}
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable(required = true) String id, @RequestBody ProductDTO prodDto) {
		try {
			var prod = prodDto.toProduct();
			var prodUpdate = service.updateProduct(id, prod);
			if (prodUpdate != null) {
				return ResponseEntity.ok(prodUpdate);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ResponseError(HttpStatus.BAD_REQUEST, "Produto não encontrado."));
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseError(HttpStatus.BAD_REQUEST, e.toString()));
		}

	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseError handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return new ResponseError(HttpStatus.BAD_REQUEST, errors.toString());
	}
}
