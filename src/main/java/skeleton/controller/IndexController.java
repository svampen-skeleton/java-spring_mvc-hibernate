package skeleton.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
//@PreAuthorize ("isAuthenticated()")
public class IndexController extends AbstractController {


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String blank(Model m, Locale locale, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		try {
			
			return "index";
		} catch (Exception e) {
			logger.error("Unexpected error: ", e);
			logger.error("Cause: ", e.getCause());
			return "redirect:/index.jsp";
		}
	}

}
