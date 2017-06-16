<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  liNarrative.jsp
 * Creation/Modification History  :
 *
 *     SHusak     Created
 *
 * Description
 * This page is al/fl applicant narrative, used to add/update.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
  
  
  <c:if test="${param.p=='fl'}"><%--11/6/12 rqsted by LAreford--%>
    <p>NOTE: New York State Family Literacy funding as per Education Law 273 1 h (3), 
    requires that project funds include components for children AND parents/caregivers.
    </p>             
  </c:if>           
  
  <html:form action="/flSaveNarrative" >         
      <table width="95%" align="center" class="boxtype" summary="for layout only" >
        <tr>
          <th><c:out value="${projNarrative.narrativeTitle}" /></th>
        </tr>
        <tr>
          <td><bean:write name="projNarrative" property="narrativeDescr" filter="false"/><br/>
          This narrative should be <b>no more than 1 page</b> in length.</td>
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
            <td align="center">
              <%--<div name="myToolBars" class="mceToolbarExternal"></div>--%>
              <html:textarea property="narrative" cols="80" rows="17" />              
            </td>  
          </tr>
          <tr>          
            <td align="center"><html:hidden property="id" /> <html:hidden property="narrTypeID" />
            <html:hidden property="module" />
            <html:submit value="Save" /></td>
          </tr>          
          <tr>
            <td><b>Instructions to copy/paste from another source:</b> <br/>
            Highlight the text that you want to copy and then click Ctrl + C on keyboard.  
            Put the cursor in the desired area and click Ctrl + V on keyboard to paste.  Or use the toolbar icons for
            Copy and Paste.</td>
          </tr>
          <tr>
            <td>
             <c:choose >
              <c:when test="${param.p=='al'}">
                  <a href="AlAddAttachment.do">Attach</a> 
              </c:when>
              <c:when test="${param.p=='fl'}">
                  <a href="FlAddAttachment.do">Attach</a>
              </c:when>           
              </c:choose>
              a document/attachment to this grant application.
             (Be sure to Save narrative first)</td>
          </tr>
        </c:otherwise>
        </c:choose>              
        
      </table>
    </html:form>  
  
  </body>
</html>
