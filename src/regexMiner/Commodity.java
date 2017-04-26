package regexMiner;

public enum Commodity {
	COAL(0.0137F, 50), IRON(0.00788F, 80), SILVER(0.0015F, 150), GOLD(0.0005F, 300);
	private float chance;
	private int startPrice;
	private int minPrice;
	private int maxPrice;

	Commodity(float chance, int startPrice) {
		this.chance = chance;
		this.startPrice = startPrice;
		this.minPrice = (int) (startPrice * 0.6);
		this.maxPrice = (int) (startPrice * 1.4);
	}

	public float getChance() {
		return chance;
	}

	public int getStartPrice() {
		return startPrice;
	}

	public int getMinPrice() {
		return minPrice;
	}

	public int getMaxPrice() {
		return maxPrice;
	}

}
