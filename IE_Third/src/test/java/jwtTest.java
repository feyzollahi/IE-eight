import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;

public class jwtTest {
    @Test
    public  void createJWT() throws UnsupportedEncodingException {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setSubject("jwt")
                .setIssuer("joboonja")
                .signWith(signatureAlgorithm, "joboonja")
                .claim("userName", "userName")
                .setExpiration(new Date(now.getTime() + 1000000));
        String jwt = builder.compact();
        System.out.println(jwt);
        //if it has been specified, let's add the expiration
        decodeJwt(jwt);
        //Builds the JWT and serializes it to a compact, URL-safe string
        return ;
    }

    public static HashMap<String, String> decodeJwt(String jwt){
        System.out.println(Jwts.parser().setSigningKey("joboonja").parseClaimsJws(jwt).getBody().getIssuer());
//        System.out.println(Jwts.parser().parse(jwt).getBody().toString());
        return null;
    }
}
