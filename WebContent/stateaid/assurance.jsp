<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>assurance</title>
  </head>
  <body>
  
  <h3>Assurance</h3>
  
    
  <c:choose >
  <c:when test="${lduser.prgNycStateaid=='read' || appStatus.instauthyn=='true' || appStatus.pendingReview=='true'}" >
     
     
     <table align="center" width="80%" class="boxtype" summary="for layout only"  >
        <tr>
          <td colspan="2"><b>Assurance:</b>  The library assures that the "Proposed State Aid Application and Budget 
             Narrative" were reviewed and approved by the library's board of trustees.</td>
        </tr>
        <tr>
          <td height="40%"> </td>
        </tr>
        <tr>
           <td>Date State Aid Application approved by library board of trustees:</td>
           <td><c:out value="${authorizationBean.authdate}"/></td>
        </tr>
        <tr>
          <td>Name of person completing form:</td>
          <td><c:out value="${authorizationBean.name}"/> </td>
        </tr>
        <tr>
          <td>Title of person completing form:</td>
          <td><c:out value="${authorizationBean.title}"/></td>
        </tr>                   
        <tr>
          <td align="center" colspan="2"><input type="BUTTON" value="Save" disabled="disabled" /> </td>
        </tr>   
      </table>
          
     
  </c:when>
  <c:otherwise >
     
     <html:errors/>
      <html:form action="/saveAidAssurance" >      
      <table align="center" width="80%" class="boxtype" summary="for layout only"  >
        <tr >
          <td colspan="2"><b>Assurance:</b>  The library assures that the "Proposed State Aid Application and Budget 
             Narrative" were reviewed and approved by the library's board of trustees.</td>
        </tr>
        <tr>
          <td height="40%"> </td>
        </tr>
        <tr>
           <td>Date State Aid Application approved by library board of trustees:</td>
           <td><html:text property="authdateStr"/> (mm/dd/yyyy)</td>
        </tr>
        <tr>
          <td>Name of person completing form:</td>
          <td><html:text property="name"/> </td>
        </tr>
        <tr>
          <td>Title of person completing form:</td>
          <td><html:text property="title"/></td>
        </tr>              
        <tr>
          <td align="center" colspan="2"><html:submit value="Save" />
                                          <html:hidden property="version" value="Assurance"/>
                                          <html:hidden property="id"/>
                                          <html:hidden property="grantid"/></td>
        </tr>
      </table>
      </html:form>
      
  </c:otherwise>
  </c:choose>    
   
  
  
  </body>
</html>