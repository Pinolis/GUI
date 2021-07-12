import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
public class disegnoprova extends Canvas {
	private String[] Dinastie;
	public disegnoprova(String[] dinastie) {
		Color c = new Color(190,0,0);
		JFrame master =  new JFrame();
		JPanel finestra= new JPanel();
		JLabel testo= new JLabel("",SwingConstants.CENTER);
		Font font = new Font("SansSerif", Font.BOLD, 20);
		JLabel label = new JLabel("Seleziona la dinastia:                   >");
		JLabel creators= new JLabel("<html>&nbsp CREATORS: <br/><br/>&nbsp &nbsp -Andrea<br/>&nbsp &nbsp Di Franco <br/><br/>&nbsp &nbsp -Tobia<br/>&nbsp &nbsp Bacchiddu <br/><br/>&nbsp &nbsp -Federico<br/>&nbsp &nbsp Iannini <br/><br/>&nbsp &nbsp -Edoardo<br/>&nbsp &nbsp Luziatelli <br/><br/>&nbsp &nbsp -Alberto<br/>&nbsp &nbsp Guglielmotti &nbsp </html>" ); 
		JPanel pannello= new JPanel();
		JScrollPane pannelloCanvas= new JScrollPane();
		JComboBox lista= new JComboBox(dinastie);
		JButton reset = new JButton("cambia dinastia");
		JPanel remove = new JPanel();
		JPanel box = new JPanel();
		
		
		
		finestra.setLayout(new BorderLayout());
		
		
		pannello.setSize(1000,5);
		pannello.setBackground(c);
		pannello.setLayout(new BorderLayout());
		
		
		lista.setMaximumRowCount(6);
		lista.setForeground(c);
		lista.setSize(new Dimension(300,40));
		pannello.add(lista,BorderLayout.CENTER);
		

		master.add(pannello,BorderLayout.NORTH);
		
		testo.setBackground(c);
		testo.setForeground(Color.white);
		testo.setHorizontalAlignment(JLabel.CENTER);
		testo.setFont(font);
		testo.setOpaque(true);
		
		finestra.add(testo,BorderLayout.SOUTH);
		
		creators.setBackground(Color.black);
		creators.setForeground(Color.white);
		creators.setOpaque(true);
		
		finestra.add(creators, BorderLayout.WEST);
		
		
		finestra.setBackground(c);
		
		
		
		
		label.setSize(100,100);
		label.setForeground(Color.white);
		
		
		
		pannello.revalidate();
		pannello.add(label,BorderLayout.WEST);
		
		
		
		master.add(finestra);
		master.setTitle("SCRAPER project");
		master.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		master.setResizable(true);
		master.setVisible(true);
		master.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		reset.setBackground(Color.white);
		reset.setForeground(c);
		reset.setFont(font);
		
		lista.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				finestra.repaint();
				JComboBox lista = (JComboBox) event.getSource();
				Object selected = lista.getSelectedItem();
				String selected1= String.valueOf(selected);
				testo.setText(selected1);
				Imperatore prova = ScraperUser.getAlberoGenealogico(new Dinastia("", 1));
				ArrayList<Integer> counter = new ArrayList<Integer>();
				int generazioni = ContaGenerazioni.ricorsiva(prova, counter);
				//JLabel ngen = new JLabel(generazioni+" Generazioni  ");
				//ngen.setBackground(c);
				//ngen.setForeground(Color.black);
				//ngen.setOpaque(true);
				//ngen.setFont(font);
				//master.add(ngen,BorderLayout.EAST);
				CanvasCreator C = new CanvasCreator(prova,generazioni);
				C.setSize(C.getWidth(),C.getLength());
				C.setBackground(Color.white);
				pannelloCanvas.add(C);
				pannelloCanvas.setBackground(c);
				remove.setBackground(c);
				remove.setOpaque(true);
				remove.add(reset);
				master.remove(pannello);
				finestra.add(remove,BorderLayout.NORTH);
				finestra.add(pannelloCanvas);
				finestra.repaint();
			}
		});
		reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				master.add(pannello,BorderLayout.NORTH);
				finestra.setVisible(false);
				pannelloCanvas.removeAll();
				finestra.remove(pannelloCanvas);
				finestra.remove(remove);
				finestra.setVisible(true);
				finestra.repaint();
				
				
				
			}
		});
	}
		
		
	
	

	public static void main(String[] args) {
		String[] dinastie={"","dinastia2","dinastia3","dinastia4","dinastia5","dinastia6","dinastia7","dinastia8"};
		disegnoprova f= new disegnoprova(dinastie);

	}

}
