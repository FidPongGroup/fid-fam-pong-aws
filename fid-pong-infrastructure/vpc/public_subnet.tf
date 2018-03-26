resource "aws_subnet" "public_subnets" {

  count = "${length(var.availability_zones)}"

  cidr_block = "${cidrsubnet(var.cidr_block,8 ,count.index)}"
  ipv6_cidr_block = "${cidrsubnet(aws_vpc.vpc.ipv6_cidr_block,8 ,count.index)}"
  vpc_id = "${aws_vpc.vpc.id}"
  map_public_ip_on_launch = true
  availability_zone = "${var.availability_zones[count.index]}"

  assign_ipv6_address_on_creation = true

  tags {
    Name = "${var.app_name}-${var.env}-public-subnet-${var.availability_zones[count.index]}"
  }
}

/**
 * Access Control List for our Subnet
 * Like a firewall, the first layer of security for our application
 * Allows inbound: HTTP, HTTPS, SSH, MySQL, Ephemeral Ports
 * Allows outbound: HTTP, HTTPS, Ephemeral Ports
 * For more information of about Ephemeral Ports see readme
 */
resource "aws_network_acl" "public-acl" {
  vpc_id = "${aws_vpc.vpc.id}"

  subnet_ids = ["${aws_subnet.public_subnets.*.id}"]

  //Allow all traffic in
  ingress {
    action = "allow"
    from_port = 0
    to_port = 0
    protocol = "-1"
    rule_no = 100
    cidr_block = "0.0.0.0/0"
  }
  //Allow all Traffic in IPV6
  ingress {
    action = "allow"
    from_port = 0
    to_port = 0
    protocol = "-1"
    rule_no = 101
    ipv6_cidr_block = "::/0"
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
  //Ephemeral Ports
  ingress {
    action = "allow"
    from_port = 32768
    to_port = 65535
    protocol = "tcp"
    rule_no = 501
    ipv6_cidr_block = "::/0"
  }
  //Allow all Traffic out
  egress {
    action = "allow"
    from_port = 0
    to_port = 0
    protocol = "-1"
    rule_no = 100
    cidr_block = "0.0.0.0/0"
  }
  //Allow all Traffic out IPV6
  egress {
    action = "allow"
    from_port = 80
    to_port = 80
    protocol = "tcp"
    rule_no = 101
    ipv6_cidr_block = "::/0"
  }

  //Ephemeral Ports
  egress {
    action = "allow"
    from_port = 32768
    to_port = 65535
    protocol = "tcp"
    rule_no = 500
    cidr_block = "0.0.0.0/0"
  }
  //Ephemeral Ports IPV6
  egress {
    action = "allow"
    from_port = 32768
    to_port = 65535
    protocol = "tcp"
    rule_no = 501
    ipv6_cidr_block = "::/0"
  }
  tags {
    Name = "${var.app_name}-${var.env}-public-acl"
  }
}