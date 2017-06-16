<%--
 * @author  shusak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 11g
 * Name of the Application        :  adminNarrativeMenuNew.jsp
 * Creation/Modification History  :    
 *     SHusak  1/19/16 Created
 *
 * Description
 * This will be new narrative menu for both AL/FL ADMIN, starting for 2016-19.
 * For the older 2 versions of narrative menus (2014-16 and pre-2014), see adminNarrativeMenu.jsp
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <title>adminNarrativeMenuNew</title>
  </head>
  <body>
  
  
  
  <c:if test="${param.p=='fl'}" >
  <%--FAMILY LITERACY LINKS--%>
  <table class="boxtype" summary="for layout only">
        <tr>
          <th>Narratives</th>
        </tr>  
        <tr>
          <td>I. Abstract         
            <br/><a class="discnarr" href="liAdminNarrative.do?t=adminnarr&id=1">a. Summary Description</a>
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>II. Need, Target Audience 
            <br/><a class="discnarr" href="liAdminNarrative.do?t=adminnarr&id=26">a. Project Need and Target Audience</a>
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>III. Project Description 
            <br/><a class="discnarr" href="liAdminNarrative.do?t=adminnarr&id=28">a. Project Goals and Objectives</a>
            <br/><a class="discnarr" href="liAdminNarrative.do?t=adminnarr&id=3">b. Activities</a>
            <br/><a class="discnarr" href="liAdminNarrative.do?t=adminnarr&id=119">c. Training Plan</a>
            <br/><a class="discnarr" href="liAdminNarrative.do?t=adminnarr&id=12">d. Timetable</a>
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>IV. Evaluation/Outcomes 
            <br/><a class="discnarr" href="liAdminNarrative.do?t=adminnarr&id=120">a. NYSL prescribed training Outcomes and Outputs</a>
            <br/><a class="discnarr" href="liAdminNarrative.do?t=adminnarr&id=29">b. Project Outputs</a>
            <br/><a class="discnarr" href="liAdminNarrative.do?t=adminnarr&id=31">c. Project Outcomes</a>
            <br/><a class="discnarr" href="liAdminNarrative.do?t=adminnarr&id=32">d. Measuring Project Outcomes</a>
          </td>
        </tr>        
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>V. Budget Narrative            
            <br/><a class="discnarr" href="liAdminNarrative.do?t=adminnarr&id=38">a. Purchased Services</a>
            <br/><a class="discnarr" href="liAdminNarrative.do?t=adminnarr&id=39">b. Supplies/Materials</a>
            <br/><a class="discnarr" href="liAdminNarrative.do?t=adminnarr&id=121">c. Equipment</a>
            <br/><a class="discnarr" href="liAdminNarrative.do?t=adminnarr&id=40">d. Travel Expenses</a>
            <br/><a class="discnarr" href="liAdminNarrative.do?t=adminnarr&id=36">e. Other Funding Sources</a>
          </td>
        </tr>
      </table>    
  
  </c:if>
  
  
  
  
  
  <c:if test="${param.p=='al'}" >
  <%--ADULT LITERACY LINKS--%>
   <%--for FY2016-19--%>
      <table class="boxtype" summary="for layout only">
        <tr>
          <th>Narratives</th>
        </tr>  
        <tr>
          <td>I. Abstract         
            <br/><a class="discnarr" href="liAdminNarrative.do?t=aadminnarr&id=1">a. Summary Description</a>
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>II. Need, Target Audience 
            <br/><a class="discnarr" href="liAdminNarrative.do?t=aadminnarr&id=26">a. Project Need and Target Audience</a>            
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>III. Project Description 
            <br/><a class="discnarr" href="liAdminNarrative.do?t=aadminnarr&id=28">a. Project Goals and Objectives</a>
            <br/><a class="discnarr" href="liAdminNarrative.do?t=aadminnarr&id=3">b. Activities</a>
            <br/><a class="discnarr" href="liAdminNarrative.do?t=aadminnarr&id=12">c. Timetable</a>
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>IV. Evaluation/Outcomes 
            <br/><a class="discnarr" href="liAdminNarrative.do?t=aadminnarr&id=29">a. Project Outputs</a>
            <br/><a class="discnarr" href="liAdminNarrative.do?t=aadminnarr&id=31">b. Project Outcomes</a>
            <br/><a class="discnarr" href="liAdminNarrative.do?t=aadminnarr&id=32">c. Measuring Project Outcomes</a>
          </td>
        </tr>        
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>V. Budget Narrative            
            <br/><a class="discnarr" href="liAdminNarrative.do?t=aadminnarr&id=38">a. Purchased Services</a>
            <br/><a class="discnarr" href="liAdminNarrative.do?t=aadminnarr&id=39">b. Supplies/Materials</a>
            <br/><a class="discnarr" href="liAdminNarrative.do?t=aadminnarr&id=121">c. Equipment</a>
            <br/><a class="discnarr" href="liAdminNarrative.do?t=aadminnarr&id=40">d. Travel Expenses</a>
            <br/><a class="discnarr" href="liAdminNarrative.do?t=aadminnarr&id=36">e. Other Funding Sources</a>
          </td>
        </tr>
      </table>  
      
    </c:if>
  
  </body>
</html>