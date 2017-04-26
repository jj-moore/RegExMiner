package regexMiner;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import regexMinerView.ViewMain;

public class Target extends KeyAdapter {
	private ViewMain view;
	private Player player;
	private ArrayList<String> totalMatches;
	private ArrayList<String> validMatches;
	private ArrayList<String> invalidMatches;
	private ArrayList<Commodity> validCommodities;
	private ArrayList<String> messages;
	private Commodity[] commodities;
	private String targetString;

	Target(Commodity[] commodities, Player player, ViewMain view) {
		this.commodities = commodities;
		this.totalMatches = new ArrayList<>();
		this.validMatches = new ArrayList<>();
		this.invalidMatches = new ArrayList<>();
		this.validCommodities = new ArrayList<>();
		this.messages = new ArrayList<>();
		this.player = player;
		this.view = view;
		targetString = TargetGenerator.setTarget(player);
		view.getViewRegex().setKeyAdapter(this);
		int[] targetSize = Skills.AREA.getValue(player.getSkillLevel(Skills.AREA));
		view.getViewRegex().setTargetHtml(TargetGenerator.generateHTML(targetString, targetSize[0], targetSize[1]));
		view.getViewRegex().updateButtonText(player);
		view.getViewRegex().updateTargetSize(player);
	}
	
	public String getTargetString() {
		return targetString;
	}
	
	public void setTargetString(String targetString) {
		this.targetString = targetString;
	}

	public ArrayList<String> getValidMatches() {
		return validMatches;
	}

	public ArrayList<String> getInvalidMatches() {
		return invalidMatches;
	}

	public ArrayList<String> getMessages() {
		return messages;
	}

	public void addMessage(String message) {
		messages.add(message);
	}

	public ArrayList<Commodity> getValidCommodities() {
		return validCommodities;
	}

	public void getResults(String expression, String target, int level) {
		this.totalMatches.clear();
		this.validMatches.clear();
		this.invalidMatches.clear();
		this.validCommodities.clear();

		this.targetString = getTotalMatches(expression, target);
		boolean pure = this.getValidMatches(level);

		this.messages.clear();
		this.getExecutionTime(expression, target);
		this.setMessage(pure);
		
		view.getViewResults().setMatchesList(validMatches, invalidMatches);
		view.getViewResults().setExpressionsList(player);
	}

	private String getTotalMatches(String expression, String target) {
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(target);
		StringBuilder builder = new StringBuilder(target);

		while (matcher.find()) {
			totalMatches.add(matcher.group(0));
			for (int groupIndex = 1; groupIndex <= matcher.groupCount(); groupIndex++)
				if (matcher.group(groupIndex) != null && !matcher.group(groupIndex).equals(matcher.group(0)))
					totalMatches.add(matcher.group(groupIndex));

			for (int index = matcher.start(0); index < matcher.end(0); index++)
				builder.replace(index, index + 1, "_");
		}
		return builder.toString();
	}

	private boolean getValidMatches(int level) {
		Pattern pattern = Pattern.compile(LevelData.getAnswer(commodities, level));
		Matcher matcher = pattern.matcher(" ");
		boolean pure = true;

		for (String match : totalMatches) {
			matcher = matcher.reset(match);
			if (matcher.find() && matcher.group(0).equals(match)) {
				validMatches.add(match);
				for (Commodity commodity : commodities)
					if (matcher.group(commodity.name()) != null)
						validCommodities.add(commodity);
			} else {
				invalidMatches.add(match);
				pure = false;
			}
		}
		return pure;
	}

	private void getExecutionTime(String expression, String target) {
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(target);

		long timer = 0;
		long start = System.nanoTime();
		while (matcher.find()) {
		}
		long stop = System.nanoTime();
		timer = stop - start;
		matcher.reset();

		messages.add("Execution time: " + ((int) (timer / 100)) + " microseconds");
	}

	private void setMessage(boolean pure) {
		int valid = validCommodities.size();
		if (pure || valid == 0) {
			messages.add("Total matches: " + totalMatches.size());
			messages.add("Valid matches: " + validMatches.size());
		} else {
			validCommodities.clear();
			messages.add("Oh no! Your results have become polluted!");
			if (valid == 1)
				messages.add(valid + " match lost!");
			else
				messages.add(valid + " matches lost!");
		}
	}

	public void keyReleased(KeyEvent event) {
		String expression = view.getViewRegex().getExpression();
		String targetHtml;
		int[] targetSize = Skills.AREA.getValue(player.getSkillLevel(Skills.AREA));

		StringBuilder htmlBuilder = new StringBuilder(targetString);
		if (expression.isEmpty()) {
			targetHtml = TargetGenerator.generateHTML(targetString, targetSize[0], targetSize[1]);
		} else {

			try {
				Pattern pattern = Pattern.compile(expression);
				htmlBuilder = markMatches(pattern, htmlBuilder, 0);
			} catch (PatternSyntaxException e) {
				messages.clear();
				String[] errorText = e.toString().split("[:\n]");
				StringBuilder errorBuilder = new StringBuilder();
				for (int i = 1; i < errorText.length - 1; i++)
					errorBuilder.append(errorText[i] + " ");
				messages.add(errorBuilder.toString().trim());
				// System.out.println(e.toString());
			}

			targetHtml = TargetGenerator.generateHTML(htmlBuilder.toString(), targetSize[0], targetSize[1]);
		}
		view.getViewRegex().setTargetHtml(targetHtml);
	}

	private static StringBuilder markMatches(Pattern pattern, StringBuilder string, int index) {
		Matcher match = pattern.matcher(string.toString());
		if (match.find(index)) {
			if (match.group().isEmpty())
				return string;
			string.insert(match.end(), "</span>");
			string.insert(match.start(), "<span class='match'>");
			index = match.end() + 20 + 7;
			markMatches(pattern, string, index);
		}
		return string;
	}

}
