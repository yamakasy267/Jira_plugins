/**
 * This view renders the custom fields table and pagination
 */
define('jira/customfields/customfieldsTableView', ['atlassian/libs/underscore-1.8.3', 'jira/marionette-3.1', 'jira/customfields/customfieldCollectionView', 'jira/customfields/customfieldsPaginationView'], function (_, Marionette, CustomfieldsCollectionView, PaginationView) {
    'use strict';

    return Marionette.View.extend({
        template: function template(data) {
            return JIRA.Templates.Admin.Customfields.customfieldsPageContent(data);
        },
        ui: {
            searchInput: '#custom-fields-filter-text'
        },
        events: {
            'input @ui.searchInput': 'performSearch'
        },
        regions: {
            customfields: {
                el: 'tbody',
                replaceElement: true
            },
            pagination: '#pagination-container'
        },
        onRender: function onRender() {
            this.showChildView('customfields', new CustomfieldsCollectionView({
                collection: this.collection
            }));

            var paginationView = new PaginationView({
                collection: this.collection
            });
            this.showChildView('pagination', paginationView);
            /* Since pagination view is using ancient versions of Marionette,
               it doesn't automatically trigger methods with child prefix here or bubble to parent,
               so we manually do this :(
             */
            this.listenTo(paginationView, 'navigate:start', this.onChildviewNavigateStart);
            this.listenTo(paginationView, 'navigate:end', this.onChildviewNavigateEnd);
            this.listenTo(paginationView, 'navigate:error', this.onChildViewNavigateError);
        },
        onChildviewNavigateStart: function onChildviewNavigateStart() {
            this.triggerMethod('navigate:start');
        },
        onChildviewNavigateEnd: function onChildviewNavigateEnd() {
            this.triggerMethod('navigate:end');
        },
        onChildViewNavigateError: function onChildViewNavigateError(error) {
            this.triggerMethod('navigate:error', error);
        },

        performSearch: _.debounce(function () {
            var _this = this;

            var input = this.getUI('searchInput');
            var searchTerm = input.val();

            // Because of IE11 triggers 'input' event on focus if input field has a placeholder,
            // let it be an additional check to prevent search loop
            if (searchTerm === this.currentInputValue) {
                return;
            }
            // show loading indicator //
            this.triggerMethod('search:start');
            // prevent user from input while loading
            input.blur();
            this.currentInputValue = searchTerm;
            this.collection.searchTerm = searchTerm;
            this.collection.getFirstPage({ reset: true }).always(function () {
                input.focus();
                // hide loading indicator
                _this.triggerMethod('search:end');
            }).fail(function (error) {
                return _this.triggerMethod('navigate:error', error);
            });
        }, 500),
        // for capability with IE11
        currentInputValue: ''
    });
});