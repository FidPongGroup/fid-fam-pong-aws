/**
 * VPC Module
 * Creates a Virtual Private Cloud with a single public subnet that
 * allows HTTP/HTTPS, SSH, and MYSQL traffic to pass through from internet
 *
 * this is like our own private network where all VMs will reside
 *
 * @TODO create a private subnet for non-public services to reside
 * @TODO create a multipe public and private subets to ensure availability
 */

/**
 * Variables Needed for the FidPong VPC Module
 */


variable "app_name" {
  description = "Name of the application supported by Infrastructure"
}

variable "env" {
  description = "Environment Level"
}

variable "cidr_block"{
  description = "CIDR BLOCK for VPC"
  default = "10.0.0.0/16"
}

variable "availability_zones" {
  type = "list"
}

/**
 * VPC, with CIDR Block Range
 */
resource "aws_vpc" "vpc" {
  cidr_block = "${var.cidr_block}"
  enable_dns_hostnames = true
  assign_generated_ipv6_cidr_block = true


  tags {
    Name = "${var.app_name}-${var.env}-vpc"
  }
}


/**
 * Interget Gateway, allows VMs residing in VPC to access Internet
 */
resource "aws_internet_gateway" "internet-gateway" {
  vpc_id = "${aws_vpc.vpc.id}"
  tags {
    Name = "${var.app_name}-${var.env}-ig"
  }

}

/**
 * Routing table, routes traffic destined to Interget to the Internet-gateway
 * For more information about networking, take a networking class
 */
resource "aws_route" "internet-access" {
  route_table_id = "${aws_vpc.vpc.main_route_table_id}"
  destination_cidr_block = "0.0.0.0/0"
  gateway_id = "${aws_internet_gateway.internet-gateway.id}"
}
resource "aws_route" "internet-access-ipv6" {
  route_table_id = "${aws_vpc.vpc.main_route_table_id}"

  destination_ipv6_cidr_block = "::/0"
  gateway_id = "${aws_internet_gateway.internet-gateway.id}"

}


output "vpc_id" {
  value = "${aws_vpc.vpc.id}"
}

output "public_subnets" {
  value = "${aws_subnet.public_subnets.*.id}"
}

output "private_subnets" {
  value = "${aws_subnet.private_subnets.*.id}"
}

