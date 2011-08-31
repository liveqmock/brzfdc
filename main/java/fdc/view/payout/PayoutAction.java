package fdc.view.payout;

import fdc.common.constant.WorkResult;
import fdc.repository.model.RsPayout;
import fdc.service.PayoutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import platform.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-30
 * Time: 下午3:41
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class PayoutAction {
    private Logger logger = LoggerFactory.getLogger(PayoutAction.class);

    private RsPayout rsPayout;
    @ManagedProperty(value = "#{payoutService}")
    private PayoutService payoutService;
    private List<RsPayout> rsPayoutList;
    private List<RsPayout> chkPayoutList;
    private List<RsPayout> passPayoutList;
    private List<RsPayout> refusePayoutList;
    private RsPayout selectedRecord;
    private RsPayout[] selectedRecords;
    private WorkResult workResult = WorkResult.CREATE;
    private double planAmt;  // 计划金额
    private double avAmt;   // 可用金额

    @PostConstruct
    public void init() {
        rsPayout = new RsPayout();
        rsPayoutList = new ArrayList<RsPayout>();
        chkPayoutList = payoutService.selectRecordsByWorkResult(WorkResult.CREATE.getCode());
        passPayoutList = payoutService.selectRecordsByWorkResult(WorkResult.PASS.getCode());
        refusePayoutList = payoutService.selectRecordsByWorkResult(WorkResult.NOTPASS.getCode());
    }

    public String onSave() {

        if (rsPayout.getPlAmount().doubleValue() > this.avAmt || rsPayout.getPlAmount().doubleValue() > this.planAmt) {
            MessageUtil.addError("申请金额不得大于计划金额或可用金额！");
            return null;
        }
        try {
            if (payoutService.insertRsPayout(rsPayout) == 1) {
                MessageUtil.addInfo("受理用款成功！");
                return onReset();
            } else {
                MessageUtil.addError("受理用款失败！");
            }
        } catch (Exception e) {
            logger.error("受理用款失败，请检查输入内容！", e.getMessage());
            MessageUtil.addError("受理用款失败，请检查输入内容！");
        }
        return null;
    }

    public String onCheck() {
        if (selectedRecords == null || selectedRecords.length == 0) {
            MessageUtil.addWarn("请至少选择一笔记录！");
        } else {
            try {
                payoutService.updateRsPayoutsToWorkResult(selectedRecords, WorkResult.PASS.getCode());
            } catch (Exception e) {
                logger.error("复核失败." + e.getMessage());
                MessageUtil.addError("复核失败." + e.getMessage());
                return null;
            }
            MessageUtil.addInfo("复核成功!");
            init();
        }
        return null;
    }

    public String onRefuse() {
        if (selectedRecords == null || selectedRecords.length == 0) {
            MessageUtil.addWarn("请至少选择一笔记录！");
        } else {
            try {
                payoutService.updateRsPayoutsToWorkResult(selectedRecords, WorkResult.NOTPASS.getCode());
            } catch (Exception e) {
                logger.error("退回失败." + e.getMessage());
                MessageUtil.addError("退回失败." + e.getMessage());
                return null;
            }
            MessageUtil.addInfo("退回完成!");
            init();
        }
        return null;
    }

    public String onReset() {
        rsPayout = new RsPayout();
        return null;
    }

    public void showDetailListener(ActionEvent event) {
        String pkid = (String) event.getComponent().getAttributes().get("pkId");
        Map sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("pkId", pkid);
    }

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

    public double getPlanAmt() {
        return planAmt;
    }

    public void setPlanAmt(double planAmt) {
        this.planAmt = planAmt;
    }

    public double getAvAmt() {
        return avAmt;
    }

    public void setAvAmt(double avAmt) {
        this.avAmt = avAmt;
    }
}
