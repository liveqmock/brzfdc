package fdc.service;

import fdc.common.constant.TradeStatus;
import fdc.repository.dao.RsAccDetailMapper;
import fdc.repository.dao.common.CommonMapper;
import fdc.repository.model.RsAccDetail;
import fdc.repository.model.RsAccDetailExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.service.SystemService;
import pub.platform.security.OperatorManager;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-9-2
 * Time: ����2:04
 * To change this template use File | Settings | File Templates.
 */
/*
 */
@Service
public class RsAccDetailService {

    @Autowired
    private RsAccDetailMapper accDetailMapper;
    @Autowired
    private CommonMapper commonMapper;

    public RsAccDetail selectAccDetailByPkid(String pkid) {
        return accDetailMapper.selectByPrimaryKey(pkid);
    }

    public List<RsAccDetail> selectAccDetailsByStatus(TradeStatus tradeStatus) {
        RsAccDetailExample example = new RsAccDetailExample();
        example.createCriteria().andDeletedFlagEqualTo("0").andStatusFlagEqualTo(tradeStatus.getCode());
        return accDetailMapper.selectByExample(example);
    }
    public int insertAccDetail(RsAccDetail rsAccDetail) {
        OperatorManager om = SystemService.getOperatorManager();
        String operId = om.getOperatorId();
        rsAccDetail.setCreatedBy(operId);
        rsAccDetail.setCreatedDate(new Date());
        rsAccDetail.setLastUpdBy(operId);
        rsAccDetail.setLastUpdDate(new Date());
        rsAccDetail.setLocalSerial(commonMapper.selectMaxAccDetailSerial());
        rsAccDetail.setBankSerial(rsAccDetail.getLocalSerial());
        return accDetailMapper.insertSelective(rsAccDetail);
    }

    public int updateAccDetail(RsAccDetail rsAccDetail) {
        RsAccDetail originRecord = accDetailMapper.selectByPrimaryKey(rsAccDetail.getPkId());
        if (!originRecord.getModificationNum().equals(rsAccDetail.getModificationNum())) {
            throw new RuntimeException("��¼�������³�ͻ�������ԣ�");
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
