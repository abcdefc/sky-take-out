package com.sky.service;

import java.time.LocalDate;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import jakarta.servlet.http.HttpServletResponse;
// 指定区间内的营业额
public interface ReportService {
    TurnoverReportVO getTurnoverStatistics(LocalDate begin,LocalDate end);


    UserReportVO getUserStatistics(LocalDate begin, LocalDate end);


    OrderReportVO getOrdersStatistics(LocalDate begin, LocalDate end);


    SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end);


    void exportBusinessDate(HttpServletResponse res);
}
