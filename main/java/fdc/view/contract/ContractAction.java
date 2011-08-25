package fdc.view.contract;

import fdc.repository.model.RsContract;
import fdc.service.contract.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-8-25
 * Time: ÏÂÎç2:29
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class ContractAction {
    private static final Logger logger = LoggerFactory.getLogger(ContractAction.class);
    @ManagedProperty(value = "#{contractService}")
    private ContractService contractService;

    private List<RsContract> detlList;
    private RsContract[] selectedRecords;
    private RsContract selectedRecord;

    @PostConstruct
    public void init() {

    }

    private void initList(){

    }

    public  String onQuery(){
         return null;
    }
    public  String onPrint(){
         return null;
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
}
