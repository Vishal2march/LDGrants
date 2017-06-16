package lgrmif;

import java.util.ArrayList;

import mypackage.PanelDbBean;

public class RevGrantSearch {
    public RevGrantSearch() {
    }       
    
    public ArrayList getReviewerByName(String lname, String panelid, String fyid) 
    {
        ArrayList allreviewers=new ArrayList();
        try{
            PanelDbBean pdb = new PanelDbBean();
            allreviewers = pdb.findReviewersByLname(lname, Long.parseLong(panelid), 
                                                           Integer.parseInt(fyid));            
        }
        catch(Exception e){System.out.println("error RevGrantSearch");}
        return allreviewers;
    }
    
    
    public ArrayList getPanelsByYear(String fyid) 
    {
        ArrayList panels=new ArrayList();
        try{
            PanelDbBean pdb = new PanelDbBean();
            panels =pdb.getPanelsForYear(Integer.parseInt(fyid));                      
        }
        catch(Exception e){System.out.println("error getPanelsByYear");}
        return panels;
    }    
    
    /*public ArrayList getGrantsByCategory(String projcategoryid, String panelid) 
    {
        ArrayList allreviewers=new ArrayList();
        try{
            PanelDbBean pdb = new PanelDbBean();
            allreviewers = pdb.findGrantsByCategory(Long.parseLong(projcategoryid), 
                                                    Long.parseLong(panelid));            
        }
        catch(Exception e){System.out.println("error getGrantsByCategory");};
        return allreviewers;
        
    }*/
    

}
