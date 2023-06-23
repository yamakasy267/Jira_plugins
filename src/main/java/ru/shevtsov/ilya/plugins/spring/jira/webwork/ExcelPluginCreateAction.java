package ru.shevtsov.ilya.plugins.spring.jira.webwork;

import com.atlassian.jira.issue.Issue;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import ru.shevtsov.ilya.plugins.spring.service.ExcelExportService;

import java.util.List;


public class ExcelPluginCreateAction extends JiraWebActionSupport
{
    private static Logger log = LoggerFactory.getLogger(ExcelPluginCreateAction.class);

    //@ComponentImport
    ExcelExportService excelExportService;
    List<Issue> relIssues;

//    public ExcelPluginCreateAction(ExcelExportService excelExportService) {
//        this.excelExportService = excelExportService;
//    }

//    @Override
//    public String doDefault() throws Exception {
//        return execute();
//    }

    @Override
    public String execute() throws Exception {
        System.out.println("\n\n\n\n\n\n 2 \n\n\n\n\n");
        relIssues = excelExportService.getRelevantIssue();
        System.out.println("\n\n\n\n\n\n 3 \n\n\n\n\n");
        return SUCCESS; //returns SUCCESS
    }

    public Byte[] getExcelFile() {
        // relIssues.forEach(issue -> );
        return null;
    }
}