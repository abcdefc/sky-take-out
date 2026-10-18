package com.sky.service;

import java.time.LocalDate;

import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
// 指定区间内的营业额
public interface ReportService {
    TurnoverReportVO getTurnoverStatistics(LocalDate begin,LocalDate end);


    UserReportVO getUserStatistics(LocalDate begin, LocalDate end);
}
