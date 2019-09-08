# Pairwise Sequence Aligner [![Build Status](https://travis-ci.org/marco-ruiz/seqAlign.svg?branch=master)](https://travis-ci.org/marco-ruiz/seqAlign) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

This project consists of:
- An abstract framework to compute sequence alignments.
- An implementation of the pairwise alignment algorithms for local, global, repeated and overlap global, plugged into the framework.
- A series of front end applications which (leveraging on the framework) allow end users to align sequences.

## ReactJS Application

- Web application built using Javascript, React and Material UI.
- A hosted version can be found at: [https://pairwise-alignment.herokuapp.com/](https://pairwise-alignment.herokuapp.com/) 

#### Usage

- Launch the web server by issuing the following command from the cloned project directory:

```
./gradlew bootRun
```

- Alternatively you can launch the web server by issuing the following command that starts a docker container with the 
[appropriate docker image](https://hub.docker.com/r/marcoruiz/align-ws):

```
docker run -p 8080:8080 marcoruiz/align-ws:master
```

- Point your browser to `http://localhost:8080/`
- Enter the parameters of the alignment and press the `Align` button


## Web Service Application

- Spring boot based REST web service.
- The requests are submitted using `POST` verb (rather than `GET`) since the sequences to aligned may be lengthy.
- By default the web service runs on port `8080`, and the resource URI is `api/align`.

#### Usage

- Launch the web service by issuing the following command from the cloned project directory:

```
./gradlew bootRun
```

- Submit a `POST` web request to the running web service (by default `http://localhost:8080/api/align`) with a payload such as the following:

```
{
  "sequenceA" : "TNAKTAKVCQSFAWNEENTQKAVSMYQQLINENGLDFANSDGLKEIAKAVGAASPVSVRSKLTS",
  "sequenceB" : "STVSPVFVCQSFAKNAGMYGERVGAVGAASPVSCFHLALTKQAQNKTIKPAVTSQLAKIIRSEVSNPPA",
  "scoringMatrixName" : "BLOSUM62",
  "alignmentType": "LOCAL",
  "maxNumberOfSolutions" : 50,
  "fixGapPenalty" : 12,
  "varGapPenalty" : 2,
  "minScore" : 10
}
```

- From the command line you could issue a curl command like the following:

```
curl 'http://localhost:8080/api/align' -i -X POST -H 'Content-Type: application/json' -d \
'{
  "sequenceA" : "TNAKTAKVCQSFAWNEENTQKAVSMYQQLINENGLDFANSDGLKEIAKAVGAASPVSVRSKLTS",
  "sequenceB" : "STVSPVFVCQSFAKNAGMYGERVGAVGAASPVSCFHLALTKQAQNKTIKPAVTSQLAKIIRSEVSNPPA",
  "scoringMatrixName" : "BLOSUM62",
  "alignmentType": "LOCAL",
  "maxNumberOfSolutions" : 50,
  "fixGapPenalty" : 12,
  "varGapPenalty" : 2,
  "minScore" : 10
}'
```

## Swing Application

![](README/sequence-aligner.gif)


#### Usage

- Launch the swing application by issuing the following command from the cloned project directory:

```
./gradlew run
```

- The initial interface provides you with inputs to specify the parameters for the alignment to compute:

    1. `Sequence A` and `Sequence B` to be aligned. The application comes preset with a couple of example sequences in
these fields (for demo purposes).
    2. `Scoring matrix` to be used (`BLOSUM 50`, `BLOSUM 62` or `PAM 250`)
    3. Values for gap penalties.
    4. Cut solutions parameters.
    5. Type of alignment to be applied (`Local`, `Repeated Local`, `Global`, `Overlap Global`)

- Once you are ready to run the alignment just press the `Run Pairwise Alignment!` button. The application will switch to
the `results` view, presenting the following information:

    1. A graphical view (`Alignment Plot`) of the currently selected `alignment solution`.
    2. A drop down control to select which one of the `alignment solution`s computed to currently explore.
    3. Statistics about the currently selected `alignment solution` (score, positives, identities, length and sequences sizes).
    4. A textual representation of the currently selected `alignment solution`. The format is similar to FASTA.

