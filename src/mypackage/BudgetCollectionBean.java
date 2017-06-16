package mypackage;

import construction.FinalExpenseBean;
import construction.FundBean;

import java.util.List;
import java.util.ArrayList;
import org.apache.struts.action.ActionForm;

public class BudgetCollectionBean extends ActionForm
{
  public BudgetCollectionBean()
  {
  }  
 
  public List allExpRecords;
  public List allBenRecords;
  public List allTravelRecords;
  public List allPersRecords;  
  public List allContractRecords;
  public List allSupplyRecords;
    
  //these are for lgrmif-need salaries,equip,purchases separate by code
  public List allSupportRecords;
  public List allContBocesRecords;
  public List allEquipRecords;
  
  //this is for fs10a amendments
  public List allAmendRecords;
  
  //for construction only - other fund sources
  public List allFundSources;
  
  //for construction final expenses
  public List allFinalExpenses;
  
  //for new literacy budget summary
  public List allSummaryRecords;
  
  
  public List getAllExpRecords()
  {
    return allExpRecords;
  }

  public void setAllExpRecords(List allExpRecords)
  {
    this.allExpRecords = allExpRecords;
  }
  
   public OtherExpBean getOtherExpItem(int index)
  {        // make sure that orderList is not null
      if(this.allExpRecords == null)
      {
          this.allExpRecords = new ArrayList();
      }

      // indexes do not come in order, populate empty spots
      while(index >= this.allExpRecords.size())
      {
          this.allExpRecords.add(new OtherExpBean());
      }

      // return the requested item
      return (OtherExpBean) allExpRecords.get(index);
  }





  public void setAllPersRecords(List allPersRecords)
  {
    this.allPersRecords = allPersRecords;
  }

  public List getAllPersRecords()
  {
    return allPersRecords;
  }
  
  public PersonalBean getPersonalItem(int index)
  {        
      // make sure that List is not null
      if(this.allPersRecords == null)
      {
          this.allPersRecords = new ArrayList();
      }

      // indexes do not come in order, populate empty spots
      while(index >= this.allPersRecords.size())
      {
          this.allPersRecords.add(new PersonalBean());
      }

      // return the requested item
      return (PersonalBean) allPersRecords.get(index);
  }



  public void setAllBenRecords(List allBenRecords)
  {
    this.allBenRecords = allBenRecords;
  }

  public List getAllBenRecords()
  {
    return allBenRecords;
  }
 
   public BenefitsBean getBenefitItem(int index)
  {        
      // make sure that List is not null
      if(this.allBenRecords == null)
      {
          this.allBenRecords = new ArrayList();
      }

      // indexes do not come in order, populate empty spots
      while(index >= this.allBenRecords.size())
      {
          this.allBenRecords.add(new BenefitsBean());
      }

      // return the requested item
      return (BenefitsBean) allBenRecords.get(index);
  } 


  public void setAllContractRecords(List allContractRecords)
  {
    this.allContractRecords = allContractRecords;
  }

  public List getAllContractRecords()
  {
    return allContractRecords;
  }
  
  public ContractedBean getContractItem(int index)
  {        
      // make sure that List is not null
      if(this.allContractRecords == null)
      {
          this.allContractRecords = new ArrayList();
      }

      // indexes do not come in order, populate empty spots
      while(index >= this.allContractRecords.size())
      {
          this.allContractRecords.add(new ContractedBean());
      }

      // return the requested item
      return (ContractedBean) allContractRecords.get(index);
  } 


  public void setAllSupplyRecords(List allSupplyRecords)
  {
    this.allSupplyRecords = allSupplyRecords;
  }

  public List getAllSupplyRecords()
  {
    return allSupplyRecords;
  }
  
  public SupplyBean getSupplyItem(int index)
  {        
      // make sure that List is not null
      if(this.allSupplyRecords == null)
      {
          this.allSupplyRecords = new ArrayList();
      }

      // indexes do not come in order, populate empty spots
      while(index >= this.allSupplyRecords.size())
      {
          this.allSupplyRecords.add(new SupplyBean());
      }

      // return the requested item
      return (SupplyBean) allSupplyRecords.get(index);
  } 


  public void setAllTravelRecords(List allTravelRecords)
  {
    this.allTravelRecords = allTravelRecords;
  }

  public List getAllTravelRecords()
  {
    return allTravelRecords;
  }
  
  public TravelBean getTravelItem(int index)
  {        
      // make sure that List is not null
      if(this.allTravelRecords == null)
      {
          this.allTravelRecords = new ArrayList();
      }

      // indexes do not come in order, populate empty spots
      while(index >= this.allTravelRecords.size())
      {
          this.allTravelRecords.add(new TravelBean());
      }

      // return the requested item
      return (TravelBean) allTravelRecords.get(index);
  } 


  public void setAllSupportRecords(List allSupportRecords)
  {
    this.allSupportRecords = allSupportRecords;
  }

  public List getAllSupportRecords()
  {
    return allSupportRecords;
  }
  
  public PersonalBean getSupportItem(int index)
  {        
      // make sure that List is not null
      if(this.allSupportRecords == null)
      {
          this.allSupportRecords = new ArrayList();
      }

      // indexes do not come in order, populate empty spots
      while(index >= this.allSupportRecords.size())
      {
          this.allSupportRecords.add(new PersonalBean());
      }

      // return the requested item
      return (PersonalBean) allSupportRecords.get(index);
  } 
  

  public void setAllContBocesRecords(List allContBocesRecords)
  {
    this.allContBocesRecords = allContBocesRecords;
  }


  public List getAllContBocesRecords()
  {
    return allContBocesRecords;
  }
  
  public ContractedBean getContBocesItem(int index)
  {        
      // make sure that List is not null
      if(this.allContBocesRecords == null)
      {
          this.allContBocesRecords = new ArrayList();
      }

      // indexes do not come in order, populate empty spots
      while(index >= this.allContBocesRecords.size())
      {
          this.allSupplyRecords.add(new SupplyBean());
      }

      // return the requested item
      return (ContractedBean) allContBocesRecords.get(index);
  } 
  
  
  public void setAllEquipRecords(List allEquipRecords)
  {
    this.allEquipRecords = allEquipRecords;
  }

  public List getAllEquipRecords()
  {
    return allEquipRecords;
  }

    public void setAllAmendRecords(List allAmendRecords) {
        this.allAmendRecords = allAmendRecords;
    }

    public List getAllAmendRecords() {
        return allAmendRecords;
    }
    
    public FS10Bean getAmendItem(int index)
    {        
        // make sure that List is not null
        if(this.allAmendRecords == null)
        {
            this.allAmendRecords = new ArrayList();
        }

        // indexes do not come in order, populate empty spots
        while(index >= this.allAmendRecords.size())
        {
            this.allAmendRecords.add(new FS10Bean());
        }

        // return the requested item
        return (FS10Bean) allAmendRecords.get(index);
    }

    public void setAllFundSources(List allFundSources) {
        this.allFundSources = allFundSources;
    }

    public List getAllFundSources() {
        return allFundSources;
    }
    
    public FundBean getFundItem(int index)
    {        
        // make sure that List is not null
        if(this.allFundSources == null)
        {
            this.allFundSources = new ArrayList();
        }

        // indexes do not come in order, populate empty spots
        while(index >= this.allFundSources.size())
        {
            this.allFundSources.add(new FundBean());
        }

        // return the requested item
        return (FundBean) allFundSources.get(index);
    }


    public void setAllFinalExpenses(List allFinalExpenses) {
        this.allFinalExpenses = allFinalExpenses;
    }

    public List getAllFinalExpenses() {
        return allFinalExpenses;
    }
    
    
    public FinalExpenseBean getFinalExpenseItem(int index)
    {        
        // make sure that List is not null
        if(this.allFinalExpenses == null)
        {
            this.allFinalExpenses = new ArrayList();
        }

        // indexes do not come in order, populate empty spots
        while(index >= this.allFinalExpenses.size())
        {
            this.allFinalExpenses.add(new FinalExpenseBean());
        }

        // return the requested item
        return (FinalExpenseBean) allFinalExpenses.get(index);
    }
    
    
            
   /////////////FOR NEW LITERACY budget summary 1/21/16   
    
    
  public void setAllSummaryRecords(List allSummaryRecords) {
      this.allSummaryRecords = allSummaryRecords;
  }

  public List getAllSummaryRecords() {
      return allSummaryRecords;
  }
  
  
  public BudgetSummaryBean getSummaryRecordItem(int index)
  {        
      // make sure that List is not null
      if(this.allSummaryRecords == null)
      {
          this.allSummaryRecords = new ArrayList();
      }

      // indexes do not come in order, populate empty spots
      while(index >= this.allSummaryRecords.size())
      {
          this.allSummaryRecords.add(new BudgetSummaryBean());
      }

      // return the requested item
      return (BudgetSummaryBean) allSummaryRecords.get(index);
  }

  
}
