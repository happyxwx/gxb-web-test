package com.kkb.test.steps.pc.gxb.old;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.kkb.test.actions.pc.gxb.CommonCourseLearnAct;
import com.kkb.test.actions.pc.gxb.old.LoginAct;
import com.kkb.test.steps.AbstractStep;
import com.kkb.test.util.PropertiesBundle;
import com.kkb.test.util.WebdriverFactory;

/**
 * 
 * @author sh.deng 
 * <p>华南理工学院|广州学院
 * @date 2015-09-14
 * @version 1.0
 */
public class OldCoursePlayStep extends AbstractStep {
	/**
	 * slf4j
	 */
	private final static Logger logger = LoggerFactory
			.getLogger(OldCoursePlayStep.class);
	//个人中心页面动作
	private CommonCourseLearnAct commonCourseLearnAct;
	


	/**
	 * 高校帮-个人中心-个人信息设置
	 * 
	 * @throws Exception
	 */
	@Parameters({"baseUrl","loginName","password","courseName" , "courseNameUrl" })
	@Test(description = "查看视频播放 " , priority = 1)
	public void testVideoPaly(String baseUrl,String loginName,String password,String courseName ,String courseNameUrl) throws Exception {
		try {
			userLogin(baseUrl,loginName, password ,courseNameUrl);
			commonCourseLearnAct = new CommonCourseLearnAct(driver);
			StringBuilder errorCourse = new StringBuilder("");
			//driver.navigate().to(courseNameUrl);

			commonCourseLearnAct.clickStartStudy();
			commonCourseLearnAct.pause(5);
			commonCourseLearnAct.clickStartLearn();
			commonCourseLearnAct.refresh();
			commonCourseLearnAct.snapshot();
			//获取所有章节
			String errorCourseList = commonCourseLearnAct.all(errorCourse,courseName);
			if(errorCourseList.length()>4){
				logger.info(courseName+"出错的视频有：");
				logger.info(errorCourseList);
				Assert.fail(errorCourseList);
			}
			logger.info("查看视频播放-测试用例执行结束！");
		
		} catch (Exception e) {
			logger.error("查看视频播放-"+courseName, e);
			throw new Exception("查看视频播放-"+courseName+"  >> "
					+ e.getMessage(), e);
		}

	}
	/**
	 * gxb 用户登录
	 */
	public void userLogin(String baseUrl ,String loginName,String password ,String courseNameUrl){
		try {
			p = PropertiesBundle.loadProperties(PropertiesBundle.ALL);

			driver = new WebdriverFactory().newInstanceOf(p.getProperty(BROWSER), p);
			driver.get(baseUrl);
			LoginAct l = new LoginAct(driver);
			int i = 0;
			/*while(i<5&&!l.checkLogin()){
				logger.info("状态为：未登录");*/
				l.clickToLogin();
				l.inputUserName(loginName);
				l.inputPassword(password);
				l.clickLogin();
				l.pause(3);
				
				if(driver.getPageSource().contains("跳过")){
				driver.findElement(By.linkText("跳过")).click();}
				if(driver.getPageSource().contains("跳过")){
					driver.findElement(By.linkText("跳过")).click();}
				driver.navigate().to(courseNameUrl);
				l.clickEnterCourse();

			/*	i++;
			}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("登录失败！");
			e.printStackTrace();
		}

	}

}