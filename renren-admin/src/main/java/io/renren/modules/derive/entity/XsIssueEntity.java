package io.renren.modules.derive.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-10-23 14:25:06
 */
@TableName("xs_issue")
public class XsIssueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 文件名
	 */
	private String fileTitle;
	/**
	 * 发行人
	 */
	private String companyMain;
	/**
	 * 甲方公司新名字
	 */
	private String companyMainNew;
	/**
	 * 法定代表人
	 */
	private String representativeMan;
	/**
	 * 董事会秘书
	 */
	private String secMan;
	/**
	 * 董事/监事/高级管理人员
	 */
	private String djg;
	/**
	 * 年份
	 */
	private String projectYear;
	/**
	 * 发布时间
	 */
	private String releaseTime;
	/**
	 * 会计师事务所
	 */
	private String accounting;
	/**
	 * 会计师事务所新名字
	 */
	private String accountingNew;
	/**
	 * 审计费用
	 */
	private String accountingCost;
	/**
	 * 经办会计师
	 */
	private String accountMan1;
	/**
	 * 
	 */
	private String accountMan2;
	/**
	 * 
	 */
	private String accountMan3;
	/**
	 * 
	 */
	private String accountMan4;
	/**
	 * 
	 */
	private String accountMan5;
	/**
	 * 
	 */
	private String accountMan6;
	/**
	 * 
	 */
	private String accountMan7;
	/**
	 * 
	 */
	private String accountMan8;
	/**
	 * 保荐人/主承销商
	 */
	private String sponsor;
	/**
	 * 保荐机构新名字
	 */
	private String sponsorNew;
	/**
	 * 保荐及承销费用
	 */
	private String sponsorCost;
	/**
	 * 保荐代表人
	 */
	private String sponsorMan1;
	/**
	 * 保荐代表人2
	 */
	private String sponsorMan2;
	/**
	 * 
	 */
	private String sponsorMan3;
	/**
	 * 
	 */
	private String sponsorMan4;
	/**
	 * 
	 */
	private String sponsorMan5;
	/**
	 * 
	 */
	private String sponsorMan6;
	/**
	 * 
	 */
	private String sponsorMan7;
	/**
	 * 
	 */
	private String sponsorMan8;
	/**
	 * 律师事务所
	 */
	private String lawyer;
	/**
	 * 律师事务所新名字
	 */
	private String lawyerNew;
	/**
	 * 律师费用
	 */
	private String lawyerCost;
	/**
	 * 经办律师
	 */
	private String lawyerMan1;
	/**
	 * 律师2
	 */
	private String lawyerMan2;
	/**
	 * 律师3
	 */
	private String lawyerMan3;
	/**
	 * 律师4
	 */
	private String lawyerMan4;
	/**
	 * 律师5
	 */
	private String lawyerMan5;
	/**
	 * 律师6
	 */
	private String lawyerMan6;
	/**
	 * 律师7
	 */
	private String lawyerMan7;
	/**
	 * 律师8
	 */
	private String lawyerMan8;
	/**
	 * 资产评估机构
	 */
	private String apperaisal;
	/**
	 * 资产评估机构新名字
	 */
	private String apperaisalNew;
	/**
	 * 评估费用
	 */
	private String apperaisalCost;
	/**
	 * 经办评估师
	 */
	private String apperaisalMan1;
	/**
	 * 
	 */
	private String apperaisalMan2;
	/**
	 * 
	 */
	private String apperaisalMan3;
	/**
	 * 
	 */
	private String apperaisalMan4;
	/**
	 * 
	 */
	private String apperaisalMan5;
	/**
	 * 
	 */
	private String apperaisalMan6;
	/**
	 * 
	 */
	private String apperaisalMan7;
	/**
	 * 
	 */
	private String apperaisalMan8;
	/**
	 * 基金中文名称
	 */
	private String fund;
	/**
	 * 基金费用
	 */
	private String fundCost;
	/**
	 * 基金管理人
	 */
	private String fundManager;
	/**
	 * 基金经理1
	 */
	private String fundMan1;
	/**
	 * 基金经理2
	 */
	private String fundMan2;
	/**
	 * 基金经理3
	 */
	private String fundMan3;
	/**
	 * 基金经理4
	 */
	private String fundMan4;
	/**
	 * 基金经理5
	 */
	private String fundMan5;
	/**
	 * 基金经理6
	 */
	private String fundMan6;
	/**
	 * 基金经理7
	 */
	private String fundMan7;
	/**
	 * 基金经理8
	 */
	private String fundMan8;
	/**
	 * 备用1
	 */
	private String backup1;
	/**
	 * 备用2
	 */
	private String backup2;
	/**
	 * 备用3
	 */
	private String backup3;
	/**
	 * 备用4
	 */
	private String backup4;
	/**
	 * 备用5
	 */
	private String backup5;
	/**
	 * 甲方公司总表id
	 */
	private String companyMainId;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：文件名
	 */
	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}
	/**
	 * 获取：文件名
	 */
	public String getFileTitle() {
		return fileTitle;
	}
	/**
	 * 设置：发行人
	 */
	public void setCompanyMain(String companyMain) {
		this.companyMain = companyMain;
	}
	/**
	 * 获取：发行人
	 */
	public String getCompanyMain() {
		return companyMain;
	}
	/**
	 * 设置：甲方公司新名字
	 */
	public void setCompanyMainNew(String companyMainNew) {
		this.companyMainNew = companyMainNew;
	}
	/**
	 * 获取：甲方公司新名字
	 */
	public String getCompanyMainNew() {
		return companyMainNew;
	}
	/**
	 * 设置：法定代表人
	 */
	public void setRepresentativeMan(String representativeMan) {
		this.representativeMan = representativeMan;
	}
	/**
	 * 获取：法定代表人
	 */
	public String getRepresentativeMan() {
		return representativeMan;
	}
	/**
	 * 设置：董事会秘书
	 */
	public void setSecMan(String secMan) {
		this.secMan = secMan;
	}
	/**
	 * 获取：董事会秘书
	 */
	public String getSecMan() {
		return secMan;
	}
	/**
	 * 设置：董事/监事/高级管理人员
	 */
	public void setDjg(String djg) {
		this.djg = djg;
	}
	/**
	 * 获取：董事/监事/高级管理人员
	 */
	public String getDjg() {
		return djg;
	}
	/**
	 * 设置：年份
     * @param projectYear
     */
	public void setProjectYear(String projectYear) {
		this.projectYear = projectYear;
	}
	/**
	 * 获取：年份
	 */
	public String getProjectYear() {
		return projectYear;
	}
	/**
	 * 设置：发布时间
	 */
	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}
	/**
	 * 获取：发布时间
	 */
	public String getReleaseTime() {
		return releaseTime;
	}
	/**
	 * 设置：会计师事务所
	 */
	public void setAccounting(String accounting) {
		this.accounting = accounting;
	}
	/**
	 * 获取：会计师事务所
	 */
	public String getAccounting() {
		return accounting;
	}
	/**
	 * 设置：会计师事务所新名字
	 */
	public void setAccountingNew(String accountingNew) {
		this.accountingNew = accountingNew;
	}
	/**
	 * 获取：会计师事务所新名字
	 */
	public String getAccountingNew() {
		return accountingNew;
	}
	/**
	 * 设置：审计费用
	 */
	public void setAccountingCost(String accountingCost) {
		this.accountingCost = accountingCost;
	}
	/**
	 * 获取：审计费用
	 */
	public String getAccountingCost() {
		return accountingCost;
	}
	/**
	 * 设置：经办会计师
	 */
	public void setAccountMan1(String accountMan1) {
		this.accountMan1 = accountMan1;
	}
	/**
	 * 获取：经办会计师
	 */
	public String getAccountMan1() {
		return accountMan1;
	}
	/**
	 * 设置：
	 */
	public void setAccountMan2(String accountMan2) {
		this.accountMan2 = accountMan2;
	}
	/**
	 * 获取：
	 */
	public String getAccountMan2() {
		return accountMan2;
	}
	/**
	 * 设置：
	 */
	public void setAccountMan3(String accountMan3) {
		this.accountMan3 = accountMan3;
	}
	/**
	 * 获取：
	 */
	public String getAccountMan3() {
		return accountMan3;
	}
	/**
	 * 设置：
	 */
	public void setAccountMan4(String accountMan4) {
		this.accountMan4 = accountMan4;
	}
	/**
	 * 获取：
	 */
	public String getAccountMan4() {
		return accountMan4;
	}
	/**
	 * 设置：
	 */
	public void setAccountMan5(String accountMan5) {
		this.accountMan5 = accountMan5;
	}
	/**
	 * 获取：
	 */
	public String getAccountMan5() {
		return accountMan5;
	}
	/**
	 * 设置：
	 */
	public void setAccountMan6(String accountMan6) {
		this.accountMan6 = accountMan6;
	}
	/**
	 * 获取：
	 */
	public String getAccountMan6() {
		return accountMan6;
	}
	/**
	 * 设置：
	 */
	public void setAccountMan7(String accountMan7) {
		this.accountMan7 = accountMan7;
	}
	/**
	 * 获取：
	 */
	public String getAccountMan7() {
		return accountMan7;
	}
	/**
	 * 设置：
	 */
	public void setAccountMan8(String accountMan8) {
		this.accountMan8 = accountMan8;
	}
	/**
	 * 获取：
	 */
	public String getAccountMan8() {
		return accountMan8;
	}
	/**
	 * 设置：保荐人/主承销商
	 */
	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}
	/**
	 * 获取：保荐人/主承销商
	 */
	public String getSponsor() {
		return sponsor;
	}
	/**
	 * 设置：保荐机构新名字
	 */
	public void setSponsorNew(String sponsorNew) {
		this.sponsorNew = sponsorNew;
	}
	/**
	 * 获取：保荐机构新名字
	 */
	public String getSponsorNew() {
		return sponsorNew;
	}
	/**
	 * 设置：保荐及承销费用
	 */
	public void setSponsorCost(String sponsorCost) {
		this.sponsorCost = sponsorCost;
	}
	/**
	 * 获取：保荐及承销费用
	 */
	public String getSponsorCost() {
		return sponsorCost;
	}
	/**
	 * 设置：保荐代表人
	 */
	public void setSponsorMan1(String sponsorMan1) {
		this.sponsorMan1 = sponsorMan1;
	}
	/**
	 * 获取：保荐代表人
	 */
	public String getSponsorMan1() {
		return sponsorMan1;
	}
	/**
	 * 设置：保荐代表人2
	 */
	public void setSponsorMan2(String sponsorMan2) {
		this.sponsorMan2 = sponsorMan2;
	}
	/**
	 * 获取：保荐代表人2
	 */
	public String getSponsorMan2() {
		return sponsorMan2;
	}
	/**
	 * 设置：
	 */
	public void setSponsorMan3(String sponsorMan3) {
		this.sponsorMan3 = sponsorMan3;
	}
	/**
	 * 获取：
	 */
	public String getSponsorMan3() {
		return sponsorMan3;
	}
	/**
	 * 设置：
	 */
	public void setSponsorMan4(String sponsorMan4) {
		this.sponsorMan4 = sponsorMan4;
	}
	/**
	 * 获取：
	 */
	public String getSponsorMan4() {
		return sponsorMan4;
	}
	/**
	 * 设置：
	 */
	public void setSponsorMan5(String sponsorMan5) {
		this.sponsorMan5 = sponsorMan5;
	}
	/**
	 * 获取：
	 */
	public String getSponsorMan5() {
		return sponsorMan5;
	}
	/**
	 * 设置：
	 */
	public void setSponsorMan6(String sponsorMan6) {
		this.sponsorMan6 = sponsorMan6;
	}
	/**
	 * 获取：
	 */
	public String getSponsorMan6() {
		return sponsorMan6;
	}
	/**
	 * 设置：
	 */
	public void setSponsorMan7(String sponsorMan7) {
		this.sponsorMan7 = sponsorMan7;
	}
	/**
	 * 获取：
	 */
	public String getSponsorMan7() {
		return sponsorMan7;
	}
	/**
	 * 设置：
	 */
	public void setSponsorMan8(String sponsorMan8) {
		this.sponsorMan8 = sponsorMan8;
	}
	/**
	 * 获取：
	 */
	public String getSponsorMan8() {
		return sponsorMan8;
	}
	/**
	 * 设置：律师事务所
	 */
	public void setLawyer(String lawyer) {
		this.lawyer = lawyer;
	}
	/**
	 * 获取：律师事务所
	 */
	public String getLawyer() {
		return lawyer;
	}
	/**
	 * 设置：律师事务所新名字
	 */
	public void setLawyerNew(String lawyerNew) {
		this.lawyerNew = lawyerNew;
	}
	/**
	 * 获取：律师事务所新名字
	 */
	public String getLawyerNew() {
		return lawyerNew;
	}
	/**
	 * 设置：律师费用
	 */
	public void setLawyerCost(String lawyerCost) {
		this.lawyerCost = lawyerCost;
	}
	/**
	 * 获取：律师费用
	 */
	public String getLawyerCost() {
		return lawyerCost;
	}
	/**
	 * 设置：经办律师
	 */
	public void setLawyerMan1(String lawyerMan1) {
		this.lawyerMan1 = lawyerMan1;
	}
	/**
	 * 获取：经办律师
	 */
	public String getLawyerMan1() {
		return lawyerMan1;
	}
	/**
	 * 设置：律师2
	 */
	public void setLawyerMan2(String lawyerMan2) {
		this.lawyerMan2 = lawyerMan2;
	}
	/**
	 * 获取：律师2
	 */
	public String getLawyerMan2() {
		return lawyerMan2;
	}
	/**
	 * 设置：律师3
	 */
	public void setLawyerMan3(String lawyerMan3) {
		this.lawyerMan3 = lawyerMan3;
	}
	/**
	 * 获取：律师3
	 */
	public String getLawyerMan3() {
		return lawyerMan3;
	}
	/**
	 * 设置：律师4
	 */
	public void setLawyerMan4(String lawyerMan4) {
		this.lawyerMan4 = lawyerMan4;
	}
	/**
	 * 获取：律师4
	 */
	public String getLawyerMan4() {
		return lawyerMan4;
	}
	/**
	 * 设置：律师5
	 */
	public void setLawyerMan5(String lawyerMan5) {
		this.lawyerMan5 = lawyerMan5;
	}
	/**
	 * 获取：律师5
	 */
	public String getLawyerMan5() {
		return lawyerMan5;
	}
	/**
	 * 设置：律师6
	 */
	public void setLawyerMan6(String lawyerMan6) {
		this.lawyerMan6 = lawyerMan6;
	}
	/**
	 * 获取：律师6
	 */
	public String getLawyerMan6() {
		return lawyerMan6;
	}
	/**
	 * 设置：律师7
	 */
	public void setLawyerMan7(String lawyerMan7) {
		this.lawyerMan7 = lawyerMan7;
	}
	/**
	 * 获取：律师7
	 */
	public String getLawyerMan7() {
		return lawyerMan7;
	}
	/**
	 * 设置：律师8
	 */
	public void setLawyerMan8(String lawyerMan8) {
		this.lawyerMan8 = lawyerMan8;
	}
	/**
	 * 获取：律师8
	 */
	public String getLawyerMan8() {
		return lawyerMan8;
	}
	/**
	 * 设置：资产评估机构
	 */
	public void setApperaisal(String apperaisal) {
		this.apperaisal = apperaisal;
	}
	/**
	 * 获取：资产评估机构
	 */
	public String getApperaisal() {
		return apperaisal;
	}
	/**
	 * 设置：资产评估机构新名字
	 */
	public void setApperaisalNew(String apperaisalNew) {
		this.apperaisalNew = apperaisalNew;
	}
	/**
	 * 获取：资产评估机构新名字
	 */
	public String getApperaisalNew() {
		return apperaisalNew;
	}
	/**
	 * 设置：评估费用
	 */
	public void setApperaisalCost(String apperaisalCost) {
		this.apperaisalCost = apperaisalCost;
	}
	/**
	 * 获取：评估费用
	 */
	public String getApperaisalCost() {
		return apperaisalCost;
	}
	/**
	 * 设置：经办评估师
	 */
	public void setApperaisalMan1(String apperaisalMan1) {
		this.apperaisalMan1 = apperaisalMan1;
	}
	/**
	 * 获取：经办评估师
	 */
	public String getApperaisalMan1() {
		return apperaisalMan1;
	}
	/**
	 * 设置：
	 */
	public void setApperaisalMan2(String apperaisalMan2) {
		this.apperaisalMan2 = apperaisalMan2;
	}
	/**
	 * 获取：
	 */
	public String getApperaisalMan2() {
		return apperaisalMan2;
	}
	/**
	 * 设置：
	 */
	public void setApperaisalMan3(String apperaisalMan3) {
		this.apperaisalMan3 = apperaisalMan3;
	}
	/**
	 * 获取：
	 */
	public String getApperaisalMan3() {
		return apperaisalMan3;
	}
	/**
	 * 设置：
	 */
	public void setApperaisalMan4(String apperaisalMan4) {
		this.apperaisalMan4 = apperaisalMan4;
	}
	/**
	 * 获取：
	 */
	public String getApperaisalMan4() {
		return apperaisalMan4;
	}
	/**
	 * 设置：
	 */
	public void setApperaisalMan5(String apperaisalMan5) {
		this.apperaisalMan5 = apperaisalMan5;
	}
	/**
	 * 获取：
	 */
	public String getApperaisalMan5() {
		return apperaisalMan5;
	}
	/**
	 * 设置：
	 */
	public void setApperaisalMan6(String apperaisalMan6) {
		this.apperaisalMan6 = apperaisalMan6;
	}
	/**
	 * 获取：
	 */
	public String getApperaisalMan6() {
		return apperaisalMan6;
	}
	/**
	 * 设置：
	 */
	public void setApperaisalMan7(String apperaisalMan7) {
		this.apperaisalMan7 = apperaisalMan7;
	}
	/**
	 * 获取：
	 */
	public String getApperaisalMan7() {
		return apperaisalMan7;
	}
	/**
	 * 设置：
	 */
	public void setApperaisalMan8(String apperaisalMan8) {
		this.apperaisalMan8 = apperaisalMan8;
	}
	/**
	 * 获取：
	 */
	public String getApperaisalMan8() {
		return apperaisalMan8;
	}
	/**
	 * 设置：基金中文名称
	 */
	public void setFund(String fund) {
		this.fund = fund;
	}
	/**
	 * 获取：基金中文名称
	 */
	public String getFund() {
		return fund;
	}
	/**
	 * 设置：基金费用
	 */
	public void setFundCost(String fundCost) {
		this.fundCost = fundCost;
	}
	/**
	 * 获取：基金费用
	 */
	public String getFundCost() {
		return fundCost;
	}
	/**
	 * 设置：基金管理人
	 */
	public void setFundManager(String fundManager) {
		this.fundManager = fundManager;
	}
	/**
	 * 获取：基金管理人
	 */
	public String getFundManager() {
		return fundManager;
	}
	/**
	 * 设置：基金经理1
	 */
	public void setFundMan1(String fundMan1) {
		this.fundMan1 = fundMan1;
	}
	/**
	 * 获取：基金经理1
	 */
	public String getFundMan1() {
		return fundMan1;
	}
	/**
	 * 设置：基金经理2
	 */
	public void setFundMan2(String fundMan2) {
		this.fundMan2 = fundMan2;
	}
	/**
	 * 获取：基金经理2
	 */
	public String getFundMan2() {
		return fundMan2;
	}
	/**
	 * 设置：基金经理3
	 */
	public void setFundMan3(String fundMan3) {
		this.fundMan3 = fundMan3;
	}
	/**
	 * 获取：基金经理3
	 */
	public String getFundMan3() {
		return fundMan3;
	}
	/**
	 * 设置：基金经理4
	 */
	public void setFundMan4(String fundMan4) {
		this.fundMan4 = fundMan4;
	}
	/**
	 * 获取：基金经理4
	 */
	public String getFundMan4() {
		return fundMan4;
	}
	/**
	 * 设置：基金经理5
	 */
	public void setFundMan5(String fundMan5) {
		this.fundMan5 = fundMan5;
	}
	/**
	 * 获取：基金经理5
	 */
	public String getFundMan5() {
		return fundMan5;
	}
	/**
	 * 设置：基金经理6
	 */
	public void setFundMan6(String fundMan6) {
		this.fundMan6 = fundMan6;
	}
	/**
	 * 获取：基金经理6
	 */
	public String getFundMan6() {
		return fundMan6;
	}
	/**
	 * 设置：基金经理7
	 */
	public void setFundMan7(String fundMan7) {
		this.fundMan7 = fundMan7;
	}
	/**
	 * 获取：基金经理7
	 */
	public String getFundMan7() {
		return fundMan7;
	}
	/**
	 * 设置：基金经理8
	 */
	public void setFundMan8(String fundMan8) {
		this.fundMan8 = fundMan8;
	}
	/**
	 * 获取：基金经理8
	 */
	public String getFundMan8() {
		return fundMan8;
	}
	/**
	 * 设置：备用1
	 */
	public void setBackup1(String backup1) {
		this.backup1 = backup1;
	}
	/**
	 * 获取：备用1
	 */
	public String getBackup1() {
		return backup1;
	}
	/**
	 * 设置：备用2
	 */
	public void setBackup2(String backup2) {
		this.backup2 = backup2;
	}
	/**
	 * 获取：备用2
	 */
	public String getBackup2() {
		return backup2;
	}
	/**
	 * 设置：备用3
	 */
	public void setBackup3(String backup3) {
		this.backup3 = backup3;
	}
	/**
	 * 获取：备用3
	 */
	public String getBackup3() {
		return backup3;
	}
	/**
	 * 设置：备用4
	 */
	public void setBackup4(String backup4) {
		this.backup4 = backup4;
	}
	/**
	 * 获取：备用4
	 */
	public String getBackup4() {
		return backup4;
	}
	/**
	 * 设置：备用5
	 */
	public void setBackup5(String backup5) {
		this.backup5 = backup5;
	}
	/**
	 * 获取：备用5
	 */
	public String getBackup5() {
		return backup5;
	}
	/**
	 * 设置：甲方公司总表id
	 */
	public void setCompanyMainId(String companyMainId) {
		this.companyMainId = companyMainId;
	}
	/**
	 * 获取：甲方公司总表id
	 */
	public String getCompanyMainId() {
		return companyMainId;
	}
}
