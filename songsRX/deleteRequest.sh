#! /bin/sh
#
# test.json ist eine Test-Datei, deren Inhalt als Payload an den Server geschickt wird
#

curl -X DELETE \
     -H "Content-Type: application/json" \
     -H "Accept: */*" \
     -v "http://localhost:8080/songsRX/rest/songs/64"

echo "-------------------------------------"

#curl -X POST -H "Content-Type: application/json" -H "Accept: text/plain" -d "@test.json" -v "http://localhost:8080/songsRx/rest/songs"
