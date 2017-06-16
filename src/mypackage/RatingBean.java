/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  RatingBean.java
 * Creation/Modification History  :
 *
 * SH   7/25/07      Created
 *
 * Description
 * This class has get/set accessors to store and retrieve info from the 
 * Rating_type and reviewer_rating tables for reviewer scores.
 *****************************************************************************/
package mypackage;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.*;

public class RatingBean extends ActionForm
{
  public String comment;
  public String userid;
  public String recommendation;
  public long recommendamt;
  public String recommendamtStr;
  public long grantassign;
  public long panelgrantId;
  public long panelreviewerId;
  public boolean ratingcomp;
  public boolean commentcomp;
  public boolean conflictinterest;
  public long commentid;
  public int appropriate=0;
  public int significance=0;
  public int involvement=0;
  public int coordination=0;
  public int bibliographic=0;
  public int onlinedb=0;
  public int timetable=0;
  public int soundness=0;
  public int equipment=0;
  public int personnel=0;
  public int storage=0;
  public int consistentdesc=0;
  public int consistentexp=0;
  public int costeffective=0;
  public int overallscore=0;
  public String name;
  public long revid;
  public long graid;
  public int score;
  public int ratingtype;
  public int sumscore;
  public int fycode;
  public String appropriateStr;
  public String bibliographicStr;
  public String consistentdescStr;
  public String consistentexpStr;
  public String coordinationStr;
  public String costeffectiveStr;
  public String equipmentStr;
  public String involvementStr;
  public String onlinedbStr;
  public String overallscoreStr;
  public String personnelStr;
  public String significanceStr;
  public String soundnessStr;
  public String storageStr;
  public String timetableStr;
  public String scoreStr;
  public int instcp=0;
  public String instcpStr;
  public int disaster=0;
  public String disasterStr;
  public int security=0;
  public String securityStr;
  public int coopactivities=0;
  public String coopactivitiesStr;
  public int availability=0;
  public String availabilityStr;
  public int staffsupport=0;
  public String staffsupportStr;
  public int financialsupport=0;
  public String financialsupportStr;
  public String module;
  public String abstractStr;
  public int abstractInt=0;
  public int groupneed=0;
  public String groupneedStr;
  public String longrangeStr;
  public int longrange=0;
  public String levelserviceStr;
  public int levelservice=0;
  public String cooporganizationStr;
  public int cooporganization=0;
  public String goalStr;
  public int goal=0;
  public String activitiesStr;
  public int activities=0;
  public String outputStr;
  public int output=0;
  public String measureoutputStr;
  public int measureoutput=0;
  public String outcomeStr;
  public int outcome=0;
  public String measureoutcomeStr;
  public int measureoutcome=0;
  public String continuationStr;
  public int continuation=0;
  public String sharingStr;
  public int sharing=0;
  public String budgetStr;
  public int budget=0;
  public String otherfundStr;
  public int otherfund=0;
  public String publicityStr;
  public int publicity;
  public String educationStr;
  //following used for lgrmif review
  public String problemStr;
  public int problem;
    public String problemDelibStr;//'delib' is used on deliberation form data-entry
    public int problemDelib;
  public String recordsStr;
  public int records;
    public String recordsDelibStr;
    public int recordsDelib;
  public String recordprogramStr;
  public int recordprogram;
    public String recordprogramDelibStr;
    public int recordprogramDelib;
  public String projcategoryStr;
  public int projcategory;
    public String projcategoryDelibStr;
    public int projcategoryDelib;
  public String govtsupportStr;
  public int govtsupport;
    public String govtsupportDelibStr;
    public int govtsupportDelib;
  public String reasonablecostStr;
  public int reasonablecost;
  public int improveserv;
  public String improveservStr;
    public int improveservDelib;
    public String improveservDelibStr;
  public int expenditures;
  public String expendituresStr;
    public int expendituresDelib;
    public String expendituresDelibStr;
    public int otherfundDelib;
    public String otherfundDelibStr;
    public int timetableDelib;
    public String timetableDelibStr;
    public int outcomeDelib;
    public String outcomeDelibStr;
    public String staffsupportDelibStr;
    public int staffsupportDelib;
    public String longrangeDelibStr;
    public int longrangeDelib;
  public DescriptionBean[] revcomments;
  public String problemcomment;
  public String resultcomment;
  public String workcomment;
  public String supportcomment;
  public String budgetcomment;
  public int bonuspts;
  public int bonusScore;//starting2016-17; used for bonus based on funding priority
  public int totamtreq;
  public int avgscore;
  public int finalScoreSum;
  public String finalScoreSumStr;
  public String status;//used for lgrmif panel status
  public String submit1;//used for lgrmif eval form anchors
  public String submit2;
  public String submit3;
  public String submit4;
  public String submit5;

  public RatingBean()
  {
  }

  public String getComment()
  {
    return comment;
  }

  public void setComment(String comment)
  {
    this.comment = comment;
  }

  public String getUserid()
  {
    return userid;
  }

  public void setUserid(String userid)
  {
    this.userid = userid;
  }

  public long getCommentid()
  {
    return commentid;
  }

  public void setCommentid(long commentid)
  {
    this.commentid = commentid;
  }

  public int getAppropriate()
  {
    return appropriate;
  }

  public void setAppropriate(int appropriate)
  {
    this.appropriate = appropriate;
  }

  public int getSignificance()
  {
    return significance;
  }

  public void setSignificance(int significance)
  {
    this.significance = significance;
  }

  public int getInvolvement()
  {
    return involvement;
  }

  public void setInvolvement(int involvement)
  {
    this.involvement = involvement;
  }

  public int getCoordination()
  {
    return coordination;
  }

  public void setCoordination(int coordination)
  {
    this.coordination = coordination;
  }

  public int getBibliographic()
  {
    return bibliographic;
  }

  public void setBibliographic(int bibliographic)
  {
    this.bibliographic = bibliographic;
  }

  public int getOnlinedb()
  {
    return onlinedb;
  }

  public void setOnlinedb(int onlinedb)
  {
    this.onlinedb = onlinedb;
  }

  public int getTimetable()
  {
    return timetable;
  }

  public void setTimetable(int timetable)
  {
    this.timetable = timetable;
  }

  public int getSoundness()
  {
    return soundness;
  }

  public void setSoundness(int soundness)
  {
    this.soundness = soundness;
  }

  public int getEquipment()
  {
    return equipment;
  }

  public void setEquipment(int equipment)
  {
    this.equipment = equipment;
  }

  public int getPersonnel()
  {
    return personnel;
  }

  public void setPersonnel(int personnel)
  {
    this.personnel = personnel;
  }

  public int getStorage()
  {
    return storage;
  }

  public void setStorage(int storage)
  {
    this.storage = storage;
  }

  public int getConsistentdesc()
  {
    return consistentdesc;
  }

  public void setConsistentdesc(int consistentdesc)
  {
    this.consistentdesc = consistentdesc;
  }

  public int getConsistentexp()
  {
    return consistentexp;
  }

  public void setConsistentexp(int consistentexp)
  {
    this.consistentexp = consistentexp;
  }

  public int getCosteffective()
  {
    return costeffective;
  }

  public void setCosteffective(int costeffective)
  {
    this.costeffective = costeffective;
  }

  public int getOverallscore()
  {
    return overallscore;
  }

  public void setOverallscore(int overallscore)
  {
    this.overallscore = overallscore;
  }
  
  
  
  public boolean isOutCoRange(int value) //used for Co and LGRMIF
  {
    //rating scores must be between 0 and 5
    return((value < 0 || value >5 )  );
  }
  
  public boolean isOutDiRange(int value) 
  {
    //rating scores must be between 0 and 3
    return((value < 0 || value >3 )  );
  }
  
    
  public boolean isOutOverallRange(int value) 
  {
    //rating scores must be between 0 and 10
    return (  (value < 0 || value >10 )  );
  }
  
  
    public boolean isOutMultipleOfTwo(int value) 
    {
      //rating scores must be between 0 and 10, and EVEN number
      return (  ((value < 0 || value >10 ) ||  (value % 2 !=0)) );
    }
    
    public boolean isOutMultipleOfThree(int value) 
    {
      boolean outOfRange=true;
      //rating scores must be between 0 and 15, and multiple of three
      switch(value){
          case 0:
          case 3:
          case 6:
          case 9:
          case 12:
          case 15:
            outOfRange=false;
            break;
          default:
            outOfRange=true;
            break;
      }
      return outOfRange;
    }
    
    public boolean isOutMultipleOfFive(int value) 
    {
      boolean outOfRange=true;
      //rating scores must be between 0 and 25, and multiple of five
      switch(value){
          case 0:
          case 5:
          case 10:
          case 15:
          case 20:
          case 25:
            outOfRange=false;
            break;
          default:
            outOfRange=true;
            break;
      }
      return outOfRange;
    }
  
    public boolean isMissing(String value) 
    {
      return((value == null) || (value.trim().equals("")));
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
  
  public ActionErrors validate(ActionMapping mapping,  HttpServletRequest request) 
  {  
    ActionErrors errors = new ActionErrors();  
        
    //GET GRANT MODULE REVIEWER IS WORKING ON -  DI OR CO OR LI OR LGRMIF
    String currentGrant = getModule();
    
    if(currentGrant!=null && currentGrant.equals("lg"))
        errors = validateLgRating(mapping, request);
    else if(currentGrant!=null && currentGrant.equals("di")) 
        errors = validateDiRating(mapping, request);      
    else if(currentGrant!=null && currentGrant.equals("co"))
        errors = validateCoRating(mapping, request);    
    else if(currentGrant!=null && currentGrant.equals("li"))
        errors = validateLiRating(mapping, request);
       
    //all modules use overall score
    if(getOverallscoreStr()!=null && !getOverallscoreStr().equals(""))
    {
      if(isInt(getOverallscoreStr()))
      {
        if (isOutOverallRange(Integer.parseInt(getOverallscoreStr().trim() )) ) 
          errors.add("overallData", new ActionError("errors.range", "Overall Score", "0", "10"));
      }
      else
        errors.add("overallData", new ActionError("errors.integer", "Overall Score"));
    }
            
    return (errors);
  }
  
  
  //added 4/30/12 -validation on deliberation rating form for 2012-13 FY.
  //modified 8/26/14 SH - changed validation for FY2015-16 new weighted scoring
    public ActionErrors validateLgDeliberation(ActionMapping mapping, HttpServletRequest request)
    {  
        ActionErrors errors = new ActionErrors();
        
         if(getProblemDelibStr()!=null && !getProblemDelibStr().equals("")){
           if(isInt(getProblemDelibStr()))
           {
             int score = Integer.parseInt(getProblemDelibStr().trim());
             if(isOutCoRange(score))
               errors.add("recData", new ActionError("errors.range", "1a. Defining the Problem", "0", "5"));
           }
           else
             errors.add("recData", new ActionError("errors.integer", "1a. Defining the Problem"));
         }
                  
                
        if(getRecordsDelibStr()!=null && !getRecordsDelibStr().equals("")){
          if(isInt(getRecordsDelibStr()))
          {
            int score = Integer.parseInt(getRecordsDelibStr().trim());
            if(isOutMultipleOfTwo(score))
              errors.add("recData", new ActionError("errors.multipleOfTwo", "1b. Defining Records", "0", "5"));
          }
          else
            errors.add("recData", new ActionError("errors.integer", "1b. Defining Records"));
        }
        
                
        /*SH rmvd 8/26/14 no longer used
         * if(getOtherfundDelibStr()!=null && !getOtherfundDelibStr().equals(""))
        {
          if(isInt(getOtherfundDelibStr()))
          {
            int score = Integer.parseInt(getOtherfundDelibStr().trim());
            if(isOutCoRange(score))
              errors.add("otherData", new ActionError("errors.range", "1c. Funding Essential", "0", "5"));
          }
          else
            errors.add("otherData", new ActionError("errors.integer", "1c. Funding Essential"));
        }*/
                
        
        if(getOutcomeDelibStr()!=null && !getOutcomeDelibStr().equals("")){
          if(isInt(getOutcomeDelibStr()))
          {
            int score = Integer.parseInt(getOutcomeDelibStr().trim());
            if(isOutMultipleOfTwo(score))
              errors.add("outcomeData", new ActionError("errors.multipleOfTwo", "2a. Methodology", "0", "5"));
          }
          else
            errors.add("outcomeData", new ActionError("errors.integer", "2a. Methodology"));
        }
                        
        
        if(getRecordprogramDelibStr()!=null && !getRecordprogramDelibStr().equals("")){
          if(isInt(getRecordprogramDelibStr()))
          {
            int score = Integer.parseInt(getRecordprogramDelibStr().trim());
            if(isOutMultipleOfTwo(score))
              errors.add("progData", new ActionError("errors.multipleOfTwo", "2b. Anticipated Benefits", "0", "5"));
          }
          else
            errors.add("progData", new ActionError("errors.integer", "2b. Anticipated Benefits"));
        }        
        
                
        /*SH rmvd 8/26/14 no longer used
         * if(getImproveservDelibStr()!=null && !getImproveservDelibStr().equals("")) {
          if(isInt(getImproveservDelibStr()))
          {
            int score = Integer.parseInt(getImproveservDelibStr().trim());
            if(isOutCoRange(score))
              errors.add("progData", new ActionError("errors.range", "2c. Improve local governement services", "0", "5"));
          }
          else
            errors.add("progData", new ActionError("errors.integer", "2c. Improve local government services"));
        }*/
                
        
        if(getTimetableDelibStr()!=null && !getTimetableDelibStr().equals("")) {
          if(isInt(getTimetableDelibStr()))
          {
            int score = Integer.parseInt(getTimetableDelibStr().trim());
            if(isOutMultipleOfThree(score))
              errors.add("timeData", new ActionError("errors.multipleOfThree", "3a. Project Outline"));
          }
          else
            errors.add("timeData", new ActionError("errors.integer", "3a. Project Outline"));
        }        
        
        
        if(getProjcategoryDelibStr()!=null && !getProjcategoryDelibStr().equals("")){
          if(isInt(getProjcategoryDelibStr()))
          {
            int score = Integer.parseInt(getProjcategoryDelibStr().trim());
            if(isOutMultipleOfThree(score))
              errors.add("catData", new ActionError("errors.multipleOfThree", "3b. Grant Requirements"));
          }
          else
            errors.add("catData", new ActionError("errors.integer", "3b. Grant Requirements"));
        }
                
        
        /*SH rmvd 8/26/14 no longer used
         * if(getStaffsupportDelibStr()!=null && !getStaffsupportDelibStr().equals("")){
          if(isInt(getStaffsupportDelibStr()))
          {
            int score = Integer.parseInt(getStaffsupportDelibStr().trim());
            if(isOutCoRange(score))
              errors.add("staffData", new ActionError("errors.range", "3c. Project Staff responsibilites", "0", "5"));
          }
          else
            errors.add("staffData", new ActionError("errors.integer", "3c. Project Staff responsibilites"));
        }*/
        
        
        if(getGovtsupportDelibStr()!=null && !getGovtsupportDelibStr().equals("")){
          if(isInt(getGovtsupportDelibStr()))
          {
            int score = Integer.parseInt(getGovtsupportDelibStr().trim());
            if(isOutCoRange(score))
              errors.add("govtData", new ActionError("errors.range", "4a. Previous Records Management", "0", "5"));
          }
          else
            errors.add("govtData", new ActionError("errors.integer", "4a. Previous Records Management"));
        }
                
        
        if(getLongrangeDelibStr()!=null && !getLongrangeDelibStr().equals("")){
          if(isInt(getLongrangeDelibStr()))
          {
            int score = Integer.parseInt(getLongrangeDelibStr().trim());
            if(isOutCoRange(score))
              errors.add("lrData", new ActionError("errors.range", "4b. Future Program Support", "0", "5"));
          }
          else
            errors.add("lrData", new ActionError("errors.integer", "4b. Future Program Support"));
        }
        
        
        if(getExpendituresDelibStr()!=null && !getExpendituresDelibStr().equals("")){
          if(isInt(getExpendituresDelibStr()))
          {
            int score = Integer.parseInt(getExpendituresDelibStr().trim());
            if(isOutMultipleOfFive(score))
              errors.add("lrData", new ActionError("errors.multipleOfFive", "5a. Budget narrative"));
          }
          else
            errors.add("lrData", new ActionError("errors.integer", "5a. Budget narrative"));
        }
        
        return errors;    
    }
  
    public ActionErrors validateLgRating(ActionMapping mapping, HttpServletRequest request)
    {  
        ActionErrors errors = new ActionErrors();
        
        if(getProblemStr()!=null && !getProblemStr().equals(""))
        {
          if(isInt(getProblemStr()))
          {
            int score = Integer.parseInt(getProblemStr().trim());
            if(isOutCoRange(score))
              errors.add("problData", new ActionError("errors.range", "Describe Problem", "0", "5"));
          }
          else
            errors.add("problData", new ActionError("errors.integer", "Describe Problem"));
        }
         
        if(getRecordsStr()!=null && !getRecordsStr().equals(""))
        {
          if(isInt(getRecordsStr()))
          {
            int score = Integer.parseInt(getRecordsStr().trim());
            if(isOutCoRange(score))
              errors.add("recData", new ActionError("errors.range", "Specific Records", "0", "5"));
          }
          else
            errors.add("recData", new ActionError("errors.integer", "Specific Records"));
        }
        
        if(getOtherfundStr()!=null && !getOtherfundStr().equals(""))
        {
          if(isInt(getOtherfundStr()))
          {
            int score = Integer.parseInt(getOtherfundStr().trim());
            if(isOutCoRange(score))
              errors.add("otherData", new ActionError("errors.range", "Funding Essential", "0", "5"));
          }
          else
            errors.add("otherData", new ActionError("errors.integer", "Funding Essential"));
        }
        
        if(getOutcomeStr()!=null && !getOutcomeStr().equals(""))
        {
          if(isInt(getOutcomeStr()))
          {
            int score = Integer.parseInt(getOutcomeStr().trim());
            if(isOutCoRange(score))
              errors.add("outcomeData", new ActionError("errors.range", "Intended Results", "0", "5"));
          }
          else
            errors.add("outcomeData", new ActionError("errors.integer", "Intended Results"));
        }
        
        if(getRecordprogramStr()!=null && !getRecordprogramStr().equals(""))
        {
          if(isInt(getRecordprogramStr()))
          {
            int score = Integer.parseInt(getRecordprogramStr().trim());
            if(isOutCoRange(score))
              errors.add("progData", new ActionError("errors.range", "Develop RM Program", "0", "5"));
          }
          else
            errors.add("progData", new ActionError("errors.integer", "Develop RM Program"));
        }
        
        if(getTimetableStr()!=null && !getTimetableStr().equals(""))
        {
          if(isInt(getTimetableStr()))
          {
            int score = Integer.parseInt(getTimetableStr().trim());
            if(isOutCoRange(score))
              errors.add("timeData", new ActionError("errors.range", "Plan of Work", "0", "5"));
          }
          else
            errors.add("timeData", new ActionError("errors.integer", "Plan of Work"));
        }
        
        if(getProjcategoryStr()!=null && !getProjcategoryStr().equals(""))
        {
          if(isInt(getProjcategoryStr()))
          {
            int score = Integer.parseInt(getProjcategoryStr().trim());
            if(isOutCoRange(score))
              errors.add("catData", new ActionError("errors.range", "Category Requirements", "0", "5"));
          }
          else
            errors.add("catData", new ActionError("errors.integer", "Category Requirements"));
        }
        
        if(getStaffsupportStr()!=null && !getStaffsupportStr().equals(""))
        {
          if(isInt(getStaffsupportStr()))
          {
            int score = Integer.parseInt(getStaffsupportStr().trim());
            if(isOutCoRange(score))
              errors.add("staffData", new ActionError("errors.range", "Project Staff responsibilites", "0", "5"));
          }
          else
            errors.add("staffData", new ActionError("errors.integer", "Project Staff responsibilites"));
        }
        
        if(getGovtsupportStr()!=null && !getGovtsupportStr().equals(""))
        {
          if(isInt(getGovtsupportStr()))
          {
            int score = Integer.parseInt(getGovtsupportStr().trim());
            if(isOutCoRange(score))
              errors.add("govtData", new ActionError("errors.range", "Project Contributions", "0", "5"));
          }
          else
            errors.add("govtData", new ActionError("errors.integer", "Project Contributions"));
        }
        
        if(getLongrangeStr()!=null && !getLongrangeStr().equals(""))
        {
          if(isInt(getLongrangeStr()))
          {
            int score = Integer.parseInt(getLongrangeStr().trim());
            if(isOutCoRange(score))
              errors.add("lrData", new ActionError("errors.range", "Long term maintenance", "0", "5"));
          }
          else
            errors.add("lrData", new ActionError("errors.integer", "Long term maintenance"));
        }
        
        if( !isMissing(getBudgetStr()) )
        {
          if(isInt(getBudgetStr()))
          {
            int score = Integer.parseInt(getBudgetStr().trim());
            if(isOutCoRange(score))
              errors.add("budData", new ActionError("errors.range", "Budget Items", "0", "5"));
          }
          else
            errors.add("budData", new ActionError("errors.integer", "Budget Items"));
        }
        
        if( !isMissing(getReasonablecostStr()) )
        {
          if(isInt(getReasonablecostStr()))
          {
            int score = Integer.parseInt(getReasonablecostStr().trim());
            if(isOutCoRange(score))
              errors.add("costData", new ActionError("errors.range", "Reasonable costs", "0", "5"));
          }
          else
            errors.add("costData", new ActionError("errors.integer", "Reasonable costs"));
        }
        
        
        if( !isMissing(getImproveservStr()) )
        {
          if(isInt(getImproveservStr()))
          {
            int score = Integer.parseInt(getImproveservStr().trim());
            if(isOutCoRange(score))
              errors.add("imprData", new ActionError("errors.range", "Improve Govt Services", "0", "5"));
          }
          else
            errors.add("imprData", new ActionError("errors.integer", "Improve Govt Services"));
        }
        
        
        if( !isMissing(getExpendituresStr()) )
        {
          if(isInt(getExpendituresStr()))
          {
            int score = Integer.parseInt(getExpendituresStr().trim());
            if(isOutCoRange(score))
              errors.add("expendData", new ActionError("errors.range", "Justify project expenditures", "0", "5"));
          }
          else
            errors.add("expendData", new ActionError("errors.integer", "Justify project expenditures"));
        }
        
        
        //if recommendation=N, then recommend amt must =0; else error
        if(!isMissing(getRecommendation())  && getRecommendation().equalsIgnoreCase("N")) {
            if(!isMissing(getRecommendamtStr()))
            {
                if(isInt(getRecommendamtStr()))
                {
                  int recom = Integer.parseInt(getRecommendamtStr().trim());
                  if(recom!=0)
                    errors.add("recomData", new ActionError("errors.recommendamt"));
                }
            }
        }
                
        //if recommendation=F, then recommend amt must = requested amt; else error
        if(!isMissing(getRecommendation())  && getRecommendation().equalsIgnoreCase("F")) {
            if(!isMissing(getRecommendamtStr()))
            {
                if(isInt(getRecommendamtStr()))
                {
                  int recom = Integer.parseInt(getRecommendamtStr().trim());
                  //recommend should = request
                  if(recom!= getTotamtreq())
                    errors.add("recomData", new ActionError("errors.recommendfund"));
                }
            }
            else{//the recommended amt is missing
                errors.add("recomreq", new ActionError("errors.recommendfund"));                
            }
        }
        
        //if recommendation=M, then recommend amt must be>0 and <requested amt
         if(!isMissing(getRecommendation())  && getRecommendation().equalsIgnoreCase("M")) {
             if(!isMissing(getRecommendamtStr()))
             {
                 if(isInt(getRecommendamtStr()))
                 {
                   int recom = Integer.parseInt(getRecommendamtStr().trim());
                   //recommend < request   and   recommend > 0
                   if((recom==0) || (recom >= getTotamtreq()))
                     errors.add("recomData", new ActionError("errors.recommendmod"));
                 }
             }
             else{//the recommended amt is missing
                 errors.add("recomreq", new ActionError("errors.recommendmod"));                
             }
         }
        return errors;
    }
  
  
    public ActionErrors validateLgAdminRating(ActionMapping mapping, HttpServletRequest request)
    {  
        ActionErrors errors = new ActionErrors();
        
        //if recommendation=N, then recommend amt must =0; else error
        if(!isMissing(getRecommendation())  && getRecommendation().equalsIgnoreCase("N")) {
            if(!isMissing(getRecommendamtStr()))
            {
                if(isInt(getRecommendamtStr()))
                {
                  int recom = Integer.parseInt(getRecommendamtStr().trim());
                  if(recom!=0)
                    errors.add("recomData", new ActionError("errors.recommendamt"));
                }
            }
        }
        
        //if recommendation=F, then recommend amt must = requested amt; else error
        if(!isMissing(getRecommendation())  && getRecommendation().equalsIgnoreCase("F")) {
            if(!isMissing(getRecommendamtStr()))
            {
                if(isInt(getRecommendamtStr()))
                {
                  int recom = Integer.parseInt(getRecommendamtStr().trim());
                  //recommend should = request
                  if(recom!= getTotamtreq())
                    errors.add("recomData", new ActionError("errors.recommendfund"));
                }
            }
            else{//the recommended amt is missing
                errors.add("recomreq", new ActionError("errors.recommendfund"));                
            }
        }
        
        //if recommendation=M, then recommend amt must be>0 and <requested amt
         if(!isMissing(getRecommendation())  && getRecommendation().equalsIgnoreCase("M")) {
             if(!isMissing(getRecommendamtStr()))
             {
                 if(isInt(getRecommendamtStr()))
                 {
                   int recom = Integer.parseInt(getRecommendamtStr().trim());
                   //recommend < request   and   recommend > 0
                   if((recom==0) || (recom >= getTotamtreq()))
                     errors.add("recomData", new ActionError("errors.recommendmod"));
                 }
             }
             else{//the recommended amt is missing
                 errors.add("recomreq", new ActionError("errors.recommendmod"));                
             }
         }        
        return errors;
    }
  
  
  public ActionErrors validateLiRating(ActionMapping mapping, HttpServletRequest request)
  {  
      ActionErrors errors = new ActionErrors();
      
      if(getAbstractStr()!=null && !getAbstractStr().equals(""))
      {
        if(isInt(getAbstractStr()))
        {
          int score = Integer.parseInt(getAbstractStr().trim());
          if(isOutCoRange(score))
            errors.add("abstractData", new ActionError("errors.range", "Abstract", "0", "5"));
        }
        else
          errors.add("abstractData", new ActionError("errors.integer", "Abstract"));
      }
      
      if(getGroupneedStr()!=null && !getGroupneedStr().equals(""))
      {
        if(isInt(getGroupneedStr()))
        {
          int score = Integer.parseInt(getGroupneedStr().trim());
          if(isOutCoRange(score))
            errors.add("needData", new ActionError("errors.range", "Group need", "0", "5"));
        }
        else
          errors.add("needData", new ActionError("errors.integer", "Group need"));
      }
      
      if(getLongrangeStr()!=null && !getLongrangeStr().equals(""))
      {
        if(isInt(getLongrangeStr()))
        {
          int score = Integer.parseInt(getLongrangeStr().trim());
          if(isOutCoRange(score))
            errors.add("longData", new ActionError("errors.range", "Long range plan", "0", "5"));
        }
        else
          errors.add("longData", new ActionError("errors.integer", "Long range plan"));
      }
      
      if(getLevelserviceStr()!=null && !getLevelserviceStr().equals(""))
      {
        if(isInt(getLevelserviceStr()))
        {
          int score = Integer.parseInt(getLevelserviceStr().trim());
          if(isOutCoRange(score))
            errors.add("servData", new ActionError("errors.range", "Level of service", "0", "5"));
        }
        else
          errors.add("servData", new ActionError("errors.integer", "Level of service"));
      }
      
      if(getCooporganizationStr()!=null && !getCooporganizationStr().equals(""))
      {
        if(isInt(getCooporganizationStr()))
        {
          int score = Integer.parseInt(getCooporganizationStr().trim());
          if(isOutCoRange(score))
            errors.add("organData", new ActionError("errors.range", "Cooperating Organizations", "0", "5"));
        }
        else
          errors.add("organData", new ActionError("errors.integer", "Cooperating Organizations"));
      }
      
      if(getGoalStr()!=null && !getGoalStr().equals(""))
      {
        if(isInt(getGoalStr()))
        {
          int score = Integer.parseInt(getGoalStr().trim());
          if(isOutOverallRange(score))
            errors.add("goalData", new ActionError("errors.range", "Goal, objectives", "0", "10"));
        }
        else
          errors.add("goalData", new ActionError("errors.integer", "Goal, objectives"));
      }
      
      if(getActivitiesStr()!=null && !getActivitiesStr().equals(""))
      {
        if(isInt(getActivitiesStr()))
        {
          int score = Integer.parseInt(getActivitiesStr().trim());
          if(isOutOverallRange(score))
            errors.add("activityData", new ActionError("errors.range", "Activities", "0", "10"));
        }
        else
          errors.add("activityData", new ActionError("errors.integer", "Activities"));
      }
      
      if(getTimetableStr()!=null && !getTimetableStr().equals(""))
      {
        if(isInt(getTimetableStr()))
        {
          int score = Integer.parseInt(getTimetableStr().trim());
          if(isOutCoRange(score))
            errors.add("timeData", new ActionError("errors.range", "Timeline", "0", "5"));
        }
        else
          errors.add("timeData", new ActionError("errors.integer", "Timeline"));
      }
      
      if(getOutputStr()!=null && !getOutputStr().equals(""))
      {
        if(isInt(getOutputStr()))
        {
          int score = Integer.parseInt(getOutputStr().trim());
          if(isOutCoRange(score))
            errors.add("outputData", new ActionError("errors.range", "Product output", "0", "5"));
        }
        else
          errors.add("outputData", new ActionError("errors.integer", "Product output"));
      }
      
      if(getMeasureoutputStr()!=null && !getMeasureoutputStr().equals(""))
      {
        if(isInt(getMeasureoutputStr()))
        {
          int score = Integer.parseInt(getMeasureoutputStr().trim());
          if(isOutCoRange(score))
            errors.add("moutputData", new ActionError("errors.range", "Measure output", "0", "5"));
        }
        else
          errors.add("moutputData", new ActionError("errors.integer", "Measure output"));
      }
      
      if(getOutcomeStr()!=null && !getOutcomeStr().equals(""))
      {
        if(isInt(getOutcomeStr()))
        {
          int score = Integer.parseInt(getOutcomeStr().trim());
          if(isOutCoRange(score))
            errors.add("outcomeData", new ActionError("errors.range", "Outcomes and impact", "0", "5"));
        }
        else
          errors.add("outcomeData", new ActionError("errors.integer", "Outcomes and impact"));
      }
      
      if(getMeasureoutcomeStr()!=null && !getMeasureoutcomeStr().equals(""))
      {
        if(isInt(getMeasureoutcomeStr()))
        {
          int score = Integer.parseInt(getMeasureoutcomeStr().trim());
          if(isOutCoRange(score))
            errors.add("moutcomeData", new ActionError("errors.range", "Measure outcome", "0", "5"));
        }
        else
          errors.add("moutcomeData", new ActionError("errors.integer", "Measure outcome"));
      }
      
      if(getContinuationStr()!=null && !getContinuationStr().equals(""))
      {
        if(isInt(getContinuationStr()))
        {
          int score = Integer.parseInt(getContinuationStr().trim());
          if(isOutCoRange(score))
            errors.add("contData", new ActionError("errors.range", "Project continuation", "0", "5"));
        }
        else
          errors.add("contData", new ActionError("errors.integer", "Project continuation"));
      }
      
      if(getSharingStr()!=null && !getSharingStr().equals(""))
      {
        if(isInt(getSharingStr()))
        {
          int score = Integer.parseInt(getSharingStr().trim());
          if(isOutCoRange(score))
            errors.add("shareData", new ActionError("errors.range", "Sharing results", "0", "5"));
        }
        else
          errors.add("shareData", new ActionError("errors.integer", "Sharing results"));
      }
      
      if(getBudgetStr()!=null && !getBudgetStr().equals(""))
      {
        if(isInt(getBudgetStr()))
        {
          int score = Integer.parseInt(getBudgetStr().trim());
          if(isOutOverallRange(score))
            errors.add("budData", new ActionError("errors.range", "Budget", "0", "10"));
        }
        else
          errors.add("budData", new ActionError("errors.integer", "Budget"));
      }
      
      if(getOtherfundStr()!=null && !getOtherfundStr().equals(""))
      {
        if(isInt(getOtherfundStr()))
        {
          int score = Integer.parseInt(getOtherfundStr().trim());
          if(isOutOverallRange(score))
            errors.add("fundData", new ActionError("errors.range", "Other funds", "0", "10"));
        }
        else
          errors.add("fundData", new ActionError("errors.integer", "Other funds"));
      }
      
      return errors;
  }
  
  
  public ActionErrors validateCoRating(ActionMapping mapping, HttpServletRequest request)
  {  
      ActionErrors errors = new ActionErrors();
      
      //CHECK THE COORDINATED RATING CATEGORIES
      if(getAppropriateStr()!=null && !getAppropriateStr().equals(""))
      {
        if(isInt(getAppropriateStr()))
        {
          int score = Integer.parseInt(getAppropriateStr().trim());
          if(isOutCoRange(score))
            errors.add("appropriateData", new ActionError("errors.range", "Appropriate materials", "0", "5"));
        }
        else
          errors.add("appropriateData", new ActionError("errors.integer", "Appropriate materials"));
      }
      
      if(getSignificanceStr()!=null && !getSignificanceStr().equals(""))
      {
        if(isInt(getSignificanceStr()))
        {
          int score = Integer.parseInt(getSignificanceStr().trim());
          if(isOutCoRange(score))
            errors.add("significantData", new ActionError("errors.range", "Significance for Research", "0", "5"));
        }
        else
        {
          errors.add("significantData", new ActionError("errors.integer", "Significance for Research"));
        }
      }
      
      if(getInvolvementStr()!=null && !getInvolvementStr().equals(""))
      {
        if(isInt(getInvolvementStr()))
        {
          int score = Integer.parseInt(getInvolvementStr().trim());
          if(isOutCoRange(score))
            errors.add("involveData", new ActionError("errors.range", "Involvement of Libraries", "0", "5"));
        }
        else
        {
          errors.add("involveData", new ActionError("errors.integer", "Involvement of Libraries"));
        }
      }
      
      if(getCoordinationStr()!=null && !getCoordinationStr().equals(""))
      {
        if(isInt(getCoordinationStr()))
        {
          int score = Integer.parseInt(getCoordinationStr().trim());
          if(isOutCoRange(score))
            errors.add("coordData", new ActionError("errors.range", "Coordination of activities", "0", "5"));
        }
        else
        {
          errors.add("coordData", new ActionError("errors.integer", "Coordination of activities"));
        }
      }
      
      if(getBibliographicStr()!=null && !getBibliographicStr().equals(""))
      {
        if(isInt(getBibliographicStr()))
        {
          int score = Integer.parseInt(getBibliographicStr().trim());
          if(isOutCoRange(score))
            errors.add("biblioData", new ActionError("errors.range", "Bibliographic control", "0", "5"));
        }
        else
        {
          errors.add("biblioData", new ActionError("errors.integer", "Bibliographic control"));
        }
      }
    
      if(getOnlinedbStr()!=null && !getOnlinedbStr().equals(""))
      {
        if(isInt(getOnlinedbStr()))
        {
          int score = Integer.parseInt(getOnlinedbStr().trim());
          if(isOutCoRange(score))
            errors.add("onlineData", new ActionError("errors.range", "Online Databases", "0", "5"));
        }
        else
          errors.add("onlineData", new ActionError("errors.integer", "Online Databases"));
      }
      
      if(getTimetableStr()!=null && !getTimetableStr().equals(""))
      {
        if(isInt(getTimetableStr()))
        {
          int score = Integer.parseInt(getTimetableStr().trim());
          if(isOutCoRange(score))
            errors.add("timeData", new ActionError("errors.range", "Timetable", "0", "5"));
        }
        else
          errors.add("timeData", new ActionError("errors.integer", "Timetable"));
      }
      
      if(getSoundnessStr()!=null && !getSoundnessStr().equals(""))
      {
        if(isInt(getSoundnessStr()))
        {
          int score = Integer.parseInt(getSoundnessStr().trim());
          if(isOutCoRange(score))
            errors.add("sounData", new ActionError("errors.range", "Technical Soundness", "0", "5"));
        }
        else
          errors.add("sounData", new ActionError("errors.integer", "Technical Soundness"));
      }
      
      if(getEquipmentStr()!=null && !getEquipmentStr().equals(""))
      {
        if(isInt(getEquipmentStr()))
        {
          int score = Integer.parseInt(getEquipmentStr().trim());
          if(isOutCoRange(score))
            errors.add("equipData", new ActionError("errors.range", "Special Equipment", "0", "5"));
        }
        else
          errors.add("equipData", new ActionError("errors.integer", "Special Equipment"));
      }
      
      if(getPersonnelStr()!=null && !getPersonnelStr().equals(""))
      {
        if(isInt(getPersonnelStr()))
        {
          int score = Integer.parseInt(getPersonnelStr().trim());
          if(isOutCoRange(score))
            errors.add("persData", new ActionError("errors.range", "Personnel and Vendors", "0", "5"));
        }
        else
          errors.add("persData", new ActionError("errors.integer", "Personnel and Vendors"));
      }
      
      if(getStorageStr()!=null && !getStorageStr().equals(""))
      {
        if(isInt(getStorageStr()))
        {
          int score = Integer.parseInt(getStorageStr().trim());
          if(isOutCoRange(score))
            errors.add("storData", new ActionError("errors.range", "Adequate storage", "0", "5"));
        }
        else
          errors.add("storData", new ActionError("errors.integer", "Adequate storage"));
      }
      
      if(getConsistentdescStr()!=null && !getConsistentdescStr().equals(""))
      {
        if(isInt(getConsistentdescStr()))
        {
          int score = Integer.parseInt(getConsistentdescStr().trim());
          if(isOutCoRange(score))
            errors.add("cdescData", new ActionError("errors.range", "Consistent with Description", "0", "5"));
        }
        else
          errors.add("cdescData", new ActionError("errors.integer", "Consistent with Description"));
      }
      
      if(getConsistentexpStr()!=null && !getConsistentexpStr().equals(""))
      {
        if(isInt(getConsistentexpStr()))
        {
          int score = Integer.parseInt(getConsistentexpStr().trim());
          if(isOutCoRange(score))
            errors.add("cexpData", new ActionError("errors.range", "Consistent with Expenses", "0", "5"));
        }
        else
          errors.add("cexpData", new ActionError("errors.integer", "Consistent with Expenses"));
      }
      
      if(getCosteffectiveStr()!=null && !getCosteffectiveStr().equals(""))
      {
        if(isInt(getCosteffectiveStr()))
        {
          int score = Integer.parseInt(getCosteffectiveStr().trim());
          if(isOutCoRange(score))
            errors.add("ceffData", new ActionError("errors.range", "Cost Effectiveness", "0", "5"));
        }
        else
          errors.add("ceffData", new ActionError("errors.integer", "Cost Effectiveness"));
      }
      
      //education form-------------------------------------------------------------------
      if(getGroupneedStr()!= null && !getGroupneedStr().equals(""))
      {
        if(isInt(getGroupneedStr()))
        {
          int score = Integer.parseInt(getGroupneedStr().trim());
          if(isOutCoRange(score))
            errors.add("grpneed", new ActionError("errors.range", "Need for Training", "0", "5"));
        }
        else
          errors.add("grpneed", new ActionError("errors.integer", "Need for Training"));
      }
      
      if(getGoalStr()!= null && !getGoalStr().equals(""))
      {
        if(isInt(getGoalStr()))
        {
          int score = Integer.parseInt(getGoalStr().trim());
          if(isOutCoRange(score))
            errors.add("goal", new ActionError("errors.range", "Training Objectives", "0", "5"));
        }
        else
          errors.add("goal", new ActionError("errors.integer", "Training Objectives"));
      }
      
      if(getPublicityStr()!= null && !getPublicityStr().equals(""))
      {
        if(isInt(getPublicityStr()))
        {
          int score = Integer.parseInt(getPublicityStr().trim());
          if(isOutCoRange(score))
            errors.add("publicity", new ActionError("errors.range", "Publicity", "0", "5"));
        }
        else
          errors.add("publicity", new ActionError("errors.integer", "Publicity"));
      }
      
      if(getSharingStr()!= null && !getSharingStr().equals(""))
      {
        if(isInt(getSharingStr()))
        {
          int score = Integer.parseInt(getSharingStr().trim());
          if(isOutCoRange(score))
            errors.add("shar", new ActionError("errors.range", "Information dissemination", "0", "5"));
        }
        else
          errors.add("shar", new ActionError("errors.integer", "Information dissemination"));
      }
      
      return errors;
  }
  
  
  public ActionErrors validateDiRating(ActionMapping mapping, HttpServletRequest request)
  {
    ActionErrors errors = new ActionErrors();
    
      //CHECK FOR VALID DISCRETIONARY SCORES
      if(getInstcpStr()!= null && !getInstcpStr().equals(""))
      {
        if(isInt(getInstcpStr()))
        {
          int score = Integer.parseInt(getInstcpStr().trim());
          if(isOutDiRange(score))
            errors.add("instcp", new ActionError("errors.range", "Adequacy of C/P activities", "0", "3"));
        }
        else
          errors.add("instcpData", new ActionError("errors.integer", "Adequacy of C/P activities"));
      }
      
      if(getStorageStr()!= null && !getStorageStr().equals(""))
      {
        if(isInt(getStorageStr()))
        {
          int score = Integer.parseInt(getStorageStr().trim());
          if(isOutDiRange(score))
            errors.add("storage", new ActionError("errors.range", "Adequate storage", "0", "3"));
        }
        else
          errors.add("storageData", new ActionError("errors.integer", "Adequate storage"));
      }
      
      if(getDisasterStr()!= null && !getDisasterStr().equals(""))
      {
        if(isInt(getDisasterStr()))
        {
          int score = Integer.parseInt(getDisasterStr().trim());
          if(isOutDiRange(score))
            errors.add("disaster", new ActionError("errors.range", "Disaster preparations", "0", "3"));
        }
        else
          errors.add("disasterData", new ActionError("errors.integer", "Disaster preparations"));
      }
      
      if(getSecurityStr()!= null && !getSecurityStr().equals(""))
      {
        if(isInt(getSecurityStr()))
        {
          int score = Integer.parseInt(getSecurityStr().trim());
          if(isOutDiRange(score))
            errors.add("security", new ActionError("errors.range", "Security arrangements", "0", "3"));
        }
        else
          errors.add("securityData", new ActionError("errors.integer", "Security arrangements"));
      }
      
      if(getCoopactivitiesStr()!= null && !getCoopactivitiesStr().equals(""))
      {
        if(isInt(getCoopactivitiesStr()))
        {
          int score = Integer.parseInt(getCoopactivitiesStr().trim());
          if(isOutDiRange(score))
            errors.add("coopactiv", new ActionError("errors.range", "Participation in regional C/P activities", "0", "3"));
        }
        else
          errors.add("coopactivData", new ActionError("errors.integer", "Participation in regional C/P activities"));
      }
      
      if(getAvailabilityStr()!= null && !getAvailabilityStr().equals(""))
      {
        if(isInt(getAvailabilityStr()))
        {
          int score = Integer.parseInt(getAvailabilityStr().trim());
          if(isOutDiRange(score))
            errors.add("avail", new ActionError("errors.range", "Availability of materials to users", "0", "3"));
        }
        else
          errors.add("availData", new ActionError("errors.integer", "Availability of materials to users"));
      }
      
      if(getBibliographicStr()!= null && !getBibliographicStr().equals(""))
      {
        if(isInt(getBibliographicStr()))
        {
          int score = Integer.parseInt(getBibliographicStr().trim());
          if(isOutDiRange(score))
            errors.add("biblio", new ActionError("errors.range", "Bibliographic Control", "0", "3"));
        }
        else
          errors.add("biblioData", new ActionError("errors.integer", "Bibliographic Control"));
      }
      
      if(getAppropriateStr()!= null && !getAppropriateStr().equals(""))
      {
        if(isInt(getAppropriateStr()))
        {
          int score = Integer.parseInt(getAppropriateStr().trim());
          if(isOutDiRange(score))
            errors.add("approp", new ActionError("errors.range", "Appropriateness of materials", "0", "3"));
        }
        else
          errors.add("appropData", new ActionError("errors.integer", "Appropriateness of materials"));
      }
      
      if(getSignificanceStr()!= null && !getSignificanceStr().equals(""))
      {
        if(isInt(getSignificanceStr()))
        {
          int score = Integer.parseInt(getSignificanceStr().trim());
          if(isOutDiRange(score))
            errors.add("signif", new ActionError("errors.range", "Significance for research", "0", "3"));
        }
        else
          errors.add("signifData", new ActionError("errors.integer", "Significance for research"));
      }
      
      if(getTimetableStr()!= null && !getTimetableStr().equals(""))
      {
        if(isInt(getTimetableStr()))
        {
          int score = Integer.parseInt(getTimetableStr().trim());
          if(isOutDiRange(score))
            errors.add("timetable", new ActionError("errors.range", "Timetable", "0", "3"));
        }
        else
          errors.add("timetableData", new ActionError("errors.integer", "Timetable"));
      }
      
      if(getSoundnessStr()!= null && !getSoundnessStr().equals(""))
      {
        if(isInt(getSoundnessStr()))
        {
          int score = Integer.parseInt(getSoundnessStr().trim());
          if(isOutDiRange(score))
            errors.add("sound", new ActionError("errors.range", "Soundness of activities", "0", "3"));
        }
        else
          errors.add("soundData", new ActionError("errors.integer", "Soundness of activities"));
      }
      
      if(getPersonnelStr()!= null && !getPersonnelStr().equals(""))
      {
        if(isInt(getPersonnelStr()))
        {
          int score = Integer.parseInt(getPersonnelStr().trim());
          if(isOutDiRange(score))
            errors.add("personnel", new ActionError("errors.range", "Personnel and vendors", "0", "3"));
        }
        else
          errors.add("personnelData", new ActionError("errors.integer", "Personnel and vendors"));
      }
      
      if(getStaffsupportStr()!= null && !getStaffsupportStr().equals(""))
      {
        if(isInt(getStaffsupportStr()))
        {
          int score = Integer.parseInt(getStaffsupportStr().trim());
          if(isOutDiRange(score))
            errors.add("staffsup", new ActionError("errors.range", "Institutional Staff support", "0", "3"));
        }
        else
          errors.add("staffsupData", new ActionError("errors.integer", "Institutional Staff support"));
      }
      
      if(getFinancialsupportStr()!= null && !getFinancialsupportStr().equals(""))
      {
        if(isInt(getFinancialsupportStr()))
        {
          int score = Integer.parseInt(getFinancialsupportStr().trim());
          if(isOutDiRange(score))
            errors.add("financialsup", new ActionError("errors.range", "Institutional Financial support", "0", "3"));
        }
        else
          errors.add("financialData", new ActionError("errors.integer", "Institutional Financial support"));
      }
      
      if(getConsistentdescStr()!= null && !getConsistentdescStr().equals(""))
      {
        if(isInt(getConsistentdescStr()))
        {
          int score = Integer.parseInt(getConsistentdescStr().trim());
          if(isOutDiRange(score))
            errors.add("consistentdesc", new ActionError("errors.range", "Consistent budget and project description", "0", "3"));
        }
        else
          errors.add("consisdescData", new ActionError("errors.integer", "Consistent budget and project description"));
      }
      
      if(getCosteffectiveStr()!= null && !getCosteffectiveStr().equals(""))
      {
        if(isInt(getCosteffectiveStr()))
        {
          int score = Integer.parseInt(getCosteffectiveStr().trim());
          if(isOutDiRange(score))
            errors.add("costeffect", new ActionError("errors.range", "Cost effectiveness", "0", "3"));
        }
        else
          errors.add("costeffectData", new ActionError("errors.integer", "Cost effectiveness"));
      }
      
      if(getConsistentexpStr()!= null && !getConsistentexpStr().equals(""))
      {
        if(isInt(getConsistentexpStr()))
        {
          int score = Integer.parseInt(getConsistentexpStr().trim());
          if(isOutDiRange(score))
            errors.add("consisexp", new ActionError("errors.range", "Consistent budget and expenditures", "0", "3"));
        }
        else
          errors.add("consisexpData", new ActionError("errors.integer", "Consistent budget and expenditures"));
      }
      //education form-------------------------------------------------------------------
      if(getGroupneedStr()!= null && !getGroupneedStr().equals(""))
      {
        if(isInt(getGroupneedStr()))
        {
          int score = Integer.parseInt(getGroupneedStr().trim());
          if(isOutDiRange(score))
            errors.add("grpneed", new ActionError("errors.range", "Need for Training", "0", "3"));
        }
        else
          errors.add("grpneed", new ActionError("errors.integer", "Need for Training"));
      }
      
      if(getGoalStr()!= null && !getGoalStr().equals(""))
      {
        if(isInt(getGoalStr()))
        {
          int score = Integer.parseInt(getGoalStr().trim());
          if(isOutDiRange(score))
            errors.add("goal", new ActionError("errors.range", "Training Objectives", "0", "3"));
        }
        else
          errors.add("goal", new ActionError("errors.integer", "Training Objectives"));
      }
      
      if(getPublicityStr()!= null && !getPublicityStr().equals(""))
      {
        if(isInt(getPublicityStr()))
        {
          int score = Integer.parseInt(getPublicityStr().trim());
          if(isOutDiRange(score))
            errors.add("publicity", new ActionError("errors.range", "Publicity", "0", "3"));
        }
        else
          errors.add("publicity", new ActionError("errors.integer", "Publicity"));
      }
      
      if(getSharingStr()!= null && !getSharingStr().equals(""))
      {
        if(isInt(getSharingStr()))
        {
          int score = Integer.parseInt(getSharingStr().trim());
          if(isOutDiRange(score))
            errors.add("shar", new ActionError("errors.range", "Information dissemination", "0", "3"));
        }
        else
          errors.add("shar", new ActionError("errors.integer", "Information dissemination"));
      }
      return errors;
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

  public int getScore()
  {
    return score;
  }

  public void setScore(int score)
  {
    this.score = score;
  }

  public int getRatingtype()
  {
    return ratingtype;
  }

  public void setRatingtype(int ratingtype)
  {
    this.ratingtype = ratingtype;
  }

  public int getSumscore()
  {
    return sumscore;
  }

  public void setSumscore(int sumscore)
  {
    this.sumscore = sumscore;
  }

  public String getAppropriateStr()
  {
    return appropriateStr;
  }

  public void setAppropriateStr(String appropriateStr)
  {
    this.appropriateStr = appropriateStr;
  }

  public String getBibliographicStr()
  {
    return bibliographicStr;
  }

  public void setBibliographicStr(String bibliographicStr)
  {
    this.bibliographicStr = bibliographicStr;
  }

  public String getConsistentdescStr()
  {
    return consistentdescStr;
  }

  public void setConsistentdescStr(String consistentdescStr)
  {
    this.consistentdescStr = consistentdescStr;
  }

  public String getConsistentexpStr()
  {
    return consistentexpStr;
  }

  public void setConsistentexpStr(String consistentexpStr)
  {
    this.consistentexpStr = consistentexpStr;
  }

  public String getCoordinationStr()
  {
    return coordinationStr;
  }

  public void setCoordinationStr(String coordinationStr)
  {
    this.coordinationStr = coordinationStr;
  }

  public String getCosteffectiveStr()
  {
    return costeffectiveStr;
  }

  public void setCosteffectiveStr(String costeffectiveStr)
  {
    this.costeffectiveStr = costeffectiveStr;
  }

  public String getEquipmentStr()
  {
    return equipmentStr;
  }

  public void setEquipmentStr(String equipmentStr)
  {
    this.equipmentStr = equipmentStr;
  }

  public String getInvolvementStr()
  {
    return involvementStr;
  }

  public void setInvolvementStr(String involvementStr)
  {
    this.involvementStr = involvementStr;
  }

  public String getOnlinedbStr()
  {
    return onlinedbStr;
  }

  public void setOnlinedbStr(String onlinedbStr)
  {
    this.onlinedbStr = onlinedbStr;
  }

  public String getOverallscoreStr()
  {
    return overallscoreStr;
  }

  public void setOverallscoreStr(String overallscoreStr)
  {
    this.overallscoreStr = overallscoreStr;
  }

  public String getPersonnelStr()
  {
    return personnelStr;
  }

  public void setPersonnelStr(String personnelStr)
  {
    this.personnelStr = personnelStr;
  }

  public String getSignificanceStr()
  {
    return significanceStr;
  }

  public void setSignificanceStr(String significanceStr)
  {
    this.significanceStr = significanceStr;
  }

  public String getSoundnessStr()
  {
    return soundnessStr;
  }

  public void setSoundnessStr(String soundnessStr)
  {
    this.soundnessStr = soundnessStr;
  }

  public String getStorageStr()
  {
    return storageStr;
  }

  public void setStorageStr(String storageStr)
  {
    this.storageStr = storageStr;
  }

  public String getTimetableStr()
  {
    return timetableStr;
  }

  public void setTimetableStr(String timetableStr)
  {
    this.timetableStr = timetableStr;
  }

  public String getScoreStr()
  {
    return scoreStr;
  }

  public void setScoreStr(String scoreStr)
  {
    this.scoreStr = scoreStr;
  }

  public int getInstcp()
  {
    return instcp;
  }

  public void setInstcp(int instcp)
  {
    this.instcp = instcp;
  }

  public String getInstcpStr()
  {
    return instcpStr;
  }

  public void setInstcpStr(String instcpStr)
  {
    this.instcpStr = instcpStr;
  }

  public int getDisaster()
  {
    return disaster;
  }

  public void setDisaster(int disaster)
  {
    this.disaster = disaster;
  }

  public String getDisasterStr()
  {
    return disasterStr;
  }

  public void setDisasterStr(String disasterStr)
  {
    this.disasterStr = disasterStr;
  }

  public int getSecurity()
  {
    return security;
  }

  public void setSecurity(int security)
  {
    this.security = security;
  }

  public String getSecurityStr()
  {
    return securityStr;
  }

  public void setSecurityStr(String securityStr)
  {
    this.securityStr = securityStr;
  }

  public int getCoopactivities()
  {
    return coopactivities;
  }

  public void setCoopactivities(int coopactivities)
  {
    this.coopactivities = coopactivities;
  }

  public String getCoopactivitiesStr()
  {
    return coopactivitiesStr;
  }

  public void setCoopactivitiesStr(String coopactivitiesStr)
  {
    this.coopactivitiesStr = coopactivitiesStr;
  }

  public int getAvailability()
  {
    return availability;
  }

  public void setAvailability(int availability)
  {
    this.availability = availability;
  }

  public String getAvailabilityStr()
  {
    return availabilityStr;
  }

  public void setAvailabilityStr(String availabilityStr)
  {
    this.availabilityStr = availabilityStr;
  }

  public int getStaffsupport()
  {
    return staffsupport;
  }

  public void setStaffsupport(int staffsupport)
  {
    this.staffsupport = staffsupport;
  }

  public String getStaffsupportStr()
  {
    return staffsupportStr;
  }

  public void setStaffsupportStr(String staffsupportStr)
  {
    this.staffsupportStr = staffsupportStr;
  }

  public int getFinancialsupport()
  {
    return financialsupport;
  }

  public void setFinancialsupport(int financialsupport)
  {
    this.financialsupport = financialsupport;
  }

  public String getFinancialsupportStr()
  {
    return financialsupportStr;
  }

  public void setFinancialsupportStr(String financialsupportStr)
  {
    this.financialsupportStr = financialsupportStr;
  }

  public String getModule()
  {
    return module;
  }

  public void setModule(String module)
  {
    this.module = module;
  }

  public String getAbstractStr()
  {
    return abstractStr;
  }

  public void setAbstractStr(String abstractStr)
  {
    this.abstractStr = abstractStr;
  }

  public int getAbstractInt()
  {
    return abstractInt;
  }

  public void setAbstractInt(int abstractInt)
  {
    this.abstractInt = abstractInt;
  }

  public int getGroupneed()
  {
    return groupneed;
  }

  public void setGroupneed(int groupneed)
  {
    this.groupneed = groupneed;
  }

  public String getGroupneedStr()
  {
    return groupneedStr;
  }

  public void setGroupneedStr(String groupneedStr)
  {
    this.groupneedStr = groupneedStr;
  }

  public String getLongrangeStr()
  {
    return longrangeStr;
  }

  public void setLongrangeStr(String longrangeStr)
  {
    this.longrangeStr = longrangeStr;
  }

  public int getLongrange()
  {
    return longrange;
  }

  public void setLongrange(int longrange)
  {
    this.longrange = longrange;
  }

  public String getLevelserviceStr()
  {
    return levelserviceStr;
  }

  public void setLevelserviceStr(String levelserviceStr)
  {
    this.levelserviceStr = levelserviceStr;
  }

  public int getLevelservice()
  {
    return levelservice;
  }

  public void setLevelservice(int levelservice)
  {
    this.levelservice = levelservice;
  }

  public String getCooporganizationStr()
  {
    return cooporganizationStr;
  }

  public void setCooporganizationStr(String cooporganizationStr)
  {
    this.cooporganizationStr = cooporganizationStr;
  }

  public int getCooporganization()
  {
    return cooporganization;
  }

  public void setCooporganization(int cooporganization)
  {
    this.cooporganization = cooporganization;
  }

  public String getGoalStr()
  {
    return goalStr;
  }

  public void setGoalStr(String goalStr)
  {
    this.goalStr = goalStr;
  }

  public int getGoal()
  {
    return goal;
  }

  public void setGoal(int goal)
  {
    this.goal = goal;
  }

  public String getActivitiesStr()
  {
    return activitiesStr;
  }

  public void setActivitiesStr(String activitiesStr)
  {
    this.activitiesStr = activitiesStr;
  }

  public int getActivities()
  {
    return activities;
  }

  public void setActivities(int activities)
  {
    this.activities = activities;
  }

  public String getOutputStr()
  {
    return outputStr;
  }

  public void setOutputStr(String outputStr)
  {
    this.outputStr = outputStr;
  }

  public int getOutput()
  {
    return output;
  }

  public void setOutput(int output)
  {
    this.output = output;
  }

  public String getMeasureoutputStr()
  {
    return measureoutputStr;
  }

  public void setMeasureoutputStr(String measureoutputStr)
  {
    this.measureoutputStr = measureoutputStr;
  }

  public int getMeasureoutput()
  {
    return measureoutput;
  }

  public void setMeasureoutput(int measureoutput)
  {
    this.measureoutput = measureoutput;
  }

  public String getOutcomeStr()
  {
    return outcomeStr;
  }

  public void setOutcomeStr(String outcomeStr)
  {
    this.outcomeStr = outcomeStr;
  }

  public int getOutcome()
  {
    return outcome;
  }

  public void setOutcome(int outcome)
  {
    this.outcome = outcome;
  }

  public String getMeasureoutcomeStr()
  {
    return measureoutcomeStr;
  }

  public void setMeasureoutcomeStr(String measureoutcomeStr)
  {
    this.measureoutcomeStr = measureoutcomeStr;
  }

  public int getMeasureoutcome()
  {
    return measureoutcome;
  }

  public void setMeasureoutcome(int measureoutcome)
  {
    this.measureoutcome = measureoutcome;
  }

  public String getContinuationStr()
  {
    return continuationStr;
  }

  public void setContinuationStr(String continuationStr)
  {
    this.continuationStr = continuationStr;
  }

  public int getContinuation()
  {
    return continuation;
  }

  public void setContinuation(int continuation)
  {
    this.continuation = continuation;
  }

  public String getSharingStr()
  {
    return sharingStr;
  }

  public void setSharingStr(String sharingStr)
  {
    this.sharingStr = sharingStr;
  }

  public int getSharing()
  {
    return sharing;
  }

  public void setSharing(int sharing)
  {
    this.sharing = sharing;
  }

  public String getBudgetStr()
  {
    return budgetStr;
  }

  public void setBudgetStr(String budgetStr)
  {
    this.budgetStr = budgetStr;
  }

  public int getBudget()
  {
    return budget;
  }

  public void setBudget(int budget)
  {
    this.budget = budget;
  }

  public String getOtherfundStr()
  {
    return otherfundStr;
  }

  public void setOtherfundStr(String otherfundStr)
  {
    this.otherfundStr = otherfundStr;
  }

  public int getOtherfund()
  {
    return otherfund;
  }

  public void setOtherfund(int otherfund)
  {
    this.otherfund = otherfund;
  }

  public String getPublicityStr()
  {
    return publicityStr;
  }

  public void setPublicityStr(String publicityStr)
  {
    this.publicityStr = publicityStr;
  }

  public int getPublicity()
  {
    return publicity;
  }

  public void setPublicity(int publicity)
  {
    this.publicity = publicity;
  }

  public String getEducationStr()
  {
    return educationStr;
  }

  public void setEducationStr(String educationStr)
  {
    this.educationStr = educationStr;
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

    public void setGrantassign(long grantassign) {
        this.grantassign = grantassign;
    }

    public long getGrantassign() {
        return grantassign;
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

    public void setProblemStr(String problemStr) {
        this.problemStr = problemStr;
    }

    public String getProblemStr() {
        return problemStr;
    }

    public void setProblem(int problem) {
        this.problem = problem;
    }

    public int getProblem() {
        return problem;
    }

    public void setRecordsStr(String recordsStr) {
        this.recordsStr = recordsStr;
    }

    public String getRecordsStr() {
        return recordsStr;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public int getRecords() {
        return records;
    }

    public void setRecordprogramStr(String recordprogramStr) {
        this.recordprogramStr = recordprogramStr;
    }

    public String getRecordprogramStr() {
        return recordprogramStr;
    }

    public void setRecordprogram(int recordprogram) {
        this.recordprogram = recordprogram;
    }

    public int getRecordprogram() {
        return recordprogram;
    }

    public void setProjcategoryStr(String projcategoryStr) {
        this.projcategoryStr = projcategoryStr;
    }

    public String getProjcategoryStr() {
        return projcategoryStr;
    }

    public void setProjcategory(int projcategory) {
        this.projcategory = projcategory;
    }

    public int getProjcategory() {
        return projcategory;
    }

    public void setGovtsupportStr(String govtsupportStr) {
        this.govtsupportStr = govtsupportStr;
    }

    public String getGovtsupportStr() {
        return govtsupportStr;
    }

    public void setGovtsupport(int govtsupport) {
        this.govtsupport = govtsupport;
    }

    public int getGovtsupport() {
        return govtsupport;
    }

    public void setReasonablecostStr(String reasonablecostStr) {
        this.reasonablecostStr = reasonablecostStr;
    }

    public String getReasonablecostStr() {
        return reasonablecostStr;
    }

    public void setReasonablecost(int reasonablecost) {
        this.reasonablecost = reasonablecost;
    }

    public int getReasonablecost() {
        return reasonablecost;
    }

    public void setPanelgrantId(long panelgrantId) {
        this.panelgrantId = panelgrantId;
    }

    public long getPanelgrantId() {
        return panelgrantId;
    }

    public void setPanelreviewerId(long panelreviewerId) {
        this.panelreviewerId = panelreviewerId;
    }

    public long getPanelreviewerId() {
        return panelreviewerId;
    }

    public void setRevcomments(DescriptionBean[] revcomments) {
        this.revcomments = revcomments;
    }

    public DescriptionBean[] getRevcomments() {
        return revcomments;
    }

    public void setProblemcomment(String problemcomment) {
        this.problemcomment = problemcomment;
    }

    public String getProblemcomment() {
        return problemcomment;
    }

    public void setResultcomment(String resultcomment) {
        this.resultcomment = resultcomment;
    }

    public String getResultcomment() {
        return resultcomment;
    }

    public void setWorkcomment(String workcomment) {
        this.workcomment = workcomment;
    }

    public String getWorkcomment() {
        return workcomment;
    }

    public void setSupportcomment(String supportcomment) {
        this.supportcomment = supportcomment;
    }

    public String getSupportcomment() {
        return supportcomment;
    }

    public void setBudgetcomment(String budgetcomment) {
        this.budgetcomment = budgetcomment;
    }

    public String getBudgetcomment() {
        return budgetcomment;
    }

    public void setRecommendamtStr(String recommendamtStr) {
        this.recommendamtStr = recommendamtStr;
    }

    public String getRecommendamtStr() {
        return recommendamtStr;
    }


  public void setName(String name)
  {
    this.name = name;
  }


  public String getName()
  {
    return name;
  }

    public void setFycode(int fycode) {
        this.fycode = fycode;
    }

    public int getFycode() {
        return fycode;
    }

    public void setConflictinterest(boolean conflictinterest) {
        this.conflictinterest = conflictinterest;
    }

    public boolean isConflictinterest() {
        return conflictinterest;
    }

    public void setBonuspts(int bonuspts) {
        this.bonuspts = bonuspts;
    }

    public int getBonuspts() {
        return bonuspts;
    }

    public void setTotamtreq(int totamtreq) {
        this.totamtreq = totamtreq;
    }

    public int getTotamtreq() {
        return totamtreq;
    }

    public void setAvgscore(int avgscore) {
        this.avgscore = avgscore;
    }

    public int getAvgscore() {
        return avgscore;
    }

    public void setImproveserv(int improveserv) {
        this.improveserv = improveserv;
    }

    public int getImproveserv() {
        return improveserv;
    }

    public void setImproveservStr(String improveservStr) {
        this.improveservStr = improveservStr;
    }

    public String getImproveservStr() {
        return improveservStr;
    }

    public void setExpenditures(int expenditures) {
        this.expenditures = expenditures;
    }

    public int getExpenditures() {
        return expenditures;
    }

    public void setExpendituresStr(String expendituresStr) {
        this.expendituresStr = expendituresStr;
    }

    public String getExpendituresStr() {
        return expendituresStr;
    }

    public void setProblemDelibStr(String problemDelibStr) {
        this.problemDelibStr = problemDelibStr;
    }

    public String getProblemDelibStr() {
        return problemDelibStr;
    }

    public void setProblemDelib(int problemDelib) {
        this.problemDelib = problemDelib;
    }

    public int getProblemDelib() {
        return problemDelib;
    }

    public void setRecordsDelibStr(String recordsDelibStr) {
        this.recordsDelibStr = recordsDelibStr;
    }

    public String getRecordsDelibStr() {
        return recordsDelibStr;
    }

    public void setRecordsDelib(int recordsDelib) {
        this.recordsDelib = recordsDelib;
    }

    public int getRecordsDelib() {
        return recordsDelib;
    }

    public void setRecordprogramDelibStr(String recordprogramDelibStr) {
        this.recordprogramDelibStr = recordprogramDelibStr;
    }

    public String getRecordprogramDelibStr() {
        return recordprogramDelibStr;
    }

    public void setRecordprogramDelib(int recordprogramDelib) {
        this.recordprogramDelib = recordprogramDelib;
    }

    public int getRecordprogramDelib() {
        return recordprogramDelib;
    }

    public void setProjcategoryDelibStr(String projcategoryDelibStr) {
        this.projcategoryDelibStr = projcategoryDelibStr;
    }

    public String getProjcategoryDelibStr() {
        return projcategoryDelibStr;
    }

    public void setProjcategoryDelib(int projcategoryDelib) {
        this.projcategoryDelib = projcategoryDelib;
    }

    public int getProjcategoryDelib() {
        return projcategoryDelib;
    }

    public void setGovtsupportDelibStr(String govtsupportDelibStr) {
        this.govtsupportDelibStr = govtsupportDelibStr;
    }

    public String getGovtsupportDelibStr() {
        return govtsupportDelibStr;
    }

    public void setGovtsupportDelib(int govtsupportDelib) {
        this.govtsupportDelib = govtsupportDelib;
    }

    public int getGovtsupportDelib() {
        return govtsupportDelib;
    }

    public void setImproveservDelib(int improveservDelib) {
        this.improveservDelib = improveservDelib;
    }

    public int getImproveservDelib() {
        return improveservDelib;
    }

    public void setImproveservDelibStr(String improveservDelibStr) {
        this.improveservDelibStr = improveservDelibStr;
    }

    public String getImproveservDelibStr() {
        return improveservDelibStr;
    }

    public void setExpendituresDelib(int expendituresDelib) {
        this.expendituresDelib = expendituresDelib;
    }

    public int getExpendituresDelib() {
        return expendituresDelib;
    }

    public void setExpendituresDelibStr(String expendituresDelibStr) {
        this.expendituresDelibStr = expendituresDelibStr;
    }

    public String getExpendituresDelibStr() {
        return expendituresDelibStr;
    }

    public void setOtherfundDelib(int otherfundDelib) {
        this.otherfundDelib = otherfundDelib;
    }

    public int getOtherfundDelib() {
        return otherfundDelib;
    }

    public void setOtherfundDelibStr(String otherfundDelibStr) {
        this.otherfundDelibStr = otherfundDelibStr;
    }

    public String getOtherfundDelibStr() {
        return otherfundDelibStr;
    }

    public void setTimetableDelib(int timetableDelib) {
        this.timetableDelib = timetableDelib;
    }

    public int getTimetableDelib() {
        return timetableDelib;
    }

    public void setTimetableDelibStr(String timetableDelibStr) {
        this.timetableDelibStr = timetableDelibStr;
    }

    public String getTimetableDelibStr() {
        return timetableDelibStr;
    }

    public void setOutcomeDelib(int outcomeDelib) {
        this.outcomeDelib = outcomeDelib;
    }

    public int getOutcomeDelib() {
        return outcomeDelib;
    }

    public void setOutcomeDelibStr(String outcomeDelibStr) {
        this.outcomeDelibStr = outcomeDelibStr;
    }

    public String getOutcomeDelibStr() {
        return outcomeDelibStr;
    }

    public void setStaffsupportDelibStr(String staffsupportDelibStr) {
        this.staffsupportDelibStr = staffsupportDelibStr;
    }

    public String getStaffsupportDelibStr() {
        return staffsupportDelibStr;
    }

    public void setStaffsupportDelib(int staffsupportDelib) {
        this.staffsupportDelib = staffsupportDelib;
    }

    public int getStaffsupportDelib() {
        return staffsupportDelib;
    }

    public void setLongrangeDelibStr(String longrangeDelibStr) {
        this.longrangeDelibStr = longrangeDelibStr;
    }

    public String getLongrangeDelibStr() {
        return longrangeDelibStr;
    }

    public void setLongrangeDelib(int longrangeDelib) {
        this.longrangeDelib = longrangeDelib;
    }

    public int getLongrangeDelib() {
        return longrangeDelib;
    }

    public void setFinalScoreSum(int finalScoreSum) {
        this.finalScoreSum = finalScoreSum;
    }

    public int getFinalScoreSum() {
        return finalScoreSum;
    }

    public void setFinalScoreSumStr(String finalScoreSumStr) {
        this.finalScoreSumStr = finalScoreSumStr;
    }

    public String getFinalScoreSumStr() {
        return finalScoreSumStr;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setSubmit1(String submit1) {
        this.submit1 = submit1;
    }

    public String getSubmit1() {
        return submit1;
    }

    public void setSubmit2(String submit2) {
        this.submit2 = submit2;
    }

    public String getSubmit2() {
        return submit2;
    }

    public void setSubmit3(String submit3) {
        this.submit3 = submit3;
    }

    public String getSubmit3() {
        return submit3;
    }

    public void setSubmit4(String submit4) {
        this.submit4 = submit4;
    }

    public String getSubmit4() {
        return submit4;
    }

    public void setSubmit5(String submit5) {
        this.submit5 = submit5;
    }

    public String getSubmit5() {
        return submit5;
    }

    public void setBonusScore(int bonusScore) {
        this.bonusScore = bonusScore;
    }

    public int getBonusScore() {
        return bonusScore;
    }
}
