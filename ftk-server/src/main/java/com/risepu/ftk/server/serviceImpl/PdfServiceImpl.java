package com.risepu.ftk.server.serviceImpl;

import com.risepu.ftk.utils.PdfReplacer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.risepu.ftk.server.domain.Template;
import com.risepu.ftk.server.service.PdfService;
import com.risepu.ftk.server.service.TemplateService;


/**
 * @author L-heng
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
        PdfReplacer textReplacer = new PdfReplacer(filePath);
        textReplacer.setFont(14);
        textReplacer.replaceText("${_template}", _template);
        textReplacer.replaceText("${_title}", "单据证明");
        textReplacer.replaceText("${_chainHash}", "SFSFSGFDGFDHFD");
        textReplacer.toPdf(newPath);
        return newPath;
    }

}
