package com.assignmentteamlongestperiod.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable; 

public class ResultsView extends JFrame {
	
	 //Headers of the teable
	private static final String[] COLIMNS = new String[] { "Employee ID #1", "Employee ID #2", "Project ID", "Days worked" };
	private static final int NUMBERS_OF_COLUMNS = 4;
  
    ResultsView (Map<Integer, List> results) { 
    	
        //results 
    	Object[][] data = proccessData(results);
        
        JTable table = new JTable(data, COLIMNS);
        this.add(new JScrollPane(table));
        this.setTitle("Table with the results");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        this.pack();
        this.setLocationRelativeTo(null);
        
        this.setVisible(true);
    }

	private Object[][] proccessData(Map<Integer, List> results) {
		Object[] keys = results.keySet().toArray();
    	Object[][] data = new Object[keys.length][NUMBERS_OF_COLUMNS];
    	List<Employee> l = null;
    	Employee e1 = null;
    	Employee e2 = null;
        for (int i = 0; i < keys.length; i++) {
        	
        	l = (List<Employee>)results.get(keys[i]);
        	e1 = l.get(0);
        	e2 = l.get(1);
        	
        	data[i][0] = e1.getEmpID();
        	data[i][1] = e2.getEmpID();
			data[i][2] = keys[i];
			
			LocalDate e1From = e1.getDateFrom();
			LocalDate e2From = e2.getDateFrom();
			LocalDate e1To = e1.getDateTo();
			LocalDate e2To = e2.getDateTo();
			
			if(e1From.isAfter(e2From)) {
				if(e1To.isBefore(e2To) || e1To.isEqual(e2To))
					data[i][3] = ChronoUnit.DAYS.between(e1From, e1To);
				else
					data[i][3] = ChronoUnit.DAYS.between(e1From, e2To);
			} else if (e1From.isBefore(e2From)) {
				if (e1To.isAfter(e2To) || e1To.isEqual(e2To))
					data[i][3] = ChronoUnit.DAYS.between(e2From, e2To);
				else 
					data[i][3] = ChronoUnit.DAYS.between(e2From, e1To);
			} else {
				if (e1To.isAfter(e2To) || e1To.isEqual(e2To))
					data[i][3] = ChronoUnit.DAYS.between(e1From, e1To);
				else 
					data[i][3] = ChronoUnit.DAYS.between(e2From, e2To);
			}
		}
		return data;
	} 
}
