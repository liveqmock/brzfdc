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
 * Time: ����2:40
 * To change this template use File | Settings | File Templates.
 */
public class TestPdfPrint extends PdfPageEventHelper {

    public PdfTemplate tpl;
    public BaseFont bf;

    public static void main(String[] args) {

        Document document = new Document(PageSize.A3, 36, 36, 36, 90);
        try {
            //ByteArrayOutputStream ops = new ByteArrayOutputStream();//������ͻ��ε���
            OutputStream ops = new FileOutputStream("F:/xiaoboRec8.pdf");//��������̵���
            //����ops����������л����Ǳ�������̻���������ͻ���

            PdfWriter writer = PdfWriter.getInstance(document, ops);
            writer.setPageEvent(new TestPdfPrint());
            // writer.setPageEvent(new PdfPrintHelper());
            document.open();
            BaseFont bfChinese = BaseFont.createFont("c:\\windows\\fonts\\simsun.ttc,1", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font headFont1 = new Font(bfChinese, 14, Font.BOLD);// ���������С
            Font headFont2 = new Font(bfChinese, 12, Font.NORMAL);// ���������С
            Font headFont3 = new Font(bfChinese, 10, Font.NORMAL);// ���������С

            float[] tablewidths = {800f};  // ���ñ������Լ��п�
            PdfPTable table = new PdfPTable(tablewidths);// ����һ��pdf���

            table.setSpacingBefore(130f);// ���ñ������հ׿��
            table.setTotalWidth(535);// ���ñ��Ŀ��
            table.setLockedWidth(false);// ���ñ��Ŀ�ȹ̶�
            table.setSpacingAfter(200f);
            table.getDefaultCell().setBorder(0);//���ñ��Ĭ��Ϊ�ޱ߿�

            PdfPCell cell;//new Paragraph("Taony125 testPdf ��������",
            //headFont1));// ����һ����Ԫ��
            // cell.setBorder(0);//���õ�Ԫ���ޱ߿�
            //cell.setColspan(7);// ���úϲ���Ԫ�������
            //table.addCell(cell);// ���ӵ�Ԫ��

            //PDF����
            cell = new PdfPCell(new Paragraph("�㻮ҵ��ƾ֤", headFont1));
            cell.setBorder(0);
            cell.setFixedHeight(40);//��Ԫ��߶�
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);// ��������ˮƽ������ʾ
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            //����
            cell = new PdfPCell(new Paragraph("�������ţ�810201011           ����ʱ�䣺" + new SimpleDateFormat("yyyy/MM/dd hh:MM:ss").format(new Date()) + "              ��ˮ��:0000048 ", headFont2));
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("¼��Ա���ţ�006               ����Ա���룺004" + "                            ���ܴ���:301 ", headFont2));
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("                                                                                   ", headFont2));
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("�ұ���룺�����              ƾ֤�ţ�810000035093"+ "                       �㻮����: " + new SimpleDateFormat("yyyy/MM/dd").format(new Date()), headFont2));
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);// ��������ˮƽ������ʾ
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("֧�����������֧��            ֧��������ţ�00941310" + "                    ҵ������:��ͨ��� ", headFont2));
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);// ��������ˮƽ������ʾ
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("�������кţ�8102909001011     ���������ƣ��й����������ൺ����ɽ��·֧��", headFont2));
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);// ��������ˮƽ������ʾ
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("�������˺ţ�8102010119090909090909           ���������ƣ��ൺ�б����㿪��ҵ���޹�˾", headFont2));
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);// ��������ˮƽ������ʾ
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("�տ����˺ţ�8102010119090909090909           ���������ƣ��ൺ���»������ʹɷ����޹�˾", headFont2));
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);// ��������ˮƽ������ʾ
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("�����8,102,010.11           ", headFont2));
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);// ��������ˮƽ������ʾ
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

