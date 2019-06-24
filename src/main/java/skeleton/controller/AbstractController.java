package skeleton.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@PreAuthorize("isAuthenticated()")
public abstract class AbstractController {

	protected Log logger = LogFactory.getLog(this.getClass());

	public AbstractController() {
		super();
	}

	@ModelAttribute("title")
	public String getTitle() {
		return "Skeleton";
	}

	/**
	 * Get the user principal from Spring security context
	 * 
	 * @return
	 */
	Object getPrimulaUser() {
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	}

}