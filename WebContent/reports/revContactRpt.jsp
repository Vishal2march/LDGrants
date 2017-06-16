<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  revContactRpt.jsp
 * Creation/Modification History  :
 *     
 *     SHusak       11/2/07     Created
 *
 * Description
 * This admin jsp rpt lists all reviewers in the database and thier contact info(phone, email, address)
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Report - Reviewer Contact Information</title>
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css"/>
  </head>
  <body>  
  
  
  <table width="100%">
    <tr>
      <th align="center">Reviewer Contact Information Report</th>
    </tr>
  </table>
  <br/><br/>
  
  
  <table width="95%" align="center" border="1" summary="for layout only">
    
    <c:forEach var="row" items="${allRev}" >
    <tr>
      <td>
        <table summary="for layout only">
          <tr>  
            <td><b>Name, Title</b></td>
            <td><c:out value="${row.salutation}" /> <c:out value="${row.fname}" /> <c:out value="${row.lname}" />,  <c:out value="${row.title}" /></td>
          </tr>
          <tr>
            <td><b>Affiliation</b></td>
            <td><c:out value="${row.affiliation}" /></td>
          </tr>
          <tr>
            <td><b>Address</b></td>
            <td><c:out value="${row.address}" /> <c:out value="${row.city}" />
                <c:out value="${row.state}" /> <c:out value="${row.zipcode}" /></td>
          </tr>
          <tr>
            <td><b>Interest/Specialty, Active</b></td>
            <td><c:out value="${row.interest}" />    <c:out value="${row.active}" /></td>      
          </tr>
          <tr>
            <td><b>Phone</b></td>
            <td><c:out value="${row.phone}" /></td>
          </tr>
          <tr>
            <td><b>Email</b></td>
            <td><c:out value="${row.email}" /></td>            
          </tr>
        </table>  
      </td>
    </tr>
    </c:forEach>     
  </table>  
  
  </body>
</html>
