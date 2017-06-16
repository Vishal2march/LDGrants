/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  LgRatingAction.java
 * Creation/Modification History  : 
 * SH   1/2010      Created
 *
 * Description
 * action class will save all LG at-home evaluation form scores & comments, recommendation,
 * recommend amt.  Then retrieve all eval form values and redisplay eval form.
 *****************************************************************************/
package reviewers;

import java.util.HashMap;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypackage.AppDatesBean;
import mypackage.BudgetDBHandler;
import mypackage.CoversheetBean;
import mypackage.EligibilityDbBean;
import mypackage.RatingBean;
import mypackage.ReviewerAssignDBbean;
import mypackage.ReviewerDBbean;
import mypackage.UserBean;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LgRatingAction extends Action{
   
   
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
             ReviewerAssignDBbean rdb = new ReviewerAssignDBbean();
             RatingBean rb = (RatingBean) form;
             rb.setRevid(userb.getRevid());
             rb.setUserid(userb.getUserid());       
                          
             //update recommendation, amt
             rdb.updateLgRecommendation(rb);
             
             //get all ratingtypeId's from db, used to determine whether to add/update score
             HashMap allRateTypes = rdb.getExistingRatingTypes(rb.getGrantassign(), rb.getModule());
                      
             HashMap updatescore = new HashMap();
             HashMap insertscore = new HashMap();
              if(rb.getProblemStr()!=null && !rb.getProblemStr().equals(""))
              {               
                if(allRateTypes.containsKey(new Integer(42)))
                  updatescore.put(new Integer(42), rb.getProblemStr());
                else
                  insertscore.put(new Integer(42), rb.getProblemStr());
              }
              
             if(rb.getRecordsStr()!=null && !rb.getRecordsStr().equals(""))
             {
               if(allRateTypes.containsKey(new Integer(43)))
                   updatescore.put(new Integer(43), rb.getRecordsStr());
                else
                   insertscore.put(new Integer(43), rb.getRecordsStr());
             }
             
             if(rb.getOtherfundStr()!=null && !rb.getOtherfundStr().equals(""))
             {               
               if(allRateTypes.containsKey(new Integer(44)))
                 updatescore.put(new Integer(44), rb.getOtherfundStr());
               else
                 insertscore.put(new Integer(44), rb.getOtherfundStr());
             }
             
             if(rb.getOutcomeStr()!=null && !rb.getOutcomeStr().equals(""))
             {               
               if(allRateTypes.containsKey(new Integer(45)))
                   updatescore.put(new Integer(45), rb.getOutcomeStr());
                else
                   insertscore.put(new Integer(45), rb.getOutcomeStr());
             }
             
             if(rb.getRecordprogramStr()!=null && !rb.getRecordprogramStr().equals(""))
             {               
               if(allRateTypes.containsKey(new Integer(46)))
                   updatescore.put(new Integer(46), rb.getRecordprogramStr());
                else
                   insertscore.put(new Integer(46), rb.getRecordprogramStr());
             }
             
             if(rb.getTimetableStr()!=null && !rb.getTimetableStr().equals(""))
             {               
               if(allRateTypes.containsKey(new Integer(47)))
                   updatescore.put(new Integer(47), rb.getTimetableStr());
                else
                   insertscore.put(new Integer(47), rb.getTimetableStr());
             }
             
             if(rb.getProjcategoryStr()!=null && !rb.getProjcategoryStr().equals(""))
             {               
               if(allRateTypes.containsKey(new Integer(48)))
                   updatescore.put(new Integer(48), rb.getProjcategoryStr());
                else
                   insertscore.put(new Integer(48), rb.getProjcategoryStr());
             }
             
             if(rb.getStaffsupportStr()!=null && !rb.getStaffsupportStr().equals(""))
             {               
               if(allRateTypes.containsKey(new Integer(49)))
                   updatescore.put(new Integer(49), rb.getStaffsupportStr());
                else
                   insertscore.put(new Integer(49), rb.getStaffsupportStr());
             }
             
             if(rb.getGovtsupportStr()!=null && !rb.getGovtsupportStr().equals(""))
             {               
               if(allRateTypes.containsKey(new Integer(50)))
                   updatescore.put(new Integer(50), rb.getGovtsupportStr());
                else
                   insertscore.put(new Integer(50), rb.getGovtsupportStr());
             }
             
             if(rb.getLongrangeStr()!=null && !rb.getLongrangeStr().equals(""))
             {               
               if(allRateTypes.containsKey(new Integer(51)))
                   updatescore.put(new Integer(51), rb.getLongrangeStr());
                else
                   insertscore.put(new Integer(51), rb.getLongrangeStr());
             }
             
             if(rb.getBudgetStr()!=null && !rb.getBudgetStr().equals(""))
             {               
               if(allRateTypes.containsKey(new Integer(36)))
                   updatescore.put(new Integer(36), rb.getBudgetStr());
                else
                   insertscore.put(new Integer(36), rb.getBudgetStr());
             }
             
             if(rb.getReasonablecostStr()!=null && !rb.getReasonablecostStr().equals(""))
             {               
               if(allRateTypes.containsKey(new Integer(52)))
                 updatescore.put(new Integer(52), rb.getReasonablecostStr());
               else
                 insertscore.put(new Integer(52), rb.getReasonablecostStr());
             }
             
             if(rb.getImproveservStr()!=null && !rb.getImproveservStr().equals(""))
             {               
               if(allRateTypes.containsKey(new Integer(53)))
                 updatescore.put(new Integer(53), rb.getImproveservStr());
               else
                 insertscore.put(new Integer(53), rb.getImproveservStr());
             }
             
             if(rb.getExpendituresStr()!=null && !rb.getExpendituresStr().equals(""))
             {               
               if(allRateTypes.containsKey(new Integer(54)))
                 updatescore.put(new Integer(54), rb.getExpendituresStr());
               else
                 insertscore.put(new Integer(54), rb.getExpendituresStr());
             }
                          
         rdb.saveLgRating(insertscore, updatescore, rb);
             
         HashMap allCommTypes = rdb.getExistingCommentTypes(rb.getGrantassign(), rb.getModule());
         int outcome = rdb.updateLgrmifComment(rb, allCommTypes);             
             
             
           //find position of save button user clicked - set anchor tag so page refreshes at same position
           if(rb.getSubmit1()!=null)
               request.setAttribute("anchorSection", "submit1");
           else if(rb.getSubmit2()!=null)
               request.setAttribute("anchorSection", "submit2");
           else if(rb.getSubmit3()!=null)
               request.setAttribute("anchorSection", "submit3");
           else if(rb.getSubmit4()!=null)
               request.setAttribute("anchorSection", "submit4");
           else if(rb.getSubmit5()!=null)
               request.setAttribute("anchorSection", "submit5");
           
         
        //--------------------------------------------
           //get all ratings and grant info and redisplay         
           RatingBean newRb = rdb.getGrantRatingsForLGReviewer(rb.getGrantassign());
           newRb.setModule("lg");
           
           if(newRb.getFycode()<12){
             EligibilityDbBean edb = new EligibilityDbBean();
             CoversheetBean csb = edb.getLgBonusScoring(newRb.getGraid());
             request.setAttribute("CoverBean", csb);
             newRb.setBonuspts(csb.getScore());
           }             
             
             BudgetDBHandler bdh = new BudgetDBHandler(); 
             newRb.setTotamtreq(bdh.calcTotalAmtRequested(newRb.getGraid(), 0));   
           request.setAttribute("RatingBean", newRb);
           
           ReviewerDBbean db = new ReviewerDBbean();
           AppDatesBean a = db.verifyReviewDue(80, newRb.getFycode());
           request.setAttribute("revDue", a);
                    
         }catch(Exception e){
           System.out.println("error LgRatingAction "+e.getMessage().toString());
         }
         return mapping.findForward("success");    
      }
                
}
