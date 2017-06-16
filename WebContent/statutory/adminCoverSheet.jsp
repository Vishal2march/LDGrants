<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  adminCoverSheet.jsp
 * Creation/Modification History  :
 *
 *     SHusak       3/1/07     Created
 *
 * Description
 * This page allows the admin to view a read only version of the
 * coversheet information that was entered by the applicant.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page contentType="text/html;charset=windows-1252"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <title>Admin Cover Sheet</title>
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css" />
  </head>
  <body>

  <h4>Coversheet</h4>

  <table width="90%" align="center" summary="for layout only" class="graygrid">
    <tr>
      <td width="30%" >Sponsoring Institution:</td>
      <td width="70%" >
        <c:out value="${thisGrant.instName}" />
      </td>
    </tr>
    <tr>
      <td>Mailing Address:</td>
      <td><c:out value="${thisGrant.addr1}" /></td>
    </tr>
    <tr>
      <td>Address:</td>
      <td><c:out value="${thisGrant.addr2}" /> </td>
    </tr>
    <tr>
      <td>City, State, Zip:</td>
      <td>
        <c:out value="${thisGrant.city}" />
        <c:out value="${thisGrant.state}" />
        <c:out value="${thisGrant.zipcd1}" /> -
        <c:out value="${thisGrant.zipcd2}" />
      </td>
    </tr>
    <tr>
      <td>Director of Library:</td>
      <td>
        <c:out value="${libDirectorBean.salutation}" /> <c:out value="${libDirectorBean.fname}" />
        <c:out value="${libDirectorBean.mname}" /> <c:out value="${libDirectorBean.lname}" />
      </td>
    </tr>
    <tr>
      <td>Title:</td>
      <td><c:out value="${libDirectorBean.title}" /></td>
    </tr>
  </table>      
  
  
  <table width="90%" align="center" summary="for layout only" class="graygrid">
    <tr bgcolor="Silver">
      <td align="center" colspan="2">Preservation Administrative Officer</td>
    </tr>
    <tr>
      <td width="30%">Name:</td>
      <td width="70%">
        <c:out value="${presOfficerBean.salutation}" /> <c:out value="${presOfficerBean.fname}"/> <c:out value="${presOfficerBean.mname}"/> <c:out value="${presOfficerBean.lname}"/>
      </td>
    </tr>
    <tr>
      <td>Title:</td>
      <td><c:out value="${presOfficerBean.title}"/></td>
    </tr>
    <tr>
      <td>Phone:</td>
      <td><c:out value="${presOfficerBean.phone}"/></td>
    </tr>
    <tr>
      <td>Email:</td>
      <td><c:out value="${presOfficerBean.email}"/></td>
    </tr>
  </table>          
  
   <table width="90%" align="center" summary="for layout only" class="boxtype">
    <tr bgcolor="Silver">
      <td>Summary description of proposed preservation activities: (5-10 sentences)</td>
    </tr>
    <tr>
      <td><bean:write name="projNarrative" property="narrative" filter="false" /></td>
    </tr>
  </table>     
  <br/><br/>
  
  <p align="center">
       <a href='Javascript:history.go(-1);'>Back To Application Checklist</a></p>


 </body>
</html>
