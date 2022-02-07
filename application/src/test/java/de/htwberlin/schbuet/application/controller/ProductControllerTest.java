package de.htwberlin.schbuet.application.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.htwberlin.schbuet.application.data.main.Product;
import de.htwberlin.schbuet.application.data.response.ResponseBasicProduct;
import de.htwberlin.schbuet.application.repos.ProductRepository;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
	@Autowired MockMvc rest;
	@Autowired ProductRepository productRepository;
	ObjectMapper json = new ObjectMapper();
	Product product;
	
	@BeforeEach
	void setUp() {
		productRepository.deleteAll();
		
		product = productRepository.save(Product.builder()
                .name("Name")
                .description("Desc")
                .category("Category")
                .itemNumber("IT-1234")
                .priceInCents(1000)
                .yearOfProduction(2022)
                .createdDate(Calendar.getInstance().getTime())
                .build());
	}
	
	@Test
	void returnsBasicProductList() throws Exception {
		var response = rest.perform(get("/api/v1/product/")).andReturn().getResponse();
		
		assertEquals(200, response.getStatus());
		var body = json.readValue(response.getContentAsString(), ResponseBasicProduct[].class);
		assertEquals(1, body.length);
		assertEquals(product.getId(), body[0].getId());
	}
	
	@Test
	void getProductValidatesProductId() throws Exception {
		var response = rest.perform(get("/api/v1/product/invalid-uuid")).andReturn().getResponse();
		
		assertEquals(400, response.getStatus());
	}
	
	@Test
	void deleteProductValidatesProductId() throws Exception {
		var response = rest.perform(delete("/api/v1/product/invalid-uuid")).andReturn().getResponse();
		
		assertEquals(400, response.getStatus());
	}
	
	@Test
	void createProductValidatesProductRequest() throws Exception {
		var response = rest.perform(post("/api/v1/product/")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}"))
				.andReturn()
				.getResponse();
		
		assertEquals(400, response.getStatus());
	}
	
	@Test
	void updateProductValidatesProductRequest() throws Exception {
		var response = rest.perform(put("/api/v1/product/" + product.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}"))
				.andReturn()
				.getResponse();
		
		assertEquals(400, response.getStatus());
	}
	
	@Test
	void updateProductValidatesProductId() throws Exception {
		var response = rest.perform(put("/api/v1/product/invalid-uuid")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}"))
				.andReturn()
				.getResponse();
		
		assertEquals(400, response.getStatus());
	}
}
