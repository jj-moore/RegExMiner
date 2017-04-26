package regexMinerView;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import regexMiner.Controller;
import regexMiner.Skills;

public class ViewSkills extends Box {
	private static final long serialVersionUID = 1L;
	private TreeMap<Skills, JButton[]> btnSkills;

	ViewSkills() {
		super(BoxLayout.PAGE_AXIS);
		this.setAlignmentY(TOP_ALIGNMENT);
		this.setAlignmentX(RIGHT_ALIGNMENT);
		Dimension min = new Dimension(720, 420);
		Dimension max = new Dimension(840, 440);
		this.setMinimumSize(min);
		this.setPreferredSize(min);
		this.setMaximumSize(max);
		btnSkills = new TreeMap<>();
	}

	public void initializeSkills(HashMap<Skills, Integer> skillLevels, Controller controller) {
		Set<Skills> skills = skillLevels.keySet();
		for (Skills skill : skills) {
			this.add(skillPanel(skill, controller));
			this.add(Box.createVerticalGlue());
		}
		this.updateArea(skillLevels);
	}

	private JPanel skillPanel(Skills skill, Controller controller) {
		SpringLayout layout = new SpringLayout();
		JPanel skills = new JPanel(layout);
		skills.setBackground(MyComponent.burlywood);
		JLabel title = new JLabel(skill.getDescription());

		JButton[] buttons = new JButton[5];
		buttons[0] = myButton(skill.getText(1), skill + "_LEVEL", false);
		buttons[1] = myButton(skill.getText(2), skill + "_LEVEL", true);
		buttons[2] = myButton(skill.getText(3), skill + "_LEVEL", false);
		buttons[3] = myButton(skill.getText(4), skill + "_LEVEL", false);
		buttons[4] = myButton(skill.getText(5), skill + "_LEVEL", false);

		buttons[0].addActionListener(controller);
		buttons[1].addActionListener(controller);
		buttons[2].addActionListener(controller);
		buttons[3].addActionListener(controller);
		buttons[4].addActionListener(controller);
		
		layout.putConstraint(SpringLayout.NORTH, buttons[0], 2, SpringLayout.SOUTH, title);
		layout.putConstraint(SpringLayout.NORTH, buttons[1], 0, SpringLayout.NORTH, buttons[0]);
		layout.putConstraint(SpringLayout.NORTH, buttons[2], 0, SpringLayout.NORTH, buttons[1]);
		layout.putConstraint(SpringLayout.NORTH, buttons[3], 0, SpringLayout.NORTH, buttons[2]);
		layout.putConstraint(SpringLayout.NORTH, buttons[4], 0, SpringLayout.NORTH, buttons[3]);

		layout.putConstraint(SpringLayout.WEST, title, 50, SpringLayout.WEST, skills);
		layout.putConstraint(SpringLayout.WEST, buttons[0], 50, SpringLayout.WEST, skills);
		layout.putConstraint(SpringLayout.WEST, buttons[1], 0, SpringLayout.EAST, buttons[0]);
		layout.putConstraint(SpringLayout.WEST, buttons[2], 0, SpringLayout.EAST, buttons[1]);
		layout.putConstraint(SpringLayout.WEST, buttons[3], 0, SpringLayout.EAST, buttons[2]);
		layout.putConstraint(SpringLayout.WEST, buttons[4], 0, SpringLayout.EAST, buttons[3]);
		
		skills.add(title);
		skills.add(buttons[0]);
		skills.add(buttons[1]);
		skills.add(buttons[2]);
		skills.add(buttons[3]);
		skills.add(buttons[4]);
		
		btnSkills.put(skill, buttons);
		return skills;
	}

	private JButton myButton(String text, String action, boolean enabled) {
		JButton myButton = new JButton(text);
		Dimension size = new Dimension(60, 40);
		Border border = BorderFactory.createRaisedBevelBorder();
		myButton.setEnabled(enabled);
		myButton.setMinimumSize(size);
		myButton.setPreferredSize(size);
		myButton.setMaximumSize(size);
		myButton.setBorder(border);
		myButton.setActionCommand(action);
		myButton.setHorizontalAlignment(JLabel.CENTER);
		return myButton;
	}

	public void updateArea(HashMap<Skills, Integer> skillLevels) {

		Set<Skills> skills = skillLevels.keySet();
		for (Skills skill : skills) {
			JButton[] buttons = btnSkills.get(skill);
			switch (skillLevels.get(skill)) {
			case 5:
				buttons[4].setBackground(Color.RED);
				buttons[4].setForeground(Color.WHITE);
			case 4:
				buttons[3].setBackground(Color.RED);
				buttons[3].setForeground(Color.WHITE);
			case 3:
				buttons[2].setBackground(Color.RED);
				buttons[2].setForeground(Color.WHITE);
			case 2:
				buttons[1].setBackground(Color.RED);
				buttons[1].setForeground(Color.WHITE);
			case 1:
				buttons[0].setBackground(Color.RED);
				buttons[0].setForeground(Color.WHITE);
				break;
			}

			switch (skillLevels.get(skill)) {
			case 5:
				buttons[4].setEnabled(false);
				break;
			case 4:
				buttons[3].setEnabled(false);
				buttons[4].setEnabled(true);
				break;
			case 3:
				buttons[2].setEnabled(false);
				buttons[3].setEnabled(true);
				break;
			case 2:
				buttons[1].setEnabled(false);
				buttons[2].setEnabled(true);
				break;
			}
		}
	}

}
