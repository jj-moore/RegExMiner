package regexMinerView;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import regexMiner.Player;

public class ViewStart extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextField name;
	private JTextPane gameRules;
	private Player player;
	private ViewMain view;
	JButton intro;
	JButton story;
	JButton regex;
	JButton level1;

	public ViewStart(ViewMain view, Player player) {
		super("RegEx Miner");
		this.setLocation(200, 100);
		this.setSize(750, 400);
		this.view = view;
		this.player = player;
		this.getContentPane().setBackground(MyComponent.burlywood);
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		this.add(this.setLogo());
		this.add(this.setGameRules());
		this.add(this.enterName());
	}

	private JLabel setLogo() {
		JLabel logo = new JLabel("Regex Miner");
		
		Font font = new Font("Calibri", Font.BOLD, 36);
		logo.setFont(font);
		logo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		logo.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		return logo;
	}

	private Box setGameRules() {
		Box main = new Box(BoxLayout.LINE_AXIS);
		
		gameRules = new JTextPane();
		gameRules.setContentType("text/html");
		gameRules.setEditable(false);
		HTMLEditorKit kit = new HTMLEditorKit();
		StyleSheet styles = kit.getStyleSheet();
		styles.addRule(".intro { " + "font-family: Consolas; " + "font-size: 14; " + "background: #8B7355; "
				+ "color: #FAEBD7; " + "padding: 3px; " + "}");
		this.gameRules.setEditorKit(kit);
		
		
		Font font = new Font("Calibri", Font.BOLD, 14);
		//gameRules.setFont(font);
		//gameRules.setBackground(MyComponent.burlywood4);
		
		JScrollPane scrollRules = new JScrollPane(gameRules, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		main.add(this.getButtons());
		main.add(scrollRules);

		return main;

	}

	private JPanel enterName() {
		JPanel enterName = MyComponent.myJPanel(MyComponent.burlywood, 750, 200);
		
		JLabel nameLabel = new JLabel("What's your name?");
		this.name = new JTextField();
		this.name.setPreferredSize(new Dimension(100, 25));
		JButton start = MyComponent.myJButton("Start", "START", 100);
		start.addActionListener(this);

		enterName.add(nameLabel);
		enterName.add(name);
		enterName.add(start);

		return enterName;
	}

	public Box getButtons() {
		Box buttons = MyComponent.myBox(BoxLayout.PAGE_AXIS);
		
		intro = MyComponent.myJToggle("Game Introduction", true, "INTRO", 150);
		intro.addActionListener(this);
		story = MyComponent.myJToggle("Backstory", false, "STORY", 150);
		story.addActionListener(this);
		regex = MyComponent.myJToggle("RegEx Introduction", false, "REGEX", 150);
		regex.addActionListener(this);
		level1 = MyComponent.myJToggle("Level 1 Details", false, "LEVEL1", 150);
		level1.addActionListener(this);

		buttons.add(intro);
		buttons.add(Box.createVerticalGlue());
		buttons.add(story);
		buttons.add(Box.createVerticalGlue());
		buttons.add(regex);
		buttons.add(Box.createVerticalGlue());
		buttons.add(level1);
		intro.doClick();

		return buttons;
	}
	
	private void setButtonStatus(JButton button, boolean selected) {
		if (selected) {
			button.setBorder(BorderFactory.createLoweredBevelBorder());
			button.setBackground(MyComponent.gold4);
			button.setEnabled(false);
			
		} else {
			button.setBorder(BorderFactory.createRaisedBevelBorder());
			button.setBackground(MyComponent.gold2);
			button.setEnabled(true);
		}
	}

	public void actionPerformed(ActionEvent event) {
		StringBuilder text = new StringBuilder();
		boolean isIntro = false;
		File inputFile = new File("RegExMinerText.txt");
		String startTag = "";
		String endTag = "";

		switch (event.getActionCommand()) {
		case "INTRO":
			startTag = "<GameRules>";
			endTag = "</GameRules>";
			this.setButtonStatus(intro, true);
			this.setButtonStatus(story, false);
			this.setButtonStatus(regex, false);
			this.setButtonStatus(level1, false);
			break;
		case "STORY":
			startTag = "<Backstory>";
			endTag = "</Backstory>";
			this.setButtonStatus(intro, false);
			this.setButtonStatus(story, true);
			this.setButtonStatus(regex, false);
			this.setButtonStatus(level1, false);
			break;
		case "REGEX":
			startTag = "<RegExIntro>";
			endTag = "</RegExIntro>";
			this.setButtonStatus(intro, false);
			this.setButtonStatus(story, false);
			this.setButtonStatus(regex, true);
			this.setButtonStatus(level1, false);
			break;
		case "LEVEL1":
			startTag = "<Level1Intro>";
			endTag = "</Level1Intro>";
			this.setButtonStatus(intro, false);
			this.setButtonStatus(story, false);
			this.setButtonStatus(regex, false);
			this.setButtonStatus(level1, true);
			break;
		case "START":
			this.setVisible(false);
			player.setName(name.getText());
			view.setLocation(this.getLocation());
			view.setVisible(true);
			
			break;
		}

		if (!event.getActionCommand().equals("Start")) {
			try (Scanner input = new Scanner(inputFile)) {
				while (input.hasNextLine()) {
					String nextLine = input.nextLine();

					if (nextLine.equals(startTag))
						isIntro = true;
					else if (nextLine.equals(endTag))
						isIntro = false;
					else if (isIntro)
						text.append(nextLine);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			gameRules.setText(text.toString());
			gameRules.setCaretPosition(0);
		}
	}

}
