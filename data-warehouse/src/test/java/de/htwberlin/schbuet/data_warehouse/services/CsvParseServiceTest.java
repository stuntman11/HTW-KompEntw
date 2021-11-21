package de.htwberlin.schbuet.data_warehouse.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class CsvParseServiceTest {

    InputStream is;
    CsvParseService parseService;

    @BeforeEach
    void setUp(){
        is = getClass().getClassLoader().getResourceAsStream("TestCsv.csv");
        parseService = new CsvParseService();
    }

    @Test
    void testParseCsvFromFile() {
        var list = parseService.parseProductInputStream(is);

        assertEquals(2, list.size());
        assertEquals("3284279A-0895-4669-A34A-894B2B415ED7", list.get(0).getId());
        assertEquals("808D0919-F872-4F6F-874D-4E2584158BF9", list.get(1).getId());

        assertEquals("Product 1", list.get(0).getName());
        assertEquals("Product 2", list.get(1).getName());

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
}