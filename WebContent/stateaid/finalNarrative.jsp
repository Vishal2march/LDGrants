<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>finalNarrative</title>
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
  
  <h4>Final Report Narrative</h4>

    <html:form action="/saveAidNarrative">  
      <table class="boxtype" width="90%" align="center" summary="for layout only">
        <tr>
          <td align="center">
            The Final Report Narrative should correspond closely to the plan of work that 
            you submitted.  It should begin with a chronological timetable 
            recording the beginning date for the year; hiring dates and duration of work for 
            personnel hired with these funds; consultant's schedules and dates when their reports 
            were received; beginning and ending dates for all contractual services; and dates 
            of all other significant activities carried out during the year.  
            The second part of the narrative should provide a description 
            of every aspect of the expenditure of the funds and how they were accomplished. 
            You should note particularly any changes from your original plan of work. <hr/>
          </td>
        </tr>
                          
        <c:choose >
        <c:when test="${lduser.prgNycStateaid=='read' || appStatus.finalnarrativeyn=='true' || appStatus.pendingFinalRev=='true'}" >
          <tr>
            <td><bean:write name="projNarrative" property="narrative" filter="false" /></td>
          </tr>
        </c:when>
        <c:otherwise >
          <tr>
            <td align="center">  
              <html:textarea property="narrative" cols="80" rows="17" />
            </td>
          </tr>
          <tr>
            <td align="center"><html:hidden property="id" /><html:hidden property="narrTypeID" />
            <html:hidden property="module" value="staid" />
            <html:submit value="Save" /></td>
          </tr>
        </c:otherwise>
        </c:choose>  
      </table>              
    </html:form>
  
  </body>
</html>