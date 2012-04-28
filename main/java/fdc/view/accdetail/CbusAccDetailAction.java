package fdc.view.accdetail;

import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class CbusAccDetailAction {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(CbusAccDetailAction.class);

    private String startDate;
    private String endDate;




    @PostConstruct
    public void init() {
    }

    // TODO 查询所有监管账户的交易明细
    public String onQuery() {
        try {

        } catch (Exception e) {

        }
        return null;
    }

    public String onSend() {
        return null;
    }

    //=======================================

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
