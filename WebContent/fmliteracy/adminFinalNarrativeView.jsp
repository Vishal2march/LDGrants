<%--
 * @author  shusak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 11g
 * Name of the Application        :  adminFinalNarrativeView.jsp
 * Creation/Modification History  :    
 *     SHusak  3/1/16 Created
 *
 * Description
 * This will be new final narrative page for both AL/FL ADMIN, starting for 2016-19.
 * For admin, final narrative is read only.  But admin can edit initial narratives.
 * For the older version of final narrative menus (2013-16 and pre-2013), see liViewFinalNarr.jsp
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>adminFinalNarrativeView</title>
  </head>
  <body>
  
  
  <h4>Final Narratives</h4>
  
  
  <table summary="for layout only" width="100%">
  <tr>
    <td width="30%" valign="top">
  
    
      <c:if test="${param.m=='afinal'}" >
          <%--       ADULT LITERACY FINAL NARRATIVES    --%>
      
        <table class="boxtype" summary="for layout only">
        <tr>
          <td>Goals/Objectives/Activities<br/>         
            <a class="discnarr" href="liAdminNarrative.do?t=adminfrpt&id=122">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=adminfrpt&id=123">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=adminfrpt&id=124">year 3</a>      
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>Summary of Evaluation Outcomes/Outputs<br/>          
            <a class="discnarr" href="liAdminNarrative.do?t=adminfrpt&id=125">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp; 
            <a class="discnarr" href="liAdminNarrative.do?t=adminfrpt&id=126">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp; 
            <a class="discnarr" href="liAdminNarrative.do?t=adminfrpt&id=127">year 3</a>
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>Budget Changes<br/>          
            <a class="discnarr" href="liAdminNarrative.do?t=adminfrpt&id=50">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=adminfrpt&id=64">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=adminfrpt&id=105">year 3</a>
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>Year 3 Only<br/>          
            <a class="discnarr" href="liAdminNarrative.do?t=adminfrpt&id=118">3 Year Summary</a><br/>
            <a class="discnarr" href="liAdminNarrative.do?t=adminfrpt&id=47">Project Continuation</a>
          </td>
        </tr>
        </table>
            
      </c:if>
      
      
      
      
      
      
      <c:if test="${param.m=='ffinal'}" >
          <%--      FAMILY LITERACY FINAL NARRATIVES  --%>      
      
          <table class="boxtype" summary="for layout only">
          <tr>
            <td>Goals/Objectives/Activities<br/>         
              <a class="discnarr" href="liAdminNarrative.do?t=adminfrpt&id=122">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="discnarr" href="liAdminNarrative.do?t=adminfrpt&id=123">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="discnarr" href="liAdminNarrative.do?t=adminfrpt&id=124">year 3</a>      
            </td>
          </tr>
          <tr>
            <td height="15"/>
          </tr>
          <tr>
            <td>Summary of Evaluation Outcomes/Outputs<br/>          
              <a class="discnarr" href="liAdminNarrative.do?t=adminfrpt&id=125">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp; 
              <a class="discnarr" href="liAdminNarrative.do?t=adminfrpt&id=126">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp; 
              <a class="discnarr" href="liAdminNarrative.do?t=adminfrpt&id=127">year 3</a>
            </td>
          </tr>
          <tr>
            <td height="15"/>
          </tr>
          <tr>
            <td>Budget Changes<br/>          
              <a class="discnarr" href="liAdminNarrative.do?t=adminfrpt&id=50">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="discnarr" href="liAdminNarrative.do?t=adminfrpt&id=64">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="discnarr" href="liAdminNarrative.do?t=adminfrpt&id=105">year 3</a>
            </td>
          </tr>
          <tr>
            <td height="15"/>
          </tr>
          <tr>
            <td>Year 3 Only<br/>          
              <a class="discnarr" href="liAdminNarrative.do?t=adminfrpt&id=118">3 Year Summary</a><br/>
              <a class="discnarr" href="liAdminNarrative.do?t=adminfrpt&id=47">Project Continuation</a>
            </td>
          </tr>
          </table>
      
      </c:if>
      
      
      
    </td><%-- table to layout page in 2 columns --%>
    <td valign="top" >
    
        <c:if test="${projNarrative != null}" >
          <table  width="100%" class="boxtype" summary="for layout only">
            <tr bgcolor="Silver">
              <th><c:out value="${projNarrative.narrativeTitle}" /></th>
            </tr>
            <tr>
              <td><c:out value="${projNarrative.narrativeDescr}" /><hr/></td>
            </tr>
            <tr>
              <td><bean:write name="projNarrative" property="narrative" filter="false" /></td>
            </tr>
          </table>
        </c:if> 
    
    </td>
  </tr>
  </table>
  
  
  
  <c:if test="${param.m=='ffinal'}" >
    <br/><br/>
    <c:url var="backURL" value="flAdminNav.do">
      <c:param name="id" value="${grantid}" />
      <c:param name="item" value="grant" />
    </c:url>   
    <p align="center"><a href='<c:out value="${backURL}" />' >Back to Application Checklist</a></p>
  </c:if>
  
  <c:if test="${param.m=='afinal'}" >
    <br/><br/>
    <c:url var="backURL" value="alAdminNav.do">
      <c:param name="id" value="${grantid}" />
      <c:param name="item" value="grant" />
    </c:url>   
    <p align="center"><a href='<c:out value="${backURL}" />' >Back to Application Checklist</a></p>
  </c:if>
  
  </body>
</html>