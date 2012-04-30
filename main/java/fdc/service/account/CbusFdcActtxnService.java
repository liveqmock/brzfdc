package fdc.service.account;

import fdc.gateway.cbus.domain.txn.QDJG02Res;
import fdc.gateway.service.CbusTxnService;
import fdc.repository.dao.CbsAccTxnMapper;
import fdc.repository.dao.RsSysCtlMapper;
import fdc.repository.model.CbsAccTxn;
import fdc.repository.model.RsAccount;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    private AccountService accountService;
    @Autowired
    private CbusTxnService cbusTxnService;
    @Autowired
    private CbsAccTxnMapper cbsAccTxnMapper;
    @Autowired
    private RsSysCtlMapper rsSysCtlMapper;

    public boolean isQryActtxns(String startDate, String endDate) {
        //TODO

        return false;
    }

    // 查询并保存所有监管账户的交易明细
    private int qrySaveActtxnsCbusByDate(String startDate, String endDate) throws Exception {
        List<RsAccount> accountList = accountService.qryAllMonitRecords();
        int cnt = 0;
        for (RsAccount account : accountList) {
            List<QDJG02Res> resList = cbusTxnService.qdjg02qryActtxnsByParams(account.getAccountCode(), startDate, endDate);
            for (QDJG02Res res : resList) {
                for (QDJG02Res.TxnRecord txnRecord : res.recordList) {
                    CbsAccTxn cbsAccTxn = new CbsAccTxn();
                    cbsAccTxn.setAccountNo(account.getAccountCode());
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
                    }
                    if (cbsAccTxnMapper.insert(cbsAccTxn) == 1) {
                        cnt++;
                    }
                }
            }
        }
        return cnt;
    }


}
