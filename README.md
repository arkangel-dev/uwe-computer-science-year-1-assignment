# UFCFC3-30-1 Introduction to OO Systems Development (2019-5) 
## uwe-computer-science-year-1-assignment
This is the first assignment for the UWE Bsc (Hons) Computer science first year project

## Voter Program
### Changes
- The voter program runs by storing the the data of the users and the candidate in text files that are comma delimited. And uses that for storing stuff. The bare bones are finshed now
- The contructors have been added.
- Incremented the selection index
- Added ID Card Field for Validation
- Added check to make sure that officers cannot delete their own accounts while logged in
- Added check to make sure that there is always atleast 1 officer in the system
- Added check to make sure that there is more than 1 candidate to start the voting function
- Added check to make sure that there are users capable of voting other than the candidate and officers

### Classes
```java
class Voter {
    String Identity = "None";
    String UserName = "None";
    String Passphrase = "None";
    String IDCard = "None";
    Boolean Voted = false;
    
    public Voter(String IdentityIn, String UsernameIn, String PassphraseIn, String IDCardIn){
        Identity = IdentityIn;
        UserName = UsernameIn;
        Passphrase = PassphraseIn;
        IDCard = IDCardIn;
    }
}

class Candidate extends Voter{
    int votecount = 0;
    public Candidate(String IdentityIn, String UsernameIn, String PassphraseIn, String IDCardIn) {
        super(IdentityIn, UsernameIn, PassphraseIn, IDCardIn);
    }

}

class Officer extends Voter{
    public Officer(String IdentityIn, String UsernameIn, String PassphraseIn, String IDCardIn) {
        super(IdentityIn, UsernameIn, PassphraseIn, IDCardIn);
    }
}
```



## Restaurant Program
### Changes
- Classes created
- Dynamic dot spacers added (dope)
- Ranged input
- Input validating function (which could've really been implemented easily with a single simple regex)
- Added javadocs to make documentation process easier
- Added multiple classes to make the code more bearable to read
- Added Sales class
- Added email address field to Customer class
- Added email notifcation
- Fixed stupidity


### Classes
```java
class FoodItem {
    String name = "None";
    Double price = 0.0;
    Double rating = 0.0;
    Integer orderCount = 0;
    
    public FoodItem(String nameIn, Double priceIn, Double ratingIn, Integer orderCountIn){
        name = nameIn;
        price = priceIn;
        rating = ratingIn;
        orderCount = orderCountIn;
    }
}

class Customer {
    String id = "None";
    String name = "None";
    double averagespending = 0.00;
    int visits = 0;
    
    public Customer(String idIn, String nameIn, Double averagespendingIn, Integer visitsIn){
        id = idIn;
        name = nameIn;
        averagespending = averagespendingIn;
        visits = visitsIn;
    }
}

class Sale {
    String saleId = "None";
    double revenue = 0.0;
    Date datetime = new Date();
    String customername = "None";
    String emailaddress = "None";
    
    public Sale(String saleIdIn, double revenueIn, Date datetimeIn, String customernameIn, String emailaddressIn){
        saleId = saleIdIn;
        revenue = revenueIn;
        datetime = datetimeIn;
        customername = customernameIn;
        emailaddress = emailaddressIn;
    }
}
```