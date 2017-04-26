package regexMiner;

import regexMinerView.ViewMain;
import regexMinerView.ViewStart;

public class Main {
	
	public static void main(String[] args) {
		Commodity[] startingCommodities = { Commodity.COAL, Commodity.IRON };
		ViewMain view = new ViewMain();
		Market market = new Market(startingCommodities, view);
		Player player = new Player(startingCommodities, view);
		Target textManager = new Target(startingCommodities, player, view);
		Controller controller = new Controller(market, player, textManager, view);
		view.getViewPlayer().setActionListeners(controller);
		view.getViewRegex().setActionListeners(controller);
		ViewStart start = new ViewStart(view, player);
		start.setVisible(true);
	}
	
}
