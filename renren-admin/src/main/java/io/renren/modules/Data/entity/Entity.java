package io.renren.modules.Data.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件下载链接表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-10-22 12:09:52
 */
@TableName("down_three_fx_file_path")
public class Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * uuid
	 */
	private String uuid;
	/**
	 * 文件名
	 */
	private String fileTitle;
	/**
	 * 文件下载链接
	 */
	private String downloadLink;
	/**
	 * 股票代码
	 */
	private String code;
	/**
	 * 发布时间
	 */
	private String pushDate;
	/**
	 * 年份
	 */
	private String year;
	/**
	 * 文件格式类型（doc\pdf）
	 */
	private String fileType;
	/**
	 * 公司简称、公司名
	 */
	private String companyName;
	/**
	 * 文件内容类型
	 */
	private String fileContentType;
	/**
	 * 默认为0（0：未下载、1：已下载、2：不需要）
	 */
	private Integer state;
	/**
	 * 更新时间
	 */
	private String addTime;

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
	 * 设置：uuid
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	/**
	 * 获取：uuid
	 */
	public String getUuid() {
		return uuid;
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
	 * 设置：文件下载链接
	 */
	public void setDownloadLink(String downloadLink) {
		this.downloadLink = downloadLink;
	}
	/**
	 * 获取：文件下载链接
	 */
	public String getDownloadLink() {
		return downloadLink;
	}
	/**
	 * 设置：股票代码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：股票代码
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：发布时间
	 */
	public void setPushDate(String pushDate) {
		this.pushDate = pushDate;
	}
	/**
	 * 获取：发布时间
	 */
	public String getPushDate() {
		return pushDate;
	}
	/**
	 * 设置：年份
	 */
	public void setYear(String year) {
		this.year = year;
	}
	/**
	 * 获取：年份
	 */
	public String getYear() {
		return year;
	}
	/**
	 * 设置：文件格式类型（doc\pdf）
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	/**
	 * 获取：文件格式类型（doc\pdf）
	 */
	public String getFileType() {
		return fileType;
	}
	/**
	 * 设置：公司简称、公司名
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * 获取：公司简称、公司名
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * 设置：文件内容类型
	 */
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	/**
	 * 获取：文件内容类型
	 */
	public String getFileContentType() {
		return fileContentType;
	}
	/**
	 * 设置：默认为0（0：未下载、1：已下载、2：不需要）
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 获取：默认为0（0：未下载、1：已下载、2：不需要）
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * 设置：更新时间
	 */
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	/**
	 * 获取：更新时间
	 */
	public String getAddTime() {
		return addTime;
	}
}
