<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
    <script language="javascript" type="text/javascript" src="jscripts/tinymce/tinymce.js"></script>
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
  
  <hr/>
  <br/><br/>
  <html:form action="/saveLgBgtNarrative">         
      <table width="95%" align="center" class="boxtype" summary="for layout only" >
        <tr>
          <th><c:out value="${projNarrative.narrativeTitle}" /></th>
        </tr>
        <tr>
          <td><bean:write name="projNarrative" property="narrativeDescr" filter="false" /><br/></td>
        </tr>      
        <tr>
          <td height="20" />
        </tr>        
         
         
        <c:choose >
        <c:when test="${lduser.readaccess=='true' || appStatus.projdescyn=='true' || appStatus.pendingReview=='true'}" > 
          <tr>   
            <td><bean:write name="projNarrative" property="narrative" filter="false" /></td>
          </tr>
        </c:when>
        <c:otherwise >
          <tr>
             <td align="center"><html:submit value="Save" />
                                <i><b>Remember to Save your work often.</b></i></td>
          </tr>
          <tr>
            <td align="center">
              <div name="myToolBars" class="mceToolbarExternal"></div>
              <html:textarea property="narrative" cols="80" rows="17" />            
            </td>  
          </tr>
          <tr>          
            <td align="center">
            <a href="#dataEntryAnchor" id="dataEntryAnchor"></a>
            <html:hidden property="id" /> <html:hidden property="narrTypeID" />
            <html:hidden property="module" value="lg" /><html:hidden property="program" />
            <html:submit value="Save" /><i><b>Remember to Save your work often.</b></i></td>
          </tr>              
          
        </c:otherwise>
        </c:choose>              
        
      </table>
    </html:form> 
    
    <c:if test="${anchorSection !=null}">
    <script type="text/Javascript">
      document.getElementById('dataEntryAnchor').focus();
    </script>  
    </c:if>
         
  </body>
</html>
