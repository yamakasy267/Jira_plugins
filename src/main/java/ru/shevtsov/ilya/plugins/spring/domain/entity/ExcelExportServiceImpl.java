package ru.shevtsov.ilya.plugins.spring.domain.entity;

import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.search.SearchResults;
import com.atlassian.jira.jql.builder.JqlClauseBuilder;
import com.atlassian.jira.jql.builder.JqlQueryBuilder;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.web.bean.PagerFilter;
import com.atlassian.plugin.spring.scanner.annotation.imports.JiraImport;
import com.atlassian.query.Query;

import ru.shevtsov.ilya.plugins.spring.domain.IExcelExportService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;


@Named
public class ExcelExportServiceImpl implements IExcelExportService {
    ProjectManager projectManager;
    SearchService searchService;
    private static final long PROJECT_ID = 1L; //заменить на трушное имя проекта/ID
    @Inject
    public ExcelExportServiceImpl(
            @JiraImport ProjectManager projectManager,
            @JiraImport SearchService searchService) {
        this.projectManager = projectManager;
        this.searchService = searchService;
    }

    @Override
    public List<Issue> getRelevantIssue(String projectKey) {
        String projectName = projectKey;
        JqlClauseBuilder jqlClauseBuilder = JqlQueryBuilder.newClauseBuilder();
        Query query = jqlClauseBuilder.project(projectName).and().status("In Progress").and().issueType("Задача").buildQuery();
        PagerFilter pagerFilter = PagerFilter.getUnlimitedFilter();
        ApplicationUser currentUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();

        SearchResults searchResults = null;
        try {
            searchResults = searchService.search(currentUser, query, pagerFilter);
        }
        catch (Exception error) {
            System.out.println("\n\n" + error + "\n\n");
        }

        return searchResults != null ? searchResults.getIssues() : null;
    }
}