#!/bin/bash

# One beerStyle
curl -X POST -H 'Content-Type: application/json' --data '{"temperature": -2 }' 'http://localhost:8080/duffman'

# More than one beerStyle
curl -X POST -H 'Content-Type: application/json' --data '{"temperature": 1 }' 'http://localhost:8080/duffman'

# Does not exist
curl -X POST -H 'Content-Type: application/json' --data '{"temperature": 19 }' 'http://localhost:8080/duffman'
