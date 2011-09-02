package fdc.service;

import fdc.common.constant.AccountStatus;
import fdc.common.constant.LimitStatus;
import fdc.common.constant.TradeType;
import fdc.repository.model.*;
import fdc.service.account.AccountService;
import fdc.service.expensesplan.ExpensesPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import platform.service.SystemService;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-9-2
 * Time: ����2:22
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TradeService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private RsAccDetailService accDetailService;
    @Autowired
    private PayoutService payoutService;
    @Autowired
    private ExpensesPlanService expensesPlanService;

    /**
     * ����ƻ������
     * @param payout
     * @return
     */
    public int handlePayoutTrade(RsPayout payout) {
        // ��ѯδ�����Ѽ��δɾ���˻�
        RsAccount account = accountService.selectNormalAccountByNo(payout.getPayAccount());
        // ������
        if (payout.getApAmount().compareTo(account.getBalanceUsable()) > 0) {
            throw new RuntimeException("�˻����㣡");
        }
        // ��Ӧ�ƻ��ۿ�
        RsPlanCtrl planCtrl = expensesPlanService.selectPlanCtrlByPlanNo(payout.getBusinessNo());
        planCtrl.setAvAmount(planCtrl.getAvAmount().subtract(payout.getApAmount()));

        //======== ��ʼ���� ===========
        //--------
        // ����������ϸ
        RsAccDetail accDetail = new RsAccDetail();
        accDetail.setAccountCode(payout.getPayAccount());
        accDetail.setAccountName(payout.getPayCompanyName());
        accDetail.setToAccountCode(payout.getRecAccount());
        accDetail.setToAccountName(payout.getRecCompanyName());
        accDetail.setToHsBankName(payout.getRecBankName());
        accDetail.setInoutFlag("0");// ֧��
        Date today = new Date();
        accDetail.setTradeDate(today);
        accDetail.setTradeAmt(payout.getApAmount());
        accDetail.setBalance(account.getBalanceUsable().subtract(accDetail.getTradeAmt()));
        accDetail.setLocalSerial(payout.getSerial());
        accDetail.setBankSerial(payout.getBankSerial());
        accDetail.setTradeType(TradeType.PLAN_PAYOUT.getCode());
        accDetail.setPlanCtrlNo(payout.getBusinessNo());
        accDetail.setRemark(payout.getRemark());
        //------------
        // �����˻���� �Ϳ������
        account.setBalance(account.getBalance().subtract(payout.getApAmount()));
        account.setBalanceUsable(account.getBalanceUsable().subtract(payout.getApAmount()));
        int rtnCnt = expensesPlanService.updatePlanCtrl(planCtrl)
                + accDetailService.insertAccDetail(accDetail)
                + accountService.updateRecord(account);
        return rtnCnt;
    }

}
