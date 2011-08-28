package fdc.gateway.service.impl;

import fdc.common.constant.AccountStatus;
import fdc.gateway.domain.CommonRes;
import fdc.gateway.domain.T000.T0001Req;
import fdc.gateway.domain.T000.T0001Res;
import fdc.gateway.domain.T000.T0002Req;
import fdc.gateway.domain.T000.T0002Res;
import fdc.gateway.domain.T200.*;
import fdc.gateway.service.BiDbService;
import fdc.gateway.service.IMessageService;
import fdc.gateway.domain.BaseBean;
import fdc.gateway.utils.BiRtnCode;
import fdc.gateway.utils.StringUtil;
import fdc.repository.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.service.SystemService;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-18
 * Time: ����2:27
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ServerMessageService implements IMessageService {

    private static final Logger logger = LoggerFactory.getLogger(ServerMessageService.class);
    private static SimpleDateFormat sdfdate = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat sdftime = new SimpleDateFormat("HHmmss");
    @Autowired
    private BiDbService biDbService;

    @Override
    public synchronized String handleMessage(String message) {

        String responseMsg = null;
        // �õ������룬���ݽ����뽫xmlת������Ӧ�Ľӿڶ���
        String opCode = StringUtil.getSubstrBetweenStrs(message, "<OpCode>", "</OpCode>");
        int nOpCode = Integer.parseInt(opCode);

        /*
        *      TODO �˿���Ӧ���ۼ��տ���һ�£��Ƿ���������������˻�������> Ф
         *    TODO ���ױ��н���ʱ���ֶθ�Ϊ6λ����
        *    TODO �ֶδ�ȷ�� �ݶ�������ϸ��ע�ֶΣ�����;��
        */
        switch (nOpCode) {
            case 1:
                T0001Req t0001Req = (T0001Req) BaseBean.toObject(T0001Req.class, message);
                logger.info(t0001Req.head.OpDate + t0001Req.head.OpTime + "==���ս��ף�" + t0001Req.head.OpCode);

                List<RsAccount> accountList = biDbService.selectAccountByCodeName(t0001Req.param.Acct, t0001Req.param.AcctName);

                T0001Res t0001Res = new T0001Res();

                if (accountList.isEmpty()) {
                    t0001Res.head.RetCode = BiRtnCode.BI_RTN_CODE_NO_ACCOUNT.getCode();
                    t0001Res.head.RetMsg = "û�в鵽�˻��������˻���Ϣ��";
                } else {
                    RsAccount account = accountList.get(0);
                    t0001Res.param.Balance = StringUtil.toBiformatAmt(account.getBalance());
                    t0001Res.param.UsableBalance = StringUtil.toBiformatAmt(account.getBalanceUsable());
                }

                responseMsg = t0001Res.toFDCDatagram();
                break;
            case 2:
                T0002Req t0002Req = (T0002Req) BaseBean.toObject(T0002Req.class, message);
                logger.info(t0002Req.head.OpDate + t0002Req.head.OpTime + "==���ս��ף�" + t0002Req.head.OpCode);

                T0002Res t0002Res = new T0002Res();

                try {
                    if (!biDbService.isAccountExistByCodeName(t0002Req.param.Acct, t0002Req.param.AcctName)) {
                        t0002Res.head.RetCode = BiRtnCode.BI_RTN_CODE_NO_ACCOUNT.getCode();
                        t0002Res.head.RetMsg = "û�в鵽�˻��������˻���Ϣ��";
                        break;
                    }
                    List<RsAccDetail> accDetailList = biDbService.selectAccDetailsByCodeNameDate(t0002Req.param.Acct,
                            t0002Req.param.AcctName, t0002Req.param.BeginDate, t0002Req.param.EndDate);
                    if (!accDetailList.isEmpty()) {
                        t0002Res.param.DetailNum = String.valueOf(accDetailList.size());
                        for (RsAccDetail accDetail : accDetailList) {
                            T0002Res.Param.Record record = T0002Res.getRecord();
                            record.Date = sdfdate.format(accDetail.getTradeDate());
                            record.Time = sdftime.format(accDetail.getTradeDate());
                            record.Flag = accDetail.getInoutFlag();
                            record.Type = accDetail.getTradeType();
                            record.Amt = StringUtil.toBiformatAmt(accDetail.getTradeAmt());
                            record.ContractNum = accDetail.getContractNo();
                            record.ToAcct = accDetail.getToAccountCode();
                            record.ToAcctName = accDetail.getToAccountName();
                            record.ToBankName = accDetail.getToHsBankName();
                            // TODO �ֶδ�ȷ�� �ݶ�������ϸ��ע�ֶΣ�����;��
                            record.Purpose = accDetail.getRemark();

                            t0002Res.param.recordList.add(record);
                        }
                    }
                } catch (ParseException e) {
                    t0002Res.head.RetCode = BiRtnCode.BI_RTN_CODE_FORMAT_ERROR.getCode();
                    t0002Res.head.RetMsg = "���ڸ�ʽ����";
                    logger.error("�ӿ��������ڸ�ʽ����.", e);
                    responseMsg = t0002Res.toFDCDatagram();
                    break;
                }
                responseMsg = t0002Res.toFDCDatagram();
                break;

            case 2001:
                T2001Req t2001Req = (T2001Req) BaseBean.toObject(T2001Req.class, message);
                logger.info(t2001Req.head.OpDate + t2001Req.head.OpTime + "==���ս��ף�" + t2001Req.head.OpCode);

                List<RsAccount> initAccountList = biDbService.selectAccountByCodeName(t2001Req.param.Acct, t2001Req.param.AcctName);

                T2001Res t2001Res = new T2001Res();

                if (!initAccountList.isEmpty()) {
                    RsAccount account = initAccountList.get(0);
                    account.setStatusFlag(AccountStatus.WATCH.getCode());
                    account.setAgrnum(t2001Req.param.AgrNum);
                    if (biDbService.updateAccount(account) != 1) {
                        t2001Res.head.RetCode = BiRtnCode.BI_RTN_CODE_FAILED.getCode();
                        t2001Res.head.RetMsg = "����ʧ�ܣ������ԡ�";
                    }
                } else {
                    t2001Res.head.RetCode = BiRtnCode.BI_RTN_CODE_NO_ACCOUNT.getCode();
                    t2001Res.head.RetMsg = "û�в鵽�˻��������˻���Ϣ��";
                }
                responseMsg = t2001Res.toFDCDatagram();
                break;
            case 2002:
                T2002Req t2002Req = (T2002Req) BaseBean.toObject(T2002Req.class, message);
                logger.info(t2002Req.head.OpDate + t2002Req.head.OpTime + "==���ս��ף�" + t2002Req.head.OpCode);

                List<RsAccount> limitAccountList = biDbService.selectAccountByCodeName(t2002Req.param.Acct, t2002Req.param.AcctName);

                T2002Res t2002Res = new T2002Res();

                if (!limitAccountList.isEmpty()) {
                    RsAccount account = limitAccountList.get(0);
                    account.setLimitFlag(t2002Req.param.LockFlag);
                    if (biDbService.updateAccount(account) != 1) {
                        t2002Res.head.RetCode = BiRtnCode.BI_RTN_CODE_FAILED.getCode();
                        t2002Res.head.RetMsg = "����ʧ�ܣ������ԡ�";
                    }
                } else {
                    t2002Res.head.RetCode = BiRtnCode.BI_RTN_CODE_NO_ACCOUNT.getCode();
                    t2002Res.head.RetMsg = "û�в鵽�˻��������˻���Ϣ��";
                }
                responseMsg = t2002Res.toFDCDatagram();
                break;
            case 2003:
                //  ��ͬ�ӿڱ�
                T2003Req t2003Req = (T2003Req) BaseBean.toObject(T2003Req.class, message);
                logger.info(t2003Req.head.OpDate + t2003Req.head.OpTime + "==���ս��ף�" + t2003Req.head.OpCode);

                BiContract contract = new BiContract();
                contract.setContractNo(t2003Req.param.ContractNum);                                     // ��ͬ��
                contract.setAccountCode(t2003Req.param.Acct);                                               // ����˺�
                contract.setAccountName(t2003Req.param.AcctName);                                     // ����˻�����
                contract.setBuyerName(t2003Req.param.BuyerName);                                     // ����������
                contract.setBuyerAccCode(t2003Req.param.BuyerAcct);                                   // �����˸��˽����˺�
                contract.setBuyerBankName(t2003Req.param.BuyerBankName);                      // �������˻�������
                contract.setBuyerCertType(t2003Req.param.BuyerIDType);                              //������֤������
                contract.setBuyerCertNo(t2003Req.param.BuyerIDCode);                                // ������֤������
                contract.setTotalAmt(new BigDecimal(t2003Req.param.TotalAmt).divide(new BigDecimal(100)));                   //�����ܼ�
                contract.setEarnestAmt(new BigDecimal(t2003Req.param.Deposit).divide(new BigDecimal(100)));                  // ����
                contract.setFirstAmt(new BigDecimal(t2003Req.param.DownPay).divide(new BigDecimal(100)));                    // �׸���
                contract.setLoanAmt(new BigDecimal(t2003Req.param.Mortgage).divide(new BigDecimal(100)));                  // ����
                contract.setHouseAddress(t2003Req.param.HouseAddress);                            // ���ݵ�ַ
                contract.setHouseType(t2003Req.param.HouseType);                                      // ��������
                contract.setHouseNo(t2003Req.param.HouseNO);                                            // ����¥����Ϣ
                contract.setOverAmt(new BigDecimal(t2003Req.param.OverAmt).divide(new BigDecimal(100)));                   // ��������ۿ�
                contract.setTreAccName(t2003Req.param.TreasuryName);                             // �Ͻɲ���ר���˻���
                contract.setTreAccCode(t2003Req.param.TreasuryAcct);                                // �Ͻɲ���ר���˺�
                contract.setTreBankName(t2003Req.param.TreasuryBankName);                  // ����ר��������

                T2003Res t2003Res = new T2003Res();

                if (biDbService.updateDBContractByBiContract(contract) != 1) {
                    t2003Res.head.RetCode = BiRtnCode.BI_RTN_CODE_FAILED.getCode();
                    t2003Res.head.RetMsg = "����ʧ�ܣ������ԡ�";
                }
                responseMsg = t2003Res.toFDCDatagram();
                break;
            case 2006:     // �����˻��������֪ͨ
                T2006Req t2006Req = (T2006Req) BaseBean.toObject(T2006Req.class, message);
                logger.info(t2006Req.head.OpDate + t2006Req.head.OpTime + "==���ս��ף�" + t2006Req.head.OpCode);

                List<RsAccount> overAccountList = biDbService.selectAccountByCodeName(t2006Req.param.Acct, t2006Req.param.AcctName);

                T2006Res t2006Res = new T2006Res();

                if (!overAccountList.isEmpty()) {
                    RsAccount account = overAccountList.get(0);
                    account.setStatusFlag(AccountStatus.CLOSE.getCode());
                    if (biDbService.updateAccount(account) == 1) {
                        t2006Res.param.CancelDate = SystemService.getSdfdate8();
                        t2006Res.param.CancelTime = SystemService.getSdftime6();
                        t2006Res.param.FinalBalance = StringUtil.toBiformatAmt(account.getBalance());
                    } else {
                        t2006Res.head.RetCode = BiRtnCode.BI_RTN_CODE_FAILED.getCode();
                        t2006Res.head.RetMsg = "����ʧ�ܣ������ԡ�";
                    }
                } else {
                    t2006Res.head.RetCode = BiRtnCode.BI_RTN_CODE_NO_ACCOUNT.getCode();
                    t2006Res.head.RetMsg = "û�в鵽�˻��������˻���Ϣ��";
                }

                responseMsg = t2006Res.toFDCDatagram();
                break;
            case 2007: //Ԥ�۷���ͬ������ֹ֪ͨ
                T2007Req t2007Req = (T2007Req) BaseBean.toObject(T2007Req.class, message);
                logger.info(t2007Req.head.OpDate + t2007Req.head.OpTime + "==���ս��ף�" + t2007Req.head.OpCode);

                BiContractClose contractClose = new BiContractClose();
                contractClose.setAccountCode(t2007Req.param.Acct);
                contractClose.setAccountName(t2007Req.param.AcctName);
                contractClose.setBuyerName(t2007Req.param.BuyerName);
                contractClose.setBuyerAccCode(t2007Req.param.BuyerAcct);
                contractClose.setBuyerBankName(t2007Req.param.BuyerBankName);
                contractClose.setBuyerCertType(t2007Req.param.BuyerIDType);
                contractClose.setBuyerCertNo(t2007Req.param.BuyerIDCode);
                contractClose.setContractNo(t2007Req.param.ContractNum);
                contractClose.setTotalAmt(new BigDecimal(t2007Req.param.TotalAmt).divide(new BigDecimal(100)));
                contractClose.setHouseAddress(t2007Req.param.HouseAddress);
                contractClose.setPurpose(t2007Req.param.EndReason);
                contractClose.setTransAmt(new BigDecimal(t2007Req.param.TransBuyerAmt).divide(new BigDecimal(100)));

                T2007Res t2007Res = new T2007Res();
                // TODO �˿���Ӧ���ۼ��տ���һ�£��Ƿ���������������˻�������> Ф
                if (biDbService.recvCloseContractInfo(contractClose) != 1) {
                    t2007Res.head.RetCode = BiRtnCode.BI_RTN_CODE_FAILED.getCode();
                    t2007Res.head.RetMsg = "����ʧ�ܣ������ԡ�";
                }
                responseMsg = t2007Res.toFDCDatagram();
                break;
            case 2008:
                T2008Req t2008Req = (T2008Req) BaseBean.toObject(T2008Req.class, message);
                logger.info(t2008Req.head.OpDate + t2008Req.head.OpTime + "==���ս��ף�" + t2008Req.head.OpCode);

                T2008Res t2008Res = new T2008Res();

                BiPlan biPlan = new BiPlan();
                biPlan.setAccountCode(t2008Req.param.Acct);
                biPlan.setAccountName(t2008Req.param.AcctName);
                biPlan.setPlanNo(t2008Req.param.PlanNo);
                biPlan.setPlanAmount(new BigDecimal(t2008Req.param.PlanAmt).divide(new BigDecimal(100)));
                biPlan.setPlanNum(Integer.parseInt(t2008Req.param.PlanNum));
                try {
                    biPlan.setSubmitDate(sdfdate.parse(t2008Req.param.SubmitDate));
                } catch (ParseException e) {
                    t2008Res.head.RetCode = BiRtnCode.BI_RTN_CODE_FORMAT_ERROR.getCode();
                    t2008Res.head.RetMsg = "���ڸ�ʽ����";
                    logger.error("���ڸ�ʽ����", e);
                    responseMsg = t2008Res.toFDCDatagram();
                    break;
                }
                int planDetailCnt = t2008Req.param.recordList.size();

                if (!t2008Req.param.PlanNum.equalsIgnoreCase(String.valueOf(planDetailCnt))) {
                    t2008Res.head.RetCode = BiRtnCode.BI_RTN_CODE_FORMAT_ERROR.getCode();
                    t2008Res.head.RetMsg = "�ÿ�ƻ���������ϸ�ƻ�������һ�¡�";
                    logger.error("�ÿ�ƻ���������ϸ�ƻ�������һ�£�");
                    responseMsg = t2008Res.toFDCDatagram();
                    break;
                }
                String wrngRecordNo = null;
                List<BiPlanDetail> biPlanDetailList = null;
                try {
                    if (planDetailCnt >= 1) {
                        biPlanDetailList = new ArrayList<BiPlanDetail>();
                        BiPlanDetail planDetail = null;
                        for (T2008Req.Param.Record record : t2008Req.param.recordList) {
                            planDetail = new BiPlanDetail();
                            wrngRecordNo = record.PlanDetailNO;
                            planDetail.setPlanId(t2008Req.param.PlanNo);
                            planDetail.setPlanCtrlNo(record.PlanDetailNO);
                            planDetail.setToAccountName(record.ToAcctName);
                            planDetail.setToAccountCode(record.ToAcct);
                            planDetail.setToHsBankName(record.ToBankName);
                            planDetail.setPlAmount(new BigDecimal(record.Amt).divide(new BigDecimal(100)));
                            planDetail.setPlanDate(sdfdate.parse(record.PlanDate));
                            planDetail.setPlanDesc(record.Purpose);
                            planDetail.setRemark(record.Remark);
                            biPlanDetailList.add(planDetail);
                        }
                        if (biDbService.storeFdcAllPlanInfos(biPlan, biPlanDetailList) == -1) {
                            throw new RuntimeException("���ݿⱣ�����ݲ���ʧ�ܣ�");
                        }
                    } else throw new RuntimeException("�ƻ���ϸΪ�գ�");

                } catch (ParseException e) {
                    t2008Res.head.RetCode = BiRtnCode.BI_RTN_CODE_FORMAT_ERROR.getCode();
                    t2008Res.head.RetMsg = wrngRecordNo + "�ƻ���ϸ���ڸ�ʽ����";
                    logger.error(wrngRecordNo + "�ƻ���ϸ���ڸ�ʽ����", e);
                    responseMsg = t2008Res.toFDCDatagram();
                    break;
                } catch (Exception e) {
                    t2008Res.head.RetCode = BiRtnCode.BI_RTN_CODE_FAILED.getCode();
                    t2008Res.head.RetMsg = e.getMessage() + "����ʧ�ܣ������ԡ�";
                    logger.error("2008������������ʧ�ܣ�", e);
                    responseMsg = t2008Res.toFDCDatagram();
                    break;
                }

                responseMsg = t2008Res.toFDCDatagram();
                break;
            default:
                CommonRes otherData = new CommonRes();
                otherData.head.RetCode = BiRtnCode.BI_RTN_CODE_FAILED.getCode();
                otherData.head.RetMsg = "������[" + opCode + "]�����ڣ�����ϵ���й���Ա��";
                responseMsg = otherData.toFDCDatagram();
                logger.error("====���յ��޷������ף��������룺" + opCode + "��");

        }
        return responseMsg;
    }
}
