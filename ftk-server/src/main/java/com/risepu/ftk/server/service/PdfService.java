package com.risepu.ftk.server.service;

import java.util.Map;

/**
 * 
 * @author L-heng
 *
 */
public interface PdfService {
	public void pdf(String templatePath, String newPDFPath, Map<String, Map<String, String>> o) throws Exception;
}
