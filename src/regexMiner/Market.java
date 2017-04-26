package regexMiner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.Timer;

import regexMinerView.ViewMain;

public class Market extends Thread implements ActionListener {
	private TreeMap<Commodity, Integer> market;
	private HashMap<Commodity, Integer> focus;
	private ViewMain view;

	Market(Commodity[] validCommodities, ViewMain view) {
		this.view = view;
		this.market = new TreeMap<>();
		this.focus = new HashMap<>();
		for (Commodity commodity : validCommodities) {
			this.market.put(commodity, commodity.getStartPrice());
			this.focus.put(commodity, commodity.getStartPrice());
		}
		view.getViewTicker().initializeTicker(market.keySet());

		Timer ticker = new Timer(0, this);
		ticker.setActionCommand("TICKER");
		ticker.setDelay(30);
		ticker.start();

		Timer prices = new Timer(0, this);
		prices.setActionCommand("PRICES");
		prices.setDelay(1000);
		prices.start();

		this.start();
	}

	public Set<Commodity> getMarketKeySet() {
		return market.keySet();
	}

	public int getPrice(Commodity commodity) {
		return this.market.get(commodity);
	}

	public String getStringPrice(Commodity commodity) {
		return String.valueOf(this.market.get(commodity));
	}

	public boolean setPrice(Commodity commodity, int price) {
		if (this.market.containsKey(commodity)) {
			this.market.put(commodity, price);
			return true;
		}
		return false;
	}

	public void actionPerformed(ActionEvent action) {
		switch (action.getActionCommand()) {
		case "TICKER":
			view.getViewTicker().setTickerLocation(view.getViewTicker().getTickerLocation() + 2);
			if (view.getViewTicker().getTickerLocation() > view.getWidth())
				view.getViewTicker().setTickerLocation(0);
			break;
		case "PRICES":
			view.getViewTicker().setPrices(market);
			break;
		}
	}

	private void updatePrices() {
		int currentPrice = 0;
		int newPrice = 0;
		double percentFromFoci;
		double chanceUp = 0.0;
		double chanceDown = 0.0;
		double chance;
		double random;
		boolean aboveFoci;

		Set<Commodity> commoditySet = market.keySet();
		Iterator<Commodity> iterator = commoditySet.iterator();
		while (iterator.hasNext()) {
			Commodity commodity = iterator.next();
			currentPrice = market.get(commodity);

			// determine relative position of current price
			if (currentPrice - focus.get(commodity) >= 0) {
				percentFromFoci = (double) (Math.abs(focus.get(commodity) - currentPrice))
						/ (commodity.getMaxPrice() - focus.get(commodity));
				aboveFoci = true;
			} else {
				percentFromFoci = (double) (Math.abs(focus.get(commodity) - currentPrice))
						/ (focus.get(commodity) - commodity.getMinPrice());
				aboveFoci = false;
			}

			// set chance of heading towards foci
			if (percentFromFoci < 0.25 && percentFromFoci >= 0)
				chance = 0.60;
			else if (percentFromFoci < 0.50 && percentFromFoci >= 0.25)
				chance = 0.65;
			else if (percentFromFoci < 0.75 && percentFromFoci >= 0.50)
				chance = 0.70;
			else if (percentFromFoci < 1.00 && percentFromFoci >= 0.75)
				chance = 0.75;
			else
				chance = 0.80;

			// set majority direction
			if (aboveFoci) {
				chanceUp = 0.8 - chance;
				chanceDown = chance;
			} else {
				chanceUp = chance;
				chanceDown = 0.8 - chance;
			}

			// set price
			random = Math.random();
			if (random < chanceDown)
				newPrice = --currentPrice;
			else if (random > (1 - chanceUp))
				newPrice = ++currentPrice;
			else
				newPrice = currentPrice;

			// record price
			market.put(commodity, newPrice);
		}
	}

	public void run() {
		int numOfTurns;
		double fociPercentAboveMin = 0.0;
		int foci = 0;

		while (true) {
			numOfTurns = (int) (Math.random() * 40 + 20);
			for (int turns = 0; turns < numOfTurns; turns++) {

				// determine foci
				if (turns == 0) {
					Set<Commodity> commoditySet = focus.keySet();
					Iterator<Commodity> iterator = commoditySet.iterator();
					while (iterator.hasNext()) {
						Commodity commodity = iterator.next();
						fociPercentAboveMin = Math.random();
						foci = (int) (fociPercentAboveMin * (commodity.getMaxPrice() - commodity.getMinPrice()))
								+ commodity.getMinPrice();
						focus.put(commodity, foci);
					}
				}

				try {
					Thread.sleep(1000);
					this.updatePrices();
				} catch (InterruptedException e) {
				}
			}
		}
	}

}
