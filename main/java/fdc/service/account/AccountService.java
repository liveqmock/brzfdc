package fdc.service.account;

import fdc.repository.dao.RsAccountMapper;
import fdc.repository.model.RsAccount;
import fdc.repository.model.RsAccountExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import platform.service.SystemService;
import pub.platform.security.OperatorManager;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-24
 * Time: 下午2:04
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AccountService {
    @Autowired
    private RsAccountMapper accountMapper;

    /**
     * 查询所有未删除监管账户记录
     *
     * @return
     */
    public List<RsAccount> qryAllRecords() {
        RsAccountExample example = new RsAccountExample();
        example.createCriteria().andDeletedFlagEqualTo("0");
        return accountMapper.selectByExample(example);
    }

    /**
     * 新增记录
     *
     * @param account
     */
    @Transactional
    public void insertRecord(RsAccount account) {
        OperatorManager om = SystemService.getOperatorManager();
        account.setCreatedBy(om.getOperatorId());
        account.setCreatedDate(new Date());
        account.setLastUpdBy(om.getOperatorId());
        account.setLastUpdDate(new Date());
        accountMapper.insertSelective(account);
    }
}
