package Progetto;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Azione2 implements ActionListener {
	private JFrame frame;
	private JPanel finestra;
	private JPanel remove;
	private PanelTest res;
	private JButton reset;
	private JScrollPane scrollable;
	private JPanel pannello;
	
	public Azione2(JFrame f,JPanel fi,JPanel rem, PanelTest r, JButton rese, JScrollPane scr,JPanel pan) {
		frame=f;
		finestra=fi;
		remove=rem;
		res=r;
		reset=rese;
		scrollable=scr;
		pannello=pan;
	}
	
	
	public void actionPerformed(ActionEvent event) {
		frame.repaint();
		finestra.revalidate();
		finestra.repaint();
		finestra.setVisible(false);
		res.removeAll();
		res.repaint();
		finestra.remove(scrollable);
		finestra.remove(remove);		
		frame.add(pannello,BorderLayout.NORTH);
		finestra.setVisible(true);
		finestra.repaint();
	}
}
