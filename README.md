优品家居


import medicine.sql by command
sudo -u <user for psql in os> psql caringdb < ./your.sql
example: sudo -u caring psql caringdb < ./your.sql 

config nginx payload:
  server
        {
             listen 80;
             location /caring/ {
                proxy_pass http://localhost:9090/caring/;
             }
             location /caring_wxrs/ {
                proxy_pass http://localhost:9091/caring_wxrs/;
                client_max_body_size 25M;
             }
             location / {
                root /home/caring/Caring;
             }
        }
