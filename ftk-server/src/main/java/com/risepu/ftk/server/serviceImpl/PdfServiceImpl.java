package com.risepu.ftk.server.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.risepu.ftk.server.service.PdfService;

/**
 * 
 * @author L-heng
 *
 */
@Service
public class PdfServiceImpl implements PdfService {

	@Override
	public void pdf(String templatePath, String newPDFPath, Map<String, Map<String, String>> o) throws Exception {
		// TODO Auto-generated method stub
		// 模板路径
//		String templatePath = "E:/测试3.pdf";
		// 生成的新文件路径
//		String newPDFPath = "E:/ceshi.pdf";
		PdfReader reader;
		FileOutputStream out;
		ByteArrayOutputStream bos;
		PdfStamper stamper;

		out = new FileOutputStream(newPDFPath);// 输出流
		reader = new PdfReader(templatePath);// 读取pdf模板
		bos = new ByteArrayOutputStream();
		stamper = new PdfStamper(reader, bos);
		AcroFields form = stamper.getAcroFields();

//		String[] str = { "123456789", "TOP__ONE", "男", "1991-01-01", "130222111133338888", "河北省保定市" };
//		int i = 0;
//		Iterator<String> it = form.getFields().keySet().iterator();
//		while (it.hasNext()) {
//			String name = it.next().toString();
//			System.out.println(name);
//			form.setField(name, str[i++]);
//		}
		// 文字类的内容处理
		Map<String, String> datemap = (Map<String, String>) o.get("datemap");
		for (String key : datemap.keySet()) {
			String value = datemap.get(key);
			form.setField(key, value);
		}

		// 图片类的内容处理
		Map<String, String> imgmap = (Map<String, String>) o.get("imgmap");
		for (String key : imgmap.keySet()) {
			String value = imgmap.get(key);
			String imgpath = value;
			int pageNo = form.getFieldPositions(key).get(0).page;
			Rectangle signRect = form.getFieldPositions(key).get(0).position;
			float x = signRect.getLeft();
			float y = signRect.getBottom();
			// 根据路径读取图片
			Image image = Image.getInstance(imgpath);
			// 获取图片页面
			PdfContentByte under = stamper.getOverContent(pageNo);
			// 图片大小自适应
			image.scaleToFit(signRect.getWidth(), signRect.getHeight());
			// 添加图片
			image.setAbsolutePosition(x, y);
			under.addImage(image);
		}

		stamper.setFormFlattening(true);// 如果为false那么生成的PDF文件还能编辑，一定要设为true
		stamper.close();

		Document doc = new Document();
		PdfCopy copy = new PdfCopy(doc, out);
		doc.open();
		PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
		copy.addPage(importPage);
		doc.close();

	}

}
