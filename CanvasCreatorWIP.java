import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class CanvasCreator extends Canvas {
	private Imperatore radice;
	private final static int WGenerazione= 1500;
	private final static int HGenerazione= 100;
	private int width; 
	private int length;
	private int fratello;
	private int[] cursore= {0,0};

	public CanvasCreator(Imperatore rad, int numgen) {
		radice=rad;
		width= WGenerazione;
		length= HGenerazione*(numgen*2);	//numgen (HGen x (NumGen x 2) perche ogni volta il livello è per 2))
		fratello=0;
		//IL SETSIZE VA MESSO NEL CODICE DI ANDREA CON I GET DI WIDTH E LENGTH
	}
	
	@Override
	public void paint(Graphics g) {
		ricorsiva(g, radice, WGenerazione, 0, false);
	}
	
	
	
	
	/*DA SISTEMARE:
	 X OGNI VOLTA CHE CI STA GETWGENE RISCRIVERE COME WSEZ 
	 X RINOMINARE VARIABILI DI GIOCO, DIMENSIONE NOMI ECC E METTERE IL NOME DELL IMPERATORE PIù CENTRATO POSSIBILE
	 X METODO RESET CURSORE
	 X CASO IMPERATORI FRATELLI (NELL'ULTIMA PARTE)
	 X CORREZIONE COLLEGAMENTO TRA IMPERATORI SE CI STA UNA MADRE SE L'IMPERATORE è ADOTTATO (fatto ma da rivedere) (l'imperatore non può avere madre ed essere adottatao (forse ero troppo hi))
	 -GRANDEZZA NOMI E GIOCHI
	 X LUNGHEZZA DELLE H GENERAZIONI (FORSE TROPPO PICCOLE PER TRATE MOGLI INUTILI)  (la fissiamo quindi sticazzi)
	 -CASO IN CUI NON CI STANNO FIGLI SE NON IMPERATORI QUINDI NON SERVIREBBE METTERE UN DOPPIO LIVELLO (forse non lo gestiamo) (non lo stiamo gestendo) 
	 	(basta un boolena di istanza che viene settato da disegna figli e un if prima delle chiamate che incrementa o meno il livello di 1)
	 -AGGIUNGERE ALL'ESTREMITà DI WSEZ UN IMMAGINE DELL'IMPERATORE CON LA DATA DI REGNO
	 */
	public void ricorsiva(Graphics g, Imperatore rad, int WSez, int livello, boolean ripeti)
	{
		//30 DIMENSIONE PER I NOMI
		//30 DIMENSIONE LINEE ORIZZONTALI DEL COLLEGAMENTO CON LA MADRE
		//10 PIXEL DI GIOCO TRA UN DISEGNO E L'ALTRO
		
		int lenNome = 30;
		int gioco = 5;
		int mezzaRiga = 15;
		int HNomi = 10;
		
		
		resetCursore(WSez, lenNome, livello);
		
		//verifico se c'è una moglie
		if ( rad.hasMogli() )
		{
			///verifico se ci sta una madre
			//se c'è la madre
			if (rad.hasMotherWife()) 
			{
				cursore[0]+= -mezzaRiga-gioco-lenNome/2;
				scriviNome(rad, g, cursore);
				g.setColor(Color.RED);
				//disegno linea orizzontale e verticale della madre e imperatore
				g.drawLine(cursore[0]+lenNome +gioco, cursore[1], cursore[0]+lenNome+gioco+mezzaRiga*2, cursore[1]);
				cursore[0]= WSez/2;
				g.drawLine(cursore[0], cursore[1], cursore[0], cursore[1]+ HGenerazione/2 );
				g.setColor(Color.BLACK);
				cursore[0]+= mezzaRiga +gioco;
				scriviNome(rad.getSuccessore().getMadre(), g, cursore);
				
				
				//se non è la radice e c'è una madre devo collegare l'imperatore corrente con la linea del precedente 
				if (livello !=0) 
				{
					cursore[0]=WSez/2;
					cursore[1]=(HGenerazione*livello);
					g.setColor(Color.RED);
					g.drawLine(cursore[0], cursore[1], cursore[0]- mezzaRiga -(lenNome/2) , cursore[1]);
					cursore[0]-=mezzaRiga;
					g.drawLine(cursore[0], cursore[1], cursore[0], cursore[1]+ HGenerazione/2 -gioco*2);
				}
				
				g.setColor(Color.BLACK);
				resetCursore(WSez, lenNome, livello);
			}
			
			//non c'è una madre ma ci sono mogli inutili (imperatore al centro)
			else
				{
					//collegamento con l'imperatore precedente
					if (livello !=0)
					{
						//set cursore inzio della generazione  (brutto perche già è settato da un altra parte e non è essenziale questo reset )
						cursore[0]=WSez/2;
						cursore[1]=(HGenerazione*livello);
						
						g.setColor(Color.RED);
						g.drawLine(cursore[0], cursore[1], cursore[0], cursore[1] +HGenerazione/2 -HNomi );   //altezza nome ipotizzata 10 piexls
						g.setColor(Color.BLACK);
					}
					//reset cursore posizione inzile
					resetCursore(WSez, lenNome, livello);
					scriviNome(rad, g, cursore);
					
					//linea verticale figli rossa
					g.setColor(Color.RED);
					g.drawLine(cursore[0]+mezzaRiga, cursore[1]+HNomi, cursore[0]+mezzaRiga, cursore[1]+HGenerazione/2);
					
					g.setColor(Color.BLACK);
					
				}
			
			//ci sono mogli inutili e forse anche madre forse anche unica (potrebbe non disegnare nulla) (caso generale per non fare 2 metodi anche nell'else precedente
			disegnaMogliInutili(rad, g, cursore);
			
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
					cursore[1]=(HGenerazione*livello);
					g.drawLine(cursore[0], cursore[1], cursore[0], cursore[1] +HGenerazione/2 -gioco*2 );
				}
				g.setColor(Color.BLACK);
				
			}
		
		//setto cursore per disegnafigli e se presenti li disegno con il metodo disegna figli
		if (rad.hasFigli()) 
		{
			cursore[0]= 0;
			cursore[1]=(HGenerazione*(livello+1));
			disegnaFigli(rad, g, WSez);
		}
		
		//setto cursore al centro e disegno linea dell'imp
		if (fratello!=1) {
		resetCursore( WSez, lenNome, livello);
		cursore[0]+=lenNome;
		cursore[1]+=HGenerazione/2;
		
		g.setColor(Color.RED);
		
		if ((rad.hasSuccessor()) && (rad.getSuccessore().isAdopted())) 
		{
			disegnaAdozione(cursore[0], cursore[1], cursore[0], cursore[1]+ HGenerazione, g);
		}
		else
		{
			g.drawLine(cursore[0], cursore[1], cursore[0], cursore[1]+ HGenerazione);
		}
		
		if (fratello==2) {
			g.drawLine(cursore[0], cursore[1]+HGenerazione, cursore[0]-WSez/2, cursore[1]+HGenerazione);
			fratello=0;
		}
			
		}
		
		if (rad.hasSuccessor()){
			if(rad.getSuccessore().hasFratelloSuccessore()) {
				fratello=1;
			}
			//condizione che vede il bit imp fratelli e svolge la ricorsiva adatta (cambi wsez in wsez/2)
			//condizione nella quale se non ci sono figli inutile abbassa l'aumento del livello a 1 e non da la linea lunga
		
			if (fratello==0) {
			ricorsiva(g, rad.getSuccessore(), WSez, livello +2,true);
			
			}
		
			else if (ripeti==true) {
			  
			 ricorsiva(g, rad.getSuccessore(), WSez/2, livello+2,false);
			 fratello+=1;
			 ricorsiva(g, rad.getSuccessore(), WSez/2, livello+2,false); 
			}
		}
	}
	
	
	public void resetCursore(int W, int lenN, int liv) {
		//se si attiva l'if o l'else if vuol dire che W è W/2 in quanto passato come parametro a ricorsiva per il caso dei fratelli imperatori
		if (fratello==1) {
			cursore[0]=W/2-lenN; //cursore va a un quarto dello schermo- len nome
		}
		else if(fratello==2){
			
			cursore[0]=W+W/2-lenN; //cursore va a 3/4 dello schermo-len nome
		}
		else {
		cursore[0]=W/2-lenN/2;
		}
		
		cursore[1]=(HGenerazione/3 + HGenerazione*liv); //viene eseguito a prescindere
	}
	
	
	public void disegnaFigli(Imperatore imp, Graphics g, int WSezTotale) {
		
		//30 DIMENSIONE NOMI
		//10 GIOCO TRA NOME E RIGA
		
		int gioco = 10;
		int lenNomi = 30;
		
		int numFigliInutili= imp.getFigli().size();
		
		//calcolo il numero dei figli togliendo l'imperatore se presente
		for (Persona figlio : imp.getFigli())
		{
			if (figlio instanceof Imperatore) 
			{
				numFigliInutili-=1;
			}
		}
		int numFigliInutiliOrig=0;
			//voglio che il numero sia pari perche così non c'è intersezione con la linea dell'imperatore
			if (numFigliInutili % 2 == 1)
			{
				numFigliInutili+=1;
				numFigliInutiliOrig=numFigliInutili-1;
			}

			// se sono presenti figli inutili li disegno
			if (numFigliInutili > 0) 
			{
				// calcolo la posizione dei divisori della generazione orizzontale e setto cursore
				int partizione= (WSezTotale)/numFigliInutili;
				if (fratello==2){
				cursore[0]+=WSezTotale;
			}
				cursore[0]+= partizione/2; //fratelo sx: resta così, fratello dx è +=WsezTotale(che sarà /2 quando chiamo la funzione) +Wsez/2
				int countFigli= 0;
				for ( Persona figlio : imp.getFigli()) 
				{
				
					if (!(figlio instanceof Imperatore))
					{	
						//linea verticale
						g.drawLine(cursore[0], cursore[1], cursore[0],cursore[1]+ HGenerazione/2 -gioco);  //ipotizzato altezza nomi 10 pixels modificabile
						
						//aggiorno cursore
						cursore[1]+= HGenerazione/2;
						cursore[0]-=lenNomi;
						scriviNome(figlio, g, cursore);
						countFigli+=1;	

						//resetto il cursore e incremento per lo spiazzamento
						cursore[1]-= HGenerazione/2;
						cursore[0]+=lenNomi;
						if (numFigliInutiliOrig==1) {
							int detrazione=0;
							if (imp.hasMotherWife()) {
								detrazione=15;
							}
							g.drawLine(cursore[0], cursore[1], WGenerazione/2-lenNomi/2- detrazione, cursore[1]);
							cursore[0]=WGenerazione/2-lenNomi/2;
							g.drawLine(cursore[0], cursore[1], cursore[0], cursore[1]- HGenerazione/2+ gioco);
						}
						else if ((countFigli < numFigliInutili-1)) {
							g.drawLine(cursore[0], cursore[1], cursore[0] + partizione, cursore[1]);
							cursore[0]+=partizione;
						}
					}
				}
			}
		}
	
	public void disegnaMogliInutili(Imperatore imp, Graphics g, int[] cursore) 
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
		if (imp.hasMotherWife())
		{
			numMogliInutili-=1;
		}
		
		//se la moglie è singola non mi serve la linea vericale 
		if (numMogliInutili == 1) 
		{
			//linea orizzonatale
			g.drawLine(cursore[0]-gioco, cursore[1], cursore[0]-gioco-riga, cursore[1]);
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
			g.drawLine(cursore[0]-gioco, cursore[1], cursore[0]-gioco-riga/2, cursore[1]+HGenerazione/2);
			
			//aggiorno il cursore
			cursore[0]=cursore[0]-gioco-riga;
			
			g.drawLine(cursore[0], cursore[1]+ HGenerazione/2 -HNomi, cursore[0] , cursore[1]-HGenerazione/2 +HNomi);
			int Hlinea= HGenerazione -HNomi-HNomi;
			
			//aggiorno cursore
			cursore[0]-=lenNomi -gioco -riga;
			cursore[1]=cursore[1]-HGenerazione/2 +HNomi;
			
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
				
	public void disegnaAdozione(int x1, int y1, int x2, int y2, Graphics g)
	{
		  g.setColor(Color.RED);
		  g.drawLine(x1,y1,x2,y2);
		  g.setColor(Color.WHITE);
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
		String nome=persona.getNome();
		int change=1;
		if (nome.length()>13){
			int lenNome=nome.length();
			String newName=nome.substring(0,13) + "\n"+nome.substring(13,lenNome-1);
			nome=newName;
			change+=1;
		}
		
		int gioco = 5;
		int spiazzamentoH = 15*change;
		int rettH = 20*change;
		int rettW = 30;
		int dimFont = 12;
		Font f;
		if (persona instanceof Imperatore) {
			f = new Font("Helvetica", Font.BOLD, dimFont);
			}
		else {
				f = new Font("Helvetica", Font.PLAIN, dimFont);
			}
		g.setFont(f);
		g.drawString(nome, cursore[0], cursore[1]);
		g.drawRect(cursore[0]- gioco, cursore[1]-spiazzamentoH-gioco, rettW+gioco, rettH+gioco);
		
	}
	
	public Imperatore getRadice() {
		return radice;
	}

	public int getWidth() {
		return width;
	}

	public int getLength() {
		return length;
	}
}
