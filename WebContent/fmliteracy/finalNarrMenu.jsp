<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  finalNarrMenu.jsp
 * Creation/Modification History  :
 *
 *     SHusak  Created
 *
 * Description
 * This page contains links to all final narratives for al/fl applicant.
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
          <th>Final Narratives</th>
        </tr> 
        <ul>
        <tr>
          <td>I. Synopsis         
            <li><a class="discnarr" href="liNarrative.do?t=frpt&id=41">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=55">year 2</a></li>       
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>II. Need          
            <li><a class="discnarr" href="liNarrative.do?t=frpt&id=42">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp; 
            <a class="discnarr" href="liNarrative.do?t=frpt&id=56">year 2</a></li> 
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>III. Target Audience          
            <li><a class="discnarr" href="liNarrative.do?t=frpt&id=43">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=57">year 2</a></li> 
          </td>
        </tr>
        <tr>
          <td height="15" />
        </tr>
        <tr>
          <td>IV. Direct Coordination with Agencies           
            <li><a class="discnarr" href="liNarrative.do?t=frpt&id=44">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=58">year 2</a></li> 
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>V. Accomplishments
            <li><a class="discnarr" href="liNarrative.do?t=frpt&id=45">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=59">year 2</a></li>
            <br/>b. Project Continuation
            <li><a class="discnarr" href="liNarrative.do?t=frpt&id=47">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=60">year 2</a></li>
            <br/>c. Sharing Results
            <li><a class="discnarr" href="liNarrative.do?t=frpt&id=48">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=61">year 2</a></li>
            <br/>d. Problems
            <li><a class="discnarr" href="liNarrative.do?t=frpt&id=49">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=62">year 2</a></li>
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>VI. Evaluation
            <li><a class="discnarr" href="liNarrative.do?t=frpt&id=46">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=63">year 2</a></li>
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>VII. Budget Changes 
            <li><a class="discnarr" href="liNarrative.do?t=frpt&id=50">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=64">year 2</a></li>
            <br/>b. Additional Funds
            <li><a class="discnarr" href="liNarrative.do?t=frpt&id=51">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=65">year 2</a></li>
          </td>
        </tr>
        </ul>
      </table>
      
  </c:when>
  <c:otherwise>
    <%-- starting fy 2013-14   --%>
    <table class="boxtype" summary="for layout only">
        <tr>
          <th>Final Narratives</th>
        </tr> 
        <tr>
          <td>Synopsis<br/>        
            <a class="discnarr" href="liNarrative.do?t=frpt&id=41">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=55">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=96">year 3</a>      
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>        
        <tr>
          <td>Coordination with Agencies <br/>          
            <a class="discnarr" href="liNarrative.do?t=frpt&id=44">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=58">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=99">year 3</a>
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>Accomplishments<br/>
            <a class="discnarr" href="liNarrative.do?t=frpt&id=45">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=59">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=100">year 3</a></td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>Publicity<br/>
            <a class="discnarr" href="liNarrative.do?t=frpt&id=48">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=61">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=102">year 3</a></td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>Problems<br/>
            <a class="discnarr" href="liNarrative.do?t=frpt&id=49">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=62">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=103">year 3</a>
          </td>
        </tr>        
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>Planning from Experience<br/>
            <a class="discnarr" href="liNarrative.do?t=frpt&id=111">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=112">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=113">year 3</a></td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>Family Component<br/>
            <a class="discnarr" href="liNarrative.do?t=frpt&id=114">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=115">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=116">year 3</a></td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>Evaluation Methods<br/>
            <a class="discnarr" href="liNarrative.do?t=frpt&id=46">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=63">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=104">year 3</a>
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>Budget Changes<br/> 
            <a class="discnarr" href="liNarrative.do?t=frpt&id=50">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=64">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=105">year 3</a></td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>Additional Funds<br/>
            <a class="discnarr" href="liNarrative.do?t=frpt&id=51">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=65">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=106">year 3</a>
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>3 Year Summary<br/>
            <a class="discnarr" href="liNarrative.do?t=frpt&id=118">3 Year Summary</a><br/>
            <a class="discnarr" href="liNarrative.do?t=frpt&id=47">Project Continuation</a>
          </td>
        </tr>
      <%--  <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>b. Project Continuation<br/>
            <a class="discnarr" href="liNarrative.do?t=frpt&id=47">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=60">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=frpt&id=101">year 3</a>
          </td>
        </tr>--%>
      </table>
  </c:otherwise>
  </c:choose>
  </c:if>
  
  
  
  <c:if test="${param.p=='al'}" >
  <%--ADULT LITERACY LINKS--%>
  <c:choose>
  <c:when test="${appStatus.fycode<14}">
      <%--prior year grants--%>  
      <table class="boxtype" summary="for layout only">
        <tr>
          <th>Final Narratives</th>
        </tr> 
        <ul>
        <tr>
          <td>I. Synopsis         
            <li><a class="discnarr" href="liNarrative.do?t=afrpt&id=41">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=55">year 2</a></li>       
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>II. Need          
            <li><a class="discnarr" href="liNarrative.do?t=afrpt&id=42">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp; 
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=56">year 2</a></li> 
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>III. Target Audience          
            <li><a class="discnarr" href="liNarrative.do?t=afrpt&id=43">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=57">year 2</a></li> 
          </td>
        </tr>
        <tr>
          <td height="15" />
        </tr>
        <tr>
          <td>IV. Direct Coordination with Agencies           
            <li><a class="discnarr" href="liNarrative.do?t=afrpt&id=44">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=58">year 2</a></li> 
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>V. Accomplishments
            <li><a class="discnarr" href="liNarrative.do?t=afrpt&id=45">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=59">year 2</a></li>
            <br/>b. Project Continuation
            <li><a class="discnarr" href="liNarrative.do?t=afrpt&id=47">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=60">year 2</a></li>
            <br/>c. Sharing Results
            <li><a class="discnarr" href="liNarrative.do?t=afrpt&id=48">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=61">year 2</a></li>
            <br/>d. Problems
            <li><a class="discnarr" href="liNarrative.do?t=afrpt&id=49">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=62">year 2</a></li>
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>VI. Evaluation
            <li><a class="discnarr" href="liNarrative.do?t=afrpt&id=46">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=63">year 2</a></li>
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>VII. Budget Changes 
            <li><a class="discnarr" href="liNarrative.do?t=afrpt&id=50">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=64">year 2</a></li>
            <br/>b. Additional Funds
            <li><a class="discnarr" href="liNarrative.do?t=afrpt&id=51">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=65">year 2</a></li>
          </td>
        </tr>
        </ul>
      </table>
      
    </c:when>
    <c:otherwise>
      <%--starting FY2013-14--%>  
      <table class="boxtype" summary="for layout only">
        <tr>
          <th>Final Narratives</th>
        </tr> 
        <tr>
          <td>I. Synopsis<br/>         
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=41">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=55">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=96">year 3</a>      
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>II. Need<br/>          
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=42">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp; 
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=56">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp; 
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=97">year 3</a>
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>III. Target Audience<br/>          
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=43">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=57">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=98">year 3</a>
          </td>
        </tr>
        <tr>
          <td height="15" />
        </tr>
        <tr>
          <td>IV. Direct Coordination with Agencies<br/>           
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=44">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=58">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=99">year 3</a>
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>V. Accomplishments<br/>
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=45">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=59">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=100">year 3</a>            
            <br/>b. Sharing Results<br/>
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=48">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=61">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=102">year 3</a>
            <br/>c. Problems<br/>
           <a class="discnarr" href="liNarrative.do?t=afrpt&id=49">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=62">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=103">year 3</a>
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>VI. Evaluation<br/>
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=46">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=63">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=104">year 3</a>
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>VII. Budget Changes<br/> 
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=50">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=64">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=105">year 3</a>
            <br/>b. Additional Funds<br/>
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=51">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=65">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=106">year 3</a>
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>VIII. 3 Year Summary<br/>
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=118">3 Year Summary</a><br/>
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=47">Project Continuation</a>
            
          <%--  <br/>b. Project Continuation<br/>
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=47">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=60">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liNarrative.do?t=afrpt&id=101">year 3</a>--%>
          </td>
        </tr>
      </table>
      
    </c:otherwise>
    </c:choose>
  </c:if>
   
  </body>
</html>
