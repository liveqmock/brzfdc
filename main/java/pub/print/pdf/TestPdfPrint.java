package pub.print.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-4-15
 * Time: 下午2:40
 * To change this template use File | Settings | File Templates.
 */
public class TestPdfPrint extends PdfPageEventHelper {

    public PdfTemplate tpl;
    public BaseFont bf;

    public static void main(String[] args) {

        Document document = new Document(PageSize.A3, 36, 36, 36, 90);
        try {
            //ByteArrayOutputStream ops = new ByteArrayOutputStream();//输出到客户段的流
            OutputStream ops = new FileOutputStream("F:/xiaoboRec8.pdf");//保存进磁盘的流
            //两个ops输出流用来切换，是保存进磁盘还是输出到客户端

            PdfWriter writer = PdfWriter.getInstance(document, ops);
            writer.setPageEvent(new TestPdfPrint());
            // writer.setPageEvent(new PdfPrintHelper());
            document.open();
            BaseFont bfChinese = BaseFont.createFont("c:\\windows\\fonts\\simsun.ttc,1", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font headFont1 = new Font(bfChinese, 14, Font.BOLD);// 设置字体大小
            Font headFont2 = new Font(bfChinese, 12, Font.NORMAL);// 设置字体大小
            Font headFont3 = new Font(bfChinese, 10, Font.NORMAL);// 设置字体大小

            float[] tablewidths = {800f};  // 设置表格的列以及列宽
            PdfPTable table = new PdfPTable(tablewidths);// 建立一个pdf表格

            table.setSpacingBefore(130f);// 设置表格上面空白宽度
            table.setTotalWidth(535);// 设置表格的宽度
            table.setLockedWidth(false);// 设置表格的宽度固定
            table.setSpacingAfter(200f);
            table.getDefaultCell().setBorder(0);//设置表格默认为无边框

            PdfPCell cell;//new Paragraph("Taony125 testPdf 中文字体",
            //headFont1));// 建立一个单元格
            // cell.setBorder(0);//设置单元格无边框
            //cell.setColspan(7);// 设置合并单元格的列数
            //table.addCell(cell);// 增加单元格

            //PDF标题
            cell = new PdfPCell(new Paragraph("汇划业务凭证", headFont1));
            cell.setBorder(0);
            cell.setFixedHeight(40);//单元格高度
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            //内容
            cell = new PdfPCell(new Paragraph("机构代号：810201011           记账时间：" + new SimpleDateFormat("yyyy/MM/dd hh:MM:ss").format(new Date()) + "              流水号:0000048 ", headFont2));
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("录入员代号：006               复核员代码：004" + "                            主管代号:301 ", headFont2));
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("                                                                                   ", headFont2));
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("币别代码：人民币              凭证号：810000035093"+ "                       汇划日期: " + new SimpleDateFormat("yyyy/MM/dd").format(new Date()), headFont2));
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居中显示
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("支付渠道：大额支付            支付交易序号：00941310" + "                    业务种类:普通汇兑 ", headFont2));
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居中显示
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("接收行行号：8102909001011     接收行名称：中国建设银行青岛分行山东路支行", headFont2));
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居中显示
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("付款人账号：8102010119090909090909           付款人名称：青岛市保利广开置业有限公司", headFont2));
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居中显示
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("收款人账号：8102010119090909090909           付款人名称：青岛市新华锦国际股份有限公司", headFont2));
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居中显示
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("汇出金额：8,102,010.11           ", headFont2));
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 设置内容水平居中显示
            table.addCell(cell);
            document.add(table);
            document.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onOpenDocument(PdfWriter writer, Document document) {
        try {
            tpl = writer.getDirectContent().createTemplate(100, 100);
            bf = BaseFont.createFont("c:\\windows\\fonts\\simsun.ttc,1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    @Override
    public void onStartPage(PdfWriter writer, Document document) {

    }

    public void onEndPage(PdfWriter writer, Document document) {

    }

    public void onCloseDocument(PdfWriter writer, Document document) {
        tpl.closePath();//sanityCheck();
    }
}

