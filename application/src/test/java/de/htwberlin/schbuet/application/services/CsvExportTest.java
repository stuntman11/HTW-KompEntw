package de.htwberlin.schbuet.application.services;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringWriter;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import de.htwberlin.schbuet.application.data.main.Product;
import de.htwberlin.schbuet.application.service.CsvExport;

@SpringBootTest
class CsvExportTest {
	StringWriter writer;
	CsvExport csv;
	Product product;
	
	@BeforeEach
	void setUp() {
		writer = new StringWriter();
		csv = new CsvExport(writer);
	}
	
	@Test
	void writeProductToCsv() {
		Product product = Product.builder()
			.id(UUID.fromString("dfc28384-33a2-495f-94c9-fad2c205a43a"))
			.name("Name")
			.description("Desc")
			.category("Category")
			.itemNumber("EP-1234")
			.priceInCents(1000)
			.yearOfProduction(2022)
			.createdDate(new Date(1644078818))
			.build();
		
		csv.writeProduct(product);
		
		assertEquals("\"dfc28384-33a2-495f-94c9-fad2c205a43a\",\"Name\",\"Desc\",\"Category\",\"EP-1234\",\"1000\",\"2022\"", writer.toString().trim());
	}
}
