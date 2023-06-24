package ru.shevtsov.ilya.plugins.spring.jira.webwork;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import ru.shevtsov.ilya.plugins.spring.service.ExcelExportService;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;


public class ExcelPluginCreateAction extends JiraWebActionSupport {
    ExcelExportService excelExportService;
    List<Issue> relIssues;

    @Inject
    public ExcelPluginCreateAction(ExcelExportService excelExportService) {
        this.excelExportService = excelExportService;
    }

    @Override
    public String execute() throws Exception {
        System.out.println("\n\n\n\n\n\n 2 \n\n\n\n\n");
        try {
            System.out.println(excelExportService);
            relIssues = excelExportService.getRelevantIssue();
        } catch (Exception e) {
            System.out.println("EXCEPTIONS!!!!!!!!!!>>>>>>>>>>" + e);
        }
        System.out.println("\n\n\n\n\n\n 3 \n\n\n\n\n");

        try{
            System.out.println("\n\n\n\n\n\n" + relIssues + "\n\n\n\n\n");
            System.out.println("getId: " + relIssues.get(0).getId() +
                    "\ngetKey: " + relIssues.get(0).getKey() +
                    "\ngetIssueType: " + relIssues.get(0).getIssueType().getName() +
                    "\ngetSummary: " + relIssues.get(0).getSummary() +
                    "\ngetReporter: " + relIssues.get(0).getReporter() +
                    "\ngetDueDate: " + relIssues.get(0).getDueDate() +
                    "\ngetAssignee: " + relIssues.get(0).getAssignee() +
                    "\ngetStatus: " + relIssues.get(0).getStatus().getName() +
                    //"\ngetParentObject: " + relIssues.get(0).getParentObject() +
                    "\ngetSubTaskObjects: " + relIssues.get(0).getSubTaskObjects()
            );

            Collection<Issue> key = relIssues.get(0).getSubTaskObjects();
            relIssues.add(ComponentAccessor.getIssueManager().getIssueByCurrentKey(key.toArray()[0].toString()));
            System.out.println("\n\n\n\n\n\n" + relIssues.get(1).getSummary() + "\n\n\n\n\n");

        } catch (Exception e) {
            System.out.println("EXCEPTIONS!!!!!!!!!!>>>>>>>>>>" + e);
        }

        return SUCCESS; //returns SUCCESS
    }

    public Byte[] getExcelFile() {
        //relIssues.forEach(issue -> );

        return null;
    }
}