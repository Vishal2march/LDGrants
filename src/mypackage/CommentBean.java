/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  CommentBean.java
 * Creation/Modification History  :
 *
 * SH   4/1/07      Created
 *
 * Description
 * This class will store/retrieve info from the Admin_comments table including the
 * comment, the date and the admin that created the comment.  
 *****************************************************************************/
package mypackage;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CommentBean extends ActionForm
{
  public long id;
  public long grantid;
  public String comment;
  public String createdby;
  public String changereq;
  public Date commentdate;
  public String module;
  public String from;
  public String subject;
  public String cc;

  public CommentBean()
  {
  }

  public long getId()
  {
    return id;
  }

  public void setId(long id)
  {
    this.id = id;
  }

  public long getGrantid()
  {
    return grantid;
  }

  public void setGrantid(long grantid)
  {
    this.grantid = grantid;
  }

  public String getComment()
  {
    return comment;
  }

  public void setComment(String comment)
  {
    this.comment = comment;
  }

  public String getCreatedby()
  {
    return createdby;
  }

  public void setCreatedby(String createdby)
  {
    this.createdby = createdby;
  }

  public String getChangereq()
  {
    return changereq;
  }

  public void setChangereq(String changereq)
  {
    this.changereq = changereq;
  }

  public Date getCommentdate()
  {
    return commentdate;
  }

  public void setCommentdate(Date commentdate)
  {
    this.commentdate = commentdate;
  }

  public String getModule()
  {
    return module;
  }

  public void setModule(String module)
  {
    this.module = module;
  }
  
  
  public boolean isMissing(String value) 
  {
    return((value == null) || (value.trim().equals("")));
  }
  
  public ActionErrors validate(ActionMapping mapping,  HttpServletRequest request) 
  {  
    ActionErrors errors = new ActionErrors();
    if (isMissing(getComment())) 
    {
      errors.add("commentMissing", new ActionError("value.required", "Comment"));
    } 
    
    
        
    return(errors);
  }

  public String getFrom()
  {
    return from;
  }

  public void setFrom(String from)
  {
    this.from = from;
  }

  public String getSubject()
  {
    return subject;
  }

  public void setSubject(String subject)
  {
    this.subject = subject;
  }

  public String getCc()
  {
    return cc;
  }

  public void setCc(String cc)
  {
    this.cc = cc;
  }


  
  
}