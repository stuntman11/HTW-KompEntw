package de.htwberlin.schbuet.data_warehouse.services;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import de.htwberlin.schbuet.data_warehouse.data.csv.ProductCsv;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class CsvParseService {

    @SneakyThrows
    public List<ProductCsv> parseInputStream(InputStream inputStream) {
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
    }
}
