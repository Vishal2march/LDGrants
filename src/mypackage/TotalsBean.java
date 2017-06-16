/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  TotalsBean.java
 * Creation/Modification History  :
 *
 * SH   8/9/07      Created
 *
 * Description
 * This bean will store all calculated totals for expense categories from db.
 *****************************************************************************/
package mypackage;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class TotalsBean 
{
  NumberFormat numF = new DecimalFormat("###,###,###");
  NumberFormat numTwoDigit = new DecimalFormat("00");
  public int perAmtAppr;
  public int benAmtAppr;
  public int conAmtAppr;
  public int suppAmtAppr;
  public int othAmtAppr;
  public int perAmtReq;
  public int benAmtReq;
  public int conAmtReq;
  public int suppAmtReq;
  public int othAmtReq;
  public int perExpSub;
  public int benExpSub;
  public int conExpSub;
  public int suppExpSub;
  public int othExpSub;
  public int perExpAppr;
  public int benExpAppr;
  public int conExpAppr;
  public int suppExpAppr;
  public int othExpAppr;
  public int perProjTot;
  public int benProjTot;
  public int conProjTot;
  public int suppProjTot;
  public int othProjTot;
  public int perInstCont;
  public int benInstCont;
  public int conInstCont;
  public int suppInstCont;
  public int othInstCont;
  public int travAmtAppr;
  public int travAmtReq;
  public int travExpSub;
  public int travExpAppr;
  public int travInstCont;
  public int travProjTot;
  public int perAmtAmend;
  public int benAmtAmend;
  public int conAmtAmend;
  public int suppAmtAmend;
  public int othAmtAmend;
  public int travAmtAmend;
  public int totAmtReq;
  public int totAmtAppr;
  public int totAmtAmend;
  public int totExpSub;
  public int totExpAppr;
  public int totInstCont;
  public int totProjTot;
  public int remainingFund;
  public int saLimit = 158000;
  public int diLimit = 40000;
  public int alLimit = 20000;
  public int flLimit = 45000;
  public int litMinimum = 10000;
  public int lgIndivLimit = 75000;//request to change to 75k 
  public int lgCoopLimit = 100000;
  public int lgSharedLimit =150000;
  public int lgPlanLimit =100000;
  public int lgImplementLimit =500000;
  public String saMessage;
  public String diMessage;
  public String lgMessage;
  public boolean lgCooperative;
  public boolean lgSharedServ;
  public String diTotApprMessage;
  public long allocationAmt;
  //8 fields used for supplies vs equip (fs forms and lgrmif)
  public int equipAmtReq;
  public int equipAmtAppr;
  public int equipExpSub;
  public int equipExpAppr;
  public int equipAmtAmend;
  public int supplyAmtReq;
  public int supplyAmtAppr;
  public int supplyExpSub;
  public int supplyExpAppr;
  public int supplyAmtAmend;
  //8 fields used for profesional staff vs support staff (fs forms and lgrmif)
  public int proffAmtReq;
  public int proffAmtAppr;
  public int proffExpSub;
  public int proffExpAppr;
  public int proffAmtAmend;
  public int profsuppAmtReq;
  public int profsuppAmtAppr;
  public int profsuppExpSub;
  public int profsuppExpAppr;
  public int profsuppAmtAmend;
  //8 fields used for boces services vs purch serv
  public int bocesAmtReq;
  public int bocesAmtAppr;
  public int bocesExpSub;
  public int bocesExpAppr;  
  public int bocesAmtAmend;
  public int purchAmtReq;
  public int purchAmtAppr;
  public int purchExpSub;
  public int purchExpAppr; 
  public int purchAmtAmend;
  public int fycode;
  public int fccode;
  public int projnum;
  public String instName;
  public boolean warning;
  public String flWarning;
  public String alWarning;
  public boolean notice;
  public String coWarning;
  public String coNotice;
  public String litWarning;
  public int amtavailable;
  public String lgWarning;
  public String lgNotice;
  public String cnMessage;
  public int amtdifference;
  public int recommendamt;
  public String status;
  public boolean amtover;
  public String amtoverWarning;
  public String panel;
  //the following for construction
  public double maxRequestCost;
  public int requestCost;
  public long sysAmtRecommended;   
  //for construction final expenses
  public long grantFundTotal;
  public long matchFundTotal;
  public long totalExpenses;
  public long grantFundPurchased;
  public long matchFundPurchased;
  public long grantFundSupply;
  public long matchFundSupply;
  public long grantFundEquip;
  public long matchFundEquip;
  //for lgmif starting fy14-15
  public boolean planningDemo;
  public boolean implementDemo;
        

  public TotalsBean()
  {
  }

  public int getPerAmtAppr()
  {
    return perAmtAppr;
  }

  public void setPerAmtAppr(int perAmtAppr)
  {
    this.perAmtAppr = perAmtAppr;
  }

  public int getBenAmtAppr()
  {
    return benAmtAppr;
  }

  public void setBenAmtAppr(int benAmtAppr)
  {
    this.benAmtAppr = benAmtAppr;
  }

  public int getConAmtAppr()
  {
    return conAmtAppr;
  }

  public void setConAmtAppr(int conAmtAppr)
  {
    this.conAmtAppr = conAmtAppr;
  }

  public int getSuppAmtAppr()
  {
    return suppAmtAppr;
  }

  public void setSuppAmtAppr(int suppAmtAppr)
  {
    this.suppAmtAppr = suppAmtAppr;
  }

  public int getOthAmtAppr()
  {
    return othAmtAppr;
  }

  public void setOthAmtAppr(int othAmtAppr)
  {
    this.othAmtAppr = othAmtAppr;
  }

  public int getPerAmtReq()
  {
    return perAmtReq;
  }

  public void setPerAmtReq(int perAmtReq)
  {
    this.perAmtReq = perAmtReq;
  }

  public int getBenAmtReq()
  {
    return benAmtReq;
  }

  public void setBenAmtReq(int benAmtReq)
  {
    this.benAmtReq = benAmtReq;
  }

  public int getConAmtReq()
  {
    return conAmtReq;
  }

  public void setConAmtReq(int conAmtReq)
  {
    this.conAmtReq = conAmtReq;
  }

  public int getSuppAmtReq()
  {
    return suppAmtReq;
  }

  public void setSuppAmtReq(int suppAmtReq)
  {
    this.suppAmtReq = suppAmtReq;
  }

  public int getOthAmtReq()
  {
    return othAmtReq;
  }

  public void setOthAmtReq(int othAmtReq)
  {
    this.othAmtReq = othAmtReq;
  }

  public int getPerExpSub()
  {
    return perExpSub;
  }

  public void setPerExpSub(int perExpSub)
  {
    this.perExpSub = perExpSub;
  }

  public int getBenExpSub()
  {
    return benExpSub;
  }

  public void setBenExpSub(int benExpSub)
  {
    this.benExpSub = benExpSub;
  }

  public int getConExpSub()
  {
    return conExpSub;
  }

  public void setConExpSub(int conExpSub)
  {
    this.conExpSub = conExpSub;
  }

  public int getSuppExpSub()
  {
    return suppExpSub;
  }

  public void setSuppExpSub(int suppExpSub)
  {
    this.suppExpSub = suppExpSub;
  }

  public int getOthExpSub()
  {
    return othExpSub;
  }

  public void setOthExpSub(int othExpSub)
  {
    this.othExpSub = othExpSub;
  }

  public int getPerExpAppr()
  {
    return perExpAppr;
  }

  public void setPerExpAppr(int perExpAppr)
  {
    this.perExpAppr = perExpAppr;
  }

  public int getBenExpAppr()
  {
    return benExpAppr;
  }

  public void setBenExpAppr(int benExpAppr)
  {
    this.benExpAppr = benExpAppr;
  }

  public int getConExpAppr()
  {
    return conExpAppr;
  }

  public void setConExpAppr(int conExpAppr)
  {
    this.conExpAppr = conExpAppr;
  }

  public int getSuppExpAppr()
  {
    return suppExpAppr;
  }

  public void setSuppExpAppr(int suppExpAppr)
  {
    this.suppExpAppr = suppExpAppr;
  }

  public int getOthExpAppr()
  {
    return othExpAppr;
  }

  public void setOthExpAppr(int othExpAppr)
  {
    this.othExpAppr = othExpAppr;
  }

  public int getPerProjTot()
  {
    return perProjTot;
  }

  public void setPerProjTot(int perProjTot)
  {
    this.perProjTot = perProjTot;
  }

  public int getBenProjTot()
  {
    return benProjTot;
  }

  public void setBenProjTot(int benProjTot)
  {
    this.benProjTot = benProjTot;
  }

  public int getConProjTot()
  {
    return conProjTot;
  }

  public void setConProjTot(int conProjTot)
  {
    this.conProjTot = conProjTot;
  }

  public int getSuppProjTot()
  {
    return suppProjTot;
  }

  public void setSuppProjTot(int suppProjTot)
  {
    this.suppProjTot = suppProjTot;
  }

  public int getOthProjTot()
  {
    return othProjTot;
  }

  public void setOthProjTot(int othProjTot)
  {
    this.othProjTot = othProjTot;
  }

  public int getPerInstCont()
  {
    return perInstCont;
  }

  public void setPerInstCont(int perInstCont)
  {
    this.perInstCont = perInstCont;
  }

  public int getBenInstCont()
  {
    return benInstCont;
  }

  public void setBenInstCont(int benInstCont)
  {
    this.benInstCont = benInstCont;
  }

  public int getConInstCont()
  {
    return conInstCont;
  }

  public void setConInstCont(int conInstCont)
  {
    this.conInstCont = conInstCont;
  }

  public int getSuppInstCont()
  {
    return suppInstCont;
  }

  public void setSuppInstCont(int suppInstCont)
  {
    this.suppInstCont = suppInstCont;
  }

  public int getOthInstCont()
  {
    return othInstCont;
  }

  public void setOthInstCont(int othInstCont)
  {
    this.othInstCont = othInstCont;
  }

  public int getTotAmtReq()
  {
    return totAmtReq;
  }

  public void setTotAmtReq(int totAmtReq)
  {
    this.totAmtReq = totAmtReq;
  }

  public int getTotAmtAppr()
  {
    return totAmtAppr;
  }

  public void setTotAmtAppr(int totAmtAppr)
  {
    this.totAmtAppr = totAmtAppr;
  }

  public int getTotExpSub()
  {
    return totExpSub;
  }

  public void setTotExpSub(int totExpSub)
  {
    this.totExpSub = totExpSub;
  }

  public int getTotExpAppr()
  {
    return totExpAppr;
  }

  public void setTotExpAppr(int totExpAppr)
  {
    this.totExpAppr = totExpAppr;
  }

  public int getTotInstCont()
  {
    return totInstCont;
  }

  public void setTotInstCont(int totInstCont)
  {
    this.totInstCont = totInstCont;
  }

  public int getTotProjTot()
  {
    return totProjTot;
  }

  public void setTotProjTot(int totProjTot)
  {
    this.totProjTot = totProjTot;
  }

  public int getSaLimit()
  {
    return saLimit;
  }

  public void setSaLimit(int saLimit)
  {
    this.saLimit = saLimit;
  }

  public int getDiLimit()
  {
    return diLimit;
  }

  public void setDiLimit(int diLimit)
  {
    this.diLimit = diLimit;
  }

  public String getSaMessage()
  {
    saMessage = "Warning:  The limit for Statutory grants is $"+ numF.format(saLimit) +
    ". The amount requested for this grant application $"+ numF.format(totAmtReq) +
    " is over the amount allowed.";
    return saMessage;
  }

  public String getDiMessage()
  {
    diMessage = "Warning:  The limit for Discretionary grants is $" +numF.format(diLimit) +
    ". The amount requested for this grant application $"+ numF.format(totAmtReq) +
    " is over the amount allowed.";
    return diMessage;
  }
      
    public String getLgMessage()
    {
      lgMessage = "Warning:  The limit for LGRMIF applications is $"+ numF.format(determineLgLimit()) +
      ". The amount requested for this grant application $"+ numF.format(totAmtReq) +
      " is over the amount allowed.";
      return lgMessage;
    }
    
    public int determineLgLimit() {
        if(isLgCooperative()){
            if(isPlanningDemo())
              return getLgPlanLimit();
            else if(isImplementDemo())
              return getLgImplementLimit();
            else
                return getLgIndivLimit();
        }
        else if(isLgSharedServ())
            return getLgSharedLimit();
        else//must be individual type app
            return getLgIndivLimit();
    }

  public String getDiTotApprMessage()
  {
    diTotApprMessage = "Warning: The total amount approved for all Discretionary grants for this "+
      "fiscal year is $" + numF.format(totAmtAppr);
    return diTotApprMessage;
  }

  public void setDiTotApprMessage(String diTotApprMessage)
  {
    this.diTotApprMessage = diTotApprMessage;
  }

  public int getTravAmtAppr()
  {
    return travAmtAppr;
  }

  public void setTravAmtAppr(int travAmtAppr)
  {
    this.travAmtAppr = travAmtAppr;
  }

  public int getTravAmtReq()
  {
    return travAmtReq;
  }

  public void setTravAmtReq(int travAmtReq)
  {
    this.travAmtReq = travAmtReq;
  }

  public int getTravExpSub()
  {
    return travExpSub;
  }

  public void setTravExpSub(int travExpSub)
  {
    this.travExpSub = travExpSub;
  }

  public int getTravExpAppr()
  {
    return travExpAppr;
  }

  public void setTravExpAppr(int travExpAppr)
  {
    this.travExpAppr = travExpAppr;
  }

  public int getTravInstCont()
  {
    return travInstCont;
  }

  public void setTravInstCont(int travInstCont)
  {
    this.travInstCont = travInstCont;
  }

  public int getTravProjTot()
  {
    return travProjTot;
  }

  public void setTravProjTot(int travProjTot)
  {
    this.travProjTot = travProjTot;
  }

 public int getFycode()
  {
    return fycode;
  }

  public void setFycode(int fycode)
  {
    this.fycode = fycode;
  }

  public int getRemainingFund()
  {
    return remainingFund;
  }

  public void setRemainingFund(int remainingFund)
  {
    this.remainingFund = remainingFund;
  }

  public int getAlLimit()
  {
    return alLimit;
  }

  public void setAlLimit(int alLimit)
  {
    this.alLimit = alLimit;
  }

  public int getFlLimit()
  {
    return flLimit;
  }

  public void setFlLimit(int flLimit)
  {
    this.flLimit = flLimit;
  }

  public boolean isWarning()
  {
    return warning;
  }

  public void setWarning(boolean warning)
  {
    this.warning = warning;
  }
  
  public String getFlWarning()
  {
    flWarning = "Warning: The maximum amount for Family Literacy grants is $" +numF.format(flLimit) +" per year.  "+
    " The minimum amount for Family Literacy grants is $" +numF.format(litMinimum) +" per year.";
    return flWarning;
  }
 
  public String getAlWarning()
  {
    alWarning = "Warning: The maximum amount for Adult Literacy grants is $" +numF.format(alLimit) +" per year.  "+
    " The minimum amount for Adult Literacy grants is $" + numF.format(litMinimum) +" per year.";
    return alWarning;
  }
  
  public String getCoWarning()
  {
    alWarning = "Warning:  The amount approved for Fiscal Year "+ numTwoDigit.format(fycode) +" is $" +numF.format(totAmtAppr);
    return alWarning;
  }
  
  public String getCoNotice()
  {
    alWarning = "Notice:  The amount approved for Fiscal Year "+  numTwoDigit.format(fycode) +" is $" +numF.format(totAmtAppr)
    +" The amount approved is close to the limit for this fiscal year.";
    return alWarning;
  }

  public boolean isNotice()
  {
    return notice;
  }

  public void setNotice(boolean notice)
  {
    this.notice = notice;
  }

  public int getProffAmtReq()
  {
    return proffAmtReq;
  }

  public void setProffAmtReq(int proffAmtReq)
  {
    this.proffAmtReq = proffAmtReq;
  }

  public int getProffAmtAppr()
  {
    return proffAmtAppr;
  }

  public void setProffAmtAppr(int proffAmtAppr)
  {
    this.proffAmtAppr = proffAmtAppr;
  }

  public int getProffExpSub()
  {
    return proffExpSub;
  }

  public void setProffExpSub(int proffExpSub)
  {
    this.proffExpSub = proffExpSub;
  }

  public int getProffExpAppr()
  {
    return proffExpAppr;
  }

  public void setProffExpAppr(int proffExpAppr)
  {
    this.proffExpAppr = proffExpAppr;
  }

  public int getProfsuppAmtReq()
  {
    return profsuppAmtReq;
  }

  public void setProfsuppAmtReq(int profsuppAmtReq)
  {
    this.profsuppAmtReq = profsuppAmtReq;
  }

  public int getProfsuppAmtAppr()
  {
    return profsuppAmtAppr;
  }

  public void setProfsuppAmtAppr(int profsuppAmtAppr)
  {
    this.profsuppAmtAppr = profsuppAmtAppr;
  }

  public int getProfsuppExpSub()
  {
    return profsuppExpSub;
  }

  public void setProfsuppExpSub(int profsuppExpSub)
  {
    this.profsuppExpSub = profsuppExpSub;
  }

  public int getProfsuppExpAppr()
  {
    return profsuppExpAppr;
  }

  public void setProfsuppExpAppr(int profsuppExpAppr)
  {
    this.profsuppExpAppr = profsuppExpAppr;
  }

  public int getEquipAmtReq()
  {
    return equipAmtReq;
  }

  public void setEquipAmtReq(int equipAmtReq)
  {
    this.equipAmtReq = equipAmtReq;
  }

  public int getEquipAmtAppr()
  {
    return equipAmtAppr;
  }

  public void setEquipAmtAppr(int equipAmtAppr)
  {
    this.equipAmtAppr = equipAmtAppr;
  }

  public int getEquipExpSub()
  {
    return equipExpSub;
  }

  public void setEquipExpSub(int equipExpSub)
  {
    this.equipExpSub = equipExpSub;
  }

  public int getEquipExpAppr()
  {
    return equipExpAppr;
  }

  public void setEquipExpAppr(int equipExpAppr)
  {
    this.equipExpAppr = equipExpAppr;
  }

  public int getSupplyAmtReq()
  {
    return supplyAmtReq;
  }

  public void setSupplyAmtReq(int supplyAmtReq)
  {
    this.supplyAmtReq = supplyAmtReq;
  }

  public int getSupplyAmtAppr()
  {
    return supplyAmtAppr;
  }

  public void setSupplyAmtAppr(int supplyAmtAppr)
  {
    this.supplyAmtAppr = supplyAmtAppr;
  }

  public int getSupplyExpSub()
  {
    return supplyExpSub;
  }

  public void setSupplyExpSub(int supplyExpSub)
  {
    this.supplyExpSub = supplyExpSub;
  }

  public int getSupplyExpAppr()
  {
    return supplyExpAppr;
  }

  public void setSupplyExpAppr(int supplyExpAppr)
  {
    this.supplyExpAppr = supplyExpAppr;
  }

  public int getBocesAmtReq()
  {
    return bocesAmtReq;
  }

  public void setBocesAmtReq(int bocesAmtReq)
  {
    this.bocesAmtReq = bocesAmtReq;
  }

  public int getBocesAmtAppr()
  {
    return bocesAmtAppr;
  }

  public void setBocesAmtAppr(int bocesAmtAppr)
  {
    this.bocesAmtAppr = bocesAmtAppr;
  }

  public int getBocesExpSub()
  {
    return bocesExpSub;
  }

  public void setBocesExpSub(int bocesExpSub)
  {
    this.bocesExpSub = bocesExpSub;
  }

  public int getBocesExpAppr()
  {
    return bocesExpAppr;
  }

  public void setBocesExpAppr(int bocesExpAppr)
  {
    this.bocesExpAppr = bocesExpAppr;
  }

  public int getPurchAmtReq()
  {
    return purchAmtReq;
  }

  public void setPurchAmtReq(int purchAmtReq)
  {
    this.purchAmtReq = purchAmtReq;
  }

  public int getPurchAmtAppr()
  {
    return purchAmtAppr;
  }

  public void setPurchAmtAppr(int purchAmtAppr)
  {
    this.purchAmtAppr = purchAmtAppr;
  }

  public int getPurchExpSub()
  {
    return purchExpSub;
  }

  public void setPurchExpSub(int purchExpSub)
  {
    this.purchExpSub = purchExpSub;
  }

  public int getPurchExpAppr()
  {
    return purchExpAppr;
  }

  public void setPurchExpAppr(int purchExpAppr)
  {
    this.purchExpAppr = purchExpAppr;
  }
  
  public void calcGrandTotals()
  {
    int totreq = getPerAmtReq() + getBenAmtReq() + getConAmtReq()+
        getSuppAmtReq() + getTravAmtReq() + getOthAmtReq();
    setTotAmtReq(totreq);
    
    int totappr = getPerAmtAppr() + getBenAmtAppr() + getConAmtAppr()+
        getSuppAmtAppr() + getTravAmtAppr() + getOthAmtAppr();
    setTotAmtAppr(totappr);
    
    int totexp = getPerExpSub() + getBenExpSub() + getConExpSub()+
        getSuppExpSub() + getTravExpSub() + getOthExpSub();
    setTotExpSub(totexp);
    
    int expappr = getPerExpAppr() + getBenExpAppr() + getConExpAppr()+
        getSuppExpAppr() + getTravExpAppr() + getOthExpAppr();
    setTotExpAppr(expappr);
    
    int ptot = getPerProjTot() + getBenProjTot() + getConProjTot()+
        getSuppProjTot() + getTravProjTot() + getOthProjTot();
    setTotProjTot(ptot);
    
    int icont = getPerInstCont() + getBenInstCont() + getConInstCont()+
        getSuppInstCont() + getTravInstCont() + getOthInstCont();
    setTotInstCont(icont);
    
      int amtamend = getPerAmtAmend() + getBenAmtAmend() + getConAmtAmend()+
          getSuppAmtAmend() + getTravAmtAmend() + getOthAmtAmend();
      setTotAmtAmend(amtamend);
  }

    public void setLgIndivLimit(int lgIndivLimit) {
        this.lgIndivLimit = lgIndivLimit;
    }

    public int getLgIndivLimit() {
        return lgIndivLimit;
    }

    public void setLgCoopLimit(int lgCoopLimit) {
        this.lgCoopLimit = lgCoopLimit;
    }

    public int getLgCoopLimit() {
        return lgCoopLimit;
    }

    public void setLgCooperative(boolean lgCooperative) {
        this.lgCooperative = lgCooperative;
    }

    public boolean isLgCooperative() {
        return lgCooperative;
    }


  public void setLitMinimum(int litMinimum)
  {
    this.litMinimum = litMinimum;
  }


  public int getLitMinimum()
  {
    return litMinimum;
  }

  public void setLitWarning(String litWarning)
  {
    this.litWarning = litWarning;
  }


  public String getLitWarning()
  {
    this.litWarning = "Warning:  The amount approved for Fiscal Year "+ numTwoDigit.format(fycode) +" is $" +numF.format(totAmtAppr);
    return litWarning;
  }

    public void setAmtavailable(int amtavailable) {
        this.amtavailable = amtavailable;
    }

    public int getAmtavailable() {
        return amtavailable;
    }

    public void setLgWarning(String lgWarning) {
        this.lgWarning = lgWarning;
    }

    public String getLgWarning() {
        this.lgWarning = "Warning:  The amount available to this panel is $"+ numF.format(amtavailable) +
        ".  The amount awarded by this panel is $" + numF.format(totAmtAppr);
        return lgWarning;
    }
    
    public String getLgNotice() {
        this.lgNotice = "Warning:  The amount awarded for this project is not equal to the "+
        "recommended amount on the Final Deliberation Form $" + numF.format(recommendamt);
        return lgNotice;
    }

    public void setAmtdifference(int amtdifference) {
        this.amtdifference = amtdifference;
    }

    public int getAmtdifference() {
        return amtdifference;
    }

    public void setRecommendamt(int recommendamt) {
        this.recommendamt = recommendamt;
    }

    public int getRecommendamt() {
        return recommendamt;
    }

    public void setLgNotice(String lgNotice) {
        this.lgNotice = lgNotice;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setFccode(int fccode) {
        this.fccode = fccode;
    }

    public int getFccode() {
        return fccode;
    }

    public void setProjnum(int projnum) {
        this.projnum = projnum;
    }

    public int getProjnum() {
        return projnum;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getInstName() {
        return instName;
    }

    public void setAmtover(boolean amtover) {
        this.amtover = amtover;
    }

    public boolean isAmtover() {
        return amtover;
    }

    public String getAmtoverWarning() {
        amtoverWarning = "Warning:  The amount awarded for this application is more than the" +
        " amount requested.";
        return amtoverWarning;
    }

    public void setPanel(String panel) {
        this.panel = panel;
    }

    public String getPanel() {
        return panel;
    }

    public void setPerAmtAmend(int perAmtAmend) {
        this.perAmtAmend = perAmtAmend;
    }

    public int getPerAmtAmend() {
        return perAmtAmend;
    }

    public void setBenAmtAmend(int benAmtAmend) {
        this.benAmtAmend = benAmtAmend;
    }

    public int getBenAmtAmend() {
        return benAmtAmend;
    }

    public void setConAmtAmend(int conAmtAmend) {
        this.conAmtAmend = conAmtAmend;
    }

    public int getConAmtAmend() {
        return conAmtAmend;
    }

    public void setSuppAmtAmend(int suppAmtAmend) {
        this.suppAmtAmend = suppAmtAmend;
    }

    public int getSuppAmtAmend() {
        return suppAmtAmend;
    }

    public void setOthAmtAmend(int othAmtAmend) {
        this.othAmtAmend = othAmtAmend;
    }

    public int getOthAmtAmend() {
        return othAmtAmend;
    }

    public void setTravAmtAmend(int travAmtAmend) {
        this.travAmtAmend = travAmtAmend;
    }

    public int getTravAmtAmend() {
        return travAmtAmend;
    }

    public void setTotAmtAmend(int totAmtAmend) {
        this.totAmtAmend = totAmtAmend;
    }

    public int getTotAmtAmend() {
        return totAmtAmend;
    }

    public void setLgSharedLimit(int lgSharedLimit) {
        this.lgSharedLimit = lgSharedLimit;
    }

    public int getLgSharedLimit() {
        return lgSharedLimit;
    }

    public void setLgSharedServ(boolean lgSharedServ) {
        this.lgSharedServ = lgSharedServ;
    }

    public boolean isLgSharedServ() {
        return lgSharedServ;
    }

    public void setEquipAmtAmend(int equipAmtAmend) {
        this.equipAmtAmend = equipAmtAmend;
    }

    public int getEquipAmtAmend() {
        return equipAmtAmend;
    }

    public void setSupplyAmtAmend(int supplyAmtAmend) {
        this.supplyAmtAmend = supplyAmtAmend;
    }

    public int getSupplyAmtAmend() {
        return supplyAmtAmend;
    }

    public void setProffAmtAmend(int proffAmtAmend) {
        this.proffAmtAmend = proffAmtAmend;
    }

    public int getProffAmtAmend() {
        return proffAmtAmend;
    }

    public void setProfsuppAmtAmend(int profsuppAmtAmend) {
        this.profsuppAmtAmend = profsuppAmtAmend;
    }

    public int getProfsuppAmtAmend() {
        return profsuppAmtAmend;
    }

    public void setBocesAmtAmend(int bocesAmtAmend) {
        this.bocesAmtAmend = bocesAmtAmend;
    }

    public int getBocesAmtAmend() {
        return bocesAmtAmend;
    }

    public void setPurchAmtAmend(int purchAmtAmend) {
        this.purchAmtAmend = purchAmtAmend;
    }

    public int getPurchAmtAmend() {
        return purchAmtAmend;
    }

    public void setMaxRequestCost(double maxRequestCost) {
        this.maxRequestCost = maxRequestCost;
    }

    public double getMaxRequestCost() {
        return maxRequestCost;
    }

    public void setRequestCost(int requestCost) {
        this.requestCost = requestCost;
    }

    public int getRequestCost() {
        return requestCost;
    }

    public void setSysAmtRecommended(long sysAmtRecommended) {
        this.sysAmtRecommended = sysAmtRecommended;
    }

    public long getSysAmtRecommended() {
        return sysAmtRecommended;
    }

    public void setCnMessage(String cnMessage) {
        this.cnMessage = cnMessage;
    }

    public String getCnMessage() {
        lgMessage = "Warning:  The 50% Maximum Award/Recommendation amount "+
        " for this project is $"+ numF.format(maxRequestCost) +
        ". The amount approved for this grant project $"+ numF.format(totAmtAppr) +
        " is over the 50% maximum amount.";
        return lgMessage;
    }

    public void setGrantFundTotal(long grantFundTotal) {
        this.grantFundTotal = grantFundTotal;
    }

    public long getGrantFundTotal() {
        return grantFundTotal;
    }

    public void setMatchFundTotal(long matchFundTotal) {
        this.matchFundTotal = matchFundTotal;
    }

    public long getMatchFundTotal() {
        return matchFundTotal;
    }

    public void setTotalExpenses(long totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public long getTotalExpenses() {
        return totalExpenses;
    }

    public void setGrantFundPurchased(long grantFundPurchased) {
        this.grantFundPurchased = grantFundPurchased;
    }

    public long getGrantFundPurchased() {
        return grantFundPurchased;
    }

    public void setMatchFundPurchased(long matchFundPurchased) {
        this.matchFundPurchased = matchFundPurchased;
    }

    public long getMatchFundPurchased() {
        return matchFundPurchased;
    }

    public void setGrantFundSupply(long grantFundSupply) {
        this.grantFundSupply = grantFundSupply;
    }

    public long getGrantFundSupply() {
        return grantFundSupply;
    }

    public void setMatchFundSupply(long matchFundSupply) {
        this.matchFundSupply = matchFundSupply;
    }

    public long getMatchFundSupply() {
        return matchFundSupply;
    }

    public void setGrantFundEquip(long grantFundEquip) {
        this.grantFundEquip = grantFundEquip;
    }

    public long getGrantFundEquip() {
        return grantFundEquip;
    }

    public void setMatchFundEquip(long matchFundEquip) {
        this.matchFundEquip = matchFundEquip;
    }

    public long getMatchFundEquip() {
        return matchFundEquip;
    }

    public void setAllocationAmt(long allocationAmt) {
        this.allocationAmt = allocationAmt;
    }

    public long getAllocationAmt() {
        return allocationAmt;
    }

    public void setLgPlanLimit(int lgPlanLimit) {
        this.lgPlanLimit = lgPlanLimit;
    }

    public int getLgPlanLimit() {
        return lgPlanLimit;
    }

    public void setLgImplementLimit(int lgImplementLimit) {
        this.lgImplementLimit = lgImplementLimit;
    }

    public int getLgImplementLimit() {
        return lgImplementLimit;
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
}
