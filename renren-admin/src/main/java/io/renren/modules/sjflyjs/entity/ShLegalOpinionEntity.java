package io.renren.modules.sjflyjs.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-13 11:45:38
 */
@TableName("sh_legal_opinion")
public class ShLegalOpinionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long id;
	/**
	 * 文件名称
	 */
	private String fvFileName;
	/**
	 * 文件标题
	 */
	private String fvFileTitle;
	/**
	 * 律师事务所
	 */
	private String fvLowerFirm;
	/**
	 * 公司名称
	 */
	private String fvCompanyNameNew;
	/**
	 * 公司名称
	 */
	private String fvCompanyName;
	/**
	 * 律师事务所
	 */
	private String fvLowerFirmNew;
	/**
	 * 律师负责人
	 */
	private String fvChargePerson;
	/**
	 * 经办律师1
	 */
	private String fvLower1;
	/**
	 * 经办律师2
	 */
	private String fvLower2;
	/**
	 * 经办律师3
	 */
	private String fvLower3;
	/**
	 * 经办律师4
	 */
	private String fvLower4;
	/**
	 * 经办律师5
	 */
	private String fvLower5;
	/**
	 * 授权代表
	 */
	private String fvAuthorizer;
	/**
	 * 签字日期
	 */
	private String fvDate;
	/**
	 * 法律意见书类型
	 */
	private String fvType;

	/**
	 * 设置：主键
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：文件名称
	 */
	public void setFvFileName(String fvFileName) {
		this.fvFileName = fvFileName;
	}
	/**
	 * 获取：文件名称
	 */
	public String getFvFileName() {
		return fvFileName;
	}
	/**
	 * 设置：文件标题
	 */
	public void setFvFileTitle(String fvFileTitle) {
		this.fvFileTitle = fvFileTitle;
	}
	/**
	 * 获取：文件标题
	 */
	public String getFvFileTitle() {
		return fvFileTitle;
	}
	/**
	 * 设置：律师事务所
	 */
	public void setFvLowerFirm(String fvLowerFirm) {
		this.fvLowerFirm = fvLowerFirm;
	}
	/**
	 * 获取：律师事务所
	 */
	public String getFvLowerFirm() {
		return fvLowerFirm;
	}
	/**
	 * 设置：公司名称
	 */
	public void setFvCompanyNameNew(String fvCompanyNameNew) {
		this.fvCompanyNameNew = fvCompanyNameNew;
	}
	/**
	 * 获取：公司名称
	 */
	public String getFvCompanyNameNew() {
		return fvCompanyNameNew;
	}
	/**
	 * 设置：公司名称
	 */
	public void setFvCompanyName(String fvCompanyName) {
		this.fvCompanyName = fvCompanyName;
	}
	/**
	 * 获取：公司名称
	 */
	public String getFvCompanyName() {
		return fvCompanyName;
	}
	/**
	 * 设置：律师事务所
	 */
	public void setFvLowerFirmNew(String fvLowerFirmNew) {
		this.fvLowerFirmNew = fvLowerFirmNew;
	}
	/**
	 * 获取：律师事务所
	 */
	public String getFvLowerFirmNew() {
		return fvLowerFirmNew;
	}
	/**
	 * 设置：律师负责人
	 */
	public void setFvChargePerson(String fvChargePerson) {
		this.fvChargePerson = fvChargePerson;
	}
	/**
	 * 获取：律师负责人
	 */
	public String getFvChargePerson() {
		return fvChargePerson;
	}
	/**
	 * 设置：经办律师1
	 */
	public void setFvLower1(String fvLower1) {
		this.fvLower1 = fvLower1;
	}
	/**
	 * 获取：经办律师1
	 */
	public String getFvLower1() {
		return fvLower1;
	}
	/**
	 * 设置：经办律师2
	 */
	public void setFvLower2(String fvLower2) {
		this.fvLower2 = fvLower2;
	}
	/**
	 * 获取：经办律师2
	 */
	public String getFvLower2() {
		return fvLower2;
	}
	/**
	 * 设置：经办律师3
	 */
	public void setFvLower3(String fvLower3) {
		this.fvLower3 = fvLower3;
	}
	/**
	 * 获取：经办律师3
	 */
	public String getFvLower3() {
		return fvLower3;
	}
	/**
	 * 设置：经办律师4
	 */
	public void setFvLower4(String fvLower4) {
		this.fvLower4 = fvLower4;
	}
	/**
	 * 获取：经办律师4
	 */
	public String getFvLower4() {
		return fvLower4;
	}
	/**
	 * 设置：经办律师5
	 */
	public void setFvLower5(String fvLower5) {
		this.fvLower5 = fvLower5;
	}
	/**
	 * 获取：经办律师5
	 */
	public String getFvLower5() {
		return fvLower5;
	}
	/**
	 * 设置：授权代表
	 */
	public void setFvAuthorizer(String fvAuthorizer) {
		this.fvAuthorizer = fvAuthorizer;
	}
	/**
	 * 获取：授权代表
	 */
	public String getFvAuthorizer() {
		return fvAuthorizer;
	}
	/**
	 * 设置：签字日期
	 */
	public void setFvDate(String fvDate) {
		this.fvDate = fvDate;
	}
	/**
	 * 获取：签字日期
	 */
	public String getFvDate() {
		return fvDate;
	}
	/**
	 * 设置：法律意见书类型
	 */
	public void setFvType(String fvType) {
		this.fvType = fvType;
	}
	/**
	 * 获取：法律意见书类型
	 */
	public String getFvType() {
		return fvType;
	}
}
