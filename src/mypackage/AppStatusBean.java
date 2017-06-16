/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  AppStatusBean.java
 * Creation/Modification History  :
 *
 * SH   2/1/07      Created
 *
 * Description
 * This class will store/retreive all information regarding the status of the grant,
 * whether it was submitted, approved, and which portions of the application have
 * been completed and reviewed.  It is used to determine access to each portion of the
 * application, ie. if the coversheetyn is y that means that the coversheet has been
 * reviewed by admin and cannot be updated by the applicant anymore.
 *****************************************************************************/
package mypackage;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


public class AppStatusBean extends ActionForm
{
  public Date dueDate;
  public Date interimdueDate;
  public Date finaldueDate;
  public Date availableDate;
  public Date systemDueDate;
  public int fycode;
  public int fccode;
  public boolean pendingReview;
  public boolean pendingFinalRev;
  public boolean lockapp;
  public boolean awaitingappr;
  public boolean dateAcceptable;
  public boolean finaldateAcceptable;
  public boolean amenddateAcceptable;
  public String applicationType;
  public long grantid;
  public String grantprogram;
  public String contractnum;
  public boolean amtappryn;
  public boolean expappryn;
  public boolean finalsignoffyn;
  public boolean finalnarrativeyn;
  public boolean expsubyn;
  public boolean projdescyn;
  public boolean coversheetyn;
  public boolean amtreqyn;
  public boolean instauthyn;
  public boolean fs20yn;
  public boolean fs10fyn;
  public boolean fs10ayn;
  public boolean fs10aComp;
  public boolean initialappr;
  public boolean finalappr;
  public boolean finalappryear2;
  public boolean finalappryear3;
  public boolean appDenied;
  public boolean appDeclined;
  public boolean initialsubmitted;
  public boolean finalsubmitted;
  public boolean finalyr2submitted;
  public boolean finalyr3submitted;
  public boolean readyYear3;
  public boolean amendsubmitted;
  public boolean amendApprYr1;
  public boolean amendApprYr2;
  public boolean amendApprYr3;
  public boolean interimsubmitted;
  public boolean allowSubmitFinal;
  public boolean allowSubmitInitial;
  public boolean allowSubmitFinal2;
  public boolean allowSubmitFinal3;
  public boolean interimappr;
  public boolean interimrptyn;
  public boolean archcooperative;
  public boolean archshared;
  public boolean educationapp;
  public boolean statisticsyn;
  public boolean payeeyn;
  public boolean interimauthyn;
  public boolean appendixyn;
  public boolean finalbudgetComp;
  public boolean finalnarrativeComp;
  public boolean signoffComp;
  public boolean coversheetComp;
  public boolean projdescComp;
  public boolean budgetComp;
  public boolean authComp;
  public boolean fs20Comp;
  public boolean fs10fComp; 
  public boolean showscorecomm;//show reviewer score/comm to apcnt
  public boolean vqimyn;//lgrmif vendor quote/imagingmicrofilming for admin
  public int amountDeclined;
  public String amountDeclinedStr;
  public String dateDeclined;
  public long declineId;
  public boolean attachcomp;
  public boolean shpocomp;
  public boolean seafcomp;
  public boolean payeecomp;
  public boolean photocomp;
  public boolean reviewDone;
  public boolean reviewStarted;
  public String systemName;
  public long systemInstId;
  public boolean dasnysubmit;//is cn app submitted to dasny?
  public boolean dasnyapproved;//is cn app approved by dasny?
  public boolean bondcomplete;//is cn app dasny bonding completed?
  public boolean assurancesyn;//is cn assurances complet?
  public boolean additfundingyn;//is cn additional funding complete?
  public boolean planningDemo;
  public boolean implementDemo;
  public int mwbeParticipation;
  public boolean mwbesubmit;//is mwbe submitted for cn?
  public long ldacAppropAmt;//for stateaid - the final approp from ldac system
  public boolean shpoExemptionFlag;//for cn; is shpo exemption received?
  public boolean groundDisturb;//for cn; 

  public AppStatusBean()
  {
  }
   
  public Date getDueDate()
  {
    return dueDate;
  }

  public void setDueDate(Date dueDate)
  {
    this.dueDate = dueDate;
  }

  public int getFycode()
  {
    return fycode;
  }

  public void setFycode(int fycode)
  {
    this.fycode = fycode;
  }

  public int getFccode()
  {
    return fccode;
  }

  public void setFccode(int fccode)
  {
    this.fccode = fccode;
  }

  public boolean isDateAcceptable()
  {
    return dateAcceptable;
  }

  public void setDateAcceptable(boolean dateAcceptable)
  {
    this.dateAcceptable = dateAcceptable;
  }
   
  public String getApplicationType()
  {
    return applicationType;
  }

  public void setApplicationType(String applicationType)
  {
    this.applicationType = applicationType;
  }

  public long getGrantid()
  {
    return grantid;
  }

  public void setGrantid(long grantid)
  {
    this.grantid = grantid;
  }

  public String getGrantprogram()
  {
    return grantprogram;
  }

  public void setGrantprogram(String grantprogram)
  {
    this.grantprogram = grantprogram;
  }

  public String getContractnum()
  {
    return contractnum;
  }

  public void setContractnum(String contractnum)
  {
    this.contractnum = contractnum;
  }

  public boolean isInitialappr()
  {
    return initialappr;
  }

  public void setInitialappr(boolean initialappr)
  {
    this.initialappr = initialappr;
  }

  public boolean isFinalappr()
  {
    return finalappr;
  }

  public void setFinalappr(boolean finalappr)
  {
    this.finalappr = finalappr;
  }

  public boolean isAppDenied()
  {
    return appDenied;
  }

  public void setAppDenied(boolean appDenied)
  {
    this.appDenied = appDenied;
  }

  public boolean isInitialsubmitted()
  {
    return initialsubmitted;
  }

  public void setInitialsubmitted(boolean initialsubmitted)
  {
    this.initialsubmitted = initialsubmitted;
  }

  public boolean isFinalsubmitted()
  {
    return finalsubmitted;
  }

  public void setFinalsubmitted(boolean finalsubmitted)
  {
    this.finalsubmitted = finalsubmitted;
  }

  public boolean isAllowSubmitFinal()
  {
    return allowSubmitFinal;
  }

  public void setAllowSubmitFinal(boolean allowSubmitFinal)
  {
    this.allowSubmitFinal = allowSubmitFinal;
  }

  public boolean isAllowSubmitInitial()
  {
    return allowSubmitInitial;
  }

  public void setAllowSubmitInitial(boolean allowSubmitInitial)
  {
    this.allowSubmitInitial = allowSubmitInitial;
  }
 
    public void setArchcooperative(boolean archcooperative) {
        this.archcooperative = archcooperative;
    }

    public boolean isArchcooperative() {
        return archcooperative;
    }
   
    public void setEducationapp(boolean educationapp) {
        this.educationapp = educationapp;
    }

    public boolean isEducationapp() {
        return educationapp;
    }

    public void setStatisticsyn(boolean statisticsyn) {
        this.statisticsyn = statisticsyn;
    }

    public boolean isStatisticsyn() {
        return statisticsyn;
    }

    public void setInterimrptyn(boolean interimrptyn) {
        this.interimrptyn = interimrptyn;
    }

    public boolean isInterimrptyn() {
        return interimrptyn;
    }

    public void setPayeeyn(boolean payeeyn) {
        this.payeeyn = payeeyn;
    }

    public boolean isPayeeyn() {
        return payeeyn;
    }

    public void setInterimauthyn(boolean interimauthyn) {
        this.interimauthyn = interimauthyn;
    }

    public boolean isInterimauthyn() {
        return interimauthyn;
    }

    public void setAppendixyn(boolean appendixyn) {
        this.appendixyn = appendixyn;
    }

    public boolean isAppendixyn() {
        return appendixyn;
    }

    public void setFinalbudgetComp(boolean finalbudgetComp) {
        this.finalbudgetComp = finalbudgetComp;
    }

    public boolean isFinalbudgetComp() {
        return finalbudgetComp;
    }

    public void setFinalnarrativeComp(boolean finalnarrativeComp) {
        this.finalnarrativeComp = finalnarrativeComp;
    }

    public boolean isFinalnarrativeComp() {
        return finalnarrativeComp;
    }

    public void setFinalsignoffyn(boolean finalsignoffyn) {
        this.finalsignoffyn = finalsignoffyn;
    }

    public boolean isFinalsignoffyn() {
        return finalsignoffyn;
    }

    public void setSignoffComp(boolean signoffComp) {
        this.signoffComp = signoffComp;
    }

    public boolean isSignoffComp() {
        return signoffComp;
    }

    public void setFinalnarrativeyn(boolean finalnarrativeyn) {
        this.finalnarrativeyn = finalnarrativeyn;
    }

    public boolean isFinalnarrativeyn() {
        return finalnarrativeyn;
    }

    public void setExpsubyn(boolean expsubyn) {
        this.expsubyn = expsubyn;
    }

    public boolean isExpsubyn() {
        return expsubyn;
    }

    public void setCoversheetComp(boolean coversheetComp) {
        this.coversheetComp = coversheetComp;
    }

    public boolean isCoversheetComp() {
        return coversheetComp;
    }

    public void setProjdescComp(boolean projdescComp) {
        this.projdescComp = projdescComp;
    }

    public boolean isProjdescComp() {
        return projdescComp;
    }

    public void setProjdescyn(boolean projdescyn) {
        this.projdescyn = projdescyn;
    }

    public boolean isProjdescyn() {
        return projdescyn;
    }

    public void setCoversheetyn(boolean coversheetyn) {
        this.coversheetyn = coversheetyn;
    }

    public boolean isCoversheetyn() {
        return coversheetyn;
    }

    public void setAmtappryn(boolean amtappryn) {
        this.amtappryn = amtappryn;
    }

    public boolean isAmtappryn() {
        return amtappryn;
    }

    public void setExpappryn(boolean expappryn) {
        this.expappryn = expappryn;
    }

    public boolean isExpappryn() {
        return expappryn;
    }

    public void setBudgetComp(boolean budgetComp) {
        this.budgetComp = budgetComp;
    }

    public boolean isBudgetComp() {
        return budgetComp;
    }

    public void setAuthComp(boolean authComp) {
        this.authComp = authComp;
    }

    public boolean isAuthComp() {
        return authComp;
    }

    public void setAmtreqyn(boolean amtreqyn) {
        this.amtreqyn = amtreqyn;
    }

    public boolean isAmtreqyn() {
        return amtreqyn;
    }

    public void setInstauthyn(boolean instauthyn) {
        this.instauthyn = instauthyn;
    }

    public boolean isInstauthyn() {
        return instauthyn;
    }

    public void setFs20yn(boolean fs20yn) {
        this.fs20yn = fs20yn;
    }

    public boolean isFs20yn() {
        return fs20yn;
    }

    public void setFs20Comp(boolean fs20Comp) {
        this.fs20Comp = fs20Comp;
    }

    public boolean isFs20Comp() {
        return fs20Comp;
    }

    public void setFs10fyn(boolean fs10fyn) {
        this.fs10fyn = fs10fyn;
    }

    public boolean isFs10fyn() {
        return fs10fyn;
    }

    public void setFs10ayn(boolean fs10ayn) {
        this.fs10ayn = fs10ayn;
    }

    public boolean isFs10ayn() {
        return fs10ayn;
    }

    public void setFs10fComp(boolean fs10fComp) {
        this.fs10fComp = fs10fComp;
    }

    public boolean isFs10fComp() {
        return fs10fComp;
    }

    public void setLockapp(boolean lockapp) {
        this.lockapp = lockapp;
    }

    public boolean isLockapp() {
        return lockapp;
    }

    public void setAwaitingappr(boolean awaitingappr) {
        this.awaitingappr = awaitingappr;
    }

    public boolean isAwaitingappr() {
        return awaitingappr;
    }

    public void setInterimappr(boolean interimappr) {
        this.interimappr = interimappr;
    }

    public boolean isInterimappr() {
        return interimappr;
    }

    public void setPendingFinalRev(boolean pendingFinalRev) {
        this.pendingFinalRev = pendingFinalRev;
    }

    public boolean isPendingFinalRev() {
        return pendingFinalRev;
    }

    public void setPendingReview(boolean pendingReview) {
        this.pendingReview = pendingReview;
    }

    public boolean isPendingReview() {
        return pendingReview;
    }

    public void setInterimdueDate(Date interimdueDate) {
        this.interimdueDate = interimdueDate;
    }

    public Date getInterimdueDate() {
        return interimdueDate;
    }

    public void setFinaldueDate(Date finaldueDate) {
        this.finaldueDate = finaldueDate;
    }

    public Date getFinaldueDate() {
        return finaldueDate;
    }

    public void setShowscorecomm(boolean showscorecomm) {
        this.showscorecomm = showscorecomm;
    }

    public boolean isShowscorecomm() {
        return showscorecomm;
    }

    public void setVqimyn(boolean vqimyn) {
        this.vqimyn = vqimyn;
    }

    public boolean isVqimyn() {
        return vqimyn;
    }

    public void setAvailableDate(Date availableDate) {
        this.availableDate = availableDate;
    }

    public Date getAvailableDate() {
        return availableDate;
    }

    public void setAppDeclined(boolean appDeclined) {
        this.appDeclined = appDeclined;
    }

    public boolean isAppDeclined() {
        return appDeclined;
    }

    public void setAmountDeclined(int amountDeclined) {
        this.amountDeclined = amountDeclined;
    }

    public int getAmountDeclined() {
        return amountDeclined;
    }

    public void setDateDeclined(String dateDeclined) {
        this.dateDeclined = dateDeclined;
    }

    public String getDateDeclined() {
        return dateDeclined;
    }

    public void setAmountDeclinedStr(String amountDeclinedStr) {
        this.amountDeclinedStr = amountDeclinedStr;
    }

    public String getAmountDeclinedStr() {
        return amountDeclinedStr;
    }

    public void setDeclineId(long declineId) {
        this.declineId = declineId;
    }

    public long getDeclineId() {
        return declineId;
    }
    
    
    public boolean isMissing(String value) 
    {
      return((value == null) || (value.trim().equals("")));
    }
    
    
    public ActionErrors validateDecline(ActionMapping mapping,  HttpServletRequest request) 
    {  
      ActionErrors errors = new ActionErrors();
      if (isAppDeclined()) 
      {
         if(isMissing(getAmountDeclinedStr()))
            errors.add("amtMissing", new ActionError("value.required", "Amount Declined"));
            
          if(isMissing(getDateDeclined()))
             errors.add("dateMissing", new ActionError("value.required", "Date Declined"));
      } 
                      
      return(errors);
    }

    public void setAmendsubmitted(boolean amendsubmitted) {
        this.amendsubmitted = amendsubmitted;
    }

    public boolean isAmendsubmitted() {
        return amendsubmitted;
    }

    public void setInterimsubmitted(boolean interimsubmitted) {
        this.interimsubmitted = interimsubmitted;
    }

    public boolean isInterimsubmitted() {
        return interimsubmitted;
    }

    public void setArchshared(boolean archshared) {
        this.archshared = archshared;
    }

    public boolean isArchshared() {
        return archshared;
    }

    public void setAttachcomp(boolean attachcomp) {
        this.attachcomp = attachcomp;
    }

    public boolean isAttachcomp() {
        return attachcomp;
    }

    public void setShpocomp(boolean shpocomp) {
        this.shpocomp = shpocomp;
    }

    public boolean isShpocomp() {
        return shpocomp;
    }

    public void setSeafcomp(boolean seafcomp) {
        this.seafcomp = seafcomp;
    }

    public boolean isSeafcomp() {
        return seafcomp;
    }

    public void setPayeecomp(boolean payeecomp) {
        this.payeecomp = payeecomp;
    }

    public boolean isPayeecomp() {
        return payeecomp;
    }

    public void setPhotocomp(boolean photocomp) {
        this.photocomp = photocomp;
    }

    public boolean isPhotocomp() {
        return photocomp;
    }

    public void setReviewDone(boolean reviewDone) {
        this.reviewDone = reviewDone;
    }

    public boolean isReviewDone() {
        return reviewDone;
    }

    public void setReviewStarted(boolean reviewStarted) {
        this.reviewStarted = reviewStarted;
    }

    public boolean isReviewStarted() {
        return reviewStarted;
    }

    public void setFinalappryear2(boolean finalappryear2) {
        this.finalappryear2 = finalappryear2;
    }

    public boolean isFinalappryear2() {
        return finalappryear2;
    }

    public void setAllowSubmitFinal2(boolean allowSubmitFinal2) {
        this.allowSubmitFinal2 = allowSubmitFinal2;
    }

    public boolean isAllowSubmitFinal2() {
        return allowSubmitFinal2;
    }

    public void setFinalyr2submitted(boolean finalyr2submitted) {
        this.finalyr2submitted = finalyr2submitted;
    }

    public boolean isFinalyr2submitted() {
        return finalyr2submitted;
    }

    public void setSystemDueDate(Date systemDueDate) {
        this.systemDueDate = systemDueDate;
    }

    public Date getSystemDueDate() {
        return systemDueDate;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemInstId(long systemInstId) {
        this.systemInstId = systemInstId;
    }

    public long getSystemInstId() {
        return systemInstId;
    }

    public void setFinaldateAcceptable(boolean finaldateAcceptable) {
        this.finaldateAcceptable = finaldateAcceptable;
    }

    public boolean isFinaldateAcceptable() {
        return finaldateAcceptable;
    }

    public void setDasnysubmit(boolean dasnysubmit) {
        this.dasnysubmit = dasnysubmit;
    }

    public boolean isDasnysubmit() {
        return dasnysubmit;
    }

    public void setDasnyapproved(boolean dasnyapproved) {
        this.dasnyapproved = dasnyapproved;
    }

    public boolean isDasnyapproved() {
        return dasnyapproved;
    }

    public void setBondcomplete(boolean bondcomplete) {
        this.bondcomplete = bondcomplete;
    }

    public boolean isBondcomplete() {
        return bondcomplete;
    }

    public void setAssurancesyn(boolean assurancesyn) {
        this.assurancesyn = assurancesyn;
    }

    public boolean isAssurancesyn() {
        return assurancesyn;
    }

    public void setAdditfundingyn(boolean additfundingyn) {
        this.additfundingyn = additfundingyn;
    }

    public boolean isAdditfundingyn() {
        return additfundingyn;
    }

    public void setFs10aComp(boolean fs10aComp) {
        this.fs10aComp = fs10aComp;
    }

    public boolean isFs10aComp() {
        return fs10aComp;
    }

    public void setAmenddateAcceptable(boolean amenddateAcceptable) {
        this.amenddateAcceptable = amenddateAcceptable;
    }

    public boolean isAmenddateAcceptable() {
        return amenddateAcceptable;
    }

    public void setFinalyr3submitted(boolean finalyr3submitted) {
        this.finalyr3submitted = finalyr3submitted;
    }

    public boolean isFinalyr3submitted() {
        return finalyr3submitted;
    }

    public void setFinalappryear3(boolean finalappryear3) {
        this.finalappryear3 = finalappryear3;
    }

    public boolean isFinalappryear3() {
        return finalappryear3;
    }

    public void setPlanningDemo(boolean planningDemo) {
        this.planningDemo = planningDemo;
    }

    public boolean isPlanningDemo() {
        return planningDemo;
    }

    public void setImplementDemo(boolean implementDemo) {
        this.implementDemo = implementDemo;
    }

    public boolean isImplementDemo() {
        return implementDemo;
    }

    public void setMwbeParticipation(int mwbeParticipation) {
        this.mwbeParticipation = mwbeParticipation;
    }

    public int getMwbeParticipation() {
        return mwbeParticipation;
    }

    public void setLdacAppropAmt(long ldacAppropAmt) {
        this.ldacAppropAmt = ldacAppropAmt;
    }

    public long getLdacAppropAmt() {
        return ldacAppropAmt;
    }

    public void setShpoExemptionFlag(boolean shpoExemptionFlag) {
        this.shpoExemptionFlag = shpoExemptionFlag;
    }

    public boolean isShpoExemptionFlag() {
        return shpoExemptionFlag;
    }

    public void setGroundDisturb(boolean groundDisturb) {
        this.groundDisturb = groundDisturb;
    }

    public boolean isGroundDisturb() {
        return groundDisturb;
    }

    public void setMwbesubmit(boolean mwbesubmit) {
        this.mwbesubmit = mwbesubmit;
    }

    public boolean isMwbesubmit() {
        return mwbesubmit;
    }

    public void setReadyYear3(boolean readyYear3) {
        this.readyYear3 = readyYear3;
    }

    public boolean isReadyYear3() {
        return readyYear3;
    }

    public void setAllowSubmitFinal3(boolean allowSubmitFinal3) {
        this.allowSubmitFinal3 = allowSubmitFinal3;
    }

    public boolean isAllowSubmitFinal3() {
        return allowSubmitFinal3;
    }

	public boolean isAmendApprYr1() {
		return amendApprYr1;
	}

	public void setAmendApprYr1(boolean amendApprYr1) {
		this.amendApprYr1 = amendApprYr1;
	}

	public boolean isAmendApprYr2() {
		return amendApprYr2;
	}

	public void setAmendApprYr2(boolean amendApprYr2) {
		this.amendApprYr2 = amendApprYr2;
	}

	public boolean isAmendApprYr3() {
		return amendApprYr3;
	}

	public void setAmendApprYr3(boolean amendApprYr3) {
		this.amendApprYr3 = amendApprYr3;
	}
    
}
