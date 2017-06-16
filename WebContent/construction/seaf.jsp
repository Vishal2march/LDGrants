<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>SEAF for Construction</title>
  </head>
  <body>
  
  <h4>Environmental Assessment Forms (SEAF)</h4>
      
<table align="center" width="90%" class="boxtype" summary="for layout only">
    
    <tr>
        <td>
    More information regarding the Environmental Assessment Forms is available on the Division of Library
    Development's website.</td>
    </tr>
    <tr>
        <td height="30%"><br/></td>
    </tr>
    <tr>
    	<td>
    	<c:choose>
    	<c:when test="${thisGrant.fycode <17}">
    	
    	<b>$14 Million State Aid for Library Construction:</b> 
        <a href="http://www.nysl.nysed.gov/libdev/construc/14m/seafinfo.htm" target="_blank">Environmental Assessment Forms</a><br/> 
            
        </c:when>
        <c:otherwise>
          
        <b>$19 Million State Aid for Library Construction:</b>  
        <a href="http://www.nysl.nysed.gov/libdev/construc/19m/seafinfo.htm" target="_blank">Environmental Assessment Forms</a><br/> 
        <br/><br/>
        
        </c:otherwise>
        </c:choose>
        </td>
    </tr>        
    <tr>
        <td height="30%"> </td>
    </tr>
    
    <c:choose>
    <c:when test="${param.p!=null && param.p=='rev'}">
        <tr>
            <td>The SEAF should be scanned and electronically attached to the grant application.</td>
        </tr>
        <tr>
            <td height="20%"> </td>
        </tr>
        <tr>
            <td>A digital signature is not required. Documents can be attached in MS Word (.doc), Excel, PDF, .txt, .bmp, .jpg formats.</td>
        </tr>
    </c:when>
    <c:otherwise>    
        <tr>
            <td>The SEAF should be scanned and electronically 
            <a href="constructionForms.do?i=attachment&m=cn">attached</a>
            to the grant application.  <br/><br/>
            A digital signature is not required.
            Documents can be attached in MS Word (.doc), Excel, PDF, .txt, .bmp, .jpg formats. 
            <%--Please do not attach MS Word 2007 or 2010 (.docx) files; they must be
            converted to MS Word 2003 (.doc) format.--%></td>
        </tr>    
    </c:otherwise>
    </c:choose>
    
</table>
 
  
  </body>
</html>