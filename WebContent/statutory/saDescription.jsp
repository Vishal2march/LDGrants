<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  saDescription.jsp
 * Creation/Modification History  :
 *     SLowe                   Created
 *     SHusak       3/1/07     Modified
 *
 * Description
 * This is the Project Description for SA appcnt.  Changed 12/23/08 to use for
 * all 3 of SA narrative categories and removed extra jsp's
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
     <noscript>
      <font size="3">You do not have Javascript enabled.  In order to use text editor options
      while typing the narrative, you must enable scripting.</font>
    </noscript>
  </head>
  <body>

  <h4>Description of Proposed State Aid Preservation Activities</h4> 

<table width="100%" summary="for layout only">
  <tr>
    <td width="33%" bgcolor="#999999" align="center">
      <a href="saReadNarrative.do?t=narr&m=sa&id=3">A. Describe in detail ...</a></td>
    
    <td width="33%" bgcolor="#999999" align="center">
    <a href="saReadNarrative.do?t=narr&m=sa&id=4">B. Describe how these activities relate ...</a></td>
    
    <%--rmvd 11/5/14 per BL
    <td bgcolor="#999999" align="center">
    <a href="saReadNarrative.do?t=narr&m=sa&id=5">C. Relate the activities to be performed ...</a></td>--%>
  </tr>
</table>    
<br/><br/>
         
   <table align="center" width="90%" class="boxtype" summary="for layout only">
    <html:form action="/saveSaNarrative" > 
      <tr>
        <th><c:out value="${projNarrative.narrativeDescr}" /></th>
      </tr>  
      <tr>
        <td><hr size="3" style="background-color:rgb(128,128,128);" /></td>
      </tr>  
                                           
      <c:choose >
      <c:when test="${lduser.prgsa=='read' || appStatus.projdescyn=='true' || appStatus.pendingReview=='true'}" >
        <tr>
          <td ><bean:write name="projNarrative" property="narrative" filter="false" /></td>
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
        <html:hidden property="module" value="sa" />
        <html:submit value="Save" /></td>
      </tr>
      </c:otherwise>
      </c:choose>
                
    </html:form>
   </table>             
  
        
  </body>
  </html>