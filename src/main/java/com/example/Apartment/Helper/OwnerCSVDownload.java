package com.example.Apartment.Helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import com.example.Apartment.Entity.OwnerDetails;

/**
 * @author arun vemireddy
 */
public class OwnerCSVDownload {
	public static ByteArrayInputStream tutorialsToCSV(List<OwnerDetails> ownerDetails) {
		final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
			List<String> data1 = Arrays.asList("ID", "Name", "Contact", "FlatNo");
			csvPrinter.printRecord(data1);
			for (OwnerDetails details : ownerDetails) {
				List<String> data = Arrays.asList(String.valueOf(details.getId()), details.getName(),
						String.valueOf(details.getContact()), String.valueOf(details.getFlatno()));
				csvPrinter.printRecord(data);
			}

			csvPrinter.flush();
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
		}
	}
}
