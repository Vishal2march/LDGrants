<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%--
 * @author  Stefanie Husak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  diViewFinalRpt.jsp
 * Creation/Modification History  :
 *
 *     SHusak       2/5/08     Created
 *
 * Description
 * This page displays di final report. Used for Di admin and di participating read
 * only view.  
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  
  <h4>Final Report Narrative</h4>
     
    <table width="95%" align="center" class="boxtype"  summary="for layout only">
      <tr>
        <td>The Final Report should correspond closely to the plan of work that 
          you submitted.  It should begin with a chronological timetable 
          recording the beginning date for the project; hiring dates and duration of work for 
          personnel hired with grant funds; consultant's schedules and copies of their reports;
          beginning and ending dates for all contractual services; and dates 
          of all other significant activities carried out during the project.  
          The second part of the narrative should provide a description 
          of every aspect of the expenditure of the funds and how they were accomplished. 
          You should note particularly any changes from your original plan of work. <br/><br/>
          The second part of the narrative should provide a description of every aspect of 
          the project and how it was accomplished.  You should note particularly any changes 
          from your original plan of work.  Be generous with examples, 
          and be specific about the names and significance of the items or collections 
          that benefited from your project.  If you have published any articles about 
          your project please append copies of these to your report.<hr/>
        </td>
      </tr>
      <tr>       
        <td><bean:write name="projNarrative" property="narrative" filter="false" /></td>
      </tr>  
    </table>
    
    
    <c:if test="${param.m=='admin'}" >
    <br/>
    <c:url var="backURL" value="diAdminNav.do">
      <c:param name="id" value="${grantid}" />
      <c:param name="item" value="grant" />
    </c:url>
    <p align="center"><a href='<c:out value="${backURL}" />' >Back to Application Checklist</a></p>
  </c:if>
  
  
  </body>
</html>
