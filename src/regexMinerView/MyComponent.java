package regexMinerView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class MyComponent {
	public static final Color burlywood = new Color(222, 184, 135);
	public static final Color green3 = new Color(0, 205, 0); // #00CD00
	public static final Color burlywood3 = new Color(205, 170, 125);
	public static final Color burlywood4 = new Color(139, 115, 85);
	public static final Color gold2 = new Color(238, 201, 0);
	public static final Color gold4 = new Color(139, 117, 0);

	public static JButton myJButton(String text, String actionCommand, int width) {
		JButton myJButton = new JButton(text);
		Color gold2 = new Color(238, 201, 0);
		Dimension dim = new Dimension(width, 30);

		myJButton.setActionCommand(actionCommand);
		myJButton.setBorder(BorderFactory.createRaisedBevelBorder());
		myJButton.setBackground(gold2);
		myJButton.setMinimumSize(dim);
		myJButton.setPreferredSize(dim);
		myJButton.setMaximumSize(dim);
		return myJButton;
	}
	
	public static JButton myJToggle(String text, boolean selected, String actionCommand, int width) {
		JButton myJToggle = new JButton(text);
		Color gold2 = new Color(238, 201, 0);
		Dimension dim = new Dimension(width, 30);

		myJToggle.setActionCommand(actionCommand);
		if (selected) {
			myJToggle.setBorder(BorderFactory.createLoweredBevelBorder());
			myJToggle.setBackground(gold4);
		} else {
			myJToggle.setBorder(BorderFactory.createRaisedBevelBorder());
			myJToggle.setBackground(gold2);
		}
		myJToggle.setPreferredSize(dim);
		myJToggle.setMaximumSize(dim);
		
		return myJToggle;
	}
	
	public static JPanel myJPanel(Color background, int width, int height) {
		JPanel myJPanel = new JPanel();
		Dimension dim = new Dimension(width, height);
		Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		
		myJPanel.setBackground(background);
		myJPanel.setBorder(border);
		myJPanel.setPreferredSize(dim);
		myJPanel.setMaximumSize(dim);

		return myJPanel;
	}
	
	public static JPanel myJPanel(LayoutManager layout, Color background, int width, int height) {
		JPanel myJPanel = new JPanel();
		Dimension dim = new Dimension(width, height);
		Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		
		myJPanel.setLayout(layout);
		myJPanel.setBorder(border);
		myJPanel.setBackground(background);
		myJPanel.setPreferredSize(dim);
		myJPanel.setMaximumSize(dim);

		return myJPanel;
	}
	
	public static Box myBox(int axis) {
		Box myBox = new Box(axis);
		Border border = BorderFactory.createEmptyBorder(0, 5, 0, 5);
		myBox.setBorder(border);
		return myBox;
		
	}

}
