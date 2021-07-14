package Progetto;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Azione1 implements ActionListener {
	private JFrame frame;
	private JComboBox lista;
	private JPanel finestra;
	private JPanel remove;
	private PanelTest res;
	private JScrollPane scrollable;
	private JButton reset;
	private JLabel label;
	private JPanel pannello;
	private Color c;
	
	public Azione1(JFrame fr,JComboBox l,JPanel fi,JPanel rem,PanelTest r,JScrollPane scr,JButton rese,JLabel lab,JPanel pan,Color color) {
		frame=fr;
		lista=l;
		finestra=fi;
		remove=rem;
		res=r;
		scrollable=scr;
		reset=rese;
		label=lab;
		pannello=pan;
		c=color;
		
	}
	public void actionPerformed(ActionEvent event) {
		finestra.repaint();
		Dinastia selected = (Dinastia) lista.getSelectedItem();
		
		String selected1= String.valueOf(selected);
		
		label.setText(selected1);
		Imperatore prova = ScraperUser.getAlberoGenealogico(selected);
		
		ArrayList<Integer> counter = new ArrayList<Integer>();
		int generazioni = ContaGenerazioni.ric(prova, counter);
		res.removeAll();
		//master.add(ngen,BorderLayout.EAST);
		res.paintComponents(res.getGraphics());
		remove.setBackground(c);
		remove.setOpaque(true);
		remove.add(reset);
		frame.remove(pannello);
		finestra.add(remove,BorderLayout.NORTH);
		finestra.add(scrollable);
		finestra.repaint();
	}
	
}

