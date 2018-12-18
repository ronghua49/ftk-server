package com.risepu.ftk.server.service;


import javax.servlet.http.HttpServletResponse;

/**
 * @author L-heng
 */
public interface PdfService {
    String pdf(Long templateId, String _template, HttpServletResponse response) throws Exception;
}
