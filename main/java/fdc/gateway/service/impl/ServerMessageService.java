package fdc.gateway.service.impl;

import fdc.gateway.domain.fdc.T000.T0001Req;
import fdc.gateway.domain.fdc.T000.T0001Res;
import fdc.gateway.domain.fdc.T000.T0002Req;
import fdc.gateway.domain.fdc.T000.T0002Res;
import fdc.gateway.domain.fdc.T100.*;
import fdc.gateway.domain.fdc.T200.*;
import fdc.gateway.service.IMessageService;
import fdc.gateway.domain.BaseBean;
import fdc.utils.DateUtil;
import fdc.utils.StringUtil;
import org.apache.ecs.html.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-18
 * Time: ����2:27
 * To change this template use File | Settings | File Templates.
 */
public class ServerMessageService implements IMessageService {

    private static final Logger logger = LoggerFactory.getLogger(ServerMessageService.class);

    @Override
    public synchronized String handleMessage(String message) {
        String responseMsg = null;

        // �õ������룬���ݽ����뽫xmlת������Ӧ�Ľӿڶ���
        String opCode = StringUtil.getSubstrBetweenStrs(message, "<OpCode>", "</OpCode>");
        int nOpCode = Integer.parseInt(opCode);
        switch (nOpCode) {
            // 0001  0002  1001 1003 1004 2001 2002 2003 2006
            case 1:
                T0001Req t0001Req = (T0001Req) BaseBean.toObject(T0001Req.class, message);
                logger.info(t0001Req.head.OpDate + t0001Req.head.OpTime + "==���ս��ף�" + t0001Req.head.OpCode);
                T0001Res t0001Res = new T0001Res();
                t0001Res.param.Balance = "80001";
                t0001Res.param.UsableBalance = "80001";
                responseMsg = t0001Res.toFDCDatagram();
                break;
            case 2:
                T0002Req t0002Req = (T0002Req) BaseBean.toObject(T0002Req.class, message);
                logger.info(t0002Req.head.OpDate + t0002Req.head.OpTime + "==���ս��ף�" + t0002Req.head.OpCode);
                T0002Res t0002Res = new T0002Res();
                t0002Res.param.DetailNum = "2";
                T0002Res.Param.Record record = T0002Res.getRecord();
                record.Date = DateUtil.getDate8();
                record.Time = DateUtil.getTime6();
                record.Flag = "1";
                record.Type = "01";
                record.Amt = "1000";
                record.ContractNum = "100000000088";
                record.ToAcct = "555555555";
                record.ToAcctName = "����";
                record.ToBankName = "��������";
                record.Purpose = "����";

                t0002Res.param.recordList.add(record);

                record = T0002Res.getRecord();
                record.Date = DateUtil.getDate8();
                record.Time = DateUtil.getTime6();
                record.Flag = "1";
                record.Type = "01";
                record.Amt = "2000";
                record.ContractNum = "100000000089";
                record.ToAcct = "55555666666";
                record.ToAcctName = "����";
                record.ToBankName = "��������";
                record.Purpose = "Ҳ����";

                t0002Res.param.recordList.add(record);
                responseMsg = t0002Res.toFDCDatagram();
                break;
            case 1001:
                T1001Req t1001Req = (T1001Req) BaseBean.toObject(T1001Req.class, message);
                logger.info(t1001Req.head.OpDate + t1001Req.head.OpTime + "==���ս��ף�" + t1001Req.head.OpCode);
                T1001Res t1001Res = new T1001Res();
                responseMsg = t1001Res.toFDCDatagram();
                break;
            case 1003:
                T1003Req t1003Req = (T1003Req) BaseBean.toObject(T1003Req.class, message);
                logger.info(t1003Req.head.OpDate + t1003Req.head.OpTime + "==���ս��ף�" + t1003Req.head.OpCode);
                T1003Res t1003Res = new T1003Res();
                t1003Res.param.BankSerial = "1003";
                responseMsg = t1003Res.toFDCDatagram();
                break;
            case 1004:
                T1004Req t1004Req = (T1004Req) BaseBean.toObject(T1004Req.class, message);
                logger.info(t1004Req.head.OpDate + t1004Req.head.OpTime + "==���ս��ף�" + t1004Req.head.OpCode);
                T1004Res t1004Res = new T1004Res();
                responseMsg = t1004Res.toFDCDatagram();
                break;
            case 2001:
                T2001Req t2001Req = (T2001Req) BaseBean.toObject(T2001Req.class, message);
                logger.info(t2001Req.head.OpDate + t2001Req.head.OpTime + "==���ս��ף�" + t2001Req.head.OpCode);
                T2001Res t2001Res = new T2001Res();
                responseMsg = t2001Res.toFDCDatagram();
                break;
            case 2002:
                T2002Req t2002Req = (T2002Req) BaseBean.toObject(T2002Req.class, message);
                logger.info(t2002Req.head.OpDate + t2002Req.head.OpTime + "==���ս��ף�" + t2002Req.head.OpCode);
                T2002Res t2002Res = new T2002Res();
                responseMsg = t2002Res.toFDCDatagram();
                break;
            case 2003:
                T2003Req t2003Req = (T2003Req) BaseBean.toObject(T2003Req.class, message);
                logger.info(t2003Req.head.OpDate + t2003Req.head.OpTime + "==���ս��ף�" + t2003Req.head.OpCode);
                T2003Res t2003Res = new T2003Res();
                responseMsg = t2003Res.toFDCDatagram();
                break;
            case 2006:
                T2006Req t2006Req = (T2006Req) BaseBean.toObject(T2006Req.class, message);
                logger.info(t2006Req.head.OpDate + t2006Req.head.OpTime + "==���ս��ף�" + t2006Req.head.OpCode);
                T2006Res t2006Res = new T2006Res();
                t2006Res.param.CancelDate = DateUtil.getDate8();
                t2006Res.param.CancelTime = DateUtil.getTime6();
                t2006Res.param.FinalBalance = "2006";
                responseMsg = t2006Res.toFDCDatagram();
                break;
            default:
               logger.error("====���յ��޷�����Ƿ����ף��������룺"+opCode+"��");

        }
        return responseMsg;
    }
}
