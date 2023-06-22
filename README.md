<br/>
<p align="center">
  <h3 align="center">Audio Reader</h3>

  <p align="center">
    A simple information extractor that works on audio files
    <br/>
    <br/>
  </p>
</p>



## Table Of Contents

* [About the Project](#about-the-project)
* [Built With](#built-with)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
* [Demonstration](#demonstration)

## About The Project

### Overview: 
The program extracts information from the audio file to determine the files MPEG Audio Version ID, MPEG Layer, Bitrate, Sample rate and Channel Mode.

### Project Justification: 
This was built as part of a take home task for a coding interview.

### Objectives: 
Write a small command-line tool in Java, that reads the first audio frame from an MP3 file and prints the following information about the file to console:
1. the MPEG Audio Version ID of the file
2. the MPEG Layer of the file
3. the bitrate of the file
4. the sample rate of the file
5. the channel mode of the file

### Assumptions:
- the first frame of the audio starts at the first position in the file (i.e. there is nothing extra in the file other than the audio)
- the audio was encoded with an encoder built to this spec: http://www.mp3-tech.org/programmer/frame_header.html (hint: this is where you will find all the information as to how to parse the frame header, which is all you will need to do)
- all frames in the file are the same, so you only need to parse the first audio frame and then exit
- you may use any build tool you like (i.e gradle/maven), but the solution must be written in Java, and you must not use any third party packages to parse the audio
- to help judge the correctness of your solution, feel free to use third party tools to compare results

### Acceptance Criteria: 
- correctness of the code, does it print the right values ? 
- how many of the tasks you were able to complete successfully
- clean code / tests, where you feel appropraite to add them in
- the performance of your code, does it use memory and compute efficiently ? 

### Target Result if LBCNews.mp3 is used:
1. MPEG Version 1
2. Layer 3
3. 256
4. 44100
5. 3 (Mono)

## Built With



* [Java](https://docs.oracle.com/en/java/)

## Getting Started

To get a local copy up and running follow these simple example steps.

### Prerequisites

If you want to use your own mp3 instead of the supplied mp3, place your mp3 file where the LBCNews.mp3 file is and in the AudioReaderTest.java file, replace the name of the file with the name of your mp3 file.

### Installation

1. Clone the repo

```sh
git clone https://github.com/your_username_/Project-Name.git
```

2. Compile/Build the java file

```sh
javac src/main/org.k5924/AudioReader.java
```

3. Run Java file

> This project has no main method thus, you can execute the code via the AudioReaderTest.java

## [Demonstration](https://www.youtube.com/playlist?list=PLnvYMKNt9C8jag0asE4nt6g-77jc4SMFL)
