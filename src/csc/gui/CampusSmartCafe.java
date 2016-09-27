package csc.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import csc.entity.CampusCard;
import csc.entity.CampusCardUser;
import csc.entity.CardUserPreferences;

public class CampusSmartCafe {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				JFrame frame = new JFrame("Campus Smart Cafe");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				// make the frame 1/2 the height and width

				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				int height = screenSize.height;
				int width = screenSize.width;
				//System.out.println(width);
				frame.setSize(width / 2, height / 2);

				// center the mainFrame on screen
				frame.setLocationRelativeTo(null);

				// JTabbedPane tabbedPane = new JTabbedPane();

				// tabbedPane.addTab("Welcome", new CampusCard());
				CampusCardPanel campusCard = new CampusCardPanel(frame);
				frame.getContentPane().add(campusCard);
				frame.getContentPane().setBackground(new Color(225, 225, 128));
				// frame.pack();
				frame.setVisible(true);

				// tabbedPane.removeTabAt(4);
			}
		});

	}
}