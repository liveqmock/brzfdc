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
    private RsPayout selectedRecord;
    private List<RsPayout> selectedRecords;
    private WorkResult workResult = WorkResult.CREATE;

    @PostConstruct
    public void init() {
        rsPayout = new RsPayout();
        selectedRecords = new ArrayList<RsPayout>();
        rsPayoutList = new ArrayList<RsPayout>();
        chkPayoutList = payoutService.selectChkRecords();
    }

    public String onSave() {

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
        if (selectedRecords.isEmpty()) {
            MessageUtil.addWarn("������ѡ��һ�ʼ�¼��");
        } else {
            try {
                payoutService.updateRsPayoutsToStatus(selectedRecords, WorkResult.PASS.getCode());
            } catch (Exception e) {
                logger.error("����ʧ��." + e.getMessage());
                MessageUtil.addError("����ʧ��." + e.getMessage());
                return null;
            }
            MessageUtil.addInfo("���˳ɹ�!");
        }
        return null;
    }

    public String onRefuse() {
        if (selectedRecords.isEmpty()) {
            MessageUtil.addWarn("������ѡ��һ�ʼ�¼��");
        } else {
            try {
                payoutService.updateRsPayoutsToStatus(selectedRecords, WorkResult.NOTPASS.getCode());
            } catch (Exception e) {
                logger.error("����ʧ��." + e.getMessage());
                MessageUtil.addError("����ʧ��." + e.getMessage());
                return null;
            }
            MessageUtil.addInfo("�������!");
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

    public List<RsPayout> getSelectedRecords() {
        return selectedRecords;
    }

    public void setSelectedRecords(List<RsPayout> selectedRecords) {
        this.selectedRecords = selectedRecords;
    }
}
