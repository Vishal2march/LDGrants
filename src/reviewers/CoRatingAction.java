/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  CoRatingAction.java
 * Creation/Modification History  : 
 * SH   8/2/07      Created
 *
 * Description
 * Class will handle the Save button on CO rating forms. Gets all values
 * from action form for regular and education rating frms and inserts/updates in db.
 *****************************************************************************/
package reviewers;

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

public class CoRatingAction extends Action{
    
  public ActionForward execute( ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) throws Exception{    
            
      HttpSession sess = request.getSession();
      ReviewerAssignDBbean rab = new ReviewerAssignDBbean();
      
      //check for session timeout 
      boolean userID = (sess.getAttribute("lduser") != null);
      if (!userID && sess.isNew())    
        return mapping.findForward("timeout");
        
        
      try{
        UserBean userb = (UserBean) sess.getAttribute("lduser");
               
        //cast the action form to a RatingBean for insert/update in the db
        RatingBean rb = (RatingBean) form;
        rb.setRevid(userb.getRevid());
        rb.setUserid(userb.getUserid());  
        
        //get all ratingtypeId's from db, used to determine whether to add/update score
        HashMap allRateTypes = rab.getExistingRatingTypes(rb.getGrantassign(), rb.getModule());
      
      
          if(rb.getAppropriateStr()!=null && !rb.getAppropriateStr().equals(""))
          {
            rb.setRatingtype(1);
            rb.setScoreStr(rb.getAppropriateStr());
            if(allRateTypes.containsKey(new Integer(1)))
              rab.updateRating(rb);
            else
              rab.insertRating(rb);
          }
          
          if(rb.getSignificanceStr()!=null && !rb.getSignificanceStr().equals(""))
          {
            rb.setRatingtype(2);
            rb.setScoreStr(rb.getSignificanceStr());
            if(allRateTypes.containsKey(new Integer(2)))
              rab.updateRating(rb);
            else
              rab.insertRating(rb);
          }
            
          if(rb.getInvolvementStr()!=null && !rb.getInvolvementStr().equals(""))
          {
            rb.setRatingtype(3);
            rb.setScoreStr(rb.getInvolvementStr());
            if(allRateTypes.containsKey(new Integer(3)))
              rab.updateRating(rb);
            else
              rab.insertRating(rb);
          }
          
          if(rb.getCoordinationStr()!=null && !rb.getCoordinationStr().equals(""))
          {
            rb.setRatingtype(4);
            rb.setScoreStr(rb.getCoordinationStr());
            if(allRateTypes.containsKey(new Integer(4)))
              rab.updateRating(rb);
            else
              rab.insertRating(rb);
          }
          
          if(rb.getBibliographicStr()!=null && !rb.getBibliographicStr().equals(""))
          {
            rb.setRatingtype(5);
            rb.setScoreStr(rb.getBibliographicStr());
            if(allRateTypes.containsKey(new Integer(5)))
              rab.updateRating(rb);
            else
              rab.insertRating(rb);
          }
          
          if(rb.getOnlinedbStr()!=null && !rb.getOnlinedbStr().equals(""))
          {
            rb.setRatingtype(6);
            rb.setScoreStr(rb.getOnlinedbStr());
            if(allRateTypes.containsKey(new Integer(6)))
              rab.updateRating(rb);
            else
              rab.insertRating(rb);
          }
          
          if(rb.getTimetableStr()!=null && !rb.getTimetableStr().equals(""))
          {
            rb.setRatingtype(7);
            rb.setScoreStr(rb.getTimetableStr());
            if(allRateTypes.containsKey(new Integer(7)))
              rab.updateRating(rb);
            else
              rab.insertRating(rb);
          }
          
          if(rb.getSoundnessStr()!=null && !rb.getSoundnessStr().equals(""))
          {
            rb.setRatingtype(8);
            rb.setScoreStr(rb.getSoundnessStr());
            if(allRateTypes.containsKey(new Integer(8)))
              rab.updateRating(rb);
            else
              rab.insertRating(rb);
          }
          
          if(rb.getEquipmentStr()!=null && !rb.getEquipmentStr().equals(""))
          {
            rb.setRatingtype(9);
            rb.setScoreStr(rb.getEquipmentStr());
            if(allRateTypes.containsKey(new Integer(9)))
              rab.updateRating(rb);
            else
              rab.insertRating(rb);
          }
            
          if(rb.getPersonnelStr()!=null && !rb.getPersonnelStr().equals(""))
          {
            rb.setRatingtype(10);
            rb.setScoreStr(rb.getPersonnelStr());
            if(allRateTypes.containsKey(new Integer(10)))
              rab.updateRating(rb);
            else
              rab.insertRating(rb);
          }
          
          if(rb.getStorageStr()!=null && !rb.getStorageStr().equals(""))
          {
            rb.setRatingtype(11);
            rb.setScoreStr(rb.getStorageStr());
            if(allRateTypes.containsKey(new Integer(11)))
              rab.updateRating(rb);
            else
              rab.insertRating(rb);
          }
          
          if(rb.getConsistentdescStr()!=null && !rb.getConsistentdescStr().equals(""))
          {
            rb.setRatingtype(12);
            rb.setScoreStr(rb.getConsistentdescStr());
            if(allRateTypes.containsKey(new Integer(12)))
              rab.updateRating(rb);
            else
              rab.insertRating(rb);
          }
          
          if(rb.getConsistentexpStr()!=null && !rb.getConsistentexpStr().equals(""))
          {
            rb.setRatingtype(13);
            rb.setScoreStr(rb.getConsistentexpStr());
            if(allRateTypes.containsKey(new Integer(13)))
              rab.updateRating(rb);
            else
              rab.insertRating(rb);
          }
          
          if(rb.getCosteffectiveStr()!=null && !rb.getCosteffectiveStr().equals(""))
          {
            rb.setRatingtype(14);
            rb.setScoreStr(rb.getCosteffectiveStr());
            if(allRateTypes.containsKey(new Integer(14)))
              rab.updateRating(rb);
            else
              rab.insertRating(rb);
          }
          
          if(rb.getOverallscoreStr()!=null && !rb.getOverallscoreStr().equals(""))
          {
            rb.setRatingtype(15);
            rb.setScoreStr(rb.getOverallscoreStr());
            if(allRateTypes.containsKey(new Integer(15)))
              rab.updateRating(rb);
            else
              rab.insertRating(rb);
          }
          
          //for education rating form------------------------------------------------
           if(rb.getGroupneedStr()!=null && !rb.getGroupneedStr().equals(""))
           {
             rb.setRatingtype(38);
             rb.setScoreStr(rb.getGroupneedStr());
             if(allRateTypes.containsKey(new Integer(38)))
               rab.updateRating(rb);
             else
               rab.insertRating(rb);
           }
           
           if(rb.getGoalStr()!=null && !rb.getGoalStr().equals(""))
           {
             rb.setRatingtype(39);
             rb.setScoreStr(rb.getGoalStr());
             if(allRateTypes.containsKey(new Integer(39)))
               rab.updateRating(rb);
             else
               rab.insertRating(rb);
           }
           
           if(rb.getPublicityStr()!=null && !rb.getPublicityStr().equals(""))
           {
             rb.setRatingtype(40);
             rb.setScoreStr(rb.getPublicityStr());
             if(allRateTypes.containsKey(new Integer(40)))
               rab.updateRating(rb);
             else
               rab.insertRating(rb);
           }
           
           if(rb.getSharingStr()!=null && !rb.getSharingStr().equals(""))
           {
             rb.setRatingtype(41);
             rb.setScoreStr(rb.getSharingStr());
             if(allRateTypes.containsKey(new Integer(41)))
               rab.updateRating(rb);
             else
               rab.insertRating(rb);
           }
          
          
      //insert/update the comment
      if(rb.getComment()!=null && !rb.getComment().equals(""))
         rab.updateComment(rb);       
         
      //get all rating scores and whether ratings have been submitted yet        
      RatingBean rtb = rab.getGrantRatingsForReviewer(rb.getGrantassign());
      rtb.setModule("co");
      request.setAttribute("RatingBean", rtb);      
      
    }catch(Exception e){
       System.out.println("error RevRatingAction "+e.getMessage().toString());
    }  
    return mapping.findForward("success");      
  }
}
