<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Construction</title>
    <script language="javascript" type="text/javascript">
    function verifyMaxlength(form){
       form.summSize.value =form.narrative.value.length;
    }  
  </script>
  </head>
  <body> 
 

 <html:errors/>
 <html:form action="/cnSaveRevAbstractNarr" >
 <table align="center" width="80%" class="boxtype" summary="for layout only">
    <tr>
      <th><c:out value="${projNarrative.narrativeTitle}" /></th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarrative" property="narrativeDescr" filter="false" /><br/></td>
    </tr>      
    <tr>
        <td align="center">
        
    <c:choose >
    <c:when test="${lduser.reviewconstruction=='false' || assignBean.systemLockOut=='true'}" > 
        <bean:write name="projNarrative" property="narrative" filter="false" />
    </c:when>
    <c:otherwise > 
    
            <html:textarea property="narrative" cols="60" rows="8" onkeydown="verifyMaxlength(this.form);"  />
            <br/>
            <input readonly type=text name="summSize" size="4" style="background-color:silver"/>Character count
             <br/>        
            <html:hidden property="id" /> 
            <html:hidden property="narrTypeID"/>
            <html:hidden property="module" value="cnreview" />
            <html:submit value="Save" />       
    </c:otherwise>
    </c:choose>    
        
        </td>
    </tr>    
 </table>
 </html:form>  

  
  
  </body>
</html>