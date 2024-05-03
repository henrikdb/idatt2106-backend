package no.ntnu.idi.stud.savingsapp;

import java.util.Collections;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.security.AuthIdentity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserUtil {

	public static Authentication getAuthentication(User user) {
		return getAuthentication(user.getId(), user.getRole().name());
	}

	public static Authentication getAuthentication(long userId, String role) {
		return new UsernamePasswordAuthenticationToken(new AuthIdentity(userId, role), null,
				Collections.singletonList(new SimpleGrantedAuthority(role)));
	}

}
