<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  

  <a href="http://www.archives.nysed.gov/common/archives/files/grants_lgrmif_egrant_user_manual.pdf" target="_blank">Project narrative help</a>
  <br/><br/>
  
  
  <c:choose>
  <c:when test="${appStatus.fycode <15}"><%--previous narrative menu--%>
  
  
  <table class="boxtype" summary="for layout only">
    <tr>
      <th>Project Narrative</th>
    </tr>  
    <ul>
    <tr>
      <td>1. Statement of the Problem (Maximum 20 points)         
          <li>1a. <a href="lgReadNarrative.do?t=narr&m=lg&id=69">Describe records management problem</a> (10)</li>
          <li>1b. <a href="lgReadNarrative.do?t=narr&m=lg&id=70">Identify records and previous projects</a> (5)</li>
          <li>1c. <a href="lgReadNarrative.do?t=narr&m=lg&id=71">Explain why funding is essential</a> (5)</li>
      </td>
    </tr>
    <tr>
      <td height="15" />
    </tr>
    <tr>
      <td>2. Intended Results (Maximum 15 points) 
          <li>2a. <a href="lgReadNarrative.do?t=narr&m=lg&id=72">Identify intended result(s) and anticipated benefits</a> (5)</li>
          <li>2b. <a href="lgReadNarrative.do?t=narr&m=lg&id=73">Describe contributions to records management program</a> (5)</li>
          <li>2c. <a href="lgReadNarrative.do?t=narr&m=lg&id=88">Describe contributions to the public</a> (5)</li>
      </td>
    </tr>
    <tr>
      <td height="15" />
    </tr>
    <tr>
      <td>3. Plan of Work (Maximum 30 points)  
          <li>3a. <a href="lgReadNarrative.do?t=narr&m=lg&id=74">Provide detailed outline and timetable for project</a> (15)</li>
          <li>3b. <a href="lgReadNarrative.do?t=narr&m=lg&id=75">Address project category requirements</a> (10)</li>
          <li>3c. <a href="lgReadNarrative.do?t=narr&m=lg&id=76">Explain responsibilities of project staff</a> (5)</li>
      </td>
    </tr>
    <tr>
      <td height="15" />
    </tr>
    <tr>
      <td>4. Local Government Support for Records Management (Maximum 10 points)   
          <li>4a. <a href="lgReadNarrative.do?t=narr&m=lg&id=77">Demonstrate contributions to project</a> (5)</li>
          <li>4b. <a href="lgReadNarrative.do?t=narr&m=lg&id=78">Describe how the program will be maintained</a> (5)</li>
     </td>
    </tr>
  </ul>
  </table>  
  
  </c:when>
  <c:when test="${appStatus.fycode ==15}">
    
    <%--new narrative menu for FY 2014-15 only--%>
    <table class="boxtype" summary="for layout only">
      <tr>
        <th>Project Narrative</th>
      </tr>  
      <ul>
      <tr>
        <td>1. Statement of the Problem (Maximum 20 points)         
            <li>1a. <a href="lgReadNarrative.do?t=narr&m=lg&id=69">Describe records management problem</a> (10)</li>
            <li>1b. <a href="lgReadNarrative.do?t=narr&m=lg&id=70">Identify records and previous projects</a> (10)</li>
        </td>
      </tr>
      <tr>
        <td height="15" />
      </tr>
      <tr>
        <td>2. Intended Results (Maximum 15 points) 
            <li>2a. <a href="lgReadNarrative.do?t=narr&m=lg&id=72">Chosen methodology and other methodologies considered.</a> (5)</li>
            <li>2b. <a href="lgReadNarrative.do?t=narr&m=lg&id=73">Identify intended result(s) and anticipated benefits.</a> (10)</li>
        </td>
      </tr>
      <tr>
        <td height="15" />
      </tr>
      <tr>
        <td>3. Plan of Work (Maximum 30 points)  
            <li>3a. <a href="lgReadNarrative.do?t=narr&m=lg&id=74">Provide detailed outline and timetable for project</a> (15)</li>
            <li>3b. <a href="lgReadNarrative.do?t=narr&m=lg&id=75">Address project category requirements</a> (10)</li>
            <li>3c. <a href="lgReadNarrative.do?t=narr&m=lg&id=76">Explain responsibilities of project staff</a> (5)</li>
        </td>
      </tr>
      <tr>
        <td height="15" />
      </tr>
      <tr>
        <td>4. Local Government Contributions (Maximum 10 points)   
            <li>4a. <a href="lgReadNarrative.do?t=narr&m=lg&id=77">Demonstrate contributions to project</a> (5)</li>
            <li>4b. <a href="lgReadNarrative.do?t=narr&m=lg&id=78">Describe how the program will be maintained</a> (5)</li>
       </td>
      </tr>
    </ul>
    </table>  
  
  </c:when>
  <c:otherwise>
    
    <%--this is new narrative menu starting FY 2015-16 per DM --%>
    <table class="boxtype" summary="for layout only">
      <tr>
        <th>Project Narrative</th>
      </tr>  
      <ul>
      <tr>
        <td>1. Statement of the Problem (Maximum 15 points)         
            <li>1a. <a href="lgReadNarrative.do?t=narr&m=lg&id=69">Defining the problem</a> (5)</li>
            <li>1b. <a href="lgReadNarrative.do?t=narr&m=lg&id=70">Defining records involved and previous grants</a> (10)</li>
        </td>
      </tr>
      <tr>
        <td height="15" />
      </tr>
      <tr>
        <td>2. Intended Results (Maximum 20 points) 
            <li>2a. <a href="lgReadNarrative.do?t=narr&m=lg&id=72">Methodology</a> (10)</li>
            <li>2b. <a href="lgReadNarrative.do?t=narr&m=lg&id=73">Anticipated benefits</a> (10)</li>
        </td>
      </tr>
      <tr>
        <td height="15" />
      </tr>
      <tr>
        <td>3. Plan of Work (Maximum 30 points)  
            <li>3a. <a href="lgReadNarrative.do?t=narr&m=lg&id=74">Project outline</a> (15)</li>
            <li>3b. <a href="lgReadNarrative.do?t=narr&m=lg&id=75">Grant requirements</a> (15)</li>
        </td>
      </tr>
      <tr>
        <td height="15" />
      </tr>
      <tr>
        <td>4. Local Government Contributions (Maximum 10 points)   
            <li>4a. <a href="lgReadNarrative.do?t=narr&m=lg&id=77">Previous records management and current project support</a> (5)</li>
            <li>4b. <a href="lgReadNarrative.do?t=narr&m=lg&id=78">Future program support</a> (5)</li>
       </td>
      </tr>
    </ul>
    </table>      
    
  
  </c:otherwise>
  </c:choose>
  
  </body>
</html>
