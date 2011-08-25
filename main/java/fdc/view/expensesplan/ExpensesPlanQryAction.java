package fdc.view.expensesplan;

import fdc.repository.model.RsPlanCtrl;
import fdc.service.expensesplan.ExpensesPlanService;
import platform.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-8-10
 * Time: 下午3:38
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class ExpensesPlanQryAction implements Serializable {
    private RsPlanCtrl rsPlanCtrl;
    private List<RsPlanCtrl> rsPlanCtrlList;

   // @ManagedProperty(value = "#{platformService}")
    //private PlatformService platformService;
    @ManagedProperty(value = "#{expensesPlanService}")
    private ExpensesPlanService expensesPlanService;

    @PostConstruct
    public void init() {
        rsPlanCtrl = new RsPlanCtrl();
         rsPlanCtrlList = expensesPlanService.selectPlanList();
      //  platformService.selectBranchTellers("9999");
    }

    public String onQuery() {
        rsPlanCtrlList = expensesPlanService.selectPlanListByFields(rsPlanCtrl.getCompanyName(),
                rsPlanCtrl.getAccountCode(), rsPlanCtrl.getPayContractNo());
        if(rsPlanCtrlList.isEmpty()) {
            MessageUtil.addWarn("没有查询到符合条件的数据记录！");
        }
        return null;
    }

    public void onPrint() {
        rsPlanCtrlList = expensesPlanService.selectPlanList();
    }

    //======================================================================


    public RsPlanCtrl getRsPlanCtrl() {
        return rsPlanCtrl;
    }

    public void setRsPlanCtrl(RsPlanCtrl rsPlanCtrl) {
        this.rsPlanCtrl = rsPlanCtrl;
    }

    public List<RsPlanCtrl> getRsPlanCtrlList() {
        return rsPlanCtrlList;
    }

    public void setRsPlanCtrlList(List<RsPlanCtrl> rsPlanCtrlList) {
        this.rsPlanCtrlList = rsPlanCtrlList;
    }

   /* public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
    } */

    public void setExpensesPlanService(ExpensesPlanService expensesPlanService) {
        this.expensesPlanService = expensesPlanService;
    }
}
