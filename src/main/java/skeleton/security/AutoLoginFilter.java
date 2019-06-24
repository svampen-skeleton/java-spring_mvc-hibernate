package skeleton.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class AutoLoginFilter extends AbstractPreAuthenticatedProcessingFilter {

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		return "IGNORE";
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		return "IGNORE";
	}

}
