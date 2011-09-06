package fdc.view.contract;

import fdc.common.constant.ReceiveType;
import fdc.common.constant.WorkResult;
import fdc.gateway.utils.StringUtil;
import fdc.repository.model.RsContract;
import fdc.repository.model.RsReceive;
import fdc.service.ContractRecvService;
import fdc.service.contract.ContractService;
import org.apache.commons.lang.StringUtils;
import org.primefaces.component.commandbutton.CommandButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import platform.common.utils.MessageUtil;
import platform.service.ToolsService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-8-25
 * Time: ÏÂÎç9:21
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class ContractRecvDetlAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(ContractRecvDetlAction.class);
    @ManagedProperty(value = "#{contractService}")
    private ContractService contractService;
    @ManagedProperty(value = "#{contractRecvService}")
    private ContractRecvService contractRecvService;
    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;

    private RsReceive selectedRecord;
    private RsContract contract;

    private List<SelectItem> recvTypeOptions;
    private ReceiveType receiveType = ReceiveType.OTHER;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        String pkid = (String) context.getExternalContext().getRequestParameterMap().get("pkid");
        String action = (String) context.getExternalContext().getRequestParameterMap().get("action");
        if ("query".equals(action)) {
            selectedRecord = contractRecvService.selectContractRecv(pkid);
            contract = contractService.selectContractByNo(selectedRecord.getBusinessNo());
        } else if (!StringUtils.isEmpty(pkid)) {
            contract = contractService.selectRecordContract(pkid);
            selectedRecord = new RsReceive();
            copyFieldsFromContract();
            recvTypeOptions = toolsService.getEnuSelectItemList("RECEIVE_TYPE", false, false);
        }
    }

    private void copyFieldsFromContract() {
        selectedRecord.setBusinessNo(contract.getContractNo());
        selectedRecord.setCompanyName(contract.getCompanyName());
        selectedRecord.setAccountCode(contract.getAccountCode());
        selectedRecord.setBuyerAccCode(contract.getBuyerAccCode());
        selectedRecord.setBuyerAccName(contract.getBuyerAccName());
        selectedRecord.setBuyerCertType(contract.getBuyerCertType());
        selectedRecord.setBuyerCertNo(contract.getBuyerCertNo());
        selectedRecord.setBuyerName(contract.getBuyerName());
        selectedRecord.setBuyerBankName(contract.getBuyerBankName());
        selectedRecord.setApplyDate(new Date());
        selectedRecord.setWorkResult(WorkResult.CREATE.getCode());

        selectedRecord.setTradeAccCode(selectedRecord.getBuyerAccCode());
        selectedRecord.setTradeAccName(selectedRecord.getBuyerAccName());
        selectedRecord.setTradeBankName(selectedRecord.getBuyerBankName());
        selectedRecord.setExecUserName(selectedRecord.getBuyerName());
        selectedRecord.setExecDate(new Date());
    }

    public String onSave() {
        try {
            if (contractRecvService.insertRecord(selectedRecord) == 1) {
                UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
                CommandButton saveBtn = (CommandButton) viewRoot.findComponent("form:saveBtn");
                saveBtn.setDisabled(true);
                MessageUtil.addInfo("½É¿îÉêÇë³É¹¦£¡");
            }
        } catch (Exception e) {
            logger.error("½É¿î±£´æÊ§°Ü", e);
            MessageUtil.addError("²Ù×÷Ê§°Ü¡£" + e.getMessage());
        }
        return null;
    }

    //======================================================

    public ContractService getContractService() {
        return contractService;
    }

    public void setContractService(ContractService contractService) {
        this.contractService = contractService;
    }

    public RsReceive getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(RsReceive selectedRecord) {
        this.selectedRecord = selectedRecord;
    }

    public ContractRecvService getContractRecvService() {
        return contractRecvService;
    }

    public void setContractRecvService(ContractRecvService contractRecvService) {
        this.contractRecvService = contractRecvService;
    }

    public RsContract getContract() {
        return contract;
    }

    public void setContract(RsContract contract) {
        this.contract = contract;
    }

    public List<SelectItem> getRecvTypeOptions() {
        return recvTypeOptions;
    }

    public void setRecvTypeOptions(List<SelectItem> recvTypeOptions) {
        this.recvTypeOptions = recvTypeOptions;
    }

    public ToolsService getToolsService() {
        return toolsService;
    }

    public void setToolsService(ToolsService toolsService) {
        this.toolsService = toolsService;
    }

    public ReceiveType getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(ReceiveType receiveType) {
        this.receiveType = receiveType;
    }
}
