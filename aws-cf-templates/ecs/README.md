# AWS ECS Template

Here we will have the ECS template that creates the cluster from which our docker instances will be run

As a reminder:

+ EC2 is AWS's VM creation Service

+ ECS is an abstraction on top of EC2 that can run Docker containers

The template should do the following

- create an EC2 cluster

- be correctly configured to run a docker app

- expose only ports 80 and 443

[Click here to learn more about EC2](https://aws.amazon.com/ec2/?nc2=h_m1)

[Click here to learn more about ECS](https://aws.amazon.com/ecs/?nc2=h_m1)