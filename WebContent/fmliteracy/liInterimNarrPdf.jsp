<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>
<pd4ml:transform screenWidth="520" pageInsets="20,20,20,20,points" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Literacy Interim-Final Narratives</title>
  </head>
  <body>
  
  <font size="1">
  <table align="center" summary="for layout only">
    <tr>
      <th colspan="2"><c:if test="${thisGrant.fccode==40}" >Adult</c:if>
      <c:if test="${thisGrant.fccode==42}" >Family</c:if> Literacy Library Services - Interim/Final Narratives</th>
    </tr>
    <tr>
      <td>Project Number</td>
      <td>03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" />
      </td>
    </tr>
    <tr>
      <td>Project Title</td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>
  </table>
  </font>
  <br/><br/>
  
  
  <font size="1">
   <table align="center" width="95%" summary="for layout only"> 
   <c:if test="${thisGrant.fycode<14}">
        <tr>
          <th>Interim Narratives</th>
        </tr>
        <tr>
          <th bgcolor="Silver">Project Changes</th>
        </tr>
        <tr>  
          <td align="left"><bean:write name="projNarr52" property="narrative" filter="false"/></td>
        </tr>
        <tr>
          <td height="30"></td>
        </tr>  
        <tr>
          <th bgcolor="Silver">Expended Funds</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr53" property="narrative" filter="false"/></td>
        </tr>
        <tr>
          <td height="30"></td>
        </tr>  
         <tr>
          <th bgcolor="Silver">Anecdote</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr54" property="narrative" filter="false"/></td>
        </tr>
        <tr>
          <td height="30"></td>
        </tr>  
    </c:if>
    
    <tr>
      <th>Final Narratives</th>
    </tr>
    <tr>
      <th bgcolor="Silver">Synopsis (Year 1)</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr41" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    
    <c:if test="${thisGrant.fccode==40 || thisGrant.fycode<14}">
        <tr>
          <th bgcolor="Silver">Need (Year 1)</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr42" property="narrative" filter="false"/></td>
        </tr>
        <tr>
          <td height="30"></td>
        </tr>  
        <tr>
          <th bgcolor="Silver">Target Audience (Year 1)</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr43" property="narrative" filter="false"/></td>
        </tr>
        <tr>
          <td height="30"></td>
        </tr>  
    </c:if>
    
    <tr>
      <th bgcolor="Silver">Coordination with Agencies (Year 1)</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr44" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Accomplishments (Year 1)</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr45" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Evaluation Methods (Year 1)</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr46" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    
    <c:if test="${thisGrant.fccode==40 || thisGrant.fycode<14}">
      <tr>
        <th bgcolor="Silver">Continuation (Year 1)</th>
      </tr>
      <tr>
        <td align="left"><bean:write name="projNarr47" property="narrative" filter="false"/></td>
      </tr>
      <tr>
        <td height="30"></td>
      </tr>  
    </c:if>
    
    <tr>
      <th bgcolor="Silver">Sharing Results (Year 1)</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr48" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Problems (Year 1)</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr49" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    
    <c:if test="${thisGrant.fccode==42 && thisGrant.fycode>13}">
            <tr>
              <th bgcolor="Silver">Planning from Experience (Year 1)</th>
            </tr>
            <tr>
              <td align="left"><bean:write name="projNarr111" property="narrative" filter="false"/></td>
            </tr>
            <tr>
              <td height="30"></td>
            </tr>  
            <tr>
              <th bgcolor="Silver">Family Component (Year 1)</th>
            </tr>
            <tr>
              <td align="left"><bean:write name="projNarr114" property="narrative" filter="false"/></td>
            </tr>
            <tr>
              <td height="30"></td>
            </tr>         
     </c:if>
     
    <tr>
      <th bgcolor="Silver">Budget Changes (Year 1)</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr50" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Additional Funds (Year 1)</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr51" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
        <th>Final Narratives Year 2</th>
    </tr>
    <tr>
      <th bgcolor="Silver">Synopsis (Year 2)</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr55" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    
    <c:if test="${thisGrant.fccode==40 || thisGrant.fycode<14}">
        <tr>
          <th bgcolor="Silver">Need (Year 2)</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr56" property="narrative" filter="false"/></td>
        </tr>
        <tr>
          <td height="30"></td>
        </tr>  
        <tr>
          <th bgcolor="Silver">Target Audience (Year 2)</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr57" property="narrative" filter="false"/></td>
        </tr>
        <tr>
          <td height="30"></td>
        </tr>  
    </c:if>
    
    <tr>
      <th bgcolor="Silver">Coordination with Agencies (Year 2)</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr58" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Accomplishments (Year 2)</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr59" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    
    <c:if test="${thisGrant.fccode==40 || thisGrant.fycode<14}">
      <tr>
        <th bgcolor="Silver">Continuation (Year 2)</th>
      </tr>
      <tr>
        <td align="left"><bean:write name="projNarr60" property="narrative" filter="false"/></td>
      </tr>
      <tr>
        <td height="30"></td>
      </tr>  
    </c:if>
    
    <tr>
      <th bgcolor="Silver">Sharing Results (Year 2)</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr61" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Problems (Year 2)</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr62" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    
    <c:if test="${thisGrant.fccode==42 && thisGrant.fycode>13}">
            <tr>
              <th bgcolor="Silver">Planning from Experience (Year 2)</th>
            </tr>
            <tr>
              <td align="left"><bean:write name="projNarr112" property="narrative" filter="false"/></td>
            </tr>
            <tr>
              <td height="30"></td>
            </tr>  
            <tr>
              <th bgcolor="Silver">Family Component (Year 2)</th>
            </tr>
            <tr>
              <td align="left"><bean:write name="projNarr115" property="narrative" filter="false"/></td>
            </tr>
            <tr>
              <td height="30"></td>
            </tr>         
    </c:if>
    
    <tr>
      <th bgcolor="Silver">Evaluation Methods (Year 2)</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr63" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Budget Changes (Year 2)</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr64" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    <tr>
      <th bgcolor="Silver">Additional Funds (Year 2)</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr65" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>  
    
     <c:if test="${thisGrant.fycode>13}">
        <tr>
            <th>Final Narratives Year 3</th>
        </tr>
        <tr>
          <th bgcolor="Silver">Synopsis (Year 3)</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr96" property="narrative" filter="false"/></td>
        </tr>
        <tr>
          <td height="30"></td>
        </tr>  
        
        <c:if test="${thisGrant.fccode==40}">
            <tr>
              <th bgcolor="Silver">Need (Year 3)</th>
            </tr>
            <tr>
              <td align="left"><bean:write name="projNarr97" property="narrative" filter="false"/></td>
            </tr>
            <tr>
              <td height="30"></td>
            </tr>  
            <tr>
              <th bgcolor="Silver">Target Audience (Year 3)</th>
            </tr>
            <tr>
              <td align="left"><bean:write name="projNarr98" property="narrative" filter="false"/></td>
            </tr>
            <tr>
              <td height="30"></td>
            </tr>  
        </c:if>
        
        <tr>
          <th bgcolor="Silver">Coordination with Agencies (Year 3)</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr99" property="narrative" filter="false"/></td>
        </tr>
        <tr>
          <td height="30"></td>
        </tr>  
        <tr>
          <th bgcolor="Silver">Accomplishments (Year 3)</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr100" property="narrative" filter="false"/></td>
        </tr>
        <tr>
          <td height="30"></td>
        </tr>  
        
        <c:if test="${thisGrant.fccode==40}">
          <tr>
            <th bgcolor="Silver">Continuation (Year 3)</th>
          </tr>
          <tr>
            <td align="left"><bean:write name="projNarr101" property="narrative" filter="false"/></td>
          </tr>
          <tr>
            <td height="30"></td>
          </tr>  
        </c:if>
        
        <tr>
          <th bgcolor="Silver">Sharing Results (Year 3)</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr102" property="narrative" filter="false"/></td>
        </tr>
        <tr>
          <td height="30"></td>
        </tr>  
        <tr>
          <th bgcolor="Silver">Problems (Year 3)</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr103" property="narrative" filter="false"/></td>
        </tr>
        <tr>
          <td height="30"></td>
        </tr>  
        
        <c:if test="${thisGrant.fccode==42}">
            <tr>
              <th bgcolor="Silver">Planning from Experience (Year 3)</th>
            </tr>
            <tr>
              <td align="left"><bean:write name="projNarr113" property="narrative" filter="false"/></td>
            </tr>
            <tr>
              <td height="30"></td>
            </tr>  
            <tr>
              <th bgcolor="Silver">Family Component (Year 3)</th>
            </tr>
            <tr>
              <td align="left"><bean:write name="projNarr116" property="narrative" filter="false"/></td>
            </tr>
            <tr>
              <td height="30"></td>
            </tr>         
        </c:if>
        
        <tr>
          <th bgcolor="Silver">Evaluation Methods (Year 3)</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr104" property="narrative" filter="false"/></td>
        </tr>
        <tr>
          <td height="30"></td>
        </tr>  
        <tr>
          <th bgcolor="Silver">Budget Changes (Year 3)</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr105" property="narrative" filter="false"/></td>
        </tr>
        <tr>
          <td height="30"></td>
        </tr>  
        <tr>
          <th bgcolor="Silver">Additional Funds (Year 3)</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr106" property="narrative" filter="false"/></td>
        </tr>
        <tr>
          <td height="30"></td>
        </tr>  
        <tr>
          <th bgcolor="Silver">3 Year Summary</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr118" property="narrative" filter="false"/></td>
        </tr>
        <tr>
          <td height="30"></td>
        </tr>  
        <tr>
          <th bgcolor="Silver">Project Continuation</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr47" property="narrative" filter="false"/></td>
        </tr>
        <tr>
          <td height="30"></td>
        </tr>  
    </c:if>
  </table> 
  </font>
  
  </body>
</html>
</pd4ml:transform>