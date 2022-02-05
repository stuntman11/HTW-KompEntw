package de.htwberlin.schbuet.application.service;

import java.io.Writer;

import com.opencsv.CSVWriter;

import de.htwberlin.schbuet.application.data.main.Product;

public class CsvExport {
	private CSVWriter csv;
	
	public CsvExport(Writer writer) {
		this.csv = new CSVWriter(writer);
	}
	
	public void writeProduct(Product product) {
    	String[] csvRow = new String[] {
            product.getId().toString(),
            product.getName(),
            product.getDescription(),
            product.getCategory(),
            product.getItemNumber(),
            String.valueOf(product.getPriceInCents()),
            String.valueOf(product.getYearOfProduction())
        };
    	csv.writeNext(csvRow);
	}
}
