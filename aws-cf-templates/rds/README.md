# AWS RDS Template

This directory represents the template for RDS (Relational Database Service)

RDS is a managed database service that takes care of almost all DBA tasks related to a DB

We will be using Amazon Aurora (specifically the MySQL compatible version) for our first iteration


Things to think about:

+ Fault Tolerant: can we recover automatically if it DB instance goes down
+ Available: can we recover if an availability zone goes down
+ Performance: what happens when we get hit with a lot of traffic

[Click here to learn more about RDS](https://aws.amazon.com/rds/?nc2=h_m1)

[Click here to learn more about Aurora](https://aws.amazon.com/rds/aurora/?nc2=h_m1)