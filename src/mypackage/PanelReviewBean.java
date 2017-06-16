package mypackage;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PanelReviewBean extends ActionForm{
    public PanelReviewBean() {
    }
    
    public long grantid;
    public long panelid;
    public long panelgrantid;
    public int finalscore;
    public String recommendation;
    public long recommendamt;
    public String recommendamtStr;
    public String justification;
    public String panelnotes;
    public String decisionnotes;
    public String raocomments;
    public String userid;
    public boolean initialappr;
    public boolean appDenied;
    public String status;
    public int bonuspts;
    public int bonusScore;//new 11/12/15
    public int totamtappr;
    public int totamtreq;


    public void setGrantid(long grantid) {
        this.grantid = grantid;
    }

    public long getGrantid() {
        return grantid;
    }

    public void setPanelid(long panelid) {
        this.panelid = panelid;
    }

    public long getPanelid() {
        return panelid;
    }

    public void setPanelgrantid(long panelgrantid) {
        this.panelgrantid = panelgrantid;
    }

    public long getPanelgrantid() {
        return panelgrantid;
    }

    public void setFinalscore(int finalscore) {
        this.finalscore = finalscore;
    }

    public int getFinalscore() {
        return finalscore;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendamt(long recommendamt) {
        this.recommendamt = recommendamt;
    }

    public long getRecommendamt() {
        return recommendamt;
    }

    public void setRecommendamtStr(String recommendamtStr) {
        this.recommendamtStr = recommendamtStr;
    }

    public String getRecommendamtStr() {
        return recommendamtStr;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public String getJustification() {
        return justification;
    }

    public void setPanelnotes(String panelnotes) {
        this.panelnotes = panelnotes;
    }

    public String getPanelnotes() {
        return panelnotes;
    }

    public void setDecisionnotes(String decisionnotes) {
        this.decisionnotes = decisionnotes;
    }

    public String getDecisionnotes() {
        return decisionnotes;
    }

    public void setRaocomments(String raocomments) {
        this.raocomments = raocomments;
    }

    public String getRaocomments() {
        return raocomments;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setInitialappr(boolean initialappr) {
        this.initialappr = initialappr;
    }

    public boolean isInitialappr() {
        return initialappr;
    }

    public void setAppDenied(boolean appDenied) {
        this.appDenied = appDenied;
    }

    public boolean isAppDenied() {
        return appDenied;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    
    private boolean isInt(String potentialInt) 
    {
      boolean isInt = true;
      try {
        int x = Integer.parseInt(potentialInt.trim());
      } 
      catch(NumberFormatException nfe) 
      {
        isInt = false;
      }
      catch(Exception e)
      {
        isInt = false;
      }

      return(isInt);
    } 
    
    public boolean isMissing(String value) 
    {
      return((value == null) || (value.trim().equals("")));
    }
    
    public ActionErrors validate(ActionMapping mapping,  HttpServletRequest request) 
    {  
      ActionErrors errors = new ActionErrors();
      if( (getFinalscore() + getBonuspts()) <60){//reviewer score plus bonus must be at least 60
          if (!isMissing(getRecommendation())){
            if(! getRecommendation().equalsIgnoreCase("N"))
                errors.add("recMissing", new ActionError("errors.recommendscore"));
          } 
      }      
      
        //if recommendation=N, then recommend amt must =0; else error
        if( !isMissing(getRecommendation())  && getRecommendation().equalsIgnoreCase("N")) {
            if( !isMissing(getRecommendamtStr()))
            {
                if(isInt(getRecommendamtStr()))
                {
                  int recom = Integer.parseInt(getRecommendamtStr().trim());
                  if(recom!=0)
                    errors.add("recomData", new ActionError("errors.recommendamt"));
                }
            }
            //if recomm=N, then initialappr=false
            if(isInitialappr()==true)
                errors.add("recomAppr", new ActionError("errors.apprNofund"));
        }
        
        //if recommendation=F, the recommendamt must =totamtreq; else error
        if( !isMissing(getRecommendation())  && getRecommendation().equalsIgnoreCase("F")) {
            int recom=0;
            if( !isMissing(getRecommendamtStr())){
                if(isInt(getRecommendamtStr())){
                  recom = Integer.parseInt(getRecommendamtStr().trim());                  
                }
            }
            if(recom!=getTotamtreq())
              errors.add("recomData", new ActionError("errors.recommendfund"));
              
            //if recomm=F, then initialappr=true
            if(isInitialappr()==false)
                errors.add("recomAppr", new ActionError("errors.apprFullfund"));
        }
         
         
        //if recommendation=M, the recommendamt must < totamtreq; else error
        if( !isMissing(getRecommendation())  && getRecommendation().equalsIgnoreCase("M")) {
            int recom=0;
            if( !isMissing(getRecommendamtStr())){
                if(isInt(getRecommendamtStr())){
                  recom = Integer.parseInt(getRecommendamtStr().trim());                  
                }
            }
            if(recom > getTotamtreq())//if recommendamt is more than amtreq
              errors.add("recomData", new ActionError("errors.recommendmod"));
              
            //if recomm=M, then initialappr=true
            if(isInitialappr()==false)
                errors.add("recomAppr", new ActionError("errors.apprModfund"));
        }       
      return(errors);
    }

    public void setBonuspts(int bonuspts) {
        this.bonuspts = bonuspts;
    }

    public int getBonuspts() {
        return bonuspts;
    }

    public void setTotamtappr(int totamtappr) {
        this.totamtappr = totamtappr;
    }

    public int getTotamtappr() {
        return totamtappr;
    }

    public void setTotamtreq(int totamtreq) {
        this.totamtreq = totamtreq;
    }

    public int getTotamtreq() {
        return totamtreq;
    }

    public void setBonusScore(int bonusScore) {
        this.bonusScore = bonusScore;
    }

    public int getBonusScore() {
        return bonusScore;
    }
}
