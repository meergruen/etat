import android.app.Application;

public class EtatApp extends Application {
	
  	private String dataFileName = "mockdata.json";
	
	private ArrayList<Bill> bills;
	private ArrayList<Article> articles;
	
	private Set<String> tags;
	private Set<String> payees;
	private Set<String> paymentMethods;
	private Set<String> articleNames;
	
	public String getBills()          { return bills; }
	public String getTags()           { return tags; }
	public String getPayees()         { return payees; }
	public String getPaymentMethods() { return paymentMethods; }
	public String getArticleNames()   { return articleNames; }
  
	public void initializeData() {
	
		bills = jsonIO().importFinanceData(dataFileName);
				
		for ( Bill bill: bills ) {
			articles.addAll(bill.getArticles());
			
			paymentMethods.add(bill.getPaymentMethod());
			payees.add(bill.getPayee());
			
			tags.addAll(bill.getTags());
			articleNames.addAll(bill.getArticleNames());
		}
	}
	
	public saveData(){
		jsonIO().saveFinanceData(bills, dataFileName);
	}
  
}
