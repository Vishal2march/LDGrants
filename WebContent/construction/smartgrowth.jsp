<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Smart Growth Form for Construction</title>
  </head>
  <body>
  
  <h4>Smart Growth Form</h4>
  
  <table align="center" width="90%" class="boxtype" summary="for layout only">
  <tr>
    <td>In accordance with the Smart Growth Public Infrastructure Policy Act, Environmental
    Conservation Law (Article 6 § 1-11), the applicant attests that the Smart Growth 
    Impact for the proposed building project application has been assessed.</td>
  </tr>
  <tr>
    <td height="20"></td>
  </tr>
  
  <tr>
    <td>
    <c:choose>
    <c:when test="${thisGrant.fycode <17}">
    
    	<b>$14 Million State Aid for Library Construction:</b><br/> 
        <a href="http://www.nysl.nysed.gov/libdev/construc/14m/smartgrowth.doc" target="_blank">Smart Growth Form MS Word</a><br/> 
        <a href="http://www.nysl.nysed.gov/libdev/construc/14m/smartgrowth.pdf" target="_blank">Smart Growth Form PDF</a> <br/>
        <a href="http://www.nysl.nysed.gov/libdev/construc/14m/smartgrowth.htm" target="_blank">
    	More information</a> regarding the Smart Growth Form is available on the Division of Library
    	Development's website.
    	
    	</c:when>
    	<c:otherwise>
    
        <b>$19 Million State Aid for Library Construction:</b><br/> 
        <a href="http://www.nysl.nysed.gov/libdev/construc/19m/smartgrowth.doc" target="_blank">Smart Growth Form MS Word</a><br/> 
        <a href="http://www.nysl.nysed.gov/libdev/construc/19m/smartgrowth.pdf" target="_blank">Smart Growth Form PDF</a> <br/>
        <a href="http://www.nysl.nysed.gov/libdev/construc/19m/smartgrowth.htm" target="_blank">
	    More information</a> regarding the Smart Growth Form is available on the Division of Library
	    Development's website.
	    
	    </c:otherwise>
	    </c:choose>
        </td>
  </tr>
  <tr>
    <td height="20"></td>
  </tr>
  <tr>
    <td>When completing the Smart Growth Form, "District" could be the applicable School District or Library System, 
    and "Firm" could be the applicable architectural firm or N/A.</td>
  </tr>
  <tr>
    <td height="20"></td>
  </tr>
  
 <c:choose>
 <c:when test="${param.p!=null && param.p=='rev'}">
    <tr>
        <td>The Smart Growth Form can be printed, signed,
        scanned and electronically attached to the grant application.</td>
    </tr>
 </c:when>
 <c:otherwise>
      <tr>
        <td>The Smart Growth Form should be printed, signed,
        scanned and electronically 
        <a href="constructionForms.do?i=attachment&m=cn">attached</a> to your grant application.
        <br/><br/>
        A digital signature is not required.
        Documents can be attached in MS Word (.doc), Excel, PDF, .txt, .bmp, .jpg formats. 
        <%--Please do not attach MS Word 2007 or 2010 (.docx) files; they must be
        converted to MS Word 2003 (.doc) format.--%></td>
      </tr>
  </c:otherwise>
  </c:choose>
  
  <tr>
    <td height="20"></td>
  </tr>
  <tr>
    <td>The original form with original signatures must be kept on file, and can be requested at any point in the
    future, should the need arise.</td>
  </tr>
  </table>
  <br><br>
    
  
  </body>
</html>