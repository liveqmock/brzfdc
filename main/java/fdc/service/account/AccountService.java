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
 * Time: ����2:04
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AccountService {
    @Autowired
    private RsAccountMapper accountMapper;

    /**
     * �ж��˺��Ƿ��Ѵ���
     * @param account
     * @return
     */
    public boolean isExistInDb(RsAccount account) {
        RsAccountExample example = new RsAccountExample();
        example.createCriteria().andAccountCodeEqualTo(account.getAccountCode());
        return accountMapper.countByExample(example) >= 1;
    }

    /**
     * ��ѯ����δɾ������˻���¼
     *
     * @return
     */
    public List<RsAccount> qryAllRecords() {
        RsAccountExample example = new RsAccountExample();
        example.createCriteria().andDeletedFlagEqualTo("0");
        return accountMapper.selectByExample(example);
    }

    /**
     * ������¼
     *
     * @param account
     */
    @Transactional
    public void insertRecord(RsAccount account) {
        if(isExistInDb(account)) {
            throw new RuntimeException("���˺��Ѵ��ڣ�������¼�룡");
        }
        OperatorManager om = SystemService.getOperatorManager();
        account.setCreatedBy(om.getOperatorId());
        account.setCreatedDate(new Date());
        account.setLastUpdBy(om.getOperatorId());
        account.setLastUpdDate(new Date());
        accountMapper.insertSelective(account);
    }
}
