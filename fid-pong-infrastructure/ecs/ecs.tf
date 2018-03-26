resource "aws_ecs_cluster" "app_cluster" {
  name = "${var.app_name}-${var.env}-cluster"
}

data aws_ami "aws_ecs_ami"{
  most_recent = true
  name_regex = "amzn-ami-.*-amazon-ecs-optimized"
  owners=["amazon"]
}

data "template_file" "ecs_user_data" {
  template = "${file("${path.module}/user_data.template.sh")}"
  vars {
    cluster_name = "${aws_ecs_cluster.app_cluster.name}"
  }
}

resource "aws_key_pair" "ecs_instance_key_pair" {
  public_key = "${file("${path.module}/ecs_instance_key.pem.pub")}"
  key_name = "${var.app_name}-${var.env}-key"
}

resource "aws_instance" "ecs_instances" {
  depends_on = ["aws_iam_instance_profile.ecs_instance_profile", "aws_ecs_cluster.app_cluster"]

  ami = "${data.aws_ami.aws_ecs_ami.id}"
  instance_type = "t2.micro"
  vpc_security_group_ids = ["${aws_security_group.ecs_instance_sg.id}"]
  subnet_id = "${var.private_subnet}"
  user_data = "${data.template_file.ecs_user_data.rendered}"
  iam_instance_profile = "${aws_iam_instance_profile.ecs_instance_profile.id}"
  key_name = "${aws_key_pair.ecs_instance_key_pair.id}"

  tags {
    Name = "${var.app_name}-${var.env}-ecs-instance"
  }
}

resource "aws_alb" "ecs_alb" {
  name = "${var.app_name}-${var.env}-ecs-alb"
  internal = false
  subnets = ["${var.public_subnets}"]
  security_groups = ["${aws_security_group.alb_sg.id}"]
  

}



resource "aws_iam_role" "ecs_instance_role" {
  name = "${var.app_name}-${var.env}-ecs-instance-role"

  assume_role_policy = <<-EOF
    {
      "Version": "2012-10-17",
      "Statement": [
        {
          "Action": "sts:AssumeRole",
          "Principal": {
            "Service": "ec2.amazonaws.com"
          },
          "Effect": "Allow",
          "Sid": ""
        }
      ]
    }
    EOF

}

resource "aws_iam_role_policy_attachment" "ecs_instance_role_policy_attachement" {
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonEC2ContainerServiceforEC2Role"
  role = "${aws_iam_role.ecs_instance_role.name}"
}

resource "aws_iam_instance_profile" "ecs_instance_profile" {
  depends_on = ["aws_iam_role.ecs_instance_role"]
  name = "${var.app_name}-${var.env}-ecs-instance-profile"
  role = "${aws_iam_role.ecs_instance_role.id}"
}

output "alb_id" {
  value = "${aws_alb.ecs_alb.id}"
}

output "ecs_cluster" {
  value = "${aws_ecs_cluster.app_cluster.id}"
}



