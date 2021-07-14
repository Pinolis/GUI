import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class ProvaProva {
	private Imperatore radice;
	private final static int WGenerazione= 1500;
	private final static int HGenerazione= 100;
	private int width; 
	private int length;
	private int fratello;
	private int[] cursore= {0,0};
	

	public ProvaProva(Imperatore rad, int numgen) {
		radice=rad;
		width= WGenerazione;
		length= HGenerazione*((numgen*2)+1);	//numgen (HGen x (NumGen x 2) perche ogni volta il livello è per 2))
		fratello=0;
		
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
		
		int lenNome = 32;
		int gioco = 10;
		int mezzaRiga = 30;
		int HNomi = 10;
		
		//DEBUGGATO A
		resetCursore(WSez, lenNome, livello);
		g.setColor(Color.RED);
		//verifico se c'è una moglie
		if ( rad.hasMogli() ) {
			///verifico se ci sta una madre
			//se c'è la madre
			if (rad.hasMotherWife()) {
			
				cursore[0]+= -mezzaRiga-gioco*5;
				
				//collegamento con imperatore precedente
				if (livello !=1) {
					
					if (rad.isAdopted()) {
						disegnaAdozione(cursore[0]+lenNome/2, cursore[1]-HGenerazione/3, WSez/2, cursore[1]-HGenerazione/3 , g);
						disegnaAdozione(cursore[0]+lenNome/2, cursore[1]-HGenerazione/3, cursore[0]+lenNome/2 , cursore[1]-HGenerazione/3+ 10, g);
					}
					
					else {
						g.drawLine(cursore[0]+lenNome/2, cursore[1]-HGenerazione/3, WSez/2, cursore[1]-HGenerazione/3);
						g.drawLine(cursore[0]+lenNome/2, cursore[1]-HGenerazione/3, cursore[0]+lenNome/2 , cursore[1]-HGenerazione/3+10 );
					}
				}
			
				scriviNome(rad, g, cursore);
				//disegno linea orizzontale e verticale della madre e imperatore
				cursore[0]= WSez/2;
				g.drawLine(cursore[0]-mezzaRiga, cursore[1], cursore[0]+mezzaRiga, cursore[1]);
				g.drawLine(cursore[0], cursore[1], cursore[0], cursore[1]+ HNomi );
				g.setColor(Color.BLACK);
				cursore[0]+= mezzaRiga +gioco*5;
				scriviNome(rad.getSuccessore().getMadre(), g, cursore);
			}
			
			//non c'è una madre ma ci sono mogli inutili (imperatore al centro)
			else
				{

					//reset cursore posizione inzile
					resetCursore(WSez, lenNome, livello);
					scriviNome(rad, g, cursore);
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
		if (fratello==1) {
			g.setColor(Color.red);
			resetCursore(WSez, lenNome, livello);
			cursore[1]-=HNomi+25;
			
			if (rad.isAdopted()) {
				disegnaAdozione(cursore[0], cursore[1], WSez, cursore[1], g);
				disegnaAdozione(cursore[0], cursore[1], cursore[0], cursore[1]+15, g);
			}
			else {
				g.drawLine(cursore[0], cursore[1], WSez, cursore[1]);
				g.drawLine(cursore[0], cursore[1], cursore[0], cursore[1]+15);
			}
		}
		if (fratello!=1) {
			resetCursore( WSez, lenNome, livello);
			cursore[0]+=lenNome/2;
			cursore[1]+=HNomi;
			g.setColor(Color.RED);
		
			if ((rad.hasSuccessor()) && (rad.getSuccessore().isAdopted())) {
				disegnaAdozione(cursore[0], cursore[1], cursore[0], cursore[1]+ 2*(HGenerazione/3)+HGenerazione-15, g);
			}
			else if ((rad.hasSuccessor())) {
				g.drawLine(cursore[0], cursore[1], cursore[0], cursore[1]+2*(HGenerazione/3)+HGenerazione-10);
			}
		
			if (fratello==2) {
				g.setColor(Color.red);
				resetCursore(WSez, lenNome, livello);
				cursore[1]-=HNomi+25;
				
				if (rad.isAdopted()) {
					disegnaAdozione(cursore[0], cursore[1], WSez, cursore[1], g);
					disegnaAdozione(cursore[0], cursore[1], cursore[0], cursore[1]+15, g);
				}
				else {
					
					g.drawLine(cursore[0], cursore[1], WSez, cursore[1]);
					g.drawLine(cursore[0], cursore[1], cursore[0], cursore[1]+15);
				}
				resetCursore(WSez, lenNome, livello);
				
				if ((rad.hasSuccessor()) && (rad.getSuccessore().isAdopted())) {
					disegnaAdozione(cursore[0], cursore[1]+ 2*(HGenerazione/3)+HGenerazione-10, cursore[0]-WSez/2, cursore[1]+ 2*(HGenerazione/3)+HGenerazione-10, g);
					disegnaAdozione(cursore[0]-WSez/2+lenNome/2, cursore[1]+ 2*(HGenerazione/3)+HGenerazione-10, cursore[0]-WSez/2+lenNome/2, cursore[1]+ 2*(HGenerazione/3)+HGenerazione-10+10, g);
				}
				else if (rad.hasSuccessor()) {
					g.drawLine(cursore[0]+lenNome/2, cursore[1]+ 2*(HGenerazione/3)+HGenerazione, cursore[0]-WSez/2+lenNome/2, cursore[1]+ 2*(HGenerazione/3)+HGenerazione);
					g.drawLine(cursore[0]-WSez/2+lenNome/2, cursore[1]+ 2*(HGenerazione/3)+HGenerazione, cursore[0]-WSez/2+lenNome/2, cursore[1]+ 2*(HGenerazione/3)+HGenerazione+10);
				}
				
				fratello=0;
				WSez= WSez*2;
				
			}
		}
		
		if (rad.hasSuccessor()){
			if(rad.getSuccessore().hasFratelloSuccessore()) {
				fratello=1;
			}
			//condizione che vede il bit imp fratelli e svolge la ricorsiva adatta (cambi wsez in wsez/2)
			//condizione nella quale se non ci sono figli inutile abbassa l'aumento del livello a 1 e non da la linea lunga
		
			if (fratello==0) {
				ricorsiva(g, rad.getSuccessore(), WSez, livello +2, true);
			}
		
			else if (ripeti==true) {
			  
			 ricorsiva(g, rad.getSuccessore(), WSez/2, livello+2,false);
			 fratello+=1;
			 ricorsiva(g, rad.getSuccessore().getSuccessore(), WSez/2, livello+2,false); 
			 
			}
		}
	}

	
		public void resetCursore(int W, int lenN, int liv) {
		//se si attiva l'if o l'else if vuol dire che W è W/2 in quanto passato come parametro a ricorsiva per il caso dei fratelli imperatori
		if (fratello==1) {
			cursore[0]=W/2-lenN/2; //cursore va a un quarto dello schermo- len nome
		}
		else if(fratello==2){
			
			cursore[0]=W+W/2-lenN/2; //cursore va a 3/4 dello schermo-len nome
		}
		else {
		cursore[0]=W/2-lenN/2;
		}
		
		cursore[1]=(HGenerazione/3 + HGenerazione*liv); //viene eseguito a prescindere
	}
	
	public void disegnaFigli(Imperatore imp, Graphics g, int WSezTotale) {
		
		//30 DIMENSIONE NOMI
		//10 GIOCO TRA NOME E RIGA
		int mezzaRiga=30;
		int gioco = 10;
		int lenNomi = 32;
		int detrazione=0;
		
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
		boolean dispari=false;
			//voglio che il numero sia pari perche così non c'è intersezione con la linea dell'imperatore
			if (numFigliInutili % 2 == 1)
			{
				numFigliInutili+=1;
				numFigliInutiliOrig=numFigliInutili-1;
				dispari=true;
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
				int condizione=numFigliInutili;
				if (dispari==true) {
					condizione-=1;
				}
				for ( Persona figlio : imp.getFigli()) 
				{
				
					if (!(figlio instanceof Imperatore))
					{	
						//linea verticale
						g.drawLine(cursore[0], cursore[1], cursore[0], cursore[1]+ HGenerazione/2 -gioco);  //ipotizzato altezza nomi 10 pixels modificabile
						
						//aggiorno cursore
						cursore[1]+= HGenerazione/2;
						cursore[0]-=lenNomi/2;
						scriviNome(figlio, g, cursore);
						countFigli+=1;	

						//resetto il cursore e incremento per lo spiazzamento
						cursore[1]-= HGenerazione/2;
						cursore[0]+=lenNomi/2;
						//caso particolare
						if (numFigliInutiliOrig==1) {
							if (imp.hasMotherWife()) {
								//da modificare
								detrazione= mezzaRiga+ gioco*5;
							}
							if (fratello==2) {
								g.drawLine(cursore[0], cursore[1], cursore[0]+WSezTotale/4-lenNomi/3- detrazione, cursore[1]);
								cursore[0]=WSezTotale/2-lenNomi/3-detrazione;
								
							}
							else {
							g.drawLine(cursore[0], cursore[1], WSezTotale/2-lenNomi/3- detrazione, cursore[1]);
							cursore[0]=WSezTotale/2-lenNomi/3-detrazione;
							
							g.drawLine(cursore[0], cursore[1], cursore[0], cursore[1]- HGenerazione/2);
							}
						}
						else if ((countFigli < condizione)) {
							g.drawLine(cursore[0], cursore[1], cursore[0] + partizione, cursore[1]);
							cursore[0]+=partizione;
						}
					}
				} 
					
					if (imp.hasMotherWife()) {
						detrazione= mezzaRiga+ gioco*5;
					}
					if (fratello==2) {
						cursore[0]=cursore[0]+WSezTotale-lenNomi/3+ 10- detrazione;
					}
					else {
						cursore[0]=WSezTotale/2-lenNomi/3-detrazione;
						}
					g.drawLine(cursore[0], cursore[1], cursore[0], cursore[1]- HGenerazione/2);
					
			}		
		}
			
	public void disegnaMogliInutili(Imperatore imp, Graphics g, int[] cursore) 
	{
		//30 DIMENSIONI NOMI
		//5 GIOCO TRA UNA DISEGNO E L'ALTRO
		//8 DIMENSIONI RIGHE ORIZZONTALI
		
		int gioco = 10;
		int riga = 20;
		int lenNome = 32;
		int HNomi = 10;
		int mezzaRiga=30;
		
		//mi serve per calcolare l'incremento della h quando scrivo ogni moglie
		int numMogliInutili= imp.getMogli().size();
		
		//vedo quante se c'è una madre e la tolgo da la lista 
		boolean esisteMadre=false;
		if (imp.hasMotherWife())
		{
			numMogliInutili-=1;
			esisteMadre=true;
		}
		if (esisteMadre) {
			cursore[0]+= -mezzaRiga-gioco*15;
		}
		
		//se la moglie è singola non mi serve la linea vericale 
		if (numMogliInutili == 1) 
		{
			//linea orizzonatale
			g.drawLine(cursore[0]-lenNome/2- gioco, cursore[1], cursore[0]-gioco-lenNome/2-riga, cursore[1]);
			//aggiorno il cursore
			cursore[0]+=-riga -lenNome/2-gioco- lenNome - 10;
			//più al centro del nome
			cursore[1]+=2;
			for (Persona moglie : imp.getMogli())  
			{
				if (!(moglie instanceof Madre))
				{
					scriviNome(moglie, g, cursore);
				}
			}
		}

		//più di una moglie inutile 
		else if (numMogliInutili>1)
		{
			//linea orizzonatale
			g.drawLine(cursore[0]-lenNome/2-gioco, cursore[1], cursore[0]-gioco-lenNome/2-riga, cursore[1]);
			//aggiorno il cursore
			cursore[0]+=-gioco-lenNome/2-riga;
			//linea verticale
			g.drawLine(cursore[0], cursore[1]+ HGenerazione/2 -HNomi, cursore[0] , cursore[1]-HGenerazione/2 +HNomi);
			int Hlinea= HGenerazione -HNomi-HNomi;
			
			//aggiorno cursore per scrittura nomi
			cursore[0]+=-lenNome-riga-20;
			cursore[1]+=-HGenerazione/2 +HNomi;
			//calcolo lo spiazzamento tra le mogli inutili
			int spiazzamento= Hlinea/(numMogliInutili-1);
			
			// disegno le mogli
			for (Persona moglie : imp.getMogli())  
			{
				if (!(moglie instanceof Madre))
				{
					scriviNome(moglie, g, cursore);
					g.drawLine(cursore[0]+lenNome+10, cursore[1], cursore[0]+lenNome+riga+20, cursore[1]);
					//sposto il cursore in basso in base allo spiazzamento
					cursore[1]+=spiazzamento;
					if (numMogliInutili % 2 == 0) {
						cursore[1]+=1;
					}
				}
			}
		}
	}
				
	public void disegnaAdozione(int x1, int y1, int x2, int y2, Graphics g) {
        g.setColor(Color.RED);

        if (y1 != y2) //se la linea è verticale
              { 
            for (int y = y1; y < y2; y+=10) {
                g.drawLine(x1, y, x2, y+5);
            }
        }
        else if (x1 != x2)//se la linea è orizzontale
        {
            for (int x = x1; x < x2; x+=10) {
                g.drawLine(x, y1, x+5, y2);
            }
        }
  }
	
	/* scriverà il nome del tipo nella posizione del cursore o coordinate generiche, verificherà
	-se deve andare a capo se il nome è troppo lungo;
	-se mettere in grassetto il nome se è imperatore(aggiungendo eventuali dati);
	*/
	public void scriviNome(Persona persona, Graphics g, int[] cursore) {
		int gioco=10;
		
		int maxLen= 10;
		if (persona instanceof Imperatore) {
			maxLen+=3;
		}
		String nome=persona.getNome();

		String[] nomi = nome.split(" ");

        String nomeMerged = nomi[0];

            for (int i = 1; i < nomi.length; i++) {

                if (i < nomi.length) {

                    String nextName = nomi[i];
                    if ((ultimaRiga(nomeMerged) + nomi[i]).length() < maxLen)
                        nomeMerged += " " + nextName;
                    else {
                        nomeMerged += "\n" + nextName;
                    }
                }
            }
        String[] nomiMerged = nomeMerged.split("\n");
		int dimFont = 12;
		Font f;
		if (persona instanceof Imperatore) {
			f = new Font("Helvetica", Font.BOLD, dimFont);
			}
		else {
				f = new Font("Helvetica", Font.PLAIN, dimFont);
			}
		g.setFont(f);
		int acapo= 0;
		for (String name : nomiMerged)
		{
		g.drawString(name, cursore[0]-gioco, cursore[1]+ acapo);
		acapo+=12;
		}
	}
	
	private static String ultimaRiga(String nome) {
        String[] nomi = nome.split("\n");
        return nomi[nomi.length-1];
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
