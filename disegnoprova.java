package Progetto;
import java.awt.*;
import javax.swing.*;
import java.awt.Canvas;
import java.awt.event.*;
public class Frame extends Canvas {
	
	private String[] Dinastie;
	
	
	public Frame(String[] dinastie) 
	{
		JFrame finestra= new JFrame();
		JTextField testo= new JTextField("");
		testo.setBackground(Color.red);
		testo.setForeground(Color.white);
		testo.setHorizontalAlignment(JTextField.CENTER);
		Font font = new Font("SansSerif", Font.BOLD, 20);
		testo.setFont(font);
		
		JLabel label = new JLabel("Seleziona la dinastia:                   >");
		JButton creators= new JButton("creators");
		//creators.setPreferredSize(new Dimension(70,20)); 
		label.setForeground(Color.white);
		
		finestra.setTitle("SCRAPER project");
		finestra.setSize(800,800);
		finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		finestra.setResizable(true);
		
		JPanel pannello= new JPanel();
		pannello.add(label);
		
		JComboBox lista= new JComboBox(dinastie);
		lista.setPreferredSize(new Dimension(300,40));
		lista.setMaximumRowCount(6);
		lista.setForeground(Color.red);
		
		Color color= new Color(220,0,0);
		
		finestra.add(new CanvasCreator(new Imperatore("ciao", "ciao")), BorderLayout.CENTER);
		pannello.setBackground(color);
		pannello.add(lista,BorderLayout.EAST);
		finestra.add(pannello,BorderLayout.NORTH);
		finestra.add(testo,BorderLayout.SOUTH);
		finestra.add(creators, BorderLayout.WEST);
		finestra.setVisible(true);
		
		lista.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent event) 
			{
				JComboBox lista = (JComboBox) event.getSource();
				Object selected = lista.getSelectedItem();
				String selected1= String.valueOf(selected);
				testo.setText(selected1);
			}
		});
	}
	public static void main(String[] args) {
		String[] dinastie={"dinastia1", "dinastia2","dinastia3","dinastia4","dinastia5","dinastia6","dinastia7","dinastia8"};
		Frame f= new Frame(dinastie);
	}
}
