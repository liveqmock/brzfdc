package fdc.gateway.cbus;

import fdc.gateway.cbus.domain.txn.QDJG02Res;
import fdc.gateway.service.CbusTxnService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-4-24
 * Time: 下午12:48
 * To change this template use File | Settings | File Templates.
 */
public class CbusTest {

    public static void main(String[] args) {
        try {
//              test04();
            // 374100201020000640  4-27
            test02();
//            test03();
//            test01("810200101421001610");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void test02() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
        CbusTxnService cbusTxnService = (CbusTxnService) context.getBean("cbusTxnService");
        // 查询交易明细
//        List<QDJG02Res> res02List = cbusTxnService.qdjg02qryActtxnsByParams("374100201020000640", "20120427", "20120427");
//        List<QDJG02Res> res02List = cbusTxnService.qdjg02qryActtxnsByParams("810200101421001610", "20120501", "20120507");
        List<QDJG02Res> res02List = cbusTxnService.qdjg02qryActtxnsByParams("374100201020000640", "20111001", "20120507");
        System.out.println("[报文包数]: " + res02List.size());
        int i = 1;
        for (QDJG02Res res : res02List) {
            System.out.println("[报文包" + (i++) + "内记录数]:" + res.recordList.size());
        }
    }

    public static void test01(String account) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
        CbusTxnService cbusTxnService = (CbusTxnService) context.getBean("cbusTxnService");
        cbusTxnService.qdjg01QryActbal(account);
    }

    public static void test03() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
        CbusTxnService cbusTxnService = (CbusTxnService) context.getBean("cbusTxnService");
        cbusTxnService.qdjg03payAmtInBank("810200101421001610", "810200101421001548", "10.0");

    }

    public static void test04() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
        CbusTxnService cbusTxnService = (CbusTxnService) context.getBean("cbusTxnService");
        String bankNo = "104829016059";
        String payOutAcctName = "转出账户";
        String payOutAcctNo = "810200101421001610";

        String payInAcctName = "转入账户";
        String payInAcctNo = "710200101421001610";
        String amt = "1.0";
        String purpose = "电汇";

        cbusTxnService.qdjg04payAmtBtwnBank(bankNo, payOutAcctName, payOutAcctNo, payInAcctName, payInAcctNo, amt, purpose);

    }
}
