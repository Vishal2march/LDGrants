<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Literacy Library Services Narratives</title>
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css" />
  </head>
  <body>
  
  <table align="center" summary="for layout only">
    <tr>
      <th colspan="2"><c:if test="${thisGrant.fccode==40}" >Adult</c:if>
      <c:if test="${thisGrant.fccode==42}" >Family</c:if> Literacy Library Services - Project Narratives</th>
    </tr>
    <tr>
      <td>Project Number</td>
      <td>
          03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" />
      </td>
    </tr>
    <tr>
      <td>Project Title</td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>
  </table>
  <br/><br/>
  
  
   <table align="center" width="95%" summary="for layout only"> 
    <tr>
      <th bgcolor="Silver">Summary Description</th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr1" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Project Need and Target Audience</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr26" property="narrative" filter="false"/></td>
    </tr>
    
    
     <c:if test="${thisGrant.fycode<14}">
         <tr>
          <td height="30"></td>
        </tr>  
         <tr>
          <th bgcolor="Silver">Long range plan</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr5" property="narrative" filter="false"/></td>
        </tr>
    </c:if>
    
    
    <c:if test="${thisGrant.fycode<16}">
        <tr>
          <td height="30"></td>
        </tr>  
         <tr>
          <th bgcolor="Silver">Programming</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr27" property="narrative" filter="false"/></td>
        </tr>
    </c:if>
    
    
    <c:if test="${thisGrant.fycode<14}">
        <tr>
          <td height="30"></td>
        </tr>  
         <tr>
          <th bgcolor="Silver">Cooperating Organizations</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr8" property="narrative" filter="false"/></td>
        </tr>
    </c:if>
    
    <tr>
      <td height="30"></td>
    </tr>  
     <tr>
      <th bgcolor="Silver">Project Goals and Objectives</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr28" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
     <tr>
      <th bgcolor="Silver">Activities</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr3" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    
    
    <c:if test="${thisGrant.fccode==42}" >
        <tr>
          <th bgcolor="Silver">Training Plan</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr119" property="narrative" filter="false"/></td>
        </tr>
        <tr>
          <td height="30"></td>
        </tr>     
    </c:if>
    
    
     <tr>
      <th bgcolor="Silver">Timetable</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr12" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
     <tr>
      <th bgcolor="Silver">Project Outputs</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr29" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    
    
    
    <c:if test="${thisGrant.fycode<16}">
         <tr>
          <th bgcolor="Silver">Measure Output</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr30" property="narrative" filter="false"/></td>
        </tr>
        <tr>
          <td height="30"></td>
        </tr>  
    </c:if>
    
    
    
     <tr>
      <th bgcolor="Silver">Project Outcomes</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr31" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
     <tr>
      <th bgcolor="Silver">Measuring Project Outcomes</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr32" property="narrative" filter="false"/></td>
    </tr>
    
    
    
    <c:if test="${thisGrant.fycode<14}">
        <tr>
          <td height="30"></td>
        </tr>  
         <tr>
          <th bgcolor="Silver">Project Continuation</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr33" property="narrative" filter="false"/></td>
        </tr>
        <tr>
          <td height="30"></td>
        </tr>  
         <tr>
          <th bgcolor="Silver">Distributing Information</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr34" property="narrative" filter="false"/></td>
        </tr>
    </c:if>
    
    
    <tr>
      <td height="30"></td>
    </tr>  
     <tr>
      <th bgcolor="Silver">Other Funding Sources</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr36" property="narrative" filter="false"/></td>
    </tr>
    
    <c:if test="${thisGrant.fycode<14}">
        <tr>
          <td height="30"></td>
        </tr>  
         <tr>
          <th bgcolor="Silver">Project Budget- Salaries</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr35" property="narrative" filter="false"/></td>
        </tr>
        <tr>
          <td height="30"></td>
        </tr>  
         <tr>
          <th bgcolor="Silver">Project Budget- Benefits</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr37" property="narrative" filter="false"/></td>
        </tr>
    </c:if>
    
    
    <tr>
      <td height="30"></td>
    </tr>  
     <tr>
      <th bgcolor="Silver">Project Budget- Purchased Services</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr38" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    
    
    <c:choose>
    <c:when test="${thisGrant.fycode<16}">
         <tr>
          <th bgcolor="Silver">Project Budget- Supplies/Equipment</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr39" property="narrative" filter="false"/></td>
        </tr>
        <tr>
          <td height="30"></td>
        </tr>  
    </c:when>
    <c:otherwise>
        <tr>
          <th bgcolor="Silver">Project Budget- Supplies/Materials</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr39" property="narrative" filter="false"/></td>
        </tr>
        <tr>
          <td height="30"></td>
        </tr>  
        <tr>
          <th bgcolor="Silver">Project Budget- Equipment</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr121" property="narrative" filter="false"/></td>
        </tr>
        <tr>
          <td height="30"></td>
        </tr>  
    
    </c:otherwise>
    </c:choose>
    
    
     <tr>
      <th bgcolor="Silver">Project Budget- Travel</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr40" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    </table>
    
    
    
    <br/><br/>
            
    
    
    <c:if test="${thisGrant.fycode>16}">
    
          
      <table align="center" width="95%" class="boxtype">
        <tr>
          <th bgcolor="Silver" colspan="4">Purchased Services Budget Summary</th>
        </tr>
        <tr>
          <th>Year</th><th>Anticipated Amount</th><th>Service Type</th><th>Consultant/Vendor or various</th>
        </tr>    
        <c:forEach var="summaryRecordItem" items="${purchasedSummary}">            
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
        </c:forEach> 
      </table>
   
      <br/><br/>
      
      
       
      <table align="center" width="95%" class="boxtype">
        <tr>
          <th bgcolor="Silver" colspan="4">Supply/Material Budget Summary</th>
        </tr>
        <tr>
          <th>Year</th><th>Anticipated Amount</th><th>Item Description</th><th>Vendor or various</th>
        </tr>
        <c:forEach var="summaryRecordItem" items="${supplySummary}">          
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
         </c:forEach>
      </table>
    
    
      <br/><br/>
      
            
      
      <table align="center" width="95%" class="boxtype">
        <tr>
          <th bgcolor="Silver" colspan="3">Travel Budget Summary</th>
        </tr>
        <tr>
          <th>Year</th><th>Anticipated Amount</th><th>Vendor or various</th>
        </tr>
        <c:forEach var="summaryRecordItem" items="${travelSummary}">            
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
        </c:forEach>
      </table>  
      
      <br/><br/>
      
      
      
      
      <table align="center" width="95%" class="boxtype">
        <tr>
          <th bgcolor="Silver" colspan="4">Equipment Budget Summary</th>
        </tr>
        <tr>
          <th>Year</th><th>Anticipated Amount</th><th>Item Description</th><th>Vendor or various</th>
        </tr>
        <c:forEach var="summaryRecordItem" items="${equipmentSummary}">           
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
         </c:forEach>
      </table>
    
    
      <br/><br/><br/><br/>
    
    </c:if><%--end if 2016-19 FY --%>
    
  </body>
</html>
