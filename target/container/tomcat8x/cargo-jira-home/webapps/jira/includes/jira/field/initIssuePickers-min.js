define("jira/field/init-issue-pickers",["jira/field/issue-picker","jira/util/events","jira/util/events/types","jira/util/events/reasons","jquery","jira/util/formatter"],function(e,i,n,t,s,r){function u(i){s(i||document.body).find(".aui-field-issuepicker").each(function(){new e({element:s(this),userEnteredOptionsMsg:r.I18n.getText("linkissue.enter.issue.key"),uppercaseUserEnteredOnSelect:!0})})}i.bind(n.NEW_CONTENT_ADDED,function(e,i,n){n!==t.panelRefreshed&&u(i)})});