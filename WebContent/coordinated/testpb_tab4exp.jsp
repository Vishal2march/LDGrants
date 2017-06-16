<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  pb_tab4exp.jsp
 * Creation/Modification History  :
 *     SHusak       8/13/07     Modified
 *
 * Description
 * This is the supply/material/equip Expense page.  It allows applicants to enter the info
 * required for claiming the expenses - including amounts requested, and to add/delete entries. 
 * Also displays a running total of requested/approved amounts for all 5 expense categories.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<!-- supplies, materials, equipment -->
<html>
<head>
</head>
<body>
<p class="bdgtitle"><b>Project Budget<br/>
  IV. Supplies, Materials & Equipment</b><br/>
  List all supplies and materials to be purchased for use during the project.  
  DO NOT include supplies to be purchased by your vendor--the vendor�s cost estimate 
  will include the cost of materials as well as labor.  Note that although the quantity * unit price of an item
  is calculated as dollars and cents, the amounts entered in Institutional Contribution and Amount Requested
  fields MUST be rounded to whole dollars.</p>


<table width="100%" summary="for layout only">
  <tr>
    <td class="scbgt">
    <%--this link allows Jaws to skip over menu items--%>
    <a href="#bdgcontent"><img src="images/dummy.bmp" border="0" height="1" width="1" alt="skip budget navigation" /></a>
    
    <a href="BudgetSelect?tab=1&p=coe">I. Personal Services</a></td>               
    
    <td class="scbgt">        
    <a href="BudgetSelect?tab=2&p=coe">II. Employee Benefits</a></td>        
    
    <td class="scbgt">        
    <a href="BudgetSelect?tab=3&p=coe">III. Contracted Services</a></td>        
    
    <td class="scbgtselect">IV. Supplies Materials & Equipment</td>
    
    <td class="scbgt">
    <a href="BudgetSelect?tab=5&p=coe">V. Other Expenses</a></td> 
    
    <td class="scbgt">
    <a href="BudgetSelect?tab=6&p=coe">VI. Travel Expenses</a></td>  
  </tr>
</table>
<a name="bdgcontent" id="bdgcontent" />
<br/><br/>



  <a name="year1" />
  <html:errors />
  <html:form action="/updateFinalExpenses">
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 1 (<c:out value="${thisGrant.fiscalyear}" />) </b></td>
    </tr>   
    <tr>
      <td>       
        <logic:present name="BudgetCollectionBean" property="allSupplyRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allSupplyRecords" id="supplyItem" >
        <c:if test="${thisGrant.fycode==supplyItem.fycode}">   
          
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Quantity</td>
            <td>Description</td>
            <td>UnitPrice</td>
            <td>Vendor</td>
            <td>Type</td>     
          </tr>          
          <tr >
            <td><c:out value="${supplyItem.quantity}" /></td>
            <td><c:out value="${supplyItem.description}" /></td>            
            <td><fmt:formatNumber value="${supplyItem.unitprice}" type="currency" /></td>
            <td><c:out value="${supplyItem.vendor}" /></td>
            <td>
                <c:if test="${supplyItem.suppmatCode=='1'}" >
                  Supplies & Materials
                </c:if>
                <c:if test="${supplyItem.suppmatCode=='2'}" >
                  Equipment
                </c:if>            
            </td>
          </tr>
          <tr>
            <td width="14%">ProjTotal</td>
            <td width="14%">InstCont</td>
            <td width="14%">AmtRequested</td>
            <td width="14%">AmtApproved</td>
            <td width="14%">AmtAmended</td>
            <td width="14%">ExpSubmitted</td>
            <td width="14%">ExpApproved</td>
          </tr>
          <tr >
            <td><fmt:formatNumber value="${supplyItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${supplyItem.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${supplyItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><b><fmt:formatNumber value="${supplyItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
            <td><html:text name="supplyItem" property="amtamendedStr" indexed="true" /></td>
            <td><html:text name="supplyItem" property="expsubmittedStr" indexed="true" /></td>
            <td><b><fmt:formatNumber value="${supplyItem.expapproved}" type="currency" maxFractionDigits="0" /></b>
            </td>
          </tr>
            <html:hidden name="supplyItem" property="id" indexed="true" />
            <html:hidden name="supplyItem" property="fycode" indexed="true" />
            <html:hidden name="supplyItem" property="quantity" indexed="true" />
            <html:hidden name="supplyItem" property="description" indexed="true" />
            <html:hidden name="supplyItem" property="unitprice" indexed="true" />
            <html:hidden name="supplyItem" property="vendor" indexed="true" />
            <html:hidden name="supplyItem" property="suppmatCode" indexed="true" />
            <html:hidden name="supplyItem" property="projtotal" indexed="true" />
            <html:hidden name="supplyItem" property="instcont" indexed="true" />
            <html:hidden name="supplyItem" property="grantrequest" indexed="true" />            
            <html:hidden name="supplyItem" property="amountapproved" indexed="true" />
            <html:hidden name="supplyItem" property="expapproved" indexed="true" />     
        </table>
         
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>
  </table>
 <hr/><br/>  
  
  
  
  <a name="year2" />
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 2 (<c:out value="${thisGrant.fiscalyear +1}" />) </b></td>
    </tr>
    
    <tr>
      <td>       
        <logic:present name="BudgetCollectionBean" property="allSupplyRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allSupplyRecords" id="supplyItem" >
        <c:if test="${supplyItem.fycode==thisGrant.fycode +1}">   
                
        <table  width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Quantity</td>
            <td>Description</td>
            <td>UnitPrice</td>
            <td>Vendor</td>
            <td>Type</td>     
          </tr>          
          <tr >
            <td><c:out value="${supplyItem.quantity}" /></td>
            <td><c:out value="${supplyItem.description}" /></td>            
            <td><fmt:formatNumber value="${supplyItem.unitprice}" type="currency" /></td>
            <td><c:out value="${supplyItem.vendor}" /></td>
            <td>
                <c:if test="${supplyItem.suppmatCode=='1'}" >
                  Supplies & Materials
                </c:if>
                <c:if test="${supplyItem.suppmatCode=='2'}" >
                  Equipment
                </c:if>            
            </td>
          </tr>
          <tr>
            <td width="14%">ProjTotal</td>
            <td width="14%">InstCont</td>
            <td width="14%">AmtRequested</td>
            <td width="14%">AmtApproved</td>
            <td width="14%">AmtAmended</td>
            <td width="14%">ExpSubmitted</td>
            <td width="14%">ExpApproved</td>
          </tr>
          <tr >
            <td><fmt:formatNumber value="${supplyItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${supplyItem.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${supplyItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><b><fmt:formatNumber value="${supplyItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
            <td><html:text name="supplyItem" property="amtamendedStr" indexed="true" /></td>
            <td><html:text name="supplyItem" property="expsubmittedStr" indexed="true" /></td>
            <td><b><fmt:formatNumber value="${supplyItem.expapproved}" type="currency" maxFractionDigits="0" /></b>
            </td>
          </tr>
          <html:hidden name="supplyItem" property="id" indexed="true" />
            <html:hidden name="supplyItem" property="fycode" indexed="true" />
            <html:hidden name="supplyItem" property="quantity" indexed="true" />
            <html:hidden name="supplyItem" property="description" indexed="true" />
            <html:hidden name="supplyItem" property="unitprice" indexed="true" />
            <html:hidden name="supplyItem" property="vendor" indexed="true" />
            <html:hidden name="supplyItem" property="suppmatCode" indexed="true" />
            <html:hidden name="supplyItem" property="projtotal" indexed="true" />
            <html:hidden name="supplyItem" property="instcont" indexed="true" />
            <html:hidden name="supplyItem" property="grantrequest" indexed="true" />            
            <html:hidden name="supplyItem" property="amountapproved" indexed="true" />
            <html:hidden name="supplyItem" property="expapproved" indexed="true" />    
        </table>
          
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>    
  </table>
  <hr/><br/>  
  
    
  
  <a name="year3" />
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 3 (<c:out value="${thisGrant.fiscalyear +2}" />) </b></td>
    </tr>   
    <tr>
      <td>       
        <logic:present name="BudgetCollectionBean" property="allSupplyRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allSupplyRecords" id="supplyItem" >
        <c:if test="${supplyItem.fycode==thisGrant.fycode +2}">   
                 
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Quantity</td>
            <td>Description</td>
            <td>UnitPrice</td>
            <td>Vendor</td>
            <td>Type</td>     
          </tr>          
          <tr >
            <td><c:out value="${supplyItem.quantity}" /></td>
            <td><c:out value="${supplyItem.description}" /></td>            
            <td><fmt:formatNumber value="${supplyItem.unitprice}" type="currency" /></td>
            <td><c:out value="${supplyItem.vendor}" /></td>
            <td>
                <c:if test="${supplyItem.suppmatCode=='1'}" >
                  Supplies & Materials
                </c:if>
                <c:if test="${supplyItem.suppmatCode=='2'}" >
                  Equipment
                </c:if>            
            </td>
          </tr>
          <tr>
            <td width="14%">ProjTotal</td>
            <td width="14%">InstCont</td>
            <td width="14%">AmtRequested</td>
            <td width="14%">AmtApproved</td>
            <td width="14%">AmtAmended</td>
            <td width="14%">ExpSubmitted</td>
            <td width="14%">ExpApproved</td>
          </tr>
          <tr >
            <td><fmt:formatNumber value="${supplyItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${supplyItem.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${supplyItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><b><fmt:formatNumber value="${supplyItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
            <td><html:text name="supplyItem" property="amtamendedStr" indexed="true" /></td>
            <td><html:text name="supplyItem" property="expsubmittedStr" indexed="true" /></td>
            <td><b><fmt:formatNumber value="${supplyItem.expapproved}" type="currency" maxFractionDigits="0" /></b>
            </td>
          </tr>
            <html:hidden name="supplyItem" property="id" indexed="true" />
            <html:hidden name="supplyItem" property="fycode" indexed="true" />
            <html:hidden name="supplyItem" property="quantity" indexed="true" />
            <html:hidden name="supplyItem" property="description" indexed="true" />
            <html:hidden name="supplyItem" property="unitprice" indexed="true" />
            <html:hidden name="supplyItem" property="vendor" indexed="true" />
            <html:hidden name="supplyItem" property="suppmatCode" indexed="true" />
            <html:hidden name="supplyItem" property="projtotal" indexed="true" />
            <html:hidden name="supplyItem" property="instcont" indexed="true" />
            <html:hidden name="supplyItem" property="grantrequest" indexed="true" />            
            <html:hidden name="supplyItem" property="amountapproved" indexed="true" />
            <html:hidden name="supplyItem" property="expapproved" indexed="true" />    
        </table>
          
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>
    <tr>
        <td><a href="coApplicantForms.do?item=fs10a">FS10A - Amendment to Budget Item</a></td>
    </tr>
    <logic:notEmpty name="BudgetCollectionBean" property="allSupplyRecords">
        <c:choose >
        <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
          <tr>
            <td align="center"><input type="button" value="Save Supply Expenses" disabled="disabled" /></td>
          </tr>
        </c:when>
        <c:otherwise >
          <tr>
            <td align="center"><input type="HIDDEN" name="currtab" value="4"/>
                               <html:submit value="Save Supply Expenses" /></td>
          </tr>    
        </c:otherwise>
        </c:choose>
    </logic:notEmpty>
  </table>
  </html:form>

  
    
 <table width="95%" summary="for layout only" align="center">
  <tr>
    <td colspan="6" ><b>Supplies, Materials, Equipment Totals for all Years</b></td>
  </tr>
  <tr>
    <td>Project Total</td>
    <td>Inst Contrib.</td>
    <td>Amt Requested</td>
    <td>Amt Approved</td>
    <td>Amt Amended</td>
    <td>Exp Submitted</td>
    <td>Exp Approved</td>
  </tr>
  <tr>
    <td><fmt:formatNumber value="${totalsBean.suppProjTot}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.suppInstCont}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.suppAmtReq}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.suppAmtAppr}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.suppAmtAmend}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.suppExpSub}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.suppExpAppr}" type="currency" maxFractionDigits="0"/></td>    
  </tr>
  <tr>
    <td height="15" colspan="6"><hr/></td>
  </tr>
  <tr>
    <td colspan="6"><b>Grand Total for all Budget Categories</b></td>
  </tr>
  <tr>   
    <td>Project Total</td>
    <td>Inst Contrib.</td>
    <td>Amt Requested</td>
    <td>Amt Approved</td>
    <td>Amt Amended</td>
    <td>Exp Submitted</td>
    <td>Exp Approved</td>
  </tr>
  <tr>
    <td >
      <b><fmt:formatNumber value="${totalsBean.totProjTot}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totInstCont}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td >
      <b><fmt:formatNumber value="${totalsBean.totAmtReq}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totAmtAppr}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totAmtAmend}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totExpSub}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td>
     <b><fmt:formatNumber value="${totalsBean.totExpAppr}" type="currency" maxFractionDigits="0"/></b>
    </td>
  </tr> 
</table>

</body>
</html>
