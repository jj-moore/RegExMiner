package regexMiner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import regexMinerView.ViewMain;

public class Player {
	private String name;
	private int balance;
	private int level;
	private TreeSet<String> expressions;
	private TreeMap<Commodity, Integer> inventory;
	private HashMap<Commodity, Double> bonusChance; //not implemented;
	private HashMap<Skills, Integer> skillLevels;
	private int matchCount;
	private ViewMain view;

	Player(Commodity[] commodities, ViewMain view) {
		this.view = view;
		this.matchCount = 0;
		this.balance = 3000;
		this.level = 1;
		this.expressions = new TreeSet<>();
		this.inventory = new TreeMap<>();
		this.bonusChance = new HashMap<>();
		this.skillLevels = new HashMap<>();
		for (Skills skill : Skills.values()) 
			skillLevels.put(skill, 1);
		for (Commodity commodity : commodities) {
			inventory.put(commodity, 0);
			bonusChance.put(commodity, 0.0);
		}
		view.getViewPlayer().intializeInventory(inventory.keySet());
		view.getViewPlayer().updateViewPlayer(this);
		view.getViewLevelIntro().setLevelText(level);
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
		view.getViewPlayer().updateViewPlayer(this);
	}
	
	public void incMatchCount() {
		matchCount++;
	}
	
	public int getMatchCount() {
		return matchCount;
	}

	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public void levelUp() {
		level++;
		matchCount = 0;
		expressions.clear();
	}

	public int getBalance() {
		return balance;
	}

	public String getStringBalance() {
		return String.valueOf(balance);
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public void changeBalance(int amount) {
		balance += amount;
		view.getViewPlayer().setBalance(String.valueOf(balance));
	}

	public TreeSet<String> getExpressions() {
		return expressions;
	}

	public boolean addExpression(String expression) {
		for (String s : expressions) {
			if (expression.toUpperCase().equals(s.toUpperCase()))
				return false;
		}
		expressions.add(expression);
		return true;
	}
	
	public int getSkillLevel (Skills skill) {
		return skillLevels.get(skill);
	}
	
	public void setSkillLevel (Skills skill, int level) {
		skillLevels.put(skill, level);
	}
	
	public HashMap<Skills, Integer> getAllSkillLevels() {
		return this.skillLevels;
	}

	public int getInventory(Commodity commodityData) {
		return inventory.get(commodityData);
	}
	
	public TreeMap<Commodity, Integer> getAllInventory() {
		return inventory;
	}

	public int getInventory(String commodityName) {
		Commodity commodityData = Commodity.valueOf(commodityName);
		return inventory.get(commodityData);
	}

	public String getStringInventory(Commodity commodity) {
		return String.valueOf(inventory.get(commodity));
	}

	public void setInventory(Commodity commodity, int current) {
		inventory.replace(commodity, current);
	}

	public void changeInventory(Commodity commodity, int change) {
		int current = inventory.get(commodity) + change;
		inventory.replace(commodity, current);
	}
	
	public int getTotalInventory() {
		int totalInventory = 0;
		Set<Commodity> list = inventory.keySet();
		Iterator<Commodity> iterator = list.iterator();
		while (iterator.hasNext())
			totalInventory += inventory.get(iterator.next());
		return totalInventory;
	}

	public String toString() {
		StringBuilder value = new StringBuilder(name + "\nCredits: " + balance);

		Set<Commodity> list = inventory.keySet();
		Iterator<Commodity> iterator = list.iterator();
		while (iterator.hasNext()) {
			Commodity commodity = iterator.next();
			value.append("\n" + commodity.name() + ": " + inventory.get(commodity) + " units");
		}
		return value.toString();
	}
	
}
