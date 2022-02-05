package de.htwberlin.schbuet.data_warehouse.services;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest
class ImportServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ImportService importService;

    private InputStream source;
    private File dest;

    @BeforeEach
    void setUp(){
        var basePath = System.getProperty("java.io.tmpdir");
        dest = new File(basePath, "export-products.csv");
        source = getClass().getClassLoader().getResourceAsStream("TestCsv.csv");
    }

    @AfterEach
    public void cleanUpEach(){
        try {
            Files.deleteIfExists(dest.toPath());
        }
        catch (IOException ignored) {
        }
    }

    @Test
    void testImportFileServiceShouldImportTwoProducts() throws IOException {
        Files.copy(source, dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        importService.importProductsFromCsv();

        var list = productService.getAllProducts();

        assertEquals("Product 1", list.get(0).getName());
        assertEquals("Product 2", list.get(1).getName());

        assertEquals("Category 1", list.get(0).getCategory());
        assertEquals("Category 2", list.get(1).getCategory());

        assertEquals("test, test", list.get(0).getDescription());
        assertEquals("", list.get(1).getDescription());

        assertEquals("A9876543", list.get(0).getItemNumber());
        assertEquals("A0123456", list.get(1).getItemNumber());

        assertEquals(500, list.get(0).getPriceInCents());
        assertEquals(1050, list.get(1).getPriceInCents());

        assertEquals(2021, list.get(0).getYearOfProduction());
        assertEquals(2019, list.get(1).getYearOfProduction());
    }

    @Test
    void testImportFileServicesShouldImportNothingWhenFileNotFound() {
        var basePath = System.getProperty("java.io.tmpdir");
        dest = new File(basePath, "export-products-test.csv");
        importService.importProductsFromCsv();

        var list = productService.getAllProducts();
        assertEquals(0, list.size());
    }
}