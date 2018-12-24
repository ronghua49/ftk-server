package com.risepu.ftk.server.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.risepu.ftk.server.domain.AuthorizationStream;
import com.risepu.ftk.server.domain.Organization;
import com.risepu.ftk.server.domain.OrganizationAdvice;
import com.risepu.ftk.server.domain.OrganizationUser;
import com.risepu.ftk.server.service.OrganizationService;
import com.risepu.ftk.utils.ConfigUtil;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.b.dto.LoginResult;

import net.lc4ever.framework.format.DateFormatter;
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
		String secutityPwd = DigestUtils.md5Hex(password + SALT);
		LoginResult loginResult = new LoginResult();
		
		if (StringUtils.isNumeric(phoneOrName)) {
			/** 使用手机号登录 */
			
				OrganizationUser org = crudService.uniqueResultByProperty(OrganizationUser.class, "id", phoneOrName);

				if(org==null) {
					loginResult.setCode(4);
					loginResult.setMessage("此手机号还未注册，请注册！");
				}
				
				if (org != null && org.getPassword().equals(secutityPwd)) {
					loginResult.setCode(0);
					loginResult.setMessage("登录成功！");
					loginResult.setOrganizationUser(org);

					/** 判断是否为审核通过的企业用户 */
					Organization organization=null;
					if(org.getOrganizationId()!=null){
						organization = crudService.uniqueResultByProperty(Organization.class, "id", org.getOrganizationId());
					}

					loginResult.setOrganization(organization);

					
				} else {
					loginResult.setCode(5);
					loginResult.setMessage("密码错误！");
				}


		} else {
			/** 使用企业名登录 */
			Organization org = crudService.uniqueResultByProperty(Organization.class, "name", phoneOrName);

			if (org != null) {
				
				OrganizationUser orgUser = crudService.uniqueResultByProperty(OrganizationUser.class, "organizationId",
						org.getId());
				
				if (orgUser.getPassword().equals(secutityPwd)) {
					loginResult.setCode(0);
					loginResult.setMessage("登录成功！");
					loginResult.setOrganization(org);
				}else {
					loginResult.setCode(5);
					loginResult.setMessage("密码错误！");
				}
			} else {
				loginResult.setCode(6);
				loginResult.setMessage("企业不存在，或未认证！");
			}
		}
		return loginResult;
	}

	@Override
	public void changePwd(String id, String newPwd) {
		OrganizationUser orgUser=crudService.get(OrganizationUser.class,id);
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
	public Organization findAuthenOrgById(String id) {
		
		return crudService.get(Organization.class, id);
	}

	@Override
	public void InsertAuthorStream(String orgId, String cardNo) {
		
		AuthorizationStream stream = new AuthorizationStream();
		
		stream.setOrgId(orgId);
		stream.setPersonId(cardNo);
		stream.setState(AuthorizationStream.AUTH_STATE_NEW);
		crudService.save(stream);
	}

	@Override
	public PageResult<Organization> findByParam(Map<String, Object> map, Integer pageNo, Integer pageSize) {
		Integer firstIndex=(pageNo)*pageSize;
	
		String hql = "";
		String hql2 = "select count(*) ";
		int total=0;
		List<Organization> orgs=new ArrayList<Organization>();
		
		String key=(String) map.get("key");
		String startTime = (String) map.get("startTime");
		String endTime = (String) map.get("endTime");
		
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate=null;
		Date endDate=null;
		Date nextDate =null;
		
		if(startTime!=""&&startTime!=null) {
			try {
				startDate = format.parse(startTime);
				endDate = format.parse(endTime);
				
				nextDate= DateFormatter.startOfDay(DateFormatter.nextDay(endDate));
				
				System.out.println("开始时间："+startDate);
				System.out.println("结束时间"+nextDate);
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		Integer state = (Integer) map.get("state");
		
		if(key!=""&&key!=null&&state==null&&startDate==null) {
			
			hql = "from Organization where name like ?1 order by createTimestamp desc";
					
			total = crudService.uniqueResultHql(Long.class, hql2+hql,"%"+key+"%").intValue();
			orgs = crudService.hql(Organization.class, firstIndex,pageSize, hql, "%"+key+"%");
			
		}else if(key!=""&&key!=null&&state!=null&&startDate==null ) {
			hql = "from Organization where name like ?1 and state=?2 order by createTimestamp desc";
			
			total=crudService.uniqueResultHql(Long.class, hql2+hql,"%"+key+"%",state).intValue();
			orgs = crudService.hql(Organization.class, firstIndex,pageSize, hql, "%"+key+"%",state);
			
		}else if(key!=""&&key!=null&&state!=null&&startDate!=null ) {
			
			hql="from Organization where name like ?1 and state=?2 and createTimestamp between ?3 and ?4 order by createTimestamp desc";
			
			total=crudService.uniqueResultHql(Long.class, hql2+hql, "%"+key+"%",state,startDate,nextDate).intValue();
			orgs = crudService.hql(Organization.class, firstIndex,pageSize, hql, "%"+key+"%",state,startDate,nextDate);
			
		}else if(key!=""&&key!=null&&state==null&&startDate!=null) {
			
			hql = "from Organization where createTimestamp between ?1 and ?2 order by createTimestamp desc";
			
			total=crudService.uniqueResultHql(Long.class, hql2+hql, startDate,nextDate).intValue();
			orgs = crudService.hql(Organization.class, firstIndex,pageSize, hql, startDate,nextDate );
			
		}else if(key!=""&&key!=null&&state!=null&&startDate!=null) {
			
			hql = "from Organization where state=?1 and createTimestamp between ?2 and ?3 order by createTimestamp desc";
			
			total=crudService.uniqueResultHql(Long.class, hql2+hql, state,startDate,nextDate).intValue();
			orgs = crudService.hql(Organization.class,firstIndex,pageSize,  hql, state,startDate,nextDate);
			
		}else if(key!=""&&key!=null&&state!=null&&startDate==null) {
			hql = "from Organization where state=?1 order by createTimestamp desc";
			
			total=crudService.uniqueResultHql(Long.class, hql2+hql, state).intValue();
			orgs = crudService.hql(Organization.class,firstIndex,pageSize, hql, state);
		}else {
			hql="from Organization order by createTimestamp desc";
			
			total=crudService.uniqueResultHql(Long.class, hql2+hql).intValue();
			orgs = crudService.hql(Organization.class,firstIndex,pageSize,hql);
		}
			PageResult<Organization> pageResult = new PageResult<>();
			pageResult.setResultCode("SUCCESS");
			pageResult.setNumber(pageNo);
			pageResult.setSize(pageSize);
			pageResult.setTotalPages(total, pageSize);
			pageResult.setTotalElements(total);
			pageResult.setContent(orgs);
			return pageResult;
	}

	/**
	 * 修改企业用户信息
	 *
	 * @param user
	 */
	@Override
	public void updateOrgUser(OrganizationUser user) {
		crudService.update(user);
	}

	/**
	 * 根据id查询OrganizationUser
	 *
	 * @param id
	 * @return
	 */
	@Override
	public OrganizationUser findOrgUserById(String id) {
		return crudService.get(OrganizationUser.class,id);
	}


}
