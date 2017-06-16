/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  ReviewerAssignBean.java
 * Creation/Modification History  : *
 * SH   7/31/07      Created
 *
 * Description
 * This class has get/set accessors for fields related to reviewer assignments, such
 * as reviwer name, the grant they are assigned, etc.
 *****************************************************************************/
package mypackage;
import java.util.Comparator;
import java.util.Date;
import java.util.ArrayList;
import org.apache.struts.action.ActionForm;

public class ReviewerAssignBean extends ActionForm
{
  public long revid;
  public long graid;
  public long panelGrantId;
  public long assignid;
  public int fycode;
  public String fiscalyear;
  public boolean ratingcomp;
  public boolean commentcomp;
  public int numaccepted;
  public Date dateassigned;
  public String instname;
  public String title;
  public int fccode;
  public long projseqnum;
  public long instid;
  public String username;
  public int numassigned;
  public String score;
  public String comment;
  public long commentId;
  public String overallScore;
  public boolean revassign;
  public boolean conflictinterest;
  public boolean lgMinimumScore;
  public String recommendation;
  public int recommendamt;
  public String name; 
  public String email;
  public int totamtreq;
  public int bonuspts;
  public String decisionNotes;
  public String rmoName;
  public long rmoId;
  public String rmoEmail;
  public String projcategory;

  public ReviewerAssignBean()
  {
  }
 
  public long getRevid()
  {
    return revid;
  }

  public void setRevid(long revid)
  {
    this.revid = revid;
  }

  public long getGraid()
  {
    return graid;
  }

  public void setGraid(long graid)
  {
    this.graid = graid;
  }

  public int getFycode()
  {
    return fycode;
  }

  public void setFycode(int fycode)
  {
    this.fycode = fycode;
  }

  public String getFiscalyear()
  {
    return fiscalyear;
  }

  public void setFiscalyear(String fiscalyear)
  {
    this.fiscalyear = fiscalyear;
  }

  public int getNumaccepted()
  {
    return numaccepted;
  }

  public void setNumaccepted(int numaccepted)
  {
    this.numaccepted = numaccepted;
  }

  public Date getDateassigned()
  {
    return dateassigned;
  }

  public void setDateassigned(Date dateassigned)
  {
    this.dateassigned = dateassigned;
  }

  public String getInstname()
  {
    return instname;
  }

  public void setInstname(String instname)
  {
    this.instname = instname;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public int getFccode()
  {
    return fccode;
  }

  public void setFccode(int fccode)
  {
    this.fccode = fccode;
  }

  public long getProjseqnum()
  {
    return projseqnum;
  }

  public void setProjseqnum(long projseqnum)
  {
    this.projseqnum = projseqnum;
  }

  public long getInstid()
  {
    return instid;
  }

  public void setInstid(long instid)
  {
    this.instid = instid;
  }

  public String getUsername()
  {
    return username;
  }

  public void setUsername(String username)
  {
    this.username = username;
  }



  public int getNumassigned()
  {
    return numassigned;
  }

  public void setNumassigned(int numassigned)
  {
    this.numassigned = numassigned;
  }

  public String getScore()
  {
    return score;
  }

  public void setScore(String score)
  {
    this.score = score;
  }



  public String getComment()
  {
    return comment;
  }

  public void setComment(String comment)
  {
    this.comment = comment;
  }

  public long getCommentId()
  {
    return commentId;
  }

  public void setCommentId(long commentId)
  {
    this.commentId = commentId;
  }







  public String getOverallScore()
  {
    return overallScore;
  }

  public void setOverallScore(String overallScore)
  {
    this.overallScore = overallScore;
  }

  public boolean isRevassign()
  {
    return revassign;
  }

  public void setRevassign(boolean revassign)
  {
    this.revassign = revassign;
  }

 
  /**
   * This will compare all reviewerASsignBean's in the collection first using the inst name.
   * If objects have same inst name, then it will compare using the grant id. 
   * Used to sort reports by instname, grantid
   */
  public static Comparator InstNameComparator = new Comparator() {
    public int compare(Object rb1, Object rb2) {
      String instname1 = ((ReviewerAssignBean) rb1).getInstname().toUpperCase();
      String instname2 = ((ReviewerAssignBean) rb2).getInstname().toUpperCase();
      long grant1 = ((ReviewerAssignBean)rb1).getGraid();
      long grant2 = ((ReviewerAssignBean)rb2).getGraid();
      
      if( !(instname1.equals(instname2)))
        return instname1.compareTo(instname2); 
      else
        return new Long(grant1).compareTo(new Long(grant2));
    }
  };
  public String module;

  public String getModule()
  {
    return module;
  }

  public void setModule(String module)
  {
    this.module = module;
  }


    public void setPanelGrantId(long panelGrantId) {
        this.panelGrantId = panelGrantId;
    }

    public long getPanelGrantId() {
        return panelGrantId;
    }

    public void setRatingcomp(boolean ratingcomp) {
        this.ratingcomp = ratingcomp;
    }

    public boolean isRatingcomp() {
        return ratingcomp;
    }

    public void setCommentcomp(boolean commentcomp) {
        this.commentcomp = commentcomp;
    }

    public boolean isCommentcomp() {
        return commentcomp;
    }

    public void setAssignid(long assignid) {
        this.assignid = assignid;
    }

    public long getAssignid() {
        return assignid;
    }

    public void setConflictinterest(boolean conflictinterest) {
        this.conflictinterest = conflictinterest;
    }

    public boolean isConflictinterest() {
        return conflictinterest;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setLgMinimumScore(boolean lgMinimumScore) {
        this.lgMinimumScore = lgMinimumScore;
    }

    public boolean isLgMinimumScore() {
        return lgMinimumScore;
    }

    public void setRecommendamt(int recommendamt) {
        this.recommendamt = recommendamt;
    }

    public int getRecommendamt() {
        return recommendamt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTotamtreq(int totamtreq) {
        this.totamtreq = totamtreq;
    }

    public int getTotamtreq() {
        return totamtreq;
    }

    public void setBonuspts(int bonuspts) {
        this.bonuspts = bonuspts;
    }

    public int getBonuspts() {
        return bonuspts;
    }

    public void setDecisionNotes(String decisionNotes) {
        this.decisionNotes = decisionNotes;
    }

    public String getDecisionNotes() {
        return decisionNotes;
    }

    public void setRmoName(String rmoName) {
        this.rmoName = rmoName;
    }

    public String getRmoName() {
        return rmoName;
    }

    public void setRmoId(long rmoId) {
        this.rmoId = rmoId;
    }

    public long getRmoId() {
        return rmoId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setRmoEmail(String rmoEmail) {
        this.rmoEmail = rmoEmail;
    }

    public String getRmoEmail() {
        return rmoEmail;
    }

    public void setProjcategory(String projcategory) {
        this.projcategory = projcategory;
    }

    public String getProjcategory() {
        return projcategory;
    }
    
    
  public static Comparator CategoryProjectComparator = new Comparator() {
     public int compare(Object gb1, Object gb2) {
       String cat1 = ((ReviewerAssignBean) gb1).getTitle().toUpperCase();
       String cat2 = ((ReviewerAssignBean) gb2).getTitle().toUpperCase();
       
       int i = cat1.compareTo(cat2);
       if(i!=0){
           return i;
       }
       
       //if same project category; then compare project nums
       long proj1 = ((ReviewerAssignBean) gb1).getProjseqnum();
       long proj2 = ((ReviewerAssignBean) gb2).getProjseqnum();
       return new Long(proj1).compareTo(new Long(proj2));    
     }
   };
  
  
  
  
  public static Comparator CategoryComparator = new Comparator() {
     public int compare(Object gb1, Object gb2) {
       String cat1 = ((ReviewerAssignBean) gb1).getProjcategory().toUpperCase();
       String cat2 = ((ReviewerAssignBean) gb2).getProjcategory().toUpperCase();
       
       int i = cat1.compareTo(cat2);
       if(i!=0){
           return i;
       }
       
       //if same project category; then compare project nums
       long proj1 = ((ReviewerAssignBean) gb1).getProjseqnum();
       long proj2 = ((ReviewerAssignBean) gb2).getProjseqnum();
       return new Long(proj1).compareTo(new Long(proj2));    
     }
   };
  
  
  
  
  
  public static Comparator ProjectNumComparator = new Comparator() {
     public int compare(Object gb1, Object gb2) {
       
       //compare project nums
       long proj1 = ((ReviewerAssignBean) gb1).getProjseqnum();
       long proj2 = ((ReviewerAssignBean) gb2).getProjseqnum();
       return new Long(proj1).compareTo(new Long(proj2));    
     }
   };
}
