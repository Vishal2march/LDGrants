/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  UserBean.java
 * Creation/Modification History  :
 *
 * SH   2/1/07      Created
 *
 * Description
 * This class will store information about the user, such as their role (either admin
 * or applicant) and the grant programs they can access.  User info is obtained from
 * the LoginChecker.java.  The userbean is saved to the session.  
 * 
 * $Id: UserBean.java 4689 2009-06-11 14:30:57Z shusak $
 *****************************************************************************/
package mypackage;
import sedems.AuthenticateBean;

public class UserBean 
{
  public String userid;
  public String email;
  public long instid;
  public String admincoor;
  public String adminstat;
  public String admindisc;
  public String adminal;
  public String adminfl;
  public String admconstruction;
  public String prgsa;
  public String prgco;
  public String prgdi;
  public String prgal;
  public String prgfl;
  public String prglg;//uses lg attribute
  public String prgNycStateaid;// new 10/7/14 reuses nysds coord attribute; for cjh/nyhs only
  public String prgconstruction;//applicant
  public boolean reviewercoor;
  public boolean reviewerdisc;
  public boolean reviewerfl;
  public boolean revieweral;
  public boolean reviewconstruction;
  public long revid;
  public String fname;
  public String lname;
  public boolean reviewerfound;//for REVIEWERS, if ldap login username found in ldgrants reviewers table
  public boolean typeadmin;
  public boolean typeapcnt;
  public boolean typerev;
  public boolean readaccess;
  public boolean lgadmin;
  public boolean lgreviewer;  
  public AuthenticateBean authBean;
  public AuthenticateBean archivesAuthBean;
  public AuthenticateBean constructionAuthBean;
  public boolean israo;
  public boolean isgovtemp;
  public boolean dasnyReviewer;
  
  public UserBean()
  {
  }

  public String getUserid()
  {
    return userid;
  }

  public void setUserid(String userid)
  {
    this.userid = userid;
  }

  public long getInstid()
  {
    return instid;
  }

  public void setInstid(long instid)
  {
    this.instid = instid;
  }



  public String getAdmincoor()
  {
    return admincoor;
  }

  public void setAdmincoor(String admincoor)
  {
    this.admincoor = admincoor;
  }

  public String getAdminstat()
  {
    return adminstat;
  }

  public void setAdminstat(String adminstat)
  {
    this.adminstat = adminstat;
  }

  public String getAdmindisc()
  {
    return admindisc;
  }

  public void setAdmindisc(String admindisc)
  {
    this.admindisc = admindisc;
  }

  public String getPrgsa()
  {
    return prgsa;
  }

  public void setPrgsa(String prgsa)
  {
    this.prgsa = prgsa;
  }

  public String getPrgco()
  {
    return prgco;
  }

  public void setPrgco(String prgco)
  {
    this.prgco = prgco;
  }

  public String getPrgdi()
  {
    return prgdi;
  }

  public void setPrgdi(String prgdi)
  {
    this.prgdi = prgdi;
  }

  public void checkAdminRoles(String admin1, String admin2, String reviewer)
  {
  
    if(admin1 != null )
    { 
      
     // Determine the user's admin levels 
      if(admin1.indexOf("stat") != -1)
      {
          //USER IS AN APPROVER FOR STAT  
          this.setTypeadmin(true);
          this.setAdminstat("approve");
      }
       if(admin1.indexOf("coor") != -1)
      {
          //USER IS AN APPROVER FOR COOR
          this.setTypeadmin(true);
          this.setAdmincoor("approve");
      }
       if(admin1.indexOf("disc") != -1)
      {
          //USER IS AN APPROVER FOR DISC
          this.setTypeadmin(true);
          this.setAdmindisc("approve");
      }
      if(admin1.indexOf("fl") != -1)
      {
        //user is approver for family literacy
        this.setTypeadmin(true);
        this.setAdminfl("approve");
      }
      if(admin1.indexOf("al") != -1)
      {
        //user is approver for adult literacy
        this.setTypeadmin(true);
        this.setAdminal("approve");
      }        
      if(admin1.indexOf("cn") != -1)
      {
          //USER IS AN LD ADMIN FOR Construction grants 
          this.setTypeadmin(true);
          this.setAdmconstruction("approve");
      }     
    }
    
    //modified 10/31/11 for dasny construction  
    if(admin2 != null)
    {       
      if(admin2.indexOf("stat") != -1)
      {
          //USER IS A SED READER FOR STAT 
          this.setTypeadmin(true);
          this.setAdminstat("review");
      }
       if(admin2.indexOf("coor") != -1)
      {
          //USER IS A SED REVIEWER FOR COOR
          this.setTypeadmin(true);
          this.setAdmincoor("review");
      }
       if(admin2.indexOf("disc") != -1)
      {
          //USER IS A SED REVIEWER FOR DISC
          this.setTypeadmin(true);
          this.setAdmindisc("review");
      }
      if(admin2.indexOf("fl") != -1)
      {
        //user sed reviewer for family lit
        this.setTypeadmin(true);
        this.setAdminfl("review");
      }
      if(admin2.indexOf("al") != -1)
      {
        //user sed reviewer for adult lit
        this.setTypeadmin(true);
        this.setAdminal("review");
      }      
      if(admin2.indexOf("cn") != -1)
      {
          //USER IS A DASNY REVIEWER/ADMIN FOR Construction grants
          this.setTypeadmin(true);
          this.setDasnyReviewer(true);
      }      
    }
    
    
    if(reviewer != null)
    {     
      if(reviewer.indexOf("coor") != -1)
      {
          //USER IS AN INDEPENDENT REVIEWER FOR COOR 
           this.setTyperev(true);
           this.setReviewercoor(true);
      }
      if(reviewer.indexOf("disc") != -1)
      {
          //USER IS AN INDEPENDENT REVIEWER FOR DISC
           this.setTyperev(true);
          this.setReviewerdisc(true);
      }
      if(reviewer.indexOf("fl") != -1)
      {
          //USER IS AN INDEPENDENT REVIEWER FOR FAM LIT
           this.setTyperev(true);
          this.setReviewerfl(true);
      }
      if(reviewer.indexOf("al") != -1)
      {
          //USER IS AN INDEPENDENT REVIEWER FOR ADULT LIT
           this.setTyperev(true);
          this.setRevieweral(true);
      }      
      if(reviewer.indexOf("cn") != -1)
      {
          //USER IS A SYSTEM REVIEWER FOR CONSTRUCTION GRANTS
          this.setTyperev(true);
          this.setReviewconstruction(true);
      }      
    } 
  }
  
  public void checkLiteracyRoles(String prgfl, String prgal)
  {
    if(prgfl !=null)
    {
        //family lit
        if(prgfl.indexOf("re") != -1)
        {
           //USER CAN READ FL
            this.setTypeapcnt(true);
            this.setPrgfl("read");
        }
        else if(prgfl.indexOf("ed") != -1)
        {
          //USER CAN EDIT FL
          this.setTypeapcnt(true);
          this.setPrgfl("edit");
        } 
        else if(prgfl.indexOf("su") != -1)
        {
          //USER CAN SUBMIT FL
          this.setTypeapcnt(true);
          this.setPrgfl("submit");
        }         
    }
    
    
    if(prgal != null)
    {
          
         //adult lit
          if(prgal.indexOf("re") != -1)
          {
            //USER CAN READ
            this.setTypeapcnt(true);
            this.setPrgal("read");
          } 
          else if(prgal.indexOf("ed") != -1)
          {
            //USER CAN EDIT
            this.setTypeapcnt(true);
            this.setPrgal("edit");
          } 
          else if(prgal.indexOf("su") != -1) 
          {
            //USER CAN SUBMIT
            this.setTypeapcnt(true);
            this.setPrgal("submit");
          }                   
    }     
  }



    public void checkLgrmifRoles(String lgrev, String lgadmin, String lgsubmit, String lgedit, 
                                String lgread)
    {
      if(lgrev !=null){
          if(lgrev.indexOf("lgrmif") != -1){
             //USER is lg reviewer
              this.setTyperev(true);
              this.setLgreviewer(true);
          }
          
      }
      
      if(lgadmin !=null) {
          if(lgadmin.indexOf("lgrmif") !=-1) {
              //user is lg admin
              this.setTypeadmin(true);
              this.setLgadmin(true);
          }
      }
      
      if(lgsubmit != null){
          if(lgsubmit.indexOf("lgrmif") !=-1){
              this.setTypeapcnt(true);
              this.setPrglg("submit");
          }
      }
      else if(lgedit !=null){
          if(lgedit.indexOf("lgrmif") !=-1){
              this.setTypeapcnt(true);
              this.setPrglg("edit");
          }
      }
      else if(lgread != null){
          if(lgread.indexOf("lgrmif") !=-1){
              this.setTypeapcnt(true);
              this.setPrglg("read");
          }
      }     
    }
    
    
  public void checkApplicantRoles(String prgsa, String prgco, String prgdi, String construction, String instid)
  {    
     if(prgsa != null)
     {          
          //Statutory grant access
          if(prgsa.indexOf("re") != -1)
          {
            //USER CAN READ STAT
            this.setTypeapcnt(true);
            this.setPrgsa("read");
         } 
          else if(prgsa.indexOf("ed") != -1)
          {
            //USER CAN EDIT STAT
            this.setTypeapcnt(true);
            this.setPrgsa("edit");
          } 
          else if(prgsa.indexOf("su") != -1)
          {
            //USER CAN SUBMIT STAT
            this.setTypeapcnt(true);
            this.setPrgsa("submit");
          }       
     }
          
     if(prgco != null)
     {          
        //System.out.println("instid "+ instid);
        long myinstid=0;
        if(instid!=null  &&  !instid.equals(""))
            myinstid = Long.parseLong(instid);
        
        
          //SH 10/7/14 reusing coord attribute for cjh and nyhs applications -> check instId
         if(myinstid == 800000047346L  ||  myinstid == 800000047794L){
                // CJH OR NYHS STATE AID APP
                 if(prgco.indexOf("re") != -1) {
                   //USER CAN READ cjh/nyhs
                   this.setTypeapcnt(true);
                   this.setPrgNycStateaid("read");
                 } 
                 else if(prgco.indexOf("ed") != -1){
                   //USER CAN EDIT cjh/nyhs
                   this.setTypeapcnt(true);
                   this.setPrgNycStateaid("edit");
                 } 
                 else if(prgco.indexOf("su") != -1) {
                   //USER CAN SUBMIT nyhs/cjh
                   this.setTypeapcnt(true);
                   this.setPrgNycStateaid("submit");
                 } 
                 System.out.println("nyc "+ this.getPrgNycStateaid());
         }
         else
         {      // CP COORDINATED PROGRAM
                //Coordinated grant access
                if(prgco.indexOf("re") != -1)
                {
                  //USER CAN READ COOR
                  this.setTypeapcnt(true);
                  this.setPrgco("read");
                } 
                else if(prgco.indexOf("ed") != -1)
                {
                  //USER CAN EDIT COOR
                  this.setTypeapcnt(true);
                  this.setPrgco("edit");
                } 
                else if(prgco.indexOf("su") != -1) 
                {
                  //USER CAN SUBMIT COOR
                  this.setTypeapcnt(true);
                  this.setPrgco("submit");
                } 
          }
     }
                   
      
    if(prgdi != null)
    {          
         //Discretionary grant access
          if(prgdi.indexOf("re") != -1)
          {
            //USER CAN READ DISC
            this.setTypeapcnt(true);
            this.setPrgdi("read");
          } 
          else if(prgdi.indexOf("ed") != -1)
          {
            //USER CAN EDIT DISC
            this.setTypeapcnt(true);
            this.setPrgdi("edit");
          } 
          else if(prgdi.indexOf("su") != -1) 
          {
            //USER CAN SUBMIT DISC
            this.setTypeapcnt(true);
            this.setPrgdi("submit");
          }         
    } 
          
      if(construction != null)
      {          
           //CONSTRUCTION grant access
           if(construction.indexOf("re") != -1)
           {
             //USER CAN READ CONSTRUCTION
             this.setTypeapcnt(true);
             this.setPrgconstruction("read");
          } 
           else if(construction.indexOf("ed") != -1)
           {
             //USER CAN EDIT CONSTRUCTION
             this.setTypeapcnt(true);
             this.setPrgconstruction("edit");
           } 
           else if(construction.indexOf("su") != -1)
           {
             //USER CAN SUBMIT CONSTRUCTION
             this.setTypeapcnt(true);
             this.setPrgconstruction("submit");
           }       
      } 
  }
   

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public long getRevid()
  {
    return revid;
  }

  public void setRevid(long revid)
  {
    this.revid = revid;
  }

  public String getFname()
  {
    return fname;
  }

  public void setFname(String fname)
  {
    this.fname = fname;
  }

  public String getLname()
  {
    return lname;
  }

  public void setLname(String lname)
  {
    this.lname = lname;
  }

  public boolean isReviewerfound()
  {
    return reviewerfound;
  }

  public void setReviewerfound(boolean reviewerfound)
  {
    this.reviewerfound = reviewerfound;
  }

  public String getAdminal()
  {
    return adminal;
  }

  public void setAdminal(String adminal)
  {
    this.adminal = adminal;
  }

  public String getAdminfl()
  {
    return adminfl;
  }

  public void setAdminfl(String adminfl)
  {
    this.adminfl = adminfl;
  }

  public String getPrgal()
  {
    return prgal;
  }

  public void setPrgal(String prgal)
  {
    this.prgal = prgal;
  }

  public String getPrgfl()
  {
    return prgfl;
  }

  public void setPrgfl(String prgfl)
  {
    this.prgfl = prgfl;
  }

  public boolean isTypeadmin()
  {
    return typeadmin;
  }

  public void setTypeadmin(boolean typeadmin)
  {
    this.typeadmin = typeadmin;
  }

  public boolean isTypeapcnt()
  {
    return typeapcnt;
  }

  public void setTypeapcnt(boolean typeapcnt)
  {
    this.typeapcnt = typeapcnt;
  }

  public boolean isTyperev()
  {
    return typerev;
  }

  public void setTyperev(boolean typerev)
  {
    this.typerev = typerev;
  }

  public AuthenticateBean getAuthBean()
  {
    return authBean;
  }

  public void setAuthBean(AuthenticateBean authBean)
  {
    this.authBean = authBean;
  }


  public void setReadaccess(boolean readaccess)
  {
    this.readaccess = readaccess;
  }


  public boolean isReadaccess()
  {
    return readaccess;
  }

    public void setPrglg(String prglg) {
        this.prglg = prglg;
    }

    public String getPrglg() {
        return prglg;
    }

    public void setLgadmin(boolean lgadmin) {
        this.lgadmin = lgadmin;
    }

    public boolean isLgadmin() {
        return lgadmin;
    }

    public void setLgreviewer(boolean lgreviewer) {
        this.lgreviewer = lgreviewer;
    }

    public boolean isLgreviewer() {
        return lgreviewer;
    }

    public void setReviewercoor(boolean reviewercoor) {
        this.reviewercoor = reviewercoor;
    }

    public boolean isReviewercoor() {
        return reviewercoor;
    }

    public void setReviewerdisc(boolean reviewerdisc) {
        this.reviewerdisc = reviewerdisc;
    }

    public boolean isReviewerdisc() {
        return reviewerdisc;
    }

    public void setReviewerfl(boolean reviewerfl) {
        this.reviewerfl = reviewerfl;
    }

    public boolean isReviewerfl() {
        return reviewerfl;
    }

    public void setRevieweral(boolean revieweral) {
        this.revieweral = revieweral;
    }

    public boolean isRevieweral() {
        return revieweral;
    }

    public void setIsrao(boolean israo) {
        this.israo = israo;
    }

    public boolean isIsrao() {
        return israo;
    }

    public void setIsgovtemp(boolean isgovtemp) {
        this.isgovtemp = isgovtemp;
    }

    public boolean isIsgovtemp() {
        return isgovtemp;
    }

    public void setArchivesAuthBean(AuthenticateBean archivesAuthBean) {
        this.archivesAuthBean = archivesAuthBean;
    }

    public AuthenticateBean getArchivesAuthBean() {
        return archivesAuthBean;
    }

   public void setAdmconstruction(String admconstruction) {
        this.admconstruction = admconstruction;
    }

    public String getAdmconstruction() {
        return admconstruction;
    }

    public void setPrgconstruction(String prgconstruction) {
        this.prgconstruction = prgconstruction;
    }

    public String getPrgconstruction() {
        return prgconstruction;
    }

    public void setReviewconstruction(boolean reviewconstruction) {
        this.reviewconstruction = reviewconstruction;
    }

    public boolean isReviewconstruction() {
        return reviewconstruction;
    }

    public void setDasnyReviewer(boolean dasnyReviewer) {
        this.dasnyReviewer = dasnyReviewer;
    }

    public boolean isDasnyReviewer() {
        return dasnyReviewer;
    }

    public void setConstructionAuthBean(AuthenticateBean constructionAuthBean) {
        this.constructionAuthBean = constructionAuthBean;
    }

    public AuthenticateBean getConstructionAuthBean() {
        return constructionAuthBean;
    }

    public void setPrgNycStateaid(String prgNycStateaid) {
        this.prgNycStateaid = prgNycStateaid;
    }

    public String getPrgNycStateaid() {
        return prgNycStateaid;
    }
}
///////////////////////////////////////////////////////////////////////