<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Assurances for Construction</title>
  </head>
  <body >
    
  <h4> Assurances</h4>
  
  
  <table align="center" width="90%" class="boxtype" summary="for layout only">
  <tr>
    <td>The following assurances must be completed as part of the application process.  
      All questions must be answered.  Applicant must indicate compliance with the 
      Assurances by checking all appropriate boxes.  The first three assurances apply to 
      all libraries.  The fourth applies to any library that does not own its
      building with the exception of those libraries whose buildings are owned by 
      a school district or BOCES.  The President of the Library/System Board of 
      Trustees must sign the Authentication of Application.<br/><br/>
    
    <c:choose>
    <c:when test="${param.p!=null && param.p=='rev'}">
        The form should be printed, signed, scanned and electronically attached to the grant application.
    </c:when>
    <c:otherwise>
        The form should be printed, signed, scanned and electronically 
        <a href="constructionForms.do?i=attachment&m=cn">attached</a> to your grant application. 
        <br/><br/>
        A digital signature is not required. 
        Documents can be attached in MS Word (.doc), Excel, PDF, .txt, .bmp, .jpg formats. 
        <%--Please do not attach MS Word 2007 or 2010 (.docx) files; they must be
        converted to MS Word 2003 (.doc) format.--%><br/><br/>
    </c:otherwise>
    </c:choose>
    
    The original form with original signatures must be kept on file, and can be requested at any point in the
    future, should the need arise. </td>
  </tr>
  
  <tr>
    	<td><br/><br/>
    	
    	<c:choose>
    	<c:when test="${thisGrant.fycode <17}">
    
    	<b>$14 Million State Aid for Library Construction:</b> 
        <a href="http://www.nysl.nysed.gov/libdev/construc/14m/assurances.pdf" target="_blank">Assurances</a> (PDF format)<br/> 
            
        </c:when>
        <c:otherwise>
       
        <b>$19 Million State Aid for Library Construction:</b>  
        <a href="http://www.nysl.nysed.gov/libdev/construc/19m/assurances.pdf" target="_blank">Assurances</a> (PDF format)<br/> 
        <br/><br/>
        
        </c:otherwise>
        </c:choose>
        </td>
    </tr>        
  </table>
    
  </body>
</html>