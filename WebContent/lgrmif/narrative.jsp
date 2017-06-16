<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>    
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
  
  
  
  <c:if test="${param.t=='frpt'}">
  <br/>
  <a href="http://www.archives.nysed.gov/common/archives/files/grants_lgrmif_egrant_user_manual.pdf" target="_blank">Final narrative report help</a>
  <br/>
  
  <h5>Final Project Narrative</h5>
  </c:if>
       
      <html:form action="/saveLgNarrative">         
      <table width="95%" align="center" summary="for layout only" >
        <%-- remove title 8/12/09, add roman numeral numbering to descr paragraph<tr>
          <th><c:out value="${projNarrative.narrativeTitle}" /></th>
        </tr>--%>
        <tr>
          <td><bean:write name="projNarrative" property="narrativeDescr" filter="false" /><br/></td>
        </tr>  
        <tr>
          <td height="20" />
        </tr>        
         
         
        <c:choose >
        <c:when test="${lduser.readaccess=='true' || projNarrative.lockNarrative=='true'}" > 
          <tr>   
            <td><bean:write name="projNarrative" property="narrative" filter="false" /></td>
          </tr>
        </c:when>
        <c:otherwise >
          <tr>
            <td align="center"><html:submit value="Save"/>
                              <i><b>Remember to Save your work often.</b></i></td>
          </tr>
          <tr>
            <td align="center">
              <%--<div name="myToolBars" class="mceToolbarExternal"></div>--%>
              <html:textarea property="narrative" cols="80" rows="17" />                  
            </td>  
          </tr>
          <tr>          
            <td align="center"><html:hidden property="id" /> <html:hidden property="narrTypeID" />
            <html:hidden property="module" value="lg" />
            <html:submit value="Save" />
            <i><b>Remember to Save your work often.</b></i></td>
          </tr>              
          <tr>
            <td height="30"/>
          </tr>
          <tr>
            <td>Use the following link to <i>print</i> or <i>save</i> your narratives to your 
            desktop:  <a href="PrintAppServlet?i=narr" target="_blank">Narratives HTML</a></td>
          </tr>
        </c:otherwise>
        </c:choose>              
        
      </table>
    </html:form> 
    
  </body>
</html>
