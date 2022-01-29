package de.htwberlin.schbuet.data_warehouse.services;

import de.htwberlin.schbuet.data_warehouse.data.csv.ProductCsv;
import de.htwberlin.schbuet.data_warehouse.data.main.Product;
import de.htwberlin.schbuet.data_warehouse.repos.ProductRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

@Service
@Slf4j
public class ImportService {

    private final CsvService parseService;
    private final ProductRepository productRepository;

    public ImportService(CsvService parseService, ProductRepository productRepository) {
        this.parseService = parseService;
        this.productRepository = productRepository;
    }

    /**
     * Importiert alle Produktdaten einmal in der Stunde als Backup.
     * Die Daten werden nicht weiter verwendet
     */
    @SneakyThrows
    @Scheduled(cron = "0 * * * *")
    public void importProductsFromCsv() {

        var file = new File("export-products.csv");
        if (file.exists() && file.isFile() && file.canRead()) {
            var inputStream = new FileInputStream(file);
            var list = parseService.parseProductInputStream(inputStream);

            for (ProductCsv csv : list) {
                var item = productRepository.findTop1ByProductId(csv.getIdAsUUID());
                if (item == null) {
                    Product product = new Product();
                    product.setImportDate(new Date());
                    setAndSaveProduct(csv, product);
                } else {
                    setAndSaveProduct(csv, item);
                }
            }
            log.info("Product list was imported");
        } else {
            log.warn("Cannot import CSV");
        }
    }

    private void setAndSaveProduct(ProductCsv csv, Product item) {
        item.setName(csv.getName());
        item.setCategory(csv.getName());
        item.setDescription(csv.getName());
        item.setYearOfProduction(csv.getYearOfProduction());
        item.setPriceInCents(csv.getPriceInCents());
        item.setItemNumber(csv.getItemNumber());

        productRepository.save(item);
    }
}
