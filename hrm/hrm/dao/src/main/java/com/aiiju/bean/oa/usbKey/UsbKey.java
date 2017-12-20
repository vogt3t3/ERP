package com.aiiju.bean.oa.usbKey;

import java.io.Serializable;


/**
 * UsbKey实体
 */
public class UsbKey implements Serializable {
	
	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * USB内部标识的ID
	 */
	
	
	private String lockId;
	
	/**
	 * 序列号
	 */
	private String serialNumber;
	
	/**
	 * 激活码
	 */
	
	private String activationCode;
	
	/**
	 * PIN码
	 */
	
	private String pinCode;
	
	/**
	 * 私钥
	 */
	
	private String privateKey;
	
	
	/**
	 * 公钥X
	 */
	
	private String publicKeyX;
	
	/**
	 *公钥Y
	 */
	
	private String publicKeyY;
	
	/**
	 * 身份(用于签名校验)
	 */
	
	private String identity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLockId() {
		return lockId;
	}

	public void setLockId(String lockId) {
		this.lockId = lockId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublicKeyX() {
		return publicKeyX;
	}

	public void setPublicKeyX(String publicKeyX) {
		this.publicKeyX = publicKeyX;
	}

	public String getPublicKeyY() {
		return publicKeyY;
	}

	public void setPublicKeyY(String publicKeyY) {
		this.publicKeyY = publicKeyY;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	@Override
	public String toString() {
		return "UsbKey [id=" + id + ", lockId=" + lockId + ", serialNumber=" + serialNumber + ", activationCode="
				+ activationCode + ", pinCode=" + pinCode + ", privateKey=" + privateKey + ", publicKeyX=" + publicKeyX
				+ ", publicKeyY=" + publicKeyY + ", identity=" + identity + "]";
	}
	
	
	
	
	
	
	
}
