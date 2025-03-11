package org.swissre.report.hierarchy;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class OrganizationReport {
	public static void main(String args[]) {
		Scanner reader = new Scanner(System.in);
		System.out.println("Please Enter the absolute path for the CSV input file");
		String filePath = reader.next();
		reader.close();
		ReportService reportService = new ReportService();
		HashMap<Integer, List<Employee>> orgHierarchy = reportService.createHierarchy(filePath);
		reportService.managerSalaryEvaluation(orgHierarchy);
		reportService.reportingLineLength(orgHierarchy);
	}
}
