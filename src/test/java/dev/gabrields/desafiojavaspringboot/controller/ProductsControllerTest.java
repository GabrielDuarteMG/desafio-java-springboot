package dev.gabrields.desafiojavaspringboot.controller;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dev.gabrields.desafiojavaspringboot.domain.Product;
import dev.gabrields.desafiojavaspringboot.domain.ResponseError;
import dev.gabrields.desafiojavaspringboot.domain.dto.ProductDTO;
import dev.gabrields.desafiojavaspringboot.repositories.ProductRepository;
import dev.gabrields.desafiojavaspringboot.services.ProductService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProductsControllerTest {

	@MockBean
	private ProductRepository productRepository;
	@MockBean
	private ProductService productService;

	@InjectMocks
	private ProductsController productController;
	@Mock
	Executor executor;

	@Test
	void shouldFindAllProduct() throws Exception {
		Mockito.when(productRepository.findAll()).thenReturn(new ArrayList<>());

		ResponseEntity<List<Product>> findAllProductLists = productController.getAllProducts();
		assertEquals(0, findAllProductLists.getBody().size());
		assertEquals(HttpStatus.OK, findAllProductLists.getStatusCode());

	}

	@Test
	void shouldGetProductByID() throws Exception {

		Mockito.when(productService.findById("1"))
				.thenReturn(new Product("1", "Produto 1", "Produto da lista de testes", BigDecimal.valueOf(0)));

		ResponseEntity<Product> findByID = productController.getProducts("1");
		assertEquals("Produto 1", findByID.getBody().getName());
		assertEquals(HttpStatus.OK, findByID.getStatusCode());

	}

	@Test
	void shouldProductDeleteIfExist() throws Exception {

		Mockito.when(productService.deleteProduct("1")).thenReturn(Boolean.TRUE);

		ResponseEntity<?> deleteById = productController.deleteProduct("1");
		assertEquals(Boolean.TRUE, deleteById.getBody());
		assertEquals(HttpStatus.OK, deleteById.getStatusCode());
	}

	@SuppressWarnings("unchecked")
	@Test
	void shouldProductDeleteIfNotExist() throws Exception {

		Mockito.when(productService.deleteProduct("1")).thenReturn(Boolean.FALSE);

		ResponseEntity<ResponseError> deleteById = (ResponseEntity<ResponseError>) (productController
				.deleteProduct("1"));
		assertEquals("Produto não encontrado.", deleteById.getBody().getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, deleteById.getStatusCode());
	}

	@SuppressWarnings("unchecked")
	@Test
	void shouldInsertProduct() throws Exception {

		ProductDTO productPayload = new ProductDTO();
		productPayload.setDescription("Produto da lista de testes");
		productPayload.setName("Produto 1");
		productPayload.setPrice(BigDecimal.valueOf(10));
		Product productResponse = productPayload.toProduct();
		productPayload.setId("1");
		Mockito.when(productService.insertProduct(productPayload.toProduct())).thenReturn(productResponse);

		ResponseEntity<Product> insertProduct = (ResponseEntity<Product>) (productController
				.insertProduct(productPayload));
		assertNull(insertProduct.getBody());
		assertEquals(HttpStatus.CREATED, insertProduct.getStatusCode());
	}

	@SuppressWarnings("unchecked")
	@Test
	void shouldUpdateProductButNotExist() throws Exception {
		ProductDTO productPayload = new ProductDTO();
		productPayload.setDescription("Produto da lista de testes");
		productPayload.setName("Produto 1");
		Product productResponse = productPayload.toProduct();
		productPayload.setId("1");
		Mockito.when(productService.updateProduct("1", productPayload.toProduct())).thenReturn(productResponse);

		ResponseEntity<ResponseError> updateProductNotExist = (ResponseEntity<ResponseError>) (productController
				.updateProduct("1", productPayload));
		assertEquals("Produto não encontrado.", updateProductNotExist.getBody().getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, updateProductNotExist.getStatusCode());
	}


}
