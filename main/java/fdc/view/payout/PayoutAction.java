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
    private List<RsPayout> rsPayoutList;
    private List<RsPayout> chkPayoutList;
    private List<RsPayout> passPayoutList;
    private List<RsPayout> refusePayoutList;
    private RsPayout selectedRecord;
    private RsPayout[] selectedRecords;
    private WorkResult workResult = WorkResult.CREATE;
    private double planAmt;  // �ƻ����
    private double avAmt;   // ���ý��

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
            MessageUtil.addError("������ô��ڼƻ�������ý�");
            return null;
        }
        try {
            if (payoutService.insertRsPayout(rsPayout) == 1) {
                MessageUtil.addInfo("�����ÿ�ɹ���");
                return onReset();
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
            init();
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
