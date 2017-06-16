<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  selectFS10F.jsp
 * Creation/Modification History  :
 *     SH       9/17/07     Created
 *
 * Description
 * This page allows CO apcnt/admin to choose the fy and format to view the FS10F form.  
 * Changed to also include select of FS20 by fiscal year (requested 6/19/08)
 * Changed to use fs10 long and fs10f long (7/21/08)
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h4>FS-10 Form</h4>
  
  <form method="POST" action="FsFormServlet" target="_blank">
  <table width="80%" align="center">
    <tr>
      <td>The <b>FS-10</b> (formerly called the FS-20) form must be submitted at the time of the Coordinated application submission.
      Choose the project year and HTML or PDF format. <br/><br/>
      The FS-10 Form contains the 'Amount Requested' from each budget record on the Project Budget pages.<br/>
      Three copies must be signed in blue ink and mailed to the Division of Library Development. 
      The form will open in a new window.</td>
    </tr>
    <tr>
      <td>
        <select name="fy" id="fy" >
          <option value='<c:out value="${thisGrant.fycode}" />'>Budget Year 1</option>
          <option value='<c:out value="${thisGrant.fycode +1}" />'>Budget Year 2</option>
          <option value='<c:out value="${thisGrant.fycode +2}" />'>Budget Year 3</option>
        </select>
      </td>
    </tr>
    <tr>
      <td><input type="RADIO" name="i" value="fs10">HTML<br/>
          <input type="RADIO" name="i" value="fs10pdf" checked="checked">PDF (PDF format preferred)
      </td>
    </tr>
    <tr>
      <td><input type="SUBMIT" value="View" /></td>
    </tr>
  </table>
  </form>
  <hr/>
  
  <c:if test="${param.pr=='admin'}">
  <form method="POST" action="FsFormServlet" target="_blank">
  <table width="80%" align="center">
    <tr>
      <td>The <b>FS-10 Form (Approved Amounts)</b> is only available to admin. This version of
      the FS-10 uses the Amount Approved from the Project Budget pages. 
      Choose the project year and HTML or PDF format. <br/><br/>
      </td>
    </tr>
    <tr>
      <td>
        <select name="fy" id="fy" >
          <option value='<c:out value="${thisGrant.fycode}" />'>Budget Year 1</option>
          <option value='<c:out value="${thisGrant.fycode +1}" />'>Budget Year 2</option>
          <option value='<c:out value="${thisGrant.fycode +2}" />'>Budget Year 3</option>
        </select>
      </td>
    </tr>
    <tr>
      <td><input type="RADIO" name="i" value="fs10ApprAmt">HTML<br/>
          <input type="RADIO" name="i" value="fs10ApprAmtPdf" checked="checked">PDF (PDF format preferred)
      </td>
    </tr>
    <tr>
      <td><input type="SUBMIT" value="View" /></td>
    </tr>
  </table>
  </form>
  <hr/>
  </c:if>
  
   <h4>FS-10-F Form</h4>
  
  <form method="POST" action="FsFormServlet" target="_blank">
  <table width="80%" align="center">
    <tr>
      <td>The FS-10-F form must be submitted at the end of each fiscal year period that
      the Coordinated grant project spans.  Choose the project year and HTML or PDF format. 
      Three copies must be signed and mailed to the Division of Library Development. 
      The form will open in a new window.</td>
    </tr>
    <tr>
      <td>
        <select name="fyf" id="fyf" >
          <option value='<c:out value="${thisGrant.fycode}" />'>Budget Year 1</option>
          <option value='<c:out value="${thisGrant.fycode +1}" />'>Budget Year 2</option>
          <option value='<c:out value="${thisGrant.fycode +2}" />'>Budget Year 3</option>
        </select>
      </td>
    </tr>
    <tr>
      <td><input type="RADIO" name="i" value="fs10f">HTML<br/>
          <input type="RADIO" name="i" value="fs10fpdf" checked="checked">PDF (PDF format preferred)
      </td>
    </tr>
    <tr>
      <td><input type="SUBMIT" value="View" /></td>
    </tr>
  </table>
  </form>
  
  </body>
</html>
