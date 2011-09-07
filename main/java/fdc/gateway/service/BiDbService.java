package fdc.gateway.service;

import fdc.common.constant.ContractStatus;
import fdc.repository.dao.*;
import fdc.repository.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    private RsAccDetailMapper accDetailMapper;
    @Autowired
    private BiPlanMapper biPlanMapper;
    @Autowired
    private BiPlanDetailMapper biPlanDetailMapper;
    @Autowired
    private RsContractMapper rsContractMapper;
    @Autowired
    private RsPlanCtrlMapper rsPlanCtrlMapper;

    @Transactional
    public int storeFdcAllPlanInfos(BiPlan biPlan, List<BiPlanDetail> biPlanDetailList) {
        BiPlanExample planExample = new BiPlanExample();
        planExample.createCriteria().andDeletedFlagEqualTo("0").andPlanNoEqualTo(biPlan.getPlanNo());
        if(biPlanMapper.countByExample(planExample) >= 1) {
            BiPlanDetailExample planDetailExample = new BiPlanDetailExample();
            planDetailExample.createCriteria().andDeletedFlagEqualTo("0").andPlanIdEqualTo(biPlan.getPlanNo());
            for (BiPlanDetail originPlanDetail : biPlanDetailMapper.selectByExample(planDetailExample)) {
                if(deletePlanCtlByDetail(originPlanDetail) == -1) {
                    return -1;
                }
            }
        }
        if (insertBiPlan(biPlan) == 1) {
            RsPlanCtrl rsPlanCtrl = null;
            for (BiPlanDetail biPlanDetail : biPlanDetailList) {
                rsPlanCtrl = new RsPlanCtrl();
                rsPlanCtrl.setAccountCode(biPlan.getAccountCode());
                rsPlanCtrl.setAcceptDate(biPlan.getSubmitDate());
                rsPlanCtrl.setPlanCtrlNo(biPlanDetail.getPlanCtrlNo());
                rsPlanCtrl.setToAccountName(biPlanDetail.getToAccountName());
                rsPlanCtrl.setToAccountCode(biPlanDetail.getToAccountCode());
                rsPlanCtrl.setToHsBankName(biPlanDetail.getToHsBankName());
                rsPlanCtrl.setPlAmount(biPlanDetail.getPlAmount());
                rsPlanCtrl.setAvAmount(rsPlanCtrl.getPlAmount());
                rsPlanCtrl.setPlanDate(biPlanDetail.getPlanDate());
                rsPlanCtrl.setPlanDesc(biPlanDetail.getPlanDesc());
                rsPlanCtrl.setRemark(biPlanDetail.getRemark());
                if (insertBiPlanDetail(biPlanDetail) != 1 || rsPlanCtrlMapper.insertSelective(rsPlanCtrl) != 1) {
                    return -1;
                }
            }
            return 1;
        }
        return -1;
    }

    private int deletePlanCtlByDetail(BiPlanDetail biPlanDetail) {
        RsPlanCtrlExample example = new RsPlanCtrlExample();
        example.createCriteria().andDeletedFlagEqualTo("0").andPlanCtrlNoEqualTo(biPlanDetail.getPlanId());
        List<RsPlanCtrl> rsPlanCtrlList = rsPlanCtrlMapper.selectByExample(example);
        if(rsPlanCtrlList.isEmpty()) return 1;
        else {
          RsPlanCtrl rsPlanCtrl = rsPlanCtrlList.get(0);
          rsPlanCtrl.setDeletedFlag("1");
          return rsPlanCtrlMapper.updateByPrimaryKey(rsPlanCtrl);
        }
    }

    /**
     * 新增计划明细
     *
     * @param biPlanDetail
     * @return
     */
    public int insertBiPlanDetail(BiPlanDetail biPlanDetail) {
        return biPlanDetailMapper.insertSelective(biPlanDetail);
    }

    /**
     * 新增计划（主表）
     *
     * @param biPlan
     * @return
     */
    public int insertBiPlan(BiPlan biPlan) {
        return biPlanMapper.insertSelective(biPlan);
    }

    /**
     * 接收处理预售房合同撤销信息到DB
     *
     * @param contractClose
     * @return
     */
    @Transactional
    public int recvCloseContractInfo(BiContractClose contractClose) {
        if (insertBiContactClose(contractClose) == 1) {
            return updateContractToClose(contractClose);
        } else return -1;
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
     * 撤销预售房合同
     *
     * @param contractClose
     * @return
     */
    @Transactional
    public int updateContractToClose(BiContractClose contractClose) {

        RsContract rsContract = selectContractByCloseInfo(contractClose);
        if (rsContract != null) {
            rsContract.setStatusFlag(ContractStatus.TRANS.getCode());
            rsContract.setModificationNum(rsContract.getModificationNum() + 1);
            rsContract.setTransbuyeramt(contractClose.getTransAmt());
            return rsContractMapper.updateByPrimaryKey(rsContract);
        }
        return -1;
    }

    private RsContract selectContractByCloseInfo(BiContractClose contractClose) {
        RsContractExample example = new RsContractExample();
        example.createCriteria().andContractNoEqualTo(contractClose.getContractNo()).andAccountCodeEqualTo(contractClose.getAccountCode())
                .andAccountNameEqualTo(contractClose.getAccountName()).andBuyerAccCodeEqualTo(contractClose.getBuyerAccCode())
                .andBuyerNameEqualTo(contractClose.getBuyerName()).andBuyerBankNameEqualTo(contractClose.getBuyerBankName())
                .andBuyerCertTypeEqualTo(contractClose.getBuyerCertType()).andBuyerCertNoEqualTo(contractClose.getBuyerCertNo())
                .andTotalAmtEqualTo(contractClose.getTotalAmt());
        List<RsContract> rsContractList = rsContractMapper.selectByExample(example);
        if (rsContractList.isEmpty() || rsContractList.size() > 1) {
            return null;
        } else return rsContractList.get(0);
    }

    @Transactional
    public int updateDBContractByBiContract(BiContract contract) {
        if (insertBiContract(contract) == 1) {
            return insertOrUpdateRsContract(contract);
        }
        return -1;
    }

    /**
     * 预售合同接口 新增
     *
     * @param contract
     * @return
     */
    @Transactional
    public int insertBiContract(BiContract contract) {
        return biContractMapper.insertSelective(contract);
    }

    @Transactional
    public int insertOrUpdateRsContract(BiContract contract) {
        RsContractExample example = new RsContractExample();
        example.createCriteria().andDeletedFlagEqualTo("0").andContractNoEqualTo(contract.getContractNo());
        List<RsContract> rsContracts = rsContractMapper.selectByExample(example);
        RsContract rsContract = null;
        if (rsContracts.isEmpty()) {
            rsContract = new RsContract();
        } else {
            rsContract = rsContracts.get(0);
            rsContract.setModificationNum(rsContract.getModificationNum() + 1);
        }
        rsContract.setContractNo(contract.getContractNo());                                     // 合同号
        rsContract.setAccountCode(contract.getAccountCode());                               // 监管账号
        rsContract.setAccountName(contract.getAccountName());                              // 监管账户户名
        rsContract.setBuyerName(contract.getBuyerName());                                   // 购房人姓名
        rsContract.setBuyerAccCode(contract.getBuyerAccCode());                          // 购房人个人结算账号
        rsContract.setBuyerBankName(contract.getBuyerBankName());                    // 购房人账户开户行
        rsContract.setBuyerCertType(contract.getBuyerCertType());                         //购房人证件类型
        rsContract.setBuyerCertNo(contract.getBuyerCertNo());                                // 购房人证件号码
        rsContract.setTotalAmt(contract.getTotalAmt());                                           //房屋总价
        rsContract.setEarnestAmt(contract.getEarnestAmt());                                    // 定金
        rsContract.setFirstAmt(contract.getFirstAmt());                                             // 首付款
        rsContract.setLoanAmt(contract.getLoanAmt());                                            // 贷款
        rsContract.setHouseAddress(contract.getHouseAddress());                            // 房屋地址
        rsContract.setHouseType(contract.getHouseType());                                      // 房屋类型
        rsContract.setHouseNo(contract.getHouseNo());                                            // 房屋楼栋信息
        rsContract.setOverAmt(contract.getOverAmt());                                            // 超标面积价款
        rsContract.setTreAccName(contract.getTreAccName());                                 // 上缴财政专用账户名
        rsContract.setTreAccCode(contract.getTreAccCode());                                   // 上缴财政专用账号
        rsContract.setTreBankName(contract.getTreBankName());                            // 财政专户开户行

        if (rsContracts.isEmpty()) {
            return rsContractMapper.insertSelective(rsContract);
        } else {
            rsContract = rsContracts.get(0);
            rsContract.setModificationNum(rsContract.getModificationNum() + 1);
            return rsContractMapper.updateByPrimaryKey(rsContract);
        }
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
                .andAccountNameEqualTo(accountName).andTradeDateBetween(fromDate, toDate);
        return accDetailMapper.selectByExample(example);
    }

    /**
     * 更新账户状态
     *
     * @param account
     * @return
     */
    @Transactional
    public int updateAccount(RsAccount account) {
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
