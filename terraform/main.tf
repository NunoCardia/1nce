provider "aws" {
    profile = "nuno"
    region = "eu-west-3"
}

resource "aws_security_group" "triangle_rds_access" {
  name = "triangle_rds_access"
  description = "Allow access to the triangle RDS instance"

  ingress {
  from_port = 3306
  to_port = 3306
  protocol = "tcp"
  security_groups = [ aws_security_group.triangle-app-sg.id]
  }
}

resource "aws_db_instance" "triangle" {
  identifier             = "triangle"
  instance_class         = "db.t2.micro"
  allocated_storage      = 5
  engine                 = "mysql"
  engine_version         = "5.7"
  username               = "root"
  password               = "password"
  parameter_group_name = "default.mysql5.7"
  publicly_accessible    = false
  skip_final_snapshot    = true
  vpc_security_group_ids = [aws_security_group.triangle_rds_access.id]
}

resource "aws_iam_role" "triangle-app-rds-access" {
  name = "triangle-app-rds-access"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Action = "sts:AssumeRole",
        Effect = "Allow",
        Principal = {
          Service = "ec2.amazonaws.com"
        }
      }
    ]
  })
}

resource "aws_iam_policy" "triangle-app-rds-access" {
  name = "triangle-app-rds-access"

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Action = [
          "rds:*"
        ],
        Effect = "Allow",
        Resource = "arn:aws:rds:*:*:db:triangle"
      }
    ]
  })
}

resource "aws_iam_policy_attachment" "triangle-app-policy-role" {
  name = "triangle-app-attachment"
  roles = [aws_iam_role.triangle-app-rds-access.name]
  policy_arn = aws_iam_policy.triangle-app-rds-access.arn
}

resource "aws_iam_instance_profile" "triangle-app-rds-access" {
  name  = "triangle-app-profile"
  role = aws_iam_role.triangle-app-rds-access.name
}

resource "aws_security_group" "triangle-app-sg" {
  name        = "triangle-app-sg"
  description = "Allow incoming traffic on port 8080"

  ingress {
    from_port = 8080
    to_port   = 8080
    protocol  = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port = 22
    to_port   = 22
    protocol  = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

locals {
  rds_instance_endpoint = aws_db_instance.triangle.endpoint
}

resource "aws_instance" "triangle-app" {
  ami           = "ami-0ca5ef73451e16dc1"
  instance_type = "t2.micro"
  security_groups = [aws_security_group.triangle-app-sg.name]
  iam_instance_profile = aws_iam_instance_profile.triangle-app-rds-access.name
  user_data = templatefile("./scripts/backend.sh", {rds_instance_endpoint = local.rds_instance_endpoint})
}


resource "aws_cloudwatch_log_group" "triangle-logs" {
  name = "triangle-logs"
  retention_in_days = 30
}

resource "aws_cloudwatch_log_stream" "triangle-stream" {
  name           = "triangle-stream"
  log_group_name = aws_cloudwatch_log_group.triangle-logs.name
}

resource "aws_cloudwatch_log_metric_filter" "error-metric-filter" {
  name           = "error-metric-filter"
  log_group_name = aws_cloudwatch_log_group.triangle-logs.name
  pattern        = "ERROR"
  metric_transformation {
    name      = "ErrorLogMetric"
    namespace = "TriangleMetrics"
    value     = "1"
  }
}

resource "aws_cloudwatch_metric_alarm" "error-alarm" {
  alarm_name = "error-log-alarm"
  metric_name         = aws_cloudwatch_log_metric_filter.error-metric-filter.name
  threshold           = "0"
  statistic           = "Sum"
  comparison_operator = "GreaterThanThreshold"
  datapoints_to_alarm = "1"
  evaluation_periods  = "1"
  period              = "30"
  namespace           = "TriangleMetrics"
}