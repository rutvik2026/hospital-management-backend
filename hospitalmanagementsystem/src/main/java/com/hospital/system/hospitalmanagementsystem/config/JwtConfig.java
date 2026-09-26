//package com.hospital.system.hospitalmanagementsystem.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.JwtEncoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
//
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import java.util.Base64;
//
//@Configuration
//public class JwtConfig {
//
//    @Value("${jwt.secret}")
//    private String jwtSecret;
//
//    @Bean
//    public SecretKey jwtSecretKey() {
//
//        byte[] decodedKey =
//                Base64.getDecoder().decode(jwtSecret);
//
//        return new SecretKeySpec(
//                decodedKey,
//                "HmacSHA256"
//        );
//    }
//
//    @Bean
//    public JwtEncoder jwtEncoder(SecretKey secretKey) {
//
//        return NimbusJwtEncoder
//                .withSecretKey(secretKey)
//                .algorithm(MacAlgorithm.HS256)
//                .build();
//    }
//
//    @Bean
//    public JwtDecoder jwtDecoder(SecretKey secretKey) {
//
//        return NimbusJwtDecoder
//                .withSecretKey(secretKey)
//                .macAlgorithm(MacAlgorithm.HS256)
//                .build();
//    }
//}