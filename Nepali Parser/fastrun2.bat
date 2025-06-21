 REM //PARSER FOR NEPALI LANGUAGE IS BUILT UNDER THE DST FUNDED PROJECT SR/CSRI/28/2015
REM // DEVELOPED BY PROF. (DR) ARCHIT YAJNIK, PRINCIPLE INVESTIGATOR
REM // DATE OF SUBMISSION : 05-11-2020
REM // TECHNIQUES : 1. AUGMENTING PATH
REM // 2. MAXIMUM MATCHING BIPARTITE GRAPH
REM // 3. GENERAL REGRESSION NEURAL NETWORKS
REM // 4. RULE BASED INTRA-CHUNK DEPENDENCY  PARSER
REM // 5. INPUT: NEPALI SENTENCE
REM // 6. OUTPUT: LOGO.PNG (PARSED TREE)
REM // 7. BATCH FILE: FASTRUN2.BAT
REM // 8. PART OF SPEECH TAGGED OUTPUT FILE POSOUT.TXT
REM // INPUT NEPALI SENTENCES FILES ARE T00.TXT AND TRANS.TXT // AVAILABLE IN EMAIL (ASHISH PRADHAN)

 path=%path%;C:\Program Files\Java\jdk-24\bin
 @echo off
 javac -encoding utf8 CreateXmlFileDemo2.java
 javac -encoding utf8 ReadXMLFile.java
 javac -Xlint:unchecked Mymatching1.java
 javac -encoding utf8 Chunker.java
 javac -encoding utf8 PaintNodes2.java
 javac -encoding utf8 TagGRNN.java
 javac -encoding utf8 GRNN5.java
 javac -encoding utf8 Lwg7.java
 javac -encoding utf8 AutoCorrector.java
 echo CONVERT UTF16 TO UTF8
 
setlocal enableextensions enabledelayedexpansion
set /a count = 0
REM for /f "tokens=*" %%s in (trainparse.txt) do (
DEL input.txt
for /f "tokens=*" %%s in (testpaper.txt) do (
   echo %%s
   @echo %%s > sentence.txt
   
   java -Xmx1000m GRNN5  sentence.txt  > t11.txt
   java -Xmx10000m AutoCorrector annoutput.txt smallmaindata1.txt >t111.txt
   java Lwg7 out[!count!].png smallmaindata1.txt > t.txt
   rem type t1.txt > st1.txt

   REM java Lwg4 Logo.png > t.txt
   REM REN   annoutput.txt posout.txt
   REN   annoutput.txt posout[!count!].txt
   REM REN input.txt output.txt
  
  set /a count += 1
  echo !count!
 
)
endlocal

pause


SET var[0]=A
SET var[1]=foo bar
SET var[2]=123
for %%a in (0,1,2) do (
    echo !var[%%a]!
)
