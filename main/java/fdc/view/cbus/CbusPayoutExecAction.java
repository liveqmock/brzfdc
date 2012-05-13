package fdc.view.cbus;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import fdc.common.constant.RefundStatus;
import fdc.common.constant.WorkResult;
import fdc.gateway.utils.StringUtil;
import fdc.repository.model.RsPayout;
import fdc.repository.model.RsPlanCtrl;
import fdc.service.CbusPayoutService;
import fdc.service.ClientBiService;
import fdc.service.PayoutService;
import fdc.service.expensesplan.ExpensesPlanService;
import org.apache.commons.lang.StringUtils;
import org.primefaces.component.commandbutton.CommandButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import platform.common.utils.MessageUtil;
import platform.service.PlatformService;
import pub.platform.security.OperatorManager;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-30
 * Time: ����3:41
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class CbusPayoutExecAction {
    private Logger logger = LoggerFactory.getLogger(CbusPayoutExecAction.class);

    private RsPayout rsPayout;
    @ManagedProperty(value = "#{cbusPayoutService}")
    private CbusPayoutService cbusPayoutService;
    @ManagedProperty(value = "#{clientBiService}")
    private ClientBiService clientBiService;
    @ManagedProperty(value = "#{payoutService}")
    private PayoutService payoutService;
    @ManagedProperty(value = "#{expensesPlanService}")
    private ExpensesPlanService expensesPlanService;

    private List<RsPayout> passPayoutList;
    private List<RsPayout> payOverList;
    private List<RsPayout> payFailList;
    private List<RsPayout> sendOverList;
    private RsPayout selectedRecord;
    private RsPayout[] selectedRecords;
    private RsPayout[] toSendRecords;
    private WorkResult workResult = WorkResult.CREATE;
    private RefundStatus statusFlag = RefundStatus.ACCOUNT_SUCCESS;
    private RsPlanCtrl planCtrl;
    private boolean isRunning;
    private String jscript;

    @PostConstruct
    public void init() {
//        rsPayout = new RsPayout();
        passPayoutList = cbusPayoutService.selectRecordsByWorkResult(WorkResult.PASS.getCode());
        payOverList = cbusPayoutService.selectRecordsByWorkResult(WorkResult.COMMIT.getCode());
        payFailList = cbusPayoutService.selectRecordsByWorkResult(WorkResult.NOT_KNOWN.getCode());

        sendOverList = cbusPayoutService.selectRecordsByWorkResult(WorkResult.SENT.getCode());

        FacesContext context = FacesContext.getCurrentInstance();
        String pkid = (String) context.getExternalContext().getRequestParameterMap().get("pkid");
        String action = (String) context.getExternalContext().getRequestParameterMap().get("action");

        if (!StringUtils.isEmpty(pkid) && "act".equalsIgnoreCase(action)) {
            rsPayout = payoutService.selectPayoutByPkid(pkid);
            planCtrl = expensesPlanService.selectPlanCtrlByPlanNo(rsPayout.getBusinessNo());
            UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
            CommandButton saveBtn = (CommandButton) viewRoot.findComponent("form:directPrint");
            saveBtn.setDisabled(true);
        }
    }

    public String onExecute() {
        try {
            if (isRunning) {
                MessageUtil.addWarn("ϵͳ�������ˣ����Ժ����ظ���������!");
                return null;
            }
            RsPayout originPayout = payoutService.selectPayoutByPkid(rsPayout.getPkId());
            if (!WorkResult.PASS.getCode().equals(originPayout.getWorkResult())) {
                MessageUtil.addWarn("�������˳�ͻ�������ظ���������!");
                return null;
            }
            isRunning = true;
            int cnt = cbusPayoutService.updateRsPayoutToExec(rsPayout);
//            int cnt = 1;
            UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
            CommandButton execBtn = (CommandButton) viewRoot.findComponent("form:saveBtn");
            execBtn.setDisabled(true);

            if (cnt == 1) {
                MessageUtil.addInfo("�������!");
                CommandButton prtnBtn = (CommandButton) viewRoot.findComponent("form:directPrint");
                prtnBtn.setDisabled(false);
            }
        } catch (Exception e) {
            logger.error("�����쳣." + e.getMessage());
            MessageUtil.addError("�����쳣." + e.getMessage());
            UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
            CommandButton execBtn = (CommandButton) viewRoot.findComponent("form:saveBtn");
            execBtn.setDisabled(true);
            return null;
        }
        init();
        return null;
    }

    public void onPrintVoucher(ActionEvent event) {
        try {
            FacesContext ctx = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
            Document document = new Document(PageSize.A3, 36, 36, 36, 90);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, bos);
            writer.setPageEvent(new PdfPageEventHelper());
//            writer.setPageEvent(new PdfVoucherHelper());
            document.open();

            document.add(transToPdfTable());
            document.close();
            response.reset();
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "inline");
            response.setContentLength(bos.size());
            response.setHeader("Cache-Control", "max-age=30");
            bos.writeTo(out);
            out.flush();
            out.close();
            ctx.responseComplete();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //
    private PdfPTable transToPdfTable() throws IOException, DocumentException {
        PdfPTable table = new PdfPTable(new float[]{800f});// ����һ��pdf���
        OperatorManager om = PlatformService.getOperatorManager();

        table.setSpacingBefore(130f);// ���ñ������հ׿��
        table.setTotalWidth(535);// ���ñ��Ŀ��
        table.setLockedWidth(false);// ���ñ��Ŀ�ȹ̶�
        table.setSpacingAfter(200f);
        table.getDefaultCell().setBorder(0);//���ñ��Ĭ��Ϊ�ޱ߿�
        BaseFont bfChinese = BaseFont.createFont("c:\\windows\\fonts\\simsun.ttc,1", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font headFont1 = new Font(bfChinese, 14, Font.BOLD);// ���������С
        Font headFont2 = new Font(bfChinese, 12, Font.NORMAL);// ���������С
        // ���ƾ֤
        if ("20".equals(rsPayout.getTransType())) {
            PdfPCell cell = new PdfPCell(new Paragraph("�㻮ҵ��ƾ֤", headFont1));
            cell.setBorder(0);
            cell.setFixedHeight(40);//��Ԫ��߶�
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);// ��������ˮƽ������ʾ
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);
            int[] rowWidths = new int[]{30, 48, 30};
            String[][] rows = new String[][]{
                    new String[]{"�������ţ�" + om.getOperator().getDeptid(),
                            "����ʱ�䣺" + new SimpleDateFormat("yyyy/MM/dd hh:MM:ss").format(new Date()),
                            "��ˮ�ţ�" + (StringUtils.isEmpty(rsPayout.getBankSerial()) ? " " : rsPayout.getBankSerial().substring(12))},
                    new String[]{"¼��Ա���ţ�" + rsPayout.getApplyUserId().substring(9),
                            "����Ա���룺" + rsPayout.getAuditUserId().substring(9),
                            "���ܴ��ţ�" + rsPayout.getApplyUserId().substring(9)},
                    new String[]{" ", " ", " "},
                    new String[]{"�ұ���룺�����",
                            "ƾ֤�ţ�" + rsPayout.getDocNo(),
                            "�㻮���ڣ�" + rsPayout.getTradeDate()},
                    new String[]{"֧�����������֧��",
                            "֧��������ţ�" + rsPayout.getWfInstanceId(),
                            "ҵ�����ࣺ��ͨ���"},
                    new String[]{"�������кţ�" + rsPayout.getRecBankCode(),
                            "���������ƣ�" + rsPayout.getRecBankName(),
                            " "},
            };
            //����
            for (String[] row : rows) {
                cell = new PdfPCell(new Paragraph(getStrLine(rowWidths, row), headFont2));
                cell.setBorder(0);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);
            }

            cell = new PdfPCell(new Paragraph("�������˺ţ�" + rsPayout.getPayAccount() + "          ���������ƣ�"
                    + rsPayout.getPayCompanyName(), headFont2));
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("�տ����˺ţ�" + rsPayout.getRecAccount() + "           �տ������ƣ�"
                    + rsPayout.getRecCompanyName(), headFont2));
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("�����" + String.format("%.2f", rsPayout.getApAmount()), headFont2));
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
        } else {
            PdfPCell cell = new PdfPCell(new Paragraph("��λ����ȡ��ƾ֤", headFont1));
            cell.setBorder(0);
            cell.setFixedHeight(40);//��Ԫ��߶�
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);// ��������ˮƽ������ʾ
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);
            int[] rowWidths = new int[]{30, 48, 30};
            String[][] rows = new String[][]{
                    new String[]{"�������ţ�" + om.getOperator().getDeptid(),
                            "����ʱ�䣺" + new SimpleDateFormat("yyyy/MM/dd hh:MM:ss").format(new Date()),
                            "������ˮ�ţ�" + (StringUtils.isEmpty(rsPayout.getBankSerial()) ? " " : rsPayout.getBankSerial().substring(12))},
                    new String[]{"��Ա���ţ�" + rsPayout.getApplyUserId().substring(9),
                            "���ܴ��ţ�" + rsPayout.getAuditUserId().substring(9),
                            "�ұ������"},
            };
            //����
            for (String[] row : rows) {
                cell = new PdfPCell(new Paragraph(getStrLine(rowWidths, row), headFont2));
                cell.setBorder(0);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);
            }

            String[] singleRows = new String[]{"�������˺ţ�" + rsPayout.getPayAccount(), "�����˻�����" + rsPayout.getPayCompanyName(),
                    "�տ����˺ţ�" + rsPayout.getRecAccount(), "�տ��˻�����" + rsPayout.getRecCompanyName(),
                    "���׽�" + StringUtils.rightPad(String.format("%.2f", rsPayout.getApAmount()), 40, " ") + "�������ת��",
                    "������ࣺ" + StringUtil.rightPad4ChineseToByteLength("����ҵ��ҵ���ڴ��", 40, " ") + "�浥����ӡˢ�ţ�",
                    "ƾ֤���룺"
            };
            for (String snglRow : singleRows) {
                cell = new PdfPCell(new Paragraph(snglRow, headFont2));
                cell.setBorder(0);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);
            }
        }
        return table;

    }

    private String getStrLine(int[] rowWidths, String[] strs) {
        String str = "";
        for (int i = 0; i < rowWidths.length; i++) {
            if (" ".equals(strs[i])) {
                str += " ";
            } else if (strs[i].getBytes().length <= rowWidths[i]) {
                str += StringUtil.rightPad4ChineseToByteLength(strs[i], rowWidths[i], " ");
            } else {
                str += strs[i];
            }
        }

        return str;
    }


    public String onAllExecute() {
        if (passPayoutList.isEmpty()) {
            MessageUtil.addWarn("�����˼�¼Ϊ�գ�");
        } else {
            try {
                for (RsPayout record : passPayoutList) {
                    if (cbusPayoutService.updateRsPayoutToExec(record) == -1) {
                        throw new RuntimeException("����¼����ʧ�ܡ��������˺ţ�" + record.getPayAccount());
                    }
                }
            } catch (Exception e) {
                logger.error("����ʧ��." + e.getMessage());
                MessageUtil.addError("����ʧ��." + e.getMessage());
                return null;
            }
            MessageUtil.addInfo("�������!");
            init();
        }
        return null;
    }

    public String onMultiExecute() {
        if (selectedRecords == null || selectedRecords.length == 0) {
            MessageUtil.addWarn("������ѡ��һ�ʼ�¼��");
        } else {
            try {
                for (RsPayout record : selectedRecords) {
                    if (cbusPayoutService.updateRsPayoutToExec(record) == -1) {
                        throw new RuntimeException("����¼����ʧ�ܡ��������˺ţ�" + record.getPayAccount());
                    }
                }
            } catch (Exception e) {
                logger.error("����ʧ��." + e.getMessage());
                MessageUtil.addError("����ʧ��." + e.getMessage());
                return null;
            }
            MessageUtil.addInfo("�������!");

            init();
        }
        return null;
    }

    public String onFlush() {
        init();
        return null;
    }

    public String onAllSend() {
        if (payOverList.isEmpty()) {
            MessageUtil.addWarn("�ɷ��ͼ�¼Ϊ�գ�");
        } else {
            int sentResult = 1;
            try {
                for (RsPayout record : payOverList) {
                    sentResult = clientBiService.sendRsPayoutMsg(record);
                    if (sentResult != 1) {
                        throw new RuntimeException("����ʧ��");
                    }
                }
                MessageUtil.addInfo("������ɣ�");
            } catch (Exception e) {
                logger.error("����ʧ��." + e.getMessage());
                MessageUtil.addError("����ʧ��." + e.getMessage());
                return null;
            }
            init();
        }
        return null;
    }

    public String onMultiSend() {
        if (toSendRecords == null || toSendRecords.length == 0) {
            MessageUtil.addWarn("������ѡ��һ�ʴ����ͼ�¼��");
        } else {
            int sentResult = 1;
            try {
                for (RsPayout record : toSendRecords) {
                    sentResult = clientBiService.sendRsPayoutMsg(record);
                    if (sentResult != 1) {
                        throw new RuntimeException("����ʧ��");
                    }
                }
                MessageUtil.addInfo("������ɣ�");
            } catch (Exception e) {
                logger.error("����ʧ��." + e.getMessage());
                MessageUtil.addError("����ʧ��." + e.getMessage());
                return null;
            }
            init();
        }
        return null;
    }

    //=========================================

    public List<RsPayout> getPayFailList() {
        return payFailList;
    }

    public void setPayFailList(List<RsPayout> payFailList) {
        this.payFailList = payFailList;
    }

    public ExpensesPlanService getExpensesPlanService() {
        return expensesPlanService;
    }

    public void setExpensesPlanService(ExpensesPlanService expensesPlanService) {
        this.expensesPlanService = expensesPlanService;
    }

    public PayoutService getPayoutService() {
        return payoutService;
    }

    public void setPayoutService(PayoutService payoutService) {
        this.payoutService = payoutService;
    }

    public RsPlanCtrl getPlanCtrl() {
        return planCtrl;
    }

    public void setPlanCtrl(RsPlanCtrl planCtrl) {
        this.planCtrl = planCtrl;
    }

    public RsPayout getRsPayout() {
        return rsPayout;
    }

    public void setRsPayout(RsPayout rsPayout) {
        this.rsPayout = rsPayout;
    }

    public CbusPayoutService getCbusPayoutService() {
        return cbusPayoutService;
    }

    public void setCbusPayoutService(CbusPayoutService cbusPayoutService) {
        this.cbusPayoutService = cbusPayoutService;
    }

    public RsPayout getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(RsPayout selectedRecord) {
        this.selectedRecord = selectedRecord;
    }

    public WorkResult getWorkResult() {
        return workResult;
    }

    public void setWorkResult(WorkResult workResult) {
        this.workResult = workResult;
    }

    public RsPayout[] getSelectedRecords() {
        return selectedRecords;
    }

    public void setSelectedRecords(RsPayout[] selectedRecords) {
        this.selectedRecords = selectedRecords;
    }

    public List<RsPayout> getPassPayoutList() {
        return passPayoutList;
    }

    public void setPassPayoutList(List<RsPayout> passPayoutList) {
        this.passPayoutList = passPayoutList;
    }

    public List<RsPayout> getPayOverList() {
        return payOverList;
    }

    public void setPayOverList(List<RsPayout> payOverList) {
        this.payOverList = payOverList;
    }

    public RsPayout[] getToSendRecords() {
        return toSendRecords;
    }

    public void setToSendRecords(RsPayout[] toSendRecords) {
        this.toSendRecords = toSendRecords;
    }

    public ClientBiService getClientBiService() {
        return clientBiService;
    }

    public void setClientBiService(ClientBiService clientBiService) {
        this.clientBiService = clientBiService;
    }

    public List<RsPayout> getSendOverList() {
        return sendOverList;
    }

    public void setSendOverList(List<RsPayout> sendOverList) {
        this.sendOverList = sendOverList;
    }

    public RefundStatus getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(RefundStatus statusFlag) {
        this.statusFlag = statusFlag;
    }

    public String getJscript() {
        return jscript;
    }

    public void setJscript(String jscript) {
        this.jscript = jscript;
    }
}
