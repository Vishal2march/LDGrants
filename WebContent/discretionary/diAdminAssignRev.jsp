<%--
 * @author  Stefanie Husak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  diAdminAssignRev.jsp
 * Creation/Modification History  :
 *
 *     SHusak       2/26/08     Created
 *
 * Description
 * This is DI admin page for viewing all reviewers who are assigned to this grant, assigning a review,
 * or deleting an assignment.  Lists the reviewer participation request, and num assigned for fy. Has
 * links to internal reports about reviewres.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h4>Assign Reviewers to this Grant</h4>
     
  <c:choose >
  <c:when test="${param.delete !=null}">
  
    <form method="POST" action="diAdminRevNav.do?item=delete">
      Are you sure you want to delete the assignment for <c:out value="${param.fn}" /> <c:out value="${param.ln}" />?
      <input type="HIDDEN" name="id" value='<c:out value="${param.id}" />'  />
      <input type="HIDDEN" name="m" value="di"  />
      <input type="SUBMIT" value="Delete" />
      <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />
    </form>
  
  </c:when>
  <c:otherwise >
  
  
  
    <table width="90%" align="center" class="boxtype" summary="for layout only">
      <tr>             
        <td><b>Project Num</b></td>
        <td><b>Institution</b></td>
        <td><b>Title</b></td>          
      </tr>        
      <tr>
        <td>03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
            -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
            -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" />
        </td>
        <td ><c:out value="${thisGrant.instName}" /></td>
        <td><c:out value="${thisGrant.title}" /></td>
      </tr>   
    </table>
    <br/><br/>
    
     
    <table width="90%" align="center" class="boxtype" summary="for layout only">
      <tr>
        <th colspan="2">Reports on Reviewer Assignments</th>
      </tr>
      <tr>
        <td>Reviewer Contact Information</td>
        <td><a href="cpAdminReport.do?item=revcontact&fc=5" target="_blank">HTML Report</a></td>
      </tr>
      <c:url var="revavail" value="cpAdminReport.do">
        <c:param name="item" value="availability" />
        <c:param name="fycode" value="${thisGrant.fycode}" />
        <c:param name="fc" value="${thisGrant.fccode}" />
      </c:url>
      <tr>
        <td>Reviewer Availability and Assignments for grant fiscal year</td>
        <td><a href='<c:out value="${revavail}" />' target="_blank">HTML Report</a></td>
      </tr>
            
      <c:url var="revassign" value="cpAdminReport.do">
        <c:param name="item" value="revassign" />
        <c:param name="revfycode" value="${thisGrant.fycode}" />
        <c:param name="fc" value="${thisGrant.fccode}" />
      </c:url>
      <tr>
        <td>Reviewer Assignments by Fiscal Year</td>
        <td><a href='<c:out value="${revassign}" />' target="_blank">HTML Report</a></td>
      </tr>     
      <tr>
        <td height="30" />
      </tr>
    </table>
    <br/><br/>
        
    
    <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <th colspan="4">Reviewer Availability for this Fiscal Year</th>
    </tr>
    <tr>
      <td><b>Name</b></td><td><b>Interest</b></td>
      <td><b>Number Accepted</b></td><td><b>Number Assigned</b></td>
    </tr>
    <c:forEach var="row" items="${allRev}" >
    <tr>  
      <td><c:out value="${row.fname}" /> <c:out value="${row.lname}" /></td>
      <td><c:out value="${row.interest}" /></td>
      <td><c:out value="${row.reviewerAssigns[0].numaccepted}" /></td>
      <td><c:out value="${row.reviewerAssigns[0].numassigned}" />
          <c:if test="${row.reviewerAssigns[0].numassigned > row.reviewerAssigns[0].numaccepted}" >
          <font color="Red">*Warning*</font>
          </c:if>
      </td>
    </tr>
    </c:forEach>    
    </table>
    <br/><br/>
        
    
    <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>  
      <th>Reviewer Assignment</th>
    </tr>
    <form method="POST" action="diAdminRevNav.do?item=assign">    
    <tr>
      <td>Assign this reviewer:
        <select name="revid" >
              <c:forEach var="row" items="${allRev}" >             
                <option value='<c:out value="${row.revid}" />'  >
                    <c:out value="${row.fname}" /> <c:out value="${row.lname}" />
                </option>               
              </c:forEach>
        </select>
        <input type="HIDDEN" name="m" value="di" />
        <input type="SUBMIT" value="Submit" />
      </td>
    </tr>
    </form>
    
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <td>The following reviewers have been assigned to this grant</td>
    </tr>
    <tr>
      <td>
        <table width="100%" summary="for layout only">
          <tr>
            <tD><b>Action</b></tD><td><b>Name</b></td>
            <td><b>Interest</b></td><td><b>Date Assigned</b></td><td><b>Rating Complete</b></td>
          </tr>
          <c:forEach var="assignBean" items="${allAssigned}" >
            <c:url var="deleteURL" value="DiAdminAssign.do">
              <c:param name="delete" value="true"/>
              <c:param name="id" value="${assignBean.reviewerAssigns[0].assignid}" />
              <c:param name="fn" value="${assignBean.fname}" />
              <c:param name="ln" value="${assignBean.lname}" />
            </c:url>
            <tr>
              <td><a href='<c:out value="${deleteURL}" />'> Delete</a></td>
              <td><c:out value="${assignBean.fname}" /> <c:out value="${assignBean.lname}" /></td>
              <td><c:out value="${assignBean.interest}" /></td>
              <td><fmt:formatDate value="${assignBean.reviewerAssigns[0].dateassigned}" pattern="MM/dd/yyyy" /></td>
              <td><c:out value="${assignBean.reviewerAssigns[0].ratingcomp}" /></td>
            </tr>
          </c:forEach>
        </table>
      </td>
    </tr>
  </table>
  
  </c:otherwise>
  </c:choose>
  
  <br/>
  <p align="center">
    <c:url var="backURL" value="diAdminNav.do">
      <c:param name="id" value="${grantid}" />
      <c:param name="item" value="grant" />
    </c:url>
    <a href='<c:out value="${backURL}" />' >Back to Application Checklist</a>
  </p>  
  
  
  </body>
</html>
