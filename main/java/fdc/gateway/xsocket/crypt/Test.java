package fdc.gateway.xsocket.crypt;

import fdc.gateway.utils.StringUtil;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import java.math.BigDecimal;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;

public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        try {
            Security.addProvider(new com.sun.crypto.provider.SunJCE());
            KeyGenerator kg = KeyGenerator.getInstance("DES");
            Cipher c = Cipher.getInstance("DES/CBC/PKCS5Padding");
            kg.init(new SecureRandom("12345678".getBytes()));
            Key key = kg.generateKey();

            c.init(Cipher.ENCRYPT_MODE, key);
            String str = "<?xml version=\"1.0\" encoding=\"GBK\"?><root><Head><OpCode>0001</OpCode><OpDate>20110829</OpDate><OpTime>105950</OpTime><BankCode>304</BankCode></Head><Param><Acct>12050000000373775</Acct><AcctName>青岛伟业房地产有限公司</AcctName></Param></root>";
            System.out.println("明文 : " + str);
            System.out.println("------------------------------------");
            byte input[] = str.getBytes();
            byte encrypted[] = c.doFinal(input);
            byte iv[] = c.getIV();
            String strMi = new BASE64Encoder().encode(encrypted);
            System.out.println("密文 : " + strMi);
            System.out.println("------------------------------------");
            IvParameterSpec dps = new IvParameterSpec(iv);
            c.init(Cipher.DECRYPT_MODE, key, dps);
            byte output[] = c.doFinal(new BASE64Decoder().decodeBuffer(strMi));
            String newStrMing = new String(output);
            System.out.println("再转明文：" + newStrMing);
            System.out.println("------------------------------------");
            System.out.println("自我转换成功：" + newStrMing.equals(str));
            System.out.println("------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
