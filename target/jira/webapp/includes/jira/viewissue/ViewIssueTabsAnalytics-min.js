define("jira/viewissue/tabs/analytics",["jquery","jira/analytics","jira/util/strings","jira/viewissue/analytics-utils","underscore"],function(a,t,e,i,s){function n(a){return o.indexOf(a)>-1?a:e.hashCode(a)}function r(a){return{tab:n(a.attr("data-key")),tabPosition:a.index()}}function l(a,t){return{inNewWindow:a,keyboard:t,context:i.context()}}var o=["com.atlassian.jira.plugin.system.issuetabpanels:all-tabpanel","com.atlassian.jira.plugin.system.issuetabpanels:comment-tabpanel","com.atlassian.jira.plugin.system.issuetabpanels:worklog-tabpanel","com.atlassian.jira.plugin.system.issuetabpanels:changehistory-tabpanel","com.atlassian.streams.streams-jira-plugin:activity-stream-issue-tab"];return{tabClicked:function(a,e,i){var n=a.parent(),o=s.extend({},l(e,i),r(n));t.send({name:"jira.viewissue.tab.clicked",properties:o})},buttonClicked:function(a,e,i){if(a.is("[data-tab-sort]")){var n=a.data("order"),o=a.parents(".tabwrap").find("li.active");t.send({name:"jira.viewissue.tabsort.clicked",properties:s.extend({},l(e,i),r(o),{order:n})})}}}});