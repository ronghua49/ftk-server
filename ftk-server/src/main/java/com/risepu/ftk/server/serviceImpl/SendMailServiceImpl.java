package com.risepu.ftk.server.serviceImpl;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.risepu.ftk.server.service.SendMailService;

/**
 * @author L-heng
 */
@Service
public class SendMailServiceImpl implements SendMailService {

    @Override
    public void sendMail(String email, String filePath, String title) throws Exception {
        Properties prop = new Properties();
        prop.setProperty("mail.host", "smtp.mxhichina.com");
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");

        // 使用JavaMail发送邮件的5个步骤
        // 1、创建session
        Session session = Session.getInstance(prop);
        // 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        // 2、通过session得到transport对象
        Transport ts = session.getTransport();
        // 3、连上邮件服务器
        ts.connect("smtp.mxhichina.com", "service@risepu.com", "Spkj123456");
        // 4、创建邮件
//		    Message message = createAttachMail(session);
        MimeMessage message = new MimeMessage(session);
        // 设置邮件的基本信息
        // 发件人
        message.setFrom(new InternetAddress("service@risepu.com"));
        // 收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        // 邮件标题
        message.setSubject("职场通行证-" + title);
        // 创建邮件正文，为了避免邮件正文中文乱码问题，需要使用charset=UTF-8指明字符编码
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("<br>" +
                "您好：<br>" +
                "    您办理的证明文件已经开具完成，请用pdf工具打开查看！\n" +
                "<br>" +
                "<br>" +
                "<br>" +
                "<br>" +
                "<br>" +
                "<br>" +
                "<br>" +
                "-------------------------------------------<br>" +
                "公司名称：北京升谱科技有限公司<br>" +
                "公司网址：http://www.risepu.com<br>" +
                "公司公众号：职场通行证<br>" +
                "公司热线：010-53357562", "text/html;charset=UTF-8");
        // 创建邮件附件
        MimeBodyPart attach = new MimeBodyPart();
//			DataHandler dh = new DataHandler(new FileDataSource("src\\2.jpg"));
        DataHandler dh = new DataHandler(new FileDataSource(filePath));
        attach.setDataHandler(dh);
        attach.setFileName(MimeUtility.encodeText(dh.getName()));
        // 创建容器描述数据关系
        MimeMultipart mp = new MimeMultipart();
        mp.addBodyPart(text);
        mp.addBodyPart(attach);
        mp.setSubType("mixed");
        message.setContent(mp);
        message.saveChanges();
        // 将创建的Email写入到E盘存储
        // message.writeTo(new FileOutputStream("E:\\attachMail.eml"));
        // 5、发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }
}
