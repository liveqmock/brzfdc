package fdc.gateway.xsocket.client;

import fdc.gateway.domain.BaseBean;
import fdc.gateway.domain.CommonRes;
import fdc.gateway.domain.T000.T0002Res;
import fdc.gateway.xsocket.crypt.des.DesCrypter;

import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-9-21
 * Time: 下午9:58
 * To change this template use File | Settings | File Templates.
 */
public class ClientTest {
    public static void main(String[] args) {

        //String dataGram = "<?xml version=\"1.0\" encoding=\"GBK\"?><root><Head><OpCode>0002</OpCode><OpDate>20110525</OpDate><OpTime>140105</OpTime><BankCode>313</BankCode></Head><Param><Acct>123456789</Acct><AcctName>青岛市测试地产有限公司</AcctName><BeginDate>20110915</BeginDate><EndDate>20110925</EndDate></Param></root>";
        //testSendData(dataGram);
        try {
            System.out.println(miStr.getBytes().length);
            testDecrypt(miStr);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    static String miStr = "bd0f42ed9acbce34291ef663b47bd46f49850abf36e3f68265126a7c9d09bcd135b6e7c45d4c0d45f808b7098d3e5c42740996637d565bf51ad72140d83b2db44a49c88b7ab2d4c0d5d27ddb0bc42b337fdba3fc75c4bb53d16b356cede675df2042a0d5cc77ed07b89a887935d3e935ce407b0233f002dc51e6bb2ef4546625ef41674a0f3cfee3e0030c949048debc989fb3cd082d6311e8583358dab2beac397dda02da0e8cfe2e6249fc5b9c78c82644711bdc0c039108fa29678cd5a6e4a411911cc80c1cb1d4a015403ce9ec68505622bbed1557aa292a5b4ed25cbc957beab2091eba940c754ed5319c7dba8edff9ff58feb082b8469c360f55f85325953f0aca8280c67e4fc2bf55e5f76f807289a40b86c17ab14b2e7895ac00b1ef937adbaa7cf8a4df748a2694542d10db6d83165401433410d4cbd35a9f6d378b66d447ce76d1acfaac046ba1a603227364db12800d80f0cac6ba3d7419dfe242812cf12eb59fe04a0a08e14de214b20167b58160f4fa2f66b9b9a3d6c1aeebee1b640c2137d1cbe252bb16ba6c5f482ce217e86b46719def4a14d16275252e8b871b6c31aae27c67f6a18d8dccc4563e5f2c6f470102f8b97fe91a0afba0dfbc38ab8c8c54da7d6af014ee438e9c94398a688f2e4979cc9a11b8f8c6cd949c0855e5b7ac2039a2b01907754f76e078bf904d339ef6e1cce6f38c43e1cd1ca47187c3b71fab7e491b8215136501818b8691b2a2a5f076881592685368ebe426519b7f611621b2cc0c6e1c68aeaaab093a9a5714ffe85099a87f9a092039fac3778d1ca922ded74105b7aa26678243637364db12800d80f0cac6ba3d7419dfe242812cf12eb59fe04ab4348f40fde6057967b58160f4fa2f66b9b9a3d6c1aeebee1b640c2137d1cbe2e818c275caa2ce3fede82f275edc0791adb393a6f2bf3bb658747469a2d0443e27c83459a2a6004bf6cd99cc54e3715fb4c186e74948a70181871fafbab09f54cfb5263fe0fb8daedc96472d9de1a2dc6e88065f8145dfe725289e65f3330d0182ab45e65ce8e184833ff39d73be6f397e2d540922b49451b1fbc997d63e0d499224cb086235e0ba2f5c7d8594305accbf1a959f2997bb91b725ebafd57d3308678df18b6b7b8c09f52ab5453e6bcaed4c1027c6c2bbedf5aaf3cfc6c40fc2ccd1c8d56c5ff4a435b425d893eac4296116fb024f1b0ef345679568766527138b62ea0b73119d051503489c1f579306be3fc5e3a0286cec1de29bf3d034eb86653f0501b41dfcddf93e592be53b18e771ea6c2088ed56851058747469a2d0443e27c83459a2a6004bf6cd99cc54e3715fb4c186e74948a70181871fafbab09f5451fa7e62483a45480bbe59bcd8426b8c11d7360e9492ae7a25289e65f3330d0182ab45e65ce8e184223b16f609af51937517101b10ed8e643e174b720b1c01d2068ac4aa9979da86499d81d9e5f8f385e2ff702fc12610c93c8880946aa4fa83d92ef6a2beab321a1cd967d12b26cbbd59f93af48fbd4ddfb425d893eac4296109a9fea0b278868be6f517c5d4f0ce4c";

    public static void testSendData(String dataGram) {
        XSocketComponent xSocketComponent = new XSocketComponent();
        String recvMsg = null;
        try {
            recvMsg = xSocketComponent.sendAndRecvDataByBlockConn(dataGram);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        T0002Res resBean = (T0002Res) BaseBean.toObject(T0002Res.class,recvMsg);
        for(T0002Res.Param.Record record : resBean.param.recordList) {
            System.out.println("交易金额:"+record.Amt);
        }
    }

    public static void testDecrypt(String miStr) throws Exception, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {
        String strMing = DesCrypter.getInstance().decrypt(miStr);
        System.out.println("转为明文："+strMing);
    }
}
