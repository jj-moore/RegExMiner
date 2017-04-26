package regexMinerView;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import regexMiner.Player;

public class ViewTarget extends Box {
	private static final long serialVersionUID = 1L;
	private ViewRegex targetArea;
	private JLabel matchesList;
	private JLabel expressionsList;

	ViewTarget() {
		super(BoxLayout.LINE_AXIS);
		this.setAlignmentY(TOP_ALIGNMENT);
		this.setAlignmentX(RIGHT_ALIGNMENT);
		Dimension min = new Dimension(720, 420);
		Dimension max = new Dimension(760, 440);
		this.setMinimumSize(min);
		this.setPreferredSize(min);
		this.setMaximumSize(max);
		
		targetArea = new ViewRegex();
		Dimension scrollMin = new Dimension(130, 420);
		Dimension scrollMax = new Dimension(140, 440);
		Dimension filler = new Dimension(5, 5);
		this.add(targetArea);
		this.add(new Box.Filler(filler, filler, filler));
		this.add(eastSub1(scrollMin, scrollMax));
		this.add(new Box.Filler(filler, filler, filler));
		this.add(eastSub2(scrollMin, scrollMax));
		this.add(new Box.Filler(filler, filler, filler));
		this.add(Box.createHorizontalGlue());
	}
	
	public ViewRegex getViewRegex() {
		return targetArea;
	}

	private JScrollPane eastSub1(Dimension min, Dimension max) {
		matchesList = new JLabel();
		Border border = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
		matchesList.setBorder(BorderFactory.createTitledBorder(border, "Dig Results"));
		matchesList.setVerticalAlignment(JLabel.TOP);
		matchesList.setForeground(Color.WHITE);

		JScrollPane matchesScroll = new JScrollPane(this.matchesList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		matchesScroll.getViewport().setBackground(MyComponent.burlywood3);
		matchesScroll.setAlignmentY(TOP_ALIGNMENT);
		matchesScroll.setMinimumSize(min);
		matchesScroll.setPreferredSize(min);
		matchesScroll.setMaximumSize(max);
		matchesScroll.setBackground(MyComponent.burlywood);
		matchesScroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		return matchesScroll;
	}

	private JScrollPane eastSub2(Dimension min, Dimension max) {
		this.expressionsList = new JLabel();
		Border border = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
		this.expressionsList.setBorder(BorderFactory.createTitledBorder(border, "Expressions"));
		this.expressionsList.setVerticalAlignment(JLabel.TOP);
		this.expressionsList.setForeground(Color.WHITE);

		JScrollPane expressionsScroll = new JScrollPane(this.expressionsList,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		expressionsScroll.getViewport().setBackground(MyComponent.burlywood3);
		expressionsScroll.setAlignmentY(TOP_ALIGNMENT);
		expressionsScroll.setMinimumSize(min);
		expressionsScroll.setPreferredSize(min);
		expressionsScroll.setMaximumSize(max);
		expressionsScroll.setBackground(MyComponent.burlywood);
		expressionsScroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		return expressionsScroll;
	}

	public void setMatchesList(ArrayList<String> validMatches, ArrayList<String> invalidMatches) {
		StringBuilder resultList = new StringBuilder("<html><u><b>Valid Matches</b></u><br>");
		for (String s : validMatches)
			resultList.append(s + "<br>");
		resultList.append("<br><u><b>Junk</b></u><br>");
		for (String s : invalidMatches)
			resultList.append(s + "<br>");
		resultList.append("</html>");
		this.matchesList.setText(resultList.toString());
	}

	public void setExpressionsList(Player player) {
		StringBuilder expressionList = new StringBuilder("<html>");
		for (String s : player.getExpressions())
			expressionList.append(s + "<br>");
		expressionList.append("</html>");
		this.expressionsList.setText(expressionList.toString());
	}
}
