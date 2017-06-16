<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  saFinalReport.jsp
 * Creation/Modification History  :
 *
 *     SH       3/1/07     Created
 *
 * Description
 * This is the final report narrative for grant appliction.  The applicant will enter the final
 * report after the initial application is approved.  
--%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>State Aid Final Report Narrative</title>    
    <script language="javascript" type="text/javascript" src="jscripts/tinymce/tinymce.js"></script>
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
    <noscript>
      <font size="3">You do not have Javascript enabled.  In order to use text editor options
      while typing the narrative, you must enable scripting.</font>
    </noscript>
  </head>
  <body>

 <h4>Final Report Narrative</h4>

    <html:form action="/saveSaNarrative">  
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
        <c:when test="${lduser.prgsa=='read' || appStatus.finalnarrativeyn=='true' || appStatus.pendingFinalRev=='true'}" >
          <tr>
            <td><bean:write name="projNarrative" property="narrative" filter="false" /></td>
          </tr>
        </c:when>
        <c:otherwise >
          <tr>
            <td align="center">  
              <div name="myToolBars" class="mceToolbarExternal"></div>
              <html:textarea property="narrative" cols="80" rows="17" />
            </td>
          </tr>
          <tr>
            <td align="center"><html:hidden property="id" /><html:hidden property="narrTypeID" />
            <html:hidden property="module" value="sa" />
            <html:submit value="Save" /></td>
          </tr>
        </c:otherwise>
        </c:choose>  
      </table>              
    </html:form>
  
 </body>
</html>
