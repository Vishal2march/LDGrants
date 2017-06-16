package mypackage;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import java.util.Date;
import org.apache.struts.action.ActionMapping;

public class PartInstBean extends ActionForm
{
  public long id;//co_institutions.id - primary key
  public String islead;
  public long instid;
  public long grantid;
  public String instName;
  public String address;
  public String city;
  public String state;
  public int rmoappointed;
  public int scheduleadopted;
  public String rmodate;
  public String scheduledate;
  public int eligibleId;

  public PartInstBean()
  {
  }

  public long getId()
  {
    return id;
  }

  public void setId(long id)
  {
    this.id = id;
  }

  public String getIslead()
  {
    return islead;
  }

  public void setIslead(String islead)
  {
    this.islead = islead;
  }

  public long getInstid()
  {
    return instid;
  }

  public void setInstid(long instid)
  {
    this.instid = instid;
  }

  public long getGrantid()
  {
    return grantid;
  }

  public void setGrantid(long grantid)
  {
    this.grantid = grantid;
  }

  public String getInstName()
  {
    return instName;
  }

  public void setInstName(String instName)
  {
    this.instName = instName;
  }

  public String getAddress()
  {
    return address;
  }

  public void setAddress(String address)
  {
    this.address = address;
  }

  public String getCity()
  {
    return city;
  }

  public void setCity(String city)
  {
    this.city = city;
  }

  public String getState()
  {
    return state;
  }

  public void setState(String state)
  {
    this.state = state;
  }

  public String getRmodate()
  {
    return rmodate;
  }

  public void setRmodate(String rmodate)
  {
    this.rmodate = rmodate;
  }

  public String getScheduledate()
  {
    return scheduledate;
  }

  public void setScheduledate(String scheduledate)
  {
    this.scheduledate = scheduledate;
  }

  public int getEligibleId()
  {
    return eligibleId;
  }

  public void setEligibleId(int eligibleId)
  {
    this.eligibleId = eligibleId;
  }

    public boolean isWrongYearFormat(String value) 
    {
      boolean badd = false;
      
      try{
        int yearint = Integer.parseInt(value);//fails if input=string or input= m/d/y
        if(yearint <1800 || yearint >2020)
            badd=true;
      }catch(Exception e)
      {badd = true; }
      
      return badd;
    }
  
  public boolean isMissing(String value) 
  {
    return((value == null) || (value.trim().equals("")));
  }
  
  public ActionErrors validate(ActionMapping mapping,  HttpServletRequest request) 
  {  
    ActionErrors errors = new ActionErrors();
    
    if(getRmoappointed()==0){ //0=yes, date required
      if( ! isMissing( getRmodate()) ){
            if(isWrongYearFormat(getRmodate()))
               errors.add("rmodate", new ActionError("errors.year", "RMO Year")); 
      }
      else//rmo=yes but rmodate is missing
        errors.add("rmodateMissing", new ActionError("value.required", "RMO Date"));
    }    
    
    if(getScheduleadopted()==0){ //0=yes, date required
      if( ! isMissing( getScheduledate()) ){
            if(isWrongYearFormat(getScheduledate()))
               errors.add("schdate", new ActionError("errors.year", "Schedule Year")); 
      }   
      else//schedule =yes but date is missing
        errors.add("scheddateMissing", new ActionError("value.required", "Schedule Date"));
    }
                     
    return(errors);
  }


    public void setRmoappointed(int rmoappointed) {
        this.rmoappointed = rmoappointed;
    }

    public int getRmoappointed() {
        return rmoappointed;
    }

    public void setScheduleadopted(int scheduleadopted) {
        this.scheduleadopted = scheduleadopted;
    }

    public int getScheduleadopted() {
        return scheduleadopted;
    }
}
