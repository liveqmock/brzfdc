package fdc.service;

import fdc.common.constant.ChangeFlag;
import fdc.common.constant.InOutFlag;
import fdc.common.constant.TradeStatus;
import fdc.common.constant.TradeType;
import fdc.repository.dao.RsAccDetailMapper;
import fdc.repository.dao.common.CommonMapper;
import fdc.repository.model.RsAccDetail;
import fdc.repository.model.RsAccDetailExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.service.SystemService;
import pub.platform.security.OperatorManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-9-2
 * Time: 下午2:04
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
    private SimpleDateFormat sdf10 = new SimpleDateFormat("yyyy-MM-dd");

    public List<RsAccDetail> selectTodayAccDetails(){
       RsAccDetailExample example = new RsAccDetailExample();
        example.createCriteria().andDeletedFlagEqualTo("0")
                .andStatusFlagEqualTo(TradeStatus.SUCCESS.getCode())
                .andChangeFlagNotEqualTo(ChangeFlag.CANCEL.getCode())
                .andTradeDateEqualTo(sdf10.format(new Date()));
        return accDetailMapper.selectByExample(example);
    }

    public RsAccDetail selectAccDetailByPkid(String pkid) {
        return accDetailMapper.selectByPrimaryKey(pkid);
    }

    public List<RsAccDetail> selectAccDetailsByStatus(TradeStatus tradeStatus) {
        RsAccDetailExample example = new RsAccDetailExample();
        example.createCriteria().andDeletedFlagEqualTo("0").andStatusFlagEqualTo(tradeStatus.getCode());
        return accDetailMapper.selectByExample(example);
    }

    // 冲正交易
     public List<RsAccDetail> selectCancelAccDetails() {
         RsAccDetailExample example = new RsAccDetailExample();
        example.createCriteria().andDeletedFlagEqualTo("0")
                .andStatusFlagEqualTo(TradeStatus.SUCCESS.getCode())
                .andTradeDateGreaterThanOrEqualTo(sdf10.format(new Date()));
        example.setOrderByClause("Trade_Date desc");
        return accDetailMapper.selectByExample(example);
    }

    // 退票交易
     public List<RsAccDetail> selectBackAccDetails() {
        RsAccDetailExample example = new RsAccDetailExample();
         try {
             example.createCriteria().andDeletedFlagEqualTo("0")
                     .andStatusFlagEqualTo(TradeStatus.SUCCESS.getCode())
                     .andInoutFlagEqualTo(InOutFlag.OUT.getCode())
                     .andTradeDateGreaterThan(SystemService.getTodayAddDays(-7));
         } catch (ParseException e) {
             throw new RuntimeException("日期转换错误！");
         }
        example.setOrderByClause("Trade_Date desc");
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
