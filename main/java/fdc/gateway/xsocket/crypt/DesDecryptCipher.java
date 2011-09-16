package fdc.gateway.xsocket.crypt;

import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-29
 * Time: 上午10:03
 * To change this template use File | Settings | File Templates.
 */
public class DesDecryptCipher {
    private static DesDecryptCipher desDecryptCipher = null;
    private static Cipher decryptCipher = null;
    private static String Algorithm = "DES";

    public static DesDecryptCipher getInstanceByKey(Key key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        if (desDecryptCipher == null) {
            desDecryptCipher = new DesDecryptCipher();
        }
        if (decryptCipher == null) {
            decryptCipher = Cipher.getInstance(Algorithm);
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        }
        return desDecryptCipher;
    }

    /**
     * 解密
     *
     * @param byteArrMi 需解密的字节数组
     * @return 明文字节数组
     * @throws Exception
     */
    public byte[] decrypt(byte[] byteArrMi) throws Exception {
        return decryptCipher.doFinal(byteArrMi);
    }

    /**
     * 解密
     *
     * @param strMi 需解密的字符串
     * @return 明文字符串
     * @throws Exception
     */
    public String decrypt(String strMi) throws Exception {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        return new String(decrypt(base64Decoder.decodeBuffer(strMi)));
    }
}
