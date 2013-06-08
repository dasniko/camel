#!/bin/bash

DBNAME=camel
HOST=localhost

dropdb -e --host $HOST $DBNAME || true
createdb -e --host $HOST $DBNAME
psql -e --host $HOST -d $DBNAME -f schema_postgres.sql 
