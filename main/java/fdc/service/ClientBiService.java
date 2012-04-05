package fdc.service;

import fdc.common.constant.SendFlag;
import fdc.common.constant.TradeType;
import fdc.common.constant.WorkResult;
import fdc.gateway.domain.CommonRes;
import fdc.gateway.domain.T000.*;
import fdc.gateway.domain.T200.T2004Req;
import fdc.gateway.domain.T200.T2005Req;
import fdc.gateway.jms.ClientJmsTemplate;
import fdc.gateway.service.impl.ClientMessageService;
import fdc.gateway.utils.StringUtil;
import fdc.gateway.xsocket.client.XSocketComponent;
import fdc.repository.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.platform.advance.utils.PropertyManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    private ClientMessageService clientMessageService;
    @Autowired
    private PayoutService payoutService;
    @Autowired
    private ContractRecvService contractRecvService;
    @Autowired
    private LockedaccDetailService lockedaccDetailService;
    @Autowired
    private RefundService refundService;
    @Autowired
    private RsAccDetailService accDetailService;

    @Autowired
    private XSocketComponent xSocketComponent;
    @Autowired
    private ClientJmsTemplate clientJmsTemplate;

    private SimpleDateFormat sdfdate8 = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat sdftime6 = new SimpleDateFormat("HHmmss");

    /**
     * 0007
     *
     * @param accDetailList
     * @return
     * @throws Exception
     */
    public int sendTodayAccDetails(List<RsAccDetail> accDetailList) throws Exception {
        T0007Req req = new T0007Req();
        req.head.OpCode = "0007";
        for (RsAccDetail accDetail : accDetailList) {
            RsAccDetail originRecord = accDetailService.selectAccDetailByPkid(accDetail.getPkId());
            if ("1".equals(originRecord.getDcheckFlag())) {
                continue;
            }
            T0007Req.Param.Record record = T0007Req.getRecord();
            record.BankSerial = accDetail.getBankSerial();
            record.Date = StringUtil.transDate10ToDate8(accDetail.getTradeDate());
            record.Time = "121212";
            record.Flag = accDetail.getInoutFlag();
            record.Type = accDetail.getTradeType();
            record.ContractNum = accDetail.getContractNo();
            record.PlanDetailNO = accDetail.getPlanCtrlNo();
            record.AcctName = accDetail.getAccountName();
            record.Acct = accDetail.getAccountCode();
            record.ToName = accDetail.getToAccountName();
            record.ToAcct = accDetail.getToAccountCode();
            record.ToBankName = accDetail.getToHsBankName();
            record.Amt = StringUtil.toBiformatAmt(accDetail.getTradeAmt());
            record.Purpose = TradeType.HOUSE_INCOME.valueOfAlias(accDetail.getTradeType()).getTitle();
            req.param.recordList.add(record);
        }
        String dataGram = req.toFDCDatagram();                // 报文

        CommonRes res = sendMsgAndRecvRes(dataGram);
        if (!"0000".equalsIgnoreCase(res.head.RetCode)) {
            return -1;
        } else {
            for (RsAccDetail record : accDetailList) {
                //record.setSendFlag(SendFlag.SENT.getCode());
                record.setDcheckFlag("1");
                accDetailService.updateAccDetail(record);
            }
            return 1;
        }
    }

    public int sendTodayLoanAccDetails(List<RsAccDetail> accDetailList) throws Exception {
        T0007Req req = new T0007Req();
        req.head.OpCode = "0007";
        for (RsAccDetail accDetail : accDetailList) {
            T0007Req.Param.Record record = T0007Req.getRecord();
            record.BankSerial = accDetail.getBankSerial();
            record.Date = StringUtil.transDate10ToDate8(accDetail.getTradeDate());
            record.Time = "121212";
            record.Flag = accDetail.getInoutFlag();
            record.Type = accDetail.getTradeType();
            record.ContractNum = accDetail.getContractNo();
            record.PlanDetailNO = accDetail.getPlanCtrlNo();
            record.AcctName = accDetail.getAccountName();
            record.Acct = accDetail.getAccountCode();
            record.ToName = accDetail.getToAccountName();
            record.ToAcct = accDetail.getToAccountCode();
            record.ToBankName = accDetail.getToHsBankName();
            record.Amt = StringUtil.toBiformatAmt(accDetail.getTradeAmt());
            record.Purpose = TradeType.HOUSE_CREDIT.valueOfAlias(accDetail.getTradeType()).getTitle();
            req.param.recordList.add(record);
        }
        String dataGram = req.toFDCDatagram();                // 报文

        CommonRes res = sendMsgAndRecvRes(dataGram);
        if (!"0000".equalsIgnoreCase(res.head.RetCode)) {
            return -1;
        } else {
            for (RsAccDetail record : accDetailList) {
                //record.setSendFlag(SendFlag.SENT.getCode());
                record.setEcheckFlag("2");
                accDetailService.updateAccDetail(record);
            }
            return 1;
        }
    }


    /**
     * 0005
     *
     * @param record
     * @return
     * @throws Exception
     */
    public int sendInterestRecord(RsAccDetail record) throws Exception {
        RsAccDetail originRecord = accDetailService.selectAccDetailByPkid(record.getPkId());
        if (SendFlag.SENT.getCode().equals(originRecord.getSendFlag())) {
            return 1;
        }
        T0005Req req = new T0005Req();
        req.head.OpCode = "0005";
        req.param.Acct = record.getAccountCode();
        req.param.AcctName = record.getAccountName();
        req.param.BankSerial = record.getBankSerial();
        req.param.Amt = StringUtil.toBiformatAmt(record.getTradeAmt());
        req.param.Purpose = "利息";
        req.param.Date = sdfdate8.format(new Date());
        req.param.Time = sdftime6.format(new Date());
        String dataGram = req.toFDCDatagram();                // 报文

        CommonRes res = sendMsgAndRecvRes(dataGram);
        if (!"0000".equalsIgnoreCase(res.head.RetCode)) {
            return -1;
        } else {
            record.setSendFlag(SendFlag.SENT.getCode());
            return accDetailService.updateAccDetail(record);
        }
    }

    /**
     * 0004 退票
     *
     * @param record
     * @return
     * @throws Exception
     */
    public int sendAccDetailBack(RsAccDetail record) throws Exception {
        RsAccDetail originRecord = accDetailService.selectAccDetailByPkid(record.getPkId());
        if (SendFlag.SENT.getCode().equals(originRecord.getSendFlag())) {
            return 1;
        }
        T0004Req req = new T0004Req();
        req.head.OpCode = "0004";
        req.param.Acct = record.getAccountCode();
        req.param.AcctName = record.getAccountName();
        req.param.BankSerial = record.getBankSerial();
        req.param.Reason = "退票";
        req.param.Date = StringUtil.transDate10ToDate8(record.getTradeDate());
        req.param.Time = "121212";
        String dataGram = req.toFDCDatagram();                // 报文

        CommonRes res = sendMsgAndRecvRes(dataGram);
        if (!"0000".equalsIgnoreCase(res.head.RetCode)) {
            return -1;
        } else {
            record.setSendFlag(SendFlag.SENT.getCode());
            return accDetailService.updateAccDetail(record);
        }
    }

    /**
     * 发送冲正交易   0003
     *
     * @param record
     * @return
     */
    public int sendAccDetailCancel(RsAccDetail record) throws Exception {
        RsAccDetail originRecord = accDetailService.selectAccDetailByPkid(record.getPkId());
        if (SendFlag.SENT.getCode().equals(originRecord.getSendFlag())) {
            return 1;
        }
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
            record.setSendFlag(SendFlag.SENT.getCode());
            return accDetailService.updateAccDetail(record);
        }
    }

    /**
     * 2004-发送账户按计划付款明细记录
     *
     * @param record
     * @return
     * @throws Exception
     */
    public int sendRsPayoutMsg(RsPayout record) throws Exception {
        RsPayout originRecord = payoutService.selectPayoutByPkid(record.getPkId());
        if (WorkResult.SENT.getCode().equals(originRecord.getWorkResult())) {
            return 1;
        }
        T2004Req req = new T2004Req();
        req.head.OpCode = req.getClass().getSimpleName().substring(1, 5);
        req.param.Acct = record.getPayAccount();
        req.param.AcctName = record.getPayCompanyName();
        req.param.BankSerial = record.getBankSerial();
        req.param.Date = StringUtil.transDate10ToDate8(record.getExecDate());
        req.param.Time = "121212";
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

        RsReceive originRecord = contractRecvService.selectContractRecv(record.getPkId());
        if (WorkResult.SENT.getCode().equals(originRecord.getWorkResult())) {
            return 1;
        }
        T2005Req req = new T2005Req();
        req.head.OpCode = req.getClass().getSimpleName().substring(1, 5);
        req.param.Acct = record.getAccountCode();
        req.param.AcctName = record.getCompanyName();
        req.param.ContractNum = record.getBusinessNo();
        req.param.BankSerial = record.getBankSerial();
        req.param.Date = StringUtil.transDate10ToDate8(record.getTradeDate());
        req.param.Time = "121212";
        req.param.Flag = flag;   // 1-收入  2-支出
        req.param.Type = TradeType.HOUSE_INCOME.getCode();
        req.param.ToAcctName = record.getBuyerAccName();
        req.param.ToAcct = record.getBuyerAccCode();
        req.param.ToBankName = record.getBuyerBankName();
        req.param.Amt = StringUtil.toBiformatAmt(record.getApAmount());
        req.param.Purpose = record.getPurpose() == null ? TradeType.HOUSE_INCOME.getTitle()
                : record.getPurpose();
        String dataGram = req.toFDCDatagram();                // 报文

        CommonRes res = sendMsgAndRecvRes(dataGram);
        if (!"0000".equalsIgnoreCase(res.head.RetCode)) {
            return -1;
        } else {
            return contractRecvService.updateRsReceiveToWorkResult(record, WorkResult.SENT);
        }
    }

    /**
     * 2005- 发送预售房合同退款记录
     *
     * @param record , flag 收支标志
     * @return
     */
    public int sendRsRefundMsg(RsRefund record, String flag) throws Exception {

        RsRefund originRecord = refundService.selectRefundByPkid(record.getPkId());
        if (WorkResult.SENT.getCode().equals(originRecord.getWorkResult())) {
            return 1;
        }

        T2005Req req = new T2005Req();
        req.head.OpCode = req.getClass().getSimpleName().substring(1, 5);
        req.param.Acct = record.getPayAccount();
        req.param.AcctName = record.getPayCompanyName();
        req.param.ContractNum = record.getBusinessNo();
        req.param.BankSerial = record.getBankSerial();
        req.param.Date = StringUtil.transDate10ToDate8(record.getTradeDate());
        req.param.Time = "121212";
        req.param.Flag = flag;   // 1-收入  2-支出
        req.param.Type = TradeType.TRANS_BACK.getCode();
        req.param.ToAcctName = record.getRecCompanyName();
        req.param.ToAcct = record.getRecAccount();
        req.param.ToBankName = record.getRecBankName();
        req.param.Amt = StringUtil.toBiformatAmt(record.getApAmount());
        req.param.Purpose = record.getPurpose() == null ?
                TradeType.TRANS_BACK.getTitle() : record.getPurpose() + TradeType.TRANS_BACK.getTitle();
        String dataGram = req.toFDCDatagram();                // 报文

        CommonRes res = sendMsgAndRecvRes(dataGram);
        if (!"0000".equalsIgnoreCase(res.head.RetCode)) {
            return -1;
        } else {
            record.setWorkResult(WorkResult.SENT.getCode());
            return refundService.updateRecord(record);
        }
    }

    /**
     * 发送账户冻结解冻明细 0006
     *
     * @param record
     * @return
     */
    public int sendLockAccDetail(RsLockedaccDetail record) throws Exception {
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

    private CommonRes sendMsgAndRecvRes(String dataGram) throws Exception {
        // TODO 选定接口通信方式
        String recvMsg = null;

        if ("socket".equalsIgnoreCase(PropertyManager.getProperty("bank.dep.bi.type"))) {
            recvMsg = xSocketComponent.sendAndRecvDataByBlockConn(dataGram);
        } else if ("mq".equalsIgnoreCase(PropertyManager.getProperty("bank.dep.bi.type"))) {
            recvMsg = clientJmsTemplate.sendAndRecv(dataGram);
        } else if ("test".equalsIgnoreCase(PropertyManager.getProperty("bank.dep.bi.type"))) {
            recvMsg = new CommonRes().toXml();
        }
        logger.info("接收响应报文：" + recvMsg);
        CommonRes resBean = clientMessageService.transMsgToBean(recvMsg);
        return resBean;
    }
}
