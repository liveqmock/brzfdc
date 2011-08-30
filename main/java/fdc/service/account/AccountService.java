package fdc.service.account;

import fdc.repository.dao.RsAccountMapper;
import fdc.repository.model.RsAccDetailExample;
import fdc.repository.model.RsAccount;
import fdc.repository.model.RsAccountExample;
import fdc.repository.model.RsFdccompany;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import platform.service.SystemService;
import pub.platform.security.OperatorManager;

import javax.management.RuntimeErrorException;
import java.math.BigDecimal;
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

    public RsAccount selectedRecord(String pkId) {
        return accountMapper.selectByPrimaryKey(pkId);
    }

    /**
     * �ж��˺��Ƿ��Ѵ���
     *
     * @param account
     * @return
     */
    public boolean isExistInDb(RsAccount account) {
        RsAccountExample example = new RsAccountExample();
        example.createCriteria().andAccountCodeEqualTo(account.getAccountCode());
        return accountMapper.countByExample(example) >= 1;
    }

    /**
     * �Ƿ񲢷����³�ͻ
     *
     * @param
     * @return
     */
    public boolean isModifiable(RsAccount act) {
        RsAccount actt = accountMapper.selectByPrimaryKey(act.getPkId());
        if (act.getModificationNum() != actt.getModificationNum()) {
            return false;
        }
        return true;
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
     * ��ѯ
     */
    public List<RsAccount> selectedRecordsByCondition(String presellNo, String companyId, String accountCode, String accountName) {
        RsAccountExample example = new RsAccountExample();
        example.clear();
        RsAccountExample.Criteria rsActCrit = example.createCriteria();
        rsActCrit.andDeletedFlagEqualTo("0");
        if (presellNo != null && !StringUtils.isEmpty(presellNo.trim())) {
            rsActCrit.andPresellNoEqualTo(presellNo);
        }
        if (companyId != null && !StringUtils.isEmpty(companyId.trim())) {
            rsActCrit.andCompanyIdEqualTo(companyId);
        }
        if (accountCode != null && !StringUtils.isEmpty(accountCode.trim())) {
            rsActCrit.andAccountCodeEqualTo(accountCode);
        }
        if (accountName != null && !StringUtils.isEmpty(accountName.trim())) {
            rsActCrit.andAccountNameLike(accountName + "%");
        }
        return accountMapper.selectByExample(example);
    }

    /**
     * ������¼
     *
     * @param account
     */
    @Transactional
    public void insertRecord(RsAccount account) {
        if (isExistInDb(account)) {
            throw new RuntimeException("���˺��Ѵ��ڣ�������¼�룡");
        } else {
            OperatorManager om = SystemService.getOperatorManager();
            account.setCreatedBy(om.getOperatorId());
            account.setCreatedDate(new Date());
            account.setLastUpdBy(om.getOperatorId());
            account.setLastUpdDate(new Date());
            accountMapper.insertSelective(account);
        }

    }

    @Transactional
    public void updateRecord(RsAccount account) {

        if (isModifiable(account)) {
            OperatorManager om = SystemService.getOperatorManager();
            account.setCreatedBy(om.getOperatorId());
            account.setCreatedDate(new Date());
            account.setLastUpdBy(om.getOperatorId());
            account.setLastUpdDate(new Date());
            accountMapper.updateByPrimaryKeySelective(account);
        } else {
            throw new RuntimeException("�������³�ͻ��ActPkid=" + account.getPkId());
        }
    }
}
