package fdc.service;

import fdc.common.constant.*;
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
     * 处理合同收款
     * @param receive
     * @return
     */
    public int handleReceiveTrade(RsReceive receive) {
        // 查询未限制已监管未删除账户
        RsAccount account = accountService.selectNormalAccountByNo(receive.getAccountCode());
        if(account == null) {
            throw new RuntimeException("监管账号不存在！");
        }
        //--------
        // 新增交易明细
        RsAccDetail accDetail = new RsAccDetail();
        accDetail.setAccountCode(receive.getAccountCode());
        accDetail.setAccountName(receive.getCompanyName());
        accDetail.setToAccountCode(receive.getTradeAccCode());
        accDetail.setToAccountName(receive.getTradeAccName());
        accDetail.setToHsBankName(receive.getTradeBankName());
        accDetail.setInoutFlag(InOutFlag.IN.getCode());// 收入
        Date today = new Date();
        accDetail.setTradeDate(today);
        accDetail.setTradeAmt(receive.getApAmount());
        accDetail.setBalance(account.getBalance().add(accDetail.getTradeAmt()));
        accDetail.setLocalSerial(receive.getSerial());
        accDetail.setBankSerial(receive.getBankSerial());
        accDetail.setTradeType(TradeType.HOUSE_INCOME.getCode());
        accDetail.setContractNo(receive.getBusinessNo());
        accDetail.setRemark(receive.getRemark());
        //------------
        // 设置账户余额 和可用余额
        account.setBalance(account.getBalance().add(receive.getApAmount()));
        account.setBalanceUsable(account.getBalanceUsable().add(receive.getApAmount()));
        int rtnCnt = accDetailService.insertAccDetail(accDetail)
                + accountService.updateRecord(account);
        return rtnCnt;
    }
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
        accDetail.setBalance(account.getBalance().subtract(accDetail.getTradeAmt()));
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
