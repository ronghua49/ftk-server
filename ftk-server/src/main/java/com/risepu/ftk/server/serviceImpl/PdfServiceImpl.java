package com.risepu.ftk.server.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.risepu.ftk.server.domain.Template;
import com.risepu.ftk.server.service.PdfService;
import com.risepu.ftk.server.service.TemplateService;

import javax.servlet.http.HttpServletResponse;

/**
 * @author L-heng
 */
@Service
public class PdfServiceImpl implements PdfService {
    @Autowired
    private TemplateService templateService;

    @Override
    public String pdf(Long templateId, String _template, HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        String newPath = "C:/Users/MACHEMIKE/Desktop/测试.pdf";
        // 根据模板id得到模板
        Template template = templateService.getTemplate(templateId);
        // 获取二次模板路径
        String filePath = template.getFilePath();
        // 输出流
        FileOutputStream out = new FileOutputStream(newPath);
        // 读取pdf模板
        PdfReader reader = new PdfReader(filePath);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        PdfStamper stamper = new PdfStamper(reader, bos);

//        PdfDictionary dict = reader.getPageN(1);
//        PdfObject object = dict.getDirectObject(PdfName.CONTENTS);
//        if (object instanceof PRStream) {
//            PRStream stream = (PRStream) object;
//
//            byte[] data = PdfReader.getStreamBytes(stream);
//            String data1 = new String(data);
//            String data2 = data1.replace("${_chainHash}", "SDGFDHFGFHFF");
//            String data3 = data2.replace("${_title}", "单据证明");
//            String data4 = data3.replace("${_template}", _template);
//            String data5 = data4.replace("${qrImage}", "山东省");
//            String data6 = data5.replace("${_corp}", "山东省");
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
//            String data7 = data6.replace("${_date}", sdf.format(new Date()));
//            stream.setData(data7.getBytes());
//        }

        PdfContentByte canvas = stamper.getOverContent(1);
        float height=595;
        System.out.println(canvas.getHorizontalScaling());
        float x,y;
        x= 216;
        y = height -49.09F;
        canvas.saveState();
        canvas.setColorFill(BaseColor.WHITE);
        canvas.rectangle(x, y-5, 43, 15);

        canvas.fill();
        canvas.restoreState();
        //开始写入文本
        canvas.beginText();
        //BaseFont bf = BaseFont.createFont(URLDecoder.decode(CutAndPaste.class.getResource("/AdobeSongStd-Light.otf").getFile()), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
        Font font = new Font(bf,10,Font.BOLD);
        //设置字体和大小
        canvas.setFontAndSize(font.getBaseFont(), 10);
        //设置字体的输出位置
        canvas.setTextMatrix(x, y);
        //要输出的text
        canvas.showText("多退少补" );

        //设置字体的输出位置
        canvas.setFontAndSize(font.getBaseFont(), 20);
        canvas.setTextMatrix(x, y-90);
        //要输出的text
        canvas.showText("多退少补" );

        canvas.endText();

        // 如果为false那么生成的PDF文件还能编辑，一定要设为true
        stamper.setFormFlattening(true);
        stamper.close();
        reader.close();

        Document doc = new Document();
        //使用windows自带的字体,字体所在路径
        PdfCopy copy = new PdfCopy(doc, out);
        doc.open();
        PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
        copy.addPage(importPage);

        doc.close();
        return newPath;
    }

}
