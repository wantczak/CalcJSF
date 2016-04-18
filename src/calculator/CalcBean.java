package calculator;

public class CalcBean {
	private String resultText = "0";
	private String btnParam; //przycisk btnParam
	private String btn; //przycisk btn
	private String btnResult; //przycisk btnResult
	
	private StringBuilder liczba1Str = new StringBuilder(); 
	private StringBuilder liczba2Str = new StringBuilder();
	private boolean drugaLiczba = false;
	private boolean newCalc = false;
	private boolean dotSet = false;
	private boolean error = false;

	private double liczba1;
	private double liczba2;
	private double wynik;

	
	
	public String getResultText() {
		return resultText;
	}

	public void setResultText(String resultText) {
		this.resultText = resultText;
	}
	
	
	//========================BUTTON PARAM========================================
	public void setBtnParam(String btnParam){
		//Gdy wcisnelismy przycisk odpowiadajacy za Parametr dzialania
		if (liczba1Str.length()!=0){
			drugaLiczba = true; //ustawienie zmiennej boolean, ktora odpowiada za wpisanie drugiej liczby
			this.btnParam = btnParam;
			dotSet = false;
		}
		
		else if (!drugaLiczba&&(liczba1Str.length()==0)){
			liczba1Str.append(resultText);
			drugaLiczba = true; //ustawienie zmiennej boolean, ktora odpowiada za wpisanie drugiej liczby
			this.btnParam = btnParam;
			dotSet = false;
		}
		newCalc();
	}
	
	public String getBtnParam(){
		return btnParam;
	}
	
	private void newCalc(){
		//zerowanie zmiennych liczbowych po wcisnieciu result
		if (newCalc == true){
			liczba1Str.setLength(0);
			liczba2Str.setLength(0);
			drugaLiczba = false;
			newCalc = false;
			dotSet = false;			
		}
	}

	
	private void checkError() {
		if (resultText.equals("Infinity")||resultText.equals("NaN")){
			setError(true);
			resultText = "Error";
		}
	}

	public String getBtn() {
		return btn;
	}

	public void setBtn(String btn) {
		if (btn.contains("C")){
			setError(false);
			newCalc = true;
			drugaLiczba=false;
			resultText = "";
		}
		//Sprawdzenie czy wpisujemy pierwsza liczbe
		if (!isError()){
			if (!drugaLiczba){
				if (btn.matches("\\d+")){
					liczba1Str.append(btn);
					resultText = liczba1Str.toString();
				}
				
				else if (btn.contains("+/-")){
					if ((liczba1Str.length()==0)){
						liczba1Str.append(resultText);
					}

					if(liczba1Str.charAt(0)=='-'){
						liczba1Str.deleteCharAt(0);
					}
					else{
						liczba1Str.insert(0, '-');
					}
					resultText = liczba1Str.toString();
				}
				
				else if (btn.contains(".")&&!dotSet){
					liczba1Str.append(".");
					dotSet = true;
					resultText = liczba1Str.toString();
				}
				
				else if (btn.contains("sqrt")){
					if (!drugaLiczba&&(liczba1Str.length()==0)){
						liczba1Str.append(resultText);
						double liczbaSqrt = Math.sqrt(Double.valueOf(liczba1Str.toString()));
						liczba1Str.setLength(0);
						liczba1Str.append(liczbaSqrt);
						resultText = liczba1Str.toString();
						checkError();
					}
					else{
						double liczbaSqrt = Math.sqrt(Double.valueOf(liczba1Str.toString()));
						liczba1Str.setLength(0);
						liczba1Str.append(liczbaSqrt);
						resultText = liczba1Str.toString();
						checkError();

					}
				}
				
				else if (btn.contains("%")){
					liczba1Str.setLength(0);
					resultText = "";
				}
			}
			
			else{
				if (btn.matches("\\d+")){
					liczba2Str.append(btn);
					System.out.println("L2: "+liczba2Str.toString());
					resultText = liczba2Str.toString();
				}
				
				else if (btn.contains(".")&&!dotSet){
					liczba2Str.append(".");
					dotSet = true;
					resultText = liczba2Str.toString();
				}
				
				else if (btn.contains("%")){
					double liczbaPom = (Double.valueOf(liczba1Str.toString())*Double.valueOf(liczba2Str.toString()))/100;
					liczba2Str.setLength(0);
					liczba2Str.append(liczbaPom);
					resultText = liczba2Str.toString();
				}
				
				else if (btn.contains("sqrt")){
					double liczbaSqrt = Math.sqrt(Double.valueOf(liczba2Str.toString()));
					liczba2Str.setLength(0);
					liczba2Str.append(liczbaSqrt);
					resultText = liczba2Str.toString();
					checkError();

				}
				else if (btn.contains("+/-")&&liczba2Str.length()!=0){
					if(liczba2Str.charAt(0)=='-'){
						liczba2Str.deleteCharAt(0);
					}
					else{
						liczba2Str.insert(0, '-');
					}
					resultText = liczba2Str.toString();
				}
			}	
			newCalc();
		}
	}
	
	public String getBtnResult() {
		return btnResult;
	}

	public void setBtnResult(String btnResult) {
		//OBLICZENIA WYNIKOWE
		if ((btnResult.contains("=")&&liczba1Str.length()!=0&&liczba2Str.length()!=0)){
			drugaLiczba = false;
			liczba1 = Double.valueOf(liczba1Str.toString());
			liczba2 = Double.valueOf(liczba2Str.toString());
			
			if (getBtnParam().equals("+")){
				wynik = liczba1 + liczba2;
				resultText = String.valueOf(wynik);
				newCalc = true;
			}
			
			else if (getBtnParam().equals("-")){
				wynik = liczba1 - liczba2;
				resultText = String.valueOf(wynik);
				newCalc = true;
			}
			else if (getBtnParam().equals("/")){
				wynik = liczba1 / liczba2;
				resultText = String.valueOf(wynik);
				newCalc = true;
			}
			else if (getBtnParam().equals("*")){
				wynik = liczba1 * liczba2;
				resultText = String.valueOf(wynik);
				newCalc = true;
			}			
			checkError();
			newCalc();
		}		
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}




	

}
