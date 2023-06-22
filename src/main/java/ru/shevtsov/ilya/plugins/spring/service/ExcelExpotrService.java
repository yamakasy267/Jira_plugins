package ru.shevtsov.ilya.plugins.spring.service;

import com.atlassian.jira.issue.Issue;

import java.util.List;

public interface ExcelExpotrService {
    List<Issue> getRelevantIssue();

}
