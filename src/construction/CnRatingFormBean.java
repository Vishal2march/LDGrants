package construction;

import org.apache.struts.action.ActionForm;
import javax.servlet.http.*;

import mypackage.DBHandler;

import org.apache.struts.action.*;

public class CnRatingFormBean extends ActionForm{
    public CnRatingFormBean() {
    }
      
    public long grantid;
    public long assignmentid;
    public boolean projectrecommend;
    public long amtrecommended;
    public String amtrecommendedStr;
    public double maxRecommendAmt;    
    public double amountRequested;
    public int requestCost;
    public long initialAlloc;
    public long tallyAmountRecommend;   
    public int fycode;
    public String commentone;
    public String commenttwo;
    public String commentthree;
    public String commentfour;
    public String commentfive;
    public String commentsix;
    public String commentseven;
    public String commenteight;
    public String commentnine;
    public String commentten;
    public String commenteleven;
    public String commenttwelve;
    public boolean questionone;
    public boolean questiontwo;
    public boolean questionthree;
    public boolean questionfour;
    public boolean questionfive;
    public boolean questionsix;
    public boolean questionseven;
    public boolean questioneight;
    public boolean questionnine;
    public boolean questionten;
    public boolean questioneleven;
    public boolean questiontwelve;
    
    


    public void setCommentone(String commentone) {
        this.commentone = commentone;
    }

    public String getCommentone() {
        return commentone;
    }

    public void setCommenttwo(String commenttwo) {
        this.commenttwo = commenttwo;
    }

    public String getCommenttwo() {
        return commenttwo;
    }

    public void setCommentthree(String commentthree) {
        this.commentthree = commentthree;
    }

    public String getCommentthree() {
        return commentthree;
    }

    public void setCommentfour(String commentfour) {
        this.commentfour = commentfour;
    }

    public String getCommentfour() {
        return commentfour;
    }

    public void setCommentfive(String commentfive) {
        this.commentfive = commentfive;
    }

    public String getCommentfive() {
        return commentfive;
    }

    public void setCommentsix(String commentsix) {
        this.commentsix = commentsix;
    }

    public String getCommentsix() {
        return commentsix;
    }

    public void setCommentseven(String commentseven) {
        this.commentseven = commentseven;
    }

    public String getCommentseven() {
        return commentseven;
    }

    public void setCommenteight(String commenteight) {
        this.commenteight = commenteight;
    }

    public String getCommenteight() {
        return commenteight;
    }

    public void setCommentnine(String commentnine) {
        this.commentnine = commentnine;
    }

    public String getCommentnine() {
        return commentnine;
    }

    public void setCommentten(String commentten) {
        this.commentten = commentten;
    }

    public String getCommentten() {
        return commentten;
    }

    public void setCommenteleven(String commenteleven) {
        this.commenteleven = commenteleven;
    }

    public String getCommenteleven() {
        return commenteleven;
    }

    public void setGrantid(long grantid) {
        this.grantid = grantid;
    }

    public long getGrantid() {
        return grantid;
    }

    public void setAssignmentid(long assignmentid) {
        this.assignmentid = assignmentid;
    }

    public long getAssignmentid() {
        return assignmentid;
    }

    public void setQuestionone(boolean questionone) {
        this.questionone = questionone;
    }

    public boolean isQuestionone() {
        return questionone;
    }

    public void setQuestiontwo(boolean questiontwo) {
        this.questiontwo = questiontwo;
    }

    public boolean isQuestiontwo() {
        return questiontwo;
    }

    public void setQuestionthree(boolean questionthree) {
        this.questionthree = questionthree;
    }

    public boolean isQuestionthree() {
        return questionthree;
    }

    public void setQuestionfour(boolean questionfour) {
        this.questionfour = questionfour;
    }

    public boolean isQuestionfour() {
        return questionfour;
    }

    public void setQuestionfive(boolean questionfive) {
        this.questionfive = questionfive;
    }

    public boolean isQuestionfive() {
        return questionfive;
    }

    public void setQuestionsix(boolean questionsix) {
        this.questionsix = questionsix;
    }

    public boolean isQuestionsix() {
        return questionsix;
    }

    public void setQuestionseven(boolean questionseven) {
        this.questionseven = questionseven;
    }

    public boolean isQuestionseven() {
        return questionseven;
    }

    public void setQuestioneight(boolean questioneight) {
        this.questioneight = questioneight;
    }

    public boolean isQuestioneight() {
        return questioneight;
    }

    public void setQuestionnine(boolean questionnine) {
        this.questionnine = questionnine;
    }

    public boolean isQuestionnine() {
        return questionnine;
    }

    public void setQuestionten(boolean questionten) {
        this.questionten = questionten;
    }

    public boolean isQuestionten() {
        return questionten;
    }

    public void setQuestioneleven(boolean questioneleven) {
        this.questioneleven = questioneleven;
    }

    public boolean isQuestioneleven() {
        return questioneleven;
    }

    public void setAmtrecommended(long amtrecommended) {
        this.amtrecommended = amtrecommended;
    }

    public long getAmtrecommended() {
        return amtrecommended;
    }

    public void setProjectrecommend(boolean projectrecommend) {
        this.projectrecommend = projectrecommend;
    }

    public boolean isProjectrecommend() {
        return projectrecommend;
    }

    public void setAmtrecommendedStr(String amtrecommendedStr) {
        this.amtrecommendedStr = amtrecommendedStr;
    }

    public String getAmtrecommendedStr() {
        return amtrecommendedStr;
    }
    
    public boolean isMissing(String value) 
    {
     return((value == null) || (value.trim().equals("")));
    }
    
    public boolean isMissing(int value) 
    {
     return((value == 0) );
    }
    
    public ActionErrors validate(ActionMapping mapping,  HttpServletRequest request) 
    {  
      ActionErrors errors = new ActionErrors();
      
      //verify that amt recommended is not more than 50/75% of this project
      if (!isMissing(getAmtrecommendedStr())) 
      {
          //parse out an $ or decimals in amt_recommended
          DBHandler dbh = new DBHandler();
          long amtrec_num = 0;
          if(getAmtrecommendedStr()!= null && !getAmtrecommendedStr().equals(""))
            amtrec_num = dbh.parseLongAmtNoDecimal(getAmtrecommendedStr());
            
          if(amtrec_num > getMaxRecommendAmt())
            errors.add("overMaxAmount", new ActionError("errors.overMaxCnAmount"));
            
          //verify that totalrecommended so far is not more than allocation
          //if(getTallyAmountRecommend() > getInitialAlloc())
          //   errors.add("overAllocation", new ActionError("errors.overAllocation"));
          /*Sh Note-commented out this validation b/c is user fills out rating form, submits,
           * and this error occurs, they may lose their work while they navigate to other
           * applications to reduce their recommended amts. */
      } 
           
      return(errors);
    }

    public void setMaxRecommendAmt(double maxRecommendAmt) {
        this.maxRecommendAmt = maxRecommendAmt;
    }

    public double getMaxRecommendAmt() {
        return maxRecommendAmt;
    }

    public void setAmountRequested(double amountRequested) {
        this.amountRequested = amountRequested;
    }

    public double getAmountRequested() {
        return amountRequested;
    }

    public void setRequestCost(int requestCost) {
        this.requestCost = requestCost;
    }

    public int getRequestCost() {
        return requestCost;
    }

    public void setTallyAmountRecommend(long tallyAmountRecommend) {
        this.tallyAmountRecommend = tallyAmountRecommend;
    }

    public long getTallyAmountRecommend() {
        return tallyAmountRecommend;
    }

    public void setInitialAlloc(long initialAlloc) {
        this.initialAlloc = initialAlloc;
    }

    public long getInitialAlloc() {
        return initialAlloc;
    }

    public void setFycode(int fycode) {
        this.fycode = fycode;
    }

    public int getFycode() {
        return fycode;
    }

    public void setCommenttwelve(String commenttwelve) {
        this.commenttwelve = commenttwelve;
    }

    public String getCommenttwelve() {
        return commenttwelve;
    }

    public void setQuestiontwelve(boolean questiontwelve) {
        this.questiontwelve = questiontwelve;
    }

    public boolean isQuestiontwelve() {
        return questiontwelve;
    }
} ///class
