
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.risepu.ftk.server.domain.Organization;
import com.risepu.ftk.server.domain.OrganizationUser;
import com.risepu.ftk.server.service.OrganizationService;
import com.risepu.ftk.web.b.dto.LoginResult;

import net.lc4ever.framework.service.GenericCrudService;

@Service
public class OrganizationServiceImpl implements OrganizationService{
	
	private static final String SALT = "$%)(P123";
	
	@Autowired
	private GenericCrudService crudService;

	public void setCrudService(GenericCrudService crudService) {
		this.crudService = crudService;
	}

	@Override
	public String orgReg(String phone,String password) {
			OrganizationUser org = new OrganizationUser();
			org.setId(phone);
			password= DigestUtils.md5Hex(password+SALT);
			org.setPassword(password);
			crudService.save(org);
	}

	@Override
	public LoginResult orgLogin(String phoneOrName, String password) {
		password= DigestUtils.md5Hex(password+SALT);
		LoginResult loginResult = new LoginResult();
		//判断输入的类型
		if(StringUtils.isNumeric(phoneOrName)) {
			//判断企业用户是否存在
			OrganizationUser org = crudService.uniqueResultByProperty(OrganizationUser.class, "id", phoneOrName);
			if(org!=null && org.getPassword().equals(password)) {
				//登录成功
				loginResult.setMessage("登录成功！");
				loginResult.setOrganizationUser(org);
			}else {
				loginResult.setMessage("登录成功！");
			}
		}else {
			//使用企业名登录
			Organization org = crudService.uniqueResultByProperty(Organization.class, "name",phoneOrName);
			
			if(org!=null) {
				OrganizationUser orgUser = crudService.uniqueResultByProperty(OrganizationUser.class, "id", org.getId());
				if(orgUser.getPassword().equals(password)) {
					//登录成功
					loginResult.setMessage("登录成功！");
					loginResult.setOrganizationUser(orgUser);
				}
				
			}else {
				
				loginResult.setMessage("企业名或者密码错误！");
			}
			
		}
		
		return loginResult;
	}

	/*在审核验证码通过后执行*/
	@Override
	public String changePwd(String phone, String newPwd) {
		OrganizationUser orgUser = new OrganizationUser();
		orgUser.setId(phone);
		orgUser.setPassword(newPwd);
		crudService.update(orgUser);
		return "success";
	}
	

	

}
