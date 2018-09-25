package com.InShowVideo.pojo.vo;


/**
 * @Description:自定义响应数据结构
 * 				这个类是提供给门户，ios，安卓，微信商城用的
 * 				门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式（类，或者list）
 * 				其他自行处理
 * 				200：表示成功
 * 				500：表示错误，错误信息在msg字段中
 * 				501：bean验证错误，不管多少个错误都以map形式返回
 * 				502：拦截器拦截到用户token出错
 * 				555：异常抛出信息
 */
public class LoginDataResult {
	private String msg;
	private String skey;
	private String code;

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getSkey() {
		return skey;
	}
	public void setSkey(String skey) {
		this.skey = skey;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public LoginDataResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoginDataResult(String msg, String skey, String code) {
		super();
		this.msg = msg;
		this.skey = skey;
		this.code = code;
	}
	
	
	
}
