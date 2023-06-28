package ru.shevtsov.ilya.plugins.spring.jira.webwork;
import ru.shevtsov.ilya.plugins.spring.domain.*;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.web.action.JiraWebActionSupport;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class ExcelPluginCreateAction extends JiraWebActionSupport {
    IExcelExportService IExcelExportService;
    IWriteExcel iWriteExcel;
    List<Issue> relIssues;
    String epic;
    private String projectKey;

    @Inject
    public ExcelPluginCreateAction(IExcelExportService IExcelExportService, IWriteExcel iWriteExcel) {
        this.IExcelExportService = IExcelExportService;
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
            relIssues = IExcelExportService.getRelevantIssue(projectKey);
        } catch (Exception error) {
            System.out.println("\n\n" + error + "\n\n");
        }
        epic = ComponentAccessor.getProjectManager().getProjectByCurrentKey(projectKey).getName();

        Map<Integer, Object[]> issueMap = new LinkedHashMap<Integer, Object[]>();
        String date;
        String assigneeName;

        for (Issue issue : relIssues)
        {
            int elemNumInGroup = issue.getSubTaskObjects().size();

            try
            {
                if(issue.getDueDate() == null) {
                    date = " ";
                } else {
                    date = new SimpleDateFormat("dd.MM.yyyy").format(issue.getDueDate());
                }

                assigneeName = issue.getAssignee().getName();

                if(date == null) { date = " "; }
                if(assigneeName == null) { assigneeName = " "; }

                issueMap.put(issue.getId().intValue(), new Object[] {
                        issue.getKey(),
                        issue.getIssueType().getName(),
                        issue.getSummary(),
                        date,
                        assigneeName,
                        issue.getStatus().getName(),
                        elemNumInGroup
                });
            }
            catch (Exception error)
            {
                System.out.println("\n\n" + error + "\n\n");
            }

            for (Issue subTask : issue.getSubTaskObjects())
            {
                try
                {
                    if(subTask.getDueDate() == null) {
                        date = " ";
                    } else {
                        date = new SimpleDateFormat("dd.MM.yyyy").format(subTask.getDueDate());
                    }

                    assigneeName = issue.getAssignee().getName();

                    if(date == null) { date = " "; }
                    if(assigneeName == null) { assigneeName = " "; }

                    issueMap.put(subTask.getId().intValue(), new Object[] {
                            subTask.getKey(),
                            subTask.getIssueType().getName(),
                            subTask.getSummary(),
                            date,
                            assigneeName,
                            subTask.getStatus().getName(),
                            elemNumInGroup
                    });
                }
                catch (Exception error)
                {
                    System.out.println("\n\n" + error + "\n\n");
                }
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