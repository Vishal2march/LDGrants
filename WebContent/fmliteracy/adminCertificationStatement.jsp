<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>adminCertificationStatement</title>
  </head>
  <body>
  
  <p><b>Certification Statement</b> - required for submission of application</p>
  
  
  <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <th colspan="2" align="center">
        <c:choose>
        <c:when test="${thisGrant.fccode==40}">        
            Adult Literacy Library Services
        </c:when>
        <c:otherwise>
            Family Literacy Library Services 
        </c:otherwise>
        </c:choose></th>
    </tr>
    <tr>
      <td colspan="2" height="10" />
    </tr>
    <tr>
      <td><b>Project Number</b></td>
      <td>03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
            -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
            -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" />
      </td>
    </tr>
    <tr>
      <td><b>Public Library System:</b></td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td><b>Address:</b></td>
      <td><c:out value="${thisGrant.addr1}" /><br/><c:out value="${thisGrant.city}" />
          <c:out value="${thisGrant.state}" /><c:out value="${thisGrant.zipcd1}" />  
          <c:out value="${thisGrant.zipcd2}" /></td>
    </tr>
    <tr>
      <td><b>County:</b></td>
      <td><c:out value="${thisGrant.county}" /></td>
    </tr>
    <tr>
        <td height="20"/>
    </tr>
    <tr>
      <td><b>State Assembly Districts:</b> <c:forEach var="dist" items="${distBean.assemblyDistricts}" >
                                        <c:out value="${dist}" /> </c:forEach></td>
      <td><b>State Senate Districts:</b> <c:forEach var="sdist" items="${distBean.senateDistricts}" >
                                      <c:out value="${sdist}" /> </c:forEach></td>
    </tr>
    <tr>
        <td><b>State Congressional Districts:</b> <c:forEach var="cdist" items="${distBean.congressDistricts}" >
                                            <c:out value="${cdist}" />
                                          </c:forEach></td>
        <td><b>State Judicial District:</b> <c:out value="${distBean.judicialDistricts}" /></td>
    </tr>
    <tr>
        <td height="20"/>
    </tr>
    <tr>
      <td><b>Project Title:</b></td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>
    <tr>
      <td><b>Project Manager:</b></td>
      <td><c:out value="${coversheetBean.fname}" /> <c:out value="${coversheetBean.lname}" /></td>
    </tr>          
    <tr>
      <td><b>Project Manager Phone:</b></td>
      <td><c:out value="${coversheetBean.phone}" /></td>
    </tr>
    <tr>
      <td><b>Project Manager Email:</b></td>
      <td><c:out value="${coversheetBean.email}" /></td>
    </tr>
    <tr>
        <td height="20"/>
    </tr>
            
    
    
    <c:choose>
    <c:when test="${thisGrant.fccode==40}">     
        <tr>
          <td colspan="2">The annual amount allocated by formula for this agency for each of the three project years under the 2016-2019 Adult Literacy Library Services Program under the provisions of Education Law 273 (1) (h) (2) will be: 
          <fmt:formatNumber value="${allocAmount.initialAlloc}" type="currency" />.   The total amount allocated by formula for the three years will be: 
          <fmt:formatNumber value="${allocAmount.initialAlloc*3}" type="currency" />.   It is understood that the annual formula allocation amount could change as a result of the actual appropriation provided in the annual State budget. </td>
        </tr>
        <tr>
            <td height="20"/>
        </tr>
        <tr>
          <td colspan="2">Per requirements in Education Law 273(1)(h)(2), I hereby certify that this agency will operate the 2016-2019 Adult Literacy Library Services program in direct coordination with at least one public school, college or other organization which operates similar adult literacy programs. </td>
        </tr>
        <tr>
            <td height="20"/>
        </tr>
        <tr>
          <td colspan="2">I hereby certify that this agency is in compliance with applicable State laws and regulations and that the undersigned hereby gives assurance to the New York State Education Department that the statements in the preceding application are true and correct to the best of their knowledge, information, and belief, and that the requested funds will be used to support the activities described. </td>
        </tr>
    </c:when>
    <c:otherwise>
        <tr>
          <td colspan="2">The annual amount allocated by formula for this agency for each of the three project years under the 2016-2019 Family Literacy Library Services Program under the provisions of Education Law 273 (1) (h) (2) will be: 
          <fmt:formatNumber value="${allocAmount.initialAlloc}" type="currency"  />.   The total amount allocated by formula for the three years will be: 
          <fmt:formatNumber value="${allocAmount.initialAlloc*3}" type="currency"  />   It is understood that the annual formula allocation amount could change as a result of the actual appropriation provided in the annual State budget. </td>
        </tr>
        <tr>
            <td height="20"/>
        </tr>
        <tr>
          <td colspan="2">I hereby certify that this agency is in compliance with applicable State laws and regulations and that the undersigned hereby gives assurance to the New York State Education Department that the statements in the preceding application are true and correct to the best of their knowledge, information, and belief, and that the requested funds will be used to support the activities described. </td>
        </tr>          
    </c:otherwise>
    </c:choose>
    
    <tr>
        <td height="20"/>
    </tr>  
    <c:choose>
         <c:when test="${appStatus.allowSubmitInitial=='false'}">
    <tr>
      <td>Date Submitted:<fmt:formatDate value="${maxDate}" pattern="MM/dd/yyyy"/></td>
      <td>      
        <input type="CHECKBOX" name="directorcert" checked="true" disabled="true">      
        Name: 
        <c:out value="${libDirectorBean.salutation}" /> <c:out value="${libDirectorBean.fname}" />
        <c:out value="${libDirectorBean.mname}" /> <c:out value="${libDirectorBean.lname}" />
        <br/>System Director
      </td>
    </tr>
        </c:when>
        <c:when test="${appStatus.allowSubmitInitial=='true'}">
    <tr>
      <td>Date Submitted:<font color="Red">Not Submitted</font> </td>
      <td>      
        <input type="CHECKBOX" name="directorcert" disabled="true">      
        Name: 
        <c:out value="${libDirectorBean.salutation}" /> <c:out value="${libDirectorBean.fname}" />
        <c:out value="${libDirectorBean.mname}" /> <c:out value="${libDirectorBean.lname}" />
        <br/>System Director
      </td>
    </tr>
    </c:when>
    </c:choose>
     <tr>
        <td height="20"/>
    </tr>
    <tr>
      <td colspan="2">NOTE: Dating and checking off this form will submit your application. Once the application is submitted, the System Director must email Carol A. Desch and Barbara Massago at DLDLP@nysed.gov .</td>
    </tr>
  </table>
    
  <br/><br/><br/>
    
    
    
    <table align="center" class="boxtype" width="90%" summary="for layout only">
      <tr>
        <th colspan="3">Certification Statement Records</th>
      </tr>
      <tr>
        <td colspan="3">The following records from the database indicate each time the Certification Statement has
        been checked and submitted</td>
      </tr>
      <tr>
        <td><b>Date</b></td>
        <td><b>Director Name on Form</b></td>
        <td><b>User that Checked and Submitted</b></td>
      </tr>
      
      <c:forEach var="authBean" items="${grantAuth}" >
      <c:if test="${authBean.version=='InstAuth'}" >
        <tr>
          <td ><c:out value="${authBean.authdateStr}" /></td>
          <td><c:out value="${authBean.name}" /></td>
          <td><c:out value="${authBean.user}" /></td>
        </tr>    
      </c:if>
      </c:forEach>          
    </table>
    
    
    
    
    <br/><br/><br/>
    
    
  
  
  
  <c:if test="${param.p=='fl'}" >
      <br/><br/>
      <c:url var="backURL" value="flAdminNav.do">
        <c:param name="id" value="${grantid}" />
        <c:param name="item" value="grant" />
      </c:url>   
      <p align="center"><a href='<c:out value="${backURL}" />' >Back to Application Checklist</a></p>
    </c:if>
    
    <c:if test="${param.p=='al'}" >
      <br/><br/>
      <c:url var="backURL" value="alAdminNav.do">
        <c:param name="id" value="${grantid}" />
        <c:param name="item" value="grant" />
      </c:url>   
      <p align="center"><a href='<c:out value="${backURL}" />' >Back to Application Checklist</a></p>
    </c:if>
    
  
  </body>
</html>