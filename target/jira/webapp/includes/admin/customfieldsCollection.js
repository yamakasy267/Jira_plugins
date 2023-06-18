/**
 * A pageableCollection for fetching paginated customfields
 */
define('jira/customfields/customfieldsCollection', ['jira/backbone/backbone-paginator', 'wrm/context-path'], function (PageableCollection, contextPath) {
    'use strict';

    return PageableCollection.extend({
        url: contextPath() + '/rest/api/2/customFields',
        state: {
            pageSize: 25
        },
        queryParams: {
            currentPage: "startAt",
            pageSize: "maxResults",
            search: function search() {
                return this.searchTerm;
            }
        },
        parseState: function parseState(resp) {
            return { totalRecords: resp.total };
        },
        parseRecords: function parseRecords(resp) {
            return resp.values;
        }
    });
});