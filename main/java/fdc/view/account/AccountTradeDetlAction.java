package fdc.view.account;

import fdc.common.constant.TradeStatus;
import fdc.common.constant.TradeType;
import fdc.repository.model.RsAccDetail;
import fdc.service.account.AccountDetlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import platform.service.SystemService;
import platform.service.ToolsService;
import pub.platform.utils.BusinessDate;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.text.SimpleDateFormat;
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
    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    private Date beginDate;
    private Date endDate;
    private List<RsAccDetail> rsAccDetails;
    private RsAccDetail rsAccDetail;
    private String rtnFlag;
    private List<RsAccDetail> rsAccDetailsInit;
    private SimpleDateFormat sdf10 = new SimpleDateFormat("yyyy-MM-dd");
    private List<SelectItem> actDetlStatusOptions;
    @PostConstruct
    public void init() {
        actDetlStatusOptions = returnStatusOptions();
        rsAccDetail = new RsAccDetail();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        beginDate = cal.getTime();
        endDate = new Date();
        rsAccDetails = accountDetlService.selectedRecordsByTradeDate(
                sdf10.format(beginDate), sdf10.format(endDate));
        List<String> statusfalgs = new ArrayList<String>();
        statusfalgs.add(0, TradeStatus.CANCEL.getCode());
        statusfalgs.add(1,TradeStatus.BACK.getCode());
        //成功录入(包括被退回的)
        rsAccDetailsInit = accountDetlService.selectedRecordsForChk(TradeType.INTEREST.getCode(),statusfalgs);

    }

    private List<SelectItem> returnStatusOptions() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        SelectItem item;
        item = new SelectItem("", "全部");
        items.add(item);
        item = new SelectItem(TradeStatus.CANCEL.getCode(),TradeStatus.CANCEL.getTitle());
        items.add(item);
        item = new SelectItem(TradeStatus.BACK.getCode(),TradeStatus.BACK.getTitle());
        items.add(item);
        return items;
    }

    public void onBtnQueryClick() {
        rsAccDetails = accountDetlService.selectedRecordsByTradeDate(
                sdf10.format(beginDate), sdf10.format(endDate));
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

    public List<RsAccDetail> getRsAccDetailsInit() {
        return rsAccDetailsInit;
    }

    public void setRsAccDetailsInit(List<RsAccDetail> rsAccDetailsInit) {
        this.rsAccDetailsInit = rsAccDetailsInit;
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

    public ToolsService getToolsService() {
        return toolsService;
    }

    public void setToolsService(ToolsService toolsService) {
        this.toolsService = toolsService;
    }

    public List<SelectItem> getActDetlStatusOptions() {
        return actDetlStatusOptions;
    }

    public void setActDetlStatusOptions(List<SelectItem> actDetlStatusOptions) {
        this.actDetlStatusOptions = actDetlStatusOptions;
    }
}
