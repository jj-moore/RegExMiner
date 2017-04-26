package regexMinerView;

import java.awt.Dimension;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import regexMiner.Commodity;

public class ViewTicker extends JPanel {
	private static final long serialVersionUID = 1L;
	private JPanel marketPanel;
	private JLabel[] tickerCommodity;
	private JLabel[] tickerPrices;
	
	ViewTicker() {
		super(null);
		Dimension marketMin = new Dimension(800, 30);
		Dimension marketMax = new Dimension(840, 60);
		this.setBackground(MyComponent.burlywood);
		this.setMinimumSize(marketMin);
		this.setPreferredSize(marketMin);
		this.setMaximumSize(marketMax);

		marketPanel = new JPanel();
		marketPanel.setBackground(MyComponent.burlywood);
		this.add(marketPanel);
		marketPanel.setBounds(0, 0, 250, 25);
	}
	
	public void initializeTicker(Set<Commodity> commodities) {
		tickerCommodity = new JLabel[commodities.size()];
		tickerPrices = new JLabel[commodities.size()];
		Iterator<Commodity> iterator = commodities.iterator();
		int index = 0;
		while (iterator.hasNext()) {
			Commodity commodity = iterator.next();
			tickerCommodity[index] = new JLabel(commodity.name());
			tickerPrices[index] = new JLabel();
			tickerCommodity[index].setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
			tickerPrices[index].setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));
			marketPanel.add(tickerCommodity[index]);
			marketPanel.add(tickerPrices[index]);
			index++;
		}
	}

	public void setPrices(TreeMap<Commodity, Integer> market) {
		int price = 0;
		for (int index = 0; index < tickerPrices.length; index++) {
			price = market.get(Commodity.valueOf(tickerCommodity[index].getText()));
			tickerPrices[index].setText(String.valueOf(price));
		}
	}
	
	public void setTickerLocation(int location) {
		marketPanel.setLocation(location, marketPanel.getY());
	}
	
	public int getTickerLocation() {
		return marketPanel.getX();
	}
}
