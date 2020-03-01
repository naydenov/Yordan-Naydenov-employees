package com.assignmentteamlongestperiod.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Employee implements Comparable<Employee> {
	
	private int empID;
	private int projectID;
	private LocalDate dateFrom;
	private LocalDate dateTo;
	private long periodOnProject;
	
	public Employee(int empID, int projectID, LocalDate dateFrom, LocalDate dateTo) {
		super();
		this.empID = empID;
		this.projectID = projectID;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.periodOnProject = ChronoUnit.DAYS.between(dateFrom, dateTo);
	}

	public int getEmpID() {
		return empID;
	}

	public int getProjectID() {
		return projectID;
	}

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}
	
	public long getPeriodOnProject() {
		return periodOnProject;
	}
	
	@Override
	public int compareTo(Employee e) {
		return Long.compare(this.getPeriodOnProject(), e.getPeriodOnProject());
	}	
}
