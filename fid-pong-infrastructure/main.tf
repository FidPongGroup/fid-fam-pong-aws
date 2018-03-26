terraform {
  backend "s3" {
    bucket = "fid-pong-tf-remote-state-dev-us-east-1-bucket"
    key = "infrastructure/main/terraform.tfstate"
    region = "us-east-1"
  }
}

provider "aws" {
  region = "${var.aws_region}"

  profile = "${var.aws_profile}"
}

data "aws_availability_zones" "zones" {}


module "vpc" {
  source = "./vpc"
  app_name = "${var.app_name}"
  env = "${var.env}"
  availability_zones = "${data.aws_availability_zones.zones.names}"
}

module "ecs_cluster" {
  source = "./ecs"
  vpc_id = "${module.vpc.vpc_id}"
  public_subnets = "${module.vpc.public_subnets}"
  private_subnet = "${module.vpc.private_subnets[0]}"
}

output "alb_id" {
  value = "${module.ecs_cluster.alb_id}"
}

output "ecs_cluster" {
  value = "${module.ecs_cluster.ecs_cluster}"
}

output "vpc_id" {
  value = "${module.vpc.vpc_id}"
}

