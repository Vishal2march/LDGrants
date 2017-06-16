package literacy;

import clobpackage.ClobBean;

import construction.ConstructionDBbean;

import construction.FinalExpenseBean;

import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypackage.AppStatusBean;
import mypackage.BudgetCollectionBean;
import mypackage.BudgetDeleteBean;
import mypackage.BudgetSummaryBean;
import mypackage.DBHandler;
import mypackage.DescriptionBean;
import mypackage.NarrativeDBbean;
import mypackage.TotalsBean;
import mypackage.UserBean;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class BudgetSummaryAction  extends DispatchAction{
    public BudgetSummaryAction() {
        super();
    }
  private BudgetSummaryDbBean bsdb = new BudgetSummaryDbBean();
  private DBHandler dbh = new DBHandler();
  private NarrativeDBbean ndb = new NarrativeDBbean(); 
 
   
   
  public ActionForward addrow(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) throws Exception 
  {         
         String finalTarget = "";
         HttpSession sess = request.getSession();
         if(checkSessionTimeout(sess))
            return mapping.findForward("timeout");
        
         try {    
             String grantnum = (String) sess.getAttribute("grantid");
             long grantid = Long.parseLong(grantnum);             
             String expcode = request.getParameter("c");
             String program = request.getParameter("p");
             String narrType = request.getParameter("narr");
                        
             //get all summary records for this budget/narr category
             ArrayList allrecs = bsdb.getBudgetSummariesForExp(grantid, Integer.parseInt(expcode));
             
             //add a blank row
             BudgetSummaryBean bs = new BudgetSummaryBean();
             bs.setExpensecode(Integer.parseInt(expcode));
             bs.setGrantId(grantid);
             allrecs.add(bs);
             
             //set to collection - for looping on jsp page
             BudgetCollectionBean bc = new BudgetCollectionBean();
             bc.setAllSummaryRecords(allrecs);
             request.setAttribute("BudgetCollectionBean", bc);
           
                          
              //navigate to selected narr/budget page
             finalTarget= getReturnPage(Integer.parseInt(expcode), program);
             
             //TODO: get narrative
             getNarrativeForBudget(request, grantid, Integer.parseInt(narrType), program);
                                 
         } catch (Exception e) {
            System.out.println("error BudgetSummaryAction "+e.getMessage());
            log.error(e.getMessage().toString());
         }   
         return mapping.findForward(finalTarget);
   }
  
  
  
  
  
  
  
  
  public ActionForward addOrUpdate(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
  {         
          String finalTarget = "";
          HttpSession sess = request.getSession();
          if(checkSessionTimeout(sess))
             return mapping.findForward("timeout");
         
          try {    
              UserBean ub = (UserBean) sess.getAttribute("lduser");
              String grantnum = (String) sess.getAttribute("grantid");
              long grantid = Long.parseLong(grantnum);             
              String expcode= (String) request.getParameter("c"); 
              String program = request.getParameter("p");
              String narrType = request.getParameter("narr");
              
              
              //navigate to selected narr/budget page
              finalTarget= getReturnPage(Integer.parseInt(expcode), program);
              
              
              /////////////////////validate amount is an integer amount ///////////////////////           
              ActionForm myform = (ActionForm) request.getAttribute("BudgetCollectionBean");
              //cast the action form to a BudgetCollectionBean 
              BudgetCollectionBean b = (BudgetCollectionBean) myform;
               
              //get the list of rows from the form
              List expList = b.getAllSummaryRecords();
               
              if(expList !=null){
                ActionErrors ae = null;//validate each record     
                for(int i=0; i<expList.size(); i++)
                {
                    BudgetSummaryBean bs = (BudgetSummaryBean)expList.get(i);   
                    ae = bs.validate(mapping, request);//validate() returns ActionErrors
                                         
                    if(ae !=null && (ae.size() > 0)){
                      request.setAttribute(Globals.ERROR_KEY, ae);//return if errors                       
                                             
                      return (mapping.findForward(finalTarget));                       
                    }                       
                }
              }
              ////////////////////////////////////////////////
                  
              //if we get here; validation has passed; save all records
              int outcome = bsdb.insertOrUpdateBudgetSummary(b.getAllSummaryRecords(), ub.getUserid());
                                 
              //refresh all variables/data and redisplay page
              ArrayList allexp = bsdb.getBudgetSummariesForExp(grantid, Integer.parseInt(expcode));
              
              BudgetCollectionBean bc = new BudgetCollectionBean();
              bc.setAllSummaryRecords(allexp);
              request.setAttribute("BudgetCollectionBean", bc);
               
              //TODO: get corresponding narrative text
              getNarrativeForBudget(request, grantid, Integer.parseInt(narrType), program);
                                                           
          } catch (Exception e) {
             System.out.println("error BudgetSummaryAction "+e.getMessage());
             log.error(e.getMessage().toString());
          }   
          return mapping.findForward(finalTarget);
  }
  
  
  //////////////////////////DELETE/////////////////////////////////////////////////////
  
  
  public ActionForward confirmdelete(ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response) throws Exception 
  {             
    HttpSession sess = request.getSession(); 
    if(checkSessionTimeout(sess))
      return mapping.findForward("timeout");
    String finalTarget="";
   
    try{
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
         
        //set parameters to bean
        BudgetDeleteBean bb = new BudgetDeleteBean();
        bb.setGrantid(grantid);
        bb.setId(Long.parseLong(request.getParameter("id").trim()));
        bb.setModule(request.getParameter("p"));
        
        finalTarget = bb.getModule() + "confirmdelete";
                
        String expcode =request.getParameter("c").trim();
        bb.setTab(Integer.parseInt(expcode));
        
        String narr = request.getParameter("narr").trim();
        bb.setNarrativeTypeId(Integer.parseInt(narr));
       
        //get year/amt of record
        //bb.setDescription(cdb.getExpenseDescription(bb.getId()));          
        request.setAttribute("deleteBean", bb);
    }
    catch(Exception e) {
      log.error("BudgetSummaryAction "+e.getMessage().toString());
    }        
    return mapping.findForward(finalTarget);      
  }
  
  
  public ActionForward delete(ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response) throws Exception 
  {             
    String finalTarget="";
    HttpSession sess = request.getSession(); 
    if(checkSessionTimeout(sess))
      return mapping.findForward("timeout");
   
    try{
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
        
        String id = request.getParameter("id");
        String exp=request.getParameter("tab");
        String prog=request.getParameter("p");
        String narr = request.getParameter("narr");
        int expcode=Integer.parseInt(exp);
        
        
        //delete record
        int outcome = bsdb.deleteBudgetSummary(Long.parseLong(id));
        
        //refresh all variables/data and redisplay page
        ArrayList allexp = bsdb.getBudgetSummariesForExp(grantid, expcode);
        
        BudgetCollectionBean bc = new BudgetCollectionBean();
        bc.setAllSummaryRecords(allexp);
        request.setAttribute("BudgetCollectionBean", bc);
        
                          
        //navigation back to budget/narrative page
        finalTarget = getReturnPage(expcode, prog);
        
        //get narrative for id
        getNarrativeForBudget(request, grantid, Integer.parseInt(narr), prog);
    }
    catch(Exception e) {
      log.error("BudgetSummaryAction "+e.getMessage().toString());
    }        
    return mapping.findForward(finalTarget);      
  }
  
  
  
  
  
  public void getNarrativeForBudget(HttpServletRequest request, long grantid, int narrTypeId, String program){
    
      try{
        DescriptionBean pdb = new DescriptionBean();    
          
        //get the narrative from the db and set to descrBean 
        ClobBean cb = new ClobBean();
        cb.setGrantid(grantid);
        cb.openOracleConnection();      
        cb.getClobNarrative(narrTypeId); 
        cb.closeOracleConnection();
          
        AppStatusBean ab = dbh.getApplicationStatus(grantid);
        request.setAttribute("appStatus", ab);
          
        pdb.setNarrative(cb.getData());
        pdb.setId(cb.getNarrID());
        pdb.setNarrTypeID(narrTypeId);  
        pdb.setNarrativeTitle(ndb.getNarrativeTitle(narrTypeId));
        pdb.setNarrativeDescr(ndb.getNarrativeInstructionsLi(narrTypeId, ab.getFccode(), ab.getFycode()));        
        pdb.setModule(program+"narr");
          
        request.setAttribute("projNarrative", pdb);   
        
      }catch(Exception e){System.out.println("BudgetSummaryAction "+ e.getMessage().toString());}
  }
  
  
  
  public String getReturnPage(int expcode, String program){
    
    String finalTarget="";
    
    try{
        //navigate to selected narr/budget page
        switch(expcode){
            case 45:  finalTarget= program + "supplynarr";
                      break;
            case 20:  finalTarget= program + "equipmentnarr";
                      break;
            case 40:  finalTarget= program + "purchasednarr";
                      break; 
            case 46:  finalTarget= program + "travelnarr";
                      break; 
        }
        
    }catch(Exception e){System.out.println("BudgetSummaryAction "+ e.getMessage().toString());}
    
    return finalTarget;    
  }
  
  
  
  public boolean checkSessionTimeout(HttpSession sess)
  {
   boolean timeout = false;
   //check for session timeout
   boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
   if (!userID && sess.isNew())
   {      
     System.out.println("SESSION TIMED OUT LiNarrativeAction");
     timeout = true;
   }      
   return timeout;
  }
  
  
}
