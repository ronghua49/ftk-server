package com.risepu.ftk.server.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.risepu.ftk.server.domain.Template;
import com.risepu.ftk.server.service.PdfService;
import com.risepu.ftk.server.service.TemplateService;

/**
 * 
 * @author L-heng
 *
 */
@Service
public class PdfServiceImpl implements PdfService {
	@Autowired
	private TemplateService templateService;

	@Override
	public String pdf(Long templateId, String _template) throws Exception {
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

		PdfDictionary dict = reader.getPageN(1);
		PdfObject object = dict.getDirectObject(PdfName.CONTENTS);
		if (object instanceof PRStream) {
			PRStream stream = (PRStream) object;
			byte[] data = PdfReader.getStreamBytes(stream);
			String data1 = new String(data);
			String data2 = data1.replace("${_chainHash}", "SDGFDHFGFHFF");
			String data3 = data2.replace("${_title}", "单据证明");
			String data4 = data3.replace("${_template}", _template);
			String data5 = data4.replace("${_qrImage}", "山东省");
			String data6 = data5.replace("${_corp}", "山东省");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			String data7 = data6.replace("${_date}", sdf.format(new Date()));
			stream.setData(data7.getBytes());
		}
		// 如果为false那么生成的PDF文件还能编辑，一定要设为true
		stamper.setFormFlattening(true);
		stamper.close();
		reader.close();
		Document doc = new Document();
		PdfCopy copy = new PdfCopy(doc, out);
		doc.open();
		PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
		copy.addPage(importPage);
		doc.close();
		return newPath;
	}

}
