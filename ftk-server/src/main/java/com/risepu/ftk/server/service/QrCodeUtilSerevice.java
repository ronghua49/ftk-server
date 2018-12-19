package com.risepu.ftk.server.service;

import java.io.InputStream;

public interface QrCodeUtilSerevice {
    /**
     * 生成二维码
     *
     * @param filePath    二维码输出流路径
     * @param content     二维码携带的信息
     * @param qrCodeSize  二维码大小
     * @param imageFormat 二维码格式
     * @return
     */
    boolean createQrCode(String filePath, String content, int qrCodeSize, String imageFormat) throws Exception;

    /**
     * 读取二维码
     *
     * @param inputStream
     */
    void readQrCode(InputStream inputStream) throws Exception;
}
