package fdc.view.contract;

import fdc.common.constant.ContractRecvStatus;
import fdc.common.constant.ContractStatus;
import fdc.common.constant.WorkResult;
import fdc.repository.model.RsReceive;
import fdc.service.ContractRecvService;
import org.springframework.beans.factory.annotation.Autowired;
import platform.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-9-6
 * Time: ����1:12
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class ContractRecvChkAction {
    @ManagedProperty(value = "#{contractRecvService}")
    private ContractRecvService contractRecvService;

    private List<RsReceive> pendChkList;
    private List<RsReceive> pendChkoverList;
    private List<RsReceive> pendChkUnpassList;
    private RsReceive[] selectedRecords;
    private ContractStatus contractStatus = ContractStatus.NORMAL;
    private ContractRecvStatus recvStatus = ContractRecvStatus.BACK;

    @PostConstruct
    public void init() {
        this.pendChkList = contractRecvService.selectContractList(WorkResult.CREATE);
        this.pendChkoverList = contractRecvService.selectContractList(WorkResult.PASS);
        this.pendChkUnpassList = contractRecvService.selectContractList(WorkResult.NOTPASS);
    }

    public String onCheck() {
        if (selectedRecords == null || selectedRecords.length == 0) {
            MessageUtil.addWarn("����ѡ��һ�����ݼ�¼��");
            return null;
        }
        try {
            for (RsReceive record : selectedRecords) {

                if (contractRecvService.updateRsReceiveToWorkResult(record, WorkResult.PASS) != 1) {
                    throw new RuntimeException("����ʧ�ܣ�");
                }
            }
            MessageUtil.addInfo("���˳ɹ���");
            init();
        } catch (Exception e) {
            MessageUtil.addError("����ʧ��." + e.getMessage());
        }
        return null;
    }

    public String onBack() {
        if (selectedRecords == null || selectedRecords.length == 0) {
            MessageUtil.addWarn("����ѡ��һ�����ݼ�¼��");
            return null;
        }
        try {
            for (RsReceive record : selectedRecords) {

                if (contractRecvService.updateRsReceiveToWorkResult(record, WorkResult.NOTPASS) != 1) {
                    throw new RuntimeException("�˻�ʧ�ܣ�");
                }
            }
            MessageUtil.addInfo("�˻سɹ���");
            init();
        } catch (Exception e) {
            MessageUtil.addError("����ʧ��." + e.getMessage());
        }
        return null;
    }

    //===========================================

    public ContractRecvService getContractRecvService() {
        return contractRecvService;
    }

    public void setContractRecvService(ContractRecvService contractRecvService) {
        this.contractRecvService = contractRecvService;
    }

    public List<RsReceive> getPendChkList() {
        return pendChkList;
    }

    public void setPendChkList(List<RsReceive> pendChkList) {
        this.pendChkList = pendChkList;
    }

    public List<RsReceive> getPendChkoverList() {
        return pendChkoverList;
    }

    public void setPendChkoverList(List<RsReceive> pendChkoverList) {
        this.pendChkoverList = pendChkoverList;
    }

    public List<RsReceive> getPendChkUnpassList() {
        return pendChkUnpassList;
    }

    public void setPendChkUnpassList(List<RsReceive> pendChkUnpassList) {
        this.pendChkUnpassList = pendChkUnpassList;
    }

    public RsReceive[] getSelectedRecords() {
        return selectedRecords;
    }

    public void setSelectedRecords(RsReceive[] selectedRecords) {
        this.selectedRecords = selectedRecords;
    }

    public ContractStatus getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(ContractStatus contractStatus) {
        this.contractStatus = contractStatus;
    }

    public ContractRecvStatus getRecvStatus() {
        return recvStatus;
    }

    public void setRecvStatus(ContractRecvStatus recvStatus) {
        this.recvStatus = recvStatus;
    }
}