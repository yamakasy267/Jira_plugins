require(['underscore', 'jira/util/data/meta', 'jira/admin/analytics'], function (_, Meta, adminAnalytics) {
    /**
     * Capture some events that better explain how people use JIRA administration in general.
     */
    AJS.toInit(function () {
        var activeTab = Meta.get('admin.active.tab');

        _.defer(function () {
            adminAnalytics.bindEvents();

            if (activeTab === "view_project_workflows") {
                adminAnalytics.sendLoadWorkflowsTabEvent();
            }
        });
    });
});