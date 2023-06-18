package ru.shevtsov.ilya.plugins.spring.jira.webwork;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.atlassian.jira.web.action.JiraWebActionSupport;



public class ExelPluginCreateAction extends JiraWebActionSupport
{
    private static final Logger log = LoggerFactory.getLogger(ExelPluginCreateAction.class);

    @Override
    public String execute() throws Exception {

        return super.execute(); //returns SUCCESS
    }
}
