package regexMinerView;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

public class ViewIntro extends Box {
	private static final long serialVersionUID = 1L;
	private JTextPane levelText;
	
	ViewIntro() {
		super(BoxLayout.PAGE_AXIS);
		this.setAlignmentY(TOP_ALIGNMENT);
		this.setAlignmentX(RIGHT_ALIGNMENT);
		Dimension min = new Dimension(720, 420);
		Dimension max = new Dimension(840, 440);
		this.setMinimumSize(min);
		this.setPreferredSize(min);
		this.setMaximumSize(max);

		levelText = new JTextPane();
		levelText.setBackground(MyComponent.burlywood4);
		levelText.setContentType("text/html");
		levelText.setEditable(false);
		HTMLEditorKit kit = new HTMLEditorKit();
		StyleSheet styles = kit.getStyleSheet();
		styles.addRule(".level2 { " + "font-family: Consolas; " + "font-size: 14; " + "background: #8B7355; "
				+ "color: #FAEBD7; " + "padding: 3px; " + "}");
		levelText.setEditorKit(kit);

		JScrollPane scroll = new JScrollPane(levelText, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBackground(MyComponent.burlywood);
		Border inside = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
		Border outside = BorderFactory.createEmptyBorder(10, 40, 10, 10);
		scroll.setBorder(BorderFactory.createCompoundBorder(outside, inside));
		this.add(scroll);
	}
	
	public void setLevelText(int level) {
		StringBuilder text = new StringBuilder();
		boolean target = false;
		File inputFile = new File("RegExMinerText.txt");
		String startTag = "<Level" + (level) + "Intro>";
		String endTag = "</Level" + (level) + "Intro>";

		try (Scanner input = new Scanner(inputFile)) {
			while (input.hasNextLine()) {
				String nextLine = input.nextLine();
				if (nextLine.equals(startTag))
					target = true;
				else if (nextLine.equals(endTag))
					target = false;
				else if (target)
					text.append(nextLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		levelText.setText(text.toString());
		levelText.setCaretPosition(0);
	}
	
	
}
