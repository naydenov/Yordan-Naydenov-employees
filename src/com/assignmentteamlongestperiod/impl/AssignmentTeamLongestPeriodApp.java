package com.assignmentteamlongestperiod.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;

public class AssignmentTeamLongestPeriodApp {

	public static void main(String[] args) {
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Please choose a file with the data");
	    int a = fileChooser.showOpenDialog(null);

	    if (a == JFileChooser.APPROVE_OPTION) {
	      File fileToOpen = fileChooser.getSelectedFile();
	      Map<Integer, List> data = null;
	      try {
				data = readFile(fileToOpen);
	      } catch (IOException e) {
				e.printStackTrace();
	      }
	      
	      data = filterResults(data);
	      
	      new ResultsView(data);
	    }		
	}

	private static Map<Integer, List> filterResults(Map<Integer, List> data) {
		
		Map<Integer, List> filteredData = new HashMap<Integer, List>();
		
		//1. remove all employees with periods on project which doesn't overlap
		for (Map.Entry<Integer, List> entry : data.entrySet()) {
			List<Employee> employees = entry.getValue(); 
			List<Employee> result = new ArrayList<>();

			    for(Employee current : employees) {
			        for(Employee other : employees) {
			            if((current != other) && (current.getDateFrom().isBefore(other.getDateTo()) 
			            						&& current.getDateTo().isAfter(other.getDateFrom()))) {
			                result.add(current);
			                break;
			            }
			        }
			    }
			    
			if (!result.isEmpty())
				filteredData.put(entry.getKey(), result);
	    }
		
		//2. remove all lists with less than two employees
		filteredData.values().removeIf(v -> (v.size()< 2));
		
		//3. sort the employees in the lists
	    filteredData.values().forEach(l -> Collections.sort(l));
	    
	    return filteredData;
	}

	private static Map<Integer, List> readFile(File source) throws IOException {
		Map<Integer, List> projects = new HashMap<Integer,List>();
		try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
			String s;
			while ((s = reader.readLine()) != null) {
				Employee tempEmpl = parssLine(s);
				int projectID = tempEmpl.getProjectID();
				if(projects.keySet().contains(projectID)) {
					projects.get(projectID).add(tempEmpl);
				} else {
					projects.put(projectID, new ArrayList<Employee>(Arrays.asList(tempEmpl)));
				}
			}
		}
		return projects;
	}
	
	private static Employee parssLine(String s) throws IOException {
		String[] splitted = s.trim().split("\\s*,\\s*");
		
		int empID = Integer.parseInt(splitted[0]);
		int projectID = Integer.parseInt(splitted[1]);
		LocalDate dateFrom = LocalDate.parse(splitted[2]);
		
		LocalDate dateTo = null;
		if(splitted[3].toUpperCase().equals("NULL"))
			dateTo = LocalDate.now();
		else
			dateTo = LocalDate.parse(splitted[3]);
		
		return new Employee(empID, projectID, dateFrom, dateTo);
	}
}
