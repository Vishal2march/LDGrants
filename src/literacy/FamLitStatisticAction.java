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

public class FamLitStatisticAction extends Action{
  public ActionForward execute( ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response) throws Exception{    
     
       StatisticDBbean sdb = new StatisticDBbean();
       HttpSession sess = request.getSession();
       StatisticBean sb = new StatisticBean();
       DBHandler dbh = new DBHandler();
      
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
           
         if(sb.getPlanSitesStr()!=null && !sb.getPlanSitesStr().equals(""))
         {
           sb.setStatisticType(58);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getPlanSitesStr()));
           if(allStatTypes.containsKey(new Integer(58)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         } 
           
         if(sb.getChildSloganStr()!=null && !sb.getChildSloganStr().equals(""))
         {
           sb.setStatisticType(59);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getChildSloganStr()));
           if(allStatTypes.containsKey(new Integer(59)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }   
           
         if(sb.getPlanChildSloganStr()!=null && !sb.getPlanChildSloganStr().equals(""))
         {
           sb.setStatisticType(60);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getPlanChildSloganStr()));
           if(allStatTypes.containsKey(new Integer(60)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }   
           
         if(sb.getTeenSloganStr()!=null && !sb.getTeenSloganStr().equals(""))
         {
           sb.setStatisticType(61);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getTeenSloganStr()));
           if(allStatTypes.containsKey(new Integer(61)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }     
           
         if(sb.getPlanTeenSloganStr()!=null && !sb.getPlanTeenSloganStr().equals(""))
         {
           sb.setStatisticType(62);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getPlanTeenSloganStr()));
           if(allStatTypes.containsKey(new Integer(62)))
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
           
         if(sb.getTeenParticipantsStr()!=null && !sb.getTeenParticipantsStr().equals(""))
         {
           sb.setStatisticType(63);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getTeenParticipantsStr()));
           if(allStatTypes.containsKey(new Integer(63)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }    
           
         if(sb.getNumChildMinutesStr()!=null && !sb.getNumChildMinutesStr().equals(""))
         {
           sb.setStatisticType(64);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getNumChildMinutesStr()));
           if(allStatTypes.containsKey(new Integer(64)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }      
           
         if(sb.getChildMinutesReadStr()!=null && !sb.getChildMinutesReadStr().equals(""))
         {
           sb.setStatisticType(65);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getChildMinutesReadStr()));
           if(allStatTypes.containsKey(new Integer(65)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }    
           
         if(sb.getNumTeenMinutesStr()!=null && !sb.getNumTeenMinutesStr().equals(""))
         {
           sb.setStatisticType(66);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getNumTeenMinutesStr()));
           if(allStatTypes.containsKey(new Integer(66)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }     
           
         if(sb.getTeenMinutesReadStr()!=null && !sb.getTeenMinutesReadStr().equals(""))
         {
           sb.setStatisticType(67);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getTeenMinutesReadStr()));
           if(allStatTypes.containsKey(new Integer(67)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }      
           
         if(sb.getNumChildBooksStr()!=null && !sb.getNumChildBooksStr().equals(""))
         {
           sb.setStatisticType(68);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getNumChildBooksStr()));
           if(allStatTypes.containsKey(new Integer(68)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }      
           
         if(sb.getChildBooksReadStr()!=null && !sb.getChildBooksReadStr().equals(""))
         {
           sb.setStatisticType(69);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getChildBooksReadStr()));
           if(allStatTypes.containsKey(new Integer(69)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }      
           
         if(sb.getNumTeenBooksStr()!=null && !sb.getNumTeenBooksStr().equals(""))
         {
           sb.setStatisticType(70);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getNumTeenBooksStr()));
           if(allStatTypes.containsKey(new Integer(70)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }      
           
         if(sb.getTeenBooksReadStr()!=null && !sb.getTeenBooksReadStr().equals(""))
         {
           sb.setStatisticType(71);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getTeenBooksReadStr()));
           if(allStatTypes.containsKey(new Integer(71)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }        
           
         if(sb.getChildProgramsStr()!=null && !sb.getChildProgramsStr().equals(""))
         {
           sb.setStatisticType(72);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getChildProgramsStr()));
           if(allStatTypes.containsKey(new Integer(72)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }          
           
         if(sb.getChildProgramAttendanceStr()!=null && !sb.getChildProgramAttendanceStr().equals(""))
         {
           sb.setStatisticType(73);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getChildProgramAttendanceStr()));
           if(allStatTypes.containsKey(new Integer(73)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }        
           
         if(sb.getTeenProgramsStr()!=null && !sb.getTeenProgramsStr().equals(""))
         {
           sb.setStatisticType(74);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getTeenProgramsStr()));
           if(allStatTypes.containsKey(new Integer(74)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }          
           
         if(sb.getTeenProgramAttendanceStr()!=null && !sb.getTeenProgramAttendanceStr().equals(""))
         {
           sb.setStatisticType(75);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getTeenProgramAttendanceStr()));
           if(allStatTypes.containsKey(new Integer(75)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }     
           
         if(sb.getParentProgramsStr()!=null && !sb.getParentProgramsStr().equals(""))
         {
           sb.setStatisticType(76);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getParentProgramsStr()));
           if(allStatTypes.containsKey(new Integer(76)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }         
           
         if(sb.getParentProgramAttendanceStr()!=null && !sb.getParentProgramAttendanceStr().equals(""))
         {
           sb.setStatisticType(77);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getParentProgramAttendanceStr()));
           if(allStatTypes.containsKey(new Integer(77)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }     
           
         if(sb.getWorkshopsStr()!=null && !sb.getWorkshopsStr().equals(""))
         {
           sb.setStatisticType(78);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getWorkshopsStr()));
           if(allStatTypes.containsKey(new Integer(78)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }         
           
         if(sb.getWorkshopAttendanceStr()!=null && !sb.getWorkshopAttendanceStr().equals(""))
         {
           sb.setStatisticType(79);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getWorkshopAttendanceStr()));
           if(allStatTypes.containsKey(new Integer(79)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }          
  ///////////////////////////YEAR 2 FAMILY LITERACY/////////////////////////////////////////////////
           
           
          //check each statistics variable; insert or update data
           if(sb.getSites2Str()!=null && !sb.getSites2Str().equals(""))
           {
             sb.setStatisticType(8);
             sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getSites2Str()));
             if(allStatTypes.containsKey(new Integer(8)))
               sdb.updateStatistic(sb);
             else
               sdb.insertStatistic(sb);
           }
            
          if(sb.getPlanSitesStr2()!=null && !sb.getPlanSitesStr2().equals(""))
          {
            sb.setStatisticType(83);
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getPlanSitesStr2()));
            if(allStatTypes.containsKey(new Integer(83)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          } 
            
          if(sb.getChildSloganStr2()!=null && !sb.getChildSloganStr2().equals(""))
          {
            sb.setStatisticType(84);
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getChildSloganStr2()));
            if(allStatTypes.containsKey(new Integer(84)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }   
            
          if(sb.getPlanChildSloganStr2()!=null && !sb.getPlanChildSloganStr2().equals(""))
          {
            sb.setStatisticType(85);
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getPlanChildSloganStr2()));
            if(allStatTypes.containsKey(new Integer(85)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }              
                                 
         if(sb.getTeenSloganStr2()!=null && !sb.getTeenSloganStr2().equals(""))
         {
           sb.setStatisticType(86);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getTeenSloganStr2()));
           if(allStatTypes.containsKey(new Integer(86)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }     
           
         if(sb.getPlanTeenSloganStr2()!=null && !sb.getPlanTeenSloganStr2().equals(""))
         {
           sb.setStatisticType(87);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getPlanTeenSloganStr2()));
           if(allStatTypes.containsKey(new Integer(87)))
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
           
         if(sb.getTeenParticipantsStr2()!=null && !sb.getTeenParticipantsStr2().equals(""))
         {
           sb.setStatisticType(88);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getTeenParticipantsStr2()));
           if(allStatTypes.containsKey(new Integer(88)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }    
           
         if(sb.getNumChildMinutesStr2()!=null && !sb.getNumChildMinutesStr2().equals(""))
         {
           sb.setStatisticType(89);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getNumChildMinutesStr2()));
           if(allStatTypes.containsKey(new Integer(89)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }      
           
         if(sb.getChildMinutesReadStr2()!=null && !sb.getChildMinutesReadStr2().equals(""))
         {
           sb.setStatisticType(90);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getChildMinutesReadStr2()));
           if(allStatTypes.containsKey(new Integer(90)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }    
           
         if(sb.getNumTeenMinutesStr2()!=null && !sb.getNumTeenMinutesStr2().equals(""))
         {
           sb.setStatisticType(91);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getNumTeenMinutesStr2()));
           if(allStatTypes.containsKey(new Integer(91)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }     
           
         if(sb.getTeenMinutesReadStr2()!=null && !sb.getTeenMinutesReadStr2().equals(""))
         {
           sb.setStatisticType(92);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getTeenMinutesReadStr2()));
           if(allStatTypes.containsKey(new Integer(92)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }      
                    
         if(sb.getNumChildBooksStr2()!=null && !sb.getNumChildBooksStr2().equals(""))
         {
           sb.setStatisticType(93);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getNumChildBooksStr2()));
           if(allStatTypes.containsKey(new Integer(93)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }      
           
         if(sb.getChildBooksReadStr2()!=null && !sb.getChildBooksReadStr2().equals(""))
         {
           sb.setStatisticType(94);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getChildBooksReadStr2()));
           if(allStatTypes.containsKey(new Integer(94)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }      
           
         if(sb.getNumTeenBooksStr2()!=null && !sb.getNumTeenBooksStr2().equals(""))
         {
           sb.setStatisticType(95);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getNumTeenBooksStr2()));
           if(allStatTypes.containsKey(new Integer(95)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }      
           
         if(sb.getTeenBooksReadStr2()!=null && !sb.getTeenBooksReadStr2().equals(""))
         {
           sb.setStatisticType(96);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getTeenBooksReadStr2()));
           if(allStatTypes.containsKey(new Integer(96)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }        
                  
         if(sb.getChildProgramsStr2()!=null && !sb.getChildProgramsStr2().equals(""))
         {
           sb.setStatisticType(97);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getChildProgramsStr2()));
           if(allStatTypes.containsKey(new Integer(97)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }          
           
         if(sb.getChildProgramAttendanceStr2()!=null && !sb.getChildProgramAttendanceStr2().equals(""))
         {
           sb.setStatisticType(98);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getChildProgramAttendanceStr2()));
           if(allStatTypes.containsKey(new Integer(98)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }        
           
         if(sb.getTeenProgramsStr2()!=null && !sb.getTeenProgramsStr2().equals(""))
         {
           sb.setStatisticType(99);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getTeenProgramsStr2()));
           if(allStatTypes.containsKey(new Integer(99)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }          
           
         if(sb.getTeenProgramAttendanceStr2()!=null && !sb.getTeenProgramAttendanceStr2().equals(""))
         {
           sb.setStatisticType(100);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getTeenProgramAttendanceStr2()));
           if(allStatTypes.containsKey(new Integer(100)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }     
           
         if(sb.getParentProgramsStr2()!=null && !sb.getParentProgramsStr2().equals(""))
         {
           sb.setStatisticType(101);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getParentProgramsStr2()));
           if(allStatTypes.containsKey(new Integer(101)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }         
           
         if(sb.getParentProgramAttendanceStr2()!=null && !sb.getParentProgramAttendanceStr2().equals(""))
         {
           sb.setStatisticType(102);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getParentProgramAttendanceStr2()));
           if(allStatTypes.containsKey(new Integer(102)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }     
           
         if(sb.getWorkshopsStr2()!=null && !sb.getWorkshopsStr2().equals(""))
         {
           sb.setStatisticType(103);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getWorkshopsStr2()));
           if(allStatTypes.containsKey(new Integer(103)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }         
           
         if(sb.getWorkshopAttendanceStr2()!=null && !sb.getWorkshopAttendanceStr2().equals(""))
         {
           sb.setStatisticType(104);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getWorkshopAttendanceStr2()));
           if(allStatTypes.containsKey(new Integer(104)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
         }          
   
   
   
   
    //////////////////////YEAR 3 FAMILY LITERACY//////////////////////////////////////////
          if(sb.getSites3Str()!=null && !sb.getSites3Str().equals(""))
          {
            sb.setStatisticType(50);
            sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getSites3Str()));
            if(allStatTypes.containsKey(new Integer(50)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
          }
           
          if(sb.getPlanSitesStr3()!=null && !sb.getPlanSitesStr3().equals(""))
          {
           sb.setStatisticType(106);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getPlanSitesStr3()));
           if(allStatTypes.containsKey(new Integer(106)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
          }
                  
          if(sb.getChildSloganStr3()!=null && !sb.getChildSloganStr3().equals(""))
          {
           sb.setStatisticType(107);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getChildSloganStr3()));
           if(allStatTypes.containsKey(new Integer(107)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
          }
           
          if(sb.getPlanChildSloganStr3()!=null && !sb.getPlanChildSloganStr3().equals(""))
          {
           sb.setStatisticType(108);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getPlanChildSloganStr3()));
           if(allStatTypes.containsKey(new Integer(108)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
          }
                      
          if(sb.getTeenSloganStr3()!=null && !sb.getTeenSloganStr3().equals(""))
          {
           sb.setStatisticType(109);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getTeenSloganStr3()));
           if(allStatTypes.containsKey(new Integer(109)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
          }
           
          if(sb.getPlanTeenSloganStr3()!=null && !sb.getPlanTeenSloganStr3().equals(""))
          {
           sb.setStatisticType(110);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getPlanTeenSloganStr3()));
           if(allStatTypes.containsKey(new Integer(110)))
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
           
          if(sb.getTeenParticipantsStr3()!=null && !sb.getTeenParticipantsStr3().equals(""))
          {
           sb.setStatisticType(111);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getTeenParticipantsStr3()));
           if(allStatTypes.containsKey(new Integer(111)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
          }
                 
          if(sb.getNumChildMinutesStr3()!=null && !sb.getNumChildMinutesStr3().equals(""))
          {
           sb.setStatisticType(112);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getNumChildMinutesStr3()));
           if(allStatTypes.containsKey(new Integer(112)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
          }
           
          if(sb.getChildMinutesReadStr3()!=null && !sb.getChildMinutesReadStr3().equals(""))
          {
           sb.setStatisticType(113);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getChildMinutesReadStr3()));
           if(allStatTypes.containsKey(new Integer(113)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
          }
                  
          if(sb.getNumTeenMinutesStr3()!=null && !sb.getNumTeenMinutesStr3().equals(""))
          {
           sb.setStatisticType(114);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getNumTeenMinutesStr3()));
           if(allStatTypes.containsKey(new Integer(114)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
          }
           
          if(sb.getTeenMinutesReadStr3()!=null && !sb.getTeenMinutesReadStr3().equals(""))
          {
           sb.setStatisticType(115);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getTeenMinutesReadStr3()));
           if(allStatTypes.containsKey(new Integer(115)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
          }
                      
          if(sb.getNumChildBooksStr3()!=null && !sb.getNumChildBooksStr3().equals(""))
          {
           sb.setStatisticType(116);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getNumChildBooksStr3()));
           if(allStatTypes.containsKey(new Integer(116)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
          }
           
          if(sb.getChildBooksReadStr3()!=null && !sb.getChildBooksReadStr3().equals(""))
          {
           sb.setStatisticType(117);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getChildBooksReadStr3()));
           if(allStatTypes.containsKey(new Integer(117)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
          }         
           
          if(sb.getNumTeenBooksStr3()!=null && !sb.getNumTeenBooksStr3().equals(""))
          {
           sb.setStatisticType(118);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getNumTeenBooksStr3()));
           if(allStatTypes.containsKey(new Integer(118)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
          }
           
          if(sb.getTeenBooksReadStr3()!=null && !sb.getTeenBooksReadStr3().equals(""))
          {
           sb.setStatisticType(119);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getTeenBooksReadStr3()));
           if(allStatTypes.containsKey(new Integer(119)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
          }
                      
          if(sb.getChildProgramsStr3()!=null && !sb.getChildProgramsStr3().equals(""))
          {
           sb.setStatisticType(120);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getChildProgramsStr3()));
           if(allStatTypes.containsKey(new Integer(120)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
          }
           
          if(sb.getChildProgramAttendanceStr3()!=null && !sb.getChildProgramAttendanceStr3().equals(""))
          {
           sb.setStatisticType(121);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getChildProgramAttendanceStr3()));
           if(allStatTypes.containsKey(new Integer(121)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
          }
           
           
          if(sb.getTeenProgramsStr3()!=null && !sb.getTeenProgramsStr3().equals(""))
          {
           sb.setStatisticType(122);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getTeenProgramsStr3()));
           if(allStatTypes.containsKey(new Integer(122)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
          }
           
          if(sb.getTeenProgramAttendanceStr3()!=null && !sb.getTeenProgramAttendanceStr3().equals(""))
          {
           sb.setStatisticType(123);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getTeenProgramAttendanceStr3()));
           if(allStatTypes.containsKey(new Integer(123)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
          }
                      
          if(sb.getParentProgramsStr3()!=null && !sb.getParentProgramsStr3().equals(""))
          {
           sb.setStatisticType(124);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getParentProgramsStr3()));
           if(allStatTypes.containsKey(new Integer(124)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
          }
                      
          if(sb.getParentProgramAttendanceStr3()!=null && !sb.getParentProgramAttendanceStr3().equals(""))
          {
           sb.setStatisticType(125);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getParentProgramAttendanceStr3()));
           if(allStatTypes.containsKey(new Integer(125)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
          }
               
          if(sb.getWorkshopsStr3()!=null && !sb.getWorkshopsStr3().equals(""))
          {
           sb.setStatisticType(126);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getWorkshopsStr3()));
           if(allStatTypes.containsKey(new Integer(126)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
          }
           
          if(sb.getWorkshopAttendanceStr3()!=null && !sb.getWorkshopAttendanceStr3().equals(""))
          {
           sb.setStatisticType(127);
           sb.setScore(dbh.parseCurrencyAmtNoDecimal(sb.getWorkshopAttendanceStr3()));
           if(allStatTypes.containsKey(new Integer(127)))
             sdb.updateStatistic(sb);
           else
             sdb.insertStatistic(sb);
          }
           
           
           
           
           
           
         //get all statistics 
         StatisticBean sb2 = sdb.getStatisticsForGrant(grantid);
         sb2.setModule(sb.getModule());
         request.setAttribute("StatBean",sb2);
           
       }catch(Exception e){System.out.println(e.getMessage().toString());}
       
      return mapping.findForward("flstatistic"); 
    }
}
