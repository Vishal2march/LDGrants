package literacy;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.AppStatusBean;
import mypackage.DBHandler;
import mypackage.StatisticBean;
import mypackage.StatisticDBbean;
import mypackage.UserBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LitStatisticAction extends Action
{
  
  
  public ActionForward execute( ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response) throws Exception{    
     
       StatisticDBbean sdb = new StatisticDBbean();
       DBHandler dbh = new DBHandler();
       HttpSession sess = request.getSession();
       StatisticBean sb = new StatisticBean();
       
       //check for session timeout
       boolean userID = (sess.getAttribute("lduser") != null); 
       if (!userID && sess.isNew())
         return mapping.findForward("timeout");
       
       try{
             
         String grantnum= (String) sess.getAttribute("grantid");
         long grantid = Long.parseLong(grantnum);
         UserBean userb = (UserBean)sess.getAttribute("lduser");
         
         //get statistic form
         sb = (StatisticBean) form; 
         sb.setGrantid(grantid);
         sb.setUserid(userb.getUserid());
         
         //get all stattypeId's from db, used to determine whether to add/update score
         HashMap allStatTypes = sdb.getExistingStatisticTypes(grantid);
         
         //refresh page attributes; need FY_Code
         AppStatusBean asb = dbh.getApplicationStatus(grantid);
         request.setAttribute("appStatus", asb);
       
         //check each statistics variable; insert or update data
          if(sb.getSitesStr()!=null && !sb.getSitesStr().equals(""))
          {
            sb.setStatisticType(1);
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getSitesStr()));
            if(allStatTypes.containsKey(new Integer(1)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
          
          if(sb.getHoursStr()!=null && !sb.getHoursStr().equals(""))
          {
            sb.setStatisticType(2);
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getHoursStr()));
            if(allStatTypes.containsKey(new Integer(2)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
       
          if(sb.getUsersStr()!=null && !sb.getUsersStr().equals(""))
          {
            sb.setStatisticType(3);
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getUsersStr()));
            if(allStatTypes.containsKey(new Integer(3)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
          
          if(sb.getProgramsStr()!=null && !sb.getProgramsStr().equals(""))
          {
            sb.setStatisticType(4);
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getProgramsStr()));
            if(allStatTypes.containsKey(new Integer(4)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
          
          if(sb.getParticipantsStr()!=null && !sb.getParticipantsStr().equals(""))
          {
            sb.setStatisticType(5);
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getParticipantsStr()));
            if(allStatTypes.containsKey(new Integer(5)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
          
          if(sb.getCirculateStr()!=null && !sb.getCirculateStr().equals(""))
          {
            sb.setStatisticType(6);
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getCirculateStr()));
            if(allStatTypes.containsKey(new Integer(6)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
       
          if(sb.getDistributeStr()!=null && !sb.getDistributeStr().equals(""))
          {
            sb.setStatisticType(7);
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getDistributeStr()));
            if(allStatTypes.containsKey(new Integer(7)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
          
          if(sb.getSites2Str()!=null && !sb.getSites2Str().equals(""))
          {
            sb.setStatisticType(8);
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getSites2Str()));
            if(allStatTypes.containsKey(new Integer(8)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
          
          if(sb.getHours2Str()!=null && !sb.getHours2Str().equals(""))
          {
            sb.setStatisticType(9);
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getHours2Str()));
            if(allStatTypes.containsKey(new Integer(9)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
          
          if(sb.getUsers2Str()!=null && !sb.getUsers2Str().equals(""))
          {
            sb.setStatisticType(10);
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getUsers2Str()));
            if(allStatTypes.containsKey(new Integer(10)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
          
          if(sb.getPrograms2Str()!=null && !sb.getPrograms2Str().equals(""))
          {
            sb.setStatisticType(11);
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getPrograms2Str()));
            if(allStatTypes.containsKey(new Integer(11)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
          
          if(sb.getParticipants2Str()!=null && !sb.getParticipants2Str().equals(""))
          {
            sb.setStatisticType(12);
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getParticipants2Str()));
            if(allStatTypes.containsKey(new Integer(12)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
          
          if(sb.getCirculate2Str()!=null && !sb.getCirculate2Str().equals(""))
          {
            sb.setStatisticType(13);
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getCirculate2Str()));
            if(allStatTypes.containsKey(new Integer(13)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
          
          if(sb.getDistribute2Str()!=null && !sb.getDistribute2Str().equals(""))
          {
            sb.setStatisticType(14);
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getDistribute2Str()));
            if(allStatTypes.containsKey(new Integer(14)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
                    
    //-------------STARTING FY2013-14; NEED YEAR 3-------------------
    if(asb.getFycode()>13)
    {
        
          if(sb.getSites3Str()!=null && !sb.getSites3Str().equals(""))
          {
             sb.setStatisticType(50);
             sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getSites3Str()));
             if(allStatTypes.containsKey(new Integer(50)))
               sdb.updateStatistic(sb);
             else
               sdb.insertStatistic(sb);
          }
           
          if(sb.getHours3Str()!=null && !sb.getHours3Str().equals(""))
          {
             sb.setStatisticType(51);
             sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getHours3Str()));
             if(allStatTypes.containsKey(new Integer(51)))
               sdb.updateStatistic(sb);
             else
               sdb.insertStatistic(sb);
          }
           
          if(sb.getUsers3Str()!=null && !sb.getUsers3Str().equals(""))
          {
             sb.setStatisticType(52);
             sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getUsers3Str()));
             if(allStatTypes.containsKey(new Integer(52)))
               sdb.updateStatistic(sb);
             else
               sdb.insertStatistic(sb);
          }
          
          if(sb.getPrograms3Str()!=null && !sb.getPrograms3Str().equals(""))
          {
            sb.setStatisticType(53);
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getPrograms3Str()));
            if(allStatTypes.containsKey(new Integer(53)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
          
          if(sb.getParticipants3Str()!=null && !sb.getParticipants3Str().equals(""))
          {
            sb.setStatisticType(54);
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getParticipants3Str()));
            if(allStatTypes.containsKey(new Integer(54)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
          
          if(sb.getCirculate3Str()!=null && !sb.getCirculate3Str().equals(""))
          {
            sb.setStatisticType(55);
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getCirculate3Str()));
            if(allStatTypes.containsKey(new Integer(55)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
          
          if(sb.getDistribute3Str()!=null && !sb.getDistribute3Str().equals(""))
          {
            sb.setStatisticType(56);
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getDistribute3Str()));
            if(allStatTypes.containsKey(new Integer(56)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
    }
          
        //get all statistics 
        StatisticBean sb2 = sdb.getStatisticsForGrant(grantid);
        sb2.setModule(sb.getModule());
        request.setAttribute("StatBean",sb2);
                  
      
      }catch(Exception e){
        System.out.println("error LitStatisticAction "+e.getMessage().toString());
      }
      return mapping.findForward(sb.getModule());     
    }
}