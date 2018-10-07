#!/bin/bash

curl -X POST -H 'Content-Type: application/json' --data '{ "beerStyle": "Weissbier", "min": -1, "max": 3 }' 'http://localhost:8080/beers'
curl -X POST -H 'Content-Type: application/json' --data '{ "beerStyle": "Pilsens", "min": -2, "max": 4 }' 'http://localhost:8080/beers'
curl -X POST -H 'Content-Type: application/json' --data '{ "beerStyle": "Weizenbier", "min": -4, "max": 6 }' 'http://localhost:8080/beers'
curl -X POST -H 'Content-Type: application/json' --data '{ "beerStyle": "Red ale", "min": -5, "max": 5 }' 'http://localhost:8080/beers'
curl -X POST -H 'Content-Type: application/json' --data '{ "beerStyle": "India pale ale", "min": -6, "max": 7 }' 'http://localhost:8080/beers'
curl -X POST -H 'Content-Type: application/json' --data '{ "beerStyle": "IPA", "min": -7, "max": 10 }' 'http://localhost:8080/beers'
curl -X POST -H 'Content-Type: application/json' --data '{ "beerStyle": "Dunkel", "min": -8, "max": 2 }' 'http://localhost:8080/beers'
curl -X POST -H 'Content-Type: application/json' --data '{ "beerStyle": "Imperial Stouts", "min": -10, "max": 13 }' 'http://localhost:8080/beers'
curl -X POST -H 'Content-Type: application/json' --data '{ "beerStyle": "Brown ale", "min": 0, "max": 14 }' 'http://localhost:8080/beers'

