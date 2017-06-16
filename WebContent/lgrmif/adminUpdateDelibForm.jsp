<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Panel Score Recommendation</title>
  </head>
  <body>
  
  
    <h5>Admin Update Deliberation Form </h5>
  
  <html:errors />
  <html:form action="/updateDelibForm">
  <table width="85%" summary="for layout only">      
    <tr>
        <td><b>Criteria</b></td><td><b>At-Home Average</b></td><td><b>Final Deliberation</b></td>
    </tr>
    <tr>
      <td>Recommendation (F, M, or N):<br/>
      (LGRMIF program has a minimum score of 60 to be considered for funding)</td>
      <td></td>
      <td><html:select property="recommendation">
            <html:option value="N"/>
            <html:option value="M"/> 
            <html:option value="F"/> 
          </html:select></td>
    </tr>
    <tr>
      <td>Recommended Amount:</td>
      <td><fmt:formatNumber value="${RatingBean.recommendamt}" type="currency" minFractionDigits="0"/></td>
      <td><html:text property="recommendamtStr"/> </td>
    </tr>
    <tr>
      <td>Total Score (rounded to the nearest integer)</td>
      <td><c:out value="${RatingBean.scoreStr}"/></td>
      <td><html:text property="finalscore" /></td>
    </tr>
    <tr>
        <td colspan="3"><hr/></td>
    </tr>
    <tr>
      <td>Bonus Scoring</td>
    </tr>
    <tr>
      <td>1. Cooperative Project  (10 Points)</td>
      <c:if test="${CoverBean.cooperative}"><td>10</td><td>10</td></c:if>
    </tr>
    <tr>
      <td>2. 1st Time Inventory & Planning  (5 points)</td>
      <c:if test="${CoverBean.inventory}"><td>5</td><td>5</td></c:if>
    </tr>
    <tr>
      <td>3. Electronic records inventory projects   (5 points)</td>
      <c:if test="${CoverBean.recordsmgmt}"><td>5</td><td>5</td></c:if>
    </tr>
    <tr>
      <td>4. Email Management projects (5 points)</td>
      <c:if test="${CoverBean.emailmgmt}"><td>5</td><td>5</td></c:if>
    </tr>
    <tr>
        <td>Total Bonus Points</td>
        <td><c:out value="${PanelReviewBean.bonuspts}"/></td>
        <td><c:out value="${PanelReviewBean.bonuspts}"/></td>
    </tr>
    <tr>
        <td></td>
        <td><hr/></td>
        <td><hr/></td>
    </tr>
    <tr>
        <td><b>Total Panel Score</b></td>
        <td><b><c:out value="${RatingBean.scoreStr + CoverBean.score}"/></b></td>
        <td><b><c:out value="${PanelReviewBean.finalscore + PanelReviewBean.bonuspts}" /></b></td>
    </tr>
  </table>
        
    <br/><br/><br/>
    <table width="85%" summary="for layout only">
        <tr>
            <td><b>Justification for change, if any, from the Average of the At-Home Score</b></td>
        </tr>
        <tr>
            <td><html:textarea property="justification" rows="10" cols="60"/></td>
        </tr>
        <tr>
            <td height="30"/>
        </tr>
        <tr>
            <td><b>Decision Notes</b></td>
        </tr>
        <tr>
            <td><html:textarea property="decisionnotes" rows="10" cols="60"/></td>
        </tr>
        <tr>
            <td><html:hidden property="grantid"/><html:hidden property="panelgrantid"/>
                <html:hidden property="status"/><html:hidden property="bonuspts"/>
                <html:hidden property="totamtreq"/><html:hidden property="initialappr"/>
                <html:submit value="Save"/></td>
        </tr>
        <tr>
            <td height="30"/>
        </tr>
    </table>
    </html:form>
  
  
  
  </body>
</html>