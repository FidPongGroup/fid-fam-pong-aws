# FidPong Infrastructure

## Summary
In these directories we will place the Terraform Scripts to create our infrastructure


Terraform templates are written in a language known as HQL

## What Language is this?
- after some deliberation I settled on using Terraform as the framework of choice to maintain the infrastructure


## What is Terraform

- When writing Infrastructure as Code there are two main choices when running an applicaiton on AWS
    - AWS CloudFormation
        - An AWS Service
        - A Template File written in JSON or YAML
        - Submit via AWS console or AWS CLI
        - IDE support via Intellij Plugin
    - Terraform
        - A Third party application that supports almost all Cloud Formation Features
        - run from commandline
            - ex: ```terraform apply```
        - must download terraform binary
        - written in a language called HQL
        - IDE support via Intellij Plugin


[Click here to learn more about Terraform](https://www.terraform.io/)

## Why did you choose this framework?
- I use terraform at my job and wanted practice.

- Also writing JSON and YAML can get a bit irritating

## Getting started
- Start with the hello world on the main website.
- create a terrafrom.tfvars file in the root directory with necessary variables
- run terraform apply

## Project Overview

This project has the main.tf at the root level,
and each folder consists of a submodules responsible for a different aspect
of the web application.
