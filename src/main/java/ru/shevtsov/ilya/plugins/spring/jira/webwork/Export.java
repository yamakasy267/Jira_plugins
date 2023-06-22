package ru.shevtsov.ilya.plugins.spring.jira.webwork;

import com.atlassian.jira.bc.JiraServiceContextImpl;
import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.jql.builder.JqlClauseBuilder;
import com.atlassian.jira.jql.builder.JqlQueryBuilder;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.web.bean.PagerFilter;
import com.atlassian.jira.issue.search.SearchResults;
import com.atlassian.plugin.spring.scanner.annotation.imports.JiraImport;
import com.atlassian.query.Query;

import java.util.List;

public class Export {
    @JiraImport
    private static SearchService searchService;
    @JiraImport
    private static ApplicationUser searchUser;
    @JiraImport
    private static JiraAuthenticationContext authenticationContext;

    public static List<Issue> getIssues() {
        ApplicationUser user = authenticationContext.getLoggedInUser();
        JqlClauseBuilder jqlClauseBuilder = JqlQueryBuilder.newClauseBuilder();
        Query query = jqlClauseBuilder.project("TUTORIAL").buildQuery();
        PagerFilter pagerFilter = PagerFilter.getUnlimitedFilter();

        SearchResults searchResults = null;
        try {
            searchResults = searchService.search(user, query, pagerFilter);
            //e.printStackTrace();
        }
        catch (SearchException e) {
            throw new RuntimeException(e);
        }

        return searchResults != null ? searchResults.getIssues() : null;
    }
}
