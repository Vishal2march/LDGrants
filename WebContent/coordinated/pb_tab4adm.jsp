<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  pb_tab4adm.jsp
 * Creation/Modification History  :
 *     SHusak       8/17/07     Modified
 *
 * Description
 * This is the supply/material/equip Expense page.  It allows admin to approve budget amounts. 
 * Also displays a running total of requested/approved amounts for all 5 expense categories.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- supplies, materials, equipment -->
<html>
<head>
</head>
<body>
<p class="bdgtitle"><b>Project Budget<br/>
  IV. Supplies, Materials & Equipment</b><br/>
  List all supplies and materials to be purchased for use during the project.  
  DO NOT include supplies to be purchased by your vendor--the vendor's cost estimate 
  will include the cost of materials as well as labor.</p>


<table width="100%" summary="for layout only">
  <tr>
    <td class="adminbgt">
    <%--this link allows Jaws to skip over menu items--%>
    <a href="#bdgcontent"><img src="images/dummy.bmp" border="0" height="1" width="1" alt="skip budget navigation" /></a>
    
    <a href="AdminBudgetSelect?tab=1&p=co">I. Personal Services</a></td>               
    
    <td class="adminbgt">        
    <a href="AdminBudgetSelect?tab=2&p=co">II. Employee Benefits</a></td>        
    
    <td class="adminbgt">        
    <a href="AdminBudgetSelect?tab=3&p=co">III. Contracted Services</a></td>        
    
    <td class="adminbgtselect">IV. Supplies Materials & Equipment</td>
    
    <td class="adminbgt">
    <a href="AdminBudgetSelect?tab=5&p=co">V. Other Expenses</a></td>  
    
    <td class="adminbgt">
    <a href="AdminBudgetSelect?tab=6&p=co">VI. Travel Expenses</a></td>   
  </tr>
</table>
<a name="bdgcontent" id="bdgcontent" />
<br/><br/>


  <c:forEach var="row" items="${fyTotals}" >
    <c:if test="${row.fycode==thisGrant.fycode || row.fycode==thisGrant.fycode +1 || row.fycode==thisGrant.fycode +2}" >
    
      <c:if test="${row.warning=='true'}" >
          <p align="center" class="error"><c:out value="${row.coWarning}" /></p>
        </c:if>
        
        <c:if test="${row.notice=='true'}">
          <p align="center" class="error"> <c:out value="${row.coNotice}" /></p>
        </c:if>
      
    </c:if>
  </c:forEach>


  <html:form action="/saveCoAdminBudget">
  
  <INPUT type="hidden" name="currtab" value="4"><input type="HIDDEN" name="p" value="co"/>
    
  <table width="100%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 1 (<c:out value="${thisGrant.fiscalyear}" />) </b></td>
    </tr>
      <c:set var="fyrec" value="false"/>
      <c:set var="ptot" value="0"/>
      <c:set var="incont" value="0"/>
      <c:set var="amtreq" value="0"/>
      <c:set var="amtappr" value="0"/>
      <c:set var="expsub" value="0"/>
      <c:set var="expappr" value="0"/>
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
            <td><fmt:formatNumber value="${supplyItem.unitprice}" type="currency"/></td>
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
            <td width="14%">InstContrib</td>
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
            <td><html:text name="supplyItem" property="amountapprovedStr" indexed="true" /></td>
            <td><fmt:formatNumber value="${supplyItem.amtamended}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${supplyItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="supplyItem" property="expapprovedStr" indexed="true" />
                <html:hidden name="supplyItem" property="id" indexed="true" /></td>
          </tr>
        </table>
          <c:set var="fyrec" value="true"/>
          <c:set var="ptot" value="${ptot + supplyItem.projtotal}"/>
          <c:set var="incont" value="${incont + supplyItem.instcont}"/>
          <c:set var="amtreq" value="${amtreq + supplyItem.grantrequest}"/>
          <c:set var="amtappr" value="${amtappr + supplyItem.amountapproved}"/>
          <c:set var="expsub" value="${expsub + supplyItem.expsubmitted}"/>
          <c:set var="expappr" value="${expappr + supplyItem.expapproved}"/>
        </c:if>
        </logic:iterate></logic:present>
      </td>
    </tr>
    
    <c:if test="${fyrec==true}">
    <tr>
      <td>   
         <table width="100%">
          <tr>
            <th colspan="6">Supplies, Materials, Equipment Totals - Budget Year 1</th>
          </tr>
          <tr>
            <td>Project Total</td>
            <td>Inst Contrib.</td>
            <td>Amt Requested</td>
            <td>Amt Approved</td>
            <td>Exp Submitted</td>
            <td>Exp Approved</td>
          </tr>
          <tr>
            <td><fmt:formatNumber value="${ptot}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${incont}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${amtreq}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${amtappr}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${expsub}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${expappr}" type="currency" maxFractionDigits="0"/></td>
          </tr>
        </table>
      </td>
    </tr>
    </c:if>
  </table>
  <br/><br/><hr/><br/>
  
  
  
  <table width="100%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 2 (<c:out value="${thisGrant.fiscalyear +1}" />) </b></td>
    </tr>
      <c:set var="fyrec" value="false"/>
      <c:set var="ptot" value="0"/>
      <c:set var="incont" value="0"/>
      <c:set var="amtreq" value="0"/>
      <c:set var="amtappr" value="0"/>
      <c:set var="expsub" value="0"/>
      <c:set var="expappr" value="0"/>
    <tr>
      <td>       
        <logic:present name="BudgetCollectionBean" property="allSupplyRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allSupplyRecords" id="supplyItem" >
        <c:if test="${supplyItem.fycode==thisGrant.fycode +1}">   
                
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
            <td width="14%">InstContrib</td>
            <td width="14%">AmtRequested</td>
            <td width="14%">AmtApproved</td>
            <td width="14%">AmtAmended</td>
            <td width="14%">ExpSubmitted</td>
            <td width="14%">ExpApproved</td>
          </tr>
          <tr>
            <td><fmt:formatNumber value="${supplyItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${supplyItem.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${supplyItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="supplyItem" property="amountapprovedStr" indexed="true" /></td>
            <td><fmt:formatNumber value="${supplyItem.amtamended}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${supplyItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="supplyItem" property="expapprovedStr" indexed="true" />
                <html:hidden name="supplyItem" property="id" indexed="true" /></td>
          </tr>
        </table>
          <c:set var="fyrec" value="true"/>
          <c:set var="ptot" value="${ptot + supplyItem.projtotal}"/>
          <c:set var="incont" value="${incont + supplyItem.instcont}"/>
          <c:set var="amtreq" value="${amtreq + supplyItem.grantrequest}"/>
          <c:set var="amtappr" value="${amtappr + supplyItem.amountapproved}"/>
          <c:set var="expsub" value="${expsub + supplyItem.expsubmitted}"/>
          <c:set var="expappr" value="${expappr + supplyItem.expapproved}"/>
        </c:if>
        </logic:iterate></logic:present>
      </td>
    </tr>
    
    <c:if test="${fyrec==true}">
    <tr>
      <td>   
         <table width="100%">
          <tr>
            <th colspan="6">Supplies, Materials, Equipment Totals - Budget Year 2</th>
          </tr>
          <tr>
            <td>Project Total</td>
            <td>Inst Contrib.</td>
            <td>Amt Requested</td>
            <td>Amt Approved</td>
            <td>Exp Submitted</td>
            <td>Exp Approved</td>
          </tr>
          <tr>
            <td><fmt:formatNumber value="${ptot}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${incont}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${amtreq}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${amtappr}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${expsub}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${expappr}" type="currency" maxFractionDigits="0"/></td>
          </tr>
        </table>
      </td>
    </tr>
    </c:if>
  </table>
  <br/><hr/><br/>
  
  
  <table width="100%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 3 (<c:out value="${thisGrant.fiscalyear +2}" />) </b></td>
    </tr>
      <c:set var="fyrec" value="false"/>
      <c:set var="ptot" value="0"/>
      <c:set var="incont" value="0"/>
      <c:set var="amtreq" value="0"/>
      <c:set var="amtappr" value="0"/>
      <c:set var="expsub" value="0"/>
      <c:set var="expappr" value="0"/>
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
            <td width="14%">InstContrib</td>
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
            <td><html:text name="supplyItem" property="amountapprovedStr" indexed="true" /></td>
            <td><fmt:formatNumber value="${supplyItem.amtamended}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${supplyItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="supplyItem" property="expapprovedStr" indexed="true" />
                <html:hidden name="supplyItem" property="id" indexed="true" /></td>
          </tr>
        </table>
          <c:set var="fyrec" value="true"/>
          <c:set var="ptot" value="${ptot + supplyItem.projtotal}"/>
          <c:set var="incont" value="${incont + supplyItem.instcont}"/>
          <c:set var="amtreq" value="${amtreq + supplyItem.grantrequest}"/>
          <c:set var="amtappr" value="${amtappr + supplyItem.amountapproved}"/>
          <c:set var="expsub" value="${expsub + supplyItem.expsubmitted}"/>
          <c:set var="expappr" value="${expappr + supplyItem.expapproved}"/>
        </c:if>
        </logic:iterate></logic:present>
      </td>
    </tr>
    
    <c:if test="${fyrec==true}">
    <tr>
      <td>   
         <table width="100%">
          <tr>
            <th colspan="6">Supplies, Materials, Equipment Totals - Budget Year 3</th>
          </tr>
          <tr>
            <td>Project Total</td>
            <td>Inst Contrib.</td>
            <td>Amt Requested</td>
            <td>Amt Approved</td>
            <td>Exp Submitted</td>
            <td>Exp Approved</td>
          </tr>
          <tr>
            <td><fmt:formatNumber value="${ptot}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${incont}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${amtreq}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${amtappr}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${expsub}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${expappr}" type="currency" maxFractionDigits="0"/></td>
          </tr>
        </table>
      </td>
    </tr>
    </c:if>
    
    <tr>
      <td><a href="coAdminNav.do?item=fs10a">FS10A Record - Budget Amendment</a></td>
    </tr>
    
    <c:if test="${lduser.admincoor=='approve'}" >
      <tr>
        <td align="center"><input type="SUBMIT" value="Save" name="btn" /><br/><br/>
          <input type="SUBMIT" value="Copy Amt Requested" name="btn" /> <input type="SUBMIT" value="Copy Exp Submitted" name="btn" />
        </td>
      </tr>
    </c:if>
  </table>
  </html:form>
  <br/><br/><hr/><br/><br/>
  

 <table width="100%" summary="for layout only" align="center">
  <tr>
    <td><b>Project Total</b></td>
    <td><b>Inst Contrib.</b></td>
    <td><b>Amt Requested</b></td>
    <td><b>Amt Approved</b></td>
    <td><b>Amt Amended</b></td>
    <td><b>Exp Submitted</b></td>
    <td><b>Exp Approved</b></td>
  </tr>
  <tr>
    <td colspan="7"><i>Supplies, Materials, Equipment Totals for all Years</i></td>
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
    <td height="15" colspan="7"><hr/></td>
  </tr>
  <tr>
      <td colspan="7" ><i>Totals for Year 1</i></td>
    </tr>
    <tr>
      <td></td>
      <td></td>
      <td><fmt:formatNumber type="currency" value='${totalsMap["1"].totAmtReq}' maxFractionDigits="0" /></td> 
      <td><fmt:formatNumber value='${totalsMap["1"].totAmtAppr}' type="currency" maxFractionDigits="0" /></td>
      <td></td>
      <td><fmt:formatNumber value='${totalsMap["1"].totExpSub}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value='${totalsMap["1"].totExpAppr}' type="currency" maxFractionDigits="0" /></td>
    </tr>
    <tr>
      <td height="15" colspan="7"><hr/></td>
    </tr>
    <tr>
      <td colspan="7"><i>Totals for Year 2</i></td>
    </tr>
    <tr>
      <td></td>
      <td></td>
      <td><fmt:formatNumber value='${totalsMap["2"].totAmtReq}' type="currency" maxFractionDigits="0" /></td> 
      <td><fmt:formatNumber value='${totalsMap["2"].totAmtAppr}' type="currency" maxFractionDigits="0" /></td>
      <td></td>
      <td><fmt:formatNumber value='${totalsMap["2"].totExpSub}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value='${totalsMap["2"].totExpAppr}' type="currency" maxFractionDigits="0" /></td>
    </tr>   
    <tr>
      <td height="15" colspan="7"><hr/></td>
    </tr>
    <tr>
      <td colspan="7"><i>Totals for Year 3</i></td>
    </tr>
    <tr>
      <td></td>
      <td></td>
      <td><fmt:formatNumber value='${totalsMap["3"].totAmtReq}' type="currency" maxFractionDigits="0" /></td> 
      <td><fmt:formatNumber value='${totalsMap["3"].totAmtAppr}' type="currency" maxFractionDigits="0" /></td>
      <td></td>
      <td><fmt:formatNumber value='${totalsMap["3"].totExpSub}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value='${totalsMap["3"].totExpAppr}' type="currency" maxFractionDigits="0" /></td>
    </tr>   
    <tr>
      <td height="15" colspan="7"><hr/></td>
    </tr>
  <tr>
    <td colspan="7"><i>Grand Total for all Budget Categories</i></td>
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
  <tr>
    <td height="15" colspan="7"><hr/></td>
  </tr>
  
  <c:forEach var="fyt" items="${fyTotals}" >
  <tr>
    <td colspan="3"><b>Total C/P Coordinated Approved for FY <fmt:formatNumber value="${fyt.fycode}" minIntegerDigits="2" /></b></td>
    <td><b><fmt:formatNumber value="${fyt.totAmtAppr}" type="currency" maxFractionDigits="0" /></b></td>
  </tr>
  </c:forEach>
</table>


<p><c:url var="backURL" value="coAdminNav.do">
      <c:param name="id" value="${grantid}" />
      <c:param name="item" value="grant" />
    </c:url>
  <a href='<c:out value="${backURL}" />' >Back to Application Checklist</a>
</p>

</body>
</html>
