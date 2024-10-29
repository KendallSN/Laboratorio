package cr.ac.una.unaplanillaws.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JwTokenHelper {

    private static JwTokenHelper jwTokenHelper = null;
    private static final long EXPIRATION_LIMIT = 1;
    private static final long EXPIRATION_RENEWAL_LIMIT = 2;
    private static final String AUTHENTICATION_SCHEME = "Bearer ";
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public JwTokenHelper() {
    }

    public static JwTokenHelper getInstance() {
        if (jwTokenHelper == null) {
            jwTokenHelper = new JwTokenHelper();
        }
        return jwTokenHelper;
    }

    private Date getExpirationDate(boolean renewal) {
        long currentTimeInMillins = System.currentTimeMillis();
        long expMillSecond = TimeUnit.MINUTES.toMillis(EXPIRATION_LIMIT);
        if (renewal) {
            expMillSecond = TimeUnit.MINUTES.toMillis(EXPIRATION_RENEWAL_LIMIT);
        }
        return new Date(currentTimeInMillins + expMillSecond);
    }

    public String generatePrivateKey(String userName) {
        return AUTHENTICATION_SCHEME + Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(getExpirationDate(false))/*Genera uno de un minuto*/
                .claim("rnt", AUTHENTICATION_SCHEME + Jwts
                        .builder()
                        .setSubject(userName)
                        .setIssuedAt(new Date())
                        .setExpiration(getExpirationDate(true)) /*Genera uno de dos minuto*/
                        .claim("rnw", true)
                        .signWith(key)
                        .compact()
                )
                .signWith(key)
                .compact();
    }

    public Claims claimKey(String privateKey) throws ExpiredJwtException, MalformedJwtException {
        return Jwts.parser().setSigningKey(key)
                .parseClaimsJws(privateKey)
                .getBody();
    }
}
