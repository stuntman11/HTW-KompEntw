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

    /**
     *  Importiert alle Produktdaten einmal in der Stunde als Backup.
     *  Die Daten werden nicht weiter verwendet und dienen nur der Erfüllung der Aufgabenstellung
     */
    @SneakyThrows
    @Scheduled(cron = "0 * * * *")
    private void importProductsFromMainApplicationService() {

        URL url = new URL("http://localhost:8080/export-product/export.csv");
        var list = parseService.parseProductInputStream(url.openStream());

        for (ProductCsv csv : list) {
            var item = productRepository.findById(csv.getIdAsUUID());
            if (item == null) {
                Product product = new Product();
                product.setImportDate(new Date());
                setAndSaveProduct(csv, product);
            } else {
                setAndSaveProduct(csv, item);
            }
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
