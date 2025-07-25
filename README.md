# Getting Started
A simple ledger API for recording and retrieving money transactions which includes deposits, withdrawals, current balance, and transaction history while using an in-memory storage.

The following is required and must be installed to run this application on the system
* Java 17 or later
* Maven 3.6+

#
### Running the Application: 
open your terminal and run below code one after the other to start the application

* download the repository `git clone https://github.com/omotayooke/ledgerApplication.git`
* drill down to the directory where the code was cloned to `cd ledgerApplication`
* run `mvn spring-boot:run` to start the application

#
Once the application starts running, below are the API endpoint to test 

For deposit API
`curl -X POST "http://localhost:8080/api/ledger/deposit?description=deposit1&amount=200"`

For Withdraw API
`curl -X POST "http://localhost:8080/api/ledger/withdraw?description=withdraw1&amount=100"`

For Balance API
`curl "http://localhost:8080/api/ledger/balance"`

For Transaction History
`curl "http://localhost:8080/api/ledger/transactions"`
