package no.ntnu.idi.stud.savingsapp.controller.redirect;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import no.ntnu.idi.stud.savingsapp.SparestiApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Controller for managing HTTP redirects, especially useful for handling callback
 * responses from authentication services.
 */
@RestController
@RequestMapping
@EnableAutoConfiguration
@Tag(name = "Redirect")
public class RedirectController {

	/**
	 * Handles the callback from an OAuth service or similar by redirecting to the
	 * appropriate local URL with the required parameters.
	 * @param state The state parameter received from the OAuth service, used to maintain
	 * state between the request and callback.
	 * @param request The HTTP request containing all the necessary parameters from the
	 * callback.
	 * @param response The HTTP response to manage redirection.
	 * @throws RuntimeException if an IOException occurs during URL redirection.
	 */
	@GetMapping("/redirect")
	@ResponseBody
	private void consumeCallback(@RequestParam(value = "state") String state, HttpServletRequest request,
			HttpServletResponse response) {
		if (request.getParameterMap().containsKey("code")) {
			String code = request.getParameterMap().get("code")[0];
			response.setHeader("Location",
					SparestiApplication.getFrontendURL() + "/redirect?code=" + code + "&state=" + state);
			response.setStatus(302);
			return;
		}
		// Default redirection if "code" parameter is missing
		try {
			response.sendRedirect(SparestiApplication.getFrontendURL() + "/login");
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
