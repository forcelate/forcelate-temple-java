package com.jedivision;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Encryption {
    private static final String AES = "AES";
    private static final String CIPHER_AES_INSTANCE = "AES/CBC/PKCS5PADDING";
    private static final String UTF8 = "UTF-8";

    public static String encryptAES(String key, String initVector, String value) throws UnsupportedEncodingException,
                                                                                        NoSuchPaddingException,
                                                                                        NoSuchAlgorithmException,
                                                                                        InvalidAlgorithmParameterException,
                                                                                        InvalidKeyException,
                                                                                        BadPaddingException,
                                                                                        IllegalBlockSizeException {
        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(UTF8));
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(UTF8), AES);
        Cipher cipher = Cipher.getInstance(CIPHER_AES_INSTANCE);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(value.getBytes());
        return Base64.encodeBase64String(encrypted);
    }

    public static String decryptAES(String key, String initVector, String encrypted) throws UnsupportedEncodingException,
                                                                                            NoSuchPaddingException,
                                                                                            NoSuchAlgorithmException,
                                                                                            InvalidAlgorithmParameterException,
                                                                                            InvalidKeyException,
                                                                                            BadPaddingException,
                                                                                            IllegalBlockSizeException {
        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(UTF8));
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(UTF8), AES);
        Cipher cipher = Cipher.getInstance(CIPHER_AES_INSTANCE);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
        return new String(original);
    }
}
