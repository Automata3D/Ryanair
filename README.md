Author: Javier del Olmo
https://www.linkedin.com/in/JavierDelOlmoLianes

--------------------
	INTRODUCTION
--------------------
The current automation framework to automate the scenario proposed on Ryanair's documentation where the user tries to pay a booking with an invalid card. 

The framework has been built with the following tools:

- IntelliJ as IDE
- Maven 3.3.9 as build tool
- Selenium 3.141.59
- Java 8
- Cucumber as BDD tool (Including Cucumber & Gherkin plugin in IntelliJ) & reporting layer



The scenario contained in this project can be executed in chrome or firefox (the last version of these drivers have been included as resources in the project). 

In order to run it successfully it´s mandatory:
- Firefox 65.0.2 (64-bit)
- Chrome Version 73.0.3683.75 (Build oficial) (64 bits)

Any variation on these browsers versions may require different driver versions.

-------------
	HOW TO
-------------
* INSTALL
1. Unzip the project file whenever in your computer
2. Open the project with IntelliJ
3. Since the .idea settings have been included, you should see the following run configurations:
	- mvn clean install (clean install -U)
	- ryanair firefox (verify -Dbrowser=firefox)
	- ryanair chrome (verify -Dbrowser=chrome)

Otherwise, please proceed to create these configurations.

* RUN THE SCENARIOS

The automated test can be found in "CardPayment.feature" file.

To run it:
1. Execute "mvn clean install" in order to download all the maven dependencies
2. Execute "ryanair firefox" or "ryanair chrome" in order to run the test case on the prefered browser.

-----------------
	REPORTING
-----------------
As mentioned before, Cucumber html has been chosen to build the reporting layer.

Cucumber report can be found in: "*\Ryanair\target\cucumber-html-report" (after executing the automated scenario)

All files have been included in the repo so results can be seen from the very beginning.

--------------------------------------------------------------------
	SOME CONSIDERATIONS (Regarding implemented scenario)
--------------------------------------------------------------------
In order to decrease sold out tickets probability, number of passengers has been limited to:
- Number of Adults in range [1,10]
- Number of Children in range [0,10]

In order to reduce non-required steps simplifying the test logic, following considerations have been taken:
- One way flight always
- Basic luggage option is chosen for all passengers
- Random allocation for all passengers when possible
- Travel in group promotion rejected (when there is a high number of passengers)
- Renting car option rejected
- Passengers' names are generated randomly (5 characters in [a-z])
- Passengers' title is Mr. by default
- Default mobile phone number is saved on Ryanair account
- There are not asserts everywhere in the code so not all the corner cases have been taken in mind.
  Examples about where the test will break:
  	- If departure/destination airport does not exist or there is no connection between them
  	- If there are no available flights for the selected fly out date
  	- If there are no enough tickets for the selected amount of passengers


Regarding card & billing details, these are the requirements:
- Card number must be 16 characters(digits) length (whitespaces not taken in mind)
- Card holder name is generated randomly (5 characters in [a-z])
- Card expiration date should follow format: MM/yy (where month in [1-12] and year in [19-29])
- CVV gets limited to 3 characters length (Ryanair website does it itself)
- City and Country are set by default to: Dublin/Ireland
