package me.lee.bloger.utils

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object Hashing {

    private const val MESSAGE_DIGEST_SHA256 = "SHA-256"

    fun bytesToHex(hash: ByteArray): String {
        val hexString = StringBuilder(2 * hash.size)
        for (b in hash) {
            val hex = Integer.toHexString(0xff and b.toInt())
            if (hex.length == 1) {
                hexString.append('0')
            }
            hexString.append(hex)
        }
        return hexString.toString()
    }

    fun sha256(): Sha256 {
        return Sha256()
    }

    class Sha256 {
        private var sha256Digest: MessageDigest? = null

        init {
            sha256Digest = try {
                MessageDigest.getInstance(MESSAGE_DIGEST_SHA256)
            } catch (e: NoSuchAlgorithmException) {
                throw RuntimeException(e)
            }
        }

        @JvmOverloads
        fun hashString(message: String, charset: Charset? = StandardCharsets.UTF_8): String {
            val hash = sha256Digest!!.digest(message.toByteArray(charset!!))
            return bytesToHex(hash)
        }
    }
}