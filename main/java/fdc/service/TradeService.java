package fdc.service;

import fdc.common.constant.*;
import fdc.repository.model.*;
import fdc.service.account.AccountService;
import fdc.service.expensesplan.ExpensesPlanService;
import org.apache.ibatis.migration.Change;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import platform.common.utils.BeanHelper;
import platform.service.SystemService;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
    @Autowired
    private ClientBiService clientBiService;
    @Autowired
    private RefundService refundService;
    private SimpleDateFormat sdf10 = new SimpleDateFormat("yyyy-MM-dd");

    // R-冲正 D-退票
    @Transactional
    public int handleCancelAccDetail(RsAccDetail record, ChangeFlag changeFlag) throws IOException {
        // 查询未限制已监管未删除账户
        RsAccount account = accountService.selectNormalAccountByNo(record.getAccountCode());
        if (account == null) {
            throw new RuntimeException("监管账号不存在！");
        }

        //-----------------------------------------------
        // 新增交易明细
        RsAccDetail accDetail = new RsAccDetail();
        accDetail.setAccountCode(record.getAccountCode());
        accDetail.setAccountName(record.getAccountName());
        accDetail.setToAccountCode(record.getToAccountCode());
        accDetail.setToAccountName(record.getToAccountName());
        accDetail.setToHsBankName(record.getToHsBankName());
        accDetail.setTradeType(TradeType.OTHERS.getCode());
        accDetail.setTradeAmt(record.getTradeAmt());
        accDetail.setTradeDate(sdf10.format(new Date()));
        accDetail.setContractNo(record.getContractNo());
        accDetail.setChangeFlag(changeFlag.getCode());      // 标记: R-冲正 D-退票
        record.setChangeFlag(changeFlag.getCode());
        accDetail.setPlanCtrlNo(record.getPlanCtrlNo());
        accDetail.setStatusFlag(TradeStatus.SUCCESS.getCode());
        if (InOutFlag.IN.getCode().equalsIgnoreCase(record.getInoutFlag())) {
            accDetail.setInoutFlag(InOutFlag.OUT.getCode());
            accDetail.setBalance(record.getBalance().subtract(record.getTradeAmt()));
            account.setBalance(account.getBalance().subtract(record.getTradeAmt()));
            account.setBalanceUsable(account.getBalanceUsable().subtract(record.getTradeAmt()));
        } else {
            accDetail.setInoutFlag(InOutFlag.IN.getCode());
            accDetail.setBalance(record.getBalance().add(record.getTradeAmt()));
            account.setBalance(account.getBalance().add(record.getTradeAmt()));
            account.setBalanceUsable(account.getBalanceUsable().add(record.getTradeAmt()));
        }

        int rtnCnt = accDetailService.insertAccDetail(accDetail) + accDetailService.updateAccDetail(record)
                + accountService.updateRecord(account);
        if (ChangeFlag.CANCEL.getCode().equalsIgnoreCase(changeFlag.getCode())) {  // 冲正
            if (clientBiService.sendAccDetailCancel(accDetail) == -1) {
                throw new RuntimeException("发送冲正交易记录失败!");
            }
        }else if(ChangeFlag.BACK.getCode().equalsIgnoreCase(changeFlag.getCode())) {   // 退票
             if (clientBiService.sendAccDetailBack(accDetail) == -1) {
                throw new RuntimeException("发送退票交易记录失败!");
            }
        }
        return rtnCnt;
    }

    /**
     * 处理合同收款
     *
     * @param receive
     * @return
     */
    @Transactional
    public int handleReceiveTrade(RsReceive receive) {
        // 查询未限制已监管未删除账户
        RsAccount account = accountService.selectNormalAccountByNo(receive.getAccountCode());
        if (account == null) {
            throw new RuntimeException("监管账号不存在！");
        }
        //------------------------------------------------
        // 新增交易明细
        RsAccDetail accDetail = new RsAccDetail();
        accDetail.setAccountCode(receive.getAccountCode());
        accDetail.setAccountName(receive.getCompanyName());
        accDetail.setToAccountCode(receive.getTradeAccCode());
        accDetail.setToAccountName(receive.getTradeAccName());
        accDetail.setToHsBankName(receive.getTradeBankName());
        accDetail.setInoutFlag(InOutFlag.IN.getCode());// 收入
        accDetail.setTradeDate(sdf10.format(new Date()));
        accDetail.setTradeAmt(receive.getApAmount());
        accDetail.setBalance(account.getBalance().add(accDetail.getTradeAmt()));
        accDetail.setLocalSerial(receive.getSerial());
        accDetail.setBankSerial(receive.getBankSerial());
        accDetail.setTradeType(TradeType.HOUSE_INCOME.getCode());
        accDetail.setContractNo(receive.getBusinessNo());
        accDetail.setRemark(receive.getRemark());
        accDetail.setStatusFlag(TradeStatus.SUCCESS.getCode());
        //----------------------------------------------------
        // 设置账户余额 和可用余额
        account.setBalance(account.getBalance().add(receive.getApAmount()));
        account.setBalanceUsable(account.getBalanceUsable().add(receive.getApAmount()));
        int rtnCnt = accDetailService.insertAccDetail(accDetail)
                + accountService.updateRecord(account);
        return rtnCnt;
    }

    /**
     * 处理合同退款交易
     *
     * @param refund
     * @return
     */
    @Transactional
    public int handleRefundTrade(RsRefund refund) {
        // 查询未限制已监管未删除账户
        RsAccount account = accountService.selectNormalAccountByNo(refund.getPayAccount());
        // 检查余额
        if (refund.getApAmount().compareTo(account.getBalanceUsable()) > 0) {
            throw new RuntimeException("账户余额不足！");
        }
        //======== 开始付款 ===========
        //--------------------------------------------------
        // 新增交易明细
        RsAccDetail accDetail = new RsAccDetail();
        accDetail.setAccountCode(refund.getPayAccount());
        accDetail.setAccountName(refund.getPayCompanyName());
        accDetail.setToAccountCode(refund.getRecAccount());
        accDetail.setToAccountName(refund.getRecCompanyName());
        accDetail.setToHsBankName(refund.getRecBankName());
        accDetail.setInoutFlag(InOutFlag.OUT.getCode());// 支出
        accDetail.setTradeDate(sdf10.format(new Date()));
        accDetail.setTradeAmt(refund.getApAmount());
        accDetail.setBalance(account.getBalance().subtract(accDetail.getTradeAmt()));
        accDetail.setLocalSerial(refund.getSerial());
        accDetail.setBankSerial(refund.getBankSerial());
        accDetail.setTradeType(TradeType.TRANS_BACK.getCode());
        accDetail.setContractNo(refund.getBusinessNo());
        accDetail.setRemark(refund.getRemark());
        accDetail.setStatusFlag(TradeStatus.SUCCESS.getCode());
        //-----------------------------------------------------
        // 设置账户余额 和可用余额
        account.setBalance(account.getBalance().subtract(refund.getApAmount()));
        account.setBalanceUsable(account.getBalanceUsable().subtract(refund.getApAmount()));
        int rtnCnt = accDetailService.insertAccDetail(accDetail)
                + accountService.updateRecord(account) + refundService.updateRecord(refund);
        return rtnCnt;
    }

    /**
     * 处理计划付款交易
     *
     * @param payout
     * @return
     */
    @Transactional
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
        //--------------------------------------------------
        // 新增交易明细
        RsAccDetail accDetail = new RsAccDetail();
        accDetail.setAccountCode(payout.getPayAccount());
        accDetail.setAccountName(payout.getPayCompanyName());
        accDetail.setToAccountCode(payout.getRecAccount());
        accDetail.setToAccountName(payout.getRecCompanyName());
        accDetail.setToHsBankName(payout.getRecBankName());
        accDetail.setInoutFlag(InOutFlag.OUT.getCode());// 支出
        accDetail.setTradeDate(sdf10.format(new Date()));
        accDetail.setTradeAmt(payout.getApAmount());
        accDetail.setBalance(account.getBalance().subtract(accDetail.getTradeAmt()));
        accDetail.setLocalSerial(payout.getSerial());
        accDetail.setBankSerial(payout.getBankSerial());
        accDetail.setTradeType(TradeType.PLAN_PAYOUT.getCode());
        accDetail.setPlanCtrlNo(payout.getBusinessNo());
        accDetail.setRemark(payout.getRemark());
        accDetail.setStatusFlag(TradeStatus.SUCCESS.getCode());
        //-----------------------------------------------------
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
