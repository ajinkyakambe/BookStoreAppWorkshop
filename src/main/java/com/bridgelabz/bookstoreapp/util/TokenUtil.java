package com.bridgelabz.bookstoreapp.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;
import org.springframework.stereotype.Component;

@Component
public class TokenUtil {

    public final String TOKEN_SECRET = "asdfghjkl";

    /*
     This method create a token with the id parameter as a claim.
     It then returns the same token.
     */
    public String createToken(int id) {
        try {
            // We create a token using the HMAC256 algorithm and store the id as claim.
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

            String token = JWT.create().withClaim("user_id", id).sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            exception.printStackTrace();
            //log Token Signing Failed
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    This method decodes the passed token and returns the id claim.
    If the verification fails it will throw an exception.
     */

     public Long decodeToken(String token) {
        Long userid;
        //for verification algorithm
        Verification verification = null;

        try {
            // We specify the algorithm for the verifier here and then build the verifier in the next step
            verification = JWT.require(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        JWTVerifier jwtVerifier = verification.build();
        // We verify and decode the token using the verifier.
         // If the token is incorrect it will throw an exception
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        // We extract the claim from the decoded token and the convert
         // the claim to long type. We then return this id.
        Claim claim = decodedJWT.getClaim("user_id");
        userid = claim.asLong();
        return userid;
    }
}
