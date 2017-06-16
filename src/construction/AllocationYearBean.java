package construction;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.ArrayList;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;

public class AllocationYearBean extends ActionForm{
    
    public long systemYearDetailId;
    public String dueDateStr;
    public String programDueDateStr;
    public long initialAlloc;
    public String initialAllocStr;
    public long finalRecommend;
    public String finalRecommendStr;
    public long additionalAlloc;
    public String additionalAllocStr;
    public int fycode;
    public String year;
    public int fccode;//10/4/12 reusing class for construction/al/fl programs
    public long systemInstId;
    public String systemName;
    public long librarySystemMapId;
    public ArrayList allPossibleSystems;
    public ArrayList allPossibleYears;
    //following needed for amt req, 50%amt, totalawarded so far, etc.
    public double amountRequested;
    public int requestCost;
    public double maxRequestCost;
    public long tallyAmountRecommend;//amt recomm so far for this FY/PLS
    public long differenceAllocation;//diff b/w initalalloc and tallyamtrecommend
    public long amtRecommend;
    public boolean warning;//used for literacy over allocation
    public String flWarning;
    public String alWarning;
    public String litWarning;
    NumberFormat numF = new DecimalFormat("###,###,###");

    public AllocationYearBean() {
    }

    public void setSystemYearDetailId(long systemYearDetailId) {
        this.systemYearDetailId = systemYearDetailId;
    }

    public long getSystemYearDetailId() {
        return systemYearDetailId;
    }

    public void setDueDateStr(String dueDateStr) {
        this.dueDateStr = dueDateStr;
    }

    public String getDueDateStr() {
        return dueDateStr;
    }

    public void setInitialAlloc(long initialAlloc) {
        this.initialAlloc = initialAlloc;
    }

    public long getInitialAlloc() {
        return initialAlloc;
    }

    public void setInitialAllocStr(String initialAllocStr) {
        this.initialAllocStr = initialAllocStr;
    }

    public String getInitialAllocStr() {
        return initialAllocStr;
    }

    public void setFycode(int fycode) {
        this.fycode = fycode;
    }

    public int getFycode() {
        return fycode;
    }

    public void setSystemInstId(long systemInstId) {
        this.systemInstId = systemInstId;
    }

    public long getSystemInstId() {
        return systemInstId;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setLibrarySystemMapId(long librarySystemMapId) {
        this.librarySystemMapId = librarySystemMapId;
    }

    public long getLibrarySystemMapId() {
        return librarySystemMapId;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    public void setAllPossibleSystems(ArrayList allPossibleSystems) {
        this.allPossibleSystems = allPossibleSystems;
    }

    public ArrayList getAllPossibleSystems() {
        return allPossibleSystems;
    }

    public void setAllPossibleYears(ArrayList allPossibleYears) {
        this.allPossibleYears = allPossibleYears;
    }

    public ArrayList getAllPossibleYears() {
        return allPossibleYears;
    }

    public void setAmountRequested(double amountRequested) {
        this.amountRequested = amountRequested;
    }

    public double getAmountRequested() {
        return amountRequested;
    }

    public void setRequestCost(int requestCost) {
        this.requestCost = requestCost;
    }

    public int getRequestCost() {
        return requestCost;
    }

    public void setMaxRequestCost(double maxRequestCost) {
        this.maxRequestCost = maxRequestCost;
    }

    public double getMaxRequestCost() {
        return maxRequestCost;
    }

    public void setTallyAmountRecommend(long tallyAmountRecommend) {
        this.tallyAmountRecommend = tallyAmountRecommend;
    }

    public long getTallyAmountRecommend() {
        return tallyAmountRecommend;
    }

    public void setDifferenceAllocation(long differenceAllocation) {
        this.differenceAllocation = differenceAllocation;
    }

    public long getDifferenceAllocation() {
        return differenceAllocation;
    }

    public void setProgramDueDateStr(String programDueDateStr) {
        this.programDueDateStr = programDueDateStr;
    }

    public String getProgramDueDateStr() {
        return programDueDateStr;
    }

    public void setAdditionalAlloc(long additionalAlloc) {
        this.additionalAlloc = additionalAlloc;
    }

    public long getAdditionalAlloc() {
        return additionalAlloc;
    }

    public void setAdditionalAllocStr(String additionalAllocStr) {
        this.additionalAllocStr = additionalAllocStr;
    }

    public String getAdditionalAllocStr() {
        return additionalAllocStr;
    }

    public void setAmtRecommend(long amtRecommend) {
        this.amtRecommend = amtRecommend;
    }

    public long getAmtRecommend() {
        return amtRecommend;
    }

    public void setFccode(int fccode) {
        this.fccode = fccode;
    }

    public int getFccode() {
        return fccode;
    }

    public void setWarning(boolean warning) {
        this.warning = warning;
    }

    public boolean isWarning() {
        return warning;
    }

    public void setFlWarning(String flWarning) {
        this.flWarning = flWarning;
    }

    public String getFlWarning() {
        flWarning = "Notice: The Family Literacy allocation for this Library System is $" +numF.format(initialAlloc) +" per year.  "+
        "The actual amount depends on the NYS budget.";
        return flWarning;
    }

    public void setAlWarning(String alWarning) {
        this.alWarning = alWarning;
    }

    public String getAlWarning() {
        alWarning = "Notice: The Adult Literacy allocation for this Library System is $" +numF.format(initialAlloc) +" per year.  "+
        "The actual amount depends on the NYS budget.";
        return alWarning;
    }

    public void setLitWarning(String litWarning) {
        this.litWarning = litWarning;
    }

    public String getLitWarning() {
        litWarning = "Notice: The Literacy appropriation for this Library System is $" +numF.format(finalRecommend) +" " +
        " for fiscal year 20"+fycode+ ".  "+
        "The amount approved is more than the allocation.";
        return litWarning;
    }

    public void setFinalRecommend(long finalRecommend) {
        this.finalRecommend = finalRecommend;
    }

    public long getFinalRecommend() {
        return finalRecommend;
    }

    public void setFinalRecommendStr(String finalRecommendStr) {
        this.finalRecommendStr = finalRecommendStr;
    }

    public String getFinalRecommendStr() {
        return finalRecommendStr;
    }
}
