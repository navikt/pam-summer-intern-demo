# pam-godkjenningsregistre
A service that looks up the approval status of companies registered in Arbeidstilysnets godkjenningsregistre.

## About the current version
The initial version of this service was developed by summer interns during the summer of 2020. Due to a lack of existing APIs related to the retrieval of data from Arbeidstilysnets godkjenningsregistre, the only registy initially available for lookups was the cleaning registy (Renholdsregisteret). It takes an organization number (Orgnr) as input and returns a json as output.
 
## Dependencies
The project requires the following to run (minimum):
- SpringBoot 2.3.1
- Gradle 6.5

## How to run this application locally
1. Clone this repository:
```
git clone https://github.com/navikt/pam-godkjenningsregistre.git
```
2. Build the application:
```
./gradlew build
```
3. Run the application:
```
./gradlew bootrun
```
6. Lookup should become available on URL: localhost:8080/(Orgnr)

## The Summer Intern Team
* [Yong Bin Kwon](https://github.com/yongbinkwon)
* [Martin Wangen-Eriksen](https://github.com/martinwe001)
* [Catriona Tørklep](https://github.com/catriont)
