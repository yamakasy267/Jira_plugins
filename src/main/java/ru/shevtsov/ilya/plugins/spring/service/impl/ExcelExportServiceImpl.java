package ru.shevtsov.ilya.plugins.spring.service.impl;

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
import com.atlassian.query.Query;
import ru.shevtsov.ilya.plugins.spring.service.ExcelExportService;

import java.util.List;


@ExportAsService
public class ExcelExportServiceImpl implements ExcelExportService {
    ProjectManager projectManager;
    SearchService searchService;
    private static final long PROJECT_ID = 1L; //заменить на трушное имя проекта/ID

    public ExcelExportServiceImpl(ProjectManager projectManager, SearchService searchService) {
        System.out.println("\n\n\n\n\n\n 4 \n\n\n\n\n");
        this.projectManager = projectManager;
        this.searchService = searchService;
        System.out.println("\n\n\n\n\n\n 5 \n\n\n\n\n");
    }

    @Override
    public List<Issue> getRelevantIssue() {

        System.out.println("\n\n\n\n\n\n 6 \n\n\n\n\n");

        JqlClauseBuilder jqlClauseBuilder = JqlQueryBuilder.newClauseBuilder();
        Query query = jqlClauseBuilder.project("TUTORIAL").buildQuery();
        PagerFilter pagerFilter = PagerFilter.getUnlimitedFilter();

        System.out.println("\n\n\n\n\n\n 7 \n\n\n\n\n");

        ApplicationUser currentUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();

        System.out.println("\n\n\n\n\n\n 8 \n\n\n\n\n");

        SearchResults searchResults = null;
        try {
            searchResults = searchService.search(currentUser, query, pagerFilter);
            //e.printStackTrace();
        }
        catch (SearchException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n\n\n\n\n\n 9 \n\n\n\n\n");

        return searchResults != null ? searchResults.getIssues() : null;
    }
}