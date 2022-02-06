package de.htwberlin.schbuet.data_warehouse.services;

import de.htwberlin.schbuet.data_warehouse.data.csv.ProductCsv;
import de.htwberlin.schbuet.data_warehouse.data.main.Product;
import de.htwberlin.schbuet.data_warehouse.repos.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ImportService {

    private final ProductRepository productRepository;

    public ImportService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Importiert alle Produktdaten einmal in der Stunde als Backup.
     * Die Daten werden nicht weiter verwendet
     */
    @Scheduled(cron = "0 * * * *")
    public void importProductsFromFile() {
        var basePath = System.getProperty("java.io.tmpdir");
        var file = new File(basePath, "export-products.csv");
        
        try (FileReader reader = new FileReader(file)) {
        	CsvImport csv = new CsvImport(reader);
        	List<ProductCsv> productsCsv = csv.readProducts();
        	
        	for (ProductCsv productCsv : productsCsv) {
        		importProduct(productCsv);
        	}
            log.info("Product list was imported");
        } catch (IOException e) {
            log.warn("Failed to import product list");
        }
    }
    
    private void importProduct(ProductCsv csv) {
    	var productId = UUID.fromString(csv.getId());
        var product = productRepository.findTop1ByProductId(productId);
        
        if (product == null) {
        	product = new Product();
        	product.setProductId(productId);
        	product.setImportDate(new Date());
        }
        product.setName(csv.getName());
        product.setCategory(csv.getCategory());
        product.setDescription(csv.getDescription());
        product.setYearOfProduction(csv.getYearOfProduction());
        product.setPriceInCents(csv.getPriceInCents());
        product.setItemNumber(csv.getItemNumber());

        productRepository.save(product);
    }
}
