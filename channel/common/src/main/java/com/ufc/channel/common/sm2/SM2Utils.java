package com.ufc.channel.common.sm2;

import com.alibaba.fastjson.JSONObject;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;

import java.io.IOException;
import java.math.BigInteger;

public class SM2Utils {
    public SM2Utils() {
    }

    private static String encrypt(byte[] publicKey, byte[] data) throws IOException {
        if (publicKey != null && publicKey.length != 0) {
            if (data != null && data.length != 0) {
                byte[] source = new byte[data.length];
                System.arraycopy(data, 0, source, 0, data.length);
                Cipher cipher = new Cipher();
                SM2 sm2 = SM2.Instance();
                ECPoint userKey = sm2.ecc_curve.decodePoint(publicKey);
                ECPoint c1 = cipher.Init_enc(sm2, userKey);
                cipher.Encrypt(source);
                byte[] c3 = new byte[32];
                cipher.Dofinal(c3);
                return Util.byteToHex(c1.getEncoded()) + Util.byteToHex(source) + Util.byteToHex(c3);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private static byte[] decrypt(byte[] privateKey, byte[] encryptedData) throws IOException {
        if (privateKey != null && privateKey.length != 0) {
            if (encryptedData != null && encryptedData.length != 0) {
                String data = Util.byteToHex(encryptedData);
                byte[] c1Bytes = Util.hexToByte(data.substring(0, 130));
                int c2Len = encryptedData.length - 97;
                byte[] c2 = Util.hexToByte(data.substring(130, 130 + 2 * c2Len));
                byte[] c3 = Util.hexToByte(data.substring(130 + 2 * c2Len, 194 + 2 * c2Len));
                SM2 sm2 = SM2.Instance();
                BigInteger userD = new BigInteger(1, privateKey);
                ECPoint c1 = sm2.ecc_curve.decodePoint(c1Bytes);
                Cipher cipher = new Cipher();
                cipher.Init_dec(userD, c1);
                cipher.Decrypt(c2);
                cipher.Dofinal(c3);
                return c2;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static void generateKeyPair() {
        SM2 sm2 = SM2.Instance();
        AsymmetricCipherKeyPair key = sm2.ecc_key_pair_generator.generateKeyPair();
        ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters) key.getPrivate();
        ECPublicKeyParameters ecpub = (ECPublicKeyParameters) key.getPublic();
        BigInteger privateKey = ecpriv.getD();
        ECPoint publicKey = ecpub.getQ();
        System.out.println("公钥: " + Util.byteToHex(publicKey.getEncoded()));
        System.out.println("私钥: " + Util.byteToHex(privateKey.toByteArray()));
    }


    /**
     * 加密
     * @param publickey 公钥
     * @param data data
     * @return 密文
     * @throws IOException
     */
    public static String encrypt(String publickey, String data) throws IOException {
        return encrypt(Util.hexToByte(publickey),data.getBytes());
    }

    /**
     * 解密
     * @param privatekey 私钥
     * @param data data
     * @return 明文
     * @throws IOException
     */
    public static String decrypt(String privatekey, String data) throws IOException {
        return new String(decrypt(Util.hexToByte(privatekey),Util.hexToByte(data)));
    }

    public static void main(String[] args) throws Exception {
        generateKeyPair();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appKey","test");

        JSONObject cont = new JSONObject();
        cont.put("testId","测试解密");
        jsonObject.put("content",cont);
//        String plainText = "sm2test";
        String plainText = jsonObject.toString();
        byte[] sourceData = plainText.getBytes();
        String prik = "3690655E33D5EA3D9A4AE1A1ADD766FDEA045CDEAA43A9206FB8C430CEFE0D94";
        String pubk = "04F6E0C3345AE42B51E06BF50B98834988D54EBC7460FE135A48171BC0629EAE205EEDE253A530608178A98F1E19BB737302813BA39ED3FA3C51639D7A20C7391A";
        System.out.println("加密: ");
        String cipherText = encrypt(Util.hexToByte(pubk), sourceData);
        System.out.println(cipherText);
        System.out.println("解密: ");
        plainText = new String(decrypt(Util.hexToByte(prik), Util.hexToByte(cipherText)));
        System.out.println(plainText);
    }
}