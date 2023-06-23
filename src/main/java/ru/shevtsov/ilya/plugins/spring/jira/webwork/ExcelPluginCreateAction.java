package ru.shevtsov.ilya.plugins.spring.jira.webwork;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shevtsov.ilya.plugins.spring.service.ExcelExportService;

import javax.inject.Inject;
import java.util.List;


public class ExcelPluginCreateAction extends JiraWebActionSupport {
//    private static Logger log = LoggerFactory.getLogger(ExcelPluginCreateAction.class);

    ExcelExportService excelExportService;
    List<Issue> relIssues;

    @Inject
    public ExcelPluginCreateAction(ExcelExportService excelExportService) {
        this.excelExportService = excelExportService;
    }

//    @Override
//    public String doDefault() throws Exception {
//        return execute();
//    }

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

        return SUCCESS; //returns SUCCESS
    }

    public Byte[] getExcelFile() {
        // relIssues.forEach(issue -> );
        return null;
    }
}