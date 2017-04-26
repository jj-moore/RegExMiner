package regexMinerView;

import java.awt.Dimension;
import java.util.Iterator;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import regexMiner.Commodity;
import regexMiner.Controller;
import regexMiner.Player;
import regexMiner.Skills;

public class ViewPlayer extends Box {
	private static final long serialVersionUID = 1L;
	private JLabel lblName;
	private JLabel lblLevel;
	private JLabel lblBalance;
	private JLabel lblTotalInventory;
	private JButton btnView;
	private JLabel[] lblCommodities;
	private JButton[] btnCommodities;
	private int width;
	
	ViewPlayer() {
		super(BoxLayout.PAGE_AXIS);
		this.setAlignmentY(Box.TOP_ALIGNMENT);
		width = 180;
		Dimension dim = new Dimension(width, 420);
		this.setMinimumSize(dim);
		this.setPreferredSize(dim);
		this.setMaximumSize(dim);

		Dimension subMinMax = new Dimension(width, 25);
		this.add(panelName(subMinMax));
		this.add(panelLevel(subMinMax));
		this.add(panelBalance(subMinMax));
		this.add(panelTotalInventory(subMinMax));
		this.add(panelView(new Dimension(width, 35)));
		this.add(Box.createRigidArea(subMinMax));
		this.add(panelInventoryTitle(subMinMax));
	}

	private JPanel panelName(Dimension dim) {
		SpringLayout layout = new SpringLayout();
		JPanel panelName = MyComponent.myJPanel(layout, MyComponent.burlywood, dim.width, dim.height);

		lblName = new JLabel();
		panelName.add(new JLabel("Player:"));
		panelName.add(lblName);
		layout.putConstraint(SpringLayout.WEST, lblName, 85, SpringLayout.WEST, panelName);
		return panelName;
	}

	private JPanel panelBalance(Dimension dim) {
		SpringLayout layout = new SpringLayout();
		JPanel panelBalance = MyComponent.myJPanel(layout, MyComponent.burlywood, dim.width, dim.height);

		lblBalance = new JLabel();
		panelBalance.add(new JLabel("Balance:"));
		panelBalance.add(lblBalance);
		layout.putConstraint(SpringLayout.WEST, lblBalance, 85, SpringLayout.WEST, panelBalance);
		return panelBalance;
	}	
	
	private JPanel panelLevel(Dimension dim) {
		SpringLayout layout = new SpringLayout();
		JPanel panelLevel = MyComponent.myJPanel(layout, MyComponent.burlywood, dim.width, dim.height);

		lblLevel = new JLabel();
		panelLevel.add(new JLabel("Level:"));
		panelLevel.add(lblLevel);
		layout.putConstraint(SpringLayout.WEST, lblLevel, 85, SpringLayout.WEST, panelLevel);
		return panelLevel;
	}

	private JPanel panelTotalInventory(Dimension dim) {
		SpringLayout layout = new SpringLayout();
		JPanel panelTotalInventory = MyComponent.myJPanel(layout, MyComponent.burlywood, dim.width, dim.height);

		lblTotalInventory = new JLabel();
		panelTotalInventory.add(new JLabel("Inventory:"));
		panelTotalInventory.add(lblTotalInventory);
		layout.putConstraint(SpringLayout.WEST, lblTotalInventory, 85, SpringLayout.WEST, panelTotalInventory);
		return panelTotalInventory;
	}
	
	private JPanel panelView(Dimension dim) {
		SpringLayout layout = new SpringLayout();
		JPanel panelView = MyComponent.myJPanel(layout, MyComponent.burlywood, dim.width, dim.height);

		btnView = MyComponent.myJButton("View Skills", "VIEW", 165);
		btnView.setToolTipText("Click to change views");
		panelView.add(btnView);
		layout.putConstraint(SpringLayout.WEST, btnView, 0, SpringLayout.WEST, panelView);
		return panelView;
	}
	
	private JPanel panelInventoryTitle(Dimension dim) {
		SpringLayout layout = new SpringLayout();
		JPanel panelInventoryTitle = MyComponent.myJPanel(layout, MyComponent.burlywood, dim.width, dim.height);

		JLabel title = new JLabel("<html><u><b>Inventory</b></u>");
		panelInventoryTitle.add(new JLabel("<html><u><b>Commodity</b></u>"));
		panelInventoryTitle.add(title);
		layout.putConstraint(SpringLayout.WEST, title, 85, SpringLayout.WEST, panelInventoryTitle);
		return panelInventoryTitle;
	}
	
	public void intializeInventory(Set<Commodity> commodities) {
		lblCommodities = new JLabel[commodities.size()];
		btnCommodities = new JButton[commodities.size()];
		Iterator<Commodity> iterator = commodities.iterator();
		Dimension min = new Dimension(5, 5);
		int count = 0;
		while (iterator.hasNext()) {
			Commodity commodity = iterator.next();
			this.add(panelCommodities(commodity, count++));
			this.add(new Box.Filler(min, min, min));
		}
	}

	private JPanel panelCommodities(Commodity commodity, int count) {
		Dimension dim = new Dimension(width, 35);
		SpringLayout layout = new SpringLayout();
		JPanel panelCommodities = MyComponent.myJPanel(layout, MyComponent.burlywood, dim.width, dim.height);

		lblCommodities[count] = new JLabel(commodity.name() + ":");
		btnCommodities[count] = MyComponent.myJButton("0", commodity.name(), 80);
		btnCommodities[count].setToolTipText("Click to sell a unit");

		panelCommodities.add(lblCommodities[count]);
		panelCommodities.add(btnCommodities[count]);
		layout.putConstraint(SpringLayout.WEST, btnCommodities[count], 85, SpringLayout.WEST, panelCommodities);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, lblCommodities[count], 0, SpringLayout.VERTICAL_CENTER,
				btnCommodities[count]);
		return panelCommodities;
	}
	
	public void setActionListeners(Controller controller) {
		btnView.addActionListener(controller);
		for (JButton button : btnCommodities) {
			button.addActionListener(controller);
		}
	}
	
	public void updateViewButton(String label) {
		btnView.setText(label);
	}

	public void updateViewPlayer(Player player) {
		lblName.setText(player.getName());
		lblLevel.setText(String.valueOf(player.getLevel()));
		lblBalance.setText(player.getStringBalance());
		int[] whseLevel = Skills.WHSE.getValue(player.getSkillLevel(Skills.WHSE));
		String whseSize = String.valueOf(whseLevel[0]);
		lblTotalInventory.setText("<html><b>" + String.valueOf(player.getTotalInventory()) + " / " + whseSize + "</b>");

		for (int i = 0; i < btnCommodities.length; i++) {
			String label = lblCommodities[i].getText().substring(0, lblCommodities[i].getText().length() - 1);
			Commodity commodity = Commodity.valueOf(label);
			btnCommodities[i].setText(player.getStringInventory(commodity));
		}
	}
	
	public void setBalance(String balance) {
		lblBalance.setText(balance);
	}

}
