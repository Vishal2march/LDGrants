<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
    
  
  <h4>Checklist</h4>
  
  <c:if test="${appStatus.dateAcceptable=='false'}">
    <font color="Red">Warning:  The due date (<fmt:formatDate value="${appStatus.dueDate}" pattern="MM/dd/yyyy" />)
    for this application has expired. You may not submit a new application for this fiscal year.</font>
  </c:if>
  <br/>
    
      
    <table width="80%" align="center" border="1" class="graygrid" summary="for layout only">
      <tr>
        <th colspan="2">Application Checklist<br/>
        Due Date for new applications: 
        <fmt:formatDate value="${appStatus.dueDate}" pattern="MM/dd/yyyy" /></th>
      </tr>
      
      <html:form action="/saveSaChecklist">
        <tr>              
          <td>
            <html:checkbox property="coversheetComp" />
            <a href="saApplicantForms.do?item=coversheet&m=sa" >Cover Sheet</a></td>
        </tr>            
                
        <tr>             
          <td>
            <html:checkbox property="projdescComp" />
            <a href="saApplicantForms.do?item=narrative&m=sa" >Project Description</a>
          </td>
        </tr>        
        
        <tr>            
          <td>
            <html:checkbox property="budgetComp" />
            <a href="saApplicantForms.do?item=budget&m=sa" >Project Budget</a>
          </td>
        </tr>            
                   
        <tr>
          <td >
            <html:checkbox property="fs20Comp" />
            <a href="FsFormServlet?i=fs20" target="_blank">FS-10 Form HTML</a> <br/>&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;<a href="FsFormServlet?i=fs20pdf" target="_blank">FS-10 Form PDF</a><br/>
            Signed copies of the FS-10 form are no longer required.  They will be created in Library Development and 
            used as an internal document only.
            </td>
        </tr>
        
        <tr>              
          <c:choose >
          <c:when test="${appStatus.allowSubmitInitial=='false' || lduser.prgsa=='read' || appStatus.dateAcceptable=='false'}">
          <td align="center">
            <input type="button" value="Save Progress" disabled="disabled" />
          </td>
          </c:when>
          <c:otherwise >  
          <td align="center">
            <html:hidden property="grantid" />
            <html:hidden property="applicationType" value="initial" />
            <html:submit value="Save Progress"/>            
          </td>
          </c:otherwise>
          </c:choose>          
        </tr>                         
            
        <tr>             
           <td >
            <html:checkbox property="authComp"/>
            <a href="ApcntFunctionsServlet?i=instauth" >Institutional Authorization</a>
           </td>
        </tr>    
      </html:form>      
      
      <form action="ApcntSubmit.do?todo=initial" method="POST">
        <tr >
          <td align="center">
            <c:choose >
            <c:when  test="${appStatus.allowSubmitInitial=='false' || lduser.prgsa!='submit' || appStatus.dateAcceptable=='false'}">
              <input type="button" value="Submit" disabled="disabled" />
            </c:when>
            <c:otherwise >                
              <input type="submit" value="Submit" name="initial" />
            </c:otherwise>
            </c:choose>                
          </td>
        </tr>
      </form>
    </table> 
    <br/><br/><br/><br/>
    
        
    <table width="80%" align="center" class="graygrid" border="1" summary="for layout only">
      <tr>
        <th>FS-10-A Budget Amendments (Optional)</th>
      </tr>      
      <tr>                          
        <td><a href="saApplicantForms.do?item=fs10a&m=sa" >FS-10-A Budget Amendments</a></td>  
      </tr>    
        
      <form action="ApcntSubmit.do?todo=amend" method="POST">              
      <tr>
        <td align="center">
          <c:choose >
          <c:when test="${appStatus.allowSubmitFinal=='false' || appStatus.fs10aComp=='true'}">
            <input type="BUTTON" value="Submit" disabled="disabled" />
          </c:when>
          <c:otherwise >
            <input type="submit" value="Submit" name="amend" />
          </c:otherwise>
          </c:choose>                
        </td>
      </tr>
     </form>
      
    </table>
    <br/><br/><br/><br/>
        
        
  
    <table width="80%" align="center" class="graygrid" border="1" summary="for layout only">
      <tr>
        <th>Final Report Checklist<br/>
        Due Date for final reports: 
        <fmt:formatDate value="${appStatus.finaldueDate}" pattern="MM/dd/yyyy" /></th>
      </tr>
      
      <html:form action="/saveSaChecklist" >
        <tr>             
          <td>
            <html:checkbox property="finalnarrativeComp"/>
            <a href="saApplicantForms.do?item=finalrpt&m=sa" >Final Report Narrative</a>
          </td>
        </tr>
        
        <tr>                          
          <td>
            <html:checkbox property="finalbudgetComp"/> 
            <a href="saApplicantForms.do?item=budget&m=sa" >Project Budget</a> (Expenses Submitted)
          </td>  
        </tr>                              
        
      <tr>            
        <c:choose >
        <c:when test="${appStatus.allowSubmitFinal=='false' || lduser.prgsa=='read'}">
          <td align="center">
            <input type="button" value="Save Progress" disabled="disabled" />
          </td>
        </c:when>
        <c:otherwise >    
          <td align="center">
            <html:hidden property="grantid" />
            <html:hidden property="applicationType" value="final" />
            <html:submit value="Save Progress" />
          </td>
        </c:otherwise>
        </c:choose>     
      </tr>             
      
        
      <tr>                            
        <td>
          <html:checkbox property="signoffComp"/>
          <a href="saApplicantForms.do?item=finalsignoff" >Final Report Sign-off</a>
        </td>
      </tr>
    </html:form>
    
    
    <form action="ApcntSubmit.do?todo=final" method="POST">              
        <tr>
          <td align="center">
            <c:choose >
            <c:when test="${appStatus.allowSubmitFinal=='false' || lduser.prgsa!='submit'}">
              <input type="BUTTON" value="Submit" disabled="disabled" />
            </c:when>
            <c:otherwise >
              <input type="submit" value="Submit" name="final" />
            </c:otherwise>
            </c:choose>                
          </td>
        </tr>
      </form>
    </table>        
    
    <br/><br/><br/><br/>
    
  
    <table width="80%" align="center" class="boxtype" summary="for layout only">
      <tr>
        <td align="center">
          <a href="saApplicantForms.do?item=appstatus">View Application Submissions/Approvals</a><br/>
          <a href="saApplicantForms.do?item=auth">View Authorizations</a><br/><br/>
          <a href="PrintAppServlet?i=app&a=false" target="_blank">View Application - HTML</a> 
             (opens in new window)<br/><br/>
          <a href="PrintAppServlet?i=apppdf&a=false" target="_blank" >View Application - PDF</a> 
             (opens in new window)<br/><br/>
        </td>
      </tr>
      <tr align="center">            
        <td >Before completing the application please carefully read the guidelines and instructions provided below:
          <br/><a href="docs/guideStatutory.pdf" target="_blank">Guidelines &amp; Instructions</a> (PDF)
        </td>
      </tr>
      <tr>
        <td align="center">
            <b>Mail written correspondence to:</b>
            <br/>Conservation/Preservation Program
            <BR/>Division of Library Development
            <BR/>New York State Library
            <BR/>10B41 Cultural Education Center
            <br/>Albany, New York 12230
            <br/>(518) 474-7890 
        </td>
      </tr>          
    </table>
  
  
  
  
  </body>
</html>
