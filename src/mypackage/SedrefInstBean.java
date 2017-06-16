/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  SedrefInstBean.java
 * Creation/Modification History  :
 *
 * SH   2/1/07      Created
 *
 * Description
 * This class will store/retrieve the names and inst id's of the Big 11 from 
 * SEDREF.  Also map Big 11 sedcodes to main inst sedcode for CAU paperwork
 *****************************************************************************/
package mypackage;
import java.util.Vector;

public class SedrefInstBean 
{
  public String name;
  public String instID;
  public String instIDTest;

  public SedrefInstBean()
  {
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getInstID()
  {
    return instID;
  }

  public void setInstID(String instID)
  {
    this.instID = instID;
  }

  public String getInstIDTest()
  {
    return instIDTest;
  }

  public void setInstIDTest(String instIDTest)
  {
    this.instIDTest = instIDTest;
  }
  
  public String getMainInstSedcode(String librarySedcode){
      String mainSedcode=librarySedcode;
      
      try{
          if(librarySedcode.equalsIgnoreCase("800000060098"))//columbia
            mainSedcode="310500335580";
          else if(librarySedcode.equalsIgnoreCase("800000059892"))//cornell
            mainSedcode="610600335805";
          else if(librarySedcode.equalsIgnoreCase("800000060095"))//NYU
            mainSedcode="310200338145";
          else if(librarySedcode.equalsIgnoreCase("800000060121"))//NYPL
            mainSedcode="310200700015";
          else if(librarySedcode.equalsIgnoreCase("800000060476"))//NYSL
            mainSedcode="010100870001";
          else if(librarySedcode.equalsIgnoreCase("800000060104"))//rochester
            mainSedcode="261600339585";
          else if(librarySedcode.equalsIgnoreCase("800000060102"))//syracuse
            mainSedcode="421800339450";
          else if(librarySedcode.equalsIgnoreCase("800000060094"))//suny albany
            mainSedcode="010100533420";
          else if(librarySedcode.equalsIgnoreCase("800000060103"))//suny binghamton
            mainSedcode="030200533465";
          else if(librarySedcode.equalsIgnoreCase("800000060101"))//suny buffalo
            mainSedcode="140600533510";
          else if(librarySedcode.equalsIgnoreCase("800000060097"))//suny stony brook
            mainSedcode="580201533555";
            
            
      }catch(Exception e){System.out.println("error getMainInstSedcode "+e.getMessage().toString());}
      return mainSedcode;
  }



  public Vector getAllInstitutions()
  {
    Vector allInst = new Vector();
    
     SedrefInstBean bb = new SedrefInstBean();
     bb.setName("");
     bb.setInstID("0");
     bb.setInstIDTest("0");
     allInst.add(bb);
    
    SedrefInstBean sb5 = new SedrefInstBean();
    sb5.setName("SUNY Albany");
    sb5.setInstID("800000060094");
    sb5.setInstIDTest("800000055597");
    allInst.add(sb5);   
    
    SedrefInstBean sb7 = new SedrefInstBean();
    sb7.setName("SUNY Binghamton");
    sb7.setInstID("800000060103");
    sb7.setInstIDTest("800000055037");
    allInst.add(sb7);
    
    SedrefInstBean sb4 = new SedrefInstBean();
    sb4.setName("SUNY Buffalo");
    sb4.setInstID("800000060101");
    sb4.setInstIDTest("800000052554");
    allInst.add(sb4);
    
    SedrefInstBean sb9 = new SedrefInstBean();
    sb9.setName("SUNY Stony Brook");
    sb9.setInstID("800000060097");
    sb9.setInstIDTest("800000037713");
    allInst.add(sb9);
         
    SedrefInstBean sb2 = new SedrefInstBean();
    sb2.setName("Columbia University");
    sb2.setInstID("800000060098");
    sb2.setInstIDTest("800000047082");
    allInst.add(sb2);
    
    //create a bean for each inst and put in vector
    SedrefInstBean sb1 = new SedrefInstBean();
    sb1.setName("Cornell University");
    sb1.setInstID("800000059892");
    sb1.setInstIDTest("800000036397");
    allInst.add(sb1);
    
    SedrefInstBean sb8 = new SedrefInstBean();
    sb8.setName("NY Public Library");
    sb8.setInstID("800000060121");
    sb8.setInstIDTest("800000055848");
    allInst.add(sb8);    
    
    SedrefInstBean sb10 = new SedrefInstBean();
    sb10.setName("NY State Library");
    sb10.setInstID("800000060476");
    sb10.setInstIDTest("800000055504");
    allInst.add(sb10);
    
    SedrefInstBean sb = new SedrefInstBean();
    sb.setName("New York University");
    sb.setInstID("800000060095");
    sb.setInstIDTest("800000047869");
    allInst.add(sb);
       
    SedrefInstBean sb3 = new SedrefInstBean();
    sb3.setName("University of Rochester");
    sb3.setInstID("800000060104");
    sb3.setInstIDTest("800000050274");
    allInst.add(sb3);
         
    SedrefInstBean sb6 = new SedrefInstBean();
    sb6.setName("Syracuse University");
    sb6.setInstID("800000060102");
    sb6.setInstIDTest("800000040733");
    allInst.add(sb6);      
    
    return allInst;
  }
  
  
  
  
  
}