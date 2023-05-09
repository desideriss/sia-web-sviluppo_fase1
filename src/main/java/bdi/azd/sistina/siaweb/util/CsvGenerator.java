package bdi.azd.sistina.siaweb.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;


@Service
public class CsvGenerator {
	
public String generate(CsvContent content) {
		
		String csvHeader=content.getHeader()
		.entrySet()
		.stream()
		.sorted((e1,e2)->e1.getKey().compareTo(e2.getKey()))
		.map(e->e.getValue())
		.collect(Collectors.joining(";"));
				
		 
		 List<String> csvRows= content
				 .getRows()
				 .stream()
				 .flatMap(r->Arrays.asList(
						 r.entrySet()
						 .stream()
						 .sorted((e1,e2)->e1.getKey().compareTo(e2.getKey()))
						 .map(e-> e.getValue())
						 .collect(Collectors.joining(";")))
						 .stream())
				 .collect(Collectors.toList());

	        StringBuilder builder = new StringBuilder();
	        builder.append(csvHeader);
	        builder.append(System.getProperty("line.separator"));
	        csvRows.forEach(s -> {
	            builder.append(s);
	            builder.append(System.getProperty("line.separator"));
	        }
	        );

	        return builder.toString();
	}
	
	
}
