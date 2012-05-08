package fdc.view.cbus;

import fdc.common.constant.RefundStatus;
import fdc.common.constant.WorkResult;
import fdc.repository.model.RsPayout;
import fdc.repository.model.RsPlanCtrl;
import fdc.service.CbusPayoutService;
import fdc.service.ClientBiService;
import fdc.service.PayoutService;
import fdc.service.expensesplan.ExpensesPlanService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import platform.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-30
 * Time: 下午3:41
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class CbusPayoutExecAction {
    private Logger logger = LoggerFactory.getLogger(CbusPayoutExecAction.class);

    private RsPayout rsPayout;
    @ManagedProperty(value = "#{cbusPayoutService}")
    private CbusPayoutService cbusPayoutService;
    @ManagedProperty(value = "#{clientBiService}")
    private ClientBiService clientBiService;
    @ManagedProperty(value = "#{payoutService}")
    private PayoutService payoutService;
    @ManagedProperty(value = "#{expensesPlanService}")
    private ExpensesPlanService expensesPlanService;

    private List<RsPayout> passPayoutList;
    private List<RsPayout> payOverList;
    private List<RsPayout> sendOverList;
    private RsPayout selectedRecord;
    private RsPayout[] selectedRecords;
    private RsPayout[] toSendRecords;
    private WorkResult workResult = WorkResult.CREATE;
    private RefundStatus statusFlag = RefundStatus.ACCOUNT_SUCCESS;
    private RsPlanCtrl planCtrl;

    @PostConstruct
    public void init() {
        rsPayout = new RsPayout();
        passPayoutList = cbusPayoutService.selectRecordsByWorkResult(WorkResult.PASS.getCode());
        payOverList = cbusPayoutService.selectRecordsByWorkResult(WorkResult.COMMIT.getCode());
        sendOverList = cbusPayoutService.selectRecordsByWorkResult(WorkResult.SENT.getCode());

        FacesContext context = FacesContext.getCurrentInstance();
        String pkid = (String) context.getExternalContext().getRequestParameterMap().get("pkid");
        String action = (String) context.getExternalContext().getRequestParameterMap().get("action");

        if (!StringUtils.isEmpty(pkid) && "act".equalsIgnoreCase(action)) {
            rsPayout = payoutService.selectPayoutByPkid(pkid);
            planCtrl = expensesPlanService.selectPlanCtrlByPlanNo(rsPayout.getBusinessNo());
        }
    }

    public String onExecute() {
        try {
            if (cbusPayoutService.updateRsPayoutToExec(rsPayout) == -1) {
                throw new RuntimeException("【记录更新失败】付款监管账号：" + rsPayout.getPayAccount());
            }
        } catch (Exception e) {
            logger.error("操作失败." + e.getMessage());
            MessageUtil.addError("操作失败." + e.getMessage());
            return null;
        }
        MessageUtil.addInfo("入账完成!");
        init();
        return null;
    }

    public String onAllExecute() {
        if (passPayoutList.isEmpty()) {
            MessageUtil.addWarn("可入账记录为空！");
        } else {
            try {
                for (RsPayout record : passPayoutList) {
                    if (cbusPayoutService.updateRsPayoutToExec(record) == -1) {
                        throw new RuntimeException("【记录更新失败】付款监管账号：" + record.getPayAccount());
                    }
                }
            } catch (Exception e) {
                logger.error("操作失败." + e.getMessage());
                MessageUtil.addError("操作失败." + e.getMessage());
                return null;
            }
            MessageUtil.addInfo("入账完成!");
            init();
        }
        return null;
    }

    public String onMultiExecute() {
        if (selectedRecords == null || selectedRecords.length == 0) {
            MessageUtil.addWarn("请至少选择一笔记录！");
        } else {
            try {
                for (RsPayout record : selectedRecords) {
                    if (cbusPayoutService.updateRsPayoutToExec(record) == -1) {
                        throw new RuntimeException("【记录更新失败】付款监管账号：" + record.getPayAccount());
                    }
                }
            } catch (Exception e) {
                logger.error("操作失败." + e.getMessage());
                MessageUtil.addError("操作失败." + e.getMessage());
                return null;
            }
            MessageUtil.addInfo("入账完成!");

            init();
        }
        return null;
    }

    public String onAllSend() {
        if (payOverList.isEmpty()) {
            MessageUtil.addWarn("可发送记录为空！");
        } else {
            int sentResult = 1;
            try {
                for (RsPayout record : payOverList) {
                    sentResult = clientBiService.sendRsPayoutMsg(record);
                    if (sentResult != 1) {
                        throw new RuntimeException("发送失败");
                    }
                }
                MessageUtil.addInfo("发送完成！");
            } catch (Exception e) {
                logger.error("操作失败." + e.getMessage());
                MessageUtil.addError("操作失败." + e.getMessage());
                return null;
            }
            init();
        }
        return null;
    }

    public String onMultiSend() {
        if (toSendRecords == null || toSendRecords.length == 0) {
            MessageUtil.addWarn("请至少选择一笔待发送记录！");
        } else {
            int sentResult = 1;
            try {
                for (RsPayout record : toSendRecords) {
                    sentResult = clientBiService.sendRsPayoutMsg(record);
                    if (sentResult != 1) {
                        throw new RuntimeException("发送失败");
                    }
                }
                MessageUtil.addInfo("发送完成！");
            } catch (Exception e) {
                logger.error("操作失败." + e.getMessage());
                MessageUtil.addError("操作失败." + e.getMessage());
                return null;
            }
            init();
        }
        return null;
    }

    //=========================================

    public ExpensesPlanService getExpensesPlanService() {
        return expensesPlanService;
    }

    public void setExpensesPlanService(ExpensesPlanService expensesPlanService) {
        this.expensesPlanService = expensesPlanService;
    }

    public PayoutService getPayoutService() {
        return payoutService;
    }

    public void setPayoutService(PayoutService payoutService) {
        this.payoutService = payoutService;
    }

    public RsPlanCtrl getPlanCtrl() {
        return planCtrl;
    }

    public void setPlanCtrl(RsPlanCtrl planCtrl) {
        this.planCtrl = planCtrl;
    }

    public RsPayout getRsPayout() {
        return rsPayout;
    }

    public void setRsPayout(RsPayout rsPayout) {
        this.rsPayout = rsPayout;
    }

    public CbusPayoutService getCbusPayoutService() {
        return cbusPayoutService;
    }

    public void setCbusPayoutService(CbusPayoutService cbusPayoutService) {
        this.cbusPayoutService = cbusPayoutService;
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

    public List<RsPayout> getPayOverList() {
        return payOverList;
    }

    public void setPayOverList(List<RsPayout> payOverList) {
        this.payOverList = payOverList;
    }

    public RsPayout[] getToSendRecords() {
        return toSendRecords;
    }

    public void setToSendRecords(RsPayout[] toSendRecords) {
        this.toSendRecords = toSendRecords;
    }

    public ClientBiService getClientBiService() {
        return clientBiService;
    }

    public void setClientBiService(ClientBiService clientBiService) {
        this.clientBiService = clientBiService;
    }

    public List<RsPayout> getSendOverList() {
        return sendOverList;
    }

    public void setSendOverList(List<RsPayout> sendOverList) {
        this.sendOverList = sendOverList;
    }

    public RefundStatus getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(RefundStatus statusFlag) {
        this.statusFlag = statusFlag;
    }
}
