/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  SupplyBean.java
 * Creation/Modification History  :
 * SH   2/1/07      Created
 *
 * Description
 * This class has get/set accessors that will store and retrieve info from the
 * Supp_Mat_Equip expense table.
 *****************************************************************************/
package mypackage;

import java.util.Date;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SupplyBean extends ActionForm
{
  public String quantity;
  public String description;
  public float unitprice;
  public String vendor;
  public int grantrequest;
  public int amountapproved;
  public int expsubmitted;
  public int expapproved;
  public int amtamended;
  public String supplytype;
  public long id;
  public String suppmatCode;
  public String amountapprovedStr;
  public String expapprovedStr;
  public String expsubmittedStr;
  public String grantrequestStr;
  public String amtamendedStr;
  public int projtotal;
  public int instcont;
  public long grantid;
  public int fycode;
  public String fiscalyear;
  public String instcontStr;
  public String projtotalStr;
  public String unitpriceStr;
  public String cost;
  public String module;
  public boolean lgFlag;
  public boolean adminwarning;
  public String warningmsg;
  public boolean categoryTotal;
  public Date encumbranceDate;
  public String encumbranceDateStr;
  public String journalEntry;

  public SupplyBean()
  {
  }

  public String getQuantity()
  {
    return quantity;
  }

  public void setQuantity(String quantity)
  {
    this.quantity = quantity;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public float getUnitprice()
  {
    return unitprice;
  }

  public void setUnitprice(float unitprice)
  {
    this.unitprice = unitprice;
  }

  public String getVendor()
  {
    return vendor;
  }

  public void setVendor(String vendor)
  {
    this.vendor = vendor;
  }

  public int getGrantrequest()
  {
    return grantrequest;
  }

  public void setGrantrequest(int grantrequest)
  {
    this.grantrequest = grantrequest;
  }

  public int getAmountapproved()
  {
    return amountapproved;
  }

  public void setAmountapproved(int amountapproved)
  {
    this.amountapproved = amountapproved;
  }

  public int getExpsubmitted()
  {
    return expsubmitted;
  }

  public void setExpsubmitted(int expsubmitted)
  {
    this.expsubmitted = expsubmitted;
  }

  public int getExpapproved()
  {
    return expapproved;
  }

  public void setExpapproved(int expapproved)
  {
    this.expapproved = expapproved;
  }

  public String getSupplytype()
  {
    return supplytype;
  }

  public void setSupplytype(String supplytype)
  {
    this.supplytype = supplytype;
  }

  public long getId()
  {
    return id;
  }

  public void setId(long id)
  {
    this.id = id;
  }

  public String getSuppmatCode()
  {
    return suppmatCode;
  }

  public void setSuppmatCode(String suppmatCode)
  {
    this.suppmatCode = suppmatCode;
  }

  public String getAmountapprovedStr()
  {
    return amountapprovedStr;
  }

  public void setAmountapprovedStr(String amountapprovedStr)
  {
    this.amountapprovedStr = amountapprovedStr;
  }

  public String getExpapprovedStr()
  {
    return expapprovedStr;
  }

  public void setExpapprovedStr(String expapprovedStr)
  {
    this.expapprovedStr = expapprovedStr;
  }

  public String getExpsubmittedStr()
  {
    return expsubmittedStr;
  }

  public void setExpsubmittedStr(String expsubmittedStr)
  {
    this.expsubmittedStr = expsubmittedStr;
  }

  public String getGrantrequestStr()
  {
    return grantrequestStr;
  }

  public void setGrantrequestStr(String grantrequestStr)
  {
    this.grantrequestStr = grantrequestStr;
  }

  public int getProjtotal()
  {
    return projtotal;
  }

  public void setProjtotal(int projtotal)
  {
    this.projtotal = projtotal;
  }

  public int getInstcont()
  {
    return instcont;
  }

  public void setInstcont(int instcont)
  {
    this.instcont = instcont;
  }

  public long getGrantid()
  {
    return grantid;
  }

  public void setGrantid(long grantid)
  {
    this.grantid = grantid;
  }

  public int getFycode()
  {
    return fycode;
  }

  public void setFycode(int fycode)
  {
    this.fycode = fycode;
  }

  public String getFiscalyear()
  {
    return fiscalyear;
  }

  public void setFiscalyear(String fiscalyear)
  {
    this.fiscalyear = fiscalyear;
  }

  public String getInstcontStr()
  {
    return instcontStr;
  }

  public void setInstcontStr(String instcontStr)
  {
    this.instcontStr = instcontStr;
  }

  public String getProjtotalStr()
  {
    return projtotalStr;
  }

  public void setProjtotalStr(String projtotalStr)
  {
    this.projtotalStr = projtotalStr;
  }
  
  
  
  
   public boolean isMissing(String value) 
  {
    return((value == null) || (value.trim().equals("")));
  }
  
  public ActionErrors validate(ActionMapping mapping,  HttpServletRequest request) 
  {  
    ActionErrors errors = new ActionErrors();
    if (isMissing(getQuantity())) 
      errors.add("quanMissing", new ActionError("value.bgtrequired", "Quantity"));
    
    if (isMissing(getDescription())) 
      errors.add("descMissing", new ActionError("value.bgtrequired", "Description"));
    
    if (isMissing(getUnitpriceStr())) 
      errors.add("priceMissing", new ActionError("value.bgtrequired", "Unit Price"));   
    else if(!isLgFlag())//for all programs except lg
    { //REQUESTED BY CA FOR FL/AL  ON 8/14/08
      //9/8/09 FOR FL/AL/CP ONLY - LGRMIF USES $10K THRESHOLD
      //unit price exists - check if valid float
      float priceFlt = parseDollarSign(getUnitpriceStr());
      String suppCode = getSuppmatCode();
      if(priceFlt >5000 && (!suppCode.equals("2")))
        errors.add("catError", new ActionError("errors.eqcategory"));
      else if(priceFlt <5000 && (!suppCode.equals("1")))
        errors.add("typeError", new ActionError("errors.smcategory"));
    }
    
     if (isMissing(getGrantrequestStr())) 
       errors.add("amtreqMissing", new ActionError("value.bgtrequired", "Amount Requested"));
         
    if(!isLgFlag())//for lgrmif, the vendor is optional
    {
        if (isMissing(getVendor())) 
           errors.add("venMissing", new ActionError("value.bgtrequired", "Vendor"));
    }
   
           
    return(errors);
  }

  public String getUnitpriceStr()
  {
    return unitpriceStr;
  }

  public void setUnitpriceStr(String unitpriceStr)
  {
    this.unitpriceStr = unitpriceStr;
  }

  public String getCost()
  {
    return cost;
  }

  public void setCost(String cost)
  {
    this.cost = cost;
  }
  
  public float parseDollarSign(String amount)
  {
    float ans =0;    
    char[] amtString = amount.toCharArray();//convert string to array of char
    Vector newAmtString = new Vector();//vector to hold new amount - just integers, no chars
    int newIndex =0;
    
    //loop on all items in the old string array
    for(int i=0; i<amtString.length; i++) 
    {
        //check if char is a number  - if yes then add to new vector
        if( Character.isDigit(amtString[i])  )
        {  
            //cannot add char to vector - must wrap in a character object
            newAmtString.add(new Character(amtString[i]) ); //it works!
        }
        else if( amtString[i]=='.')//keep any decimal points
        {
          newAmtString.add(new Character(amtString[i]));
        }
    }
    
    String tmpAmtString = "";
    //now convert all the numbers in the vector back to a string
    for(int i=0; i<newAmtString.size();i++)
    {
      tmpAmtString+= newAmtString.get(i);
    }    
    //convert the string to a float
    ans = Float.parseFloat(tmpAmtString);
    
    return ans;
  }

  public String getModule()
  {
    return module;
  }

  public void setModule(String module)
  {
    this.module = module;
  }

    public void setLgFlag(boolean lgFlag) {
        this.lgFlag = lgFlag;
    }

    public boolean isLgFlag() {
        return lgFlag;
    }

    public void setAdminwarning(boolean adminwarning) {
        this.adminwarning = adminwarning;
    }

    public boolean isAdminwarning() {
        return adminwarning;
    }

    public void setWarningmsg(String warningmsg) {
        this.warningmsg = warningmsg;
    }

    public String getWarningmsg() {
        this.warningmsg="Warning:  The amount approved for record "+ this.description 
        +" is more than the amount requested.";
        return warningmsg;
    }

    public void setCategoryTotal(boolean categoryTotal) {
        this.categoryTotal = categoryTotal;
    }

    public boolean isCategoryTotal() {
        return categoryTotal;
    }

    public void setAmtamended(int amtamended) {
        this.amtamended = amtamended;
    }

    public int getAmtamended() {
        return amtamended;
    }

    public void setAmtamendedStr(String amtamendedStr) {
        this.amtamendedStr = amtamendedStr;
    }

    public String getAmtamendedStr() {
        return amtamendedStr;
    }

    public void setEncumbranceDate(Date encumbranceDate) {
        this.encumbranceDate = encumbranceDate;
    }

    public Date getEncumbranceDate() {
        return encumbranceDate;
    }

    public void setJournalEntry(String journalEntry) {
        this.journalEntry = journalEntry;
    }

    public String getJournalEntry() {
        return journalEntry;
    }

    public void setEncumbranceDateStr(String encumbranceDateStr) {
        this.encumbranceDateStr = encumbranceDateStr;
    }

    public String getEncumbranceDateStr() {
        return encumbranceDateStr;
    }
}
