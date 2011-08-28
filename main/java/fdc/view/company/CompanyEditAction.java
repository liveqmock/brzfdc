package fdc.view.company;

import fdc.repository.model.RsFdccompany;
import fdc.service.company.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import platform.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-8-28
 * Time: 下午8:09
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class CompanyEditAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(CompanyEditAction.class);
    @ManagedProperty(value = "#{companyService}")
    private CompanyService companyService;
    private RsFdccompany fdccompany;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (!context.isPostback()) {
            Map<String, String> paramsmap = context.getExternalContext().getRequestParameterMap();
            String paramDoType = paramsmap.get("doType");
            String paramCmpnyID = paramsmap.get("cmpnyPKID");
            fdccompany = companyService.selectedReocrdByPK(paramCmpnyID);
        }
    }

    public String onBtnSaveClick() {
        try {
            companyService.updateRsFdccompany(fdccompany);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        MessageUtil.addInfo("更新成功!");
        return null;
    }

    public CompanyService getCompanyService() {
        return companyService;
    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public RsFdccompany getFdccompany() {
        return fdccompany;
    }

    public void setFdccompany(RsFdccompany fdccompany) {
        this.fdccompany = fdccompany;
    }
}
