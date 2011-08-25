package fdc.view.biplan;

import fdc.repository.model.BiPlan;
import fdc.service.BiplanService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-25
 * Time: ÏÂÎç5:21
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class BiplanAction {
    private List<BiPlan> biPlanList;
    private ParamPlan paramPlan;
    @ManagedProperty(value = "#{biplanService}")
    private BiplanService biplanService;

    @PostConstruct
    public void init() {
        paramPlan = new ParamPlan();
    }

    // ====================================
    public List<BiPlan> getBiPlanList() {
        return biPlanList;
    }

    public void setBiPlanList(List<BiPlan> biPlanList) {
        this.biPlanList = biPlanList;
    }

    public ParamPlan getParamPlan() {
        return paramPlan;
    }

    public void setParamPlan(ParamPlan paramPlan) {
        this.paramPlan = paramPlan;
    }

    public BiplanService getBiplanService() {
        return biplanService;
    }

    public void setBiplanService(BiplanService biplanService) {
        this.biplanService = biplanService;
    }
}
