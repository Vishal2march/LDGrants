package construction;

import java.util.List;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypackage.RevAssignCollectionBean;
import mypackage.UserBean;

import mypackage.VendorBean;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SaveCnReviewSubmit extends Action{
    
    private ConstructionDBbean cdb = new ConstructionDBbean();
    
    public ActionForward execute(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws Exception
      {
        ActionErrors ae = null;     
        HttpSession sess = request.getSession();
        if(checkSessionTimeout(sess))
            return mapping.findForward("timeout");
          
        try{
          int fycode = 0;//fy the user is looking at
          UserBean userb = (UserBean) sess.getAttribute("lduser");
          RevAssignCollectionBean allItems = (RevAssignCollectionBean) form;
       
          // get the list of construct assignment records 
          List assignList = allItems.getAllConstructionAssigns();   
          
          if(assignList!=null){
          
              //check that amtrecommend is not > 50%amt
              for(int i=0; i<assignList.size(); i++)
              {
                SystemAssignBean sb = (SystemAssignBean)assignList.get(i);   
                ae = sb.validate(mapping, request);//validate() returns ActionErrors
                
                 if(ae !=null && (ae.size() > 0))
                 {
                   request.setAttribute(Globals.ERROR_KEY, ae); 
                   return (mapping.findForward("fail"));
                 }                                       
              }  
              
                        
          
             //update the recommendAmt and submit to LD fields for each assignment
             cdb.handleSystemSubmitMember(assignList, userb.getUserid());         
             
             //for each application submitted; lock out system and member
             cdb.handleLockAllLdSubmissions(assignList, userb.getUserid());
             
             //this gets the FY the user is working on, so the same FY data can be redisplayed
             for(int i=0; i<assignList.size(); i++){
                 SystemAssignBean sab=(SystemAssignBean) assignList.get(i);
                 fycode =sab.getFycode();
             }
          }
                              
           //redisplay the 'submit to ld' list w/ data for the last FY chosen
           Vector allapps = cdb.getMembersForSubmitList(fycode, userb.getInstid());
 
            //set all assign records to collection bean
            RevAssignCollectionBean rac = new RevAssignCollectionBean();
            rac.setAllConstructionAssigns(allapps);
            
            //get total pls allocation, amtrecommend so far
            ConstructAllocationDbBean cadb = new ConstructAllocationDbBean();
            AllocationYearBean ab = cadb.calcAllocAndAwardForPlsFy(userb.getInstid(), fycode);
            request.setAttribute("allocationBean", ab);
                              
            //get totals for amtreq and amtrecommend
            long totreq=0, totrecom=0;
            for(int j=0; j<allapps.size(); j++){
                SystemAssignBean sb=(SystemAssignBean)allapps.get(j);
                totreq+= sb.getAmtrequested();
                totrecom+= sb.getRecommendAmt();
            }
            rac.setTotalAmountRequest(totreq);
            rac.setTotalAmountRecommend(totrecom);
            rac.setInitialAlloc(ab.getInitialAlloc());
            request.setAttribute("AssignCollectionBean", rac);
            
            
        }catch(Exception e){
          System.out.println("error SaveCnReviewSubmit "+e.getMessage().toString());
        }
        return mapping.findForward("success");
      }
      
      
    public boolean checkSessionTimeout(HttpSession sess)
    {
        boolean timeout = false;
        //check for session timeout
        boolean userID = (sess.getAttribute("lduser") != null);
        if (!userID && sess.isNew()){      
          timeout = true;
        }      
        return timeout;
    }
      
}
