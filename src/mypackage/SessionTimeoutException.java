package mypackage;

public class SessionTimeoutException extends Exception 
{
  String message = "Your Session has timed out. Please login again.";
  
  public SessionTimeoutException()
  {
    super();
  }
  
  public SessionTimeoutException(String err)
  {
    super();
    message = err;
  }


  public void setMessage(String message)
  {
    this.message = message;
  }


  public String getMessage()
  {
    return message;
  }
  
}