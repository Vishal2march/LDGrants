package reviewers.literacyrev;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.RatingBean;
import mypackage.ReviewerAssignDBbean;
import mypackage.UserBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LiSaveRating extends Action
{
  public ActionForward execute( ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response) throws Exception{    
     
       HttpSession sess = request.getSession();
       //check for session timeout
       boolean userID = (sess.getAttribute("lduser") != null); 
       if (!userID && sess.isNew())
         return mapping.findForward("timeout");
       
       //get reviewer info
       UserBean userb = (UserBean) sess.getAttribute("lduser");
       long revid = userb.getRevid();
       String userid = userb.getUserid();
       
       try{
           //get rating bean user was working on
           ReviewerAssignDBbean rdb = new ReviewerAssignDBbean();
           RatingBean rb = (RatingBean) form;
           rb.setRevid(revid);
           rb.setUserid(userid);
                
          //get all ratingtypeId's from db, used to determine whether to add/update score
          HashMap allRateTypes = rdb.getExistingRatingTypes(rb.getGrantassign(), rb.getModule());
               
          
            if(rb.getAbstractStr()!=null && !rb.getAbstractStr().equals(""))
            {
              rb.setRatingtype(23);
              rb.setScoreStr(rb.getAbstractStr());
              if(allRateTypes.containsKey(new Integer(23)))
                rdb.updateRating(rb);
              else
                rdb.insertRating(rb);
            }
            
            if(rb.getGroupneedStr()!=null && !rb.getGroupneedStr().equals(""))
            {
              rb.setRatingtype(24);
              rb.setScoreStr(rb.getGroupneedStr());
              if(allRateTypes.containsKey(new Integer(24)))
                rdb.updateRating(rb);
              else
                rdb.insertRating(rb);
            }
         
            if(rb.getLongrangeStr()!=null && !rb.getLongrangeStr().equals(""))
            {
              rb.setRatingtype(25);
              rb.setScoreStr(rb.getLongrangeStr());
              if(allRateTypes.containsKey(new Integer(25)))
                rdb.updateRating(rb);
              else
                rdb.insertRating(rb);
            }
            
            if(rb.getLevelserviceStr()!=null && !rb.getLevelserviceStr().equals(""))
            {
              rb.setRatingtype(26);
              rb.setScoreStr(rb.getLevelserviceStr());
              if(allRateTypes.containsKey(new Integer(26)))
                rdb.updateRating(rb);
              else
                rdb.insertRating(rb);
            }
            
            if(rb.getCooporganizationStr()!=null && !rb.getCooporganizationStr().equals(""))
            {
              rb.setRatingtype(27);
              rb.setScoreStr(rb.getCooporganizationStr());
              if(allRateTypes.containsKey(new Integer(27)))
                rdb.updateRating(rb);
              else
                rdb.insertRating(rb);
            }
            
            if(rb.getGoalStr()!=null && !rb.getGoalStr().equals(""))
            {
              rb.setRatingtype(28);
              rb.setScoreStr(rb.getGoalStr());
              if(allRateTypes.containsKey(new Integer(28)))
                rdb.updateRating(rb);
              else
                rdb.insertRating(rb);
            }
         
            if(rb.getActivitiesStr()!=null && !rb.getActivitiesStr().equals(""))
            {
              rb.setRatingtype(29);
              rb.setScoreStr(rb.getActivitiesStr());
              if(allRateTypes.containsKey(new Integer(29)))
                rdb.updateRating(rb);
              else
                rdb.insertRating(rb);
            }
            
            if(rb.getTimetableStr()!=null && !rb.getTimetableStr().equals(""))
            {
              rb.setRatingtype(7);
              rb.setScoreStr(rb.getTimetableStr());
              if(allRateTypes.containsKey(new Integer(7)))
                rdb.updateRating(rb);
              else
                rdb.insertRating(rb);
            }
            
            if(rb.getOutputStr()!=null && !rb.getOutputStr().equals(""))
            {
              rb.setRatingtype(30);
              rb.setScoreStr(rb.getOutputStr());
              if(allRateTypes.containsKey(new Integer(30)))
                rdb.updateRating(rb);
              else
                rdb.insertRating(rb);
            }
            
            if(rb.getMeasureoutputStr()!=null && !rb.getMeasureoutputStr().equals(""))
            {
              rb.setRatingtype(31);
              rb.setScoreStr(rb.getMeasureoutputStr());
              if(allRateTypes.containsKey(new Integer(31)))
                rdb.updateRating(rb);
              else
                rdb.insertRating(rb);
            }
            
            if(rb.getOutcomeStr()!=null && !rb.getOutcomeStr().equals(""))
            {
              rb.setRatingtype(32);
              rb.setScoreStr(rb.getOutcomeStr());
              if(allRateTypes.containsKey(new Integer(32)))
                rdb.updateRating(rb);
              else
                rdb.insertRating(rb);
            }
            
            if(rb.getMeasureoutcomeStr()!=null && !rb.getMeasureoutcomeStr().equals(""))
            {
              rb.setRatingtype(33);
              rb.setScoreStr(rb.getMeasureoutcomeStr());
              if(allRateTypes.containsKey(new Integer(33)))
                rdb.updateRating(rb);
              else
                rdb.insertRating(rb);
            }
         
            if(rb.getContinuationStr()!=null && !rb.getContinuationStr().equals(""))
            {
              rb.setRatingtype(34);
              rb.setScoreStr(rb.getContinuationStr());
              if(allRateTypes.containsKey(new Integer(34)))
                rdb.updateRating(rb);
              else
                rdb.insertRating(rb);
            }
         
            if(rb.getSharingStr()!=null && !rb.getSharingStr().equals(""))
            {
              rb.setRatingtype(35);
              rb.setScoreStr(rb.getSharingStr());
              if(allRateTypes.containsKey(new Integer(35)))
                rdb.updateRating(rb);
              else
                rdb.insertRating(rb);
            }
         
            if(rb.getBudgetStr()!=null && !rb.getBudgetStr().equals(""))
            {
              rb.setRatingtype(36);
              rb.setScoreStr(rb.getBudgetStr());
              if(allRateTypes.containsKey(new Integer(36)))
                rdb.updateRating(rb);
              else
                rdb.insertRating(rb);
            }
         
            if(rb.getOtherfundStr()!=null && !rb.getOtherfundStr().equals(""))
            {
              rb.setRatingtype(37);
              rb.setScoreStr(rb.getOtherfundStr());
              if(allRateTypes.containsKey(new Integer(37)))
                rdb.updateRating(rb);
              else
                rdb.insertRating(rb);
            }
            
                    
            if(rb.getOverallscoreStr()!=null && !rb.getOverallscoreStr().equals(""))
            {
              rb.setRatingtype(15);
              rb.setScoreStr(rb.getOverallscoreStr());
              if(allRateTypes.containsKey(new Integer(15)))
                rdb.updateRating(rb);
              else
                rdb.insertRating(rb);
            }
      //------------------------------------------------------------------------
         
          //insert/update the comment
          if(rb.getComment()!=null && !rb.getComment().equals(""))
            rdb.updateComment(rb);     
                          
          
         //get all ratings and grant info and redisplay
          RatingBean newRb = rdb.getGrantRatingsForReviewer(rb.getGrantassign());
          newRb.setModule("li");
          request.setAttribute("RatingBean", newRb);
      
      }catch(Exception e){
         System.out.println("error LiSaveRating "+e.getMessage().toString());
      }
      return mapping.findForward("success");     
    }  
}