package me.lee.bloger.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class Jwt {

    class Signer {
        companion object {
            fun sign(key: String, claims: Map<String, Any>, expireDays: Long): String =
                    Jwts.builder()
                            .setIssuedAt(Date())
                            .setClaims(claims)
                            .setExpiration(generateExpirationDate(expireDays))
                            .signWith(SignatureAlgorithm.HS512, key)
                            .compact()

            private fun generateExpirationDate(expireDays: Long): Date {
                return Date.from(LocalDateTime.now().plusDays(expireDays).toInstant(ZoneOffset.UTC))
            }
        }
    }

    class Verifier {
        companion object {
            fun verify(jwtToken: String, key: String): Optional<Claims> =
                    try {
                        val claims = Jwts.parser()
                                .setSigningKey(key)
                                .parseClaimsJws(jwtToken)
                                .body
                        Optional.of(claims)
                    } catch (e: Exception) {
                        Optional.empty()
                    }
        }
    }

}