/**
 * Variables Needed for the FidPong VPC Module
 */


variable "app_name" {
  default = "fid-pong"
  description = "Name of the application supported by Infrastructure"
}

variable "creator" {
  description = "Person/Service that is creating the infrastructure"
}

variable "env" {
  default = "DEV"
  description = "Environment Level"
}


variable "vpc_id" {
  description = "id of the VPC where RDS will reside"
}

variable "db_subnet_ids" {
  type = "list"
  description = "subnet ID where the Database will live"
}

variable "storage_size" {
  default     = "5"
  description = "Storage size in GB"
}

variable "engine" {
  default     = "mysql"
  description = "Engine type, example values mysql, postgres"
}

variable "engine_version" {
  description = "Engine version"
  type = "map"
  default = {
    mysql    = "5.7.17"
    postgres = "9.6.2"
  }
}

variable "db_instance_class" {
  default     = "db.t2.micro"
  description = "Databse instance class Instance class"
}

variable "db_name" {
  description = "db name"
}

variable "db_username" {
  description = "User name"
}

variable "db_password" {
  description = "password, provide through your ENV variables"
}