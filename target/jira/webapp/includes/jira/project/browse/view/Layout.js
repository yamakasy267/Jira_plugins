define('jira/project/browse/layout', ['marionette'], function (Marionette) {
    'use strict';

    return Marionette.Layout.extend({
        regions: {
            sidebar: '#browse-projects-sidebar',
            categoryNav: '.category-nav',
            projectTypeNav: '.project-type-nav',
            filter: '#filter-projects',
            content: '#projects',
            pagination: '#pagination'
        }
    });
});