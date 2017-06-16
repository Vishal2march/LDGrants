<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Admin Update Evaluation Form</title>
  </head>
  <body>
  
  <h4>Update At-Home Evaluation-Recommendation</h4>
  
  
  <html:form action="/updateEvalForm">
  <html:errors />
  <table summary="for layout only">
    <tr>
        <td colspan="2"><c:if test="${RatingBean.sumscore + RatingBean.bonuspts < 60}">
            <font color="Red">**Warning**:</font>If the Total At-Home Score 
            is less than 60, you must select 'N' (No Fund) for Recommendation.</c:if>
        </td>
    </tr>
    <tr>
      <td>Recommendation (F, M, or N):<br/>
      (LGRMIF program has a minimum score of 60 to be considered for funding)</td>
      <td><html:select property="recommendation">
            <html:option value="N"/>
            <html:option value="M"/> 
            <html:option value="F"/> 
          </html:select>
      </td>
    </tr>
    <tr>
        <td>Total Requested:</td>
        <td><c:out value="${RatingBean.totamtreq}"/>
            <html:hidden property="totamtreq"/></td>
    </tr>
    <tr>
      <td>Recommended Amount:</td>
      <td><html:text property="recommendamtStr" /></td>
    </tr>
    <tr>
      <td colspan="2" align="center">
      <html:hidden property="grantassign"/><html:hidden property="bonuspts"/>
      <html:hidden property="sumscore"/>
      <html:submit value="Save" /></td>
    </tr>    
    
    <tr>
      <td>Total Score</td>
      <td><c:out value="${RatingBean.sumscore}"/></td>
    </tr>
    <tr>
      <td>1. Cooperative Project  (10 Points)</td>
      <td><c:if test="${CoverBean.cooperative}">10</c:if></td>
    </tr>
    <tr>
      <td>2. 1st Time Inventory & Planning  (5 points)</td>
      <td><c:if test="${CoverBean.inventory}">5</c:if></td>
    </tr>
    <tr>
      <td>3. Electronic records inventory projects   (5 points)</td>
      <td><c:if test="${CoverBean.recordsmgmt}">5</c:if></td>
    </tr>
    <tr>
      <td>4. Email Management projects (5 points)</td>
      <td><c:if test="${CoverBean.emailmgmt}">5</c:if></td>
    </tr>
    <tr>
      <td><b>Total At-Home Score</b></td>
      <td><b><c:out value="${RatingBean.sumscore + RatingBean.bonuspts}"/></b></td>
    </tr>
  </table>
  </html:form>
  
  </body>
</html>