/**
 * Module responsible for the creation of a MySQL RDS Instance
 * Initially this will be one single instance
 * @TODO Reimplement as Aurora Cluster to allow for high availablity
 */

data "aws_vpc" "vpc" {
  id = "${var.vpc_id}"
}

/**
 * A single MySQL database instance
 */
resource "aws_db_instance" "fid-pong-db" {
  depends_on = ["aws_security_group.rds-sg"]

  identifier = "fid-pong-rds-${lower(var.creator)}-${lower(var.env)}"

  storage_type         = "gp2"
  engine               = "${var.engine}"
  engine_version       = "${lookup(var.engine_version, var.engine)}"
  instance_class       = "${var.db_instance_class}"
  name                 = "${var.db_name}"
  username             = "${var.db_username}"
  password             = "${var.db_password}"
  allocated_storage = "${var.storage_size}"
  vpc_security_group_ids = ["${aws_security_group.rds-sg.id}"]
  allow_major_version_upgrade = true
  db_subnet_group_name = "${aws_db_subnet_group.db-subnets.id}"


  final_snapshot_identifier = "${var.app_name}-RDS-Backup-${var.env}"

  tags {
    Name = "${var.app_name}-RDS-${var.env}"
    Creator = "${var.creator}"
  }

}

resource "aws_db_subnet_group" "db-subnets" {
  subnet_ids = ["${var.db_subnet_ids}"]
}

resource "aws_security_group" "rds-sg" {
  name        = "main_rds_sg"
  description = "Allow all inbound traffic"
  vpc_id      = "${var.vpc_id}"

  ingress {
    from_port   = 3306
    to_port     = 3306
    protocol    = "TCP"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 5432
    to_port     = 5432
    protocol    = "TCP"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags {
    Name = "FidPong-RDS-SecurityGroup"
  }
}

output "rds_endpoint" {
  value = "${aws_db_instance.fid-pong-db.endpoint}"
}