package de.htwberlin.schbuet.data_warehouse.services;

import de.htwberlin.schbuet.data_warehouse.data.csv.ProductCsv;
import de.htwberlin.schbuet.data_warehouse.data.main.Product;
import de.htwberlin.schbuet.data_warehouse.repos.ProductRepository;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;

@Service
public class ImportProductService {

    private final CsvParseService parseService;
    private final ProductRepository productRepository;

    public ImportProductService(CsvParseService parseService, ProductRepository productRepository) {
        this.parseService = parseService;
        this.productRepository = productRepository;
    }

    @SneakyThrows
    @Scheduled(cron = "0 * * * *")
    private void importAllProducts() {

        URL url = new URL("http://localhost:8080/export/export.csv");
        var list = parseService.parseInputStream(url.openStream());

        for (ProductCsv csv : list) {
            var item = productRepository.findById(csv.getIdAsUUID());
            if (item == null) {
                Product product = new Product();
                product.setImportDate(new Date());
                setAndSaveItem(csv, product);
            } else {
                setAndSaveItem(csv, item);
            }
        }
    }

    private void setAndSaveItem(ProductCsv csv, Product item) {
        item.setName(csv.getName());
        item.setCategory(csv.getName());
        item.setDescription(csv.getName());
        item.setYearOfProduction(csv.getYearOfProduction());
        item.setPriceInCents(csv.getPriceInCents());
        item.setItemNumber(csv.getItemNumber());

        productRepository.save(item);
    }
}
