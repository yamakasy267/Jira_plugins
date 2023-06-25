package ru.shevtsov.ilya.plugins.spring.jira.webwork;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import ru.shevtsov.ilya.plugins.spring.service.ExcelExportService;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class ExcelPluginCreateAction extends JiraWebActionSupport {
    ExcelExportService excelExportService;
    List<Issue> relIssues;
    String epic = "BPM. Развитие и оптимизация 3PL 2023";

    @Inject
    public ExcelPluginCreateAction(ExcelExportService excelExportService) {
        this.excelExportService = excelExportService;
    }

    @Override
    public String execute() throws Exception {
        try {
            System.out.println(excelExportService);
            relIssues = excelExportService.getRelevantIssue();
        } catch (Exception e) {
            System.out.println("EXCEPTIONS!!!!!!!!!!>>>>>>>>>>" + e);
        }

        Map<Integer, Object[]> issueMap = new TreeMap<Integer, Object[]>();

        for (Issue issue : relIssues)
        {
            //data.put(0, new Object[] {"BPM3PL3-1", "Тема", "Продукт/инструмент для работы с большими/сложными клиентами", "", "", "Открыто"});

            issueMap.put(issue.getId().intValue(), new Object[] { issue.getKey(), issue.getIssueType().getName(),
                    issue.getSummary(), issue.getReporter(), issue.getDueDate(), issue.getAssignee(),
                    issue.getStatus().getName() });

            for (Issue subTask : issue.getSubTaskObjects())
            {
                issueMap.put(subTask.getId().intValue(), new Object[] { subTask.getKey(), subTask.getIssueType().getName(),
                        subTask.getSummary(), subTask.getReporter(), subTask.getDueDate(), subTask.getAssignee(),
                        subTask.getStatus().getName() });
            }
        }

//        IWriteExcel iWriteExcel = null;
//        iWriteExcel.WriteExcel(issueMap, epic);

        System.out.println("\n\n" + issueMap + "\n\n");
        System.out.println("\n\n" + issueMap.get(10005)[0] + "\n\n");

        return SUCCESS;
    }

    public Byte[] getExcelFile() {
        //relIssues.forEach(issue -> );

        return null;
    }
}