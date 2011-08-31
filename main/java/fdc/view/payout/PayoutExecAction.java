package fdc.view.payout;

import fdc.common.constant.RefundStatus;
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
public class PayoutExecAction {
    private Logger logger = LoggerFactory.getLogger(PayoutExecAction.class);

    private RsPayout rsPayout;
    @ManagedProperty(value = "#{payoutService}")
    private PayoutService payoutService;
    private List<RsPayout> passPayoutList;
    private List<RsPayout> payOverList;
    private RsPayout selectedRecord;
    private RsPayout[] selectedRecords;
    private WorkResult workResult = WorkResult.CREATE;

    @PostConstruct
    public void init() {
        rsPayout = new RsPayout();
        passPayoutList = payoutService.selectRecordsByWorkResult(WorkResult.PASS.getCode());
        payOverList = payoutService.selectRecordsByWorkResult(WorkResult.COMMIT.getCode());
    }

    public String onAllExecute() {
       if (passPayoutList.isEmpty()) {
            MessageUtil.addWarn("�����˼�¼Ϊ�գ�");
        } else {
            try {
                for(RsPayout record : passPayoutList) {
                    if(payoutService.updateRsPayoutToStatus(record, RefundStatus.ACCOUNT_SUCCESS.getCode()) == -1){
                        throw new RuntimeException("����¼����ʧ�ܡ��������˺ţ�"+record.getPayAccount());
                    }
                }
            } catch (Exception e) {
                logger.error("����ʧ��." + e.getMessage());
                MessageUtil.addError("����ʧ��." + e.getMessage());
                return null;
            }
            MessageUtil.addInfo("�������!");
            init();
        }
        return null;
    }

    public String onMultiExecute() {
          if (selectedRecords == null || selectedRecords.length ==0) {
            MessageUtil.addWarn("������ѡ��һ�ʼ�¼��");
        } else {
               try {
                for(RsPayout record : selectedRecords) {
                    if(payoutService.updateRsPayoutToStatus(record, RefundStatus.ACCOUNT_SUCCESS.getCode()) == -1){
                        throw new RuntimeException("����¼����ʧ�ܡ��������˺ţ�"+record.getPayAccount());
                    }
                }
            } catch (Exception e) {
                logger.error("����ʧ��." + e.getMessage());
                MessageUtil.addError("����ʧ��." + e.getMessage());
                return null;
            }
            MessageUtil.addInfo("�������!");
            init();
          }
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
}
