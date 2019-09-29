package skeleton.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

public class AccessDeniedHandler implements AccessDeniedHandler {

	Log logger = LogFactory.getLog(this.getClass());

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc)
			throws IOException, ServletException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			logger.warn(
					"User: " + auth.getName() + " attempted to access the protected URL: " + request.getRequestURI());
		}

		response.sendRedirect(request.getContextPath() + "/fundamentalException403.jsp");
	}

}
