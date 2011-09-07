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
 * Time: ����2:37
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
     * �����ƻ���ϸ
     *
     * @param biPlanDetail
     * @return
     */
    public int insertBiPlanDetail(BiPlanDetail biPlanDetail) {
        return biPlanDetailMapper.insertSelective(biPlanDetail);
    }

    /**
     * �����ƻ�������
     *
     * @param biPlan
     * @return
     */
    public int insertBiPlan(BiPlan biPlan) {
        return biPlanMapper.insertSelective(biPlan);
    }

    /**
     * ���մ���Ԥ�۷���ͬ������Ϣ��DB
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
     * ����Ԥ�ۺ�ͬ����
     *
     * @param contractClose
     * @return
     */
    @Transactional
    public int insertBiContactClose(BiContractClose contractClose) {
        return biContractCloseMapper.insertSelective(contractClose);
    }

    /**
     * ����Ԥ�۷���ͬ
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
     * Ԥ�ۺ�ͬ�ӿ� ����
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
        rsContract.setContractNo(contract.getContractNo());                                     // ��ͬ��
        rsContract.setAccountCode(contract.getAccountCode());                               // ����˺�
        rsContract.setAccountName(contract.getAccountName());                              // ����˻�����
        rsContract.setBuyerName(contract.getBuyerName());                                   // ����������
        rsContract.setBuyerAccCode(contract.getBuyerAccCode());                          // �����˸��˽����˺�
        rsContract.setBuyerBankName(contract.getBuyerBankName());                    // �������˻�������
        rsContract.setBuyerCertType(contract.getBuyerCertType());                         //������֤������
        rsContract.setBuyerCertNo(contract.getBuyerCertNo());                                // ������֤������
        rsContract.setTotalAmt(contract.getTotalAmt());                                           //�����ܼ�
        rsContract.setEarnestAmt(contract.getEarnestAmt());                                    // ����
        rsContract.setFirstAmt(contract.getFirstAmt());                                             // �׸���
        rsContract.setLoanAmt(contract.getLoanAmt());                                            // ����
        rsContract.setHouseAddress(contract.getHouseAddress());                            // ���ݵ�ַ
        rsContract.setHouseType(contract.getHouseType());                                      // ��������
        rsContract.setHouseNo(contract.getHouseNo());                                            // ����¥����Ϣ
        rsContract.setOverAmt(contract.getOverAmt());                                            // ��������ۿ�
        rsContract.setTreAccName(contract.getTreAccName());                                 // �Ͻɲ���ר���˻���
        rsContract.setTreAccCode(contract.getTreAccCode());                                   // �Ͻɲ���ר���˺�
        rsContract.setTreBankName(contract.getTreBankName());                            // ����ר��������

        if (rsContracts.isEmpty()) {
            return rsContractMapper.insertSelective(rsContract);
        } else {
            rsContract = rsContracts.get(0);
            rsContract.setModificationNum(rsContract.getModificationNum() + 1);
            return rsContractMapper.updateByPrimaryKey(rsContract);
        }
    }

    /**
     * ��ѯʱ������˻����׼�¼
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
     * �����˻�״̬
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
     * �����˻�����/�������״̬
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
     * ���ݻ������˻��Ų����˻�
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
