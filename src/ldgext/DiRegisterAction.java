package ldgext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DiRegisterAction extends Action
{
  public ActionForward execute(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
        try{
            RegisterBean rb = (RegisterBean) form;            
            RegistrationMailBean rmb = new RegistrationMailBean();
                        
            boolean msgSent = rmb.sendRegistrationMail(rb);  
            
            boolean islgrmif=false;
            if(rb.getGrantprogram()!=null && rb.getGrantprogram().equals("lgrmif"))
                islgrmif=true;
            
            request.setAttribute("islgrmif", new Boolean(islgrmif));
            
        } catch(Exception e){
          System.err.println("error DiRegisterAction "+e.getMessage().toString());
          return mapping.findForward("error");
        }                
        return mapping.findForward("success");
    }
          
}