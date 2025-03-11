package org.swissre.report.hierarchy;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportServiceTest {
	ReportService reportService = new ReportService();
	
	 private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    private final PrintStream originalOut = System.out;

	    @BeforeEach
	    public void setUp() {
	        // Redirect System.out to a ByteArrayOutputStream
	        System.setOut(new PrintStream(outputStream));
	    }

	    @AfterEach
	    public void restoreSystemOut() {
	        // Restore the original System.out after each test
	        System.setOut(originalOut);
	    }
	@Test
	public void testCreateHierarchy() {
		Map<Integer,List<Employee>> hierarchy =  reportService.createHierarchy("src/test/resources/input.csv");
		assertEquals(2, (hierarchy.get(123)).size()); // assert that  2 employees reports to the CEO
		assertEquals(1, (hierarchy.get(124)).size()); // assert that  1 employees reports to the manager with employeeId 124
		assertEquals(1, (hierarchy.get(300)).size()); // assert that  1 employees reports to the manager with employeeId 300
		assertEquals("Joe", (hierarchy.get(null)).get(0).getFirstName()); // assert the first name of the CEO
	}

	
	@Test
	public void testmanagerSalaryEvaluation() {
		HashMap<Integer,List<Employee>> hierarchy =  reportService.createHierarchy("src/test/resources/input.csv");
		reportService.managerSalaryEvaluation(hierarchy);
		assertEquals("Employee Martin earns less than target by 15000.0" + System.getProperty("line.separator"), outputStream.toString());
	}

	@Test
	public void testreportingLineLengthn() {
		HashMap<Integer,List<Employee>> hierarchy =  reportService.createHierarchy("src/test/resources/ReportingLine.csv");
		reportService.reportingLineLength(hierarchy);
		assertEquals("Employee e has 1 Additional manager in between" + System.getProperty("line.separator"), outputStream.toString());
	}
}
