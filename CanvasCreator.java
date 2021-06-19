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

	public CanvasCreator(Imperatore rad, int numgen) {
		rad= radice;
		this.WIDTH= 100;
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
	 -CASO IMPERATORI FRATELLI (NELL'ULTIMA PARTE)
	 -CORREZIONE COLLEGAMENTO TRA IMPERATORI SE CI STA UNA MADRE SE L'IMPERATORE è ADOTTATO
	 -GRANDEZZA NOMI E GIOCHI
	 -LUNGHEZZA DELLE H GENERAZIONI (FORSE TROPPO PICCOLE PER TRATE MOGLI INUTILI
	 -CASO IN CUI NON CI STANNO FIGLI SE NON IMPERATORI QUINDI NON SERVIREBBE METTERE UN DOPPIO LIVELLO
	 */
	
	public void ricorsiva(Graphics g, Imperatore rad, int WSez,  int[] cursore, int livello)
	{
		//30 DIMENSIONE PER I NOMI
		//30 DIMENSIONE LINEE ORIZZONTALI DEL COLLEGAMENTO CON LA MADRE
		//10 PIXEL DI GIOCO TRA UN DISEGNO E L'ALTRO
		
		cursore[0]=getWGenerazione()/2-30;
		cursore[1]=(getHGenerazione()/2 + getHGenerazione()*livello);
		
		//verifico se c'è una moglie
		if ( !rad.getMogli().isEmpty() )
		{
			///verifico se ci sta una madre
			Persona madre=esisteMadre(rad);
			//se c'è la madre
			if (!madre.getNome().equals("")) 
			{
				cursore[0]-= -15-5 ;
				scriviNome(rad, g, cursore);
				g.drawLine(cursore[0]+30 +5, cursore[1], cursore[0]+30+5+30, cursore[1]);
				cursore[0]=cursore[0]+30+30 +5;
				scriviNome(madre, g, cursore);
				
				//se non è la radice è c'è una madre devo collegare l'imperatore corrente con la linea del precedente 
				if (livello !=0) 
				{
					cursore[0]=getWGenerazione()/2;
					cursore[1]=(getHGenerazione()*livello);
					g.setColor(Color.RED);
					g.drawLine(cursore[0], cursore[1], cursore[0]- 15 -(30/2) , cursore[1]);
					cursore[0]-=15;
					g.drawLine(cursore[0], cursore[1], cursore[0], cursore[1]+ getHGenerazione()/2 -10);
				}
				
				//reset del curosre (principalmente per metodo diseganmogliinutili)
				g.setColor(Color.BLACK);
				cursore[0]=getWGenerazione()/2-30;
				cursore[1]=(getHGenerazione()/2 + getHGenerazione()*livello);
			}
			
			//non c'è una madre ma ci sono mogli inutili (imperatore al centro)
			else
				{
					//collegamento con l'imperatore precedente
					if (livello !=0)
					{
						cursore[0]=getWGenerazione()/2;
						cursore[1]=(getHGenerazione()*livello);
						g.setColor(Color.RED);
						g.drawLine(cursore[0], cursore[1], cursore[0], cursore[1] +getHGenerazione()/2 -10 );
						g.setColor(Color.BLACK);
					}
					scriviNome(rad, g, cursore);
					g.drawLine(cursore[0]+15, cursore[1]+10, cursore[0]+15, cursore[1]+getHGenerazione()/2);
				}
			
			//ci sono mogli inutili e forse anche madre forse anche unica (potrebbe non disegnare nulla) (caso generale per non fare 2 metodi anche nell'else precedente
			disegnaMogliInutili(rad, g, cursore, madre);
			
		}
		//no mogli (caso uguale all'else prima in cui disegno l'imperatore al centro)
		else
			{
				//collegamento con l'imperatore precedente
				if (livello !=0)
				{
					cursore[0]=getWGenerazione()/2;
					cursore[1]=(getHGenerazione()*livello);
					g.setColor(Color.RED);
					g.drawLine(cursore[0], cursore[1], cursore[0], cursore[1] +getHGenerazione()/2 -10 );
					g.setColor(Color.BLACK);
				}
				
				scriviNome(rad, g, cursore);
				g.drawLine(cursore[0]+15, cursore[1]+10, cursore[0]+15, cursore[1]+getHGenerazione()/2);
			}
		
		//setto cursore per disegnafigli e se presenti li disegno con il metodo disegna figli
		cursore[0]= 0;
		cursore[1]=(getHGenerazione()*(livello+1));
		disegnaFigli(rad, g, cursore, WSez);
		
		//setto cursore al centro e disegno linea dell'imp
		cursore[0]=getWGenerazione()/2;
		cursore[1]=(getHGenerazione()/2 +10 + getHGenerazione()*livello);
		g.setColor(Color.RED);
		if (figlioAdottato(rad)) 
		{
			disegnaAdozione(cursore[0], cursore[1], cursore[0], cursore[1]+ getHGenerazione()*2 - getHGenerazione()/2, g);
		}
		else
		{
			g.drawLine(cursore[0], cursore[1], cursore[0], cursore[1]+ getHGenerazione()*2 - getHGenerazione()/2);
		}
		
		if (rad.getSuccessore() != null)
		{
			ricorsiva(g, rad.getSuccessore(),getWGenerazione(), cursore, livello +2);
		}
		
	}
	
	
	public void disegnaFigli(Imperatore imp, Graphics g, int[] cursore, int WSezTotale) {
		
		//30 DIMENSIONE NOMI
		//10 GIOCO TRA NOME E RIGA
		
		int numFigliInutili= imp.getFigli().size();
		
		//calcolo il numero dei figli togliendo l'imperatore se presente
		for (Persona figlio : imp.getFigli())
		{
			if (figlio instanceof Imperatore) 
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
					g.drawLine(cursore[0], cursore[1], cursore[0],cursore[1]+ getHGenerazione()/2 -10);
						
					//aggiorno cursore
					cursore[1]+= getHGenerazione()/2;
					cursore[0]-=30;
					scriviNome(figlio, g, cursore);
				}
				
				//risetto il cursore e incremento per lo spiazzamento
				cursore[1]-= getHGenerazione()/2;
				cursore[0]+=30;
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
			g.drawLine(cursore[0]-5, cursore[1], cursore[0]-5-8, cursore[1] );
			//linea verticale figli
			g.drawLine(cursore[0]-5, cursore[1], cursore[0]-5-4, cursore[1]+getHGenerazione()/2);
			//aggiorno il cursore
			cursore[0]=cursore[0]-5-8 -30;
			scriviNome(imp.getMogli().get(0), g, cursore);
		}
		
		//più di una moglie inutile 
		else if (numMogliInutili>1)
		{
			//linea orizzonatale
			g.drawLine(cursore[0]-5, cursore[1], cursore[0]-5-8, cursore[1] );
			//linea verticale figli
			g.drawLine(cursore[0]-5, cursore[1], cursore[0]-5-4, cursore[1]+getHGenerazione()/2);
			
			//aggiorno il cursore
			cursore[0]=cursore[0]-5-8;
			
			g.drawLine(cursore[0], cursore[1]+ getHGenerazione()/2 -10, cursore[0] , cursore[1]-getHGenerazione()/2 +10);
			int Hlinea= getHGenerazione() -10-10;
			
			//aggiorno cursore
			cursore[0]-=30 -5 -8;
			cursore[1]=cursore[1]-getHGenerazione()/2 +10;
			
			//calcolo lo spiazzamento tra le mogli inutili
			int spiazzamento= Hlinea/(numMogliInutili-1);
			
			// disegno le mogli
			for (Persona moglie : imp.getMogli())  
			{
				if (!(moglie instanceof Madre))
				{
					scriviNome(moglie, g, cursore);
					g.drawLine(cursore[0]+30+5, cursore[1], cursore[0]+30+5+8, cursore[1]);
					//sposto il cursore in basso in base allo spiazzamento
					cursore[1]+=spiazzamento;
				}
			}
		}
	}
	
	public boolean figlioAdottato(Imperatore imp) {
		if (imp.getMogli().contains(imp.getSuccessore().getMadre())) 
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
	public void scriviNome(Persona persona,Graphics g, int[] punt) {
		 Font f;

		    if (persona instanceof Imperatore) {
		        f = new Font("Helvetica", Font.BOLD, 12);
		    }
		    else {
		        f = new Font("Helvetica", Font.PLAIN, 12);
		    }
		    
		    String nome="";
		    int paroleNome=1;
		    g.setColor(Color.black);
		    g.setFont(f);
		    
		    //caso nome lungo
		    if (persona.getNome().length()>10) {
		        String[] lista=persona.getNome().split(" ");
		        paroleNome=lista.length;
		        for (int i=0; i < lista.length; i++) {
		            g.drawString(lista[i], punt[0], punt[1]);
		            punt[1]+=paroleNome*2;
		        }
		    }
		    else {
		    	nome=persona.getNome();
		    	g.drawString(nome, punt[0], punt[1]);
		    }

		    //g.drawRect(punt[0]-5, punt[1]-h*13, 80, h*12); // metodo se vogliamo disegnare i rettangooi (da modificare dimensione h)
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
}
