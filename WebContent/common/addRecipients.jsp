<%--
 * @author  shusak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  addRecipients.jsp
 * Creation/Modification History  :
 *     
 *     SHusak       6/15/07     Modified
 *
 * Description
 * This page is for lit admin to search for email recipient groups (reviewers, 
 * PM's of approved/denied projects), and add them to the email template.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
    
   
  <form method="POST" action="litAdminEmail.do?i=viewrecipients" >
  <table width="100%" class="boxtype" summary="for layout only">
    <tr>
      <td><b>Choose a recipient group</b></td>
    </tr>
    <tr>
      <td>Program:<br/>
          <input type="RADIO" name="fc" value="40" checked="checked" />Adult Literacy
          <br/><input type="RADIO" name="fc" value="42" />Family Literacy</td>
    </tr>
    <tr>
      <td>Role:<br/>
      <input type="RADIO" name="type" value="Reviewer" />Reviewers<br/>
      <input type="RADIO" name="type" value="Approve" checked="checked" />Project Managers/Additional Contacts 
      Awarded Projects<br/>
      <input type="RADIO" name="type" value="Denied" />Project Managers/Additional Contacts Denied Projects</td>
    </tr>
    <tr>
      <td>Fiscal Year:
          <select name="fy" >
            <c:forEach var="row" items="${dropDownList}">
                <option value="<c:out value='${row.id}' />">
                <c:out value="${row.description}" /></option>
            </c:forEach>              
          </select> <hr/></td>
      </tr>
      <tr>
        <td><input type="HIDDEN" name="wtid" value='<c:out value="${id}"/>' />
        <input type="SUBMIT" value="View Recipients" /></td>
      </tr>  
  </table>
  </form>
  
  
  <logic:notEmpty name="selectgroup">  
  
  <form method="POST" action="litAdminEmail.do?i=addrecipients">
  <table width="100%" class="borderbox" summary="for layout only">
    <tr>
      <th colspan="3">Recipients that match the selected search criteria:</th>
    </tr>
    <tr>
      <th>Name</th>
      <th>Email Address</th>
      <th>Project Number (for Project Managers)</th>
      <th>Institution</th>
    </tr>
    
    <c:forEach var="row" items="${selectgroup}">
      <tr>
        <td><c:out value="${row.projectManager.fname}"/> <c:out value="${row.projectManager.lname}" /></td>
        <td><c:out value="${row.projectManager.email}"/></td>
        <td><c:if test="${row.projseqnum!=0}">
          03<fmt:formatNumber minIntegerDigits="2" value="${row.fccode}" />
          -<fmt:formatNumber value="${row.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${row.projseqnum}" pattern="####" />
          </c:if></td>
        <td><c:out value="${row.instName}"/></td>
      </tr>
    </c:forEach>
    <tr>
      <td colspan="3"><hr/>Optional CC email address (comma separated for multiple email addresses): <input type="TEXT" name="cc" /></td>
    </tr>
    <tr>
      <td colspan="3"><input type="HIDDEN" name="wtid" value='<c:out value="${id}"/>' />
      <input type="HIDDEN" name="fc" value='<c:out value="${fc}"/>' />
      <input type="HIDDEN" name="fy" value='<c:out value="${fy}"/>' />
      <input type="HIDDEN" name="atype" value='<c:out value="${atype}"/>' />
      <input type="SUBMIT" value="Add"> this Recipient Group to email</td>
    </tr>
  </table>
  </form>
  </logic:notEmpty>
   
  </body>
</html>
