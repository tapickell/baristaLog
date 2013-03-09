#1/bin/bash

while true
do
  calabash-android run baristaLog/bin/BaristaLog.apk
  echo "Press CTRL + C to stop..."
  sleep 1
done