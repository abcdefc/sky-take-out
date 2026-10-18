package com.sky.service;

import java.time.LocalDate;

import com.sky.vo.TurnoverReportVO;
// 指定区间内的营业额
public interface ReportService {
    TurnoverReportVO getTurnoverStatistics(LocalDate begin,LocalDate end);
}
