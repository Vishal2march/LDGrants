/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 1.0
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  SaveStatisticAction.java
 * Creation/Modification History  :
 * SH   9/13/09      Created
 *
 * Description
 * This action will insert/update items from the lgrmif apcnt final statistics page. 
 *****************************************************************************/
package lgrmif;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.AppStatusBean;
import mypackage.DBHandler;
import mypackage.GrantBean;
import mypackage.StatisticBean;
import mypackage.StatisticDBbean;
import mypackage.UserBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SaveStatisticAction extends Action
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
         
         HashMap allStatTypes = sdb.getExistingStatisticTypes(grantid);
       
          if(sb.getInventoryStr()!=null && !sb.getInventoryStr().equals(""))
          {
            sb.setStatisticType(15);
            //modified 8/12/11; validate no decimals or commas in user input
            //sb.setScore(Integer.parseInt(sb.getInventoryStr()));
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getInventoryStr()));
                        
            if(allStatTypes.containsKey(new Integer(15)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
          
          if(sb.getDestroyStr()!=null && !sb.getDestroyStr().equals(""))
          {
            sb.setStatisticType(16);
            //sb.setScore(Integer.parseInt(sb.getDestroyStr()));
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getDestroyStr()));
            
            if(allStatTypes.containsKey(new Integer(16)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
       
          if(sb.getScanStr()!=null && !sb.getScanStr().equals(""))
          {
            sb.setStatisticType(17);
            //sb.setScore(Integer.parseInt(sb.getScanStr()));
             sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getScanStr()));
            
            if(allStatTypes.containsKey(new Integer(17)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
          
          if(sb.getMicrofilmStr()!=null && !sb.getMicrofilmStr().equals(""))
          {
            sb.setStatisticType(18);
            //sb.setScore(Integer.parseInt(sb.getMicrofilmStr()));
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getMicrofilmStr()));
            
            if(allStatTypes.containsKey(new Integer(18)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
          
          if(sb.getDestroymicrofilmStr()!=null && !sb.getDestroymicrofilmStr().equals(""))
          {
            sb.setStatisticType(19);
            //sb.setScore(Integer.parseInt(sb.getDestroymicrofilmStr()));
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getDestroymicrofilmStr()));
            
            if(allStatTypes.containsKey(new Integer(19)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
             
          if(sb.getDestroyscanStr()!=null && !sb.getDestroyscanStr().equals(""))
          {
            sb.setStatisticType(20);
            //sb.setScore(Integer.parseInt(sb.getDestroyscanStr()));
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getDestroyscanStr()));
            
            if(allStatTypes.containsKey(new Integer(20)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
          
          if(sb.getRecordsarrangedStr()!=null && !sb.getRecordsarrangedStr().equals(""))
          {
            sb.setStatisticType(21);
            //sb.setScore(Integer.parseInt(sb.getRecordsarrangedStr()));
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getRecordsarrangedStr()));
            
            if(allStatTypes.containsKey(new Integer(21)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
          
          if(sb.getRecordsdescribedStr()!=null && !sb.getRecordsdescribedStr().equals(""))
          {
            sb.setStatisticType(22);
            //sb.setScore(Integer.parseInt(sb.getRecordsdescribedStr()));
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getRecordsdescribedStr()));
            
            if(allStatTypes.containsKey(new Integer(22)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
          
          if(sb.getInactiverecordsStr()!=null && !sb.getInactiverecordsStr().equals(""))
          {
            sb.setStatisticType(23);
            //sb.setScore(Integer.parseInt(sb.getInactiverecordsStr()));
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getInactiverecordsStr()));
            
            if(allStatTypes.containsKey(new Integer(23)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
          
          if(sb.getRecordsconservedStr()!=null && !sb.getRecordsconservedStr().equals(""))
          {
            sb.setStatisticType(24);
            //sb.setScore(Integer.parseInt(sb.getRecordsconservedStr()));
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getRecordsconservedStr()));
            
            if(allStatTypes.containsKey(new Integer(24)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
          
          if(sb.getHoursStr()!=null && !sb.getHoursStr().equals(""))
          {
            sb.setStatisticType(25);
            //sb.setScore(Integer.parseInt(sb.getHoursStr()));
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getHoursStr()));
            
            if(allStatTypes.containsKey(new Integer(25)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
          
          if(sb.getImageStr()!=null && !sb.getImageStr().equals(""))
          {
            sb.setStatisticType(26);
            //sb.setScore(Integer.parseInt(sb.getImageStr()));
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getImageStr()));
            
            if(allStatTypes.containsKey(new Integer(26)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
          
          if(sb.getOnlineStr()!=null && !sb.getOnlineStr().equals(""))
          {
            sb.setStatisticType(27);
            //sb.setScore(Integer.parseInt(sb.getOnlineStr()));
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getOnlineStr()));
            
            if(allStatTypes.containsKey(new Integer(27)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
          
          if(sb.getSeriesdescripStr()!=null && !sb.getSeriesdescripStr().equals(""))
          {
            sb.setStatisticType(28);
            //sb.setScore(Integer.parseInt(sb.getSeriesdescripStr()));
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getSeriesdescripStr()));
            
            if(allStatTypes.containsKey(new Integer(28)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
          
          if(sb.getOtherStr()!=null && !sb.getOtherStr().equals(""))
          {
            sb.setStatisticType(29);
            //sb.setScore(Integer.parseInt(sb.getOtherStr()));
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getOtherStr()));
            
            //method needs to add/update otherExplained field
            sb.setNarrativeStr(sb.getOtherExplained());
            if(allStatTypes.containsKey(new Integer(29)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
                    
                    
        //2 new fields added on 8/19/13 for fy 2013-14
        if(sb.getStartupcostStr()!=null && !sb.getStartupcostStr().equals(""))
        {
          sb.setStatisticType(80);
          sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getStartupcostStr()));
          
          if(allStatTypes.containsKey(new Integer(80)))
            sdb.updateStatistic(sb);
          else
            sdb.insertStatistic(sb);
        }
           
        if(sb.getCostsavingStr()!=null && !sb.getCostsavingStr().equals(""))
        {
          sb.setStatisticType(81);
          sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getCostsavingStr()));
          
          if(allStatTypes.containsKey(new Integer(81)))
            sdb.updateStatistic(sb);
          else
            sdb.insertStatistic(sb);
        }
           
          
        //refresh page attributes and return to statistic form
         GrantBean gb=dbh.getRecordBean(grantid); 
         sess.setAttribute("thisGrant", gb);  
         AppStatusBean asb = dbh.getApplicationStatus(grantid);
         request.setAttribute("appStatus", asb);
          
        //get all statistics 
        StatisticBean sb2 = sdb.getStatisticsForGrant(grantid);
        sb2.setModule(sb.getModule());
        request.setAttribute("StatBean",sb2);
             
      }catch(Exception e){
        System.out.println("error SaveStatisticAction "+e.getMessage().toString());
      }
      return mapping.findForward(sb.getModule());     
    }       
             
             
}