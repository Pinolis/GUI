package Progetto;


/*            ___       
              \\||      
             ,'_,-\     
             ;'____\    
             || =\=|    
             ||  - |                               
         ,---'._--''-,,---------.--.----_,         JAVA GUI - SCRAPER PROJECT.
        / `-._- _--/,,|  ___,,--'--'._<            __________________________
       /-._,  `-.__;,,|'                           
      /   ;\      / , ;                            
     /  ,' | _ - ',/, ;
    (  (   |     /, ,,;
     \  \  |     ',,/,;
      \  \ |    /, / ,;
      (| ,^|   / ,, ,/;
      `-'./ `-._,, ,/,;
           |-._ `-._,,;
           |/,,`-._ `-.
           |, ,;, ,`-._\
*/
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class disegnoprova {
	
	public disegnoprova() {
		
//__________________________________________________________________________________________________________________
		//inizializzazione oggetti presenti nel frame:

		Color c = new Color(190,0,0); //colore bordeaux per la gui
		JFrame master =  new JFrame(); //frame principale
		JPanel finestra= new JPanel(); //pannello principale nel frame
		JLabel testo= new JLabel("",SwingConstants.CENTER); //linea di testo in basso nel frame
		Font font = new Font("SansSerif", Font.BOLD, 20); //creazione di font usato
		JLabel label = new JLabel("<html>&nbsp &nbsp &nbsp &emsp &emsp &emsp &emsp &emsp &emsp &emsp &emsp &emsp &emsp &emsp &emsp &emsp &emsp &emsp &emsp &emsp &emsp Seleziona la dinastia: &emsp &emsp>> </html>"); //inizializzazione testo barra di selezione in alto
		JLabel creators= new JLabel("<html>&nbsp CREATORS: <br/><br/>&nbsp &nbsp -Andrea<br/>&nbsp &nbsp Di Franco <br/><br/>&nbsp &nbsp -Tobia<br/>&nbsp &nbsp Bacchiddu <br/><br/>&nbsp &nbsp -Federico<br/>&nbsp &nbsp Iannini <br/><br/>&nbsp &nbsp -Edoardo<br/>&nbsp &nbsp Luziatelli <br/><br/>&nbsp &nbsp -Alberto<br/>&nbsp &nbsp Guglielmotti &nbsp </html>" ); 
		JPanel pannello= new JPanel(); 
		JComboBox lista= new JComboBox(Dinastia.DINASTIE_IMPERATORI_ROMANI); //creazione menu a tendina
		JButton reset = new JButton("cambia dinastia"); //tasto per il reset e cambio dinastia
		JPanel remove = new JPanel(); //pannello del tasto reset
		PanelTest res= new PanelTest(); //pannello che contiene l'albero
		JScrollPane scrollable = new JScrollPane(res); //pannello scrollable che contiene il pannello contenente l'albero
		
		
		
// ________________________________________________________________________________________________________________
		//inizio set dei vari componenti
		finestra.setLayout(new BorderLayout()); 
		pannello.setSize(1000,5);
		pannello.setBackground(c);
		pannello.setLayout(new BorderLayout()); //per il posizionamento uso il borderlayout
		lista.setMaximumRowCount(6);
		lista.setForeground(c);
		lista.setSize(new Dimension(300,40));
		lista.setFont(font);
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
		label.setFont(font);
		pannello.revalidate();
		pannello.add(label,BorderLayout.WEST);
		master.add(finestra);
		master.setTitle("SCRAPER project ®");
		master.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		master.setResizable(true);
		master.setVisible(true);
		master.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		reset.setBackground(Color.white);
		reset.setForeground(c);
		reset.setFont(font);
		//fine set compenenti
		
		
//________________________________________________________________________________________________________________
		// azione di selezione dinastia che sarà passata allo scraper
		lista.addActionListener(new Azione1(master,lista,finestra,remove,res,scrollable,reset,testo,pannello,c));
//________________________________________________________________________________________________________________
		//azione di reset allo stato precedente alla selezione dell'albero
		
		reset.addActionListener(new Azione2(master,finestra,remove,res,reset,scrollable,pannello));
	}
	public static void main(String[] args) {
		String[] dinastie={"","dinastia2","dinastia3","dinastia4","dinastia5","dinastia6","dinastia7","dinastia8"};
		disegnoprova f= new disegnoprova();
	}

}