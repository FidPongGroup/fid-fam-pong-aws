resource "aws_security_group" "alb_sg" {

  vpc_id = "${var.vpc_id}"

  name = "${var.app_name}-${var.env}-alb-sg"

  description = "Security Group for an Application Loadbalancer that supports an ECS service"

  ingress {
    from_port = 80
    protocol = "tcp"
    to_port = 80
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port = 80
    protocol = "tcp"
    to_port = 80
    ipv6_cidr_blocks = ["::/0"]
  }
  ingress {
    from_port = 8080
    protocol = "tcp"
    to_port = 8080
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port = 8080
    protocol = "tcp"
    to_port = 8080
    ipv6_cidr_blocks = ["::/0"]
  }
  ingress {
    from_port = 443
    protocol = "tcp"
    to_port = 443
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port = 443
    protocol = "tcp"
    to_port = 443
    ipv6_cidr_blocks = ["::/0"]
  }
  ingress {
    from_port = 8443
    protocol = "tcp"
    to_port = 8443
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port = 8443
    protocol = "tcp"
    to_port = 8443
    ipv6_cidr_blocks = ["::/0"]
  }
}

resource "aws_security_group" "ecs_instance_sg" {

  depends_on = ["aws_security_group.alb_sg"]

  vpc_id = "${var.vpc_id}"

  name = "${var.app_name}-${var.env}-ecs-instance-sg"

  description = "Security group for an ECS instance"

  ingress {
    from_port = 0
    protocol = "-1"
    to_port = 0
    security_groups = ["${aws_security_group.alb_sg.id}"]
  }

  egress {
    from_port = 0
    to_port = 0
    protocol = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
  egress {
    from_port = 0
    to_port = 0
    protocol = "-1"
    ipv6_cidr_blocks = ["::/0"]
  }
}

resource "aws_security_group_rule" "alb_egress_rule" {
  depends_on = [
      "aws_security_group.alb_sg",
      "aws_security_group.ecs_instance_sg"
  ]
  security_group_id = "${aws_security_group.alb_sg.id}"
  from_port = 0
  protocol = "tcp"
  source_security_group_id = "${aws_security_group.ecs_instance_sg.id}"
  to_port = 0
  type = "egress"

}
