 /******************************************************************************

  * @author  : Stefanie Husak
  * @Version : 3.0
  *
  * Development Environment        :  JDeveloper 11g
  * Name of the file               :  NarrativeDBbean.java
  * Creation/Modification History  : 05/13/2016
  *
  * SH   2/1/07      Created
  *      7/9/07      Modified to test for null or empty string before saving, otherwise
  *                  when reading in from the clob field you get a nullexception
  *                  
  *GH 05/13/2016     Modified implement spring jdbc; remove clobInitializer
  *
  *
  * Description
  * This class will update any of the clob narratives in the Project_narratives 
  * table.  The code was found online and modified.  It uses the SetBigStringTryClob
  * property to insert/update the clob data type field. 
  *****************************************************************************/
package mypackage;

import clobpackage.ClobBean;
import gov.nysed.oce.ldgrants.shared.dao.common.DatabaseConnectionInt;
import mypackage.AppStatusBean;
import mypackage.CoversheetBean;
import mypackage.DescriptionBean;
import mypackage.NarrInstructionBean;
import mypackage.UserBean;

import java.util.*;
import javax.servlet.http.*;


public class NarrativeDBbean implements DatabaseConnectionInt
{
  public NarrativeDBbean()
  {
  }  
 
    public void updateNarrative(CoversheetBean cb, UserBean userb, long grantid) {
        String update =
            "update PROJECT_NARRATIVES set NARRATIVE_DESCR = ?, " 
            + " MODIFIED_BY=?, DATE_MODIFIED=SYSDATE where GRA_ID = ? and NAT_ID = ?";

        try {

            jt.update(update, new Object[] { cb.getSummaryDesc(), userb.getUserid(),
                                             grantid, cb.getNarrTypeId() });

        } catch (Exception ex) {
            System.err.println("error updateNarrative() " + ex.toString());
        } finally {

        }

    }


    public void updateNarrative(UserBean userb, long grantid, DescriptionBean pdb) {
        String update =
            " update PROJECT_NARRATIVES set NARRATIVE_DESCR = ?, " 
            + " MODIFIED_BY=?, DATE_MODIFIED=SYSDATE where GRA_ID = ? and NAT_ID = ?";
        
        try {
            jt.update(update, 
                      new Object[] { pdb.getNarrative(), userb.getUserid(),
                                     grantid, pdb.getNarrTypeID() });
       
        } catch (Exception ex) {
            System.err.println("error updateNarrative() " + ex.toString());

        } finally {

        }

    }
  
  
  public void updateNarrative(UserBean userb, long grantid, int narrTypeId, String narrative)
  {         
      String update = " update PROJECT_NARRATIVES set NARRATIVE_DESCR = ?, "+
            " MODIFIED_BY=?, DATE_MODIFIED=SYSDATE where GRA_ID = ? and NAT_ID = ?";     
    try {                      
        jt.update(update, 
                  new Object[] { narrative, userb.getUserid(),
                                 grantid, narrTypeId});
                                                   
    } catch (Exception ex){
        System.err.println("error updateNarrative() " + ex.toString());    
    }
    finally{
    }
  }
  
  
  
  /**
   * The narrTypeID was previously an int 1/4/08
   * @return 
   * @param narrTypeID
   * @param userb
   * @param grantid
   */
  public void insertNarrativeRecord(UserBean userb, long grantid, int narrTypeID)
  {
      String  update = "insert into project_narratives(ID, GRA_ID, NARRATIVE_DESCR, NAT_ID, "+
       " DATE_CREATED, CREATED_BY) VALUES (proj_narrative_seq.nextval, ?, "+
         " EMPTY_CLOB(), ?, SYSDATE, ?) ";

    try {               
        jt.update(update, 
                  new Object[] { grantid, narrTypeID,
                                 userb.getUserid()});   
    
    } catch (Exception ex){
        System.err.println("error insertNarrativeRecord() " + ex.toString());
    }
    finally
    {
    }
    
  }

    /**
     * The narrTypeID was previously an int 1/4/08
     * @return 
     * @param narrTypeID
     * @param grantid
     */
    public void deleteNarrativeRecord(long grantid, int narrTypeID)
    {
        String update = "DELETE FROM Project_Narratives WHERE gra_id =? AND nat_id = ? "; 

      try {               

      jt.update(update, 
                    new Object[] { grantid, narrTypeID});   
      } catch (Exception ex){
          System.err.println("error deleteNarrativeRecord() " + ex.toString());
      }
      finally
      {
     
      }    
    }

    public String getNarrativeTitle(int narrTypeID) {
        String title = "";
        try {
            List queryList =
                jt.queryForList("select narrative_name from narrative_types where id=?", new Object[] { narrTypeID });

            for (int i = 0; i < queryList.size(); i++) {
                Map queryMap = (Map)queryList.get(i);
                title = (String)queryMap.get("narrative_name");
            }
        } catch (Exception ex) {
            System.err.println("error getNarrativeTitle() " + ex.toString());
        } finally {
        }
        return title;
    }

  /**
   * Method will get narrative instructions for DI
   * @return 
   * @param narrTypeID
   */
  public String getNarrativeInstructions(int narrTypeID)
  {
    NarrInstructionBean nib = new NarrInstructionBean();
    String descr = "";
    
    switch(narrTypeID)
    {
      case 2:
          descr = nib.getNarrType2Di();
          break;
      case 19:
          descr = nib.getNarrType19();
          break;
      case 20:
          descr = nib.getNarrType20();
          break;
      case 3:
          descr = nib.getNarrType3();
          break;
      case 16:
          descr = nib.getNarrType16();
          break;
      case 21:
          descr = nib.getNarrType21();
          break;
      case 22:
          descr = nib.getNarrType22();
          break;
      case 23:
          descr = nib.getNarrType23();
          break;
      case 24:
          descr = nib.getNarrType24();
          break;
      case 10:
          descr = nib.getNarrType10();
          break;
      case 25:
          descr = nib.getNarrType25();
          break;
      case 6:
          descr = nib.getNarrType6();
          break;
      case 7:
          descr = nib.getNarrType7();
          break;
      case 12:
          descr = nib.getNarrType12();
          break;
      case 13:
          descr = nib.getNarrType13();
          break;
      case 15:
          descr = nib.getNarrType15();
          break;
      case 17:
          descr = nib.getNarrType17();
          break;
      case 18:
          descr = nib.getNarrType18();
          break;      
      case 66:
          descr = nib.getNarrType66();
          break;
      case 67:
          descr = nib.getNarrType67();
          break;
      case 68:
          descr = nib.getNarrType68();
          break;
      case 34:
          descr = nib.getNarrType34();
          break;    
    }
    
    return descr;
  }
  
  
  /**
     *modified 8/19/13 to add fycode param; FC wants to change narr instructions
     * starting 2013-14, keep old instruct for prior grants
     * @param narrTypeID
     * @param fycode
     * @return
     */
   public String getNarrativeInstructionsLg(int narrTypeID, int fycode)
  {
    NarrInstructionBean nib = new NarrInstructionBean();
    String descr = "";
    
    switch(narrTypeID)
    {
      case 2://final rpt
          if(fycode <14)//new final narr starting FY 2013-14
            descr = nib.getNarrTypeLg2();
          else
            descr = nib.getNarrTypeLg2New();  
          break;
      case 69://1a - statement of problem
          if(fycode < 15)//new narrs starting FY 2014-15
            descr = nib.getNarrType69();
          else if(fycode==15)
            descr = nib.getNarrType69New();
          else
            descr = nib.getNarrType69NewDm();//new starting fy2015-16
          break;
      case 70://1b - identify records
          if(fycode < 15)
            descr = nib.getNarrType70();
          else if(fycode==15)
            descr = nib.getNarrType70New();
          else
            descr = nib.getNarrType70NewDm();
          break;
      case 71:
          descr = nib.getNarrType71();
          break;
      case 72://2a - results, benefits
          if(fycode < 15)
            descr = nib.getNarrType72();
          else if(fycode==15)
            descr = nib.getNarrType72New();
          else
            descr = nib.getNarrType72NewDm();
          break;
      case 73://2b - solutions
          if(fycode < 15)
            descr = nib.getNarrType73();
          else if(fycode==15)
            descr = nib.getNarrType73New();
          else
            descr = nib.getNarrType73NewDm();
          break;
      case 74://3a - timeline, work activities
          if(fycode < 15)
            descr = nib.getNarrType74();
          else if(fycode==15)
            descr = nib.getNarrType74New();
          else
            descr = nib.getNarrType74NewDm();
          break;
      case 75://3b - category requirements
          if(fycode < 15)
            descr = nib.getNarrType75();
          else if(fycode==15)
            descr = nib.getNarrType75New();
          else
            descr = nib.getNarrType75NewDm();
          break;
      case 76://3c - qualifications, mangment
          if(fycode < 15)
            descr = nib.getNarrType76();
          else
            descr = nib.getNarrType76New();
          break;
      case 77://4a - contributions
          if(fycode < 15)
            descr = nib.getNarrType77();
          else if(fycode==15)
            descr = nib.getNarrType77New();
          else
            descr = nib.getNarrType77NewDm();
          break;
      case 78://4b - maintain results
          if(fycode < 15)
            descr = nib.getNarrType78();
          else if(fycode==15)
            descr = nib.getNarrType78New();
          else
            descr = nib.getNarrType78NewDm();
          break;
      case 79:
          descr = nib.getNarrTypeLg79();//remodeling
          break;
      case 80:
          descr = nib.getNarrTypeLg80();//purch serv boces
          break;
      //case 35:
      case 83:
      case 81:
          descr = nib.getNarrTypeLg35();//prof/support salaries
          break;
      case 82:
          descr = nib.getNarrTypeLg82();//equip
          break;
      //case 37:
      case 87:
          descr = nib.getNarrTypeLg37();//benefits
          break;
      //case 38:
      case 84:
          descr = nib.getNarrTypeLg38();//purchased serv
          break;      
      //case 39:
      case 85:
          descr = nib.getNarrTypeLg39();//supp/materials
          break;
      //case 40:
      case 86:
          descr = nib.getNarrTypeLg40();//travel
          break;    
      case 88:
          descr = nib.getNarrTypeLg88();//new 9/17/10 per FC.
          break;
    }    
    return descr;
  }
  
  
  public String getNarrativeInstructionsLi(int narrTypeID, int fccode, int fycode)
  {
    NarrInstructionBean nib = new NarrInstructionBean();
    String descr = "";
    
    switch(narrTypeID)
    {
      case 1:
          descr = nib.getNarrType1Li();
          break;
      case 26:
          descr = nib.getNarrType26Li();
          break;
      case 5:
          descr = nib.getNarrType5Li();
          break;
      case 27:
          descr = nib.getNarrType27Li();
          break;
      case 8:
          descr = nib.getNarrType8Li();
          break;
      case 28:
          descr = nib.getNarrType28Li(fccode);
          break;
      case 3:
          descr = nib.getNarrType3Li();
          break;
      case 12:
          descr = nib.getNarrType12Li();
          break;
      case 10:
          descr = nib.getNarrType10();
          break;
      case 29:
          descr = nib.getNarrType29Li(fccode);
          break;
      case 30:
          descr = nib.getNarrType30Li();
          break;
      case 31:
          descr = nib.getNarrType31Li(fccode);
          break;
      case 32:
          descr = nib.getNarrType32Li();
          break;
      case 33:
          descr = nib.getNarrType33Li();
          break;
      case 34:
          descr = nib.getNarrType34Li();
          break;
      case 35:
          descr= nib.getNarrType35Li();//proffessional staff
          break;
      case 36:
          descr= nib.getNarrType36Li();
          break;
      case 37:
          descr= nib.getNarrType37Li();
          break;
      case 38:
          descr= nib.getNarrType38Li();
          break;
      case 39:
          descr= nib.getNarrType39Li();//supplies/materials
          break;
      case 40:
          descr= nib.getNarrType40Li();//travel
          break;          
      case 81:
          descr= nib.getNarrType35Li();//support staff
          break;
      case 82:
          descr= nib.getNarrType39Li();//equip
          break;
      case 41://final rpt - synopsis - yr 1,2,3
      case 55:
      case 96:
          descr= nib.getNarrType41Li();
          break;
      case 42://final rpt - need - yr 1,2,3
      case 56:
      case 97:
          descr= nib.getNarrType42Li();
          break;
      case 43://final rpt - target audience - yr 1,2,3
      case 57:
      case 98:
          descr= nib.getNarrType43Li();
          break;
      case 44://final rpt - coordination agencies - yr 1,2,3
      case 58:
      case 99:
          descr= nib.getNarrType44Li();
          break;
      case 45://final rpt - accomplishments - yr 1,2,3
      case 59:
      case 100:
          descr= nib.getNarrType45Li();
          break;
      case 46://final rpt - eval methods - yr 1,2,3
      case 63:
      case 104:
          descr= nib.getNarrType46Li(fccode, fycode);
          break;
      case 47://final rpt - continuation - yr 1,2,3
      case 60:
      case 101:
          descr= nib.getNarrType47Li();
          break;
      case 48://final rpt - sharing results/publicity - yr 1,2,3
      case 61:
      case 102:
          descr= nib.getNarrType48Li(fccode, fycode);
          break;
      case 49://final rpt - problems - yr 1,2,3
      case 62:
      case 103:
          descr= nib.getNarrType49Li();
          break;
      case 50://final rpt - budget changes - yr 1,2,3
      case 64:
      case 105:
          descr= nib.getNarrType50Li(fccode, fycode);
          break;
      case 51://final rpt - additional funds - yr 1,2,3
      case 65:
      case 106:
          descr= nib.getNarrType51Li();
          break;    
      case 52:
          descr= nib.getNarrType52Li();
          break;    
      case 53:
          descr= nib.getNarrType53Li();
          break;    
      case 54:
          descr= nib.getNarrType54Li();
          break;    
      case 111://final rpt - planning experience - yr 1,2,3; FL2013-14
      case 112:
      case 113:
          descr= nib.getNarrType111Li();
          break;
      case 114://final rpt - family component - yr 1,2,3; FL2013-14
      case 115:
      case 116:
          descr= nib.getNarrType114Li();
          break;
      case 118:
          descr= nib.getNarrType118Li(fycode);
          break;
      case 119:
          descr= nib.getNarrType119Li();//119 and 120 are new for Family FY16-19
          break;
      case 120:
          descr= nib.getNarrType120Li();
          break;
      case 121:
          descr= nib.getNarrType121Li();//new equipment for 2016-19
          break;
      case 122:
      case 123:
      case 124:
          descr = nib.getNarrType122Li();//goals/objectives/activities - yr1/2/3 final rpt fy 16-19
          break;
      case 125:
      case 126:
      case 127:
          descr = nib.getNarrType125Li();//evaluation outcome/outputs - yr1/2/3 final rpt fy 16-19
          break;
     default:
          descr="Not found.";
          break;
    }
    
    return descr;
  }
  
  
  public String getNarrativeInstructionsCo(int narrTypeID)
  {
    NarrInstructionBean nib = new NarrInstructionBean();
    String descr = "";
    
    switch(narrTypeID)
    {
      case 1:
          descr = nib.getNarrType1();
          break;
      case 2:
      case 89:
      case 90:
          descr = nib.getNarrType2Co();
          break;
      case 6:
          descr = nib.getNarrType6Co();
          break;
      case 7:
          descr = nib.getNarrType7Co();
          break;
      case 8:
          descr = nib.getNarrType8Co();
          break;
      case 9:
          descr = nib.getNarrType9Co();
          break;
      case 10:
          descr = nib.getNarrType10Co();
          break;
      case 11:
          descr = nib.getNarrType11Co();
          break;
      case 12:
          descr = nib.getNarrType12Co();
          break;
      case 13:
          descr = nib.getNarrType13Co();
          break;
      case 14:
          descr = nib.getNarrType14Co();
          break;
      case 15:
          descr = nib.getNarrType15Co();
          break;
      case 16:
          descr = nib.getNarrType16Co();
          break;
      case 17:
          descr = nib.getNarrType17Co();
          break;
      case 18:
          descr = nib.getNarrType18();
          break;      
      case 66://following 4 narrs are for education proposals
          descr = nib.getNarrType66();
          break;
      case 67:
          descr = nib.getNarrType67();
          break;
      case 68:
          descr = nib.getNarrType68();
          break;
      case 34:
          descr = nib.getNarrType34();
          break;      
    }    
    return descr;
  }

 
  
  //cp statutory
  public String getNarrativeInstructionsSa(int narrTypeID)
  {
    NarrInstructionBean nib = new NarrInstructionBean();
    String descr = "";
      
    switch(narrTypeID){
      case 3:
          descr = nib.getNarrType3sa();
          break;
      case 4:
          descr = nib.getNarrType4sa();
          break;
      case 5:
          descr = nib.getNarrType5sa();
          break;
    }    
    return descr;
  }
  
  //stateaid  cjh/nyhs
  public String getNarrativeInstructionsStaid(int narrTypeID)
  {
    NarrInstructionBean nib = new NarrInstructionBean();
    String descr = "";
      
    switch(narrTypeID){
      case 3:
          descr = nib.getNarrType3staid();
          break;
      case 93:
          descr = nib.getNarrType93staid();
          break;
    }    
    return descr;
  }
  
  
 
  //construction
    public String getNarrativeInstructionsCn(int narrTypeID)
    {
      NarrInstructionBean nib = new NarrInstructionBean();
      String descr = "";
        
      switch(narrTypeID){
          case 94:
              descr = nib.getNarrTypeCon94();
              break;
          case 91:
              descr = nib.getNarrTypeCon91();
              break;
          case 92:
              descr = nib.getNarrTypeCon92();
              break;
          case 12:
              descr = nib.getNarrTypeCon12();
              break;
          case 93:
            descr = nib.getNarrTypeCon93();
            break;
          case 95:
            descr = nib.getNarrTypeCon95();
            break;
      } 
      return descr;
    }  
  
  public int getBudgetTabNarrMapping(String tab)
  {
    int narrTypeId=0;
    try{
      int ctab = Integer.parseInt(tab);
      
      switch(ctab){
            case 1:
                narrTypeId =35;
                break;
            case 2:
                narrTypeId =37;
                break;
            case 3:
                narrTypeId =38;
                break;
            case 4:
                narrTypeId =39;
                break;
            case 6:
                narrTypeId =40;
                break;
          }      
          
    }catch(Exception e){
      System.out.println("error getBudgetTabNarrMapping "+e.getMessage().toString());
    }
    return narrTypeId;
  }
  
    public int getNewLitTabNarrMapping(String tab)
    {
      int narrTypeId=0;
      try{
        int ctab = Integer.parseInt(tab);
        
        switch(ctab){
              case 1:
                  narrTypeId =35;//proff sal
                  break;
              case 2:
                  narrTypeId =81;//support staff sal
                  break;
              case 3:
                  narrTypeId =37;//benefits
                  break;
              case 4:
                  narrTypeId =38;//purchased
                  break;
              case 5:
                  narrTypeId =39;//supplies/materials
                  break;
              case 6:
                  narrTypeId =82;//equipment
                  break;          
              case 7:
                  narrTypeId =40;//travel
                  break;
            }      
            
      }catch(Exception e){
        System.out.println("error getNewLitTabNarrMapping "+e.getMessage().toString());
      }
      return narrTypeId;
    }
  
  
  public void getLitBudgetNarrative(HttpServletRequest request, String tab, long grantid, int fccode, int fycode)
  {
    try{            
         //get cooresponding budget narrId
         int narrTypeInt= 0;
         //if(newTabMap)
            narrTypeInt=getNewLitTabNarrMapping(tab);
         //else
         //   narrTypeInt=getBudgetTabNarrMapping(tab);
                           
          DescriptionBean pdb = new DescriptionBean();              
          ClobBean cb = new ClobBean();
          cb.setGrantid(grantid);
          cb.openOracleConnection();      
          cb.getClobNarrative(narrTypeInt); 
          cb.closeOracleConnection();
          
          pdb.setNarrative(cb.getData());
          pdb.setId(cb.getNarrID());
          pdb.setNarrTypeID(narrTypeInt);  
          pdb.setNarrativeTitle(getNarrativeTitle(narrTypeInt));
          pdb.setNarrativeDescr(getNarrativeInstructionsLi(narrTypeInt, fccode, fycode));          
          request.setAttribute("projNarrative", pdb);   
      
    }catch(Exception e){
       System.out.println("error getLitBudgetNarrative "+e.getMessage().toString());
    }
  }
  
  public void getLgBudgetNarrative(HttpServletRequest request, String tab, long grantid)
  {
    int narrTypeId =0;
    try{
       //get cooresponding budget narrId
       int ctab = Integer.parseInt(tab);
      
        switch(ctab){
              case 1:
                  //narrTypeId =35;//prof salaries
                  narrTypeId=83;
                  break;
              case 2:
                  narrTypeId =81;//suport salaries
                  break;
              case 3:
                  narrTypeId =82;//equip
                  break;
              case 4:
                  narrTypeId =79;//remodeling
                  break;
              case 5:
                  //narrTypeId =38;//purch serv
                  narrTypeId =84;
                  break;
              case 6:
                  narrTypeId =80;//purch boces
                  break;
              case 7:
                 // narrTypeId =39;//supp/materials
                  narrTypeId=85;
                  break;
              case 8:
                  //narrTypeId =40;//travel
                  narrTypeId=86;
                  break;
              case 9:
                  //narrTypeId =37;//benefits
                  narrTypeId=87;
                  break;
          }
                               
          
          DescriptionBean pdb = new DescriptionBean();    
          pdb.setProgram(tab);//budget tab
          ClobBean cb = new ClobBean();
          cb.setGrantid(grantid);
          cb.openOracleConnection();      
          cb.getClobNarrative(narrTypeId); 
          cb.closeOracleConnection();
          
          pdb.setNarrative(cb.getData());
          pdb.setId(cb.getNarrID());
          pdb.setNarrTypeID(narrTypeId);  
          pdb.setNarrativeTitle(getNarrativeTitle(narrTypeId));
          pdb.setNarrativeDescr(getNarrativeInstructionsLg(narrTypeId, 0));   
          request.setAttribute("projNarrative", pdb);
               
    }catch(Exception e){
      System.out.println("error getLgBudgetNarrative "+e.getMessage().toString());
    }
  }
  
  public boolean determineNarrativeLock(AppStatusBean asb, int narrTypeId)
  {
    boolean lockNarr = false;
    try{
    
      switch(narrTypeId)
      {
        case 2:
        case 89:
        case 90:
            if(asb.isPendingFinalRev() || asb.isFinalnarrativeyn()  )
              lockNarr=true;
            break;         
        default:
            if(asb.isPendingReview() || asb.isProjdescyn() )
              lockNarr=true;
              break;
      }
    }catch(Exception e){
      System.out.println("error determineNarrativeLock "+e.getMessage().toString());
    }
    return lockNarr;
  }
  
}
