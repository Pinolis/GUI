package Progetto;
import java.awt.*;
import javax.swing.*;
import java.awt.Canvas;
import java.awt.event.*;
public class disegnoprova extends Canvas {
	private String[] Dinastie;
	public disegnoprova(String[] dinastie) {
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
		finestra.setSize(600,400);
		finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		finestra.setResizable(true);
		JPanel pannello= new JPanel();
		pannello.add(label);
		JComboBox lista= new JComboBox(dinastie);
		lista.setPreferredSize(new Dimension(300,40));
		lista.setMaximumRowCount(6);
		lista.setForeground(Color.red);
		Color color= new Color(220,0,0);
		pannello.setBackground(color);
		pannello.add(lista,BorderLayout.EAST);
		finestra.add(pannello,BorderLayout.NORTH);
		finestra.setVisible(true);
		finestra.add(testo,BorderLayout.SOUTH);
		finestra.add(creators, BorderLayout.WEST);
		lista.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				JComboBox lista = (JComboBox) event.getSource();
				Object selected = lista.getSelectedItem();
				String selected1= String.valueOf(selected);
				testo.setText(selected1);
			}
		});
	
	}
	
	

	public static void main(String[] args) {
		String[] dinastie={"","dinastia2","dinastia3","dinastia4","dinastia5","dinastia6","dinastia7","dinastia8"};
		disegnoprova f= new disegnoprova(dinastie);

	}

}
