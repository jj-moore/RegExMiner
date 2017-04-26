package regexMinerView;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import regexMiner.Controller;
import regexMiner.Player;
import regexMiner.Skills;
import regexMiner.Target;

public class ViewRegex extends Box {
	private static final long serialVersionUID = 1L;
	private JTextPane target;
	private JButton newScan;
	private JTextField expression;
	private JLabel messages;
	private JButton newDig;

	ViewRegex() {
		super(BoxLayout.PAGE_AXIS);
		this.setAlignmentY(TOP_ALIGNMENT);
		this.setAlignmentX(LEFT_ALIGNMENT);
		Dimension min = new Dimension(420, 420);
		Dimension max = new Dimension(440, 440);
		this.setMinimumSize(min);
		this.setPreferredSize(min);
		this.setMaximumSize(max);

		target = getAreaText();
		this.add(target);
		int minWidth = 250;

		newScan = MyComponent.myJButton("", "NEW_GROUND", minWidth);
		newScan.setAlignmentX(CENTER_ALIGNMENT);

		JLabel prompt = new JLabel("Enter matching algorithm:");
		prompt.setAlignmentX(CENTER_ALIGNMENT);

		expression = new JTextField();
		expression.setMinimumSize(new Dimension(minWidth, 30));
		expression.setMaximumSize(new Dimension(minWidth, 30));

		messages = new JLabel();
		messages.setAlignmentX(CENTER_ALIGNMENT);
		messages.setMinimumSize(new Dimension(minWidth, 50));
		messages.setPreferredSize(new Dimension(minWidth, 60));
		messages.setMaximumSize(new Dimension(minWidth, 120));
		messages.setVerticalAlignment(JLabel.TOP);

		newDig = MyComponent.myJButton("", "DIG", minWidth);
		newDig.setAlignmentX(CENTER_ALIGNMENT);

		Dimension minFiller = new Dimension(5, 5);
		Dimension maxFiller = new Dimension(5, 20);
		this.add(new Box.Filler(minFiller, minFiller, maxFiller));
		this.add(newScan);
		this.add(new Box.Filler(minFiller, minFiller, maxFiller));
		this.add(prompt);
		this.add(expression);
		this.add(messages);
		this.add(newDig);
		this.add(new Box.Filler(minFiller, minFiller, maxFiller));
	}

	private JTextPane getAreaText() {
		target = new JTextPane();
		target.setBackground(MyComponent.green3);
		target.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		target.setContentType("text/html");
		target.setEditable(false);
		HTMLEditorKit kit = new HTMLEditorKit();
		StyleSheet styles = kit.getStyleSheet();
		styles.addRule(".data { " + "font-family: Consolas; " + "font-size: 14; " + "background: #00CD00; "
				+ "text-align: center; " + "} .match { " + "background-color: #00FFFF; " + "}");
		target.setEditorKit(kit);
		return target;
	}
	
	public void setActionListeners(Controller controller) {
		newScan.addActionListener(controller);
		newDig.addActionListener(controller);
	}
	
	public void setKeyAdapter(Target targetManager) {
		expression.addKeyListener(targetManager);
	}

	public void setTargetHtml(String targetHtml) {
		target.setText(targetHtml);
	}

	public void updateTargetSize(Player player) {
		int[] targetSize = Skills.AREA.getValue(player.getSkillLevel(Skills.AREA));
		Dimension targetDim = new Dimension((int)(targetSize[1] * 8.4), (targetSize[0] * 18));
		target.setMinimumSize(targetDim);
		target.setPreferredSize(targetDim);
		target.setMaximumSize(targetDim);
	}

	public String getExpression() {
		return this.expression.getText();
	}

	public void setExpression(String expression) {
		this.expression.setText(expression);
	}

	public void updateButtonText(Player player) {
		int[] scanCost = Skills.SCAN.getValue(player.getSkillLevel(Skills.SCAN));
		int[] digCost = Skills.DIG.getValue(player.getSkillLevel(Skills.DIG));
		
		newScan.setText("Scan new area (" + -scanCost[0] + " dollars)");
		newDig.setText("Dig (" + -digCost[0] + " dollars)");
	}

	public void setMessages(ArrayList<String> messages) {
		StringBuilder messageBuilder = new StringBuilder("<html>");
		for (String s : messages)
			messageBuilder.append(s + "<br>");
		messageBuilder.append("</html>");
		this.messages.setText(messageBuilder.toString());
	}

	public void setMessages(String string) {
		this.messages.setText(string);
	}

}
