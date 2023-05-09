package bdi.azd.sistina.siaweb.constants;

public class ErrorMessage {
	
	public static final String PROCEDURA_NOT_FOUND="Attenzione! Procedura non presente";
    public static final String FORNITORE_NOT_FOUND = "Nessun fornitore presente";

    public static final String ERRORE_GENERICO = "Rilevato errore durante l'elaborazione dei dati";
    public static final String FORNITORE_NOT_DELETED
            = "Il fornitore non può essere cancellato perché associato ad un contratto in essero";

    public static final String FORNITORE_EXCEPTION_PI_CF
            = "Uno tra i campi Partita IVA e Codice Fiscale deve essere valorizzato";

    public static final String FORNITORE_PRESENT
            = "Verificare ragione sociale o codice fiscale o partita iva del Fornitore perché risultano già presenti";
    public static final String FORNITORE_EXCEPTION_PMI = "Il campo PMI deve essere valorizzato";
    
    public static final String CRONOLOGIA_NOT_FOUND = "Nessuna cronologia presente";
}
