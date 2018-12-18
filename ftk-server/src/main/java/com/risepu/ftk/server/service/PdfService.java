package com.risepu.ftk.server.service;

/**
 * @author L-heng
 */
public interface PdfService {
    /**
     * 生成pdf
     *
     * @param templateId 模板id
     * @param _template  一次模板内容
     * @return 生成pdf的路径
     * @throws Exception
     */
    String pdf(Long templateId, String _template) throws Exception;
}
