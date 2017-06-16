package mypackage;

public class BudgetDeleteBean {
    public BudgetDeleteBean() {
    }
    
    public long id;
    public long grantid;
    public String description;
    public int tab;
    public String module;
    public int narrativeTypeId;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setGrantid(long grantid) {
        this.grantid = grantid;
    }

    public long getGrantid() {
        return grantid;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setTab(int tab) {
        this.tab = tab;
    }

    public int getTab() {
        return tab;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getModule() {
        return module;
    }

    public void setNarrativeTypeId(int narrativeTypeId) {
        this.narrativeTypeId = narrativeTypeId;
    }

    public int getNarrativeTypeId() {
        return narrativeTypeId;
    }
}
