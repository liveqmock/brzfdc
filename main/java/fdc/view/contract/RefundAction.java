package fdc.view.contract;

import fdc.common.constant.*;
import fdc.repository.model.RsContract;
import fdc.repository.model.RsRefund;
import fdc.service.ContractRecvService;
import fdc.service.RefundService;
import fdc.service.contract.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import platform.service.ToolsService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-8-25
 * Time: ÏÂÎç2:29
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class RefundAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(RefundAction.class);

    @ManagedProperty(value = "#{contractService}")
    private ContractService contractService;
    @ManagedProperty(value = "#{contractRecvService}")
    private ContractRecvService contractRecvService;
    @ManagedProperty(value = "#{refundService}")
    private RefundService refundService;

    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;

    private List<RsContract> detlList;
    private RsContract[] selectedRecords;
    private RsContract selectedRecord;

    private List<RsRefund> detlRefundList;
    private List<RsRefund> pendChkList;
    private List<RsRefund> pendActList;
    private RsRefund[] selectedRefundRecords;
    private RsRefund selectedRefundRecord;

    private List<SelectItem> contractStatusOptions;
    private List<SelectItem> refundStatusOptions;
    private List<SelectItem> workResultOptions;
    private List<SelectItem> houseTypeOptions;
    private List<SelectItem> loanTypeOptions;
    private List<SelectItem> payupFlagOptions;
    private List<SelectItem> receiveTypeOptions;

    private ContractStatus contractStatus = ContractStatus.NORMAL;
    private RefundStatus refundStatus = RefundStatus.BACK;
    private WorkResult workResult = WorkResult.NOTPASS;
    private HouseType houseType = HouseType.NORMAL;
    private LoanType loanType = LoanType.SHANG_YE;
    private PayupFlag payupType = PayupFlag.PEND_PAYUP;
    private ReceiveType receiveType = ReceiveType.DEPOSIT;

    @PostConstruct
    public void init() {
        this.contractStatusOptions = toolsService.getEnuSelectItemList("CONTRACT_STATUS", true, false);
        this.houseTypeOptions = toolsService.getEnuSelectItemList("HOUSE_TYPE", true, false);
        this.loanTypeOptions = toolsService.getEnuSelectItemList("LOAN_TYPE", true, false);
        this.payupFlagOptions = toolsService.getEnuSelectItemList("PAYUP_FLAG", true, false);
        this.workResultOptions = toolsService.getEnuSelectItemList("WORK_RESULT", true, false);
        this.refundStatusOptions = toolsService.getEnuSelectItemList("REFUND_STATUS", true, false);
        this.receiveTypeOptions = toolsService.getEnuSelectItemList("RECEIVE_TYPE", true, false);
        initList();
    }

    private void initList() {
        this.detlList = contractService.selectContractList();
        this.pendChkList = refundService.selectRefundList(RefundStatus.INIT);
        this.pendActList = refundService.selectRefundList(RefundStatus.CHECKED);
        this.detlRefundList = refundService.selectRefundList();
    }

    public String onQuery() {
        return null;
    }

    public String onPrint() {
        return null;
    }

    public String onShowDetail() {
        return "common/contractDetlForm.xhtml";
    }

    public void showDetailListener(ActionEvent event) {
        String pkid = (String) event.getComponent().getAttributes().get("pkId");
        Map sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("pkId", pkid);
    }


    //================================================

    public ContractService getContractService() {
        return contractService;
    }

    public void setContractService(ContractService contractService) {
        this.contractService = contractService;
    }

    public List<RsContract> getDetlList() {
        return detlList;
    }

    public void setDetlList(List<RsContract> detlList) {
        this.detlList = detlList;
    }

    public RsContract[] getSelectedRecords() {
        return selectedRecords;
    }

    public void setSelectedRecords(RsContract[] selectedRecords) {
        this.selectedRecords = selectedRecords;
    }

    public RsContract getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(RsContract selectedRecord) {
        this.selectedRecord = selectedRecord;
    }

    public ToolsService getToolsService() {
        return toolsService;
    }

    public void setToolsService(ToolsService toolsService) {
        this.toolsService = toolsService;
    }

    public List<SelectItem> getContractStatusOptions() {
        return contractStatusOptions;
    }

    public void setContractStatusOptions(List<SelectItem> contractStatusOptions) {
        this.contractStatusOptions = contractStatusOptions;
    }

    public ContractStatus getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(ContractStatus contractStatus) {
        this.contractStatus = contractStatus;
    }

    public List<SelectItem> getHouseTypeOptions() {
        return houseTypeOptions;
    }

    public void setHouseTypeOptions(List<SelectItem> houseTypeOptions) {
        this.houseTypeOptions = houseTypeOptions;
    }

    public HouseType getHouseType() {
        return houseType;
    }

    public void setHouseType(HouseType houseType) {
        this.houseType = houseType;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    public PayupFlag getPayupType() {
        return payupType;
    }

    public void setPayupType(PayupFlag payupType) {
        this.payupType = payupType;
    }

    public List<SelectItem> getLoanTypeOptions() {
        return loanTypeOptions;
    }

    public void setLoanTypeOptions(List<SelectItem> loanTypeOptions) {
        this.loanTypeOptions = loanTypeOptions;
    }

    public List<SelectItem> getPayupFlagOptions() {
        return payupFlagOptions;
    }

    public void setPayupFlagOptions(List<SelectItem> payupFlagOptions) {
        this.payupFlagOptions = payupFlagOptions;
    }


    public List<RsRefund> getDetlRefundList() {
        return detlRefundList;
    }

    public void setDetlRefundList(List<RsRefund> detlRefundList) {
        this.detlRefundList = detlRefundList;
    }

    public RsRefund[] getSelectedRefundRecords() {
        return selectedRefundRecords;
    }

    public void setSelectedRefundRecords(RsRefund[] selectedRefundRecords) {
        this.selectedRefundRecords = selectedRefundRecords;
    }

    public RsRefund getSelectedRefundRecord() {
        return selectedRefundRecord;
    }

    public void setSelectedRefundRecord(RsRefund selectedRefundRecord) {
        this.selectedRefundRecord = selectedRefundRecord;
    }

    public List<SelectItem> getWorkResultOptions() {
        return workResultOptions;
    }

    public void setWorkResultOptions(List<SelectItem> workResultOptions) {
        this.workResultOptions = workResultOptions;
    }

    public WorkResult getWorkResult() {
        return workResult;
    }

    public void setWorkResult(WorkResult workResult) {
        this.workResult = workResult;
    }


    public List<SelectItem> getReceiveTypeOptions() {
        return receiveTypeOptions;
    }

    public void setReceiveTypeOptions(List<SelectItem> receiveTypeOptions) {
        this.receiveTypeOptions = receiveTypeOptions;
    }

    public ReceiveType getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(ReceiveType receiveType) {
        this.receiveType = receiveType;
    }

    public List<RsRefund> getPendChkList() {
        return pendChkList;
    }

    public void setPendChkList(List<RsRefund> pendChkList) {
        this.pendChkList = pendChkList;
    }

    public List<RsRefund> getPendActList() {
        return pendActList;
    }

    public void setPendActList(List<RsRefund> pendActList) {
        this.pendActList = pendActList;
    }

    public RefundService getRefundService() {
        return refundService;
    }

    public void setRefundService(RefundService refundService) {
        this.refundService = refundService;
    }

    public ContractRecvService getContractRecvService() {
        return contractRecvService;
    }

    public void setContractRecvService(ContractRecvService contractRecvService) {
        this.contractRecvService = contractRecvService;
    }

    public List<SelectItem> getRefundStatusOptions() {
        return refundStatusOptions;
    }

    public void setRefundStatusOptions(List<SelectItem> refundStatusOptions) {
        this.refundStatusOptions = refundStatusOptions;
    }

    public RefundStatus getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(RefundStatus refundStatus) {
        this.refundStatus = refundStatus;
    }

}
