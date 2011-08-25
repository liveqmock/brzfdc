package fdc.service;

import fdc.repository.dao.BiPlanDetailMapper;
import fdc.repository.dao.BiPlanMapper;
import fdc.repository.model.BiPlan;
import fdc.repository.model.BiPlanExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-25
 * Time: ÏÂÎç5:23
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BiplanService {
    @Autowired
    private BiPlanMapper biPlanMapper;
     @Autowired
    private BiPlanDetailMapper biPlanDetailMapper;

    public List<BiPlan> selectAllPlans() {
        return null;
    }
}
