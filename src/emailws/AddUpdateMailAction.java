package emailws;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.xml.ws.WebServiceRef;

import mypackage.EmailHelpBean;
import mypackage.UserBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import sedems.EmailWebService;
import sedems.EmailWebService_Service;
import sedems.MessageVarSend;
import sedems.SEDEMSEZClass;
import sedems.WorkingTemplateQuery;
import sedems.WorkingTemplateSend;

public class AddUpdateMailAction extends Action
{
  public ActionForward execute(ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response) throws Exception
    {        
      HttpSession sess = request.getSession();      
      
      try{              
        boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
        if (!userID && sess.isNew())         
          mapping.findForward("timeout");
          
        EmailHelpBean ehb = (EmailHelpBean) form;
        String emsVars = "";
    
        if(ehb.isManagerName())
          emsVars+= "$$managerName";          
        if(ehb.isProjectNum()){
          emsVars+= ",$$projectNum";
          emsVars+=",$$grantId";
        }
        if(ehb.isAmtapproved())
            emsVars+= ",$$amtApproved";
        if(ehb.isProgramName())
            emsVars+=",$$program";
            
        if(emsVars.equals("")){
            emsVars+= "$$grantId";//default
        }
          
        UserBean userb = (UserBean) sess.getAttribute("lduser");
        SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());
                      
        int id=0;
        if(ehb.getWtid()==0)
          id=ez.draftTemplateAndVars(ehb.getFrom(), "", ehb.getSubject(),ehb.getMessage(), emsVars);
        else
        {
          //do update wt : DOES NOT UPDATE MVARS
          id=ez.updateEmail(ehb.getFrom(), "", ehb.getMessage(), ehb.getSubject(), ehb.getWtid());
          //the following custom call used to update mvars until sedems moved to 11g; call no longer works
          //id = handleUpdateEmail(ehb, userb);
        }
                  
        //get all unsent mail and display
        WorkingTemplateQuery[] wtq=ez.listUnsentMail();
        Collections.sort(Arrays.asList(wtq), WorkingTemplateQuery.DateCreatedComparator );
        request.setAttribute("wtBeans", wtq);  
        
      }catch(Exception e){
        System.out.println("error AddUpdateMailAction "+e.getMessage().toString());
      }      
      return mapping.findForward("success");      
    }
    
    
    
  //declaration added 1/7/13 for sedems in 11g  
  @WebServiceRef
  private static EmailWebService_Service emailWebService_Service;
  
    
    public int handleUpdateEmail(EmailHelpBean eb, UserBean userb )
    {
      Integer wtid = new Integer(0);
    System.err.println("inside handleupdateemail");
      try{      
          WorkingTemplateSend wt;
          wt=new WorkingTemplateSend();
          wt.setApplicationId(userb.getAuthBean().getApplicationId());
          wt.setCreatedby(userb.getUserid());
          wt.setModifiedby(userb.getUserid());
          wt.setFromEmail(eb.getFrom());
          wt.setMessage(eb.getMessage());
          wt.setSubject(eb.getSubject());
          wt.setSummaryEmail("");
          wt.setId(eb.getWtid());
          
          //the following code to add MV is not in ez class method
          Vector allvars = new Vector();
          if(eb.isManagerName() && eb.getManagerNameId()==0)
          {
            MessageVarSend mv = new MessageVarSend();
            mv.setName("$$managerName");  
            allvars.add(mv);
          }          
          if(eb.isProjectNum() && eb.getProjectNumId()==0)
          {
            MessageVarSend mv = new MessageVarSend();
            mv.setName("$$projectNum");  
            allvars.add(mv);
          }
          if(eb.isGrantNum() && eb.getGrantId()==0)
          {
            MessageVarSend mv = new MessageVarSend();
            mv.setName("$$grantId");  
            allvars.add(mv);
          }
          if(eb.isProgramName() && eb.getProgramId()==0)
          {
            MessageVarSend mv = new MessageVarSend();
            mv.setName("$$program");  
            allvars.add(mv);
          }
          if(eb.isAmtapproved() && eb.getAmtapprovedId()==0)
          {
            MessageVarSend mv = new MessageVarSend();
            mv.setName("$$amtApproved");  
            allvars.add(mv);
          }
          MessageVarSend[] mvs = (MessageVarSend[]) allvars.toArray(new MessageVarSend[0]);
          wt.setMessagevariables(mvs);
          
          //----------------------------------------------------------
          
          //1/7/13 stub no longer used in 11g, now use proxy file
          //EmailWebServiceStub stub=new EmailWebServiceStub(userb.getAuthBean().getProviderURL()); 
          System.out.println("creating ews");
          emailWebService_Service = new EmailWebService_Service();
           
          System.out.println("getting port");
          EmailWebService emailWebService = emailWebService_Service.getEmailWebServicePort();
               
          wtid =emailWebService.addOrUpdateWorkingTemplate(wt, userb.getAuthBean());        
        
      }catch(Exception e){
        System.out.println("error AddUpdateMailAction "+e.getMessage().toString());
      }  
      return wtid.intValue();
    }
}