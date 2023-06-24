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
import com.atlassian.plugin.spring.scanner.annotation.imports.JiraImport;
import com.atlassian.query.Query;
import com.atlassian.jira.issue.context.AbstractJiraContext;
import com.atlassian.jira.issue.context.ProjectContext;

import ru.shevtsov.ilya.plugins.spring.service.ExcelExportService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;


@Named
public class ExcelExportServiceImpl implements ExcelExportService {
    ProjectManager projectManager;
    SearchService searchService;
    private static final long PROJECT_ID = 1L; //заменить на трушное имя проекта/ID
    @Inject
    public ExcelExportServiceImpl(
            @JiraImport ProjectManager projectManager,
            @JiraImport SearchService searchService) {
        System.out.println("\n\n\n\n\n\n 4 \n\n\n\n\n");
        this.projectManager = projectManager;
        this.searchService = searchService;
        System.out.println("\n\n\n\n\n\n 5 \n\n\n\n\n");
    }



    @Override
    public List<Issue> getRelevantIssue() {

        System.out.println("\n\n\n\n\n\n 6 \n\n\n\n\n");

        //def baseurl = com.atlassian.jira.component.ComponentAccessor.getApplicationProperties().getString("jira.baseurl")
        //ComponentAccessor.getApplicationProperties().

        //
        String ApplicationProperties = ComponentAccessor.getApplicationProperties().getString("jira.baseurl");
        System.out.println("\n\n\n\n\n\n" + ApplicationProperties + "\n\n\n\n\n");
        //

        //project = projectName AND status = Open AND type = Тема //итоговое

        String projectName = "RJ";
        JqlClauseBuilder jqlClauseBuilder = JqlQueryBuilder.newClauseBuilder();
        Query query = jqlClauseBuilder.project(projectName).and().status("In Progress").and().issueType("Задача").buildQuery();

        PagerFilter pagerFilter = PagerFilter.getUnlimitedFilter();

        ApplicationUser currentUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();

        SearchResults searchResults = null;
        try {
            searchResults = searchService.search(currentUser, query, pagerFilter);
            //e.printStackTrace();
        }
        catch (Exception e) {
            System.out.println("\n\n\n\n\n\n бегите " + e + "\n\n\n\n\n" );
        }

        System.out.println("\n\n\n\n\n\n 9 \n\n\n\n\n");

        return searchResults != null ? searchResults.getIssues() : null;
    }
}