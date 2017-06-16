<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>    
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
  
   
<center>

 <html:form action="/cnSaveNarrative" >
 <table align="center" width="80%" class="boxtype" summary="for layout only">
    <tr>
      <th><c:out value="${projNarrative.narrativeTitle}" /></th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarrative" property="narrativeDescr" filter="false" /><br/></td>
    </tr>      
    <tr>
        <td><a href="constructionForms.do?i=attachment&m=cn">Add an Attachment</a></td>
    </tr> 
    <tr>
        <td>
        
    <c:choose >
    <c:when test="${lduser.prgconstruction=='read' || projNarrative.lockNarrative=='true'}" > 
        <bean:write name="projNarrative" property="narrative" filter="false" />
    </c:when>
    <c:otherwise >   
        <%--<div name="myToolBars" class="mceToolbarExternal"></div>--%>
        <html:textarea property="narrative" cols="80" rows="17" />                   
            <html:hidden property="id" /> 
            <html:hidden property="narrTypeID"/>
            <html:hidden property="module" value="cn" />
            <html:submit value="Save" />       
    </c:otherwise>
    </c:choose>    
        
        </td>
    </tr>    
    </table>
    </html:form>  
  </center> 
  
  </body>
</html>