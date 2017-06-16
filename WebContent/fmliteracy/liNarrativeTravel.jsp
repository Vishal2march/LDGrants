<%--
 * @author  shusak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 11g
 * Name of the Application        :  liNarrativeTravel.jsp
 * Creation/Modification History  :    
 *     SHusak  1/25/16 Created
 *
 * Description
 * This will be new budget summary/narrative fortravel, for both AL/FL, starting for 2016-19.
 * Per KBALSEN, the applicant fills out this travel budget summary at initial application (instead of
 * the detailed budget records) Detailed budget will be done during an "interim" submission after approp amt is known.
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>liNarrativeTravel</title>
  </head>
  <body>
  
  <br/><br/>
  <p align="left"><b>Travel Budget Summary</b> (include each year separately. If multiple items are known for each year, add repeating groups so everything can be listed.)</p>
  
  
  <c:choose >
  <c:when test="${appStatus.projdescyn=='true' || appStatus.pendingReview=='true'}" > 
  <%-- READ ONLY ACCESS --%>
    
    
    <table width="100%" class="boxtype">
        <tr>
          <th>Year</th><th>Anticipated Amount</th><th>Vendor or various</th>
        </tr>
           
            <logic:present name="BudgetCollectionBean" property="allSummaryRecords" >
            <logic:iterate name="BudgetCollectionBean" property="allSummaryRecords" id="summaryRecordItem" >   
                      
              <tr>      
                <td width="20%" height="50">
                          <c:if test="${summaryRecordItem.fycode==17}">
                            2016-2017
                          </c:if>
                          <c:if test="${summaryRecordItem.fycode==18}">
                            2017-2018
                          </c:if>
                          <c:if test="${summaryRecordItem.fycode==19}">
                            2018-2019
                          </c:if>
                        </td>
                <td width="20%">$<c:out value="${summaryRecordItem.amountStr}"/></td>
                <td width="20%"><c:out value="${summaryRecordItem.vendor}"/></td>
             </tr>
                     
            </logic:iterate>
            </logic:present>
      </table>  
      
  
  </c:when>
  <c:otherwise>
  <%--  DATA ENTRY ACCESS  --%>
    
    
  
  <html:errors />
  <table width="100%" class="boxtype">
    <tr>
      <th>Year</th><th>Anticipated Amount</th><th>Vendor or various</th><th>Delete</th>
    </tr>
    
        
    <html:form action="/saveLitSummaryBudget" >
    
    
        <logic:present name="BudgetCollectionBean" property="allSummaryRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allSummaryRecords" id="summaryRecordItem" >   
        
          <c:url value="budgetSummaryNav.do" var="deleteURL">
            <c:param name="t" value="confirmdelete" />
            <c:param name="id" value="${summaryRecordItem.id}" />
            <c:param name="p" value="${param.p}" />
            <c:param name="c" value="46" />
            <c:param name="narr" value="40" />
          </c:url>          
          
          <tr>      
            <td width="20%" height="50">
              <html:select name="summaryRecordItem" property="fycode" indexed="true">
                <html:option value="17">2016-2017</html:option>
                <html:option value="18">2017-2018</html:option>
                <html:option value="19">2018-2019</html:option>
              </html:select>
            </td>
            <td width="20%"><html:text name="summaryRecordItem" property="amountStr" indexed="true" />
                        
                <html:hidden name="summaryRecordItem" property="id" indexed="true" />
                <html:hidden name="summaryRecordItem" property="grantId" indexed="true" />
                <html:hidden name="summaryRecordItem" property="expensecode" indexed="true" /></td>
                
            <td width="20%"><html:text name="summaryRecordItem" property="vendor" indexed="true" /></td>               
            <td width="20%"><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
         </tr>
         
        
        </logic:iterate>
        </logic:present>
        
        <tr>
          <td colspan="5" align="center">
              <input type="hidden" name="p" value="${param.p}"/>
              <input type="hidden" name="c" value="46"/>
              <input type="hidden" name="narr" value="40"/>
              <html:submit value="Save"/>
          </td>
        </tr>      
      
    </html:form>
  </table>
  
  
  
  <form method="post" action="budgetSummaryNav.do?t=addrow">    
  <p>
    <input type="submit" value="Add"/>
    Save any changes first before adding a new record.
    <INPUT type="hidden" name="c" value="46"/>
    <input type="hidden" name="narr" value="40"/>
    <input type="hidden" name="p" value="${param.p}"/></p>
  </form>
  
  
  
  </c:otherwise>
  </c:choose>
  
  </body>
</html>