package fdc.view.contract;

import fdc.repository.model.RsContract;
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
 * User: haiyuhuang
 * Date: 11-8-25
 * Time: ����9:21
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
public class ContractDetlAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(ContractDetlAction.class);
    @ManagedProperty(value = "#{contractService}")
    private ContractService contractService;

    private RsContract selectedRecord;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
//        String paramPk_id = (String)context.getExternalContext().getSessionMap().get("pkId");
        String paramPk_id = (String) context.getExternalContext().getRequestParameterMap().get("pkId");
        contractDetlQry(paramPk_id);
    }

    private void contractDetlQry(String pkid) {
        selectedRecord = contractService.selectRecordContract(pkid);
    }

    public ContractService getContractService() {
        return contractService;
    }

    public void setContractService(ContractService contractService) {
        this.contractService = contractService;
    }

    public RsContract getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(RsContract selectedRecord) {
        this.selectedRecord = selectedRecord;
    }
}
