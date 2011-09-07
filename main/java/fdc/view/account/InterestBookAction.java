package fdc.view.account;

import fdc.common.constant.SendFlag;
import fdc.common.constant.TradeStatus;
import fdc.common.constant.TradeType;
import fdc.repository.model.RsAccDetail;
import fdc.repository.model.RsAccount;
import fdc.service.account.AccountDetlService;
import fdc.service.account.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import platform.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-9-7
 * Time: 下午2:28
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class InterestBookAction {
    private static final Logger logger = LoggerFactory.getLogger(InterestBookAction.class);
    @ManagedProperty(value = "#{accountDetlService}")
    private AccountDetlService accountDetlService;
    @ManagedProperty(value = "#{accountService}")
    private AccountService accountService;
    //已复核数据 待入账
    private List<RsAccDetail> rsAccDetailsChk;
    private RsAccDetail[] selectedRecords;
    //待发送数据
    private List<RsAccDetail> rsAccDetailsSend;
    private RsAccDetail[] selectedRecordsSend;
    //已发送数据
    private List<RsAccDetail> rsAccDetailsSended;
    private RsAccDetail[] selectedRecordsSended;

    @PostConstruct
    public void init() {
        rsAccDetailsChk = accountDetlService.selectedRecordsForChk(TradeType.INTEREST.getCode(), TradeStatus.CHECKED.getCode());
        rsAccDetailsSend = accountDetlService.selectedRecordsForSend(TradeType.INTEREST.getCode(), TradeStatus.SUCCESS.getCode()
                , SendFlag.UN_SEND.getCode());
        rsAccDetailsSended = accountDetlService.selectedRecordsForSend(TradeType.INTEREST.getCode(), TradeStatus.CHECKED.getCode()
                , SendFlag.SENT.getCode());
    }

    /**
     * 监管账户利息入账
     */
    public String onBook() {
        if (selectedRecords == null || selectedRecords.length == 0) {
            MessageUtil.addWarn("至少选择一笔数据记录！");
            return null;
        }
        try {
            for (RsAccDetail record : selectedRecords) {
                RsAccount rsAccount = new RsAccount();
                rsAccount.setAccountCode(record.getAccountCode());
                rsAccount.setCompanyId(record.getCompanyId());
                //将发生额添加到账户余额字段中
                rsAccount.setBalance(record.getTradeAmt());
                //更新监管账户余额
                if (accountService.updateRecordBalance(rsAccount) == 1) {
                    record.setStatusFlag(TradeStatus.SUCCESS.getCode());
                    if (accountDetlService.updateSelectedRecord(record) != 1) {
                        throw new RuntimeException("入账失败！");
                    }
                } else {
                    throw new RuntimeException("入账失败！");
                }
            }
            MessageUtil.addInfo("入账成功！");
            init();
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.addError("操作失败." + e.getMessage());
        }
        return null;
    }

    public String onSend() {
        return null;
    }

    public AccountDetlService getAccountDetlService() {
        return accountDetlService;
    }

    public void setAccountDetlService(AccountDetlService accountDetlService) {
        this.accountDetlService = accountDetlService;
    }

    public List<RsAccDetail> getRsAccDetailsChk() {
        return rsAccDetailsChk;
    }

    public void setRsAccDetailsChk(List<RsAccDetail> rsAccDetailsChk) {
        this.rsAccDetailsChk = rsAccDetailsChk;
    }

    public RsAccDetail[] getSelectedRecords() {
        return selectedRecords;
    }

    public void setSelectedRecords(RsAccDetail[] selectedRecords) {
        this.selectedRecords = selectedRecords;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public List<RsAccDetail> getRsAccDetailsSend() {
        return rsAccDetailsSend;
    }

    public void setRsAccDetailsSend(List<RsAccDetail> rsAccDetailsSend) {
        this.rsAccDetailsSend = rsAccDetailsSend;
    }

    public RsAccDetail[] getSelectedRecordsSend() {
        return selectedRecordsSend;
    }

    public void setSelectedRecordsSend(RsAccDetail[] selectedRecordsSend) {
        this.selectedRecordsSend = selectedRecordsSend;
    }

    public List<RsAccDetail> getRsAccDetailsSended() {
        return rsAccDetailsSended;
    }

    public void setRsAccDetailsSended(List<RsAccDetail> rsAccDetailsSended) {
        this.rsAccDetailsSended = rsAccDetailsSended;
    }

    public RsAccDetail[] getSelectedRecordsSended() {
        return selectedRecordsSended;
    }

    public void setSelectedRecordsSended(RsAccDetail[] selectedRecordsSended) {
        this.selectedRecordsSended = selectedRecordsSended;
    }
}

