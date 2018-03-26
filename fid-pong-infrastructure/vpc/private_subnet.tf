resource "aws_subnet" "private_subnets" {

  count = "${length(var.availability_zones)}"

  cidr_block = "${cidrsubnet(var.cidr_block,8 ,count.index + aws_subnet.public_subnets.count)}"
  ipv6_cidr_block = "${cidrsubnet(aws_vpc.vpc.ipv6_cidr_block,8 ,count.index + aws_subnet.public_subnets.count)}"
  vpc_id = "${aws_vpc.vpc.id}"
  map_public_ip_on_launch = false
  availability_zone = "${var.availability_zones[count.index]}"

  assign_ipv6_address_on_creation = true

  tags {
    Name = "${var.app_name}-${var.env}-private-subnet-${var.availability_zones[count.index]}"
  }
}

resource "aws_egress_only_internet_gateway" "egress_only_gateway"{
  vpc_id = "${aws_vpc.vpc.id}"

}

resource "aws_eip" "nat_eip_address" {
  vpc = true
}

resource "aws_nat_gateway" "nat_gateway" {
  allocation_id = "${aws_eip.nat_eip_address.id}"
  subnet_id = "${aws_subnet.public_subnets.*.id[0]}"
}

resource "aws_route_table" "private_route_table" {
  depends_on = [
    "aws_egress_only_internet_gateway.egress_only_gateway",
    "aws_vpc.vpc"
  ]
  vpc_id = "${aws_vpc.vpc.id}"

  route {
    ipv6_cidr_block = "::/0"
    egress_only_gateway_id = "${aws_egress_only_internet_gateway.egress_only_gateway.id}"
  }
  route {
    cidr_block = "0.0.0.0/0"
    nat_gateway_id = "${aws_nat_gateway.nat_gateway.id}"
  }

  tags{
    Name = "${var.app_name}-${var.env}-private-route-table"
  }
}

resource "aws_route_table_association" "private_subnet_route_association" {
  depends_on = [
    "aws_vpc.vpc",
    "aws_subnet.private_subnets"
  ]
  count = "${length(var.availability_zones)}"
  route_table_id = "${aws_route_table.private_route_table.id}"
  subnet_id = "${aws_subnet.private_subnets.*.id[count.index]}"
}



resource "aws_network_acl" "private-acl" {
  vpc_id = "${aws_vpc.vpc.id}"

  subnet_ids = ["${aws_subnet.private_subnets.*.id}"]


  //Allow All Traffic in from VPC
  ingress {
    action = "allow"
    from_port = 0
    to_port = 0
    protocol = "-1"
    rule_no = 100
    cidr_block = "${aws_vpc.vpc.cidr_block}"
  }
  //Allow All Traffic in from VPC IPV6
  ingress {
    action = "allow"
    from_port = 0
    protocol = "-1"
    rule_no = 101
    to_port = 0
    ipv6_cidr_block = "${aws_vpc.vpc.ipv6_cidr_block}"
  }

  //Ephemeral Ports
  ingress {
    action = "allow"
    from_port = 32768
    to_port = 65535
    protocol = "tcp"
    rule_no = 500
    cidr_block = "0.0.0.0/0"
  }
  //Ephemeral Ports IPV6
  ingress {
    action = "allow"
    from_port = 32768
    to_port = 65535
    protocol = "tcp"
    rule_no = 501
    ipv6_cidr_block = "::/0"
  }
  //Allow HTTP Traffic out
  egress {
    action = "allow"
    from_port = 80
    to_port = 80
    protocol = "tcp"
    rule_no = 100
    cidr_block = "0.0.0.0/0"
  }
  //Allow HTTP Traffic out IPV6
  egress {
    action = "allow"
    from_port = 80
    to_port = 80
    protocol = "tcp"
    rule_no = 101
    ipv6_cidr_block = "::/0"
  }
  //Allow HTTPS traffic out
  egress {
    action = "allow"
    from_port = 443
    to_port = 443
    protocol = "tcp"
    rule_no = 200
    cidr_block = "0.0.0.0/0"
  }
  //Allow HTTPS traffic out IPV6
  egress {
    action = "allow"
    from_port = 443
    to_port = 443
    protocol = "tcp"
    rule_no = 201
    ipv6_cidr_block = "::/0"
  }
  //Ephemeral Ports
  egress {
    action = "allow"
    from_port = 32768
    to_port = 65535
    protocol = "tcp"
    rule_no = 500
    cidr_block = "${aws_vpc.vpc.cidr_block}"
  }
  //Ephemeral Ports
  egress {
    action = "allow"
    from_port = 32768
    to_port = 65535
    protocol = "tcp"
    rule_no = 501
    ipv6_cidr_block = "${aws_vpc.vpc.ipv6_cidr_block}"
  }
  tags {
    Name = "${var.app_name}-${var.env}-private-acl"
  }
}
