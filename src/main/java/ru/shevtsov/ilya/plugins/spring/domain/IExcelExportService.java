package ru.shevtsov.ilya.plugins.spring.domain;

import com.atlassian.jira.issue.Issue;

import java.util.List;

public interface IExcelExportService {
    List<Issue> getRelevantIssue(String projectKey);
}