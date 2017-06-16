/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  UploadDocBean.java
 * Creation/Modification History  :
 *
 * SH   6/14/07      Created
 *
 * Description
 * This class will store/retrieve info about documents that are in the uploads table.
 * It will get the name, document type, size, date saved, and user that saved the document
 * to the database. 
 * $Id: 
 *****************************************************************************/
package mypackage;
import java.util.Date;

public class UploadDocBean 
{
  public long id;
  public long grantid;
  public String createdBy;
  public Date dateCreated;
  public String blobContent;
  public String contentType;
  public String docSize;
  public String name;
  public String docType;
  public String description;
  public String program;

  public UploadDocBean()
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



  public long getGrantid()
  {
    return grantid;
  }

  public void setGrantid(long grantid)
  {
    this.grantid = grantid;
  }

  public String getCreatedBy()
  {
    return createdBy;
  }

  public void setCreatedBy(String createdBy)
  {
    this.createdBy = createdBy;
  }

  public Date getDateCreated()
  {
    return dateCreated;
  }

  public void setDateCreated(Date dateCreated)
  {
    this.dateCreated = dateCreated;
  }

  public String getBlobContent()
  {
    return blobContent;
  }

  public void setBlobContent(String blobContent)
  {
    this.blobContent = blobContent;
  }

  public String getContentType()
  {
    return contentType;
  }

  public void setContentType(String contentType)
  {
    this.contentType = contentType;
  }

  public String getDocSize()
  {
    return docSize;
  }

  public void setDocSize(String docSize)
  {
    this.docSize = docSize;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getDocType()
  {
    return docType;
  }

  public void setDocType(String docType)
  {
    this.docType = docType;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public String getProgram()
  {
    return program;
  }

  public void setProgram(String program)
  {
    this.program = program;
  }
}