package fdc.view.accdetail;

import fdc.common.constant.ChangeFlag;
import fdc.common.constant.InOutFlag;
import fdc.common.constant.TradeStatus;
import fdc.common.constant.TradeType;
import fdc.repository.model.RsAccDetail;
import fdc.service.RsAccDetailService;
import fdc.service.TradeService;
import org.apache.commons.lang.StringUtils;
import org.primefaces.component.commandbutton.CommandButton;
import platform.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-9-6
 * Time: 下午3:37
 * To change this template use File | Settings | File Templates.
 */
// 冲正
@ManagedBean
@ViewScoped
public class AccDetailCancelAction {

    @ManagedProperty(value = "#{rsAccDetailService}")
    private RsAccDetailService accDetailService;
    @ManagedProperty(value = "#{tradeService}")
    private TradeService tradeService;
    private RsAccDetail accDetail;
    private List<RsAccDetail> accDetailList;
    private List<RsAccDetail> accDetailApList;

    private InOutFlag inoutFlag = InOutFlag.IN;
    private TradeType tradeType = TradeType.HOUSE_INCOME;
    private TradeStatus tradeStatus = TradeStatus.SUCCESS;


    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        String pkid = (String) context.getExternalContext().getRequestParameterMap().get("pkid");
        // String action = (String) context.getExternalContext().getRequestParameterMap().get("action");
        if (!StringUtils.isEmpty(pkid)) {
            accDetail = accDetailService.selectAccDetailByPkid(pkid);
        } else {
            accDetailList = accDetailService.selectCancelAccDetails();
            accDetailApList = accDetailService.selectAPCancelAccDetails();
        }
    }

    public String onApplyCancel() {
        try {
            accDetail.setChangeFlag(ChangeFlag.AP_CANCEL.getCode());
            if (accDetailService.updateAccDetail(accDetail)== 1) {
                UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
                CommandButton saveBtn = (CommandButton) viewRoot.findComponent("form:saveBtn");
                saveBtn.setDisabled(true);
                MessageUtil.addInfo("交易冲正申请成功，待审核！");
            }else {
                MessageUtil.addError("交易冲正申请失败！");
            }
        } catch (Exception e) {
            MessageUtil.addError("操作失败." + e.getMessage());
        }
        return null;
    }

    public String onCancel() {
        try {
            if (tradeService.handleCancelAccDetail(accDetail, ChangeFlag.CANCEL) == 3) {
                UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
                CommandButton saveBtn = (CommandButton) viewRoot.findComponent("form:saveBtn");
                saveBtn.setDisabled(true);
                MessageUtil.addInfo("交易冲正成功！");
            }else {
                MessageUtil.addError("交易冲正失败！");
            }
        } catch (Exception e) {
            MessageUtil.addError("操作失败." + e.getMessage());
        }
        return null;
    }


    //=================================

    public RsAccDetail getAccDetail() {
        return accDetail;
    }

    public void setAccDetail(RsAccDetail accDetail) {
        this.accDetail = accDetail;
    }

    public List<RsAccDetail> getAccDetailList() {
        return accDetailList;
    }

    public void setAccDetailList(List<RsAccDetail> accDetailList) {
        this.accDetailList = accDetailList;
    }

    public RsAccDetailService getAccDetailService() {
        return accDetailService;
    }

    public void setAccDetailService(RsAccDetailService accDetailService) {
        this.accDetailService = accDetailService;
    }

    public InOutFlag getInoutFlag() {
        return inoutFlag;
    }

    public void setInoutFlag(InOutFlag inoutFlag) {
        this.inoutFlag = inoutFlag;
    }

    public TradeStatus getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(TradeStatus tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public void setTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
    }

    public TradeService getTradeService() {
        return tradeService;
    }

    public void setTradeService(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    public List<RsAccDetail> getAccDetailApList() {
        return accDetailApList;
    }

    public void setAccDetailApList(List<RsAccDetail> accDetailApList) {
        this.accDetailApList = accDetailApList;
    }
}
