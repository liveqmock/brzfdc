package fdc.gateway.xsocket.crypt;

import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-29
 * Time: 上午10:21
 * To change this template use File | Settings | File Templates.
 */
public class DesEncryptCipher {
    private static DesEncryptCipher desEncryptCipher = null;
    private static Cipher encryptCipher = null;

    public static DesEncryptCipher getInstanceByKey(Key key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        if (desEncryptCipher == null) {
            desEncryptCipher = new DesEncryptCipher();
        }
        if (encryptCipher == null) {
            encryptCipher = Cipher.getInstance("DES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
        }
        return desEncryptCipher;
    }

    private DesEncryptCipher() {
    }

    /**
     * 加密
     *
     * @param byteArrMing 明文
     * @return 密文
     * @throws Exception
     */
    public byte[] encrypt(byte[] byteArrMing) throws Exception {
        return encryptCipher.doFinal(byteArrMing);
    }

    /**
     * 加密
     *
     * @param strMing 明文
     * @return 密文
     * @throws Exception
     */
    public String encrypt(String strMing) throws Exception {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(encrypt(strMing.getBytes()));
    }

}
