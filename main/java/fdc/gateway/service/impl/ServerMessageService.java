package fdc.gateway.service.impl;

import fdc.gateway.domain.ResHead;
import fdc.gateway.domain.CommonRes;
import fdc.gateway.domain.T000.T0001Req;
import fdc.gateway.domain.T000.T0001Res;
import fdc.gateway.domain.T000.T0002Req;
import fdc.gateway.domain.T000.T0002Res;
import fdc.gateway.domain.T200.*;
import fdc.gateway.service.IMessageService;
import fdc.gateway.domain.BaseBean;
import fdc.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import platform.service.SystemService;
import pub.platform.db.ConnectionManager;
import pub.platform.db.DatabaseConnection;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-18
 * Time: 上午2:27
 * To change this template use File | Settings | File Templates.
 */
public class ServerMessageService implements IMessageService {

    private static final Logger logger = LoggerFactory.getLogger(ServerMessageService.class);

    @Override
    public synchronized String handleMessage(String message) {
        String responseMsg = null;
        ConnectionManager cm = ConnectionManager.getInstance();
        DatabaseConnection dc = cm.getConnection();
        dc.executeUpdate("insert into T_INTERFACE_LOG values('" + message + "',sysdate)");
        cm.releaseConnection(dc);
        // 得到交易码，根据交易码将xml转换成相应的接口对象。
        String opCode = StringUtil.getSubstrBetweenStrs(message, "<OpCode>", "</OpCode>");
        int nOpCode = Integer.parseInt(opCode);
        switch (nOpCode) {
            // 0001  0002   2001 2002 2003 2006    2007 2008
            case 1:
                T0001Req t0001Req = (T0001Req) BaseBean.toObject(T0001Req.class, message);
                logger.info(t0001Req.head.OpDate + t0001Req.head.OpTime + "==接收交易：" + t0001Req.head.OpCode);
                T0001Res t0001Res = new T0001Res();
                t0001Res.param.Balance = "80001";
                t0001Res.param.UsableBalance = "80001";
                responseMsg = t0001Res.toFDCDatagram();
                break;
            case 2:
                T0002Req t0002Req = (T0002Req) BaseBean.toObject(T0002Req.class, message);
                logger.info(t0002Req.head.OpDate + t0002Req.head.OpTime + "==接收交易：" + t0002Req.head.OpCode);
                T0002Res t0002Res = new T0002Res();
                t0002Res.param.DetailNum = "2";
                T0002Res.Param.Record record = T0002Res.getRecord();
                record.Date = SystemService.getSdfdate8();
                record.Time = SystemService.getSdftime6();
                record.Flag = "1";
                record.Type = "01";
                record.Amt = "1000";
                record.ContractNum = "100000000088";
                record.ToAcct = "555555555";
                record.ToAcctName = "张三";
                record.ToBankName = "日照银行";
                record.Purpose = "购房";

                t0002Res.param.recordList.add(record);

                record = T0002Res.getRecord();
                record.Date = SystemService.getSdfdate8();
                record.Time = SystemService.getSdftime6();
                record.Flag = "1";
                record.Type = "01";
                record.Amt = "2000";
                record.ContractNum = "100000000089";
                record.ToAcct = "55555666666";
                record.ToAcctName = "张四";
                record.ToBankName = "日照银行";
                record.Purpose = "也购房";

                t0002Res.param.recordList.add(record);
                responseMsg = t0002Res.toFDCDatagram();
                break;

            case 2001:
                T2001Req t2001Req = (T2001Req) BaseBean.toObject(T2001Req.class, message);
                logger.info(t2001Req.head.OpDate + t2001Req.head.OpTime + "==接收交易：" + t2001Req.head.OpCode);
                T2001Res t2001Res = new T2001Res();
                responseMsg = t2001Res.toFDCDatagram();
                break;
            case 2002:
                T2002Req t2002Req = (T2002Req) BaseBean.toObject(T2002Req.class, message);
                logger.info(t2002Req.head.OpDate + t2002Req.head.OpTime + "==接收交易：" + t2002Req.head.OpCode);
                T2002Res t2002Res = new T2002Res();
                responseMsg = t2002Res.toFDCDatagram();
                break;
            case 2003:
                T2003Req t2003Req = (T2003Req) BaseBean.toObject(T2003Req.class, message);
                logger.info(t2003Req.head.OpDate + t2003Req.head.OpTime + "==接收交易：" + t2003Req.head.OpCode);
                T2003Res t2003Res = new T2003Res();
                responseMsg = t2003Res.toFDCDatagram();
                break;
            case 2006:
                T2006Req t2006Req = (T2006Req) BaseBean.toObject(T2006Req.class, message);
                logger.info(t2006Req.head.OpDate + t2006Req.head.OpTime + "==接收交易：" + t2006Req.head.OpCode);
                T2006Res t2006Res = new T2006Res();
                t2006Res.param.CancelDate = SystemService.getSdfdate8();
                t2006Res.param.CancelTime = SystemService.getSdftime6();
                t2006Res.param.FinalBalance = "2006";
                responseMsg = t2006Res.toFDCDatagram();
                break;
            case 2007:
                T2007Req t2007Req = (T2007Req) BaseBean.toObject(T2007Req.class, message);
                logger.info(t2007Req.head.OpDate + t2007Req.head.OpTime + "==接收交易：" + t2007Req.head.OpCode);
                T2007Res t2007Res = new T2007Res();
                responseMsg = t2007Res.toFDCDatagram();
                break;
              case 2008:
                T2007Req t2008Req = (T2007Req) BaseBean.toObject(T2008Req.class, message);
                logger.info(t2008Req.head.OpDate + t2008Req.head.OpTime + "==接收交易：" + t2008Req.head.OpCode);
                T2008Res t2008Res = new T2008Res();
                responseMsg = t2008Res.toFDCDatagram();
                break;
            default:
                CommonRes okData = new CommonRes();
                responseMsg = okData.toFDCDatagram();
               // logger.error("====接收到无法处理非法交易：【交易码：" + opCode + "】");

        }
        return responseMsg;
    }
}
