<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
  </head>
  <body>
  
  <h5>Grant Reviewer Assignments</h5>
  
  
  <table summary="for layout only">    
    <tr>             
      <td><b>Project Number</b></td>
      <td><b>Institution</b></td>
    </tr>        
    <tr>
      <td>05<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" />
      </td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>      
    <tr>
        <td height="25"/>
    </tr>
    <tr>
      <td><c:url var="commurl" value="lgReports.do">
            <c:param name="i" value="revcomment" />
            <c:param name="gid" value="${thisGrant.grantid}"/>
        </c:url>
        <c:url var="rateurl" value="lgReports.do">
            <c:param name="i" value="revrating" />
            <c:param name="gid" value="${thisGrant.grantid}"/>
        </c:url>
        <c:url var="deliburl" value="lgReports.do">
            <c:param name="i" value="delibsummary" />
            <c:param name="gid" value="${thisGrant.grantid}"/>
            <c:param name="pgid" value="${panelgrant}"/>
        </c:url>
        <c:url var="editurl" value="lgAdminNav.do">
            <c:param name="item" value="delibform" />
            <c:param name="gid" value="${thisGrant.grantid}"/>
            <c:param name="pgid" value="${panelgrant}"/>
        </c:url>
        <a href='<c:out value="${rateurl}" />' target="_blank">View</a> at-home reviewer ratings<br/>
        <a href='<c:out value="${commurl}" />' target="_blank">View</a> at-home reviewer comments<br/>
        <a href='<c:out value="${deliburl}" />' target="_blank">View</a> deliberation form<br/>
        <a href='<c:out value="${editurl}" />'>Edit</a> deliberation form</td>
    </tr>    
    <tr>
        <td height="20"/>
    </tr>
    </table>
    <br/>
    
    <logic:notEmpty name="allAssigns">
    <table width="90%" summary="for layout only">
        <tr>
            <td colspan="4"><b>Reviewer's assigned to this grant</b></td>
        </tr>
        <tr>
            <td><b>Reviewer</b></td>
            <td><b>Unsubmit Evaluation Form</b></td>
            <td><b>Evaluation Submitted</b></td>
            <td><b>Change Conflict of Interest</b></td>
            <td><b>Conflict of Interest</b></td>    
            <td><b>Update Ratings</b></td>
            <td><b>Delete Ratings**(see notes below)</b></td>
        </tr>
        
        <c:forEach var="row" items="${allAssigns}">
            <c:url var="unsuburl" value="lgAdminNav.do">
                <c:param name="item" value="unsubmit"/>
                <c:param name="praid" value="${row.grantassign}"/>
            </c:url>
            <c:url var="confurl" value="lgAdminNav.do">
                <c:param name="item" value="updateconflict"/>
                <c:param name="praid" value="${row.grantassign}"/>
                <c:param name="conf" value="${!row.conflictinterest}"/>
            </c:url>
            <c:url var="updateurl" value="lgAdminNav.do">
                <c:param name="item" value="evalform"/>
                <c:param name="praid" value="${row.grantassign}"/>
            </c:url>
            <c:url var="delurl" value="lgAdminNav.do">
                <c:param name="item" value="deleterating"/>
                <c:param name="praid" value="${row.grantassign}"/>
            </c:url>
            <tr>
                <td><c:out value="${row.name}"/></td>
                <td><a href='<c:out value="${unsuburl}"/>'>Unsubmit</a></td>
                <td><c:choose><c:when test="${row.ratingcomp=='true'}">Yes</c:when>
                    <c:otherwise>No</c:otherwise></c:choose></td>
                
                <td><a href='<c:out value="${confurl}"/>'>Change Conflict of Interest</a></td>
                <td><c:choose><c:when test="${row.conflictinterest=='true'}">Yes</c:when>
                    <c:otherwise>No</c:otherwise></c:choose></td>  
                <td><a href='<c:out value="${updateurl}"/>'>Update</a></td>
                <td><a href='<c:out value="${delurl}"/>'>Delete</a></td>
            </tr>        
        </c:forEach>     
            <tr>
                <td colspan="5"><br/>Note:  If the reviewer has a conflict of interest, click the
                link to 'Change conflict of interest Yes/No' to set the conflict of interest
                to Yes or No.<br/>
                If you change the conflict of interest to Yes, the reviewer cannot save or submit
                the evaluation form.<br/>
                Delete Ratings option will delete <b>all</b> ratings/comments the reviewer made for
                this grant application.</td>
            </tr>
    </table>
    </logic:notEmpty>
  
  </body>
</html>