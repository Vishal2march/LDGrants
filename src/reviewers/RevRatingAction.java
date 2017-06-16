/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  RevRatingAction.java
 * Creation/Modification History  : *
 * SH   10/31/80      Modified
 *
 * Description
 * This action class will save Di rating and DI education rating form entries.
 * It will add/update the score for each rating type.
 *****************************************************************************/
package reviewers;

import java.util.HashMap;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypackage.AppDatesBean;
import mypackage.DBHandler;
import mypackage.GrantBean;
import mypackage.RatingBean;
import mypackage.ReviewerAssignDBbean;
import mypackage.ReviewerDBbean;
import mypackage.UserBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RevRatingAction extends Action
{
  private  DBHandler dbh = new DBHandler();
  private ReviewerDBbean rdbbean = new ReviewerDBbean();
  private ReviewerAssignDBbean rdb = new ReviewerAssignDBbean();
    
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
           RatingBean rb = (RatingBean) form;
           rb.setRevid(userb.getRevid());
           rb.setUserid(userb.getUserid());          
           
        //get all ratingtypeId's from db, used to determine whether to add/update score
        HashMap allRateTypes = rdb.getExistingRatingTypes(rb.getGrantassign(), rb.getModule());
                  
        if(rb.getInstcpStr()!=null && !rb.getInstcpStr().equals(""))
        {
          rb.setRatingtype(21);
          rb.setScoreStr(rb.getInstcpStr());
          if(allRateTypes.containsKey(new Integer(21)))
            rdb.updateRating(rb);
          else
            rdb.insertRating(rb);
        }
            
        if(rb.getStorageStr()!=null && !rb.getStorageStr().equals(""))
        {
          rb.setRatingtype(11);
          rb.setScoreStr(rb.getStorageStr());
          if(allRateTypes.containsKey(new Integer(11)))
            rdb.updateRating(rb);
          else
            rdb.insertRating(rb);
        }
     
        if(rb.getDisasterStr()!=null && !rb.getDisasterStr().equals(""))
        {
          rb.setRatingtype(20);
          rb.setScoreStr(rb.getDisasterStr());
          if(allRateTypes.containsKey(new Integer(20)))
            rdb.updateRating(rb);
          else
            rdb.insertRating(rb);
        }
            
        if(rb.getSecurityStr()!=null && !rb.getSecurityStr().equals(""))
        {
          rb.setRatingtype(19);
          rb.setScoreStr(rb.getSecurityStr());
          if(allRateTypes.containsKey(new Integer(19)))
            rdb.updateRating(rb);
          else
            rdb.insertRating(rb);
        }
        
        if(rb.getCoopactivitiesStr()!=null && !rb.getCoopactivitiesStr().equals(""))
        {
          rb.setRatingtype(22);
          rb.setScoreStr(rb.getCoopactivitiesStr());
          if(allRateTypes.containsKey(new Integer(22)))
            rdb.updateRating(rb);
          else
            rdb.insertRating(rb);
        }
        
        if(rb.getAvailabilityStr()!=null && !rb.getAvailabilityStr().equals(""))
        {
          rb.setRatingtype(18);
          rb.setScoreStr(rb.getAvailabilityStr());
          if(allRateTypes.containsKey(new Integer(18)))
            rdb.updateRating(rb);
          else
            rdb.insertRating(rb);
        }
     
        if(rb.getBibliographicStr()!=null && !rb.getBibliographicStr().equals(""))
        {
          rb.setRatingtype(5);
          rb.setScoreStr(rb.getBibliographicStr());
          if(allRateTypes.containsKey(new Integer(5)))
            rdb.updateRating(rb);
          else
            rdb.insertRating(rb);
        }
        
        if(rb.getAppropriateStr()!=null && !rb.getAppropriateStr().equals(""))
        {
          rb.setRatingtype(1);
          rb.setScoreStr(rb.getAppropriateStr());
          if(allRateTypes.containsKey(new Integer(1)))
            rdb.updateRating(rb);
          else
            rdb.insertRating(rb);
        }
        
        if(rb.getSignificanceStr()!=null && !rb.getSignificanceStr().equals(""))
        {
          rb.setRatingtype(2);
          rb.setScoreStr(rb.getSignificanceStr());
          if(allRateTypes.containsKey(new Integer(2)))
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
        
        if(rb.getSoundnessStr()!=null && !rb.getSoundnessStr().equals(""))
        {
          rb.setRatingtype(8);
          rb.setScoreStr(rb.getSoundnessStr());
          if(allRateTypes.containsKey(new Integer(8)))
            rdb.updateRating(rb);
          else
            rdb.insertRating(rb);
        }
        
        if(rb.getPersonnelStr()!=null && !rb.getPersonnelStr().equals(""))
        {
          rb.setRatingtype(10);
          rb.setScoreStr(rb.getPersonnelStr());
          if(allRateTypes.containsKey(new Integer(10)))
            rdb.updateRating(rb);
          else
            rdb.insertRating(rb);
        }
     
        if(rb.getStaffsupportStr()!=null && !rb.getStaffsupportStr().equals(""))
        {
          rb.setRatingtype(16);
          rb.setScoreStr(rb.getStaffsupportStr());
          if(allRateTypes.containsKey(new Integer(16)))
            rdb.updateRating(rb);
          else
            rdb.insertRating(rb);
        }
     
        if(rb.getFinancialsupportStr()!=null && !rb.getFinancialsupportStr().equals(""))
        {
          rb.setRatingtype(17);
          rb.setScoreStr(rb.getFinancialsupportStr());
          if(allRateTypes.containsKey(new Integer(17)))
            rdb.updateRating(rb);
          else
            rdb.insertRating(rb);
        }
     
        if(rb.getConsistentdescStr()!=null && !rb.getConsistentdescStr().equals(""))
        {
          rb.setRatingtype(12);
          rb.setScoreStr(rb.getConsistentdescStr());
          if(allRateTypes.containsKey(new Integer(12)))
            rdb.updateRating(rb);
          else
            rdb.insertRating(rb);
        }
     
        if(rb.getCosteffectiveStr()!=null && !rb.getCosteffectiveStr().equals(""))
        {
          rb.setRatingtype(14);
          rb.setScoreStr(rb.getCosteffectiveStr());
          if(allRateTypes.containsKey(new Integer(14)))
            rdb.updateRating(rb);
          else
            rdb.insertRating(rb);
        }
        
          if(rb.getConsistentexpStr()!=null && !rb.getConsistentexpStr().equals(""))
          {
            rb.setRatingtype(13);
            rb.setScoreStr(rb.getConsistentexpStr());
            if(allRateTypes.containsKey(new Integer(13)))
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
          
          //following 4 needed for DI educ rating form (38,39,40,41)
          if(rb.getGroupneedStr()!=null && !rb.getGroupneedStr().equals(""))
          {
            rb.setRatingtype(38);
            rb.setScoreStr(rb.getGroupneedStr());
            if(allRateTypes.containsKey(new Integer(38)))
              rdb.updateRating(rb);
            else
              rdb.insertRating(rb);
          }
          
          if(rb.getGoalStr()!=null && !rb.getGoalStr().equals(""))
          {
            rb.setRatingtype(39);
            rb.setScoreStr(rb.getGoalStr());
            if(allRateTypes.containsKey(new Integer(39)))
              rdb.updateRating(rb);
            else
              rdb.insertRating(rb);
          }
          
          if(rb.getPublicityStr()!=null && !rb.getPublicityStr().equals(""))
          {
            rb.setRatingtype(40);
            rb.setScoreStr(rb.getPublicityStr());
            if(allRateTypes.containsKey(new Integer(40)))
              rdb.updateRating(rb);
            else
              rdb.insertRating(rb);
          }
          
          if(rb.getSharingStr()!=null && !rb.getSharingStr().equals(""))
          {
            rb.setRatingtype(41);
            rb.setScoreStr(rb.getSharingStr());
            if(allRateTypes.containsKey(new Integer(41)))
              rdb.updateRating(rb);
            else
              rdb.insertRating(rb);
          }
          
  //-------------------------------------------------------         
          //insert/update the comment
          if(rb.getComment()!=null && !rb.getComment().equals(""))
            rdb.updateComment(rb);     
                    
         //get all ratings and grant info and redisplay
          RatingBean newRb = rdb.getGrantRatingsForReviewer(rb.getGrantassign());
          newRb.setModule("di");
          request.setAttribute("RatingBean", newRb);
          
         //get info about grant 
         GrantBean gb=dbh.getRecordBean(newRb.getGraid()); 
         sess.setAttribute("thisGrant", gb);  
         
         AppDatesBean a = rdbbean.getDueDateReviews(gb.getFccode(), newRb.getGraid());
         request.setAttribute("revDue", a);
          
         Vector allDocuments = dbh.getAllDocuments(newRb.getGraid());
         request.setAttribute("allDocs", allDocuments);
      
     }catch(Exception e){
       System.out.println("error RevRatingAction "+e.getMessage().toString());
     }
      
      return mapping.findForward("success");     
    }  
  
}