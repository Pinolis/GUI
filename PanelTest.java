import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PanelTest extends JPanel{
	private ProvaProva painter;
	
	public PanelTest() {
		
		Imperatore root = ScraperUser.getAlberoGenealogico(new Dinastia("", 1));
		ArrayList<Integer> counter = new ArrayList<Integer>();
		int generazioni = ContaGenerazioni.ric(root, counter);
		ProvaProva paint= new ProvaProva(root, generazioni);
		painter=paint;
		
		setBorder(BorderFactory.createLineBorder(Color.black));
		setPreferredSize(new Dimension(painter.getWidth(), painter.getLength()));
	}

	
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       

        // Draw 
        painter.ricorsiva(g, painter.getRadice(), painter.getWidth(), 1, true);
        
    }  
}
