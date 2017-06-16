<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  fs10aPDF.jsp
 * Creation/Modification History  :
 *
 *     SHusak       3/1/07     Created
 *
 * Description
 * This page allows the applicant to view or print a prefilled version of
 * the FS10A budget amendment form.  It will contain the information they entered
 * on the fs10aForm that was saved to the database. 
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>
<pd4ml:transform screenWidth="600" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>FS10A Budget Amendment</title>
  </head>
  <body>
  
  <c:choose>
  <c:when test="${thisGrant.fccode==80}">
    <c:set var="fcpre" value="05"/>
  </c:when>
  <c:otherwise>
    <c:set var="fcpre" value="03"/>
  </c:otherwise>
  </c:choose>
  
  
   <font size="1">
   <table align="center" summary="for layout only">
    <tr>
      <td valign="top">The University of the State of New York		
          <br/>THE STATE EDUCATION DEPARTMENT			
          <br/>(see instructions for mailing address)
      </td>
      <td valign="top">PROPOSED AMENDMENT FOR A<br/>FEDERAL OR STATE PROJECT
          <br/>FS-10-A (03/15) 
      </td>
    </tr>
  </table>
  </font>
  
  
  <font size="1">
  <table align="center" width="100%" style="outline-style:solid; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
  <tr>
    <td colspan="2">Fund Source: <c:out value="${fundsource}"/><br/>
        Fiscal Year: <c:out value="${fyPeriod}"/><br/></td>
  </tr>
  <tr>
    <td>Agency Name and Address</td>
  </tr>
  <tr>
    <td colspan="2"><c:out value="${thisGrant.instName}" /></td>
  </tr>
  <tr>
    <td colspan="2"><c:out value="${thisGrant.addr1}" /><br/><c:out value="${thisGrant.addr2}" /></td>
  </tr>
  <tr>
    <td width="60%"><c:out value="${thisGrant.city}" /> <c:out value="${thisGrant.state}" />
      <c:out value="${thisGrant.zipcd1}" /> <c:out value="${thisGrant.zipcd2}" /></td>
    <td width="40%">County:  <c:out value="${thisGrant.county}" /></td>
  </tr>
  <tr>
    <td width="60%">Agency Code: <c:out value="${thisGrant.sedcode}" /></td>
    <td width="40%">Amendment #: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
  </tr>
  <tr>
    <td>Project #: <c:out value="${fcpre}"/><fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
                  -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
                  -<fmt:formatNumber  value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" />
    </td>
  </tr>
  <tr>
    <td>Contract #: <c:out value="${thisGrant.contractNum}" /></td>
  </tr>
  <tr>
    <td>Contact Person: <c:out value="${presOfficerBean.fname}"/> <c:out value="${presOfficerBean.mname}"/> <c:out value="${presOfficerBean.lname}"/></td>
    <td>Telephone #: <c:out value="${presOfficerBean.phone}" /></td>
  </tr>  
  <tr>
      <td>E-Mail Address: </td>
      <td><c:out value="${presOfficerBean.email}" /></td>
    </tr>   
  </table>
  </font>
 
  
  <font size="1">
  <table align="center" style="outline-style:solid; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
  <tr>
    <th>INSTRUCTIONS</th>
  </tr>
  <tr>
    <td>
     Submit three copies with signatures in blue ink directly to the same State Education Department 
     office where budget was mailed.  DO NOT submit this form to the Grants Finance Unit.<br/>
     Enter whole dollar amounts only.<br/>
     This form need only be submitted for budget changes that require prior approval as follows:<br/>
        Personnel positions, number and type<br/>
        Equipment items having a unit value of $5,000 or more, number and type<br/>
        Minor remodeling<br/>
        Any increase in a budget subtotal (professional salaries, purchased services, travel, etc.) by more than 10 percent or $1,000, whichever is greater<br/>
        Any increase in the total budget amount.<br/>
     Amendment # at top of this page must be completed.<br/>
     Do not use the FS-10-A for requesting a project extension.
    </td>
  </tr>  
  </table>
  </font>

  
  <font size="1">
  <table align="center" summary="for layout only">
  <tr>
    <td>
      <table summary="for layout only" style="outline-style:solid; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;">
        <tr>
          <th colspan="2">CHIEF ADMINISTRATOR'S CERTIFICATION</th>
        </tr>
        <tr>
          <td colspan="2">By signing this report, I certify to the best of my knowledge and belief that the report is true, complete, and accurate, and the expenditures, disbursements, and cash receipts are for the purposes and objectives set forth in the terms and conditions of the Federal (or State) award. I am aware that any false, fictitious, or fraudulent information, or the omission of any material fact, may subject me to criminal, civil, or administrative penalties for fraud, false statements, false claims, or otherwise. (U.S. Code Title 18, Section 1001 and Title 31, Sections 3729-3730 and 3801-3812).
          </td>
        </tr>
        <tr>
          <td>DATE:______________</td>
          <td>SIGNATURE:__________________________________________</td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table width="100%" style="outline-style:solid; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
        <tr>
          <th colspan="2">FOR DEPARTMENT USE ONLY</th>
        </tr>
        <tr>
          <td>Program Approval:______________________</td>
          <td>Date:_______________________</td>
        </tr>
        <tr>
          <td>Finance:</td>
        </tr>
        <tr>
          <td align="center"><hr/>Log</td>
          <td align="center"><hr/>Approved</td>
        </tr>
      </table>
    </td>
  </tr>
  </table>
  </font>
  <br/>
  
  <pd4ml:page.break />
  <font size="1">
  <c:set var="totIncr" value="0"/>
  <c:set var="totDecr" value="0"/>
  <table align="center" border="1" style="outline-style:solid; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
    <tr>
      <th>SUBTOTAL</th>
      <td align="center"><b>EXPLANATION</b><br/>(Provide same detail as required in FS-10 Budget)</td>
      <th>SUBTOTAL INCREASE</th>
      <th>SUBTOTAL DECREASE</th>
    </tr>
    <tr>
      <td height="50">15 Professional Salaries</td>
      <td colspan="3">
        <table width="100%" border="0">          
          <c:forEach var="fsaBean" items="${allFSRecords}" >  
              <c:if test="${fsaBean.expendTypeId =='15'}">
              <tr>
                <td width="50%"><c:out value="${fsaBean.description}" /></td>
                <td width="25%"><fmt:formatNumber value="${fsaBean.amountincr}" type="currency" maxFractionDigits="0" /></td>
                <td><fmt:formatNumber value="${fsaBean.amountdecr}" type="currency" maxFractionDigits="0" /></td>
              </tr>
              <c:set var="totIncr" value="${totIncr + fsaBean.amountincr}" />
              <c:set var="totDecr" value="${totDecr + fsaBean.amountdecr}" />
              </c:if>
            </c:forEach>
        </table>
    </tr>
    <tr>
      <td height="50">16 Support Staff Salaries</td>
      <td colspan="3">
        <table width="100%" border="0">          
          <c:forEach var="fsaBean" items="${allFSRecords}" >  
              <c:if test="${fsaBean.expendTypeId=='16'}">
              <tr>
                <td width="50%"><c:out value="${fsaBean.description}" /></td>
                <td width="25%"><fmt:formatNumber value="${fsaBean.amountincr}" type="currency" maxFractionDigits="0" /></td>
                <td><fmt:formatNumber value="${fsaBean.amountdecr}" type="currency" maxFractionDigits="0" /></td>
              </tr>
              <c:set var="totIncr" value="${totIncr + fsaBean.amountincr}" />
              <c:set var="totDecr" value="${totDecr + fsaBean.amountdecr}" />
              </c:if>
            </c:forEach>
        </table>
    </tr>
    <tr>
      <td height="50">40 Purchased Services</td>
      <td colspan="3">
        <table width="100%" border="0">          
          <c:forEach var="fsaBean" items="${allFSRecords}" >    
              <c:if test="${fsaBean.expendTypeId=='40'}">
              <tr>
                <td width="50%"><c:out value="${fsaBean.description}" /></td>
                <td width="25%"><fmt:formatNumber value="${fsaBean.amountincr}" type="currency" maxFractionDigits="0" /></td>
                <td><fmt:formatNumber value="${fsaBean.amountdecr}" type="currency" maxFractionDigits="0" /></td>
              </tr>
              <c:set var="totIncr" value="${totIncr + fsaBean.amountincr}" />
              <c:set var="totDecr" value="${totDecr + fsaBean.amountdecr}" />
              </c:if>
          </c:forEach>
        </table>
      </td>
    </tr>
    <tr>
      <td height="50">45 Supplies & Materials</td>
      <td colspan="3">
        <table width="100%" border="0">          
          <c:forEach var="fsaBean" items="${allFSRecords}" >  
              <c:if test="${fsaBean.expendTypeId=='45'}">
              <tr>
                <td width="50%"><c:out value="${fsaBean.description}" /></td>
                <td width="25%"><fmt:formatNumber value="${fsaBean.amountincr}" type="currency" maxFractionDigits="0" /></td>
                <td><fmt:formatNumber value="${fsaBean.amountdecr}" type="currency" maxFractionDigits="0" /></td>
              </tr>
              <c:set var="totIncr" value="${totIncr + fsaBean.amountincr}" />
              <c:set var="totDecr" value="${totDecr + fsaBean.amountdecr}" />
              </c:if>
            </c:forEach>
        </table>
    </tr>
    <tr>
      <td height="50">46 Travel Expenses</td>
      <td colspan="3">
        <table width="100%" border="0">          
          <c:forEach var="fsaBean" items="${allFSRecords}" >  
              <c:if test="${fsaBean.expendTypeId=='46'}">              
              <tr>
                <td width="50%"><c:out value="${fsaBean.description}" /></td>
                <td width="25%"><fmt:formatNumber value="${fsaBean.amountincr}" type="currency" maxFractionDigits="0" /></td>
                <td><fmt:formatNumber value="${fsaBean.amountdecr}" type="currency" maxFractionDigits="0" /></td>
              </tr>
              <c:set var="totIncr" value="${totIncr + fsaBean.amountincr}" />
              <c:set var="totDecr" value="${totDecr + fsaBean.amountdecr}" />
              </c:if>
            </c:forEach>
        </table>
    </tr>
    <tr>
      <td height="50">80 Employee Benefits</td>
      <td colspan="3">
        <table width="100%" border="0">          
          <c:forEach var="fsaBean" items="${allFSRecords}" >  
              <c:if test="${fsaBean.expendTypeId=='80'}">
              <tr>
                <td width="50%"><c:out value="${fsaBean.description}" /></td>
                <td width="25%"><fmt:formatNumber value="${fsaBean.amountincr}" type="currency" maxFractionDigits="0" /></td>
                <td><fmt:formatNumber value="${fsaBean.amountdecr}" type="currency" maxFractionDigits="0" /></td>
              </tr>
              <c:set var="totIncr" value="${totIncr + fsaBean.amountincr}" />
              <c:set var="totDecr" value="${totDecr + fsaBean.amountdecr}" />
              </c:if>
            </c:forEach>
        </table>
    </tr>
    <tr>
      <td height="20">90 Indirect Cost</td>
      <td> </td>
      <td> </td>
      <td> </td>
    </tr>
    <tr>
      <td height="50">49 BOCES Services</td>
      <td> </td>
      <td> </td>
      <td> </td>
    </tr>
    <tr>
      <td height="50">30 Minor Remodeling</td>
      <td colspan="3">
        <table width="100%" border="0">          
          <c:forEach var="fsaBean" items="${allFSRecords}" >  
              <c:if test="${fsaBean.expendTypeId=='30'}">
              <tr>
                <td width="50%"><c:out value="${fsaBean.description}" /></td>
                <td width="25%"><fmt:formatNumber value="${fsaBean.amountincr}" type="currency" maxFractionDigits="0" /></td>
                <td><fmt:formatNumber value="${fsaBean.amountdecr}" type="currency" maxFractionDigits="0" /></td>
              </tr>
              <c:set var="totIncr" value="${totIncr + fsaBean.amountincr}" />
              <c:set var="totDecr" value="${totDecr + fsaBean.amountdecr}" />
              </c:if>
            </c:forEach>
        </table>
    </tr>
    <tr>
      <td height="50">20 Equipment</td>
      <td colspan="3">
        <table width="100%" border="0">          
          <c:forEach var="fsaBean" items="${allFSRecords}" >  
              <c:if test="${fsaBean.expendTypeId=='20'}">
              <tr>
                <td width="50%"><c:out value="${fsaBean.description}" /></td>
                <td width="25%"><fmt:formatNumber value="${fsaBean.amountincr}" type="currency" maxFractionDigits="0" /></td>
                <td><fmt:formatNumber value="${fsaBean.amountdecr}" type="currency" maxFractionDigits="0" /></td>
              </tr>
              <c:set var="totIncr" value="${totIncr + fsaBean.amountincr}" />
              <c:set var="totDecr" value="${totDecr + fsaBean.amountdecr}" />
              </c:if>
            </c:forEach>
        </table>
    </tr>
    <tr>
      <td></td>
      <th>Total Increase or Decrease</th>
      <td>(+)<fmt:formatNumber type="currency" value="${totIncr}"/></td>
      <td>(-)<fmt:formatNumber type="currency" value="${totDecr}"/></td>
    </tr>
    <tr>
      <td></td>
      <c:set var="netamt" value="${totIncr - totDecr}"/>
      <th>Net Increase or Decrease</th>
      <td colspan="2"><fmt:formatNumber type="currency" value="${netamt}"/></td>
    </tr>
    <tr>
      <td></td>
      <th>Previous Budget Total</th>
      <td colspan="2"><fmt:formatNumber type="currency" value="${totAmtAppr}"/></td>
    </tr>
    <tr>
      <td></td>
      <c:set var="newtot" value="${totAmtAppr + netamt}" />
      <th>Proposed Amended Total</th>
      <td colspan="2"><fmt:formatNumber type="currency" value="${newtot}"/></td>
    </tr>    
  </table> 
  </font>
  </body>
</html>
</pd4ml:transform>