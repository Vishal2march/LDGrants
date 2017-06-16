<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  adminViewReviewers.jsp
 * Creation/Modification History  :
 *
 *     SH       7/25/07     Created
 *
 * Description
 * This page allows admin to view all reviewers in the db and choose to
 * update or delete a reviewer entry.
 * update or delete a reviewer entry.  Used by co/di/li/lg admin
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
    
  <h4>Reviewers</h4>
     
  
  <c:choose >
  <c:when test="${param.delete !=null}">
  
    <form method="POST" action="adminReviewerNav.do?item=deleterev">
     Do you want to delete the reviewer <c:out value="${param.fn}" /> 
      <c:out value="${param.ln}" />?
      <br/><br/>
      <input type="SUBMIT" value="Delete" />
      <input type="HIDDEN" name="id" value='<c:out value="${param.id}" />' />
      <input type="HIDDEN" name="m" value='<c:out value="${param.p}" />' />
      <input type="BUTTON" value="Cancel" onclick="Javascript:history.go(-1);" />
    </form>
    
  
  </c:when>
  <c:otherwise >  
  
  <c:if test="${saveStatus==0}">
    <font color="Red">The reviewer could not be deleted. This reviewer is tied to one or more grant ratings.
     Update the reviewer's 'Active' status to No to remove the reviewer from notification mailings.</font>
  </c:if><br/>

    <table width="90%" align="center">
      <tr>
        <th>Reviewer Tasks</th>
      </tr>
      
      <c:url var="addURL" value="adminReviewerNav.do">
        <c:param name="item" value="newrev"/>
        <c:param name="m" value="${param.p}"/>
      </c:url>
      
      <c:choose >
      <c:when test="${param.p=='lg'}">
          <tr>
            <td><a href='<c:out value="${addURL}" />'> Add a Reviewer</a></td>
            
          </tr>
      </c:when>
      <c:when test="${param.p=='co'}">
          <tr>
            <td><a href='<c:out value="${addURL}" />'> Add a Reviewer</a><br/>
                <%--<a href="adminReviewerNav.do?item=loademail&m=co">Send Email to Reviewers</a>--%>
                <a href="CpAdminMassMail.do">Send Email to Reviewers</a><br/>
                <a href="adminEmailNav.do?item=loadsearch&m=co">Search Emails Sent to Reviewer</a></td>
          </tr>
      </c:when>
      <c:when test="${param.p=='di'}">
          <tr>
            <td><a href='<c:out value="${addURL}" />'> Add a Reviewer</a><br/>
            <%--<a href="adminReviewerNav.do?item=loademail&m=di">Send Email to Reviewers</a>--%>
            <a href="CpAdminMassMail.do">Send Email to Reviewers</a><br/>
            <a href="adminEmailNav.do?item=loadsearch&m=di">Search Emails Sent to Reviewer</a></td>
          </tr>
      </c:when>
      <c:when test="${param.p=='li'}">
          <tr>
            <td><a href='<c:out value="${addURL}" />'> Add a Reviewer</a><br/>
                <a href="litAdminRevNav.do?item=loadassign">Assign Reviewers to Grant Proposals</a></td>
          </tr>
      </c:when>
      </c:choose>      
      <tr>
        <td height="20" />
      </tr>
    </table>
    
   
    <table width="90%" align="center" class="boxtype">
      <tr>
        <th align="left">Update</th><th align="left">Delete</th>
        <th align="left">Name</th><th align="left">Reviewer ID<th align="left">Title</th>
        <th align="left">Affiliation</th align="left"><th align="left">Interest</th><th align="left">Active</th>
      </tr>
      
      <c:forEach var="revBean" items="${allRev}">
        <tr>
          <c:url var="upURL" value="adminReviewerNav.do">
            <c:param name="item" value="record"/>
            <c:param name="id" value="${revBean.revid}" />
            <c:param name="m" value="${param.p}"/>
          </c:url>
        
        <c:choose >
        <c:when test="${param.p=='lg'}">            
            <c:url var="delURL" value="LgViewReviewers.do">
              <c:param name="delete" value="true" />
              <c:param name="fn" value="${revBean.fname}" />
              <c:param name="ln" value="${revBean.lname}" />
              <c:param name="id" value="${revBean.revid}" />
            </c:url>
        </c:when>
        <c:when test="${param.p=='co'}">          
            <c:url var="delURL" value="AdminViewRev.do">
              <c:param name="delete" value="true" />
              <c:param name="fn" value="${revBean.fname}" />
              <c:param name="ln" value="${revBean.lname}" />
              <c:param name="id" value="${revBean.revid}" />
            </c:url>
        </c:when>
        <c:when test="${param.p=='di'}">            
            <c:url var="delURL" value="DiViewReviewers.do">
              <c:param name="delete" value="true" />
              <c:param name="fn" value="${revBean.fname}" />
              <c:param name="ln" value="${revBean.lname}" />
              <c:param name="id" value="${revBean.revid}" />
            </c:url>
        </c:when>
        <c:when test="${param.p=='li'}">
            <c:url var="delURL" value="LitViewReviewers.do">
              <c:param name="delete" value="true" />
              <c:param name="fn" value="${revBean.fname}" />
              <c:param name="ln" value="${revBean.lname}" />
              <c:param name="id" value="${revBean.revid}" />
            </c:url>
        </c:when>
        </c:choose>
          
          <td><a href='<c:out value="${upURL}" />' > Update</a></td>
          <td><a href='<c:out value="${delURL}" />' > Delete</a></td>
          <td><c:out value="${revBean.fname}" /> <c:out value="${revBean.mname}" /> 
              <c:out value="${revBean.lname}" /></td>
          <td><c:out value="${revBean.revid}" /></td>
          <td><c:out value="${revBean.title}" /></td>          
          <td><c:out value="${revBean.affiliation}" /></td>
          <td><c:out value="${revBean.interest}" /></td>
          <td><c:out value="${revBean.active}" /></td>
        </tr>
      </c:forEach>
      
      
      <tr>
        <td colspan="8">If a reviewer has filled out a participation form, or they have been assigned a 
        grant to review, they may not be deleted from the database.  If you do not want the reviewer to
        receive any future mailings, click the link to Update reviewer, and set the reviewer's
        'Active' property to 'No'.</td>
      </tr>
    </table>
    
  </c:otherwise>
  </c:choose>
  
  </body>
</html>
