<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>testlogin</title>
  </head>
  <body>test

    <a href="http://iwiki.nysed.gov/twiki/pub/PMO/OCE/17_OCElibconstruction.doc">wiki</a>
    <form action="index.jsp" method="Get">
      <table cellspacing="3" cellpadding="3" width="90%" summary="for layout only">
        <tr><td>
          <%--<a href="ldgext/discRegister.jsp"> Register for Discretionary</a>--%>
          <a href="ldgext/diRegistration.do">Register for c/p literacy construction</a>
          <br/><br/>
          <a href="ldgext/litRegistration.do"> Register for Literacy</a>
          <br/><br/>
          <a href="ldgext/cnRegistration.do"> Register for lgrmif</a>
          <br /> <br />
          <a href="ldgext/DiPublicReport">C/P Public reports</a>
          <br /> <br />
          <a href="ld177.action">Fs10 Record Test</a>
                    
        </td></tr>
        <tr>
          <td align="center">
            <table width="400" summary="for layout only">
              <tr>
                <td rowspan="5" width="140">&nbsp;</td>
                <td colspan="2"><h2>LDGrants Test Login</h2></td>                
              </tr>
              <tr>
                <td class="formLabel">User ID: </td>
                <td><input type="text" name="userid"></td>
              </tr>
              <tr>
                <td class="formLabel">Password: </td>
                <td><input type="PASSWORD" name="password"></td>
                <td>                  
                    <!-- SL: Using these as preset values to  pass in querystring and test login-->
                    <!-- Ultimately, these will come from LDAP  -->                
                    <input type="hidden" name="SM_USER" value="localpc">
                    <input type="HIDDEN" name="SM_EMAIL" value="stefanie.husak@nysed.gov" />
                    <!--<input type="hidden" name="INSTID" value="800000036397"> --><!-- Cornell -->
                    <!--<input type="HIDDEN" name="INSTID" value="800000059892"/>--><!--cornell libraries -->
                    <!--<input type="hidden" name="INSTID" value="800000047869">--><!-- NYU -->
                    <!--input type="HIDDEN" name="INSTID" value="800000060095">--> <!-- NYU Libraries-->
                    <!--<input type="hidden" name="INSTID" value="800000060094"> --><!-- UAlbany libraries -->
                    <!--<input type="hidden" name="INSTID" value="800000060098">--><!--Columbia libraries-->                     
                    <!--<input type="hidden" name="INSTID" value="800000047082">--><!-- columbia -->
                    <!--<input type="hidden" name="INSTID" value="800000060097">--><!-- stoneybrook libraries -->
                    <!--<input type="hidden" name="INSTID" value="800000060101">--><!-- ubuffalo libraries-->                    
                    <!-- <input type="hidden" name="INSTID" value="800000060102">--><!--syracuse univ libraries-->
                    <!-- <input type="hidden" name="INSTID" value="800000060103">--><!--ubingamton libraries-->
                    <!--<input type="hidden" name="INSTID" value="800000050274">--><!-- u roch -->
                   <!--<input type="hidden" name="INSTID" value="800000060104">--><!--rochester univ libraries-->
                    <!-- <input type="hidden" name="INSTID" value="800000040733">--><!--syracuse -->
                    <!--<input type="hidden" name="INSTID" value="800000055597">--><!--ualbany -->
                    <!--  <input type="hidden" name="INSTID" value="800000052554">--><!--buffalo-->
                    <!--<input type="hidden" name="INSTID" value="800000055037">--><!--binghamton-->
                    <!--input type="hidden" name="INSTID" value="800000055585"><!--st rose-->
                   <!-- <input type="hidden" name="INSTID" value="800000036229"><!--st rose-->
                   <!-- <input type="hidden" name="INSTID" value="800000055556">--><!--albany county-->
                   <!-- <input type="hidden" name="INSTID" value="800000060104">--><!--DORIS-->
                    <!-- <input type="HIDDEN" name="INSTID" value="800000039407" />--><!--troy public libr -->
                    <!-- <input type="HIDDEN" name="INSTID" value="800000036257" /> --><!--kingston -->
                    <!--<input type="HIDDEN" name="INSTID" value="800000055560" />--><!--albany public libr -->
                    <!--<input type="HIDDEN" name="INSTID" value="800000033988" >--><!--siena -->
                   <!--<input type="HIDDEN" name="INSTID" value="800000055588">--><!-- acp -->
                    <!--<input type="HIDDEN" name="INSTID" value="800000060121">--><!--NYPL Lib -->
                    <!-- <input type="HIDDEN" name="INSTID" value="800000055848">--><!--NYPL -->
                   <!-- <input type="HIDDEN" name="INSTID" value="800000055504">--><!--nysed tref -->
                   <!--<input type="HIDDEN" name="INSTID" value="800000047848" />--><!--leo baeck isnt -->
                   <!-- <input type="HIDDEN" name="INSTID" value="800000060476">--><!-- nysl -->
                    <!-- <input type="hidden" name="INSTID" value="800000033960">--><!-- troy schools-->
                   <!--  <input type="HIDDEN" name="INSTID" value="800000070669" /--><!-- NY HISTORICAL SOC-->
                    <!--  <input type="HIDDEN" name="INSTID" value="800000047794" />--> <!-- CENT JEWISH HIST-->
                    <!--input type="HIDDEN" name="INSTID" value="800000060121" /><!-- ny libraries-->
                    <input type="HIDDEN" name="INSTID" value="800000040608" /> <!-- upper hudson-->
                    <!--<input type="HIDDEN" name="INSTID" value="800000051128" />  --><!-- north country-->
                    <!--input type="HIDDEN" name="INSTID" value="800000055559" /><!-- cap dist lib sys-->
                    <input type="hidden" name="PRGSA" value="su">
                    <input type="hidden" name="PRGCO" value="su">
                    <input type="hidden" name="PRGDI" value="su">
                    <input type="hidden" name="PRGFL" value="su">
                    <input type="hidden" name="PRGAL" value="su">
                    
                    <input type="hidden" name="PRGCN" value="su">   
                    
                    <input type="hidden" name="PRGSUBMIT" value="lgrmif">  
                </td>
              </tr>
              <tr>
                <td colspan="2" align="right">&nbsp;</td>
              </tr>
              <tr>
                <td colspan="2" align="right">
                  <input type="submit" name="sub_button" value="Log In"/>
                </td>
              </tr>
            </table>
      </form>
      <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

    <form action="index.jsp" method="GET">
      <table cellspacing="3" cellpadding="3" border="0" width="90%" summary="for layout only">
        <tr><td>&nbsp;</td></tr>
        <tr>
          <td align="center">
            <table width="400" summary="for layout only">
              <tr>
                <td rowspan="5" width="140">&nbsp;</td>
                <td colspan="2"><h2>ADMIN LDGrants Test Login</h2></td>                
              </tr>
              <tr>
                <td class="formLabel">User ID: </td>
                <td><input type="text" name="userid"></td>
              </tr>
              <tr>
                <td class="formLabel">Password: </td>
                <td><input  type="PASSWORD" name="password"></td>
                <td>                                         
                  <input type="HIDDEN" name="INSTID" value="800000055558" /><!-- upper hudson-->
                  <!--<input type="hidden" name="INSTID" value="800000053308"/>--><!--mid hudson ls-->
                  <!-- SL: Using these as preset values to  to pass in querystring and test login stuff  -->
                  <!-- Ultimately, these will come from LDAP  -->                
                  <input type="hidden" name="SM_USER" value="localpc">
                  <input type="HIDDEN" name="SM_EMAIL" value="stefanie.husak@nysed.gov" />             
                  <input type="hidden" name="ADMIN1" value="stat,coor,disc,fl,al,cn">  
                  <input type="hidden" name="ADMIN2" value="cn"/>
                  <input type="hidden" name="REVIEW" value="coor,disc,fl,al,cn">
                  <input type="hidden" name="SAREVIEW" value="lgrmif">  
                  <input type="hidden" name="SAADMIN" value="lgrmif">    
               
                </td>
              </tr>
              <tr>
                <td colspan="2" align="right">&nbsp;</td>
              </tr>
              <tr>
                <td colspan="2" align="right">
                  <input type="submit" name="sub_button" value="Log In"/>
                </td>
              </tr>
            </table>
      </form>
  </body>
</html>
