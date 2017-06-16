package gov.nysed.oce.ldgrants.shared;

public class Breadcrumb {
	
	private String displayName;
	private String url;
	private BreadcrumbPage breadcrumbPage;
	
	
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public BreadcrumbPage getBreadcrumbPage() {
		return breadcrumbPage;
	}
	public void setBreadcrumbPage(BreadcrumbPage breadcrumbPage) {
		this.breadcrumbPage = breadcrumbPage;
	}

}
