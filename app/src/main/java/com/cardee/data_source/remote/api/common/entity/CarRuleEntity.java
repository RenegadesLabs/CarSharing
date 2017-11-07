package com.cardee.data_source.remote.api.common.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarRuleEntity {

    @Expose
    @SerializedName("rule_id")
    private Integer ruleId;
    @Expose
    @SerializedName("rule_name")
    private String ruleName;
    @Expose
    @SerializedName("is_active_rule")
    private Boolean ruleActive;

    public CarRuleEntity() {

    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Boolean getRuleActive() {
        return ruleActive;
    }

    public void setRuleActive(Boolean ruleActive) {
        this.ruleActive = ruleActive;
    }
}
