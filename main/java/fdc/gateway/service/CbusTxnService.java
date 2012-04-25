package fdc.gateway.service;

import fdc.gateway.cbus.CbusSocketClient;
import fdc.gateway.cbus.domain.base.MsgHeader;
import fdc.gateway.cbus.domain.txn.QDJG01Req;
import fdc.gateway.cbus.domain.txn.QDJG01Res;
import org.springframework.stereotype.Service;
import platform.service.PlatformService;
import pub.platform.advance.utils.PropertyManager;
import pub.platform.security.OperatorManager;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-4-25
 * Time: 下午9:28
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CbusTxnService {
    private static final String CBUS_SERVER_IP = PropertyManager.getProperty("bank.core.server.ip");
    private static final int CBUS_SERVER_PORT = PropertyManager.getIntProperty("bank.core.server.port");
    private static final int CBUS_SERVER_TIMEOUT = PropertyManager.getIntProperty("bank.core.server.timeout");

    // 测试帐号 "6228571080001329608"
    public QDJG01Res qdjg01QryActbal(String acccountNo) throws Exception {
        QDJG01Req qdjg01Req = new QDJG01Req();
        MsgHeader header = qdjg01Req.getHeader();
        try {
            OperatorManager om = PlatformService.getOperatorManager();
            header.setOperId(om.getOperatorId());
        } catch (Exception e) {
            header.setOperId("810201011002");
        }
        header.setSerialNo("105302"); // TODO 生成交易流水号

        qdjg01Req.setAccountNo(acccountNo);
        String reqStr = qdjg01Req.toString();

        byte[] rtnBytes = sendUntilRcv(reqStr);
        QDJG01Res qdjg01Res = new QDJG01Res();
        qdjg01Res.assembleFields(rtnBytes);
        return qdjg01Res;
    }

    // ----------------------------------------------------

    private byte[] sendUntilRcv(String strData) throws Exception {
        System.out.println("【本地客户端】【请求核心】" + strData);
        CbusSocketClient socketBlockClient = new CbusSocketClient(CBUS_SERVER_IP, CBUS_SERVER_PORT, CBUS_SERVER_TIMEOUT);
        byte[] rtnBytes = socketBlockClient.sendDataUntilRcv(strData.getBytes());
        System.out.println("【本地客户端】【核心响应】" + new String(rtnBytes));
        socketBlockClient.close();
        return rtnBytes;
    }
}
