package fdc.gateway.xsocket.crypt;

import org.apache.ecs.html.Base;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-29
 * Time: 上午9:54
 * To change this template use File | Settings | File Templates.
 */
public class DesCryptHelper {

    private static final String STR_DEFAULT_KEY = "12345678";

    private DesEncryptCipher encryptCipher = null;
    private DesDecryptCipher decryptCipher = null;

    /**
     * 默认构造方法，使用默认密钥
     *
     * @throws Exception
     */
    public DesCryptHelper() throws Exception {
        this(STR_DEFAULT_KEY);
    }

    /**
     * 指定密钥构造方法
     *
     * @param strKey 指定的密钥
     * @throws Exception
     */
    public DesCryptHelper(String strKey) throws Exception {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        Key key = generateKeyByStr(strKey);
        encryptCipher = DesEncryptCipher.getInstanceByKey(key);
        decryptCipher = DesDecryptCipher.getInstanceByKey(key);
    }

    /**
     * 加密
     *
     * @param strMing
     * @return 密文
     * @throws Exception
     */
    public String encrypt(String strMing) throws Exception {
        return encryptCipher.encrypt(strMing);
    }

    /**
     * 解密
     *
     * @param strMi
     * @return 明文
     * @throws Exception
     */
    public String decrypt(String strMi) throws Exception {
        return decryptCipher.decrypt(strMi);
    }


    /**
     * 用指定字符串生成密钥
     *
     * @param strKey
     * @return
     */
    private Key generateKeyByStr(String strKey) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(new SecureRandom(strKey.getBytes()));
        Key desKey = keyGenerator.generateKey();
        keyGenerator = null;
        return desKey;
    }

    public static void main(String[] args) throws Exception {
        String str = "<?xml version=\"1.0\" encoding=\"GBK\"?><root><Head><OpCode>0001</OpCode><OpDate>20110829</OpDate><OpTime>105950</OpTime><BankCode>304</BankCode></Head><Param><Acct>12050000000373775</Acct><AcctName>青岛伟业房地产有限公司</AcctName></Param></root>";
        DesCryptHelper helper = new DesCryptHelper();
        System.out.println("明文："+str);
        System.out.println("---------------------------------------------");
        String miStr = helper.encrypt(str);
        System.out.println("密文："+ miStr);
        System.out.println("---------------------------------------------");
        System.out.println("明文："+ helper.decrypt(miStr));
        System.out.println("---------------------------------------------");
    }
}
