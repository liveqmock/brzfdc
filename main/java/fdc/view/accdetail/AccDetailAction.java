package fdc.view.accdetail;

import fdc.repository.model.RsAccDetail;
import fdc.service.ClientBiService;
import fdc.service.RsAccDetailService;
import fdc.service.TradeService;
import org.slf4j.LoggerFactory;
import platform.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-9-8
 * Time: 上午11:36
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class AccDetailAction {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AccDetailAction.class);
    @ManagedProperty(value = "#{rsAccDetailService}")
    private RsAccDetailService accDetailService;
    @ManagedProperty(value = "#{clientBiService}")
    private ClientBiService clientBiService;
    private List<RsAccDetail> todayAccDetailList;
    @ManagedProperty(value = "#{tradeService}")
    private TradeService tradeService;

    @PostConstruct
    public void init() {
        todayAccDetailList = accDetailService.selectTodayAccDetails();
    }

    public String onSend() {
        // 默认先检查是否有当日各种未入账交易

        try {
            if (!tradeService.isHasUnsendTrade()) {
                if (clientBiService.sendTodayAccDetails(todayAccDetailList) == 1) {
                    MessageUtil.addInfo("发送当日交易明细完成！");
                } else {
                    MessageUtil.addError("发送当日交易明细失败！");
                }
            }
        } catch (Exception e) {
            MessageUtil.addError("操作失败." + e.getMessage());
            logger.error("发送当日交易明细失败！",e.getMessage());
        }
        return null;
    }

    //=======================================

    public ClientBiService getClientBiService() {
        return clientBiService;
    }

    public void setClientBiService(ClientBiService clientBiService) {
        this.clientBiService = clientBiService;
    }

    public RsAccDetailService getAccDetailService() {
        return accDetailService;
    }

    public TradeService getTradeService() {
        return tradeService;
    }

    public void setTradeService(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    public void setAccDetailService(RsAccDetailService accDetailService) {
        this.accDetailService = accDetailService;
    }

    public List<RsAccDetail> getTodayAccDetailList() {
        return todayAccDetailList;
    }

    public void setTodayAccDetailList(List<RsAccDetail> todayAccDetailList) {
        this.todayAccDetailList = todayAccDetailList;
    }
}
