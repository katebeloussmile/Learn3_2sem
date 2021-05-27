from os import listdir
from os.path import isfile, join
import csv
import matplotlib.pyplot as plt
measurementsPath = "src/main/resources/measurements"
for file in [f for f in listdir(measurementsPath) if isfile(join(measurementsPath, f))]:
    with open(f"{measurementsPath}/{file}") as csvfile:
        csvreader = csv.reader(csvfile)
        threads = []
        millis = []
        next(csvreader)
        for row in csvreader:
            threads.append(int(row[0]))
            millis.append(int(row[1]))
        file_count = file.split(".")[0]
        plt.plot(threads, millis, label=f"{file_count} files")
plt.xlabel('threads')
plt.ylabel('time, ms')
plt.legend()
plt.show()