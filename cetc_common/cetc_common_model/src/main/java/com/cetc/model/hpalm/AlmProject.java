package com.cetc.model.hpalm;

import java.io.Serializable;
import java.util.Date;

public class AlmProject implements Serializable {
    private static final long serialVersionUID = -4609179487643407409L;

    private long id;

    private String name;

    private String domainName;

    private Date createTime;

    private Date updateTime;

    private String requirementFieldJson;

    private Boolean rfIsRefresh;

    private String requirementsJson;

    private Boolean rIsRefresh;

    private String defectFieldJson;

    private Boolean dfIsRefresh;

    private String defectsJson;

    private Boolean dIsRefresh;

    private String testFieldJson;

    private Boolean tfIsRefresh;

    private String testsJson;

    private Boolean tIsRefresh;

    private String designStepFieldJson;

    private Boolean dsfIsRefresh;

    private String designStepsJson;

    private Boolean dsIsRefresh;

    private Boolean isRefresh;

    public Boolean getRfIsRefresh() {
        return rfIsRefresh;
    }

    public void setRfIsRefresh(Boolean rfIsRefresh) {
        this.rfIsRefresh = rfIsRefresh;
    }

    public Boolean getrIsRefresh() {
        return rIsRefresh;
    }

    public void setrIsRefresh(Boolean rIsRefresh) {
        this.rIsRefresh = rIsRefresh;
    }

    public Boolean getDfIsRefresh() {
        return dfIsRefresh;
    }

    public void setDfIsRefresh(Boolean dfIsRefresh) {
        this.dfIsRefresh = dfIsRefresh;
    }

    public Boolean getdIsRefresh() {
        return dIsRefresh;
    }

    public void setdIsRefresh(Boolean dIsRefresh) {
        this.dIsRefresh = dIsRefresh;
    }

    public Boolean getTfIsRefresh() {
        return tfIsRefresh;
    }

    public void setTfIsRefresh(Boolean tfIsRefresh) {
        this.tfIsRefresh = tfIsRefresh;
    }

    public Boolean gettIsRefresh() {
        return tIsRefresh;
    }

    public void settIsRefresh(Boolean tIsRefresh) {
        this.tIsRefresh = tIsRefresh;
    }

    public Boolean getDsfIsRefresh() {
        return dsfIsRefresh;
    }

    public void setDsfIsRefresh(Boolean dsfIsRefresh) {
        this.dsfIsRefresh = dsfIsRefresh;
    }

    public Boolean getDsIsRefresh() {
        return dsIsRefresh;
    }

    public void setDsIsRefresh(Boolean dsIsRefresh) {
        this.dsIsRefresh = dsIsRefresh;
    }

    public Boolean getRefresh() {
        return isRefresh;
    }

    public void setRefresh(Boolean refresh) {
        isRefresh = refresh;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRequirementFieldJson() {
        return requirementFieldJson;
    }

    public void setRequirementFieldJson(String requirementFieldJson) {
        this.requirementFieldJson = requirementFieldJson;
    }

    public String getRequirementsJson() {
        return requirementsJson;
    }

    public void setRequirementsJson(String requirementsJson) {
        this.requirementsJson = requirementsJson;
    }

    public String getDefectFieldJson() {
        return defectFieldJson;
    }

    public void setDefectFieldJson(String defectFieldJson) {
        this.defectFieldJson = defectFieldJson;
    }

    public String getDefectsJson() {
        return defectsJson;
    }

    public void setDefectsJson(String defectsJson) {
        this.defectsJson = defectsJson;
    }

    public String getTestFieldJson() {
        return testFieldJson;
    }

    public void setTestFieldJson(String testFieldJson) {
        this.testFieldJson = testFieldJson;
    }

    public String getTestsJson() {
        return testsJson;
    }

    public void setTestsJson(String testsJson) {
        this.testsJson = testsJson;
    }

    public String getDesignStepFieldJson() {
        return designStepFieldJson;
    }

    public void setDesignStepFieldJson(String designStepFieldJson) {
        this.designStepFieldJson = designStepFieldJson;
    }

    public String getDesignStepsJson() {
        return designStepsJson;
    }

    public void setDesignStepsJson(String designStepsJson) {
        this.designStepsJson = designStepsJson;
    }
}
