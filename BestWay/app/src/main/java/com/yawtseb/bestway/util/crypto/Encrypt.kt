package com.airetefacruo.myapplication.util.crypto

import android.util.Base64
import com.yawtseb.bestway.util.ConstData.CRYPTO_BYTES
import com.yawtseb.bestway.util.ConstData.CRYPTO_KEY
import java.lang.Exception
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class Encrypt {
    companion object{
        fun encrypt(value: String, padding: Int): String{
            return try{
                val transformation = "AES/CBC/PKCS${padding}Padding"
                val textByte = value.toByteArray(StandardCharsets.UTF_8)
                val spec = IvParameterSpec(CRYPTO_BYTES)
                val newKey = SecretKeySpec(CRYPTO_KEY.toByteArray(StandardCharsets.UTF_8), "AES")
                val cipher = Cipher.getInstance(transformation)
                cipher.init(Cipher.ENCRYPT_MODE, newKey, spec)
                URLEncoder.encode(Base64.encodeToString(cipher.doFinal(textByte), 0), "UTF-8")
            }
            catch (e: Exception){
                e.message.toString()
            }
        }
    }
}