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
    <title>narrative</title>
    <script type="text/javascript" src="jscripts/tinymce/tinymce.js"></script>
    <script type="text/javascript">
    tinymce.init({
         selector: "textarea",
         menubar: false,
         toolbar: "bold italic underline | alignleft aligncenter alignright | bullist numlist outdent indent",
         statusbar: false,
         nowrap: false,
         width: 600
     });
  </script>
  </head>
  <body>
  
  

  <table width="100%" summary="for layout only">
    <tr>
      <td width="15%" valign="top">
        <b>Narrative:</b><br/>
        <a href="stateaidReadNarr.do?t=narr&m=staid&id=3">Description of Activities</a><br/> <br/>
        <a href="stateaidReadNarr.do?t=narr&m=staid&id=93">Budget Narrative</a><br/> 
      </td>
          
          
      <td>                       
       <table align="center" width="90%" class="boxtype" summary="for layout only">
        <html:form action="/saveAidNarrative" > 
          <tr><th>Narrative</th></tr>
          <tr>
            <td><bean:write name="projNarrative" property="narrativeDescr" filter="false" /></td>
          </tr>  
          <tr>
            <td><hr size="1" style="background-color:rgb(128,128,128);" /></td>
          </tr>  
                                               
          <c:choose >
          <c:when test="${lduser.prgNycStateaid=='read' || appStatus.projdescyn=='true' || appStatus.pendingReview=='true'}" >
            <tr>
              <td ><bean:write name="projNarrative" property="narrative" filter="false" /></td>
            </tr>
          </c:when>
          <c:otherwise > 
          <tr>
            <td align="center">         
              <html:textarea property="narrative" cols="80" rows="17" />
            </td>  
          </tr>
          <tr>
            <td align="center"><html:hidden property="id" /><html:hidden property="narrTypeID" />
            <html:hidden property="module" value="staid" />
            <html:submit value="Save" /></td>
          </tr>
          </c:otherwise>
          </c:choose>
                    
        </html:form>
       </table>             
  
  
    </td>
    </tr>
  </table>
  
  </body>
</html>