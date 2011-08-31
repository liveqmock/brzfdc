package fdc.service;

import fdc.common.constant.TradeType;
import fdc.common.constant.WorkResult;
import fdc.gateway.domain.CommonRes;
import fdc.gateway.domain.T200.T2004Req;
import fdc.gateway.service.impl.ClientMessageService;
import fdc.gateway.utils.StringUtil;
import fdc.gateway.xsocket.client.XSocketComponent;
import fdc.repository.model.RsPayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.common.utils.MessageUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-31
 * Time: 下午1:45
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ClientBiService {
    private static final Logger logger = LoggerFactory.getLogger(ClientBiService.class);
    @Autowired
    private XSocketComponent xSocketComponent;
    @Autowired
    private ClientMessageService clientMessageService;
    @Autowired
    private PayoutService payoutService;

    private SimpleDateFormat sdfdate8 = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat sdftime6 = new SimpleDateFormat("HHmmss");

    public int sendRsPayoutMsg(RsPayout record) throws IOException {
        T2004Req req = new T2004Req();
        req.head.OpCode = req.getClass().getSimpleName().substring(1, 5);
        req.param.Acct = record.getPayAccount();
        req.param.AcctName = record.getPayCompanyName();
        req.param.BankSerial = record.getSerial();  // TODO record.getBankSerial();  暂时以fdc流水代替
        req.param.Date = sdfdate8.format(record.getExecDate());
        req.param.Time = sdftime6.format(record.getExecDate());
        req.param.Flag = "0";
        req.param.Type = TradeType.PLAN_PAYOUT.getCode();
        req.param.ToAcctName = record.getRecCompanyName();
        req.param.ToAcct = record.getRecAccount();
        req.param.ToBankName = record.getRecBankName();
        req.param.Amt = StringUtil.toBiformatAmt(record.getPlAmount());
        req.param.Purpose = record.getPurpose();
        req.param.PlanDetailNO = record.getBusinessNo();
        String dataGram = req.toFDCDatagram();                // 报文

        CommonRes res = sendMsgAndRecvRes(dataGram);
        if (!"0000".equalsIgnoreCase(res.head.RetCode)) {
            return -1;
        } else {
            return payoutService.updateRsPayoutSent(record);
        }
    }

    private CommonRes sendMsgAndRecvRes(String dataGram) throws IOException {
        String recvMsg = xSocketComponent.sendAndRecvDataByBlockConn(dataGram);
        CommonRes resBean = clientMessageService.transMsgToBean(recvMsg);
        return resBean;
    }
}
