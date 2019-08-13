# Fallen Finns RDF parser


With this application one may parse the [RDF](https://en.wikipedia.org/wiki/Resource_Description_Framework) 
file that contains the database of Finns who died in the wars between 1939 and 1945, and that is 
[available from avoindata.fi](https://www.avoindata.fi/data/fi/dataset/suomen-sodissa-1939-1945-menehtyneet/resource/77a650c9-aab1-4026-83a2-5b0dfcb6df18)
(approximately 200 MB of XML). Be aware that depending on your computer, it may take even a minute for the application
to read in the data.

## Usage

Give the path to the RDF file as the first argument to the application.
Give the name of the unit (such as "3./JP 2") that you are interested in as the second parameter.
The application then prints the names and some more information on everyone who died in that unit.
