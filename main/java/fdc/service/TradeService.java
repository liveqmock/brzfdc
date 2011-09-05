package fdc.service;

import fdc.common.constant.AccountStatus;
import fdc.common.constant.LimitStatus;
import fdc.common.constant.LockAccStatus;
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
 * Time: 下午2:22
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
    @Autowired
    private LockedaccDetailService lockedaccDetailService;

    /**
     * 处理计划付款交易
     *
     * @param payout
     * @return
     */
    public int handlePayoutTrade(RsPayout payout) {
        // 查询未限制已监管未删除账户
        RsAccount account = accountService.selectNormalAccountByNo(payout.getPayAccount());
        // 检查余额
        if (payout.getApAmount().compareTo(account.getBalanceUsable()) > 0) {
            throw new RuntimeException("账户余额不足！");
        }
        // 相应计划扣款
        RsPlanCtrl planCtrl = expensesPlanService.selectPlanCtrlByPlanNo(payout.getBusinessNo());
        planCtrl.setAvAmount(planCtrl.getAvAmount().subtract(payout.getApAmount()));

        //======== 开始付款 ===========
        //--------
        // 新增交易明细
        RsAccDetail accDetail = new RsAccDetail();
        accDetail.setAccountCode(payout.getPayAccount());
        accDetail.setAccountName(payout.getPayCompanyName());
        accDetail.setToAccountCode(payout.getRecAccount());
        accDetail.setToAccountName(payout.getRecCompanyName());
        accDetail.setToHsBankName(payout.getRecBankName());
        accDetail.setInoutFlag("0");// 支出
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
        // 设置账户余额 和可用余额
        account.setBalance(account.getBalance().subtract(payout.getApAmount()));
        account.setBalanceUsable(account.getBalanceUsable().subtract(payout.getApAmount()));
        int rtnCnt = expensesPlanService.updatePlanCtrl(planCtrl)
                + accDetailService.insertAccDetail(accDetail)
                + accountService.updateRecord(account);
        return rtnCnt;
    }

    // 账户冻结
    @Transactional
    public int handleLockAccountByDetail(RsAccount rsAccount, RsLockedaccDetail rsLockedaccDetail) {
        rsLockedaccDetail.setAccountCode(rsAccount.getAccountCode());
        rsLockedaccDetail.setAccountName(rsAccount.getAccountName());
        rsLockedaccDetail.setBalance(rsAccount.getBalance());
        rsAccount.setBalanceLock(rsAccount.getBalanceLock().add(rsLockedaccDetail.getBalanceLock()));
        rsAccount.setBalanceUsable(rsAccount.getBalance().subtract(rsAccount.getBalanceLock()));
        if (rsLockedaccDetail.getBalanceLock().equals(rsAccount.getBalanceUsable())) {
            rsLockedaccDetail.setStatusFlag(LockAccStatus.FULL_LOCK.getCode());
        } else if (rsLockedaccDetail.getBalanceLock().compareTo(rsAccount.getBalanceUsable()) < 0) {
            rsLockedaccDetail.setStatusFlag(LockAccStatus.PART_LOCK.getCode());
        }
        return accountService.updateRecord(rsAccount) + lockedaccDetailService.insertRecord(rsLockedaccDetail);
    }

    // 账户解冻
    @Transactional
    public int handleUnlockAccountByDetail(RsAccount rsAccount, RsLockedaccDetail rsLockedaccDetail) {
        rsLockedaccDetail.setAccountCode(rsAccount.getAccountCode());
        rsLockedaccDetail.setAccountName(rsAccount.getAccountName());
        rsLockedaccDetail.setBalance(rsAccount.getBalance());
        rsAccount.setBalanceLock(rsAccount.getBalanceLock().subtract(rsLockedaccDetail.getBalanceLock()));
        rsAccount.setBalanceUsable(rsAccount.getBalance().subtract(rsAccount.getBalanceLock()));
        rsLockedaccDetail.setStatusFlag(LockAccStatus.UN_LOCK.getCode());
        return accountService.updateRecord(rsAccount) + lockedaccDetailService.insertRecord(rsLockedaccDetail);
    }
}
