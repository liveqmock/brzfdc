package fdc.view.company;

import fdc.repository.model.RsFdccompany;
import fdc.service.company.FdccompanyService;
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
 * User: zhangxiaobo
 * Date: 11-8-24
 * Time: 上午10:58
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name = "fdcCompanyAction")
@ViewScoped
public class FDCCompanyAction {
    private Logger logger = LoggerFactory.getLogger(FDCCompanyAction.class);
    @ManagedProperty(value = "#{fdccompanyService}")
    private FdccompanyService fdccompanyService;
    private RsFdccompany fdccompany;
    private String companyName;
    private List<RsFdccompany> fdccompanyList;

    @PostConstruct
    public void init() {
        this.fdccompany = new RsFdccompany();
        this.fdccompanyList = fdccompanyService.qryAllRecords();
    }

    // 增
    public String insertNewFdccompany() {
        try {
            fdccompanyService.insertRsFdccompany(fdccompany);
            fdccompanyList.add(fdccompany);
        } catch (Exception e) {
            logger.error("新增数据失败，", e);
            MessageUtil.addError(e.getMessage());
            return null;
        }
        MessageUtil.addInfo("新增数据完成。");
        fdccompany = new RsFdccompany();
        return null;
    }

    // 改
    public String updateNewFdccompany() {
        try {
            fdccompanyService.updateRsFdccompany(fdccompany);
        } catch (Exception e) {
            logger.error("更新异常", e);
            MessageUtil.addError(e.getMessage());
            return null;
        }
        MessageUtil.addInfo("修改成功！");
        return null;
    }

    //  查
    public String qryFdccompanys() {
        try {
            fdccompanyList = fdccompanyService.qryRsFdccompanyByName(companyName);
        } catch (Exception e) {
            logger.error("查询异常", e);
            MessageUtil.addError(e.getMessage());
        }
        return null;
    }

    public String reset() {
        this.fdccompany = null;
        fdccompanyList = null;
        return null;
    }

    public RsFdccompany getFdccompany() {
        return fdccompany;
    }

    public void setFdccompany(RsFdccompany fdccompany) {
        this.fdccompany = fdccompany;
    }

    public FdccompanyService getFdccompanyService() {
        return fdccompanyService;
    }

    public void setFdccompanyService(FdccompanyService fdccompanyService) {
        this.fdccompanyService = fdccompanyService;
    }

    public List<RsFdccompany> getFdccompanyList() {
        return fdccompanyList;
    }

    public void setFdccompanyList(List<RsFdccompany> fdccompanyList) {
        this.fdccompanyList = fdccompanyList;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
