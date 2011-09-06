package fdc.view.account;

import fdc.repository.model.RsAccDetail;
import fdc.service.account.AccountDetlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-9-6
 * Time: ÉÏÎç10:07
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class AccountTradeDetlAction {
    private static final Logger logger = LoggerFactory.getLogger(AccountTradeDetlAction.class);
    @ManagedProperty(value="#{accountDetlService}")
    private AccountDetlService accountDetlService;
    private Date beginDate;
    private Date endDate;
    private List<RsAccDetail> rsAccDetails;
//    private Map<String,String> tradeTypeMap;
//    private

    @PostConstruct
    public void init() {
//        tradeTypeMap = FlagStatusForMap.getTradeTypeMap();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH,1);
        beginDate = cal.getTime();
        endDate = new Date();
        rsAccDetails = accountDetlService.selectedRecordsByTradeDate(beginDate,endDate);
    }

    public void onBtnQueryClick() {
        rsAccDetails = accountDetlService.selectedRecordsByTradeDate(beginDate,endDate);
    }

    public AccountDetlService getAccountDetlService() {
        return accountDetlService;
    }

    public void setAccountDetlService(AccountDetlService accountDetlService) {
        this.accountDetlService = accountDetlService;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<RsAccDetail> getRsAccDetails() {
        return rsAccDetails;
    }

    public void setRsAccDetails(List<RsAccDetail> rsAccDetails) {
        this.rsAccDetails = rsAccDetails;
    }
}
