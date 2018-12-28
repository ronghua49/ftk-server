package com.risepu.ftk.server.serviceImpl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.gson.Gson;
import com.risepu.ftk.server.service.SmsService;
import com.risepu.ftk.utils.VerifyCode;

@Service
public class SmsServiceImpl implements SmsService {

	private final Logger LOG=LoggerFactory.getLogger(getClass());
	
    @Value("${ftk.sms.appKey}")
    private String smsAppkey;
    @Value("${ftk.sms.secret}")
    private String smsSecret;
    @Value("${ftk.sms.signName}")
    private String smsSignName;
    
    @Override
    public boolean identify(String inCode, String createCode) {
        if (inCode.equals(createCode)) {
            return true;
        }
        return false;
    }

    @Override
    public String sendCode(String phone,String templateCode,Map<String, String>params) {
    	String verifyCode  =VerifyCode.genValidCode();
    	if(params!=null) {
    		params.put("idfcode", verifyCode);
    	}
        sendSms(new Gson().toJson(params), phone, templateCode);
        return verifyCode;
    }
    
    private Boolean sendSms(String params, String recNum, String templateCode) {
		try {
			// 设置超时时间-可自行调整
			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
			System.setProperty("sun.net.client.defaultReadTimeout", "10000");
			// 初始化ascClient需要的几个参数
			final String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
			final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
			// 初始化acsClient,暂不支持region化
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
					smsAppkey, smsSecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product,
					domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);

			// 组装请求对象-具体描述见控制台-文档部分内容
			SendSmsRequest request = new SendSmsRequest();
			// 使用post提交
			request.setMethod(MethodType.POST);
			// 必填:待发送手机号
			request.setPhoneNumbers(recNum);
			// 必填:短信签名-可在短信控制台中找到
			request.setSignName(smsSignName);
			// 必填:短信模板-可在短信控制台中找到
			request.setTemplateCode(templateCode);
			// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
			request.setTemplateParam(params);

			// hint 此处可能会抛出异常，注意catch
			SendSmsResponse response = acsClient.getAcsResponse(request);

			// TODO 发送结果处理
			if ("OK".equals(response.getCode())) {
				LOG.info("短信发送成功.");
				return true;
			} else {
				LOG.error("短信发送失败[RequestId={},Code={},Message={},BizId={}]",
						response.getRequestId(), response.getCode(),
						response.getMessage(), response.getBizId());
				return false;

			}
		} catch (Exception e) {
			LOG.error("短信发送异常", e);
			return false;
		}
	}
    
}
