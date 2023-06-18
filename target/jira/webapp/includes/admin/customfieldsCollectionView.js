/**
 * This view is a container (acts as <tbody>) for a customfieldRow views (<tr>) per custom field in collection
 * This must use old version of marionette in order to be compatible with the old paginator we are using,
 * otherwise the new childviews will be appended without cleaning up to existing ones.
 */
define('jira/customfields/customfieldCollectionView', ['marionette', 'jira/util/data/meta', 'jira/customfields/customfieldRowView', 'jira/util/formatter'], function (Marionette, meta, CustomfieldRowView, formatter) {
    'use strict';

    return Marionette.CollectionView.extend({
        tagName: 'tbody',
        itemView: CustomfieldRowView,
        itemViewOptions: function itemViewOptions() {
            return { isMultiLingual: meta.get('is-multilingual') };
        },

        emptyView: Marionette.ItemView.extend({
            tagName: 'tr',
            template: function template(data) {
                data.extraClasses = 'no-project-results';
                data.colspan = 5;
                data.name = formatter.I18n.getText('admin.issuefields.customfields.no.results.name');
                return JIRA.Templates.Common.emptySearchTableRow(data);
            }
        }),
        onRender: function onRender() {
            this.$('tr td:first-child strong').tipsy();
            this.$('tr td:first-child div.description').tipsy({ html: true });
        }
    });
});