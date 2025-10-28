package com.CptFranck.ApiGateway.config.security;

import io.micrometer.common.lang.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    static private String RESSOURCE_ID;

    static private String PRINCIPAL_ATTRIBUTE;

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter;

    public JwtAuthConverter(@Value("${keycloak.auth.converter.ressource-id}") String ressourceId,
                            @Value("${keycloak.auth.converter.principal-attribute}") String principalAttribute) {
        RESSOURCE_ID = ressourceId;
        PRINCIPAL_ATTRIBUTE = principalAttribute;
        this.jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    }

    @Override
    public Mono<AbstractAuthenticationToken> convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                this.jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractRessourceRoles(jwt).stream()
            ).collect(Collectors.toSet());

        return Mono.just(new JwtAuthenticationToken(
                jwt,
                authorities,
                getPrinciplesClaimName(jwt)
        ));
    }

    private Collection<GrantedAuthority> extractRessourceRoles(Jwt jwt) {
        if(jwt.getClaim("resource_access") == null) return Set.of();

        Map<String, Object> ressourceAccess = jwt.getClaim("resource_access");
        if(ressourceAccess.get(RESSOURCE_ID) == null) return Set.of();

        Map<String, Object> ressource = (Map<String, Object>) ressourceAccess.get(RESSOURCE_ID);

        Collection<String> roles = (Collection<String>) ressource.get("roles");
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }

    private String getPrinciplesClaimName(Jwt jwt) {
        String claimName = JwtClaimNames.SUB;
        if(PRINCIPAL_ATTRIBUTE != null)
            claimName = PRINCIPAL_ATTRIBUTE;
        return jwt.getClaimAsString(claimName);
    }
}
