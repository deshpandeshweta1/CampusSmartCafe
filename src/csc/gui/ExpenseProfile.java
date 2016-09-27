package csc.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import csc.control.CampusCardUserProfile;
import csc.entity.CampusCardUser;
import csc.entity.CardUserPreferences;

public class ExpenseProfile {	
	
	JComboBox comboDuration;
	ChartPanel chartPanel1, chartPanel2;
	JPanel panel0, panel1, panel2, panel3, panel4, panel5, panel6, panel7, panel8, chartPanel;
	JLabel getMoneyLeft, getTime;
	CategoryDataset dataset1;
	DefaultCategoryDataset dataset2;
	
	public JPanel displayUserExpenseProfile(){
		
		JPanel profilePanel = new JPanel();
		profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));	
		profilePanel.setBackground(new Color(225, 225, 128));
		panel0 = getPanel0();
		panel1 = getPanel1();
		panel2 = getPanel2();
		panel3 = getPanel3();
		panel4 = getPanel4();
		panel5 = getPanel5();
		panel6 = getPanel6();
		panel7 = getPanel7();
		chartPanel = new JPanel(new FlowLayout());
		chartPanel.add(panel6);
		chartPanel.add(panel7);
		
		profilePanel.add(panel0);
		profilePanel.add(panel1);
		profilePanel.add(Box.createRigidArea(new Dimension(0,25)));
		profilePanel.add(panel2);
		profilePanel.add(panel3);
		profilePanel.add(panel4);
		profilePanel.add(Box.createRigidArea(new Dimension(0,25)));
		profilePanel.add(panel5);
		profilePanel.add(Box.createRigidArea(new Dimension(0,35)));
		profilePanel.add(chartPanel);
		
		// TO-DO: add last purchase order details here.
		
		return profilePanel;
	}
	
	private JPanel getPanel0() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JLabel label = new JLabel("Time");
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		LocalTime time = LocalTime.now();
		getTime = new JLabel(time.toString());
		getTime.setFont(new Font("Serif", Font.PLAIN, 20));
		panel.add(label);
		panel.add(getTime);
		panel.setBackground(new Color(225, 225, 128));
		return panel;
	}
	
	private JPanel getPanel1() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JLabel name = new JLabel("Name:");
		name.setFont(new Font("Serif", Font.BOLD, 20));
		JLabel getName = new JLabel(CampusCardUser.getName());
		getName.setFont(new Font("Serif", Font.BOLD, 20));
		panel.add(name);
		panel.add(getName);
		panel.setBackground(new Color(225, 225, 128));
		return panel;
	}
	
	private JPanel getPanel2() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JLabel monthlyExpenseLimit = new JLabel("Monthly Expense Limit:");
		String temp = CardUserPreferences.getMonthlyExpenseLimit() + "";
		JTextField getMonthlyExpenseLimit = new JTextField(10);
		getMonthlyExpenseLimit.setText(temp);
		getMonthlyExpenseLimit.setEditable(false);
		
		JButton save = new JButton("Save");   // TO-DO: write actionPerformed for "save"
		JButton cancel = new JButton("Cancel");
		JButton delete = new JButton("Delete");  // TO-DO: write actionPerformed for "delete"
		JButton edit = new JButton("Edit");
		
		save.setVisible(false);
		cancel.setVisible(false);
		String limit = getMonthlyExpenseLimit.getText();
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				getMonthlyExpenseLimit.setText(limit);
				getMonthlyExpenseLimit.setEditable(false);
				save.setVisible(false);
				cancel.setVisible(false);
				edit.setVisible(true);
				delete.setVisible(true);
				LocalTime time = LocalTime.now();
				getTime.setText(time.toString());
				getTime.repaint();
			}
		});	
		
		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				getMonthlyExpenseLimit.setEditable(true);;
				edit.setVisible(false);
				delete.setVisible(false);
				save.setVisible(true);
				cancel.setVisible(true);
				LocalTime time = LocalTime.now();
				getTime.setText(time.toString());
				getTime.repaint();
			}
		});
		
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					int s = CampusCardUserProfile.updateMonthlyExpenseLimit(0);
					String temp2 = CardUserPreferences.getMoneyLeft() + "";
					getMoneyLeft.setText(temp2);
					getMoneyLeft.repaint();
					LocalTime time = LocalTime.now();
					getTime.setText(time.toString());
					getTime.repaint();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		});		
		
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				getMonthlyExpenseLimit.setEditable(false);
				save.setVisible(false);
				cancel.setVisible(false);
				edit.setVisible(true);
				delete.setVisible(true);
				LocalTime time = LocalTime.now();
				getTime.setText(time.toString());
				getTime.repaint();
				
				String newValue = getMonthlyExpenseLimit.getText();
				String status = CampusCardUserProfile.checkDouble(newValue);
				
				if(status.isEmpty()) { //Success case
					try {						
						int s = CampusCardUserProfile.updateMonthlyExpenseLimit(Double.parseDouble(newValue));
						
						if(s == 2) {
							JOptionPane.showMessageDialog( null,
									"Overspent in this month." );
						}
						String temp2 = CardUserPreferences.getMoneyLeft() + "";
						getMoneyLeft.setText(temp2);
						getMoneyLeft.repaint();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog( null,
							status );
				}				
			}
		});		
		
		panel.add(monthlyExpenseLimit);
		panel.add(getMonthlyExpenseLimit);
		panel.add(edit);
		panel.add(delete);
		panel.add(save);
		panel.add(cancel);
		panel.setBackground(new Color(225, 225, 128));
		return panel;
	}
	
	private JPanel getPanel3() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JLabel moneySpentTillDate = new JLabel("Money spent in this month:");
		String temp1 = CardUserPreferences.getMoneySpentTillDate() + "";
		JLabel getMoneySpentTillDate = new JLabel(temp1);
		panel.add(moneySpentTillDate);
		panel.add(getMoneySpentTillDate);
		panel.setBackground(new Color(225, 225, 128));
		return panel;
	}
	
	private JPanel getPanel4() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JLabel moneyLeft = new JLabel("Money left:");
		String temp2 = CardUserPreferences.getMoneyLeft() + "";		
		getMoneyLeft = new JLabel(temp2);
		panel.add(moneyLeft);
		panel.add(getMoneyLeft);
		
		if (CardUserPreferences.getMonthlyExpenseLimit() == 0) {
			panel.setVisible(false);
		}
		panel.setBackground(new Color(225, 225, 128));
		return panel;
	}
	
	private JPanel getPanel5() {
		JPanel panel = new JPanel();
		panel.setBackground(new Color(225, 225, 128));
		panel.setLayout(new FlowLayout());
		JLabel showGraph = new JLabel("Show money spent in last:");
		
		comboDuration = new JComboBox();
		comboDuration.addItem("1 Week");
		comboDuration.addItem("15 Days");
		//comboDuration.addItem("3 months");	
		comboDuration.setForeground(Color.BLUE);
		//comboDuration.setFont(new Font("Times New Roman", Font.BOLD, 16));
		comboDuration.setEditable(false);
		
		JButton btn = new JButton("Display");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				LocalTime time = LocalTime.now();
				getTime.setText(time.toString());
				getTime.repaint();
				/*chartPanel1.repaint();
				panel6.repaint();
				chartPanel2.repaint();
				panel7.repaint();
				chartPanel.repaint();*/	
				
				panel6.removeAll();
				panel6.add(getPanel6());
				panel6.validate();
				panel6.repaint();

				panel7.removeAll();
				panel7.add(getPanel7());
				panel7.validate();
				panel7.repaint();
			}
		});	
		
		panel.add(showGraph);
		panel.add(comboDuration);
		panel.add(btn);
		
		return panel;
	}
	
	private JPanel getPanel6() {
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(225, 225, 128));
		dataset1 = createDataset1();
        JFreeChart chart = createChart1(dataset1);
        chartPanel1 = new ChartPanel(chart);
        chartPanel1.setPreferredSize(new java.awt.Dimension(650, 270));     
        panel.add(chartPanel1);
		return panel;
	}
	
	private JPanel getPanel7() {
		
		JPanel panel = new JPanel();		
		panel.setBackground(new Color(225, 225, 128));
		dataset2 = createDataset2();
        JFreeChart chart = createChart2(dataset2);
        chartPanel2 = new ChartPanel(chart);
        chartPanel2.setPreferredSize(new java.awt.Dimension(650, 270));
        
        panel.add(chartPanel2);
		return panel;
	}
	
	private JFreeChart createChart1(CategoryDataset dataset) {
        // create the chart...
        JFreeChart chart = ChartFactory.createBarChart(
            "Bar Graph",                 // chart title
            "Date",                  // domain axis label
            "Money spent",                 // range axis label
            dataset1,                     // data
            PlotOrientation.HORIZONTAL,  // orientation
            true,                        // include legend
            true,
            false
        );
        
        chart.setBackgroundPaint(Color.lightGray);

        CategoryPlot plot = chart.getCategoryPlot();

        plot.getRenderer().setSeriesPaint(0, new Color(0, 0, 255));
        
        // change the auto tick unit selection to integer units only...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        //rangeAxis.setRange(0, 3000);
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        return chart;
    }
	
	private JFreeChart createChart2(DefaultCategoryDataset dataset) {
        
        // create the chart...
        JFreeChart chart = ChartFactory.createLineChart(
            "Line Graph",      // chart title
            "Date",                      // x axis label
            "Money Spent",       // y axis label
            dataset2,                  // data
            PlotOrientation.HORIZONTAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);
        
//        XYPlot plot = chart.getXYPlot();
//        plot.setBackgroundPaint(Color.lightGray);
//        plot.setDomainGridlinePaint(Color.white);
//        plot.setRangeGridlinePaint(Color.white);
//        
//        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
//        renderer.setSeriesLinesVisible(1, false);
//        plot.setRenderer(renderer);
//
//        // change the auto tick unit selection to integer units only...
//        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
//        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
                
        return chart;
        
    }
	
	private CategoryDataset createDataset1() {
		
		ArrayList<Double> moneyList = CardUserPreferences.getMoneyList();
		ArrayList<String> dateList = CardUserPreferences.getDateList();
		ArrayList<String> splitDateList = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		ArrayList <String> getDays = new ArrayList <String>();
		
		double [] money = new double[16];
		for(int i = 0; i <= 15; i++) {
			money[i] = 0;
		}
		
		ArrayList<String> columnList = new ArrayList();
		double[][] data ;
		String month = "";
		for(int i = 0; i < dateList.size(); i++) {
			columnList.add(dateList.get(i));
			String [] splitDate = dateList.get(i).split("-");
			month = splitDate[1];
			if(splitDate[2].substring(0, 1).equals("0")) {
				splitDate[2] = splitDate[2].substring(1, 2);
			}
			splitDateList.add(splitDate[2]);
		}
		
		int durataionSelected = (int) comboDuration.getSelectedIndex();
		if(durataionSelected==0)
		{
			
			// get starting date
			cal.add(Calendar.DAY_OF_YEAR, -7);
			
			for(int i = 0; i< 7; i++){
			    cal.add(Calendar.DAY_OF_YEAR, 1);
			    getDays.add(sdf.format(cal.getTime()));
			}
			Object[] objDays1 = getDays.toArray();
			String[] strDays = Arrays.copyOf(objDays1, objDays1.length, String[].class);
			
			for(int i = 0; i < dateList.size(); i++) {
				if(getDays.contains(dateList.get(i).toString()))
				{
					int pos = getDays.indexOf(dateList.get(i))+1;
					money[pos]=moneyList.get(i);
				
				}
			}
			
			
			data = new double[][] {
				 {money[1], money[2], money[3], money[4], money[5],
					 money[6], money[7]}
				};
		}
		else if(durataionSelected==1)
		{
			// get starting date
						cal.add(Calendar.DAY_OF_YEAR, -15);
						
						for(int i = 0; i< 15; i++){
						    cal.add(Calendar.DAY_OF_YEAR, 1);
						    getDays.add(sdf.format(cal.getTime()));
						}
						Object[] objDays1 = getDays.toArray();
						String[] strDays = Arrays.copyOf(objDays1, objDays1.length, String[].class);
						
						for(int i = 0; i < dateList.size(); i++) {
							if(getDays.contains(dateList.get(i).toString()))
							{
								int pos = getDays.indexOf(dateList.get(i))+1;
								money[pos]=moneyList.get(i);
							
							}
						}
			data = new double[][] {
				 {money[1], money[2], money[3], money[4], money[5],
					 money[6], money[7], money[8], money[9],money[10], money[11], money[12], money[13], money[14], money[15]}
				};
		
		}
		else
		{
			data = new double[][] {
				 {money[1], money[2], money[3], money[4], money[5],
	                	money[6], money[7], money[8], money[9],money[10], money[11], money[12], money[13], money[14], money[15]}
				};
		}
		
		String[] userName = new String[] {"Money Spent"};
		
		Object[] objDays1 = getDays.toArray();
		String[] strDays = Arrays.copyOf(objDays1, objDays1.length, String[].class);
		
			return DatasetUtilities.createCategoryDataset(userName,strDays , data);
		
		       
    }
	
	private DefaultCategoryDataset  createDataset2() {		 
		
		ArrayList<Double> moneyList = CardUserPreferences.getMoneyList();
		ArrayList<String> dateList = CardUserPreferences.getDateList();
		ArrayList<String> splitDateList = new ArrayList<String>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		ArrayList <String> getDays = new ArrayList <String>();
		
		double [] money = new double[16];
		for(int i = 0; i <= 15; i++) {
			money[i] = 0;
		}
		
		ArrayList<String> columnList = new ArrayList();
		double[][] data ;
		String month = "";
		for(int i = 0; i < dateList.size(); i++) {
			columnList.add(dateList.get(i));
			String [] splitDate = dateList.get(i).split("-");
			month = splitDate[1];
			if(splitDate[2].substring(0, 1).equals("0")) {
				splitDate[2] = splitDate[2].substring(1, 2);
			}
			splitDateList.add(splitDate[2]);
		}
		

		int durataionSelected = (int) comboDuration.getSelectedIndex();
		if(durataionSelected==0)
		{
			
			// get starting date
			cal.add(Calendar.DAY_OF_YEAR, -7);
			
			for(int i = 0; i< 7; i++){
			    cal.add(Calendar.DAY_OF_YEAR, 1);
			    getDays.add(sdf.format(cal.getTime()));
			}
			Object[] objDays1 = getDays.toArray();
			String[] strDays = Arrays.copyOf(objDays1, objDays1.length, String[].class);
			
			for(int i = 0; i < dateList.size(); i++) {
				if(getDays.contains(dateList.get(i).toString()))
				{
					int pos = getDays.indexOf(dateList.get(i))+1;
					money[pos]=moneyList.get(i);
				
				}
			}
			
			
			data = new double[][] {
				 {money[1], money[2], money[3], money[4], money[5],
	                	money[6], money[7]}
				};
		}
		else if(durataionSelected==1)
		{
			// get starting date
						cal.add(Calendar.DAY_OF_YEAR, -15);
						
						for(int i = 0; i< 15; i++){
						    cal.add(Calendar.DAY_OF_YEAR, 1);
						    getDays.add(sdf.format(cal.getTime()));
						}
						Object[] objDays1 = getDays.toArray();
						String[] strDays = Arrays.copyOf(objDays1, objDays1.length, String[].class);
						
						for(int i = 0; i < dateList.size(); i++) {
							if(getDays.contains(dateList.get(i).toString()))
							{
								int pos = getDays.indexOf(dateList.get(i))+1;
								money[pos]=moneyList.get(i);
							
							}
						}
			data = new double[][] {
				 {money[1], money[2], money[3], money[4], money[5],
	                	money[6], money[7], money[8], money[9],money[10], money[11], money[12], money[13], money[14], money[15]}
				};
		
		}
		else
		{
			data = new double[][] {
				 {money[1], money[2], money[3], money[4], money[5],
	                	money[6], money[7], money[8], money[9],money[10], money[11], money[12], money[13], money[14], money[15]}
				};
		}
		
		String[] userName = new String[] {"Money Spent"};
		
		Object[] objDays1 = getDays.toArray();
		String[] strDays = Arrays.copyOf(objDays1, objDays1.length, String[].class);
		
			return (DefaultCategoryDataset) DatasetUtilities.createCategoryDataset(userName,strDays , data);
	
	}
}
