package fdc.view.accdetail;

import fdc.common.constant.SendFlag;
import fdc.repository.model.CbsAccTxn;
import fdc.service.account.CbusFdcActtxnService;
import org.slf4j.LoggerFactory;
import platform.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ManagedBean
@ViewScoped
public class CbusAccDetailAction {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(CbusAccDetailAction.class);

    private String startDate;
    private String endDate;
    private List<CbsAccTxn> cbsAccTxnList;
    @ManagedProperty(value = "#{cbusFdcActtxnService}")
    private CbusFdcActtxnService cbusFdcActtxnService;
    private SendFlag sendFlag = SendFlag.UN_SEND;

    @PostConstruct
    public void init() {
    }

    // TODO 查询所有监管账户的交易明细
    public String onQuery() {
        try {
            endDate = startDate;
            if (cbusFdcActtxnService.isSentActtxns(endDate)) {
                MessageUtil.addWarn(endDate + "贷款汇总数据已发送完成！");
                return null;
            }

            if (!cbusFdcActtxnService.isQryedActtxns(endDate)) {
                cbusFdcActtxnService.qrySaveActtxnsCbusByDate(startDate, endDate);
            }
            MessageUtil.addInfo(endDate + "交易明细数据已从核心系统获取完成。");
            cbsAccTxnList = cbusFdcActtxnService.qryCbsAccTotalTxnsByDateAndFlag(endDate, "0");
            if (cbsAccTxnList.isEmpty()) {
                MessageUtil.addWarn(endDate + "贷款明细数据为空！");
            }
        } catch (Exception e) {
            logger.error("交易失败。", e);
            MessageUtil.addError("操作失败。" + e.getMessage());
        }
        return null;
    }

    public String onSend() {
        try {
            if (cbusFdcActtxnService.isSentActtxns(endDate)) {
                MessageUtil.addWarn(endDate + "贷款汇总数据已发送完成！");
                return null;
            }
            cbsAccTxnList = cbusFdcActtxnService.qryCbsAccTotalTxnsByDateAndFlag(endDate, "0");
            if (cbsAccTxnList == null || cbsAccTxnList.isEmpty()) {
                MessageUtil.addWarn("没有待发送数据！");
                return null;
            } else {
                MessageUtil.addInfo(endDate + "交易明细数据已从核心系统获取完成。");
                cbusFdcActtxnService.sendAccTxns(cbsAccTxnList);
            }
            MessageUtil.addInfo(endDate + "贷款交易汇总发送成功！");
        } catch (Exception e) {
            logger.error("交易失败。", e);
            MessageUtil.addError("操作失败。" + e.getMessage());
        }
        return null;
    }

    //=======================================


    public SendFlag getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(SendFlag sendFlag) {
        this.sendFlag = sendFlag;
    }

    public List<CbsAccTxn> getCbsAccTxnList() {
        return cbsAccTxnList;
    }

    public void setCbsAccTxnList(List<CbsAccTxn> cbsAccTxnList) {
        this.cbsAccTxnList = cbsAccTxnList;
    }

    public CbusFdcActtxnService getCbusFdcActtxnService() {
        return cbusFdcActtxnService;
    }

    public void setCbusFdcActtxnService(CbusFdcActtxnService cbusFdcActtxnService) {
        this.cbusFdcActtxnService = cbusFdcActtxnService;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
