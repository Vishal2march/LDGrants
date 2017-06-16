<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction Application</title>
  </head>
  <body>
  
    
  <h4>Public Library Construction Application - Checklist</h4>

  <table align="center" width="80%" border="1" class="graygrid" summary="for layout only" > 
  <tr>
      <td><b>Project Number:</b>&nbsp;
      03<fmt:formatNumber value="${thisGrant.fccode}" />
        -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
        -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" /></td>
  </tr>
  <tr>
     <td><b>Member Library:</b>&nbsp;<c:out value="${thisGrant.instName}" /></td>
  </tr>
  <tr>
     <td><b>Project Title:</b>&nbsp;<c:out value="${thisGrant.title}" /></td>
  </tr>
  
  <tr>
    <td height="30">&nbsp;</td>
  </tr>  
  <tr>
    <td align="center">
    Please use the following links to <i>print</i>
    or <i>save</i> the application to your desktop:<br/>
    
    <a href="PrintAppServlet?i=evalform" target="_blank">Evaluation & Reduced Match Form - HTML</a>
    &nbsp;&nbsp;&nbsp;&nbsp;  
    <a href="PrintAppServlet?i=evalformpdf" target="_blank">Evaluation & Reduced Match Form - PDF</a>
    <br/><br/>
    <a href="PrintAppServlet?i=cover" target="_blank">Application Form - HTML</a>
    &nbsp;&nbsp;&nbsp;&nbsp;  
    <a href="PrintAppServlet?i=coverpdf" target="_blank">Application Form - PDF</a>
    <br/><br/>
    <a href="PrintAppServlet?i=otherfund" target="_blank">Additional Funds - HTML</a>
    &nbsp;&nbsp;&nbsp;&nbsp;  
    <a href="PrintAppServlet?i=otherfundpdf" target="_blank">Additional Funds - PDF</a>
    <br/><br/>     
    <a href="PrintAppServlet?i=narr" target="_blank">Narratives - HTML</a>
    &nbsp;&nbsp;&nbsp;&nbsp;  
    <a href="PrintAppServlet?i=narrpdf" target="_blank" >Narratives - PDF</a>
    <br/><br/>
    <a href="PrintAppServlet?i=budget&a=true" target="_blank">Budget - HTML</a>
    &nbsp;&nbsp;&nbsp;&nbsp;     
    <a href="PrintAppServlet?i=budgetpdf&a=true" target="_blank">Budget - PDF</a>
    <br/><br/>   
    
    </td>
  </tr>
  
  
  <tr>
    <td height="30">&nbsp;</td>
   </tr>
   
  <tr>
    <th bgcolor="#7AC5CD">DASNY - Construction Grant Program Checklist</th> 
  </tr>
  <tr>
    <td><a href="cnDasnyNav.do?task=evalmatchform">Evaluation & Reduced Match Form</a></td>
  </tr>
  <tr>
    <td><a href="cnDasnyNav.do?task=coversheet">Application Form</a></td>
  </tr>
  <tr>
    <td><a href="cnDasnyNav.do?task=otherfunds">Additional Funding Sources</a></td>
  </tr>  
  <tr>
    <td><a href="cnDasnyNav.do?task=narrative">Project Narratives</a></td> 
  </tr>    
  <tr>
    <td><a href="cnDasnyNav.do?task=budget">Budget</a></td>
  </tr>
   <tr>
    <td><a href="cnDasnyNav.do?task=attachment">Attachments</a></td>
  </tr>    
  </table>
  <br/><br/>
  
  
  <%-- This is the approval section  --%>
  <html:form action="/saveDasnyChecklist" > 
      <table width="80%" align="center" class="boxtype" summary="for layout only">
        <tr>
          <td bgcolor="#7AC5CD" colspan="2"><b>Application Status</b></td>
        </tr>
        <tr>
          <td height="15" />
        </tr>
        <tr><td><html:checkbox property="initialappr" disabled="true"/>Application Approved by LD</td>
            </tr>
        
        <tr><td><html:checkbox property="dasnyapproved"/>Application Approved by DASNY</td>
            </tr>            
       <%-- <tr><td><html:checkbox property="bondcomplete"/>Bond Council Review Complete</td>
            </tr>--%>
        <tr>
          <td height="15" />
        </tr>
        <tr>
          <td colspan="2" align="center"><html:submit value="Save" /></td>
        </tr> 
      </table>
   </html:form>
  
   
  </body>
</html>