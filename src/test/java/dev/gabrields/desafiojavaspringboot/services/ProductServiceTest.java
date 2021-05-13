package dev.gabrields.desafiojavaspringboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import dev.gabrields.desafiojavaspringboot.domain.Product;
import dev.gabrields.desafiojavaspringboot.repositories.ProductRepository;

@SpringBootTest(classes = ProductService.class)
class ProductServiceTest {
	@Autowired
	private ProductService productService;
	@MockBean
	private ProductRepository productRepository;

	@Test
	void shouldFindAllAndReturnEmpty() {
		Mockito.when(productRepository.findAll()).thenReturn(new ArrayList<>());
		var result = productService.findAll();
		assertEquals(0, result.size());
	}

	@Test
	void shouldFindAll() {
		var products = new ArrayList<Product>();
		products.add(new Product("1", "Produto 1", "Produto da lista de testes", BigDecimal.valueOf(0)));
		products.add(new Product("2", "Produto 2", "Produto da lista de testes", BigDecimal.valueOf(0)));
		products.add(new Product("3", "Produto 3", "Produto da lista de testes", BigDecimal.valueOf(0)));

		Mockito.when(productRepository.findAll()).thenReturn(products);
		var result = productService.findAll();
		assertEquals(3, result.size());
	}

	@Test
	void shouldFindById() {
		Mockito.when(productRepository.findById("1")).thenReturn(
				Optional.of(new Product("1", "Produto 1", "Produto da lista de testes", BigDecimal.valueOf(0))));
		var result = productService.findById("1");
		assertNotNull(result.getId());
	}

	@Test
	void shouldFindByIdReturnEmpty() {
		Mockito.when(productRepository.findById("1")).thenReturn(
				Optional.of(new Product("1", "Produto 1", "Produto da lista de testes", BigDecimal.valueOf(0))));
		var result = productService.findById("2");
		assertNull(result);
	}

	@Test
	void shouldDeleteById() {
		Mockito.when(productRepository.existsById("1")).thenReturn(true);
		var result = productService.deleteProduct("1");
		verify(productRepository).deleteById("1");
		assertEquals(true, result);
	}

	@Test
	void shouldDeleteByIdButNotExist() {
		Mockito.when(productRepository.existsById("1")).thenReturn(false);
		var result = productService.deleteProduct("1");
		assertEquals(false, result);
	}

	@Test
	void shouldUpdateProduct() {
		var productExample = new Product();
		productExample.setDescription("Produto da lista de testes");
		productExample.setId("1");
		productExample.setName("Produto 1");
		productExample.setPrice(BigDecimal.valueOf(-1));
		productExample.setPrice(BigDecimal.valueOf(0));
		Mockito.when(productRepository.existsById("1")).thenReturn(true);
		Mockito.when(productRepository.save(ArgumentMatchers.any(Product.class))).thenReturn(productExample);
		var result = productService.updateProduct("1", productExample);
		assertNotNull(result);
	}

	@Test
	void shouldUpdateProductButNotExist() {
		var productExample = new Product( "Produto 1", "Produto da lista de testes", BigDecimal.valueOf(0));
		productExample.setId("1");
		Mockito.when(productRepository.existsById("1")).thenReturn(false);
		var result = productService.updateProduct("1", productExample);
		assertNull(result);
	}

	@Test
	void shouldInsertProduct() {
		var productExample = new Product("1", "Produto 1", "Produto da lista de testes", BigDecimal.valueOf(0));
		
		Mockito.when(productRepository.save(ArgumentMatchers.any(Product.class))).thenReturn(productExample);
		var newProduct = productService.insertProduct(productExample);
		assertEquals(newProduct,productExample);
	}
	
	@Test
	void shouldSearchProductWithName() {
		var productExample = new Product("1", "Produto de busca", "Produto da lista de testes", BigDecimal.valueOf(0));
		var products = new ArrayList<Product>();
		products.add(new Product("1", "Produto 1", "Produto de busca", BigDecimal.valueOf(0)));
		products.add(new Product("2", "Produto 2", "Produto de busca", BigDecimal.valueOf(0)));
		Mockito.when(productRepository.findTitleByNameDescription("Produto de busca")).thenReturn(products);
		var newProduct = productService.searchProduct(productExample.getName(), null, null);

		assertEquals(2,newProduct.size());
	}
	
	@Test
	void shouldSearchProductWithNameNotFound() {
		var products = new ArrayList<Product>();
		products.add(new Product("1", "Produto 1", "Produto de busca", BigDecimal.valueOf(0)));
		products.add(new Product("2", "Produto 2", "Produto de busca", BigDecimal.valueOf(0)));
		Mockito.when(productRepository.findTitleByNameDescription("Produto de busca")).thenReturn(products);
		var newProduct = productService.searchProduct("NOT_FOUND", null, null);

		assertEquals(0,newProduct.size());
	}
	
	@Test
	void shouldSearchProductWithoutName() {
		var products = new ArrayList<Product>();
		products.add(new Product("1", "Produto 1", "Produto da lista de testes", BigDecimal.valueOf(2)));
		products.add(new Product("2", "Produto 2", "Produto da lista de testes", BigDecimal.valueOf(35)));
		products.add(new Product("3", "Produto 3", "Produto da lista de testes", BigDecimal.valueOf(250)));
		Mockito.when(productRepository.findAll()).thenReturn(products);
		var newProduct = productService.searchProduct(null,BigDecimal.valueOf(2),BigDecimal.valueOf(35));
		for (Product product : newProduct) {
			System.out.println(product.getDescription());
		}
		assertEquals(3,newProduct.size());
	}
}
