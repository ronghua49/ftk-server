
一.

项目说明:
	commons-api: 公共引用类库
	ftk-chain: 区块链代码部分
	ftk-chain-api: 区块链API
	
	ftk-server: 后台业务逻辑
	ftk-server-api: 业务API
	
	ftk-web-b: 企业端web-api
	ftk-web-p: 个人端web-api
	ftk-web-m: 管理端web-api

引用关系:
	根下build.gradle文件内添加的依赖，影响所有项目
	commons-api项目被所有其他项目依赖
	
	ftk-chain-api被ftk-server引用
	
	ftk-server-api被ftk-web-*引用
	
	在开发web端时, 将所有domain, dto类放在ftk-server-api下
	


gradle 安装

JDK: 8.0
eclipse-jee: 
	General->Keys:
		Content Assist: 修改为Alt-/
		Word Completion: 修改为空
	

