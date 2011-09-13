package fdc.view.payout;

import fdc.common.constant.LimitStatus;
import fdc.common.constant.RefundStatus;
import fdc.common.constant.WorkResult;
import fdc.repository.model.RsAccount;
import fdc.repository.model.RsPayout;
import fdc.repository.model.RsPlanCtrl;
import fdc.service.PayoutService;
import fdc.service.account.AccountService;
import fdc.service.expensesplan.ExpensesPlanService;
import org.apache.commons.lang.StringUtils;
import org.primefaces.component.commandbutton.CommandButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import platform.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-30
 * Time: ����3:41
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class PayoutAction {
    private Logger logger = LoggerFactory.getLogger(PayoutAction.class);

    private RsPayout rsPayout;
    @ManagedProperty(value = "#{payoutService}")
    private PayoutService payoutService;
    @ManagedProperty(value = "#{expensesPlanService}")
    private ExpensesPlanService expensesPlanService;
    @ManagedProperty(value = "#{accountService}")
    private AccountService accountService;
    private List<RsPayout> rsPayoutList;
    private List<RsPayout> chkPayoutList;
    private List<RsPayout> passPayoutList;
    private List<RsPayout> refusePayoutList;
    private List<RsPlanCtrl> rsPlanCtrlList;

    private RsPayout selectedRecord;
    private RsPayout[] selectedRecords;
    private WorkResult workResult = WorkResult.CREATE;
    private RefundStatus statusFlag = RefundStatus.ACCOUNT_SUCCESS;
    private RsPlanCtrl planCtrl;

    @PostConstruct
    public void init() {
        rsPayout = new RsPayout();
        planCtrl = new RsPlanCtrl();
        rsPayoutList = new ArrayList<RsPayout>();
        if (!initPayout()) {
            initTabList();
        }
    }

    private boolean initPayout() {
        FacesContext context = FacesContext.getCurrentInstance();
        String pkid = (String) context.getExternalContext().getRequestParameterMap().get("pkid");
        String action = (String) context.getExternalContext().getRequestParameterMap().get("action");

        if (!StringUtils.isEmpty(pkid) && "insert".equalsIgnoreCase(action)) {
            planCtrl = expensesPlanService.selectPlanCtrlByPkid(pkid);
            rsPayout.setBusinessNo(planCtrl.getPlanCtrlNo());
            rsPayout.setRecAccount(planCtrl.getToAccountCode());
            rsPayout.setRecCompanyName(planCtrl.getToAccountName());
            // TODO  ���д���
            rsPayout.setRecBankCode("313");
            rsPayout.setRecBankName(planCtrl.getToHsBankName());
            rsPayout.setPayCompanyName(planCtrl.getCompanyName());
            rsPayout.setPayAccount(planCtrl.getAccountCode());

            return true;
        } else if (!StringUtils.isEmpty(pkid) && "query".equalsIgnoreCase(action)) {
            rsPayout = payoutService.selectPayoutByPkid(pkid);
            planCtrl = expensesPlanService.selectPlanCtrlByPlanNo(rsPayout.getBusinessNo());
        }
        return false;
    }

    public void initTabList() {
        chkPayoutList = payoutService.selectRecordsByWorkResult(WorkResult.CREATE.getCode());
        passPayoutList = payoutService.selectRecordsByWorkResult(WorkResult.PASS.getCode());
        refusePayoutList = payoutService.selectRecordsByWorkResult(WorkResult.NOTPASS.getCode());
        rsPlanCtrlList = expensesPlanService.selectPlanList();
    }

    public String onSave() {
        RsAccount account = accountService.selectCanPayAccountByNo(rsPayout.getPayAccount());
        if(account.getLimitFlag().equalsIgnoreCase(LimitStatus.LIMITED.getCode())){
            MessageUtil.addError("���˻��ѱ����Ƹ��");
            return null;
        }
        if (rsPayout.getPlAmount().compareTo(planCtrl.getAvAmount()) > 0) {
            MessageUtil.addError("������ô��ڿ��ý�");
            return null;
        }
        try {
            rsPayout.setApAmount(rsPayout.getPlAmount());
            if (payoutService.insertRsPayout(rsPayout) == 1) {
                MessageUtil.addInfo("�����ÿ�ɹ���");
                UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
                CommandButton saveBtn = (CommandButton) viewRoot.findComponent("form:saveBtn");
                saveBtn.setDisabled(true);
            } else {
                MessageUtil.addError("�����ÿ�ʧ�ܣ�");
            }
        } catch (Exception e) {
            logger.error("�����ÿ�ʧ�ܣ������������ݣ�", e.getMessage());
            MessageUtil.addError("�����ÿ�ʧ�ܣ������������ݣ�");
        }
        return null;
    }

    public String onCheck() {
        if (selectedRecords == null || selectedRecords.length == 0) {
            MessageUtil.addWarn("������ѡ��һ�ʼ�¼��");
        } else {
            try {
                payoutService.updateRsPayoutsToWorkResult(selectedRecords, WorkResult.PASS.getCode());
            } catch (Exception e) {
                logger.error("����ʧ��." + e.getMessage());
                MessageUtil.addError("����ʧ��." + e.getMessage());
                return null;
            }
            MessageUtil.addInfo("���˳ɹ�!");
            initTabList();
        }
        return null;
    }

    public String onRefuse() {
        if (selectedRecords == null || selectedRecords.length == 0) {
            MessageUtil.addWarn("������ѡ��һ�ʼ�¼��");
        } else {
            try {
                payoutService.updateRsPayoutsToWorkResult(selectedRecords, WorkResult.NOTPASS.getCode());
            } catch (Exception e) {
                logger.error("�˻�ʧ��." + e.getMessage());
                MessageUtil.addError("�˻�ʧ��." + e.getMessage());
                return null;
            }
            MessageUtil.addInfo("�˻����!");
            initTabList();
        }
        return null;
    }

    /* public void onTabChange(TabChangeEvent event) {
         initTabList();
      }*/

    //=========================================

    public RsPayout getRsPayout() {
        return rsPayout;
    }

    public void setRsPayout(RsPayout rsPayout) {
        this.rsPayout = rsPayout;
    }

    public PayoutService getPayoutService() {
        return payoutService;
    }

    public void setPayoutService(PayoutService payoutService) {
        this.payoutService = payoutService;
    }

    public List<RsPayout> getRsPayoutList() {
        return rsPayoutList;
    }

    public void setRsPayoutList(List<RsPayout> rsPayoutList) {
        this.rsPayoutList = rsPayoutList;
    }

    public List<RsPayout> getChkPayoutList() {
        return chkPayoutList;
    }

    public void setChkPayoutList(List<RsPayout> chkPayoutList) {
        this.chkPayoutList = chkPayoutList;
    }

    public RsPayout getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(RsPayout selectedRecord) {
        this.selectedRecord = selectedRecord;
    }

    public WorkResult getWorkResult() {
        return workResult;
    }

    public void setWorkResult(WorkResult workResult) {
        this.workResult = workResult;
    }

    public RsPayout[] getSelectedRecords() {
        return selectedRecords;
    }

    public void setSelectedRecords(RsPayout[] selectedRecords) {
        this.selectedRecords = selectedRecords;
    }

    public List<RsPayout> getPassPayoutList() {
        return passPayoutList;
    }

    public void setPassPayoutList(List<RsPayout> passPayoutList) {
        this.passPayoutList = passPayoutList;
    }

    public List<RsPayout> getRefusePayoutList() {
        return refusePayoutList;
    }

    public void setRefusePayoutList(List<RsPayout> refusePayoutList) {
        this.refusePayoutList = refusePayoutList;
    }

    public ExpensesPlanService getExpensesPlanService() {
        return expensesPlanService;
    }

    public void setExpensesPlanService(ExpensesPlanService expensesPlanService) {
        this.expensesPlanService = expensesPlanService;
    }

    public List<RsPlanCtrl> getRsPlanCtrlList() {
        return rsPlanCtrlList;
    }

    public void setRsPlanCtrlList(List<RsPlanCtrl> rsPlanCtrlList) {
        this.rsPlanCtrlList = rsPlanCtrlList;
    }

    public RsPlanCtrl getPlanCtrl() {
        return planCtrl;
    }

    public void setPlanCtrl(RsPlanCtrl planCtrl) {
        this.planCtrl = planCtrl;
    }

    public RefundStatus getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(RefundStatus statusFlag) {
        this.statusFlag = statusFlag;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}
