package fdc.service;

import fdc.common.constant.RefundStatus;
import fdc.common.constant.WorkResult;
import fdc.repository.dao.RsContractMapper;
import fdc.repository.dao.RsRefundMapper;
import fdc.repository.dao.common.CommonMapper;
import fdc.repository.model.RsRefund;
import fdc.repository.model.RsRefundExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import platform.service.SystemService;
import pub.platform.security.OperatorManager;

import java.util.Date;
import java.util.List;

/**
 * 主要对应合同收款.
 * User: zhanrui
 * Date: 11-8-25
 * Time: 下午2:30
 * To change this template use File | Settings | File Templates.
 */
@Service
public class RefundService {

    @Autowired
    private RsContractMapper contractMapper;
    @Autowired
    private RsRefundMapper refundMapper;
    @Autowired
    private CommonMapper commonMapper;

    @Transactional
    public int insertRecord(RsRefund record) {
        OperatorManager om = SystemService.getOperatorManager();
        record.setCreatedBy(om.getOperatorId());
        record.setCreatedDate(new Date());
        record.setApplyDate(new Date());
        record.setApplyUserId(om.getOperatorId());
        record.setApplyUserName(om.getOperatorName());
        String serial = commonMapper.selectMaxRefundSerial();
        record.setSerial(serial);
        record.setBankSerial(serial);
        return refundMapper.insertSelective(record);
    }

    public List<RsRefund> selectRefundList() {
        RsRefundExample example = new RsRefundExample();
        example.createCriteria().andDeletedFlagEqualTo("0");
        return refundMapper.selectByExample(example);
    }

    public List<RsRefund> selectRefundList(RefundStatus status) {
        RsRefundExample example = new RsRefundExample();
        example.createCriteria().andDeletedFlagEqualTo("0").andStatusFlagEqualTo(status.getCode());
        return refundMapper.selectByExample(example);
    }

     public List<RsRefund> selectRefundList(WorkResult status) {
        RsRefundExample example = new RsRefundExample();
        example.createCriteria().andDeletedFlagEqualTo("0").andStatusFlagEqualTo(status.getCode());
        return refundMapper.selectByExample(example);
    }

    public RsRefund selectRefundByPkid(String pkId) {
        return refundMapper.selectByPrimaryKey(pkId);
    }

    @Transactional
    public int updateRecord(RsRefund record) {
        RsRefund originRecord = selectRefundByPkid(record.getPkId());
        if(!record.getModificationNum().equals(originRecord.getModificationNum())) {
            throw new RuntimeException("并发更新冲突！");
        }
        OperatorManager om = SystemService.getOperatorManager();
        record.setLastUpdBy(om.getOperatorId());
        record.setLastUpdDate(new Date());
        record.setModificationNum(record.getModificationNum() + 1);
        return refundMapper.updateByPrimaryKeySelective(record);
    }
}
