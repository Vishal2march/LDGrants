<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>At-home evaluation comments</title>    
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css" />
  </head>
  <body>
  
  
  <table align="center" width="95%" summary="for layout only" class="boxtype">
    <tr>
        <th colspan="2">LGRMIF Grant At-Home Evaluation Form - Comments</th>
    </tr>
    <tr>
      <td width="40%"><b>Project Number</b></td>
      <td>05<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
              -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
              -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" /></td>
    </tr>
    <tr>
      <td><b>Institution</b></td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td><b>Category</b></td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>
    <tr>
      <td><b>Average Panel Score</b></td>
      <td><c:out value="${avgScore}" /></td>
    </tr>
  </table>
  
  
  <br/><br/>
  <table summary="for layout only">
  
    <c:forEach items="${allComments}" var="ratebean">
    <tr>
      <td>Reviewer</td>
      <td><c:out value="${ratebean.name}" /></td>
    </tr>
    <tr>
      <td>Recommendation</td>
      <td><c:out value="${ratebean.recommendation}"/></td>
    </tr>
    <tr>
      <td>Recommended Amount</td>
      <td><fmt:formatNumber value="${ratebean.recommendamt}" type="currency" minFractionDigits="0"/></td>
    </tr>
    <tr>
      <td>Total Initial Score</td>
      <td><fmt:formatNumber value="${ratebean.score}" /></td>
    </tr>
    <tr>
      <td height="25" />
    </tr>
    <tr>
      <td><b>1. Statement of the Problem Comments</b></td>
    </tr>
    <tr>
      <td colspan="2"><c:out value="${ratebean.problemcomment}" /></td>
    </tr>
    <tr>
      <td height="25"/>
    </tr>
    <tr>
      <td><b>2. Intended Results Comments</b></td>
    </tr>
    <tr>
      <td colspan="2"><c:out value="${ratebean.resultcomment}" /></td>
    </tr>
    <tr>
      <td height="25"/>
    </tr>
    <tr>
      <td><b>3. Plan of Work Comments</b></td>
    </tr>
    <tr>
      <td colspan="2"><c:out value="${ratebean.workcomment}" /></td>
    </tr>
    <tr>
      <td height="25"/>
    </tr>
    <tr>
      <td><b>4. Local Government Support Comments</b></td>
    </tr>
    <tr>
      <td colspan="2"><c:out value="${ratebean.supportcomment}" /></td>
    </tr>
    <tr>
      <td height="25"/>
    </tr>
    <tr>
      <td><b>5. Budget Narrative and Forms Comments</b></td>
    </tr>
    <tr>
      <td colspan="2"><c:out value="${ratebean.budgetcomment}" /></td>
    </tr>
    <tr>
      <td height="25"/>
    </tr>
    <tr>
      <td><b>6. Overall Impression Comments</b></td>
    </tr>
    <tr>
      <td colspan="2"><c:out value="${ratebean.comment}" /></td>
    </tr>
    <tr>
      <td colspan="2"><hr/></td>
    </tr>
    <tr>
      <td height="25"/>
    </tr>
    
    </c:forEach>
    
  </table>
    
  </body>
</html>
