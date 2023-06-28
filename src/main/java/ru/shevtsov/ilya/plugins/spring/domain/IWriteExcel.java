package ru.shevtsov.ilya.plugins.spring.domain;

import java.util.Map;

public interface IWriteExcel {
    void WriteExcel(Map<Integer, Object[]> issueMap, String epic);
}
