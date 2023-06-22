package ru.shevtsov.ilya.plugins.spring.jira.webwork;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.user.ApplicationUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.atlassian.jira.web.action.JiraWebActionSupport;

import java.util.List;


public class ExcelPluginCreateAction extends JiraWebActionSupport
{
    private static final Logger log = LoggerFactory.getLogger(ExcelPluginCreateAction.class);

    @Override
    public String execute() throws Exception {

        //String jqlQuery = "project = AB"; // insert your JQL query here
        //ApplicationUser currentUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();

        List<Issue> issues = Export.getIssues();

        System.out.println("\n\n\n\nwegwg" + issues + "\n\n\n");
        return super.execute(); //returns SUCCESS
    }
}
