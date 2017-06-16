package mypackage;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class VendorBean extends ActionForm{
    public VendorBean() {
    }
    
        
    public long id;
    public String name;
    public long addressId;
    public String address;
    public String city;
    public String state;
    public String zipcode;
    public String statecontractnum;
    public String contractNum;
    public boolean preferredvendor;
    public long vendorquoteId;
    public String description;
    public boolean solesource;
    public boolean selectedquote;
    public String pricequote;
    public long grantid;
    public boolean procurementreq;
    

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setStatecontractnum(String statecontractnum) {
        this.statecontractnum = statecontractnum;
    }

    public String getStatecontractnum() {
        return statecontractnum;
    }

    public void setPreferredvendor(boolean preferredvendor) {
        this.preferredvendor = preferredvendor;
    }

    public boolean isPreferredvendor() {
        return preferredvendor;
    }
    
    
    public boolean isMissing(String value) 
    {
      return((value == null) || (value.trim().equals("")));
    }
    
    public ActionErrors validate(ActionMapping mapping,  HttpServletRequest request) 
    {  
      ActionErrors errors = new ActionErrors();
      if (isMissing(getName())) 
        errors.add("nameMissing", new ActionError("value.required", "Name"));
           
      if (isMissing(getAddress())) 
        errors.add("addrMissing", new ActionError("value.required", "Address"));
                
      if (isMissing(getCity())) 
        errors.add("cityMissing", new ActionError("value.required", "City"));
    
        if (isMissing(getState())) 
          errors.add("stateMissing", new ActionError("value.required", "State"));
        
        if (isMissing(getZipcode())) 
          errors.add("zipMissing", new ActionError("value.required", "Zip Code"));
                      
      return(errors);
    }
    
    
    public ActionErrors validateQuote(ActionMapping mapping,  HttpServletRequest request) 
    {  
      ActionErrors errors = new ActionErrors();
      if (isMissing(getDescription())) 
        errors.add("descrMissing", new ActionError("value.required", "Description"));
           
      if (isMissing(getPricequote())) 
        errors.add("priceMissing", new ActionError("value.required", "Price Quote"));
                      
      return(errors);
    }

    public void setVendorquoteId(long vendorquoteId) {
        this.vendorquoteId = vendorquoteId;
    }

    public long getVendorquoteId() {
        return vendorquoteId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setSolesource(boolean solesource) {
        this.solesource = solesource;
    }

    public boolean isSolesource() {
        return solesource;
    }

    public void setSelectedquote(boolean selectedquote) {
        this.selectedquote = selectedquote;
    }

    public boolean isSelectedquote() {
        return selectedquote;
    }

    public void setPricequote(String pricequote) {
        this.pricequote = pricequote;
    }

    public String getPricequote() {
        return pricequote;
    }

    public void setGrantid(long grantid) {
        this.grantid = grantid;
    }

    public long getGrantid() {
        return grantid;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setProcurementreq(boolean procurementreq) {
        this.procurementreq = procurementreq;
    }

    public boolean isProcurementreq() {
        return procurementreq;
    }
}
