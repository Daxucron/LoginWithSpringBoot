package yellow.penguin.demo.security.jwt.filters;


import yellow.penguin.demo.security.jwt.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Extraer el token
            if (tokenManager.validateToken(token)) {
                String username = tokenManager.getSubject(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                // Aquí puede crear un objeto de autenticación y establecerlo en el contexto de seguridad
                // Ejemplo: UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                // Si el token es inválido, puede establecer una respuesta de error
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
                return;
            }
        }
        
        chain.doFilter(request, response); // Continuar con la cadena de filtros
    }

    
}
