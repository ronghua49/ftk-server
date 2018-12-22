package com.risepu.ftk.server.service;

/**
 * @author L-heng
 */
public interface PdfService {
    /**
     * 生成pdf
     *
     * @param _template  一次模板内容
     * @return 生成pdf的路径
     * @throws Exception
     */
    String pdf(String _template,String hash,String title,String qrFilePath,String GrFilePath) throws Exception;
}
