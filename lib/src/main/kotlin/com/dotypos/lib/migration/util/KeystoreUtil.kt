package com.dotypos.lib.migration.util

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.util.*

object KeystoreUtil {
    @Throws(
        KeyStoreException::class,
        IOException::class,
        CertificateException::class,
        NoSuchAlgorithmException::class
    )
    fun loadPkcs12KeystoreFromInputStream(inputStream: InputStream?, passphrase: String): KeyStore {
        val keyStore = KeyStore.getInstance("pkcs12")
        keyStore.load(inputStream, passphrase.toCharArray())
        return keyStore
    }

    fun KeyStore.repackage(originalPassphrase: String, newPassphrase: String): KeyStore {
        val newKeyStore = KeyStore.getInstance("pkcs12")
        newKeyStore.load(null, newPassphrase.toCharArray())
        val aliases = aliases()
        while (aliases.hasMoreElements()) {
            val alias = aliases.nextElement()
            getCertificate(alias)?.also { certificate ->
                newKeyStore.setCertificateEntry(alias, certificate)
            }
            getKey(alias, originalPassphrase.toCharArray())?.also { key ->
                newKeyStore.setKeyEntry(alias, key, newPassphrase.toCharArray(), getCertificateChain(alias))
            }
        }
        return newKeyStore
    }

    fun KeyStore.toBase64(password: String): String {
        return ByteArrayOutputStream().use { outputStream ->
            store(outputStream, password.toCharArray())
            Base64.getEncoder().encodeToString(outputStream.toByteArray())
        }
    }
}