package com.risepu.ftk.server.serviceImpl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.risepu.ftk.server.domain.Domain;
import com.risepu.ftk.server.domain.Template;
import com.risepu.ftk.server.service.DomainService;
import com.risepu.ftk.server.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.risepu.ftk.server.service.PdfService;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


/**
 * @author L-heng
 */
@Service
public class PdfServiceImpl implements PdfService {
    @Autowired
    private DomainService domainService;
    @Autowired
    private TemplateService templateService;

    @Override
    public String pdf(String _template, String hash, String title, String qrFilePath, String GrFilePath, String pdfFilePath) throws Exception {
        // TODO Auto-generated method stub
        SimpleDateFormat ft = new SimpleDateFormat("yyyy年MM月dd日");
        String date = "" + ft.format(new Date());
        _template = _template.replaceAll("/t", " ");
        //设置纸张
        Rectangle rect = new Rectangle(PageSize.A4);
        //创建文档实例
        Document doc = new Document(rect);

        //添加中文字体
        BaseFont bfChinese = BaseFont.createFont("/simsun.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        //设置字体样式
        Font textFont = new Font(bfChinese, 15, Font.BOLD); //加粗
        Font boldFont = new Font(bfChinese, 15, Font.UNDEFINED); //正常
        Font secondTitleFont = new Font(bfChinese, 20, Font.UNDEFINED); //标题

        //创建输出流
        PdfWriter pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream(new File(pdfFilePath)));

        doc.open();
        PdfContentByte cd = pdfWriter.getDirectContent();
        doc.newPage();

        //段落
        Paragraph p1 = new Paragraph();
        //短语
        Phrase ph1 = new Phrase();
        //块
        Chunk c2 = new Chunk("区块链哈希：", boldFont);
        Chunk c22 = new Chunk(hash, boldFont);
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
        p1 = new Paragraph(title, secondTitleFont);
        //设置行间距
        p1.setLeading(50);
        p1.setAlignment(Element.ALIGN_CENTER);
        doc.add(p1);

        p1 = new Paragraph(" ");
        //设置行间距
        p1.setLeading(30);
        doc.add(p1);

        String[] a = _template.split("/n");
        for (int i = 0; i < a.length; i++) {
            p1 = new Paragraph();
            ph1 = new Phrase();
            Chunk c1 = new Chunk(a[i], boldFont);
            p1.setLeading(30);
            ph1.add(c1);
            p1.add(ph1);
            doc.add(p1);
        }

        //插入一个二维码图片
        Image image = Image.getInstance(qrFilePath);
        image.setAbsolutePosition(30, 300);//坐标
        image.scaleAbsolute(90, 90);//自定义大小
        doc.add(image);

        //插入公司盖章图片
        Image image1 = Image.getInstance(GrFilePath);
        image1.setAbsolutePosition(400, 330);//坐标
        image1.scaleAbsolute(175, 50);//自定义大小
        doc.add(image1);

        cd.beginText();
        //文字加粗
        //设置文本描边宽度
        //cd.setLineWidth(0.5);
        //设置文本为描边模式
        //cd.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE);

        cd.setFontAndSize(bfChinese, 20);
        cd.showTextAligned(Element.ALIGN_UNDEFINED, date, 430, 310, 0);
        cd.endText();
        doc.close();
        return pdfFilePath;
    }

    @Override
    public String pdf(Map<String, String> map, String hash, String qrFilePath, String GrFilePath, String pdfFilePath) throws Exception {
        Long templateId = Long.parseLong(map.get("templateId"));
        // 根据模板id得到模板数据
        java.util.List<Domain> list = domainService.selectByTemplate(templateId);
        // 根据模板id得到模板
        Template template = templateService.getTemplate(templateId);
        // 获取一次模板
        String _template = template.get_template();
        _template = _template.replaceAll("/t", " ");
        //获取pdf标题
        String title = template.getName();

        SimpleDateFormat ft = new SimpleDateFormat("yyyy年MM月dd日");
        String date = "" + ft.format(new Date());

        //设置纸张
        Rectangle rect = new Rectangle(PageSize.A4);
        //创建文档实例
        Document doc = new Document(rect);

        //添加中文字体
        BaseFont bfChinese = BaseFont.createFont("/simsun.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        //设置字体样式
        Font textFont = new Font(bfChinese, template.getContentSize(), Font.BOLD); //加粗
        Font hashFont = new Font(bfChinese, template.getHashSize(), Font.UNDEFINED); //加粗
        Font contentFont = new Font(bfChinese, template.getContentSize(), Font.UNDEFINED); //正常
        Font titleFont = new Font(bfChinese, template.getTitleSize(), Font.UNDEFINED); //标题

        //创建输出流
        PdfWriter pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream(new File(pdfFilePath)));

        doc.open();
        PdfContentByte cd = pdfWriter.getDirectContent();
        doc.newPage();

        //段落
        Paragraph p1 = new Paragraph();
        //短语
        Phrase ph1 = new Phrase();
        //块
        Chunk c2 = new Chunk("区块链哈希：", hashFont);
        Chunk c22 = new Chunk(hash, hashFont);
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
        p1 = new Paragraph(title, titleFont);
        //设置行间距
        p1.setLeading(50);
        p1.setAlignment(Element.ALIGN_CENTER);
        doc.add(p1);

        p1 = new Paragraph(" ");
        //设置行间距
        p1.setLeading(30);
        doc.add(p1);

        // 替换一次模板中的数据
        for (int i = 0; i < list.size(); i++) {
            Domain domain = list.get(i);
            String key = "${" + domain.getCode() + "}";
            //得到页面输入的值
            String value = map.get(domain.getCode());
            //将模板中的参数替换为输入的值
            _template = _template.replace(key, value);
        }

        String[] a = _template.split("/n");
        for (int i = 0; i < a.length; i++) {
            p1 = new Paragraph();
            ph1 = new Phrase();
            Chunk c1 = new Chunk(a[i], contentFont);
            p1.setLeading(30);
            ph1.add(c1);
            p1.add(ph1);
            doc.add(p1);
        }

        //插入一个二维码图片
        Image image = Image.getInstance(qrFilePath);
        image.setAbsolutePosition(30, 300);//坐标
        image.scaleAbsolute(90, 90);//自定义大小
        doc.add(image);

        //插入公司盖章图片
        Image image1 = Image.getInstance(GrFilePath);
        image1.setAbsolutePosition(400, 330);//坐标
        image1.scaleAbsolute(175, 50);//自定义大小
        doc.add(image1);

        cd.beginText();
        //文字加粗
        //设置文本描边宽度
        //cd.setLineWidth(0.5);
        //设置文本为描边模式
        //cd.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE);

        cd.setFontAndSize(bfChinese, 20);
        cd.showTextAligned(Element.ALIGN_UNDEFINED, date, 430, 310, 0);
        cd.endText();
        doc.close();
        return pdfFilePath;
    }

    /*public static void main(String[] args) {
        PdfServiceImpl a = new PdfServiceImpl();
        try {
            a.pdf("撒烦烦烦烦烦烦烦烦烦烦烦烦烦烦烦烦烦烦的反对大师傅嘀咕嘀咕的事发生发射点发生发射点发生/n沙发沙发沙发沙发丰富的石帆胜丰沙发上的方式犯得上发射点发射点犯得上发射点发生随风倒十分", "SFDSFSFSFSDFS", "但是发射点发生", "/file-path/示例二维码.jpg", "/file-path/示例盖章.jpg", "/file-path/test.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
