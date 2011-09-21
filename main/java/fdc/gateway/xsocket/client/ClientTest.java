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
            testDecrypt(miStr);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    static String miStr = "bd0f42ed9acbce34291ef663b47bd46f49850abf36e3f68265126a7c9d09bcd135b6e7c45d4c0d45f808b7098d3e5c42740996637" +
            "d565bf54c21bc6392770c9b4a49c88b7ab2d4c0d5d27ddb0bc42b33c78edbd78d6db281d16b356cede675df3c0ea1aca470a8c4489" +
            "95d0fcdad2161ce407b0233f002dc51e6bb2ef4546625ef41674a0f3cfee3e0030c949048debc989fb3cd082d6311e8583358dab2be" +
            "ac397dda02da0e8cfe2e6249fc5b9c78c82644711bdc0c039108fa29678cd5a6e4a411911cc80c1cb1d4a015403ce9ec68505622bbe" +
            "d1557aa72b9b8f34375d378678a7bbd0d901be1160357c28aa7c8df7fd8bbaca8ea3df17fcf86409fb209ece9a194f1f45d9bb97529" +
            "e1e9f708466e4de430d5524be1f04c12e3690ff73b8e0c66449bc4590e8c5a6d363b779744185903a76493164fd14c12e3690ff73b8e72" +
            "b9b8f34375d378e1df92b50e3dc41740fc776615209ceafcce38584c3718a807ea9e0c6665ee69ce99d37d9eb1a141cc38769e2638bb1" +
            "34123c80da04eb150c592152248baabdace99d37d9eb1a14114bea955baeb65e6e4db6ac602905d185b918e376802756120d64679b7ef12" +
            "2bfc83ddf6f495f21f5896f9ec7308c75dce032284160146a01050346a9be6cd687931af06b10406959e88d59619f841a8fba3a61298c07e2f6e5" +
            "865ce6ad4a43b97e87612290ce72083a525c7cb59451e0742d4d3c9696630477b1a2b0d1bf33075bec893738e3d10b753254" +
            "437017537310b142b9826a03875bec893738e3d1009a9fea0b278868be6f517c5d4f0ce4c";

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
