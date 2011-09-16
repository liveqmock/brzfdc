package fdc.view.contract;

import fdc.common.constant.ContractStatus;
import fdc.common.constant.WorkResult;
import fdc.repository.model.BiContractClose;
import fdc.repository.model.RsContract;
import fdc.repository.model.RsRefund;
import fdc.service.RefundService;
import fdc.service.contract.ContractService;
import org.apache.commons.lang.StringUtils;
import org.primefaces.component.commandbutton.CommandButton;
import platform.common.utils.MessageUtil;
import platform.service.SystemService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-9-7
 * Time: 下午2:24
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class RefundDetlAction {

    private RsRefund refund;
    private RsContract contract;
    @ManagedProperty(value = "#{contractService}")
    private ContractService contractService;
    @ManagedProperty(value = "#{refundService}")
    private RefundService refundService;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        String pkid = (String) context.getExternalContext().getRequestParameterMap().get("pkid");
        String action = (String) context.getExternalContext().getRequestParameterMap().get("action");
        if ("query".equals(action)) {
            refund = refundService.selectRefundByPkid(pkid);
            contract = contractService.selectContractByNo(refund.getBusinessNo());
        } else if (!StringUtils.isEmpty(pkid)) {
            contract = contractService.selectRecordContract(pkid);
            BiContractClose contractClose = contractService.selectCloseContractByNo(contract.getContractNo());
            refund = new RsRefund();
            copyFields(contractClose);
        }
    }

    public String onSave() {
        try {
            BigDecimal totalPlamt = refundService.selectSumPlamount() == null ? new BigDecimal(0) : refundService.selectSumPlamount();

            if (totalPlamt.add(refund.getPlAmount()).compareTo(contract.getTransbuyeramt()) > 0) {
                MessageUtil.addError("退款申请总金额不能大于合同退款金额！");
                return null;
            } else {
                refund.setTradeDate(SystemService.getSdfdate10());
                if (refundService.insertRecord(refund) == 1) {
                    if (totalPlamt.add(refund.getPlAmount()).equals(contract.getTransbuyeramt())) {
                        contract.setStatusFlag(ContractStatus.CANCELING.getCode());
                        contractService.updateRecord(contract);
                    }
                    UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
                    CommandButton saveBtn = (CommandButton) viewRoot.findComponent("form:saveBtn");
                    saveBtn.setDisabled(true);
                    MessageUtil.addInfo("申请退款已提交！");
                } else {
                    MessageUtil.addError("申请退款失败！");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.addError("操作失败。" + e.getMessage());
        }
        return null;
    }

    public String onEdit() {
        try {
            BigDecimal totalPlamt = refundService.selectSumPlamountExceptPkid(refund.getPkId()) == null
                    ? new BigDecimal(0) : refundService.selectSumPlamountExceptPkid(refund.getPkId());

            if (totalPlamt.add(refund.getPlAmount()).compareTo(contract.getTransbuyeramt()) > 0) {
                MessageUtil.addError("退款申请总金额不能大于合同退款金额！");
                return null;
            } else {
                refund.setWorkResult(WorkResult.CREATE.getCode());
                if (refundService.updateRecord(refund) == 1) {
                    if (totalPlamt.add(refund.getPlAmount()).equals(contract.getTransbuyeramt())) {
                        contract.setStatusFlag(ContractStatus.CANCELING.getCode());
                        contractService.updateRecord(contract);
                    }
                    UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
                    CommandButton saveBtn = (CommandButton) viewRoot.findComponent("form:saveBtn");
                    saveBtn.setDisabled(true);
                    MessageUtil.addInfo("申请退款已修改！");
                } else {
                    MessageUtil.addError("申请退款失败！");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.addError("操作失败。" + e.getMessage());
        }
        return null;
    }

    private void copyFields(BiContractClose contractClose) {
        refund.setPayAccount(contractClose.getAccountCode());
        refund.setPayCompanyName(contractClose.getAccountName());
        refund.setRecAccount(contractClose.getBuyerAccCode());
        refund.setRecCompanyName(contractClose.getBuyerName());
        refund.setRecBankName(contractClose.getBuyerBankName());
        refund.setPlAmount(contractClose.getTransAmt());
        refund.setBusinessNo(contractClose.getContractNo());
        refund.setPurpose(contractClose.getPurpose());
        refund.setWorkResult(WorkResult.CREATE.getCode());
    }

    //===========================================================


    public RsContract getContract() {
        return contract;
    }

    public void setContract(RsContract contract) {
        this.contract = contract;
    }

    public ContractService getContractService() {
        return contractService;
    }

    public void setContractService(ContractService contractService) {
        this.contractService = contractService;
    }

    public RsRefund getRefund() {
        return refund;
    }

    public void setRefund(RsRefund refund) {
        this.refund = refund;
    }

    public RefundService getRefundService() {
        return refundService;
    }

    public void setRefundService(RefundService refundService) {
        this.refundService = refundService;
    }
}
