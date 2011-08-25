package fdc.view.contract;

import fdc.repository.model.RsContract;
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
public class ContractAction implements Serializable{
    private static final Logger logger = LoggerFactory.getLogger(ContractAction.class);

    @ManagedProperty(value = "#{contractService}")
    private ContractService contractService;

    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;

    private List<RsContract> detlList;
    private RsContract[] selectedRecords;
    private RsContract selectedRecord;

    private List<SelectItem> contractStatusList;

    @PostConstruct
    public void init() {

        initList();
    }

    private void initList(){
       this.detlList = contractService.selectContractList();
    }

    public  String onQuery(){
         return null;
    }
    public  String onPrint(){
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

    public List<SelectItem> getContractStatusList() {
        return contractStatusList;
    }

    public void setContractStatusList(List<SelectItem> contractStatusList) {
        this.contractStatusList = contractStatusList;
    }
}
