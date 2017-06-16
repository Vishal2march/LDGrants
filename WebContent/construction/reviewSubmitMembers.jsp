<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
  </head>
  <body>
  
  <h4>Submit Member Applications to Library Development</h4>
  
  <p>Search Member Library Applications for Fiscal Year:
  <form method="POST" action="cnReviewNav.do?item=listMembersForSubmit">
        <select name="fycode">
          <c:forEach var="row" items="${dropDownList}">
          <option value='<c:out value="${row.id}"/>'><c:out value="${row.description}"/></option>
          </c:forEach>       
       </select>
       <input type="SUBMIT" value="View" /> 
  </form></p>
  
  
  
  <logic:notEmpty name="AssignCollectionBean">
  
  <c:if test="${statusBean.dateAcceptable=='false'}">
  <font color="red">Warning:  The due date (<fmt:formatDate value="${statusBean.dueDate}" pattern="MM/dd/yyyy" />)
   for construction applications has expired. You may not submit applications for this fiscal year.</font>
  </c:if>
  
                
  <html:errors/>
  <html:form action="saveCnReviewSubmission">     
  <table width="100%" align="center" summary="for layout only" class="boxtype" >  
    <tr>
      <th>Project Number</th><th>Title</th><th>Institution</th>
      <th>Amt Requested</th><th>Maximum Recommendation</th><th>Recommended</th><th>Submit to LD</th>
    </tr>  
    
    <c:if test="${AssignCollectionBean.totalAmountRecommend > AssignCollectionBean.initialAlloc}">
    <tr>
        <td></td>
        <td colspan="6"><font color="Red">Warning: The Total Amount Recommended by System to Members
        is more than the System Allocation.</font></td>
    </tr>
    </c:if>
        
    <logic:present name="AssignCollectionBean" property="allConstructionAssigns" >
    <logic:iterate name="AssignCollectionBean" property="allConstructionAssigns" id="systemAssignItem" >   
    
      <tr>
       <td>03<fmt:formatNumber value="${systemAssignItem.fccode}"/>
            -<fmt:formatNumber value="${systemAssignItem.fycode}"/>
            -<fmt:formatNumber value="${systemAssignItem.projectSeqNum}" pattern="####"/></td>  
       <td><c:out value="${systemAssignItem.projName}" /></td>  
       <td><c:out value="${systemAssignItem.instName}"/></td>
       <td><fmt:formatNumber type="currency" value="${systemAssignItem.amtrequested}" maxFractionDigits="0"/>
           <html:hidden name="systemAssignItem" property="grantId" indexed="true"/>
           <html:hidden name="systemAssignItem" property="assignmentId" indexed="true"/>
           <html:hidden name="systemAssignItem" property="fycode" indexed="true"/>
           <html:hidden name="systemAssignItem" property="projectSeqNum" indexed="true"/>
           <html:hidden name="systemAssignItem" property="amtrequested" indexed="true"/>
           <html:hidden name="systemAssignItem" property="maxRequestCost" indexed="true"/>
           <html:hidden name="systemAssignItem" property="instName" indexed="true"/>
           <html:hidden name="systemAssignItem" property="projName" indexed="true"/></td>
       <td><fmt:formatNumber value="${systemAssignItem.maxRequestCost}" type="currency" maxFractionDigits="0"/></td>
       
       <c:choose>
       <c:when test="${systemAssignItem.ratingComplete=='true'}">
            <td><fmt:formatNumber type="currency" value="${systemAssignItem.recommendAmt}" maxFractionDigits="0"/></td>
                <html:hidden name="systemAssignItem" property="recommendAmtStr" indexed="true" />
                <html:hidden name="systemAssignItem" property="ratingComplete" indexed="true" />
             <td>Submitted</td> 
       </c:when>          
       <c:otherwise>   
             <td><html:text name="systemAssignItem" property="recommendAmtStr" indexed="true"/></td>
             <td><html:checkbox name="systemAssignItem" property="ratingComplete" indexed="true" /> 
       </c:otherwise>
       </c:choose>
    </tr>
    
    </logic:iterate>
    </logic:present>
  
    <tr>
       <td height="20"/>
    </tr>
    <tr>
       <td></td><td></td><td></td><td><b>Total Recommended</b></td>
       <td><b><%--<fmt:formatNumber value="${AssignCollectionBean.totalAmountRequest}" type="currency" maxFractionDigits="0"/>--%></b>
              <html:hidden property="totalAmountRequest"/></td>
       <td><b><fmt:formatNumber value="${AssignCollectionBean.totalAmountRecommend}" type="currency" maxFractionDigits="0"/></b>
              <html:hidden property="totalAmountRecommend"/></td>
    </tr> 
    <tr>
        <td height="15"/>
    </tr>
    <tr>
       <td></td><td></td><td></td>
       <td colspan="2"><b>System Allocation</b></td>
       <td><b><fmt:formatNumber value="${AssignCollectionBean.initialAlloc}" type="currency" maxFractionDigits="0"/></b>
              <html:hidden property="initialAlloc"/></td>
    </tr> 
    <tr>
       <td></td><td></td><td></td>
       <td colspan="2"><b>Difference (Amt available to recommend)</b></td>
       <td><b><fmt:formatNumber value="${allocationBean.differenceAllocation}" type="currency" maxFractionDigits="0"/></b></td>
    </tr> 
    
    <c:if test="${AssignCollectionBean.totalAmountRecommend > AssignCollectionBean.initialAlloc}">
    <tr>
        <td></td>
        <td colspan="6"><font color="Red">Warning: The Total Amount Recommended by System to Members
        is more than the System Allocation.</font></td>
    </tr>
    </c:if>
    
    <tr>
       <td height="20"/>
    </tr>
    
    <c:choose>
    <c:when test="${statusBean.dateAcceptable=='false'}">
        <tr>
            <td colspan="7" align="center">
            <font color="red">Warning:  The due date (<fmt:formatDate value="${statusBean.dueDate}" pattern="MM/dd/yyyy" />)
                for construction applications has expired. You may not submit applications 
                for this fiscal year.</font><br/>
                <input type="button" value="Save" disabled="disabled" /></td>
        </tr>
    </c:when>
    <c:otherwise>
        <tr>
            <td colspan="7" align="center"><html:submit value="Save" /></td>
        </tr>
    </c:otherwise>
    </c:choose>
    
  </table>   
  </html:form>  
  </logic:notEmpty>
  
  
  </body>
</html>