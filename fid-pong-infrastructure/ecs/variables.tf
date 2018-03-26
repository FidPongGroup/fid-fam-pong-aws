variable "app_name" {
  default = "fid-pong"
  description = "Name of the application supported by Infrastructure"
}


variable "env" {
  default = "dev"
  description = "Environment Level"
}

variable "vpc_id" {
  description = "The VPC where ECS cluster will be deployed"
}

variable "public_subnets" {
  type = "list"
}

variable "private_subnet" {

}