package ClassiUtili;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.*;

public class FrameTest {
	public FrameTest() {
		//CREAZIONE DEL FRAME
		JFrame frame = new JFrame("test");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//PROVA CON CANVAS CREATOR
		//frame.pack();
		//frame.setVisible(true);
		
		//PROVA CON FRAME GENERICO
		JPanel pannello= new PanelTest();
		//JScrollPane scrollPanel= new JScrollPane();
		//scrollPanel.setPreferredSize(new Dimension(1000, 1000));
		//scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//scrollPanel.add(pannello);
		frame.add(pannello);
		
		frame.pack();
		frame.setVisible(true);
	}
public static void main(String[] args) {
	FrameTest test= new FrameTest();
	
}

}
