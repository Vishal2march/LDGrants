/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  SaveMicroformAction.java
 * Creation/Modification History  :
 * SH   10/13/09      Created
 *
 * Description
 * This action will insert/update records into the statistics table for the lgrmif
 * applicant microform page.
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

public class SaveMicroformAction extends Action{
      
   private StatisticDBbean sdb = new StatisticDBbean();
   
    public ActionForward execute( ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response) throws Exception{    
                  
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
          
             if(sb.isPaperToDigital()){
               sb.setStatisticType(30);          
               if(allStatTypes.containsKey(new Integer(30)))
                 sdb.updateStatistic(sb);
               else
                 sdb.insertStatistic(sb);
             }
             else {             
                 if(allStatTypes.containsKey(new Integer(30)))
                    sdb.deleteStatistic(30, grantid);                 
             }
             
             if(sb.isMicrofilmToDigital()){
               sb.setStatisticType(31);           
               if(allStatTypes.containsKey(new Integer(31)))
                 sdb.updateStatistic(sb);
               else
                 sdb.insertStatistic(sb);
             }
             else {             
                 if(allStatTypes.containsKey(new Integer(31)))
                    sdb.deleteStatistic(31, grantid);                 
             }
             
             
            if(sb.isDigitalToDigital()){
              sb.setStatisticType(32);
              if(allStatTypes.containsKey(new Integer(32)))
                sdb.updateStatistic(sb);
              else
                sdb.insertStatistic(sb);
            }
            else {             
                if(allStatTypes.containsKey(new Integer(32)))
                   sdb.deleteStatistic(32, grantid);                 
            }
            
            if(sb.isPaperToMicrofilm()){
              sb.setStatisticType(33);        
              if(allStatTypes.containsKey(new Integer(33)))
                sdb.updateStatistic(sb);
              else
                sdb.insertStatistic(sb);
            }
            else {             
                if(allStatTypes.containsKey(new Integer(33)))
                   sdb.deleteStatistic(33, grantid);                 
            }
            
            if(sb.isDigitalToMicrofilm()){
              sb.setStatisticType(34);           
              if(allStatTypes.containsKey(new Integer(34)))
                sdb.updateStatistic(sb);
              else
                sdb.insertStatistic(sb);
            }
            else {             
                if(allStatTypes.containsKey(new Integer(34)))
                   sdb.deleteStatistic(34, grantid);                 
            }
            
            
            if(sb.getNameseries()!=null &&  !sb.getNameseries().equals("")) {
                sb.setStatisticType(35);
                sb.setNarrativeStr(sb.getNameseries());
                if(allStatTypes.containsKey(new Integer(35)))
                  sdb.updateStatistic(sb);
                else
                  sdb.insertStatistic(sb);
            }
            else {
              if(allStatTypes.containsKey(new Integer(35)))
                 sdb.deleteStatistic(35, grantid);                 
            }
            
            
            if(sb.getDaterange()!=null &&  !sb.getDaterange().equals("")) {
                sb.setStatisticType(36);
                sb.setNarrativeStr(sb.getDaterange());
                if(allStatTypes.containsKey(new Integer(36)))
                  sdb.updateStatistic(sb);
                else
                  sdb.insertStatistic(sb);
            }
            else {
              if(allStatTypes.containsKey(new Integer(36)))
                 sdb.deleteStatistic(36, grantid);                 
            }
            
            if(sb.getTotalimages()!=null &&  !sb.getTotalimages().equals("")) {
                sb.setStatisticType(37);
                sb.setNarrativeStr(sb.getTotalimages());
                if(allStatTypes.containsKey(new Integer(37)))
                  sdb.updateStatistic(sb);
                else
                  sdb.insertStatistic(sb);
            }
            else {
              if(allStatTypes.containsKey(new Integer(37)))
                 sdb.deleteStatistic(37, grantid);                 
            }
            
            
        if(sb.getRetentionperiod()!=null &&  !sb.getRetentionperiod().equals("")) {
            sb.setStatisticType(38);
            sb.setNarrativeStr(sb.getRetentionperiod());
            if(allStatTypes.containsKey(new Integer(38)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
        }
        else {
          if(allStatTypes.containsKey(new Integer(38)))
             sdb.deleteStatistic(38, grantid);                 
        } 
            
        if(sb.getRecordschedule()!=null &&  !sb.getRecordschedule().equals("")) {
            sb.setStatisticType(39);
            sb.setNarrativeStr(sb.getRecordschedule());
            if(allStatTypes.containsKey(new Integer(39)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
        }
        else {
          if(allStatTypes.containsKey(new Integer(39)))
             sdb.deleteStatistic(39, grantid);                 
        }
                        
        if(sb.isDiazofilm()){
          sb.setStatisticType(40);          
          if(allStatTypes.containsKey(new Integer(40)))
            sdb.updateStatistic(sb);
          else
            sdb.insertStatistic(sb);
        }
        else {             
            if(allStatTypes.containsKey(new Integer(40)))
               sdb.deleteStatistic(40, grantid);                 
        }
                          
        if(sb.isDigitalimage()){
          sb.setStatisticType(41);          
          if(allStatTypes.containsKey(new Integer(41)))
            sdb.updateStatistic(sb);
          else
            sdb.insertStatistic(sb);
        }
        else {             
            if(allStatTypes.containsKey(new Integer(41)))
               sdb.deleteStatistic(41, grantid);                 
        }
        
        if(sb.getElectronicdata()!=null &&  !sb.getElectronicdata().equals("")) {
            sb.setStatisticType(42);
            sb.setNarrativeStr(sb.getElectronicdata());
            if(allStatTypes.containsKey(new Integer(42)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
        }
        else {
          if(allStatTypes.containsKey(new Integer(42)))
             sdb.deleteStatistic(42, grantid);                 
        }
        
        if(sb.getDocsize()!=null &&  !sb.getDocsize().equals("")) {
            sb.setStatisticType(43);
            sb.setNarrativeStr(sb.getDocsize());
            if(allStatTypes.containsKey(new Integer(43)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
        }
        else {
          if(allStatTypes.containsKey(new Integer(43)))
             sdb.deleteStatistic(43, grantid);                 
        }
        
        if(sb.getPapertype()!=null &&  !sb.getPapertype().equals("")) {
            sb.setStatisticType(44);
            sb.setNarrativeStr(sb.getPapertype());
            if(allStatTypes.containsKey(new Integer(44)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
        }
        else {
          if(allStatTypes.containsKey(new Integer(44)))
             sdb.deleteStatistic(44, grantid);                 
        }
        
        if(sb.getPapercondition()!=null &&  !sb.getPapercondition().equals("")) {
            sb.setStatisticType(45);
            sb.setNarrativeStr(sb.getPapercondition());
            if(allStatTypes.containsKey(new Integer(45)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
        }
        else {
          if(allStatTypes.containsKey(new Integer(45)))
             sdb.deleteStatistic(45, grantid);                 
        }
        
        if(sb.getImprint()!=null &&  !sb.getImprint().equals("")) {
            sb.setStatisticType(46);
            sb.setNarrativeStr(sb.getImprint());
            if(allStatTypes.containsKey(new Integer(46)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
        }
        else {
          if(allStatTypes.containsKey(new Integer(46)))
             sdb.deleteStatistic(46, grantid);                 
        }
        
        if(sb.getPapercolor()!=null &&  !sb.getPapercolor().equals("")) {
            sb.setStatisticType(47);
            sb.setNarrativeStr(sb.getPapercolor());
            if(allStatTypes.containsKey(new Integer(47)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
        }
        else {
          if(allStatTypes.containsKey(new Integer(47)))
             sdb.deleteStatistic(47, grantid);                 
        }
        
        if(sb.getFasteners()!=null &&  !sb.getFasteners().equals("")) {
            sb.setStatisticType(48);
            sb.setNarrativeStr(sb.getFasteners());
            if(allStatTypes.containsKey(new Integer(48)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
        }
        else {
          if(allStatTypes.containsKey(new Integer(48)))
             sdb.deleteStatistic(48, grantid);                 
        }
        
        if(sb.getFreqfasteners()!=null &&  !sb.getFreqfasteners().equals("")) {
            sb.setStatisticType(49);
            sb.setNarrativeStr(sb.getFreqfasteners());
            if(allStatTypes.containsKey(new Integer(49)))
              sdb.updateStatistic(sb);
            else
              sdb.insertStatistic(sb);
        }
        else {
          if(allStatTypes.containsKey(new Integer(49)))
             sdb.deleteStatistic(49, grantid);                 
        }      
             
        //refresh page attributes and return to image/microfilm
        DBHandler dbh = new DBHandler();
        GrantBean gb=dbh.getRecordBean(grantid); 
        sess.setAttribute("thisGrant", gb);  
        AppStatusBean asb = dbh.getApplicationStatus(grantid);
        request.setAttribute("appStatus", asb);
      
        //get all statistics 
        StatisticBean sb2 = sdb.getStatisticsForGrant(grantid);
        sb2.setModule(sb.getModule());
        request.setAttribute("StatBean",sb2);
         
    }catch(Exception e){
    System.out.println("error SaveMicroformAction "+e.getMessage().toString());
    }
    return mapping.findForward("success");
    }
}
