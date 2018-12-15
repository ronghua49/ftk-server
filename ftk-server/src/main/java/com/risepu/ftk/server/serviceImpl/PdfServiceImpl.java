package com.risepu.ftk.server.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
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
	public void pdf(Long templateId) throws Exception {
		// TODO Auto-generated method stub
		// 根据模板id得到模板
		Template template = templateService.getTemplate(templateId);

		// 获取一次模板
		String _template = template.get_template();

		// 获取二次模板路径
		String filePath = template.getFilePath();

		FileOutputStream out = new FileOutputStream("C:/Users/MACHEMIKE/Desktop/测试.pdf");// 输出流
		PdfReader reader = new PdfReader(filePath);// 读取pdf模板
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		PdfDictionary dict = reader.getPageN(1);
		PdfObject object = dict.getDirectObject(PdfName.CONTENTS);
		if (object instanceof PRStream) {
			PRStream stream = (PRStream) object;
			byte[] data = PdfReader.getStreamBytes(stream);
			System.out.println(new String(data));
//			stream.setData(new String(data).replace("Hello World", "HELLO WORLD").getBytes());
		}
		PdfStamper stamper = new PdfStamper(reader, bos);

		stamper.setFormFlattening(true);// 如果为false那么生成的PDF文件还能编辑，一定要设为true
		stamper.close();
		reader.close();
		Document doc = new Document();
		PdfCopy copy = new PdfCopy(doc, out);
		doc.open();
		PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
		copy.addPage(importPage);
		doc.close();

	}

}
