package fdc.service.account;

import fdc.common.constant.InOutFlag;
import fdc.common.constant.TradeType;
import fdc.gateway.cbus.domain.txn.QDJG02Res;
import fdc.gateway.domain.CommonRes;
import fdc.gateway.domain.T000.T0007Req;
import fdc.gateway.service.CbusTxnService;
import fdc.gateway.utils.StringUtil;
import fdc.repository.dao.CbsAccTxnMapper;
import fdc.repository.dao.RsSendLogMapper;
import fdc.repository.dao.common.CommonMapper;
import fdc.repository.model.*;
import fdc.service.ClientBiService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-4-26
 * Time: 下午3:07
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CbusFdcActtxnService {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(CbusFdcActtxnService.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private CbusTxnService cbusTxnService;
    @Autowired
    private CbsAccTxnMapper cbsAccTxnMapper;
    @Autowired
    private RsSendLogMapper rsSendLogMapper;
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private ClientBiService clientBiService;

    // 自动发送当日贷款交易汇总
    public int autoSendLoanTxns() {
        String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
        try {
            qrySaveActtxnsCbusByDate(today, today);
            List<CbsAccTxn> accTxnList = qryCbsAccTotalTxnsByDateAndFlag(today, "0");
            sendAccTxns(accTxnList);
        } catch (Exception e) {
            logger.error("自动发送当日贷款交易汇总异常。", e);
        }
        return 0;
    }

    public boolean isQryedActtxns(String endDate) {
        RsSendLogExample example = new RsSendLogExample();
        example.createCriteria().andTxnDateEqualTo(endDate).andTxnResultEqualTo("1");
        return rsSendLogMapper.countByExample(example) > 0;
    }

    public boolean isSentActtxns(String endDate) {
        RsSendLogExample example = new RsSendLogExample();
        example.createCriteria().andTxnDateEqualTo(endDate).andTxnResultEqualTo("2");
        return rsSendLogMapper.countByExample(example) > 0;
    }

    // 查询并保存所有监管账户的交易明细
    public int qrySaveActtxnsCbusByDate(String startDate, String endDate) throws Exception {
        List<RsAccount> accountList = accountService.qryAllMonitRecords();
        int cnt = 0;
        for (RsAccount account : accountList) {
            List<QDJG02Res> resList = cbusTxnService.qdjg02qryActtxnsByParams(account.getAccountCode(), startDate, endDate);
            for (QDJG02Res res : resList) {
                for (QDJG02Res.TxnRecord txnRecord : res.recordList) {
                    CbsAccTxn cbsAccTxn = new CbsAccTxn();
                    cbsAccTxn.setAccountNo(account.getAccountCode());
                    cbsAccTxn.setAccountName(account.getAccountName());
                    cbsAccTxn.setBankId(res.getHeader().getBankId());
                    cbsAccTxn.setOperId(res.getHeader().getOperId());
                    cbsAccTxn.setSerialNo(res.getHeader().getSerialNo());
                    cbsAccTxn.setQryDate(res.getHeader().getTxnDate());
                    cbsAccTxn.setQryTime(res.getHeader().getTxnTime());
                    cbsAccTxn.setSeqNo(txnRecord.seqNo);
                    cbsAccTxn.setDebitAmt(txnRecord.debitAmt);
                    cbsAccTxn.setCreditAmt(txnRecord.creditAmt);
                    cbsAccTxn.setTxnType(txnRecord.txnType);
                    cbsAccTxn.setTxnSerialNo(txnRecord.txnSerialNo);
                    cbsAccTxn.setSummaryCode(txnRecord.summaryCode);
                    cbsAccTxn.setRemark(txnRecord.remark);
                    cbsAccTxn.setTxnDate(txnRecord.txnDate);
                    cbsAccTxn.setTxnTime(txnRecord.txnTime);
                    // TODO 判断该笔交易是否是贷款项
                    if (!StringUtils.isEmpty(txnRecord.remark) && txnRecord.remark.contains("贷款")) {
                        cbsAccTxn.setSendFlag("0");
                    } else {
                        cbsAccTxn.setSendFlag(null);
                    }
                    if (cbsAccTxnMapper.insert(cbsAccTxn) == 1) {
                        cnt++;
                    }
                }
            }
        }
        insertNewSendLog("QDJG02", "CBUS查询交易明细", endDate, "1");
        return cnt;
    }


    public List<CbsAccTxn> qryCbsAccTotalTxnsByDateAndFlag(String date, String sendFlag) {
        return commonMapper.qryCbsAcctxnsByDateAndFlag(date, sendFlag);
    }

    public int sendAccTxns(List<CbsAccTxn> cbsAccTxnList) throws Exception {
        T0007Req req = new T0007Req();
        req.head.OpCode = "0007";
        String txnDate = null;
        String bankSerial = null;
        for (CbsAccTxn accTxn : cbsAccTxnList) {
            T0007Req.Param.Record record = T0007Req.getRecord();
            record.Date = accTxn.getTxnDate();
            if (txnDate == null) {
                txnDate = accTxn.getTxnDate();
                bankSerial = commonMapper.qryBatchSerialNo(txnDate);
            }
            record.BankSerial = bankSerial;
            record.Time = accTxn.getTxnTime();
            record.Flag = InOutFlag.IN.getCode();
            record.Type = TradeType.HOUSE_CREDIT.getCode();
            record.ContractNum = "";
            record.PlanDetailNO = "";
            record.AcctName = accTxn.getAccountName();
            record.Acct = accTxn.getAccountNo();
            record.ToName = "";
            record.ToAcct = "";
            record.ToBankName = "";
            if (StringUtils.isEmpty(accTxn.getCreditAmt())) {
                record.Amt = "000";
            } else {
                record.Amt = StringUtil.toBiformatAmt(new BigDecimal(accTxn.getCreditAmt()));
            }
            record.Purpose = TradeType.HOUSE_CREDIT.getTitle();
            req.param.recordList.add(record);
        }
        String dataGram = req.toFDCDatagram();                // 报文

        CommonRes res = clientBiService.sendMsgAndRecvRes(dataGram);
        if (!"0000".equalsIgnoreCase(res.head.RetCode)) {
            return -1;
        } else {
            commonMapper.updateCbsActtxnsSent(txnDate);
            return insertNewSendLog("0007", "发送按揭贷款交易金额汇总", txnDate, "2");
        }
    }

    private int insertNewSendLog(String txnCode, String txnName, String txnDate, String txnResult) {
        RsSendLog rsSendLog = new RsSendLog();
        rsSendLog.setSysDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        rsSendLog.setTxnCode(txnCode);
        rsSendLog.setTxnDate(txnDate);
        rsSendLog.setTxnNam(txnName);
        rsSendLog.setTxnResult(txnResult);
        return rsSendLogMapper.insertSelective(rsSendLog);
    }
}
