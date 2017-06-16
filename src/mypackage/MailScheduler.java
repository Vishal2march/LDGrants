/******************************************************************************
 * @author  : Anton Keller
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  MailScheduler.java
 * Creation/Modification History  :
 *
 * SH   8/14/07     Modified methods to call MailScheduler instead of sending the
 *                  email message b/c mailscheduler uses threads so its faster
 * SH   6/8/09 THIS CLASS NO LONGER USED.  SEDEMS now sends all LDGrants emails.
 * 
 * Description
 * This class will handle the sending of all email messages for the system.  It has 
 * methods to send correction emails, application submitted emails, and application
 * approved emails.  It has methods to determine the PO officer that the mail should
 * be sent to, and has vars that store the general messages. 
 *****************************************************************************/
package mypackage;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class MailScheduler
{
  private static final boolean USE_WAIT_NOTIFY = true;
  private static MailScheduler sched = null;
  private LinkedList mailQ;
  private int numWorkers = 5; // Number of worker threads. Num of simultaneous SMTP connections allowed.
  private Mailer[] workers; 
  
  private MailScheduler()
  {
    mailQ = new LinkedList();
    workers = new Mailer[numWorkers];
    for(int i=0; i < numWorkers; i++) 
    {
      workers[i]=new Mailer(i+1);   // ID 0 is not used.
    }
    start();
  }
  public static MailScheduler getInstance() 
  {
    if(sched==null) 
    {
      sched = new MailScheduler();
      // start the worker threads.
    }
    return sched;
  }
  public void enqueue(EMailMessage msg) 
  {
    synchronized(mailQ) {
      mailQ.add(msg);
      // If mail queue was empty, the mailer threads are waiting on it.
      // if(mailQ.size()==1 && USE_WAIT_NOTIFY) mailQ.notifyAll();  // releases the lock on mailQ here.
      if(USE_WAIT_NOTIFY) mailQ.notify(); // notify a waiting thread.
    }
  }
  public EMailMessage dequeue()
  {
    synchronized(mailQ) 
    {
      try {
        return (EMailMessage)mailQ.removeFirst();
      } catch (NoSuchElementException nseEx) {}
    }
    return null;
  }
  public void start() 
  {
    for(int i=0; i < numWorkers; i++) 
    {
      if(!workers[i].isAlive()) workers[i].start();
    }
  }
  public void stop() 
  {
    // signal stop to all worker threads.
    for(int i=0; i < numWorkers; i++) 
    {
      workers[i].shutdown();
    }
  }
  
  private class Mailer extends Thread 
  {
    private boolean stop = false;
    private int freq = 15;  // run every 15 seconds.
    
    public Mailer(int id) {
      super("Mailer#" + id);
    }
    public void shutdown() {
      stop = false;
      interrupt();
    }
    public void run() 
    {
      EMailMessage msg;
      long starttime, endtime;
      //System.out.println(Thread.currentThread().getName() + " started.");
      while(!stop) 
      {
        int count = 0;
        starttime = System.currentTimeMillis();
        
        while((msg=dequeue())!=null) {
          ++count;
          if(msg.send()) 
          {
            System.out.println(Thread.currentThread().getName() + ": Success - Mail (" + msg.subject + ") sent to " + msg.to[0]);
          } else 
          {
            System.out.println(Thread.currentThread().getName() + ": Failure - Mail (" + msg.subject + ") NOT sent to " + msg.to[0]);
          }
        }
        
        endtime = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + ": Processed " + count + " messages.");
        
        // sleep for the remaining time? or wait/notify?
        if(USE_WAIT_NOTIFY)
          waitForMessages();
        else 
          sleepThread(starttime, endtime);
      }
      
      //System.out.println(Thread.currentThread().getName() + " exiting.");
    }
    
    private void sleepThread(long starttime, long endtime) {
      long sleeptime = starttime + freq * 1000 - endtime;
      //System.out.println(Thread.currentThread().getName() + ": Sleep for " + sleeptime + " millis.");
      if(sleeptime>0) {
        try 
        {
          Thread.sleep(sleeptime);
        } catch (InterruptedException iEx) {
          System.out.println(Thread.currentThread().getName() + " interrupted.");
        }
      }
    }
    
    private void waitForMessages() {
      synchronized(mailQ) {
        try {
          //System.out.println(Thread.currentThread().getName() + " going to wait.");
          mailQ.wait(); // releases lock on mailQ here.
        } catch (InterruptedException iEx) {
          System.out.println(Thread.currentThread().getName() + " interrupted.");
        }
      }
    }
  }
}