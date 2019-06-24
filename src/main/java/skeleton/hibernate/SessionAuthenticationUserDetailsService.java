package skeleton.hibernate;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import skeleton.dao.UserDao;


@Service
public class SessionAuthenticationUserDetailsService implements AuthenticationUserDetailsService<Authentication> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public UserDetails loadUserDetails(Authentication user) throws UsernameNotFoundException {

		if (user.getPrincipal() != null) {
			Users tmp = userDao.getUser((String)user.getPrincipal());
			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			return new User(tmp, "none", authorities);

		}

		return null;
	}
}
