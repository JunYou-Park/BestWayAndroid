package com.airetefacruo.myapplication.util.crypto

import android.util.Base64
import com.yawtseb.bestway.util.ConstData.CRYPTO_BYTES
import com.yawtseb.bestway.util.ConstData.CRYPTO_KEY
import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class Decrypt {
    companion object{
        fun decrypt(value: String, padding: Int): String{
            return try {
                val transformation = "AES/CBC/PKCS${padding}Padding"
                val textBytes = Base64.decode(value, 0)
                val spec = IvParameterSpec(CRYPTO_BYTES)
                val newKey = SecretKeySpec(CRYPTO_KEY.toByteArray(StandardCharsets.UTF_8), "AES")
                val cipher = Cipher.getInstance(transformation)
                cipher.init(Cipher.DECRYPT_MODE, newKey, spec)
                String(cipher.doFinal(textBytes), StandardCharsets.UTF_8)
            }
            catch (e : Exception){
                e.message.toString()
            }
        }
    }
}