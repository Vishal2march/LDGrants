<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction Application</title>    
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

 <html:errors/>
 <html:form action="/cnSaveAdminNarr" >
 <table align="center" width="80%" class="boxtype" summary="for layout only">
    <tr>
      <th><c:out value="${projNarrative.narrativeTitle}" /></th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarrative" property="narrativeDescr" filter="false" /><br/></td>
    </tr>      
    <tr>
        <td>   
           <%-- <div name="myToolBars" class="mceToolbarExternal"></div>--%>
            <html:textarea property="narrative" cols="80" rows="17" />                   
                <html:hidden property="id" /> 
                <html:hidden property="narrTypeID"/>
                <html:hidden property="module" value="admncn" />
                <html:submit value="Save" />             
        </td>
    </tr>    
 </table>
 </html:form>  
 
 <p>Back to application 
        <c:url var="checklistURL" value="cnAdminNav.do">
            <c:param name="item" value="grant"/> 
            <c:param name="id" value="${grantid}"/>
        </c:url>
    <a href='<c:out value="${checklistURL}"/>'>checklist</a>
  </p>
  
 </center> 
   
  </body>
</html>