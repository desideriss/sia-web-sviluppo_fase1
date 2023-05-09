package bdi.azd.sistina.siaweb.service;

import org.springframework.stereotype.Service;


@Service
public interface FornitoreContrattoService {
    Integer getCountFornitoreContrattoByIdFornitore(long idFornitore);

}
