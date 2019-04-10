package sk.fri.uniza.resources;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import sk.fri.uniza.api.AccessToken;
import sk.fri.uniza.api.LoginApi;
import sk.fri.uniza.auth.User;
import sk.fri.uniza.auth.Users;

import javax.crypto.SecretKey;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Path("/login")
public class LoginResource {
    private static Users users = new Users();

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response getAccessToken(LoginApi loginApi) {

        if (loginApi != null) {
            Optional<User> optionalUser = users.get(loginApi.getUsername());

            if (!optionalUser.isPresent()) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            User user = optionalUser.get();
            if (user.getPassword().compareTo(loginApi.getPassword()) != 0) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

           String token = createJWT(user.getUserName(), "admin", user.getUserName(),
                    Integer.MAX_VALUE, Map.of("scope", user.getRoles()));
            // Create JWT token
            AccessToken accessToken = new AccessToken().withAccessToken(token)
                    .withRefreshToken("")
                    .withTokenType("Bearer")
                    .withExpiresIn(Integer.MAX_VALUE);
            CacheControl cacheControl = new CacheControl();
            cacheControl.setNoCache(true);

            return Response.ok()
                    .cacheControl(cacheControl)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .entity(accessToken)
                    .build();

        }
        return null;
    }

    public String createJWT(String id, String issuer, String subject, long ttlMillis, Map<String, Object> claims) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); //or HS384 or HS512

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);


        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(key);

        //if it has been specified, let's add the expiration
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        if (claims != null) {
            builder.addClaims(claims);
        }
        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

}
