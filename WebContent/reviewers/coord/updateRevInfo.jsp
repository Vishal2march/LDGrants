<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  updateRevInfo.jsp
 * Creation/Modification History  :
 *     
 *     SHusak       8/6/07     Created
 *
 * Description
 * This page allows the reviewer to update thier contact info in our database,
 * including name, mailing address, phone, email(required). Used for co/di/lit/lg reviewers.
--%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
    
  
  <h4>Update Contact Information</h4>
   

  <c:choose >
  <c:when test="${param.update !=null}" >
  
    <c:if test="${saveStatus==0}">
      The account information could not be updated. 
    </c:if>
    
    <c:if test="${saveStatus==1}">    
    <table border="1" class="graygrid" align="center" summary="for layout only">
      <tr>
        <th colspan="2">The following information was updated in the database.</th>
      </tr>
      <tr>
        <td>Name</td>
        <td><c:out value="${newReviewer.salutation}"/> <c:out value="${newReviewer.fname}"/> 
        <c:out value="${newReviewer.mname}"/> <c:out value="${newReviewer.lname}"/> 
        </td>
      </tr>
      <tr>
        <td>Title</td>
        <td><c:out value="${newReviewer.title}"/> </td>
      </tr>
       <tr>
        <td>Affiliation</td>
        <td><c:out value="${newReviewer.affiliation}"/> </td>
      </tr>
       <tr>
        <td>Phone, Extension</td>
        <td><c:out value="${newReviewer.phone}"/> <c:out value="${newReviewer.phoneext}" /></td>
      </tr>
       <tr>
        <td>Email</td>
        <td><c:out value="${newReviewer.email}"/> </td>
      </tr>
       <tr>
        <td>Address</td>
        <td><c:out value="${newReviewer.address}"/> </td>
      </tr>
       <tr>
        <td>City, State, Zip</td>
        <td><c:out value="${newReviewer.city}"/> <c:out value="${newReviewer.state}"/> 
        <c:out value="${newReviewer.zipcode}"/></td>
      </tr>
    </table>
    </c:if>
  
  
  </c:when>
  <c:otherwise >

  <html:errors />
  
  <html:form action="/updateReviewerInfo" >
  <table align="center" border="1" class="graygrid" width="70%" summary="for layout only">
    <tr>
      <td>Salutation</td>
      <td><html:text property="salutation" />
      <html:hidden property="revid" /><html:hidden property="username" />
      <html:hidden property="active" /><html:hidden property="grantprogram" />
      <html:hidden property="interest" /><html:hidden property="comment" />
      <html:hidden property="rao"/><html:hidden property="govtemp"/></td>
    </tr>
    <tr>
      <td>First Name</td>
      <td><html:text property="fname" /></td>
    </tr>
    <tr>
      <td>Middle Name</td>
      <td><html:text property="mname" /></td>
    </tr>
    <tr>
      <td>Last Name</td>
      <td><html:text property="lname" /></td>
    </tr>
    <tr>
      <td>Title</td>
      <td><html:text property="title" /></td>
    </tr>
    <tr>
      <td>Affiliation</td>
      <td><html:text property="affiliation" /></td>
    </tr>
    <tr>
      <td>Phone (format 111-111-1111)</td>
      <td><html:text property="phone" /></td>
    </tr>
    <tr>
      <td>Phone Extension</td>
      <td><html:text property="phoneext" /><html:hidden property="phoneextId" /></td>
    </tr>
    <tr>
      <td>Email</td>
      <td><html:text property="email" /></td>
    </tr>
    <tr>
      <td>Address</td>
      <td><html:text property="address" /></td>
    </tr>
    <tr>
      <td>City</td>
      <td><html:text property="city" /></td>
    </tr>
    <tr>
      <td>State</td>
      <td><html:text property="state" /></td>
    </tr>
    <tr>
      <td>Zip Code</td>
      <td><html:text property="zipcode" /></td>
    </tr>   
    
      <c:choose>
      <c:when test="${ReviewerBean.grantprogram=='lg'}">
          <tr>
            <td>Social Security ID<br/>
                (format 111-11-1111)</td>
            <td><html:text property="ssn" /></td>
          </tr>
          <tr>
            <td>Vendor ID</td>
            <td><html:text property="vendornum" maxlength="10"/> </td>
          </tr>
      </c:when>  
      <c:otherwise>
          
          <tr>
            <td colspan="2"><html:hidden property="ssn"/><html:hidden property="vendornum"/></td>
          </tr>
      </c:otherwise>
      </c:choose>
    <tr>
      <td colspan="2" align="center"><html:submit value="Submit"/></td>
    </tr>  
  </table>
</html:form>

  </c:otherwise>
  </c:choose>  
  
  
  </body>
</html>
