package PokemonDemo;

public class Dialogue {
	private String firstLine = "", secondLine = "";
	public Dialogue(String one, String two){
		firstLine = one;
		secondLine = two;
	}
	public Dialogue(String one){
		if(one.toCharArray().length < 35){
			firstLine = one;
			secondLine = "";
		}
		else{
			for(int i = 0; i < one.toCharArray().length; i++){
				if(i < 35)
					firstLine = firstLine + one.toCharArray()[i];
				else
					secondLine = secondLine + one.toCharArray()[i];
			}
		}
	}
	public void setLineOne(String one){
		firstLine = one;
	}
	public void setLineTwo(String two){
		secondLine = two;
	}
	public String getLineOne(){
		return firstLine;
	}
	public String getLineTwo(){
		return secondLine;
	}
}
