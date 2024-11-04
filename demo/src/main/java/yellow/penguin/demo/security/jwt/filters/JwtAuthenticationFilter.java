package yellow.penguin.demo.security.jwt.filters;

import yellow.penguin.demo.security.jwt.TokenManager;
import yellow.penguin.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private TokenManager tokenManager;
	@Autowired
	private UserService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String authorizationHeader = request.getHeader("Authorization");
		System.out.println("AUTH HEADER = " + authorizationHeader);
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String token = authorizationHeader.substring(7); // Extraer el token
			if (tokenManager.validateToken(token)) {
				String userId = tokenManager.getSubject(token);
				UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authToken);
			} else {
				// Si el token es inválido, puede establecer una respuesta de error
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
				return;
			}
		}
		chain.doFilter(request, response); // Continuar con la cadena de filtros
	}
}
