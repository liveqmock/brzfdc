package fdc.gateway.cbus;

import fdc.gateway.service.CbusTxnService;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-4-24
 * Time: ����12:48
 * To change this template use File | Settings | File Templates.
 */
public class CbusTest {

    public static void main(String[] args) {
        try {
            CbusTxnService cbusTxnService = new CbusTxnService();

            // ��ѯ�˻����
            cbusTxnService.qdjg01QryActbal("6228571080001329608");
            // ����ת�� ת���ʺţ�ת���ʺţ����
            // cbusTxnService.qdjg03payAmtInBank("6228571080001329608", "6228571080001329609", "1.00");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
