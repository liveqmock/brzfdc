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
 * Time: ����9:54
 * To change this template use File | Settings | File Templates.
 */
public class DesCryptHelper {

    private static final String STR_DEFAULT_KEY = "12345678";

    private DesEncryptCipher encryptCipher = null;
    private DesDecryptCipher decryptCipher = null;

    /**
     * Ĭ�Ϲ��췽����ʹ��Ĭ����Կ
     *
     * @throws Exception
     */
    public DesCryptHelper() throws Exception {
        this(STR_DEFAULT_KEY);
    }

    /**
     * ָ����Կ���췽��
     *
     * @param strKey ָ������Կ
     * @throws Exception
     */
    public DesCryptHelper(String strKey) throws Exception {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        Key key = generateKeyByStr(strKey);
        encryptCipher = DesEncryptCipher.getInstanceByKey(key);
        decryptCipher = DesDecryptCipher.getInstanceByKey(key);
    }

    /**
     * ����
     *
     * @param strMing
     * @return ����
     * @throws Exception
     */
    public String encrypt(String strMing) throws Exception {
        return encryptCipher.encrypt(strMing);
    }

    /**
     * ����
     *
     * @param strMi
     * @return ����
     * @throws Exception
     */
    public String decrypt(String strMi) throws Exception {
        return decryptCipher.decrypt(strMi);
    }


    /**
     * ��ָ���ַ���������Կ
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
}
