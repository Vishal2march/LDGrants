package reviewers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypackage.ApprovalsDBbean;
import mypackage.BudgetDBHandler;
import mypackage.CoversheetBean;
import mypackage.DBHandler;
import mypackage.EligibilityDbBean;
import mypackage.GrantBean;
import mypackage.PanelDbBean;
import mypackage.PanelReviewBean;
import mypackage.RatingBean;
import mypackage.ReviewerAssignDBbean;
import mypackage.UserBean;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LgDelibNarrativeAction extends Action{
    
    private  DBHandler dbh = new DBHandler();
    
    public ActionForward execute( ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) throws Exception{    
       
         HttpSession sess = request.getSession();
         //check for session timeout
         boolean userID = (sess.getAttribute("lduser") != null); 
         if (!userID && sess.isNew())
           return mapping.findForward("timeout");
         
         try{         
             //get reviewer info
             UserBean userb = (UserBean) sess.getAttribute("lduser");
             ApprovalsDBbean adb = new ApprovalsDBbean();
             
             //get panel rating user was working on
             PanelDbBean pdb = new PanelDbBean();
             PanelReviewBean prb = (PanelReviewBean) form;
             prb.setUserid(userb.getUserid());       
                          
             //update only the decision narratives
             pdb.updateDelibDecision(prb, userb);
                                  
             //get new panel score/justifications
             PanelReviewBean pb = pdb.getDeliberationPanelGrant(prb.getPanelgrantid());
             
             GrantBean gb=dbh.getRecordBean(pb.getGrantid()); 
             sess.setAttribute("thisGrant", gb);  
                               
            //get bonus scoring
            CoversheetBean csb =new CoversheetBean();
            if(gb.getFycode()<12){
               EligibilityDbBean edb = new EligibilityDbBean();
               csb = edb.getLgBonusScoring(pb.getGrantid());
               request.setAttribute("CoverBean", csb);                   
            }    
        
            BudgetDBHandler bdh = new BudgetDBHandler(); 
            int totalAmtAppr = bdh.calcTotalAmtApproved(prb.getGrantid(), 0);//from budget pages
            pb.setTotamtappr(totalAmtAppr);
            pb.setTotamtreq(bdh.calcTotalAmtRequested(prb.getGrantid(), 0)); 
                     
            //get averages of all reviewer ratings for this grant
            ReviewerAssignDBbean rab = new ReviewerAssignDBbean();
            RatingBean rb =rab.getLgAvgRatingByCategory(prb.getPanelgrantid(), gb.getFycode());
              
            if(gb.getFycode()>12){  
              //get panel ratings for this grant; if rating doesnot extist->use avg rating
              rb = pdb.getDeliberationRatings(prb.getPanelgrantid(), rb);  
              rb.setGraid(prb.getGrantid());
              rb.setPanelgrantId(prb.getPanelgrantid());
              rb.setStatus(pb.getStatus());
              //starting fy 2012-13, panel score is read-only; sum of delib rating categories
              pb.setFinalscore(rb.getFinalScoreSum());
            }
            request.setAttribute("RatingBean", rb);   
              
            //find out if application was approved or denied             
            pb.setInitialappr( adb.isInitialAppApproved(prb.getGrantid()) );
            pb.setBonuspts(csb.getScore());
            request.setAttribute("PanelReviewBean", pb);
                                   
            request.setAttribute("anchorSection", "anchorSection");    
             
    }catch(Exception e){
      System.out.println("error LgDelibNarrativeAction "+e.getMessage().toString());
    }
    return mapping.findForward("success");    
    }
    
    
    
}
