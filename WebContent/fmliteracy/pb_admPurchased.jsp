<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <p class="bdgtitle"><b>Project Budget<br/>
  Purchased Services (Code 40)</b><br/>
  List all services to be purchased for the project by service type (ie. consultants, 
  rentals, tuition, printing, communications, and other contractual services).  Attach per 
  diem rate for consultants, cost estimates, bids, or other supporting data.    
  <br/><br/>
  Consultant Services include professional and technical advice that will be provided by 
  individuals or groups of individuals.  Consultants are normally retained for a short period
  to provide advice about specific aspects of the project.  Consultants are normally expected
  to provide a report of their activities, usually at a time agreed upon before the 
  consultancy begins.  Provide the number of days the consultant is being hired for and their
  daily rate.<br/><br/>

  Contracted Services include professional or technical activities that will be performed by 
  commercial vendors or qualified individuals.  Contractual services are normally used for 
  project activities that cannot be carried out by the institution, or for those activities 
  that can be more economically performed by firms or individuals specializing in a particular
  service.
</p><br/>

<%--print warnings for over fund/allocation amts--%>
<c:choose>
<c:when test="${thisGrant.fycode >13}"><%--starting fy2013-14--%>
   <c:forEach var="row" items="${fyWarnings}" >
        <c:if test="${row.warning=='true'}" >
          <p align="center" class="error"><c:out value="${row.litWarning}" /></p>
        </c:if>       
   </c:forEach> 
</c:when>
<c:otherwise>
  <c:forEach var="row" items="${fyTotals}" >
    <c:if test="${row.fycode==thisGrant.fycode || row.fycode==thisGrant.fycode +1}">
        <c:if test="${row.warning=='true'}" >
          <p align="center" class="error"><c:out value="${row.litWarning}" /></p>
        </c:if>        
    </c:if>
  </c:forEach>
</c:otherwise>
</c:choose>
  

  <html:form action="/saveLitAdminBudget">  
  <INPUT type="hidden" name="currtab" value="3">
  <input type="HIDDEN" name="p" value='<c:out value="${p}" />'/>
  <input type="HIDDEN" name="littab" value="4"/>
    
  <table width="95%" align="center" summary="for layout only" >
    <%--<tr>
        <td><b>Admin Instructions:</b> Click the link to 'Add Purchased Approval Record' to add a record for this budget category 
        and fiscal year. Then type in the total 'AmtApproved' for this budget category and fiscal year. 
        Only 1 approval record per category per fiscal year.</td>
    </tr>--%>
    <tr>
        <td height="20"/>
    </tr>
    
    <tr>
      <td><b>Budget Year 1 (<c:out value="${thisGrant.fiscalyear}" />) </b><br/>
      
      <c:url var="add1" value="addLitAdminAward.do">
            <c:param name="item" value="blankrec"/>
            <c:param name="tab" value="3"/>
            <c:param name="fy" value="0"/>
            <c:param name="p" value="${p}"/>
            <c:param name="t" value="4"/>
        </c:url>
        <a href='<c:out value="${add1}"/>' >Add Year 1 Record</a><br/>
        
        
        <c:url var="add1a" value="addLitAdminAward.do">
            <c:param name="item" value="adminrec"/>
            <c:param name="tab" value="3"/>
            <c:param name="fy" value="0"/>
            <c:param name="p" value="${p}"/>
            <c:param name="t" value="4"/>
        </c:url>
        <%--<a href="<c:out value='${add1a}'/>">Add Purchased Services Approval Record for Year 1</a>--%>
      </td>
    </tr>
      <c:set var="fyrec" value="false"/>
      <c:set var="amtreq" value="0"/>
      <c:set var="amtappr" value="0"/>
      <c:set var="expsub" value="0"/>
      <c:set var="expappr" value="0"/>
    <tr>
      <td>           
        <logic:present name="BudgetCollectionBean" property="allContractRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allContractRecords" id="contractItem" >
        <c:if test="${thisGrant.fycode==contractItem.fycode}">   
                    
                      
        <%-- create url that allows for deletion of this record, uses the expense id --%>  
        <c:url value="addLitAdminAward.do" var="deleteURL">
            <c:param name="item" value="confirmbdgtdelete" />
            <c:param name="tab" value="3" />
            <c:param name="id" value="${contractItem.id}" />
            <c:param name="p" value="4" />
        </c:url>
        
        <c:choose>
        <c:when test="${contractItem.categoryTotal=='false'}">
             
        <%--this is for regular applicant budget records--%>
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Service Type</td>
            <td>Consultant/Vendor</td>
            <td>Description</td>
          </tr>     
          <tr >
            <td><html:text name="contractItem" property="servicetype" indexed="true" maxlength="50" /></td>
            <td><html:text name="contractItem" property="recipient" indexed="true" maxlength="50" /></td>
            <td colspan="2"><html:text name="contractItem" property="servicedescr" indexed="true" size="50" maxlength="100" /></td>
          </tr>   
          <tr>
            <td colspan="2">Encumbrance Date (mm/dd/yyyy)</td>    
            <td colspan="2">Check/Journal Entry#</td>
          </tr> 
          <tr>
            <td colspan="2"><html:text name="contractItem" property="encumbranceDateStr" indexed="true" /></td>
            <td colspan="2"><html:text name="contractItem" property="journalEntry" indexed="true" /></td>
          </tr>  
          <tr>
            <td width="25%">AmtRequested</td>
            <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td>
           <%-- <td width="25%">ExpApproved</td>--%>
          </tr>
          <tr>
            <td><html:text name="contractItem" property="grantrequestStr" indexed="true" />
                  <html:hidden name="contractItem" property="id" indexed="true" />
                  <html:hidden name="contractItem" property="fycode" indexed="true" /></td>
            <td><html:text name="contractItem" property="amountapprovedStr" indexed="true" /></td>
            <td><html:text name="contractItem" property="expsubmittedStr" indexed="true" /></td>
            <%--<td><fmt:formatNumber value="${contractItem.expapprovedStr}" type="currency" maxFractionDigits="0"/></td>--%>
          </tr>   
          <tr>
            <td><a href='<c:out value="${deleteURL}" />' >Delete this record</a></td>
          </tr>
        </table>
        </c:when>
        
        <%--this is for admin budget record for approval by category--%>
        <c:otherwise>        
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="3"><b>Admin Purchased Category Approval Record</b></td>            
          </tr>     
          <tr>
            <td width="25%">&nbsp;</td> <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td> <%--<td width="25%">ExpApproved</td>--%>
          </tr>
          <tr>
            <td><html:hidden name="contractItem" property="id" indexed="true" />
                <html:hidden name="contractItem" property="servicetype" indexed="true" />
                <html:hidden name="contractItem" property="recipient" indexed="true" />
                <html:hidden name="contractItem" property="servicedescr" indexed="true" />     
                <html:hidden name="contractItem" property="fycode" indexed="true" />
            </td>
            <td><html:text name="contractItem" property="amountapprovedStr" indexed="true" /></td>
            <td><html:text name="contractItem" property="expsubmittedStr" indexed="true" /></td>
            <%--<html:text name="contractItem" property="expapprovedStr" indexed="true" />--%>
          </tr>  
          <tr>
            <td><a href='<c:out value="${deleteURL}" />' >Delete this record</a></td>
          </tr>
        </table>        
        </c:otherwise>
        </c:choose>        
        
        </c:if>
        </logic:iterate></logic:present>
      </td>
    </tr>
  </table>
 <br/><hr/><br/>    
      
  
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 2 (<c:out value="${thisGrant.fiscalyear +1}" />) </b><br/>
      <c:url var="add2" value="addLitAdminAward.do">
            <c:param name="item" value="blankrec"/>
            <c:param name="tab" value="3"/>
            <c:param name="fy" value="1"/>
            <c:param name="p" value="${p}"/>
            <c:param name="t" value="4"/>
        </c:url>
        <a href='<c:out value="${add2}"/>' >Add Year 2 Record</a><br/>
      
      
        <c:url var="add2a" value="addLitAdminAward.do">
            <c:param name="item" value="adminrec"/>
            <c:param name="tab" value="3"/>
            <c:param name="fy" value="1"/>
            <c:param name="p" value="${p}"/>
            <c:param name="t" value="4"/>
        </c:url>
        <%--<a href="<c:out value='${add2a}'/>">Add Purchased Services Approval Record for Year 2</a>--%>
      </td>
    </tr>
      <c:set var="fyrec" value="false"/>
      <c:set var="amtreq" value="0"/>
      <c:set var="amtappr" value="0"/>
      <c:set var="expsub" value="0"/>
      <c:set var="expappr" value="0"/>
    <tr>
      <td>           
        <logic:present name="BudgetCollectionBean" property="allContractRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allContractRecords" id="contractItem" >
        <c:if test="${contractItem.fycode==thisGrant.fycode + 1}">   
            
            
        <%-- create url that allows for deletion of this record, uses the expense id --%>
        <c:url value="addLitAdminAward.do" var="deleteURL">
            <c:param name="item" value="confirmbdgtdelete" />
            <c:param name="tab" value="3" />
            <c:param name="id" value="${contractItem.id}" />
            <c:param name="p" value="4" />
        </c:url>
        
                
        <c:choose>
        <c:when test="${contractItem.categoryTotal=='false'}">
                
        <%--this is for regular applicant budget records--%>
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Service Type</td>
            <td>Consultant/Vendor</td>
            <td>Description</td>
          </tr>     
           <tr >
            <td><html:text name="contractItem" property="servicetype" indexed="true" maxlength="50" /></td>
            <td><html:text name="contractItem" property="recipient" indexed="true" maxlength="50" /></td>
            <td colspan="2"><html:text name="contractItem" property="servicedescr" indexed="true" size="50" maxlength="100" /></td>
          </tr>       
          <tr>
            <td colspan="2">Encumbrance Date (mm/dd/yyyy)</td>    
            <td colspan="2">Check/Journal Entry#</td>
          </tr> 
          <tr>
            <td colspan="2"><html:text name="contractItem" property="encumbranceDateStr" indexed="true" /></td>
            <td colspan="2"><html:text name="contractItem" property="journalEntry" indexed="true" /></td>
          </tr>  
          <tr>
            <td width="25%">AmtRequested</td>
            <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td>
            <%--<td width="25%">ExpApproved</td>--%>
          </tr>
          <tr>
            <td><html:text name="contractItem" property="grantrequestStr" indexed="true" />
                  <html:hidden name="contractItem" property="id" indexed="true" />
                  <html:hidden name="contractItem" property="fycode" indexed="true" /></td>
            <td><html:text name="contractItem" property="amountapprovedStr" indexed="true" /></td>
            <td><html:text name="contractItem" property="expsubmittedStr" indexed="true" /></td>
            <%--<td><fmt:formatNumber value="${contractItem.expapprovedStr}" type="currency" maxFractionDigits="0"/></td>--%>
          </tr>   
          <tr>
            <td><a href='<c:out value="${deleteURL}" />' >Delete this record</a></td>
          </tr>
        </table>
        </c:when>
        
        <%--this is for admin budget record for approval by category--%>
        <c:otherwise>
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="3"><b>Admin Purchased Category Approval Record</b></td>            
          </tr>     
          <tr>
            <td width="25%">&nbsp;</td> <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td> <%--<td width="25%">ExpApproved</td>--%>
          </tr>
          <tr>
            <td><html:hidden name="contractItem" property="id" indexed="true" />
                <html:hidden name="contractItem" property="servicetype" indexed="true" />
                <html:hidden name="contractItem" property="recipient" indexed="true" />
                <html:hidden name="contractItem" property="servicedescr" indexed="true" />  
                <html:hidden name="contractItem" property="fycode" indexed="true" />
            </td>
            <td><html:text name="contractItem" property="amountapprovedStr" indexed="true" /></td>
            <td><html:text name="contractItem" property="expsubmittedStr" indexed="true" /></td>
            <%--<html:text name="contractItem" property="expapprovedStr" indexed="true" />--%>
          </tr>  
          <tr>
            <td><a href='<c:out value="${deleteURL}" />' >Delete this record</a></td>
          </tr>
        </table>        
        </c:otherwise>
        </c:choose>
                
        </c:if>
        </logic:iterate></logic:present>
      </td>
    </tr>
  </table>
  <br/><hr/><br/>
  
  
  <c:if test="${thisGrant.fycode>13}">
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 3 (<c:out value="${thisGrant.fiscalyear +2}" />) </b><br/>
      <c:url var="add3" value="addLitAdminAward.do">
            <c:param name="item" value="blankrec"/>
            <c:param name="tab" value="3"/>
            <c:param name="fy" value="2"/>
            <c:param name="p" value="${p}"/>
            <c:param name="t" value="4"/>
        </c:url>
        <a href='<c:out value="${add3}"/>' >Add Year 3 Record</a><br/>
        
        
        <c:url var="add3a" value="addLitAdminAward.do">
            <c:param name="item" value="adminrec"/>
            <c:param name="tab" value="3"/>
            <c:param name="fy" value="2"/>
            <c:param name="p" value="${p}"/>
            <c:param name="t" value="4"/>
        </c:url>
        <%--<a href="<c:out value='${add3a}'/>">Add Purchased Services Approval Record for Year 3</a>--%>
      </td>
    </tr>
      <c:set var="fyrec" value="false"/>
      <c:set var="amtreq" value="0"/>
      <c:set var="amtappr" value="0"/>
      <c:set var="expsub" value="0"/>
      <c:set var="expappr" value="0"/>
    <tr>
      <td>           
        <logic:present name="BudgetCollectionBean" property="allContractRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allContractRecords" id="contractItem" >
        <c:if test="${contractItem.fycode==thisGrant.fycode + 2}">   
            
            
        <%-- create url that allows for deletion of this record, uses the expense id --%>
        <c:url value="addLitAdminAward.do" var="deleteURL">
            <c:param name="item" value="confirmbdgtdelete" />
            <c:param name="tab" value="3" />
            <c:param name="id" value="${contractItem.id}" />
            <c:param name="p" value="4" />
        </c:url>
                
        
        <c:choose>
        <c:when test="${contractItem.categoryTotal=='false'}">
               
        <%--this is for regular applicant budget records--%>
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Service Type</td>
            <td>Consultant/Vendor</td>
            <td>Description</td>
          </tr>     
           <tr >
            <td><html:text name="contractItem" property="servicetype" indexed="true" maxlength="50" /></td>
            <td><html:text name="contractItem" property="recipient" indexed="true" maxlength="50" /></td>
            <td colspan="2"><html:text name="contractItem" property="servicedescr" indexed="true" size="50" maxlength="100" /></td>
          </tr>       
          <tr>
            <td colspan="2">Encumbrance Date (mm/dd/yyyy)</td>    
            <td colspan="2">Check/Journal Entry#</td>
          </tr> 
          <tr>
            <td colspan="2"><html:text name="contractItem" property="encumbranceDateStr" indexed="true" /></td>
            <td colspan="2"><html:text name="contractItem" property="journalEntry" indexed="true" /></td>
          </tr>  
          <tr>
            <td width="25%">AmtRequested</td>
            <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td>
            <%--<td width="25%">ExpApproved</td>--%>
          </tr>
          <tr>
            <td><html:text name="contractItem" property="grantrequestStr" indexed="true" />
                  <html:hidden name="contractItem" property="id" indexed="true" />
                  <html:hidden name="contractItem" property="fycode" indexed="true" /></td>
            <td><html:text name="contractItem" property="amountapprovedStr" indexed="true" /></td>
            <td><html:text name="contractItem" property="expsubmittedStr" indexed="true" /></td>
            <%--<td><fmt:formatNumber value="${contractItem.expapprovedStr}" type="currency" maxFractionDigits="0"/></td>--%>
          </tr>      
          <tr>
            <td><a href='<c:out value="${deleteURL}" />' >Delete this record</a></td>
          </tr>
        </table>
        </c:when>
        
        <%--this is for admin budget record for approval by category--%>
        <c:otherwise>
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="3"><b>Admin Purchased Category Approval Record</b></td>            
          </tr>     
          <tr>
            <td width="25%">&nbsp;</td> <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td> <%--<td width="25%">ExpApproved</td>--%>
          </tr>
          <tr>
            <td><html:hidden name="contractItem" property="id" indexed="true" />
                <html:hidden name="contractItem" property="servicetype" indexed="true" />
                <html:hidden name="contractItem" property="recipient" indexed="true" />
                <html:hidden name="contractItem" property="servicedescr" indexed="true" />  
                <html:hidden name="contractItem" property="fycode" indexed="true" />
            </td>
            <td><html:text name="contractItem" property="amountapprovedStr" indexed="true" /></td>
            <td><html:text name="contractItem" property="expsubmittedStr" indexed="true" /></td>
            <%--<html:text name="contractItem" property="expapprovedStr" indexed="true" />--%>
          </tr>  
          <tr>
            <td><a href='<c:out value="${deleteURL}" />' >Delete this record</a></td>
          </tr>
        </table>        
        </c:otherwise>
        </c:choose>
                
        </c:if>
        </logic:iterate></logic:present>
      </td>
    </tr>
  </table>
  <br/><hr/><br/>
  </c:if>
  
 
  
  <c:if test="${lduser.adminfl=='approve'}" >
    <p align="center"><input type="SUBMIT" value="Save" name="btn" /><br/><br/>
        <%--<input type="SUBMIT" value="Copy Amt Requested" name="btn" /> <input type="SUBMIT" value="Copy Exp Submitted" name="btn" />--%>
      </p>
  </c:if>
  
  <br/><br/>
  
  <c:forEach var="fyt" items="${fyTotals}" >
  <p><b>Total Literacy Amt Approved for FY <fmt:formatNumber value="${fyt.fycode}" minIntegerDigits="2" />:&nbsp;
    <fmt:formatNumber value="${fyt.totAmtAppr}" type="currency" maxFractionDigits="0" /></b>
  </p>
  </c:forEach>
  
  </html:form>
  <br/><br/><hr/><br/>  
  
  </body>
</html>
