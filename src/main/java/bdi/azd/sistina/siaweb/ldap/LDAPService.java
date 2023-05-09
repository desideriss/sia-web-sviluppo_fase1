package bdi.azd.sistina.siaweb.ldap;

import java.util.List;

import bdi.azd.sistina.siaweb.dto.AttoreDTO;
import bdi.azd.sistina.siaweb.dto.UserDTO;

public interface LDAPService {

	public UserDTO getUser(String userName);
	
	public List<AttoreDTO> getAttori(String nominativoAttore);
	
	public List<AttoreDTO> getAttoriProva(String nominativoAttore);
}
