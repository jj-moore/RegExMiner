package regexMiner;

import java.util.ArrayList;

public class TargetGenerator {

	public static String setTarget(Player player) {
		StringBuilder groundBuilder = new StringBuilder();
		char newChar;
		String commodity;
		int[] targetSize = Skills.AREA.getValue(player.getSkillLevel(Skills.AREA));

		ArrayList<Character> disallowed = new ArrayList<>();
		Character[] disallowedList = { '<', '>' };
		for (Character c : disallowedList)
			disallowed.add(c);
		
		for (int chars = 0; chars < (targetSize[0] * targetSize[1]); chars++) {
			do {
				newChar = (char) (int) (Math.random() * 94 + 33);
			} while (disallowed.contains(newChar));
			groundBuilder.append(newChar);

			commodity = addCommodity(player, Math.random());
			if (commodity != null && chars < (targetSize[0] * targetSize[1] - commodity.length())) {
				groundBuilder.append(commodity);
				chars += commodity.length();
			}
		}
		return groundBuilder.toString();
	}

	private static String addCommodity(Player player, double random) {
		double placeholder = 0.0;
		for (Commodity commodity : player.getAllInventory().keySet()) {
			if (random >= placeholder && random < (placeholder + commodity.getChance()))
				return LevelData.getString(commodity, player.getLevel());
			placeholder += commodity.getChance();
		}
		return "";
	}

	public static String generateHTML(String target, int rows, int columns) {
		StringBuilder builder = new StringBuilder(target);
		int counter = 0;
		boolean tag = false;

		for (int index = builder.length() - 1; index >= 0; index--) {
			switch (builder.charAt(index)) {
			case '>':
				tag = true;
				break;
			case '<':
				tag = false;
				break;
			default:
				counter = (tag ? counter : counter + 1);
				if (rows != 1 && counter == columns) {
					builder.insert(index, "<br>");
					counter = 0;
					rows--;
				}
				break;
			}
		}
		builder.insert(0, "<html><body><div class='data'>");
		builder.append("</div></body></html>");
		return builder.toString();
	}
}
