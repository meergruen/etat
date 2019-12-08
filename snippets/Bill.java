
public class Bill {
	
	DateFormat dateFormat = SimpleDateFormat("yyyy-MM-dd");
	static private int count = 0;

	Date paymentDate;
	String payee;
	String paymentMethod;
	String currencySymbol;
	ArrayList<Article> items;
	
	int billNumber;
	
	public Bill(JSONObject obj) {
		
		billNumber = count;
		++count;
		
		paymentDate = dateFormat.parse(expense.getString("date"));
		payee = obj.getString("payee");
		currencySymbol = obj.getString("currency");
		paymentMethod = obj.getString("payment");
		JSONArray jsonItems = obj.getString("items");
		
		for ( JSONObject jsonItem: jsonItems ) {
			items.add(Article(jsonItem, this));
		}
	}
	
	Bill(String payee, String paymentMethod, Date paymentDate, Article[] items) {
		
		billNumber = count;
		++count;
		
		paymentDate = paymentDate;
		payee = payee;
		paymentMethod = paymentMethod;
		items = items;
	}
	
	
	Bill() {
		paymentDate = Date();
		payee = "";
		paymentMethod = "";
		items = ArrayList<Article>();
		billNumber = count;
		++count;
	}
	
	
	Date   getDate()              { return this.paymentDate;   }
	String getPayee()             { return this.payee;         }
	String getPaymentMethod()     { return this.paymentMethod; }
	ArrayList<Article> getItems() { return this.items;         }
	
	setDate(Date date)                     { this.paymentDate = date;                   }
	setDate(int year, int month, int day)  { this.paymentDate = Date(year, month, day); }
	setDate(Calendar cal)                  { this.paymentDate = Date(cal.get(Calendar.YEAR),
		                                                             cal.get(Calendar.MONTH),
		                                                             cal.get(Calendar.DAY_OF_MONTH)); }
	setPayee(String payee)                 { this.payee = payee;                        }
	setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod;        }
	setItems(ArrayList<Article> items)     { this.items = items;                        }
	
	addItem(Article item)                  { this.items.add(item); }
	
	public getTotalString() {
		
		
		int total = 0;
		for (Article item: items) {
			total += item.getPrice();
		}
		
		String totalStr = System.out.format(Locale.getDefault().getDisplayLanguage(), "%-10.2f%n%n", total);
		return totalStr + " " + this.currencySymbol;
	};
	
	public getItemPreview() {
		
		String itemsStr = '';
		for (Article item: items) {
			itemsStr += item.getName() + ', ';
		}
		
		return itemsStr.substring(0, itemsStr.length-2);
	}
	
	public toString() {
		
		String itemsStr = '[';
		for (Article item: items) {
			itemsStr += '"' + item.toString() + '",';
		}
		itemsStr[itemsStr.length-2] = ']';
		
		return '{"date":"' + dateFormat.format(paymentDate) + 
		     '", "payee":"' + payee +
			 '", "payment":"' + paymentMethod + 
			 '", "currency":"' + currencySymbol + 
		     '", "items":[' + itemsStr + ']}';
	}

}
