package regexMiner;

public class LevelData {

	public static String getString(Commodity commodity, int level) {
		char[] chars = commodity.name().toLowerCase().toCharArray();

		switch (level) {
		case 1:
			// c-al co-l coa- c--- c--l 
			for (int i = 1; i < chars.length; i++) {
				if (Math.random() < (double) (1.4 / chars.length))
					chars[i] = '-';
			}
			return String.valueOf(chars);
		case 2:
			// col co-l c-al cal c--l
			StringBuilder level2 = new StringBuilder();

			level2.append(chars[0]);
			for (int i = 1; i < chars.length - 1; i++) {
				double random = Math.random();
				if (random < (double) (0.8 / chars.length))
					level2.append("-");
				else if (random < (double) (1.5 / chars.length))
					;
				else
					level2.append(String.valueOf(chars[i]));
			}
			level2.append(chars[chars.length - 1]);

			return level2.toString();
		case 3:
			// cooooaaal cooaaal
			StringBuilder level3 = new StringBuilder();
			
			level3.append(chars[0]);
			for (int i = 1; i < chars.length - 1; i++) {
				int random = (int) (Math.random() * 5) + 1;
				for (int repeat = 0; repeat < random; repeat++)
					level3.append(chars[i]);
			}
			level3.append(chars[chars.length - 1]);
			return level3.toString();
		default:
			for (int i = 0; i < commodity.name().length(); i++) {
				chars[i] = '_';
			}
			return String.valueOf(chars);
		}
	}

	public static boolean isValid(String expression, int level) {
		switch (level) {
		case 1:
			// literals - .
			if (expression.matches("[-\\w.]+")) {
				return true;
			}
			return false;
		case 2:
			// literals - . ? 
			if (expression.matches("[-\\w.?]+")) {
				return true;
			}
			return false;
		case 3:
			// literals - . ? + 
			if (expression.matches("[-\\w.?+]+")) {
				return true;
			}
			return false;
		}
		return false;
	}

	public static String getAnswer(Commodity[] commodities, int level) {
		StringBuilder answer = new StringBuilder();

		switch (level) {
		case 1:
			// (?<COAL>c[-o][-a][-l])
			for (Commodity commodity : commodities) {
				answer.append("(?<" + commodity.name() + ">");
				char[] chars = commodity.name().toLowerCase().toCharArray();
				answer.append(chars[0]);
				for (int i = 1; i < chars.length; i++)
					answer.append("[-" + chars[i] + "]");
				answer.append(")|");
			}
			answer.deleteCharAt(answer.length() - 1);
			break;
		case 2:
			// (?<COAL>c(?:[-oa]|[-o][-a])l)
			for (Commodity commodity : commodities) {
				answer.append("(?<" + commodity.name() + ">");
				char[] chars = commodity.name().toLowerCase().toCharArray();
				answer.append(chars[0] + "(?:[-");
				for (int i = 1; i < chars.length - 1; i++)
					answer.append(chars[i]);
				answer.append("]|");
				for (int i = 1; i < chars.length - 1; i++)
					answer.append("[-" + chars[i] + "]");
				answer.append(")" + chars[chars.length - 1] + ")|");
			}
			answer.deleteCharAt(answer.length() - 1);
			break;
		case 3:
			// (?<COAL>co+a+l)
			for (Commodity commodity : commodities) {
				answer.append("(?<" + commodity.name() + ">");
				char[] chars = commodity.name().toLowerCase().toCharArray();
				answer.append(chars[0]);
				for (int i = 1; i < chars.length - 1; i++)
					answer.append(chars[i] + "+");
				answer.append(chars[chars.length - 1] + ")|");
			}
			answer.deleteCharAt(answer.length() - 1);
			break;
		}
		//System.out.println("LevelData.getAnswer: " + answer);
		return answer.toString();
	}

	public static boolean checkLevel(Player player) {
		int random;
		
		switch(player.getLevel()) {
		case 1:
			random = (int)(Math.random() * 8) + 6;
			if (player.getMatchCount() > random) 
				return true;
		case 2:
			random = (int)(Math.random() * 8) + 6;
			if (player.getExpressions().size() > random)
				return true;
		}
		return false;
	}
}
