# Improvements

For 1,2 and 5 switch the key and value, should be faster

For 3 and 4, use a multidimensional array.

First, create a Map<Integer[], String[]>
The key of the map will be the bit pattern,
The string will be the bitrate/sample rate in each scenario.
The string array will contain the bitrate/sample rate in a comma 
separated array of strings.

Use the version and layers as switches to get the index to retrive from
the String array in the map.




