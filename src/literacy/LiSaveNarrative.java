/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  LiSaveNarrative.java
 * Creation/Modification History  :
 *
 * SH   Sept 2008      Created
 *
 * Description
 * This class will save al/fl applicant narratives for initial, interim, and final
 * narratives. Then forward to appropriate page.
 *****************************************************************************/
package literacy;
import clobpackage.ClobBean;

import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.AppStatusBean;
import mypackage.BudgetCollectionBean;
import mypackage.DBHandler;
import mypackage.DescriptionBean;
import mypackage.GrantBean;
import mypackage.NarrativeDBbean;
import mypackage.TotalsBean;
import mypackage.UserBean;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LiSaveNarrative extends Action
{
  private DBHandler dbh = new DBHandler();
  private NarrativeDBbean ndb = new NarrativeDBbean();

  public ActionForward execute(ActionMapping mapping, ActionForm form,
     HttpServletRequest request, HttpServletResponse response) throws Exception
    {    
       HttpSession sess = request.getSession();
       String finalTarget="";
       //check for session timeout
        boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
        if (!userID && sess.isNew())
          return mapping.findForward("timeout");
    
    
      try{
           String grantnum = (String) sess.getAttribute("grantid");
           long grantid = Long.parseLong(grantnum);     
           DescriptionBean pdb = (DescriptionBean) form;            
           finalTarget= pdb.getModule();
           int expcode =0;
           
           //check for user permissions
           UserBean lduser = (UserBean) sess.getAttribute("lduser");
           String permiss="";
          
           if(pdb.getModule().indexOf("al")>-1)
             permiss=lduser.getPrgal();
           else if(pdb.getModule().indexOf("fl")>-1 )
             permiss=lduser.getPrgfl();
          
        
            
           if(permiss.equals("read")){
              request.setAttribute("errormsg", "User does not have access to update the application");
              return mapping.findForward("authorize");
           }         
           
           if(pdb.getNarrTypeID()==1){
               //validate summary descr size
               ActionErrors ae =null;
               ae = pdb.validateSummary(mapping, request, 2000);
               if(ae !=null && (ae.size() > 0)){
                 request.setAttribute(Globals.ERROR_KEY, ae); 
                 return (mapping.findForward(pdb.getModule()+"summary"));
               }       
           }
//-----------------------------------------------------------------------                            
           
           //check if user entered text; either insert/update to db
           if(pdb.getNarrative()!=null && !pdb.getNarrative().equals("") && pdb.getNarrative().trim().length() > 0)
           {
             if(pdb.getId()==0)//insert blank record first
                ndb.insertNarrativeRecord(lduser, grantid, pdb.getNarrTypeID());
              
              //update record w/ text
              ndb.updateNarrative(lduser, grantid, pdb);
           } else if (pdb.getNarrative().equals("") || pdb.getNarrative().trim().length() <= 0){
                   ndb.deleteNarrativeRecord(grantid, pdb.getNarrTypeID());
               }
//-----------------------------------------------------------------------
          
          
          //refresh all variables on page
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);  
          
          if(pdb.getNarrTypeID()==1)//type 1 'summary descr' has separate jsp page
            finalTarget=pdb.getModule()+"summary";
          else if(pdb.getNarrTypeID()==120){//1/19/16 new 'nysl' narrative separate jsp from others per KBALSEN
              finalTarget=pdb.getModule() +"nysl";
          }
          else if(pdb.getNarrTypeID()==38){//new starting FY16-19; budget narratives will also have budget summary jsp
              expcode = 40;
              //purchased
              if(asb.getFycode()>16)                  
                finalTarget= pdb.getModule() + "purchased";
          }
          else if(pdb.getNarrTypeID()==40){
              expcode = 46;
              //travel
              if(asb.getFycode()>16)                  
                finalTarget= pdb.getModule() +"travel";
          }
          else if(pdb.getNarrTypeID()==39){
              expcode = 45;
              //supply
              if(asb.getFycode()>16)                  
                finalTarget= pdb.getModule() + "supply";
          }
          else if(pdb.getNarrTypeID()==121){
              expcode = 20;
              //equip
              if(asb.getFycode()>16)                  
                finalTarget= pdb.getModule() + "equipment";
          }
          else if(pdb.getNarrTypeID()==122 || pdb.getNarrTypeID()==125 || pdb.getNarrTypeID()==50){
            //new 2/22/16 per kbalsen;  final narr for 16-19 split into 3 pages
            finalTarget = pdb.getModule() + "year1";            
          }
          else if(pdb.getNarrTypeID()==123 || pdb.getNarrTypeID()==126 || pdb.getNarrTypeID()==64){
            //new 2/22/16 per kbalsen;  final narr for 16-19 split into 3 pages
            finalTarget = pdb.getModule() + "year2";            
          }
          else if(pdb.getNarrTypeID()==124 || pdb.getNarrTypeID()==127 || pdb.getNarrTypeID()==105 ||
                  pdb.getNarrTypeID()==118 || pdb.getNarrTypeID()==47){
            //new 2/22/16 per kbalsen;  final narr for 16-19 split into 3 pages
            finalTarget = pdb.getModule() + "year3";            
          }
          
          
          
          //get new/updated version of the project narrative
          ClobBean cb = new ClobBean();
          cb.setGrantid(grantid);
          cb.openOracleConnection();      
          cb.getClobNarrative(pdb.getNarrTypeID()); 
          cb.closeOracleConnection();             
                 
          DescriptionBean db = new DescriptionBean();
          db.setNarrative(cb.getData());
          db.setId(cb.getNarrID());
          db.setNarrTypeID(pdb.getNarrTypeID());
          db.setNarrativeDescr(ndb.getNarrativeInstructionsLi(pdb.getNarrTypeID(), asb.getFccode(), asb.getFycode()));
          db.setNarrativeTitle(ndb.getNarrativeTitle(pdb.getNarrTypeID()));
          db.setModule(pdb.getModule());
          request.setAttribute("projNarrative", db);     
                              
          if(pdb.getModule().indexOf("b")>-1)
          {
            String tab = request.getParameter("litpge");
            finalTarget+=tab;
            
            //refresh budget records if saving a budget narrative
            BudgetCollectionBean bc = dbh.getApcntBudgetActionLD(grantid, asb, Integer.parseInt(tab), true);
            request.setAttribute("BudgetCollectionBean", bc);
            
            GrantBean gb = dbh.getRecordBean(grantid);
            HashMap tb = dbh.getProjectBudgetTotalsByFy(grantid, false, asb, gb.getInstID());
            request.setAttribute("totalsMap", tb);
          }
          else{//new 1/19/16 - for NON-budget narratives, need to split by new narrative sections per KBALSEN
              
              if(asb.getFycode()>16)
                  finalTarget+= "new";              
          }
          
          
          //new for 2016-19 budget summary narratives, need to get budget records
          if(pdb.getNarrTypeID()==38 || pdb.getNarrTypeID()==40 || pdb.getNarrTypeID()==39 || pdb.getNarrTypeID()==121){
              
              //refresh budget data and redisplay page
              BudgetSummaryDbBean bsdb = new BudgetSummaryDbBean();
              ArrayList allexp = bsdb.getBudgetSummariesForExp(grantid, expcode);
              
              BudgetCollectionBean bc = new BudgetCollectionBean();
              bc.setAllSummaryRecords(allexp);
              request.setAttribute("BudgetCollectionBean", bc);
          }
                                  
      }
      catch(Exception e){
        System.out.println("error LiSaveNarrative "+ e.getMessage().toString());
      }
      return mapping.findForward(finalTarget);        
    }
}