package fdc.view.account;

import fdc.repository.model.RsAccount;
import fdc.service.account.AccountService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import platform.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-24
 * Time: 下午2:12
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@RequestScoped
public class AccountAction {
    private static final Logger logger = LoggerFactory.getLogger(AccountAction.class);
    @ManagedProperty(value = "#{accountService}")
    private AccountService accountService;
    private RsAccount account;
    private List<RsAccount> accountList;
    private String confirmAccountNo;

    @PostConstruct
    public void init() {
        this.account = new RsAccount();
        querySelectedRecords();
    }

    private void querySelectedRecords() {
        accountList = accountService.qryAllRecords();
    }

    private void querySelectedRecords(RsAccount act) {
        accountList = accountService.selectedRecordsByCondition(act.getPresellNo(),act.getCompanyId(),act.getAccountCode(),
                act.getAccountName());
    }

    public String onBtnQueryClick() {
        querySelectedRecords(account);
        return null;
    }

    // 增
    public String insertRecord() {
        try {
            if(StringUtils.isEmpty(account.getCompanyId())) {
              MessageUtil.addError("请选择房地产商！");
              return null;
            }

            if(!confirmAccountNo.equalsIgnoreCase(account.getAccountCode())) {
              MessageUtil.addError("两次输入的监管账户号不一致！");
              return null;
            }
            // 初始帐户余额均为可用
            account.setBalanceUsable(account.getBalance());
            accountService.insertRecord(account);
        } catch (Exception e) {
            logger.error("新增数据失败，", e);
            MessageUtil.addError(e.getMessage());
            return null;
        }
        MessageUtil.addInfo("新增数据完成。");
        querySelectedRecords();
        this.account = new RsAccount();
        return null;
    }

    public String reset() {
        this.account = new RsAccount();
        if (!accountList.isEmpty()) {
            accountList.clear();
        }
        return null;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public RsAccount getAccount() {
        return account;
    }

    public void setAccount(RsAccount account) {
        this.account = account;
    }

    public List<RsAccount> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<RsAccount> accountList) {
        this.accountList = accountList;
    }

    public String getConfirmAccountNo() {
        return confirmAccountNo;
    }

    public void setConfirmAccountNo(String confirmAccountNo) {
        this.confirmAccountNo = confirmAccountNo;
    }
}
