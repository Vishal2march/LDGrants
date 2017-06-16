<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  pd_tabIa.jsp
 * Creation/Modification History  :
 *     SHusak       7/13/07     Modified
 *
 * Description
 * This is the Project Description page for coordinated Ia.  It allows applicants to
 * enter the description narrative info required for the application.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html>
  <head>
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
  
  <p align="center"><b>Important:</b> For all education proposals 
  sections Cooperative Planning, Plan of Work and Contributions are required.  
  A separate section on Education Pre-planning is also required.</p>
    
    <html:form action="/saveCoNarrative">           
      <table width="95%" align="center" class="boxtype" summary="for layout only" >
        <tr>
          <th><c:out value="${projNarrative.narrativeTitle}" /></th>
        </tr>
        <tr>
          <td align="center">
            <c:out value="${projNarrative.narrativeDescr}" />
          </td>
        </tr>      
        <tr>
          <td height="20" />
        </tr>                 
         
        <c:choose >
        <c:when test="${lduser.prgco=='read' || projNarrative.lockNarrative=='true'}" > 
          <tr>   
            <td><bean:write name="projNarrative" property="narrative" filter="false" /></td>
          </tr>
        </c:when>
        <c:otherwise >
          <tr>
            <td align="center">
              <div name="myToolBars" class="mceToolbarExternal"></div>
              <html:textarea property="narrative" cols="80" rows="17" />
            </td>  
          </tr>
          <tr>          
            <td align="center"><html:hidden property="id" /><html:hidden property="narrTypeID" />
            <html:hidden property="module" value="co" />
            <html:submit value="Save" /></td>
          </tr>          
           <tr>
            <td height="20" />
          </tr>
          <tr>
            <td><a href="CoAddDocs.do">Attach</a> a document/attachment to this grant application.  
            (Be sure to Save narrative first)</td>
          </tr>
          <tr>
            <td><b>Instructions to copy/paste from another source:</b> <br/>
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
  