<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  editDecision.jsp
 * Description
 * This page is for lgrmif reviewers/rao's to edit the decision notes for a project.
 * After delib meeting, RAO'S can view all decision notes for their region, and
 * edit - until the panel's access level is set to 'N' for none.  
--%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
  </head>
  <body>
  
  <h5>Edit Decision Notes</h5>
  
  
  <table width="95%" summary="for layout only">
    <tr>
      <td width="40%"><b>Project Number</b></td>
      <td>05<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
              -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
              -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4"/></td>
    </tr>
    <tr>
      <td><b>Institution</b></td>
      <td><c:out value="${thisGrant.instName}" />
          <c:if test="${thisGrant.dorisflag=='true'}"> - <c:out value="${thisGrant.dorisname}"/></c:if></td>
    </tr>
    <tr>
      <td><b>Category</b></td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>
    <tr>
        <td colspan="2" height="25"/>
    </tr>
    <tr>
        <td><a href="PrintAppServlet?i=cover" target="_blank">Application Sheet - HTML</a></td>
        <td><a href="PrintAppServlet?i=coverpdf" target="_blank" >Application Sheet - PDF</a></td>
      </tr>
      <tr>       
        <td><a href="PrintAppServlet?i=narr" target="_blank">Project Narrative - HTML</a></td>
        <td><a href="PrintAppServlet?i=narrpdf" target="_blank" >Project Narrative - PDF</a></td>
      </tr> 
      <tr>       
        <td><a href="PrintAppServlet?i=budget&a=true" target="_blank">Project Budget - HTML</a></td>
        <td><a href="PrintAppServlet?i=budgetpdf&a=true" target="_blank" >Project Budget - PDF</a></td>
      </tr> 
      <tr>       
        <td><a href="PrintAppServlet?i=vq" target="_blank">LG-VQ Form - HTML</a></td>
        <td><a href="PrintAppServlet?i=vqpdf" target="_blank" >LG-VQ Form - PDF</a></td>
      </tr>
      
      <%--per FC 8/15/13, IM form not needed starting 2014-15  --%>
      <c:if test="${thisGrant.fycode<15}">
      <tr>       
        <td><a href="PrintAppServlet?i=im" target="_blank">LG-IM Form - HTML</a></td>
        <td><a href="PrintAppServlet?i=impdf" target="_blank" >LG-IM Form - PDF</a></td>
      </tr> 
      </c:if>
      
      <tr>
        <td><a href="FsFormServlet?i=fs20" target="_blank">Proposed Budget Summary (FS-20) - HTML</a></td>
        <td><a href="FsFormServlet?i=fs20pdf" target="_blank">Proposed Budget Summary (FS-20) - PDF</a></td>        
      </tr>   
      <tr>
        <td><a href="lgReviewer.do?item=attachment">Attachments</a></td>
      </tr>
      
      <c:choose>
      <c:when test="${PanelReviewBean.status=='n'}">
          <tr>
            <td>Comments not available<br/>
                Scores not available</td>
          </tr>
      </c:when>
      <c:otherwise>
      
        <c:url var="commurl" value="lgReports.do">
            <c:param name="i" value="revcomment" />
            <c:param name="gid" value="${thisGrant.grantid}"/>
        </c:url>
        <c:url var="rateurl" value="lgReports.do">
            <c:param name="i" value="revrating" />
            <c:param name="gid" value="${thisGrant.grantid}"/>
        </c:url>
        
        <tr>
            <td><a href='<c:out value="${commurl}"/>' target="_blank">Reviewer Comments Report</a><br/>
                <a href='<c:out value="${rateurl}"/>' target="_blank">Reviewer Scores Report</a></td>
        </tr>
      
      </c:otherwise>
      </c:choose>
  </table>
  <br/><br/>
  
  
  <table width="90%" summary="for layout only">
    <tr>
      <td>Recommendation (F, M, or N):</td>
      <td><c:out value="${PanelReviewBean.recommendation}"/></td>
    </tr>
    <tr>
      <td>Recommended Amount:</td>
      <td><fmt:formatNumber value="${PanelReviewBean.recommendamt}" type="currency" minFractionDigits="0"/></td>
    </tr>    
    <tr>
      <td>Final Score</td>
      <td><c:out value="${PanelReviewBean.finalscore}"/></td>
    </tr>
    <tr>
      <td>Bonus Scoring</td>
    </tr>
    <tr>
      <td>1. Cooperative Project  (10 Points)</td>
      <c:if test="${CoverBean.cooperative}"><td>10</td></c:if>
    </tr>
    <tr>
      <td>2. 1st Time Inventory & Planning  (5 points)</td>
      <c:if test="${CoverBean.inventory}"><td>5</td></c:if>
    </tr>
    <tr>
      <td>3. Electronic records inventory projects   (5 points)</td>
      <c:if test="${CoverBean.recordsmgmt}"><td>5</td></c:if>
    </tr>
    <tr>
      <td>4. Email Management projects (5 points)</td>
      <c:if test="${CoverBean.emailmgmt}"><td>5</td></c:if>
    </tr>
    <tr>
        <td>Total Final Score</td>
        <td><c:out value="${PanelReviewBean.finalscore + CoverBean.score}" /></td>
    </tr>
    <tr>
        <td height="25"/>
    </tr>
    
    
    <c:choose>
    <c:when test="${PanelReviewBean.status=='n'}">
        <tr>
            <td colspan="2"><b>Decision Notes</b><br/>
            <c:out value="${PanelReviewBean.decisionnotes}" /></td>
        </tr>
    </c:when>
    <c:otherwise>
        <html:form action="/editDecision">
        <tr>
            <td colspan="2">Decision Notes<br/>
            <html:textarea property="decisionnotes" rows="10" cols="60"/></td>
        </tr>
        <tr>
            <td><html:hidden property="grantid"/><html:hidden property="panelgrantid"/>
                <html:hidden property="status"/><html:submit value="Save"/></td>
        </tr>
        </html:form>
    
    </c:otherwise>
    </c:choose>
  </table>
    
  </body>
</html>