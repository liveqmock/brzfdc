package fdc.gateway.service;

import fdc.repository.dao.*;
import fdc.repository.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-26
 * Time: 下午2:37
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BiDbService {

    @Autowired
    private RsAccountMapper accountMapper;
    @Autowired
    private BiContractMapper biContractMapper;
    @Autowired
    private BiContractCloseMapper biContractCloseMapper;
    @Autowired
    private RsAccDetailMapper rsAccDetailMapper;
    @Autowired
    private BiPlanMapper biPlanMapper;
    @Autowired
    private BiPlanDetailMapper biPlanDetailMapper;


    /**
     * 新增计划明细
     *
     * @param biPlanDetail
     * @return
     */
    @Transactional
    public int insertBiPlanDetail(BiPlanDetail biPlanDetail) {
        return biPlanDetailMapper.insertSelective(biPlanDetail);
    }

    /**
     * 新增计划（主表）
     *
     * @param biPlan
     * @return
     */
    @Transactional
    public int insertBiPlan(BiPlan biPlan) {
        return biPlanMapper.insertSelective(biPlan);
    }

    /**
     * 新增预售合同撤销
     *
     * @param contractClose
     * @return
     */
    @Transactional
    public int insertBiContactClose(BiContractClose contractClose) {
        return biContractCloseMapper.insertSelective(contractClose);
    }

    /**
     * 预售合同接口 新增
     *
     * @param contract
     * @return
     */
    @Transactional
    public int insertBiContact(BiContract contract) {
        return biContractMapper.insertSelective(contract);
    }

    /**
     * 查询时间段内账户交易记录
     *
     * @param accountCode
     * @param accountName
     * @param fromDate
     * @param toDate
     * @return
     * @throws ParseException
     */
    public List<RsAccDetail> selectAccDetailsByCodeNameDate(String accountCode, String accountName, String fromDate, String toDate) throws ParseException {
        RsAccDetailExample example = new RsAccDetailExample();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        example.createCriteria().andDeletedFlagEqualTo("0").andAccountCodeEqualTo(accountCode)
                .andAccountNameEqualTo(accountName).andTradeDateBetween(sdf.parse(fromDate), sdf.parse(toDate));
        return rsAccDetailMapper.selectByExample(example);
    }

    /**
     * 更新账户状态
     *
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
     * 更新账户限制/解除限制状态
     *
     * @param account
     * @param limitStatus
     * @return
     */
    public int updateAccountToLimitStatus(RsAccount account, String limitStatus) {
        account.setLimitFlag(limitStatus);
        account.setModificationNum(account.getModificationNum() + 1);
        return accountMapper.updateByPrimaryKey(account);
    }

    /**
     * 根据户名和账户号查找账户
     *
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

    public boolean isAccountExistByCodeName(String accountCode, String accountName) {
        RsAccountExample example = new RsAccountExample();
        example.createCriteria().andDeletedFlagEqualTo("0")
                .andAccountCodeEqualTo(accountCode).andAccountNameEqualTo(accountName);
        return accountMapper.countByExample(example) == 1;
    }

}
