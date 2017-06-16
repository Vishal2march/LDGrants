package construction;

import clobpackage.ClobBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypackage.DBHandler;
import mypackage.DescriptionBean;
import mypackage.DistrictBean;
import mypackage.GrantBean;
import mypackage.UserBean;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SaveCnJustificationAction extends Action{
    
    private DBHandler dbh = new DBHandler();
    private ConstructionDBbean cdb = new ConstructionDBbean();
    
    
  public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) throws Exception{    
        
    HttpSession sess = request.getSession();
                  
    try{       
        boolean userID = (sess.getAttribute("lduser") != null); 
        if (!userID && sess.isNew())         
          mapping.findForward("timeout");
                    
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
        SystemAssignBean sb = (SystemAssignBean) form;//get data entry          
        UserBean lduser = (UserBean) sess.getAttribute("lduser");
      
        ////SAVE justification data entry  (this saves narrative)
        int outcome = cdb.updateReduceJustification(sb, lduser.getUserid());
        
        //starting fy2014-15; save the reduce match criteria checkboxes
        cdb.handleSaveReduceMatchCriteria(sb, lduser);
          
        ////REFRESH ALL DATA   
        GrantBean gb=dbh.getRecordBean(grantid); 
        gb = cdb.getAmtRecommendCostForGrant(gb, grantid);
        request.setAttribute("thisGrant", gb);  
        
        //get the senate, congress districts, etc.
        DistrictBean db = dbh.getDistrictInfo(gb.getInstID());
        request.setAttribute("distBean", db);
        
        //get the narrative from the db and set to descrBean
        ClobBean cb = new ClobBean();
        cb.setGrantid(grantid);
        cb.openOracleConnection();      
        cb.getClobNarrative(95); 
        cb.closeOracleConnection();
          
        DescriptionBean pdb = new DescriptionBean();
        pdb.setNarrative(cb.getData());
        request.setAttribute("projNarrative", pdb);  
        
        //get the justificationExists and justification narrative
        SystemAssignBean sab = cdb.getSystemAssignRecord(grantid);
        if(gb.getFycode()>14)//starting fy14-15; new reduce match criteria/fields
            sab = cdb.getReduceMatchTypes(sab);
        request.setAttribute("assignBean", sab);
     
    }catch(Exception e){System.out.println("error SaveCnJustificationAction "
                          +e.getMessage().toString());}
    
    return(mapping.findForward("success"));  
  }
}
