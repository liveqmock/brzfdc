package fdc.service.account;

import fdc.repository.dao.RsAccountMapper;
import fdc.repository.model.RsAccount;
import fdc.repository.model.RsAccountExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import platform.service.SystemService;
import pub.platform.security.OperatorManager;

import javax.management.RuntimeErrorException;
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
     * 更新账户状态
     * @param account
     * @param status
     * @return
     */
    @Transactional
    public int updateAccountToStatus(RsAccount account, String status) {
       account.setStatusFlag(status);
       account.setModificationNum(account.getModificationNum() + 1);
       return accountMapper.updateByPrimaryKey(account);
    }

    /**
     * 根据户名和账户号查找账户
     * @param accountCode
     * @param accountName
     * @return
     */
    public List<RsAccount> selectAccountByCodeName(String accountCode, String accountName) {
       RsAccountExample example = new RsAccountExample();
        example.createCriteria().andDeletedFlagEqualTo("0")
                .andAccountCodeEqualTo(accountCode).andAccountNameEqualTo(accountName);
        return accountMapper.selectByExample(example);
    }
    /**
     * 判断账号是否已存在
     * @param account
     * @return
     */
    public boolean isExistInDb(RsAccount account) {
        RsAccountExample example = new RsAccountExample();
        example.createCriteria().andAccountCodeEqualTo(account.getAccountCode());
        return accountMapper.countByExample(example) >= 1;
    }

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
        if(isExistInDb(account)) {
            throw new RuntimeException("该账号已存在，请重新录入！");
        }
        OperatorManager om = SystemService.getOperatorManager();
        account.setCreatedBy(om.getOperatorId());
        account.setCreatedDate(new Date());
        account.setLastUpdBy(om.getOperatorId());
        account.setLastUpdDate(new Date());
        accountMapper.insertSelective(account);
    }
}
