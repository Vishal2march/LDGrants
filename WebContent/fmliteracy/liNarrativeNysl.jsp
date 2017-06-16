<%--
 * @author  Stefanie Husak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 11g
 * Name of the Application        :  liNarrativeNysl.jsp
 * Creation/Modification History  :
 * SHusak Created 1/19/16
 *
 * Description
 * Per KBALSEN; starting FY 2016-19, family lit has new "nysl outcomes/outputs" narrative.  Informational text only,
 * the applicant doesn't actually type in anything for this narrative.
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>liNarrativeNysl</title>
  </head>
  <body>
  
  
  <c:if test="${param.p=='fl'}"><%--11/6/12 rqsted by LAreford--%>
    <p>NOTE: New York State Family Literacy funding as per Education Law 273 1 h (3), 
    requires that project funds include components for children AND parents/caregivers.
    </p>             
  </c:if>           
  
      
      
      <table width="95%" align="center" class="boxtype" summary="for layout only" >
        <tr>
          <th><c:out value="${projNarrative.narrativeTitle}" /></th>
        </tr>
        <tr>
          <td><bean:write name="projNarrative" property="narrativeDescr" filter="false"/></td>
        </tr>      
        <tr>
          <td height="20" />
        </tr>        
        <tr>
            <td>
            <b><u>Quantitative Measures</u></b>
            <ul>
                <li>Please identify the area(s) in which your partner(s) operate:</li>
                <ul>
                    <li>Libraries</li>
                    <li>Historical Societies or Organizations</li>
                    <li>Museums</li>
                    <li>Archives</li>
                    <li>Cultural Heritage Organization Multi-type</li>
                    <li>Preschools</li>
                    <li>Schools</li>
                    <li>Adult Education</li>
                    <li>Human Service Organizations</li>
                    <li>Other</li>
                </ul>
                
                <li>Please identify the legal type of the partner organization(s) for this project:</li>
                <ul>
                    <li>Federal Government</li>
                    <li>State Government</li>
                    <li>Local Government</li>
                    <li>School District</li>
                    <li>Non-Profit</li>
                    <li>Private Sector</li>
                    <li>Tribe/Native Hawaiian Organization</li>
                </ul>
                
                
                <li>How many Ready to Read at New York Libraries: Early Childhood Public Library Staff Development Program training sessions were held? </li>
                <li>Total library staff attendance at Ready to Read at New York Libraries: Early Childhood Public Library Staff Development Program training sessions?</li>
                <li>How many public libraries participated in Ready to Read at New York Libraries: Early Childhood Public Library Staff Development Program ?</li> 
            </ul>
        
        
            <br/><br/>
            <b><u>Qualitative Measures for staff training sessions</u></b>
            <ul>
                <li>Did the instruction improve staff knowledge?</li>
                <li>Are staff participants more likely to apply what they have learned?</li>
                <li>Will the training improve the library's ability to provide services for the public?</li>
            </ul>
       

                </td>
            </tr>
        </table>   
            
          
  
  </body>
</html>