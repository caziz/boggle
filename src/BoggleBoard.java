import java.util.Random;

public class BoggleBoard {
	public static String[][] letters;
	public static int boardDimensions=Login.length;
	public static final String[] dice={"AACIOT","ABILTY", "ABJMOQ", "ACDEMP", "ACELRS", "ADENVZ", "AHMORS", "BIFORX",
		"DENOSW", "DKNOTU", "EEFHIY", "EGKLUY", "EGINTV", "EHINPS", "ELPSTU", "GILRUW"};
	public BoggleBoard(int length){
		boardDimensions = length;
		if(Login.length!=4) {
			rando();
		}else {
			randomize();
		}
	}
	public void rando() {
		letters = new String[boardDimensions][boardDimensions];
		for(int i = 0; i < boardDimensions; i++) {
			for(int j = 0; j < boardDimensions; j++) {
				int rand6=(int)(Math.random()*6);
				int rand16=(int)(Math.random()*16);
				letters[i][j]=dice[rand16].substring(rand6, rand6+1);
			}
		}
	}
	
	public void randomize(){
		letters=new String[boardDimensions][boardDimensions];
		int[] theseDice={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		RandomizeArray(theseDice);
		String[] whichDie=new String[boardDimensions*boardDimensions];
				for (int i=0; i<whichDie.length; i++){
					whichDie[i]=dice[theseDice[i]];
				}
		int numI=0;
		for (int r=0; r<boardDimensions; r++){
			for (int c=0; c<boardDimensions; c++){
				int numRand=(int)(Math.random()*6);
				letters[r][c]=whichDie[numI].substring(numRand, numRand+1);
				if (letters[r][c].equals("Q")) letters[r][c]="B";
				numI++;
			}
		}
	}

	public static int[] RandomizeArray(int[] array){
		Random rgen = new Random();  // Random number generator			
 
		for (int i=0; i<array.length; i++) {
		    int randomPosition = rgen.nextInt(array.length);
		    int temp = array[i];
		    array[i] = array[randomPosition];
		    array[randomPosition] = temp;
		}
 
		return array;
	}
	
	public static String[][] getLetters() {
		return letters;
	}
}
