package com.itba.eda.AirPollution;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.itba.eda.IndexService.IndexRecord;
import com.itba.eda.IndexService.IndexService;
import com.itba.eda.IndexService.IndexWithDuplicates;

public class AirPollution {
    public static void main(String[] args) throws IOException {
        // Read file
        String fileName = "co_1980_alabama.csv";
        InputStream is = AirPollution.class.getClassLoader().getResourceAsStream(fileName);
        Reader in = new InputStreamReader(is);

        // Read records from CSV
        var csv = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .get();

        var records = csv.parse(in);

        // Create data store and index
        var store = new HashMap<Long, CSVRecord>();
        IndexService<IndexRecord<Double, Long>> index = new IndexWithDuplicates<>();

        // Fill in data from CSV
        for (var rec : records) {
            var number = rec.getRecordNumber();

            store.put(number, rec);

            var value = Double.valueOf(rec.get("daily_max_8_hour_co_concentration"));
            index.insert(new IndexRecord<Double, Long>(value, number));
        }

        // Close the file handle
        in.close();

        // Get pollution average
        double avg = 0.0;
        for (var item : index)
            avg += item.key();
        avg /= index.count();

        System.out.printf("Average pollution: %.5g\n", avg);

        // Print all sorted by pollution
        System.out.println("All entries, sorted by pollution:");
        for (var item : index)
            System.out.println(store.get(item.value()));

        // Search for 2.8
        System.out.printf("Entry with pollution = 2.8: %s\n",
                index.search(new IndexRecord<Double, Long>(2.8)) ? "yes" : "no");

        // Min pollution
        System.out.printf("Minimum pollution: %g\n", index.min().key());
        System.out.println(store.get(index.min().value()));

        // Ranges
        var range = index.range(new IndexRecord<Double, Long>(3.65), new IndexRecord<Double, Long>(3.84), true, true);
        for (int i = 0; i < range.length; i++)
            System.out.println(range[i].key());

        range = index.range(new IndexRecord<Double, Long>(3.65), new IndexRecord<Double, Long>(3.84), true, false);
        for (int i = 0; i < range.length; i++)
            System.out.println(range[i].value());

        range = index.range(new IndexRecord<Double, Long>(10.5), new IndexRecord<Double, Long>(12.0), true, true);
        for (int i = 0; i < range.length; i++)
            System.out.println(range[i].value());
    }
}
