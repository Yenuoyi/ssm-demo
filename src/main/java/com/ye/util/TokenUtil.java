package com.ye.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class TokenUtil {
	public String CreateTokenTime(String user){
		String token = null;
		try {
			//选择算法
		    Algorithm algorithm = Algorithm.HMAC256("secret");
		    token = JWT.create()
		        .withIssuer("auth0")
		        .withExpiresAt(new Date())
		        .withClaim("role", user)
		        .sign(algorithm);
		} catch (UnsupportedEncodingException exception1){
		    //UTF-8 encoding not supported
		} catch (JWTCreationException exception2){
		    //Invalid Signing configuration / Couldn't convert Claims.
		}
		return token;
		
	}
	
	public String CreateTokenNoTime(String user){
		String token = null;
		try {
			//选择算法
		    Algorithm algorithm = Algorithm.HMAC256("secret");
		    token = JWT.create()
		        .withIssuer("auth0")
		        .withClaim("role", user)
		        .sign(algorithm);
		} catch (UnsupportedEncodingException exception1){
		    //UTF-8 encoding not supported
		} catch (JWTCreationException exception2){
		    //Invalid Signing configuration / Couldn't convert Claims.
		}
		return token;
		
	}
	//解密token,有时间限制
	public String Vertify(String token,int secs){
	    String vertify = null;
		try {
		    Algorithm algorithm = Algorithm.HMAC256("secret");
		    JWTVerifier verifier = JWT.require(algorithm)
		        .withIssuer("auth0")
		        .acceptExpiresAt(secs)
		        .build(); //Reusable verifier instance
		    DecodedJWT jwt = verifier.verify(token);
		    vertify = jwt.getClaim("role").asString();
		} catch (UnsupportedEncodingException exception){
			//UTF-8 encoding not supported
			System.out.println("解密的token错误1");
		} catch (JWTVerificationException exception){
			//Invalid signature/claims
			System.out.println("解密的token错误2");
		}
		return vertify;
	}
	//解密token，无时间限制
		public String Vertify(String token){
			String vertify = null;
			try {
			    Algorithm algorithm = Algorithm.HMAC256("secret");
			    JWTVerifier verifier = JWT.require(algorithm)
			        .withIssuer("auth0")
			        .build(); //Reusable verifier instance
			    DecodedJWT jwt = verifier.verify(token);
			    vertify = jwt.getClaim("role").asString();
			} catch (UnsupportedEncodingException exception){
				//UTF-8 encoding not supported
				System.out.println("解密的token错误1");
			} catch (JWTVerificationException exception){
				//Invalid signature/claims
				System.out.println("解密的token错误2");
			}
			return vertify;
		}
}
