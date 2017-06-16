package construction;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class FundBean extends ActionForm{

    public long buildingFundId;
    public long amountReceived;
    public String amountReceivedStr;
    public int fundSourceId;
    public String description;
    public long grantId;
    public long grantBuildingId;
    public ArrayList allPossibleFundSources;
    public String fundSource;
    

    public FundBean() {
    }

    public void setBuildingFundId(long buildingFundId) {
        this.buildingFundId = buildingFundId;
    }

    public long getBuildingFundId() {
        return buildingFundId;
    }

    public void setAmountReceived(long amountReceived) {
        this.amountReceived = amountReceived;
    }

    public long getAmountReceived() {
        return amountReceived;
    }

    public void setFundSourceId(int fundSourceId) {
        this.fundSourceId = fundSourceId;
    }

    public int getFundSourceId() {
        return fundSourceId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setGrantId(long grantId) {
        this.grantId = grantId;
    }

    public long getGrantId() {
        return grantId;
    }

    public void setGrantBuildingId(long grantBuildingId) {
        this.grantBuildingId = grantBuildingId;
    }

    public long getGrantBuildingId() {
        return grantBuildingId;
    }

    public void setAmountReceivedStr(String amountReceivedStr) {
        this.amountReceivedStr = amountReceivedStr;
    }

    public String getAmountReceivedStr() {
        return amountReceivedStr;
    }

    public void setAllPossibleFundSources(ArrayList allPossibleFundSources) {
        this.allPossibleFundSources = allPossibleFundSources;
    }

    public ArrayList getAllPossibleFundSources() {
        return allPossibleFundSources;
    }

    public void setFundSource(String fundSource) {
        this.fundSource = fundSource;
    }

    public String getFundSource() {
        return fundSource;
    }
}
