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
     <script language="javascript" type="text/javascript">
    function verifyMaxlength(form){
       form.summSize.value =form.narrative.value.length;
    }  
  </script>
  </head>
  <body>
  <center>
  
  <html:errors />
  <html:form action="/cnSaveAdminAbstract" >         
      <table width="80%" align="center" class="boxtype" summary="for layout only" >
        <tr>
          <th><c:out value="${projNarrative.narrativeTitle}" /></th>
        </tr>
        <tr>
          <td><bean:write name="projNarrative" property="narrativeDescr" filter="false"/><br/></td>
        </tr>   
        <tr>
          <td height="20" />
        </tr>        
         
          <tr>
            <td align="center">
              <html:textarea property="narrative" cols="60" rows="8" onkeydown="verifyMaxlength(this.form);"  />
              <br/>
              <input readonly type=text name="summSize" size="4" style="background-color:silver"/>Character count
            </td>
          </tr>
          <tr>          
            <td align="center">
                <html:hidden property="id" /> 
                <html:hidden property="narrTypeID" />
                <html:hidden property="module" value="admncn"/>
                <html:hidden property="narrativeDescr"/>
                <html:hidden property="narrativeTitle"/>
                <html:submit value="Save" /></td>
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