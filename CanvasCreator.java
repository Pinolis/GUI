package ClassiUtili;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JFrame;


public class CanvasCreator extends Canvas {
	private Imperatore radice;
	private int WGenerazione; //dimensioni della generazione corrente variabili
	private int HGenerazione;
	private final int WIDTH; //dimensioni fissate del canvas 
	private final int LENGTH;
	private boolean ImpFratelli;

	public CanvasCreator(Imperatore rad, int numgen) {
		radice=rad;
		this.WIDTH= 100 ;
		this.LENGTH= 100;
		setWGenerazione(WIDTH);
		setBackground(Color.white);
		setSize(WIDTH, LENGTH);
		
	//costruttore con dati di grandezza ecc da aggiungere al frame
	}
	public void paint(Graphics g) { //CanvasCreator canvas, Persona albero, double h , double w, boolean ott, double[] cur
		//ricorsiva(g, rad, h, w, cursore[ 0, 0], 0)
	}
	
	
	/*DA SISTEMARE:
	 X OGNI VOLTA CHE CI STA GETWGENE RISCRIVERE COME WSEZ 
	 X RINOMINARE VARIABILI DI GIOCO, DIMENSIONE NOMI ECC E METTERE IL NOME DELL IMPERATORE PIù CENTRATO POSSIBILE
	 X METODO RESET CURSORE
	 -CASO IMPERATORI FRATELLI (NELL'ULTIMA PARTE)
	 -CORREZIONE COLLEGAMENTO TRA IMPERATORI SE CI STA UNA MADRE SE L'IMPERATORE è ADOTTATO (fatto ma da rivedere)
	 -GRANDEZZA NOMI E GIOCHI
	 -LUNGHEZZA DELLE H GENERAZIONI (FORSE TROPPO PICCOLE PER TRATE MOGLI INUTILI)
	 -CASO IN CUI NON CI STANNO FIGLI SE NON IMPERATORI QUINDI NON SERVIREBBE METTERE UN DOPPIO LIVELLO (forse non lo gestiamo)
	 */
	
	public void ricorsiva(Graphics g, Imperatore rad, int WSez,  int[] cursore, int livello)
	{
		//30 DIMENSIONE PER I NOMI
		//30 DIMENSIONE LINEE ORIZZONTALI DEL COLLEGAMENTO CON LA MADRE
		//10 PIXEL DI GIOCO TRA UN DISEGNO E L'ALTRO
		
		int lenNome = 30;
		int gioco = 5;
		int mezzaRiga = 15;
		int HNomi = 10;
		
		
		resetCursore(cursore, WSez, lenNome, livello);
		
		//verifico se c'è una moglie
		if ( !(rad.getMogli()==null) )
		{
			///verifico se ci sta una madre
			Persona madre=esisteMadre(rad);
			//se c'è la madre
			if (!madre.getNome().equals("")) 
			{
				cursore[0]+= -mezzaRiga-gioco-lenNome/2;
				scriviNome(rad, g, cursore);
				g.setColor(Color.RED);
				//disegno linea orizzontale e verticale della madre e imperatore
				g.drawLine(cursore[0]+lenNome +gioco, cursore[1], cursore[0]+lenNome+gioco+mezzaRiga*2, cursore[1]);
				cursore[0]= WSez/2;
				g.drawLine(cursore[0], cursore[1], cursore[0], cursore[1]+ getHGenerazione()/2 );
				g.setColor(Color.BLACK);
				cursore[0]+= mezzaRiga +gioco;
				scriviNome(madre, g, cursore);
				
				
				//se non è la radice e c'è una madre devo collegare l'imperatore corrente con la linea del precedente 
				if (livello !=0) 
				{
					cursore[0]=WSez/2;
					cursore[1]=(getHGenerazione()*livello);
					g.setColor(Color.RED);
					g.drawLine(cursore[0], cursore[1], cursore[0]- mezzaRiga -(lenNome/2) , cursore[1]);
					cursore[0]-=mezzaRiga;
					g.drawLine(cursore[0], cursore[1], cursore[0], cursore[1]+ getHGenerazione()/2 -gioco*2);
				}
				
				g.setColor(Color.BLACK);
				resetCursore(cursore, WSez, lenNome, livello);
			}
			
			//non c'è una madre ma ci sono mogli inutili (imperatore al centro)
			else
				{
					//collegamento con l'imperatore precedente
					if (livello !=0)
					{
						//set cursore inzio della generazione  (brutto perche già è settato da un altra parte e non è essenziale questo reset )
						cursore[0]=WSez/2;
						cursore[1]=(getHGenerazione()*livello);
						
						g.setColor(Color.RED);
						g.drawLine(cursore[0], cursore[1], cursore[0], cursore[1] +getHGenerazione()/2 -HNomi );   //altezza nome ipotizzata 10 piexls
						g.setColor(Color.BLACK);
					}
					//reset cursore posizione inzile
					resetCursore(cursore, WSez, lenNome, livello);
					scriviNome(rad, g, cursore);
					
					//linea verticale figli rossa
					g.setColor(Color.RED);
					g.drawLine(cursore[0]+mezzaRiga, cursore[1]+HNomi, cursore[0]+mezzaRiga, cursore[1]+getHGenerazione()/2);
					
					g.setColor(Color.BLACK);
					
				}
			
			//ci sono mogli inutili e forse anche madre forse anche unica (potrebbe non disegnare nulla) (caso generale per non fare 2 metodi anche nell'else precedente
			disegnaMogliInutili(rad, g, cursore, madre);
			
		}
		//no mogli (caso uguale all'else prima in cui disegno l'imperatore al centro)
		else
			{
				//scivo il nome al centro
				scriviNome(rad, g, cursore);
				g.setColor(Color.RED);
				//collegamento con l'imperatore precedente
				if (livello !=0)
				{
					cursore[0]=WSez/2;
					cursore[1]=(getHGenerazione()*livello);
					g.drawLine(cursore[0], cursore[1], cursore[0], cursore[1] +getHGenerazione()/2 -gioco*2 );
				}
				g.setColor(Color.BLACK);
				
			}
		
		//setto cursore per disegnafigli e se presenti li disegno con il metodo disegna figli
		if (!(rad.getFigli()==null)) 
		{
			cursore[0]= 0;
			cursore[1]=(getHGenerazione()*(livello+1));
			disegnaFigli(rad, g, cursore, WSez);
		}
		
		//setto cursore al centro e disegno linea dell'imp
		cursore[0]=WSez/2;
		cursore[1]=getHGenerazione()*(livello+1);
		g.setColor(Color.RED);
		
		if (figlioAdottato(rad)) 
		{
			disegnaAdozione(cursore[0], cursore[1], cursore[0], cursore[1]+ getHGenerazione(), g);
		}
		else
		{
			g.drawLine(cursore[0], cursore[1], cursore[0], cursore[1]+ getHGenerazione());
		}
		
		if (rad.getSuccessore() != null)
			//condizione che vede il bit imp fratelli e svolge la ricorsiva adatta (cambi wsez in wsez/2)
			//condizione nella quale se non ci sono figli inutile abbassa l'aumento del livello a 1 e non da la linea lunga
		{
			ricorsiva(g, rad.getSuccessore(), WSez, cursore, livello +2);
		}
		
	}
	
	
	
	
	public void resetCursore(int[] cursore, int W, int lenN, int liv) {
		cursore[0]=W/2-lenN/2;
		cursore[1]=(getHGenerazione()/2 + getHGenerazione()*liv);
	}
	
	public void disegnaFigli(Imperatore imp, Graphics g, int[] cursore, int WSezTotale) {
		
		//30 DIMENSIONE NOMI
		//10 GIOCO TRA NOME E RIGA
		
		int gioco = 10;
		int lenNomi = 30;
		
		int numFigliInutili= imp.getFigli().size();
		
		//calcolo il numero dei figli togliendo l'imperatore se presente
		for (Persona figlio : imp.getFigli())
		{
			if (figlio instanceof Imperatore) //IDEA: counter che conta gli imperatori nella lista figli alla fine se è maggiore di 1 setta la variabile imp frat a 1
			{
				numFigliInutili-=1;
			}
		}
		//voglio che il numero sia pari perche così non c'è intersezione con la linea dell'imperatore
		if (numFigliInutili % 2 == 1)
		{
			numFigliInutili+=1;
		}

		// se sono presenti figli inutili li disegno
		if (numFigliInutili > 0) 
		{
			// calcolo la posizione dei divisori della generazione orizzontale e setto cursore
			int Wsez= WSezTotale/numFigliInutili;
			cursore[0]+= Wsez/2;
			
			for ( Persona figlio : imp.getFigli()) 
			{
				if (!(figlio instanceof Imperatore))
				{	
					//linea verticale
					g.drawLine(cursore[0], cursore[1], cursore[0],cursore[1]+ getHGenerazione()/2 -gioco);  //ipotizzato altezza nomi 10 pixels modificabile
						
					//aggiorno cursore
					cursore[1]+= getHGenerazione()/2;
					cursore[0]-=lenNomi;
					scriviNome(figlio, g, cursore);
				}
				
				//resetto il cursore e incremento per lo spiazzamento
				cursore[1]-= getHGenerazione()/2;
				cursore[0]+=lenNomi;
				g.drawLine(cursore[0], cursore[1], cursore[0]+ Wsez, cursore[1]);
				cursore[0]+=Wsez;
			}
		}
	}
	
	public void disegnaMogliInutili(Imperatore imp, Graphics g, int[] cursore, Persona madre) 
	{
		//30 DIMENSIONI NOMI
		//5 GIOCO TRA UNA DISEGNO E L'ALTRO
		//8 DIMENSIONI RIGHE ORIZZONTALI
		int gioco = 5;
		int riga = 8;
		int lenNomi = 30;
		int HNomi = 10;
		
		//mi serve per calcolare l'incremento della h quando scrivo ogni moglie
		int numMogliInutili= imp.getMogli().size();
		
		//vedo quante se c'è una madre e la tolgo da la lista 
		if (!madre.getNome().equals(""))
		{
			numMogliInutili-=1;
		}
		
		//se la moglie è singola non mi serve la linea vericale 
		if (numMogliInutili == 1) 
		{
			//linea orizzonatale
			g.drawLine(cursore[0]-gioco, cursore[1], cursore[0]-gioco-riga, cursore[1] );
			//linea verticale figli
			g.drawLine(cursore[0]-gioco, cursore[1], cursore[0]-gioco-riga/2, cursore[1]+getHGenerazione()/2);
			//aggiorno il cursore
			cursore[0]=cursore[0]-gioco-riga -lenNomi;
			scriviNome(imp.getMogli().get(0), g, cursore);
		}
		
		//più di una moglie inutile 
		else if (numMogliInutili>1)
		{
			//linea orizzonatale
			g.drawLine(cursore[0]-gioco, cursore[1], cursore[0]-gioco-riga, cursore[1] );
			//linea verticale figli
			g.drawLine(cursore[0]-gioco, cursore[1], cursore[0]-gioco-riga/2, cursore[1]+getHGenerazione()/2);
			
			//aggiorno il cursore
			cursore[0]=cursore[0]-gioco-riga;
			
			g.drawLine(cursore[0], cursore[1]+ getHGenerazione()/2 -HNomi, cursore[0] , cursore[1]-getHGenerazione()/2 +HNomi);
			int Hlinea= getHGenerazione() -HNomi-HNomi;
			
			//aggiorno cursore
			cursore[0]-=lenNomi -gioco -riga;
			cursore[1]=cursore[1]-getHGenerazione()/2 +HNomi;
			
			//calcolo lo spiazzamento tra le mogli inutili
			int spiazzamento= Hlinea/(numMogliInutili-1);
			
			// disegno le mogli
			for (Persona moglie : imp.getMogli())  
			{
				if (!(moglie instanceof Madre))
				{
					scriviNome(moglie, g, cursore);
					g.drawLine(cursore[0]+lenNomi+gioco, cursore[1], cursore[0]+lenNomi+gioco+riga, cursore[1]);
					//sposto il cursore in basso in base allo spiazzamento
					cursore[1]+=spiazzamento;
				}
			}
		}
	}
	
	public boolean figlioAdottato(Imperatore imp) {
		if (imp.getMogli()==null) {
			return true;
		}
		else if (imp.getMogli().contains(imp.getSuccessore().getMadre())) 
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	
	//verifica se è presente una madre
	public Persona esisteMadre(Imperatore imp)
	{
		Persona madre= new Persona( "", "url");
		for (Persona moglie : imp.getMogli()) 
		{
			if (moglie instanceof Madre)
			{
				madre=moglie;
				break;
			}
		}
		return madre;
	}
	

	public boolean DoppiaRiga(Imperatore person, int max) {
		return true;
	/*conta i figli dell'imperatore e le loro mogli, se superano un numero settato
	in base alla larghezza del suo spazio dedicato da a quella gerarchia una doppia riga
	*/
	
	}
				
	public void disegnaAdozione(int x1, int y1, int x2, int y2, Graphics g)
	{
		  g.setColor(Color.RED);
		  g.drawLine(x1,y1,x2,y2);
		  g.setColor(Color.white);
		  final int ognquant=5;
		  if (x1!=x2) 
		  {
			  for (int i=x1;i<x2;i+=ognquant) 
			  {
				  g.drawLine(i, y1, i+ognquant, y2);
			  }	  
		  }
		  else 
		  {
			  for (int i=y1;i<y2;i+=2*ognquant) 
			  {
				  g.drawLine(x1, i, x2, i+ognquant);
			  }
		  }
	}
	
	/* scriverà il nome del tipo nella posizione del cursore o coordinate generiche, verificherà
	-se deve andare a capo se il nome è troppo lungo;
	-se mettere in grassetto il nome se è imperatore(aggiungendo eventuali dati);
	*/
	public void scriviNome(Persona persona, Graphics g, int[] cursore) {
		String nome= persona.getNome();
		int gioco = 5;
		int minLen = 5;
		int spiazzamentoH = 15;
		int maxLen = 17;
		int rettH = 20;
		int dimFont = 12;
		Font f;

		if (persona instanceof Imperatore) {
			f = new Font("Helvetica", Font.BOLD, dimFont);
			}
		else {
				f = new Font("Helvetica", Font.PLAIN, dimFont);
			}
		g.drawString(nome, cursore[0], cursore[1]);

		if (nome.length()<=minLen) {
			g.drawRect(cursore[0]-gioco, cursore[1]-spiazzamentoH, nome.length()*8, rettH);
		} else {
			if (nome.length()>=maxLen) {
				g.drawRect(cursore[0]-gioco, cursore[1]-spiazzamentoH, nome.length()*6+6, rettH);
			}
			else{g.drawRect(cursore[0]-gioco, cursore[1]-spiazzamentoH, nome.length()*7, rettH);

			}
		}
	}
	
	
	public int getWGenerazione() {
		return WGenerazione;
	}
	public void setWGenerazione(int wGenerazione) {
		WGenerazione = wGenerazione;
	}
	public int getHGenerazione() {
		return HGenerazione;
	}
	public void setHGenerazione(int hGenerazione) {
		HGenerazione = hGenerazione;
	}
	public Imperatore getRadice() {
		return radice;
	}
	public boolean isImpFratelli() {
		return ImpFratelli;
	}
	public void setImpFratelli(boolean impFratelli) {
		ImpFratelli = impFratelli;
	}
}
