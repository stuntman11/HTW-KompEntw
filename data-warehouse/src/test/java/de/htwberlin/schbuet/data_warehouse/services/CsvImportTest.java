package de.htwberlin.schbuet.data_warehouse.services;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;

class CsvImportTest {
    @Test
    void testParseCsvFromFileShouldReturnValidList() {
        var testCsv = readInternalCsvResource("TestCsv.csv");
        
        var products = testCsv.readProducts();

        assertEquals(2, products.size());
        assertEquals("3284279A-0895-4669-A34A-894B2B415ED7", products.get(0).getId());
        assertEquals("808D0919-F872-4F6F-874D-4E2584158BF9", products.get(1).getId());

        assertEquals("Product 1", products.get(0).getName());
        assertEquals("Product 2", products.get(1).getName());

        assertEquals("Category 1", products.get(0).getCategory());
        assertEquals("Category 2", products.get(1).getCategory());

        assertEquals("test, test", products.get(0).getDescription());
        assertEquals("", products.get(1).getDescription());

        assertEquals("A9876543", products.get(0).getItemNumber());
        assertEquals("A0123456", products.get(1).getItemNumber());

        assertEquals(500, products.get(0).getPriceInCents());
        assertEquals(1050, products.get(1).getPriceInCents());

        assertEquals(2021, products.get(0).getYearOfProduction());
        assertEquals(2019, products.get(1).getYearOfProduction());
    }

    @Test
    void testParseMalformedFileShouldThrowException() {
        var testCsv = readInternalCsvResource("CorruptTestCsv.csv");
        
        assertThrows(RuntimeException.class, () -> {
            testCsv.readProducts();
        });
    }
    
    private CsvImport readInternalCsvResource(String name) {
        InputStream testCsvIn = getClass().getClassLoader().getResourceAsStream(name);
    	return new CsvImport(new InputStreamReader(testCsvIn));
    }
}