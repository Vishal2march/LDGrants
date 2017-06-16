package mypackage;
import java.util.ArrayList;
import java.util.List;
import org.apache.struts.action.ActionForm;

public class PartCollectionBean extends ActionForm
{
  public List allPartInst;


  public void setAllPartInst(List allPartInst)
  {
    this.allPartInst = allPartInst;
  }

  public List getAllPartInst()
  {
    return allPartInst;
  }
  
  public PartInstBean getPartinstItem(int index)
  {        // make sure that orderList is not null
      if(this.allPartInst == null)
      {
          this.allPartInst = new ArrayList();
      }

      // indexes do not come in order, populate empty spots
      while(index >= this.allPartInst.size())
      {
          this.allPartInst.add(new PartInstBean());
      }

      // return the requested item
      return (PartInstBean) allPartInst.get(index);
  }
}