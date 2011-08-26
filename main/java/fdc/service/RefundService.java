package fdc.service;

import fdc.common.constant.RefundStatus;
import fdc.repository.dao.RsContractMapper;
import fdc.repository.dao.RsRefundMapper;
import fdc.repository.model.RsRefund;
import fdc.repository.model.RsRefundExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public RsRefund selectContractRecv(String pkId) {
        return refundMapper.selectByPrimaryKey(pkId);
    }
}
