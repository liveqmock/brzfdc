package fdc.gateway.cbus;

import fdc.gateway.service.CbusTxnService;

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
            CbusTxnService cbusTxnService = new CbusTxnService();

            // 查询账户余额
            cbusTxnService.qdjg01QryActbal("6228571080001329608");
            // 行内转账 转出帐号，转入帐号，金额
            // cbusTxnService.qdjg03payAmtInBank("6228571080001329608", "6228571080001329609", "1.00");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
