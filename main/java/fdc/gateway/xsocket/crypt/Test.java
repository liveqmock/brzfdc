package fdc.gateway.xsocket.crypt;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
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

    private void test1() {
        try {
            String passPhrase = "12345678";
//            String message = "1:1;2:1;3:APSERVER;4:00-14-5E-87-51-4B;5:ADMIN;6:VT;7:123;8:20110421;9:101109;10:3314954025;11:2000";
            String message = "<?xml version=\"1.0\" encoding=\"GBK\"?><root><Head><OpCode>0001</OpCode><OpDate>20110829</OpDate><OpTime>105950</OpTime><BankCode>304</BankCode></Head><Param><Acct>12050000000373775</Acct><AcctName>青岛伟业房地产有限公司</AcctName></Param></root>";

            String algorithm = "DES";
            Key key = KeyGenerator.getInstance(algorithm).generateKey();
            Cipher cipher = Cipher.getInstance(algorithm);

            KeySpec keySpec = new DESKeySpec(passPhrase.getBytes());
            key = SecretKeyFactory.getInstance(algorithm).generateSecret(keySpec);

            System.out.println(message);

            byte[] encryptionBytes = null;
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] inputBytes = message.getBytes();
            encryptionBytes = cipher.doFinal(inputBytes);

            int size = encryptionBytes.length;
            encryptionBytes[size - 1] = (byte) 0x5a;
            encryptionBytes[size - 2] = (byte) 0x7d;
            for (byte b : encryptionBytes) {
                System.out.format("%02x", b);
            }
            System.out.println();

            cipher.init(Cipher.DECRYPT_MODE, key);

//            encryptionBytes = "bd0f42ed9acbce34291ef663b47bd46f49850abf36e3f68265126a7c9d09bcd135b6e7c45d4c0d45f808b7098d3e5c42740996637d565bf57916c38d3c6fd3a04a49c88b7ab2d4c0d5d27ddb0bc42b334bf7742512cb9498d16b356cede675df2042a0d5cc77ed07b41fe0e04c152502ce407b0233f002dc51e6bb2ef454662549382dcbd2991f8de0030c949048debc989fb3cd082d6311e8583358dab2beac9c217fe47a462e62c5e788b0e2fb902ca17b4492994a72272644711bdc0c0391a68f7f06b073afa076ebc007ffb1b99bd4a015403ce9ec68505622bbed1557aa7f7021ec4d123f487a9fc5fa4a68f0c227359059f3ce7d5a".getBytes();
            byte[] recoveredBytes = cipher.doFinal(encryptionBytes);
            String recovered = new String(recoveredBytes);
            System.out.println(recovered);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
