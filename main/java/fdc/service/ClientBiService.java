package fdc.service;

import com.sun.corba.se.spi.orbutil.threadpool.Work;
import fdc.common.constant.SendFlag;
import fdc.common.constant.TradeType;
import fdc.common.constant.WorkResult;
import fdc.gateway.domain.CommonRes;
import fdc.gateway.domain.T000.T0003Req;
import fdc.gateway.domain.T000.T0006Req;
import fdc.gateway.domain.T200.T2004Req;
import fdc.gateway.domain.T200.T2005Req;
import fdc.gateway.service.impl.ClientMessageService;
import fdc.gateway.utils.StringUtil;
import fdc.gateway.xsocket.client.XSocketComponent;
import fdc.repository.model.RsAccDetail;
import fdc.repository.model.RsLockedaccDetail;
import fdc.repository.model.RsPayout;
import fdc.repository.model.RsReceive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.common.utils.MessageUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-31
 * Time: 下午1:45
 * To change this template use File | Settings | File Templates.
 */

/**
 * 0004-0005-0007
 * 2004 -2005-0006-0003
 */
@Service
public class ClientBiService {
    private static final Logger logger = LoggerFactory.getLogger(ClientBiService.class);
    @Autowired
    private XSocketComponent xSocketComponent;
    @Autowired
    private ClientMessageService clientMessageService;
    @Autowired
    private PayoutService payoutService;
    @Autowired
    private ContractRecvService contractRecvService;
    @Autowired
    private LockedaccDetailService lockedaccDetailService;

    private SimpleDateFormat sdfdate8 = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat sdftime6 = new SimpleDateFormat("HHmmss");

    /**
     * 发送冲正交易   0003
     *
     * @param record
     * @return
     */
    public int sendAccDetailCancel(RsAccDetail record) throws IOException {
        T0003Req req = new T0003Req();
        req.head.OpCode = "0003";
        req.param.Acct = record.getAccountCode();
        req.param.AcctName = record.getAccountName();
        req.param.BankSerial = record.getBankSerial();
        req.param.Reason = "冲正";
        String dataGram = req.toFDCDatagram();                // 报文

        CommonRes res = sendMsgAndRecvRes(dataGram);
        if (!"0000".equalsIgnoreCase(res.head.RetCode)) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * 2004-发送账户按计划付款明细记录
     *
     * @param record
     * @return
     * @throws IOException
     */
    public int sendRsPayoutMsg(RsPayout record) throws IOException {
        T2004Req req = new T2004Req();
        req.head.OpCode = req.getClass().getSimpleName().substring(1, 5);
        req.param.Acct = record.getPayAccount();
        req.param.AcctName = record.getPayCompanyName();
        req.param.BankSerial = record.getBankSerial();
        req.param.Date = sdfdate8.format(record.getExecDate());
        req.param.Time = sdftime6.format(record.getExecDate());
        req.param.Flag = "0";
        req.param.Type = TradeType.PLAN_PAYOUT.getCode();
        req.param.ToAcctName = record.getRecCompanyName();
        req.param.ToAcct = record.getRecAccount();
        req.param.ToBankName = record.getRecBankName();
        req.param.Amt = StringUtil.toBiformatAmt(record.getPlAmount());
        req.param.Purpose = record.getPurpose();
        req.param.PlanDetailNO = record.getBusinessNo();
        String dataGram = req.toFDCDatagram();                // 报文

        CommonRes res = sendMsgAndRecvRes(dataGram);
        if (!"0000".equalsIgnoreCase(res.head.RetCode)) {
            return -1;
        } else {
            return payoutService.updateRsPayoutSent(record);
        }
    }

    /**
     * 2005- 发送预售房合同收支记录
     *
     * @param record , flag 收支标志
     * @return
     */
    public int sendRsReceiveMsg(RsReceive record, String flag) throws Exception {
        T2005Req req = new T2005Req();
        req.head.OpCode = req.getClass().getSimpleName().substring(1, 5);
        req.param.Acct = record.getAccountCode();
        req.param.AcctName = record.getCompanyName();
        req.param.ContractNum = record.getBusinessNo();
        req.param.BankSerial = record.getBankSerial();
        req.param.Date = sdfdate8.format(record.getTradeDate());
        req.param.Time = sdftime6.format(record.getTradeDate());
        req.param.Flag = flag;   // 1-收入  2-支出
        req.param.Type = TradeType.HOUSE_INCOME.getCode();
        req.param.ToAcctName = record.getBuyerAccName();
        req.param.ToAcct = record.getBuyerAccCode();
        req.param.ToBankName = record.getBuyerBankName();
        req.param.Amt = StringUtil.toBiformatAmt(record.getPlAmount());
        req.param.Purpose = record.getPurpose() + TradeType.HOUSE_INCOME.getTitle();
        String dataGram = req.toFDCDatagram();                // 报文

        CommonRes res = sendMsgAndRecvRes(dataGram);
        if (!"0000".equalsIgnoreCase(res.head.RetCode)) {
            return -1;
        } else {
            return contractRecvService.updateRsReceiveToWorkResult(record, WorkResult.SENT);
        }
    }

    /**
     * 发送账户冻结解冻明细 0006
     *
     * @param record
     * @return
     */
    public int sendLockAccDetail(RsLockedaccDetail record) throws IOException {
        if (lockedaccDetailService.isSent(record)) {
            return 1;
        }
        T0006Req req = new T0006Req();
        req.head.OpCode = req.getClass().getSimpleName().substring(1, 5);
        req.param.Acct = record.getAccountCode();
        req.param.AcctName = record.getAccountName();
        req.param.Status = record.getStatusFlag();
        req.param.Balance = StringUtil.toBiformatAmt(record.getBalance());
        req.param.LockAmt = StringUtil.toBiformatAmt(record.getBalanceLock());
        String dataGram = req.toFDCDatagram();                // 报文

        CommonRes res = sendMsgAndRecvRes(dataGram);
        if (!"0000".equalsIgnoreCase(res.head.RetCode)) {
            return -1;
        } else {
            return lockedaccDetailService.updateRecordToSendflag(record, SendFlag.SENT.getCode());
        }
    }

    private CommonRes sendMsgAndRecvRes(String dataGram) throws IOException {
        // TODO TEST-ing
        //String recvMsg = xSocketComponent.sendAndRecvDataByBlockConn(dataGram);
        String recvMsg = new CommonRes().toXml();
        CommonRes resBean = clientMessageService.transMsgToBean(recvMsg);
        return resBean;
    }
}
