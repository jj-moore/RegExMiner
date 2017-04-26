package regexMiner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import regexMinerView.ViewMain;

public class Controller implements ActionListener {
	Market market;
	Player player;
	Target target;
	ViewMain view;

	Controller(Market market, Player player, Target target, ViewMain view) {
		this.market = market;
		this.player = player;
		this.target = target;
		this.view = view;
		view.getViewSkills().initializeSkills(player.getAllSkillLevels(), this);
	}
	
	private void levelUp() {
		int[] area = Skills.AREA.getValue(player.getSkillLevel(Skills.AREA));
		player.levelUp();
		view.getViewPlayer().updateViewPlayer(player);
		target.setTargetString(TargetGenerator.setTarget(player));
		view.getViewRegex().setTargetHtml(TargetGenerator.generateHTML(target.getTargetString(), area[0], area[1]));
		view.getViewPlayer().updateViewButton("View Target Area");
		view.getViewRegex().setExpression("");
		view.getViewLevelIntro().setLevelText(player.getLevel());
		view.getViewResults().setVisible(false);
		view.getViewLevelIntro().setVisible(true);
		view.getViewResults().setExpressionsList(player);
		
	}

	public void actionPerformed(ActionEvent action) {
		Commodity commodity;
		String skillText;
		Skills skill;

		int[] scanCost = Skills.SCAN.getValue(player.getSkillLevel(Skills.SCAN));
		int[] digCost = Skills.DIG.getValue(player.getSkillLevel(Skills.DIG));
		int[] whseSize = Skills.WHSE.getValue(player.getSkillLevel(Skills.WHSE));
		int[] area = Skills.AREA.getValue(player.getSkillLevel(Skills.AREA));
		int[] sell = Skills.SELL.getValue(player.getSkillLevel(Skills.SELL));
		
		if (action.getActionCommand().endsWith("_LEVEL")) {
			skillText = action.getActionCommand().substring(0, action.getActionCommand().length() - 6);
			skill = Skills.valueOf(skillText);
			if (player.getBalance() > -skill.getCostToAdvance(player.getSkillLevel(skill))) {
				player.changeBalance(skill.getCostToAdvance(player.getSkillLevel(skill)));
				player.setSkillLevel(skill, player.getSkillLevel(skill) + 1);
			}
		}
		
		switch (action.getActionCommand()) {
		case "NEW_GROUND":
			if (player.getBalance() >= -scanCost[0]) {
				player.changeBalance(scanCost[0]);
				target.setTargetString(TargetGenerator.setTarget(player));
				view.getViewRegex().setTargetHtml(TargetGenerator.generateHTML(target.getTargetString(), area[0], area[1]));
				view.getViewRegex().setExpression("");
			} else {
				view.getViewRegex().setMessages("You don't have enough money!");
			}
			break;
		case "DIG":
			if (!LevelData.isValid(view.getViewRegex().getExpression(), player.getLevel()))
				view.getViewRegex().setMessages("Characters not allowed on this level");
			else if (!player.addExpression(view.getViewRegex().getExpression()))
				view.getViewRegex().setMessages("This expression already used.");
			else {

				if (player.getBalance() >= -digCost[0]) {
				
					player.changeBalance(digCost[0]);
					target.getResults(view.getViewRegex().getExpression(), target.getTargetString(), player.getLevel());
					view.getViewRegex().setTargetHtml(TargetGenerator.generateHTML(target.getTargetString(), area[0], area[1]));

					for (Commodity valid : target.getValidCommodities())
						if (player.getTotalInventory() < whseSize[0]) {
							player.changeInventory(valid, 1);
							player.incMatchCount();
						}
				
					view.getViewPlayer().updateViewPlayer(player);
					view.getViewRegex().setMessages(target.getMessages());
					view.getViewResults().setExpressionsList(player);
					view.getViewResults().setMatchesList(target.getValidMatches(), target.getInvalidMatches());
					
					if (LevelData.checkLevel(player))
						this.levelUp();
				} else {
					view.getViewRegex().setMessages("You don't have enough money!");
				}
			}
			break;
		case "VIEW":
			if (view.getViewResults().isVisible()) {
				view.getViewPlayer().updateViewButton("View Level Intro");
				view.getViewResults().setVisible(false);
				view.getViewSkills().setVisible(true);
			} else if (view.getViewSkills().isVisible()) {
				view.getViewPlayer().updateViewButton("View Target Area");
				view.getViewSkills().setVisible(false);
				view.getViewLevelIntro().setVisible(true);
			} else if (view.getViewLevelIntro().isVisible()) {
				view.getViewPlayer().updateViewButton("View Skills");
				view.getViewLevelIntro().setVisible(false);
				view.getViewResults().setVisible(true);
			}
			break;
		case "AREA_LEVEL":
				int[] newArea = Skills.AREA.getValue(player.getSkillLevel(Skills.AREA));
				view.getViewRegex().updateTargetSize(player);
				target.setTargetString(TargetGenerator.setTarget(player));
				String html = TargetGenerator.generateHTML(target.getTargetString(), newArea[0], newArea[1]);
				view.getViewRegex().setTargetHtml(html);
				view.getViewSkills().updateArea(player.getAllSkillLevels());
			break;
		case "SCAN_LEVEL":
			view.getViewRegex().updateButtonText(player);
			view.getViewSkills().updateArea(player.getAllSkillLevels());
			break;
		case "DIG_LEVEL":
			view.getViewRegex().updateButtonText(player);
			view.getViewSkills().updateArea(player.getAllSkillLevels());
			break;
		case "SELL_LEVEL":
			view.getViewSkills().updateArea(player.getAllSkillLevels());
			break;
		case "WHSE_LEVEL":
			view.getViewPlayer().updateViewPlayer(player);
			view.getViewSkills().updateArea(player.getAllSkillLevels());
			break;
		default:
			commodity = Commodity.valueOf(action.getActionCommand());
			if (player.getInventory(commodity) > 0) {
				this.player.changeInventory(commodity, -1);
				this.player.changeBalance(market.getPrice(commodity) + sell[0]);
				view.getViewPlayer().updateViewPlayer(player);
			}
			break;
		}
	}
	
}
