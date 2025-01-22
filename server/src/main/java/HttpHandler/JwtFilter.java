package HttpHandler;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class JwtFilter extends Filter {
    private final String secret = "my-secret-key";
    private final Algorithm algorithm = Algorithm.HMAC256(secret);
    private final JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build();

    @Override
    public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
        String authHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                verifier.verify(token);
                chain.doFilter(exchange);
                return;
            } catch (JWTVerificationException exception) {
                String response = "Token is invalid or expired";
                exchange.sendResponseHeaders(401, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                }
                return;
            }
        }
        String response = "Authorization header is missing or invalid";
        exchange.sendResponseHeaders(401, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }

    @Override
    public String description() {
        return "JWT Filter";
    }
}