define('jira/project/browse/filterview', ['marionette', 'underscore', 'jira/util/data/meta'], function (Marionette, _, meta) {
    'use strict';

    return Marionette.ItemView.extend({
        ui: {
            'contains': '.text'
        },
        events: {
            'change @ui.contains': 'inputContains',
            'keydown @ui.contains': 'inputContains',
            'submit form': 'formSubmit'
        },
        modelEvents: {
            'change:category': 'render',
            'change:projectType': 'render'
        },
        template: function template(data) {
            data.isAdminMode = meta.get('in-admin-mode');
            return JIRA.Templates.Project.Browse.filter(data);
        },

        inputContains: _.debounce(function inputsContains() {
            var filter = this.ui.contains.val();
            this.model.set('contains', filter);
        }, 300),
        formSubmit: function formSubmit(e) {
            e.preventDefault();
        }
    });
});