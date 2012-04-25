package fdc.gateway.cbus;

import fdc.gateway.service.CbusTxnService;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-4-24
 * Time: œ¬ŒÁ12:48
 * To change this template use File | Settings | File Templates.
 */
public class CbusTest {


    public static void main(String[] args) {
        try {
            // ≤È—Ø’Àªß”‡∂Ó
            new CbusTxnService().qdjg01QryActbal("6228571080001329608");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
