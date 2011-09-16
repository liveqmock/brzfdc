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
 * Time: ����10:03
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
     * ����
     *
     * @param byteArrMi ����ܵ��ֽ�����
     * @return �����ֽ�����
     * @throws Exception
     */
    public byte[] decrypt(byte[] byteArrMi) throws Exception {
        return decryptCipher.doFinal(byteArrMi);
    }

    /**
     * ����
     *
     * @param strMi ����ܵ��ַ���
     * @return �����ַ���
     * @throws Exception
     */
    public String decrypt(String strMi) throws Exception {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        return new String(decrypt(base64Decoder.decodeBuffer(strMi)));
    }
}
