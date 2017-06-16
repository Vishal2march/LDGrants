package mypackage;
import org.apache.struts.action.ActionForm;

public class DropDownListBean extends ActionForm
{
  public int id;
  public String description;
  public long idLong;

  public DropDownListBean()
  {
  }

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

    public void setIdLong(long idLong) {
        this.idLong = idLong;
    }

    public long getIdLong() {
        return idLong;
    }
}
