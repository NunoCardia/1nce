provider "aws" {
    profile = "default"
    region = "eu-west-1"
}

resource "aws_db_instance" "triangle" {
  identifier             = "triangle"
  instance_class         = "db.t2.micro"
  allocated_storage      = 5
  engine                 = "mysql"
  engine_version         = "5.7"
  username               = "root"
  password               = "thepassword"
  parameter_group_name = "default.mysql5.7"
  publicly_accessible    = true
  skip_final_snapshot    = true
  vpc_security_group_ids = []
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