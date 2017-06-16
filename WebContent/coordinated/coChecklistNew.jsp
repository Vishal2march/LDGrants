<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
  
  
    <table width="80%" align="center" summary="for layout only" class="boxtype">
      <tr>
        <th>Project Number</th>
        <th>03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
                -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
                -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" />
        </th>
      </tr>
      <tr>  
        <th>Contract Number</th>
        <th><c:out value="${thisGrant.contractNum}" /></th>
      </tr>
    </table>
    <br/>
    
     
    <table width="80%" align="center" border="1" summary="for layout only" class="graygrid" >
      <html:form action="/saveCoChecklist">
        <tr>
            <th>Initial Application Checklist</th>
        </tr>
        <tr>                          
          <td><html:checkbox property="coversheetComp"/>
            <a href="coApplicantForms.do?item=coversheet&m=co" >Cover Sheet</a></td>      
        </tr>
                    
        <tr>             
          <td><html:checkbox property="projdescComp"/>
            <a href="coApplicantForms.do?item=narrative&m=co" >Project Narratives</a></td>
        </tr>            
        
        <tr>              
          <td><html:checkbox property="budgetComp"/>
            <a href="coApplicantForms.do?item=budget&m=co" >Project Budget</a></td>
        </tr>            
                   
        <tr>
          <td><html:checkbox property="fs20Comp"/>
            <a href="CoSelectFs.do">3 Copies of the FS 10</a> (must be completed and mailed)</td>
        </tr>
        
        <tr>        
          <td align="center">
          <c:choose >
          <c:when test="${appStatus.allowSubmitInitial=='false' || lduser.prgco=='read' || appStatus.dateAcceptable=='false'}">
              <input type="button" value="Save Progress" disabled="disabled" />
          </c:when>
          <c:otherwise >  
              <html:hidden property="grantid" />
              <html:hidden property="applicationType" value="initial" />
              <html:submit value="Save Progress" />
          </c:otherwise>
          </c:choose> 
          </td>
        </tr>             
                            
        <tr>             
           <td><html:checkbox property="authComp"/>
             <a href="ApcntFunctionsServlet?i=instauth" >Institutional Authorization</a></td>
        </tr>    
      </html:form>          
        
      <form action="coSubmitApp.do?i=verifyinitial" method="POST">
      <tr >
        <td align="center">
          <c:choose >
          <c:when  test="${appStatus.allowSubmitInitial=='false' || lduser.prgco!='submit' || appStatus.dateAcceptable=='false'}">
            <input type="button" value="Submit" disabled="disabled" />
          </c:when>
          <c:otherwise >   
            <input type="HIDDEN" name="fund" value="7" />
            <input type="HIDDEN" name="id" value='<c:out value="${thisGrant.grantid}" />' />
            <input type="submit" value="Submit" name="btn" />
          </c:otherwise>
          </c:choose>             
        </td>
      </tr>
      <tr>
        <td>Due Date for new applications: 
        <fmt:formatDate value="${appStatus.dueDate}" pattern="MM/dd/yyyy" /></td>
      </tr>
    </form>
    </table> 
    <br/><br/><br/>
        
   
   <table width="80%" align="center" class="graygrid" border="1" summary="for layout only">
      <tr>
        <th>FS-10-A Budget Amendments (Optional)<br/>Only if there is an amendment
        to the approved project budget</th>
      </tr>      
      <tr>                          
        <td><a href="coApplicantForms.do?item=fs10a&m=co">Budget Amendments</a></td>  
      </tr>    
      <tr>
        <td><a href="FsFormServlet?i=fs10a" target="_blank">FS-10-A Form HTML</a><br/>
            <a href="FsFormServlet?i=fs10apdf" target="_blank">FS-10-A Form PDF</a>
            (3 copies signed in blue ink and mailed)</td>
      </tr>
        
      <form action="CoConfirmSubmit.do?todo=amend" method="POST">              
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
    <br/><br/><br/>
    
      
    <table width="80%" border="1" align="center" summary="for layout only" class="graygrid">
    <html:form action="/saveCoChecklist">
      <tr>
        <th>Final Report Checklist</th>
      </tr>
      <tr>             
        <td><html:checkbox property="finalnarrativeComp"/>
          <a href="coApplicantForms.do?item=finalrpt&m=co" >Final Report Narrative</a></td>
      </tr>
      
      <tr>                          
        <td ><html:checkbox property="finalbudgetComp"/>
          <a href="coApplicantForms.do?item=budget&m=co" >Project Budget</a> (Expenses Submitted)</td>       
      </tr>              
                       
      <tr>
        <td><html:checkbox property="fs10fComp"/>
          <a href="CoSelectFs.do">3 Copies of the FS-10-F</a> (must be completed and mailed)</td>
      </tr>
 
 
    <tr>            
      <td align="center">
      <c:choose >
      <c:when test="${appStatus.allowSubmitFinal=='false' || lduser.prgco=='read'}">
          <input type="button" value="Save Progress" disabled="disabled" />
      </c:when>
      <c:otherwise >    
          <html:hidden property="grantid" />
          <html:hidden property="applicationType" value="final" />
          <html:submit value="Save Progress"/>
      </c:otherwise>
      </c:choose>     
      </td>
    </tr>             
      
    <tr>                            
        <td><html:checkbox property="signoffComp"/>
          <a href="coApplicantForms.do?item=finalsignoff" >Final Report Sign-off</a></td>
    </tr>
   </html:form>
   
        
    <form action="CoConfirmSubmit.do?todo=final" method="POST">
      <tr>
        <td align="center">
          <c:choose >
          <c:when test="${appStatus.allowSubmitFinal=='false' || lduser.prgco!='submit'}">
            <input type="BUTTON" value="Submit" disabled="disabled" />
          </c:when>
          <c:otherwise >
            <input type="submit" value="Submit" name="btn" />
          </c:otherwise>
          </c:choose>
        </td>
      </tr>
      <tr>
        <td>Due Date for final reports: 
        <fmt:formatDate value="${appStatus.finaldueDate}" pattern="MM/dd/yyyy" /></td>
      </tr>
    </form>
    </table>            
    <br/><br/>
    
    
        
    <table width="80%" align="center" summary="for layout only" class="boxtype">
      <tr >
        <td align="center" colspan="2">
          <a href="coApplicantForms.do?item=appstatus">View Submissions/Approvals</a><br/>
          <a href="coApplicantForms.do?item=auth">View Authorizations</a><br/>
          <a href="coApplicantForms.do?item=comments">View Reviewer Comments/Scores</a></td>
      </tr>
      <tr>
        <td colspan="2" align="center">The following links open in a new window</td>
      </tr>
      <tr>
        <td align="center"><a href="PrintAppServlet?i=cover" target="_blank">View CoverSheet - HTML</a><br/>
        <a href="PrintAppServlet?i=narr" target="_blank">View Narratives - HTML</a><br/>
        <a href="PrintAppServlet?i=budget&a=false" target="_blank">View Budget - HTML</a></td>
     
        <td align="center"><a href="PrintAppServlet?i=coverpdf" target="_blank" >View CoverSheet - PDF</a><br/>
        <a href="PrintAppServlet?i=narrpdf" target="_blank" >View Narratives - PDF</a><br/>
        <a href="PrintAppServlet?i=budgetpdf&a=false" target="_blank">View Budget - PDF</a></td>
      </tr>    
      <tr>
        <td align="center" colspan="2">
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
