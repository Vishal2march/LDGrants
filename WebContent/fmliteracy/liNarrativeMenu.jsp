<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  liNarrativeMenu.jsp
 * Creation/Modification History  :
 *
 *     SHusak    Created
 *
 * Description
 * This page contains links to all fl/al narratives for applicant.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <c:if test="${param.p=='fl'}" >
  <%--FAMILY LITERACY LINKS--%>
  <c:choose>
  <c:when test="${appStatus.fycode<14}">
      <%--prior year grants--%>
      <table class="boxtype" summary="for layout only">
        <tr>
          <th>Narratives</th>
        </tr>  
        <ul>
        <tr>
          <td>I. Abstract (5 pts)          
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=1">a. Summary Description</a></li>
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>II. Need, Target Audience, Collaboration (20 pts) 
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=26">a. Project Need and Target Audience</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=5">b. Long-range Plan</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=27">c. Programming</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=8">d. Participating Organizations</a></li>         
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>III. Project Description (25 pts)
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=28">a. Project objectives</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=3">b. Activities Performed</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=12">c. Project Timeline</a></li>
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>IV. Evaluation/Outcomes (20 pts)
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=29">a. What are Project outputs</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=30">b. How will you measure outputs</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=31">c. What are Project outcomes</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=32">d. How will you measure outcomes</a></li>
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>V. Continuation Plans/Dissemination of Project Results (10 pts)
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=33">a. Continuation of project</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=34">b. Distributing information to NYS organizations</a></li>
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>VI. Budget Narrative (20 pts)
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=36">a. Other Funding Sources</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=35">b. Salaries</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=37">c. Benefits</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=38">d. Purchased Services</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=39">e. Supplies/Equip</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=40">f. Travel Expenses</a></li>
          </td>
        </tr>
        </ul>
      </table>  
  
  </c:when>
  <c:otherwise>
      <%--starting FY2013-14--%>
      <table class="boxtype" summary="for layout only">
        <tr>
          <th>Narratives</th>
        </tr>  
        <ul>
        <tr>
          <td>I. Abstract         
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=1">a. Summary Description</a></li>
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>II. Need, Target Audience 
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=26">a. Project Need and Target Audience</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=27">b. Programming</a></li>
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>III. Project Description 
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=28">a. Project Objectives</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=3">b. Activities Performed</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=12">c. Timetable</a></li>
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>IV. Evaluation/Outcomes 
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=29">a. What are Project outputs</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=30">b. How will you measure outputs</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=31">c. What are Project outcomes</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=32">d. How will you measure outcomes</a></li>
          </td>
        </tr>        
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>V. Budget Narrative
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=36">a. Other Funding Sources</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=38">b. Purchased Services</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=39">c. Supplies/Equip</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=narr&id=40">d. Travel Expenses</a></li>
          </td>
        </tr>
        </ul>
      </table>  
  
  </c:otherwise>
  </c:choose>
  
  </c:if>
  
  
  
  <c:if test="${param.p=='al'}">
  <%--ADULT LITERACY LINKS--%>
  <c:choose>
  <c:when test="${appStatus.fycode<14}">
      <%--prior year grants--%>  
      <table class="boxtype" summary="for layout only">
        <tr>
          <th>Narratives</th>
        </tr>  
        <ul>
        <tr>
          <td>I. Abstract (5 pts)          
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=1">a. Summary Description</a></li>
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>II. Need, Target Audience, Collaboration (20 pts) 
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=26">a. Project Need and Target Audience</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=5">b. Long-range Plan</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=27">c. Programming</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=8">d. Participating Organizations</a></li>         
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>III. Project Description (25 pts)
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=28">a. Project objectives</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=3">b. Activities Performed</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=12">c. Project Timeline</a></li>
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>IV. Evaluation/Outcomes (20 pts)
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=29">a. What are Project outputs</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=30">b. How will you measure outputs</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=31">c. What are Project outcomes</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=32">d. How will you measure outcomes</a></li>
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>V. Continuation Plans/Dissemination of Project Results (10 pts)
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=33">a. Continuation of project</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=34">b. Distributing information to NYS organizations</a></li>
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>VI. Budget Narrative (20 pts)
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=36">a. Other Funding Sources</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=35">b. Salaries</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=37">c. Benefits</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=38">d. Purchased Services</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=39">e. Supplies/Equip</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=40">f. Travel Expenses</a></li>
          </td>
        </tr>
        </ul>
      </table>    
      
    </c:when>
    <c:otherwise>
      <%--starting FY2013-14--%>  
      <table class="boxtype" summary="for layout only">
        <tr>
          <th>Narratives</th>
        </tr>  
        <ul>
        <tr>
          <td>I. Abstract         
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=1">a. Summary Description</a></li>
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>II. Need, Target Audience
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=26">a. Project Need and Target Audience</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=27">b. Programming</a></li>
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>III. Project Description 
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=28">a. Project Objectives</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=3">b. Activities Performed</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=12">c. Timetable</a></li>
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>IV. Evaluation/Outcomes
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=29">a. What are Project outputs</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=30">b. How will you measure outputs</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=31">c. What are Project outcomes</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=32">d. How will you measure outcomes</a></li>
          </td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        <tr>
          <td>V. Budget Narrative 
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=36">a. Other Funding Sources</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=38">b. Purchased Services</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=39">c. Supplies/Equip</a></li>
            <li><a class="discnarr" href="liNarrative.do?t=anarr&id=40">d. Travel Expenses</a></li>
          </td>
        </tr>
        </ul>
      </table>    
    
    </c:otherwise>
    </c:choose>
    
  </c:if>  
  
  </body>
</html>
