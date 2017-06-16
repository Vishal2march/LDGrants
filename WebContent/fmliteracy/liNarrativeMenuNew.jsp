<%--
 * @author  shusak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 11g
 * Name of the Application        :  liNarrativeMenuNew.jsp
 * Creation/Modification History  :    
 *     SHusak  1/15/16 Created
 *
 * Description
 * This will be new narrative menu for both AL/FL, starting for 2016-19.
 * For the older 2 versions of narrative menus (2014-16 and pre-2014), see liNarrativeMenu.jsp
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>liNarrativeMenuNew</title>
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
            <br/><a class="discnarr" href="liNarrative.do?t=narr&id=1">a. Summary Description</a>
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>II. Need, Target Audience 
            <br/><a class="discnarr" href="liNarrative.do?t=narr&id=26">a. Project Need and Target Audience</a>
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>III. Project Description 
            <br/><a class="discnarr" href="liNarrative.do?t=narr&id=28">a. Project Goals and Objectives</a>
            <br/><a class="discnarr" href="liNarrative.do?t=narr&id=3">b. Activities</a>
            <br/><a class="discnarr" href="liNarrative.do?t=narr&id=119">c. Training Plan</a>
            <br/><a class="discnarr" href="liNarrative.do?t=narr&id=12">d. Timetable</a>
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>IV. Evaluation/Outcomes 
            <br/><a class="discnarr" href="liNarrative.do?t=narr&id=120">a. NYSL prescribed training Outcomes and Outputs</a>
            <br/><a class="discnarr" href="liNarrative.do?t=narr&id=29">b. Project Outputs</a>
            <br/><a class="discnarr" href="liNarrative.do?t=narr&id=31">c. Project Outcomes</a>
            <br/><a class="discnarr" href="liNarrative.do?t=narr&id=32">d. Measuring Project Outcomes</a>
          </td>
        </tr>        
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>V. Budget Narrative            
            <br/><a class="discnarr" href="liNarrative.do?t=narr&id=38">a. Budget Purchased Services</a>
            <br/><a class="discnarr" href="liNarrative.do?t=narr&id=39">b. Budget Supplies/Materials</a>
            <br/><a class="discnarr" href="liNarrative.do?t=narr&id=121">c. Budget Equipment</a>
            <br/><a class="discnarr" href="liNarrative.do?t=narr&id=40">d. Budget Travel Expenses</a>
            <br/><a class="discnarr" href="liNarrative.do?t=narr&id=36">e. Other Funding Sources</a>
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
            <br/><a class="discnarr" href="liNarrative.do?t=anarr&id=1">a. Summary Description</a>
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>II. Need, Target Audience 
            <br/><a class="discnarr" href="liNarrative.do?t=anarr&id=26">a. Project Need and Target Audience</a>            
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>III. Project Description 
            <br/><a class="discnarr" href="liNarrative.do?t=anarr&id=28">a. Project Goals and Objectives</a>
            <br/><a class="discnarr" href="liNarrative.do?t=anarr&id=3">b. Activities</a>
            <br/><a class="discnarr" href="liNarrative.do?t=anarr&id=12">c. Timetable</a>
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>IV. Evaluation/Outcomes 
            <br/><a class="discnarr" href="liNarrative.do?t=anarr&id=29">a. Project Outputs</a>
            <br/><a class="discnarr" href="liNarrative.do?t=anarr&id=31">b. Project Outcomes</a>
            <br/><a class="discnarr" href="liNarrative.do?t=anarr&id=32">c. Measuring Project Outcomes</a>
          </td>
        </tr>        
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>V. Budget Narrative
            <br/><a class="discnarr" href="liNarrative.do?t=anarr&id=38">a. Budget Purchased Services</a>
            <br/><a class="discnarr" href="liNarrative.do?t=anarr&id=39">b. Budget Supplies/Materials</a>
            <br/><a class="discnarr" href="liNarrative.do?t=anarr&id=121">c. Budget Equipment</a>
            <br/><a class="discnarr" href="liNarrative.do?t=anarr&id=40">d. Budget Travel Expenses</a>
            <br/><a class="discnarr" href="liNarrative.do?t=anarr&id=36">e. Other Funding Sources</a>
            
          </td>
        </tr>
      </table>  
      
    </c:if>
  
  
  </body>
</html>