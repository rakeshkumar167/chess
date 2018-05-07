package com.inkkpot.chess.ui;

import java.awt.*;
import javax.swing.*;

public class Splash extends JWindow {
	private static final long serialVersionUID = 1L;

	public Splash(String filename, Frame f, int waitTime)  {
		super(f);
		f.setAlwaysOnTop(true);
		JLabel label = new JLabel(new ImageIcon(getClass().getClassLoader().getResource(filename)));
		getContentPane().add(label, BorderLayout.CENTER);
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension labelSize = label.getPreferredSize();
		setLocation(screenSize.width / 2 - (labelSize.width / 2), screenSize.height / 2 - (labelSize.height / 2));
		final int pause = waitTime;
		setVisible(true);
		try {
			Thread.sleep(pause);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		setVisible(false);
		dispose();
	}

}