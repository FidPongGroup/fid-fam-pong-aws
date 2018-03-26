resource "aws_subnet" "dmz_subnet" {

  cidr_block = "${cidrsubnet(var.cidr_block,8 ,aws_subnet.public_subnets.count * 2)}"
  ipv6_cidr_block = "${cidrsubnet(aws_vpc.vpc.ipv6_cidr_block,8 ,aws_subnet.public_subnets.count * 2)}"
  vpc_id = "${aws_vpc.vpc.id}"
  map_public_ip_on_launch = true
  availability_zone = "${var.availability_zones[0]}"

  assign_ipv6_address_on_creation = true

  tags {
    Name = "${var.app_name}-dmz-subnet-${var.availability_zones[count.index]}-${var.env}"
  }
}

resource "aws_network_acl" "dmz-acl" {
  vpc_id = "${aws_vpc.vpc.id}"

  subnet_ids = ["${aws_subnet.dmz_subnet.id}"]

  //Allow HTTP Traffic In
  ingress {
    action = "allow"
    from_port = 80
    to_port = 80
    protocol = "tcp"
    rule_no = 100
    cidr_block = "0.0.0.0/0"
  }
  //Allow HTTP Traffic In Ipv6
  ingress {
    action = "allow"
    from_port = 80
    to_port = 80
    protocol = "tcp"
    rule_no = 101
    ipv6_cidr_block = "::/0"
  }
  //Allow HTTPS Traffic In
  ingress {
    action = "allow"
    from_port = 443
    to_port = 443
    protocol = "tcp"
    rule_no = 200
    cidr_block = "0.0.0.0/0"
  }
  //Allow HTTPS Traffic In
  ingress {
    action = "allow"
    from_port = 443
    to_port = 443
    protocol = "tcp"
    rule_no = 201
    ipv6_cidr_block = "::/0"
  }
  //Allow SSH Traffic in from internet
  ingress {
    action = "allow"
    from_port = 22
    to_port = 22
    protocol = "tcp"
    rule_no = 300
    cidr_block = "0.0.0.0/0"
  }
  //Allow SSH Traffic in from internet
  ingress {
    action = "allow"
    from_port = 22
    to_port = 22
    protocol = "tcp"
    rule_no = 301
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
  //Allow HTTP Traffic out
  egress {
    action = "allow"
    from_port = 80
    to_port = 80
    protocol = "tcp"
    rule_no = 100
    cidr_block = "0.0.0.0/0"
  }
  //Allow HTTP Traffic out in IPV6
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
  //Allow HTTPS traffic out in IPV6
  egress {
    action = "allow"
    from_port = 443
    to_port = 443
    protocol = "tcp"
    rule_no = 201
    ipv6_cidr_block = "::/0"
  }
  //Allow SSH traffic out to local network
  egress {
    action = "allow"
    from_port = 22
    to_port = 22
    protocol = "tcp"
    rule_no = 300
    cidr_block = "${aws_vpc.vpc.cidr_block}"
  }
  //Allow SSH traffic out to local network
  egress {
    action = "allow"
    from_port = 22
    to_port = 22
    protocol = "tcp"
    rule_no = 301
    ipv6_cidr_block = "${aws_vpc.vpc.ipv6_cidr_block}"
  }

  //Allow MySQL traffic out to local network
  egress {
    action = "allow"
    from_port = 3306
    to_port = 3306
    protocol = "tcp"
    rule_no = 400
    cidr_block = "${aws_vpc.vpc.cidr_block}"
  }
  //Allow MySQL traffic out to local network IPV6
  egress {
    action = "allow"
    from_port = 3306
    to_port = 3306
    protocol = "tcp"
    rule_no = 401
    ipv6_cidr_block = "${aws_vpc.vpc.ipv6_cidr_block}"
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
  //Ephemeral Ports
  egress {
    action = "allow"
    from_port = 32768
    to_port = 65535
    protocol = "tcp"
    rule_no = 501
    ipv6_cidr_block = "::/0"
  }
  tags {
    Name = "${var.app_name}-${var.env}-dmz-acl"
  }
}