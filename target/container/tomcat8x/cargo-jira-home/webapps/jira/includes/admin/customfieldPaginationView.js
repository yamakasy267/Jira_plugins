define('jira/customfields/customfieldsPaginationView', ['jquery', 'underscore', 'jira/project/browse/paginationview'], function ($, _, PaginationView) {
    'use strict';

    return PaginationView.extend({
        // overriding since I don't want to unwrap
        onRender: function onRender() {
            //this.unwrapTemplate();
        },
        serializeData: function serializeData() {
            //var url = Navigation.getRoot() + this.model.getQueryParams();
            var data = _.extend({
                url: ''
            }, this.collection.state);
            data.firstPage = Math.max(data.currentPage - 5, data.firstPage);
            data.totalPages = data.lastPage;
            data.lastPage = Math.min(data.currentPage + 5, data.lastPage);
            return data;
        },
        clickPage: function clickPage(e) {
            var _this = this;

            e.preventDefault();
            // this must be integer value
            var pageNumber = +$(e.target).attr('data-page');
            if (pageNumber) {
                this.triggerMethod('navigate:start');
                this.collection.getPage(pageNumber).done(function () {
                    _this.render();
                    _this.triggerMethod('navigate:end');
                }).fail(function (error) {
                    return _this.triggerMethod('navigate:error', error);
                });
            }
        }
    });
});