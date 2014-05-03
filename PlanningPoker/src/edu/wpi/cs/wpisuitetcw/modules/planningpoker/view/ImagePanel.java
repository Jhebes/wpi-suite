package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

class ImagePanel extends JPanel {
	private Image img;

	public ImagePanel() {
	}

	public ImagePanel(String img) throws IOException {
		// this.img = new ImageIcon(img).getImage();
		this.img = ImageIO.read(getClass().getResource("new_card.png"));
	}

	public ImagePanel(Image img) {
		this.img = img;
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
	}

	public void paintComponent(Graphics g) {
		// g.drawImage(img, 0, 0, null);
		g.drawImage(img, 0, 0, null);
	}
}