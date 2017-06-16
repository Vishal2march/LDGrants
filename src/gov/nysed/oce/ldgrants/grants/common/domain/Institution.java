package gov.nysed.oce.ldgrants.grants.common.domain;

public class Institution {

	private Long instId;
	private String popularName;
	private String addrLine1;
	private String addrLine2;
	private String city;
	private String stateCode;
	private String zipCd5;
	private String zipAdd4;
	private String sedCode;
	private int countCode;
	private String county;
	private boolean isLibrarySystem;

	public Long getInstId() {
		return instId;
	}

	public void setInstId(Long instId) {
		this.instId = instId;
	}

	public String getPopularName() {
		return popularName;
	}

	public void setPopularName(String popularName) {
		this.popularName = popularName;
	}

	public String getAddrLine1() {
		return addrLine1;
	}

	public void setAddrLine1(String addrLine1) {
		this.addrLine1 = addrLine1;
	}

	public String getAddrLine2() {
		return addrLine2;
	}

	public void setAddrLine2(String addrLine2) {
		this.addrLine2 = addrLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getZipCd5() {
		return zipCd5;
	}

	public void setZipCd5(String zipCd5) {
		this.zipCd5 = zipCd5;
	}

	public String getZipAdd4() {
		return zipAdd4;
	}

	public void setZipAdd4(String zipAdd4) {
		this.zipAdd4 = zipAdd4;
	}

	public String getSedCode() {
		return sedCode;
	}

	public void setSedCode(String sedCode) {
		this.sedCode = sedCode;
	}

	public int getCountCode() {
		return countCode;
	}

	public void setCountCode(int countCode) {
		this.countCode = countCode;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public boolean isLibrarySystem() {
		return isLibrarySystem;
	}

	public void setLibrarySystem(boolean isLibrarySystem) {
		this.isLibrarySystem = isLibrarySystem;
	}

}
