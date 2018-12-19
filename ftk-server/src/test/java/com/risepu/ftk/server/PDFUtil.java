package com.risepu.ftk.server;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileOutputStream;

public class PDFUtil {
    public static void main(String[] args) throws Exception {
        createPDF();
    }

    /**
     * 创建PDF文档
     *
     * @return
     * @throws Exception
     * @throws Exception
     */
    public static String createPDF() throws Exception {
        String _tem = "李华先生/女士，身份证号：41023154632865,职位：财务主管,于2018-12-18离职。/n对公司在任职期间的工作，负有保密义务，自单据之日起两年内有义务保存完整。/n特此证明！！！";
        //输出路径
        String outPath = "C:/Users/MACHEMIKE/Desktop/test.pdf";
        //设置纸张
        Rectangle rect = new Rectangle(PageSize.A4);
        //创建文档实例
        Document doc = new Document(rect);

        //添加中文字体
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        //设置字体样式
        Font textFont = new Font(bfChinese, 11, Font.NORMAL); //正常
        Font boldFont = new Font(bfChinese, 11, Font.BOLD); //加粗
        Font secondTitleFont = new Font(bfChinese, 15, Font.BOLD); //标题

        //创建输出流
        PdfWriter.getInstance(doc, new FileOutputStream(new File(outPath)));
        doc.open();
        doc.newPage();
        //段落
        Paragraph p1 = new Paragraph();
        //短语
        Phrase ph1 = new Phrase();
        //块
        Chunk c2 = new Chunk("区块链哈希：", boldFont);
        Chunk c22 = new Chunk("SN-201604010001", textFont);
        //将块添加到短语
        ph1.add(c2);
        ph1.add(c22);
        //将短语添加到段落
        p1.add(ph1);
        //将段落添加到短语
        doc.add(p1);
        p1 = new Paragraph();
        //设置行间距
        p1.setLeading(20);
        p1.setAlignment(Element.ALIGN_CENTER);
        p1 = new Paragraph("单据证明", secondTitleFont);
        //设置行间距
        p1.setLeading(50);
        p1.setAlignment(Element.ALIGN_CENTER);
        doc.add(p1);
        p1 = new Paragraph(" ");
        //设置行间距
        p1.setLeading(30);
        doc.add(p1);

        String[] a = _tem.split("/n");
        for (int i = 0; i < a.length; i++) {
            p1 = new Paragraph();
            ph1 = new Phrase();
            Chunk c1 = new Chunk(a[i], textFont);
            p1.setFirstLineIndent(23);
            p1.setSpacingAfter(15);
            ph1.add(c1);
            p1.add(ph1);
            doc.add(p1);
        }

        //插入一个图片
        //手指图片
        Image image = Image.getInstance("C:/Users/MACHEMIKE/Desktop/测试.jpg");
        image.setAbsolutePosition(10, 500);//坐标
        image.scaleAbsolute(90, 90);//自定义大小
        doc.add(image);
        doc.close();

        return outPath;
    }

}


