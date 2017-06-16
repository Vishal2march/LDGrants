<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
    <link href="css/archivesLDGrants.css" rel="stylesheet" media="screen" type="text/css" />
  </head>
  <body>
  
  <table summary="for layout only" class="boxtype">
    <tr>
      <td width="40%"><b>Project Number</b></td>
      <td>05<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
              -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
              -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4"/></td>
    </tr>
    <tr>
      <td><b>Institution</b></td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td><b>Category</b></td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>
 </table>
 <br/>
 
  <c:set var="lgtab" value="${param.p}" />
  <p class="bdgtitle"><b>Project Budget</b><br/>  
  
  <c:choose >
  <c:when test="${lgtab=='3'}">
   <b>Equipment (Code 20) </b><br/>
    Describe how the requested equipment will be used to support project activities and goals, and demonstrate why 
    this particular equipment is critical to the project’s success. Demonstrate how such equipment will be used on 
    an ongoing basis after the grant to support records management.  
  </c:when>
  <c:when test="${lgtab=='7'}">
    <b>Supplies and Materials (Code 45)</b><br/>
    Describe how all the supplies and materials requested will support the project activities and goals and why 
    they are essential to the project.   
  </c:when>
  </c:choose>
  </p><br/><br/>
  
  <font color="Red">
  <c:if test="${fyTotal.warning=='true'}">
    <p align="center"><c:out value="${fyTotal.lgWarning}" /> </p>
  </c:if>
  
  <c:if test="${fyTotal.notice=='true'}">
    <p align="center"><c:out value="${fyTotal.lgNotice}" /> </p>
  </c:if>
  
  <c:if test="${totalsBean.amtover=='true'}">
    <p align="center"><c:out value="${totalsBean.amtoverWarning}" /> </p>
  </c:if>
  </font>
  
  
  <html:form action="/saveLgAdminBudget">

  <INPUT type="hidden" name="currtab" value="4">
  <input type="HIDDEN" name="p" value='<c:out value="${pagemodule}"/>'/>
  <input type="HIDDEN" name="lgtab" value='<c:out value="${lgtab}"/>'/>
  
    <logic:present name="BudgetCollectionBean" property="allSupplyRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allSupplyRecords" id="supplyItem" >
      
    <table class="boxtype" width="100%" summary="for layout only">
      <tr>
        <td width="20%">Quantity</td><td width="20%">Description</td>
        <td width="20%">UnitPrice</td><td width="20%">Vendor</td>   
      </tr>      
      <tr >
        <td><c:out value="${supplyItem.quantity}" /></td>
        <td><c:out value="${supplyItem.description}" /></td>
        <td><fmt:formatNumber value="${supplyItem.unitprice}" /></td>
        <td><c:out value="${supplyItem.vendor}" /></td>
      </tr>
      <tr>
        <td>AmtRequested</td><td>AmtAwarded</td>
        <c:if test="${pagemodule=='lg'}">
            <td>AmtAmended</td>
            <td>ExpSubmitted</td><td>ExpApproved</td>
        </c:if>
      </tr>
      <tr >        
        <td><b><fmt:formatNumber value="${supplyItem.grantrequest}" type="currency" maxFractionDigits="0" /></b></td>
        <td><html:text name="supplyItem" property="amountapprovedStr" indexed="true" />
            <html:hidden name="supplyItem" property="id" indexed="true" /></td>
        <c:if test="${pagemodule=='lg'}">
           <td><html:text name="supplyItem" property="amtamendedStr" indexed="true"/></td>
           <td><b><fmt:formatNumber value="${supplyItem.expsubmitted}" type="currency" maxFractionDigits="0" /></b></td>
           <td><html:text name="supplyItem" property="expapprovedStr" indexed="true" />
               </td> 
        </c:if>
      </tr>      
      <tr>
        <td colspan="4"><font color="Red">
            <c:if test="${supplyItem.adminwarning=='true'}">
                <c:out value="${supplyItem.warningmsg}"/>
            </c:if>
        </font></td>
      </tr>
      <tr>
        <td colspan="4" height="25"/>
      </tr>
    </table>
 
 </logic:iterate></logic:present>

    <p align="center">    
    <c:choose>
    <c:when test="${pagemodule=='lgr' && fyTotal.status!='f'}">
            <input type="button" value="Save" disabled="disabled"/>
    </c:when>
    <c:otherwise>
            <input type="submit" name="btn" value="Save"/>
    </c:otherwise>
    </c:choose>    
    </p> 
</html:form>  
  
  
  
  <br/>
  <table width="100%" summary="for layout only">  
  <c:if test="${lgtab=='3'}">
    <tr>
      <td colspan="4"><b>Equipment Totals (Code 20)</b></td>
    </tr>
     <tr>
      <td width="20%">Amount Requested</td>
      <td width="20%">Amount Awarded</td>
      <c:if test="${pagemodule=='lg'}">
         <td width="20%">Amount Amended</td>
         <td width="20%">Expense Submitted</td>
         <td width="20%">Expense Approved</td>
      </c:if>
    </tr>
    <tr>
      <td><fmt:formatNumber value="${totalsBean.equipAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.equipAmtAppr}" type="currency" maxFractionDigits="0" /></td>
      <c:if test="${pagemodule=='lg'}">
         <td><fmt:formatNumber value="${totalsBean.equipAmtAmend}" type="currency" maxFractionDigits="0"/></td>
         <td><fmt:formatNumber value="${totalsBean.equipExpSub}" type="currency" maxFractionDigits="0" /></td>
         <td><fmt:formatNumber value="${totalsBean.equipExpAppr}" type="currency" maxFractionDigits="0" /></td>    
      </c:if>
    </tr>  
  </c:if>
    
  <c:if test="${lgtab=='7'}">
    <tr>
      <td colspan="4"><b>Supplies, Materials Totals (Code 45)</b></td>
    </tr>
     <tr>
      <td width="20%">Amount Requested</td>
      <td width="20%">Amount Awarded</td>
      <c:if test="${pagemodule=='lg'}">
         <td width="20%">Amount Amended</td>
         <td width="20%">Expense Submitted</td>
         <td width="20%">Expense Approved</td>
      </c:if>
    </tr>
    <tr>
      <td><fmt:formatNumber value="${totalsBean.supplyAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.supplyAmtAppr}" type="currency" maxFractionDigits="0" /></td>
      <c:if test="${pagemodule=='lg'}">
         <td><fmt:formatNumber value="${totalsBean.supplyAmtAmend}" type="currency" maxFractionDigits="0" /></td>
         <td><fmt:formatNumber value="${totalsBean.supplyExpSub}" type="currency" maxFractionDigits="0" /></td>
         <td><fmt:formatNumber value="${totalsBean.supplyExpAppr}" type="currency" maxFractionDigits="0" /></td>    
      </c:if>
    </tr>
  </c:if>
  
  <tr>
    <td height="15" colspan="4"></td>
  </tr>
  <tr>
    <td colspan="4"><b>Grand Totals for all Budget Categories</b></td>
  </tr>
  <tr>   
    <td>Amount Requested</td>
    <td>Amount Awarded</td>
    <c:if test="${pagemodule=='lg'}">
       <td>Amount Amended</td>
       <td>Expense Submitted</td>
       <td>Expense Approved</td>
    </c:if>
  </tr>
  <tr>
    <td><b><fmt:formatNumber value="${totalsBean.totAmtReq}" type="currency" maxFractionDigits="0"/></b></td>
    <td><b><fmt:formatNumber value="${totalsBean.totAmtAppr}" type="currency" maxFractionDigits="0" /></b></td>
    <c:if test="${pagemodule=='lg'}">
       <td><b><fmt:formatNumber value="${totalsBean.totAmtAmend}" type="currency" maxFractionDigits="0" /></b></td>
       <td><b><fmt:formatNumber value="${totalsBean.totExpSub}" type="currency" maxFractionDigits="0" /></b></td>
       <td><b><fmt:formatNumber value="${totalsBean.totExpAppr}" type="currency" maxFractionDigits="0" /></b></td>
    </c:if>
  </tr>  
  <tr>
    <td height="20"/>
  </tr>
  <tr>
    <td><b>Amount available to panel:</b></td>
    <td><fmt:formatNumber value="${fyTotal.amtavailable}" type="currency" maxFractionDigits="0" /></td>
  </tr>
  <tr>
    <td><b>Amount awarded by panel:</b></td>
    <td><fmt:formatNumber value="${fyTotal.totAmtAppr}" type="currency" maxFractionDigits="0" /></td>
  </tr>
  <tr>
    <td><b>Difference:</b></td>
    <td><fmt:formatNumber value="${fyTotal.amtdifference}" type="currency" maxFractionDigits="0" /></td>
  </tr>
</table>

  </body>
</html>
