package fdc.view.account;

import fdc.common.constant.TradeStatus;
import fdc.common.constant.TradeType;
import fdc.repository.model.RsAccDetail;
import fdc.service.RsAccDetailService;
import fdc.service.account.AccountDetlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import platform.common.utils.MessageUtil;
import platform.service.SystemService;
import platform.service.ToolsService;
import pub.platform.utils.BusinessDate;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
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
@RequestScoped
public class AccountTradeDetlAction {
    private static final Logger logger = LoggerFactory.getLogger(AccountTradeDetlAction.class);
    @ManagedProperty(value = "#{accountDetlService}")
    private AccountDetlService accountDetlService;
    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    @ManagedProperty(value = "#{rsAccDetailService}")
    private RsAccDetailService accDetailService;
    private Date beginDate;
    private Date endDate;
    private String acctname;
    private String acctno;
    private List<RsAccDetail> rsAccDetails;
    private RsAccDetail rsAccDetail;
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
        rsAccDetails = accountDetlService.selectedRecordsByTradeDate(acctname, acctno
                , sdf10.format(beginDate), sdf10.format(endDate));
        List<String> statusfalgs = new ArrayList<String>();
        statusfalgs.add(0, TradeStatus.CANCEL.getCode());
        statusfalgs.add(1, TradeStatus.BACK.getCode());
        //成功录入(包括被退回的)
        rsAccDetailsInit = accountDetlService.selectedRecordsForChk(TradeType.INTEREST.getCode(), statusfalgs);

    }

    private List<SelectItem> returnStatusOptions() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        SelectItem item;
        item = new SelectItem("", "全部");
        items.add(item);
        item = new SelectItem(TradeStatus.CANCEL.getCode(), TradeStatus.CANCEL.getTitle());
        items.add(item);
        item = new SelectItem(TradeStatus.BACK.getCode(), TradeStatus.BACK.getTitle());
        items.add(item);
        return items;
    }

    public String onDelete() {
        FacesContext context = FacesContext.getCurrentInstance();
        String pkid = context.getExternalContext().getRequestParameterMap().get("pkid").toString();
        RsAccDetail record = accDetailService.selectAccDetailByPkid(pkid);
        record.setDeletedFlag("1");
        if(accDetailService.updateAccDetail(record) == 1) {
            MessageUtil.addInfo("账户"+record.getAccountCode() +"利息已删除！");
            init();
        }else {
            MessageUtil.addError("账户"+record.getAccountCode() +"利息删除失败！");
        }
        return null;
    }

    public void onBtnQueryClick() {
        rsAccDetails = accountDetlService.selectedRecordsByTradeDate(acctname, acctno,
                sdf10.format(beginDate), sdf10.format(endDate));
    }

    public String getAcctname() {
        return acctname;
    }

    public void setAcctname(String acctname) {
        this.acctname = acctname;
    }

    public String getAcctno() {
        return acctno;
    }

    public void setAcctno(String acctno) {
        this.acctno = acctno;
    }

    public List<RsAccDetail> getRsAccDetailsInit() {
        return rsAccDetailsInit;
    }

    public void setRsAccDetailsInit(List<RsAccDetail> rsAccDetailsInit) {
        this.rsAccDetailsInit = rsAccDetailsInit;
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

    public RsAccDetailService getAccDetailService() {
        return accDetailService;
    }

    public void setAccDetailService(RsAccDetailService accDetailService) {
        this.accDetailService = accDetailService;
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
