package skeleton.hibernate;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class User extends PreAuthenticatedAuthenticationToken implements UserDetails {

	private static final long serialVersionUID = -4840411972706729327L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private Users user;

	public User(Users user, Object aCredentials,
			Collection<? extends GrantedAuthority> anAuthorities) {
		super(user, aCredentials, anAuthorities);
		this.user = user;
	}

	@Override
	public String getPassword() {
		// Not used
		return null;
	}

	@Override
	public String getUsername() {
		return user == null ? null : user.getUserId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO No such thing in Primula.
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

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return super.getAuthorities();
	}

	public void refreshUser() {
		Authentication authentication = new PreAuthenticatedAuthenticationToken(this.user.getUserId(),
				"none");
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
