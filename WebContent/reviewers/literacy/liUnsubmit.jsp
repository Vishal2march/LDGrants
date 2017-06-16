<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <br/>
  <table align="center" width="90%" summary="for layout only" class="boxtype">
    <tr>
      <th colspan="2">Unsubmit Reviewer Rating Form</th>
    </tr>   
    <tr>
      <td><b>Project Number</b></td>
      <td>03<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
              -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
              -<fmt:formatNumber value="${thisGrant.projseqnum}" /></td>
    </tr>
    <tr>
      <td><b>Sponsoring Institution</b></td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td><b>Project Title</b></td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    
    <form method="POST" action="liReviewer.do">
      <tr>
        <td colspan="2">Do you want to unsubmit the Rating Form and Comments for this Grant Proposal?</td>
      </tr>
      <tr>
        <input type="HIDDEN" name="assignid" value='<c:out value="${assignBean.assignid}" />' />
        <input type="HIDDEN" name="item" value="dounsubmit" />
        <td colspan="2"><input type="SUBMIT" value="Unsubmit" /> &nbsp;
        <input type="BUTTON" value="Cancel" onclick="Javascript:history.go(-1);" /></td>
      </tr>
    </form>
       
    </table>
    
  </body>
</html>
