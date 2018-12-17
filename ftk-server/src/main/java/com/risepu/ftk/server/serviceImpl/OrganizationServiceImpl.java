package com.risepu.ftk.server.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.risepu.ftk.server.domain.Organization;
import com.risepu.ftk.server.domain.OrganizationAdvice;
import com.risepu.ftk.server.domain.OrganizationUser;
import com.risepu.ftk.server.service.OrganizationService;
import com.risepu.ftk.web.ConfigUtil;
import com.risepu.ftk.web.b.dto.LoginResult;

import net.lc4ever.framework.service.GenericCrudService;

@Service
public class OrganizationServiceImpl implements OrganizationService {

	private static final String SALT = ConfigUtil.getValue("salt"); 

	@Autowired
	private GenericCrudService crudService;

	public void setCrudService(GenericCrudService crudService) {
		this.crudService = crudService;
	}

	@Override
	public String orgReg(String phone, String password) {
		OrganizationUser org = new OrganizationUser();
		org.setId(phone);
		password = DigestUtils.md5Hex(password + SALT);
		org.setPassword(password);
		crudService.save(org);
		return "success";
	}

	@Override
	public LoginResult orgLogin(String phoneOrName, String password) {
		password = DigestUtils.md5Hex(password + SALT);
		LoginResult loginResult = new LoginResult();

		if (StringUtils.isNumeric(phoneOrName)) {
			/** 使用手机号登录 */
			try {
				OrganizationUser org = crudService.uniqueResultByProperty(OrganizationUser.class, "id", phoneOrName);

				if (org != null && org.getPassword().equals(password)) {
					loginResult.setSuccess(true);
					loginResult.setMessage("登录成功！");
					loginResult.setOrganizationUser(org);
				} else {
					loginResult.setSuccess(false);
					loginResult.setMessage("密码错误！");
				}

			} catch (Exception e) {
				loginResult.setSuccess(false);
				loginResult.setMessage("此手机号还未注册，请注册！");
			}

		} else {
			/** 使用企业名登录 */
			Organization org = crudService.uniqueResultByProperty(Organization.class, "name", phoneOrName);

			if (org != null) {
				OrganizationUser orgUser = crudService.uniqueResultByProperty(OrganizationUser.class, "id",
						org.getId());
				if (orgUser.getPassword().equals(password)) {

					loginResult.setSuccess(true);
					loginResult.setMessage("登录成功！");
					loginResult.setOrganizationUser(orgUser);
				}
			} else {
				loginResult.setSuccess(false);
				loginResult.setMessage("企业名或者密码错误！");
			}
		}
		return loginResult;
	}

	@Override
	public void changePwd(String id, String newPwd) {
		
		newPwd = DigestUtils.md5Hex(newPwd+SALT);

		OrganizationUser orgUser = new OrganizationUser();
		orgUser.setId(id);
		orgUser.setPassword(newPwd);
		crudService.update(orgUser);
	}

	@Override
	public String upload(MultipartFile file) throws IllegalStateException, IOException {
		String name = UUID.randomUUID().toString().replaceAll("-", "");
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		name = name + "." + ext;
		/** 上传图片到指定地址路径 */
		file.transferTo(new File(ConfigUtil.getValue("file.upload.path"), name));
		return name;
	}

	@Override
	public void download(String imgName, HttpServletResponse response) throws IOException {

		String filePath = ConfigUtil.getValue("file.upload.path");

		InputStream in = new FileInputStream(new File(filePath, imgName));
		OutputStream out = response.getOutputStream();

		IOUtils.copy(in, out);
		out.flush();
		out.close();
		in.close();

	}

	@Override
	public void saveOrUpdateOrgInfo(Organization organization) {
		crudService.saveOrUpdate(organization);
	}

	@Override
	public String checkOrgName(String mobileOrName) {

		if (StringUtils.isNumeric(mobileOrName)) {
			OrganizationUser user = crudService.uniqueResultByProperty(OrganizationUser.class, "id", mobileOrName);
			if (user != null) {
				return user.getId();
			}
		} else {
			Organization user = crudService.uniqueResultByProperty(Organization.class, "name", mobileOrName);
			if (user != null) {
				return user.getId();
			}
		}

		return null;
	}

	@Override
	public void saveAdviceInfo(OrganizationAdvice advice) {
		crudService.save(advice);
	}

	@Override
	public Organization checkAuthState(String id) {
		return crudService.uniqueResultByProperty(Organization.class, "id", id);
	}

	@Override
	public void InsertAuthorStream(String orgId, String personId) {
		// TODO Auto-generated method stub
		
	}

	
}
