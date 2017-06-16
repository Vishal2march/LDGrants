<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
    <script language="javascript" type="text/javascript">
    function verifyMaxlength(form){
       form.summSize.value =form.narrative.value.length;
    }  
  </script>
  </head>
  <body>
  
  <c:if test="${param.p=='fl'}"><%--11/6/12 rqsted by LAreford--%>
    <p>NOTE: New York State Family Literacy funding as per Education Law 273 1 h (3), 
    requires that project funds include components for children AND parents/caregivers.
    </p>             
  </c:if>        
  
  <html:errors />
  <html:form action="/flSaveNarrative" >         
      <table width="95%" align="center" class="boxtype" summary="for layout only" >
        <tr>
          <th><c:out value="${projNarrative.narrativeTitle}" /></th>
        </tr>
        <tr>
          <td><bean:write name="projNarrative" property="narrativeDescr" filter="false"/><br/></td>
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
              <html:textarea property="narrative" cols="80" rows="12" onkeydown="verifyMaxlength(this.form);"  />
              <br/>
              <input readonly="readonly" type=text name="summSize" size="4" style="background-color:silver"/>Character count
            </td>
          </tr>
          <tr>          
            <td align="center"><html:hidden property="id" /> <html:hidden property="narrTypeID" />
            <html:hidden property="module" /><html:hidden property="narrativeDescr"/>
            <html:hidden property="narrativeTitle"/>
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