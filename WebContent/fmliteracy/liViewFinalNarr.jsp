<%--
 * @author  shusak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 11g
 * Name of the Application        :  liViewFinalNarr.jsp
 * Creation/Modification History  :    
 *     SHusak  ?
 * Description
 * This is original final narrative menu for both AL/FL ADMIN, for before/after 2013-16 year
 * For the new admin final narrative page starting 2016-19, see adminFinalNarrativeView.jsp
 * Note: admin can edit initial narratives, but not final. Not sure who/when decided this.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h4>Final Narratives</h4>
  
  
  <table summary="for layout only" width="100%">
  <tr>
    <td width="30%" valign="top">
  
  
  
  <c:if test="${param.m=='afinal'}" >
    <%--starting 13-14; al vs fl has different final narrative categories --%>
    <table class="boxtype" summary="for layout only">
        <tr>
          <td>I. Synopsis<br/>         
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=41">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=55">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=96">year 3</a>      
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>II. Need<br/>          
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=42">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp; 
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=56">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp; 
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=97">year 3</a>
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>III. Target Audience<br/>          
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=43">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=57">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=98">year 3</a>
          </td>
        </tr>
        <tr>
          <td height="15" />
        </tr>
        <tr>
          <td>IV. Direct Coordination with Agencies<br/>           
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=44">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=58">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=99">year 3</a>
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>V. Accomplishments<br/>
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=45">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=59">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=100">year 3</a>
            
            <br/>b. Sharing Results<br/>
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=48">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=61">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=102">year 3</a>
            <br/>c. Problems<br/>
           <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=49">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=62">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=103">year 3</a>
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>VI. Evaluation<br/>
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=46">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=63">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=104">year 3</a>
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>VII. Budget Changes<br/> 
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=50">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=64">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=105">year 3</a>
            <br/>b. Additional Funds<br/>
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=51">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=65">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=106">year 3</a>
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>VIII. Summary <br/>
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=118">3 Year Summary</a><br/>
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=47">Project Continuation</a>
            
            <%--<br/>b. Project Continuation<br/>
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=47">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=60">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=afrpt&id=101">year 3</a>--%>
          </td>
        </tr>
      </table>
  </c:if>
  
  
  <c:if test="${param.m=='ffinal'}" >
    <%--starting 13-14; al vs fl has different final narrative categories --%>
    <table class="boxtype" summary="for layout only">
        <tr>
          <td>Synopsis<br/>        
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=41">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=55">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=96">year 3</a>      
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>        
        <tr>
          <td>Coordination with Agencies <br/>          
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=44">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=58">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=99">year 3</a>
          </td>
        </tr>      
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>Accomplishments<br/>
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=45">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=59">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=100">year 3</a></td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>Publicity<br/>
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=48">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=61">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=102">year 3</a></td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>Problems<br/>
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=49">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=62">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=103">year 3</a>
          </td>
        </tr>        
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>Planning from Experience<br/>
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=111">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=112">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=113">year 3</a></td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>Family Component<br/>
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=114">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=115">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=116">year 3</a></td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>Evaluation Methods<br/>
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=46">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=63">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=104">year 3</a>
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>Budget Changes<br/> 
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=50">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=64">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=105">year 3</a></td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>Additional Funds<br/>
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=51">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=65">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=106">year 3</a>
          </td>
        </tr>
        <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>3 Year Summary<br/>
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=118">3 Year Summary</a><br/>
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=47">Project Continuation</a>
          </td>
        </tr>
      <%--  <tr>
          <td height="15"/>
        </tr>
        <tr>
          <td>Project Continuation<br/>
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=47">year 1</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=60">year 2</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="discnarr" href="liAdminNarrative.do?t=frpt&id=101">year 3</a>
          </td>
        </tr>--%>
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
  
  
  
  <%--removing this per kbalsen 7/2/15; replace with links/menu that includes year 3
  <form method="post" action="liReadNarrative.do?item=readNarr">  
    <select name="narrType">
      <c:if test="${param.m=='afinal'}">
          <option value="41">Synopsis Year 1</option>
          <option value="55">Synopsis Year 2</option>
          <option value="42">Need Year 1</option>
          <option value="56">Need Year 2</option>
          <option value="43">Target Audience Year 1</option>
          <option value="57">Target Audience Year 2</option>
          <option value="44">Coordination with Agencies Year 1</option>
          <option value="58">Coordination with Agencies Year 2</option>
          <option value="45">Accomplishments Year 1</option>
          <option value="59">Accomplishments Year 2</option>
          <option value="47">Project Continuation Year 1</option>
          <option value="60">Project Continuation Year 2</option>
          <option value="48">Sharing Results Year 1</option>
          <option value="61">Sharing Results Year 2</option>
          <option value="49">Problems Year 1</option>
          <option value="62">Problems Year 2</option>
          <option value="46">Evaluation Year 1</option>
          <option value="63">Evaluation Year 2</option>
          <option value="50">Budget Summary Year 1</option>
          <option value="64">Budget Summary Year 2</option>
          <option value="51">Additional Funds Year 1</option>
          <option value="65">Additional Funds Year 2</option>
      </c:if>      
      <c:if test="${param.m=='ffinal'}">
          <option value="41">Synopsis Year 1</option>
          <option value="55">Synopsis Year 2</option>          
          <option value="44">Coordination with Agencies Year 1</option>
          <option value="58">Coordination with Agencies Year 2</option>
          <option value="45">Accomplishments Year 1</option>
          <option value="59">Accomplishments Year 2</option>
          <option value="48">Publicity Year 1</option>
          <option value="61">Publicity Year 2</option>
          <option value="49">Problems Year 1</option>
          <option value="62">Problems Year 2</option>
          <option value="111">Planning from Experience Year 1</option>
          <option value="112">Planning from Experience Year 2</option>
          <option value="114">Family Component Year 1</option>
          <option value="115">Family Component Year 2</option>
          <option value="46">Evaluation Year 1</option>
          <option value="63">Evaluation Year 2</option>
          <option value="50">Budget Summary Year 1</option>
          <option value="64">Budget Summary Year 2</option>
          <option value="51">Additional Funds Year 1</option>
          <option value="65">Additional Funds Year 2</option>          
      </c:if>
    </select>    
    <input type="HIDDEN" name="m" value='<c:out value="${param.m}" />' />
    <input type="hidden" name="p" value="lit" />
    <input type="SUBMIT" value="Select" />
  </form> --%>
  
  
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
