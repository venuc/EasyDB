# EasyDB
A very simple API to access databases.

An effort to provide a wrapper over the stock JDBC API. Hopefully this should be simple to use
as one just has to setup a handle (DB handle) to the database and from there it's the responsibility
of the RawQueryHandler to handle all the queries. One can have multiple handles to multiple databases
simultaneously.

For code samples, please see package: org.easydb.test.client

When SELECTing, the basic idea is:
1. to represent a row of data being fetched from the database as an entity
2. a query returns an ArrayList of entities

When INSERTing or UPDATEing, the basic idea is:
1. pass a query to the DB
2. get the number of rows impacted by the query
3. batch updates / inserts returns an array of int for every query passed
4. batch updates accept queries that do not return a resultset.

This is a work in progress so please feel free to point out bugs and suggest improvements.

Supported databases: Oracle, MySQL.

TO DO:
1. Can't call stored procedures
