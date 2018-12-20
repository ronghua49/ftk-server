package com.risepu.ftk.utils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author L-heng
 */
public class ChartGraphics {
    private BufferedImage image;
    private int imageWidth = 350;  //图片的宽度
    private int imageHeight = 100; //图片的高度

    //生成图片文件
    @SuppressWarnings("restriction")
    public void createImage(String fileLocation) {
        BufferedOutputStream bos = null;
        if (image != null) {
            try {
                FileOutputStream fos = new FileOutputStream(fileLocation);
                bos = new BufferedOutputStream(fos);
//                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
//                encoder.encode(image);
                //保存新图片
                ImageIO.write(image, "JPG", bos);
                bos.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bos != null) {//关闭输出流
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void graphicsGeneration(String name, String imgurl) {

        image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        //设置图片的背景色
        Graphics2D main = image.createGraphics();
        main.setColor(Color.white);
        main.fillRect(0, 0, imageWidth, imageHeight);

        Graphics2D tip = image.createGraphics();
        //设置区域颜色
//        tip.setColor(new Color(255, 120, 89));
        tip.setColor(Color.WHITE);
        //填充区域并确定区域大小位置
        tip.fillRect(0, 0, imageWidth, imageHeight);
        //设置字体颜色，先设置颜色，再填充内容
        tip.setColor(Color.RED);
        //设置字体
        Font tipFont = new Font("宋体", Font.BOLD, 24);
        tip.setFont(tipFont);
        tip.drawString(name, 60, imageHeight / 2 - 10);
        tip.drawString("声明有效", 120, imageHeight / 2 + 15);

        tip.setColor(Color.RED);
        tip.setStroke(new BasicStroke(8));
        tip.drawRoundRect(0, 0, imageWidth - 1, imageHeight - 1, 40, 40);
        createImage(imgurl);
    }
}
