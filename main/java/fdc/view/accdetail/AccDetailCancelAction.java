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
 * Time: ����3:37
 * To change this template use File | Settings | File Templates.
 */
// ����
@ManagedBean
@ViewScoped
public class AccDetailCancelAction {

    @ManagedProperty(value = "#{rsAccDetailService}")
    private RsAccDetailService accDetailService;
    @ManagedProperty(value = "#{tradeService}")
    private TradeService tradeService;
    private RsAccDetail accDetail;
    private List<RsAccDetail> accDetailList;
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
        }
    }

    public String onCancel() {
        try {
            if (tradeService.handleCancelAccDetail(accDetail, ChangeFlag.CANCEL) == 3) {
                UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
                CommandButton saveBtn = (CommandButton) viewRoot.findComponent("form:saveBtn");
                saveBtn.setDisabled(true);
                MessageUtil.addInfo("���׳����ɹ���");
            }else {
                MessageUtil.addError("���׳���ʧ�ܣ�");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.addError("����ʧ��." + e.getMessage());
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
}
