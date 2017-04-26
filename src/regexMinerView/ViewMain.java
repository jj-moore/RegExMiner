package regexMinerView;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

public class ViewMain extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private ViewTarget viewTarget;
	private ViewPlayer viewPlayer;
	private ViewTicker viewTicker;
	private ViewSkills viewSkills;
	private ViewIntro viewIntro;

	public ViewMain() {
		super("RegEx Miner");
		
		BorderLayout layout = new BorderLayout();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(layout);
		this.getContentPane().setBackground(MyComponent.burlywood);
		this.setSize(900, 420);
		
		viewTicker = new ViewTicker();
		viewPlayer = new ViewPlayer();
		viewTarget = new ViewTarget();
		viewSkills = new ViewSkills();
		viewIntro = new ViewIntro();
		viewSkills.setVisible(false);
		viewIntro.setVisible(false);
		JPanel overlay = setOverlay();

		this.add(viewPlayer, BorderLayout.WEST);
		this.add(overlay, BorderLayout.CENTER);
		this.add(viewTicker, BorderLayout.NORTH);
	}
	
	private JPanel setOverlay() {
		JPanel overlay = new JPanel();
		OverlayLayout layout = new OverlayLayout(overlay);
		overlay.setLayout(layout);
		overlay.setBackground(MyComponent.burlywood);
		overlay.add(viewTarget);
		overlay.add(viewSkills);
		overlay.add(viewIntro);
		return overlay;
	}
	
	public ViewTicker getViewTicker() {
		return this.viewTicker;
	}
	
	public ViewPlayer getViewPlayer() {
		return this.viewPlayer;
	}
	
	public ViewRegex getViewRegex() {
		return viewTarget.getViewRegex();
	}
	
	public ViewTarget getViewResults() {
		return this.viewTarget;
	}
	
	public ViewSkills getViewSkills() {
		return viewSkills;
	}
	
	public ViewIntro getViewLevelIntro() {
		return viewIntro;
	}
	
}
