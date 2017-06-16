/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  DBHandler.java
 * Creation/Modification History  :
 *
 * SH   2/1/07      Created
 *
 * Description
 * This class will handle all database transanctions including creating or updating records,
 * and deleting records.  
 *****************************************************************************/
package mypackage;


import construction.AllocationYearBean;

import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import javax.naming.*;
import javax.sql.*;
import java.text.NumberFormat;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import mypackage.DropDownListBean;


public class DBHandler 
{

  Connection conn;
  PreparedStatement ps;
  ResultSet rs;
    
  public DBHandler()
  {
  }
    
  /**
   * This creates connection to oracle ldgrants db
   * @throws java.lang.Exception
   * @return 
   */
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

 
  /**
   * This method will check if the institution has a grant application
   * for this fiscal year for the selected grant program.
   * @return true/false depending if inst. has record for current fiscal year
   */
  public boolean hasRecord(int fycode, long inst_id, int fundcode)
  {
    String grant_id = null;
    boolean recordFound = false;
  
    try {    
      conn = initializeConn();
                
      ps = conn.prepareStatement("select ID from GRANTS where FC_CODE=? and FY_CODE=? and "+        
        " ID in (select gra_id from CO_INSTITUTIONS where IS_LEAD='Y' and INST_ID=?)");
      
      ps.setInt(1, fundcode);
      ps.setInt(2, fycode);
      ps.setLong(3, inst_id);
      rs = ps.executeQuery();
                 
      while(rs.next())
      {
        grant_id = rs.getString("ID");                   
        recordFound = true;
      }
                            
    } catch (Exception ex){
        System.err.println("error hasRecord()  " + ex.toString());
        return false;
    }
    finally{
      Close(conn);
      Close(rs);
      Close(ps);
    }    
    return recordFound;  
  }
  
  
  /**
   * This method determines how many lgrmif apps an institution has. lgrmif allows 
   * 2 apps per fy, unlimited apps for doris 800000047654
   * @return 
   * @param inst_id
   * @param fycode
   */
  public boolean hasLgrmifMaxRecord(int fycode, long inst_id, int fccode)
  {
    boolean maxRecords = false;
    int appcount =0;
  
    try {    
      conn = initializeConn();
                
      ps = conn.prepareStatement("select count(*) as appcnt from GRANTS where FC_CODE=? and "+
      "FY_CODE=? and ID in (select gra_id from CO_INSTITUTIONS where IS_LEAD='Y' and INST_ID=?)");
      ps.setInt(1, fccode);
      ps.setInt(2, fycode);
      ps.setLong(3, inst_id);
      rs = ps.executeQuery();
                 
      while(rs.next()){
        appcount = rs.getInt("appcnt");        
      }
      
      long dorisInst = 800000047654L;
      if(appcount==2 && (inst_id!=dorisInst))
        maxRecords=true;
                            
    } catch (Exception ex){
        System.err.println("error hasLgrmifRecord() " + ex.toString());
        return false;
    }
    finally{
      Close(conn);
      Close(rs);
      Close(ps);
    }    
    return maxRecords;  
  }
     
    
  public GrantBean getRecordBean(long grantid)
  {            
    GrantBean gb = new GrantBean();      
    try {
      conn = initializeConn();      
                  
      ps = conn.prepareStatement("SELECT grants.id, inst_id, NAME, fc_code, fy_code, amount_req, "+
      " proj_seq_num, contract_num, education_app, doris_flag, doris_name, fiscal_years.description, "+
      " start_date, end_date, fund_codes.description AS progname FROM grants, co_institutions, fiscal_years, "+
      " fund_codes WHERE grants.ID = ? AND is_lead = 'Y' AND grants.ID = co_institutions.gra_id "+
      " AND grants.fy_code = fiscal_years.code AND grants.fc_code = fund_codes.code");    
      ps.setLong(1, grantid);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        gb.setGrantid(rs.getLong("id"));
        gb.setInstID(rs.getLong("inst_id"));
        gb.setFycode(rs.getInt("fy_code"));
        gb.setFccode(rs.getInt("fc_code"));
        gb.setProjseqnum(rs.getLong("proj_seq_num"));  
          
        if(gb.getFccode()==20){  
            //per BL; need 4 digit proj num to be same every year for stateaid
            gb.setProjseqnum(handleStateAidProjNumbers(gb.getInstID()));            
        }
          
        gb.setTitle(rs.getString("name"));
        gb.setFiscalyear(rs.getString("description"));  
        gb.setProgram(rs.getString("progname"));
        gb.setContractNum(rs.getString("contract_num"));
        gb.setStartdate(rs.getDate("start_date"));//8/13/08 changed db start/end dates to 7/1-6/30
        gb.setEnddate(rs.getDate("end_date"));
        gb.setEducationapp(rs.getBoolean("education_app"));
        gb.setDorisflag(rs.getBoolean("doris_flag"));
        if(gb.isDorisflag())
          gb.setDorisname(rs.getString("doris_name"));
          
        if(gb.getFccode()==20)
            gb.setLdacAppropAmt(rs.getLong("amount_req"));        
      }      
      
      Close(rs);
      Close(ps);
          
     //Get address, county from SEDREF tables       
      ps = conn.prepareStatement("select initcap(i.POPULAR_NAME) as POPULAR_NAME, initcap(a.ADDR_LINE1) as ADDR_LINE1, "+
       " initcap(a.ADDR_LINE2) as ADDR_LINE2, initcap(a.CITY) as CITY, a.STATE_CODE, a.ZIPCD5, a.ZIPADD4, i.sed_code, "+
       " initcap(sed_counties.description) as countyname, sed_counties.county_code from SED_ADDRESSES a, SED_INSTITUTIONS i "+
       " left join sed_counties on sed_counties.COUNTY_CODE =i.county_code where i.INST_ID =? and "+
       " i.INST_ID = A.INST_ID and a.ADDR_TYPE_CODE = 1 ");
       
      ps.setLong(1, gb.getInstID());            
      rs = ps.executeQuery();      
      while(rs.next() )
      {
          gb.setInstName(rs.getString("POPULAR_NAME"));
          gb.setAddr1(rs.getString("ADDR_LINE1"));
          gb.setAddr2(rs.getString("ADDR_LINE2"));
          gb.setCity(rs.getString("CITY"));
          gb.setState(rs.getString("STATE_CODE"));
          gb.setZipcd1(rs.getString("ZIPCD5"));
          gb.setZipcd2(rs.getString("ZIPADD4"));
          gb.setCounty(rs.getString("countyname"));//sed_counties.description
          gb.setCountycode(rs.getInt("county_code"));
          gb.setSedcode(rs.getString("sed_code"));
      } 
      
      if(gb.getFccode()==80) {//for lgrmif get proj category
          ps =conn.prepareStatement("select id, descr from project_categories where id in "+
          " (select pc_id from grants where id=?)");
          ps.setLong(1, gb.getGrantid());
          rs = ps.executeQuery();
          while(rs.next()){
             gb.setProjcategory(rs.getString("descr"));
             gb.setTitle(rs.getString("descr"));
             gb.setProjcategoryId(rs.getInt("id"));
          }
      }
      else if(gb.getFccode()==86){//for construction get library system
            ps = conn.prepareStatement("select lsm.ID, lsm.INST_ID_HAS, initcap(popular_name) as popular_name from "+
            " LDSTATEAID.LIBRARY_SYSTEM_MAPPINGS lsm, sed_institutions where "+
            " lsm.inst_id_has=sed_institutions.inst_id and lsm.INST_ID=? and lsm.END_DATE is null");      
            ps.setLong(1, gb.getInstID());
            rs = ps.executeQuery();
            while(rs.next()){
                gb.setSystemInstId(rs.getLong("inst_id_has"));
                gb.setSystemName(rs.getString("popular_name"));
            }
      }
             
    }catch(Exception e){
      System.err.println("error getRecordBean() " + e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(conn);
      Close(ps);
    }
    return gb;
  }
        
    
    public String determineFederalId(long instid)
    {            
      String fedid="";
      try {
        conn = initializeConn();                          
        ps = conn.prepareStatement("select federal_id from sed_payee_infos where payee_id in " + 
        " (select payee_id from sed_institutions where inst_id=?)");    
        ps.setLong(1, instid);
        rs = ps.executeQuery();        
        while(rs.next())
        {          
          fedid= rs.getString("federal_id");
        }      
     
      }catch(Exception e){
        System.err.println("error determineFederalId() " + e.getMessage().toString());
      }
      finally{
        Close(rs);
        Close(conn);
        Close(ps);
      }
      return fedid;
    }

  /**
     * method used only by al/fl. calculates grand totals for each year of project.
     * @param grantid
     * @param isAdmin
     * @param asb
     * @param instId -11/7/12 needed to calc allocations for FY>2013, per LAreford
     * @return
     */
  public HashMap getProjectBudgetTotalsByFy(long grantid, boolean isAdmin, AppStatusBean asb, long instId)
  {
     BudgetDBHandler adb = new BudgetDBHandler();
     HashMap map = new HashMap();
     TotalsBean tb1 = new TotalsBean();  
     TotalsBean tb2 = new TotalsBean();
     TotalsBean tb3 = new TotalsBean();
    
     try{         
         //calculate the grand totals for amtRequested, ExpSubmitted
          tb1.setFycode(asb.getFycode());
          tb1.setTotAmtReq(adb.calcTotalAmtRequested(grantid, asb.getFycode()));
          tb1.setTotExpSub(adb.calcTotalExpSubmitted(grantid, asb.getFycode()));
          
          tb2.setFycode(asb.getFycode()+1);
          tb2.setTotAmtReq(adb.calcTotalAmtRequested(grantid, asb.getFycode()+1));
          tb2.setTotExpSub(adb.calcTotalExpSubmitted(grantid, asb.getFycode()+1));
                       
          if(asb.getFycode()>13){//starting in FY2013-14, 3 year grants
             tb3.setFycode(asb.getFycode()+2);
             tb3.setTotAmtReq(adb.calcTotalAmtRequested(grantid, asb.getFycode()+2));
             tb3.setTotExpSub(adb.calcTotalExpSubmitted(grantid, asb.getFycode()+2));
             
             //starting FY2013-14; need allocation for each year
             Vector allocs =adb.getYearlyAllocationForInst(asb.getFycode(), asb.getFccode(), instId);
             for(int i=0; i<allocs.size(); i++){
                 AllocationYearBean ab = (AllocationYearBean) allocs.get(i);
                 
                 if(ab.getFycode()==tb1.getFycode())
                    tb1.setAllocationAmt(ab.getInitialAlloc());
                 else if(ab.getFycode()==tb2.getFycode())
                    tb2.setAllocationAmt(ab.getInitialAlloc());
                else if(ab.getFycode()==tb3.getFycode())
                    tb3.setAllocationAmt(ab.getInitialAlloc());             
             }             
          }
         
          //if(isAdmin || asb.isInitialappr() || asb.isAppDenied() )
          if(isAdmin || asb.isShowscorecomm())
          {
            //calculate the grand totals for amt/exp approved           
            tb1.setTotAmtAppr(adb.calcTotalAmtApproved(grantid, asb.getFycode()));
            tb1.setTotExpAppr(adb.calcTotalExpApproved(grantid, asb.getFycode()));
                        
            tb2.setTotAmtAppr(adb.calcTotalAmtApproved(grantid, asb.getFycode()+1));  
            tb2.setTotExpAppr(adb.calcTotalExpApproved(grantid, asb.getFycode()+1));
            
            if(asb.getFycode()>13){//starting FY2013-14
                tb3.setTotAmtAppr(adb.calcTotalAmtApproved(grantid, asb.getFycode()+2));  
                tb3.setTotExpAppr(adb.calcTotalExpApproved(grantid, asb.getFycode()+2));
            }
          }          
          map.put("1", tb1);
          map.put("2", tb2);
          map.put("3", tb3);
          
     }catch(Exception e){
       System.out.println("error getProjectBudgetTotalsByFy "+e.getMessage().toString());
     }
     return map;
  }
     
  
  /**
   * This method will get totals by budget code for amt req/appr, exp sub/appr, and also 
   * grand totals for entire project. 4/22/09
   * @return 
   * @param asb
   * @param isAdmin
   * @param grantid
   */
  public TotalsBean getProjectBudgetTotals(long grantid, boolean isAdmin, AppStatusBean asb)
  {
    BudgetDBHandler adb = new BudgetDBHandler();
    TotalsBean tb = new TotalsBean();  
    boolean showAppr = false;
    
    try{        
        //if(isAdmin || asb.isInitialappr() || asb.isAppDenied() )
        if(isAdmin || asb.isShowscorecomm())
          showAppr = true;
          
        //calculate the totals by expense code for amtRequested, ExpSubmitted, InstCont, ProjTot
        tb = adb.calcAmtExpBenefits(grantid, tb, 0, showAppr);
        tb = adb.calcAmtExpOther(grantid, tb, 0, showAppr);
        tb = adb.calcAmtExpTravel(grantid, tb, 0, showAppr);
        tb = adb.calcAmtExpProfSupport(grantid, tb, 0, showAppr);
        tb = adb.calcAmtExpSuppEquip(grantid, tb, 0, showAppr);
        tb = adb.calcAmtExpPurchBoces(grantid, tb, 0, showAppr);        
        
        //calc the grand totals
        tb.calcGrandTotals();
        
        if(asb.getFccode()!=0) {
            switch(asb.getFccode()){
                case 5:
                    if(tb.getTotAmtReq() > tb.getDiLimit())
                        tb.setWarning(true);
                    break;
                case 6:
                    if(tb.getTotAmtReq() > tb.getSaLimit())
                        tb.setWarning(true);
                    break;
                case 20:
                    if(tb.getTotAmtReq() > asb.getLdacAppropAmt())
                        tb.setWarning(true);
                    break;
                case 80:
                    tb.setLgCooperative(asb.isArchcooperative());//10/3/13 cooperative now demo
                    tb.setLgSharedServ(asb.isArchshared());
                    tb.setPlanningDemo(asb.isPlanningDemo());
                    tb.setImplementDemo(asb.isImplementDemo());
                    if(asb.isArchcooperative())
                    {
                        if(asb.isPlanningDemo()){
                          if(tb.getTotAmtReq() > tb.getLgPlanLimit())
                              tb.setWarning(true);
                        }
                        else if(asb.isImplementDemo()){
                            if(tb.getTotAmtReq() > tb.getLgImplementLimit())
                                tb.setWarning(true);
                        }                        
                    }
                    else if(asb.isArchshared()){
                        if (tb.getTotAmtReq() > tb.getLgSharedLimit())
                          tb.setWarning(true);
                    }
                    else{//must be individual project
                        if(tb.getTotAmtReq() > tb.getLgIndivLimit())
                            tb.setWarning(true);
                    }
                        
                    if(tb.getTotAmtAppr() > tb.getTotAmtReq())
                        tb.setAmtover(true);//lgfmif admin
                    break;
            }
        }       
    } catch(Exception e){System.out.println("error getProjectBudgetTotals "+e.getMessage().toString());}
    return tb;
  }
     
  
  public BudgetCollectionBean getBudgetBeanRecords(long grantid, int fycode, boolean isAdmin,
                              AppStatusBean asb, boolean splitTypeCodes, int tab)
  {
    BudgetDBHandler adb = new BudgetDBHandler();
    BudgetCollectionBean bb= new BudgetCollectionBean();
    
    Vector perExp=null;
    Vector benExp=null;
    Vector contrExp=null;
    Vector supplyExp=null;
    Vector otherExp=null;
    Vector travelExp=null;
    Vector supportExp = null;
    Vector equipExp=null;
    Vector bocesExp=null;
    
    try{
        boolean showApprAmt = false;        
        if(asb!=null)
        {
          //if(isAdmin || asb.isInitialappr() || asb.isAppDenied() )
          if(isAdmin || asb.isShowscorecomm())
            showApprAmt = true;//show the approval amts based on showrevscore flag
          
            if(splitTypeCodes)
            {
              if(tab==0){
              //these will get ALL budget records by category (for lgrmif/cn print view)
              perExp = adb.getPersonalInfo(grantid, showApprAmt, fycode, 3);//proff staff
              supportExp = adb.getPersonalInfo(grantid, showApprAmt, fycode, 4);//supportstaff              
              benExp = adb.getBenefitInfo(grantid, showApprAmt, fycode);
              contrExp = adb.getContractedInfo(grantid, showApprAmt, fycode, 5);//contracted
              bocesExp = adb.getContractedInfo(grantid, showApprAmt, fycode, 6);//boces              
              supplyExp = adb.getSupplyInfo(grantid, showApprAmt, fycode, 1);//supplies
              equipExp = adb.getSupplyInfo(grantid, showApprAmt, fycode, 2);//equip
              otherExp = adb.getExpenseInfo(grantid, showApprAmt, fycode);  
              travelExp =adb.getTravelInfo(grantid, showApprAmt, fycode);      
              }
              else{
                if(asb.getFccode()==80){
                    //for lgrmif rev approve budget, lgrmif admin edit/appr budget
                    switch(tab){
                      case 1:  perExp = adb.getPersonalInfo(grantid, showApprAmt, fycode, 3);//proff staff
                               break;
                      case 2:  perExp = adb.getPersonalInfo(grantid, showApprAmt, fycode, 4);//supportstaff 
                               break;
                      case 3:  supplyExp = adb.getSupplyInfo(grantid, showApprAmt, fycode, 2);//equip
                               break;
                      case 4:  otherExp = adb.getExpenseInfo(grantid, showApprAmt, fycode); 
                               break;
                      case 5:  contrExp = adb.getContractedInfo(grantid, showApprAmt, fycode, 5);//contracted
                               break;
                      case 6:  contrExp = adb.getContractedInfo(grantid, showApprAmt, fycode, 6);//boces 
                               break;
                      case 7:  supplyExp = adb.getSupplyInfo(grantid, showApprAmt, fycode, 1);//supplies
                               break;
                      case 8:  travelExp =adb.getTravelInfo(grantid, showApprAmt, fycode); 
                               break;
                      case 9:  benExp = adb.getBenefitInfo(grantid, showApprAmt, fycode);
                               break;                  
                    }
                }
                else if(asb.getFccode()==40 || asb.getFccode()==42 || asb.getFccode()==86){
                    //for lit & cn admin appr budget; modified 9/30/11 to add construction
                     switch(tab){
                       case 1:  perExp = adb.getPersonalInfo(grantid, showApprAmt, fycode, 3);//proff staff
                                break;
                       case 2:  perExp = adb.getPersonalInfo(grantid, showApprAmt, fycode, 4);//supportstaff 
                                break;
                       case 3:  benExp = adb.getBenefitInfo(grantid, showApprAmt, fycode);
                                break; 
                       case 4:  contrExp = adb.getContractedInfo(grantid, showApprAmt, fycode, 5);//contracted
                                break;
                       case 5:  supplyExp = adb.getSupplyInfo(grantid, showApprAmt, fycode, 1);//supplies
                                break;
                       case 6:  supplyExp = adb.getSupplyInfo(grantid, showApprAmt, fycode, 2);//equip
                                break;
                       case 7:  travelExp =adb.getTravelInfo(grantid, showApprAmt, fycode); 
                                break;                                        
                     }
                }
              }
            }
            else//do not split by type code
            {
              //these will get ALL budget records by table (not slit by typecode supp vs exp)              
              if(tab==0){
                  perExp = adb.getPersonalInfo(grantid, showApprAmt, fycode, 0);
                  benExp = adb.getBenefitInfo(grantid, showApprAmt, fycode);
                  contrExp = adb.getContractedInfo(grantid, showApprAmt, fycode, 0);
                  supplyExp = adb.getSupplyInfo(grantid, showApprAmt, fycode, 0);
                  otherExp = adb.getExpenseInfo(grantid, showApprAmt, fycode);  
                  travelExp =adb.getTravelInfo(grantid, showApprAmt, fycode);
              }
              else{//get budget records by table for given tab only
                  switch(tab){
                    case 1:
                      perExp = adb.getPersonalInfo(grantid, showApprAmt, fycode, 0);
                      break;
                    case 2:
                      benExp = adb.getBenefitInfo(grantid, showApprAmt, fycode);
                      break;
                    case 3:
                      contrExp = adb.getContractedInfo(grantid, showApprAmt, fycode, 0);
                      break;
                    case 4:
                      supplyExp = adb.getSupplyInfo(grantid, showApprAmt, fycode, 0);
                      break;
                    case 5:
                      otherExp = adb.getExpenseInfo(grantid, showApprAmt, fycode);  
                      break;
                    case 6:
                      travelExp =adb.getTravelInfo(grantid, showApprAmt, fycode);
                      break;                 
                  }
              }
            }
          }            
          
          //set vectors to bean
          bb.setAllPersRecords(perExp);          
          bb.setAllBenRecords(benExp);
          bb.setAllContractRecords(contrExp);
          bb.setAllSupplyRecords(supplyExp);
          bb.setAllExpRecords(otherExp);
          bb.setAllTravelRecords(travelExp);
          bb.setAllSupportRecords(supportExp);
          bb.setAllEquipRecords(equipExp);
          bb.setAllContBocesRecords(bocesExp);
    } catch(Exception e){System.out.println("error getBudgetBeanRecords "+e.getMessage().toString());}
    
    return bb;     
  }  
 
  
   public int parseCurrencyAmtNoDecimal(String amount)
   {
     int ans =0;
     
     try{
         char[] amtString = amount.toCharArray();//convert string to array of char
         Vector newAmtString = new Vector();//vector to hold new amount - just integers, no chars
         
         //loop on all items in the old string array
         for(int i=0; i<amtString.length; i++) 
         {
             //check if char is a number (or a negative sign) - if yes then add to new vector
             if( Character.isDigit(amtString[i]) || amtString[i]=='-' )//   6/24/10
             {  
                 //cannot add char to vector - must wrap in a character object
                 newAmtString.add(new Character(amtString[i]) ); //it works!
             }
             else if(amtString[i]=='.')//cannot take floating pt nums, only integer part
             {
               break;
             }
         }
         
         String tmpAmtString = "";
         //now convert all the numbers in the vector back to a string
         for(int i=0; i<newAmtString.size();i++){
           tmpAmtString+= newAmtString.get(i);
         }
       
         ans = Integer.parseInt(tmpAmtString);
     }catch(Exception e){System.out.println("error parseCurrencyAmtNoDecimal "+e.getMessage().toString());}
     return ans;
   }
   
   
    public long parseLongAmtNoDecimal(String amount)
    {
      long ans =0;
      
      try{
          char[] amtString = amount.toCharArray();//convert string to array of char
          Vector newAmtString = new Vector();//vector to hold new amount - just integers, no chars
          
          //loop on all items in the old string array
          for(int i=0; i<amtString.length; i++) 
          {
              //check if char is a number (or a negative sign) - if yes then add to new vector
              if( Character.isDigit(amtString[i]) || amtString[i]=='-' )//   6/24/10
              {  
                  //cannot add char to vector - must wrap in a character object
                  newAmtString.add(new Character(amtString[i]) ); //it works!
              }
              else if(amtString[i]=='.')//cannot take floating pt nums, only integer part
              {
                break;
              }
          }
          
          String tmpAmtString = "";
          //now convert all the numbers in the vector back to a string
          for(int i=0; i<newAmtString.size();i++){
            tmpAmtString+= newAmtString.get(i);
          }
        
          ans = Long.parseLong(tmpAmtString);
      }catch(Exception e){System.out.println("error parseLongAmtNoDecimal "+e.getMessage().toString());}
      return ans;
    }
    
    
    
    public void saveMwbeParticipation(AppStatusBean asb, long grantid, UserBean userb){
      
      try{                         
       conn = initializeConn(); 
       
         ps = conn.prepareStatement("update GRANTS set DATE_MODIFIED=SYSDATE, "+
         " MODIFIED_BY = ?, amount_req=? where ID = ?");                
        
         ps.setString(1, userb.getUserid());        
         ps.setInt(2, asb.getMwbeParticipation());
         ps.setLong(3, grantid);        
         int outcome = ps.executeUpdate();
               
      }catch(Exception e){
        System.err.println("error saveMwbeParticipation() " + e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(conn);
      }        
    }
    
    
    
  public String getMwbeParticipation(long grantid){
    String mwbe=null;
    try{                         
     conn = initializeConn(); 
     
       ps = conn.prepareStatement("select amount_req from GRANTS where ID = ?");                
       ps.setLong(1, grantid);        
       rs = ps.executeQuery();
        
        while(rs.next()){
          mwbe = rs.getString("amount_req");//should be 1,2,3,etc depending on mwbe compliance
        }
             
    }catch(Exception e){
      System.err.println("error saveMwbeParticipation() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }  
    return mwbe;
  }
  
  

  public int saveAdminReview(AppStatusBean asb, long grantid, UserBean userb, int fccode)
  {
    int outcome =0;      
   try{                         
    conn = initializeConn(); 
    
    if(fccode ==80 || fccode==5 || fccode==6 || fccode==7)
    {                 
        ps = conn.prepareStatement("update GRANTS set COVERSHEET_YN=?, PROJ_DESC_YN=?, "+
        " AMT_REQUESTED_YN=?, EXP_SUBMITTED_YN=?, FINAL_NARRATIVE_YN=?, INST_AUTH_YN=?, "+
        " FINAL_SIGNOFF_YN=?, FS20_YN=?, AWAITING_APPR=?, CONTRACT_NUM=?, DATE_MODIFIED=SYSDATE, "+
        " MODIFIED_BY = ?, FS10F_YN=?, EDUCATION_APP=?, exp_approved_yn=?, DURATION=?, amount_req=? where ID = ?");                
        ps.setBoolean(1, asb.isCoversheetyn());
        ps.setBoolean(2, asb.isProjdescyn());
        ps.setBoolean(3, asb.isAmtreqyn());
        ps.setBoolean(4, asb.isExpsubyn());
        ps.setBoolean(5, asb.isFinalnarrativeyn());
        ps.setBoolean(6, asb.isInstauthyn());
        ps.setBoolean(7, asb.isFinalsignoffyn());
        ps.setBoolean(8, asb.isFs20yn());
        ps.setBoolean(9, asb.isAwaitingappr());
        ps.setString(10, asb.getContractnum());
        ps.setString(11, userb.getUserid());
        ps.setBoolean(12, asb.isFs10fyn());
        ps.setBoolean(13, asb.isEducationapp());
        ps.setBoolean(14, asb.isVqimyn());//for lgrmif admin
        ps.setBoolean(15, asb.isFs10aComp());
        ps.setInt(16, asb.getMwbeParticipation());
        ps.setLong(17, grantid);        
        outcome = ps.executeUpdate();
    }
    else if(fccode==40 || fccode==42)
    {           
        ps = conn.prepareStatement("update GRANTS set COVERSHEET_YN=?, PROJ_DESC_YN=?, "+
        " AMT_REQUESTED_YN=?, EXP_SUBMITTED_YN=?, FINAL_NARRATIVE_YN=?, INST_AUTH_YN=?, "+
        " FINAL_SIGNOFF_YN=?, FS20_YN=?, AWAITING_APPR=?, CONTRACT_NUM=?, DATE_MODIFIED=SYSDATE, "+
        " MODIFIED_BY = ?, FS10F_YN=?, MICROFORM_yn=?, RELIGIOUS_AFill=?, EDUCATION_APP=?, DURATION=? where ID = ?");
          ps.setBoolean(1, asb.isCoversheetyn());
          ps.setBoolean(2, asb.isProjdescyn());
          ps.setBoolean(3, asb.isAmtreqyn());
          ps.setBoolean(4, asb.isExpsubyn());
          ps.setBoolean(5, asb.isFinalnarrativeyn());
          ps.setBoolean(6, asb.isInstauthyn());
          ps.setBoolean(7, asb.isFinalsignoffyn());
          ps.setBoolean(8, asb.isFs20yn());
          ps.setBoolean(9, asb.isAwaitingappr());
          ps.setString(10, asb.getContractnum());
          ps.setString(11, userb.getUserid());
          ps.setBoolean(12, asb.isFs10fyn());
          ps.setBoolean(13, asb.isInterimrptyn());
          ps.setBoolean(14, asb.isInterimauthyn());
          ps.setBoolean(15, asb.isStatisticsyn());
          ps.setBoolean(16, asb.isFs10aComp());
          ps.setLong(17, grantid);          
          outcome = ps.executeUpdate();
    }
    else if(fccode ==20)//for stateaid- no mwbe or education apps
     {                 
         ps = conn.prepareStatement("update GRANTS set COVERSHEET_YN=?, PROJ_DESC_YN=?, "+
         " AMT_REQUESTED_YN=?, EXP_SUBMITTED_YN=?, FINAL_NARRATIVE_YN=?, INST_AUTH_YN=?, "+
         " FINAL_SIGNOFF_YN=?, FS20_YN=?, AWAITING_APPR=?, CONTRACT_NUM=?, DATE_MODIFIED=SYSDATE, "+
         " MODIFIED_BY = ?, FS10F_YN=?, DURATION=? where ID = ?");                
         ps.setBoolean(1, asb.isCoversheetyn());
         ps.setBoolean(2, asb.isProjdescyn());
         ps.setBoolean(3, asb.isAmtreqyn());
         ps.setBoolean(4, asb.isExpsubyn());
         ps.setBoolean(5, asb.isFinalnarrativeyn());
         ps.setBoolean(6, asb.isInstauthyn());
         ps.setBoolean(7, asb.isFinalsignoffyn());
         ps.setBoolean(8, asb.isFs20yn());
         ps.setBoolean(9, asb.isAwaitingappr());
         ps.setString(10, asb.getContractnum());
         ps.setString(11, userb.getUserid());
         ps.setBoolean(12, asb.isFs10fyn());        
         ps.setBoolean(13, asb.isFs10aComp());
         ps.setLong(14, grantid);        
         outcome = ps.executeUpdate();
     }
    
    Close(ps);
    Close(conn);
    
    //check if the app needs to be unlocked for correction
    if(asb.isLockapp())
      handleUnlockApp(grantid, userb, asb);
         
   }catch(Exception e){
     System.err.println("error saveAdminReview() " + e.getMessage().toString());
   }
   finally{
     Close(ps);
     Close(conn);
   }  
    return outcome;
  }
  
    
  public AppStatusBean getGrantStatusYn(long grantid)  
  {
    AppStatusBean asb = new AppStatusBean();
    try{    
      
      conn = initializeConn();
      ps = conn.prepareStatement("select * from GRANTS where ID=?");
      ps.setLong(1, grantid);      
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        asb.setCoversheetyn(rs.getBoolean("COVERSHEET_YN"));
        asb.setCoversheetComp(rs.getBoolean("COVERSHEET_COMP"));
        asb.setProjdescyn(rs.getBoolean("PROJ_DESC_YN"));
        asb.setProjdescComp(rs.getBoolean("DESCRIPTION_COMP"));
        asb.setAmtreqyn(rs.getBoolean("AMT_REQUESTED_YN"));//admin amt requested
        asb.setShowscorecomm(rs.getBoolean("AMT_APPROVED_YN"));//USED BY all programs TO SHOW/HIDE REV SCOREs/amt appr TO APCNT
        asb.setBudgetComp(rs.getBoolean("BUDGET_COMP"));//used on apcnt checklist
        asb.setExpsubyn(rs.getBoolean("EXP_SUBMITTED_YN"));//admin final expenses
        asb.setExpappryn(rs.getBoolean("EXP_APPROVED_YN"));//expappryn not used
        asb.setVqimyn(rs.getBoolean("exp_approved_yn"));//lgrmif admin vq/im forms
        asb.setFs10ayn(rs.getBoolean("FS10A_YN"));        
        asb.setFinalbudgetComp(rs.getBoolean("FINAL_BUDGET_COMP"));//used on apcnt checklist
        asb.setFinalnarrativeyn(rs.getBoolean("FINAL_NARRATIVE_YN"));
        asb.setFinalnarrativeComp(rs.getBoolean("FINAL_NARR_COMP"));//cn uses this to lock/unlock final rpts
        asb.setInstauthyn(rs.getBoolean("INST_AUTH_YN"));
        asb.setAuthComp(rs.getBoolean("AUTH_COMP"));
        asb.setFinalsignoffyn(rs.getBoolean("FINAL_SIGNOFF_YN"));
        asb.setSignoffComp(rs.getBoolean("SIGNOFF_COMP"));
        asb.setFs20yn(rs.getBoolean("FS20_YN"));
        asb.setFs20Comp(rs.getBoolean("FS20_COMP")); 
        asb.setLockapp(rs.getBoolean("LOCK_APP"));
        asb.setAwaitingappr(rs.getBoolean("AWAITING_APPR"));
        asb.setFs10fyn(rs.getBoolean("FS10F_YN"));
        asb.setFs10fComp(rs.getBoolean("FS10F_YN"));
        asb.setFycode(rs.getInt("FY_CODE"));
        asb.setFccode(rs.getInt("FC_CODE"));
        asb.setGrantid(rs.getLong("ID"));
        asb.setContractnum(rs.getString("contract_num"));
        asb.setEducationapp(rs.getBoolean("education_app"));
        asb.setFs10aComp(rs.getBoolean("duration"));//used for fs10a admin lock/unlock
        //for cp DG and lgrmif and construction only 
        asb.setMwbeParticipation(rs.getInt("amount_req"));//amount_req = mwbe for cp dg; change for tref/pref
        //per DenisMeadows 10/15/14 - if no mwbe -> then default to "1" full participation
        if(asb.getMwbeParticipation()==0)
            asb.setMwbeParticipation(1);
          
        //for stateaid only; the final approp amt from ldac is stored in this field
        asb.setLdacAppropAmt(rs.getLong("amount_req"));          
          
        //for FL and AL admin use only
        asb.setInterimrptyn(rs.getBoolean("microform_yn"));
        asb.setInterimauthyn(rs.getBoolean("religious_afill"));
        asb.setStatisticsyn(rs.getBoolean("education_app"));//for al/fl admin and lgrmif apcnt
        //for LGRMIF and construction apcnt
        asb.setPayeeyn(rs.getBoolean("microform_yn"));//construct=lease agreement
        asb.setAppendixyn(rs.getBoolean("religious_afill"));//construct=OFP approval
        //for lgrmif starting FY14-15
        asb.setPlanningDemo(rs.getBoolean("shpo_comp"));
        asb.setImplementDemo(rs.getBoolean("seaf_comp"));
        //for Construction 
        asb.setAttachcomp(rs.getBoolean("ATTACH_COMP"));
        asb.setShpocomp(rs.getBoolean("SHPO_COMP")); 
        asb.setSeafcomp(rs.getBoolean("SEAF_COMP")); 
        asb.setPayeecomp(rs.getBoolean("PAYEE_COMP"));
        asb.setPhotocomp(rs.getBoolean("PHOTO_COMP"));
        asb.setAssurancesyn(rs.getBoolean("education_app"));//cn assurances
        asb.setAdditfundingyn(rs.getBoolean("doris_flag"));//cn additional funding
        asb.setGroundDisturb(rs.getBoolean("paid_in_full_yn"));//cn 6/3/15; new question on coversheet
      }
                      
        if(asb.getFccode()==80) {            
            ps = conn.prepareStatement("select cooperative_yn, shared_serv_yn from govt_infos where gra_id=?");
            ps.setLong(1, grantid);      
            rs = ps.executeQuery();            
            while(rs.next()){
                asb.setArchcooperative(rs.getBoolean("cooperative_yn"));
                asb.setArchshared(rs.getBoolean("shared_serv_yn"));
            }            
        }
          
    }catch(Exception e){
      System.err.println("error getGrantStatusYn() " + e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }   
    return asb;
  }
  
  
  /**
     * Get all dates for this fund/FY.  determine if today's date allows for starting/submitting
     * initial/final/interim/fs10a application.
     * @param asb
     * @return
     */
  public AppStatusBean verifyDateAcceptable(AppStatusBean asb)
  {
    Timestamp dbDate=null;
    Timestamp startdate=null;
    Timestamp duedate=null;
    Timestamp finalduedate=null;
    Timestamp interimamendduedate=null;
    try{
      conn = initializeConn();
      ps = conn.prepareStatement("select sysdate from dual");
      rs = ps.executeQuery();
      while(rs.next()){
        dbDate = rs.getTimestamp("sysdate");
      }
      //System.out.println("sys date is "+dbDate);
      //Format formatter = new SimpleDateFormat("MM/dd/yyyy H:mm");
      //String todayDate = formatter.format(dbDate);
            
      Close(rs);
      Close(ps);
       
      ps = conn.prepareStatement("select * from APP_DATES where FY_CODE=? and FC_CODE=?");
      ps.setInt(1, asb.getFycode());
      ps.setInt(2, asb.getFccode());
      rs = ps.executeQuery();      
      while(rs.next())
      {
        asb.setAvailableDate(rs.getTimestamp("start_date"));
        asb.setDueDate(rs.getTimestamp("DUE_DATE"));   
        startdate = rs.getTimestamp("start_date");
        duedate = rs.getTimestamp("due_date");
        asb.setFinaldueDate(rs.getTimestamp("FINAL_RPT_DATE"));
        finalduedate = rs.getTimestamp("final_rpt_date");
        asb.setInterimdueDate(rs.getDate("INTERIM_RPT_DATE"));
        interimamendduedate = rs.getTimestamp("interim_rpt_date");
      }
      //System.out.println("due date is "+duedate);
      //System.out.println("start date is "+startdate);
      Close(rs);
      Close(ps);
      
      
      boolean dateOk = false;      
      if(startdate!=null && duedate!=null){//else nullptrexception when no records found
          if(dbDate.compareTo(startdate) >=0) {//dbDate is =or after the startdate
            
            if(dbDate.compareTo(duedate) <=0)//dbdate is = or before due date
                dateOk =true;
          }
      }
      asb.setDateAcceptable(dateOk);   
      
      
      boolean finalDateOk = false;      
      if(finalduedate!=null){
          //currentdate is = or before final due date-used to lockout lgrmif after finalduedate
          if(dbDate.compareTo(finalduedate) <=0)
            finalDateOk = true;
      }
      asb.setFinaldateAcceptable(finalDateOk);
        
      
      //new 5/9/14; for construction; can only submit final between final avail/due dates
      //note:  for cn:  "final rpt avail date" is stored in field interim_rpt_date
      if(asb.getFccode()==86){
          
          dateOk = false;//reset
          if(interimamendduedate!=null && finalduedate!=null){
            
              if(dbDate.compareTo(interimamendduedate) >=0) {//dbDate is =or after the finalrpt startdate
                  if(dbDate.compareTo(finalduedate) <=0)//dbdate is = or before due date
                      dateOk=true;
              }        
          }
          //System.out.println("cn date ok "+ dateOk);
          asb.setFinaldateAcceptable(dateOk);
      }
      
      
      boolean amendDateOk = false;
      if(interimamendduedate!=null){
          //current is is =< amend due date - used to lockout lgrmif amendments
          if(dbDate.compareTo(interimamendduedate) <=0)
            amendDateOk = true;
      }
      asb.setAmenddateAcceptable(amendDateOk);
      
      /*ps = conn.prepareStatement("select * from APP_DATES where to_date(?, 'MM/dd/yyyy') "+
      "  >= START_DATE and to_date(?, 'MM/dd/yyyy') <= DUE_DATE and FC_CODE=? and FY_CODE=? ");
      ps.setString(1, dbDate );
      ps.setString(2, dbDate);
      ps.setInt(3, asb.getFccode());
      ps.setInt(4, asb.getFycode());
      rs = ps.executeQuery();
      while(rs.next())
      {
        dateOk=true;
        System.out.println("date is ok");
      }
      asb.setDateAcceptable(dateOk);*/
      
    }catch(Exception e){
      System.err.println("error verifyDateAcceptable() " + e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }   
    return asb;
  }


  public AppStatusBean getApplicationStatus(long grantid)
  {      
    AppStatusBean asb=null;
    
    try{        
      asb = getGrantStatusYn(grantid);    
        
      //find out if the application has been submitted yet
      SubmissionDBbean sdb = new SubmissionDBbean();
      Vector allSubmits = sdb.getSubmissions(grantid);
      for(int i=0; i<allSubmits.size(); i++)
      {
        SubmitBean sb = (SubmitBean)allSubmits.get(i);
        if(sb.getVersionSubmitted().equalsIgnoreCase("Initial"))
          asb.setInitialsubmitted(true);
        else if(sb.getVersionSubmitted().equalsIgnoreCase("Final"))
          asb.setFinalsubmitted(true);   
        else if(sb.getVersionSubmitted().equalsIgnoreCase("Amendment"))
          asb.setAmendsubmitted(true);
        else if(sb.getVersionSubmitted().equalsIgnoreCase("Interim"))
            asb.setInterimsubmitted(true);
        else if(sb.getVersionSubmitted().equalsIgnoreCase("Final Year2"))
            asb.setFinalyr2submitted(true);
        else if(sb.getVersionSubmitted().equalsIgnoreCase("Final Year3"))
            asb.setFinalyr3submitted(true);
        else if(sb.getVersionSubmitted().equalsIgnoreCase("DASNY"))
            asb.setDasnysubmit(true);
        else if(sb.getVersionSubmitted().equalsIgnoreCase("MWBE"))
            asb.setMwbesubmit(true);        
      }
            
      //check if current date/due date allows app submission      
      asb = verifyDateAcceptable(asb);
            
      //find out if application was approved or denied
      ApprovalsDBbean adb = new ApprovalsDBbean();
      asb.setInitialappr( adb.isInitialAppApproved(grantid));
      asb.setFinalappr( adb.isFinalAppApproved(grantid, false, 0));
      asb.setAppDenied( adb.isAppDenied(grantid));      
      asb.setAppDeclined( adb.isAppDeclined(grantid));
      asb.setReadyYear3( adb.isYr2ApprovedForYear3Start(grantid));
      asb.setAmendApprYr1(adb.isAmendApprYr1(grantid));
      asb.setAmendApprYr2(adb.isAmendApprYr2(grantid));
      asb.setAmendApprYr3(adb.isAmendApprYr3(grantid));
        
      //for construction only
      if(asb.getFccode()==86){
        asb.setDasnyapproved(adb.isDasnyApproval(grantid));
        //asb.setBondcomplete(adb.isDasnyBondComplete(grantid));//rmvd 1/31/12 per dasny
      }
                  
      //if declined; then get details
      if(asb.isAppDeclined())
        asb = adb.getDeclinedAwardInfo(asb);
      if(asb.getFccode()==40 || asb.getFccode()==42){
        asb.setInterimappr(adb.isInterimAppApproved(grantid));
        asb.setFinalappryear2(adb.isFinalAppApproved(grantid, true, 2));
        asb.setFinalappryear3(adb.isFinalAppApproved(grantid, true, 3));
      }
      
           
      //determine whether the application version may be submitted or not
      if( (asb.isInitialsubmitted() && asb.isLockapp()) || 
        (asb.isInitialsubmitted() && asb.isInitialappr()) ||
        (asb.isAppDenied()) )
        {
          asb.setAllowSubmitInitial(false);
        }
        else
          asb.setAllowSubmitInitial(true);
                

      if( (asb.isFinalsubmitted() && asb.isLockapp()) ||
        ( asb.isFinalsubmitted() && asb.isFinalappr()) ||
        ( asb.isAppDenied())  ||
        //( asb.isInitialsubmitted() && !asb.isDateAcceptable()  )   )
        ( !asb.isInitialsubmitted() && !asb.isDateAcceptable()  )   )//changed 3/10/09
        {
          asb.setAllowSubmitFinal(false);
        }
        else
          asb.setAllowSubmitFinal(true);  
        
     //for literacy year 2
      if(  (asb.isFinalyr2submitted() && asb.isLockapp())  ||
           (asb.isFinalyr2submitted() && asb.isFinalappryear2())  ||
           ( asb.isAppDenied())   ||
           ( !asb.isInitialsubmitted() && !asb.isDateAcceptable()))
        {
               asb.setAllowSubmitFinal2(false);
        }
        else
            asb.setAllowSubmitFinal2(true);
                               
     //for literacy year 3   
     if(  (asb.isFinalyr3submitted() && asb.isLockapp())  ||
          (asb.isFinalyr3submitted() && asb.isFinalappryear3())  ||
          ( asb.isAppDenied())   ||
          ( !asb.isInitialsubmitted() && !asb.isDateAcceptable()))
       {
              asb.setAllowSubmitFinal3(false);
       }
       else
           asb.setAllowSubmitFinal3(true);
    
        
      
      //all fields must be disabled once app is submitted and waiting for
      //administrative action (ie. either an approval or correction requested)
      if(asb.isInitialsubmitted()&& asb.isLockapp())
        asb.setPendingReview(true);
      else
        asb.setPendingReview(false);
      
      if(asb.isFinalsubmitted()&& asb.isLockapp())
        asb.setPendingFinalRev(true);
      else
        asb.setPendingFinalRev(false);
   
   }catch(Exception e){
      System.err.println("error getApplicationStatus() " + e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }   
    return asb;
  }


    public void unlockApp(long grantid, UserBean userb) {
        int outcome = 0;
        //when apcnt submitted - all xComp were set to true.   now anything that the admin has not reviewed
        //and accepted will have the xComp set to false so that the apcnt can get back in and update it

        try {
            conn = initializeConn();
            ps =
    conn.prepareStatement("update GRANTS set COVERSHEET_YN=0, PROJ_DESC_YN=0, COVERSHEET_COMP =0, DESCRIPTION_COMP =0, " + 
                          " BUDGET_COMP=0, AUTH_COMP=0, FS20_COMP=0,  " +
                       " LOCK_APP=0, DATE_MODIFIED=SYSDATE, MODIFIED_BY=? where ID=?");

            ps.setString(1, userb.getUserid());
            ps.setLong(2, grantid);
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("error unlockApp() " + e.getMessage().toString());
        } finally {
            Close(conn);
            Close(ps);
        }
    }
    
    
    
    
    
    public void unlockFinalYear1(long grantid, UserBean userb) {
        int outcome = 0;
        //when apcnt submitted - all xComp were set to true.   now anything that the admin has not reviewed
        //and accepted will have the xComp set to false so that the apcnt can get back in and update it

        try {
            conn = initializeConn();
            ps =
    conn.prepareStatement("update GRANTS set FINAL_NARR_COMP=0, FINAL_BUDGET_COMP=0, "+
    				" SIGNOFF_COMP=0, LOCK_APP=0, DATE_MODIFIED=SYSDATE, MODIFIED_BY=? where ID=?");

            ps.setString(1, userb.getUserid());
            ps.setLong(2, grantid);
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("error unlockFinalYear1() " + e.getMessage().toString());
        } finally {
            Close(conn);
            Close(ps);
        }
    }
    
    
    
    
    public void unlockAmendment(long grantid, UserBean userb) {
        int outcome = 0;
        
        try {
            conn = initializeConn();
            ps =   conn.prepareStatement("update GRANTS set FS10A_YN=0, "+
            		" DATE_MODIFIED=SYSDATE, MODIFIED_BY=? where ID=?");

            ps.setString(1, userb.getUserid());
            ps.setLong(2, grantid);
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("error unlockAmendment() " + e.getMessage().toString());
        } finally {
            Close(conn);
            Close(ps);
        }
    }
    
    
    
  public int handleUnlockApp(long grantid, UserBean userb, AppStatusBean asb)
  {
    int outcome = 0;
    //when apcnt submitted - all xComp were set to true.   now anything that the admin has not reviewed
    //and accepted will have the xComp set to false so that the apcnt can get back in and update it
       
    try{
      conn = initializeConn();
      ps = conn.prepareStatement("update GRANTS set COVERSHEET_COMP =?, DESCRIPTION_COMP =?, "+
    " BUDGET_COMP=?, AUTH_COMP=?, FS20_COMP=?, FINAL_NARR_COMP=?, FINAL_BUDGET_COMP=?, "+
    " SIGNOFF_COMP=?, LOCK_APP=0, DATE_MODIFIED=SYSDATE, MODIFIED_BY=?, DURATION=? where ID=?");
              
      ps.setBoolean(1, asb.isCoversheetyn());
      ps.setBoolean(2, asb.isProjdescyn());
      ps.setBoolean(3, asb.isAmtreqyn());
      ps.setBoolean(4, asb.isInstauthyn());
      ps.setBoolean(5, asb.isFs20yn());
      ps.setBoolean(6, asb.isFinalnarrativeyn());
      ps.setBoolean(7, asb.isExpsubyn());
      ps.setBoolean(8, asb.isFinalsignoffyn());
      ps.setString(9, userb.getUserid());
      ps.setBoolean(10, asb.isFs10aComp());//fs10aComp = DURATION in ldgrants.grants table
      ps.setLong(11, grantid);      
     outcome = ps.executeUpdate();
    }catch(Exception e){
      System.err.println("error handleUnlockApp() "+ e.getMessage().toString());      
    }
    finally{
      Close(conn);
      Close(ps);
    }  
    return outcome;
  }
  
  
  
  public int revertLitBudgetToInitial(long grantid, UserBean userb)
  {
    int outcome = 0;
    //allow literacy applicant back into initial budget instead of final   
    try{
      conn = initializeConn();
      ps = conn.prepareStatement("update GRANTS set AMT_REQUESTED_YN=0,  "+
      "  BUDGET_COMP=0, LOCK_APP=0, DATE_MODIFIED=SYSDATE, MODIFIED_BY=? where ID=?");
                   
      ps.setString(1, userb.getUserid());
      ps.setLong(2, grantid);      
      outcome = ps.executeUpdate();
        
       // System.out.println(outcome);
    }catch(Exception e){
      System.err.println("error revertLitBudgetToInitial() "+ e.getMessage().toString());      
    }
    finally{
      Close(conn);
      Close(ps);
    }  
    return outcome;
  }
  
  

  /**
     * for construction only - used to lock/unlock app for members by admin
     * @param grantid
     * @param userb
     * @param asb
     * @return
     */
    public int unLockConstructAppForMember(long grantid, UserBean userb, AppStatusBean asb)
    {
      int outcome = 0;
      //when apcnt submitted - all xComp were set to true.   now anything that the admin has not reviewed
      //and accepted will have the xComp set to false so that the apcnt can get back in and update it
         
      try{
        conn = initializeConn();
        ps = conn.prepareStatement("update GRANTS set COVERSHEET_COMP =?, DESCRIPTION_COMP =?, "+
                " BUDGET_COMP=?, AUTH_COMP=?, FS20_COMP=?, FINAL_NARR_COMP=?, FINAL_BUDGET_COMP=?, "+
                " SIGNOFF_COMP=?, LOCK_APP=?, DATE_MODIFIED=SYSDATE, MODIFIED_BY=? where ID=?");
                
        ps.setBoolean(1, asb.isCoversheetyn());
        ps.setBoolean(2, asb.isProjdescyn());
        ps.setBoolean(3, asb.isAmtreqyn());
        ps.setBoolean(4, asb.isInstauthyn());
        ps.setBoolean(5, asb.isFs20yn());
        ps.setBoolean(6, asb.isFinalnarrativeyn());
        ps.setBoolean(7, asb.isExpsubyn());
        ps.setBoolean(8, asb.isFinalsignoffyn());
        ps.setBoolean(9, asb.isLockapp());
        ps.setString(10, userb.getUserid());
        ps.setLong(11, grantid);      
       outcome = ps.executeUpdate();
      }catch(Exception e){
        System.err.println("error unLockConstructAppForMember() "+ e.getMessage().toString());      
      }
      finally{
        Close(conn);
        Close(ps);
      }  
      return outcome;
    }


  /**
   * Used for admin SA/CO/DI budget approval buttons.  Will copy all amtreq as amtappr
   * and expsub as expappr for the selected budget tab. 
   * @return 
   * @param type - either AmtReq or ExpSub
   */
  public int autoSaveApprAmts(UserBean userb, int tabnum, long grantid, String type)
  {
    BudgetDBHandler adbh = new BudgetDBHandler();      
    int outcome = 0;
         
    try{    
      //get all expense records for that expense category
      switch(tabnum)
      {
        case 1:
          outcome = adbh.autoInsertPersonalAmts(userb, grantid, type);
          break;
        case 2:
          outcome = adbh.autoInsertBenefitsAmts(userb, grantid, type);
          break;
        case 3:
          outcome = adbh.autoInsertContractAmts(userb, grantid, type);
          break;
        case 4:
           outcome = adbh.autoInsertSupplyAmts(userb, grantid, type);          
          break;
        case 5:
          outcome = adbh.autoInsertOtherAmts(userb, grantid, type);
          break;
        case 6:
          outcome = adbh.autoInsertTravelAmts(userb, grantid, type);
        default:
          break;          
      }           
      
    }catch(Exception e){
      System.err.println("error autoSaveApprAmts() "+e.getMessage().toString());
    }    
    
    return outcome;
  }

  public Vector getGrantsForInst(long instid, String fundCodeList)
  {
    Vector results = new Vector();
    try{
      conn = initializeConn();
    
      ps = conn.prepareStatement("select grants.id, name, fy_code, fc_code, proj_seq_num, description, "+
      " initcap(POPULAR_NAME) as popular_name, co_institutions.inst_id "+
      " from grants, fiscal_years, sed_institutions, co_institutions "+
      " where grants.FY_CODE= fiscal_years.CODE and sed_institutions.INST_ID= co_institutions.INST_ID "+
      " and co_institutions.GRA_ID = grants.ID and co_institutions.inst_id=? and fc_code in ("+fundCodeList+") " +
      " order by description desc");
        
      ps.setLong(1, instid);
      rs = ps.executeQuery();      
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
        gb.setGrantid( rs.getLong("ID"));      
        gb.setInstID(rs.getLong("inst_id"));
        gb.setFiscalyear(rs.getString("description"));//fiscal year
        gb.setFycode(rs.getInt("fy_code"));
        gb.setFccode(rs.getInt("fc_code"));
        gb.setProjseqnum(rs.getLong("proj_seq_num"));
        gb.setInstName(rs.getString("popular_name"));     
              
        switch(gb.getFccode())
        {
          case 5:  gb.setProgram("C/P Discretionary");
                   break;
          case 6:  gb.setProgram("C/P Statutory");
                   break;
          case 7:  gb.setProgram("C/P Coordinated");
                   break;
          case 40:  gb.setProgram("Adult Literacy");
                   break;
          case 42:  gb.setProgram("Family Literacy");
                   break;
          case 80:  gb.setProgram("LGRMIF");
                  break;
          case 86:  gb.setProgram("Construction");
                  break;
        }              
        
        results.add(gb);
      }              
    }
    catch(Exception e){
      System.err.println("error getGrantsForInst() "+ e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }
      
    return results;
  }

  /**
   * This will get all grant info for grants started in given fy. Changed to get only
   * the lead institution. 
   * @return 
   * @param year
   */
  public Vector getGrantsForYear(String year, String fundCodeList)
  {
    Vector results = new Vector();  
    try{
      conn = initializeConn();    
      ps = conn.prepareStatement("select grants.id, name, fy_code, fc_code, proj_seq_num, description, "+
        " initcap(POPULAR_NAME) as popular_name, co_institutions.inst_id "+
        " from grants, fiscal_years, sed_institutions, co_institutions "+
        " where grants.FY_CODE= fiscal_years.CODE and sed_institutions.INST_ID= co_institutions.INST_ID "+
        " and co_institutions.GRA_ID = grants.ID and is_lead='Y' and fc_code in ("+fundCodeList+") "+
        " and grants.fy_code=? order by fc_code, popular_name");    
      ps.setString(1, year);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
        gb.setGrantid( rs.getLong("ID"));   
        gb.setTitle(rs.getString("name"));
        gb.setInstID(rs.getLong("inst_id"));
        gb.setFiscalyear(rs.getString("description"));//fiscal year
        gb.setFycode(rs.getInt("fy_code"));
        gb.setFccode(rs.getInt("fc_code"));
        gb.setProjseqnum(rs.getLong("proj_seq_num"));
        gb.setInstName(rs.getString("popular_name"));  
        switch(gb.getFccode())
        {
          case 5:  gb.setProgram("Discretionary");
                   break;
          case 6:  gb.setProgram("Statutory");
                   break;
          case 7:  gb.setProgram("Coordinated");
                   break;
          case 20:  gb.setProgram("State Aid: CJH & NYHS");
                    break;
          case 40:  gb.setProgram("Adult Literacy");
                   break;
          case 42:  gb.setProgram("Family Literacy");
                   break;
          case 80:  gb.setProgram("LGRMIF");
                  break;
          case 86:  gb.setProgram("Construction");
        }        
        results.add(gb);
      }                    
    }
    catch(Exception e) {
      System.err.println("error getGrantsForYear() "+ e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }    
    return results;
  }

  /**
   * This method will get all info for a given project number(last 4 digits only).
   * Changed to get only lead instititution
   * @return 
   * @param projnum
   */
  public Vector getGrantsByNum(String projnum)
  {
    Vector results = new Vector();  
    try{
      conn = initializeConn();
    
      ps = conn.prepareStatement("select grants.id, name, fy_code, fc_code, proj_seq_num, description, "+
            " initcap(POPULAR_NAME) as popular_name, co_institutions.inst_id "+
             " from grants, fiscal_years, sed_institutions, co_institutions "+
             " where grants.proj_seq_num=? and is_lead='Y' "+
             " and grants.FY_CODE= fiscal_years.CODE "+
             " and sed_institutions.INST_ID= co_institutions.INST_ID "+
             " and co_institutions.GRA_ID = grants.ID");
      ps.setString(1, projnum);
      rs = ps.executeQuery();      
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
        gb.setGrantid( rs.getLong("ID"));      
        gb.setTitle(rs.getString("name"));
        gb.setInstID(rs.getLong("inst_id"));
        gb.setFiscalyear(rs.getString("description"));//fiscal year
        gb.setFycode(rs.getInt("fy_code"));
        gb.setFccode(rs.getInt("fc_code"));
        gb.setProjseqnum(rs.getLong("proj_seq_num"));
        gb.setInstName(rs.getString("popular_name"));  
        switch(gb.getFccode())
        {
          case 5:  gb.setProgram("Discretionary");
                   break;
          case 6:  gb.setProgram("Statutory");
                   break;
          case 7:  gb.setProgram("Coordinated");
                   break;
          case 20:  gb.setProgram("State Aid: CJH & NYHS");
                    break;
          case 40:  gb.setProgram("Adult Literacy");
                   break;
          case 42:  gb.setProgram("Family Literacy");
                   break;
          case 80:  gb.setProgram("LGRMIF");
                  break;
          case 86:  gb.setProgram("Construction");
                  break;
        }                
        results.add(gb);
      }                    
    }
    catch(Exception e){
      System.err.println("error getGrantsByNum() "+ e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }
    
    return results;
  }

  /**
   * This method will get all uploaded documents in the uploads table for the 
   * given grantid.  It stores each document info in a bean.
   * @return 
   * @param grantid
   */
  public Vector getAllDocuments(long grantid)
  {   
    Vector results = new Vector(); 
    try{
      conn = initializeConn();
      
      ps = conn.prepareStatement("select * from UPLOADS where GRA_ID=? " +
      " order by lower(DAD_CHARSET) ");
      ps.setLong(1, grantid);
      
      rs = ps.executeQuery();
      while(rs.next())
      {
        UploadDocBean ub = new UploadDocBean();
        ub.setId(rs.getLong("ID"));
        ub.setName(rs.getString("NAME"));
        ub.setDocSize(rs.getString("DOC_SIZE"));
        ub.setDocType(rs.getString("DOC_TYPE"));
        ub.setDateCreated(rs.getDate("DATE_CREATED"));
        ub.setCreatedBy(rs.getString("CREATED_BY"));
        ub.setDescription(rs.getString("DAD_CHARSET"));
        
        results.add(ub);
      }      
          
    }
    catch(Exception e) {
      System.err.println("error getAllDocuments() "+ e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }  
  
    return results;
  }

  /**
   * This method deletes the selected document from the uploads table.
   * @return 
   * @param docid
   */
  public int deleteDocument(int docid)
  {
    int outcome = 0;
        
    try{
      conn = initializeConn();
      
      ps = conn.prepareStatement("delete from UPLOADS where ID=?");
      ps.setInt(1, docid);
      
      outcome = ps.executeUpdate();                    
    }
    catch(Exception e){
      System.err.println("error deleteDocuments() "+ e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
    }  
  
    return outcome;
  }


    public HashMap getSubmitStatusForHome(HashMap allgrants)
    {  
      Vector currGrant = new Vector();
      Vector partGrant = new Vector();
     
      try {
        
        if(allgrants.containsKey(new Integer(1)))//curr grants exist
          currGrant = (Vector) allgrants.get(new Integer(1));
        if(allgrants.containsKey(new Integer(2)))//participant grants exist
          partGrant = (Vector) allgrants.get(new Integer(2));
          
        conn = initializeConn();        
        ps = conn.prepareStatement("select * from grant_submissions where gra_id=? ");
        
        for(int i=0; i<currGrant.size(); i++)
        {
            GrantBean g = (GrantBean)currGrant.get(i);
            ps.setLong(1, g.getGrantid());
            rs = ps.executeQuery();
            while(rs.next()) {
                SubmitBean s=new SubmitBean();
                s.setId(rs.getLong("id"));
                s.setVersionSubmitted(rs.getString("version"));
                g.setSubmissionBean(s);
            }
        }
        
        
        for(int j=0; j<partGrant.size(); j++) {
            GrantBean g = (GrantBean)partGrant.get(j);
            ps.setLong(1, g.getGrantid());
            rs = ps.executeQuery();
            while(rs.next()) {
                SubmitBean s=new SubmitBean();
                s.setId(rs.getLong("id"));
                s.setVersionSubmitted(rs.getString("version"));
                g.setSubmissionBean(s);
            }
        }
              
    } catch (Exception ex){
        System.err.println("error getSubmitStatusForHome() " + ex.toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }  
    return allgrants;
  }

  /**
   * Method will get all grants for given institution and program and display
   * to applicant on the program home page. 
   * @return 
   * @param fccode
   * @param instid
   */
  public HashMap getGrantsForProgramHome(long instid, int fccode)
  {  
    HashMap results = new HashMap();
    Vector thisGrant = new Vector();
    Vector partGrant = new Vector();
   
    try {
      conn = initializeConn();      
             
      ps = conn.prepareStatement("select grants.id, name, fy_code, fc_code, proj_seq_num, description, "+
             " initcap(POPULAR_NAME) as popular_name, co_institutions.inst_id, is_lead, doris_flag, doris_name "+
             " from grants, fiscal_years, co_institutions "+
             " left join SED_INSTITUTIONS on sed_institutions.inst_id=co_institutions.inst_id "+
             " where grants.FY_CODE= fiscal_years.CODE "+
             " and sed_institutions.INST_ID= co_institutions.INST_ID "+
             " and co_institutions.GRA_ID = grants.ID "+
             " and fc_code=? and co_institutions.inst_id=? order by description desc");
      ps.setInt(1, fccode);
      ps.setLong(2, instid);
      rs = ps.executeQuery();      
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
        gb.setGrantid( rs.getLong("ID"));   
        gb.setTitle(rs.getString("name"));
        gb.setInstID(rs.getLong("inst_id"));
        gb.setFiscalyear(rs.getString("description"));//fiscal year
        gb.setFycode(rs.getInt("fy_code"));
        gb.setFccode(rs.getInt("fc_code"));
        gb.setProjseqnum(rs.getLong("proj_seq_num"));
          
        if(gb.getFccode()==20){  
            //per BL; need 4 digit proj num to be same every year for stateaid
        	gb.setProjseqnum(handleStateAidProjNumbers(gb.getInstID()));       
        }
          
        gb.setInstName(rs.getString("popular_name"));        
        String islead =rs.getString("is_lead");
        gb.setDorisflag(rs.getBoolean("doris_flag"));
        if(gb.isDorisflag());
          gb.setDorisname(rs.getString("doris_name"));        
        
        if(islead.equals("Y"))
          thisGrant.add(gb);
        else
          partGrant.add(gb);        
      }
      
      Close(rs);
      Close(ps);
      
      //for each participating grant - get the sponsoring inst 2/15/08
       if(partGrant.size()>0)
       {
          ps = conn.prepareStatement("select initcap(POPULAR_NAME) as popular_name from co_institutions, sed_institutions "+
          " where gra_id=? and is_lead='Y' and co_institutions.inst_id = sed_institutions.inst_id");
         
           for (int i=0; i<partGrant.size(); i++)
           {
             GrantBean g = (GrantBean) partGrant.get(i);
             ps.setLong(1, g.getGrantid());
             rs = ps.executeQuery();
             while(rs.next())
             {
               g.setInstName(rs.getString("popular_name"));
             }
           }
       }
       //add both vectors to hashmap
       results.put(new Integer(1), thisGrant);
       results.put(new Integer(2), partGrant);           
                                 
    } catch (Exception ex){
        System.err.println("error getGrantsForProgramHome() " + ex.toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    } 
    
    return results;
  }

 
  public int updateProjNameReligiousAffil(UserBean userb, long grantid, String projName, boolean religiousAffil)
  {  
    String created_by = userb.getUserid();
    int outcome = 0;
   
    try {
      conn = initializeConn(); 
      ps = conn.prepareStatement("update GRANTS set NAME=?, RELIGIOUS_AFill=?, DATE_MODIFIED=sysdate, "+
         " MODIFIED_BY=? where ID=?");
      ps.setString(1, projName);
      ps.setBoolean(2, religiousAffil);
      ps.setString(3, created_by);
      ps.setLong(4, grantid);
      outcome = ps.executeUpdate();
      
    } catch (Exception ex){
        System.err.println("error updateProjName()  " + ex.toString());
    }
    finally{
      Close(ps);
      Close(conn);
    }
    
    return outcome;
  }

  public void handleParticipatingLib(HttpServletRequest request)
  {
  
    HttpSession sess = request.getSession();
    String grantnum= (String)sess.getAttribute("grantid");
    UserBean userb = (UserBean) sess.getAttribute("lduser");
    int outcome = 0;
    PartInstBean pb;
    PartInstDBbean pdb = new PartInstDBbean();
   
    try {
      long grantid = Long.parseLong(grantnum);
      String inst1 = request.getParameter("inst1");
      String inst2 = request.getParameter("inst2");
      String inst3 = request.getParameter("inst3");
      String inst4 = request.getParameter("inst4");
      String inst5 = request.getParameter("inst5");
      String inst6 = request.getParameter("inst6");
      String inst7 = request.getParameter("inst7");
      String inst8 = request.getParameter("inst8");
      String inst9 = request.getParameter("inst9");
      String inst10 = request.getParameter("inst10");
      
      if(inst1.equals("0"))//no inst chosen for this select box
      {
        pb = (PartInstBean)sess.getAttribute("instBean1");
        if(pb != null)//prior inst record existed in table
        {
          //user chose to delete the prior inst record
          outcome = pdb.deletePartLib(pb.getId());
        }
      }
      else//an inst was chosen for this select box
      {
        pb = (PartInstBean)sess.getAttribute("instBean1");
        if(pb ==null)//no prior inst record, so create one
        {
          outcome = pdb.insertPartLib(grantid, userb, Long.parseLong(inst1));
        }
        else
        {
          //had a prior inst record, so just update the record
          outcome = pdb.updatePartLib(userb, Long.parseLong(inst1), pb.getId());
        }
      }
      
      
      
      if(inst2.equals("0"))//no inst chosen for this select box
      {
        pb = (PartInstBean)sess.getAttribute("instBean2");
        if(pb != null)//prior inst record existed in table
        {
          //user chose to delete the prior inst record
          outcome = pdb.deletePartLib(pb.getId());
        }
      }
      else//an inst was chosen for this select box
      {
        pb = (PartInstBean)sess.getAttribute("instBean2");
        if(pb ==null)//no prior inst record, so create one
        {
          outcome = pdb.insertPartLib(grantid, userb, Long.parseLong(inst2));
        }
        else
        {
          //had a prior inst record, so just update the record
          outcome = pdb.updatePartLib(userb, Long.parseLong(inst2), pb.getId());
        }
      }
      
      
      if(inst3.equals("0"))//no inst chosen for this select box
      {
        pb = (PartInstBean)sess.getAttribute("instBean3");
        if(pb != null)//prior inst record existed in table
        {
          //user chose to delete the prior inst record
          outcome = pdb.deletePartLib(pb.getId());
        }
      }
      else//an inst was chosen for this select box
      {
        pb = (PartInstBean)sess.getAttribute("instBean3");
        if(pb ==null)//no prior inst record, so create one
        {
          outcome = pdb.insertPartLib(grantid, userb, Long.parseLong(inst3));
        }
        else
        {
          //had a prior inst record, so just update the record
          outcome = pdb.updatePartLib(userb, Long.parseLong(inst3), pb.getId());
        }
      }
      
      
      if(inst4.equals("0"))//no inst chosen for this select box
      {
        pb = (PartInstBean)sess.getAttribute("instBean4");
        if(pb != null)//prior inst record existed in table
        {
          //user chose to delete the prior inst record
          outcome = pdb.deletePartLib(pb.getId());
        }
      }
      else//an inst was chosen for this select box
      {
        pb = (PartInstBean)sess.getAttribute("instBean4");
        if(pb ==null)//no prior inst record, so create one
        {
          outcome = pdb.insertPartLib(grantid, userb, Long.parseLong(inst4));
        }
        else
        {
          //had a prior inst record, so just update the record
          outcome = pdb.updatePartLib(userb, Long.parseLong(inst4), pb.getId());
        }
      }
      
      
      if(inst5.equals("0"))//no inst chosen for this select box
      {
        pb = (PartInstBean)sess.getAttribute("instBean5");
        if(pb != null)//prior inst record existed in table
        {
          //user chose to delete the prior inst record
          outcome = pdb.deletePartLib(pb.getId());
        }
      }
      else//an inst was chosen for this select box
      {
        pb = (PartInstBean)sess.getAttribute("instBean5");
        if(pb ==null)//no prior inst record, so create one
        {
          outcome = pdb.insertPartLib(grantid, userb, Long.parseLong(inst5));
        }
        else
        {
          //had a prior inst record, so just update the record
          outcome = pdb.updatePartLib(userb, Long.parseLong(inst5), pb.getId());
        }
      }
      
      
      if(inst6.equals("0"))//no inst chosen for this select box
      {
        pb = (PartInstBean)sess.getAttribute("instBean6");
        if(pb != null)//prior inst record existed in table
        {
          //user chose to delete the prior inst record
          outcome = pdb.deletePartLib(pb.getId());
        }
      }
      else//an inst was chosen for this select box
      {
        pb = (PartInstBean)sess.getAttribute("instBean6");
        if(pb ==null)//no prior inst record, so create one
        {
          outcome = pdb.insertPartLib(grantid, userb, Long.parseLong(inst6));
        }
        else
        {
          //had a prior inst record, so just update the record
          outcome = pdb.updatePartLib(userb, Long.parseLong(inst6), pb.getId());
        }
      }
      
      if(inst7.equals("0"))//no inst chosen for this select box
      {
        pb = (PartInstBean)sess.getAttribute("instBean7");
        if(pb != null)//prior inst record existed in table
        {
          //user chose to delete the prior inst record
          outcome = pdb.deletePartLib(pb.getId());
        }
      }
      else//an inst was chosen for this select box
      {
        pb = (PartInstBean)sess.getAttribute("instBean7");
        if(pb ==null)//no prior inst record, so create one
        {
          outcome = pdb.insertPartLib(grantid, userb, Long.parseLong(inst7));
        }
        else
        {
          //had a prior inst record, so just update the record
          outcome = pdb.updatePartLib(userb, Long.parseLong(inst7), pb.getId());
        }
      }
      
      if(inst8.equals("0"))//no inst chosen for this select box
      {
        pb = (PartInstBean)sess.getAttribute("instBean8");
        if(pb != null)//prior inst record existed in table
        {
          //user chose to delete the prior inst record
          outcome = pdb.deletePartLib(pb.getId());
        }
      }
      else//an inst was chosen for this select box
      {
        pb = (PartInstBean)sess.getAttribute("instBean8");
        if(pb ==null)//no prior inst record, so create one
        {
          outcome = pdb.insertPartLib(grantid, userb, Long.parseLong(inst8));
        }
        else
        {
          //had a prior inst record, so just update the record
          outcome = pdb.updatePartLib(userb, Long.parseLong(inst8), pb.getId());
        }
      }
      
      if(inst9.equals("0"))//no inst chosen for this select box
      {
        pb = (PartInstBean)sess.getAttribute("instBean9");
        if(pb != null)//prior inst record existed in table
        {
          //user chose to delete the prior inst record
          outcome = pdb.deletePartLib(pb.getId());
        }
      }
      else//an inst was chosen for this select box
      {
        pb = (PartInstBean)sess.getAttribute("instBean9");
        if(pb ==null)//no prior inst record, so create one
        {
          outcome = pdb.insertPartLib(grantid, userb, Long.parseLong(inst9));
        }
        else
        {
          //had a prior inst record, so just update the record
          outcome = pdb.updatePartLib(userb, Long.parseLong(inst9), pb.getId());
        }
      }
      
      if(inst10.equals("0"))//no inst chosen for this select box
      {
        pb = (PartInstBean)sess.getAttribute("instBean10");
        if(pb != null)//prior inst record existed in table
        {
          //user chose to delete the prior inst record
          outcome = pdb.deletePartLib(pb.getId());
        }
      }
      else//an inst was chosen for this select box
      {
        pb = (PartInstBean)sess.getAttribute("instBean10");
        if(pb ==null)//no prior inst record, so create one
        {
          outcome = pdb.insertPartLib(grantid, userb, Long.parseLong(inst10));
        }
        else
        {
          //had a prior inst record, so just update the record
          outcome = pdb.updatePartLib(userb, Long.parseLong(inst10), pb.getId());
        }
      }
      
      //remove old part inst's from session
      sess.removeAttribute("instBean1");
      sess.removeAttribute("instBean2");
      sess.removeAttribute("instBean3");
      sess.removeAttribute("instBean4");
      sess.removeAttribute("instBean5");
      sess.removeAttribute("instBean6");
      sess.removeAttribute("instBean7");
      sess.removeAttribute("instBean8");
      sess.removeAttribute("instBean9");
      sess.removeAttribute("instBean10");   
      
    } catch (Exception ex){
        System.err.println("error handleParticipatingLib()  " + ex.toString());
    }      
  }

  /**
   * Checks today's date against the available/due dates for the given
   * grant program. 
   * @return 
   * @param fundcode
   */
  public AppDatesBean allowCreateSubmitRecord(int fundcode)
  {
    AppDatesBean ab = new AppDatesBean();
    Date dbDate = new Date();
    try {    
      conn = initializeConn();
            
      ps = conn.prepareStatement("select sysdate from dual");
      rs = ps.executeQuery();
      while(rs.next())
      {
        dbDate = rs.getDate("sysdate");
      }
      Format formatter = new SimpleDateFormat("MM/dd/yyyy");
      String todayDate = formatter.format(dbDate);
            
      Close(rs);
      Close(ps);
     
      ps = conn.prepareStatement("select * from APP_DATES where to_date(?, 'MM/dd/yyyy') "+
      " >= START_DATE and to_date(?, 'MM/dd/yyyy') <= DUE_DATE and FC_CODE=? ");
      ps.setString(1, todayDate);
      ps.setString(2, todayDate);
      ps.setInt(3, fundcode);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        ab.setDateAcceptable(true);
        ab.setFycode(rs.getInt("fy_code"));
        ab.setDuedate(rs.getDate("due_date"));
      }
  
    } catch (Exception ex){
        System.err.println("error allowCreateSubmitRecord()  " + ex.toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }       
    return ab;
  }

  /**
   * This method will get all budget records for a given tab. Used for applicant action forms-
   * initial/final budget forms data entry for sa/co/di/fl/al/cn applicants. LG has seperate method
   * b/c tab numbering is different.
   * @return 
   * @param tab
   * @param asb
   * @param grantid
   */
  public BudgetCollectionBean getApcntBudgetActionLD(long grantid, AppStatusBean asb, int tab, boolean splitCategory)
  {  
    BudgetDBHandler bdh = new BudgetDBHandler();
    BudgetCollectionBean bc = new BudgetCollectionBean();
   
    try{       
      if(asb!=null){
        boolean showApprovals = false;
        if(asb.isShowscorecomm())//appr amts only shown if showscore flag==true
          showApprovals = true;
        
        if(splitCategory){//needed for lit/cn budget categories
            switch(tab){
              case 1:
                 bc = bdh.getPersonalActionInfo(grantid, bc, showApprovals,3);
                 break;
              case 2:
                 bc = bdh.getPersonalActionInfo(grantid, bc, showApprovals,4);
                 break;
              case 3:
                 bc = bdh.getBenefitActionInfo(grantid, bc, showApprovals); 
                 break;
              case 4:
                 bc = bdh.getContractedActionInfo(grantid, bc, showApprovals, 5);
                 break;
              case 5:
                 bc = bdh.getSupplyActionInfo(grantid, bc, showApprovals, 1);
                 break;
              case 6:
                 bc = bdh.getSupplyActionInfo(grantid, bc, showApprovals, 2);
                 break;
              case 7:
                bc = bdh.getTravelActionInfo(grantid, bc, showApprovals); 
                break;          
            }    
        }
        else{            
            switch(tab){//for cn apcnt/sysrev/admin initial budget click
              case 1:
                 bc = bdh.getPersonalActionInfo(grantid, bc, showApprovals,0);
                 break;
              case 2:
                 bc = bdh.getBenefitActionInfo(grantid, bc, showApprovals); 
                 break;
              case 3:
                 bc = bdh.getContractedActionInfo(grantid, bc, showApprovals, 0);
                 break;
              case 4:
                 bc = bdh.getSupplyActionInfo(grantid, bc, showApprovals, 0);
                 break;
              case 5:
                 bc = bdh.getExpenseActionInfo(grantid, bc, showApprovals);
                 break;
              case 6:
                bc = bdh.getTravelActionInfo(grantid, bc, showApprovals); 
                break;          
            }    
        }
      } 
      
    } catch(Exception e){System.out.println("error getApcntBudgetActionLD " + e.toString());}
    return bc;
  }
  
  /*copy of fn made 10/15/09
   * public BudgetCollectionBean getApcntBudgetAction(long grantid, AppStatusBean asb)
  {  
    BudgetDBHandler bdh = new BudgetDBHandler();
    BudgetCollectionBean bc = new BudgetCollectionBean();
   
    try{       
      if(asb!=null)
      {
        boolean showApprovals = false;
        if(asb.isInitialappr() || asb.isAppDenied())
          showApprovals = true;
            
        bc = bdh.getPersonalActionInfo(grantid, bc, showApprovals,0);
        bc = bdh.getBenefitActionInfo(grantid, bc, showApprovals);        
        bc = bdh.getContractedActionInfo(grantid, bc, showApprovals, 0);
        bc = bdh.getSupplyActionInfo(grantid, bc, showApprovals, 0);
        bc = bdh.getExpenseActionInfo(grantid, bc, showApprovals);    
        bc = bdh.getTravelActionInfo(grantid, bc, showApprovals);        
      } 
      
    } catch(Exception e){System.out.println("error getApcntBudgetAction " + e.toString());}
    return bc;
  }*/
  
  /**
   * Get all budget records for given tab. Used for lgrmif applicant to display records in
   * action form for initial/final budget. 
   * @return 
   * @param asb
   * @param tab
   * @param grantid
   */
  public BudgetCollectionBean getApcntBudgetActionTab(long grantid, int tab, AppStatusBean asb)
  {  
    BudgetDBHandler bdh = new BudgetDBHandler();
    BudgetCollectionBean bc = new BudgetCollectionBean();
   
    try{             
      if(asb!=null){
        boolean showApprovals = false;
        if(asb.isShowscorecomm())//show appr amts based on showscore flag
          showApprovals = true;
                
        //switch on lgrmif budget tab
          switch(tab)
          {
            case 1:
              bc = bdh.getPersonalActionInfo(grantid, bc, showApprovals,3);//proff staff
              break;
            case 2:
              bc = bdh.getPersonalActionInfo(grantid, bc, showApprovals,4);//support staff
              break;
            case 3:
              bc = bdh.getSupplyActionInfo(grantid, bc, showApprovals, 2);//equip
              break;
            case 4:
              bc = bdh.getExpenseActionInfo(grantid, bc, showApprovals);//minor remodleing
              break;
            case 5:
              bc = bdh.getContractedActionInfo(grantid, bc, showApprovals, 5);//purch serv
              break;
            case 6:
              bc = bdh.getContractedActionInfo(grantid, bc, showApprovals, 6);//purch boces
              break;
            case 7:
              bc = bdh.getSupplyActionInfo(grantid, bc, showApprovals, 1);//supp/mat
              break;
            case 8:
              bc = bdh.getTravelActionInfo(grantid, bc, showApprovals);
              break;
            case 9:
              bc = bdh.getBenefitActionInfo(grantid, bc, showApprovals);
              break;            
          }
      } 
      
    } catch(Exception e){System.out.println("error getApcntBudgetActionTab " + e.toString());}
    return bc;
  }


  public DistrictBean getDistrictInfo(long instid)
  {
    DistrictBean db = new DistrictBean();
    try {
      conn = initializeConn();    
      
      //get federal ID num, school district, judicial district
      ps = conn.prepareStatement("select sed_institutions.sdl_code, initcap(sed_sch_dist_locs.description) as description, "+
      " judicial_dist_code, federal_id from sed_sch_dist_locs, sed_counties, sed_institutions left "+
      " join sed_payee_infos on sed_institutions.payee_id = sed_payee_infos.payee_id where "+
      " inst_id=? and sed_institutions.sdl_code = sed_sch_dist_locs.SDL_CODE and "+
      " sed_institutions.COUNTY_CODE = sed_counties.county_code");
      ps.setLong(1, instid);
      rs = ps.executeQuery();
      while(rs.next())
      {
        db.setFederalid(rs.getString("federal_id"));
        db.setSchDistCode(rs.getString("sdl_code"));
        db.setSchoolDistrict(rs.getString("description"));
        db.setJudicialDistricts(rs.getString("judicial_dist_code"));
      }
      
      //get senate districts
      ps.clearParameters();
      ArrayList senate = new ArrayList();
      ps = conn.prepareStatement("select * from sed_senate_dist_data where sdl_code=? and year='2000'");
      ps.setString(1, db.getSchDistCode());
      rs = ps.executeQuery();
      while(rs.next())
      {
        senate.add(rs.getString("senate_dist_code"));
      }
      db.setSenateDistricts(senate);      
      
      //get congress districts
      ps.clearParameters();
      ArrayList congress = new ArrayList();
      ps = conn.prepareStatement("select * from sed_congress_dist_data where sdl_code=? and year='2000'");
      ps.setString(1, db.getSchDistCode());
      rs = ps.executeQuery();
      while(rs.next())
      {
        congress.add(rs.getString("congress_dist_code"));
      }
      db.setCongressDistricts(congress);      
      
      //get assembly districts
      ps.clearParameters();
      ArrayList assemb = new ArrayList();
      ps = conn.prepareStatement("select * from sed_assembly_dist_data where sdl_code=? and year='2000'");
      ps.setString(1, db.getSchDistCode());
      rs = ps.executeQuery();
      while(rs.next())
      {
        assemb.add(rs.getString("assembly_dist_code"));
      }
      db.setAssemblyDistricts(assemb);
      
      
      //get the institution sub type
      ps.clearParameters();
      ps = conn.prepareStatement("select initcap(sed_inst_sub_types.description) as insttype from sed_inst_sub_types, sed_institutions "+
      " where inst_id=? and sed_institutions.inst_sub_type_code = sed_inst_sub_types.inst_sub_type_code and "+
      " sed_institutions.inst_type_code = sed_inst_sub_types.INST_TYPE_CODE");
      ps.setLong(1, instid);
      rs = ps.executeQuery();
      while(rs.next())
      {
        db.setInsttype(rs.getString("insttype"));//sed_inst_sub_types.description
      }
      
    }catch(Exception e){
      System.err.println("error getDistrictInfo() " + e.getMessage().toString());
    }
    finally
    {
      Close(rs);
      Close(conn);
      Close(ps);
    }    
    return db;      
  }
   

  /**
   * Create a new record in grants and co_institutions for SA/CO/DI,FL,AL,LG.  Returns the new grant id
   * @return 
   * @param fycode
   * @param fccode
   * @param userb
   */
  public long createRecordForProgram(UserBean userb, int fycode, int fccode)
  {    
    long grant_id=0;
    int numrows = 0;     
    String created_by = userb.getUserid();
    long inst_id = userb.getInstid();    
    
    try {     
      //make sure we have fy to apply for
      if(fycode==0)
        return 0;
        
      conn = initializeConn();             
      //create a new entry into grants table
      ps = conn.prepareStatement("insert into GRANTS(ID, NAME, FC_CODE, FY_CODE, PROJ_SEQ_NUM, "+
      " CREATED_BY, DATE_CREATED) VALUES(Grants_seq.nextval, ?,?,?,PROJ_NUM_SEQ.NEXTVAL,?,sysdate) ");
    
      ps.setString(1, " ");
      ps.setInt(2, fccode);
      ps.setInt(3, fycode);
      ps.setString(4, created_by);      
      numrows = ps.executeUpdate();  
           
      Close(ps);
//--------------------------------------------------------------------------------

    //create entry in co_institution  - ties a grant with an institution id    
      ps = conn.prepareStatement("insert into co_institutions (ID, GRA_ID, IS_LEAD, CREATED_BY, "+
         " DATE_CREATED, INST_ID) values (co_inst_seq.nextval, grants_seq.currval, 'Y', ?,sysdate, ?)");
      
      ps.setString(1, created_by);
      ps.setLong(2, inst_id);
      numrows = ps.executeUpdate();
      
      Close(ps);
//--------------------------------------------------------------------------------

      //get library director for this institution - type code of 1 or 4
      ps = conn.prepareStatement("select * from SED_ADMIN_POSITIONS "+
      " where INST_ID=? and (ADMIN_POS_TYPE_CODE =1 or ADMIN_POS_TYPE_CODE=4) and inactive_date is null");
      ps.setLong(1, inst_id);
      rs = ps.executeQuery();
      
      int libdirID = 0;
      while( rs.next()){
        libdirID = rs.getInt("ADMIN_POS_ID");
      }     
      
     //get pres officer (for statutory and coordinated)
     ps = conn.prepareStatement("select * from SED_ADMIN_POSITIONS "+
      " where INST_ID=? and ADMIN_POS_TYPE_CODE =16 and inactive_date is null");
     ps.setLong(1, inst_id);
     rs = ps.executeQuery();
     
     int presofficerID =0;
     while( rs.next()){
       presofficerID = rs.getInt("ADMIN_POS_ID");
     }
     
     Close(rs);
     Close(ps);
     
     //insert lib dir/pres officer into grant_admins table if they exist in SEDREF
     ps = conn.prepareStatement("insert into GRANT_ADMINS (ID, ADMIN_POS_ID, GRA_ID, DATE_CREATED, "+
     " CREATED_BY, TITLE, start_date) values (GRA_ADMIN_SEQ.NEXTVAL, ?,GRANTS_SEQ.CURRVAL, SYSDATE, ?, ?, SYSDATE) ");
     
     if(! (libdirID==0))
     {
        ps.setInt(1, libdirID);
        ps.setString(2, created_by);
        ps.setString(3, "Library Director");
        numrows = ps.executeUpdate();
     }    
     if(! (presofficerID==0))
     {
        ps.setInt(1, presofficerID);
        ps.setString(2, created_by);
        ps.setString(3, "Preservation Officer");
        numrows += ps.executeUpdate();
     }
     
     Close(ps);       
//-----------------------------------------------------------------------
      //Get new grant id  
      ps = conn.prepareStatement("select max(ID) from GRANTS");
      rs = ps.executeQuery();
      rs.next();
      if(rs.getLong(1) != 0){
          grant_id = rs.getLong(1);      
      }      
                            
    } catch (Exception ex){
        System.err.println("error createRecordForProgram()  " + ex.toString());
       return 0;
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }
    return grant_id;
  }

  public int saveInitialProgress(AppStatusBean asb, UserBean userb, int fccode)
  {
    int outcome =0;
    
    try{                  
      conn = initializeConn();
      if(fccode==80){//for lgrmif
          ps = conn.prepareStatement("update GRANTS set COVERSHEET_COMP=?, DESCRIPTION_COMP=?, "+
            " BUDGET_COMP=?, AUTH_COMP=?, FS20_COMP=?, DATE_MODIFIED=sysdate, MODIFIED_BY=?, "+
            " MICROFORM_YN =?, RELIGIOUS_AFill=?, amount_req=? where ID=? ");
          ps.setBoolean(1, asb.isCoversheetComp());
          ps.setBoolean(2, asb.isProjdescComp());
          ps.setBoolean(3, asb.isBudgetComp());
          ps.setBoolean(4, asb.isAuthComp());
          ps.setBoolean(5, asb.isFs20Comp());
          ps.setString(6, userb.getUserid());
          ps.setBoolean(7, asb.isPayeeyn());
          ps.setBoolean(8, asb.isAppendixyn());
          ps.setInt(9, asb.getMwbeParticipation());
          ps.setLong(10, asb.getGrantid());         
      }
      else if(fccode==86)//new, for construction
      {
          ps = conn.prepareStatement("update GRANTS set COVERSHEET_COMP=?, DESCRIPTION_COMP=?, "+
          " ATTACH_COMP=?, BUDGET_COMP=?, AUTH_COMP=?, MICROFORM_YN=?, RELIGIOUS_AFill=?, SHPO_COMP=?, "+
          " SEAF_COMP=?, FS20_COMP=?, DATE_MODIFIED=sysdate, MODIFIED_BY=?, "+
          " PAYEE_COMP =?, PHOTO_COMP=?, EDUCATION_APP=?, DORIS_FLAG=?, amount_req=? where ID=? ");
            ps.setBoolean(1, asb.isCoversheetComp());
            ps.setBoolean(2, asb.isProjdescComp());
            ps.setBoolean(3, asb.isAttachcomp());                        
            ps.setBoolean(4, asb.isBudgetComp());
            ps.setBoolean(5, asb.isAuthComp());
            ps.setBoolean(6, asb.isPayeeyn());//minimum lease agreement
            ps.setBoolean(7, asb.isAppendixyn());//OFP approval
            ps.setBoolean(8, asb.isShpocomp());
            ps.setBoolean(9, asb.isSeafcomp());
            ps.setBoolean(10, asb.isFs20Comp());
            ps.setString(11, userb.getUserid());
            ps.setBoolean(12, asb.isPayeecomp());
            ps.setBoolean(13, asb.isPhotocomp());
            ps.setBoolean(14, asb.isAssurancesyn());//assurances
            ps.setBoolean(15, asb.isAdditfundingyn());//additional funding
            ps.setInt(16, asb.getMwbeParticipation());
            ps.setLong(17, asb.getGrantid());         
        }    
      else{    
          ps = conn.prepareStatement("update GRANTS set COVERSHEET_COMP=?, DESCRIPTION_COMP=?, "+
            " BUDGET_COMP=?, AUTH_COMP=?, FS20_COMP=?, DATE_MODIFIED=sysdate, MODIFIED_BY=?, amount_req=?  where ID=? ");
          ps.setBoolean(1, asb.isCoversheetComp());
          ps.setBoolean(2, asb.isProjdescComp());
          ps.setBoolean(3, asb.isBudgetComp());
          ps.setBoolean(4, asb.isAuthComp());
          ps.setBoolean(5, asb.isFs20Comp());
          ps.setString(6, userb.getUserid());
          ps.setInt(7, asb.getMwbeParticipation());
          ps.setLong(8, asb.getGrantid());                  
      }
      outcome = ps.executeUpdate();
      
    }catch(Exception e){
      System.err.println("error saveInitialProgress() " + e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
    }
    return outcome;
  }

  public int saveFinalProgress(AppStatusBean asb, UserBean userb, int fccode)
  {
    int outcome = 0;
    
    try{                      
      conn = initializeConn();      
      if(fccode==80)
      {                  
        ps = conn.prepareStatement("update GRANTS set FINAL_NARR_COMP=?, FINAL_BUDGET_COMP=?, "+
        "SIGNOFF_COMP=?, FS10F_YN =?, DATE_MODIFIED=sysdate, MODIFIED_BY=?, Education_app=? where ID=?");        
        ps.setBoolean(1, asb.isFinalnarrativeComp());
        ps.setBoolean(2, asb.isFinalbudgetComp());
        ps.setBoolean(3, asb.isSignoffComp());
        ps.setBoolean(4, asb.isFs10fComp());      
        ps.setString(5, userb.getUserid());
        ps.setBoolean(6, asb.isStatisticsyn());//into education_app field
        ps.setLong(7, asb.getGrantid());
        outcome = ps.executeUpdate();        
      }
      else 
      {
        ps = conn.prepareStatement("update GRANTS set FINAL_NARR_COMP=?, FINAL_BUDGET_COMP=?, "+
        " SIGNOFF_COMP=?, FS10F_YN =?, DATE_MODIFIED=sysdate, MODIFIED_BY=?  where ID=?");        
        ps.setBoolean(1, asb.isFinalnarrativeComp());
        ps.setBoolean(2, asb.isFinalbudgetComp());
        ps.setBoolean(3, asb.isSignoffComp());
        ps.setBoolean(4, asb.isFs10fyn());      
        ps.setString(5, userb.getUserid());
        ps.setLong(6, asb.getGrantid());
        outcome = ps.executeUpdate();        
      }
                          
    }
    catch(Exception e){
      System.err.println("error saveFinalProgress() " + e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
    }        
    return outcome;
  }

  public CoversheetBean getProjectNameReligAffil(CoversheetBean csb)
  {    
    try {
      conn = initializeConn();
      ps = conn.prepareStatement("select name, religious_afill from grants where id=?");
      ps.setLong(1, csb.getGrantid());

      rs = ps.executeQuery();
      while(rs.next()){
        csb.setProjectTitle(rs.getString("name"));
        csb.setReligious(rs.getBoolean("religious_afill")); 
      }
    
    }catch(Exception e){
      System.err.println("error getProjectNameReligAffil() " + e.getMessage().toString());
    }finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }      
    return csb;
  } 

  public Vector searchGrantsByInst(String instName, String fundCodeList)
  {
    Vector results = new Vector();
    try{
      conn = initializeConn();
    
      ps = conn.prepareStatement("select grants.id, name, fy_code, fc_code, proj_seq_num, description, "+
            " initcap(POPULAR_NAME) as popular_name, co_institutions.inst_id "+
            " from grants, fiscal_years, sed_institutions, co_institutions "+
            " where grants.FY_CODE= fiscal_years.CODE "+
            " and sed_institutions.INST_ID= co_institutions.INST_ID "+
            " and co_institutions.GRA_ID = grants.ID and fc_code in ("+fundCodeList+") "+
            " and sed_institutions.popular_name like upper(?) order by popular_name desc");
      ps.setString(1, instName+"%");
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
        gb.setGrantid(rs.getLong("ID"));      
        gb.setTitle(rs.getString("name"));
        gb.setInstID(rs.getLong("inst_id"));
        gb.setFiscalyear(rs.getString("description"));//fiscal year
        gb.setFycode(rs.getInt("fy_code"));
        gb.setFccode(rs.getInt("fc_code"));
        gb.setProjseqnum(rs.getLong("proj_seq_num"));
        gb.setInstName(rs.getString("popular_name"));     
        switch(gb.getFccode())
        {
          case 5:  gb.setProgram("C/P Discretionary");
                   break;
          case 6:  gb.setProgram("C/P Statutory");
                   break;
          case 7:  gb.setProgram("C/P Coordinated");
                   break;
          case 20:  gb.setProgram("State Aid: CJH & NYHS");
                    break;
          case 40:  gb.setProgram("Adult Literacy");
                   break;
          case 42:  gb.setProgram("Family Literacy");
                   break;
          case 80:  gb.setProgram("LGRMIF");
                  break;
          case 86:  gb.setProgram("Construction");
                  break;
        }              
        results.add(gb);
      }        
      
    }
    catch(Exception e){
      System.err.println("error searchGrantsByInst() "+ e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }
    return results;
  }


  /**
     * method used to validate that lgrmif can submit 2 apps, but 1 or both Must be
     * of disastermgmt category. method added 2/7/11
     * mod 10/4/13 - the 62 counties can submit 2 apps (neither of which needs to be disastermgmt) per FC FY2014-15
     * @param gb
     * @param csb
     * @return
     */
  public boolean validateOneDisasterApp(GrantBean gb, CoversheetBean csb){
        boolean canSubmit = false;
               
        try{        
          conn = initializeConn();         
            
          //check if this inst is one of 62 counties - they can submit 2 apps of any category
          ps = conn.prepareStatement("select inst_id, popular_name from sed_institutions where inst_type_code=11 "+
               " and inst_sub_type_code=1 and inst_id=? " + 
               " and inactive_date is null ");
          ps.setLong(1, gb.getInstID());
          rs = ps.executeQuery();
          while(rs.next()){
              canSubmit = true;//results indicate inst is a county - can submit 2 apps any category
              //System.out.println("county - can submit anything");
          }
            
            
          if(!canSubmit){//not a county - check any other apps that this inst has submitted
              //System.out.println("not county -> validate");
            
            //check if this inst has submitted any other lgrmif apps for this fy.
             ps = conn.prepareStatement("select grants.id as grantid, fc_code, fy_code, "+
             " grant_submissions.id as submitid, version, co_institutions.inst_id, pc_id " + 
             " from grants, grant_submissions, co_institutions " + 
             " where grants.id=grant_submissions.gra_id " + 
             "and grants.id=co_institutions.gra_id and is_lead='Y' and inst_id=? " + 
             "and fc_code=80 and fy_code=? and version='Initial' ");
             ps.setLong(1, gb.getInstID());
             ps.setInt(2, gb.getFycode());
             rs = ps.executeQuery();
                          
             int projcategory =0;
             while(rs.next()){//if results=true, inst has submitted another app for this fy
                 projcategory = rs.getInt("pc_id");
             }         
             //System.out.println("projcategory "+projcategory);
             //---------------------------------------------------------
             if(projcategory == 0){//no other apps submitted; allow this app to be submitted
                 canSubmit = true;
             }
             else if(projcategory >0){//another app was already submitted; verify category
                 //if submitted app or this app are disastermgt, then ok to continue submitting
                 if(projcategory ==1 || csb.getProjcategoryId()==1)
                    canSubmit = true;                    
             }
          }
          //System.out.println("canSubmit "+canSubmit);
        
        }catch(Exception e){
            System.err.println("error validateOneDisasterApp() "+ e.getMessage().toString());
        }
        finally{
          Close(rs);
          Close(ps);
          Close(conn);
        }
        return canSubmit;
   }
   
   
   /**
     * For LGRMIF; if project category is GIS, search lead and participant inst_id's
     * and verify that at least 1 inst_id is a county
     * @param grantid
     * @return
     */
   public boolean validateCountyForGISCategory(long grantid){
          boolean canSubmit = false;
          ArrayList allinstitutions = new ArrayList();
          
          try{
          //get all institutions for this grant app
           conn = initializeConn();         
           ps = conn.prepareStatement("select i.id, i.is_lead, i.gra_id, i.inst_id, s.popular_name, " + 
           "s.inst_type_code, s.inst_sub_type_code from co_institutions i, sed_institutions s "+
           "where i.inst_id=s.inst_id and gra_id=?");
           ps.setLong(1, grantid);
           rs = ps.executeQuery();
                      
           while(rs.next()){
               SearchResultBean sb = new SearchResultBean();
               sb.setInstitution(rs.getString("popular_name"));
               sb.setInstTypeCode(rs.getInt("inst_type_code"));
               sb.setInstSubTypeCode(rs.getInt("inst_sub_type_code"));
               allinstitutions.add(sb);
           }           
          
           //for each inst; check if sedref type codes indicate a county
           for(int i=0; i<allinstitutions.size(); i++){
               SearchResultBean sb = (SearchResultBean) allinstitutions.get(i);
               if(sb.getInstTypeCode()==11 && sb.getInstSubTypeCode()==1)
                    canSubmit = true; 
           }
          
          }catch(Exception e){
              System.err.println("error validateCountyForGISCategory() "+ e.getMessage().toString());
          }
          finally{
            Close(rs);
            Close(ps);
            Close(conn);
          }
          return canSubmit;
     }
     
   
  public GrantBean findFyDates(int fycode, GrantBean gb)
  {  
    try{
      conn = initializeConn();
    
      ps = conn.prepareStatement("select * from fiscal_years where code=?");
      ps.setInt(1, fycode);
      rs = ps.executeQuery();
      
      while(rs.next()){
        gb.setStartdate(rs.getDate("start_date"));
        gb.setEnddate(rs.getDate("end_date"));
      }        
      
    }
    catch(Exception e){
      System.err.println("error findFyDates() "+ e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }
    return gb;
  }
      
  
    public int getFiscalYearForGrant(long grantid)
    {  
      int fy=0;
      try{
        conn = initializeConn();
      
        ps = conn.prepareStatement("select fy_code from grants where id=?");
        ps.setLong(1, grantid);
        rs = ps.executeQuery();
        
        while(rs.next()){
          fy = rs.getInt("fy_code");
        }        
        
      }
      catch(Exception e){
        System.err.println("error getFiscalYearForGrant() "+ e.getMessage().toString());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }
      return fy;
    }
    
    public int getYearForGrant(long grantid)
    {  
      int fy=0;
      try{
        conn = initializeConn();
      
        ps = conn.prepareStatement("select grants.id, fy_code, code, description from "+
        " grants, fiscal_years where grants.fy_code=fiscal_years.code and id=?");
        ps.setLong(1, grantid);
        rs = ps.executeQuery();
        
        while(rs.next()){
          fy = rs.getInt("description");
        }        
        
      }
      catch(Exception e){
        System.err.println("error getYearForGrant() "+ e.getMessage().toString());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }
      return fy;
    }

    public String determineFiscalPeriodString(int fycode)
    {  
      String fyperiod="";
      java.text.DecimalFormat nft = new java.text.DecimalFormat("#00");
      //nft.setDecimalSeparatorAlwaysShown(false);

      try{
       if(fycode >= 86){//for FY's 1986 - 1999 
         fyperiod = "19"+ nft.format(fycode-1) + " - 19"+ nft.format(fycode);
       }
       else if(fycode >=0 && fycode <86){//for FY's 2000 - 2085
           fyperiod = "20"+ nft.format(fycode-1) + " - 20"+ nft.format(fycode);
       }
      }catch(Exception e){
        System.err.println("error determineFiscalPeriodString() "+ e.getMessage().toString());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }
      return fyperiod;
    }

  public HashMap populateDropDownList(HttpServletRequest request)
  {  
    HashMap newmap = new HashMap();
    Vector results;
    try{
      conn = initializeConn();
    
      ps = conn.prepareStatement("select id, descr from project_categories where "+
      " inactive_date is null order by id");
      rs = ps.executeQuery();
      results = new Vector();
      while(rs.next())
      {
        DropDownListBean dd = new DropDownListBean();
        dd.setId(rs.getInt("id"));
        dd.setDescription(rs.getString("descr"));
        results.add(dd);
      }        
      DropDownListBean[] catlist = (DropDownListBean[]) results.toArray(new DropDownListBean[0]); 
      newmap.put("category", catlist);
      
      
      ps = conn.prepareStatement("select id, descr from region_types where date_modified is null");
      rs = ps.executeQuery();
      results = new Vector();
      while(rs.next())
      {
        DropDownListBean dd = new DropDownListBean();
        dd.setId(rs.getInt("id"));
        dd.setDescription(rs.getString("descr"));
        results.add(dd);
      }        
      DropDownListBean[] reglist = (DropDownListBean[]) results.toArray(new DropDownListBean[0]);
      newmap.put("regions", reglist);
      
      
      ps = conn.prepareStatement("select id, descr from govt_types");
      rs = ps.executeQuery();
      results = new Vector();
      while(rs.next())
      {
        DropDownListBean dd = new DropDownListBean();
        dd.setId(rs.getInt("id"));
        dd.setDescription(rs.getString("descr"));
        results.add(dd);
      }        
      DropDownListBean[] govlist = (DropDownListBean[]) results.toArray(new DropDownListBean[0]);
      newmap.put("govttypes", govlist);
            
      //doris agencies
      PartInstDBbean pdb = new PartInstDBbean();
      ArrayList alldoris = pdb.loadDorisFromFile(request);
      DropDownListBean[] dorislist = (DropDownListBean[]) alldoris.toArray(new DropDownListBean[0]);
      newmap.put("dorislist", dorislist); 
      
    }
    catch(Exception e){
      System.err.println("error populateDropDownList() "+ e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }
    return newmap;
  }
  
  public ArrayList dropDownFiscalYears()
  {  
    ArrayList results=new ArrayList();
    try{
      conn = initializeConn();
    
      ps = conn.prepareStatement("select code, description from fiscal_years order by description");
      rs = ps.executeQuery();
      while(rs.next())
      {
        DropDownListBean dd = new DropDownListBean();
        dd.setId(rs.getInt("code"));
        dd.setDescription(rs.getString("description"));
        results.add(dd);
      }        
      
      }catch(Exception e){
        System.err.println("error dropDownFiscalYears() "+ e.getMessage().toString());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }
      return results;
   }
   
   
    public ArrayList dropDownFiscalYearsDesc()
    {  
      ArrayList results=new ArrayList();
      try{
        conn = initializeConn();
      
        ps = conn.prepareStatement("select code, description from fiscal_years order by description desc");
        rs = ps.executeQuery();
        while(rs.next())
        {
          DropDownListBean dd = new DropDownListBean();
          dd.setId(rs.getInt("code"));
          dd.setDescription(rs.getString("description"));
          results.add(dd);
        }        
        
        }catch(Exception e){
          System.err.println("error dropDownFiscalYearsDesc() "+ e.getMessage().toString());
        }
        finally{
          Close(rs);
          Close(ps);
          Close(conn);
        }
        return results;
     }
   
    public ArrayList dropDownMembersForSystem(long systemInstId)
    {  
      ArrayList results=new ArrayList();
      try{
        conn = initializeConn();
      
        ps = conn.prepareStatement("select lsm.id, lsm.inst_id, lsm.inst_id_has, " + 
        " initcap(popular_name) as popular_name " + 
        " from ldstateaid.library_system_mappings lsm " + 
        " left join sed_institutions on lsm.inst_id=sed_institutions.inst_id " + 
        "  where lsm.inst_id_has=? and end_date is null order by popular_name");
        ps.setLong(1, systemInstId);
        rs = ps.executeQuery();
        
        while(rs.next())
        {
          DropDownListBean dd = new DropDownListBean();
          dd.setIdLong(rs.getLong("inst_id"));
          dd.setDescription(rs.getString("popular_name"));
          results.add(dd);
        }        
        
        }catch(Exception e){
          System.err.println("error dropDownMembersForSystem() "+ e.getMessage().toString());
        }
        finally{
          Close(rs);
          Close(ps);
          Close(conn);
        }
        return results;
     }
   
   
    public ArrayList dropDownLibrarySystems()
    {  
      ArrayList results=new ArrayList();
      try{
        conn = initializeConn();
      
        ps = conn.prepareStatement("select initcap(popular_name) as popular_name, " + 
        " sed_institutions.inst_id, lsm.id from sed_institutions " + 
        "left join ldstateaid.library_system_mappings lsm " + 
        "on sed_institutions.inst_id=lsm.inst_id_has " + 
        "where inst_type_code=7 and inst_sub_type_code=1 " + 
        "and lsm.inst_id_has=lsm.inst_id order by popular_name");
        rs = ps.executeQuery();
        while(rs.next())
        {
          DropDownListBean dd = new DropDownListBean();
          dd.setId(rs.getInt("id"));
          dd.setDescription(rs.getString("popular_name"));
          dd.setIdLong(rs.getLong("inst_id"));
          results.add(dd);
        }        
        
        }catch(Exception e){
          System.err.println("error dropDownLibrarySystems() "+ e.getMessage().toString());
        }
        finally{
          Close(rs);
          Close(ps);
          Close(conn);
        }
        return results;
     }
   
    public ArrayList dropDownCounties()
    {  
      ArrayList results=new ArrayList();
      try{
        conn = initializeConn();
      
        ps = conn.prepareStatement("select county_code, initcap(description) as description from sed_counties");
        rs = ps.executeQuery();
        while(rs.next())
        {
          DropDownListBean dd = new DropDownListBean();
          dd.setId(rs.getInt("county_code"));
          dd.setDescription(rs.getString("description"));
          results.add(dd);
        }        
        
        }catch(Exception e){
          System.err.println("error dropDownCounties() "+ e.getMessage().toString());
        }
        finally{
          Close(rs);
          Close(ps);
          Close(conn);
        }
        return results;
     }
     
    public ArrayList dropDownFundCodes()
    {  
      ArrayList results=new ArrayList();
      try{
        conn = initializeConn();
      
        ps = conn.prepareStatement("select code, description from fund_codes");
        rs = ps.executeQuery();
        while(rs.next())
        {
          DropDownListBean dd = new DropDownListBean();
          dd.setId(rs.getInt("code"));
          dd.setDescription(rs.getString("description"));
          results.add(dd);
        }        
        
        }catch(Exception e){
          System.err.println("error dropDownFundCodes() "+ e.getMessage().toString());
        }
        finally{
          Close(rs);
          Close(ps);
          Close(conn);
        }
        return results;
     }
     
     
    public ArrayList dropDownProjCategories()
    {  
      ArrayList results=new ArrayList();
      try{
        conn = initializeConn();
      
        ps = conn.prepareStatement("select id, descr from project_categories order by id");
        rs = ps.executeQuery();
        while(rs.next())
        {
          DropDownListBean dd = new DropDownListBean();
          dd.setId(rs.getInt("id"));
          dd.setDescription(rs.getString("descr"));
          results.add(dd);
        }        
        
    }catch(Exception e){
      System.err.println("error dropDownProjCategories() "+ e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }
    return results;
 }
 
    //this will get active regions from table for lgrmif application/coversheet user data-entry
    public ArrayList dropDownRegions()
    {  
      ArrayList results=new ArrayList();
      try{
        conn = initializeConn();      
          //note: using date_modified as an "inactive date" for this table
        ps = conn.prepareStatement("select id, descr from region_types where date_modified is null order by id");
        rs = ps.executeQuery();
        while(rs.next())
        {
          DropDownListBean dd = new DropDownListBean();
          dd.setId(rs.getInt("id"));
          dd.setDescription(rs.getString("descr"));
          results.add(dd);
        }        
        
    }catch(Exception e){
      System.err.println("error dropDownRegions() "+ e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }
    return results;
  }
    
    
  //this will get all regions in table; regardless if they are inactive. used for lgrmif report selection
  public ArrayList dropDownRegionsAll()
  {  
    ArrayList results=new ArrayList();
    try{
      conn = initializeConn();      
      ps = conn.prepareStatement("select id, descr from region_types order by id");
      rs = ps.executeQuery();
      while(rs.next())
      {
        DropDownListBean dd = new DropDownListBean();
        dd.setId(rs.getInt("id"));
        dd.setDescription(rs.getString("descr"));
        results.add(dd);
      }        
      
  }catch(Exception e){
    System.err.println("error dropDownRegionsAll() "+ e.getMessage().toString());
  }
  finally{
    Close(rs);
    Close(ps);
    Close(conn);
  }
  return results;
  }
  
  
  
  
  /**
   * For state aid NYHS/CJH - BLilley wants project number to be same every year: 1=NYHS and 2=CJH.
   * database uses sequence for proj_seq_num.  On screen, show 1 or 2.
   * @param instId
   * @return
   */
  public long handleStateAidProjNumbers(long instId)
  {  
	long projSeqNum=0;
    try{
	    	//per BL; need 4 digit proj num to be same every year for stateaid
	        if(instId==800000047346L)
	        	projSeqNum=1;
	        else if(instId==800000047794L)
	        	projSeqNum=2;
      
	  }catch(Exception e){
	    System.err.println("error handleStateAidProjNumbers() "+ e.getMessage().toString());
	  }  
	  return projSeqNum;
  }
  
  
}//Closes class
