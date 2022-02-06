package de.htwberlin.schbuet.data_warehouse.services;

import java.io.Reader;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import de.htwberlin.schbuet.data_warehouse.data.csv.ProductCsv;

public class CsvImport {
	private CsvToBean<ProductCsv> csv;
	
	public CsvImport(Reader reader) {
		this.csv = new CsvToBeanBuilder<ProductCsv>(new CSVReader(reader))
                .withType(ProductCsv.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
	}
	
	public List<ProductCsv> readProducts() {
		return csv.parse();
	}
}
