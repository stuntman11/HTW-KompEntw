package de.htwberlin.schbuet.application.services;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.htwberlin.schbuet.application.data.main.Product;
import de.htwberlin.schbuet.application.repos.ProductRepository;
import de.htwberlin.schbuet.application.service.ExportService;

@SpringBootTest
class ExportServiceTest {
	@Autowired ExportService export;
	@Autowired ProductRepository productRepository;
	File dest;

	@BeforeEach
    void setUp() throws IOException {
        var basePath = System.getProperty("java.io.tmpdir");
        dest = new File(basePath, "export-products.csv");
        Files.deleteIfExists(dest.toPath());
        productRepository.deleteAll();
    }
	
	@Test
    void exportProductsShouldCreateAFile() throws IOException {
		productRepository.save(Product.builder()
                .name("Name")
                .description("Desc")
                .category("Category")
                .itemNumber("IT-1234")
                .priceInCents(1000)
                .yearOfProduction(2022)
                .createdDate(Calendar.getInstance().getTime())
                .build());
		
		export.createProductsExportFile();
        
		assertTrue(dest.exists());
    }
}
