package com.ajy.web.listener;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.aiiju.service.IBasicEnumService;
import com.aiiju.service.IDutyManageService;
import com.aiiju.service.IFileUploadService;
import com.aiiju.service.IHomeService;
import com.aiiju.service.IPermissionManageService;
import com.aiiju.service.IPositionManageService;
import com.aiiju.service.dept.IDepartmentPersonNumService;
import com.aiiju.service.dept.IDepartmentPrincipalService;
import com.aiiju.service.dept.IDepartmentService;
import com.aiiju.service.salary.ISpwSalaryChangeService;
import com.aiiju.service.salary.ISpwSalaryPayService;
import com.aiiju.service.staff.IContractAgreementService;
import com.aiiju.service.staff.IEducationLogService;
import com.aiiju.service.staff.IJobStatusLogService;
import com.aiiju.service.staff.ILanguageAbilityLogService;
import com.aiiju.service.staff.IPersonnelAffairsService;
import com.aiiju.service.staff.IProfessionJobQualificationService;
import com.aiiju.service.staff.IProfessionSkillQualificationService;
import com.aiiju.service.staff.IReportRelationService;
import com.aiiju.service.staff.ISocietyRelationsService;
import com.aiiju.service.staff.IUserDutyLevelLogService;
import com.aiiju.service.staff.IUserService;
import com.aiiju.service.staff.IWorkCompanyLogService;
import com.aiiju.service.workStage.IAnnouncementService;
import com.aiiju.service.workStage.IFlowInfoService;
import com.aiiju.service.workStage.IReplyService;
import com.aiiju.service.workStage.ITaskInfoService;

/**
 * 
 * @ClassName: ContextListener
 * @Description:存放services
 * @author 小飞
 * @date 2016年7月22日 上午11:56:29
 */
public class ServicesListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		Map<String, Class> services = new HashMap<String, Class>();
		services = new HashMap<String, Class>();
		services.put("dutyManage", IDutyManageService.class);
		services.put("positionManage", IPositionManageService.class);
		services.put("department", IDepartmentService.class);
		services.put("basicEnum", IBasicEnumService.class);
		services.put("contractAgreement", IContractAgreementService.class);
		services.put("educationLog", IEducationLogService.class);
		services.put("jobStatusLog", IJobStatusLogService.class);
		services.put("languageAbilityLog", ILanguageAbilityLogService.class);
		services.put("personnelAffairs", IPersonnelAffairsService.class);
		services.put("professionJobQualification", IProfessionJobQualificationService.class);
		services.put("professionSkillQualification", IProfessionSkillQualificationService.class);
		services.put("reportRelation", IReportRelationService.class);
		services.put("societyRelations", ISocietyRelationsService.class);
		services.put("userDutyLevelLog", IUserDutyLevelLogService.class);
		services.put("workCompanyLog", IWorkCompanyLogService.class);
		services.put("user", IUserService.class);
		services.put("fileUpload", IFileUploadService.class);
		services.put("departmentPrincipal", IDepartmentPrincipalService.class);
		services.put("departmentPersonNum", IDepartmentPersonNumService.class);
		services.put("spwSalaryPay", ISpwSalaryPayService.class);
		services.put("home", IHomeService.class);
		services.put("permissionManage", IPermissionManageService.class);
		services.put("spwSalaryChange", ISpwSalaryChangeService.class);
		services.put("reply", IReplyService.class);
		services.put("announcement", IAnnouncementService.class);
		services.put("taskInfo", ITaskInfoService.class);
		services.put("flowInfo", IFlowInfoService.class);
		event.getServletContext().setAttribute("services", services);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
