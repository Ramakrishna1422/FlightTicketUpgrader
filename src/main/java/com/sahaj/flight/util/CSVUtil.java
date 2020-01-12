package com.sahaj.flight.util;

import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.sahaj.flight.model.FlightTicket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

@Slf4j
@Service
public class CSVUtil<E> {

    public List<E> getRecords(InputStream file, Class cls) throws Exception {
        ColumnPositionMappingStrategy columnPositionMappingStrategy = new ColumnPositionMappingStrategy();
        columnPositionMappingStrategy.setType(cls);
        Reader reader = new InputStreamReader(file);
        CsvToBean<E> csvToBean = new CsvToBeanBuilder(reader)
                .withType(cls)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        return csvToBean.parse();
    }

    public String writeRecords(File file, List<?> records) throws Exception{
        if(file.exists()) {
            file.delete();
        }
        file.createNewFile();
        Writer writer  = new FileWriter(file);
        StatefulBeanToCsv statefulBeanToCsv = new StatefulBeanToCsvBuilder<>(writer)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withApplyQuotesToAll(false)
                .build();
        statefulBeanToCsv.write(records);
        writer.close();
        return file.getAbsolutePath();
    }

}
