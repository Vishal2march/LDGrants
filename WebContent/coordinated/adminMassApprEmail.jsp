<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  adminMassApprEmail.jsp
 * Creation/Modification History  :
 *
 *     SHusak       10/5/07     Created
 *
 * Description
 * This page allows the admin to send mass mailing to PO/PM for sa/co/di. Admin chooses
 * fy and approval/denial email type, then gets listing of all matching grants, then confirms
 * the send email. 
 * NOT USED STARTING ON 3/5/10.  SEE COMMON/cpAdminSelectMail.JSP FOR NEW CP ADMIN EMAIL FNS
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
   
  <h4>Send mass mailing to Preservation Officers/Project Managers</h4>
  
  
  <c:choose >
  <c:when test="${param.display !=null}">
  
  <form method="POST" action="adminMassEmail.do?item=send">
    <table border="1" width="90%" align="center" summary="for layout only">
      <tr>
        <td colspan="3">The following grants fit your search criteria.  The contact person for these
        grants will receive <b><c:out value="${emailType}" /></b> email. </td>
      </tr>
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
        <td colspan="3" align="center"><input type="HIDDEN" name="type" value="<c:out value='${type}' />" />
        <input type="HIDDEN" name="fycode" value="<c:out value='${fycode}' />" />
        <input type="HIDDEN" name="m" value="<c:out value='${param.p}'/>" />
        <input type="SUBMIT" value="Send Emails" /></td>
      </tr>
    </table>
 </form>
 
  
  </c:when>
  <c:otherwise >
  
  
  <form method="POST" action="adminMassEmail.do?item=listall" >
  <table border="1" width="90%" align="center" summary="for layout only">
    <tr>
      <td>Send a <i>mass</i> mailing to contact person for all approved or denied grants 
      for the fiscal year.   Or use the email link from the Grant proposal check
      status page to send an <i>individual</i> approval/deniel email.</td>
    </tr>
    <tr>
      <td>Fiscal Year
          <select name="fycode" >
            <c:forEach var="row" items="${dropDownList}">
                <option value="<c:out value='${row.id}' />">
                <c:out value="${row.description}" /></option>
            </c:forEach>              
          </select>
        </td>
    </tr>
    <tr>
      <td><input type="RADIO" name="type" value="Initial" checked="checked" />Application Approved Email<br/>
          <input type="RADIO" name="type" value="Final" />Final Report Approved Email<br/>
          <input type="RADIO" name="type" value="All" />Application Denied Email<br/>
          <input type="RADIO" name="type" value="Prov" />Provisional Award Email (For C/P Discretionary) <font color="red">*New</font><br/>
          <input type="radio" name="type" value="OSC"/>Intent to Award Pending OSC Approval (For C/P Coordinated)</td>
    </tr>
    <tr>
      <td><input type="HIDDEN" name="m" value="<c:out value='${param.p}' />" />
          <input type="SUBMIT" value="View Grants" /></td>
    </tr>
  </table>
  </form>
    
  <p><b>If you already started an email but have not sent the email: </b>
  <a href="cpAdminEmail.do?item=unsentmail">View Unsent Emails</a></p>
  
  </c:otherwise>
  </c:choose>
  
  </body>
</html>
