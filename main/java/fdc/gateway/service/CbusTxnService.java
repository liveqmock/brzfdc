package fdc.gateway.service;

import fdc.gateway.cbus.CbusSocketClient;
import fdc.gateway.cbus.domain.base.MsgHeader;
import fdc.gateway.cbus.domain.txn.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import platform.service.PlatformService;
import pub.platform.advance.utils.PropertyManager;
import pub.platform.security.OperatorManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-4-25
 * Time: ����9:28
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CbusTxnService {
    private static Logger logger = LoggerFactory.getLogger(CbusTxnService.class);

    private static final String CBUS_SERVER_IP = PropertyManager.getProperty("bank.core.server.ip");
    private static final int CBUS_SERVER_PORT = PropertyManager.getIntProperty("bank.core.server.port");
    private static final int CBUS_SERVER_TIMEOUT = PropertyManager.getIntProperty("bank.core.server.timeout");

    // ����ѯ �����ʺ� "6228571080001329608"
    public QDJG01Res qdjg01QryActbal(String acccountNo) throws Exception {
        QDJG01Req qdjg01Req = new QDJG01Req();
        MsgHeader header = qdjg01Req.getHeader();
        try {
            OperatorManager om = PlatformService.getOperatorManager();
            header.setOperId(om.getOperatorId());
        } catch (Exception e) {
            header.setOperId("810201011002");
        }
        header.setSerialNo("105302"); // TODO ���ɽ�����ˮ��
        qdjg01Req.accountNo = acccountNo;
        String reqStr = qdjg01Req.toString();
        byte[] rtnBytes = sendUntilRcv(reqStr);
        QDJG01Res qdjg01Res = new QDJG01Res();
        qdjg01Res.assembleFields(rtnBytes);
        return qdjg01Res;
    }

    // TODO ��ѯ������ϸ
    public List<QDJG02Res> qdjg02qryActtxnsByInitReq(QDJG02Req qdjg02Req) throws Exception {
        MsgHeader header = qdjg02Req.getHeader();
        String operId = null;
        try {
            OperatorManager om = PlatformService.getOperatorManager();
            operId = om.getOperatorId();
        } catch (Exception e) {
            operId = "810201011002";
        }
        header.setOperId(operId);
        QDJG02Res qdjg02Res = qdjg02qryActtxnsByReq(qdjg02Req);
        List<QDJG02Res> resList = new ArrayList<QDJG02Res>();
        resList.add(qdjg02Res);
        while (!"1".equals(qdjg02Res.isLast)) { // �������һҳ
            qdjg02Req.preFirstKey = qdjg02Res.thisFirstKey;
            qdjg02Req.preLastKey = qdjg02Res.thisLastKey;
            int newSeqNo = Integer.parseInt(qdjg02Res.recordList.get(qdjg02Res.recordList.size() - 1).seqNo) + 1;
            qdjg02Req.startDetailNo = String.valueOf(newSeqNo);
            qdjg02Res = qdjg02qryActtxnsByReq(qdjg02Req);
            resList.add(qdjg02Res);
        }
        return resList;
    }

    //  ��ѯ������ϸ
    private QDJG02Res qdjg02qryActtxnsByReq(QDJG02Req qdjg02Req) throws Exception {

        qdjg02Req.getHeader().setSerialNo("105303"); // TODO ���ɽ�����ˮ��
        String reqStr = qdjg02Req.toString();
        byte[] rtnBytes = sendUntilRcv(reqStr);
        QDJG02Res qdjg02Res = new QDJG02Res();
        qdjg02Res.assembleFields(rtnBytes);
        return qdjg02Res;
    }

    // ����ת��
    public QDJG03Res qdjg03payAmtInBank(String payOutAct, String payInAct, String payAmt) throws Exception {
        QDJG03Req qdjg03Req = new QDJG03Req();
        MsgHeader header = qdjg03Req.getHeader();
        try {
            OperatorManager om = PlatformService.getOperatorManager();
            header.setOperId(om.getOperatorId());
        } catch (Exception e) {
            header.setOperId("810201011002");
        }
        header.setSerialNo("105303"); // TODO ���ɽ�����ˮ��
        qdjg03Req.payOutAccount = payOutAct;
        qdjg03Req.payInAccount = payInAct;
        qdjg03Req.payAmt = payAmt;

        String reqStr = qdjg03Req.toString();
        byte[] rtnBytes = sendUntilRcv(reqStr);
        QDJG03Res qdjg03Res = new QDJG03Res();
        qdjg03Res.assembleFields(rtnBytes);
        return qdjg03Res;
    }

    // ���
    /*
                �ձ���	SND-TO-BK-NO	C(12)
                ���������	RMTR-NAME-FL	C(64)
                ������ʺ�	RMTR-ACCT-NO	C(32)
                �տ�������	PAYEE-NAME-FL	C(64)
                �տ����ʺ�	PAYEE-FL-ACCT-NO	C(32)
                �����	RMT-AMT	N(13��2)
                �����;	RMT-PURP	C(64)
     */
    public QDJG04Res qdjg04payAmtBtwnBank(String sndToBkNo, String rmtrNameFl, String rmtrAcctNo,
                                          String payeeNameFl, String payeeFlAcctNo, String rmtAmt, String rmtPurp)
            throws Exception {
        QDJG04Req qdjg04Req = new QDJG04Req();
        qdjg04Req.sndToBkNo = sndToBkNo;
        qdjg04Req.rmtrNameFl = rmtrNameFl;
        qdjg04Req.rmtrAcctNo = rmtrAcctNo;
        qdjg04Req.payeeNameFl = payeeNameFl;
        qdjg04Req.payeeFlAcctNo = payeeFlAcctNo;
        qdjg04Req.rmtAmt = rmtAmt;
        qdjg04Req.rmtPurp = rmtPurp;

        return qdjg04payAmtBtwnBankByReq(qdjg04Req);
    }

    // ���
    public QDJG04Res qdjg04payAmtBtwnBankByReq(QDJG04Req qdjg04Req) throws Exception {
        MsgHeader header = qdjg04Req.getHeader();
        try {
            OperatorManager om = PlatformService.getOperatorManager();
            header.setOperId(om.getOperatorId());
        } catch (Exception e) {
            header.setOperId("810201011002");
        }
        header.setSerialNo("105303"); // TODO ���ɽ�����ˮ��
        String reqStr = qdjg04Req.toString();
        byte[] rtnBytes = sendUntilRcv(reqStr);
        QDJG04Res qdjg04Res = new QDJG04Res();
        qdjg04Res.assembleFields(rtnBytes);
        return qdjg04Res;
    }

    // ----------------------------------------------------

    private byte[] sendUntilRcv(String strData) throws Exception {
        logger.info("�����ؿͻ��ˡ���������ġ�" + strData);
        CbusSocketClient socketBlockClient = new CbusSocketClient(CBUS_SERVER_IP, CBUS_SERVER_PORT, CBUS_SERVER_TIMEOUT);
        byte[] rtnBytes = socketBlockClient.sendDataUntilRcv(strData.getBytes());
        logger.info("�����ؿͻ��ˡ���������Ӧ��" + new String(rtnBytes));
        socketBlockClient.close();
        return rtnBytes;
    }
}
