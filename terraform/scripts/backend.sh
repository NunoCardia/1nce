#!/bin/bash
### Basic Infrastructure Installation ###
sudo yum update -y
# CloudWatch agent
sudo yum install amazon-cloudwatch-agent -y
sudo yum install git -y
# Java
sudo amazon-linux-extras install java-openjdk11 -y
# Maven
sudo wget https://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
sudo sed -i s/\$releasever/6/g /etc/yum.repos.d/epel-apache-maven.repo
sudo yum install -y apache-maven
# CW agent configuration
sudo bash -c 'cat > /opt/aws/amazon-cloudwatch-agent/etc/amazon-cloudwatch-agent.json << EOL
{
    "logs": {
        "logs_collected": {
            "files": {
                "collect_list": [
                    {
                        "file_path": "/home/ec2-user/apps/triangle-app/logs/triangle-app.log",
                        "log_group_name": "triangle-logs",
                        "log_stream_name": "triangle-stream"
                    }
                ]
            }
        }
    }
}
EOL'
sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl -a fetch-config -m ec2 -c file:/opt/aws/amazon-cloudwatch-agent/etc/amazon-cloudwatch-agent.json -s

# Set up environment variables
export JAVA_HOME=$(readlink -f /usr/bin/java | sed "s:bin/java::")
source ~/.bashrc

### BackEnd Deployment ###
sudo mkdir -p /home/ec2-user/apps/triangle-app
sudo git clone https://github.com/NunoCardia/1nce.git /home/ec2-user/apps/triangle-app
cd /home/ec2-user/apps/triangle-app
sudo sed -i -r "s/localhost:3306/${rds_instance_endpoint}/g" triangle-application/src/main/resources/application.properties
sudo mvn clean install -DskipTests -Djib.skip
### Fixing file permissions ###
sudo chmod 777 triangle-application/target/triangle-application-0.1.0.jar

### Running the application ### 
sudo java -jar triangle-application/target/triangle-application-0.1.0.jar

