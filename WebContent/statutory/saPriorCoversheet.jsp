<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  saPriorCoversheet.jsp
 * Creation/Modification History  :
 *
 *     SHusak       3/1/07     Created
 *
 * Description
 * This page allows the applicant to view a read only version of the
 * prior fiscal year coversheet information.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>View Prior State Aid Cover Sheet</title>
  </head>
  <body>
    
  <h4>Coversheet - Fiscal Year <c:out value="${thisGrant.fiscalyear}" /></h4>
      

  <br/>
  <table align="center" border="1" class="boxtype" width="90%" summary="for layout only">
    <tr>
      <th colspan="2">Statutory Aid Grant Application for Fiscal Year <c:out value="${thisGrant.fiscalyear}" /></th>
    </tr>
    <tr>
      <td><b>Project Number</b></td>
      <td><b>03<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" /></b></td>
    </tr>
    <tr>
      <td><b>Institution</b></td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td><b>Address</b></td>
      <td>
        <c:out value="${thisGrant.addr1}" /> <br/><c:out value="${thisGrant.addr2}" />
      </td>
    </tr>
    <tr>
      <td><b>City, State, Zipcode</b></td>
      <td><c:out value="${thisGrant.city}" /> <c:out value="${thisGrant.state}" /> 
          <c:out value="${thisGrant.zipcd1}" />-<c:out value="${thisGrant.zipcd2}" /></td>
    </tr>
    <tr>
      <td><b>Library Director</b></td>
      <td><c:out value="${libDirectorBean.salutation}" /> <c:out value="${libDirectorBean.fname}" />
      <c:out value="${libDirectorBean.lname}" /></td>
    </tr>
     <tr>
      <td><b>Preservation Administrative Officer</b></td>
      <td>
      <c:out value="${presOfficerBean.salutation}" /> <c:out value="${presOfficerBean.fname}" /> <c:out value="${presOfficerBean.lname}" />
      </td>
    </tr>
     <tr>
      <td><b>Cover Sheet Summary</b></td>
      <td><bean:write name="projNarrative" property="narrative" filter="false" /></td>
    </tr>
    <tr>
      <td><b>FS 20</b> - forms open in new window</td>
      <td><a href="FsFormServlet?i=fs20" target="_blank" > HTML</a><br/>
          <a href="FsFormServlet?i=fs20pdf" target="_blank" > PDF</a>
      </td>
    </tr>
    <tr>
      <td><b>FS10A</b> - forms open in new window</td>
      <td><a href="FsFormServlet?i=fs10a" target="_blank"> HTML</a><br/>
          <a href="FsFormServlet?i=fs10apdf" target="_blank" > PDF</a></td>
    </tr>
  </table>
  
  
  
  <p align="center">
    <a href="saPriorForms.do?item=appstatus" >View Application Submission/Approvals</a><br/>
    <a href="saPriorForms.do?item=auth">View Authorizations</a><br/>
    View Complete Application - <a href="PrintAppServlet?i=app&a=false" target="_blank" >HTML</a>
               &nbsp;(opens in new window)<br/>
    View Complete Application - <a href="PrintAppServlet?i=apppdf&a=false" target="_blank" >PDF</a>
               &nbsp;&nbsp;(opens in new window)<br/>
  </p>
  
  
  </body>
</html>
