variable "aws_region" {
  type    = string
  default = "us-east-1"
}

variable "project_name" {
  type    = string
  default = "proyecto-backend"
}

variable "artifact_bucket_name" {
  type = string
}

variable "artifact_key" {
  type    = string
  default = "backend.jar"
}

variable "jar_file_path" {
  type = string
}

variable "ami_id" {
  type = string
  description = "AMI de Ubuntu 24.04 para tu región"
}

variable "db_name" {
  type    = string
  default = "proyecto_db"
}

variable "db_username" {
  type = string
}

variable "db_password" {
  type      = string
  sensitive = true
}

variable "auth0_issuer" {
  type = string
}

variable "auth0_audience" {
  type = string
}

variable "admin_cidr" {
  type    = string
  default = "0.0.0.0/0"
}