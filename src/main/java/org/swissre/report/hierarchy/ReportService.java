package org.swissre.report.hierarchy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService {
	// This map hold employeeId as key and corresponding employee object as value
	private  HashMap<Integer, Employee> employeeMap = new HashMap<>();

	/**
	 * @param fileLocation the location of the input C file.
	 * @return a map containing managerId and all its reportees
	 */
	public HashMap<Integer, List<Employee>> createHierarchy(String fileLocation) {
		BufferedReader reader;
		// This  contains MangerId as key and all its reportees as value (a list of employee)
		HashMap<Integer, List<Employee>> OrgHierarchy = new HashMap<Integer, List<Employee>>();
		try {
			reader = new BufferedReader(new FileReader(fileLocation));
			String line = reader.readLine();
			line = reader.readLine();

			while (line != null) {
				String[] empDetails = line.split(",");
				Employee employee = new Employee(Integer.parseInt(empDetails[0]), empDetails[1], empDetails[2],
						Float.parseFloat(empDetails[3]));
				// if an employee has manager there will be 5 entries in a row.
				if (empDetails.length > 4) {
					int managerId = Integer.parseInt(empDetails[4]);
					if (OrgHierarchy.containsKey(managerId)) {
						employee.setManagerId(managerId);
						// add reportee details to existing list of reportees
						OrgHierarchy.get(managerId).add(employee);
					} else {
						List<Employee> empList = new ArrayList<>();
						empList.add(employee);
						employee.setManagerId(managerId);
						// add reportee details for the manager
						OrgHierarchy.put(managerId, empList);
					}	
				} else {
					List<Employee> empList = new ArrayList<>();
					empList.add(employee);
					// for the CEO managerId will be null
					OrgHierarchy.put(null, empList);
				}
				employeeMap.put(Integer.parseInt(empDetails[0]), employee);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return OrgHierarchy;
	}

	/**
	 * @param OrgHierachy a map with MangerId as key and all its reportees as value (a list of employee)
	 * This method evaluates the salary for each manager as per the criterion and prints on console if a 
	 * manger earns more/less than what he/she should earn and by how much
	 */
	public void managerSalaryEvaluation(HashMap<Integer, List<Employee>> OrgHierachy) {
		OrgHierachy.forEach((empId, reporteeList) -> {
			if (empId != null) {
				float averageReporteeSalary = getAverageReporteeSalary(reporteeList);
				float minTargetSalary = averageReporteeSalary + averageReporteeSalary / 5;
				float maxTrargetSalary = averageReporteeSalary + averageReporteeSalary / 2;
				if (employeeMap.get(empId).getSalary() < minTargetSalary) {
					System.out.println("Employee " + employeeMap.get(empId).getFirstName()
							+ " earns less than target by " + (minTargetSalary - (employeeMap.get(empId).getSalary())));
				} else if (employeeMap.get(empId).getSalary() > maxTrargetSalary) {
					System.out
							.println("Employee " + employeeMap.get(empId).getFirstName() + " earns More than target by "
									+ ((employeeMap.get(empId).getSalary()) - maxTrargetSalary));
				}
			}

		});
	}

	/**
	 * @param OrgHierachy a map with MangerId as key and all its reportees as value (a list of employee)
	 * This method calculates the Number for managers between employee and the CEO for each employee and
	 *  prints if there are more than 4 managers in between
	 */
	public void reportingLineLength(HashMap<Integer, List<Employee>> OrgHierachy) {
		for (Map.Entry<Integer, Employee> employee : employeeMap.entrySet()) {
			int level = getReportingLevel(employee.getValue(), 0);
			if (level > 4) {
				System.out.println("Employee " + employee.getValue().getFirstName() + " has " + (level - 4)
						+ " Additional manager in between");
			}
		}
	}

	private int getReportingLevel(Employee employee, int level) {
		if (employee.getManagerId() != 0) {
			return getReportingLevel(employeeMap.get(employee.getManagerId()), level + 1);
		}
		return level;
	}

	private float getAverageReporteeSalary(List<Employee> reportees) {
		float totalReporteeSalary = 0;
		for (Employee emp : reportees) {
			totalReporteeSalary = totalReporteeSalary + emp.getSalary();
		}
		return totalReporteeSalary / reportees.size();

	}

}
