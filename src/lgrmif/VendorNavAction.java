/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 1.0
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  VendorNavAction.java
 * Creation/Modification History  :
 * SH   10/13/09      Created
 *
 * Description
 * This dispatch action is used for lgrmif apcnts.  It has actions to get all vendors,
 * individual vendor records, insert/update vendors, add vendor to quote and delete quote.
 *****************************************************************************/
package lgrmif;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypackage.AppStatusBean;
import mypackage.DBHandler;
import mypackage.GrantBean;
import mypackage.RevAssignCollectionBean;
import mypackage.StatisticBean;
import mypackage.StatisticDBbean;

import mypackage.UserBean;
import mypackage.VendorBean;
import mypackage.VendorDBbean;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class VendorNavAction extends DispatchAction{
    
    private VendorDBbean vdb = new VendorDBbean();
    private DBHandler dbh = new DBHandler();
    
    public ActionForward allvendors(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {         
          HttpSession sess = request.getSession(); 
          if(checkSessionTimeout(sess))
            return mapping.findForward("timeout");
                  
         try{             
             ArrayList results = vdb.getAllVendors();
             request.setAttribute("vendorlist", results);    
             
             String grantid = (String) sess.getAttribute("grantid");
             AppStatusBean asb = dbh.getApplicationStatus(Long.parseLong(grantid));
             request.setAttribute("appStatus", asb);
             
         }catch(Exception e){ log.error(e.getMessage().toString()); }
        return mapping.findForward("allvendors");
      }
      
      
    public ActionForward loadsearch(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {         
          HttpSession sess = request.getSession(); 
          if(checkSessionTimeout(sess))
            return mapping.findForward("timeout");
                  
         try{             
             String grantid = (String) sess.getAttribute("grantid");
             AppStatusBean asb = dbh.getApplicationStatus(Long.parseLong(grantid));
             request.setAttribute("appStatus", asb);
             
         }catch(Exception e){ log.error(e.getMessage().toString()); }
        return mapping.findForward("allvendors");
      }
      
    public ActionForward searchvendor(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {         
          HttpSession sess = request.getSession(); 
          if(checkSessionTimeout(sess))
            return mapping.findForward("timeout");
                  
         try{             
             String grantid = (String) sess.getAttribute("grantid");
             AppStatusBean asb = dbh.getApplicationStatus(Long.parseLong(grantid));
             request.setAttribute("appStatus", asb);
             
             String vnamestr = request.getParameter("vname");
             ArrayList results = vdb.searchVendorByName(vnamestr);
             request.setAttribute("vendorlist", results);    
             
         }catch(Exception e){ log.error(e.getMessage().toString()); }
        return mapping.findForward("allvendors");
      }
      
      
    public ActionForward selectv(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {         
          HttpSession sess = request.getSession(); 
          if(checkSessionTimeout(sess))
            return mapping.findForward("timeout");
                  
         try{          
             String vendorid = request.getParameter("id");
                                    
             //get any existing vq records
             String grantnum = (String) sess.getAttribute("grantid");
             long grantid = Long.parseLong(grantnum);
             ArrayList allquotes = vdb.getVendorQuotesForGrant(grantid);
             
             //get info about selected vendor
             VendorBean vb = vdb.getVendor(Long.parseLong(vendorid));
             vb.setGrantid(grantid);    
             
             //add blank vq record to list
             allquotes.add(vb);         
             
             RevAssignCollectionBean rc = new RevAssignCollectionBean();
             rc.setAllVendorRecords(allquotes);
             request.setAttribute("AssignCollectionBean", rc);
             
             AppStatusBean asb = dbh.getApplicationStatus(grantid);
             request.setAttribute("appStatus", asb);
             
         }catch(Exception e){ log.error(e.getMessage().toString()); }
        return mapping.findForward("vendorquote");
      }
    
    
    public ActionForward vrecord(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {         
          HttpSession sess = request.getSession(); 
          if(checkSessionTimeout(sess))
            return mapping.findForward("timeout");
                  
         try{
             String id = request.getParameter("id");
             VendorBean v = vdb.getVendor(Long.parseLong(id));
             request.setAttribute("VendorBean", v);          
             
         }catch(Exception e){ log.error(e.getMessage().toString()); }
        return mapping.findForward("vrecord");
      }
    
  
    public ActionForward savevendor(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {         
          HttpSession sess = request.getSession(); 
          if(checkSessionTimeout(sess))
            return mapping.findForward("timeout");
             
         try{
             UserBean userb= (UserBean) sess.getAttribute("lduser");
             ActionForm myform = (ActionForm) request.getAttribute("VendorBean");
             VendorBean v = (VendorBean) myform;
             
             if(v.getId()==0)
                vdb.insertVendor(v, userb);
            else
                vdb.updateVendor(v, userb);            
                          
             //get new list of all vendors  
             ArrayList results = vdb.getAllVendors();
             request.setAttribute("vendorlist", results);
                                       
         }catch(Exception e){ log.error(e.getMessage().toString()); }
        return mapping.findForward("allvendors");
      }
      
      
    public ActionForward deletequote(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {         
          HttpSession sess = request.getSession(); 
          if(checkSessionTimeout(sess))
            return mapping.findForward("timeout");
                  
         try{             
             String quoteid = request.getParameter("id");
             vdb.deleteVendorQuote(Long.parseLong(quoteid));
             
             //get existing vq records
             String grantnum = (String) sess.getAttribute("grantid");
             long grantid = Long.parseLong(grantnum);
             ArrayList allquotes = vdb.getVendorQuotesForGrant(grantid);
             
             RevAssignCollectionBean rc = new RevAssignCollectionBean();
             rc.setAllVendorRecords(allquotes);
             request.setAttribute("AssignCollectionBean", rc);
             
             AppStatusBean asb = dbh.getApplicationStatus(grantid);
             request.setAttribute("appStatus", asb);
             
         }catch(Exception e){ log.error(e.getMessage().toString()); }
        return mapping.findForward("vendorquote");
      }
           
    
    public boolean checkSessionTimeout(HttpSession sess)
      {
        boolean timeout = false;
        //check for session timeout
        boolean userID = (sess.getAttribute("lduser") != null);
        if (!userID && sess.isNew())
        {      
          timeout = true;
        }      
        return timeout;
      }
    
    
}
