package ru.shevtsov.ilya.plugins.spring.jira.webwork;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import ru.shevtsov.ilya.plugins.spring.service.ExcelExpotrService;

import java.util.List;


public class ExcelPluginCreateAction extends JiraWebActionSupport
{
    private static final Logger log = LoggerFactory.getLogger(ExcelPluginCreateAction.class);
    @ComponentImport
    ExcelExpotrService excelExportService;
    List<Issue> relIssues;
    public ExcelPluginCreateAction(ExcelExpotrService excelExportService) {
        this.excelExportService = excelExportService;
    }

    @Override
    public String doDefault() throws Exception {
        return execute();
    }

    @Override
    public String execute() throws Exception {

        //String jqlQuery = "project = AB"; // insert your JQL query here
        //ApplicationUser currentUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
        relIssues = excelExportService.getRelevantIssue();


        return SUCCESS; //returns SUCCESS
    }

    public Byte[] getExcekFile() {
        // relIssues.forEach(issue -> );
        return null;
    }
}
