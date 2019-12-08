	
public class jsonIO {
		
	ArrayList<Bill> importFinanceData(String filename) {
		
		JSONObject json = new JSONObject(jsonString);
		JSONArray expensesObj = json.getArray("expenses");
		
		bills = ArrayList<Bill>(expensesObj.length());
		
		for ( JSONObject expense: expensesObj ) {
			Bill bill = Bill(expense);
			bills.add(bill);
			articles.addAll(bill.getArticles());
			
			paymentMethods.add(bill.getPaymentMethod());
			payees.add(bill.getPayee());
			
			tags.addAll(bill.getTags());
			articleNames.addAll(bill.getArticleNames());
		}
		return bills;
	}
	
	
	void saveFinanceData(ArrayList<Bill> bills, String filename) {
		
		String path = this.filesDir.absolutePath + "/" + dataFileName
		
		String billsStr = "[";
		for (Bill bill: bills) {
			billsStr += bill.toString() + ",";
		}
		billsStr = billsStr[billsStr.length-2] + ']';
		
		String billsStr = '{"expenses": [' + billsStr + ']}';
		
		
		File file = FileWriter(path)
        try {
            file.write(billsStr)
        }
        catch (e: Exception){
            Log.e(tag, "Writing to $path failed!")
            return false
        }
        finally {
            file.flush()
            file.close()
        }
        
	}
}
