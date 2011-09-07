package fdc.view.account;

import fdc.common.constant.TradeStatus;
import fdc.common.constant.WorkResult;
import fdc.repository.model.RsAccDetail;
import fdc.repository.model.RsReceive;
import fdc.service.account.AccountDetlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import platform.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-9-7
 * Time: 上午7:22
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class InterestBookChkAction {
    private static final Logger logger = LoggerFactory.getLogger(InterestBookChkAction.class);
    @ManagedProperty(value = "#{accountDetlService}")
    private AccountDetlService accountDetlService;

    private List<RsAccDetail> rsAccDetails;
    private RsAccDetail[] selectedRecords;
    private List<RsAccDetail> rsAccDetailsChk;


    @PostConstruct
    public void init() {
        rsAccDetails = accountDetlService.selectedRecordsForChk("04", "0");
        rsAccDetailsChk = accountDetlService.selectedRecordsForChk("04","2");
    }

    public String onCheck() {
        if (selectedRecords == null || selectedRecords.length == 0) {
            MessageUtil.addWarn("至少选择一笔数据记录！");
            return null;
        }
        try {
            for (RsAccDetail record : selectedRecords) {
                record.setStatusFlag(TradeStatus.CHECKED.getCode());
                if (accountDetlService.updateSelectedRecord(record) != 1) {
                    throw new RuntimeException("复核失败！");
                }
            }
            MessageUtil.addInfo("复核成功！");
            init();
        } catch (Exception e) {
            MessageUtil.addError("操作失败." + e.getMessage());
        }
        return null;
    }

    public String onBack() {
        return null;
    }

    public List<RsAccDetail> getRsAccDetailsChk() {
        return rsAccDetailsChk;
    }

    public void setRsAccDetailsChk(List<RsAccDetail> rsAccDetailsChk) {
        this.rsAccDetailsChk = rsAccDetailsChk;
    }

    public AccountDetlService getAccountDetlService() {
        return accountDetlService;
    }

    public void setAccountDetlService(AccountDetlService accountDetlService) {
        this.accountDetlService = accountDetlService;
    }

    public List<RsAccDetail> getRsAccDetails() {
        return rsAccDetails;
    }

    public void setRsAccDetails(List<RsAccDetail> rsAccDetails) {
        this.rsAccDetails = rsAccDetails;
    }

    public RsAccDetail[] getSelectedRecords() {
        return selectedRecords;
    }

    public void setSelectedRecords(RsAccDetail[] selectedRecords) {
        this.selectedRecords = selectedRecords;
    }
}
