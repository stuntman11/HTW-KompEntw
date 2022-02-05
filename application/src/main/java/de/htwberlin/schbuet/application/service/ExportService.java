package de.htwberlin.schbuet.application.service;

import de.htwberlin.schbuet.application.data.main.Product;
import de.htwberlin.schbuet.application.repos.ProductRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileWriter;

@Service
@Slf4j
public class ExportService {

    private final ProductRepository productRepository;

    public ExportService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    @SneakyThrows
    public void createProductsExportFile() {
        try (FileWriter file = new FileWriter("export-products.csv")) {
        	CsvExport csv = new CsvExport(file);
        	var allProducts = productRepository.findAll();
            
        	for (Product product : allProducts) {
        		csv.writeProduct(product);
        	}
            log.info("export-products.csv was created");
        }
    }
}
