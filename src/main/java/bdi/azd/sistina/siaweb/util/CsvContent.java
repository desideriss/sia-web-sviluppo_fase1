package bdi.azd.sistina.siaweb.util;

import java.util.HashMap;
import java.util.List;

public class CsvContent {

	private HashMap<Integer, String> header;

    private List<HashMap<Integer, String>> rows;

	public HashMap<Integer, String> getHeader() {
		return header;
	}

	public void setHeader(HashMap<Integer, String> header) {
		this.header = header;
	}

	public List<HashMap<Integer, String>> getRows() {
		return rows;
	}

	public void setRows(List<HashMap<Integer, String>> rows) {
		this.rows = rows;
	}
}
