/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  BudgetDBHandler.java
 * Creation/Modification History  :
 *
 * SH   2/1/07      Created
 *
 * Description
 * This class will handle all database transactions for viewing prior grants
 * and saving all grant info to the appropriate bean class.
 *****************************************************************************/
package mypackage;

import construction.AllocationYearBean;

import java.sql.*;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import java.util.*;
import javax.naming.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.*;
import mypackage.OtherExpBean;
import org.apache.struts.action.ActionForm;


public class BudgetDBHandler 
{

  Connection conn;
  PreparedStatement ps;
  ResultSet rs;
  NumberFormat numF = new DecimalFormat("#,###,###.##");
  Format formatter = new SimpleDateFormat("MM/dd/yyyy"); 
  private DBHandler dbh = new DBHandler();
  public BudgetDBHandler()
  { 
  }

    
  public Vector getPersonalInfo(long grantid, boolean apprVisible, int fycode, int typeCode)
  {
    Vector results = new Vector();    
    
    try{
        conn = initializeConn();
        
        if(typeCode!=0)
        {
          //get either proff staff or support staff 
          ps = conn.prepareStatement("select * from personal_services where gra_id=? and smet_code=? order by fy_code, id");
          ps.setLong(1, grantid);
          ps.setInt(2, typeCode);//either 3 or 4
        }
        else if(fycode==0)
        {
          ps =conn.prepareStatement("select * from personal_services where gra_id=? order by is_total");
          ps.setLong(1, grantid);
        }
        else
        {
          ps =conn.prepareStatement("select * from personal_services where gra_id=? and fy_code=?");
          ps.setLong(1, grantid);
          ps.setInt(2, fycode);
        }
                                
        rs = ps.executeQuery();            
        while(rs.next())
        {
          PersonalBean pb = new PersonalBean();
          pb.setId(rs.getLong("id"));
          pb.setName(rs.getString("name"));     
          pb.setTitle(rs.getString("title"));
          pb.setSalaryrate(rs.getString("salary_rate"));
          pb.setSalary(rs.getInt("salary_rate"));
          pb.setSalaryf(rs.getFloat("salary_rate"));
          pb.setTypeCode(rs.getInt("smet_code"));
          pb.setFte(rs.getFloat("fte"));
          pb.setFteStr(rs.getString("fte"));
          pb.setGrantrequest(rs.getInt("grant_request"));
          pb.setGrantrequestStr(rs.getString("grant_request"));          
          pb.setExpsubmitted(rs.getInt("exp_submitted"));
          pb.setExpsubmittedStr(rs.getString("exp_submitted"));         
          pb.setProjtotal(rs.getInt("proj_total"));
          pb.setInstcont(rs.getInt("inst_cont"));
          pb.setInstcontStr(rs.getString("inst_cont"));
          pb.setFycode(rs.getInt("fy_code"));
          pb.setCategoryTotal(rs.getBoolean("is_total"));
          pb.setAmtamended(rs.getInt("amend_amount"));
          pb.setAmtamendedStr(rs.getString("amend_amount"));
          pb.setBeginDate(rs.getDate("begin_date"));//new 8/1/14
          if(rs.getDate("begin_date")!=null){
            pb.setBeginDateStr(formatter.format(rs.getDate("begin_date")));
          }
          pb.setEndDate(rs.getDate("end_date"));
          if(rs.getDate("end_date")!=null){
            pb.setEndDateStr(formatter.format(rs.getDate("end_date")));
          }
          
          if(apprVisible)
          {
             pb.setAmountapproved(rs.getInt("amount_approved"));
             pb.setAmountapprovedStr(rs.getString("amount_approved"));
             pb.setExpapproved(rs.getInt("exp_approved"));
             pb.setExpapprovedStr(rs.getString("exp_approved"));
          }
          
          if(pb.getAmountapproved() > pb.getGrantrequest())
            pb.setAdminwarning(true);
          
          results.add(pb);
        }
        
      }catch(Exception e){
        System.err.println("error getPersonalInfo() "+ e.getMessage());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }  
    return results;
  }
  
    
  public Vector getBenefitInfo(long grantid, boolean apprVisible, int fycode)
  {
    Vector results = new Vector();  
    
    try{
        conn = initializeConn();
        
        if(fycode==0)
        {
          ps = conn.prepareStatement("select * from employee_benefits where gra_id=? order by is_total");
          ps.setLong(1, grantid);
        }
        else
        {
          ps = conn.prepareStatement("select * from employee_benefits where gra_id=? and fy_code=?");
          ps.setLong(1, grantid);
          ps.setInt(2, fycode);
        }
                
        rs = ps.executeQuery();             
        while(rs.next())
        {
          BenefitsBean bb = new BenefitsBean();
          bb.setId(rs.getLong("id"));
          bb.setName(rs.getString("name"));     
          bb.setGrantrequest(rs.getInt("grant_request"));     
          bb.setGrantrequestStr(rs.getString("grant_request"));
          bb.setExpsubmitted(rs.getInt("exp_submitted"));      
          bb.setExpsubmittedStr(rs.getString("exp_submitted"));
          bb.setProjtotal(rs.getInt("proj_total"));
          bb.setInstcont(rs.getInt("inst_cont"));
          bb.setInstcontStr(rs.getString("inst_cont"));
          bb.setFycode(rs.getInt("fy_code"));
          bb.setBenpercent(rs.getFloat("benefit_percent"));
          bb.setBenpercentStr(rs.getString("benefit_percent"));
          bb.setCategoryTotal(rs.getBoolean("is_total"));
          bb.setAmtamended(rs.getInt("amend_amount"));
          bb.setAmtamendedStr(rs.getString("amend_amount"));
          if(apprVisible)
          {
            bb.setAmountapproved(rs.getInt("amount_approved"));
            bb.setAmountapprovedStr(rs.getString("amount_approved"));
            bb.setExpapproved(rs.getInt("exp_approved"));
            bb.setExpapprovedStr(rs.getString("exp_approved"));
          }          
          
          if(bb.getAmountapproved() > bb.getGrantrequest())
            bb.setAdminwarning(true);
            
          results.add(bb);
        }
        
      }catch(Exception e){
        System.err.println("error getBenefitInfo() "+ e.getMessage());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }  
    return results;
  }
  

  public BudgetCollectionBean getBenefitActionInfo(long grantid, BudgetCollectionBean bc, boolean apprVisible)
  {    
    ArrayList results = new ArrayList();  
    
    try{
        conn = initializeConn();
        
        //get all info from employee_benefits for this grant id
        String select = "select employee_benefits.id, employee_benefits.name, employee_benefits.proj_total, "+
        " employee_benefits.inst_cont, employee_benefits.grant_request, employee_benefits.amount_approved, employee_benefits.benefit_percent, "+
        " employee_benefits.exp_submitted, employee_benefits.exp_approved, employee_benefits.fy_code, employee_benefits.gra_id, employee_benefits.amend_amount, "+
        " employee_benefits.is_total, salary_rate, fte from employee_benefits left join personal_services on employee_benefits.pers_id=personal_services.id "+
        " where employee_benefits.gra_id=?   order by employee_benefits.fy_code, employee_benefits.id";
        ps = conn.prepareStatement(select);
        ps.setLong(1, grantid);
        rs = ps.executeQuery();       
        
        while(rs.next())
        {
          BenefitsBean bb = new BenefitsBean();
          bb.setId(rs.getLong("id"));
          bb.setName(rs.getString("name"));     
          bb.setProjtotal(rs.getInt("proj_total"));          
          bb.setFycode(rs.getInt("fy_code"));
          bb.setBenpercent(rs.getFloat("benefit_percent"));
          bb.setBenpercentStr(rs.getString("benefit_percent"));
          bb.setSalary(rs.getString("salary_rate"));
          bb.setFte(rs.getFloat("fte"));
          bb.setInstcont(rs.getInt("inst_cont"));
          bb.setGrantrequest(rs.getInt("grant_request"));
          bb.setExpsubmitted(rs.getInt("exp_submitted"));
          bb.setCategoryTotal(rs.getBoolean("is_total"));
          bb.setAmtamended(rs.getInt("amend_amount"));
          if(apprVisible)
          {
            bb.setAmountapproved(rs.getInt("amount_approved"));
            bb.setExpapproved(rs.getInt("exp_approved"));
          }
          
          
          if(bb.getSalary()!=null && !bb.getSalary().equals(""))
          {
            int cost =0;
            cost = (int) (bb.getBenpercent() * Float.parseFloat(bb.getSalary()) * bb.getFte() );//changed to float 2/25/08
            String costStr = Integer.toString(cost);
                        
            if(costStr!=null)
            {
              double costDb = Double.parseDouble(costStr);
              bb.setCost(numF.format(costDb));
            }
            
            double sal = Double.parseDouble(bb.getSalary());
            sal = sal * bb.getFte();
            bb.setSalary(numF.format(sal));
          }
          
                   
          bb.setGrantrequestStr(rs.getString("grant_request"));
          if(bb.getGrantrequestStr()!=null && !bb.getGrantrequestStr().equals(""))
          {
            long amtreq = Long.parseLong(bb.getGrantrequestStr());
            bb.setGrantrequestStr(numF.format(amtreq));
          }
          
          bb.setExpsubmittedStr(rs.getString("exp_submitted"));
          if(bb.getExpsubmittedStr()!=null && !bb.getExpsubmittedStr().equals(""))
          {
            long expsub = Long.parseLong(bb.getExpsubmittedStr());
            bb.setExpsubmittedStr(numF.format(expsub));        
          }
         
          bb.setInstcontStr(rs.getString("inst_cont"));
          if(bb.getInstcontStr()!=null && !bb.getInstcontStr().equals(""))
          {
            long instcont = Long.parseLong(bb.getInstcontStr());
            bb.setInstcontStr(numF.format(instcont));       
          }
          
            bb.setAmtamendedStr(rs.getString("amend_amount"));
            if(bb.getAmtamendedStr()!=null && !bb.getAmtamendedStr().equals(""))
            {
              long amamt = Long.parseLong(bb.getAmtamendedStr());
              bb.setAmtamendedStr(numF.format(amamt));       
            }
          
          results.add(bb);
        }
        
        bc.setAllBenRecords(results);
        
      }catch(Exception e){
        System.err.println("error getBenefitActionInfo() "+ e.getMessage());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }  
    return bc;
  }

    
  
  public Vector getContractedInfo(long grantid, boolean apprVisible, int fycode, int typeCode)
  {
    Vector results = new Vector();  
    
    try{
        conn = initializeConn();
        
        if(typeCode!=0)
        {
          //get either purchased or boces info for this grant id*
          ps = conn.prepareStatement("select * from contracted_services where gra_id=? and code=? order by fy_code, id");
          ps.setLong(1, grantid);   
          ps.setInt(2, typeCode);
        }
        else if(fycode==0)
        {
          ps = conn.prepareStatement("select * from contracted_services where gra_id=? order by is_total");
          ps.setLong(1, grantid);
        }
        else
        {
          ps = conn.prepareStatement("select * from contracted_services where gra_id=? and fy_code=?");
          ps.setLong(1, grantid);
          ps.setInt(2, fycode);
        }
        
        rs = ps.executeQuery();       
        
        while(rs.next())
        {
          ContractedBean cb = new ContractedBean();
          cb.setId(rs.getLong("id"));
          cb.setServicetype(rs.getString("service_type"));     
          cb.setRecipient(rs.getString("recipient")); 
          cb.setServicedescr(rs.getString("service_descr"));
          cb.setTypeCode(rs.getInt("code"));//for purchased vs boces
          cb.setGrantrequest(rs.getInt("grant_request"));       
          cb.setGrantrequestStr(rs.getString("grant_request"));
          cb.setExpsubmitted(rs.getInt("exp_submitted"));     
          cb.setExpsubmittedStr(rs.getString("exp_submitted"));
          cb.setProjtotal(rs.getInt("proj_total"));
          cb.setInstcont(rs.getInt("inst_cont"));
          cb.setInstcontStr(rs.getString("inst_cont"));
          cb.setFycode(rs.getInt("fy_code"));
          cb.setCategoryTotal(rs.getBoolean("is_total"));
          cb.setAmtamended(rs.getInt("amend_amount"));
          cb.setAmtamendedStr(rs.getString("amend_amount"));
          cb.setJournalEntry(rs.getString("journal_entry"));
          cb.setEncumbranceDate(rs.getDate("encumbrance_date"));//new 8/1/14
          cb.setProviderUsed(rs.getString("provider_used"));//new 10/15/15
          if(rs.getDate("encumbrance_date")!=null){
            cb.setEncumbranceDateStr(formatter.format(rs.getDate("encumbrance_date")));
          }
            
          if(apprVisible)
          {
            cb.setAmountapproved(rs.getInt("amount_approved"));
            cb.setAmountapprovedStr(rs.getString("amount_approved"));
            cb.setExpapproved(rs.getInt("exp_approved"));
            cb.setExpapprovedStr(rs.getString("exp_approved"));
          }
          
          if(cb.getAmountapproved() > cb.getGrantrequest())
            cb.setAdminwarning(true);
            
          results.add(cb);
        }
        
      }catch(Exception e){
        System.err.println("error getContractedInfo() "+ e.getMessage());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }  
    return results;
  }
  
  
  
  public Vector getSupplyInfo(long grantid, boolean apprVisible, int fycode, int typeCode)
  {
    Vector results = new Vector();    
    try{
        conn = initializeConn();
      
      if(typeCode!=0)
      {
        //get either supplies or equipment
        ps = conn.prepareStatement("select * from supp_mat_equips where gra_id=? and smet_code=? order by fy_code, id");
        ps.setLong(1, grantid);
        ps.setInt(2, typeCode);
      }
      else if(fycode==0)
       {
         ps =conn.prepareStatement("select * from supp_mat_equips where gra_id=? order by is_total");
         ps.setLong(1, grantid);
       }
       else
       {
         ps =conn.prepareStatement("select * from supp_mat_equips where gra_id=? and fy_code=?");
         ps.setLong(1, grantid);
         ps.setInt(2, fycode);
       }
               
       rs = ps.executeQuery();       
        
        while(rs.next())
        {
          SupplyBean sb = new SupplyBean();
          sb.setId(rs.getLong("id"));
          sb.setQuantity(rs.getString("quantity"));     
          sb.setDescription(rs.getString("description")); 
          sb.setUnitprice(rs.getFloat("unit_price")); 
          sb.setUnitpriceStr(rs.getString("unit_price"));
          sb.setVendor(rs.getString("vendor")); 
          sb.setSuppmatCode(rs.getString("smet_code"));
          sb.setGrantrequest(rs.getInt("grant_request"));  
          sb.setGrantrequestStr(rs.getString("grant_request"));
          sb.setExpsubmitted(rs.getInt("exp_submitted"));     
          sb.setExpsubmittedStr(rs.getString("exp_submitted"));
          sb.setProjtotal(rs.getInt("proj_total"));
          sb.setInstcont(rs.getInt("inst_cont"));
          sb.setInstcontStr(rs.getString("inst_cont"));
          sb.setFycode(rs.getInt("fy_code"));
          sb.setCategoryTotal(rs.getBoolean("is_total"));
          sb.setAmtamended(rs.getInt("amend_amount"));
          sb.setAmtamendedStr(rs.getString("amend_amount"));
          sb.setJournalEntry(rs.getString("journal_entry"));
          sb.setEncumbranceDate(rs.getDate("encumbrance_date"));//new 8/1/14
          if(rs.getDate("encumbrance_date")!=null){
            sb.setEncumbranceDateStr(formatter.format(rs.getDate("encumbrance_date")));
          }
            
          if(apprVisible)
          {
            sb.setAmountapproved(rs.getInt("amount_approved"));
            sb.setAmountapprovedStr(rs.getString("amount_approved"));
            sb.setExpapproved(rs.getInt("exp_aproved"));
            sb.setExpapprovedStr(rs.getString("exp_aproved"));
          }         
          
          if(sb.getAmountapproved() > sb.getGrantrequest())
            sb.setAdminwarning(true);
            
          results.add(sb);
        }
        
      }catch(Exception e){
        System.err.println("error getSupplyInfo() "+ e.getMessage());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }  
    return results;
  }
  
  
  public Vector getTravelInfo(long grantid, boolean apprVisible, int fycode)
  {
    Vector results = new Vector();     
    try{
        conn = initializeConn();
        
        if(fycode==0){
          ps = conn.prepareStatement("select * from travel_expenses where gra_id=? order by is_total");
          ps.setLong(1, grantid);
        }
        else{
          ps= conn.prepareStatement("select * from travel_expenses where gra_id=? and fy_code=?");
          ps.setLong(1, grantid);
          ps.setInt(2, fycode);
        }        
        rs = ps.executeQuery();       
        
        while(rs.next())
        {
          TravelBean tb = new TravelBean();
          tb.setId(rs.getLong("id"));
          tb.setDescription(rs.getString("description"));   
          tb.setPurpose(rs.getString("purpose"));
          tb.setCostsummary(rs.getString("costsummary"));
          tb.setGrantrequest(rs.getInt("grant_request")); 
          tb.setGrantrequestStr(rs.getString("grant_request"));
          tb.setExpsubmittedStr(rs.getString("exp_submitted"));
          tb.setExpsubmitted(rs.getInt("exp_submitted"));          
          tb.setProjtotal(rs.getInt("proj_total"));
          tb.setInstcont(rs.getInt("inst_cont"));
          tb.setInstcontStr(rs.getString("inst_cont"));
          tb.setFycode(rs.getInt("fy_code"));
          tb.setCategoryTotal(rs.getBoolean("is_total"));
          tb.setAmtamended(rs.getInt("amend_amount"));
          tb.setAmtamendedStr(rs.getString("amend_amount"));
          tb.setTravelPeriod(rs.getString("travel_period"));
          tb.setJournalEntry(rs.getString("journal_entry"));
          tb.setTravelerName(rs.getString("traveler_name"));
            
          if(apprVisible)
          {
            tb.setAmountapproved(rs.getInt("amount_approved"));
            tb.setAmountapprovedStr(rs.getString("amount_approved"));
            tb.setExpapproved(rs.getInt("exp_approved"));
            tb.setExpapprovedStr(rs.getString("exp_approved"));
          } 
          
          if(tb.getAmountapproved() > tb.getGrantrequest())
            tb.setAdminwarning(true);
          results.add(tb);
        }
        
      }catch(Exception e){
        System.err.println("error getTravelInfo() "+ e.getMessage());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }  
    return results;
  }
  

  public Vector getExpenseInfo(long grantid, boolean apprVisible, int fycode)
  {
    Vector results = new Vector();     
    try{
        conn = initializeConn();
        
        if(fycode==0){
          ps = conn.prepareStatement("select * from other_expenses where gra_id=? order by is_total");
          ps.setLong(1, grantid);
        }
        else{
          ps= conn.prepareStatement("select * from other_expenses where gra_id=? and fy_code=?");
          ps.setLong(1, grantid);
          ps.setInt(2, fycode);
        }
        
        rs = ps.executeQuery();        
        while(rs.next()){
          OtherExpBean eb = new OtherExpBean();
          eb.setId(rs.getLong("id"));
          eb.setDescription(rs.getString("description"));     
          eb.setCostsummary(rs.getString("cost_summary"));//new 3/31/15 per DMeadows
          eb.setGrantrequest(rs.getInt("grant_request"));     
          eb.setGrantrequestStr(rs.getString("grant_request"));
          eb.setExpsubmitted(rs.getInt("exp_submitted"));  
          eb.setExpsubmittedStr(rs.getString("exp_submitted"));
          eb.setProjtotal(rs.getInt("proj_total"));
          eb.setInstcont(rs.getInt("inst_cont"));
          eb.setFycode(rs.getInt("fy_code"));
          eb.setCategoryTotal(rs.getBoolean("is_total"));
          eb.setAmtamended(rs.getInt("amend_amount"));
          eb.setAmtamendedStr(rs.getString("amend_amount"));
          eb.setJournalEntry(rs.getString("journal_entry"));
          eb.setEncumbranceDate(rs.getDate("encumbrance_date"));//new 8/1/14
          eb.setProviderUsed(rs.getString("provider_used"));//new 10/15/15
          if(rs.getDate("encumbrance_date")!=null){
            eb.setEncumbranceDateStr(formatter.format(rs.getDate("encumbrance_date")));
          }
          
          if(apprVisible){
            eb.setAmountapproved(rs.getInt("amount_approved"));
            eb.setAmountapprovedStr(rs.getString("amount_approved"));
            eb.setExpapproved(rs.getInt("exp_approved"));
            eb.setExpapprovedStr(rs.getString("exp_approved"));
          } 
          if(eb.getAmountapproved() > eb.getGrantrequest())
            eb.setAdminwarning(true);
          
          results.add(eb);
        }
        
      }catch(Exception e){
        System.err.println("error getExpenseInfo() "+ e.getMessage());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }  
    return results;
  }
  
  
  public BudgetCollectionBean getTravelActionInfo(long grantid, BudgetCollectionBean bc, boolean apprVisible)
  {          
    ArrayList results = new ArrayList();
    
    try {
        conn = initializeConn();        
               
        //get all info for this grant id
        ps = conn.prepareStatement("select * from travel_expenses where gra_id=?");
        ps.setLong(1, grantid);
        rs = ps.executeQuery();       
        
        while(rs.next())
        {
          TravelBean eb = new TravelBean();
          eb.setId(rs.getLong("id"));
          eb.setDescription(rs.getString("description"));
          eb.setPurpose(rs.getString("purpose"));
          eb.setCostsummary(rs.getString("costsummary"));
          eb.setProjtotal(rs.getInt("proj_total"));         
          eb.setFycode(rs.getInt("fy_code"));
          eb.setInstcont(rs.getInt("inst_cont"));
          eb.setGrantrequest(rs.getInt("grant_request"));
          eb.setExpsubmitted(rs.getInt("exp_submitted"));
          eb.setCategoryTotal(rs.getBoolean("is_total"));
          eb.setAmtamended(rs.getInt("amend_amount"));
          eb.setTravelPeriod(rs.getString("travel_period"));
          eb.setJournalEntry(rs.getString("journal_entry"));
          eb.setTravelerName(rs.getString("traveler_name"));
            
          if(apprVisible)
          {
            eb.setAmountapproved(rs.getInt("amount_approved"));
            eb.setExpapproved(rs.getInt("exp_approved"));
          }
                    
          eb.setGrantrequestStr(rs.getString("grant_request"));
          if(eb.getGrantrequestStr()!=null && !eb.getGrantrequestStr().equals(""))
          {
            long amtreq = Long.parseLong(eb.getGrantrequestStr());
            eb.setGrantrequestStr(numF.format(amtreq));
          }
          
          eb.setExpsubmittedStr(rs.getString("exp_submitted"));
          if(eb.getExpsubmittedStr()!=null && !eb.getExpsubmittedStr().equals(""))
          {
            long expsub = Long.parseLong(eb.getExpsubmittedStr());
            eb.setExpsubmittedStr(numF.format(expsub));        
          }
         
          eb.setInstcontStr(rs.getString("inst_cont"));
          if(eb.getInstcontStr()!=null && !eb.getInstcontStr().equals(""))
          {
            long instcont = Long.parseLong(eb.getInstcontStr());
            eb.setInstcontStr(numF.format(instcont));       
          }
          
            eb.setAmtamendedStr(rs.getString("amend_amount"));
            if(eb.getAmtamendedStr()!=null && !eb.getAmtamendedStr().equals(""))
            {
              long amamt = Long.parseLong(eb.getAmtamendedStr());
              eb.setAmtamendedStr(numF.format(amamt));       
            }
       
          results.add(eb);          
        }
        
        bc.setAllTravelRecords(results);
              
      }catch(Exception e){
        System.err.println("error getTravelActionInfo() "+ e.getMessage());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }  
    return bc;
  }
 
  
  public BudgetCollectionBean getExpenseActionInfo(long grantid, BudgetCollectionBean bc, boolean apprVisible)
  {          
    ArrayList results = new ArrayList();
    
    try {
        conn = initializeConn();        
               
        //get all info for this grant id
        ps = conn.prepareStatement("select * from other_expenses where gra_id=?");
        ps.setLong(1, grantid);
        rs = ps.executeQuery();       
        
        while(rs.next())
        {
          OtherExpBean eb = new OtherExpBean();
          eb.setId(rs.getLong("id"));
          eb.setDescription(rs.getString("description")); 
          eb.setCostsummary(rs.getString("cost_summary"));
          eb.setProjtotal(rs.getInt("proj_total"));         
          eb.setFycode(rs.getInt("fy_code"));
          eb.setInstcont(rs.getInt("inst_cont"));
          eb.setGrantrequest(rs.getInt("grant_request"));
          eb.setExpsubmitted(rs.getInt("exp_submitted"));
          eb.setCategoryTotal(rs.getBoolean("is_total"));
          eb.setAmtamended(rs.getInt("amend_amount"));
          eb.setJournalEntry(rs.getString("journal_entry"));
          eb.setProviderUsed(rs.getString("provider_used"));//new 10/15/15
          eb.setEncumbranceDate(rs.getDate("encumbrance_date"));//new 8/1/14
          if(rs.getDate("encumbrance_date")!=null){
            eb.setEncumbranceDateStr(formatter.format(rs.getDate("encumbrance_date")));
          }
            
          if(apprVisible)
          {
            eb.setAmountapproved(rs.getInt("amount_approved"));
            eb.setExpapproved(rs.getInt("exp_approved"));
          }
          
          
          eb.setGrantrequestStr(rs.getString("grant_request"));
          if(eb.getGrantrequestStr()!=null && !eb.getGrantrequestStr().equals(""))
          {
            long amtreq = Long.parseLong(eb.getGrantrequestStr());
            eb.setGrantrequestStr(numF.format(amtreq));
          }
          
          eb.setExpsubmittedStr(rs.getString("exp_submitted"));
          if(eb.getExpsubmittedStr()!=null && !eb.getExpsubmittedStr().equals(""))
          {
            long expsub = Long.parseLong(eb.getExpsubmittedStr());
            eb.setExpsubmittedStr(numF.format(expsub));        
          }
         
          eb.setInstcontStr(rs.getString("inst_cont"));
          if(eb.getInstcontStr()!=null && !eb.getInstcontStr().equals(""))
          {
            long instcont = Long.parseLong(eb.getInstcontStr());
            eb.setInstcontStr(numF.format(instcont));       
          }
          
            eb.setAmtamendedStr(rs.getString("amend_amount"));
            if(eb.getAmtamendedStr()!=null && !eb.getAmtamendedStr().equals(""))
            {
              long amamt = Long.parseLong(eb.getAmtamendedStr());
              eb.setAmtamendedStr(numF.format(amamt));       
            }
       
          results.add(eb);          
        }
        
        bc.setAllExpRecords(results);
              
      }catch(Exception e){
        System.err.println("error getExpActionInfo() "+ e.getMessage());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }  
    return bc;
  }

  private static Connection initializeConn() throws Exception
  {
      Context namingContext = new InitialContext();
      DataSource ds = (DataSource) namingContext.lookup("jdbc/LDGrantsDS");
      return ds.getConnection();   
  }

  
  
  public void Close(Connection conn)
  {
    try{
      if(conn != null)
      {
        conn.close();
      }
    }catch(Exception e){}
  }
  
  public void Close(PreparedStatement stmt)
  {
    try{
      if(stmt != null)
      {
        stmt.close();
      }
    }catch(Exception e){}
  }

 public void Close(ResultSet rs)
  {
    try{
      if(rs != null)
      {
        rs.close();
      }
    }catch(Exception e){}
  }

 
 
  public int autoInsertPersonalAmts(UserBean userb, long grantid , String type)
  {    
    //get all personal expense records for this grant
    Vector allRecords = getPersonalInfo(grantid, true, 0, 0);    
    int outcome = 0;
    
    //if records returned - auto update appr amts
    if(allRecords.size()>0){             
    try {
        conn = initializeConn();
        
        //create stmt based on type of auto update (amts requested or exps sub)
        String updateSQL = "";
        if(type.equals("AmtReq"))
        {
          updateSQL = "update PERSONAL_SERVICES set AMOUNT_APPROVED = ?, " + 
          " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?";
        }
        else if(type.equals("ExpSub"))
        {
          updateSQL = "update PERSONAL_SERVICES set EXP_APPROVED = ?, "+
          " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?";
        }
        
        //loop on all the records we must update
        for(int i=0; i<allRecords.size(); i++)
        {
          //get 1 record
          PersonalBean pb = (PersonalBean) allRecords.get(i); 
          
          ps = conn.prepareStatement(updateSQL);
          if(type.equals("AmtReq"))
          {
            ps.setInt(1, pb.getGrantrequest() );
          }
          else
            ps.setInt(1, pb.getExpsubmitted());
            
          ps.setString(2, userb.getUserid());
          ps.setLong(3, pb.getId());
          outcome = ps.executeUpdate();          
        }       
      }
      catch(Exception e){
        System.err.println("error autoInsertPersonalAmts() "+ e.getMessage().toString());
      }
      finally{
        Close(conn);
        Close(ps);
      }
    }
    
    return outcome;
  }

  public int autoInsertBenefitsAmts(UserBean userb, long grantid, String type)
  {
    //get all personal expense records for this grant
    Vector allRecords = getBenefitInfo(grantid, true, 0);    
    int outcome = 0;
    
    //if records returned - auto update appr amts
    if(allRecords.size()>0){             
      try{
        conn = initializeConn();
        
        //create stmt based on type of auto update (amts requested or exps sub)
        String updateSQL = "";
        if(type.equals("AmtReq"))
        {
          updateSQL = "update EMPLOYEE_BENEFITS set AMOUNT_APPROVED = ?, " + 
          " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?";
        }
        else if(type.equals("ExpSub"))
        {
          updateSQL = "update EMPLOYEE_BENEFITS set EXP_APPROVED = ?, "+
          " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?";
        }
        
        //loop on all the records we must update
        for(int i=0; i<allRecords.size(); i++)
        {
          //get 1 record
          BenefitsBean bb = (BenefitsBean) allRecords.get(i); 
          
          ps = conn.prepareStatement(updateSQL);
          if(type.equals("AmtReq"))
          {
            ps.setInt(1, bb.getGrantrequest() );
          }
          else
            ps.setInt(1, bb.getExpsubmitted());
            
          ps.setString(2, userb.getUserid());
          ps.setLong(3, bb.getId());
          outcome = ps.executeUpdate();          
        }       
      }
      catch(Exception e){
        System.err.println("error autoInsertBenefitsAmts() "+ e.getMessage().toString());
      }
      finally{
        Close(conn);
        Close(ps);
      }
    }
    
    return outcome;
  }

  public int autoInsertSupplyAmts(UserBean userb, long grantid, String type)
  {
    //get all personal expense records for this grant
    Vector allRecords = getSupplyInfo(grantid, true, 0, 0);    
    int outcome = 0;
    
    //if records returned - auto update appr amts
    if(allRecords.size()>0){             
      try{
        conn = initializeConn();
        
        //create stmt based on type of auto update (amts requested or exps sub)
        String updateSQL = "";
        if(type.equals("AmtReq"))
        {
          updateSQL = "update SUPP_MAT_EQUIPS set AMOUNT_APPROVED = ?, " + 
          " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?";
        }
        else if(type.equals("ExpSub"))
        {
          updateSQL = "update SUPP_MAT_EQUIPS set EXP_APROVED = ?, "+
          " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?";
        }
        
        //loop on all the records we must update
        for(int i=0; i<allRecords.size(); i++)
        {
          //get 1 record
          SupplyBean sb = (SupplyBean) allRecords.get(i); 
          
          ps = conn.prepareStatement(updateSQL);
          if(type.equals("AmtReq"))
          {
            ps.setInt(1, sb.getGrantrequest() );
          }
          else
            ps.setInt(1, sb.getExpsubmitted());
            
          ps.setString(2, userb.getUserid());
          ps.setLong(3, sb.getId());
          outcome = ps.executeUpdate();          
        }       
      }
      catch(Exception e){
        System.err.println("error autoInsertSupplyAmts() "+ e.getMessage().toString());
      }
      finally{
        Close(conn);
        Close(ps);
      }
    }    
    return outcome;
  }

  public int autoInsertContractAmts(UserBean userb, long grantid, String type)
  {
    //get all personal expense records for this grant
    Vector allRecords = getContractedInfo(grantid, true, 0, 0);    
    int outcome = 0;
    
    //if records returned - auto update appr amts
    if(allRecords.size()>0){             
      try{
        conn = initializeConn();
        
        //create stmt based on type of auto update (amts requested or exps sub)
        String updateSQL = "";
        if(type.equals("AmtReq"))
        {
          updateSQL = "update CONTRACTED_SERVICES set AMOUNT_APPROVED = ?, " + 
          " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?";
        }
        else if(type.equals("ExpSub"))
        {
          updateSQL = "update CONTRACTED_SERVICES set EXP_APPROVED = ?, "+
          " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?";
        }
        
        //loop on all the records we must update
        for(int i=0; i<allRecords.size(); i++)
        {
          //get 1 record
          ContractedBean cb = (ContractedBean) allRecords.get(i); 
          
          ps = conn.prepareStatement(updateSQL);
          if(type.equals("AmtReq"))
          {
            ps.setInt(1, cb.getGrantrequest() );
          }
          else
            ps.setInt(1, cb.getExpsubmitted());
            
          ps.setString(2, userb.getUserid());
          ps.setLong(3, cb.getId());
          outcome = ps.executeUpdate();          
        }       
      }
      catch(Exception e){
        System.err.println("error autoInsertContractAmts() "+ e.getMessage().toString());
      }
      finally{
        Close(conn);
        Close(ps);
      }
    }    
    return outcome;
  }


  public int autoInsertTravelAmts(UserBean userb, long grantid, String type)
  {
    //get all expense records for this grant
    Vector allRecords = getTravelInfo(grantid, true, 0);    
    int outcome = 0;
    
    //if records returned - auto update appr amts
    if(allRecords.size()>0){             
      try{
        conn = initializeConn();
        
        //create stmt based on type of auto update (amts requested or exps sub)
        String updateSQL = "";
        if(type.equals("AmtReq"))
        {
          updateSQL = "update TRAVEL_EXPENSES set AMOUNT_APPROVED = ?, " + 
          " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?";
        }
        else if(type.equals("ExpSub"))
        {
          updateSQL = "update TRAVEL_EXPENSES set EXP_APPROVED = ?, "+
          " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?";
        }
        
        //loop on all the records we must update
        for(int i=0; i<allRecords.size(); i++)
        {
          TravelBean cb = (TravelBean) allRecords.get(i); 
          
          ps = conn.prepareStatement(updateSQL);
          if(type.equals("AmtReq"))
          {
            ps.setInt(1, cb.getGrantrequest() );
          }
          else
            ps.setInt(1, cb.getExpsubmitted());
            
          ps.setString(2, userb.getUserid());
          ps.setLong(3, cb.getId());
          outcome = ps.executeUpdate();          
        }       
      }
      catch(Exception e){
        System.err.println("error autoInsertTravelAmts() "+ e.getMessage().toString());
      }
      finally{
        Close(conn);
        Close(ps);
      }
    }    
    return outcome;
  }

  public int autoInsertOtherAmts(UserBean userb, long grantid, String type)
  {
    //get all personal expense records for this grant
    Vector allRecords = getExpenseInfo(grantid, true, 0);    
    int outcome = 0;
    
    //if records returned - auto update appr amts
    if(allRecords.size()>0){             
      try {
        conn = initializeConn();
        
        //create stmt based on type of auto update (amts requested or exps sub)
        String updateSQL = "";
        if(type.equals("AmtReq"))
        {
          updateSQL = "update OTHER_EXPENSES set AMOUNT_APPROVED = ?, " + 
          " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?";
        }
        else if(type.equals("ExpSub"))
        {
          updateSQL = "update OTHER_EXPENSES set EXP_APPROVED = ?, "+
          " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?";
        }
        
        //loop on all the records we must update
        for(int i=0; i<allRecords.size(); i++)
        {
          //get 1 record
          OtherExpBean ob = (OtherExpBean) allRecords.get(i); 
          
          ps = conn.prepareStatement(updateSQL);
          if(type.equals("AmtReq"))
          {
            ps.setInt(1, ob.getGrantrequest() );
          }
          else
            ps.setInt(1, ob.getExpsubmitted());
            
          ps.setString(2, userb.getUserid());
          ps.setLong(3, ob.getId());
          outcome = ps.executeUpdate();          
        }       
      }
      catch(Exception e){
        System.err.println("error autoInsertOtherAmts() "+ e.getMessage().toString());
      }
      finally{
        Close(conn);
        Close(ps);
      }
    }    
    return outcome;
  }  
  
  
  public int getFieldTotal(String tablename, String fieldname, long grantid)
  {    
    int total = 0;    
    try {
      conn = initializeConn();
      ps = conn.prepareStatement("select sum(" + fieldname + ") from " + tablename +
                                " where GRA_ID = ?" );      
      ps.setLong(1, grantid);
      rs = ps.executeQuery();
      rs.next();
      if(rs.getInt(1) != 0){
          total = rs.getInt(1);      
      }
                                 
    } catch (Exception ex){
        System.err.println("error getFieldTotal() " + ex.toString());
    }finally {
      Close(conn);
      Close(ps);
      Close(rs);
    }
    return total;
  }
  
  
  /**
   * This method will calculate the total amount approved accross all budget categories
   * for a given grant number. used for lgrmif acceptanceform and coord admin FS10
   * @return the total
   * @param grantid
   */
  public TotalsBean calcTotalAmtApprCategorized(long grantid, TotalsBean tb, int fycode)
  {
    int total, pExp, bExp, cExp, sExp, oExp, tExp =0;
  
    try{      
        if(fycode==0)
        {
          //calculate the total amount requested for all 5 category expenses
          pExp = getFieldTotal("Personal_Services", "amount_approved", grantid);
          bExp = getFieldTotal("Employee_Benefits", "amount_approved", grantid);
          cExp = getFieldTotal("Contracted_services", "amount_approved", grantid);
          sExp = getFieldTotal("supp_mat_equips", "amount_approved", grantid);
          oExp = getFieldTotal("Other_Expenses", "amount_approved", grantid);
          tExp = getFieldTotal("Travel_Expenses", "amount_approved", grantid);
          total = pExp + bExp + cExp + sExp + oExp + tExp;//this is the total of amt_appr for all exp categories
        }
        else{
            pExp = getFieldTotalFY("Personal_Services", "amount_approved", grantid, fycode);
            bExp = getFieldTotalFY("Employee_Benefits", "amount_approved", grantid, fycode);
            cExp = getFieldTotalFY("Contracted_services", "amount_approved", grantid, fycode);
            sExp = getFieldTotalFY("supp_mat_equips", "amount_approved", grantid, fycode);
            oExp = getFieldTotalFY("Other_Expenses", "amount_approved", grantid, fycode);
            tExp = getFieldTotalFY("Travel_Expenses", "amount_approved", grantid, fycode);
            total = pExp + bExp + cExp + sExp + oExp + tExp;//this is the total of amt_appr for all exp categories for given year
        }
        
      tb.setPerAmtAppr(pExp);
      tb.setBenAmtAppr(bExp);
      tb.setConAmtAppr(cExp);
      tb.setSuppAmtAppr(sExp);
      tb.setOthAmtAppr(oExp);
      tb.setTotAmtAppr(total);
      tb.setTravAmtAppr(tExp);
                        
    }catch(Exception e){
      System.err.println("error calcTotalAmtApprCategorized() " + e.getMessage().toString());
    }
    return tb;
  }


/**
   * This method will calculate the total amount approved accross all budget categories
   * for a given grant number.  It calculates for all years of a grant or specific fy
   * of a grant project.
   * @return the total
   * @param grantnum
   */
  public int calcTotalAmtApproved(long grantnum, int fycode)
  {
    int total =0;
  
    try{     
      if(fycode==0){
        //calculate the total amount approved for all 5 category expenses       
         conn = initializeConn();
         ps = conn.prepareStatement("select sum(totappr) as totamtappr from ldgrants.BUDGETTOTALSVIEW "+
         " where gra_id=?");
         ps.setLong(1, grantnum);
         rs = ps.executeQuery();
         while(rs.next()){
             total = rs.getInt("totamtappr");
         }
        
      }else{         
        conn = initializeConn();
        ps = conn.prepareStatement("select sum(totappr) as sumappr from ldgrants.multifyproj_budgettotals_view " + 
        " where gra_id=? and fy_code=? ");
        ps.setLong(1, grantnum);
        ps.setInt(2, fycode);
        rs = ps.executeQuery();
        while(rs.next()){
            total = rs.getInt("sumappr");
        }
      }
      
    }catch(Exception e){
      System.err.println("error calcTotalAmtApproved() " + e.getMessage().toString());
    }
    finally{
        Close(rs);
        Close(ps);
        Close(conn);
    }
    return total;
  }
  
  
  /**
   * This method will calculate the total expenses submitted accross all budged categories
   * for a given grand number.
   * @return 
   * @param grantid
   */
  public TotalsBean calcTotalExpSubCategorized(long grantid, TotalsBean tb, int fycode)
  {
    int total, pExp, bExp, cExp, sExp, oExp, tExp =0;
  
    try{
      
      //calculate the total exp submitted for all 5 category expenses
      if(fycode==0)
      {
        pExp = getFieldTotal("Personal_Services", "exp_submitted", grantid);
        bExp = getFieldTotal("Employee_Benefits", "exp_submitted", grantid);
        cExp = getFieldTotal("Contracted_services", "exp_submitted", grantid);
        sExp = getFieldTotal("supp_mat_equips", "exp_submitted", grantid);
        oExp = getFieldTotal("Other_Expenses", "exp_submitted", grantid); 
        tExp = getFieldTotal("Travel_Expenses", "exp_submitted", grantid);
      }
      else{
        pExp = getFieldTotalFY("Personal_Services", "exp_submitted", grantid, fycode);
        bExp = getFieldTotalFY("Employee_Benefits", "exp_submitted", grantid, fycode);
        cExp = getFieldTotalFY("Contracted_services", "exp_submitted", grantid, fycode);
        sExp = getFieldTotalFY("supp_mat_equips", "exp_submitted", grantid, fycode);
        oExp = getFieldTotalFY("Other_Expenses", "exp_submitted", grantid, fycode); 
        tExp = getFieldTotalFY("Travel_Expenses", "exp_submitted", grantid, fycode); 
      }
      total = pExp + bExp + cExp + sExp + oExp +tExp;
      
      tb.setPerExpSub(pExp);
      tb.setBenExpSub(bExp);
      tb.setConExpSub(cExp);
      tb.setSuppExpSub(sExp);
      tb.setOthExpSub(oExp);
      tb.setTravExpSub(tExp);
      tb.setTotExpSub(total);
      
    }catch(Exception e){
      System.err.println("error calcTotalExpSubCategorized() " + e.getMessage().toString());
    }
    return tb;
  }

  
  /*commented out 11/20/09 - not being used, don't know why its here?
   * public TotalsBean calcTotalExpApprCategorized(long grantid, TotalsBean tb)
  {
    int total =0;  
    try{
      
      //calculate the total exp approved for all 5 category expenses
      int pExp = getFieldTotal("Personal_Services", "exp_approved", grantid);
      int bExp = getFieldTotal("Employee_Benefits", "exp_approved", grantid);
      int cExp =  getFieldTotal("Contracted_services", "exp_approved", grantid);
      int sExp = getFieldTotal("supp_mat_equips", "exp_aproved", grantid);
      int oExp = getFieldTotal("Other_Expenses", "exp_approved", grantid);
      int tExp = getFieldTotal("Travel_Expenses", "exp_approved", grantid);
      total = pExp + bExp + cExp + sExp + oExp;
      
      tb.setPerExpAppr(pExp);
      tb.setBenExpAppr(bExp);
      tb.setConExpAppr(cExp);
      tb.setSuppExpAppr(sExp);
      tb.setOthExpAppr(oExp);
      tb.setTravExpAppr(tExp);
      tb.setTotExpAppr(total);
      
    }catch(Exception e){
      System.err.println("error calcTotalExpApprCategorized() " + e.getMessage().toString());
    }
    return tb;
  }*/
  
  
  
  /**
   * Method will calculate the total exp apprv for all fy of grant project, or the
   * given fy of grant project.
   * @return 
   * @param fycode
   * @param grantnum
   */
  public int calcTotalExpApproved(long grantnum, int fycode)
  {
    int total =0;
  
    try{      
      if(fycode==0)
      {
        //calculate the total exp approved for all fy of project
        conn = initializeConn();
        ps = conn.prepareStatement("select sum(totexpappr) as totappr from ldgrants.BUDGETTOTALSVIEW "+
        " where gra_id=?");
        ps.setLong(1, grantnum);
        rs = ps.executeQuery();
        while(rs.next()){
            total = rs.getInt("totappr");
        }        
        
      }else{
        //calc total exp appr for certain fy          
         conn = initializeConn();
         ps = conn.prepareStatement("select sum(totexpappr) as sumappr from ldgrants.multifyproj_budgettotals_view " + 
         " where gra_id=? and fy_code=? ");
         ps.setLong(1, grantnum);
         ps.setInt(2, fycode);
         rs = ps.executeQuery();
         while(rs.next()){
             total = rs.getInt("sumappr");
         }
      }
               
    }catch(Exception e) {
      System.err.println("error calcTotalExpApproved() " + e.getMessage().toString());
    }
    finally{
        Close(rs);
        Close(ps);
        Close(conn);
    }
    return total;
  }
  
  public int calcTotalExpSubmitted(long grantnum, int fycode)
  {
    int total =0;
  
    try{      
      if(fycode==0){
        //calculate the total exp sub for all fy of project       
          conn = initializeConn();
          ps = conn.prepareStatement("select sum(totexpsub) as totsub from ldgrants.BUDGETTOTALSVIEW "+
          " where gra_id=?");
          ps.setLong(1, grantnum);
          rs = ps.executeQuery();
          while(rs.next()){
              total = rs.getInt("totsub");
          }
                 
      } else{
        //calc total exp appr for certain fy   
        /*int pExp = getFieldTotalFY("Personal_Services", "exp_submitted", grantnum, fycode);
        int bExp = getFieldTotalFY("Employee_Benefits", "exp_submitted", grantnum, fycode);
        int cExp = getFieldTotalFY("Contracted_services", "exp_submitted", grantnum, fycode);
        int sExp = getFieldTotalFY("Supp_mat_equips", "exp_submitted", grantnum, fycode);
        int oExp = getFieldTotalFY("Other_Expenses", "exp_submitted", grantnum, fycode);
        int tExp = getFieldTotalFY("Travel_Expenses", "exp_submitted", grantnum, fycode);
        total = pExp + bExp + cExp + sExp + oExp + tExp;*/
         conn = initializeConn();
         ps = conn.prepareStatement("select sum(totexpsub) as totsub from ldgrants.multifyproj_budgettotals_view " + 
         " where gra_id=? and fy_code=? ");
         ps.setLong(1, grantnum);
         ps.setInt(2, fycode);
         rs = ps.executeQuery();
         while(rs.next()){
             total = rs.getInt("totsub");
         }
      }
               
    }catch(Exception e) {
      System.err.println("error calcTotalExpSubmitted() " + e.getMessage().toString());
    }
    finally{
        Close(rs);
        Close(ps);
        Close(conn);
    }
    return total;
  }
  
  
  /**
     * new method 2/22/11 -calculates totals of amt req/app exp submit/appr for all 3 FY's
     * of a given coordinated grantId. using this method (as opposed to similar method for
     * literacy totals by FY getProjectBudgetTotalsByFy() ) because this method only calls
     * the DB one time. 
     * @param grantnum
     * @param fycode
     * @param isAdmin
     * @param asb
     * @return
     */
    public HashMap calcFyBudgetTotals(long grantnum, int fycode, boolean isAdmin, AppStatusBean asb)
    {
        HashMap map = new HashMap();
        TotalsBean tb1 = new TotalsBean();  
        TotalsBean tb2 = new TotalsBean();
        TotalsBean tb3 = new TotalsBean();//year 3 needed for c/p coordinated 2/17/11
    
      try{      
           conn = initializeConn();
           ps = conn.prepareStatement("select sum(totreq) as totreq, sum(totappr) as totappr, "+
           " sum(totexpsub) as totsub, sum(totexpappr) as expappr from ldgrants.multifyproj_budgettotals_view " + 
           " where gra_id=? and fy_code=? ");
           
           ps.setLong(1, grantnum);
           ps.setInt(2, fycode);
           rs = ps.executeQuery();
           while(rs.next()){
               tb1.setFycode(fycode);
               tb1.setTotAmtReq(rs.getInt("totreq"));
               tb1.setTotExpSub(rs.getInt("totsub"));
               if(isAdmin || asb.isShowscorecomm()){
                   tb1.setTotAmtAppr(rs.getInt("totappr"));
                   tb1.setTotExpAppr(rs.getInt("expappr"));
               }
           }
           
           ps.clearParameters();
           ps.setLong(1, grantnum);
           ps.setInt(2, fycode+1);
           rs = ps.executeQuery();
           while(rs.next()){
               tb2.setFycode(fycode+1);
               tb2.setTotAmtReq(rs.getInt("totreq"));
               tb2.setTotExpSub(rs.getInt("totsub"));
               if(isAdmin || asb.isShowscorecomm()){
                   tb2.setTotAmtAppr(rs.getInt("totappr"));
                   tb2.setTotExpAppr(rs.getInt("expappr"));
               }
           }        
        
          ps.clearParameters();
          ps.setLong(1, grantnum);
          ps.setInt(2, fycode+2);
          rs = ps.executeQuery();
          while(rs.next()){
              tb3.setFycode(fycode+2);
              tb3.setTotAmtReq(rs.getInt("totreq"));
              tb3.setTotExpSub(rs.getInt("totsub"));
              if(isAdmin || asb.isShowscorecomm()){
                  tb3.setTotAmtAppr(rs.getInt("totappr"));
                  tb3.setTotExpAppr(rs.getInt("expappr"));
              }
          }
          
          map.put("1", tb1);
          map.put("2", tb2);
          map.put("3", tb3);
                 
      }catch(Exception e) {
        System.err.println("error calcFyBudgetTotals() " + e.getMessage().toString());
      }
      finally{
          Close(rs);
          Close(ps);
          Close(conn);
      }
      return map;
    }

  
 /**
   * This method will calculate the total amount requested across all budget categories
   * for given grant id.  It will save each category amt to a bean.  Please see other
   * methods for retrieving just totalAmtReq (w/o category totals).
   * @return 
   * @param grantid
   */ 
  public TotalsBean calcTotalAmtReqCategorized(long grantid, TotalsBean tb, int fycode, int fccode)
  {
    int total, pExp, bExp, cExp, sExp, oExp, tExp =0;

    try{
      if(fycode==0)
      {
        //calculate the total amount requested for all 5 category expenses
        pExp = getFieldTotal("Personal_Services", "grant_request", grantid);
        bExp = getFieldTotal("Employee_Benefits", "grant_request", grantid);
        cExp = getFieldTotal("Contracted_services", "grant_request", grantid);
        sExp = getFieldTotal("supp_mat_equips", "grant_request", grantid);
        oExp = getFieldTotal("Other_Expenses", "grant_request", grantid);
        tExp = getFieldTotal("Travel_Expenses", "grant_request", grantid);
      }
      else
      {
        pExp = getFieldTotalFY("Personal_Services", "grant_request", grantid, fycode);
        bExp = getFieldTotalFY("Employee_Benefits", "grant_request", grantid, fycode);
        cExp = getFieldTotalFY("Contracted_services", "grant_request", grantid, fycode);
        sExp = getFieldTotalFY("supp_mat_equips", "grant_request", grantid, fycode);
        oExp = getFieldTotalFY("Other_Expenses", "grant_request", grantid, fycode);
        tExp = getFieldTotalFY("Travel_Expenses", "grant_request", grantid, fycode);
      }
      
      //add together to get total amt requested          
      total = pExp + bExp + cExp + sExp + oExp +tExp;
      
      tb.setPerAmtReq(pExp);
      tb.setBenAmtReq(bExp);
      tb.setConAmtReq(cExp);
      tb.setSuppAmtReq(sExp);
      tb.setOthAmtReq(oExp);
      tb.setTravAmtReq(tExp);
      tb.setTotAmtReq(total);
      
      //limit for statutory aid grants is 158000 per app
      if(fccode==6 && (total > tb.getSaLimit()))
        tb.setWarning(true);
      //limit for discretionary grants is 40000 per app
      if(fccode==5 && (total > tb.getDiLimit()))
        tb.setWarning(true);        
            
      
    }catch(Exception e){
      System.err.println("error calcTotalAmtReqCategorized() " + e.getMessage().toString());
    }
    return tb;
  }
  
  
  
  public TotalsBean calcAmtExpSuppEquip(long grantid, TotalsBean tb, int fycode, boolean showApproval)
  { 
    int sumreq=0, sumappr=0, sumexp=0, sumexpappr=0, sumptot=0, suminst =0, sumamend=0;
    
    try{//calculate the total amount requested for supplies vs equip      
      conn = initializeConn();
      
      if(fycode==0)
      {
        ps = conn.prepareStatement("select sum(grant_request) as sumreq, sum(amount_approved) "+
        "as sumappr, sum(exp_submitted) as sumexp, sum(exp_aproved) as sumexpappr, sum(amend_amount) as sumamend, "+
        "sum(proj_total) as sumptot, sum(inst_cont) as suminst, smet_code "+
        "from supp_mat_equips where GRA_ID = ? group by smet_code");
        ps.setLong(1, grantid);
      }
      else
      {
        ps = conn.prepareStatement("select sum(grant_request) as sumreq, sum(amount_approved) "+
        "as sumappr, sum(exp_submitted) as sumexp, sum(exp_aproved) as sumexpappr, sum(amend_amount) as sumamend, "+
        "sum(proj_total) as sumptot, sum(inst_cont) as suminst, smet_code "+
        "from supp_mat_equips where GRA_ID = ? and fy_code=? group by smet_code");
        ps.setLong(1, grantid);
        ps.setInt(2, fycode);
      }
      
            
      rs = ps.executeQuery();      
      while(rs.next())
      {
        if(rs.getInt("smet_code")==1)
        {
          tb.setSupplyAmtReq(rs.getInt("sumreq"));
          tb.setSupplyExpSub(rs.getInt("sumexp"));
          tb.setSupplyAmtAmend(rs.getInt("sumamend"));
          if(showApproval){
            tb.setSupplyAmtAppr(rs.getInt("sumappr"));    
            tb.setSupplyExpAppr(rs.getInt("sumexpappr"));  
            }          
        }
        else if(rs.getInt("smet_code")==2)
        {
          tb.setEquipAmtReq(rs.getInt("sumreq"));
          tb.setEquipExpSub(rs.getInt("sumexp"));
          tb.setEquipAmtAmend(rs.getInt("sumamend"));
          if(showApproval){
              tb.setEquipAmtAppr(rs.getInt("sumappr"));    
              tb.setEquipExpAppr(rs.getInt("sumexpappr"));
          }                   
        }
        //get the grand totals for both profesional and support staff
        sumreq+= rs.getInt("sumreq");
        sumappr+=rs.getInt("sumappr");
        sumexp+=rs.getInt("sumexp");
        sumexpappr += rs.getInt("sumexpappr");
        sumptot += rs.getInt("sumptot");
        suminst += rs.getInt("suminst");        
        sumamend +=rs.getInt("sumamend");
      }   
      
      tb.setSuppAmtReq(sumreq);
      tb.setSuppExpSub(sumexp);
      tb.setSuppInstCont(suminst);
      tb.setSuppProjTot(sumptot);
      tb.setSuppAmtAmend(sumamend);
      if(showApproval){
        tb.setSuppAmtAppr(sumappr);
        tb.setSuppExpAppr(sumexpappr);
      }
                            
    }catch(Exception e){
      System.err.println("error calcAmtExpSuppEquip() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(rs);
      Close(conn);
    }
    return tb;
  }
    
  public TotalsBean calcAmtExpPurchBoces(long grantid, TotalsBean tb, int fycode, boolean showApproval)
  { 
    try{      
      //calculate the totals for purchased vs boces     
      conn = initializeConn();
      
      if(fycode==0)
      {
        ps = conn.prepareStatement("select sum(grant_request) as sumreq, sum(amount_approved) "+
        "as sumappr, sum(exp_submitted) as sumexp, sum(exp_approved) as sumexpappr, sum(amend_amount) as sumamend, "+
        "sum(proj_total) as sumptot, sum(inst_cont) as suminst, code "+
        "from contracted_services where GRA_ID = ? group by code");
        ps.setLong(1, grantid);
      }
      else
      {
        ps = conn.prepareStatement("select sum(grant_request) as sumreq, sum(amount_approved) "+
        "as sumappr, sum(exp_submitted) as sumexp, sum(exp_approved) as sumexpappr, sum(amend_amount) as sumamend, "+
        "sum(proj_total) as sumptot, sum(inst_cont) as suminst, code "+
        "from contracted_services where GRA_ID = ? and fy_code=? group by code");
        ps.setLong(1, grantid);
        ps.setInt(2, fycode);
      }
      int sumreq=0, sumappr=0, sumexp=0, sumexpappr=0, sumptot=0, suminst =0, sumamend=0;
      
      rs = ps.executeQuery();      
      while(rs.next())
      {
        if(rs.getInt("code")==5)
        {
          tb.setPurchAmtReq(rs.getInt("sumreq"));
          tb.setPurchExpSub(rs.getInt("sumexp"));
          tb.setPurchAmtAmend(rs.getInt("sumamend"));
          if(showApproval){
              tb.setPurchAmtAppr(rs.getInt("sumappr"));    
              tb.setPurchExpAppr(rs.getInt("sumexpappr"));
          }                  
        }
        else if(rs.getInt("code")==6)
        {
          tb.setBocesAmtReq(rs.getInt("sumreq"));
          tb.setBocesExpSub(rs.getInt("sumexp"));
          tb.setBocesAmtAmend(rs.getInt("sumamend"));
          if(showApproval){
              tb.setBocesAmtAppr(rs.getInt("sumappr"));    
              tb.setBocesExpAppr(rs.getInt("sumexpappr"));
          }        
        }
        //get the grand totals for both purchased and boces
        sumreq+= rs.getInt("sumreq");
        sumappr+=rs.getInt("sumappr");
        sumexp+=rs.getInt("sumexp");
        sumexpappr += rs.getInt("sumexpappr");
        sumptot += rs.getInt("sumptot");
        suminst += rs.getInt("suminst");        
        sumamend +=rs.getInt("sumamend");
      }   
      
      tb.setConAmtReq(sumreq);
      tb.setConExpSub(sumexp);
      tb.setConInstCont(suminst);
      tb.setConProjTot(sumptot); 
      tb.setConAmtAmend(sumamend);
      if(showApproval){
        tb.setConAmtAppr(sumappr);
        tb.setConExpAppr(sumexpappr);
      }
                            
    }catch(Exception e){
      System.err.println("error calcAmtExpPurchBoces() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(rs);
      Close(conn);
    }
    return tb;
  }
    
  /**
   * Method used to calc total professional staff vs support staff within personal_services
   * table.  Used for FS10Long form categories and lgrmif budget tabs.
   * @return 
   * @param fycode
   * @param tb
   * @param grantid
   */
  public TotalsBean calcAmtExpProfSupport(long grantid, TotalsBean tb, int fycode, boolean showApproval)
  { 
    try{      
      //calculate the total amount req/appr, exp req/appr for supplies vs equip      
      conn = initializeConn();
      
      if(fycode==0)
      {
        ps = conn.prepareStatement("select sum(grant_request) as sumreq, sum(amount_approved) "+
        "as sumappr, sum(exp_submitted) as sumexp, sum(exp_approved) as sumexpappr, sum(amend_amount) as sumamend, "+
        "sum(proj_total) as sumptot, sum(inst_cont) as suminst, smet_code "+
        "from personal_services where GRA_ID = ? group by smet_code");
        ps.setLong(1, grantid);
      }
      else
      {
        ps = conn.prepareStatement("select sum(grant_request) as sumreq, sum(amount_approved) "+
        "as sumappr, sum(exp_submitted) as sumexp, sum(exp_approved) as sumexpappr, sum(amend_amount) as sumamend, "+
        "sum(proj_total) as sumptot, sum(inst_cont) as suminst, smet_code "+
        "from personal_services where GRA_ID = ? and fy_code=? group by smet_code");
        ps.setLong(1, grantid);
        ps.setInt(2, fycode);
      }
            
      int sumreq=0, sumappr=0, sumexp=0, sumexpappr=0, sumptot=0, suminst =0, sumamend=0;
      
      rs = ps.executeQuery();      
      while(rs.next())
      {
        if(rs.getInt("smet_code")==0 || rs.getInt("smet_code")==3)
        {
          tb.setProffAmtReq(rs.getInt("sumreq"));
          tb.setProffExpSub(rs.getInt("sumexp"));
          tb.setProffAmtAmend(rs.getInt("sumamend"));
          if(showApproval){              
              tb.setProffAmtAppr(rs.getInt("sumappr"));    
              tb.setProffExpAppr(rs.getInt("sumexpappr"));
          }          
        }
        else if(rs.getInt("smet_code")==4)
        {
          tb.setProfsuppAmtReq(rs.getInt("sumreq"));
          tb.setProfsuppExpSub(rs.getInt("sumexp"));
          tb.setProfsuppAmtAmend(rs.getInt("sumamend"));
          if(showApproval){              
              tb.setProfsuppAmtAppr(rs.getInt("sumappr")); 
              tb.setProfsuppExpAppr(rs.getInt("sumexpappr"));
          }
       }
        //get the grand totals for both proffesional and support staff
        sumreq+= rs.getInt("sumreq");
        sumappr+=rs.getInt("sumappr");
        sumexp+=rs.getInt("sumexp");
        sumexpappr += rs.getInt("sumexpappr");
        sumptot += rs.getInt("sumptot");
        suminst += rs.getInt("suminst");       
        sumamend += rs.getInt("sumamend");
      }
      
      tb.setPerAmtReq(sumreq);
      tb.setPerExpSub(sumexp);
      tb.setPerProjTot(sumptot);
      tb.setPerInstCont(suminst);      
      tb.setPerAmtAmend(sumamend);
      if(showApproval){
        tb.setPerAmtAppr(sumappr);
        tb.setPerExpAppr(sumexpappr);
      }
                            
    }catch(Exception e){
      System.err.println("error calcAmtExpProfSupport() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(rs);
      Close(conn);
    }
    return tb;
  }
   
   
  public TotalsBean calcAmtExpBenefits(long grantid, TotalsBean tb, int fycode, boolean showApproval)
  { 
    try{//calculate the total amount req/appr, exp req/appr     
      conn = initializeConn();
      
      if(fycode==0)
      {
        ps = conn.prepareStatement("select sum(grant_request) as sumreq, sum(amount_approved) "+
        "as sumappr, sum(exp_submitted) as sumexp, sum(exp_approved) as sumexpappr, sum(amend_amount) as sumamend, "+
        "sum(proj_total) as sumptot, sum(inst_cont) as suminst from employee_benefits where "+
        "GRA_ID =? ");
        ps.setLong(1, grantid);
      }
      else
      {
        ps = conn.prepareStatement("select sum(grant_request) as sumreq, sum(amount_approved) "+
        "as sumappr, sum(exp_submitted) as sumexp, sum(exp_approved) as sumexpappr, sum(amend_amount) as sumamend, "+
        "sum(proj_total) as sumptot, sum(inst_cont) as suminst from "+
        "employee_benefits where GRA_ID =? and fy_code=?");
        ps.setLong(1, grantid);
        ps.setInt(2, fycode);
      }
            
      rs = ps.executeQuery();      
      while(rs.next())
      {        
          tb.setBenAmtReq(rs.getInt("sumreq"));          
          tb.setBenExpSub(rs.getInt("sumexp"));          
          tb.setBenProjTot(rs.getInt("sumptot"));
          tb.setBenInstCont(rs.getInt("suminst"));
          tb.setBenAmtAmend(rs.getInt("sumamend"));
          
          if(showApproval)
          {
            tb.setBenAmtAppr(rs.getInt("sumappr"));
            tb.setBenExpAppr(rs.getInt("sumexpappr"));
          }
      }   
                            
    }catch(Exception e){
      System.err.println("error calcAmtExpBenefits() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(rs);
      Close(conn);
    }
    return tb;
  }
  
  
  public TotalsBean calcAmtExpTravel(long grantid, TotalsBean tb, int fycode, boolean showApproval)
  { 
    try{      
      //calculate the total amount req/appr, exp req/appr     
      conn = initializeConn();
      
      if(fycode==0)
      {
        ps = conn.prepareStatement("select sum(grant_request) as sumreq, sum(amount_approved) "+
        "as sumappr, sum(exp_submitted) as sumexp, sum(exp_approved) as sumexpappr, sum(amend_amount) as sumamend, "+
        "sum(proj_total) as sumptot, sum(inst_cont) as suminst from "+
        "travel_expenses where GRA_ID =? ");
        ps.setLong(1, grantid);
      }
      else
      {
        ps = conn.prepareStatement("select sum(grant_request) as sumreq, sum(amount_approved) "+
        "as sumappr, sum(exp_submitted) as sumexp, sum(exp_approved) as sumexpappr, sum(amend_amount) as sumamend, "+
        "sum(proj_total) as sumptot, sum(inst_cont) as suminst from "+
        "travel_expenses where GRA_ID =? and fy_code=?");
        ps.setLong(1, grantid);
        ps.setInt(2, fycode);
      }
            
      rs = ps.executeQuery();      
      while(rs.next())
      {        
          tb.setTravAmtReq(rs.getInt("sumreq"));          
          tb.setTravExpSub(rs.getInt("sumexp"));
          tb.setTravInstCont(rs.getInt("suminst"));
          tb.setTravProjTot(rs.getInt("sumptot"));
          tb.setTravAmtAmend(rs.getInt("sumamend"));
          
          if(showApproval)
          {
            tb.setTravAmtAppr(rs.getInt("sumappr"));
            tb.setTravExpAppr(rs.getInt("sumexpappr"));
          }
      }   
                            
    }catch(Exception e){
      System.err.println("error calcAmtExpTravel() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(rs);
      Close(conn);
    }
    return tb;
  }
  
  
  public TotalsBean calcAmtExpOther(long grantid, TotalsBean tb, int fycode, boolean showApproval)
  { 
    try{      
      //calculate the total amount req/appr, exp req/appr     
      conn = initializeConn();
      
      if(fycode==0)
      {
        ps = conn.prepareStatement("select sum(grant_request) as sumreq, sum(amount_approved) "+
        "as sumappr, sum(exp_submitted) as sumexp, sum(exp_approved) as sumexpappr, sum(amend_amount) as sumamend, "+
        "sum(proj_total) as sumptot, sum(inst_cont) as suminst from "+
        "other_expenses where GRA_ID =? ");
        ps.setLong(1, grantid);
      }
      else
      {
        ps = conn.prepareStatement("select sum(grant_request) as sumreq, sum(amount_approved) "+
        "as sumappr, sum(exp_submitted) as sumexp, sum(exp_approved) as sumexpappr, sum(amend_amount) as sumamend, "+
        "sum(proj_total) as sumptot, sum(inst_cont) as suminst from "+
        "other_expenses where GRA_ID =? and fy_code=?");
        ps.setLong(1, grantid);
        ps.setInt(2, fycode);
      }
            
      rs = ps.executeQuery();      
      while(rs.next())
      {        
          tb.setOthAmtReq(rs.getInt("sumreq"));          
          tb.setOthExpSub(rs.getInt("sumexp"));          
          tb.setOthInstCont(rs.getInt("suminst"));
          tb.setOthProjTot(rs.getInt("sumptot"));
          tb.setOthAmtAmend(rs.getInt("sumamend"));
          
          if(showApproval)
          {
            tb.setOthAmtAppr(rs.getInt("sumappr"));
            tb.setOthExpAppr(rs.getInt("sumexpappr"));
          }
      }   
                            
    }catch(Exception e){
      System.err.println("error calcAmtExpOther() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(rs);
      Close(conn);
    }
    return tb;
  }
  
  
  /**
   * This method will return the total amt requested for given grantid, either for all
   * years of project, or for certain fy of the project.
   * There is a separate method that will calc totalAmtRequested and save each category total to a bean.
   * @return 
   * @param grantid
   */
  public int calcTotalAmtRequested(long grantid, int fycode)
  {
    int total =0;
  
    try{      
      if(fycode==0){
          //calculate the total amount requested for all fy's of grant
           conn = initializeConn();
           ps = conn.prepareStatement("select sum(totreq) as totamtreq from ldgrants.BUDGETTOTALSVIEW "+
           " where gra_id=?");
           ps.setLong(1, grantid);
           rs = ps.executeQuery();
           while(rs.next()){
               total = rs.getInt("totamtreq");
           }
                      
      }else{
        //calc total amt req for certain fy  
        conn = initializeConn();
        ps = conn.prepareStatement("select sum(totreq) as sumreq from ldgrants.multifyproj_budgettotals_view " + 
        "where gra_id=? and fy_code=? ");
        ps.setLong(1, grantid);
        ps.setInt(2, fycode);
        rs = ps.executeQuery();
        while(rs.next()){
            total = rs.getInt("sumreq");
        }
      }
      
    }catch(Exception e){
      System.err.println("error calcTotalAmtRequested() " + e.getMessage().toString());
    }
    finally{
        Close(rs);
        Close(ps);
        Close(conn);
    }
    return total;
  }
  
  
  /**
   * This method will add a blank record to a given budget table.  
   * Currently used for SA/di/lg add budget records.
   * @return success/fail depending on outcome of insert
   * @param userb
   */
  public int addBudgetItem(UserBean userb, long grantid, int currtab, int typecode)
  {
    int numrows=0;
    long personalId = 0;
  
    try {
      conn = initializeConn();      
      
      switch(currtab){
        case 1: // Add to Personal_Services -defaults to SMET_CODE 3 if no code selected
            ps = conn.prepareStatement("insert into PERSONAL_SERVICES(ID, GRA_ID, DATE_CREATED, CREATED_BY, "+
            " NAME, GRANT_REQUEST, SMET_CODE) values (PROJ_BUDG_SEQ.nextval, ?, SYSDATE, ?, ?, 0, ?)");
            ps.setLong(1, grantid);            
            ps.setString(2, userb.getUserid());
            ps.setString(3, " ");
            if(typecode!=0)
              ps.setInt(4, typecode);
            else
              ps.setInt(4, 3);
            numrows = ps.executeUpdate();
            
            if(numrows>0){
              //get the last id that was added
              ps.clearParameters();
              ps = conn.prepareStatement("select proj_budg_seq.currval from dual");
              rs = ps.executeQuery();
              while(rs.next())
                personalId = rs.getLong(1);              
            }
            break;
            
        case 2: // Add to Employee_Benefits
            ps = conn.prepareStatement("insert into EMPLOYEE_BENEFITS(ID, GRA_ID, DATE_CREATED, CREATED_BY, "+
            " NAME, GRANT_REQUEST) values (PROJ_BUDG_SEQ.nextval,?,SYSDATE,?,?,0)   ");
            ps.setLong(1, grantid);            
            ps.setString(2, userb.getUserid());
            ps.setString(3, " ");
            numrows = ps.executeUpdate();
            break;
            
        case 3: // Add to Contracted_Services-default to code 5 if no code selected
            ps = conn.prepareStatement("insert into CONTRACTED_SERVICES(ID, GRA_ID, CODE, DATE_CREATED, "+
            " CREATED_BY, SERVICE_TYPE, GRANT_REQUEST)" +
            " values(PROJ_BUDG_SEQ.nextval,?,?,SYSDATE,?,?,0)");
            ps.setLong(1, grantid);            
            if(typecode!=0)
              ps.setInt(2, typecode);
            else
              ps.setInt(2, 5);
            ps.setString(3, userb.getUserid());
            ps.setString(4, " ");
            numrows = ps.executeUpdate();
            break;            

        case 4: // Add to Supp_Mat_Equips -defaults to SMET_CODE 1 if no code is selected
            ps = conn.prepareStatement("insert into SUPP_MAT_EQUIPS(ID, GRA_ID, SMET_CODE, DATE_CREATED, CREATED_BY, "+
            " QUANTITY, DESCRIPTION, GRANT_REQUEST)" +
            " values(PROJ_BUDG_SEQ.nextval, ?, ?, SYSDATE, ?,0,?,0)");
            ps.setLong(1, grantid);   
            if(typecode!=0)
              ps.setInt(2, typecode);
            else
              ps.setInt(2, 1);
            ps.setString(3, userb.getUserid());
            ps.setString(4, " ");
            numrows = ps.executeUpdate();
            break;

        case 5: // Add to Other_Expenses
            ps = conn.prepareStatement("insert into OTHER_EXPENSES(ID, GRA_ID, DATE_CREATED, CREATED_BY, "+
            " DESCRIPTION, GRANT_REQUEST, COST_SUMMARY) values(PROJ_BUDG_SEQ.nextval, ?, SYSDATE, ?, ?, 0, ? )");
            ps.setLong(1, grantid);            
            ps.setString(2, userb.getUserid());
            ps.setString(3, " ");
            ps.setString(4, " ");
            numrows = ps.executeUpdate();
            break;
            
        case 6: //Add to travel_expenses
          ps = conn.prepareStatement("insert into travel_expenses (id, GRA_ID, DATE_CREATED, CREATED_BY, "+
          "  GRANT_REQUEST) values(PROJ_BUDG_SEQ.nextval, ?, SYSDATE, ?, 0)");
          ps.setLong(1, grantid);            
          ps.setString(2, userb.getUserid());
          numrows = ps.executeUpdate();
          break;
      }
            
      if(currtab==1){
        //add a benefits record to correspond to this personal record
        ps.clearParameters();
        ps = conn.prepareStatement("insert into EMPLOYEE_BENEFITS(ID, GRA_ID, DATE_CREATED, CREATED_BY, "+
        " NAME, GRANT_REQUEST, PERS_ID) values (PROJ_BUDG_SEQ.nextval,?,SYSDATE,?,?,0,?)   ");
        ps.setLong(1, grantid);            
        ps.setString(2, userb.getUserid());
        ps.setString(3, " ");
        ps.setLong(4, personalId);
        numrows = ps.executeUpdate();
      }
                             
    } catch (Exception ex){
        System.err.println("error addBudgetItem()  " + ex.toString());
        return 0;
    }    
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }    
    return numrows;    
  }
  
  
  
  
  /**
   * This method will delete a record from one of the budget tables. It uses the tab page
   * to determine the budget table - and then deletes the given row id.
   * @return 
   * @param curr_tab
   * @param rowID
   */
  public int deletePbItem(int curr_tab, long rowID) throws Exception
  {      
    int outcome=0;         
    long associateID = 0;
       
    try {
     conn = initializeConn();      
      
      switch(curr_tab)
      {      
        case 1:      
        //check for associated BENEFITS records, and delete those first
        ps = conn.prepareStatement("select id from employee_benefits where pers_id=?");
        ps.setLong(1, rowID);
        rs = ps.executeQuery();
        while(rs.next())
          associateID = rs.getLong("id");
          
        if(associateID !=0)
        {
          ps.clearParameters();
          ps = conn.prepareStatement("delete from EMPLOYEE_BENEFITS where ID = ?");        
          ps.setLong(1, associateID);
          outcome = ps.executeUpdate();
        }
        ps.clearParameters();
        ps = conn.prepareStatement("delete from PERSONAL_SERVICES where ID = ?");        
        ps.setLong(1, rowID);
        outcome = ps.executeUpdate();
        break;
        
    case 2:
        ps = conn.prepareStatement("delete from EMPLOYEE_BENEFITS where ID = ?");        
        ps.setLong(1, rowID);
        outcome = ps.executeUpdate();
        break;
        
    case 3:
        ps = conn.prepareStatement("delete from CONTRACTED_SERVICES where ID = ?");        
        ps.setLong(1, rowID);
        outcome = ps.executeUpdate();
        break;
    
    case 4:
        ps = conn.prepareStatement("delete from SUPP_MAT_EQUIPS where ID = ?");        
        ps.setLong(1, rowID);
        outcome = ps.executeUpdate();
        break;
        
    case 5:
        ps = conn.prepareStatement("delete from OTHER_EXPENSES where ID = ?");        
        ps.setLong(1, rowID);
        outcome = ps.executeUpdate();
        break;
        
    case 6:
        ps = conn.prepareStatement("delete from TRAVEL_EXPENSES where ID = ?");        
        ps.setLong(1, rowID);
        outcome = ps.executeUpdate();
        break;
    }
                      
    } catch (Exception ex){
        System.err.println("error deletePbItem() " + ex.toString());
        throw new Exception(ex.toString(), ex);
    }    
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }
    
    return outcome;  
  }



    public String getBudgetRecordDesc(int curr_tab, long rowID) throws Exception
    {      
      String desc="";         
          
      try {
       conn = initializeConn();      
        
        switch(curr_tab)
        {      
          case 1:               
          ps = conn.prepareStatement("select * from PERSONAL_SERVICES where ID = ?");        
          ps.setLong(1, rowID);
          rs = ps.executeQuery();
          while(rs.next()){
              desc = rs.getString("name");
          }
          break;
          
      case 2:
          ps = conn.prepareStatement("select * from EMPLOYEE_BENEFITS where ID = ?");        
          ps.setLong(1, rowID);
          rs = ps.executeQuery();
          while(rs.next()){
              desc = rs.getString("name");
          }
          break;
          
      case 3:
          ps = conn.prepareStatement("select * from CONTRACTED_SERVICES where ID = ?");        
          ps.setLong(1, rowID);
          rs = ps.executeQuery();
          while(rs.next()){
              desc = rs.getString("recipient");
          }
          break;
      
      case 4:
          ps = conn.prepareStatement("select * from SUPP_MAT_EQUIPS where ID = ?");        
          ps.setLong(1, rowID);
          rs = ps.executeQuery();
          while(rs.next()){
              desc = rs.getString("description");
          }
          break;
          
      case 5:
          ps = conn.prepareStatement("select * from OTHER_EXPENSES where ID = ?");        
          ps.setLong(1, rowID);
          rs = ps.executeQuery();
          while(rs.next()){
              desc = rs.getString("description");
          }
          break;
          
      case 6:
          ps = conn.prepareStatement("select * from TRAVEL_EXPENSES where ID = ?");        
          ps.setLong(1, rowID);
          rs = ps.executeQuery();
          while(rs.next()){
              desc = rs.getString("description");
          }
          break;
      }
                        
      } catch (Exception ex){
          System.err.println("error getBudgetRecordDesc() " + ex.toString());
          throw new Exception(ex.toString(), ex);
      }    
      finally{
        Close(conn);
        Close(ps);
        Close(rs);
      }      
      return desc;  
    }
  
  
  /**
   * This will save any values that the admin types in for amount approved or expenses
   * approved for SA/CO/DI/CN grant. 2/26/08 modified to throw exception
   * @return 
   * @throws java.lang.Exception
   * @param tab
   * @param userb
   * @param bc
   */
  public int updateAdminBudget(BudgetCollectionBean bc, UserBean userb, int tab) throws Exception
  {      
    int outcome=1;
    String user_id = userb.getUserid();    
          
    try{      
      conn = initializeConn();
      
      switch(tab){      
          case 1:
          //PERSONAL SERVICE EXPENSES
          ps = conn.prepareStatement("update PERSONAL_SERVICES set AMOUNT_APPROVED = ?, EXP_APPROVED =?, " + 
              " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?");
          
          //get the number of personal service records
          List allPersonal = bc.getAllPersRecords();    
          int numPerExp =0;
          if(allPersonal!=null)
            numPerExp= allPersonal.size();
        
          //loop on the number of personal service records and get all info
          for(int i=0;i<numPerExp; i++)
          {
             PersonalBean pb = (PersonalBean) allPersonal.get(i);
             
            String amtappr = pb.getAmountapprovedStr();
            int amtappr_int = 0;
            if(amtappr!= null && !amtappr.equals(""))
            {
              amtappr_int = dbh.parseCurrencyAmtNoDecimal(amtappr);//this will get rid of any commas, decimals or $ in the value
            }
              
            String exp = pb.getExpapprovedStr();
            int exp_int = 0;
            if(exp!= null && !exp.equals(""))
              exp_int = dbh.parseCurrencyAmtNoDecimal(exp);
            
            long id = pb.getId();
                        
            //set params and update
            ps.setInt(1, amtappr_int);
            ps.setInt(2, exp_int);
            ps.setString(3, user_id);
            ps.setLong(4, id);            
           
            outcome = ps.executeUpdate();
          }
        break;
          
          case 2:
          //BENEFITS EXPENSES
          ps = conn.prepareStatement("update EMPLOYEE_BENEFITS set AMOUNT_APPROVED = ?, EXP_APPROVED = ?, " + 
              " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?");
          
          //get the number of benfit exp records
          List allBenefits = bc.getAllBenRecords();
          int numBenExp = 0;
          if(allBenefits!=null)
            numBenExp= allBenefits.size();
      
          //loop on the number of benefits expense records and get all info
          for(int i=0;i<numBenExp; i++)
          {
            BenefitsBean bb = (BenefitsBean) allBenefits.get(i);
            
            String amtappr =bb.getAmountapprovedStr();
            int amtappr_int = 0;
            if(amtappr!= null && !amtappr.equals(""))
              amtappr_int = dbh.parseCurrencyAmtNoDecimal(amtappr);
              
            String exp = bb.getExpapprovedStr();
            int exp_int = 0;
            if(exp!= null && !exp.equals(""))
             exp_int = dbh.parseCurrencyAmtNoDecimal(exp);
                   
            long id = bb.getId();
                         
            //set params and update           
            ps.setInt(1, amtappr_int);
            ps.setInt(2, exp_int);
            ps.setString(3, user_id);
            ps.setLong(4, id);
            
            outcome = ps.executeUpdate();
          }
          break;
          
          
          case 3:
          //CONTRACTED EXPENSES
          ps = conn.prepareStatement("update CONTRACTED_SERVICES set AMOUNT_APPROVED = ?, "+
          " EXP_APPROVED = ?, DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?");
          
          //get the number of contracted records
          List allContract = bc.getAllContractRecords();
          int numConExp = 0;
          if(allContract!=null)
            numConExp =allContract.size();
            
          //loop on the number of contracted expense records and get all info
          for(int i=0;i<numConExp; i++)
          {
            ContractedBean cb = (ContractedBean) allContract.get(i);
        
            String amtappr =cb.getAmountapprovedStr();
            int amtappr_int = 0;
            if(amtappr!= null && !amtappr.equals(""))
              amtappr_int = dbh.parseCurrencyAmtNoDecimal(amtappr);
              
            String exp = cb.getExpapprovedStr();
            int exp_int = 0;
            if(exp!= null && !exp.equals(""))
              exp_int = dbh.parseCurrencyAmtNoDecimal(exp);
                   
            long id =cb.getId();
                         
            //set params and update
            ps.setInt(1, amtappr_int);
            ps.setInt(2, exp_int);
            ps.setString(3, user_id);
            ps.setLong(4, id);
            
            outcome = ps.executeUpdate();
          }
          break;
          
          
          case 4:
          //SUPPLY MATERIAL EQUIP EXPENSES 
          ps = conn.prepareStatement("update SUPP_MAT_EQUIPS set AMOUNT_APPROVED = ?, "+
          " EXP_APROVED = ?, DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?");
          
          //get the number of supply records
          List allSupply= bc.getAllSupplyRecords();
          int numSmeExp = 0;
          if(allSupply!=null)
            numSmeExp =allSupply.size();
        
          //loop on the number of supply, material expense records and get all info
          for(int i=0;i<numSmeExp; i++)
          {
            SupplyBean sb = (SupplyBean) allSupply.get(i);
                         
            String amtappr = sb.getAmountapprovedStr();
            int amtappr_int = 0;
            if(amtappr!= null && !amtappr.equals(""))
              amtappr_int = dbh.parseCurrencyAmtNoDecimal(amtappr);
              
            String exp =sb.getExpapprovedStr();
            int exp_int = 0;
            if(exp!= null && !exp.equals(""))
              exp_int = dbh.parseCurrencyAmtNoDecimal(exp);
                   
            long id = sb.getId();
                                             
            //set params and update
            ps.setInt(1, amtappr_int);
            ps.setInt(2, exp_int);
            ps.setString(3, user_id);
            ps.setLong(4, id);
            
            outcome = ps.executeUpdate();
          }
          break;
          
          
          case 5:
          //OTHER EXPENSES
          ps = conn.prepareStatement("update OTHER_EXPENSES set AMOUNT_APPROVED = ?, EXP_APPROVED = ?, " + 
              " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?");
          
          //get the number of other_exp records
          List allOther = bc.getAllExpRecords();
          int numOtExp = 0;
          if(allOther!=null)
            numOtExp= allOther.size();

          //loop on the number of other_expense records and get all info
          for(int i=0;i<numOtExp; i++)
          {
            OtherExpBean ob = (OtherExpBean) allOther.get(i);
            
            String amtappr = ob.getAmountapprovedStr();
            int amtappr_int = 0;
            if(amtappr!= null && !amtappr.equals(""))
              amtappr_int = dbh.parseCurrencyAmtNoDecimal(amtappr);
              
            String exp =ob.getExpapprovedStr();
            int exp_int = 0;
            if(exp!= null && !exp.equals(""))
              exp_int = dbh.parseCurrencyAmtNoDecimal(exp);
                   
            long id = ob.getId();
                         
            //set params and update
            ps.setInt(1, amtappr_int);
            ps.setInt(2, exp_int);
            ps.setString(3, user_id);
            ps.setLong(4, id);
            
            outcome = ps.executeUpdate();           
          }
          break;
          
          
        case 6:
          //TRAVEL EXPENSES
          ps = conn.prepareStatement("update TRAVEL_EXPENSES set AMOUNT_APPROVED = ?, EXP_APPROVED = ?, " + 
              " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?");
          
          //get the number of other_exp records
          List allTravel = bc.getAllTravelRecords();
          int numTrExp = 0;
          if(allTravel !=null)
            numTrExp = allTravel.size();
        
          //loop on the number of other_expense records and get all info
          for(int i=0;i<numTrExp; i++)
          {
            TravelBean tb = (TravelBean) allTravel.get(i);
            
            String amtappr =tb.getAmountapprovedStr();
            int amtappr_int = 0;
            if(amtappr!= null && !amtappr.equals(""))
              amtappr_int = dbh.parseCurrencyAmtNoDecimal(amtappr);
              
            String exp =tb.getExpapprovedStr();
            int exp_int = 0;
            if(exp!= null && !exp.equals(""))
              exp_int = dbh.parseCurrencyAmtNoDecimal(exp);
                   
            long id =tb.getId();
                         
            //set params and update
            ps.setInt(1, amtappr_int);
            ps.setInt(2, exp_int);
            ps.setString(3, user_id);
            ps.setLong(4, id);
            
            outcome = ps.executeUpdate();           
          }
          break;
      }
      
  }catch(Exception e){
    System.err.println("error updateAdminBudget() "+ e.getMessage().toString());
    throw new Exception(e.toString(), e);
  }
  finally{
    Close(conn);
    Close(rs);
    Close(ps);
  }
    
    return outcome;
  }


    public int updateAdminBudgetAmendment(BudgetCollectionBean bc, UserBean userb, int tab) throws Exception
    {      
      int outcome=1;
      String user_id = userb.getUserid();    
            
      try{      
        conn = initializeConn();
        
        switch(tab){      
            case 1:
            //PERSONAL SERVICE EXPENSES
            ps = conn.prepareStatement("update PERSONAL_SERVICES set AMOUNT_APPROVED = ?, EXP_APPROVED =?, " + 
                " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ?, AMEND_AMOUNT=? where ID = ?");
            
            //get the number of personal service records
            List allPersonal = bc.getAllPersRecords();      
            int numPerExp =0;
            if(allPersonal!=null)
              numPerExp= allPersonal.size();
                  
            //loop on the number of personal service records and get all info
            for(int i=0;i<numPerExp; i++)
            {
               PersonalBean pb = (PersonalBean) allPersonal.get(i);
               
              String amtappr = pb.getAmountapprovedStr();
              int amtappr_int = 0;
              if(amtappr!= null && !amtappr.equals(""))
              {
                amtappr_int = dbh.parseCurrencyAmtNoDecimal(amtappr);//this will get rid of any commas, decimals or $ in the value
              }
                
              String exp = pb.getExpapprovedStr();
              int exp_int = 0;
              if(exp!= null && !exp.equals(""))
                exp_int = dbh.parseCurrencyAmtNoDecimal(exp);
                
              String amend = pb.getAmtamendedStr();
              int amend_int = 0;
              if(amend!= null && !amend.equals(""))
                amend_int = dbh.parseCurrencyAmtNoDecimal(amend);
              
              long id = pb.getId();
                          
              //set params and update
              ps.setInt(1, amtappr_int);
              ps.setInt(2, exp_int);
              ps.setString(3, user_id);
              ps.setInt(4, amend_int);
              ps.setLong(5, id);            
             
              outcome = ps.executeUpdate();
            }
          break;
            
            case 2:
            //BENEFITS EXPENSES
            ps = conn.prepareStatement("update EMPLOYEE_BENEFITS set AMOUNT_APPROVED = ?, EXP_APPROVED = ?, " + 
                " DATE_MODIFIED = SYSDATE, MODIFIED_BY =?, AMEND_AMOUNT=? where ID = ?");
            
            //get the number of benfit exp records
            List allBenefits = bc.getAllBenRecords();
            int numBenExp = 0;
            if(allBenefits!=null)
              numBenExp= allBenefits.size();
        
            //loop on the number of benefits expense records and get all info
            for(int i=0;i<numBenExp; i++)
            {
              BenefitsBean bb = (BenefitsBean) allBenefits.get(i);
              
              String amtappr =bb.getAmountapprovedStr();
              int amtappr_int = 0;
              if(amtappr!= null && !amtappr.equals(""))
                amtappr_int = dbh.parseCurrencyAmtNoDecimal(amtappr);
                
              String exp = bb.getExpapprovedStr();
              int exp_int = 0;
              if(exp!= null && !exp.equals(""))
               exp_int = dbh.parseCurrencyAmtNoDecimal(exp);
                     
              String amend = bb.getAmtamendedStr();
              int amend_int = 0;
              if(amend!= null && !amend.equals(""))
                amend_int = dbh.parseCurrencyAmtNoDecimal(amend);
                  
              long id = bb.getId();
                           
              //set params and update           
              ps.setInt(1, amtappr_int);
              ps.setInt(2, exp_int);
              ps.setString(3, user_id);
              ps.setInt(4, amend_int);
              ps.setLong(5, id);
              
              outcome = ps.executeUpdate();
            }
            break;
            
            
            case 3:
            //CONTRACTED EXPENSES
            ps = conn.prepareStatement("update CONTRACTED_SERVICES set AMOUNT_APPROVED = ?, "+
            " EXP_APPROVED=?, DATE_MODIFIED=SYSDATE, MODIFIED_BY=?, AMEND_AMOUNT=? where ID=?");
            
            //get the number of contracted records
            List allContract = bc.getAllContractRecords();
            int numConExp = 0;
            if(allContract!=null)
              numConExp =allContract.size();
              
            //loop on the number of contracted expense records and get all info
            for(int i=0;i<numConExp; i++)
            {
              ContractedBean cb = (ContractedBean) allContract.get(i);
          
              String amtappr =cb.getAmountapprovedStr();
              int amtappr_int = 0;
              if(amtappr!= null && !amtappr.equals(""))
                amtappr_int = dbh.parseCurrencyAmtNoDecimal(amtappr);
                
              String exp = cb.getExpapprovedStr();
              int exp_int = 0;
              if(exp!= null && !exp.equals(""))
                exp_int = dbh.parseCurrencyAmtNoDecimal(exp);
                
              String amend = cb.getAmtamendedStr();
              int amend_int = 0;
              if(amend!= null && !amend.equals(""))
                amend_int = dbh.parseCurrencyAmtNoDecimal(amend);
                     
              long id =cb.getId();
                           
              //set params and update
              ps.setInt(1, amtappr_int);
              ps.setInt(2, exp_int);
              ps.setString(3, user_id);
              ps.setInt(4, amend_int);
              ps.setLong(5, id);
              
              outcome = ps.executeUpdate();
            }
            break;
            
            
            case 4:
            //SUPPLY MATERIAL EQUIP EXPENSES 
            ps = conn.prepareStatement("update SUPP_MAT_EQUIPS set AMOUNT_APPROVED = ?, "+
            " EXP_APROVED=?, DATE_MODIFIED=SYSDATE, MODIFIED_BY=?, AMEND_AMOUNT=? where ID=?");
            
            //get the number of supply records
            List allSupply= bc.getAllSupplyRecords();
            int numSmeExp = 0;
            if(allSupply!=null)
              numSmeExp =allSupply.size();
          
            //loop on the number of supply, material expense records and get all info
            for(int i=0;i<numSmeExp; i++)
            {
              SupplyBean sb = (SupplyBean) allSupply.get(i);
                           
              String amtappr = sb.getAmountapprovedStr();
              int amtappr_int = 0;
              if(amtappr!= null && !amtappr.equals(""))
                amtappr_int = dbh.parseCurrencyAmtNoDecimal(amtappr);
                
              String exp =sb.getExpapprovedStr();
              int exp_int = 0;
              if(exp!= null && !exp.equals(""))
                exp_int = dbh.parseCurrencyAmtNoDecimal(exp);
                     
              String amend = sb.getAmtamendedStr();
              int amend_int = 0;
              if(amend!= null && !amend.equals(""))
                amend_int = dbh.parseCurrencyAmtNoDecimal(amend);
                  
              long id = sb.getId();
                                               
              //set params and update
              ps.setInt(1, amtappr_int);
              ps.setInt(2, exp_int);
              ps.setString(3, user_id);
              ps.setInt(4, amend_int);
              ps.setLong(5, id);
              
              outcome = ps.executeUpdate();
            }
            break;
            
            
            case 5:
            //OTHER EXPENSES
            ps = conn.prepareStatement("update OTHER_EXPENSES set AMOUNT_APPROVED=?, EXP_APPROVED = ?, " + 
            " DATE_MODIFIED=SYSDATE, MODIFIED_BY=?, AMEND_AMOUNT=? where ID=?");
            
            //get the number of other_exp records
            List allOther = bc.getAllExpRecords();
            int numOtExp = 0;
            if(allOther!=null)
              numOtExp= allOther.size();

            //loop on the number of other_expense records and get all info
            for(int i=0;i<numOtExp; i++)
            {
              OtherExpBean ob = (OtherExpBean) allOther.get(i);
              
              String amtappr = ob.getAmountapprovedStr();
              int amtappr_int = 0;
              if(amtappr!= null && !amtappr.equals(""))
                amtappr_int = dbh.parseCurrencyAmtNoDecimal(amtappr);
                
              String exp =ob.getExpapprovedStr();
              int exp_int = 0;
              if(exp!= null && !exp.equals(""))
                exp_int = dbh.parseCurrencyAmtNoDecimal(exp);
                     
              String amend = ob.getAmtamendedStr();
              int amend_int = 0;
              if(amend!= null && !amend.equals(""))
                amend_int = dbh.parseCurrencyAmtNoDecimal(amend);
                  
              long id = ob.getId();
                           
              //set params and update
              ps.setInt(1, amtappr_int);
              ps.setInt(2, exp_int);
              ps.setString(3, user_id);
              ps.setInt(4, amend_int);
              ps.setLong(5, id);
              
              outcome = ps.executeUpdate();           
            }
            break;
            
            
          case 6:
            //TRAVEL EXPENSES
            ps = conn.prepareStatement("update TRAVEL_EXPENSES set AMOUNT_APPROVED = ?, EXP_APPROVED = ?, " + 
            " DATE_MODIFIED=SYSDATE, MODIFIED_BY=?, AMEND_AMOUNT=? where ID=?");
            
            //get the number of other_exp records
            List allTravel = bc.getAllTravelRecords();
            int numTrExp = 0;
            if(allTravel !=null)
              numTrExp = allTravel.size();
          
            //loop on the number of other_expense records and get all info
            for(int i=0;i<numTrExp; i++)
            {
              TravelBean tb = (TravelBean) allTravel.get(i);
              
              String amtappr =tb.getAmountapprovedStr();
              int amtappr_int = 0;
              if(amtappr!= null && !amtappr.equals(""))
                amtappr_int = dbh.parseCurrencyAmtNoDecimal(amtappr);
                
              String exp =tb.getExpapprovedStr();
              int exp_int = 0;
              if(exp!= null && !exp.equals(""))
                exp_int = dbh.parseCurrencyAmtNoDecimal(exp);
                     
              String amend = tb.getAmtamendedStr();
              int amend_int = 0;
              if(amend!= null && !amend.equals(""))
                amend_int = dbh.parseCurrencyAmtNoDecimal(amend);
                  
              long id =tb.getId();
                           
              //set params and update
              ps.setInt(1, amtappr_int);
              ps.setInt(2, exp_int);
              ps.setString(3, user_id);
              ps.setInt(4, amend_int);
              ps.setLong(5, id);
              
              outcome = ps.executeUpdate();           
            }
            break;
        }
        
    }catch(Exception e){
      System.err.println("error updateAdminBudgetAmendment() "+ e.getMessage().toString());
      throw new Exception(e.toString(), e);
    }
    finally{
      Close(conn);
      Close(rs);
      Close(ps);
    }      
      return outcome;
    }
    
    
    /**
     * new method 3/14/11 to allow admin update all apcnt budget fields (first added per FC; now used for cn/al/fl also)
     * modified 8/4/14 with new fields encumbrance/journal/other FS10F fields
     * @param bc
     * @param userb
     * @param tab
     * @return
     * @throws Exception
     */
    public int updateAdminApcntBudget(BudgetCollectionBean bc, UserBean userb, int tab) throws Exception
    {      
      int outcome=1;
      String user_id = userb.getUserid();    
      
      try{      
        conn = initializeConn();
        
        switch(tab){      
            case 1:
            //PERSONAL SERVICE EXPENSES
            ps = conn.prepareStatement("update PERSONAL_SERVICES set NAME = ?, TITLE = ?, "+
            " SALARY_RATE = ?, FTE = ?, GRANT_REQUEST = ?, DATE_MODIFIED = SYSDATE, "+
            " MODIFIED_BY = ?, PROJ_TOTAL = ?, INST_CONT = ?, SMET_CODE=?,  "+
            " AMOUNT_APPROVED = ?, EXP_APPROVED =?, AMEND_AMOUNT=?, EXP_SUBMITTED=?, "+
            " begin_date=to_date(?, 'mm/dd/yyyy'), end_date=to_date(?, 'mm/dd/yyyy') where ID = ?");
                        
            //get the number of personal service records
            List allPersonal = bc.getAllPersRecords();      
            int numPerExp =0;
            if(allPersonal!=null)
              numPerExp= allPersonal.size();
                  
            //loop on the number of personal service records and get all info
            for(int i=0;i<numPerExp; i++)
            {
               PersonalBean pb = (PersonalBean) allPersonal.get(i);
               
                String salary = pb.getSalaryrate();
                float salary_flt=0;
                if(salary!=null && !salary.equals(""))
                  salary_flt = parseDollarSign(salary);
                
                String fte = pb.getFteStr();
                float fte_float=0;
                if(fte!= null && !fte.equals(""))
                  fte_float = Float.parseFloat(fte);
                              
                String req = pb.getGrantrequestStr();
                int req_int=0;
                if(req!= null && !req.equals(""))
                  req_int = dbh.parseCurrencyAmtNoDecimal(req);//this will get rid of any commas, decimals or $ in the value
                                                          
                String incont = pb.getInstcontStr();
                int icont_int=0;
                if(incont!=null && !incont.equals(""))
                  icont_int = dbh.parseCurrencyAmtNoDecimal(incont);
                                          
                int projtot= icont_int + req_int;
               
              String amtappr = pb.getAmountapprovedStr();
              int amtappr_int = 0;
              if(amtappr!= null && !amtappr.equals("")){
                amtappr_int = dbh.parseCurrencyAmtNoDecimal(amtappr);//this will get rid of any commas, decimals or $ in the value
              }
                
              String exp = pb.getExpapprovedStr();
              int exp_int = 0;
              if(exp!= null && !exp.equals(""))
                exp_int = dbh.parseCurrencyAmtNoDecimal(exp);
                
              String amend = pb.getAmtamendedStr();
              int amend_int = 0;
              if(amend!= null && !amend.equals(""))
                amend_int = dbh.parseCurrencyAmtNoDecimal(amend);
                
                String expsub = pb.getExpsubmittedStr();
                int expsub_int = 0;
                if(expsub!= null && !expsub.equals(""))
                  expsub_int = dbh.parseCurrencyAmtNoDecimal(expsub);
                                                      
              //set params and update
               ps.setString(1, pb.getName());
               ps.setString(2, pb.getTitle());
               ps.setFloat(3, salary_flt);
               ps.setFloat(4, fte_float);
               ps.setInt(5, req_int);
               ps.setString(6, user_id);
               ps.setInt(7, projtot);
               ps.setInt(8, icont_int);
               ps.setInt(9, pb.getTypeCode());                              
               ps.setInt(10, amtappr_int);
               ps.setInt(11, exp_int);
               ps.setInt(12, amend_int);
               ps.setInt(13, expsub_int);
               ps.setString(14, pb.getBeginDateStr());
               ps.setString(15, pb.getEndDateStr());
               ps.setLong(16, pb.getId());            
             
              outcome = ps.executeUpdate();
            }
          break;
            
            case 2:
            //BENEFITS EXPENSES
            ps = conn.prepareStatement("update EMPLOYEE_BENEFITS set NAME = ?," +
                 " GRANT_REQUEST = ?, BENEFIT_PERCENT=?, DATE_MODIFIED = SYSDATE, MODIFIED_BY = ?, " +
                 " PROJ_TOTAL=?, INST_CONT=?, AMOUNT_APPROVED = ?, EXP_APPROVED = ?, "+
                 " AMEND_AMOUNT=?, EXP_SUBMITTED=? where ID = ?");
                   
            //get the number of benfit exp records
            List allBenefits = bc.getAllBenRecords();
            int numBenExp = 0;
            if(allBenefits!=null)
              numBenExp= allBenefits.size();
        
            //loop on the number of benefits expense records and get all info
            for(int i=0;i<numBenExp; i++)
            {
              BenefitsBean bb = (BenefitsBean) allBenefits.get(i);
              
                String req = bb.getGrantrequestStr();
                int req_int = 0;
                if(req!= null && !req.equals(""))
                  req_int = dbh.parseCurrencyAmtNoDecimal(req);
                                
                String perc = bb.getBenpercentStr();
                float perc_float = 0;
                if(perc!= null && !perc.equals(""))
                  perc_float = Float.parseFloat(perc);
                                                                          
                String icont = bb.getInstcontStr();
                int icont_int = 0;
                if(icont!= null && !icont.equals(""))
                  icont_int = dbh.parseCurrencyAmtNoDecimal(icont);
                  
                int projtot = icont_int + req_int;               
                                  
              String amtappr =bb.getAmountapprovedStr();
              int amtappr_int = 0;
              if(amtappr!= null && !amtappr.equals(""))
                amtappr_int = dbh.parseCurrencyAmtNoDecimal(amtappr);
                
              String exp = bb.getExpapprovedStr();
              int exp_int = 0;
              if(exp!= null && !exp.equals(""))
               exp_int = dbh.parseCurrencyAmtNoDecimal(exp);
                     
              String amend = bb.getAmtamendedStr();
              int amend_int = 0;
              if(amend!= null && !amend.equals(""))
                amend_int = dbh.parseCurrencyAmtNoDecimal(amend);
                
                String expsub = bb.getExpsubmittedStr();
                int expsub_int = 0;
                if(expsub!= null && !expsub.equals(""))
                  expsub_int = dbh.parseCurrencyAmtNoDecimal(expsub);
                  
                //set params and update
                ps.setString(1, bb.getName());
                ps.setInt(2, req_int);
                ps.setFloat(3, perc_float);
                ps.setString(4, user_id);
                ps.setInt(5, projtot);
                ps.setInt(6, icont_int);
                ps.setInt(7, amtappr_int);
                ps.setInt(8, exp_int);
                ps.setInt(9, amend_int);
                ps.setInt(10, expsub_int);
                ps.setLong(11, bb.getId());
              
              outcome = ps.executeUpdate();
            }
            break;
            
            
            case 3:
            //CONTRACTED EXPENSES
             ps = conn.prepareStatement("update CONTRACTED_SERVICES set SERVICE_TYPE = ?, RECIPIENT = ?, " +
             " GRANT_REQUEST = ?, SERVICE_DESCR= ?, DATE_MODIFIED = SYSDATE, MODIFIED_BY = ?, " +
             " PROJ_TOTAL=?, INST_CONT=?, AMOUNT_APPROVED = ?, EXP_APPROVED=?, "+
             " AMEND_AMOUNT=?, EXP_SUBMITTED=?, encumbrance_date=to_date(?, 'mm/dd/yyyy'), journal_entry=? where ID = ?");
                                 
            //get the number of contracted records
            List allContract = bc.getAllContractRecords();
            int numConExp = 0;
            if(allContract!=null)
              numConExp =allContract.size();
              
            //loop on the number of contracted expense records and get all info
            for(int i=0;i<numConExp; i++)
            {
              ContractedBean cb = (ContractedBean) allContract.get(i);
          
                String req = cb.getGrantrequestStr();
                int req_int = 0;
                if(req!= null && !req.equals(""))
                  req_int = dbh.parseCurrencyAmtNoDecimal(req);
                                              
                String icont = cb.getInstcontStr();
                int icont_int = 0;
                if(icont!= null && !icont.equals(""))
                  icont_int = dbh.parseCurrencyAmtNoDecimal(icont);
                  
                int projtot = icont_int + req_int; //projtot = institcontrib + amtreq     
                                               
              String amtappr =cb.getAmountapprovedStr();
              int amtappr_int = 0;
              if(amtappr!= null && !amtappr.equals(""))
                amtappr_int = dbh.parseCurrencyAmtNoDecimal(amtappr);
                
              String exp = cb.getExpapprovedStr();
              int exp_int = 0;
              if(exp!= null && !exp.equals(""))
                exp_int = dbh.parseCurrencyAmtNoDecimal(exp);
                
              String amend = cb.getAmtamendedStr();
              int amend_int = 0;
              if(amend!= null && !amend.equals(""))
                amend_int = dbh.parseCurrencyAmtNoDecimal(amend);
         
                String expsub = cb.getExpsubmittedStr();
                int expsub_int = 0;
                if(expsub!= null && !expsub.equals(""))
                  expsub_int = dbh.parseCurrencyAmtNoDecimal(expsub);
                  
              //set params and update
              ps.setString(1, cb.getServicetype());
              ps.setString(2, cb.getRecipient());
              ps.setInt(3, req_int);
              ps.setString(4, cb.getServicedescr());
              ps.setString(5, user_id);
              ps.setInt(6, projtot);
              ps.setInt(7, icont_int);
              ps.setInt(8, amtappr_int);
              ps.setInt(9, exp_int);
              ps.setInt(10, amend_int);
              ps.setInt(11, expsub_int);
              ps.setString(12, cb.getEncumbranceDateStr());
              ps.setString(13, cb.getJournalEntry());
              ps.setLong(14, cb.getId());
              
              outcome = ps.executeUpdate();
            }
            break;
            
            
            case 4:
            //SUPPLY MATERIAL EQUIP EXPENSES 
             ps = conn.prepareStatement("update SUPP_MAT_EQUIPS set QUANTITY = ?, DESCRIPTION = ?, " +
             " UNIT_PRICE = ?, VENDOR = ?, GRANT_REQUEST = ?, SMET_CODE= ?, " +
             " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ?, PROJ_TOTAL=?, INST_CONT=?, "+
             " AMOUNT_APPROVED = ?, EXP_APROVED=?, AMEND_AMOUNT=?, EXP_SUBMITTED=?, "+
             " encumbrance_date=to_date(?, 'mm/dd/yyyy'), journal_entry=? where ID = ?");
                       
            //get the number of supply records
            List allSupply= bc.getAllSupplyRecords();
            int numSmeExp = 0;
            if(allSupply!=null)
              numSmeExp =allSupply.size();
          
            //loop on the number of supply, material expense records and get all info
            for(int i=0;i<numSmeExp; i++)
            {
              SupplyBean sb = (SupplyBean) allSupply.get(i);
                           
                String quan = sb.getQuantity();
                int quan_int = 0;
                if(quan!= null && !quan.equals(""))
                  quan_int = Integer.parseInt(quan);
                  
                String price = sb.getUnitpriceStr();
                float price_float = 0;
                if(price!= null && !price.equals(""))
                  price_float = parseDollarSign(price);
                          
                String req = sb.getGrantrequestStr();
                int req_int = 0;
                if(req!= null && !req.equals(""))
                  req_int = dbh.parseCurrencyAmtNoDecimal(req);
                                                
                String code = sb.getSuppmatCode();
                int code_int = 0;
                if(code!= null && !code.equals(""))
                  code_int = Integer.parseInt(code);
                
                String icont = sb.getInstcontStr();
                int icont_int = 0;
                if(icont!= null && !icont.equals(""))
                  icont_int = dbh.parseCurrencyAmtNoDecimal(icont);
                  
                int projtot = icont_int + req_int;   
                                                
              String amtappr = sb.getAmountapprovedStr();
              int amtappr_int = 0;
              if(amtappr!= null && !amtappr.equals(""))
                amtappr_int = dbh.parseCurrencyAmtNoDecimal(amtappr);
                
              String exp =sb.getExpapprovedStr();
              int exp_int = 0;
              if(exp!= null && !exp.equals(""))
                exp_int = dbh.parseCurrencyAmtNoDecimal(exp);
                     
              String amend = sb.getAmtamendedStr();
              int amend_int = 0;
              if(amend!= null && !amend.equals(""))
                amend_int = dbh.parseCurrencyAmtNoDecimal(amend);
                
                String expsub = sb.getExpsubmittedStr();
                int expsub_int = 0;
                if(expsub!= null && !expsub.equals(""))
                  expsub_int = dbh.parseCurrencyAmtNoDecimal(expsub);
                  
              //set params and update
              ps.setInt(1, quan_int);
              ps.setString(2, sb.getDescription());
              ps.setFloat(3, price_float);
              ps.setString(4, sb.getVendor());
              ps.setInt(5, req_int);
              ps.setInt(6, code_int);               
              ps.setString(7, user_id);
              ps.setInt(8, projtot);
              ps.setInt(9, icont_int);               
              ps.setInt(10, amtappr_int);
              ps.setInt(11, exp_int);
              ps.setInt(12, amend_int);
              ps.setInt(13, expsub_int);
              ps.setString(14, sb.getEncumbranceDateStr());
              ps.setString(15, sb.getJournalEntry());
              ps.setLong(16, sb.getId());
              
              outcome = ps.executeUpdate();
            }
            break;
            
            
          case 5:
            //OTHER EXPENSES
             ps = conn.prepareStatement("update OTHER_EXPENSES set DESCRIPTION = ?, GRANT_REQUEST = ?,  " + 
             " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ?, PROJ_TOTAL=?, INST_CONT=?, "+
             " AMOUNT_APPROVED=?, EXP_APPROVED = ?, AMEND_AMOUNT=?, EXP_SUBMITTED=?, "+
             " encumbrance_date=to_date(?, 'mm/dd/yyyy'), journal_entry=?, COST_SUMMARY=? where ID = ?");
                       
            //get the number of other_exp records
            List allOther = bc.getAllExpRecords();
            int numOtExp = 0;
            if(allOther!=null)
              numOtExp= allOther.size();

            //loop on the number of other_expense records and get all info
            for(int i=0;i<numOtExp; i++)
            {
              OtherExpBean ob = (OtherExpBean) allOther.get(i);
              
                String req = ob.getGrantrequestStr();
                int req_int = 0;
                if(req!= null && !req.equals(""))
                  req_int = dbh.parseCurrencyAmtNoDecimal(req);
                                           
                String icont = ob.getInstcontStr();
                int icont_int = 0;
                if(icont!= null && !icont.equals(""))
                  icont_int = dbh.parseCurrencyAmtNoDecimal(icont);
                    
                int projtot = icont_int + req_int;    
                            
              String amtappr = ob.getAmountapprovedStr();
              int amtappr_int = 0;
              if(amtappr!= null && !amtappr.equals(""))
                amtappr_int = dbh.parseCurrencyAmtNoDecimal(amtappr);
                
              String exp =ob.getExpapprovedStr();
              int exp_int = 0;
              if(exp!= null && !exp.equals(""))
                exp_int = dbh.parseCurrencyAmtNoDecimal(exp);
                     
              String amend = ob.getAmtamendedStr();
              int amend_int = 0;
              if(amend!= null && !amend.equals(""))
                amend_int = dbh.parseCurrencyAmtNoDecimal(amend);
                
                String expsub = ob.getExpsubmittedStr();
                int expsub_int = 0;
                if(expsub!= null && !expsub.equals(""))
                  expsub_int = dbh.parseCurrencyAmtNoDecimal(expsub);
                
              //set params and update
              ps.setString(1, ob.getDescription());
              ps.setInt(2, req_int);
              ps.setString(3, user_id);
              ps.setInt(4, projtot);
              ps.setInt(5, icont_int);               
              ps.setInt(6, amtappr_int);
              ps.setInt(7, exp_int);
              ps.setInt(8, amend_int);
              ps.setInt(9, expsub_int);
              ps.setString(10, ob.getEncumbranceDateStr());
              ps.setString(11, ob.getJournalEntry());
              ps.setString(12, ob.getCostsummary());
              ps.setLong(13, ob.getId());
              
              outcome = ps.executeUpdate();           
            }
            break;
            
            
          case 6:
            //TRAVEL EXPENSES
             ps = conn.prepareStatement("update TRAVEL_EXPENSES set DESCRIPTION = ?, PURPOSE=?, "+
             " COSTSUMMARY=?, GRANT_REQUEST = ?, DATE_MODIFIED = SYSDATE, MODIFIED_BY = ?, " +
             " PROJ_TOTAL=?, INST_CONT=?, AMOUNT_APPROVED=?, EXP_APPROVED=?, AMEND_AMOUNT=?, EXP_SUBMITTED=?, "+
             " travel_period=?, journal_entry=?, traveler_name=? where ID=?");
                   
            //get the number of other_exp records
            List allTravel = bc.getAllTravelRecords();
            int numTrExp = 0;
            if(allTravel !=null)
              numTrExp = allTravel.size();
          
            //loop on the number of other_expense records and get all info
            for(int i=0;i<numTrExp; i++)
            {
              TravelBean tb = (TravelBean) allTravel.get(i);
              
                String req = tb.getGrantrequestStr();
                int req_int = 0;
                if(req!= null && !req.equals(""))
                  req_int = dbh.parseCurrencyAmtNoDecimal(req);
                                                           
                String icont = tb.getInstcontStr();
                int icont_int = 0;
                if(icont!= null && !icont.equals(""))
                  icont_int = dbh.parseCurrencyAmtNoDecimal(icont);
                    
                int projtot = icont_int + req_int;             
                
              String amtappr =tb.getAmountapprovedStr();
              int amtappr_int = 0;
              if(amtappr!= null && !amtappr.equals(""))
                amtappr_int = dbh.parseCurrencyAmtNoDecimal(amtappr);
                
              String exp =tb.getExpapprovedStr();
              int exp_int = 0;
              if(exp!= null && !exp.equals(""))
                exp_int = dbh.parseCurrencyAmtNoDecimal(exp);
                     
              String amend = tb.getAmtamendedStr();
              int amend_int = 0;
              if(amend!= null && !amend.equals(""))
                amend_int = dbh.parseCurrencyAmtNoDecimal(amend);
                
                String expsub = tb.getExpsubmittedStr();
                int expsub_int = 0;
                if(expsub!= null && !expsub.equals(""))
                  expsub_int = dbh.parseCurrencyAmtNoDecimal(expsub);
                                 
              //set params and update
              ps.setString(1, tb.getDescription());
              ps.setString(2, tb.getPurpose());
              ps.setString(3, tb.getCostsummary());
              ps.setInt(4, req_int);
              ps.setString(5, user_id);
              ps.setInt(6, projtot);
              ps.setInt(7, icont_int);               
              ps.setInt(8, amtappr_int);
              ps.setInt(9, exp_int);
              ps.setInt(10, amend_int);
              ps.setInt(11, expsub_int);
              ps.setString(12, tb.getTravelPeriod());
              ps.setString(13, tb.getJournalEntry());
              ps.setString(14, tb.getTravelerName());
              ps.setLong(15, tb.getId());
              
              outcome = ps.executeUpdate();           
            }
            break;
        }
        
    }catch(Exception e){
      System.err.println("error updateAdminApcntBudget() "+ e.getMessage().toString());
      throw new Exception(e.toString(), e);
    }
    finally{
      Close(conn);
      Close(rs);
      Close(ps);
    }      
      return outcome;
    }

  public float parseDollarSign(String amount)
  {
    float ans =0;
    try{
        char[] amtString = amount.toCharArray();//convert string to array of char
        Vector newAmtString = new Vector();//vector to hold new amount - just integers, no chars
         
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
    }catch(Exception e){System.out.println("error parseDollarSign "+e.getMessage().toString());}
    return ans;
  }

  /**
     * Used to create blank budget record for fl/al/co-based on fy chosen (and category for lit).
     * @param userb
     * @param grantid
     * @param tab
     * @param fy
     * @param typecode
     * @return
     */
  public int addBudgetFy(UserBean userb, long grantid, int tab, int fy, int typecode)
  {  
    long personalId=0;
    int fycode = 0;
    int outcome = 0;
    
    try{            
      conn = initializeConn();
      ps = conn.prepareStatement("select FY_CODE from GRANTS where ID=?");
      ps.setLong(1, grantid);
      rs = ps.executeQuery();      
      while(rs.next()){
        fycode = rs.getInt("fy_code");
      }
      fycode += fy;//offset the fiscal year depending on budget exp year user choose
      ps.clearParameters();
      
      switch(tab)
      {
        case 1://add Personal_services -defaults to SMET_CODE 3 when first created
            ps = conn.prepareStatement("insert into PERSONAL_SERVICES(ID, GRA_ID, DATE_CREATED, CREATED_BY, "+
            " NAME, GRANT_REQUEST, FY_CODE, SMET_CODE) values (PROJ_BUDG_SEQ.nextval, ?, SYSDATE, ?, ?, 0,?,?)");
            ps.setLong(1, grantid);            
            ps.setString(2, userb.getUserid());
            ps.setString(3, " ");
            ps.setInt(4, fycode);
            if(typecode==0)
                ps.setInt(5,3);
            else
                ps.setInt(5, typecode);
            outcome = ps.executeUpdate();
            
            if(outcome>0){
              //get the last id that was added
              ps.clearParameters();
              ps = conn.prepareStatement("select proj_budg_seq.currval from dual");
              rs = ps.executeQuery();
              while(rs.next())
                personalId = rs.getLong(1);              
            }
            break;
        case 2://add Emp_benefits
            ps = conn.prepareStatement("insert into EMPLOYEE_BENEFITS(ID, GRA_ID, DATE_CREATED, CREATED_BY, "+
            " NAME, GRANT_REQUEST, FY_CODE) values (PROJ_BUDG_SEQ.nextval,?,SYSDATE,?,?,0, ?)   ");
            ps.setLong(1, grantid);            
            ps.setString(2, userb.getUserid());
            ps.setString(3, " ");
            ps.setInt(4, fycode);
            outcome = ps.executeUpdate();
            break;
        case 3://add contracted_services -default to smet_code 5 when created
            ps = conn.prepareStatement("insert into CONTRACTED_SERVICES(ID, GRA_ID, DATE_CREATED, "+
            " CREATED_BY, SERVICE_TYPE, GRANT_REQUEST, FY_CODE, CODE)" +
            " values(PROJ_BUDG_SEQ.nextval,?,SYSDATE,?,?,0,?,?)");
            ps.setLong(1, grantid);            
            ps.setString(2, userb.getUserid());
            ps.setString(3, " ");
            ps.setInt(4, fycode);
            if(typecode==0)
                ps.setInt(5, 5);
            else
                ps.setInt(5, typecode);
            outcome = ps.executeUpdate();
            break;
        case 4://add supply_material - default to smet_code 1 when created
            ps = conn.prepareStatement("insert into SUPP_MAT_EQUIPS(ID, GRA_ID, DATE_CREATED, CREATED_BY, "+
            " QUANTITY, DESCRIPTION, GRANT_REQUEST, FY_CODE, SMET_CODE)" +
            " values(PROJ_BUDG_SEQ.nextval, ?, SYSDATE, ?,0,?,0,?,?)");
            ps.setLong(1, grantid);            
            ps.setString(2, userb.getUserid());
            ps.setString(3, " ");
            ps.setInt(4, fycode);
            if(typecode==0)
                ps.setInt(5, 1);
            else
                ps.setInt(5, typecode);
            outcome = ps.executeUpdate();
            break;
        case 5://add other_expense
            ps = conn.prepareStatement("insert into OTHER_EXPENSES(ID, GRA_ID, DATE_CREATED, CREATED_BY, "+
            " DESCRIPTION, GRANT_REQUEST, FY_CODE, COST_SUMMARY) values(PROJ_BUDG_SEQ.nextval, ?, SYSDATE, ?, ?, 0,?,? )");
            ps.setLong(1, grantid);            
            ps.setString(2, userb.getUserid());
            ps.setString(3, " ");
            ps.setInt(4, fycode);
            ps.setString(4, " ");
            outcome = ps.executeUpdate();
            break;         
        case 6://add travel_expense
            ps = conn.prepareStatement("insert into TRAVEL_EXPENSES(ID, GRA_ID, DATE_CREATED, CREATED_BY, "+
            " GRANT_REQUEST, FY_CODE) values(PROJ_BUDG_SEQ.nextval, ?, SYSDATE, ?, 0,? )");
            ps.setLong(1, grantid);            
            ps.setString(2, userb.getUserid());
            ps.setInt(3, fycode);
            outcome = ps.executeUpdate();
            break;         
      }
      
      if(tab==1){
        //add a benefits record to correspond to this personal record
        ps.clearParameters();
        ps = conn.prepareStatement("insert into EMPLOYEE_BENEFITS(ID, GRA_ID, DATE_CREATED, CREATED_BY, "+
        " NAME, GRANT_REQUEST, FY_CODE, PERS_ID) values (PROJ_BUDG_SEQ.nextval,?,SYSDATE,?,?,0, ?,?)   ");
        ps.setLong(1, grantid);            
        ps.setString(2, userb.getUserid());
        ps.setString(3, " ");
        ps.setInt(4, fycode);
        ps.setLong(5, personalId);
        outcome = ps.executeUpdate();
      }
            
    }catch (Exception ex){
        System.err.println("error addBudgetFy()  " + ex.toString());
    }    
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }
    return outcome;
  }



    public int addAdminBudgetItem(UserBean userb, long grantid, int tab, int fycode, int smetcode )
    {  
      int outcome = 0;
      long awardId=0;      
      try{            
        conn = initializeConn();
        //check to see if admin budget record already exists
        String sqlstr="";
        switch(tab){
            case 1: sqlstr = "select id from personal_services where gra_id=? "+
                            " and is_total=1 and fy_code=? and smet_code=?";            
                    break;
            case 2: sqlstr = "select id from employee_benefits where gra_id=? and fy_code=? and is_total=1";
                    break;
            case 3: sqlstr = "select id from contracted_services where gra_id=? and fy_code=? and is_total=1";
                    break;
            case 4: sqlstr = "select id from supp_mat_equips where gra_id=? and fy_code=? and is_total=1 and smet_code=?";
                    break;
            case 6: sqlstr = "select id from travel_expenses where gra_id=? and fy_code=? and is_total=1";
                    break;                    
        }
        ps = conn.prepareStatement(sqlstr);
        ps.setLong(1, grantid);
        ps.setInt(2, fycode);
        if(tab==1 || tab==4)
            ps.setInt(3, smetcode);
        rs = ps.executeQuery();
        while(rs.next()){
            awardId=rs.getLong("id");
        }
          
        //if admin record does not exist; create one
        if(awardId==0){               
            switch(tab)
            {          
              case 1:
                  //add Personal_services
                  ps = conn.prepareStatement("insert into PERSONAL_SERVICES(ID, GRA_ID, DATE_CREATED, CREATED_BY, "+
                  " NAME, GRANT_REQUEST, FY_CODE, SMET_CODE, IS_TOTAL) values (PROJ_BUDG_SEQ.nextval, ?, SYSDATE, ?, ?, 0,?,?,1)");
                  ps.setLong(1, grantid);            
                  ps.setString(2, userb.getUserid());
                  ps.setString(3, "Admin Award Record");
                  ps.setInt(4, fycode);
                  ps.setInt(5, smetcode);
                  outcome = ps.executeUpdate();            
                  break;
              case 2://add Emp_benefits
                  ps = conn.prepareStatement("insert into EMPLOYEE_BENEFITS(ID, GRA_ID, DATE_CREATED, CREATED_BY, "+
                  " NAME, GRANT_REQUEST, FY_CODE, IS_TOTAL) values (PROJ_BUDG_SEQ.nextval,?,SYSDATE,?,?,0, ?, 1)   ");
                  ps.setLong(1, grantid);            
                  ps.setString(2, userb.getUserid());
                  ps.setString(3, "Admin Award Record");
                  ps.setInt(4, fycode);
                  outcome = ps.executeUpdate();
                  break;
              case 3://add contracted_services 
                  ps = conn.prepareStatement("insert into CONTRACTED_SERVICES(ID, GRA_ID, DATE_CREATED, "+
                  " CREATED_BY, SERVICE_TYPE, GRANT_REQUEST, FY_CODE, CODE, IS_TOTAL)" +
                  " values(PROJ_BUDG_SEQ.nextval,?,SYSDATE,?,?,0,?, ?, 1)");
                  ps.setLong(1, grantid);            
                  ps.setString(2, userb.getUserid());
                  ps.setString(3, "Admin Award Record");
                  ps.setInt(4, fycode);
                  ps.setInt(5, smetcode);
                  outcome = ps.executeUpdate();
                  break;
              case 4://add supply_material -
                  ps = conn.prepareStatement("insert into SUPP_MAT_EQUIPS(ID, GRA_ID, SMET_CODE, DATE_CREATED, CREATED_BY, "+
                  " QUANTITY, DESCRIPTION, GRANT_REQUEST, FY_CODE, IS_TOTAL)" +
                  " values(PROJ_BUDG_SEQ.nextval, ?, ?, SYSDATE, ?,0,?,0,?,1 )");
                  ps.setLong(1, grantid);    
                  ps.setInt(2, smetcode);
                  ps.setString(3, userb.getUserid());
                  ps.setString(4, "Admin Award Record");
                  ps.setInt(5, fycode);
                  outcome = ps.executeUpdate();
                  break;
              case 5://add other_expense
                  ps = conn.prepareStatement("insert into OTHER_EXPENSES(ID, GRA_ID, DATE_CREATED, CREATED_BY, "+
                  " DESCRIPTION, GRANT_REQUEST, FY_CODE, IS_TOTAL) values(PROJ_BUDG_SEQ.nextval, ?, SYSDATE, ?, ?, 0,?,1 )");
                  ps.setLong(1, grantid);            
                  ps.setString(2, userb.getUserid());
                  ps.setString(3, "Admin Award Record");
                  ps.setInt(4, fycode);
                  outcome = ps.executeUpdate();
                  break;         
              case 6://add travel_expense
                  ps = conn.prepareStatement("insert into TRAVEL_EXPENSES(ID, GRA_ID, DATE_CREATED, CREATED_BY, "+
                  " GRANT_REQUEST, FY_CODE, IS_TOTAL, description) values(PROJ_BUDG_SEQ.nextval, ?, SYSDATE, ?, 0,?,1,? )");
                  ps.setLong(1, grantid);            
                  ps.setString(2, userb.getUserid());
                  ps.setInt(3, fycode);
                  ps.setString(4, "Admin Award Record");
                  outcome = ps.executeUpdate();
                  break;         
            }
        }                             
      }catch (Exception ex){
          System.err.println("error addAdminBudgetItem() " + ex.toString());
      }    
      finally{
        Close(conn);
        Close(ps);
        Close(rs);
      }
      return outcome;
    }   
    
    
  /**
   * Method will get co/fl/al amts approved for all projects and organize by fy.
   * Used to print warnings/totals on admin budget tabs.
   * @return 
   * @param fycode
   */
  public Vector getTotalApprForFyCoLit(int fycode, int fccode)
  {
    Vector totals = new Vector();
    HashMap fyfunds = new HashMap();
    int fyoffset = 1;
                  
    try{
      //coordinated is 3 years; al/fl is 2 years, 3 years starting FY13-14
      if(fccode==7 || fycode>13)
        fyoffset=2;
                
      conn = initializeConn();   
      //get total fund amts for grant fy and 1 or 2 years out
      ps = conn.prepareStatement("select TOTAL_FUND, FY_CODE from APP_DATES where FC_CODE=? and FY_CODE between ? and ?");
      ps.setInt(1, fccode);
      ps.setInt(2, fycode);
      ps.setInt(3, fycode+fyoffset);
      rs = ps.executeQuery();
      while(rs.next()){
        int fy = rs.getInt("FY_CODE");
        int fund = rs.getInt("TOTAL_FUND");
        fyfunds.put(new Integer(fy), new Integer(fund));
      }
      
            
      ps.clearParameters();
      ps = conn.prepareStatement("select fy_code, sum(totappr) as sumappr from LDGRANTS.MULTIFYPROJ_BUDGETTOTALS_VIEW " + 
      " where fc_code=? and fy_code between ? and ? group by fy_code");
      ps.setInt(1, fccode);
      ps.setInt(2, fycode);
      ps.setInt(3, fycode+fyoffset);
      rs = ps.executeQuery();
      while(rs.next()){
      
          TotalsBean tb = new TotalsBean();
          tb.setFycode(rs.getInt("fy_code"));
          tb.setTotAmtAppr(rs.getInt("sumappr"));
          //get total fund for rs fy
          if(fyfunds.containsKey(new Integer(tb.getFycode())))
            tb.setAmtavailable( ((Integer) fyfunds.get(new Integer(tb.getFycode()))).intValue() );
                                  
          if(tb.getTotAmtAppr() > tb.getAmtavailable()){
            tb.setWarning(true);
          }
          else if(tb.getTotAmtAppr() > (tb.getAmtavailable()-25000)){
            tb.setNotice(true);
          }           
          totals.add(tb);
      }
           
    }catch(Exception e){
      System.err.println("error getTotalApprForFyCoLit() "+ e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }      
    return totals;
  }  
  
  /* public Vector getTotalAmtApprLitByFy(int fycode, int fccode)
  {
    Vector totals = new Vector();
    HashMap fyfunds = new HashMap();
              
    try{
      conn = initializeConn();   
      //get total fund amts for grant fy and 1 year out
      ps = conn.prepareStatement("select TOTAL_FUND, FY_CODE from APP_DATES where FC_CODE=? and FY_CODE between ? and ?");
      ps.setInt(1, fccode);
      ps.setInt(2, fycode);
      ps.setInt(3, fycode+1);
      rs = ps.executeQuery();
      while(rs.next())
      {
        int fy = rs.getInt("FY_CODE");
        int fund = rs.getInt("TOTAL_FUND");
        fyfunds.put(new Integer(fy), new Integer(fund));
      }
      
      ps.clearParameters();
      ps = conn.prepareStatement("select sum(asum) as totappr, fy_code from ( "+
        " select sum(amount_approved) as asum, fy_code from personal_services where gra_id in (select id "+
        " from grants where fc_code=?) group by fy_code     union all "+
        " select sum(amount_approved) as asum, fy_code from employee_benefits where gra_id in (select id "+
        " from grants where fc_code=?) group by fy_code     union all "+
        " select sum(amount_approved) as asum, fy_code from contracted_services where gra_id in (select id "+
        " from grants where fc_code=?) group by fy_code     union all "+
        " select sum(amount_approved) as asum, fy_code from supp_mat_equips where gra_id in (select id "+
        " from grants where fc_code=?) group by fy_code      union all "+
        " select sum(amount_approved) as asum, fy_code from other_expenses where gra_id in (select id "+
        " from grants where fc_code=?) group by fy_code     union all "+
        " select sum(amount_approved) as asum, fy_code from travel_expenses where gra_id in (select id "+
        " from grants where fc_code=?) group by fy_code "+
        " ) tmptable group by fy_code");
      ps.setInt(1, fccode);
      ps.setInt(2, fccode);
      ps.setInt(3, fccode);
      ps.setInt(4, fccode);
      ps.setInt(5, fccode);
      ps.setInt(6, fccode);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        int rsfy = rs.getInt("FY_CODE");
        if(rsfy>=fycode && (rsfy < fycode +2))//only get recs for grant fy and 1 year out
        {            
            TotalsBean tb = new TotalsBean();
            tb.setFycode(rs.getInt("fy_code"));
            tb.setTotAmtAppr(rs.getInt("TOTAPPR"));
            //get total fund for rs fy
            if(fyfunds.containsKey(new Integer(tb.getFycode())))
              tb.setAmtavailable( ((Integer) fyfunds.get(new Integer(tb.getFycode()))).intValue() );
                                    
            if(tb.getTotAmtAppr() > tb.getAmtavailable()){
              tb.setWarning(true);
            }                        
            totals.add(tb);
        }
      }           
    }catch(Exception e){
      System.err.println("error getTotalAmtApprLitByFy() "+ e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }      
    return totals;
  }  */
  
    
  /**
   * Method will calcualte the total amt available for co grants for the given fy
   * Used on coor home page to inform applicants of total pot of money available
   * @return 
   */
  public TotalsBean getTotalCoAmtAvailableForFY(int fycode)
  {
    TotalsBean tb = new TotalsBean();
    int totalfund=0;
    
    try{
      conn = initializeConn();      
      ps = conn.prepareStatement("select TOTAL_FUND from APP_DATES where FC_CODE=7 and FY_CODE=?");
      ps.setInt(1, fycode);
      rs = ps.executeQuery();
      while(rs.next())
        totalfund=rs.getInt("total_fund");
        
      Close(rs);
      Close(ps);      
      /*removed 11/17/09 to use view instead
       * ps = conn.prepareStatement("select sum(asum) as totappr from ( "+
       " select sum(amount_approved) as asum from personal_services where fy_code=? and gra_id in (select id "+
       " from grants where fc_code=7 )      union all "+
       " select sum(amount_approved) as asum from employee_benefits where fy_code=? and gra_id in (select id "+
       " from grants where fc_code=7 )    union all "+
       " select sum(amount_approved) as asum from contracted_services where fy_code=? and gra_id in (select id "+
       " from grants where fc_code=7 )      union all "+
       " select sum(amount_approved) as asum from supp_mat_equips where fy_code=? and gra_id in (select id "+
       " from grants where fc_code=7 )       union all "+
       " select sum(amount_approved) as asum from other_expenses where fy_code=? and gra_id in (select id "+
       " from grants where fc_code=7 )        union all "+
       " select sum(amount_approved) as asum from travel_expenses where fy_code=? and gra_id in (select id "+
       " from grants where fc_code=7 )"+
       " ) tmptable ");
       ps.setInt(1, fycode);
       ps.setInt(2, fycode);
       ps.setInt(3, fycode);
       ps.setInt(4, fycode);
       ps.setInt(5, fycode);
       ps.setInt(6, fycode);           
      rs = ps.executeQuery();      
      while(rs.next())
      {        
        tb.setFycode(fycode);
        tb.setTotAmtAppr(rs.getInt("TOTAPPR"));
        tb.setAmtavailable(totalfund);
        int totavail = tb.getAmtavailable()- tb.getTotAmtAppr();
        tb.setRemainingFund(totavail);        
      }    */
      
      ps = conn.prepareStatement("select sum(totappr) as sumappr from LDGRANTS.MULTIFYPROJ_BUDGETTOTALS_VIEW " + 
      " where fc_code=7 and fy_code=?");
      ps.setInt(1, fycode);
      rs = ps.executeQuery();
      while(rs.next()){
          tb.setFycode(fycode);
          tb.setTotAmtAppr(rs.getInt("sumappr"));
          tb.setAmtavailable(totalfund);
          int totavail = tb.getAmtavailable()- tb.getTotAmtAppr();
          tb.setRemainingFund(totavail);        
      }
      
    }catch(Exception e){
      System.err.println("error getTotalCoAmtAvailableForFY() "+ e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }      
    return tb;
  }
  
  
  public TotalsBean getTotCoAmtReqForFy(int fycode)
  {
    TotalsBean tb = new TotalsBean();          
    try{
      conn = initializeConn();       
           
      /*removed 11/17/09 to use view instead
       * ps = conn.prepareStatement("select sum(asum) as totreq from ( "+
      " select sum(grant_request) as asum from personal_services where fy_code=? and gra_id in (select id "+
      " from grants where fc_code=7 )      union all "+
      " select sum(grant_request) as asum from employee_benefits where fy_code=? and gra_id in (select id "+
      " from grants where fc_code=7 )    union all "+
      " select sum(grant_request) as asum from contracted_services where fy_code=? and gra_id in (select id "+
      " from grants where fc_code=7 )      union all "+
      " select sum(grant_request) as asum from supp_mat_equips where fy_code=? and gra_id in (select id "+
      " from grants where fc_code=7 )       union all "+
      " select sum(grant_request) as asum from other_expenses where fy_code=? and gra_id in (select id "+
      " from grants where fc_code=7 )       union all "+
      " select sum(grant_request) as asum from travel_expenses where fy_code=? and gra_id in (select id "+
      " from grants where fc_code=7 )) tmptable ");
       ps.setInt(1, fycode);
       ps.setInt(2, fycode);
       ps.setInt(3, fycode);
       ps.setInt(4, fycode);
       ps.setInt(5, fycode);
       ps.setInt(6, fycode);     */
       
       ps = conn.prepareStatement("select sum(totreq) as sumreq from LDGRANTS.MULTIFYPROJ_BUDGETTOTALS_VIEW " + 
       " where fc_code=7 and fy_code=?");
       ps.setInt(1, fycode);
       rs = ps.executeQuery();      
       while(rs.next()){        
        tb.setFycode(fycode);
        tb.setTotAmtReq(rs.getInt("sumreq"));
       }    
      
    }catch(Exception e){
      System.err.println("error getTotCoAmtReqForFy() "+ e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }      
    return tb;
  }
  
  public Vector getTotAmtReqCoFYPeriod(long grantid, int fycode)
  {
    Vector allsums = new Vector();
    
    try{
      //get total req for 3 years starting with given fy
      /*for(int i=fycode; i<fycode+3; i++)
      {
        int personalExp = getFieldTotalFY("Personal_Services", "grant_request", grantid, i);
        int benefitExp = getFieldTotalFY("Employee_Benefits", "grant_request", grantid, i);
        int contractedExp = getFieldTotalFY("Contracted_services", "grant_request", grantid, i);
        int supplyExp = getFieldTotalFY("Supp_mat_equips", "grant_request", grantid, i);
        int otherExp = getFieldTotalFY("Other_Expenses", "grant_request", grantid, i);
        int travExp = getFieldTotalFY("Travel_Expenses", "grant_request", grantid, i);
        
        int totalAmtReq = personalExp + benefitExp + contractedExp + supplyExp + otherExp + travExp;
        TotalsBean tb = new TotalsBean();
        tb.setFycode(i);
        tb.setTotAmtReq(totalAmtReq);
        allsums.add(tb);
      }*/
      
      conn = initializeConn();
      ps = conn.prepareStatement("select totreq, fy_code from LDGRANTS.MULTIFYPROJ_BUDGETTOTALS_VIEW " + 
      " where gra_id=? and fy_code between ? and ?");
      ps.setLong(1, grantid);
      ps.setInt(2, fycode);
      ps.setInt(3, fycode+2);
      rs = ps.executeQuery();
      while(rs.next()){
          TotalsBean tb = new TotalsBean();
          tb.setFycode(rs.getInt("fy_code"));
          tb.setTotAmtReq(rs.getInt("totreq"));
          allsums.add(tb);
      }
            
    }catch(Exception e){
      System.err.println("error getTotAmtReqCoFYPeriod() "+ e.getMessage().toString());
    }       
    finally{
        Close(rs);
        Close(ps);
        Close(conn);
    }
    return allsums;
  }


 public Vector<TotalsBean> getTotAmtReqLiFYPeriod(long grantid, int fycode, int fccode)
  {
    Vector<TotalsBean> allsums = new Vector();    
    try{
      //get total req for 2 years starting with given fy
      /*for(int i=fycode; i<fycode+2; i++){
        int personalExp = getFieldTotalFY("Personal_Services", "grant_request", grantid, i);
        int benefitExp = getFieldTotalFY("Employee_Benefits", "grant_request", grantid, i);
        int contractedExp = getFieldTotalFY("Contracted_services", "grant_request", grantid, i);
        int supplyExp = getFieldTotalFY("Supp_mat_equips", "grant_request", grantid, i);
        int travExp = getFieldTotalFY("Travel_Expenses", "grant_request", grantid, i);
                
        int totalAmtReq = personalExp + benefitExp + contractedExp + supplyExp + travExp;
        TotalsBean tb = new TotalsBean();
        tb.setFycode(i);
        tb.setTotAmtReq(totalAmtReq);
        if(fccode==40 && (totalAmtReq > tb.getAlLimit()))
          tb.setWarning(true);
        else if(fccode==42 && (totalAmtReq > tb.getFlLimit()))
          tb.setWarning(true);
        else if(totalAmtReq < tb.getLitMinimum())
          tb.setWarning(true);        
        allsums.add(tb);
      }*/
      
        conn = initializeConn();
        ps = conn.prepareStatement("select totreq, fy_code from LDGRANTS.MULTIFYPROJ_BUDGETTOTALS_VIEW " + 
        " where gra_id=? and fy_code between ? and ?");
        ps.setLong(1, grantid);
        ps.setInt(2, fycode);
        ps.setInt(3, fycode+2);
        rs = ps.executeQuery();
        while(rs.next()){
            TotalsBean tb = new TotalsBean();
            tb.setFycode(rs.getInt("fy_code"));
            tb.setTotAmtReq(rs.getInt("totreq"));
            
            if(fccode==40 && (tb.getTotAmtReq() > tb.getAlLimit()))
              tb.setWarning(true);
            else if(fccode==42 && (tb.getTotAmtReq() > tb.getFlLimit()))
              tb.setWarning(true);
            else if(tb.getTotAmtReq() < tb.getLitMinimum())
              tb.setWarning(true);
            
            allsums.add(tb);
        }
      
    }catch(Exception e){
      System.err.println("error getTotAmtReqLiFYPeriod() "+ e.getMessage().toString());
    }       
    finally{
        Close(rs);
        Close(ps);
        Close(conn);
    }
    return allsums;
  }
  
  
  public Vector<AllocationYearBean> getTotAmtReqLitAllocation(long grantid, int fycode, int fccode, long systemInstId)
  {
       Vector<AllocationYearBean> allsums = new Vector();    
       try{
           //get amt available to the pls for lit for all 3 FY's
           conn = initializeConn();
           ps = conn.prepareStatement("select * from system_fiscalyear_details where " + 
           " fy_code between ? and ? and fc_code=? and lsm_id in (select id from "+
           " ldstateaid.library_system_mappings " + 
           " where inst_id_has=? and inst_id_has=inst_id and end_date is null)");
           ps.setInt(1, fycode);
           ps.setInt(2, fycode+2);
           ps.setInt(3, fccode);
           ps.setLong(4, systemInstId);
           rs = ps.executeQuery();
           while(rs.next()){   
               AllocationYearBean ab = new AllocationYearBean();
               ab.setInitialAlloc(rs.getLong("initial_allocation"));    
               ab.setFinalRecommend(rs.getLong("final_recommendation"));
               ab.setFycode(rs.getInt("fy_code"));
               allsums.add(ab);
           }
           
           
           ps = conn.prepareStatement("select totreq, fy_code from LDGRANTS.MULTIFYPROJ_BUDGETTOTALS_VIEW " + 
           " where gra_id=? and fy_code between ? and ?");
           ps.setLong(1, grantid);
           ps.setInt(2, fycode);
           ps.setInt(3, fycode+2);
           rs = ps.executeQuery();
           while(rs.next()){
           
               for(int i=0; i<allsums.size(); i++){
                   AllocationYearBean ab = allsums.get(i);
                   
                   int fy = rs.getInt("fy_code");
                   if(ab.getFycode()==fy){
                       ab.setAmountRequested(rs.getInt("totreq"));
                       
                       //difference b/w iniatial allocation and final appropriation
                       //using allocation here b/c at start of grant cycle;  appropriation amounts are Not known yet.
                       if(ab.getAmountRequested() > ab.getInitialAlloc())
                            ab.setWarning(true);
                   }
               }             
           }
         
       }catch(Exception e){
         System.err.println("error getTotAmtReqLitAllocation() "+ e.getMessage().toString());
       }       
       finally{
           Close(rs);
           Close(ps);
           Close(conn);
       }
       return allsums;
  }
  
  
  
    public Vector<AllocationYearBean> getTotApproveLitAllocation(long grantid, int fycode, int fccode, long systemInstId)
    {
         Vector<AllocationYearBean> allsums = new Vector();    
         try{
             //get amt available to the pls for lit for all 3 FY's
             conn = initializeConn();
             ps = conn.prepareStatement("select * from system_fiscalyear_details where " + 
             " fy_code between ? and ? and fc_code=? and lsm_id in (select id from "+
             " ldstateaid.library_system_mappings " + 
             " where inst_id_has=? and inst_id_has=inst_id and end_date is null)");
             ps.setInt(1, fycode);
             ps.setInt(2, fycode+2);
             ps.setInt(3, fccode);
             ps.setLong(4, systemInstId);
             rs = ps.executeQuery();
             while(rs.next()){   
                 AllocationYearBean ab = new AllocationYearBean();
                 ab.setInitialAlloc(rs.getLong("initial_allocation"));    
                 ab.setFinalRecommend(rs.getLong("final_recommendation"));
                 ab.setFycode(rs.getInt("fy_code"));
                 allsums.add(ab);
             }
             
             
             ps = conn.prepareStatement("select totappr, fy_code from LDGRANTS.MULTIFYPROJ_BUDGETTOTALS_VIEW " + 
             " where gra_id=? and fy_code between ? and ?");
             ps.setLong(1, grantid);
             ps.setInt(2, fycode);
             ps.setInt(3, fycode+2);
             rs = ps.executeQuery();
             while(rs.next()){
             
                 for(int i=0; i<allsums.size(); i++){
                     AllocationYearBean ab = allsums.get(i);
                     
                     int fy = rs.getInt("fy_code");
                     if(ab.getFycode()==fy){
                         ab.setAmtRecommend(rs.getLong("totappr"));
                         
                         //if amt_approved > appropriation; show warning (note diff b/w allocation and appropriation
                         if(ab.getAmtRecommend() > ab.getFinalRecommend())
                              ab.setWarning(true);
                     }
                 }             
             }
           
         }catch(Exception e){
           System.err.println("error getTotApproveLitAllocation() "+ e.getMessage().toString());
         }       
         finally{
             Close(rs);
             Close(ps);
             Close(conn);
         }
         return allsums;
    }
  
  
    public Vector<AllocationYearBean> getYearlyAllocationForInst(int fycode, int fccode, long systemInstId)
    {
         Vector<AllocationYearBean> allsums = new Vector();    
         try{
             //get amt available to the pls for lit for all 3 FY's
             conn = initializeConn();
             ps = conn.prepareStatement("select * from system_fiscalyear_details where " + 
             " fy_code between ? and ? and fc_code=? and lsm_id in (select id from "+
             " ldstateaid.library_system_mappings " + 
             " where inst_id_has=? and inst_id_has=inst_id and end_date is null) order by fy_code");
             ps.setInt(1, fycode);
             ps.setInt(2, fycode+2);
             ps.setInt(3, fccode);
             ps.setLong(4, systemInstId);
             rs = ps.executeQuery();
             while(rs.next()){   
                 AllocationYearBean ab = new AllocationYearBean();
                 ab.setInitialAlloc(rs.getLong("initial_allocation"));    
                 ab.setFinalRecommend(rs.getLong("final_recommendation"));
                 ab.setFycode(rs.getInt("fy_code"));
                 allsums.add(ab);
             }
           
         }catch(Exception e){
           System.err.println("error getYearlyAllocationForInst() "+ e.getMessage().toString());
         }       
         finally{
             Close(rs);
             Close(ps);
             Close(conn);
         }
         return allsums;
    }
    
    
    
    
  public AllocationYearBean getAllocationForInstFy(int fycode, int fccode, long systemInstId)
  {
        AllocationYearBean ab = new AllocationYearBean();   
       try{
           //get amt available to the pls for lit for fy
           conn = initializeConn();
           ps = conn.prepareStatement("select * from system_fiscalyear_details where " + 
           " fy_code=? and fc_code=? and lsm_id in (select id from "+
           " ldstateaid.library_system_mappings " + 
           " where inst_id_has=? and inst_id_has=inst_id and end_date is null)");
           ps.setInt(1, fycode);
           ps.setInt(2, fccode);
           ps.setLong(3, systemInstId);
           rs = ps.executeQuery();
           while(rs.next()){                  
               ab.setInitialAlloc(rs.getLong("initial_allocation"));    
               ab.setFinalRecommend(rs.getLong("final_recommendation"));
               ab.setFycode(rs.getInt("fy_code"));               
           }
         
       }catch(Exception e){
         System.err.println("error getAllocationForInstFy() "+ e.getMessage().toString());
       }       
       finally{
           Close(rs);
           Close(ps);
           Close(conn);
       }
       return ab;
  }
    
    
    
  public int getFieldTotalFY(String tablename, String fieldname, long grantid, int fycode)
  {
    int total_tmp = 0;    
    try {
      conn = initializeConn();
      ps = conn.prepareStatement("select sum(" + fieldname + ") from " + tablename +
                                " where GRA_ID = ? and fy_code= ? " );   
      ps.setLong(1, grantid);
      ps.setInt(2, fycode);
      rs = ps.executeQuery();
      while(rs.next()){                
        total_tmp = rs.getInt(1);          
      }
                      
    } catch (Exception ex){
        System.err.println("error getFieldTotalFY()  " + ex.toString());
        return 0;
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }
    return total_tmp;
  }  
  

  /**
   * Method will save all budget records for given budget tab. Used for sa,co,di,li/lg/cn apcnt
   * budgets.  Each record is first validated in the struts action.
   * @return 
   * @param bc
   */
  public int updateCpLiBudget(BudgetCollectionBean bc, UserBean userb, int tab) throws Exception
  {
    int outcome =0;  
    String user_id = userb.getUserid();    
           
    try{                
      conn = initializeConn();            
           
      switch(tab)
      {
        case 1:           
            //PERSONAL SERVICE EXPENSES
            PreparedStatement perExpStmt = conn.prepareStatement("update PERSONAL_SERVICES set NAME = ?, TITLE = ?, "+
            " SALARY_RATE = ?, FTE = ?, GRANT_REQUEST = ?, DATE_MODIFIED = SYSDATE, "+
            " MODIFIED_BY = ?, PROJ_TOTAL = ?, INST_CONT = ?, SMET_CODE=?  where ID = ?");
            
            //get the number of personal service records
            List allPersonal = bc.getAllPersRecords();
            int numPerExp = 0;
            if(allPersonal !=null)
              numPerExp =allPersonal.size();
          
            //loop on the number of personal service records
            for(int i=0;i<numPerExp; i++)
            {
              PersonalBean pb = (PersonalBean) allPersonal.get(i);
            
              String salary = pb.getSalaryrate();
              float salary_flt=0;
              if(salary!=null && !salary.equals(""))
                salary_flt = parseDollarSign(salary);
              
              String fte = pb.getFteStr();
              float fte_float=0;
              if(fte!= null && !fte.equals(""))
                fte_float = Float.parseFloat(fte);
                            
              String req = pb.getGrantrequestStr();
              int req_int=0;
              if(req!= null && !req.equals(""))
                req_int = dbh.parseCurrencyAmtNoDecimal(req);//this will get rid of any commas, decimals or $ in the value
                
              long id = pb.getId();
                                             
              String incont = pb.getInstcontStr();
              int icont_int=0;
              if(incont!=null && !incont.equals(""))
                icont_int = dbh.parseCurrencyAmtNoDecimal(incont);
                
              int code_int = pb.getTypeCode();
                            
             int projtot= icont_int + req_int;                 
 
              perExpStmt.setString(1, pb.getName());
              perExpStmt.setString(2, pb.getTitle());
              perExpStmt.setFloat(3, salary_flt);
              perExpStmt.setFloat(4, fte_float);
              perExpStmt.setInt(5, req_int);
              perExpStmt.setString(6, user_id);
              perExpStmt.setInt(7, projtot);
              perExpStmt.setInt(8, icont_int);
              perExpStmt.setInt(9, code_int);
              perExpStmt.setLong(10, id);        
             
              outcome = perExpStmt.executeUpdate();
                           
          //------------------------------------------------------------
              String benefitID = "";
              //see if associated benefit record exists
              ps = conn.prepareStatement("select id from employee_benefits where pers_id=?");
              ps.setLong(1, id);
              rs = ps.executeQuery();
              while(rs.next())
                benefitID = rs.getString("id");
              
              if(benefitID!=null &&  !benefitID.equals(""))
              {
                  //if benefit record exists, update the information
                  ps.clearParameters();
                  ps = conn.prepareStatement("update EMPLOYEE_BENEFITS set "+
                  " NAME = ?, DATE_MODIFIED = SYSDATE, MODIFIED_BY = ?  where ID = ?");
                  ps.setString(1, pb.getName());
                  ps.setString(2, user_id);
                  ps.setString(3, benefitID);
                  outcome = ps.executeUpdate();
                  ps.clearParameters();
              }
              
            }
            Close(perExpStmt);
            Close(ps);
            break;
            
            
        case 2:
            //BENEFITS EXPENSES
            ps = conn.prepareStatement("update EMPLOYEE_BENEFITS set NAME = ?," +
                " GRANT_REQUEST = ?, BENEFIT_PERCENT=?, DATE_MODIFIED = SYSDATE, MODIFIED_BY = ?, " +
                " PROJ_TOTAL=?, INST_CONT=? where ID = ?");
            
            //get the number of benfit exp records
            List allBenefits = bc.getAllBenRecords();
            int numBenExp = 0;
            if(allBenefits !=null)
              numBenExp =allBenefits.size();
               
            //loop on the number of benefits expense records and get all info
            for(int i=0;i<numBenExp; i++)
            {
              BenefitsBean bb = (BenefitsBean) allBenefits.get(i);
           
              String req = bb.getGrantrequestStr();
              int req_int = 0;
              if(req!= null && !req.equals(""))
                req_int = dbh.parseCurrencyAmtNoDecimal(req);
                              
              String perc = bb.getBenpercentStr();
              float perc_float = 0;
              if(perc!= null && !perc.equals(""))
                perc_float = Float.parseFloat(perc);
                                                                           
              String icont = bb.getInstcontStr();
              int icont_int = 0;
              if(icont!= null && !icont.equals(""))
                icont_int = dbh.parseCurrencyAmtNoDecimal(icont);
                
              int projtot = icont_int + req_int;               
                
              //set params and update
              ps.setString(1, bb.getName());
              ps.setInt(2, req_int);
              ps.setFloat(3, perc_float);
              ps.setString(4, user_id);
              ps.setInt(5, projtot);
              ps.setInt(6, icont_int);
              ps.setLong(7, bb.getId());
              outcome = ps.executeUpdate();
            }     
            break;
            
            
            
        case 3:
             //CONTRACTED EXPENSES
            ps = conn.prepareStatement("update CONTRACTED_SERVICES set SERVICE_TYPE = ?, RECIPIENT = ?, " +
          " GRANT_REQUEST = ?, SERVICE_DESCR= ?, DATE_MODIFIED = SYSDATE, MODIFIED_BY = ?, " +
          " PROJ_TOTAL=?, INST_CONT=? where ID = ?");
            
            //get the number of contracted records           
            List allContracts = bc.getAllContractRecords();
            int numConExp =0;
            if(allContracts !=null)
              numConExp =allContracts.size();
            
            //loop on the number of contracted expense records and get all info
            for(int i=0;i<numConExp; i++)
            {
              ContractedBean cb = (ContractedBean) allContracts.get(i);
           
              String req = cb.getGrantrequestStr();
              int req_int = 0;
              if(req!= null && !req.equals(""))
                req_int = dbh.parseCurrencyAmtNoDecimal(req);
                                          
              String icont = cb.getInstcontStr();
              int icont_int = 0;
              if(icont!= null && !icont.equals(""))
                icont_int = dbh.parseCurrencyAmtNoDecimal(icont);
                
              int projtot = icont_int + req_int; //projtot = institcontrib + amtreq     
                
              //set params and update
              ps.setString(1, cb.getServicetype());
              ps.setString(2, cb.getRecipient());
              ps.setInt(3, req_int);
              ps.setString(4, cb.getServicedescr());
              ps.setString(5, user_id);
              ps.setInt(6, projtot);
              ps.setInt(7, icont_int);
              ps.setLong(8, cb.getId());              
              outcome = ps.executeUpdate();
            }
            break;
            
        case 4:
            //SUPPLY MATERIAL EQUIP EXPENSES 
             ps = conn.prepareStatement("update SUPP_MAT_EQUIPS set QUANTITY = ?, DESCRIPTION = ?, " +
            " UNIT_PRICE = ?, VENDOR = ?, GRANT_REQUEST = ?, SMET_CODE= ?, " + 
            " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ?, PROJ_TOTAL=?, INST_CONT=? where ID = ?");
              
              //get the number of supply records
             List allSupplies = bc.getAllSupplyRecords();
             int numSmeExp = 0;
             if(allSupplies !=null)
              numSmeExp =allSupplies.size();
            
              //loop on the number of supply, material expense records and get all info
              for(int i=0;i<numSmeExp; i++)
              {
                SupplyBean sb = (SupplyBean) allSupplies.get(i);
            
                String quan = sb.getQuantity();
                int quan_int = 0;
                if(quan!= null && !quan.equals(""))
                  quan_int = Integer.parseInt(quan);
                  
                String price = sb.getUnitpriceStr();
                float price_float = 0;
                if(price!= null && !price.equals(""))
                  price_float = parseDollarSign(price);
                          
                String req = sb.getGrantrequestStr();
                int req_int = 0;
                if(req!= null && !req.equals(""))
                  req_int = dbh.parseCurrencyAmtNoDecimal(req);
                               
                String code = sb.getSuppmatCode();
                int code_int = 0;
                if(code!= null && !code.equals(""))
                  code_int = Integer.parseInt(code);
                
                String icont = sb.getInstcontStr();
                int icont_int = 0;
                if(icont!= null && !icont.equals(""))
                  icont_int = dbh.parseCurrencyAmtNoDecimal(icont);
                  
                int projtot = icont_int + req_int;   
                  
                //set params and update
                ps.setInt(1, quan_int);
                ps.setString(2, sb.getDescription());
                ps.setFloat(3, price_float);
                ps.setString(4, sb.getVendor());
                ps.setInt(5, req_int);
                ps.setInt(6, code_int);               
                ps.setString(7, user_id);
                ps.setInt(8, projtot);
                ps.setInt(9, icont_int);
                ps.setLong(10, sb.getId());                
                outcome = ps.executeUpdate();
              }
            break;
            
        case 5:
            //OTHER EXPENSES
            ps = conn.prepareStatement("update OTHER_EXPENSES set DESCRIPTION = ?, GRANT_REQUEST = ?,  " + 
            " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ?, PROJ_TOTAL=?, INST_CONT=?, COST_SUMMARY=? where ID = ?");
            
            //get the number of other_exp records
            List expRecords = bc.getAllExpRecords();
            int numOtExp = 0;
            if(expRecords !=null)
              numOtExp =expRecords.size();
          
            //loop on the number of other_expense records and get all info
            for(int i=0;i<numOtExp; i++)
            {
              OtherExpBean ob = (OtherExpBean)expRecords.get(i);
            
              String req = ob.getGrantrequestStr();
              int req_int = 0;
              if(req!= null && !req.equals(""))
                req_int = dbh.parseCurrencyAmtNoDecimal(req);
                                           
              String icont = ob.getInstcontStr();
              int icont_int = 0;
              if(icont!= null && !icont.equals(""))
                icont_int = dbh.parseCurrencyAmtNoDecimal(icont);
                  
              int projtot = icont_int + req_int;    
                
              //set params and update
              ps.setString(1, ob.getDescription());
              ps.setInt(2, req_int);
              ps.setString(3, user_id);
              ps.setInt(4, projtot);
              ps.setInt(5, icont_int);
              ps.setString(6, ob.getCostsummary());
              ps.setLong(7, ob.getId());              
              outcome = ps.executeUpdate();
            }
            break;      
            
        case 6: //travel expenses
            ps = conn.prepareStatement("update TRAVEL_EXPENSES set DESCRIPTION = ?, PURPOSE=?, "+
            " COSTSUMMARY=?, GRANT_REQUEST = ?,  DATE_MODIFIED = SYSDATE, MODIFIED_BY = ?, " +
            " PROJ_TOTAL=?, INST_CONT=? where ID = ?");
            
            //get the number of records
            List allTravel = bc.getAllTravelRecords();
            int numTrExp = 0;
            if(allTravel !=null)
              numTrExp =allTravel.size();
          
            //loop on the number of travel_expense records and get all info
            for(int i=0;i<numTrExp; i++)
            {
              TravelBean tb = (TravelBean)allTravel.get(i);
            
              String req = tb.getGrantrequestStr();
              int req_int = 0;
              if(req!= null && !req.equals(""))
                req_int = dbh.parseCurrencyAmtNoDecimal(req);
                                                         
              String icont = tb.getInstcontStr();
              int icont_int = 0;
              if(icont!= null && !icont.equals(""))
                icont_int = dbh.parseCurrencyAmtNoDecimal(icont);
                  
              int projtot = icont_int + req_int;   
                
              //set params and update
              ps.setString(1, tb.getDescription());
              ps.setString(2, tb.getPurpose());
              ps.setString(3, tb.getCostsummary());
              ps.setInt(4, req_int);
              ps.setString(5, user_id);
              ps.setInt(6, projtot);
              ps.setInt(7, icont_int);
              ps.setLong(8, tb.getId());              
              outcome = ps.executeUpdate();
            }
            break;      
      }      
   
    }catch(SQLException se){
      System.err.println("error updateCpLiBudget() "+ se.getMessage().toString());
      throw new Exception(se.toString(), se);
              
    } catch(Exception e) {
      System.err.println("error updateCpLiBudget() " + e.getMessage().toString());
      throw new Exception("budget error "+e, e);
    }
    finally{
      Close(conn); 
      Close(ps);
      Close(rs);
    }            
    return outcome;
  }

  public BudgetCollectionBean getSupplyActionInfo(long grantid, BudgetCollectionBean bc, 
                                boolean apprVisible, int typeCode)
  {
     ArrayList results = new ArrayList();
    
    try{
        conn = initializeConn();        
               
        if(typeCode==0)
        {
          //get all supp/mat/equip for this grant id
          ps = conn.prepareStatement("select * from supp_mat_equips where gra_id=? order by fy_code, id");
          ps.setLong(1, grantid);
        }
        else
        {
          //get either supplies or equipment
          ps = conn.prepareStatement("select * from supp_mat_equips where gra_id=? and smet_code=? order by fy_code, id");
          ps.setLong(1, grantid);
          ps.setInt(2, typeCode);
        }
        
        rs = ps.executeQuery();       
        while(rs.next())
        {
          SupplyBean sb = new SupplyBean();
          sb.setId(rs.getLong("id"));
          sb.setQuantity(rs.getString("quantity"));     
          sb.setDescription(rs.getString("description")); 
          sb.setUnitprice(rs.getFloat("unit_price")); 
          sb.setUnitpriceStr(rs.getString("unit_price"));
          sb.setVendor(rs.getString("vendor")); 
          sb.setSuppmatCode(rs.getString("smet_code"));
          sb.setProjtotal(rs.getInt("proj_total"));
          sb.setFycode(rs.getInt("fy_code"));
          sb.setInstcont(rs.getInt("inst_cont"));
          sb.setGrantrequest(rs.getInt("grant_request"));
          sb.setExpsubmitted(rs.getInt("exp_submitted"));
          sb.setCategoryTotal(rs.getBoolean("is_total"));
          sb.setAmtamended(rs.getInt("amend_amount"));
          sb.setJournalEntry(rs.getString("journal_entry"));
          sb.setEncumbranceDate(rs.getDate("encumbrance_date"));//new 8/1/14
          if(rs.getDate("encumbrance_date")!=null){
            sb.setEncumbranceDateStr(formatter.format(rs.getDate("encumbrance_date")));
          }
            
          if(apprVisible)
          {
            sb.setAmountapproved(rs.getInt("amount_approved"));
            sb.setExpapproved(rs.getInt("exp_aproved"));
          }
          
          
          int cost=0;
          if(sb.getQuantity()!=null)
            cost = (int)  ( sb.getUnitprice() * Integer.parseInt(sb.getQuantity())  );
          String costStr = Integer.toString(cost);
          long costLg = Long.parseLong(costStr);
          sb.setCost(numF.format(costLg));
          
          sb.setGrantrequestStr(rs.getString("grant_request"));
          if(sb.getGrantrequestStr()!=null && !sb.getGrantrequestStr().equals(""))
          {
            long amtreq = Long.parseLong(sb.getGrantrequestStr());
            sb.setGrantrequestStr(numF.format(amtreq));
          }
          
          sb.setExpsubmittedStr(rs.getString("exp_submitted"));
          if(sb.getExpsubmittedStr()!=null && !sb.getExpsubmittedStr().equals(""))
          {
            long expsub = Long.parseLong(sb.getExpsubmittedStr());
            sb.setExpsubmittedStr(numF.format(expsub));          
          }
         
          sb.setInstcontStr(rs.getString("inst_cont"));
          if(sb.getInstcontStr()!=null && !sb.getInstcontStr().equals(""))
          {
            long instcont = Long.parseLong(sb.getInstcontStr());
            sb.setInstcontStr(numF.format(instcont));        
          }
          
            sb.setAmtamendedStr(rs.getString("amend_amount"));
            if(sb.getAmtamendedStr()!=null && !sb.getAmtamendedStr().equals(""))
            {
              long amamt = Long.parseLong(sb.getAmtamendedStr());
              sb.setAmtamendedStr(numF.format(amamt));        
            }
          
          results.add(sb);
        }        
        bc.setAllSupplyRecords(results);
        
      }catch(Exception e){
        System.err.println("error getSupplyActionInfo() "+ e.getMessage());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }  
    return bc;
  }

  public BudgetCollectionBean getContractedActionInfo(long grantid, BudgetCollectionBean bc, 
                                        boolean apprVisible, int typeCode)
  {
    ArrayList results = new ArrayList();  
    try {
        conn = initializeConn();
        
        if(typeCode==0)
        {
          //get all purchased info for this grant id
          ps = conn.prepareStatement("select * from contracted_services where gra_id=? order by fy_code, id");
          ps.setLong(1, grantid);  
        }
        else
        {
          //get either purchased or boces info for this grant id*
          ps = conn.prepareStatement("select * from contracted_services where gra_id=? and code=? order by fy_code, id");
          ps.setLong(1, grantid);   
          ps.setInt(2, typeCode);
        }
        
        rs = ps.executeQuery();        
        while(rs.next())
        {
          ContractedBean cb = new ContractedBean();
          cb.setId(rs.getLong("id"));
          cb.setServicetype(rs.getString("service_type"));     
          cb.setRecipient(rs.getString("recipient")); 
          cb.setServicedescr(rs.getString("service_descr"));
          cb.setTypeCode(rs.getInt("code"));//for purchased vs boces
          cb.setProjtotal(rs.getInt("proj_total"));
          cb.setFycode(rs.getInt("fy_code"));
          cb.setInstcont(rs.getInt("inst_cont"));
          cb.setGrantrequest(rs.getInt("grant_request"));
          cb.setExpsubmitted(rs.getInt("exp_submitted"));
          cb.setCategoryTotal(rs.getBoolean("is_total"));
          cb.setAmtamended(rs.getInt("amend_amount"));
          cb.setJournalEntry(rs.getString("journal_entry"));
          cb.setEncumbranceDate(rs.getDate("encumbrance_date"));//new 8/1/14
          cb.setProviderUsed(rs.getString("provider_used"));//new 10/15/15
          if(rs.getDate("encumbrance_date")!=null){
            cb.setEncumbranceDateStr(formatter.format(rs.getDate("encumbrance_date")));
          }
            
          if(apprVisible)
          {
            cb.setAmountapproved(rs.getInt("amount_approved"));
            cb.setExpapproved(rs.getInt("exp_approved"));
          }
          
          cb.setGrantrequestStr(rs.getString("grant_request"));
          if(cb.getGrantrequestStr()!=null && !cb.getGrantrequestStr().equals(""))
          {
            long amtreq = Long.parseLong(cb.getGrantrequestStr());
            cb.setGrantrequestStr(numF.format(amtreq));
          }
          
          cb.setExpsubmittedStr(rs.getString("exp_submitted"));
          if(cb.getExpsubmittedStr()!=null && !cb.getExpsubmittedStr().equals(""))
          {
            long expsub = Long.parseLong(cb.getExpsubmittedStr());
            cb.setExpsubmittedStr(numF.format(expsub));        
          }
         
          cb.setInstcontStr(rs.getString("inst_cont"));
          if(cb.getInstcontStr()!=null && !cb.getInstcontStr().equals(""))
          {
            long instcont = Long.parseLong(cb.getInstcontStr());
            cb.setInstcontStr(numF.format(instcont));       
          }
          
            cb.setAmtamendedStr(rs.getString("amend_amount"));
            if(cb.getAmtamendedStr()!=null && !cb.getAmtamendedStr().equals(""))
            {
              long amamt = Long.parseLong(cb.getAmtamendedStr());
              cb.setAmtamendedStr(numF.format(amamt));       
            }
          results.add(cb);
        }
        
        bc.setAllContractRecords(results);
        
      }catch(Exception e){
        System.err.println("error getContractedActionInfo() "+ e.getMessage());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }  
    return bc;
  }
  
  
  
  public BudgetCollectionBean getPersonalActionInfo(long grantid, BudgetCollectionBean bc, 
                                          boolean apprVisible, int typeCode)
  {
    ArrayList results = new ArrayList();    
    try{
        conn = initializeConn();
        
        if(typeCode==0)
        {
          //get all info from personal_expenses      
          ps = conn.prepareStatement("select * from personal_services where gra_id=? order by fy_code, id");
          ps.setLong(1, grantid);
        }
        else
        {
          //get either proff staff or support staff 
          ps = conn.prepareStatement("select * from personal_services where gra_id=? and smet_code=? order by fy_code, id");
          ps.setLong(1, grantid);
          ps.setInt(2, typeCode);
        }
              
        rs = ps.executeQuery();         
        while(rs.next())
        {
          PersonalBean pb = new PersonalBean();
          pb.setId(rs.getLong("id"));
          pb.setName(rs.getString("name"));     
          pb.setTitle(rs.getString("title"));
          pb.setFycode(rs.getInt("fy_code"));
          pb.setFte(rs.getFloat("fte"));
          pb.setFteStr(rs.getString("fte"));
          pb.setProjtotal(rs.getInt("proj_total"));
          pb.setSalaryrate(rs.getString("salary_rate"));
          pb.setSalary(rs.getInt("salary_rate"));
          pb.setSalaryf(rs.getFloat("salary_rate"));//NEW 2/25/08
          pb.setTypeCode(rs.getInt("smet_code"));
          pb.setInstcont(rs.getInt("inst_cont"));
          pb.setGrantrequest(rs.getInt("grant_request"));
          pb.setExpsubmitted(rs.getInt("exp_submitted"));
          pb.setCategoryTotal(rs.getBoolean("is_total"));
          pb.setAmtamended(rs.getInt("amend_amount"));//NEW 7/7/10
          pb.setBeginDate(rs.getDate("begin_date"));//new 8/1/14
          if(rs.getDate("begin_date")!=null){
            pb.setBeginDateStr(formatter.format(rs.getDate("begin_date")));
          }
          pb.setEndDate(rs.getDate("end_date"));
          if(rs.getDate("end_date")!=null){
            pb.setEndDateStr(formatter.format(rs.getDate("end_date")));
          }
            
          if(apprVisible)
          {
            pb.setExpapproved(rs.getInt("exp_approved"));
            pb.setAmountapproved(rs.getInt("amount_approved"));
          }
          
          //need to calc cost 1st before formatting as currency
          int cost = 0;
          if(pb.getSalaryrate()!=null)
          {
            //cost = (int) (pb.getFte() * Integer.parseInt(pb.getSalaryrate())  );
            cost = (int) (pb.getFte() * Float.parseFloat(pb.getSalaryrate()));//testing float
          }
          String costStr = Integer.toString(cost);
          //long costLg = Long.parseLong(costStr);//error when salaryrate is decimal
          //pb.setCost(numF.format(costLg));
          double costDb = Double.parseDouble(costStr);
          pb.setCost(numF.format(costDb));
                    
          if(pb.getSalaryrate()!=null && !pb.getSalaryrate().equals(""))
          {
            //long salary = Long.parseLong(pb.getSalaryrate());//error parsing to long when salaryrate has a decimal
            //pb.setSalaryrate(numF.format(salary)); 
            double salary = Double.parseDouble(pb.getSalaryrate());//2/25/08
            pb.setSalaryrate(numF.format(salary));
          }
                   
          pb.setGrantrequestStr(rs.getString("grant_request"));
          if(pb.getGrantrequestStr()!=null && !pb.getGrantrequestStr().equals(""))
          {
            long amtreq = Long.parseLong(pb.getGrantrequestStr());
            pb.setGrantrequestStr(numF.format(amtreq));
          }
          
          pb.setExpsubmittedStr(rs.getString("exp_submitted"));
          if(pb.getExpsubmittedStr()!=null && !pb.getExpsubmittedStr().equals(""))
          {
            long expsub = Long.parseLong(pb.getExpsubmittedStr());
            pb.setExpsubmittedStr(numF.format(expsub));         
          }
         
          pb.setInstcontStr(rs.getString("inst_cont"));
          if(pb.getInstcontStr()!=null && !pb.getInstcontStr().equals(""))
          {
            long instcont = Long.parseLong(pb.getInstcontStr());
            pb.setInstcontStr(numF.format(instcont));        
          }
          
          pb.setAmtamendedStr(rs.getString("amend_amount"));
          if(pb.getAmtamendedStr()!=null && !pb.getAmtamendedStr().equals(""))
          {
            long amtamend = Long.parseLong(pb.getAmtamendedStr());
            pb.setAmtamendedStr(numF.format(amtamend));        
          }
          results.add(pb);
        }
        
        bc.setAllPersRecords(results);        
        
      }catch(Exception e){
        System.err.println("error getPersonalActionInfo() "+ e.getMessage());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }  
    return bc;
  }


  /**
   * Modified 2/26/08 to throw db exception. Method saves expenses for apcnt.
   * Gets info from ActionForm which validates required fields. not used for lit anymore 1/3/11
   * modified 8/3/14 to allow updating of encumbrance/journal/other FS10F fields.
   * @throws java.lang.Exception
   * @return 
   * @param bc
   * @param userb
   * @param tab
   */
  public int updateExpenses(BudgetCollectionBean bc, UserBean userb, int tab) throws Exception
  {    
    String user_id = userb.getUserid();
    int outcome=0;      
      
    try{
      conn = initializeConn();    
      
      switch(tab){
        
      case 1:
      //PERSONAL SERVICE EXPENSES
      ps = conn.prepareStatement("update PERSONAL_SERVICES set " +
          " EXP_SUBMITTED = ?, DATE_MODIFIED = SYSDATE, MODIFIED_BY = ?, "+
          " begin_date=to_date(?, 'mm/dd/yyyy'), end_date=to_date(?, 'mm/dd/yyyy') where ID = ?");
      
      //get the number of personal service records
      List allRecords = bc.getAllPersRecords();
      if(allRecords !=null)
      {
          int numPerExp = allRecords.size();
        
          //loop on the number of personal service records and get all info
          for(int i=0;i<numPerExp; i++)
          {
            PersonalBean pb = (PersonalBean) allRecords.get(i);
          
            String sub = pb.getExpsubmittedStr();
            int sub_int = 0;
            if(sub!= null && !sub.equals(""))
              sub_int = dbh.parseCurrencyAmtNoDecimal(sub);
                                       
            //set params and update
            ps.setInt(1, sub_int);
            ps.setString(2, user_id);
            ps.setString(3, pb.getBeginDateStr());
            ps.setString(4, pb.getEndDateStr());
            ps.setLong(5, pb.getId());                 
            outcome = ps.executeUpdate();
          }
      }
      break;
      
      case 2:
      //BENEFITS EXPENSES
      ps = conn.prepareStatement("update EMPLOYEE_BENEFITS set " +
          " EXP_SUBMITTED = ?, DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?");
      
      //get the number of benfit exp records
      List allBenefits = bc.getAllBenRecords();
      if(allBenefits !=null)
      {
          int numBenExp = allBenefits.size();
        
          //loop on the number of benefits expense records and get all info
          for(int i=0;i<numBenExp; i++)
          {
            BenefitsBean bb = (BenefitsBean) allBenefits.get(i);
                  
            String sub = bb.getExpsubmittedStr();
            int sub_int = 0;
            if(sub!= null && !sub.equals(""))
              sub_int = dbh.parseCurrencyAmtNoDecimal(sub);
                                                         
            //set params and update
            ps.setInt(1, sub_int);
            ps.setString(2, user_id);
            ps.setLong(3, bb.getId());            
            outcome = ps.executeUpdate();
          }
      }
      break;
      
      
      case 3:
      //CONTRACTED EXPENSES
      ps= conn.prepareStatement("update CONTRACTED_SERVICES set " +
          " EXP_SUBMITTED = ?, DATE_MODIFIED = SYSDATE, MODIFIED_BY = ?, "+
          " encumbrance_date=to_date(?, 'mm/dd/yyyy'), journal_entry=?, provider_used=? where ID = ?");
      
      //get the number of contracted records
      List allContract = bc.getAllContractRecords();
      if(allContract !=null)
      {
          int numConExp = allContract.size();
        
          //loop on the number of contracted expense records and get all info
          for(int i=0;i<numConExp; i++)
          {
            ContractedBean cb = (ContractedBean) allContract.get(i);
                           
            String sub = cb.getExpsubmittedStr();
            int sub_int = 0;
            if(sub!= null && !sub.equals(""))
              sub_int = dbh.parseCurrencyAmtNoDecimal(sub);
                                                                
            //set params and update
            ps.setInt(1, sub_int);
            ps.setString(2, user_id);
            ps.setString(3, cb.getEncumbranceDateStr());
            ps.setString(4, cb.getJournalEntry());
            ps.setString(5, cb.getProviderUsed());
            ps.setLong(6, cb.getId());            
            outcome = ps.executeUpdate();
          }
      }
      break;
      
      
      case 4:
      //SUPPLY MATERIAL EQUIP EXPENSES 
      ps = conn.prepareStatement("update SUPP_MAT_EQUIPS set " +
          " EXP_SUBMITTED = ?, DATE_MODIFIED = SYSDATE, MODIFIED_BY = ?, "+
          " encumbrance_date=to_date(?, 'mm/dd/yyyy'), journal_entry=? where ID = ?");
      
      //get the number of supply records
      List allSupply = bc.getAllSupplyRecords();
      if(allSupply !=null)
      {
          int numSmeExp = allSupply.size();
        
          //loop on the number of supply, material expense records and get all info
          for(int i=0;i<numSmeExp; i++)
          {
            SupplyBean sb = (SupplyBean) allSupply.get(i);
          
            String sub = sb.getExpsubmittedStr();
            int sub_int = 0;
            if(sub!= null && !sub.equals(""))
              sub_int = dbh.parseCurrencyAmtNoDecimal(sub);
                     
            //set params and update
            ps.setInt(1, sub_int);
            ps.setString(2, user_id);
            ps.setString(3, sb.getEncumbranceDateStr());
            ps.setString(4, sb.getJournalEntry());
            ps.setLong(5, sb.getId());
            
            outcome = ps.executeUpdate();
          }
      }
      break;
      
      
      case 5:
      //OTHER EXPENSES
      ps= conn.prepareStatement("update OTHER_EXPENSES set " +
          " EXP_SUBMITTED = ?, DATE_MODIFIED = SYSDATE, MODIFIED_BY = ?,  "+
          " encumbrance_date=to_date(?, 'mm/dd/yyyy'), journal_entry=?, provider_used=?  where ID = ?");
      
      //get the number of other_exp records
      List allOther = bc.getAllExpRecords();
      if(allOther !=null)
      {
          int numOtExp = allOther.size();
        
          //loop on the number of other_expense records and get all info
          for(int i=0;i<numOtExp; i++)
          {
            OtherExpBean ob = (OtherExpBean) allOther.get(i);
              
            String sub = ob.getExpsubmittedStr();
            int sub_int = 0;
            if(sub!= null && !sub.equals(""))
              sub_int = dbh.parseCurrencyAmtNoDecimal(sub);
                                                   
            //set params and update
            ps.setInt(1, sub_int);
            ps.setString(2, user_id);
            ps.setString(3, ob.getEncumbranceDateStr());
            ps.setString(4, ob.getJournalEntry());
            ps.setString(5, ob.getProviderUsed());
            ps.setLong(6, ob.getId());
            outcome = ps.executeUpdate();
          }
      }
      break;     
      
      
    case 6:
      //TRAVEL EXPENSES
      ps = conn.prepareStatement("update TRAVEL_EXPENSES set " +
          " EXP_SUBMITTED = ?, DATE_MODIFIED = SYSDATE, MODIFIED_BY = ?,  "+
          " travel_period=?, journal_entry=?, traveler_name=? where ID = ?");
      
      //get the number of other_exp records
      List allTravel = bc.getAllTravelRecords();
      if(allTravel !=null)
      {
          int numTrExp = allTravel.size();
        
          //loop on the number of other_expense records and get all info
          for(int i=0;i<numTrExp; i++)
          {
            TravelBean ob = (TravelBean) allTravel.get(i);
              
            String sub = ob.getExpsubmittedStr();
            int sub_int = 0;
            if(sub!= null && !sub.equals(""))
              sub_int = dbh.parseCurrencyAmtNoDecimal(sub);
                                                  
            //set params and update
            ps.setInt(1, sub_int);
            ps.setString(2, user_id);
            ps.setString(3, ob.getTravelPeriod());
            ps.setString(4, ob.getJournalEntry());
            ps.setString(5, ob.getTravelerName());
            ps.setLong(6, ob.getId());
            outcome = ps.executeUpdate();
          }
      }
      break;      
    }           
           
  }catch(SQLException se) {
    System.err.println("error updateExpenses() "+ se.getMessage().toString());
    throw new Exception(se.toString(), se);
    
  }catch(Exception e){
    System.err.println("error updateExpenses() "+ e.getMessage().toString());
    throw new Exception(e.toString(), e);    
  }
  finally{
    Close(conn);
    Close(ps);
    Close(rs);
  }
        
  return outcome;
}

 
    public int updateLitCategoryExpenses(BudgetCollectionBean bc, UserBean userb, int tab) throws Exception
    {    
      String user_id = userb.getUserid();
      int outcome=0;      
        
      try{
        conn = initializeConn();    
        
        switch(tab){
          
        case 1:
        //PERSONAL SERVICE EXPENSES
        ps = conn.prepareStatement("update PERSONAL_SERVICES set " +
            " EXP_SUBMITTED = ?, DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?");
        
        //get the number of personal service records
        List allRecords = bc.getAllPersRecords();
        if(allRecords !=null)
        {
            int numPerExp = allRecords.size();
            //loop on the number of personal service records and get all info
            for(int i=0;i<numPerExp; i++)
            {
              PersonalBean pb = (PersonalBean) allRecords.get(i);
              
              //lit apcnts enter expenses per fy per category-not indiv records 1/3/11
              if(pb.isCategoryTotal()){
                  String sub = pb.getExpsubmittedStr();
                  int sub_int = 0;
                  if(sub!= null && !sub.equals(""))
                    sub_int = dbh.parseCurrencyAmtNoDecimal(sub);
                                             
                  //set params and update
                  ps.setInt(1, sub_int);
                  ps.setString(2, user_id);
                  ps.setLong(3, pb.getId());                 
                  outcome = ps.executeUpdate();
              }
            }
        }
        break;
        
        case 2:
        //BENEFITS EXPENSES
        ps = conn.prepareStatement("update EMPLOYEE_BENEFITS set " +
            " EXP_SUBMITTED = ?, DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?");
        
        //get the number of benfit exp records
        List allBenefits = bc.getAllBenRecords();
        if(allBenefits !=null)
        {
            int numBenExp = allBenefits.size();
          
            //loop on the number of benefits expense records and get all info
            for(int i=0;i<numBenExp; i++)
            {
              BenefitsBean bb = (BenefitsBean) allBenefits.get(i);
              
              if(bb.isCategoryTotal()){      
               
                  String sub = bb.getExpsubmittedStr();
                  int sub_int = 0;
                  if(sub!= null && !sub.equals(""))
                    sub_int = dbh.parseCurrencyAmtNoDecimal(sub);
                                                               
                  //set params and update
                  ps.setInt(1, sub_int);
                  ps.setString(2, user_id);
                  ps.setLong(3, bb.getId());            
                  outcome = ps.executeUpdate();
              }
            }
        }
        break;
        
        
        case 3:
        //CONTRACTED EXPENSES
        ps= conn.prepareStatement("update CONTRACTED_SERVICES set " +
            " EXP_SUBMITTED = ?, DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?");
        
        //get the number of contracted records
        List allContract = bc.getAllContractRecords();
        if(allContract !=null)
        {
            int numConExp = allContract.size();
          
            //loop on the number of contracted expense records and get all info
            for(int i=0;i<numConExp; i++)
            {
              ContractedBean cb = (ContractedBean) allContract.get(i);
                             
              if(cb.isCategoryTotal()){
             
                  String sub = cb.getExpsubmittedStr();
                  int sub_int = 0;
                  if(sub!= null && !sub.equals(""))
                    sub_int = dbh.parseCurrencyAmtNoDecimal(sub);
                                                                      
                  //set params and update
                  ps.setInt(1, sub_int);
                  ps.setString(2, user_id);
                  ps.setLong(3, cb.getId());            
                  outcome = ps.executeUpdate();
              }
            }
        }
        break;
        
        
        case 4:
        //SUPPLY MATERIAL EQUIP EXPENSES 
        ps = conn.prepareStatement("update SUPP_MAT_EQUIPS set " +
            " EXP_SUBMITTED = ?, DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?");
        
        //get the number of supply records
        List allSupply = bc.getAllSupplyRecords();
        if(allSupply !=null)
        {
            int numSmeExp = allSupply.size();
          
            //loop on the number of supply, material expense records and get all info
            for(int i=0;i<numSmeExp; i++)
            {
              SupplyBean sb = (SupplyBean) allSupply.get(i);
            
              if(sb.isCategoryTotal()){
               
                  String sub = sb.getExpsubmittedStr();
                  int sub_int = 0;
                  if(sub!= null && !sub.equals(""))
                    sub_int = dbh.parseCurrencyAmtNoDecimal(sub);
                           
                  //set params and update
                  ps.setInt(1, sub_int);
                  ps.setString(2, user_id);
                  ps.setLong(3, sb.getId());              
                  outcome = ps.executeUpdate();
              }
            }
        }
        break;
        
        
        case 5:
        //OTHER EXPENSES
        ps= conn.prepareStatement("update OTHER_EXPENSES set " +
            " EXP_SUBMITTED = ?, DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?");
        
        //get the number of other_exp records
        List allOther = bc.getAllExpRecords();
        if(allOther !=null)
        {
            int numOtExp = allOther.size();
          
            //loop on the number of other_expense records and get all info
            for(int i=0;i<numOtExp; i++)
            {
              OtherExpBean ob = (OtherExpBean) allOther.get(i);
                
              if(ob.isCategoryTotal()){
             
                  String sub = ob.getExpsubmittedStr();
                  int sub_int = 0;
                  if(sub!= null && !sub.equals(""))
                    sub_int = dbh.parseCurrencyAmtNoDecimal(sub);
                                                         
                  //set params and update
                  ps.setInt(1, sub_int);
                  ps.setString(2, user_id);
                  ps.setLong(3, ob.getId());
                  outcome = ps.executeUpdate();
              }
            }
        }
        break;     
        
        
      case 6:
        //TRAVEL EXPENSES
        ps = conn.prepareStatement("update TRAVEL_EXPENSES set " +
            " EXP_SUBMITTED = ?, DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?");
        
        //get the number of other_exp records
        List allTravel = bc.getAllTravelRecords();
        if(allTravel !=null)
        {
            int numTrExp = allTravel.size();
          
            //loop on the number of other_expense records and get all info
            for(int i=0;i<numTrExp; i++)
            {
              TravelBean ob = (TravelBean) allTravel.get(i);
                
              if(ob.isCategoryTotal()){
             
                  String sub = ob.getExpsubmittedStr();
                  int sub_int = 0;
                  if(sub!= null && !sub.equals(""))
                    sub_int = dbh.parseCurrencyAmtNoDecimal(sub);
                                                        
                  //set params and update
                  ps.setInt(1, sub_int);
                  ps.setString(2, user_id);
                  ps.setLong(3, ob.getId());
                  outcome = ps.executeUpdate();
              }
            }
        }
        break;      
      }           
             
    }catch(SQLException se) {
      System.err.println("error updateLitCategoryExpenses() "+ se.getMessage().toString());
      throw new Exception(se.toString(), se);
      
    }catch(Exception e){
      System.err.println("error updateLitCategoryExpenses() "+ e.getMessage().toString());
      throw new Exception(e.toString(), e);    
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }          
    return outcome;
    }
    
 
    public int updateExpensesWithAmend(BudgetCollectionBean bc, UserBean userb, int tab) throws Exception
    {    
      String user_id = userb.getUserid();
      int outcome=0;      
        
      try{
        conn = initializeConn();    
        
        switch(tab){
          
        case 1:
        //PERSONAL SERVICE EXPENSES
        ps = conn.prepareStatement("update PERSONAL_SERVICES set " +
            " AMEND_AMOUNT=?, EXP_SUBMITTED = ?, DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?");
        
        //get the number of personal service records
        List allRecords = bc.getAllPersRecords();
        if(allRecords !=null)
        {
            int numPerExp = allRecords.size();
          
            //loop on the number of personal service records and get all info
            for(int i=0;i<numPerExp; i++)
            {
              PersonalBean pb = (PersonalBean) allRecords.get(i);
            
              String sub = pb.getExpsubmittedStr();
              int sub_int = 0;
              if(sub!= null && !sub.equals(""))
                sub_int = dbh.parseCurrencyAmtNoDecimal(sub);
                
                String amendamt = pb.getAmtamendedStr();
                int amamt = 0;
                if(amendamt!= null && !amendamt.equals(""))
                  amamt = dbh.parseCurrencyAmtNoDecimal(amendamt);
                            
              //set params and update
              ps.setInt(1, amamt);
              ps.setInt(2, sub_int);
              ps.setString(3, user_id);
              ps.setLong(4, pb.getId());                 
              outcome = ps.executeUpdate();
            }
        }
        break;
        
        case 2:
        //BENEFITS EXPENSES
        ps = conn.prepareStatement("update EMPLOYEE_BENEFITS set " +
            " AMEND_AMOUNT=?, EXP_SUBMITTED = ?, DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?");
        
        //get the number of benfit exp records
        List allBenefits = bc.getAllBenRecords();
        if(allBenefits !=null)
        {
            int numBenExp = allBenefits.size();
          
            //loop on the number of benefits expense records and get all info
            for(int i=0;i<numBenExp; i++)
            {
              BenefitsBean bb = (BenefitsBean) allBenefits.get(i);
                    
              String sub = bb.getExpsubmittedStr();
              int sub_int = 0;
              if(sub!= null && !sub.equals(""))
                sub_int = dbh.parseCurrencyAmtNoDecimal(sub);
                
                String amendamt = bb.getAmtamendedStr();
                int amamt = 0;
                if(amendamt!= null && !amendamt.equals(""))
                  amamt = dbh.parseCurrencyAmtNoDecimal(amendamt);
                                            
              //set params and update
              ps.setInt(1, amamt);
              ps.setInt(2, sub_int);
              ps.setString(3, user_id);
              ps.setLong(4, bb.getId());            
              outcome = ps.executeUpdate();
            }
        }
        break;
        
        
        case 3:
        //CONTRACTED EXPENSES
        ps= conn.prepareStatement("update CONTRACTED_SERVICES set " +
            " AMEND_AMOUNT=?, EXP_SUBMITTED = ?, DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?");
        
        //get the number of contracted records
        List allContract = bc.getAllContractRecords();
        if(allContract !=null)
        {
            int numConExp = allContract.size();
          
            //loop on the number of contracted expense records and get all info
            for(int i=0;i<numConExp; i++)
            {
              ContractedBean cb = (ContractedBean) allContract.get(i);
                             
              String sub = cb.getExpsubmittedStr();
              int sub_int = 0;
              if(sub!= null && !sub.equals(""))
                sub_int = dbh.parseCurrencyAmtNoDecimal(sub);
                
                String amendamt = cb.getAmtamendedStr();
                int amamt = 0;
                if(amendamt!= null && !amendamt.equals(""))
                  amamt = dbh.parseCurrencyAmtNoDecimal(amendamt);
                                               
              //set params and update
              ps.setInt(1, amamt);
              ps.setInt(2, sub_int);
              ps.setString(3, user_id);
              ps.setLong(4, cb.getId());            
              outcome = ps.executeUpdate();
            }
        }
        break;
        
        
        case 4:
        //SUPPLY MATERIAL EQUIP EXPENSES 
        ps = conn.prepareStatement("update SUPP_MAT_EQUIPS set " +
            " AMEND_AMOUNT=?, EXP_SUBMITTED = ?, DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?");
        
        //get the number of supply records
        List allSupply = bc.getAllSupplyRecords();
        if(allSupply !=null)
        {
            int numSmeExp = allSupply.size();
          
            //loop on the number of supply, material expense records and get all info
            for(int i=0;i<numSmeExp; i++)
            {
              SupplyBean sb = (SupplyBean) allSupply.get(i);
            
              String sub = sb.getExpsubmittedStr();
              int sub_int = 0;
              if(sub!= null && !sub.equals(""))
                sub_int = dbh.parseCurrencyAmtNoDecimal(sub);
              
                String amendamt = sb.getAmtamendedStr();
                int amamt = 0;
                if(amendamt!= null && !amendamt.equals(""))
                  amamt = dbh.parseCurrencyAmtNoDecimal(amendamt);
            
              //set params and update
              ps.setInt(1, amamt);
              ps.setInt(2, sub_int);
              ps.setString(3, user_id);
              ps.setLong(4, sb.getId());
              
              outcome = ps.executeUpdate();
            }
        }
        break;
        
        
        case 5:
        //OTHER EXPENSES
        ps= conn.prepareStatement("update OTHER_EXPENSES set " +
            " AMEND_AMOUNT=?, EXP_SUBMITTED = ?, DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?");
        
        //get the number of other_exp records
        List allOther = bc.getAllExpRecords();
        if(allOther !=null)
        {
            int numOtExp = allOther.size();
          
            //loop on the number of other_expense records and get all info
            for(int i=0;i<numOtExp; i++)
            {
              OtherExpBean ob = (OtherExpBean) allOther.get(i);
                
              String sub = ob.getExpsubmittedStr();
              int sub_int = 0;
              if(sub!= null && !sub.equals(""))
                sub_int = dbh.parseCurrencyAmtNoDecimal(sub);
                     
                String amendamt = ob.getAmtamendedStr();
                int amamt = 0;
                if(amendamt!= null && !amendamt.equals(""))
                  amamt = dbh.parseCurrencyAmtNoDecimal(amendamt);
                                  
              //set params and update
              ps.setInt(1, amamt);
              ps.setInt(2, sub_int);
              ps.setString(3, user_id);
              ps.setLong(4, ob.getId());
              outcome = ps.executeUpdate();
            }
        }
        break;     
        
        
      case 6:
        //TRAVEL EXPENSES
        ps = conn.prepareStatement("update TRAVEL_EXPENSES set AMEND_AMOUNT=?, " +
            " EXP_SUBMITTED = ?, DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?");
        
        //get the number of other_exp records
        List allTravel = bc.getAllTravelRecords();
        if(allTravel !=null)
        {
            int numTrExp = allTravel.size();
          
            //loop on the number of other_expense records and get all info
            for(int i=0;i<numTrExp; i++)
            {
              TravelBean ob = (TravelBean) allTravel.get(i);
                
              String sub = ob.getExpsubmittedStr();
              int sub_int = 0;
              if(sub!= null && !sub.equals(""))
                sub_int = dbh.parseCurrencyAmtNoDecimal(sub);
                     
                String amendamt = ob.getAmtamendedStr();
                int amamt = 0;
                if(amendamt!= null && !amendamt.equals(""))
                  amamt = dbh.parseCurrencyAmtNoDecimal(amendamt);
                                 
              //set params and update
              ps.setInt(1, amamt);
              ps.setInt(2, sub_int);
              ps.setString(3, user_id);
              ps.setLong(4, ob.getId());
              outcome = ps.executeUpdate();
            }
        }
        break;      
      }           
             
    }catch(SQLException se) {
      System.err.println("error updateExpensesWithAmend() "+ se.getMessage().toString());
      throw new Exception(se.toString(), se);
      
    }catch(Exception e){
      System.err.println("error updateExpensesWithAmend() "+ e.getMessage().toString());
      throw new Exception(e.toString(), e);    
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }
          
    return outcome;
    }

  /**
   * This method will calculate the total amount of discretionary money approved so far for the
   * fiscal year.  Fiscal year is determined by the selected grant.  used to print warnings
   * for di admin when they have spent all alloted di money for the fiscal year. 
   * @return 
   * @param fycode
   */
  public TotalsBean calcDiFyTotalAmtAppr(int fycode)
  {
    TotalsBean tb = new TotalsBean();
    int total = 0, totalfund=0;
          
    try {
      conn = initializeConn();       
      ps = conn.prepareStatement("select TOTAL_FUND from APP_DATES where FC_CODE=5 and FY_CODE=?");
      ps.setInt(1, fycode);
      rs = ps.executeQuery();
      while(rs.next())
        totalfund= rs.getInt("total_fund");
        
      ps.clearParameters();     
      ps = conn.prepareStatement("select sum(totappr) as sumappr from ldgrants.BUDGETTOTALSVIEW "+
      " where fy_code=? and fc_code=5");
      ps.setInt(1, fycode);
      rs = ps.executeQuery();
      while(rs.next()){
          total = rs.getInt("sumappr");
      }
      
      //'usual' fy limit for DI money is 500,000
      tb.setAmtavailable(totalfund);
      tb.setTotAmtAppr(total);
      tb.setAmtdifference((totalfund-total));
      tb.setFycode(fycode);      
      if(total > totalfund)
        tb.setWarning(true);
            
    }catch(Exception e){
      System.err.println("error calcDiFyTotalAmtAppr() "+ e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }      
    return tb;
  }
  
  
  
  
  
   
    /**
     * This method will calculate the total amount of lgrmif money approved so far for the
     * fy for given panel. used to print warnings for lg admin/revs when they have spent 
     * all alloted panel money for the fiscal year. 
     * NOTE: this gets total appr regardless of the approve/deny flag.  So it will calc 
     * amt appr even if the app flag is changed to deny.
     * @return 
     * @param fycode
     */
    public TotalsBean calcLgAmtApprForPanel(long grantid, int fycode, int totgrantappr)
    {
      TotalsBean tb = new TotalsBean();
      long panelid =0;
      
      try {
        conn = initializeConn();    
        ps = conn.prepareStatement("select pan_id, recommend_amt, status from ldgrants.panels, "+
        " panel_grants where panels.id=panel_grants.pan_id and gra_id=?");
        ps.setLong(1, grantid);
        rs = ps.executeQuery();
        while(rs.next()){
            panelid = rs.getLong("pan_id");
            tb.setRecommendamt(rs.getInt("recommend_amt"));
            tb.setStatus(rs.getString("status"));
        }
        
        ps = conn.prepareStatement("select * from ldgrants.lgrmif_paneltotalsview where "+
        " id=? and FY_CODE=?");
        ps.setLong(1, panelid);
        ps.setInt(2, fycode);
        rs = ps.executeQuery();
        while(rs.next()){
            tb.setFycode(fycode);
            tb.setAmtavailable(rs.getInt("amt_available"));
            tb.setTotAmtAppr(rs.getInt("totapproval"));
            tb.setAmtdifference(rs.getInt("amtdiff"));
        }       
        if(tb.getTotAmtAppr() > tb.getAmtavailable())
          tb.setWarning(true);
        if(totgrantappr != tb.getRecommendamt())
            tb.setNotice(true);
                      
      }catch(Exception e){
        System.err.println("error calcLgAmtApprForPanel() "+ e.getMessage().toString());
      }
      finally{
        Close(conn);
        Close(ps);
        Close(rs);
      }      
      return tb;
    }
  
  
  public int determineLgBudgetCode(int lgTab)
  {
    int typeCode=0;
    
    switch(lgTab)
    {
      case 1:
        typeCode=3;
        break;
      case 2:
        typeCode=4;
        break;
      case 3:
        typeCode=2;
        break;
      case 7:
        typeCode=1;
        break;
      case 5:
        typeCode=5;
        break;
      case 6:
        typeCode=6;
        break;        
    }
    return typeCode;
  }
  
  
    public int determineLitBudgetCode(int litTab)
    {
      int typeCode=0;
      
      switch(litTab)
      {
        case 1:
          typeCode=3;
          break;
        case 2:
          typeCode=4;
          break;
        case 4:
          typeCode=5;
          break;
        case 5:
          typeCode=1;
          break;
        case 6:
          typeCode=2;
          break;        
      }
      return typeCode;
    }
    
  
    public int deleteAmtApprovedAll(long grantid, String username)
    {    
      int outcome = 0;
      
      try {
        conn = initializeConn();
        ps = conn.prepareStatement("update personal_services set amount_approved=0, modified_by=?, "+
         " date_modified=sysdate where gra_id=?");  
        ps.setString(1, username);
        ps.setLong(2, grantid);        
        outcome = ps.executeUpdate();
        
      ps = conn.prepareStatement("update employee_benefits set amount_approved=0, modified_by=?, "+
       " date_modified=sysdate where gra_id=?");  
      ps.setString(1, username);
      ps.setLong(2, grantid);        
      outcome = ps.executeUpdate();
      
      ps = conn.prepareStatement("update contracted_services set amount_approved=0, modified_by=?, "+
       " date_modified=sysdate where gra_id=?");  
      ps.setString(1, username);
      ps.setLong(2, grantid);        
      outcome = ps.executeUpdate();
      
      ps = conn.prepareStatement("update supp_mat_equips set amount_approved=0, modified_by=?, "+
       " date_modified=sysdate where gra_id=?");  
      ps.setString(1, username);
      ps.setLong(2, grantid);        
      outcome = ps.executeUpdate();
      
      ps = conn.prepareStatement("update other_expenses set amount_approved=0, modified_by=?, "+
       " date_modified=sysdate where gra_id=?");  
      ps.setString(1, username);
      ps.setLong(2, grantid);        
      outcome = ps.executeUpdate();
    
      ps = conn.prepareStatement("update travel_expenses set amount_approved=0, modified_by=?, "+
       " date_modified=sysdate where gra_id=?");  
      ps.setString(1, username);
      ps.setLong(2, grantid);        
      outcome = ps.executeUpdate();
                                   
      } catch (Exception ex){
          System.err.println("error deleteAmtApprovedAll()" + ex.toString());
      }finally {
        Close(conn);
        Close(ps);
        Close(rs);
      }

      return outcome;
    }

    public int calcCnFinalExpenseTotal(long grantnum)
    {
      int total =0;    
      try{             
          //calculate the total of all final exp records-used by construction      
            conn = initializeConn();
            ps = conn.prepareStatement("select sum(expenditure) as totsub from " +
            "grant_expenditures where gra_id=?");
            ps.setLong(1, grantnum);
            rs = ps.executeQuery();
            while(rs.next()){
                total = rs.getInt("totsub");
            }                   
                       
      }catch(Exception e) {
        System.err.println("error calcCnFinalExpenseTotal() " + e.getMessage().toString());
      }
      finally{
          Close(rs);
          Close(ps);
          Close(conn);
      }
      return total;
    }    
    
    
  public void autoApprovePersonalServ(UserBean userb, long grantid)
  {    
    //get all personal expense records for this grant
    Vector allRecords = getPersonalInfoForAppr(grantid);    
               
    try {             
        //if records returned - auto update appr amts
        if(allRecords.size()>0){      
            
          conn = initializeConn();
          
          ps = conn.prepareStatement("update PERSONAL_SERVICES set AMOUNT_APPROVED = ?, " + 
            " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?");
                    
          //loop on all the records we must update
          for(int i=0; i<allRecords.size(); i++)
          {
              //get each record
              PersonalBean pb = (PersonalBean) allRecords.get(i); 
              
              ps.setInt(1, pb.getGrantrequest());         
              ps.setString(2, userb.getUserid());
              ps.setLong(3, pb.getId());
              ps.addBatch();         
          }
          
          ps.executeBatch();
         // System.out.println(allRecords.size()+" pers recs");
        }
        
    }catch(Exception e){
        System.err.println("error autoApprovePersonalServ() "+ e.getMessage().toString());
      }
      finally{
        Close(conn);
        Close(ps);
      }    
  }
  
  
  public void autoApproveBenefits(UserBean userb, long grantid)
  {
    //get all personal expense records for this grant
    Vector allRecords = getBenefitInfoForAppr(grantid);    
    
    try{           
        //if records returned - auto update appr amts
        if(allRecords.size()>0){             
        
          conn = initializeConn();
          
          ps = conn.prepareStatement("update EMPLOYEE_BENEFITS set AMOUNT_APPROVED = ?, " + 
            " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?");
          
          //loop on all the records we must update
          for(int i=0; i<allRecords.size(); i++)
          {
            //get 1 record
            BenefitsBean bb = (BenefitsBean) allRecords.get(i); 
            
            ps.setInt(1, bb.getGrantrequest());
            ps.setString(2, userb.getUserid());
            ps.setLong(3, bb.getId());
            ps.addBatch();        
          }
          ps.executeBatch();
        //System.out.println(allRecords.size()+" benefit recs");
      }
        
    }catch(Exception e){
        System.err.println("error autoApproveBenefits() "+ e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
    }
  }
  
  
  
  
  public void autoApproveContractedServ(UserBean userb, long grantid)
  {
    //get all personal expense records for this grant
    Vector allRecords = getContractedInfoForAppr(grantid);    
    
      try{          
        //if records returned - auto update appr amts
        if(allRecords.size()>0){             
        
          conn = initializeConn();
          
          //create stmt based on type of auto update (amts requested or exps sub)
           ps = conn.prepareStatement("update CONTRACTED_SERVICES set AMOUNT_APPROVED = ?, " + 
            " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?");
         
          
          //loop on all the records we must update
          for(int i=0; i<allRecords.size(); i++)
          {
            //get 1 record
            ContractedBean cb = (ContractedBean) allRecords.get(i); 
       
            ps.setInt(1, cb.getGrantrequest());           
            ps.setString(2, userb.getUserid());
            ps.setLong(3, cb.getId());
            ps.addBatch();     
          }       
          ps.executeBatch();
        //System.out.println(allRecords.size()+" contract recs");
      }      
      
    }catch(Exception e){
        System.err.println("error autoApproveContractedServ() "+ e.getMessage().toString());
      }
      finally{
        Close(conn);
        Close(ps);
      }    
  }
  
  
  
  
  public void autoApproveSupplyEquip(UserBean userb, long grantid)
  {
    //get all personal expense records for this grant
    Vector allRecords = getSupplyInfoForAppr(grantid);    
    
    try{           
        //if records returned - auto update appr amts
        if(allRecords.size()>0){             
        
            conn = initializeConn();
            
            //create stmt based on type of auto update (amts requested or exps sub)
            ps = conn.prepareStatement("update SUPP_MAT_EQUIPS set AMOUNT_APPROVED = ?, " + 
              " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?");
                        
            //loop on all the records we must update
            for(int i=0; i<allRecords.size(); i++)
            {
              //get 1 record
              SupplyBean sb = (SupplyBean) allRecords.get(i); 
              ps.setInt(1, sb.getGrantrequest());                
              ps.setString(2, userb.getUserid());
              ps.setLong(3, sb.getId());
              ps.addBatch();         
            }       
            ps.executeBatch();
          //System.out.println(allRecords.size()+" supply recs");
        }
    }catch(Exception e){
        System.err.println("error autoApproveSupplyEquip() "+ e.getMessage().toString());
      }
      finally{
        Close(conn);
        Close(ps);
      }
  }
  
  
  
  
  public void autoApproveOtherAmts(UserBean userb, long grantid)
  {
    //get all personal expense records for this grant
    Vector allRecords = getExpenseInfoForAppr(grantid);    
    
     try {
        //if records returned - auto update appr amts
        if(allRecords.size()>0){             
        
            conn = initializeConn();
            
            //create stmt based on type of auto update (amts requested or exps sub)
            ps = conn.prepareStatement("update OTHER_EXPENSES set AMOUNT_APPROVED = ?, " + 
              " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?");
                        
            //loop on all the records we must update
            for(int i=0; i<allRecords.size(); i++)
            {
              //get 1 record
              OtherExpBean ob = (OtherExpBean) allRecords.get(i); 
          
              ps.setInt(1, ob.getGrantrequest());
              ps.setString(2, userb.getUserid());
              ps.setLong(3, ob.getId());
              ps.addBatch();     
            }       
            ps.executeBatch();
            //System.out.println(allRecords.size()+" other recs");
      }
    }catch(Exception e){
      System.err.println("error autoApproveOtherAmts() "+ e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
    }
  }  
  
  
  
  
  public void autoApproveTravel(UserBean userb, long grantid)
  {
    //get all expense records for this grant
    Vector allRecords = getTravelInfoForAppr(grantid);    
 
     try{         
        //if records returned - auto update appr amts
        if(allRecords.size()>0){             
        
            conn = initializeConn();
            
            ps = conn.prepareStatement("update TRAVEL_EXPENSES set AMOUNT_APPROVED = ?, " + 
              " DATE_MODIFIED = SYSDATE, MODIFIED_BY = ? where ID = ?");
             
            //loop on all the records we must update
            for(int i=0; i<allRecords.size(); i++)
            {
              TravelBean cb = (TravelBean) allRecords.get(i); 
              
              ps.setInt(1, cb.getGrantrequest());
              ps.setString(2, userb.getUserid());
              ps.setLong(3, cb.getId());
              ps.addBatch();          
            }       
            ps.executeBatch();
            //System.out.println(allRecords.size()+" travel recs");
      }
    }catch(Exception e){
      System.err.println("error autoApproveTravel() "+ e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
    }
  }
  
  
  
  public Vector getPersonalInfoForAppr(long grantid)
  {
    Vector results = new Vector();    
    
    try{
        conn = initializeConn();
        
        ps =conn.prepareStatement("select id, grant_request from personal_services where gra_id=?");
        ps.setLong(1, grantid);
                                   
        rs = ps.executeQuery();            
        while(rs.next())
        {
          PersonalBean pb = new PersonalBean();
          pb.setId(rs.getLong("id"));
          pb.setGrantrequest(rs.getInt("grant_request"));
          pb.setGrantrequestStr(rs.getString("grant_request"));                    
          results.add(pb);
        }
        
      }catch(Exception e){
        System.err.println("error getPersonalInfoForAppr() "+ e.getMessage());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }  
    return results;
  }
  
  
  
  public Vector getBenefitInfoForAppr(long grantid)
  {
    Vector results = new Vector();  
    
    try{
        conn = initializeConn();
        ps = conn.prepareStatement("select id, grant_request from employee_benefits where gra_id=?");
        ps.setLong(1, grantid);        
                
        rs = ps.executeQuery();             
        while(rs.next())
        {
          BenefitsBean bb = new BenefitsBean();
          bb.setId(rs.getLong("id"));
          bb.setGrantrequest(rs.getInt("grant_request"));     
          bb.setGrantrequestStr(rs.getString("grant_request"));
          results.add(bb);
        }
        
      }catch(Exception e){
        System.err.println("error getBenefitInfoForAppr() "+ e.getMessage());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }  
    return results;
  }
  
  
  
  public Vector getContractedInfoForAppr(long grantid)
  {
    Vector results = new Vector();  
    
    try{
        conn = initializeConn();
        ps = conn.prepareStatement("select id, grant_request from contracted_services where gra_id=?");
        ps.setLong(1, grantid);
                
        rs = ps.executeQuery();               
        while(rs.next())
        {
          ContractedBean cb = new ContractedBean();
          cb.setId(rs.getLong("id"));
          cb.setGrantrequest(rs.getInt("grant_request"));       
          cb.setGrantrequestStr(rs.getString("grant_request"));
          results.add(cb);
        }
        
      }catch(Exception e){
        System.err.println("error getContractedInfoForAppr() "+ e.getMessage());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }  
    return results;
  }
  
  
  public Vector getSupplyInfoForAppr(long grantid)
  {
    Vector results = new Vector();    
    try{
        conn = initializeConn();     
        ps =conn.prepareStatement("select id, grant_request from supp_mat_equips where gra_id=?");
        ps.setLong(1, grantid);
                     
       rs = ps.executeQuery();          
        while(rs.next())
        {
          SupplyBean sb = new SupplyBean();
          sb.setId(rs.getLong("id"));
          sb.setGrantrequest(rs.getInt("grant_request"));  
          sb.setGrantrequestStr(rs.getString("grant_request"));
          results.add(sb);
        }
        
      }catch(Exception e){
        System.err.println("error getSupplyInfoForAppr() "+ e.getMessage());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }  
    return results;
  }
  
  
  public Vector getExpenseInfoForAppr(long grantid)
  {
    Vector results = new Vector();     
    try{
        conn = initializeConn();
        ps = conn.prepareStatement("select id, grant_request from other_expenses where gra_id=?");
        ps.setLong(1, grantid);       
        
        rs = ps.executeQuery();        
        while(rs.next()){
          OtherExpBean eb = new OtherExpBean();
          eb.setId(rs.getLong("id"));
          eb.setGrantrequest(rs.getInt("grant_request"));     
          eb.setGrantrequestStr(rs.getString("grant_request"));  
          results.add(eb);
        }
        
      }catch(Exception e){
        System.err.println("error getExpenseInfoForAppr() "+ e.getMessage());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }  
    return results;
  }
  
  
  
  public Vector getTravelInfoForAppr(long grantid)
  {
    Vector results = new Vector();     
    try{
        conn = initializeConn();
        ps = conn.prepareStatement("select id, grant_request from travel_expenses where gra_id=?");
        ps.setLong(1, grantid);
       
        rs = ps.executeQuery();       
        while(rs.next())
        {
          TravelBean tb = new TravelBean();
          tb.setId(rs.getLong("id"));
          tb.setGrantrequest(rs.getInt("grant_request")); 
          tb.setGrantrequestStr(rs.getString("grant_request"));
          results.add(tb);
        }
        
      }catch(Exception e){
        System.err.println("error getTravelInfoForAppr() "+ e.getMessage());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }  
    return results;
  }
  
  
  
}
