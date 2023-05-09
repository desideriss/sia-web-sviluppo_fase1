package bdi.azd.sistina.siaweb.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import bdi.azd.sistina.siaweb.dto.RuoloDTO;
import bdi.azd.sistina.siaweb.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties({ "enabled", "credentialsNonExpired", "accountNonExpired", "accountNonLocked" })
public class UserDetailsImpl extends UserDTO implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	private String username;
	@JsonIgnore
	private String password;
	@JsonIgnore
	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public static UserDetailsImpl build(UserDTO user) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for(RuoloDTO ruolo : user.getRuoli()) {
			String codice = ruolo.getCodice();
			for(String struttura : ruolo.getStrutture()) {
				authorities.add(new SimpleGrantedAuthority(codice + "_" + struttura));
			}
		}
/*
		List<GrantedAuthority> authorities = user.getRuoli().stream()
				.map(role -> new SimpleGrantedAuthority(role.name()))
				.collect(Collectors.toList());
*/
		UserDetailsImpl userDetailsImpl = new UserDetailsImpl(user.getId(), new BCryptPasswordEncoder().encode("PreAuthenticated"), authorities);
		userDetailsImpl.setId(user.getId());
		userDetailsImpl.setNome(user.getNome());
		userDetailsImpl.setCognome(user.getCognome());
		userDetailsImpl.setRuoli(user.getRuoli());
		return userDetailsImpl;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(this.getId(), user.getId());
	}
	
	 @Override
	    public int hashCode() {
	        final int prime = 31;
	        int result = 1;
	        result = prime * result
	                + ((username == null) ? 0 : username.hashCode());
	        return result;
	    }
	
}