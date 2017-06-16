<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%--
 * @author  Stefanie Husak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  diApcntNarrative.jsp
 * Creation/Modification History  :
 *
 *     SHusak       12/21/07     Created
 *
 * Description
 * This page allows Di apcnt to type in and save thier project description, or to 
 * view the description depending on user permissions and grant submit status. 
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
  
    <p align="center"><b>Important:</b> For Education Proposals, do NOT complete sections II, III,
    and IV.   Substitute section VII. Education Pre-Planning</p>
    
    <html:form action="/diSaveNarrative" >         
      <table width="95%" align="center" class="boxtype" summary="for layout only" >
        <tr>
          <th><c:out value="${projNarrative.narrativeTitle}" /></th>
        </tr>
        <tr>
          <td><bean:write name="projNarrative" property="narrativeDescr" filter="false" /><br/><br/>
            <b>Please see <a href="docs/guidelinesDiscretionary.htm#narrative" target="_blank">Guidelines</a>
            for more detailed instructions on completing the project narratives</b></td>
        </tr>      
        <tr>
          <td height="20" />
        </tr>        
         
         
        <c:choose >
        <c:when test="${lduser.prgdi=='read' || projNarrative.lockNarrative=='true'}" > 
          <tr>   
            <td><bean:write name="projNarrative" property="narrative" filter="false" /></td>
          </tr>
        </c:when>
        
        <c:otherwise >
          <tr>
            <td align="center">
              <%--<div name="myToolBars" class="mceToolbarExternal"></div>--%>
              <html:textarea property="narrative" cols="80" rows="17" />                         
            </td>  
          </tr>
          <tr>          
            <td align="center"><html:hidden property="id" /> <html:hidden property="narrTypeID" />
            <html:hidden property="module" value="di" />
            <html:submit value="Save" /></td>
          </tr>          
          <tr>
            <td height="20" />
          </tr>
          <tr>
            <td><a href="DiAttachment.do">Attach</a> a document/attachment to this grant application. (Be sure to Save narrative first)</td>
          </tr>
          <tr>
            <td><br/><b>Instructions to copy/paste from another source:</b> <br/>
            Highlight the text that you want to copy and then click Ctrl + C on keyboard.  
            Put the cursor in the desired area and click Ctrl + V on keyboard to paste.  Or use the toolbar icons for
            Copy and Paste.</td>
          </tr>
        </c:otherwise>
        </c:choose>              
        
      </table>
    </html:form>  
  
  </body>
</html>
