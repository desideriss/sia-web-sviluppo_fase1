package bdi.azd.sistina.siaweb.enums;

import java.util.HashMap;

public enum CsvHeaders {
	CONTRATTI_IN_SCADENZA("CIG","CIG PADRE","TIPO CIG","TIPO CONTRATTO","DENOMINAZIONE","IMPORTO MASSIMO"),
	CONTRATTI_RICERCA_COMPLETE("CIG","STRUTTURA DESTINATARIA","DENOMINAZIONE","FORNITORE","DATA PRIMA SCADENZA","DATA ULTIMA SCADENZA","CODICE PROCEDURA ORIGINE","CODICE PROCEDURA RINNOVO"),
	CONTRATTI_RICERCA_PARZIALE("CIG","STRUTTURA DESTINATARIA","DENOMINAZIONE","FORNITORE","DATA PRIMA SCADENZA","DATA ULTIMA SCADENZA"),
	CONTRATTI_BY_STATO_PROCESSO("CIG","TIPO CIG","CIG PADRE","DESCRIZIONE","TIPO CONTRATTO","IMPORTO MASSIMO");

	
private HashMap<Integer, String> value;
    
    private CsvHeaders(String ...  headers) {
        HashMap<Integer, String> h = new HashMap<>();
        for(int i = 0; i< headers.length; i++) {
            h.put(i, headers[i]);
        }
        this.value = h;
    }
    
    public HashMap<Integer, String> getValue(){
        return this.value;
    }
}
