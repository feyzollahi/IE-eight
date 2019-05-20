package webTools;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.UnsupportedEncodingException;
import java.util.Date;


public class JwtFactory {
    public String createJWT(String userName, long ttlMillis) throws UnsupportedEncodingException {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setIssuer("joboonja")
                .signWith(signatureAlgorithm, "joboonja")
                .claim("userName", userName)
                .setExpiration(new Date(now.getTime() + ttlMillis));
        String jwt = builder.compact();
        System.out.println(jwt);
        //if it has been specified, let's add the expiration

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }
    public Claims decodeJwt(String jwt){
       Claims claims = Jwts.parser().setSigningKey("joboonja").parseClaimsJws(jwt).getBody();
       return claims;
    }
    public boolean validateJwt(String jwt){
        Claims claims = decodeJwt(jwt);
        if(claims.getExpiration().after(new Date()) && claims.get("userName") != null){
            return true;
        }
        System.out.println("expirationDate = " + claims.getExpiration().getTime());
        System.out.println("now = " + new Date().getTime());
        System.out.println("userName = " + claims.get("userName"));
        return false;
    }
}
