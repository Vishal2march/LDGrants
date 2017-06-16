<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>adminNarrativeBudgetSummary</title>
  </head>
  <body>
  <br/><br/>
  
  
  <c:choose>
  <c:when test="${projNarrative.narrTypeID==38}">
    
      <p align="left"><b>Purchased Services Budget Summary</b></p>
           
      <table width="100%" class="boxtype">
        <tr>
          <th>Year</th><th>Anticipated Amount</th><th>Service Type</th><th>Consultant/Vendor or various</th>
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
                <td width="20%"><c:out value="${summaryRecordItem.serviceType}"/></td>
                 <td width="20%"><c:out value="${summaryRecordItem.vendor}"/></td>
             </tr>
                     
            </logic:iterate>
            </logic:present>    
      </table>
    
  </c:when>
  
  
  
  
  
  <c:when test="${projNarrative.narrTypeID==39}">
  
      <p align="left"><b>Supply/Material Budget Summary</b></p>
     
      <table width="100%" class="boxtype">
        <tr>
          <th>Year</th><th>Anticipated Amount</th><th>Item Description</th><th>Vendor or various</th>
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
                <td width="20%"><c:out value="${summaryRecordItem.description}"/></td>
                <td width="20%"><c:out value="${summaryRecordItem.vendor}"/></td>
             </tr>
                     
            </logic:iterate>
            </logic:present>
      </table>
  
  </c:when>
  
  
  
  
  
  
  <c:when test="${projNarrative.narrTypeID==40}">
  
      <p align="left"><b>Travel Budget Summary</b></p>
      
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
  
  
  
  
  
  
  
  <c:when test="${projNarrative.narrTypeID==121}">
  
      <p align="left"><b>Equipment Budget Summary</b></p>
      
      <table width="100%" class="boxtype">
        <tr>
          <th>Year</th><th>Anticipated Amount</th><th>Item Description</th><th>Vendor or various</th>
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
                <td width="20%"><c:out value="${summaryRecordItem.description}"/></td>
                <td width="20%"><c:out value="${summaryRecordItem.vendor}"/></td>
             </tr>             
            
            </logic:iterate>
            </logic:present>
      </table>
  
  </c:when>
  </c:choose>
  
  
  
  </body>
</html>