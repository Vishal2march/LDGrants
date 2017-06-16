package mypackage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class StatisticDBbean 
{
  public StatisticDBbean()
  {
  }
  
  Connection conn;
  PreparedStatement ps;
  ResultSet rs;
  
  
  
   /**
   * This creates connection to oracle ldgrants db
   * @throws java.lang.Exception
   * @return 
   */
  private static Connection initializeConn() throws Exception
  {
      Context namingContext = new InitialContext();
      DataSource ds = (DataSource) namingContext.lookup("jdbc/LDGrantsDS");
      return ds.getConnection();   
  }
   

  public void Close(Connection conn)
  {
    try
    {
      if(conn != null)
      {
        conn.close();
      }
    }catch(Exception e){}
  }
  
  public void Close(PreparedStatement stmt)
  {
    try
    {
      if(stmt != null)
      {
        stmt.close();
      }
    }catch(Exception e){}
  }

   public void Close(ResultSet rs)
  {
    try
    {
      if(rs != null)
      {
        rs.close();
      }
    }catch(Exception e){}
  }
  
   
  
  public StatisticBean getStatisticsForGrant(long grantid)
  {
    StatisticBean sb = new StatisticBean();    
    try {
      conn = initializeConn();
            
      //get all statistics
      ps = conn.prepareStatement("select * from PROJECT_STATISTICS, STATISTIC_TYPES where "+
        " GRA_ID=? and PROJECT_STATISTICS.ST_ID=STATISTIC_TYPES.ID ");
      ps.setLong(1, grantid);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        int typeid = rs.getInt("ST_ID");
               
        switch(typeid)
        {
          case 1:
            sb.setSites(rs.getInt("SCORE"));
            sb.setSitesStr(rs.getString("SCORE"));
            break;
          case 2:
            sb.setHours(rs.getInt("SCORE"));
            sb.setHoursStr(rs.getString("SCORE"));
            break;
          case 3:
            sb.setUsers(rs.getInt("SCORE"));
            sb.setUsersStr(rs.getString("SCORE"));
            break;
          case 4:
            sb.setPrograms(rs.getInt("SCORE"));
            sb.setProgramsStr(rs.getString("SCORE"));
            break;
          case 5:
            sb.setParticipants(rs.getInt("SCORE"));
            sb.setParticipantsStr(rs.getString("SCORE"));
            break;
          case 6:
            sb.setCirculate(rs.getInt("SCORE"));
            sb.setCirculateStr(rs.getString("SCORE"));
            break;
          case 7:
            sb.setDistribute(rs.getInt("SCORE"));
            sb.setDistributeStr(rs.getString("SCORE"));
            break;
          case 8:
            sb.setSites2(rs.getInt("SCORE"));
            sb.setSites2Str(rs.getString("SCORE"));
            break;
          case 9:
            sb.setHours2(rs.getInt("SCORE"));
            sb.setHours2Str(rs.getString("SCORE"));
            break;
          case 10:
            sb.setUsers2(rs.getInt("SCORE"));
            sb.setUsers2Str(rs.getString("SCORE"));
            break;
          case 11:
            sb.setPrograms2(rs.getInt("SCORE"));
            sb.setPrograms2Str(rs.getString("SCORE"));
            break;
          case 12:
            sb.setParticipants2(rs.getInt("SCORE"));
            sb.setParticipants2Str(rs.getString("SCORE"));
            break;
          case 13:
            sb.setCirculate2(rs.getInt("SCORE"));
            sb.setCirculate2Str(rs.getString("SCORE"));
            break;
          case 14:
            sb.setDistribute2(rs.getInt("SCORE"));
            sb.setDistribute2Str(rs.getString("SCORE"));
            break;   
         case 15://START of LGRMIF final statistics
            sb.setInventory(rs.getInt("SCORE"));
            sb.setInventoryStr(rs.getString("SCORE"));
            break;  
        case 16:
            sb.setDestroy(rs.getInt("SCORE"));
            sb.setDestroyStr(rs.getString("SCORE"));
            break;  
        case 17:
            sb.setScan(rs.getInt("SCORE"));
            sb.setScanStr(rs.getString("SCORE"));
            break;  
        case 18:
            sb.setMicrofilm(rs.getInt("SCORE"));
            sb.setMicrofilmStr(rs.getString("SCORE"));
            break;  
        case 19:
            sb.setDestroymicrofilm(rs.getInt("SCORE"));
            sb.setDestroymicrofilmStr(rs.getString("SCORE"));
            break;  
        case 20:
            sb.setDestroyscan(rs.getInt("SCORE"));
            sb.setDestroyscanStr(rs.getString("SCORE"));
            break;  
        case 21:
            sb.setRecordsarranged(rs.getInt("SCORE"));
            sb.setRecordsarrangedStr(rs.getString("SCORE"));
            break;  
        case 22:
            sb.setRecordsdescribed(rs.getInt("SCORE"));
            sb.setRecordsdescribedStr(rs.getString("SCORE"));
            break;  
        case 23:
            sb.setInactiverecords(rs.getInt("SCORE"));
            sb.setInactiverecordsStr(rs.getString("SCORE"));
            break;  
        case 24:
            sb.setRecordsconserved(rs.getInt("SCORE"));
            sb.setRecordsconservedStr(rs.getString("SCORE"));
            break;  
        case 25:
            sb.setHours(rs.getInt("SCORE"));
            sb.setHoursStr(rs.getString("SCORE"));
            break;  
        case 26:
            sb.setImage(rs.getInt("SCORE"));
            sb.setImageStr(rs.getString("SCORE"));
            break;  
        case 27:
            sb.setOnline(rs.getInt("SCORE"));
            sb.setOnlineStr(rs.getString("SCORE"));
            break;  
        case 28:
            sb.setSeriesdescrip(rs.getInt("SCORE"));
            sb.setSeriesdescripStr(rs.getString("SCORE"));
            break;  
        case 29:
            sb.setOther(rs.getInt("SCORE"));
            sb.setOtherStr(rs.getString("SCORE"));
            sb.setOtherExplained(rs.getString("REASON"));
            break;          //end of LGRMIF final statistics
        case 30:
            sb.setPaperToDigital(true);
            break;
        case 31:
            sb.setMicrofilmToDigital(true);
            break;
        case 32:
            sb.setDigitalToDigital(true);
            break;
        case 33:
            sb.setPaperToMicrofilm(true);
            break;
        case 34:
            sb.setDigitalToMicrofilm(true);
            break;
        case 35:
            sb.setNameseries(rs.getString("reason"));
            break;
        case 36:
            sb.setDaterange(rs.getString("reason"));
            break;
        case 37:
            sb.setTotalimages(rs.getString("reason"));
            break;
        case 38:
            sb.setRetentionperiod(rs.getString("reason"));
            break;
        case 39:
            sb.setRecordschedule(rs.getString("reason"));
            break;
        case 40:
            sb.setDiazofilm(true);
            break;
        case 41:
            sb.setDigitalimage(true);
            break;
        case 42:
            sb.setElectronicdata(rs.getString("reason"));
            break;
        case 43:
            sb.setDocsize(rs.getString("reason"));
            break;
        case 44:
            sb.setPapertype(rs.getString("reason"));
            break;
        case 45:
            sb.setPapercondition(rs.getString("reason"));
            break;
        case 46:
            sb.setImprint(rs.getString("reason"));
            break;        
        case 47:
            sb.setPapercolor(rs.getString("reason"));
            break;        
        case 48:
            sb.setFasteners(rs.getString("reason"));
            break;
        case 49:
            sb.setFreqfasteners(rs.getString("reason"));
            break;            
        case 50://for literacy starting FY 2013-14
            sb.setSites3(rs.getInt("score"));
            sb.setSites3Str(rs.getString("score"));
            break;
        case 51:
            sb.setHours3(rs.getInt("score"));
            sb.setHours3Str(rs.getString("score"));
            break;
        case 52:
            sb.setUsers3(rs.getInt("score"));
            sb.setUsers3Str(rs.getString("score"));
            break;
        case 53:
            sb.setPrograms3(rs.getInt("score"));
            sb.setPrograms3Str(rs.getString("score"));
            break;
        case 54:
            sb.setParticipants3(rs.getInt("score"));
            sb.setParticipants3Str(rs.getString("score"));
            break;
        case 55:
            sb.setCirculate3(rs.getInt("score"));
            sb.setCirculate3Str(rs.getString("score"));
            break;
        case 56:
            sb.setDistribute3(rs.getInt("score"));
            sb.setDistribute3Str(rs.getString("score"));
            break;
        case 58://FOR FAMILY LIT FINAL RPT starting fy2013-14
            sb.setPlanSitesStr(rs.getString("score"));
            sb.setPlanSites(rs.getInt("score"));
            break;
        case 59:
            sb.setChildSloganStr(rs.getString("score"));
            sb.setChildSlogan(rs.getInt("score"));
            break;
        case 60:
            sb.setPlanChildSloganStr(rs.getString("score"));
            sb.setPlanChildSlogan(rs.getInt("score"));
            break;
        case 61:
            sb.setTeenSloganStr(rs.getString("score"));
            sb.setTeenSlogan(rs.getInt("score"));
            break;
        case 62:
            sb.setPlanTeenSloganStr(rs.getString("score"));
            sb.setPlanTeenSlogan(rs.getInt("score"));
            break;
        case 63:
            sb.setTeenParticipants(rs.getInt("score"));
            sb.setTeenParticipantsStr(rs.getString("score"));
            break;
        case 64:
            sb.setNumChildMinutesStr(rs.getString("score"));
            sb.setNumChildMinutes(rs.getInt("score"));
            break;
        case 65:
            sb.setChildMinutesReadStr(rs.getString("score"));
            sb.setChildMinutesRead(rs.getInt("score"));
            break;
        case 66:
            sb.setNumTeenMinutesStr(rs.getString("score"));
            sb.setNumTeenMinutes(rs.getInt("score"));
            break;
        case 67:
            sb.setTeenMinutesReadStr(rs.getString("score"));
            sb.setTeenMinutesRead(rs.getInt("score"));
            break;
        case 68:
            sb.setNumChildBooksStr(rs.getString("score"));
            sb.setNumChildBooks(rs.getInt("score"));
            break;
        case 69:
            sb.setChildBooksReadStr(rs.getString("score"));
            sb.setChildBooksRead(rs.getInt("score"));
            break;
        case 70:
            sb.setNumTeenBooksStr(rs.getString("score"));
            sb.setNumTeenBooks(rs.getInt("score"));
            break;
        case 71:
            sb.setTeenBooksReadStr(rs.getString("score"));
            sb.setTeenBooksRead(rs.getInt("score"));
            break;
        case 72:
            sb.setChildProgramsStr(rs.getString("score"));
            sb.setChildPrograms(rs.getInt("score"));
            break;
        case 73:
            sb.setChildProgramAttendanceStr(rs.getString("score"));
            sb.setChildProgramAttendance(rs.getInt("score"));
            break;
        case 74:
            sb.setTeenProgramsStr(rs.getString("score"));
            sb.setTeenPrograms(rs.getInt("score"));
            break;
        case 75:
            sb.setTeenProgramAttendanceStr(rs.getString("score"));
            sb.setTeenProgramAttendance(rs.getInt("score"));
            break;
        case 76:
            sb.setParentProgramsStr(rs.getString("score"));
            sb.setParentPrograms(rs.getInt("score"));
            break;
        case 77:
            sb.setParentProgramAttendanceStr(rs.getString("score"));
            sb.setParentProgramAttendance(rs.getInt("score"));
            break;
        case 78:
            sb.setWorkshopsStr(rs.getString("score"));
            sb.setWorkshops(rs.getInt("score"));
            break;
        case 79:
            sb.setWorkshopAttendanceStr(rs.getString("score"));
            sb.setWorkshopAttendance(rs.getInt("score"));
            break;  
      //lgrmif new statistics
        case 80:
            sb.setStartupcost(rs.getInt("score"));
            sb.setStartupcostStr(rs.getString("score"));
            break;
        case 81:
            sb.setCostsaving(rs.getInt("score"));
            sb.setCostsavingStr(rs.getString("score"));
            break;
      ///new family lit year 2
          case 83:
              sb.setPlanSites2(rs.getInt("score"));
              sb.setPlanSitesStr2(rs.getString("score"));
              break;
          case 84:
              sb.setChildSlogan2(rs.getInt("score"));
              sb.setChildSloganStr2(rs.getString("score"));
              break;
          case 85:
              sb.setPlanChildSlogan2(rs.getInt("score"));
              sb.setPlanChildSloganStr2(rs.getString("score"));
              break;
          case 86:
              sb.setTeenSlogan2(rs.getInt("score"));
              sb.setTeenSloganStr2(rs.getString("score"));
              break;        
          case 87:
              sb.setPlanTeenSlogan2(rs.getInt("score"));
              sb.setPlanTeenSloganStr2(rs.getString("score"));
              break;
          case 88:
              sb.setTeenParticipants2(rs.getInt("score"));
              sb.setTeenParticipantsStr2(rs.getString("score"));
              break;
          case 89:
              sb.setNumChildMinutes2(rs.getInt("score"));
              sb.setNumChildMinutesStr2(rs.getString("score"));
              break;
          case 90:
              sb.setChildMinutesRead2(rs.getInt("score"));
              sb.setChildMinutesReadStr2(rs.getString("score"));
              break;        
          case 91:
              sb.setNumTeenMinutes2(rs.getInt("score"));
              sb.setNumTeenMinutesStr2(rs.getString("score"));
              break;
          case 92:
              sb.setTeenMinutesRead2(rs.getInt("score"));
              sb.setTeenMinutesReadStr2(rs.getString("score"));
              break;
          case 93:
              sb.setNumChildBooks2(rs.getInt("score"));
              sb.setNumChildBooksStr2(rs.getString("score"));
              break;
          case 94:
              sb.setChildBooksRead2(rs.getInt("score"));
              sb.setChildBooksReadStr2(rs.getString("score"));
              break;        
          case 95:
              sb.setNumTeenBooks2(rs.getInt("score"));
              sb.setNumTeenBooksStr2(rs.getString("score"));
              break;
          case 96:
              sb.setTeenBooksRead2(rs.getInt("score"));
              sb.setTeenBooksReadStr2(rs.getString("score"));
              break;
          case 97:
              sb.setChildPrograms2(rs.getInt("score"));
              sb.setChildProgramsStr2(rs.getString("score"));
              break;
          case 98:
              sb.setChildProgramAttendance2(rs.getInt("score"));
              sb.setChildProgramAttendanceStr2(rs.getString("score"));
              break;        
          case 99:
              sb.setTeenPrograms2(rs.getInt("score"));
              sb.setTeenProgramsStr2(rs.getString("score"));
              break;
          case 100:
              sb.setTeenProgramAttendance2(rs.getInt("score"));
              sb.setTeenProgramAttendanceStr2(rs.getString("score"));
              break;
          case 101:
              sb.setParentPrograms2(rs.getInt("score"));
              sb.setParentProgramsStr2(rs.getString("score"));
              break;
          case 102:
              sb.setParentProgramAttendance2(rs.getInt("score"));
              sb.setParentProgramAttendanceStr2(rs.getString("score"));
              break;        
          case 103:
              sb.setWorkshops2(rs.getInt("score"));
              sb.setWorkshopsStr2(rs.getString("score"));
              break;
          case 104:
              sb.setWorkshopAttendance2(rs.getInt("score"));
              sb.setWorkshopAttendanceStr2(rs.getString("score"));
              break;
        
        /////year 3 family literacy
          case 106:
              sb.setPlanSites3(rs.getInt("score"));
              sb.setPlanSitesStr3(rs.getString("score"));
              break;
          case 107:
              sb.setChildSlogan3(rs.getInt("score"));
              sb.setChildSloganStr3(rs.getString("score"));
              break;        
          case 108:
              sb.setPlanChildSlogan3(rs.getInt("score"));
              sb.setPlanChildSloganStr3(rs.getString("score"));
              break;
          case 109:
              sb.setTeenSlogan3(rs.getInt("score"));
              sb.setTeenSloganStr3(rs.getString("score"));
              break;
          case 110:
              sb.setPlanTeenSlogan3(rs.getInt("score"));
              sb.setPlanTeenSloganStr3(rs.getString("score"));
              break;
          case 111:
              sb.setTeenParticipants3(rs.getInt("score"));
              sb.setTeenParticipantsStr3(rs.getString("score"));
              break;        
          case 112:
              sb.setNumChildMinutes3(rs.getInt("score"));
              sb.setNumChildMinutesStr3(rs.getString("score"));
              break;
          case 113:
              sb.setChildMinutesRead3(rs.getInt("score"));
              sb.setChildMinutesReadStr3(rs.getString("score"));
              break;
          case 114:
              sb.setNumTeenMinutes3(rs.getInt("score"));
              sb.setNumTeenMinutesStr3(rs.getString("score"));
              break;
          case 115:
              sb.setTeenMinutesRead3(rs.getInt("score"));
              sb.setTeenMinutesReadStr3(rs.getString("score"));
              break;        
          case 116:
              sb.setNumChildBooks3(rs.getInt("score"));
              sb.setNumChildBooksStr3(rs.getString("score"));
              break;
          case 117:
              sb.setChildBooksRead3(rs.getInt("score"));
              sb.setChildBooksReadStr3(rs.getString("score"));
              break;
          case 118:
              sb.setNumTeenBooks3(rs.getInt("score"));
              sb.setNumTeenBooksStr3(rs.getString("score"));
              break;
          case 119:
              sb.setTeenBooksRead3(rs.getInt("score"));
              sb.setTeenBooksReadStr3(rs.getString("score"));
              break;        
          case 120:
              sb.setChildPrograms3(rs.getInt("score"));
              sb.setChildProgramsStr3(rs.getString("score"));
              break;
          case 121:
              sb.setChildProgramAttendance3(rs.getInt("score"));
              sb.setChildProgramAttendanceStr3(rs.getString("score"));
              break;
          case 122:
              sb.setTeenPrograms3(rs.getInt("score"));
              sb.setTeenProgramsStr3(rs.getString("score"));
              break;
          case 123:
              sb.setTeenProgramAttendance3(rs.getInt("score"));
              sb.setTeenProgramAttendanceStr3(rs.getString("score"));
              break;        
          case 124:
              sb.setParentPrograms3(rs.getInt("score"));
              sb.setParentProgramsStr3(rs.getString("score"));
              break;
          case 125:
              sb.setParentProgramAttendance3(rs.getInt("score"));
              sb.setParentProgramAttendanceStr3(rs.getString("score"));
              break;
          case 126:
              sb.setWorkshops3(rs.getInt("score"));
              sb.setWorkshopsStr3(rs.getString("score"));
              break;
          case 127:
              sb.setWorkshopAttendance3(rs.getInt("score"));
              sb.setWorkshopAttendanceStr3(rs.getString("score"));
              break;        
        
        }
      }//end while loop
        
        
      //for FL starting fy2013-14
      int childprogram=0, teenprogram=0, childattend=0, teenattend=0;  
      if(sb.getChildProgramsStr()!=null && !sb.getChildProgramsStr().equals("")){
          childprogram = Integer.parseInt(sb.getChildProgramsStr());
      }
      if(sb.getTeenProgramsStr()!=null && !sb.getTeenProgramsStr().equals("")){
          teenprogram = Integer.parseInt(sb.getTeenProgramsStr());
      }
      sb.setChildTeenProgramTotal(childprogram+teenprogram);  
        
        
      if(sb.getChildProgramAttendanceStr()!=null && !sb.getChildProgramAttendanceStr().equals("")){
          childattend = Integer.parseInt(sb.getChildProgramAttendanceStr());
      }
      if(sb.getTeenProgramAttendanceStr()!=null && !sb.getTeenProgramAttendanceStr().equals("")){
         teenattend = Integer.parseInt(sb.getTeenProgramAttendanceStr());
      }
      sb.setChildTeenAttendanceTotal(childattend+teenattend);
        
    }
    catch(Exception e){
      System.err.println("error getStatisticsForGrant() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }  
    return sb;
  }
  
  public boolean hasStatisticRecord(long grantid, int statistictypeid)
  {
    boolean hasStat=false;
        
    try {      
      conn = initializeConn();
      
      ps = conn.prepareStatement("select * from PROJECT_STATISTICS where GRA_ID=? and ST_ID=?");
      ps.setLong(1, grantid); 
      ps.setInt(2, statistictypeid);
      rs = ps.executeQuery();
      
      while(rs.next()){
        hasStat = true;
      }
            
    }
    catch(Exception e){
      System.err.println("error hasStatisticRecord() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
    return hasStat;
  }
  
  
  public HashMap getExistingStatisticTypes(long grantid)
  {
    HashMap allStatTypes = new HashMap();
        
    try {      
      conn = initializeConn();
      
      ps = conn.prepareStatement("select * from PROJECT_STATISTICS where GRA_ID=?");
      ps.setLong(1, grantid); 
      rs = ps.executeQuery();
      
      while(rs.next()){
        int id = rs.getInt("st_id");
        allStatTypes.put(new Integer(id), "true");
      }
            
    }
    catch(Exception e){
      System.err.println("error getExistingStatisticTypes() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
    return allStatTypes;
  }

  public int insertStatistic(StatisticBean sb)
  {
    int outcome = 0;
    
    try {      
      conn = initializeConn();
      
      ps = conn.prepareStatement("insert into PROJECT_STATISTICS (ID, SCORE, DATE_CREATED, "+
      " CREATED_BY, ST_ID, GRA_ID, REASON) values  (PS_SEQ.nextval, ?,sysdate,?,?,?,?) ");
      ps.setInt(1, sb.getScore()); 
      ps.setString(2, sb.getUserid());
      ps.setInt(3, sb.getStatisticType());
      ps.setLong(4, sb.getGrantid());
      ps.setString(5, sb.getNarrativeStr());
       
      outcome = ps.executeUpdate();            
    }
    catch(Exception e){
      System.err.println("error insertStatistic " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
    }
    return outcome;
  }

  public int updateStatistic(StatisticBean sb)
  {
    int outcome = 0;
    
    try{      
      conn = initializeConn();      
      
      ps = conn.prepareStatement("update PROJECT_STATISTICS set SCORE=?, DATE_MODIFIED=SYSDATE, MODIFIED_BY=?, "+
        " REASON=? where ST_ID=? AND GRA_ID=? ");
      ps.setInt(1, sb.getScore()); 
      ps.setString(2, sb.getUserid());
      ps.setString(3, sb.getNarrativeStr());
      ps.setInt(4, sb.getStatisticType());
      ps.setLong(5, sb.getGrantid());       
      outcome = ps.executeUpdate();     
      
    }catch(Exception e){
      System.err.println("error updateStatistic() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
    }
    return outcome;
  }



    public int deleteStatistic(int statisticTypeId, long grantid)
    {
      int outcome = 0;      
      try{      
        conn = initializeConn();      
        
        ps = conn.prepareStatement("delete from PROJECT_STATISTICS where ST_ID=? AND GRA_ID=?");
        ps.setInt(1, statisticTypeId);
        ps.setLong(2, grantid);       
        outcome = ps.executeUpdate();     
        
      }catch(Exception e){
        System.err.println("error deleteStatistic() " + e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(conn);
      }
      return outcome;
    }

}