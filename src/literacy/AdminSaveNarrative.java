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

import mypackage.UserBean;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AdminSaveNarrative  extends Action
{
  private DBHandler dbh = new DBHandler();
  private NarrativeDBbean ndb = new NarrativeDBbean();

  public ActionForward execute(ActionMapping mapping, ActionForm form,
     HttpServletRequest request, HttpServletResponse response) throws Exception
    {    
       HttpSession sess = request.getSession();
       String finalTarget="";
       int expcode=0;
       //check for session timeout
        boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
        if (!userID && sess.isNew())
          return mapping.findForward("timeout");
        
        
      try{
           String grantnum = (String) sess.getAttribute("grantid");
           long grantid = Long.parseLong(grantnum);     
           DescriptionBean pdb = (DescriptionBean) form;            
           finalTarget= pdb.getModule();
           
           //check for user permissions
           UserBean lduser = (UserBean) sess.getAttribute("lduser");
                    
      //-----------------------------------------------------------------------
           
           //check if user entered text
           if(pdb.getNarrative()!=null && !pdb.getNarrative().equals(""))
           {
             if(pdb.getId()==0)//insert blank record first
                ndb.insertNarrativeRecord(lduser, grantid, pdb.getNarrTypeID());
              
              //update record w/ text
              ndb.updateNarrative(lduser, grantid, pdb);
           }
          
          //refresh all variables on page
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);  
          
          if(asb.getFycode()>16)
              finalTarget+="new";
          
          if(pdb.getNarrTypeID()==120)
              finalTarget = "fladminnarrnysl";
          //starting FY16-19; budget narratives will also have budget summary jsp
          else if(pdb.getNarrTypeID()==38){
                expcode = 40;
                //purchased
                if(asb.getFycode()>16)                  
                  finalTarget+="budget";
          }
          else if(pdb.getNarrTypeID()==40){
              expcode = 46;
              //travel
              if(asb.getFycode()>16)                  
                finalTarget+="budget";
          }
          else if(pdb.getNarrTypeID()==39){
              expcode = 45;
              //supply
              if(asb.getFycode()>16)                  
                finalTarget+="budget";
          }
          else if(pdb.getNarrTypeID()==121){
              expcode = 20;
              //equip
              if(asb.getFycode()>16)                  
                finalTarget+="budget";
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
               
          
          
          //get budget summary details for budget narrs
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
        System.out.println("error AdminSaveNarrative "+ e.getMessage().toString());
      }
      return mapping.findForward(finalTarget);        
       
    }
}
