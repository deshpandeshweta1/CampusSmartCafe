package csc.gui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import csc.control.CampusCardUserProfile;
import csc.entity.CampusCardUser;
import csc.entity.CardUserPreferences;
import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class DietaryProfile {
	
	JComboBox comboDuration;
	ChartPanel chartPanel1, chartPanel2;
	JPanel panel0, panel1, panel2, panel3, panel4, panel5, panel6, panel7, chartPanel;
	JLabel getCaloriesLeft, getTime, lowCarbLb, lowFatLb, lowSodiumLb, vegLb, veganLb, nonVegLb, none;
	CategoryDataset dataset1;
	DefaultCategoryDataset dataset2;
	
	public JPanel displayUserDietaryProfile(){
		
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
			
		JButton edit1 = new JButton("Edit");
		JButton delete1 = new JButton("Delete");
		JButton save1 = new JButton("Save");   
		JButton cancel1 = new JButton("Cancel");
		save1.setVisible(false);
		cancel1.setVisible(false);
		
		JLabel dietaryRestrictions = new JLabel("Dietary Restrictions:");
		//JLabel getDietaryRestrictions = new JLabel(getRestrictions);	
		 
		none = new JLabel("None");
		none.setVisible(false);
		lowCarbLb = new JLabel("Low-Carb");
		lowCarbLb.setVisible(false);
		lowFatLb = new JLabel("Low-Fat");
		lowFatLb.setVisible(false);
		lowSodiumLb = new JLabel("Low-Sodium");
		lowSodiumLb.setVisible(false);
		vegLb = new JLabel("Veg");
		vegLb.setVisible(false);
		nonVegLb = new JLabel("Non-Veg");
		nonVegLb.setVisible(false);
		veganLb = new JLabel("Vegan");
		veganLb.setVisible(false);
		JPanel diet = new JPanel();
		diet.add(dietaryRestrictions);
		diet.add(none);
		diet.add(lowCarbLb);
		diet.add(lowFatLb);
		diet.add(lowSodiumLb);
		diet.add(vegLb);
		diet.add(nonVegLb);
		diet.add(veganLb);		
		diet.add(edit1);
		diet.add(delete1);
		diet.add(save1);
		diet.add(cancel1);
		diet.setBackground(new Color(225, 225, 128));
				
		String[] temp = splitRestrictions();
		String getRestrictions = "";
		if(temp != null) {
			getRestrictions = getRestrictions(temp);
		}
		if(getRestrictions.isEmpty()) {
			getRestrictions = "None";
			none.setVisible(true);
			delete1.setVisible(false);
			edit1.setText("Add");
		}		
		
		String stored = getRestrictions;
		
		final JCheckBox lowCarb = new JCheckBox("Low Carb");
	    final JCheckBox lowFat = new JCheckBox("Low Fat");
	    final JCheckBox lowSodium = new JCheckBox("Low Sodium");	    
	    	       
	    JPanel low = new JPanel();
	    low.add(lowCarb);
	    low.add(lowFat);
	    low.add(lowSodium);
	    low.setBackground(new Color(225, 225, 128));
	    
	    JLabel foodType = new JLabel("Food Type: ");	    
	    final JRadioButton veg = new JRadioButton("VEG");
	    final JRadioButton nonVeg = new JRadioButton("NON-VEG");
	    final JRadioButton vegan = new JRadioButton("VEGAN");
	    
	    ButtonGroup editableGroup = new ButtonGroup();
	    editableGroup.add(veg);
	    editableGroup.add(nonVeg);
	    editableGroup.add(vegan);
	    
	    if(getRestrictions.equals("None") == false) {
	    	String[] splitTemp = splitRestrictions();
		    if(splitTemp[0].equals("Y")) {
		    	lowCarb.setSelected(true);
			} 
			if(splitTemp[1].equals("Y")) {
				lowFat.setSelected(true);
			}
			if(splitTemp[2].equals("Y")) {
				lowSodium.setSelected(true);
			}
			if(splitTemp[3].equals("Y")) {
				veg.setSelected(true);
			}
			if(splitTemp[4].equals("Y")) {
				nonVeg.setSelected(true);
			}
			if(splitTemp[5].equals("Y")) {
				vegan.setSelected(true);
			}			
	    }
	    
	    low.setVisible(false);;
	    foodType.setVisible(false);	 
	    veg.setVisible(false);;
	    nonVeg.setVisible(false);
	    vegan.setVisible(false);
	    
	    String newRestrictions = "";
	    
	    lowCarb.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {
	        	 
	        	 if (e.getStateChange()==1) {
		        	 lowCarbLb.setVisible(true); 
		        	 none.setVisible(false);
	        	 } else {
	        		 lowCarbLb.setVisible(false);
	        	 }
	         }	         
	    });
	   	    
	    JLabel label1 = new JLabel();
	    lowFat.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {

	        	 if (e.getStateChange()==1) {
		        	 lowFatLb.setVisible(true); 
		        	 none.setVisible(false);
	        	 } else {
	        		 lowFatLb.setVisible(false);
	        	 }
	         }
	    });
	    
	    JLabel label2 = new JLabel();
	    lowSodium.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {
	        	 
	        	 if (e.getStateChange()==1) {
		        	 lowSodiumLb.setVisible(true);
		        	 none.setVisible(false);
	        	 } else {
	        		 lowSodiumLb.setVisible(false);
	        	 }
	         }
	    });
	    
	    veg.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent event) {	        	 
	    		
	    		if (veg.isSelected()) {
		        	 vegLb.setVisible(true); 
		        	 nonVegLb.setVisible(false); 
		        	 veganLb.setVisible(false);
		        	 none.setVisible(false);
	        	 } else {
	        		 vegLb.setVisible(false);
	        	 }
	         }
	    });
	    
	    JLabel label4 = new JLabel();
	    nonVeg.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent event) {

	    		if (nonVeg.isSelected()) {
	    			 nonVegLb.setVisible(true); 
	    			 vegLb.setVisible(false);
	    			 veganLb.setVisible(false);
	    			 none.setVisible(false);
	        	 } else {
	        		 nonVegLb.setVisible(false);
	        	 }   		
	         }
	    });
	    
	    vegan.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent event) {
	        	 
	    		if (vegan.isSelected()) {
	    			veganLb.setVisible(true); 
	    			vegLb.setVisible(false); 
		        	 nonVegLb.setVisible(false);
		        	 none.setVisible(false);
	        	 } else {
	        		 veganLb.setVisible(false);
	        	 }        
	         }
	    });
	    
	    edit1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				LocalTime time = LocalTime.now();
				getTime.setText(time.toString());
				getTime.repaint();
				edit1.setVisible(false);
				low.setVisible(true);;
				foodType.setVisible(true);	  
				veg.setVisible(true);;
			    nonVeg.setVisible(true);
			    vegan.setVisible(true);
				delete1.setVisible(false);
				save1.setVisible(true);
				cancel1.setVisible(true);
			}
		});	
	    
	    delete1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				LocalTime time = LocalTime.now();
				getTime.setText(time.toString());
				getTime.repaint();
				try {
					int s = CampusCardUserProfile.updateDietaryRestrictions("N","N","N","N","N","N","N");
					none.setVisible(true);
					edit1.setText("Add");
					delete1.setVisible(false);
					lowCarbLb.setVisible(false);
					lowFatLb.setVisible(false);
					lowSodiumLb.setVisible(false);
					vegLb.setVisible(false);
					nonVegLb.setVisible(false);
					veganLb.setVisible(false);
					lowCarb.setSelected(false);
					lowFat.setSelected(false);
					lowSodium.setSelected(false);					
					editableGroup.clearSelection();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});	 
	    
	    save1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				LocalTime time = LocalTime.now();
				getTime.setText(time.toString());
				getTime.repaint();
				save1.setVisible(false);
				cancel1.setVisible(false);
				edit1.setVisible(true);
				delete1.setVisible(true);
				low.setVisible(false);
				foodType.setVisible(false);	  
				veg.setVisible(false);;
			    nonVeg.setVisible(false);
			    vegan.setVisible(false);
				
				try {					
					String t1 = "N", t2 = "N", t3 = "N", t4 = "N", t5 = "N", t6 = "N", t7 = "N";
					if(lowCarbLb.isVisible()) {t2 = "Y"; t1 = "Y";}
					if(lowFatLb.isVisible()) {t3 = "Y"; t1 = "Y";}
					if(lowSodiumLb.isVisible()) {t4 = "Y"; t1 = "Y";}
					if(vegLb.isVisible()) {t5 = "Y"; t1 = "Y";}
					if(nonVegLb.isVisible()) {t6 = "Y"; t1 = "Y";}
					if(veganLb.isVisible()) {t7 = "Y"; t1 = "Y";}
					int s = CampusCardUserProfile.updateDietaryRestrictions(t1,t2,t3,t4,t5,t6,t7);
										
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		});
	    
	    cancel1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				LocalTime time = LocalTime.now();
				getTime.setText(time.toString());
				getTime.repaint();
				none.setText(stored);
				none.setVisible(true);
				none.repaint();				
				save1.setVisible(false);
				cancel1.setVisible(false);
				edit1.setVisible(true);
				delete1.setVisible(true);
				low.setVisible(false);
				foodType.setVisible(false);
				veg.setVisible(false);;
			    nonVeg.setVisible(false);
			    vegan.setVisible(false);
			    lowCarb.setSelected(false);
				lowFat.setSelected(false);
				lowSodium.setSelected(false);					
				editableGroup.clearSelection();
			}
		});
	    
	    JPanel radioBtnPanel = new JPanel();
	    radioBtnPanel.setLayout(new FlowLayout());
	    radioBtnPanel.add(foodType);
	    radioBtnPanel.add(veg);
	    radioBtnPanel.add(nonVeg);
	    radioBtnPanel.add(vegan);
		
	    profilePanel.add(panel0);
		profilePanel.add(panel1);
		profilePanel.add(panel2);
		profilePanel.add(panel3);
		profilePanel.add(panel4);
		profilePanel.add(diet);
		profilePanel.add(low);
		profilePanel.add(radioBtnPanel);
		profilePanel.add(panel5);
		profilePanel.add(chartPanel);
		
		return profilePanel;
	}
	
	private String[] splitRestrictions() {
		
		ArrayList<String> restrictions = CardUserPreferences.getDietaryRestrictions();
		String [] splitTemp = null;
		//System.out.println(restrictions);
		
		if(restrictions != null) {
			for(int i = 0; i < restrictions.size(); i++) {
				//System.out.println(restrictions);
				String temp = restrictions.get(i);
				splitTemp = splitData(temp);
			}
		}
		return splitTemp;
	}
	
	private String getRestrictions(String[] splitTemp) {
		
		String returnString = "";
		
		if(splitTemp[0].equals("Y")) {
			returnString = returnString.concat("Low Carb, ");
			lowCarbLb.setVisible(true);
		} 
		if(splitTemp[1].equals("Y")) {
			returnString = returnString.concat("Low Fat, ");
			lowFatLb.setVisible(true);
		}
		if(splitTemp[2].equals("Y")) {
			returnString = returnString.concat("Low Sodium, ");
			lowSodiumLb.setVisible(true);
		}
		if(splitTemp[3].equals("Y")) {
			returnString = returnString.concat("Veg , ");
			vegLb.setVisible(true);
		}
		if(splitTemp[4].equals("Y")) {
			returnString = returnString.concat("Non Veg, ");
			nonVegLb.setVisible(true);
		}
		if(splitTemp[5].equals("Y")) {
			returnString = returnString.concat("Vegan, ");
			veganLb.setVisible(true);
		}
		returnString = returnString.substring(0, returnString.length()-2);
		return returnString;		
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
		JLabel dailyCaloriesLimit = new JLabel("Daily Calories Limit:");
		JTextField getDailyCaloriesLimit = new JTextField(10);
		String temp3 = CardUserPreferences.getDailyCaloriesLimit() + "";		
		getDailyCaloriesLimit.setText(temp3);
		getDailyCaloriesLimit.setEditable(false);
		panel.setBackground(new Color(225, 225, 128));
		JButton save = new JButton("Save");   // TO-DO: write actionPerformed for "save"
		JButton cancel = new JButton("Cancel");
		JButton delete = new JButton("Delete");  // TO-DO: write actionPerformed for "delete"
		JButton edit = new JButton("Edit");
		
		save.setVisible(false);
		cancel.setVisible(false);
		String limit = getDailyCaloriesLimit.getText();
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				LocalTime time = LocalTime.now();
				getTime.setText(time.toString());
				getTime.repaint();
				getDailyCaloriesLimit.setText(limit);
				getDailyCaloriesLimit.setEditable(false);
				save.setVisible(false);
				cancel.setVisible(false);
				edit.setVisible(true);
				delete.setVisible(true);
			}
		});	
		
		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				LocalTime time = LocalTime.now();
				getTime.setText(time.toString());
				getTime.repaint();
				getDailyCaloriesLimit.setEditable(true);;
				edit.setVisible(false);
				delete.setVisible(false);
				save.setVisible(true);
				cancel.setVisible(true);
			}
		});
		
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				LocalTime time = LocalTime.now();
				getTime.setText(time.toString());
				getTime.repaint();
				try {
					int s = CampusCardUserProfile.updateDailyCalorieLimit(0);
					panel4.repaint();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
		});		
		
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				LocalTime time = LocalTime.now();
				getTime.setText(time.toString());
				getTime.repaint();
				getDailyCaloriesLimit.setEditable(false);
				save.setVisible(false);
				cancel.setVisible(false);
				edit.setVisible(true);
				delete.setVisible(true);
				
				String newValue = getDailyCaloriesLimit.getText();
				String status = CampusCardUserProfile.checkInt(newValue);				
				
				if(status.isEmpty()) { //Success case
					try {
						int s = CampusCardUserProfile.updateDailyCalorieLimit(Integer.parseInt(newValue));
						
						if(s == 2) {
							JOptionPane.showMessageDialog( null,
									"Overintake of calories in this month." );
						}
						
						String temp3 = CardUserPreferences.getCaloriesLeft() + "";		
						System.out.println(temp3);
						getCaloriesLeft.setText(temp3);
						getCaloriesLeft.repaint();
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
		
		panel.add(dailyCaloriesLimit);
		panel.add(getDailyCaloriesLimit);
		panel.add(edit);
		panel.add(delete);
		panel.add(save);
		panel.add(cancel);
		
		return panel;
	}
	
	private JPanel getPanel3() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JLabel calorieIntakeTillDate = new JLabel("Calorie intake of this month:");
		String temp4 = CardUserPreferences.getCalorieIntakeTillDate() + "";
		JLabel getCalorieIntakeTillDate = new JLabel(temp4);
		panel.add(calorieIntakeTillDate);
		panel.add(getCalorieIntakeTillDate);
		panel.setBackground(new Color(225, 225, 128));
		return panel;
	}
	
	private JPanel getPanel4() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JLabel caloriesLeft = new JLabel("Calories remaining:");
		String temp5 = CardUserPreferences.getCaloriesLeft() + "";
		getCaloriesLeft = new JLabel(temp5);
		panel.add(caloriesLeft);
		panel.add(getCaloriesLeft);
		panel.setBackground(new Color(225, 225, 128));
		if (CardUserPreferences.getDailyCaloriesLimit() == 0) {
			panel.setVisible(false);
		}

		return panel;
	}
	
	private JPanel getPanel5() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JLabel showGraph = new JLabel("Show money spent in last:");
		panel.setBackground(new Color(225, 225, 128));
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
		dataset1 = createDataset1();
        JFreeChart chart = createChart1(dataset1);
        chartPanel1 = new ChartPanel(chart);
        chartPanel1.setPreferredSize(new java.awt.Dimension(650, 270));     
		
        panel.add(chartPanel1);
		return panel;
	}
	
	private JPanel getPanel7() {
		
		JPanel panel = new JPanel();
		dataset2 = createDataset2();
        JFreeChart chart = createChart2(dataset2);
        chartPanel2 = new ChartPanel(chart);
        chartPanel2.setPreferredSize(new java.awt.Dimension(650, 270));
        
        panel.add(chartPanel2);
		return panel;
	}
	
	private String[] splitData(String temp) {
		 return temp.split("!");
	}
	
	private JFreeChart createChart1(CategoryDataset dataset) {
        // create the chart...
        JFreeChart chart = ChartFactory.createBarChart(
            "Bar Graph",                 // chart title
            "Date",                  // domain axis label
            "Number of calories",                 // range axis label
            dataset1,                     // data
            PlotOrientation.HORIZONTAL,  // orientation
            true,                        // include legend
            true,
            false
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        chart.setBackgroundPaint(Color.lightGray);

        CategoryPlot plot = chart.getCategoryPlot();

        plot.getRenderer().setSeriesPaint(0, new Color(0, 0, 255));
        //plot.getRenderer().setSeriesPaint(1, new Color(75, 75, 255));
        //plot.getRenderer().setSeriesPaint(2, new Color(150, 150, 255));
        
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
            "Number of calories",       // y axis label
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
		ArrayList<Integer> calorieList = CardUserPreferences.getCaloriesList();
		ArrayList<String> dateList = CardUserPreferences.getDateList();
		ArrayList<String> splitDateList = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		ArrayList <String> getDays = new ArrayList <String>();
		
		double [] calorie = new double[16];
		for(int i = 0; i <= 15; i++) {
			calorie[i] = 0;
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
					calorie[pos]=calorieList.get(i);
				
				}
			}
			
			
			data = new double[][] {
				 {calorie[1], calorie[2], calorie[3], calorie[4], calorie[5],
	                	calorie[6], calorie[7]}
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
								calorie[pos]=calorieList.get(i);
							
							}
						}
			data = new double[][] {
				 {calorie[1], calorie[2], calorie[3], calorie[4], calorie[5],
	                	calorie[6], calorie[7], calorie[8], calorie[9],calorie[10], calorie[11], calorie[12], calorie[13], calorie[14], calorie[15]}
				};
		
		}
		else
		{
			data = new double[][] {
				 {calorie[1], calorie[2], calorie[3], calorie[4], calorie[5],
	                	calorie[6], calorie[7], calorie[8], calorie[9],calorie[10], calorie[11], calorie[12], calorie[13], calorie[14], calorie[15]}
				};
		}
		
		String[] userName = new String[] {"Calorie Intake"};
		
		Object[] objDays1 = getDays.toArray();
		String[] strDays = Arrays.copyOf(objDays1, objDays1.length, String[].class);
		
			return DatasetUtilities.createCategoryDataset(userName,strDays , data);
		
		       
    }
	
	private DefaultCategoryDataset createDataset2() {
		 
	
		
		ArrayList<Integer> calorieList = CardUserPreferences.getCaloriesList();
		ArrayList<String> dateList = CardUserPreferences.getDateList();
		ArrayList<String> splitDateList = new ArrayList<String>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		ArrayList <String> getDays = new ArrayList <String>();
		
		double [] calorie = new double[16];
		for(int i = 0; i <= 15; i++) {
			calorie[i] = 0;
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
					calorie[pos]=calorieList.get(i);
				
				}
			}
			
			
			data = new double[][] {
				 {calorie[1], calorie[2], calorie[3], calorie[4], calorie[5],
	                	calorie[6], calorie[7]}
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
								calorie[pos]=calorieList.get(i);
							
							}
						}
			data = new double[][] {
				 {calorie[1], calorie[2], calorie[3], calorie[4], calorie[5],
	                	calorie[6], calorie[7], calorie[8], calorie[9],calorie[10], calorie[11], calorie[12], calorie[13], calorie[14], calorie[15]}
				};
		
		}
		else
		{
			data = new double[][] {
				 {calorie[1], calorie[2], calorie[3], calorie[4], calorie[5],
	                	calorie[6], calorie[7], calorie[8], calorie[9],calorie[10], calorie[11], calorie[12], calorie[13], calorie[14], calorie[15]}
				};
		}
		
		String[] userName = new String[] {"Calorie Intake"};
		
		Object[] objDays1 = getDays.toArray();
		String[] strDays = Arrays.copyOf(objDays1, objDays1.length, String[].class);
		
			return (DefaultCategoryDataset) DatasetUtilities.createCategoryDataset(userName,strDays , data);
	
	}		
		
}
