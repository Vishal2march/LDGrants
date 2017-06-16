package mypackage;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class StatisticBean extends ActionForm
{
  public long grantid;
  public int fycode;
  public int fccode;
  public long projseqnum;
  public String instName;
  public int sites;
  public String sitesStr;
  public String hoursStr;
  public int hours;
  public String usersStr;
  public int users;
  public String programsStr;
  public int programs;
  public String participantsStr;
  public int participants;
  public String circulateStr;
  public int circulate;
  public String distributeStr;
  public int distribute;
  public String module;
  public int statisticType;
  public int score;
  public String userid;
  public int sites2;
  public String sites2Str;
  public int hours2;
  public String hours2Str;
  public int users2;
  public String users2Str;
  public int programs2;
  public String programs2Str;
  public int participants2;
  public String participants2Str;
  public int circulate2;
  public String circulate2Str;
  public int distribute2;
  public String distribute2Str;
  public int sites3;
  public String sites3Str;
  public int hours3;
  public String hours3Str;
  public int users3;
  public String users3Str;
  public int programs3;
  public String programs3Str;
  public int participants3;
  public String participants3Str;
  public int circulate3;
  public String circulate3Str;
  public int distribute3;
  public String distribute3Str;
  public String inventoryStr;
  public int inventory;
  public String destroyStr;
  public int destroy;
  public String scanStr;
  public int scan;
  public String microfilmStr;
  public int microfilm;
  public String destroymicrofilmStr;
  public int destroymicrofilm;
  public String destroyscanStr;
  public int destroyscan;
  public String recordsarrangedStr;
  public int recordsarranged;
  public String recordsdescribedStr;
  public int recordsdescribed;
  public String inactiverecordsStr;
  public int inactiverecords;
  public String recordsconservedStr;
  public int recordsconserved;
  public String imageStr;
  public int image;
  public String onlineStr;
  public int online;
  public String seriesdescripStr;
  public int seriesdescrip;
  public String otherStr;
  public int other;
  public String otherExplained;
  //new for lgrmif fy 2013-14
  public int startupcost;
  public String startupcostStr;
  public int costsaving;
  public String costsavingStr;
  
  public String narrativeStr;
  public boolean paperToDigital;
  public boolean microfilmToDigital;
  public boolean digitalToDigital;
  public boolean paperToMicrofilm;
  public boolean digitalToMicrofilm;
  public String nameseries;
  public String daterange;
  public String totalimages;
  public String retentionperiod;
  public String recordschedule;
  public boolean diazofilm;
  public boolean digitalimage;
  public String electronicdata;
  public String docsize;
  public String papertype;
  public String papercondition;
  public String imprint;
  public String papercolor;
  public String fasteners;
  public String freqfasteners;
  //family literacy year 1 int and string
  public int planSites;
  public int childSlogan;
  public int planChildSlogan;
  public int teenSlogan;
  public int planTeenSlogan;
  public int teenParticipants;  
  public int numChildMinutes;
  public int childMinutesRead;
  public int numTeenMinutes;
  public int teenMinutesRead;  
  public int numChildBooks;
  public int childBooksRead;
  public int numTeenBooks;
  public int teenBooksRead;  
  public int childPrograms;
  public int childProgramAttendance;
  public int teenPrograms;
  public int teenProgramAttendance;  
  public int childTeenProgramTotal;
  public int childTeenAttendanceTotal;  
  public int parentPrograms;
  public int parentProgramAttendance;
  public int workshops;
  public int workshopAttendance;
  ////////////////
  public String planSitesStr;
  public String childSloganStr;
  public String planChildSloganStr;
  public String teenSloganStr;
  public String planTeenSloganStr;
  public String teenParticipantsStr;  
  public String numChildMinutesStr;
  public String childMinutesReadStr;
  public String numTeenMinutesStr;
  public String teenMinutesReadStr;  
  public String numChildBooksStr;
  public String childBooksReadStr;
  public String numTeenBooksStr;
  public String teenBooksReadStr;  
  public String childProgramsStr;
  public String childProgramAttendanceStr;
  public String teenProgramsStr;
  public String teenProgramAttendanceStr; 
  public String parentProgramsStr;
  public String parentProgramAttendanceStr;
  public String workshopsStr;
  public String workshopAttendanceStr;
  //family literacy year 2 int and string
  public int planSites2;
  public int childSlogan2;
  public int planChildSlogan2;
  public int teenSlogan2;
  public int planTeenSlogan2;
  public int teenParticipants2;  
  public int numChildMinutes2;
  public int childMinutesRead2;
  public int numTeenMinutes2;
  public int teenMinutesRead2;  
  public int numChildBooks2;
  public int childBooksRead2;
  public int numTeenBooks2;
  public int teenBooksRead2;  
  public int childPrograms2;
  public int childProgramAttendance2;
  public int teenPrograms2;
  public int teenProgramAttendance2;  
  public int childTeenProgramTotal2;
  public int childTeenAttendanceTotal2;  
  public int parentPrograms2;
  public int parentProgramAttendance2;
  public int workshops2;
  public int workshopAttendance2;
  ////////////////
  public String planSitesStr2;
  public String childSloganStr2;
  public String planChildSloganStr2;
  public String teenSloganStr2;
  public String planTeenSloganStr2;
  public String teenParticipantsStr2;  
  public String numChildMinutesStr2;
  public String childMinutesReadStr2;
  public String numTeenMinutesStr2;
  public String teenMinutesReadStr2;  
  public String numChildBooksStr2;
  public String childBooksReadStr2;
  public String numTeenBooksStr2;
  public String teenBooksReadStr2;  
  public String childProgramsStr2;
  public String childProgramAttendanceStr2;
  public String teenProgramsStr2;
  public String teenProgramAttendanceStr2; 
  public String parentProgramsStr2;
  public String parentProgramAttendanceStr2;
  public String workshopsStr2;
  public String workshopAttendanceStr2;
  //family literacy year 3 int and string
  public int planSites3;
  public int childSlogan3;
  public int planChildSlogan3;
  public int teenSlogan3;
  public int planTeenSlogan3;
  public int teenParticipants3;  
  public int numChildMinutes3;
  public int childMinutesRead3;
  public int numTeenMinutes3;
  public int teenMinutesRead3;  
  public int numChildBooks3;
  public int childBooksRead3;
  public int numTeenBooks3;
  public int teenBooksRead3;  
  public int childPrograms3;
  public int childProgramAttendance3;
  public int teenPrograms3;
  public int teenProgramAttendance3;  
  public int childTeenProgramTotal3;
  public int childTeenAttendanceTotal3;  
  public int parentPrograms3;
  public int parentProgramAttendance3;
  public int workshops3;
  public int workshopAttendance3;
  ////////////////
  public String planSitesStr3;
  public String childSloganStr3;
  public String planChildSloganStr3;
  public String teenSloganStr3;
  public String planTeenSloganStr3;
  public String teenParticipantsStr3;  
  public String numChildMinutesStr3;
  public String childMinutesReadStr3;
  public String numTeenMinutesStr3;
  public String teenMinutesReadStr3;  
  public String numChildBooksStr3;
  public String childBooksReadStr3;
  public String numTeenBooksStr3;
  public String teenBooksReadStr3;  
  public String childProgramsStr3;
  public String childProgramAttendanceStr3;
  public String teenProgramsStr3;
  public String teenProgramAttendanceStr3; 
  public String parentProgramsStr3;
  public String parentProgramAttendanceStr3;
  public String workshopsStr3;
  public String workshopAttendanceStr3;
  
  
  public StatisticBean()
  {
  }

  public long getGrantid()
  {
    return grantid;
  }

  public void setGrantid(long grantid)
  {
    this.grantid = grantid;
  }

  public int getSites()
  {
    return sites;
  }

  public void setSites(int sites)
  {
    this.sites = sites;
  }

  public String getSitesStr()
  {
    return sitesStr;
  }

  public void setSitesStr(String sitesStr)
  {
    this.sitesStr = sitesStr;
  }

  public String getHoursStr()
  {
    return hoursStr;
  }

  public void setHoursStr(String hoursStr)
  {
    this.hoursStr = hoursStr;
  }

  public int getHours()
  {
    return hours;
  }

  public void setHours(int hours)
  {
    this.hours = hours;
  }

  public String getUsersStr()
  {
    return usersStr;
  }

  public void setUsersStr(String usersStr)
  {
    this.usersStr = usersStr;
  }

  public int getUsers()
  {
    return users;
  }

  public void setUsers(int users)
  {
    this.users = users;
  }

  public String getProgramsStr()
  {
    return programsStr;
  }

  public void setProgramsStr(String programsStr)
  {
    this.programsStr = programsStr;
  }

  public int getPrograms()
  {
    return programs;
  }

  public void setPrograms(int programs)
  {
    this.programs = programs;
  }

  public String getParticipantsStr()
  {
    return participantsStr;
  }

  public void setParticipantsStr(String participantsStr)
  {
    this.participantsStr = participantsStr;
  }

  public int getParticipants()
  {
    return participants;
  }

  public void setParticipants(int participants)
  {
    this.participants = participants;
  }

  public String getCirculateStr()
  {
    return circulateStr;
  }

  public void setCirculateStr(String circulateStr)
  {
    this.circulateStr = circulateStr;
  }

  public int getCirculate()
  {
    return circulate;
  }

  public void setCirculate(int circulate)
  {
    this.circulate = circulate;
  }

  public String getDistributeStr()
  {
    return distributeStr;
  }

  public void setDistributeStr(String distributeStr)
  {
    this.distributeStr = distributeStr;
  }

  public int getDistribute()
  {
    return distribute;
  }

  public void setDistribute(int distribute)
  {
    this.distribute = distribute;
  }

  public String getModule()
  {
    return module;
  }

  public void setModule(String module)
  {
    this.module = module;
  }

  public int getStatisticType()
  {
    return statisticType;
  }

  public void setStatisticType(int statisticType)
  {
    this.statisticType = statisticType;
  }

  public int getScore()
  {
    return score;
  }

  public void setScore(int score)
  {
    this.score = score;
  }

  public String getUserid()
  {
    return userid;
  }

  public void setUserid(String userid)
  {
    this.userid = userid;
  }

  public int getSites2()
  {
    return sites2;
  }

  public void setSites2(int sites2)
  {
    this.sites2 = sites2;
  }

  public String getSites2Str()
  {
    return sites2Str;
  }

  public void setSites2Str(String sites2Str)
  {
    this.sites2Str = sites2Str;
  }

  public int getHours2()
  {
    return hours2;
  }

  public void setHours2(int hours2)
  {
    this.hours2 = hours2;
  }

  public String getHours2Str()
  {
    return hours2Str;
  }

  public void setHours2Str(String hours2Str)
  {
    this.hours2Str = hours2Str;
  }

  public int getUsers2()
  {
    return users2;
  }

  public void setUsers2(int users2)
  {
    this.users2 = users2;
  }

  public String getUsers2Str()
  {
    return users2Str;
  }

  public void setUsers2Str(String users2Str)
  {
    this.users2Str = users2Str;
  }

  public int getPrograms2()
  {
    return programs2;
  }

  public void setPrograms2(int programs2)
  {
    this.programs2 = programs2;
  }

  public String getPrograms2Str()
  {
    return programs2Str;
  }

  public void setPrograms2Str(String programs2Str)
  {
    this.programs2Str = programs2Str;
  }

  public int getParticipants2()
  {
    return participants2;
  }

  public void setParticipants2(int participants2)
  {
    this.participants2 = participants2;
  }

  public String getParticipants2Str()
  {
    return participants2Str;
  }

  public void setParticipants2Str(String participants2Str)
  {
    this.participants2Str = participants2Str;
  }

  public int getCirculate2()
  {
    return circulate2;
  }

  public void setCirculate2(int circulate2)
  {
    this.circulate2 = circulate2;
  }

  public String getCirculate2Str()
  {
    return circulate2Str;
  }

  public void setCirculate2Str(String circulate2Str)
  {
    this.circulate2Str = circulate2Str;
  }

  public int getDistribute2()
  {
    return distribute2;
  }

  public void setDistribute2(int distribute2)
  {
    this.distribute2 = distribute2;
  }

  public String getDistribute2Str()
  {
    return distribute2Str;
  }

  public void setDistribute2Str(String distribute2Str)
  {
    this.distribute2Str = distribute2Str;
  }

  public String getInventoryStr()
  {
    return inventoryStr;
  }

  public void setInventoryStr(String inventoryStr)
  {
    this.inventoryStr = inventoryStr;
  }

  public int getInventory()
  {
    return inventory;
  }

  public void setInventory(int inventory)
  {
    this.inventory = inventory;
  }

  public String getDestroyStr()
  {
    return destroyStr;
  }

  public void setDestroyStr(String destroyStr)
  {
    this.destroyStr = destroyStr;
  }

  public int getDestroy()
  {
    return destroy;
  }

  public void setDestroy(int destroy)
  {
    this.destroy = destroy;
  }

  public String getScanStr()
  {
    return scanStr;
  }

  public void setScanStr(String scanStr)
  {
    this.scanStr = scanStr;
  }

  public int getScan()
  {
    return scan;
  }

  public void setScan(int scan)
  {
    this.scan = scan;
  }

  public String getMicrofilmStr()
  {
    return microfilmStr;
  }

  public void setMicrofilmStr(String microfilmStr)
  {
    this.microfilmStr = microfilmStr;
  }

  public int getMicrofilm()
  {
    return microfilm;
  }

  public void setMicrofilm(int microfilm)
  {
    this.microfilm = microfilm;
  }

  public String getDestroymicrofilmStr()
  {
    return destroymicrofilmStr;
  }

  public void setDestroymicrofilmStr(String destroymicrofilmStr)
  {
    this.destroymicrofilmStr = destroymicrofilmStr;
  }

  public int getDestroymicrofilm()
  {
    return destroymicrofilm;
  }

  public void setDestroymicrofilm(int destroymicrofilm)
  {
    this.destroymicrofilm = destroymicrofilm;
  }

  public String getDestroyscanStr()
  {
    return destroyscanStr;
  }

  public void setDestroyscanStr(String destroyscanStr)
  {
    this.destroyscanStr = destroyscanStr;
  }

  public int getDestroyscan()
  {
    return destroyscan;
  }

  public void setDestroyscan(int destroyscan)
  {
    this.destroyscan = destroyscan;
  }

  public String getRecordsarrangedStr()
  {
    return recordsarrangedStr;
  }

  public void setRecordsarrangedStr(String recordsarrangedStr)
  {
    this.recordsarrangedStr = recordsarrangedStr;
  }

  public int getRecordsarranged()
  {
    return recordsarranged;
  }

  public void setRecordsarranged(int recordsarranged)
  {
    this.recordsarranged = recordsarranged;
  }

  public String getRecordsdescribedStr()
  {
    return recordsdescribedStr;
  }

  public void setRecordsdescribedStr(String recordsdescribedStr)
  {
    this.recordsdescribedStr = recordsdescribedStr;
  }

  public int getRecordsdescribed()
  {
    return recordsdescribed;
  }

  public void setRecordsdescribed(int recordsdescribed)
  {
    this.recordsdescribed = recordsdescribed;
  }

  public String getInactiverecordsStr()
  {
    return inactiverecordsStr;
  }

  public void setInactiverecordsStr(String inactiverecordsStr)
  {
    this.inactiverecordsStr = inactiverecordsStr;
  }

  public int getInactiverecords()
  {
    return inactiverecords;
  }

  public void setInactiverecords(int inactiverecords)
  {
    this.inactiverecords = inactiverecords;
  }

  public String getRecordsconservedStr()
  {
    return recordsconservedStr;
  }

  public void setRecordsconservedStr(String recordsconservedStr)
  {
    this.recordsconservedStr = recordsconservedStr;
  }

  public int getRecordsconserved()
  {
    return recordsconserved;
  }

  public void setRecordsconserved(int recordsconserved)
  {
    this.recordsconserved = recordsconserved;
  }

  public String getImageStr()
  {
    return imageStr;
  }

  public void setImageStr(String imageStr)
  {
    this.imageStr = imageStr;
  }

  public int getImage()
  {
    return image;
  }

  public void setImage(int image)
  {
    this.image = image;
  }

  public String getOnlineStr()
  {
    return onlineStr;
  }

  public void setOnlineStr(String onlineStr)
  {
    this.onlineStr = onlineStr;
  }

  public int getOnline()
  {
    return online;
  }

  public void setOnline(int online)
  {
    this.online = online;
  }

  public String getSeriesdescripStr()
  {
    return seriesdescripStr;
  }

  public void setSeriesdescripStr(String seriesdescripStr)
  {
    this.seriesdescripStr = seriesdescripStr;
  }

  public int getSeriesdescrip()
  {
    return seriesdescrip;
  }

  public void setSeriesdescrip(int seriesdescrip)
  {
    this.seriesdescrip = seriesdescrip;
  }

  public String getOtherStr()
  {
    return otherStr;
  }

  public void setOtherStr(String otherStr)
  {
    this.otherStr = otherStr;
  }

  public int getOther()
  {
    return other;
  }

  public void setOther(int other)
  {
    this.other = other;
  }

  public String getOtherExplained()
  {
    return otherExplained;
  }

  public void setOtherExplained(String otherExplained)
  {
    this.otherExplained = otherExplained;
  }

  public String getNarrativeStr()
  {
    return narrativeStr;
  }

  public void setNarrativeStr(String narrativeStr)
  {
    this.narrativeStr = narrativeStr;
  }

    public void setPaperToDigital(boolean paperToDigital) {
        this.paperToDigital = paperToDigital;
    }

    public boolean isPaperToDigital() {
        return paperToDigital;
    }

    public void setMicrofilmToDigital(boolean microfilmToDigital) {
        this.microfilmToDigital = microfilmToDigital;
    }

    public boolean isMicrofilmToDigital() {
        return microfilmToDigital;
    }

    public void setDigitalToDigital(boolean digitalToDigital) {
        this.digitalToDigital = digitalToDigital;
    }

    public boolean isDigitalToDigital() {
        return digitalToDigital;
    }

    public void setPaperToMicrofilm(boolean paperToMicrofilm) {
        this.paperToMicrofilm = paperToMicrofilm;
    }

    public boolean isPaperToMicrofilm() {
        return paperToMicrofilm;
    }

    public void setDigitalToMicrofilm(boolean digitalToMicrofilm) {
        this.digitalToMicrofilm = digitalToMicrofilm;
    }

    public boolean isDigitalToMicrofilm() {
        return digitalToMicrofilm;
    }

    public void setNameseries(String nameseries) {
        this.nameseries = nameseries;
    }

    public String getNameseries() {
        return nameseries;
    }

    public void setDaterange(String daterange) {
        this.daterange = daterange;
    }

    public String getDaterange() {
        return daterange;
    }

    public void setTotalimages(String totalimages) {
        this.totalimages = totalimages;
    }

    public String getTotalimages() {
        return totalimages;
    }

    public void setRetentionperiod(String retentionperiod) {
        this.retentionperiod = retentionperiod;
    }

    public String getRetentionperiod() {
        return retentionperiod;
    }

    public void setRecordschedule(String recordschedule) {
        this.recordschedule = recordschedule;
    }

    public String getRecordschedule() {
        return recordschedule;
    }

    public void setDiazofilm(boolean diazofilm) {
        this.diazofilm = diazofilm;
    }

    public boolean isDiazofilm() {
        return diazofilm;
    }

    public void setDigitalimage(boolean digitalimage) {
        this.digitalimage = digitalimage;
    }

    public boolean isDigitalimage() {
        return digitalimage;
    }

    public void setElectronicdata(String electronicdata) {
        this.electronicdata = electronicdata;
    }

    public String getElectronicdata() {
        return electronicdata;
    }

    public void setDocsize(String docsize) {
        this.docsize = docsize;
    }

    public String getDocsize() {
        return docsize;
    }

    public void setPapertype(String papertype) {
        this.papertype = papertype;
    }

    public String getPapertype() {
        return papertype;
    }

    public void setPapercondition(String papercondition) {
        this.papercondition = papercondition;
    }

    public String getPapercondition() {
        return papercondition;
    }

    public void setImprint(String imprint) {
        this.imprint = imprint;
    }

    public String getImprint() {
        return imprint;
    }

    public void setPapercolor(String papercolor) {
        this.papercolor = papercolor;
    }

    public String getPapercolor() {
        return papercolor;
    }

    public void setFasteners(String fasteners) {
        this.fasteners = fasteners;
    }

    public String getFasteners() {
        return fasteners;
    }

    public void setFreqfasteners(String freqfasteners) {
        this.freqfasteners = freqfasteners;
    }

    public String getFreqfasteners() {
        return freqfasteners;
    }
    
    public boolean isMissing(String value) 
    {
     return((value == null) || (value.trim().equals("")));
    }


    public void setSites3(int sites3) {
        this.sites3 = sites3;
    }

    public int getSites3() {
        return sites3;
    }

    public void setSites3Str(String sites3Str) {
        this.sites3Str = sites3Str;
    }

    public String getSites3Str() {
        return sites3Str;
    }

    public void setHours3(int hours3) {
        this.hours3 = hours3;
    }

    public int getHours3() {
        return hours3;
    }

    public void setHours3Str(String hours3Str) {
        this.hours3Str = hours3Str;
    }

    public String getHours3Str() {
        return hours3Str;
    }

    public void setUsers3(int users3) {
        this.users3 = users3;
    }

    public int getUsers3() {
        return users3;
    }

    public void setUsers3Str(String users3Str) {
        this.users3Str = users3Str;
    }

    public String getUsers3Str() {
        return users3Str;
    }

    public void setPrograms3(int programs3) {
        this.programs3 = programs3;
    }

    public int getPrograms3() {
        return programs3;
    }

    public void setPrograms3Str(String programs3Str) {
        this.programs3Str = programs3Str;
    }

    public String getPrograms3Str() {
        return programs3Str;
    }

    public void setParticipants3(int participants3) {
        this.participants3 = participants3;
    }

    public int getParticipants3() {
        return participants3;
    }

    public void setParticipants3Str(String participants3Str) {
        this.participants3Str = participants3Str;
    }

    public String getParticipants3Str() {
        return participants3Str;
    }

    public void setCirculate3(int circulate3) {
        this.circulate3 = circulate3;
    }

    public int getCirculate3() {
        return circulate3;
    }

    public void setCirculate3Str(String circulate3Str) {
        this.circulate3Str = circulate3Str;
    }

    public String getCirculate3Str() {
        return circulate3Str;
    }

    public void setDistribute3(int distribute3) {
        this.distribute3 = distribute3;
    }

    public int getDistribute3() {
        return distribute3;
    }

    public void setDistribute3Str(String distribute3Str) {
        this.distribute3Str = distribute3Str;
    }

    public String getDistribute3Str() {
        return distribute3Str;
    }

    public void setFycode(int fycode) {
        this.fycode = fycode;
    }

    public int getFycode() {
        return fycode;
    }

    public void setFccode(int fccode) {
        this.fccode = fccode;
    }

    public int getFccode() {
        return fccode;
    }

    public void setProjseqnum(long projseqnum) {
        this.projseqnum = projseqnum;
    }

    public long getProjseqnum() {
        return projseqnum;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getInstName() {
        return instName;
    }

   

    public void setChildTeenProgramTotal(int childTeenProgramTotal) {
        this.childTeenProgramTotal = childTeenProgramTotal;
    }

    public int getChildTeenProgramTotal() {
        return childTeenProgramTotal;
    }

    public void setChildTeenAttendanceTotal(int childTeenAttendanceTotal) {
        this.childTeenAttendanceTotal = childTeenAttendanceTotal;
    }

    public int getChildTeenAttendanceTotal() {
        return childTeenAttendanceTotal;
    }

    public void setStartupcost(int startupcost) {
        this.startupcost = startupcost;
    }

    public int getStartupcost() {
        return startupcost;
    }

    public void setStartupcostStr(String startupcostStr) {
        this.startupcostStr = startupcostStr;
    }

    public String getStartupcostStr() {
        return startupcostStr;
    }

    public void setCostsaving(int costsaving) {
        this.costsaving = costsaving;
    }

    public int getCostsaving() {
        return costsaving;
    }

    public void setCostsavingStr(String costsavingStr) {
        this.costsavingStr = costsavingStr;
    }

    public String getCostsavingStr() {
        return costsavingStr;
    }

    public void setPlanSites(int planSites) {
        this.planSites = planSites;
    }

    public int getPlanSites() {
        return planSites;
    }

    public void setChildSlogan(int childSlogan) {
        this.childSlogan = childSlogan;
    }

    public int getChildSlogan() {
        return childSlogan;
    }

    public void setPlanChildSlogan(int planChildSlogan) {
        this.planChildSlogan = planChildSlogan;
    }

    public int getPlanChildSlogan() {
        return planChildSlogan;
    }

    public void setTeenSlogan(int teenSlogan) {
        this.teenSlogan = teenSlogan;
    }

    public int getTeenSlogan() {
        return teenSlogan;
    }

    public void setPlanTeenSlogan(int planTeenSlogan) {
        this.planTeenSlogan = planTeenSlogan;
    }

    public int getPlanTeenSlogan() {
        return planTeenSlogan;
    }

    public void setTeenParticipants(int teenParticipants) {
        this.teenParticipants = teenParticipants;
    }

    public int getTeenParticipants() {
        return teenParticipants;
    }

    public void setNumChildMinutes(int numChildMinutes) {
        this.numChildMinutes = numChildMinutes;
    }

    public int getNumChildMinutes() {
        return numChildMinutes;
    }

    public void setChildMinutesRead(int childMinutesRead) {
        this.childMinutesRead = childMinutesRead;
    }

    public int getChildMinutesRead() {
        return childMinutesRead;
    }

    public void setNumTeenMinutes(int numTeenMinutes) {
        this.numTeenMinutes = numTeenMinutes;
    }

    public int getNumTeenMinutes() {
        return numTeenMinutes;
    }

    public void setTeenMinutesRead(int teenMinutesRead) {
        this.teenMinutesRead = teenMinutesRead;
    }

    public int getTeenMinutesRead() {
        return teenMinutesRead;
    }

    public void setNumChildBooks(int numChildBooks) {
        this.numChildBooks = numChildBooks;
    }

    public int getNumChildBooks() {
        return numChildBooks;
    }

    public void setChildBooksRead(int childBooksRead) {
        this.childBooksRead = childBooksRead;
    }

    public int getChildBooksRead() {
        return childBooksRead;
    }

    public void setNumTeenBooks(int numTeenBooks) {
        this.numTeenBooks = numTeenBooks;
    }

    public int getNumTeenBooks() {
        return numTeenBooks;
    }

    public void setTeenBooksRead(int teenBooksRead) {
        this.teenBooksRead = teenBooksRead;
    }

    public int getTeenBooksRead() {
        return teenBooksRead;
    }

    public void setChildPrograms(int childPrograms) {
        this.childPrograms = childPrograms;
    }

    public int getChildPrograms() {
        return childPrograms;
    }

    public void setChildProgramAttendance(int childProgramAttendance) {
        this.childProgramAttendance = childProgramAttendance;
    }

    public int getChildProgramAttendance() {
        return childProgramAttendance;
    }

    public void setTeenPrograms(int teenPrograms) {
        this.teenPrograms = teenPrograms;
    }

    public int getTeenPrograms() {
        return teenPrograms;
    }

    public void setTeenProgramAttendance(int teenProgramAttendance) {
        this.teenProgramAttendance = teenProgramAttendance;
    }

    public int getTeenProgramAttendance() {
        return teenProgramAttendance;
    }

    public void setParentPrograms(int parentPrograms) {
        this.parentPrograms = parentPrograms;
    }

    public int getParentPrograms() {
        return parentPrograms;
    }

    public void setParentProgramAttendance(int parentProgramAttendance) {
        this.parentProgramAttendance = parentProgramAttendance;
    }

    public int getParentProgramAttendance() {
        return parentProgramAttendance;
    }

    public void setWorkshops(int workshops) {
        this.workshops = workshops;
    }

    public int getWorkshops() {
        return workshops;
    }

    public void setWorkshopAttendance(int workshopAttendance) {
        this.workshopAttendance = workshopAttendance;
    }

    public int getWorkshopAttendance() {
        return workshopAttendance;
    }

    public void setPlanSitesStr(String planSitesStr) {
        this.planSitesStr = planSitesStr;
    }

    public String getPlanSitesStr() {
        return planSitesStr;
    }

    public void setChildSloganStr(String childSloganStr) {
        this.childSloganStr = childSloganStr;
    }

    public String getChildSloganStr() {
        return childSloganStr;
    }

    public void setPlanChildSloganStr(String planChildSloganStr) {
        this.planChildSloganStr = planChildSloganStr;
    }

    public String getPlanChildSloganStr() {
        return planChildSloganStr;
    }

    public void setTeenSloganStr(String teenSloganStr) {
        this.teenSloganStr = teenSloganStr;
    }

    public String getTeenSloganStr() {
        return teenSloganStr;
    }

    public void setPlanTeenSloganStr(String planTeenSloganStr) {
        this.planTeenSloganStr = planTeenSloganStr;
    }

    public String getPlanTeenSloganStr() {
        return planTeenSloganStr;
    }

    public void setTeenParticipantsStr(String teenParticipantsStr) {
        this.teenParticipantsStr = teenParticipantsStr;
    }

    public String getTeenParticipantsStr() {
        return teenParticipantsStr;
    }

    public void setNumChildMinutesStr(String numChildMinutesStr) {
        this.numChildMinutesStr = numChildMinutesStr;
    }

    public String getNumChildMinutesStr() {
        return numChildMinutesStr;
    }

    public void setChildMinutesReadStr(String childMinutesReadStr) {
        this.childMinutesReadStr = childMinutesReadStr;
    }

    public String getChildMinutesReadStr() {
        return childMinutesReadStr;
    }

    public void setNumTeenMinutesStr(String numTeenMinutesStr) {
        this.numTeenMinutesStr = numTeenMinutesStr;
    }

    public String getNumTeenMinutesStr() {
        return numTeenMinutesStr;
    }

    public void setTeenMinutesReadStr(String teenMinutesReadStr) {
        this.teenMinutesReadStr = teenMinutesReadStr;
    }

    public String getTeenMinutesReadStr() {
        return teenMinutesReadStr;
    }

    public void setNumChildBooksStr(String numChildBooksStr) {
        this.numChildBooksStr = numChildBooksStr;
    }

    public String getNumChildBooksStr() {
        return numChildBooksStr;
    }

    public void setChildBooksReadStr(String childBooksReadStr) {
        this.childBooksReadStr = childBooksReadStr;
    }

    public String getChildBooksReadStr() {
        return childBooksReadStr;
    }

    public void setNumTeenBooksStr(String numTeenBooksStr) {
        this.numTeenBooksStr = numTeenBooksStr;
    }

    public String getNumTeenBooksStr() {
        return numTeenBooksStr;
    }

    public void setTeenBooksReadStr(String teenBooksReadStr) {
        this.teenBooksReadStr = teenBooksReadStr;
    }

    public String getTeenBooksReadStr() {
        return teenBooksReadStr;
    }

    public void setChildProgramsStr(String childProgramsStr) {
        this.childProgramsStr = childProgramsStr;
    }

    public String getChildProgramsStr() {
        return childProgramsStr;
    }

    public void setChildProgramAttendanceStr(String childProgramAttendanceStr) {
        this.childProgramAttendanceStr = childProgramAttendanceStr;
    }

    public String getChildProgramAttendanceStr() {
        return childProgramAttendanceStr;
    }

    public void setTeenProgramsStr(String teenProgramsStr) {
        this.teenProgramsStr = teenProgramsStr;
    }

    public String getTeenProgramsStr() {
        return teenProgramsStr;
    }

    public void setTeenProgramAttendanceStr(String teenProgramAttendanceStr) {
        this.teenProgramAttendanceStr = teenProgramAttendanceStr;
    }

    public String getTeenProgramAttendanceStr() {
        return teenProgramAttendanceStr;
    }

    public void setParentProgramsStr(String parentProgramsStr) {
        this.parentProgramsStr = parentProgramsStr;
    }

    public String getParentProgramsStr() {
        return parentProgramsStr;
    }

    public void setParentProgramAttendanceStr(String parentProgramAttendanceStr) {
        this.parentProgramAttendanceStr = parentProgramAttendanceStr;
    }

    public String getParentProgramAttendanceStr() {
        return parentProgramAttendanceStr;
    }

    public void setWorkshopsStr(String workshopsStr) {
        this.workshopsStr = workshopsStr;
    }

    public String getWorkshopsStr() {
        return workshopsStr;
    }

    public void setWorkshopAttendanceStr(String workshopAttendanceStr) {
        this.workshopAttendanceStr = workshopAttendanceStr;
    }

    public String getWorkshopAttendanceStr() {
        return workshopAttendanceStr;
    }

    public void setPlanSites2(int planSites2) {
        this.planSites2 = planSites2;
    }

    public int getPlanSites2() {
        return planSites2;
    }

    public void setChildSlogan2(int childSlogan2) {
        this.childSlogan2 = childSlogan2;
    }

    public int getChildSlogan2() {
        return childSlogan2;
    }

    public void setPlanChildSlogan2(int planChildSlogan2) {
        this.planChildSlogan2 = planChildSlogan2;
    }

    public int getPlanChildSlogan2() {
        return planChildSlogan2;
    }

    public void setTeenSlogan2(int teenSlogan2) {
        this.teenSlogan2 = teenSlogan2;
    }

    public int getTeenSlogan2() {
        return teenSlogan2;
    }

    public void setPlanTeenSlogan2(int planTeenSlogan2) {
        this.planTeenSlogan2 = planTeenSlogan2;
    }

    public int getPlanTeenSlogan2() {
        return planTeenSlogan2;
    }

    public void setTeenParticipants2(int teenParticipants2) {
        this.teenParticipants2 = teenParticipants2;
    }

    public int getTeenParticipants2() {
        return teenParticipants2;
    }

    public void setNumChildMinutes2(int numChildMinutes2) {
        this.numChildMinutes2 = numChildMinutes2;
    }

    public int getNumChildMinutes2() {
        return numChildMinutes2;
    }

    public void setChildMinutesRead2(int childMinutesRead2) {
        this.childMinutesRead2 = childMinutesRead2;
    }

    public int getChildMinutesRead2() {
        return childMinutesRead2;
    }

    public void setNumTeenMinutes2(int numTeenMinutes2) {
        this.numTeenMinutes2 = numTeenMinutes2;
    }

    public int getNumTeenMinutes2() {
        return numTeenMinutes2;
    }

    public void setTeenMinutesRead2(int teenMinutesRead2) {
        this.teenMinutesRead2 = teenMinutesRead2;
    }

    public int getTeenMinutesRead2() {
        return teenMinutesRead2;
    }

    public void setNumChildBooks2(int numChildBooks2) {
        this.numChildBooks2 = numChildBooks2;
    }

    public int getNumChildBooks2() {
        return numChildBooks2;
    }

    public void setChildBooksRead2(int childBooksRead2) {
        this.childBooksRead2 = childBooksRead2;
    }

    public int getChildBooksRead2() {
        return childBooksRead2;
    }

    public void setNumTeenBooks2(int numTeenBooks2) {
        this.numTeenBooks2 = numTeenBooks2;
    }

    public int getNumTeenBooks2() {
        return numTeenBooks2;
    }

    public void setTeenBooksRead2(int teenBooksRead2) {
        this.teenBooksRead2 = teenBooksRead2;
    }

    public int getTeenBooksRead2() {
        return teenBooksRead2;
    }

    public void setChildPrograms2(int childPrograms2) {
        this.childPrograms2 = childPrograms2;
    }

    public int getChildPrograms2() {
        return childPrograms2;
    }

    public void setChildProgramAttendance2(int childProgramAttendance2) {
        this.childProgramAttendance2 = childProgramAttendance2;
    }

    public int getChildProgramAttendance2() {
        return childProgramAttendance2;
    }

    public void setTeenPrograms2(int teenPrograms2) {
        this.teenPrograms2 = teenPrograms2;
    }

    public int getTeenPrograms2() {
        return teenPrograms2;
    }

    public void setTeenProgramAttendance2(int teenProgramAttendance2) {
        this.teenProgramAttendance2 = teenProgramAttendance2;
    }

    public int getTeenProgramAttendance2() {
        return teenProgramAttendance2;
    }

    public void setChildTeenProgramTotal2(int childTeenProgramTotal2) {
        this.childTeenProgramTotal2 = childTeenProgramTotal2;
    }

    public int getChildTeenProgramTotal2() {
        return childTeenProgramTotal2;
    }

    public void setChildTeenAttendanceTotal2(int childTeenAttendanceTotal2) {
        this.childTeenAttendanceTotal2 = childTeenAttendanceTotal2;
    }

    public int getChildTeenAttendanceTotal2() {
        return childTeenAttendanceTotal2;
    }

    public void setParentPrograms2(int parentPrograms2) {
        this.parentPrograms2 = parentPrograms2;
    }

    public int getParentPrograms2() {
        return parentPrograms2;
    }

    public void setParentProgramAttendance2(int parentProgramAttendance2) {
        this.parentProgramAttendance2 = parentProgramAttendance2;
    }

    public int getParentProgramAttendance2() {
        return parentProgramAttendance2;
    }

    public void setWorkshops2(int workshops2) {
        this.workshops2 = workshops2;
    }

    public int getWorkshops2() {
        return workshops2;
    }

    public void setWorkshopAttendance2(int workshopAttendance2) {
        this.workshopAttendance2 = workshopAttendance2;
    }

    public int getWorkshopAttendance2() {
        return workshopAttendance2;
    }

    public void setPlanSitesStr2(String planSitesStr2) {
        this.planSitesStr2 = planSitesStr2;
    }

    public String getPlanSitesStr2() {
        return planSitesStr2;
    }

    public void setChildSloganStr2(String childSloganStr2) {
        this.childSloganStr2 = childSloganStr2;
    }

    public String getChildSloganStr2() {
        return childSloganStr2;
    }

    public void setPlanChildSloganStr2(String planChildSloganStr2) {
        this.planChildSloganStr2 = planChildSloganStr2;
    }

    public String getPlanChildSloganStr2() {
        return planChildSloganStr2;
    }

    public void setTeenSloganStr2(String teenSloganStr2) {
        this.teenSloganStr2 = teenSloganStr2;
    }

    public String getTeenSloganStr2() {
        return teenSloganStr2;
    }

    public void setPlanTeenSloganStr2(String planTeenSloganStr2) {
        this.planTeenSloganStr2 = planTeenSloganStr2;
    }

    public String getPlanTeenSloganStr2() {
        return planTeenSloganStr2;
    }

    public void setTeenParticipantsStr2(String teenParticipantsStr2) {
        this.teenParticipantsStr2 = teenParticipantsStr2;
    }

    public String getTeenParticipantsStr2() {
        return teenParticipantsStr2;
    }

    public void setNumChildMinutesStr2(String numChildMinutesStr2) {
        this.numChildMinutesStr2 = numChildMinutesStr2;
    }

    public String getNumChildMinutesStr2() {
        return numChildMinutesStr2;
    }

    public void setChildMinutesReadStr2(String childMinutesReadStr2) {
        this.childMinutesReadStr2 = childMinutesReadStr2;
    }

    public String getChildMinutesReadStr2() {
        return childMinutesReadStr2;
    }

    public void setNumTeenMinutesStr2(String numTeenMinutesStr2) {
        this.numTeenMinutesStr2 = numTeenMinutesStr2;
    }

    public String getNumTeenMinutesStr2() {
        return numTeenMinutesStr2;
    }

    public void setTeenMinutesReadStr2(String teenMinutesReadStr2) {
        this.teenMinutesReadStr2 = teenMinutesReadStr2;
    }

    public String getTeenMinutesReadStr2() {
        return teenMinutesReadStr2;
    }

    public void setNumChildBooksStr2(String numChildBooksStr2) {
        this.numChildBooksStr2 = numChildBooksStr2;
    }

    public String getNumChildBooksStr2() {
        return numChildBooksStr2;
    }

    public void setChildBooksReadStr2(String childBooksReadStr2) {
        this.childBooksReadStr2 = childBooksReadStr2;
    }

    public String getChildBooksReadStr2() {
        return childBooksReadStr2;
    }

    public void setNumTeenBooksStr2(String numTeenBooksStr2) {
        this.numTeenBooksStr2 = numTeenBooksStr2;
    }

    public String getNumTeenBooksStr2() {
        return numTeenBooksStr2;
    }

    public void setTeenBooksReadStr2(String teenBooksReadStr2) {
        this.teenBooksReadStr2 = teenBooksReadStr2;
    }

    public String getTeenBooksReadStr2() {
        return teenBooksReadStr2;
    }

    public void setChildProgramsStr2(String childProgramsStr2) {
        this.childProgramsStr2 = childProgramsStr2;
    }

    public String getChildProgramsStr2() {
        return childProgramsStr2;
    }

    public void setChildProgramAttendanceStr2(String childProgramAttendanceStr2) {
        this.childProgramAttendanceStr2 = childProgramAttendanceStr2;
    }

    public String getChildProgramAttendanceStr2() {
        return childProgramAttendanceStr2;
    }

    public void setTeenProgramsStr2(String teenProgramsStr2) {
        this.teenProgramsStr2 = teenProgramsStr2;
    }

    public String getTeenProgramsStr2() {
        return teenProgramsStr2;
    }

    public void setTeenProgramAttendanceStr2(String teenProgramAttendanceStr2) {
        this.teenProgramAttendanceStr2 = teenProgramAttendanceStr2;
    }

    public String getTeenProgramAttendanceStr2() {
        return teenProgramAttendanceStr2;
    }

    public void setParentProgramsStr2(String parentProgramsStr2) {
        this.parentProgramsStr2 = parentProgramsStr2;
    }

    public String getParentProgramsStr2() {
        return parentProgramsStr2;
    }

    public void setParentProgramAttendanceStr2(String parentProgramAttendanceStr2) {
        this.parentProgramAttendanceStr2 = parentProgramAttendanceStr2;
    }

    public String getParentProgramAttendanceStr2() {
        return parentProgramAttendanceStr2;
    }

    public void setWorkshopsStr2(String workshopsStr2) {
        this.workshopsStr2 = workshopsStr2;
    }

    public String getWorkshopsStr2() {
        return workshopsStr2;
    }

    public void setWorkshopAttendanceStr2(String workshopAttendanceStr2) {
        this.workshopAttendanceStr2 = workshopAttendanceStr2;
    }

    public String getWorkshopAttendanceStr2() {
        return workshopAttendanceStr2;
    }

    public void setPlanSites3(int planSites3) {
        this.planSites3 = planSites3;
    }

    public int getPlanSites3() {
        return planSites3;
    }

    public void setChildSlogan3(int childSlogan3) {
        this.childSlogan3 = childSlogan3;
    }

    public int getChildSlogan3() {
        return childSlogan3;
    }

    public void setPlanChildSlogan3(int planChildSlogan3) {
        this.planChildSlogan3 = planChildSlogan3;
    }

    public int getPlanChildSlogan3() {
        return planChildSlogan3;
    }

    public void setTeenSlogan3(int teenSlogan3) {
        this.teenSlogan3 = teenSlogan3;
    }

    public int getTeenSlogan3() {
        return teenSlogan3;
    }

    public void setPlanTeenSlogan3(int planTeenSlogan3) {
        this.planTeenSlogan3 = planTeenSlogan3;
    }

    public int getPlanTeenSlogan3() {
        return planTeenSlogan3;
    }

    public void setTeenParticipants3(int teenParticipants3) {
        this.teenParticipants3 = teenParticipants3;
    }

    public int getTeenParticipants3() {
        return teenParticipants3;
    }

    public void setNumChildMinutes3(int numChildMinutes3) {
        this.numChildMinutes3 = numChildMinutes3;
    }

    public int getNumChildMinutes3() {
        return numChildMinutes3;
    }

    public void setChildMinutesRead3(int childMinutesRead3) {
        this.childMinutesRead3 = childMinutesRead3;
    }

    public int getChildMinutesRead3() {
        return childMinutesRead3;
    }

    public void setNumTeenMinutes3(int numTeenMinutes3) {
        this.numTeenMinutes3 = numTeenMinutes3;
    }

    public int getNumTeenMinutes3() {
        return numTeenMinutes3;
    }

    public void setTeenMinutesRead3(int teenMinutesRead3) {
        this.teenMinutesRead3 = teenMinutesRead3;
    }

    public int getTeenMinutesRead3() {
        return teenMinutesRead3;
    }

    public void setNumChildBooks3(int numChildBooks3) {
        this.numChildBooks3 = numChildBooks3;
    }

    public int getNumChildBooks3() {
        return numChildBooks3;
    }

    public void setChildBooksRead3(int childBooksRead3) {
        this.childBooksRead3 = childBooksRead3;
    }

    public int getChildBooksRead3() {
        return childBooksRead3;
    }

    public void setNumTeenBooks3(int numTeenBooks3) {
        this.numTeenBooks3 = numTeenBooks3;
    }

    public int getNumTeenBooks3() {
        return numTeenBooks3;
    }

    public void setTeenBooksRead3(int teenBooksRead3) {
        this.teenBooksRead3 = teenBooksRead3;
    }

    public int getTeenBooksRead3() {
        return teenBooksRead3;
    }

    public void setChildPrograms3(int childPrograms3) {
        this.childPrograms3 = childPrograms3;
    }

    public int getChildPrograms3() {
        return childPrograms3;
    }

    public void setChildProgramAttendance3(int childProgramAttendance3) {
        this.childProgramAttendance3 = childProgramAttendance3;
    }

    public int getChildProgramAttendance3() {
        return childProgramAttendance3;
    }

    public void setTeenPrograms3(int teenPrograms3) {
        this.teenPrograms3 = teenPrograms3;
    }

    public int getTeenPrograms3() {
        return teenPrograms3;
    }

    public void setTeenProgramAttendance3(int teenProgramAttendance3) {
        this.teenProgramAttendance3 = teenProgramAttendance3;
    }

    public int getTeenProgramAttendance3() {
        return teenProgramAttendance3;
    }

    public void setChildTeenProgramTotal3(int childTeenProgramTotal3) {
        this.childTeenProgramTotal3 = childTeenProgramTotal3;
    }

    public int getChildTeenProgramTotal3() {
        return childTeenProgramTotal3;
    }

    public void setChildTeenAttendanceTotal3(int childTeenAttendanceTotal3) {
        this.childTeenAttendanceTotal3 = childTeenAttendanceTotal3;
    }

    public int getChildTeenAttendanceTotal3() {
        return childTeenAttendanceTotal3;
    }

    public void setParentPrograms3(int parentPrograms3) {
        this.parentPrograms3 = parentPrograms3;
    }

    public int getParentPrograms3() {
        return parentPrograms3;
    }

    public void setParentProgramAttendance3(int parentProgramAttendance3) {
        this.parentProgramAttendance3 = parentProgramAttendance3;
    }

    public int getParentProgramAttendance3() {
        return parentProgramAttendance3;
    }

    public void setWorkshops3(int workshops3) {
        this.workshops3 = workshops3;
    }

    public int getWorkshops3() {
        return workshops3;
    }

    public void setWorkshopAttendance3(int workshopAttendance3) {
        this.workshopAttendance3 = workshopAttendance3;
    }

    public int getWorkshopAttendance3() {
        return workshopAttendance3;
    }

    public void setPlanSitesStr3(String planSitesStr3) {
        this.planSitesStr3 = planSitesStr3;
    }

    public String getPlanSitesStr3() {
        return planSitesStr3;
    }

    public void setChildSloganStr3(String childSloganStr3) {
        this.childSloganStr3 = childSloganStr3;
    }

    public String getChildSloganStr3() {
        return childSloganStr3;
    }

    public void setPlanChildSloganStr3(String planChildSloganStr3) {
        this.planChildSloganStr3 = planChildSloganStr3;
    }

    public String getPlanChildSloganStr3() {
        return planChildSloganStr3;
    }

    public void setTeenSloganStr3(String teenSloganStr3) {
        this.teenSloganStr3 = teenSloganStr3;
    }

    public String getTeenSloganStr3() {
        return teenSloganStr3;
    }

    public void setPlanTeenSloganStr3(String planTeenSloganStr3) {
        this.planTeenSloganStr3 = planTeenSloganStr3;
    }

    public String getPlanTeenSloganStr3() {
        return planTeenSloganStr3;
    }

    public void setTeenParticipantsStr3(String teenParticipantsStr3) {
        this.teenParticipantsStr3 = teenParticipantsStr3;
    }

    public String getTeenParticipantsStr3() {
        return teenParticipantsStr3;
    }

    public void setNumChildMinutesStr3(String numChildMinutesStr3) {
        this.numChildMinutesStr3 = numChildMinutesStr3;
    }

    public String getNumChildMinutesStr3() {
        return numChildMinutesStr3;
    }

    public void setChildMinutesReadStr3(String childMinutesReadStr3) {
        this.childMinutesReadStr3 = childMinutesReadStr3;
    }

    public String getChildMinutesReadStr3() {
        return childMinutesReadStr3;
    }

    public void setNumTeenMinutesStr3(String numTeenMinutesStr3) {
        this.numTeenMinutesStr3 = numTeenMinutesStr3;
    }

    public String getNumTeenMinutesStr3() {
        return numTeenMinutesStr3;
    }

    public void setTeenMinutesReadStr3(String teenMinutesReadStr3) {
        this.teenMinutesReadStr3 = teenMinutesReadStr3;
    }

    public String getTeenMinutesReadStr3() {
        return teenMinutesReadStr3;
    }

    public void setNumChildBooksStr3(String numChildBooksStr3) {
        this.numChildBooksStr3 = numChildBooksStr3;
    }

    public String getNumChildBooksStr3() {
        return numChildBooksStr3;
    }

    public void setChildBooksReadStr3(String childBooksReadStr3) {
        this.childBooksReadStr3 = childBooksReadStr3;
    }

    public String getChildBooksReadStr3() {
        return childBooksReadStr3;
    }

    public void setNumTeenBooksStr3(String numTeenBooksStr3) {
        this.numTeenBooksStr3 = numTeenBooksStr3;
    }

    public String getNumTeenBooksStr3() {
        return numTeenBooksStr3;
    }

    public void setTeenBooksReadStr3(String teenBooksReadStr3) {
        this.teenBooksReadStr3 = teenBooksReadStr3;
    }

    public String getTeenBooksReadStr3() {
        return teenBooksReadStr3;
    }

    public void setChildProgramsStr3(String childProgramsStr3) {
        this.childProgramsStr3 = childProgramsStr3;
    }

    public String getChildProgramsStr3() {
        return childProgramsStr3;
    }

    public void setChildProgramAttendanceStr3(String childProgramAttendanceStr3) {
        this.childProgramAttendanceStr3 = childProgramAttendanceStr3;
    }

    public String getChildProgramAttendanceStr3() {
        return childProgramAttendanceStr3;
    }

    public void setTeenProgramsStr3(String teenProgramsStr3) {
        this.teenProgramsStr3 = teenProgramsStr3;
    }

    public String getTeenProgramsStr3() {
        return teenProgramsStr3;
    }

    public void setTeenProgramAttendanceStr3(String teenProgramAttendanceStr3) {
        this.teenProgramAttendanceStr3 = teenProgramAttendanceStr3;
    }

    public String getTeenProgramAttendanceStr3() {
        return teenProgramAttendanceStr3;
    }

    public void setParentProgramsStr3(String parentProgramsStr3) {
        this.parentProgramsStr3 = parentProgramsStr3;
    }

    public String getParentProgramsStr3() {
        return parentProgramsStr3;
    }

    public void setParentProgramAttendanceStr3(String parentProgramAttendanceStr3) {
        this.parentProgramAttendanceStr3 = parentProgramAttendanceStr3;
    }

    public String getParentProgramAttendanceStr3() {
        return parentProgramAttendanceStr3;
    }

    public void setWorkshopsStr3(String workshopsStr3) {
        this.workshopsStr3 = workshopsStr3;
    }

    public String getWorkshopsStr3() {
        return workshopsStr3;
    }

    public void setWorkshopAttendanceStr3(String workshopAttendanceStr3) {
        this.workshopAttendanceStr3 = workshopAttendanceStr3;
    }

    public String getWorkshopAttendanceStr3() {
        return workshopAttendanceStr3;
    }
}
