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

variable "creator" {
  description = "Person/Service that is creating the infrastructure"
}

variable "env" {
  default = "dev"
  description = "Environment Level"
}

variable "rdb_schema_name" {
  default = "fidpong_db"
  description = "Name of the Application Database"
}

variable "rdb_admin_username" {
  default = "fid_admin"
  description = "The username for the RDS Database"
}
variable "rdb_admin_password" {
  description = "The master password for the RDS Database"
}