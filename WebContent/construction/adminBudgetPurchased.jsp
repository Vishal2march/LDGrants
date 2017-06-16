<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction Application</title>
  </head>
  <body>
  
  <!--- PURCHASED SERVICES --> 

<p class="bdgtitle"><b>Project Budget<br/>
  Purchased Services (Code 40)</b><br/>
  List all services to be purchased for the project, arranged, as appropriate, under Consultant Services or Contracted Services. 
  Attach detailed cost estimates supplied by vendors, quotes and/or bids, or other supporting data in an appendix.<br/>
  <br/>- Consultant Services: include professional and technical advice that will be provided by 
  individuals or groups of individuals.  Consultants are normally retained for a short period 
  to provide advice about specific aspects of the project.  Consultants are normally expected 
  to provide a report of their activities, usually at a time agreed upon before the 
  consultancy begins.  Architectural services are not eligible.  Please see 
  <a href="http://www.nysl.nysed.gov/libdev/excerpts/finished_regs/9012.htm" target="_blank">
  Construction regulations</a> for eligible/ineligible costs.<br/><br/>
  - Contracted Services: include professional or technical activities that will be performed 
  by commercial vendors or qualified individuals.  Contractual services are normally used for
  project activities that cannot be carried out by the institution, or for those activities 
  that can be more economically performed by firms or individuals specializing in a particular
  service. 
  <br/><br/>
  <font color="blue">* Cost is the Cost of project for which funding is being requested.</font></p>

  <table width="100%" summary="for layout only">
    <tr>  
      <td class="dibgtselect">Purchased Services</td>
      
      <td class="dibgt">
      <a href="AdminBudgetSelect?tab=5&p=cn">Supplies & Materials</a></td> 
      
      <td class="dibgt">
      <a href="AdminBudgetSelect?tab=6&p=cn">Equipment</a></td>             
    </tr>
  </table>
  <br/>

  
  <c:if test="${totalsBean.warning =='true'}">
    <p align="center" class="error"><c:out value="${totalsBean.cnMessage}" /></p>
  </c:if>
  
  <form method="post" action="AddBudgetItem">    
   <p><input type="submit" value="Add"/>
    Save any changes first before adding a new record.
    <INPUT type="hidden" name="tab" value="3"> 
    <input type="hidden" name="t" value="4"/>
    <INPUT type="hidden" name="p" value="admncn" /></p>
  </form>
  
  <html:errors />
  <html:form action="/saveCnAdminBudget" >
    <logic:present name="BudgetCollectionBean" property="allContractRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allContractRecords" id="contractItem" >  
     
    <%-- create url that allows for deletion of this record, uses the expense id --%>   
    <c:url value="cnAdminNav.do" var="deleteURL">
        <c:param name="item" value="confirmbdgtdelete" />
        <c:param name="tab" value="3" />
        <c:param name="id" value="${contractItem.id}" />
        <c:param name="p" value="admncn4" />
    </c:url>   
    
    <table class="boxtype" width="100%" summary="for layout only">
      <tr >
        <td>Service Type</td>
        <td>Consultant/Vendor</td>
        <td colspan="2">Description</td>
      </tr>     
      <tr >
        <td><html:text name="contractItem" property="servicetype" indexed="true" maxlength="50" /></td>
        <td><html:text name="contractItem" property="recipient" indexed="true" maxlength="50" /></td>
        <td colspan="2"><html:text name="contractItem" property="servicedescr" indexed="true" maxlength="100" /></td>
      </tr>     
      <tr>
        <td width="25%">Cost<font color="blue">*</font></td>
        <td width="25%">AmtApproved</td>
        <td width="25%">ExpSubmitted</td> 
        <td width="25%">ExpApproved</td>
      </tr>
      <tr>
        <td><html:text name="contractItem" property="grantrequestStr" indexed="true" /></td>
        <td><html:text name="contractItem" property="amountapprovedStr" indexed="true" /></td>
        <td><fmt:formatNumber value="${contractItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>     
        <td><html:text name="contractItem" property="expapprovedStr" indexed="true" />
            <html:hidden name="contractItem" property="expsubmittedStr" indexed="true" /> 
             <html:hidden name="contractItem" property="id" indexed="true" /></td>
      </tr>    
      <tr>
        <td><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
      </tr>
    </table>        
    </logic:iterate>
    </logic:present>
  
  
   <p align="center">
      <INPUT type="hidden" name="currtab" value="3">
      <INPUT type="hidden" name="constructtab" value="4">
      <input type="HIDDEN" name="p" value="cn"/>
      <input type="submit" name="btn" value="Save"/>
   </p>

</html:form>  
  
    
 <table width="80%" summary="for layout only">
  <tr>
    <th colspan="2">Project Costs/Funding</th>
  </tr>
  <tr>
      <td>1. Cost of Project for Which Funding is Being Requested:</td>
      <td><fmt:formatNumber value="${totalsBean.totAmtReq}" type="currency" maxFractionDigits="0" /></td>
  </tr>
  
  <c:choose>
  <c:when test="${thisGrant.fycode < 13}">    
      <tr>
          <td>2. Amount of Public Library Construction Program Funds requested for this project (cannot be more than
                50% of the amounted in question #1 above :</td>
          <td><fmt:formatNumber value="${totalsBean.requestCost}" type="currency" maxFractionDigits="0"/></td>
      </tr>
      <tr>
          <td>3. Maximum Award/Recommendation Amount (50% of question #1 above):</td>
          <td><fmt:formatNumber value="${totalsBean.maxRequestCost}" type="currency" maxFractionDigits="0"/></td>
      </tr>
  </c:when>
  <c:otherwise>
      <tr>
          <td>2. Amount of Public Library Construction Program Funds requested for this project (cannot be more than
                75% of the amounted in question #1 above :</td>
          <td><fmt:formatNumber value="${totalsBean.requestCost}" type="currency" maxFractionDigits="0"/></td>
      </tr>
      <tr>
          <td>3. Maximum Award/Recommendation Amount (75% of question #1 above):</td>
          <td><fmt:formatNumber value="${totalsBean.maxRequestCost}" type="currency" maxFractionDigits="0"/></td>
      </tr>  
  </c:otherwise>
  </c:choose>
  
  <tr>
      <td>4. Amount Recommended by System:</td>
      <td><fmt:formatNumber value="${totalsBean.sysAmtRecommended}" type="currency" maxFractionDigits="0" />  </td>
  </tr>
 </table>
 <br/>
    
        
  <table width="100%" summary="for layout only">
  <tr>
    <td colspan="5"><font color="blue">* Cost is the Cost of project for which funding is being requested.</font></td>
  </tr>
  <tr>
    <td colspan="5"><b>Purchased Service (Code 40) Totals</b></td>
  </tr>
   <tr>
    <td width="20%">Cost<font color="blue">*</font></td>
    <td width="20%"> </td> 
    <td width="20%">Amount Approved</td>
    <td width="20%">Expense Submitted</td>
    <td width="20%">Expense Approved</td>
  </tr>
  <tr>
    <td><fmt:formatNumber value="${totalsBean.conAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td> </td>
    <td><fmt:formatNumber value="${totalsBean.conAmtAppr}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.conExpSub}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.conExpAppr}" type="currency" maxFractionDigits="0" /></td>    
  </tr>
  <tr>
    <td height="15" colspan="5"></td>
  </tr>
  <tr>
    <td colspan="5"><b>Total for all Budget Categories</b></td>
  </tr>
  <tr>   
    <td>Cost<font color="blue">*</font></td>
    <td>Max up to 75% Award</td> 
    <td>Amount Approved</td>
    <td>Expense Submitted</td>
    <td>Expense Approved</td>
  </tr>
  <tr>    
    <td>
      <b><fmt:formatNumber value="${totalsBean.totAmtReq}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.maxRequestCost}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totAmtAppr}" type="currency" maxFractionDigits="0" /></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totExpSub}" type="currency" maxFractionDigits="0" /></b>
    </td>
    <td>
     <b><fmt:formatNumber value="${totalsBean.totExpAppr}" type="currency" maxFractionDigits="0" /></b>
    </td>
  </tr>  
  </table> 
  
  <p>Back to application 
        <c:url var="checklistURL" value="cnAdminNav.do">
            <c:param name="item" value="grant"/> 
            <c:param name="id" value="${grantid}"/>
        </c:url>
    <a href='<c:out value="${checklistURL}"/>'>checklist</a>
  </p>
    
  </body>
</html>