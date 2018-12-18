package com.risepu.ftk.server.service;

/**
 * @author L-heng
 */
public interface PdfService {
    String pdf(Long templateId, String _template) throws Exception;
}
