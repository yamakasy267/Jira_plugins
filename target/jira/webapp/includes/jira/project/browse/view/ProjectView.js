define('jira/project/browse/projectview', ['jquery', 'marionette', 'jira/util/data/meta', 'jira/moment', 'wrm/context-path', 'aui/flag', 'jira/ajs/ajax/ajax-util', 'jira/util/strings'], function ($, Marionette, meta, moment, contextPath, flag, ajaxUtil, stringUtil) {
    'use strict';

    return Marionette.ItemView.extend({
        template: function template(data) {
            data.isAdminMode = meta.get('in-admin-mode');
            data.archivingEnabled = meta.get('archiving-enabled');

            if (data.archivedTimestamp) {
                data.archivedDate = moment(data.archivedTimestamp).format("Do MMMM YYYY");
            }
            if (data.lastUpdatedTimestamp) {
                data.lastUpdatedDate = moment(data.lastUpdatedTimestamp).format("Do MMMM YYYY");
            }

            return JIRA.Templates.Project.Browse.projectRow(data);
        },

        ui: {
            'name': 'td.cell-type-name a',
            'leadUser': 'td.cell-type-user a',
            'category': 'td.cell-type-category a',
            'url': 'td.cell-type-url a',
            'restore': '.aui-list-truncate li a.restore-project'
        },
        events: {
            'click @ui.name': function clickUiName() {
                this.trigger('project-view.click-project-name', this.model);
            },
            'click @ui.leadUser': function clickUiLeadUser() {
                this.trigger('project-view.click-lead-user', this.model);
            },
            'click @ui.category': function clickUiCategory() {
                this.trigger('project-view.click-category', this.model);
            },
            'click @ui.url': function clickUiUrl() {
                this.trigger('project-view.click-url', this.model);
            }
        },
        onRender: function onRender() {
            this.unwrapTemplate();
            this.ui.restore.on("click", this.restoreProject.bind(this));
        },
        restoreProject: function restoreProject(event) {
            var _this = this;

            event.preventDefault();
            var projectKey = this.model.get('key');
            $.ajax({
                url: contextPath() + ('/rest/api/2/project/' + projectKey + '/restore'),
                type: 'PUT',
                dataType: 'text'
            }).success(function () {
                // little work around to remove model from collection without sending a request/accessing collection from model
                _this.model.trigger('destroy', _this.model);
                flag({
                    type: 'success',
                    close: 'manual',
                    body: JIRA.Templates.Project.Browse.restoreSuccessFlag({
                        projectName: stringUtil.escapeHtml(_this.model.get('name')),
                        projectKey: projectKey
                    })
                });
            }).error(function (xhr) {
                var errorMessage = ajaxUtil.getErrorMessageFromXHR(xhr);
                flag({
                    type: 'error',
                    close: 'manual',
                    body: errorMessage
                });
            });
        }
    });
});