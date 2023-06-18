/**
 * This view renders an AUI message when there are no custom fields
 */
define('jira/customfields/customfieldsEmptyView', ['jira/marionette-3.1'], function (Marionette) {
    'use strict';

    return Marionette.View.extend({
        template: function template(data) {
            return JIRA.Templates.Admin.Customfields.customfieldsEmptyPageContent(data);
        }
    });
});