output "alb_dns_name" {
  value = aws_lb.app.dns_name
}

output "rds_proxy_endpoint" {
  value = aws_db_proxy.main.endpoint
}

output "rds_endpoint" {
  value = aws_db_instance.postgres.address
}

output "artifact_bucket" {
  value = aws_s3_bucket.artifacts.bucket
}