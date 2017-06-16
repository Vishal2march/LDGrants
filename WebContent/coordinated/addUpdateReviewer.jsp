<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  addUpdateReviewer.jsp
 * Creation/Modification History  :
 *
 *     SH       7/25/07 Created    3/11/08 Modified
 *
 * Description
 * This page allows CO/DI/FL/AL/LG admin to add or update a reviewer to the db. Uses struts
 * actions to save.
--%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h4>Update a Reviewer</h4>
  
<table width="90%" align="center" class="boxtype" >
  <tr>
    <td><b>Note if adding reviewer:</b> Adding Reviewers is a 2 step process.  You must create a NYSDS LDAP account for
    the user first, then add the Reviewer information to the database.</td>
  </tr>
  <tr>
    <td>Use the following link to access the NYSDS LDAP system.  You can follow the steps to create a 
    user account for the Reviewer.  The account UserName should be the reviewer's firstName.lastName, with
    a period in between the names.  The system will then create an account and generate a password for
    the user.</td>
  </tr>
  <tr>
    <td align="center"><a href="https://ws04.nyenet.state.ny.us" target="_blank">
      OFT's NYSDS Delegated Admin System</a></td>
  </tr>
</table>
<br/><br/>


<c:choose >
<c:when test="${param.result !=null}">


  <c:if test="${saveStatus==0}">
    The reviewer could not be added. 
  </c:if>
  
  <c:if test="${saveStatus==1}">    
    <table border="1" align="center">
      <tr>
        <th colspan="2">The following information was saved to the database.</th>
      </tr>
      <tr>
        <td>Name</td>
        <td><c:out value="${newReviewer.salutation}"/> <c:out value="${newReviewer.fname}"/> 
        <c:out value="${newReviewer.mname}"/> <c:out value="${newReviewer.lname}"/> 
        </td>
      </tr>
      <tr>
        <td>UserName from NYSDS LDAP</td>
        <td><c:out value="${newReviewer.username}" /></td>
      <tr>
        <td>Title</td>
        <td><c:out value="${newReviewer.title}"/> </td>
      </tr>
       <tr>
        <td>Affiliation</td>
        <td><c:out value="${newReviewer.affiliation}"/> </td>
      </tr>
       <tr>
        <td>Interest</td>
        <td><c:out value="${newReviewer.interest}"/> </td>
      </tr>
       <tr>
        <td>Phone, Extension</td>
        <td><c:out value="${newReviewer.phone}"/>  <c:out value="${newReviewer.phoneext}" /> </td>
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
      <tr>
        <td>Grant Program to Review</td>
        <td><c:if test="${newReviewer.coordinated}">Coordinated<br/></c:if> 
            <c:if test="${newReviewer.discretionary}" >Discretionary<br/></c:if>
            <c:if test="${newReviewer.fliteracy}">Family Literacy<br/></c:if> 
            <c:if test="${newReviewer.aliteracy}" >Adult Literacy</c:if>
            <c:if test="${newReviewer.lgrmif}" >LGRMIF</c:if></td>
      </tr>
    </table>
  </c:if>


</c:when>
<c:otherwise >


<html:errors />

  <table align="center" border="1" width="70%">
  <html:form action="/adminSaveReviewer">
    <tr>
      <td>UserName from NYSDS LDAP</td>
      <td><html:text property="username" /></td>
    </tr>
    <tr>
      <td colspan="2">The UserName is the same as the UserName that was entered for the NYSDS LDAP
        account.  The format for UserName is 'firstName'.'lastName' with a period in between names.
        Please make sure to enter the same UserName as the NYSDS LDAP account UserName. </td>
    </tr>
    <tr>
      <td>Salutation</td>
      <td><html:text property="salutation" /> <html:hidden property="revid" /></td>
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
      <td>Area of Interest</td>
      <td><html:text property="interest" /></td>
    </tr>
    <tr>
      <td>Phone</td>
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
    <tr>
      <td colspan="2">If 'Active' is set to No, the reviewer will not receive emails such as the
      request for participation email, or reviewer assignments emails. The reviewer's information will
      continue to exist in the database, and they can be reset to 'Active' status if needed.</td>
    </tr>
    <tr>
      <td>Active</td>
      <td style="border-width:1.0px; border-style:solid; border-color:rgb(122,150,223);">
        <html:radio property="active" value="Y" />Yes
        <br>
        <html:radio property="active" value="N" />No      
      </td>
    </tr> 
    <c:choose >
      <c:when test="${ReviewerBean.grantprogram=='lg'}">
          <tr>
            <td>SSN</td>
            <td><html:text property="ssn" /></td>
          </tr>
          <tr>
            <td>Vendor ID</td>
            <td><html:text property="vendornum" maxlength="10" /></td>
          </tr>
          <tr>
            <td>RAO?</td>
            <td><html:checkbox property="rao" /></td>
          </tr>
          <tr>
            <td>Report access?</td>
            <td><html:checkbox property="govtemp"/></td>
          </tr>
          <tr>
            <td>Grant Program to Review</td>
            <td><html:checkbox property="lgrmif" />LGRMIF</td>
          </tr>
          <tr>
            <td>Comment</td>
            <td><html:textarea property="comment" rows="4" cols="20" />
                <html:hidden property="discretionary"/>
                <html:hidden property="coordinated"/>
                <html:hidden property="aliteracy"/>
                <html:hidden property="fliteracy"/></td>
          </tr>
      </c:when>
      <c:otherwise >
          <tr>
            <td>Grant Program to Review
            <html:hidden property="ssn"/><html:hidden property="govtemp"/>
            <html:hidden property="rao"/><html:hidden property="comment"/>
            <html:hidden property="vendornum"/><html:hidden property="lgrmif"/>
            </td>
            <td><html:checkbox property="coordinated"/>C/P Coordinated<br/>
                <html:checkbox property="discretionary"/>C/P Discretionary<br/>
                <html:checkbox property="aliteracy"/>Adult Literacy<br/>
                <html:checkbox property="fliteracy"/>Family Literacy</td>
          </tr>
      </c:otherwise>
    </c:choose>
    <tr>
      <td colspan="2" align="center">
      <html:hidden property="grantprogram"/><html:submit value="Save"/></td>
    </tr>
  </html:form>
  </table>
  
  
</c:otherwise>
</c:choose>
  
  </body>
</html>
