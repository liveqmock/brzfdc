package fdc.gateway.xsocket.crypt;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.spec.KeySpec;

public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        try {
            //Security.addProvider(new com.sun.crypto.provider.SunJCE());
            //KeyGenerator kg = KeyGenerator.getInstance("DES");
            //Cipher c = Cipher.getInstance("DES/CBC/PKCS5Padding");
            //kg.init(new SecureRandom("12345678".getBytes()));
            //Key key = kg.generateKey();

            KeySpec keySpec = new DESKeySpec("12345678".getBytes());
            Key key = SecretKeyFactory.getInstance("DES").generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance("DES");

            cipher.init(Cipher.ENCRYPT_MODE, key);
            String str = "<?xml version=\"1.0\" encoding=\"GBK\"?><root><Head><OpCode>0001</OpCode><OpDate>20110829</OpDate><OpTime>105950</OpTime><BankCode>304</BankCode></Head><Param><Acct>12050000000373775</Acct><AcctName>青岛伟业房地产有限公司</AcctName></Param></root>";
//            String str = "123321";
            System.out.println("明文 : " + str);
            System.out.println("------------------------------------");
            byte input[] = str.getBytes();
            byte encrypted[] = cipher.doFinal(input);

            for (byte b : encrypted) {
                System.out.format("%02x", b);
            }

            byte iv[] = cipher.getIV();
            String strMi = new BASE64Encoder().encode(encrypted);
            System.out.println("密文 : " + strMi);
            System.out.println("------------------------------------");


            IvParameterSpec dps = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, key, dps);
            byte output[] = cipher.doFinal(new BASE64Decoder().decodeBuffer(strMi));
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
