package Progetto;

import java.util.ArrayList;

public class ContaGenerazioni {
	
	public ContaGenerazioni() {
		
	}
	public static int ricorsiva(Imperatore albero, ArrayList<Integer> contatore) {
		contatore.add(1);
		if (albero.getFigli()!= null) {
			int numerofigli= albero.getFigli().size();	
			for (int i=0; i<numerofigli;i++) {
				if (albero.getFigli().get(i) instanceof Imperatore) {
					ricorsiva((Imperatore) albero.getFigli().get(i), contatore);	
					
									
			}	
		}	
		}
		return contatore.size();
	}
	public static void main(String[] args) {
		ArrayList<Integer> counter = new ArrayList<Integer>();
		Imperatore tree= AlberoPerTestGUI.Tree();
		System.out.println(ricorsiva(tree, counter));
	}
	
}
