package fdc.service.account;

import fdc.gateway.cbus.domain.txn.QDJG02Res;
import fdc.gateway.service.CbusTxnService;
import fdc.repository.model.RsAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-4-26
 * Time: ÏÂÎç3:07
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CbusActtxnService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private CbusTxnService cbusTxnService;

    public int qryAllActtxnsCbusByDate(String startDate, String endDate) throws Exception {
        List<RsAccount> accountList = accountService.qryAllMonitRecords();
        for (RsAccount account : accountList) {
            List<QDJG02Res> resList = cbusTxnService.qdjg02qryActtxnsByParams(account.getAccountCode(), startDate, endDate);

        }
        return 0;
    }
}
