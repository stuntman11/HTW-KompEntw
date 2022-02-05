package de.htwberlin.schbuet.data_warehouse.services;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import de.htwberlin.schbuet.data_warehouse.data.csv.ProductCsv;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvService {

    public List<ProductCsv> parseProductInputStream(InputStream inputStream) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            CSVReader csvReader = new CSVReader(reader);

            CsvToBean<ProductCsv> csvToBean = new CsvToBeanBuilder<ProductCsv>(csvReader)
                    .withType(ProductCsv.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            var list = csvToBean.parse();
            reader.close();
            csvReader.close();

            return list;
        } catch (NullPointerException | IOException e) {
            return new ArrayList<>();
        }
    }
}
