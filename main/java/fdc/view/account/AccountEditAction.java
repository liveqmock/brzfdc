package fdc.view.account;

import fdc.repository.model.RsAccount;
import fdc.service.account.AccountService;
import fdc.service.company.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import platform.common.utils.MessageUtil;
import platform.service.ToolsService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-8-30
 * Time: ����4:59
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class AccountEditAction {
    private static final Logger logger = LoggerFactory.getLogger(AccountEditAction.class);
    @ManagedProperty(value = "#{accountService}")
    private AccountService accountService;
    @ManagedProperty(value = "#{companyService}")
    private CompanyService companyService;
    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    private RsAccount account;
    private String rtnFlag;
    private List<SelectItem> companyList;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (!context.isPostback()) {
            Map<String, String> paramsmap = context.getExternalContext().getRequestParameterMap();
            String paramDoType = paramsmap.get("doType");
            String paramActPkID = paramsmap.get("actPKID");
            account = accountService.selectedRecordByPkid(paramActPkID);
        }
    }

    public String onBtnSaveClick() {
        try {
            accountService.updateRecord(account);
        } catch (Exception ex) {
            ex.printStackTrace();
            rtnFlag = "<script language='javascript'>rtnScript('false');</script>";
            return null;
        }
        rtnFlag = "<script language='javascript'>rtnScript('true');</script>";
        return null;
    }

    public List<SelectItem> getCompanyList() {
        companyList = companyService.selectItemsCompany(null);
        return companyList;
    }

    public CompanyService getCompanyService() {
        return companyService;
    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public void setCompanyList(List<SelectItem> companyList) {
        this.companyList = companyList;
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

    public ToolsService getToolsService() {
        return toolsService;
    }

    public void setToolsService(ToolsService toolsService) {
        this.toolsService = toolsService;
    }

    public String getRtnFlag() {
        return rtnFlag;
    }

    public void setRtnFlag(String rtnFlag) {
        this.rtnFlag = rtnFlag;
    }
}