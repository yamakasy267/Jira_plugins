package ru.shevtsov.ilya.plugins.spring.jira.webwork;
import ru.shevtsov.ilya.plugins.spring.service.*;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.web.action.JiraWebActionSupport;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class ExcelPluginCreateAction extends JiraWebActionSupport {
    ExcelExportService excelExportService;
    IWriteExcel iWriteExcel;
    List<Issue> relIssues;
    String epic;
    private String projectKey;

    @Inject
    public ExcelPluginCreateAction(ExcelExportService excelExportService, IWriteExcel iWriteExcel) {
        this.excelExportService = excelExportService;
        this.iWriteExcel = iWriteExcel;
    }
    public String getProjectKey() {
        return projectKey;
    }
    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    @Override
    public String doDefault() throws Exception {
        try {
            relIssues = excelExportService.getRelevantIssue(projectKey);
        } catch (Exception error) {
            System.out.println("\n\n" + error + "\n\n");
        }
        epic = ComponentAccessor.getProjectManager().getProjectByCurrentKey(projectKey).getName();

        Map<Integer, Object[]> issueMap = new LinkedHashMap<Integer, Object[]>();

        for (Issue issue : relIssues)
        {
            int elemNumInGroup = issue.getSubTaskObjects().size();
            String date = new SimpleDateFormat("dd.MM.yyyy").format(issue.getDueDate());
            issueMap.put(issue.getId().intValue(), new Object[] {
                    issue.getKey(),
                    issue.getIssueType().getName(),
                    issue.getSummary(),
                    date,
                    issue.getAssignee().getName(),
                    issue.getStatus().getName(),
                    elemNumInGroup
            });

            for (Issue subTask : issue.getSubTaskObjects())
            {
                date = new SimpleDateFormat("dd.MM.yyyy").format(subTask.getDueDate());
                issueMap.put(subTask.getId().intValue(), new Object[] {
                        subTask.getKey(),
                        subTask.getIssueType().getName(),
                        subTask.getSummary(),
                        date,
                        issue.getAssignee().getName(),
                        subTask.getStatus().getName(),
                        elemNumInGroup
                });
            }
        }

        try {
            iWriteExcel.WriteExcel(issueMap, epic);
        }
        catch (Exception error)
        {
            System.out.println("\n\n" + error + "\n\n");
        }

        return SUCCESS;
    }
}