package de.htwberlin.schbuet.application.service;

import com.opencsv.CSVWriter;
import de.htwberlin.schbuet.application.data.main.Product;
import de.htwberlin.schbuet.application.repos.ProductRepository;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.UUID;

@Service
public class CsvService {

    private final ProductRepository productRepository;

    public CsvService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public CSVWriter getAllProductAsCSV() {
        var all = productRepository.findAll();
        StringWriter sw = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(sw);
        for (Product product : all) {
            String[] rowData = {
                    product.getId().toString(),
                    product.getName(),
                    product.getDescription(),
                    product.getCategory(),
                    product.getItemNumber(),
                    String.valueOf(product.getPriceInCents()),
                    String.valueOf(product.getYearOfProduction())
            };
            csvWriter.writeNext(rowData);
        }
        return csvWriter;
    }

    public CSVWriter getWarehouseExportItem(UUID id, int quantity, int deliveryTimeInDays, Double latitude, Double longitude) {
        var all = productRepository.findAll();
        StringWriter sw = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(sw);

        String[] rowData = {
                id.toString(),
                String.valueOf(quantity),
                String.valueOf(deliveryTimeInDays),
                String.valueOf(latitude),
                String.valueOf(longitude)};

        csvWriter.writeNext(rowData);

        return csvWriter;
    }
}
