package com.cardee.data_source.remote.api.common.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarRuleEntity {

    @Expose
    @SerializedName("rules")
    private Rule[] rules;

    @Expose
    @SerializedName("car_other_rules")
    private String otherRules;

    public CarRuleEntity(/*Rule[] rules, */String otherRules) {
        this.rules = rules;
        this.otherRules = otherRules;
    }

    public static class Rule {

        @Expose
        @SerializedName("rule_id")
        private Integer ruleId;
        @Expose
        @SerializedName("is_active_rule")
        private Boolean ruleActive;

        public Rule(int ruleId, boolean isActive) {
            this.ruleId = ruleId;
            this.ruleActive = isActive;
        }

        public Integer getRuleId() {
            return ruleId;
        }

        public Boolean getRuleActive() {
            return ruleActive;
        }
    }

    public Rule[] getRules() {
        return rules;
    }

    public String getOtherRules() {
        return otherRules;
    }
}
