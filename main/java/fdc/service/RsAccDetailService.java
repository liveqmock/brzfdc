package fdc.service;

import fdc.repository.dao.RsAccDetailMapper;
import fdc.repository.model.RsAccDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.service.SystemService;
import pub.platform.security.OperatorManager;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-9-2
 * Time: 下午2:04
 * To change this template use File | Settings | File Templates.
 */
/*
  TODO !!!  此类中方法不可添加事务托管
 */
@Service
public class RsAccDetailService {

    @Autowired
    private RsAccDetailMapper accDetailMapper;

    public int insertAccDetail(RsAccDetail rsAccDetail) {
        OperatorManager om = SystemService.getOperatorManager();
        String operId = om.getOperatorId();
        rsAccDetail.setCreatedBy(operId);
        rsAccDetail.setCreatedDate(new Date());
        rsAccDetail.setLastUpdBy(operId);
        rsAccDetail.setLastUpdDate(new Date());
        return accDetailMapper.insertSelective(rsAccDetail);
    }

    public int updateAccDetail(RsAccDetail rsAccDetail) {
        RsAccDetail originRecord = accDetailMapper.selectByPrimaryKey(rsAccDetail.getPkId());
        if (originRecord.getModificationNum().equals(rsAccDetail.getModificationNum())) {
            throw new RuntimeException("记录并发更新冲突，请重试！");
        } else {
            OperatorManager om = SystemService.getOperatorManager();
            String operId = om.getOperatorId();
            rsAccDetail.setLastUpdBy(operId);
            rsAccDetail.setLastUpdDate(new Date());
            rsAccDetail.setModificationNum(rsAccDetail.getModificationNum() + 1);
            return accDetailMapper.updateByPrimaryKeySelective(rsAccDetail);
        }
    }
}
