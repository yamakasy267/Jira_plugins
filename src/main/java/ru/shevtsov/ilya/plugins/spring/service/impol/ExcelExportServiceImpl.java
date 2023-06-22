package ru.shevtsov.ilya.plugins.spring.jira.webwork;

import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.issue.search.SearchResults;
import com.atlassian.jira.jql.builder.JqlClauseBuilder;
import com.atlassian.jira.jql.builder.JqlQueryBuilder;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.web.bean.PagerFilter;
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.query.Query;
import ru.shevtsov.ilya.plugins.spring.service.ExcelExpotrService;

import java.util.List;


@ExportAsService
public class ExcelExportServiceImpl implements ExcelExpotrService {
    ProjectManager projectManager;
    @ComponentImport
    final SearchService searchService;
    private static final long PROJECT_ID = 1L;

    public ExcelExportServiceImpl(ProjectManager projectManager,
                                  SearchService searchService) {
        this.projectManager = projectManager;
        this.searchService = searchService;
    }

    @Override
    public List<Issue> getRelevantIssue() {
        JqlClauseBuilder jqlClauseBuilder = JqlQueryBuilder.newClauseBuilder();
        Query query = jqlClauseBuilder.project("TUTORIAL").buildQuery();
        PagerFilter pagerFilter = PagerFilter.getUnlimitedFilter();


        ApplicationUser currentUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();

        SearchResults searchResults = null;
        try {
            searchResults = searchService.search(currentUser, query, pagerFilter);
            //e.printStackTrace();
        }
        catch (SearchException e) {
            throw new RuntimeException(e);
        }

        return searchResults != null ? searchResults.getIssues() : null;
    }


}
