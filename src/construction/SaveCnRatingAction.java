/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  SaveCnRatingAction.java
 * Creation/Modification History  :
 *
 * SH   4/29/11     Modified
 *
 * Description
 * This action will save all comments,answers, and recommend amt on Cn reviewer
 * rating form, then redisplay rating form. modified 12/29/11 for cn admin use.
 *****************************************************************************/
package construction;

import construction.ConstructionDBbean;
import java.lang.String;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypackage.DBHandler;
import mypackage.GrantBean;
import mypackage.ReviewerAssignDBbean;
import mypackage.UserBean;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SaveCnRatingAction extends Action {

    private ConstructionDBbean cdb = new ConstructionDBbean();
    private DBHandler dbh = new DBHandler();
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception {

        HttpSession sess = request.getSession();
        if(checkSessionTimeout(sess))
            return mapping.findForward("timeout");
            
        try {
            UserBean ub = (UserBean) sess.getAttribute("lduser");
            
            CnRatingFormBean fb = (CnRatingFormBean)form;
            
            //update the t/f recommendation, and amt_recommend
            int outcome= cdb.updateSystemRecomendation(fb, ub.getUserid());

            //get all comments already saved to the database
            ReviewerAssignDBbean rdb = new ReviewerAssignDBbean();
            HashMap allCommTypes = rdb.getExistingCommentTypes(fb.getAssignmentid(), "cn");
            
            //either insert/update comment depending on whether record already exists
            outcome = cdb.updateCnReviewerComment(fb, allCommTypes, ub.getUserid());             
          
            //get all ratingtypeId's from db, used to determine whether to add/update score
            HashMap allRateTypes = rdb.getExistingRatingTypes(fb.getAssignmentid(), "cn");
                       
            //either insert/update ans(rating) depending on whether record already exists
            outcome = cdb.updateCnReviewerAnswer(fb, allRateTypes, ub.getUserid());             
                        
            ///////////////refresh all ratings/comments and redisplay eval page
             GrantBean gb = dbh.getRecordBean(fb.getGrantid());
             request.setAttribute("thisGrant", gb);
             
             //get all info about system assignment
             SystemAssignBean sab = cdb.getSystemAssignRecord(fb.getGrantid());
             request.setAttribute("assignBean", sab);
             
            //get amtreq, 50%, total pls allocation, amtrecommend so far, etc
            ConstructAllocationDbBean cadb = new ConstructAllocationDbBean();
            AllocationYearBean ab = cadb.calcRequestApprAmounts(gb.getGrantid(), gb.getFycode());//amtreq & 50% amt
            AllocationYearBean ab2 = cadb.calcAllocAndAwardForPlsFy(sab.getSystemInstId(), gb.getFycode());
            
             //get all eval form comments and answers
             CnRatingFormBean rb = cdb.getCnRatingComments(sab.getAssignmentId());
             rb.setGrantid(gb.getGrantid());
             rb.setAssignmentid(sab.getAssignmentId());
             rb.setFycode(gb.getFycode());
             rb.setAmtrecommended(sab.getRecommendAmt());
             rb.setAmtrecommendedStr(sab.getRecommendAmtStr());
             rb.setProjectrecommend(sab.isRecommendation());
            rb.setAmountRequested(ab.getAmountRequested());//from alloc bean
            rb.setRequestCost(ab.getRequestCost());//from alloc bean
            rb.setMaxRecommendAmt(ab.getMaxRequestCost());//from alloc bean
            rb.setInitialAlloc(ab2.getInitialAlloc());//from alloc bean
            rb.setTallyAmountRecommend(ab2.getTallyAmountRecommend());//from alloc bean
             rb = cdb.getCnRatingAnswers(rb, sab.getAssignmentId());                         
             request.setAttribute("reviewFormBean", rb);        
          
                        
        } catch (Exception e) {
            System.out.println("error SaveCnRatingAction "+e.getMessage().toString());
        }
        return (mapping.findForward("success"));
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

}//end of class
