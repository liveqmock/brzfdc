package fdc.gateway.xsocket.crypt.des;

import org.apache.commons.lang.StringUtils;
import pub.platform.advance.utils.PropertyManager;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-29
 * Time: 上午9:54
 * To change this template use File | Settings | File Templates.
 */
public class DesCrypter {

    private static final String STR_DEFAULT_KEY = PropertyManager.getProperty("fdc.socket.data.crypt.key");
    private static BASE64Encoder base64Encoder;
    private static BASE64Decoder base64Decoder;

    private static Cipher cipher;
    private static Key key;

    private static DesCrypter instance;

    private DesCrypter() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {
        KeySpec keySpec = new DESKeySpec(STR_DEFAULT_KEY.getBytes());
        key = SecretKeyFactory.getInstance("DES").generateSecret(keySpec);
        cipher = Cipher.getInstance("DES");
        base64Encoder = new BASE64Encoder();
        base64Decoder = new BASE64Decoder();
    }

    public static DesCrypter getInstance() throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException {
        if (instance == null) {
            instance = new DesCrypter();
        }
        return instance;
    }

    /**
     * 加密
     *
     * @param strMing
     * @return 密文
     * @throws Exception
     */
    public String encrypt(String strMing) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(strMing.getBytes());
        return base64Encoder.encode(encryptedBytes);
    }

    /**
     * 解密
     *
     * @param strMi
     * @return 明文
     * @throws Exception
     */
    public String decrypt(String strMi) throws Exception {

        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] miBytes = base64Decoder.decodeBuffer(strMi);
        byte[] decryptBytes = cipher.doFinal(miBytes);
        return new String(decryptBytes);
    }

    public static void main(String[] args) throws Exception {
       // String str = "<?xml version=\"1.0\" encoding=\"GBK\"?><root><Head><OpCode>0001</OpCode><OpDate>20110829</OpDate><OpTime>105950</OpTime><BankCode>304</BankCode></Head><Param><Acct>12050000000373775</Acct><AcctName>青岛伟业房地产有限公司</AcctName></Param></root>";
        String str = "<?xml version=\"1.0\" encoding=\"GBK\"?><root><Head><OpCode>0001</OpCode><OpDate>20110829</OpDate><OpTime>105950</OpTime><BankCode>304</BankCode></Head><Param><Acct>12050000000373775</Acct><AcctName>青岛伟业房地产有限公司</AcctName></Param></root>";
        System.out.println("明文：" + str);
        System.out.println("---------------------------------------------");
        DesCrypter crypter = DesCrypter.getInstance();
        String miStr = crypter.encrypt(str);
        System.out.println("密文：" + miStr);
        System.out.println("---------------------------------------------");
        System.out.println("明文：" + crypter.decrypt(miStr));
        System.out.println("---------------------------------------------");
    }
}
