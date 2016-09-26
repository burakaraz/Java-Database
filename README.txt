Burak Araz
1818939

1) "javac *.java"
2) "java -cp .:mysql-connector-java-5.1.35-bin.jar Server" in one terminal
3) "java Client" in other terminal
4) when you run the Client the following output shows up

-when you enter the 'get <filename>' and if the file hash is not in the database, first it is added to the db, then read into a serialize file.
-If you want to exit, enter 'exit'.
-Input format must be in 'get <filename>' 'update <filename>' 'read <filename> | *'
-Please enter input:

5) For get operation enter;

-get <filename>
-<newFileName>

For example;

-get input0
-Please enter new output name:
-output0

6) For read operation enter;

-read <filename>
-read *

For example;

-read output2
Song Informations 
Song id: 3
Song Name: asdsa
Song Artist: ""
Song Album:ritchie blackmore's rainbow
Song Genre:classical  rock
Song Year: 1975
Song Hash:a6369ea7a1fd7fb1fc7c055218f894ac

OR

-read *

7) For update operation enter;

update <filename>

For example;
-update output0
Enter the name: (If you dont want to change, just press enter)
-miles
Enter the artist: (If you dont want to change, just press enter)
-duman
Enter the album: (If you dont want to change, just press enter)
-sak
Enter the genre: (If you dont want to change, just press enter)

Enter the year: (If you dont want to change, just press enter)

8) For exit operation enter;

-exit

Assumptions;
All .audio files must be in the same place with the source code.
All .ser files are produced in the same place with source code.