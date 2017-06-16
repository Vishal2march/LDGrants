package reviewers;

import java.util.HashMap;

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

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LgDelibRatingAction extends Action{
    
    private  DBHandler dbh = new DBHandler();
    private PanelDbBean pdb = new PanelDbBean();
    private ApprovalsDBbean adb = new ApprovalsDBbean();
    
    
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
            
            //get rating bean user was working on
            RatingBean rbform = (RatingBean) form;
            
            //VALIDATE rating entries
             ActionErrors ae = null;   
             ae = rbform.validateLgDeliberation(mapping, request);
             
            if(ae !=null && (ae.size() > 0)){
              request.setAttribute(Globals.ERROR_KEY, ae); 
              return (mapping.findForward("fail"));
            }      
                        
            //get all ratingtypeId's from db, used to determine whether to insert/update score
            ReviewerAssignDBbean rdb = new ReviewerAssignDBbean();
            HashMap allRateTypes = rdb.getExistingRatingTypes(rbform.getPanelgrantId(), "delib");
            
            //insert or update the scores for each rating category 
            pdb.handleSaveDelibRatings(rbform, allRateTypes, userb.getUserid());
            
    //////////////////refresh all data on page and redisplay
    
            //get new panel score/justifications
            PanelReviewBean pb = pdb.getDeliberationPanelGrant(rbform.getPanelgrantId());
            
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
            int totalAmtAppr = bdh.calcTotalAmtApproved(pb.getGrantid(), 0);//from budget pages
            pb.setTotamtappr(totalAmtAppr);
            pb.setTotamtreq(bdh.calcTotalAmtRequested(pb.getGrantid(), 0)); 
                          
            //get averages of all reviewer ratings for this grant
            ReviewerAssignDBbean rab = new ReviewerAssignDBbean();
            RatingBean rb =rab.getLgAvgRatingByCategory(pb.getPanelgrantid(), gb.getFycode());
            
            if(gb.getFycode()>12){  
              //get panel ratings for this grant; if rating doesnot extist->use avg rating
              rb = pdb.getDeliberationRatings(pb.getPanelgrantid(), rb);  
              rb.setGraid(pb.getGrantid());
              rb.setPanelgrantId(pb.getPanelgrantid());
              rb.setStatus(pb.getStatus());
              //starting fy 2012-13, panel score is read-only; sum of delib rating categories
              pb.setFinalscore(rb.getFinalScoreSum());
            }
            request.setAttribute("RatingBean", rb);             
    
            //find out if application was approved or denied             
            pb.setInitialappr( adb.isInitialAppApproved(pb.getGrantid()) );
            pb.setBonuspts(csb.getScore());
            request.setAttribute("PanelReviewBean", pb);
            
            request.setAttribute("anchorSection", "anchorSection");
    
        }catch(Exception e){
          System.out.println("error LgDelibRatingAction "+e.getMessage().toString());
        }
        return mapping.findForward("success");    
    }
        
}
