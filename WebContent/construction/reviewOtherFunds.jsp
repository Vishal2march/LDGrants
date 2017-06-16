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
   
  
  <h4>Additional Sources of Funding for this Construction Project</h4>
  <p>List all funding sources that contribute to this construction project.  Note: The
  Application Form must be completed first, before you can complete the Additional 
  Sources of Funding section.</p>
  
  
  <c:choose >
  <c:when test="${lduser.reviewconstruction=='false' || assignBean.systemLockOut=='true'}">
  
  <html:form action="/saveReviewOtherFunds" >
    <table width="90%" summary="for layout only">
      <tr>
        <th width="33%">Fund Source</th>
        <th width="33%">Description</th>
        <th width="33%">Amount</th>
      </tr> 
    </table>
    
    <logic:present name="BudgetCollectionBean" property="allFundSources" >
    <logic:iterate name="BudgetCollectionBean" property="allFundSources" id="fundItem">   
                           
      <table width="90%" class="boxtype" summary="for layout only">
      <tr>      
        <td width="33%">
          <html:select name="fundItem" property="fundSourceId" indexed="true" disabled="true">
            <html:optionsCollection name="fundItem" property="allPossibleFundSources" value="id" label="description"/>
          </html:select>     
        </td>      
        <td width="33%"><c:out value="${fundItem.description}" /></td>
         
        <td width="33%"><fmt:formatNumber value="${fundItem.amountReceived}" type="currency" /></td>        
      </tr>
      </table>    
    
    </logic:iterate>
    </logic:present>    
    </html:form>
  
  
  </c:when>
  <c:otherwise>
    
  <c:if test="${noBuildingError!=null && noBuildingError=='true'}">
    <p><font color="Red">The Application Form must be completed first. You cannot fill out
    the Additional Sources of Funding section until the Application Form is completed and
    saved.</font></p>
  </c:if>
      
  <form method="POST" action="otherFundsNav.do">
      <p><input type="HIDDEN" name="i" value="addrecord" />
      <input type="hidden" name="mod" value='<c:out value="${param.p}"/>'/>
      <input type="submit" name="btn" value="Add" />        
       Please save any changes before adding a new record.</p>
   </form>    
  
  <html:errors />
  <html:form action="/saveReviewOtherFunds" >

    <table width="90%" summary="for layout only">
      <tr>
        <th width="33%">Fund Source</th>
        <th width="33%">Description</th>
        <th width="33%">Amount</th>
      </tr> 
    </table>
    
    <logic:present name="BudgetCollectionBean" property="allFundSources" >
    <logic:iterate name="BudgetCollectionBean" property="allFundSources" id="fundItem">   
                      
      <c:choose>
      <c:when test="${param.p=='cn'}">      
              <c:url var="deleteURL" value="CnDeleteFunds.do">
                <c:param name="m" value="cn"/>
                <c:param name="fid" value="${fundItem.buildingFundId}"/>
              </c:url>
        </c:when>
        <c:otherwise><%-- cn review--%>
              <c:url var="deleteURL" value="CnReviewDeleteFunds.do">
                <c:param name="m" value="cnreview"/>
                <c:param name="fid" value="${fundItem.buildingFundId}"/>
              </c:url>
        </c:otherwise>
        </c:choose>
      
                 
      <table width="90%" class="boxtype" summary="for layout only">
      <tr>      
        <td width="33%">
          <html:select name="fundItem" property="fundSourceId" indexed="true">
            <html:optionsCollection name="fundItem" property="allPossibleFundSources" value="id" label="description"/>
          </html:select>     
          <br/><br/>
          <a href='<c:out value="${deleteURL}" />' >Delete</a>
        </td>      
        <td width="33%"><html:textarea name="fundItem" property="description" rows="5" cols="35" indexed="true" /></td>
         
        <td width="33%"><html:text name="fundItem" property="amountReceivedStr" indexed="true" />
            <html:hidden name="fundItem" property="buildingFundId" indexed="true" />
            <html:hidden name="fundItem" property="grantBuildingId" indexed="true" />
            <html:hidden name="fundItem" property="grantId" indexed="true" /></td>        
      </tr>
      </table>    
    
    </logic:iterate>
    </logic:present>    
    
        <p align="center">
            <input type="HIDDEN" name="mod" value='<c:out value="${param.p}" />' />
            <html:submit value="Save"/></p>
  
    </html:form>    
    
    </c:otherwise>
    </c:choose>     
  
  
  </body>
</html>