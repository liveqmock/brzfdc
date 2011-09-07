package fdc.view.account;

import fdc.common.constant.TradeType;
import fdc.repository.model.RsAccDetail;
import fdc.service.account.AccountDetlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import platform.service.SystemService;
import pub.platform.utils.BusinessDate;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-9-6
 * Time: 上午10:07
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class AccountTradeDetlAction {
    private static final Logger logger = LoggerFactory.getLogger(AccountTradeDetlAction.class);
    @ManagedProperty(value = "#{accountDetlService}")
    private AccountDetlService accountDetlService;
    private Date beginDate;
    private Date endDate;
    private List<RsAccDetail> rsAccDetails;
    private RsAccDetail rsAccDetail;
    private String rtnFlag;
    private List<RsAccDetail> rsAccDetailsChk;

    @PostConstruct
    public void init() {
        rsAccDetail = new RsAccDetail();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        beginDate = cal.getTime();
        endDate = new Date();
        rsAccDetails = accountDetlService.selectedRecordsByTradeDate(beginDate, endDate);
        rsAccDetailsChk = accountDetlService.selectedRecordsForChk("04", "0");
    }

    public void onBtnQueryClick() {
        rsAccDetails = accountDetlService.selectedRecordsByTradeDate(beginDate, endDate);
    }

    //利息录入
    public String onBtnSaveClick() {
        try {
            String createBy = SystemService.getOperatorManager().getOperatorId();
            FacesContext context = FacesContext.getCurrentInstance();
            String accountno = (String) context.getExternalContext().getRequestParameterMap().get("acctno");
            String companyid = (String) context.getExternalContext().getRequestParameterMap().get("companyid");
            rsAccDetail.setAccountCode(accountno);
            rsAccDetail.setCompanyId(companyid);
            rsAccDetail.setStatusFlag("0");
            rsAccDetail.setInoutFlag("1");
            rsAccDetail.setTradeType(TradeType.INTEREST.getCode());
            rsAccDetail.setCreatedBy(createBy);
            rsAccDetail.setCreatedDate(new Date());
            accountDetlService.insertSelectedRecord(rsAccDetail);
        } catch (Exception ex) {
            ex.printStackTrace();
            rtnFlag = "<script language='javascript'>rtnScript('false');</script>";
            return null;
        }
        rtnFlag = "<script language='javascript'>rtnScript('true');</script>";
        return null;
    }

    public List<RsAccDetail> getRsAccDetailsChk() {
        return rsAccDetailsChk;
    }

    public void setRsAccDetailsChk(List<RsAccDetail> rsAccDetailsChk) {
        this.rsAccDetailsChk = rsAccDetailsChk;
    }

    public String getRtnFlag() {
        return rtnFlag;
    }

    public void setRtnFlag(String rtnFlag) {
        this.rtnFlag = rtnFlag;
    }

    public RsAccDetail getRsAccDetail() {
        return rsAccDetail;
    }

    public void setRsAccDetail(RsAccDetail rsAccDetail) {
        this.rsAccDetail = rsAccDetail;
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
