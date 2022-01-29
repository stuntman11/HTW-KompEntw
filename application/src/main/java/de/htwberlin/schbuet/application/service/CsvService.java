package de.htwberlin.schbuet.application.service;

import com.opencsv.CSVWriter;
import de.htwberlin.schbuet.application.data.main.Product;
import de.htwberlin.schbuet.application.repos.ProductRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.StringWriter;
import java.util.UUID;

@Service
@Slf4j
public class CsvService {

    private final ProductRepository productRepository;

    public CsvService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    @SneakyThrows
    public void exportCsvToFile() {
        try (FileWriter file = new FileWriter("export-products.csv")) {
            file.write(getAllProductsAsCsv());
            log.info("export-products.csv was created");
        }
    }
    
    @SneakyThrows
    public String getAllProductsAsCsv() {
        var allProducts = productRepository.findAll();
        StringWriter sw = new StringWriter();
        
        try (CSVWriter csvWriter = new CSVWriter(sw)) {
            for (Product product : allProducts) {
                String[] csvRow = exportProductToCsvRow(product);
                csvWriter.writeNext(csvRow);
            }
        }
        return sw.toString();
    }

    public String getWarehouseExportItem(UUID id, int quantity, int deliveryTimeInDays, Double latitude, Double longitude) {
        var all = productRepository.findById(id);
        StringWriter sw = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(sw);

        String[] rowData = {
                id.toString(),
                String.valueOf(quantity),
                String.valueOf(deliveryTimeInDays),
                String.valueOf(latitude),
                String.valueOf(longitude)};

        csvWriter.writeNext(rowData);

        return csvWriter.toString();
    }
    
    private String[] exportProductToCsvRow(Product product) {
    	return new String[] {
            product.getId().toString(),
            product.getName(),
            product.getDescription(),
            product.getCategory(),
            product.getItemNumber(),
            String.valueOf(product.getPriceInCents()),
            String.valueOf(product.getYearOfProduction())
        };
    }
}
