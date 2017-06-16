<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>checklist</title>
  </head>
  <body>
  
  <h3>Checklist</h3>
  
  
  <c:if test="${appStatus.dateAcceptable=='false'}">
    <font color="Red">Warning:  The due date (<fmt:formatDate value="${appStatus.dueDate}" pattern="MM/dd/yyyy" />)
    for this application has expired. You may not submit a new application for this fiscal year.</font>
  </c:if>
  <br/>
    
      
    <table width="60%" align="center" border="1" class="graygrid" summary="for layout only">
      <tr>
        <th colspan="2">Application Checklist<br/>
        Due Date for applications: 
        <fmt:formatDate value="${appStatus.dueDate}" pattern="MM/dd/yyyy" /></th>
      </tr>
      
        <tr>              
          <td><a href="stateaidForms.do?i=coversheet&m=staid" >Cover Sheet</a></td>
        </tr>            
                
        <tr>             
          <td><a href="stateaidForms.do?i=narrative&m=staid" >Narrative</a></td>
        </tr>        
        
        <tr>            
          <td><a href="stateaidForms.do?i=budget&m=staid" >Project Budget</a> </td>
        </tr> 
        
        <tr>            
          <td><a href="stateaidForms.do?i=assurance&m=staid" >Assurance</a> </td>
        </tr> 
        
        <tr>            
          <td><a href="stateaidForms.do?i=attachment&m=staid" >Attachments</a> </td>
        </tr> 
                   
        <tr>
          <td> <a href="FsFormServlet?i=fs10&fy=0" target="_blank">FS-10 Form HTML</a> <br/>
                <a href="FsFormServlet?i=fs10pdf&fy=0" target="_blank">FS-10 Form PDF</a> <br/>
                (3 original FS-10 forms must be signed and mailed)
            </td>
        </tr>
        
      
      <form action="staidSubmitApp.do?i=verifyinitial" method="POST">
        <tr >
          <td align="center">
            <c:choose >
            <c:when  test="${appStatus.allowSubmitInitial=='false' || lduser.prgNycStateaid!='submit' || appStatus.dateAcceptable=='false'}">
              <input type="button" value="Submit" disabled="disabled" />
            </c:when>
            <c:otherwise >                
              <input type="HIDDEN" name="fund" value="20" />
              <input type="HIDDEN" name="id" value='<c:out value="${thisGrant.grantid}" />' />
              <input type="submit" value="Submit" name="initial" />
            </c:otherwise>
            </c:choose>                
          </td>
        </tr>
      </form>
    </table> 
    <br/><br/><br/><br/>
    
    
    
    
    
    
    <table width="60%" align="center" class="graygrid" border="1" summary="for layout only">
      <tr>
        <th>Final Report Checklist<br/>
        Due Date for final reports: 
        <fmt:formatDate value="${appStatus.finaldueDate}" pattern="MM/dd/yyyy" /></th>
      </tr>
      
        <tr>             
          <td><a href="stateaidForms.do?i=finalrpt&m=staid" >Final Report Narrative</a>
          </td>
        </tr>
        
        <tr>                          
          <td><a href="stateaidForms.do?i=budget&m=staid" >Project Budget</a> (Expenses Submitted)
          </td>  
        </tr>      
        
        <tr><td><a href="FsFormServlet?i=fs10a" target="_blank">FS-10-A Form HTML</a><br/> 
                <a href="FsFormServlet?i=fs10apdf" target="_blank">FS-10-A Form PDF</a><br/> 
                  (3 original Forms must be completed and mailed only if there is an amendment
                  to your original approved budget)</td>
         </tr>
        <tr><td><a href="FsFormServlet?i=fs10f&fyf=0" target="_blank">FS-10-F Form HTML</a> <br/>
                <a href="FsFormServlet?i=fs10fpdf&fyf=0" target="_blank">FS-10-F Form PDF</a><br/>
                  (3 original FS-10-F Forms must be signed and mailed)</td>
         </tr>
                      
          
    <form action="staidSubmitApp.do?i=verifyfinal" method="POST">              
        <tr>
          <td align="center">
            <c:choose >
            <c:when test="${appStatus.allowSubmitFinal=='false' || lduser.prgNycStateaid!='submit'}">
              <input type="BUTTON" value="Submit" disabled="disabled" />
            </c:when>
            <c:otherwise >
              <input type="HIDDEN" name="fund" value="20" />
              <input type="HIDDEN" name="id" value='<c:out value="${thisGrant.grantid}" />' />
              <input type="submit" value="Submit" name="final" />
            </c:otherwise>
            </c:choose>                
          </td>
        </tr>
      </form>
    </table>        
    
    <br/><br/><br/><br/>
    
  
    <table width="60%" align="center" class="boxtype" summary="for layout only">
      <tr>
        <td align="center">
          <a href="stateaidForms.do?i=appstatus">View Application Submissions/Approvals</a><br/>
          <a href="PrintAppServlet?i=app&a=false" target="_blank">View Application - HTML</a> 
             (opens in new window)<br/><br/>
          <a href="PrintAppServlet?i=apppdf&a=false" target="_blank" >View Application - PDF</a> 
             (opens in new window)<br/><br/>
        </td>
      </tr>
      
      <tr>
        <td align="center">
            <b>Mail written correspondence to:</b>
            <BR/>Division of Library Development
            <BR/>New York State Library
            <BR/>10B41 Cultural Education Center
            <br/>Albany, New York 12230
            <br/>Attn: Kerry Lynch
            <br/>(518) 474-7890 
        </td>
      </tr>          
    </table>
  
  </body>
</html>