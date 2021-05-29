To build inverted index and search tokens in it you have to create run configuration:
* Choose ```Run -> Run... -> Edit configuration```
* Click ```+```, choose ```Application``` 
* Set main class to ```org.cubain.App```

To perform measurements:
* Repeat steps above and set main class to ```org.cubain.measurements.Measurements```
\
  There is also class MeasurementSettings in which you can choose:
  * number of threads
  * number of files to process 
  * step of processing
  
Measurement results ar written to ```/src/main/java/resources/measurements``` as ```.csv```\
To get graph of measurements run script ```plot.py``` in IDE or in CLI with ```python plot.py```
or ```python3 plot.py``` if using linux