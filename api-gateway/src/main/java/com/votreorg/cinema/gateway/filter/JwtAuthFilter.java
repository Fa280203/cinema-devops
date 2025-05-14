package com.votreorg.cinema.gateway.filter;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import com.votreorg.cinema.gateway.util.JwtUtil;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import java.util.Set;



@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtAuthFilter implements ContainerRequestFilter {
    private static final Set<String> OPEN = Set.of("register","login");

    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {
        String path = ctx.getUriInfo().getPath();                // ex: "auth/register"
        String[] parts = path.split("/");
        if (parts.length > 1 && "auth".equals(parts[0]) && OPEN.contains(parts[1])) {
            return; // on laisse passer register & login
        }

        String auth = ctx.getHeaderString("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            ctx.abortWith(Response.status(401).build());
            return;
        }

        String token = auth.substring("Bearer ".length());
        try {
            JwtUtil.validateToken(token);
        } catch (Exception e) {
            ctx.abortWith(Response.status(401).build());
        }
    }
}

