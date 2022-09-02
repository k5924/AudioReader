# Improvements

1. Getting MPEG Audio Version

Store results in a Map<String, List[Integer]> where string is the mpeg audio version, list contains the bit patterns.

Check if map contains specified bit pattern, return the key of that map which is the MPEG Audio Version string.

Would add an if statement to increment the version counter.

2. MPEG Layer

Same solution as 1 but key is MPEG Layer

Would add an if statement to increment the layer counter.

3. Bitrate

This needs work, The proposed solution would still need individual checks for each combination as all the combinations have different bitrates except some of them.
Proposed: 
Have a multidimensional array, int[][] storing all the bit patterns. Iterate through a multidimensional array, if the bit pattern matches the pattern from mp3 audio file header....

4. Sample rate

Same as bitrate. Because the bitrate and sample rate are dependent on layer and version, i cant think of a better way to improve the algorithm

5. Channel mode

Same as 1 and 2 but the key would be the channel mode
