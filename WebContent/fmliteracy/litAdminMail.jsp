<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
    <script type="text/javascript">
      function validate_form(thisform)
      {            
        if (thisform.from.value==null|| thisform.from.value=="") {
          alert("The 'from' email address field must be filled in!");
          thisform.from.focus();
          return false;
        }        
        return true;
      }
    </script>
  </head>
  <body>  
 
    
  <h4>Send Award Email to Project Managers</h4>  
  
  <form method="POST" action="litAdminEmail.do?i=viewgrants" >
  <table border="1" width="90%" align="center" summary="for layout only">
    <tr>
      <td>Send an award/denial email to Project Managers for all grants that have been approved
      or denied for the fiscal year. Choose Adult or Family Literacy and the fiscal year
      of the project. Then click View Grants to view all grant proposals that match the
      selected criteria.</td>
    </tr>
    <tr>
      <td><input type="RADIO" name="fc" value="40" checked="checked" />Adult Literacy<br/>
          <input type="RADIO" name="fc" value="42" />Family Literacy</td>
    </tr>
    <tr>
      <td>Fiscal Year
          <select name="fy" >
            <c:forEach var="row" items="${dropDownList}">
                <option value="<c:out value='${row.id}' />">
                <c:out value="${row.description}" /></option>
            </c:forEach>              
          </select>
        </td>
    </tr>
    <tr>
      <td><input type="RADIO" name="type" value="Initial" checked="checked" />Application Approved Email<br/>
          <input type="RADIO" name="type" value="All" />Application Denied Email</td>
    </tr>
    <tr>
      <td><input type="SUBMIT" value="View Grants" /></td>
    </tr>
  </table>
  </form>
  
  
  <logic:notEmpty name="allGrants">
  
  <br/><br/>
  <p>The following grants fit your search criteria.  The project managers for these
  grants will receive the following email:</p>
  <form method="POST" action="litAdminEmail.do?i=template" onsubmit="return validate_form(this)">
    <table class="borderbox" width="95%" align="center" summary="for layout only">
      <tr>
        <th>Project Number</th><th>Institution</th><th>Title</th>
      </tr>
      
      <c:forEach var="row" items="${allGrants}" >
        <tr>
          <td>03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${row.fccode}" />
            -<fmt:formatNumber value="${row.fycode}" minIntegerDigits="2" />
            -<fmt:formatNumber value="${row.projseqnum}" minIntegerDigits="4" pattern="####" /></td>
        
          <td><c:out value="${row.instName}" /></td>
          <td><c:out value="${row.title}" /></td>
        </tr>        
      </c:forEach>
      
      <tr>
        <td height="20" />
      </tr>
      <tr>
        <td colspan="3">*From email address: <input type="TEXT" name="from" /></td>
      </tr>
      <tr>
        <td colspan="3">Cc email address: <input type="TEXT" name="cc" /></td>
      </tr>
      <tr>
        <td colspan="3" align="center">
        <input type="HIDDEN" name="type" value="<c:out value='${emailHelp.approvalType}' />" />
        <input type="HIDDEN" name="fy" value="<c:out value='${emailHelp.fycode}' />" />
        <input type="HIDDEN" name="fc" value="<c:out value='${emailHelp.fccode}' />" />
        <input type="SUBMIT" value="Confirm Recipient Group" /></td>
      </tr>
    </table>
    </form> <br/>
    
    <table class="borderbox" width="95%" align="center" summary="for layout only">
      <tr>
        <th>Email Subject</th>
        <td><c:out value="${stemplate.subject}" /></td>
      </tr>
      <tr>
        <th>Email Message</th>
        <td><c:out value="${stemplate.message}" /></td>
      </tr>
    </table>
  
 </logic:notEmpty>
 
    
  </body>
</html>
