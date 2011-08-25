package fdc.view.contract;

import fdc.repository.model.RsContract;
import fdc.repository.model.RsReceive;
import fdc.service.ContractRecvService;
import fdc.service.contract.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-8-25
 * Time: ÏÂÎç9:21
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
public class ContractRecvDetlAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(ContractRecvDetlAction.class);
    @ManagedProperty(value = "#{contractService}")
    private ContractService contractService;
    @ManagedProperty(value = "#{contractRecvService}")
    private ContractRecvService contractRecvService;

    private RsReceive selectedRecord;
    private RsContract contract;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        String paramPk_id = (String)context.getExternalContext().getSessionMap().get("pkId");
        contractRecvDetlQry(paramPk_id);
    }

    private void contractRecvDetlQry(String pkid) {
        selectedRecord = contractRecvService.selectContractRecv(pkid);
    }

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

}
