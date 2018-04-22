
variable "aws_region" {
  default = "us-east-1"
  description = "AWS Region where infrastructure will be deployed"
}
variable "aws_profile" {
  default = "default"
  description = "aws credentials profile that will be used"
}

variable "app_name" {
  default = "fid-pong"
  description = "Name of the application supported by Infrastructure"
}

variable "env" {
  default = "dev"
  description = "Environment Level"
}

terraform {
  backend "s3" {
    bucket = "fid-pong-tf-remote-state-dev-us-east-1-bucket"
    key = "infrastructure/web-service/terraform.tfstate"
    region = "us-east-1"
  }
}

provider "aws" {
  region = "${var.aws_region}"

  profile = "${var.aws_profile}"
}

data "terraform_remote_state" "main_infastructure" {
  backend = "s3"
  config {
    bucket = "fid-pong-tf-remote-state-dev-us-east-1-bucket"
    key = "infrastructure/main/terraform.tfstate"
    region = "us-east-1"
  }
}

resource "aws_ecs_task_definition" "web_service_task" {
  container_definitions = "${file("container_def.json")}"
  family = "fid-pong-task-def"
}

resource "aws_alb_target_group" "nginx_tg" {
  name = "${var.app_name}-${var.env}-nginx-tg"
  port = 80
  protocol = "HTTP"
  vpc_id = "${data.terraform_remote_state.main_infastructure.vpc_id}"

}

resource "aws_alb_listener" "nginx_listener" {
  "default_action" {
    target_group_arn = "${aws_alb_target_group.nginx_tg.id}"
    type = "forward"
  }
  load_balancer_arn = "${data.terraform_remote_state.main_infastructure.alb_id}"
  port = 80
}

resource "aws_ecs_service" "web_service" {
  name = "${var.app_name}-${var.env}-service"
  task_definition = "${aws_ecs_task_definition.web_service_task.id}"
  cluster = "${data.terraform_remote_state.main_infastructure.ecs_cluster}"
  desired_count = 2

  load_balancer {
    container_name = "nginx"
    container_port = 80
    target_group_arn = "${aws_alb_target_group.nginx_tg.id}"
  }

}
