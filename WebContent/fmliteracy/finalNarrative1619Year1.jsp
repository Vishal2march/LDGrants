<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>finalNarrative1619Year1</title>
    <%--SH 7/23/14 newer version of tinymce to fix cursor issue --%>
    <script type="text/javascript" src="jscripts/tinymce/tinymce.js"></script>
    <script type="text/javascript">
    tinymce.init({
         selector: "textarea",
         menubar: false,
         toolbar: "bold italic underline | alignleft aligncenter alignright | bullist numlist outdent indent",
         statusbar: false,
         nowrap: false,
         width: 600
     });
  </script>
  </head>
  <body>
  
  
  <h4>Final Narrative Year 1</h4>
  <br/><br/>
  
  <table width="90%" summary="for layout only"><%--2 column page layout --%>
    <tr>
      <td width="30%" valign="top">
          <%-- narrative menu --%>
          
          <c:choose>
          <c:when test="${param.p=='fl'}" ><%--family lit menu year 1 --%>
          
            <div style="border-style:solid; border-width:thin; padding:5.0px;">
              <b>Narrative Menu</b>
              <br/><br/>
              <a class="discnarr" href="liNarrative.do?t=frpt&id=122">Goals/Objectives/Activities</a>
              <br/><br/>
              <a class="discnarr" href="liNarrative.do?t=frpt&id=125">Summary of Evaluation Outcomes/Outputs</a>
              <br/><br/>
              <a class="discnarr" href="liNarrative.do?t=frpt&id=50">Budget Changes</a>
              <br/><br/>
            </div>
            
          </c:when>
          <c:when test="${param.p=='al'}"><%--adult lit menu year 1 --%>
          
            <div style="border-style:solid; border-width:thin;">
              <b>Narrative Menu</b>
              <br/><br/>
              <a class="discnarr" href="liNarrative.do?t=afrpt&id=122">Goals/Objectives/Activities</a>
              <br/><br/>
              <a class="discnarr" href="liNarrative.do?t=afrpt&id=125">Summary of Evaluation Outcomes/Outputs</a>
              <br/><br/>
              <a class="discnarr" href="liNarrative.do?t=afrpt&id=50">Budget Changes</a>
              <br/><br/>
            </div>
          
          </c:when>
          </c:choose>
          
      </td>
      <td valign="top">
          <%-- narrative data entry box & instructions --%>
          
          
          
        <div style="border-style:solid; border-width:thin; padding:5.0px;">
        
        <html:form action="/flSaveNarrative" >         
          
          <p align="center"><b><c:out value="${projNarrative.narrativeTitle}" /></b></p>
          <br/><br/>
          <c:out value="${projNarrative.narrativeDescr}" />
          <br/><br/>
          <br/>
           
           
          <c:choose >
          <c:when test="${lduser.readaccess=='true' || appStatus.allowSubmitFinal=='false'}" > 
            
            <%-- read only --%>
            <bean:write name="projNarrative" property="narrative" filter="false" />
            
          </c:when>
          <c:otherwise >
            
              <%-- data entry --%>              
              <html:textarea property="narrative" cols="80" rows="17" />              
              <br/><br/><br/>
              
              <html:hidden property="id" /> 
              <html:hidden property="narrTypeID" />
              <html:hidden property="module" />
              <html:submit value="Save" />
            
              <br/><br/>
              <b>Instructions to copy/paste from another source:</b> <br/>
              Highlight the text that you want to copy and then click Ctrl + C on keyboard.  
              Put the cursor in the desired area and click Ctrl + V on keyboard to paste.  Or use the toolbar icons for
              Copy and Paste.
              <br/><br/>
              
              <c:choose >
              <c:when test="${param.p=='al'}">
                  <a href="AlAddAttachment.do">Attach</a> 
              </c:when>
              <c:when test="${param.p=='fl'}">
                  <a href="FlAddAttachment.do">Attach</a>
              </c:when>           
              </c:choose>
              a document/attachment to this grant application.
             (Be sure to Save narrative first)
              <br/><br/>
                
          </c:otherwise>
          </c:choose>              
                
      </html:form>    
            
      </div>
          
          
      </td>
    </tr>
  </table>
  
  
  </body>
</html>