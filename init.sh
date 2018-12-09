#!/bin/bash

PRJS="ftk-web-b ftk-web-p ftk-server-api ftk-server ftk-chain-api ftk-chain"

for PRJ in $PRJS; do
	mkdir -p $PRJ/src/main/java
	mkdir -p $PRJ/src/main/resources
	mkdir -p $PRJ/src/test/java
	mkdir -p $PRJ/src/test/resources
done
